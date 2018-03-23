package com.talkweb.xwzcxt.util;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFSimpleShape;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.talkweb.twdpe.core.util.Output;
import com.talkweb.xwzcxt.pojo.CheckCardPoJo;

@SuppressWarnings("unchecked")
public class ExcelDataUtil {

	private static final Logger logger = LoggerFactory
			.getLogger(ExcelDataUtil.class);

	/**
	 * 写 2003 Excel文件
	 * 
	 * @param filePath
	 *            待写入的文件
	 * @param head
	 *            表头
	 * @param data
	 *            数据
	 */
	public static void writeExcelXls(String filePath, Output[] head,
			List resultData) throws IntrospectionException, IOException {
		writeExcelXls(filePath, head, resultData, "sheet1");
	}

	/**
	 * 写 2003 Excel文件
	 * 
	 * @param filePath
	 *            待写入的文件
	 * @param head
	 *            表头
	 * @param data
	 *            数据
	 * @param sheetName
	 *            表名
	 */
	public static void writeExcelXls(String filePath, Output[] head,
			List resultData, String sheetName) throws IntrospectionException,
			IOException {
		String[][] data = changeDataFormat(head, resultData);
		// ExcelUtil.writeExcelXls(filePath, head, data);
		ExcelDataUtil.writeExcelXls(filePath, head, data, sheetName);
	}

	/**
	 * 按快框的ExcelUtil工具类需要的格式转换对象的数据格式
	 * 
	 * @param fields
	 *            List<Output> Excel文件要显示的表头，及属性的字段信息数组对象
	 * @param resultData
	 *            List 要转换的数据对象
	 * @return String[][]
	 * **/
	public static String[][] changeDataFormat(Output[] fields, List resultData)
			throws IntrospectionException {
		return changeDataFormat(fields, resultData, true);
	}

	/**
	 * 按快框的ExcelUtil工具类需要的格式转换对象的数据格式
	 * 
	 * @param fields
	 *            List<Output> Excel文件要显示的表头，及属性的字段信息数组对象
	 * @param resultData
	 *            List 要转换的数据对象
	 * @return String[][]
	 * **/
	public static String[][] changeDataFormat(Output[] fields, List resultData,
			boolean formatData) throws IntrospectionException {
		String[][] excelData = new String[resultData.size()][fields.length];
		for (int i = 0; i < resultData.size(); i++) {
			Object obj = resultData.get(i);
			if (obj instanceof Map) {
				Map map = (Map) obj;
				for (int j = 0; j < fields.length; j++) {
					Output o = fields[j];
					Object v = map.get(o.getField());
					if (formatData)
						v = changeValueFormat(v);
					if (v instanceof String)
						excelData[i][j] = v.toString();
					else
						excelData[i][j] = v != null ? String.valueOf(v) : "";
				}
			} else {
				Map<String, String> m = new HashMap<String, String>();
				PropertyDescriptor[] properties = java.beans.Introspector
						.getBeanInfo(obj.getClass()).getPropertyDescriptors();
				for (PropertyDescriptor field : properties) {
					String fieldName = field.getName();
					Object value = getFieldValueByName(obj, fieldName);
					if (formatData)
						value = changeValueFormat(value);
					if (value == null)
						continue;
					if (value instanceof Map) {
						Map mm = (Map) value;
						for (Object key : mm.keySet()) {
							Object v = mm.get(key);
							if (formatData)
								v = changeValueFormat(v);
							m.put(fieldName + "." + key.toString(),
									null != v ? v.toString() : "");
						}
					} else {
						m.put(fieldName, null != value ? value.toString() : "");
					}
				}
				for (int j = 0; j < fields.length; j++) {
					Output o = fields[j];
					excelData[i][j] = m.get(o.getField());
				}
			}
		}
		return excelData;
	}

	/**
	 * 按快框的ExcelUtil工具类需要的格式转换对象的数据格式
	 * 
	 * @param titles
	 *            String[] Excel文件要显示的表头，按顺序显示
	 * @param fields
	 *            String[] 对象的属性名，对应相应的表头
	 * @param resultData
	 *            List 要转换的数据对象
	 * @return String[][]
	 * **/
	public static String[][] changeDataFormat(String[] titles, String[] fields,
			List resultData) throws IntrospectionException {
		return changeDataFormat(getOutputList(titles, fields), resultData);
	}

	/**
	 * 将数组格式的表头及属性名数据转换成数组对象格式
	 * 
	 * @param titles
	 *            String[] Excel文件要显示的表头，按顺序显示
	 * @param fields
	 *            String[] 对象的属性名，对应相应的表头
	 * @return List<Output>
	 * */
	public static Output[] getOutputList(String[] titles, String[] fields) {
		int length = Math.min(titles.length, fields.length);
		Output[] outputs = new Output[length];
		for (int i = 0; i < length; i++) {
			Output o = new Output();
			o.setField(fields[i]);
			o.setText(titles[i]);
			outputs[i] = o;
		}
		return outputs;
	}

