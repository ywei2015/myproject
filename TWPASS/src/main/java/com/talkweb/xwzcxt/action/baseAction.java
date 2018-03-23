package com.talkweb.xwzcxt.action;

import java.beans.IntrospectionException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.talkweb.twdpe.base.org.pojo.User;
import com.talkweb.twdpe.core.util.Output;
import com.talkweb.twdpe.core.util.json.JsonArray;
import com.talkweb.twdpe.web.commons.StaticUploadInfo;
import com.talkweb.twdpe.web.session.main.Session;
import com.talkweb.twdpe.web.session.util.SessionFactory;
import com.talkweb.twdpe.web.upload.exception.IllegalEnctypeException;
import com.talkweb.twdpe.web.upload.exception.NotFoundStoragePathException;
import com.talkweb.twdpe.web.upload.impl.File;
import com.talkweb.twdpe.webcomponent.common.action.BusinessAction;
import com.talkweb.xwzcxt.util.ExcelDataUtil;
import com.talkweb.xwzcxt.util.ExcelReadUtil;
//import com.talkweb.xwgl.Json.MessageJson;
//import com.talkweb.xwgl.service.SecurityAccessServiceI;


import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;

//@Namespace("/")
//@ParentPackage("demoPackage")
public class baseAction extends BusinessAction {
	// extends BusinessAction

