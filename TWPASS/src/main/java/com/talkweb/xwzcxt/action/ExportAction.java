package com.talkweb.xwzcxt.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.talkweb.twdpe.core.ArgumentOutOfRangeException;
import com.talkweb.twdpe.core.Convert;
import com.talkweb.twdpe.core.io.FileNotFoundException;
import com.talkweb.twdpe.core.io.IOException;
import com.talkweb.twdpe.web.action.ComponentAction;
import com.talkweb.twdpe.web.jcontrols.CsvWriter;
import com.talkweb.twdpe.web.jcontrols.ExcelWriter;
import com.talkweb.twdpe.web.jcontrols.IExportWriter;
import com.talkweb.twdpe.web.jcontrols.PdfWriter;

/**
 * 提供 DataGrid 数据导出功能。
 * 
 * @author: 2013-3-22，yidi。
 */
public class ExportAction extends ComponentAction {
	public static final int RenderBufferSize = 1024 * 100;

	public static final String Pdf = "pdf";

	public static final String Xls = "xls";

	public static final String Csv = "csv";

	private static final char TextSeparator = '@';

	private static final char SpanSeparator = '-';

	private String _result;

	private String _contentType;

	private String _contentDisposition;

	private String _exportFileName;

	public String getResult() {
		return _result;
	}

	public String getContentType() {
		return _contentType;
	}

	public String getContentDisposition() {
		return _contentDisposition;
	}