	/**
	 * 写 2003 Excel文件
	 * 
	 * @param filePath
	 *            待写入的文件
	 * @param head
	 *            表头
	 * @param data
	 *            数据
	 */
	@SuppressWarnings("deprecation")
	public static void writeExcelXls(String filePath, Output[] head,
			String[][] data, String sheetName) throws IOException {
		HSSFWorkbook hssfworkbook = new HSSFWorkbook();
		HSSFSheet hssfSheet = hssfworkbook.createSheet();
		hssfworkbook.setSheetName(0, sheetName);
		HSSFRow hssfRowHead = hssfSheet.createRow(0);
		// -----------------------------------------------
		for (int j = 0; j < head.length; j++) {
			Output h = head[j];
			HSSFCell hssfCell = hssfRowHead.createCell(j);
			hssfCell.setCellValue(h.getText());
		}
		for (int i = 0; i < data.length; i++) {
			HSSFRow hssfRowData = hssfSheet.createRow(i + 1);
			String[] row = data[i];
			for (int j = 0; j < row.length; j++) {
				HSSFCell hssfCell = hssfRowData.createCell(j);
				hssfCell.setCellValue(row[j]);
			}
		}
		// 杈撳嚭
		FileOutputStream fileoutputstream = new FileOutputStream(filePath);
		hssfworkbook.write(fileoutputstream);
		fileoutputstream.close();
	}
	/**
	 * 写带有表头的2003 Excel文件
	 * 
	 */
	public static void writeTExcelXls(String filePath,
			String[][] data, String sheetName) throws IOException {
		HSSFWorkbook hssfworkbook = new HSSFWorkbook();
		HSSFSheet hssfSheet = hssfworkbook.createSheet();
		hssfworkbook.setSheetName(0, sheetName);
//		HSSFRow hssfRowHead = hssfSheet.createRow(0);
		// -----------------------------------------------
		for (int i = 0; i < data.length; i++) {
			HSSFRow hssfRowData = hssfSheet.createRow(i + 4);
			String[] row = data[i];
			for (int j = 0; j < row.length; j++) {
				HSSFCell hssfCell = hssfRowData.createCell(j);
				hssfCell.setCellValue(row[j]);
			}
		}
		// 杈撳嚭
		FileOutputStream fileoutputstream = new FileOutputStream(filePath);
		hssfworkbook.write(fileoutputstream);
		fileoutputstream.close();
	}

	/**
	 * 分多个sheet写入2003Excel文件
	 * 
	 * @param filePath
	 * @param sheets
	 *            结构如下： { "sheet_name_1" : { "header" : Output[], "data" : List
	 *            }, "sheet_name_2" : { "header" : Output[], "data" : List } }
	 */
	public static void writeExcelXlsWithMultiSheet(String filePath,
			List<String> names, List<Output[]> headers, List<List> datas)
			throws IntrospectionException, IOException {
		List<String[][]> results = new ArrayList<String[][]>(datas.size());
		for (int i = 0; i < datas.size(); i++) {
			results.add(changeDataFormat(headers.get(i), datas.get(i)));
		}
		ExcelDataUtil.writeMultiSheet(filePath, names, headers, results);
	}

	@SuppressWarnings("deprecation")
	public static void writeMultiSheet(String filePath, List<String> names,
			List<Output[]> headers, List<String[][]> datas) throws IOException {
		// 工作簿
		HSSFWorkbook hssfworkbook = new HSSFWorkbook();
		for (int i = 0; i < names.size(); i++) {
			// 创建sheet页
			HSSFSheet hssfSheet = hssfworkbook.createSheet();
			// sheet名称
			hssfworkbook.setSheetName(i, names.get(i));
			// 取得第一行
			HSSFRow hssfRowHead = hssfSheet.createRow(0);
			// -----------------------------------------------
			Output[] header = headers.get(i);
			for (int j = 0; j < header.length; j++) {
				Output h = header[j];
				// 创建单元格
				HSSFCell hssfCell = hssfRowHead.createCell(j);
				// 对单元格赋值
				hssfCell.setCellValue(h.getText());
			}
			// -----------------------------------------------
			String[][] data = datas.get(i);
			// 数据
			for (int k = 0; k < data.length; k++) {
				HSSFRow hssfRowData = hssfSheet.createRow(k + 1);
				String[] row = data[k];
				for (int m = 0; m < row.length; m++) {
					// 创建单元格
					HSSFCell hssfCell = hssfRowData.createCell(m);
					// 对单元格赋值
					hssfCell.setCellValue(row[m]);
				}
			}
		}
		// 输出
		FileOutputStream fileoutputstream = new FileOutputStream(filePath);
		hssfworkbook.write(fileoutputstream);
		fileoutputstream.close();
	}