	public void writeJson(Object obj) {

		try {

			String json = JSON.toJSONString(obj);
			// String json = JSON.toJSONStringWithDateFormat(obj,
			// "yyyy-MM-dd HH:mm:ss");
			ServletActionContext.getResponse().setContentType(
					"text/html;charset=utf-8");
			ServletActionContext.getResponse().getWriter().write(json);
			ServletActionContext.getResponse().getWriter().flush();
			ServletActionContext.getResponse().getWriter().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void writeJson(Object obj, String Cookie) {

		try {
			String json = JSON.toJSONString(obj);

			ServletActionContext.getResponse().setContentType(
					"text/html;charset=utf-8");
			ServletActionContext.getResponse().setHeader("Cookie", Cookie);
			ServletActionContext.getResponse().getWriter().write(json);
			ServletActionContext.getResponse().getWriter().flush();
			ServletActionContext.getResponse().getWriter().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void writeImage(Object Path) {
		try {
			ServletActionContext.getResponse().setContentType("image/jpeg");
			FileInputStream in = new java.io.FileInputStream(Path.toString());
			if (in != null) {
				byte[] b = new byte[1024];
				int len;
				while ((len = in.read(b)) != -1) {
					ServletActionContext.getResponse().getOutputStream()
							.write(b);
				}

				in.close();
			}

			// 创建可用来将图像数据编码为JPEG数据流的编码器
			// 强行将缓冲区的内容输入到页面
			ServletActionContext.getResponse().getOutputStream().flush();
			// 关闭输出流
			ServletActionContext.getResponse().getOutputStream().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 文件的物理路径
	 */
	public List<String[]> importExcelData() {
		List<File> files = null;
		try {
			files = this.getUploadFile();
		} catch (IllegalArgumentException e1) {
			e1.printStackTrace();
		} catch (IllegalEnctypeException e1) {
			e1.printStackTrace();
		} catch (FileUploadException e1) {
			e1.printStackTrace();
		} catch (NotFoundStoragePathException e1) {
			e1.printStackTrace();
		}
		List<String[]> list = null;
		if (files != null && files.size() > 0) {
			String filePath = files.get(0).getPath();
			try {
				list = ExcelReadUtil.readExcel(filePath);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	/**
	 * 获取上传数据以及文件名
	 */
	public Map getImportExcelData() {
		Map map = new HashMap();
		List<File> files = null;
		try {
			files = this.getUploadFile();
		} catch (IllegalArgumentException e1) {
			e1.printStackTrace();
		} catch (IllegalEnctypeException e1) {
			e1.printStackTrace();
		} catch (FileUploadException e1) {
			e1.printStackTrace();
		} catch (NotFoundStoragePathException e1) {
			e1.printStackTrace();
		}
		List<String[]> list = null;
		if (files != null && files.size() > 0) {
			String filePath = files.get(0).getPath();
			try {
				list = ExcelReadUtil.readExcel(filePath);
			} catch (Exception e) {
				e.printStackTrace();
			}
			map.put("fileName", filePath.substring(filePath.indexOf("_") + 1));
		}
		map.put("list", list);
		return map;
	}

	/***************************************************************************************************************************************************************************************************
	 * 获取上传文件的文件信息
	 **************************************************************************************************************************************************************************************************/
	public List<File> getUploadFile() throws IllegalArgumentException,
			IllegalEnctypeException, FileUploadException,
			NotFoundStoragePathException {
		String path = ServletActionContext.getRequest().getSession()
				.getServletContext()
				.getRealPath(StaticUploadInfo.upload_system_import);
		this.creatDirectory(path);
		List<File> fileLists = new ArrayList<File>();
		try {
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			List list = upload.parseRequest(ServletActionContext.getRequest());
			FileItem item = null;
			for (int i = 0; i < list.size(); i++) {
				item = (FileItem) list.get(i);
				// 上传文件
				if (!item.isFormField()) {
					try {
						File uploadedFile = new File(path
								+ new Date().getTime() + "-" + item.getName());
						item.write(uploadedFile);
						fileLists.add(uploadedFile);
					} catch (Exception e) {
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileLists;
	}

	public void formatData(Object o) {
//		if (o instanceof Map || o instanceof List) {
//			super.formatData(o);
//		} else {
//			try {
//				Map map = this.addPrefix(o, "");
//				Object obj = null;
//				for (Object key : map.keySet()) {
//					obj = map.get(key);
//					if (obj != null) {
//						if (obj instanceof Double && ((Double) obj) == 0) {
//							map.put(key, "0");
//						} else if (obj instanceof Float && ((Float) obj) == 0) {
//							map.put(key, "0");
//						} else if (obj instanceof Long && ((Long) obj) == 0) {
//							map.put(key, "0");
//						} else if (obj instanceof Integer
//								&& ((Integer) obj) == 0) {
//							map.put(key, "0");
//						}
//					}
//				}
//				super.formatData(map);
//				return;
//			} catch (Exception e) {
				super.formatData(o);
//			}
//		}
	}

	/**
	 * 判断文件目录存不存在，如果不存在，则创建文件目录
	 * 
	 * @param path
	 *            目录路径
	 */
	private void creatDirectory(String path) {
		java.io.File f = new java.io.File(path);
		if (!f.exists())
			f.mkdirs();
	}

	/**
	 * 按指定的输出表头，属性列表，数据对象导出Excel文件的公用方法
	 * 
	 * @param titles
	 *            String[] Excel文件要显示的表头，按顺序显示
	 * @param fields
	 *            String[] 对象的属性名，对应相应的表头
	 * @param fileName
	 *            文件名，不带后缀
	 * @param resultData
	 *            List 要转换的数据对象
	 * @return String 返回的文件链接
	 * */
	public String exportExcelData(String[] titles, String[] fields,
			String fileName, List resultData) {
		return this.exportExcelData(titles, fields, fileName, resultData,
				"sheet1");
	}

	/**
	 * 按指定的输出表头，属性列表，数据对象导出Excel文件的公用方法
	 * 
	 * @param titles
	 *            String[] Excel文件要显示的表头，按顺序显示
	 * @param fields
	 *            String[] 对象的属性名，对应相应的表头
	 * @param fileName
	 *            文件名，不带后缀
	 * @param resultData
	 *            List 要转换的数据对象
	 * @return String 返回的文件链接
	 * */
	public String exportExcelData(String[] titles, String[] fields,
			String fileName, List resultData, String sheetName) {
		HttpServletRequest request = ServletActionContext.getRequest();
		String path = request.getSession().getServletContext()
				.getRealPath(StaticUploadInfo.upload_system_export);
		this.creatDirectory(path);
		String url = null;
		Output[] outputs = ExcelDataUtil.getOutputList(titles, fields);
		try {
			String filePath = path + "/" + fileName + ".xls"; // 统一输出2003格式的excel
			ExcelDataUtil.writeExcelXls(filePath, outputs, resultData,
					sheetName);
			String projectPath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ request.getContextPath() + "/";
			url = projectPath
					+ StaticUploadInfo.upload_system_export.substring(1)
					+ fileName + ".xls";
		} catch (IntrospectionException e) {

		} catch (IOException e) {

		}
		return url;
	}

	/**
	 * 导出多个sheet的Excel
	 * 
	 * @param names
	 *            List<String> 各个sheet的名称
	 * @param titles
	 *            List<String[]> 每个sheet里面的表头
	 * @param fields
	 *            List<String[]> 每个sheet里面的字段
	 * @param datas
	 *            List<List> 每个sheet里面的数据项
	 * @param fileName
	 *            String 导出文件名
	 * @return 文件URL路径
	 */
	public String exportExcelWithMultiSheet(List<String> names,
			List<String[]> titles, List<String[]> fields, List<List> datas,
			String fileName) {
		HttpServletRequest request = ServletActionContext.getRequest();
		String path = request.getSession().getServletContext()
				.getRealPath(StaticUploadInfo.upload_system_export);
		this.creatDirectory(path);
		String url = null;
		List<Output[]> headers = new ArrayList<Output[]>();
		for (int i = 0; i < titles.size(); i++) {
			headers.add(ExcelDataUtil.getOutputList(titles.get(i),
					fields.get(i)));
			if (names.get(i) == null) {
				names.set(i, "sheet" + i);
			}
		}
		try {
			String filePath = path + "/" + fileName + ".xls"; // 统一输出2003格式的excel
			ExcelDataUtil.writeExcelXlsWithMultiSheet(filePath, names, headers,
					datas);
			String projectPath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ request.getContextPath() + "/";
			url = projectPath
					+ StaticUploadInfo.upload_system_export.substring(1)
					+ fileName + ".xls";
		} catch (IntrospectionException e) {

		} catch (IOException e) {

		}
		return url;
	}
	
	/**
	 * 导出消火栓灭火器检查记录卡
	 * @param titles
	 * @param typeId
	 * @param fileName
	 * @param resultData
	 * @param sheetName
	 * @return
	 */
	public String exportExcelCheckCard(String[] titles, Long typeId,
			String fileName, List resultData, String sheetName) {
		HttpServletRequest request = ServletActionContext.getRequest();
		String path = request.getSession().getServletContext()
				.getRealPath(StaticUploadInfo.upload_system_export);
		this.creatDirectory(path);
		String url = null;
		
		if(typeId != 11006){
			fileName = "mhqCardExport";
			sheetName = "灭火器检查记录卡";
		}
		String filePath = path + "/" + fileName + ".xls"; // 统一输出2003格式的excel
 
		try {
			if(typeId == 11006){
				ExcelDataUtil.writeExcelXhs(filePath, titles, resultData, sheetName);
			}else{
				ExcelDataUtil.writeExcelMhq(filePath, titles, resultData, sheetName);
			}
			
			String projectPath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ request.getContextPath() + "/";
			url = projectPath
					+ StaticUploadInfo.upload_system_export.substring(1)
					+ fileName + ".xls";
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return url;
	}

}