	public InputStream getInputStream() {
		try {
			return new FileInputStream(_exportFileName);
		} catch (java.io.FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	public String exportStream() {
		_exportFileName = ServletActionContext.getServletContext().getRealPath(
				"/")
				+ "/export/765f19b8aed04ee58e5aa3774943f0ee.pdf";
		renderHeader(ServletActionContext.getResponse(), Pdf);
		return SUCCESS;
	}

	/**
	 * 导出数据。 expData：JSON格式的导出数据。 expId：导出会话ID。 expEnd：0：导出尚未结束，1：导出已结束。
	 * expType：pdf，xls，csv。 expMode：0：url，1：流。
	 * 返回消息：如果是最后一次数据传递则为导出文件URL，否则为导出会话ID。
	 */
	public String exportGrid() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String exportData = request.getParameter("expData");
		String exportId = request.getParameter("expId");
		String exportEnd = request.getParameter("expEnd");
		String exportType = request.getParameter("expType");
		String exportMode = request.getParameter("expMode");
		String exportPath = ServletActionContext.getServletContext()
				.getRealPath("/") + "/export";
		if ((exportId == null) || (exportId.length() == 0)
				|| exportId.equals("null") || exportId.equals("undefined")) {
			exportId = UUID.randomUUID().toString().replace("-", "");
		}

		String bufferFileName = exportPath + "/" + exportId;
		File dir = new File(exportPath);
		if (!dir.exists()) {
			dir.mkdir();
		}
		if (exportData != null) {
			writeBuffer(bufferFileName, exportData);
		}

		if ("1".equals(exportEnd)) {
			/*
			 * if ((exportData != null) && "1".equals(exportMode)) { String url
			 * = request.getRequestURI(); StringBuilder urlBuilder = new
			 * StringBuilder(url); if (url.indexOf('?') == -1) {
			 * urlBuilder.append("?expId=").append(exportId); } else {
			 * urlBuilder.append("&expId=").append(exportId); }
			 * urlBuilder.append("&expEnd=").append(exportEnd);
			 * urlBuilder.append("&expType=").append(exportType);
			 * urlBuilder.append("&expMode=").append(exportMode); try {
			 * response.sendRedirect(urlBuilder.toString()); } catch (Exception
			 * e) { e.printStackTrace(); } } else {
			 */
			try {
				FileInputStream in = new FileInputStream(bufferFileName);
				_exportFileName = bufferFileName + "." + exportType;
				OutputStream out = new FileOutputStream(_exportFileName);
				if ("1".equals(exportMode)) {
					renderHeader(response, exportType);
				}
				IExportWriter exportWriter = getExportWriter(exportType, out);
				boolean isFirst = true;
				int columnCount = 0;
				while (true) {
					byte[] lenBytes = new byte[4];
					if (in.read(lenBytes) == -1) {
						break;
					}
					int dataLen = Convert.toInteger(lenBytes, 0);
					byte[] dataBytes = new byte[dataLen];
					in.read(dataBytes);
					String dataString = new String(dataBytes, "utf-8");
					columnCount = writeExport(exportWriter, dataString,
							columnCount, isFirst, false);
					isFirst = false;
				}
				in.close();
				File bufferFile = new File(bufferFileName);
				bufferFile.delete();
				exportWriter.writeEndTable();
				exportWriter.close();
				out.flush();
				out.close();
			} catch (java.io.FileNotFoundException e) {
				throw new FileNotFoundException(bufferFileName);
			} catch (java.io.IOException e) {
				throw new IOException(e.getMessage());
			}
			// }
			_result = request.getContextPath() + "/export/" + exportId + "."
					+ exportType;
		} else {
			_result = exportId;
		}
		return SUCCESS;
	}

	private void download(String path, HttpServletResponse response) {
		try {
			// path是指欲下载的文件的路径。
			File file = new File(path);
			// 取得文件名。
			String filename = file.getName();
			// 以流的形式下载文件。
			InputStream fis;

			fis = new BufferedInputStream(new FileInputStream(path));

			byte[] buffer;
			buffer = new byte[fis.available()];

			fis.read(buffer);
			fis.close();

			// 清空response
			response.reset();
			// 设置response的Header
			response.addHeader("Content-Disposition", "attachment;filename="
					+ new String(filename.getBytes()));
			response.addHeader("Content-Length", "" + file.length());
			OutputStream toClient = new BufferedOutputStream(
					response.getOutputStream());
			response.setContentType("application/vnd.ms-excel;charset=gb2312");
			toClient.write(buffer);
			toClient.flush();
			toClient.close();
		} catch (java.io.IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private IExportWriter getExportWriter(String exportType, OutputStream out) {
		if (Pdf.equals(exportType)) {
			return new PdfWriter(out);
		} else if (Xls.equals(exportType)) {
			return new ExcelWriter(out);
		} else if (Csv.equals(exportType)) {
			return new CsvWriter(out);
		} else {
			throw new ArgumentOutOfRangeException("type", exportType);
		}
	}

	private void writeBuffer(String fileName, String exportData) {
		try {
			FileOutputStream out = new FileOutputStream(fileName, true);
			byte[] bytes = exportData.getBytes("utf-8");
			out.write(Convert.toBytes(bytes.length));
			out.write(bytes);
			out.flush();
			out.close();
		} catch (java.io.FileNotFoundException e) {
			throw new FileNotFoundException(fileName);
		} catch (java.io.IOException e) {
			throw new IOException(e.getMessage());
		}
	}

	private int writeExport(IExportWriter exportWriter, Object exportData,
			int columnCount, boolean isFirst, boolean isEnd) {
		JSONObject exportJson = JSONObject.fromObject(exportData);
		JSONArray rowsJson = exportJson.getJSONArray("data");
		int rowCount = rowsJson.size();
		if (isFirst) {
			// int headerCount = exportJson.getInt("titlecount");
			int columnIndex = 0;
			JSONArray columnsJson = exportJson.getJSONArray("colwidth");
			columnCount = columnsJson.size();
			float[] columnWidths = new float[columnCount];
			for (int i = 0; i < columnCount; i++) {
				// if (columnsJson.getInt(i) != 0)
				// {
				columnWidths[columnIndex++] = columnsJson.getInt(i);
				// }
			}
			float[] columnWidths2;
			if (columnIndex != columnWidths.length) {
				columnWidths2 = new float[columnIndex];
				System.arraycopy(columnWidths, 0, columnWidths2, 0, columnIndex);
			} else {
				columnWidths2 = columnWidths;
			}
			columnCount = columnWidths2.length;
			exportWriter.writeStartTable(columnWidths2);
		}

		for (int i = 0; i < rowCount; i++) {
			JSONArray rowJson = rowsJson.getJSONArray(i);
			int cellCount = rowJson.size();
			for (int j = 0; j < columnCount && j < cellCount; j++) {
				String value = rowJson.getString(j);
				int textEnd = value.indexOf(TextSeparator);
				int spanSeparator = value.indexOf(SpanSeparator, textEnd + 1);
				int rowSpan = Integer.valueOf(value.substring(textEnd + 1,
						spanSeparator));
				int colSpan = Integer.valueOf(value
						.substring(spanSeparator + 1));
				exportWriter.writeCell(value.substring(0, textEnd));
			}
		}
		if (isEnd) {
			exportWriter.writeEndTable();
		}
		return columnCount;
	}

	private void renderHeader(HttpServletResponse response, String exportType) {
		response.reset();
		response.addHeader("Connection", "Keep-Alive");
		response.addHeader("Content-Transfer-Encoding", "binary");

		if (exportType.equals(Xls)) {
			_contentType = "application/vnd.ms-excel";
		} else if (exportType.equals(Pdf)) {
			_contentType = "application/pdf";
		} else if (exportType.equals(Csv)) {
			_contentType = "application/vnd.ms-excel";
		}
		_contentDisposition = "attachment;" + "filename=\"export." + exportType
				+ "\"";
	}
	
	
	

}