	/**
	 * 使用反射根据属性名称获取属性值
	 * */
	private static Object getFieldValueByName(Object o, String fieldName) {
		try {
			String firstLetter = fieldName.substring(0, 1).toUpperCase();
			String getter = "get" + firstLetter + fieldName.substring(1);
			Method method = o.getClass().getMethod(getter, new Class[] {});
			Object value = method.invoke(o, new Object[] {});
			return value;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	private static Object changeValueFormat(Object o) {
		if (o instanceof Double) {
			if (((Double) o) >= 1) {
				DecimalFormat df = new DecimalFormat("###############0.00");
				return df.format(o);
			} else {
				DecimalFormat df = new DecimalFormat("###############0.0000");
				return df.format(o);
			}
		} else if (o instanceof Date) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			return sdf.format(o);
		}
		return o;
	}

	public static void writeExcelXhs(String filePath, String[] head,
			List resultData, String sheetName) throws FileNotFoundException {

//		short heightHead = (short) 15.625 * 33;
//		short heightFoot = (short) 15.625 * 26;
		
		short height = (short)15.625 * 32;
		//列宽
		short colLeft = (short) 36 * 115;
		short colCenter = (short) 36 * 54;
		short colRight = (short) 36 * 132;
		short colLast = (short) 36 * 110;
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();
		//设置列宽
		sheet.setColumnWidth(0, colLeft);
		sheet.setColumnWidth(6, colRight);
		sheet.setColumnWidth(7, colLast);
		for (int i = 1; i < 6; i++) {
			sheet.setColumnWidth(i, colCenter);
		}

		HSSFCellStyle headstyle = workbook.createCellStyle();
		HSSFFont headFont = workbook.createFont();
		headFont.setFontName("黑体");
		headFont.setFontHeightInPoints((short) 10.5);// 设置字体大小
		headstyle.setFont(headFont);
		headstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
		headstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中

		HSSFCellStyle codestyle = workbook.createCellStyle();
		HSSFFont codeFont = workbook.createFont();
		codeFont.setFontName("宋体");
		codeFont.setFontHeightInPoints((short) 10.5);// 设置字体大小
		codestyle.setFont(codeFont);
		codestyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);// 左右居右
		codestyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中

		HSSFCellStyle unitstyle = workbook.createCellStyle();
		HSSFFont unitFont = workbook.createFont();
		unitFont.setFontName("宋体");
		unitFont.setFontHeightInPoints((short) 9);// 设置字体大小
		unitstyle.setFont(unitFont);
		unitstyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);// 左右居左
		unitstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
		unitstyle.setWrapText(true);

		HSSFCellStyle contentstyle = workbook.createCellStyle();
		HSSFFont contentFont = workbook.createFont();
		contentFont.setFontName("宋体");
		contentFont.setFontHeightInPoints((short) 9);// 设置字体大小
		contentstyle.setFont(contentFont);
		contentstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
		contentstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
		contentstyle.setWrapText(true);
		contentstyle.setBorderLeft((short) 1);
		contentstyle.setBorderRight((short) 1);
		contentstyle.setBorderBottom((short) 1);
		contentstyle.setBorderTop((short) 1);

		HSSFCellStyle[] styles = { headstyle, codestyle };
		String[] unitInfo = { "单位:" + head[0], "配置位置:" + head[1],
				"编号:" + head[2] };
		String[] values = { "室内消火栓检查记录卡", "HNZY/GL-AQ-10/04.0" };

		for (int x = 0; x < 2; x++) {
			HSSFRow theRow = sheet.createRow(x);
			theRow.setHeight(height);

			HSSFCell theCell = theRow.createCell(0);
			theCell.setCellValue(values[x]);
			theCell.setCellStyle(styles[x]);

			CellRangeAddress theRange = new CellRangeAddress(x, x, 0, 7);
			sheet.addMergedRegion(theRange);
		}

		HSSFRow unitRow = sheet.createRow(2);
		unitRow.setHeight(height);

		HSSFCell dwCell = unitRow.createCell(0);
		dwCell.setCellValue(unitInfo[0]);
		dwCell.setCellStyle(unitstyle);
		CellRangeAddress theRange = new CellRangeAddress(2, 2, 0, 1);
		sheet.addMergedRegion(theRange);

		HSSFCell pzCell = unitRow.createCell(2);
		pzCell.setCellValue(unitInfo[1]);
		pzCell.setCellStyle(unitstyle);
		CellRangeAddress pzRange = new CellRangeAddress(2, 2, 2, 5);
		sheet.addMergedRegion(pzRange);

		HSSFCell bmCell = unitRow.createCell(6);
		bmCell.setCellValue(unitInfo[2]);
		bmCell.setCellStyle(unitstyle);
		CellRangeAddress bmRange = new CellRangeAddress(2, 2, 6, 7);
		sheet.addMergedRegion(bmRange);

		String[] title = { "箱体", "水枪", "水带", "接头", "阀门", "备注", "检查人" };
		HSSFRow row = sheet.createRow(3);
		row.setHeight(height);

		HSSFCell cell = row.createCell(0);
		cell.setCellValue("时间        内容");
		cell.setCellStyle(contentstyle);

		// 画线(由左上到右下的斜线)
		HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
		HSSFClientAnchor a = new HSSFClientAnchor(0, 0, 1023, 255, (short) 0,
				3, (short) 0, 3);
		HSSFSimpleShape shape1 = patriarch.createSimpleShape(a);
		shape1.setShapeType(HSSFSimpleShape.OBJECT_TYPE_LINE);
		shape1.setLineStyle(HSSFSimpleShape.LINESTYLE_SOLID);

		for (int v = 1; v < 8; v++) {
			HSSFCell cellTitle = row.createCell(v);
			cellTitle.setCellValue(title[v - 1]);
			cellTitle.setCellStyle(contentstyle);
		}

		int n = resultData.size();
		int x = (n+5)/27;
		int y = (n+5)%27;
		if(y != 0){
			x += 1;
		}

		if (n < 13) {
			for (int i = 0; i < n; i++) {
				HSSFRow rowContent = sheet.createRow(i + 4);
				rowContent.setHeight(height);
				rowContent.setRowStyle(contentstyle);

				CheckCardPoJo checkCard = (CheckCardPoJo) resultData.get(i);
				String[] datas = { checkCard.getTime(),
						checkCard.getXtNormal(), checkCard.getSqNormal(),
						checkCard.getSdNormal(), checkCard.getJtNormal(),
						checkCard.getFmNormal(), checkCard.getC_remark(),
						checkCard.getC_exec_username() };

				for (int j = 0; j < datas.length; j++) {
					HSSFCell cellData = rowContent.createCell(j);
					cellData.setCellValue(datas[j]);
					cellData.setCellStyle(contentstyle);
				}
			}

			for (int i = 4 + n; i < 16; i++) {
				HSSFRow rowContent = sheet.createRow(i);
				rowContent.setHeight(height);
				rowContent.setRowStyle(contentstyle);

				HSSFCell cellNull = rowContent.createCell(0);
				cellNull.setCellValue("\040\040年\040\040月\040\040日");
				cellNull.setCellStyle(contentstyle);
			}

			HSSFRow rowAttention = sheet.createRow(16);
			rowAttention.setHeight(height);
			HSSFCell attention = rowAttention.createCell(0);
			attention.setCellValue("注：正常打“√”， 不正常打“×”并在备注栏说明处理及整改情况。");
			attention.setCellStyle(unitstyle);
			// 合并单元格
			CellRangeAddress rangeAtt = new CellRangeAddress(16, 16, 0, 7);
			sheet.addMergedRegion(rangeAtt);

			for (int k = 17; k < 27*x; k++) {
				HSSFRow attHead = sheet.createRow(k);
				attHead.setHeight(height);
			}
		} else {
			for (int i = 0; i < n; i++) {
				HSSFRow rowContent = sheet.createRow(i + 4);
				rowContent.setHeight(height);
				rowContent.setRowStyle(contentstyle);

				CheckCardPoJo checkCard = (CheckCardPoJo) resultData.get(i);
				String[] datas = { checkCard.getTime(),
						checkCard.getXtNormal(), checkCard.getSqNormal(),
						checkCard.getSdNormal(), checkCard.getJtNormal(),
						checkCard.getFmNormal(), checkCard.getC_remark(),
						checkCard.getC_exec_username() };

				for (int j = 0; j < datas.length; j++) {
					HSSFCell cellData = rowContent.createCell(j);
					cellData.setCellValue(datas[j]);
					cellData.setCellStyle(contentstyle);
				}
			}

			HSSFRow rowAttention = sheet.createRow(4 + n);
			rowAttention.setHeight(height);
			HSSFCell attention = rowAttention.createCell(0);
			attention.setCellValue("注：正常打“√”， 不正常打“×”并在备注栏说明处理及整改情况。");
			attention.setCellStyle(unitstyle);
			// 合并单元格
			CellRangeAddress rangeAtt = new CellRangeAddress(4 + n, 4 + n, 0, 7);
			sheet.addMergedRegion(rangeAtt);

			for (int k = 5 + n; k < 27*x; k++) {
				HSSFRow attHead = sheet.createRow(k);
				attHead.setHeight(height);
			}

		}

		// 检查要求（换页）
		HSSFRow attHead = sheet.createRow(27*x);
		attHead.setHeight(height);
		HSSFCell attHeadCell = attHead.createCell(0);
		attHeadCell.setCellValue("检查内容和要求");
		attHeadCell.setCellStyle(contentstyle);

		// 合并单元格
		CellRangeAddress rangeAttHead = new CellRangeAddress(27*x, 27*x, 0, 7);
		sheet.addMergedRegion(rangeAttHead);
		RegionUtil.setBorderTop(1, rangeAttHead, sheet, workbook);
		RegionUtil.setBorderBottom(1, rangeAttHead, sheet, workbook);
		RegionUtil.setBorderLeft(1, rangeAttHead, sheet, workbook);
		RegionUtil.setBorderRight(1, rangeAttHead, sheet, workbook);

		String[] attContent = { "1.室内消火栓箱体、开关是否完好。",
				"2.室内消火栓内水带、接头及水枪是否有破损、脱落现象。", "3.室内消火栓水带、水枪是否配置齐全，有无遗失现象。",
				"4.室内消火栓阀门是否无明显锈蚀、损伤。", "5.室内消火栓内水带、水枪及阀门的密封垫有无脱落现象。" };
		CellRangeAddress rangeAttLeft = null;
		for (int i = 0; i < 5; i++) {
			HSSFRow attContentRow = sheet.createRow(27*x + 1 + i);
			attContentRow.setHeight(height);

			if (i == 0) {
				HSSFCell attCellLeft = attContentRow.createCell(0);
				attCellLeft.setCellValue("配\n置\n要\n求");
				attCellLeft.setCellStyle(contentstyle);

				rangeAttLeft = new CellRangeAddress(27*x+1, 27*x+5, 0, 0);
				sheet.addMergedRegion(rangeAttLeft);
			}

			HSSFCell attCellRight = attContentRow.createCell(1);
			attCellRight.setCellValue(attContent[i]);
			CellRangeAddress rangeAttRight = new CellRangeAddress(27*x+1 + i,
					27*x+1 + i, 1, 7);
			sheet.addMergedRegion(rangeAttRight);

			RegionUtil.setBorderTop(1, rangeAttRight, sheet, workbook);
			RegionUtil.setBorderBottom(1, rangeAttRight, sheet, workbook);
			RegionUtil.setBorderLeft(1, rangeAttRight, sheet, workbook);
			RegionUtil.setBorderRight(1, rangeAttRight, sheet, workbook);
		}
		RegionUtil.setBorderTop(1, rangeAttLeft, sheet, workbook);
		RegionUtil.setBorderBottom(1, rangeAttLeft, sheet, workbook);
		RegionUtil.setBorderLeft(1, rangeAttLeft, sheet, workbook);
		RegionUtil.setBorderRight(1, rangeAttLeft, sheet, workbook);

		OutputStream outputStream = new FileOutputStream(filePath);
		try {
			workbook.write(outputStream);
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// System.out.println("generate  excel success ! ");

	}

	public static void writeExcelMhq(String filePath, String[] titles,
			List resultData, String sheetName) throws FileNotFoundException {

//		short heightHead = (short) 15.625 * 33;
//		short heightFoot = (short) 15.625 * 26;
		short height = (short)15.625 * 32;
		short colLeft = (short) 36 * 129;
		short colCen = (short) 36 * 64;
		short colCenter = (short) 36 * 169;
		short colRight = (short) 36 * 189;

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();

		sheet.setColumnWidth(0, colLeft);
		sheet.setColumnWidth(1, colCen);
		sheet.setColumnWidth(2, colCen);
		sheet.setColumnWidth(3, colCenter);
		sheet.setColumnWidth(4, colRight);

		HSSFCellStyle headstyle = workbook.createCellStyle();
		HSSFFont headFont = workbook.createFont();
		headFont.setFontName("黑体");
		headFont.setFontHeightInPoints((short) 10.5);// 设置字体大小
		headstyle.setFont(headFont);
		headstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
		headstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中

		HSSFCellStyle codestyle = workbook.createCellStyle();
		HSSFFont codeFont = workbook.createFont();
		codeFont.setFontName("宋体");
		codeFont.setFontHeightInPoints((short) 10.5);// 设置字体大小
		codestyle.setFont(codeFont);
		codestyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);// 左右居右
		codestyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中

		HSSFCellStyle unitstyle = workbook.createCellStyle();
		HSSFFont unitFont = workbook.createFont();
		unitFont.setFontName("宋体");
		unitFont.setFontHeightInPoints((short) 9);// 设置字体大小
		unitstyle.setFont(unitFont);
		unitstyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);// 左右居左
		unitstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
		unitstyle.setWrapText(true);

		HSSFCellStyle contentstyle = workbook.createCellStyle();
		HSSFFont contentFont = workbook.createFont();
		contentFont.setFontName("宋体");
		contentFont.setFontHeightInPoints((short) 9);// 设置字体大小
		contentstyle.setFont(contentFont);
		contentstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
		contentstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
		contentstyle.setWrapText(true);
		contentstyle.setBorderLeft((short) 1);
		contentstyle.setBorderRight((short) 1);
		contentstyle.setBorderBottom((short) 1);
		contentstyle.setBorderTop((short) 1);

		HSSFCellStyle[] styles = { headstyle, codestyle };
		String[] unitInfo = { "单位:" + titles[0], "配置位置:" + titles[1],
				"编号:" + titles[2] };
		String[] values = { "灭火器检查记录卡", "HNZY/GL-AQ-10/05.0" };

		for (int x = 0; x < 2; x++) {
			HSSFRow theRow = sheet.createRow(x);
			theRow.setHeight(height);

			HSSFCell theCell = theRow.createCell(0);
			theCell.setCellValue(values[x]);
			theCell.setCellStyle(styles[x]);

			CellRangeAddress theRange = new CellRangeAddress(x, x, 0, 4);
			sheet.addMergedRegion(theRange);
		}

		HSSFRow unitRow = sheet.createRow(2);
		unitRow.setHeight(height);

		HSSFCell dwCell = unitRow.createCell(0);
		dwCell.setCellValue(unitInfo[0]);
		dwCell.setCellStyle(unitstyle);
		CellRangeAddress theRange = new CellRangeAddress(2, 2, 0, 1);
		sheet.addMergedRegion(theRange);

		HSSFCell pzCell = unitRow.createCell(2);
		pzCell.setCellValue(unitInfo[1]);
		pzCell.setCellStyle(unitstyle);
		CellRangeAddress pzRange = new CellRangeAddress(2, 2, 2, 3);
		sheet.addMergedRegion(pzRange);

		HSSFCell bmCell = unitRow.createCell(4);
		bmCell.setCellValue(unitInfo[2]);
		bmCell.setCellStyle(unitstyle);

		String[] title = { "外观", "压力", "备注", "检查人" };
		HSSFRow row = sheet.createRow(3);
		row.setHeight(height);

		HSSFCell cell = row.createCell(0);
		cell.setCellValue("时间        内容");
		cell.setCellStyle(contentstyle);

		// 画线(由左上到右下的斜线)
		HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
		HSSFClientAnchor a = new HSSFClientAnchor(0, 0, 1023, 255, (short) 0,
				3, (short) 0, 3);
		HSSFSimpleShape shape1 = patriarch.createSimpleShape(a);
		shape1.setShapeType(HSSFSimpleShape.OBJECT_TYPE_LINE);
		shape1.setLineStyle(HSSFSimpleShape.LINESTYLE_SOLID);

		for (int v = 1; v < 5; v++) {
			HSSFCell cellTitle = row.createCell(v);
			cellTitle.setCellValue(title[v - 1]);
			cellTitle.setCellStyle(contentstyle);
		}

		int n = resultData.size();
		int x = (n+5)/27;
		int y = (n+5)%27;
		if(y != 0){
			x += 1;
		}
		if (n < 13) {
			for (int i = 0; i < n; i++) {
				HSSFRow rowContent = sheet.createRow(i + 4);
				rowContent.setHeight(height);
				rowContent.setRowStyle(contentstyle);

				CheckCardPoJo checkCard = (CheckCardPoJo) resultData.get(i);
				String[] datas = { checkCard.getTime(),
						checkCard.getWgNormal(), checkCard.getYlNormal(),
						checkCard.getC_remark(), checkCard.getC_exec_username() };

				for (int j = 0; j < datas.length; j++) {
					HSSFCell cellData = rowContent.createCell(j);
					cellData.setCellValue(datas[j]);
					cellData.setCellStyle(contentstyle);
				}
			}

			for (int i = 4 + n; i < 16; i++) {
				HSSFRow rowContent = sheet.createRow(i);
				rowContent.setHeight(height);
				rowContent.setRowStyle(contentstyle);

				HSSFCell cellNull = rowContent.createCell(0);
				cellNull.setCellValue("\040\040年\040\040月\040\040日");
				cellNull.setCellStyle(contentstyle);
			}

			HSSFRow rowAttention = sheet.createRow(16);
			rowAttention.setHeight(height);
			HSSFCell attention = rowAttention.createCell(0);
			attention.setCellValue("注：正常打“√”， 不正常打“×”并在备注栏说明处理及整改情况。");
			attention.setCellStyle(unitstyle);
			// 合并单元格
			CellRangeAddress rangeAtt = new CellRangeAddress(16, 16, 0, 4);
			sheet.addMergedRegion(rangeAtt);

			for (int k = 17; k < 27*x; k++) {
				HSSFRow attHead = sheet.createRow(k);
				attHead.setHeight(height);
			}
		} else {
			for (int i = 0; i < n; i++) {
				HSSFRow rowContent = sheet.createRow(i + 4);
				rowContent.setHeight(height);
				rowContent.setRowStyle(contentstyle);

				CheckCardPoJo checkCard = (CheckCardPoJo) resultData.get(i);
				String[] datas = { checkCard.getTime(),
						checkCard.getWgNormal(), checkCard.getYlNormal(),
						checkCard.getC_remark(), checkCard.getC_exec_username() };

				for (int j = 0; j < datas.length; j++) {
					HSSFCell cellData = rowContent.createCell(j);
					cellData.setCellValue(datas[j]);
					cellData.setCellStyle(contentstyle);
				}
			}

			HSSFRow rowAttention = sheet.createRow(4 + n);
			rowAttention.setHeight(height);
			HSSFCell attention = rowAttention.createCell(0);
			attention.setCellValue("注：正常打“√”， 不正常打“×”并在备注栏说明处理及整改情况。");
			attention.setCellStyle(unitstyle);
			// 合并单元格
			CellRangeAddress rangeAtt = new CellRangeAddress(4 + n, 4 + n, 0, 4);
			sheet.addMergedRegion(rangeAtt);

			for (int k = 5 + n; k < 27*x; k++) {
				HSSFRow attHead = sheet.createRow(k);
				attHead.setHeight(height);
			}

		}

		//
		HSSFRow attHead = sheet.createRow(27*x);
		attHead.setHeight(height);
		HSSFCell attHeadCell = attHead.createCell(0);
		attHeadCell.setCellValue("检查内容和要求");
		attHeadCell.setCellStyle(contentstyle);

		// 合并单元格
		CellRangeAddress rangeAttHead = new CellRangeAddress(27*x, 27*x, 0, 4);
		sheet.addMergedRegion(rangeAttHead);
		RegionUtil.setBorderTop(1, rangeAttHead, sheet, workbook);
		RegionUtil.setBorderBottom(1, rangeAttHead, sheet, workbook);
		RegionUtil.setBorderLeft(1, rangeAttHead, sheet, workbook);
		RegionUtil.setBorderRight(1, rangeAttHead, sheet, workbook);

		String[] attContent = {
				"1.灭火器是否放置在配置图表规定的设置点位置。",
				"2.灭火器的落地、托架、挂钩等设置方式是否符合配置设计要求。手提式灭火器的\n挂钩、托架安装后是否能承受一定的静载荷，并不出现松动、脱落、断裂和明显变形。",
				"3.灭火器的铭牌是否朝外，并且器头宜向上。",
				"4.灭火器的类型、规格、灭火级别和配置数量是否符合配置设计要求。",
				"5.灭火器配置场所的使用性质，包括可燃物 的种类和物态等，是否发生变化。",
				"6.灭火器是否达到送修条件和维修期限。",
				"7.灭火器是否达到报废条件和报废期限。",
				"8.室外灭火器是否有防雨、防晒等保护措施",
				"9.灭火器周围是否存在有障碍物、遮挡、栓系等影响取用的现象。",
				"10.灭火器周围是否上锁，箱内是否干燥、清洁。",
				"11.特殊场所中灭火器的保护措施是否安好。",
				"12.灭火器的铭牌是否无缺陷，并清晰明了。",
				"13.灭火器的铭牌上关于灭火剂、驱动气体的种类、充装压力、总质量、灭火级别、\n制造厂名和生产日期或维修日期等标志及操作说明是否齐全。",
				"14.灭火器的铅封、销门等保险装置是否损坏或遗失",
				"15.灭火器的筒体是否无明显的损伤（瞌伤、划伤）、缺陷、锈蚀（特别是筒底和焊\n缝）、泄漏。",
				"16.灭火器的软管是否完好，无明显龟裂痕，喷嘴不堵塞",
				"17.灭火器的驱动气体压力是否在工作压力范围内（ 贮式灭火器查看压力指示器是否\n在绿区范围内，二氧化碳灭火器和储气瓶式灭火器可用称重法检查）。",
				"18.灭火器的零部件是否齐全，并且无松动，脱落或损伤现象。", "19.灭火器是否未开启、喷射过。" };

		CellRangeAddress rangeAttLeft = null;
		CellRangeAddress rangeAttLeftTwo = null;
		for (int i = 0, k = 0; i < 19 && k < 23; i++, k++) {
			HSSFRow attContentRow = sheet.createRow(27*x+1 + k);
			attContentRow.setHeight(height);

			if (k == 0) {
				HSSFCell attCellLeft = attContentRow.createCell(0);
				attCellLeft.setCellValue("配\n置\n要\n求");
				attCellLeft.setCellStyle(contentstyle);

				rangeAttLeft = new CellRangeAddress(27*x+1, 27*x+12, 0, 0);
				sheet.addMergedRegion(rangeAttLeft);
			}

			if (k == 12) {
				HSSFCell attCellLeft = attContentRow.createCell(0);
				attCellLeft.setCellValue("外\n观\n检\n查");
				attCellLeft.setCellStyle(contentstyle);

				rangeAttLeftTwo = new CellRangeAddress(27*x+13, 27*x+23, 0, 0);
				sheet.addMergedRegion(rangeAttLeftTwo);
			}

			if (k == 1) {
				HSSFCell attCellRight = attContentRow.createCell(1);
				attCellRight.setCellStyle(unitstyle);
				attCellRight.setCellValue(attContent[i]);
				CellRangeAddress rangeAttRight = new CellRangeAddress(27*x+1 + k,
						27*x+2 + k, 1, 4);
				sheet.addMergedRegion(rangeAttRight);

				RegionUtil.setBorderTop(1, rangeAttRight, sheet, workbook);
				RegionUtil.setBorderBottom(1, rangeAttRight, sheet, workbook);
				RegionUtil.setBorderLeft(1, rangeAttRight, sheet, workbook);
				RegionUtil.setBorderRight(1, rangeAttRight, sheet, workbook);
				k++;
				continue;
			}

			if (k == 13) {
				HSSFCell attCellRight = attContentRow.createCell(1);
				attCellRight.setCellStyle(unitstyle);
				attCellRight.setCellValue(attContent[i]);
				CellRangeAddress rangeAttRight = new CellRangeAddress(27*x+1 + k,
						27*x+2 + k, 1, 4);
				sheet.addMergedRegion(rangeAttRight);

				RegionUtil.setBorderTop(1, rangeAttRight, sheet, workbook);
				RegionUtil.setBorderBottom(1, rangeAttRight, sheet, workbook);
				RegionUtil.setBorderLeft(1, rangeAttRight, sheet, workbook);
				RegionUtil.setBorderRight(1, rangeAttRight, sheet, workbook);
				k++;
				continue;
			}

			if (k == 16) {
				HSSFCell attCellRight = attContentRow.createCell(1);
				attCellRight.setCellStyle(unitstyle);
				attCellRight.setCellValue(attContent[i]);
				CellRangeAddress rangeAttRight = new CellRangeAddress(27*x+1 + k,
						27*x+2 + k, 1, 4);
				sheet.addMergedRegion(rangeAttRight);

				RegionUtil.setBorderTop(1, rangeAttRight, sheet, workbook);
				RegionUtil.setBorderBottom(1, rangeAttRight, sheet, workbook);
				RegionUtil.setBorderLeft(1, rangeAttRight, sheet, workbook);
				RegionUtil.setBorderRight(1, rangeAttRight, sheet, workbook);
				k++;
				continue;
			}

			if (k == 19) {
				HSSFCell attCellRight = attContentRow.createCell(1);
				attCellRight.setCellStyle(unitstyle);
				attCellRight.setCellValue(attContent[i]);
				CellRangeAddress rangeAttRight = new CellRangeAddress(27*x+1 + k,
						27*x+2 + k, 1, 4);
				sheet.addMergedRegion(rangeAttRight);

				RegionUtil.setBorderTop(1, rangeAttRight, sheet, workbook);
				RegionUtil.setBorderBottom(1, rangeAttRight, sheet, workbook);
				RegionUtil.setBorderLeft(1, rangeAttRight, sheet, workbook);
				RegionUtil.setBorderRight(1, rangeAttRight, sheet, workbook);
				k++;
				continue;
			}

			HSSFCell attCellRight = attContentRow.createCell(1);
			attCellRight.setCellStyle(unitstyle);
			attCellRight.setCellValue(attContent[i]);
			CellRangeAddress rangeAttRight = new CellRangeAddress(27*x+1 + k,
					27*x+1 + k, 1, 4);
			sheet.addMergedRegion(rangeAttRight);

			RegionUtil.setBorderTop(1, rangeAttRight, sheet, workbook);
			RegionUtil.setBorderBottom(1, rangeAttRight, sheet, workbook);
			RegionUtil.setBorderLeft(1, rangeAttRight, sheet, workbook);
			RegionUtil.setBorderRight(1, rangeAttRight, sheet, workbook);
		}
		RegionUtil.setBorderTop(1, rangeAttLeft, sheet, workbook);
		RegionUtil.setBorderBottom(1, rangeAttLeft, sheet, workbook);
		RegionUtil.setBorderLeft(1, rangeAttLeft, sheet, workbook);
		RegionUtil.setBorderRight(1, rangeAttLeft, sheet, workbook);

		RegionUtil.setBorderTop(1, rangeAttLeftTwo, sheet, workbook);
		RegionUtil.setBorderBottom(1, rangeAttLeftTwo, sheet, workbook);
		RegionUtil.setBorderLeft(1, rangeAttLeftTwo, sheet, workbook);
		RegionUtil.setBorderRight(1, rangeAttLeftTwo, sheet, workbook);

		OutputStream outputStream = new FileOutputStream(filePath);
		try {
			workbook.write(outputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// System.out.println("generate  excel success ! ");

	}
}
