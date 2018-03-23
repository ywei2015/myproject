package com.talkweb.xwzcxt.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReadUtil {
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//处理时间格式
	private static DecimalFormat df = new DecimalFormat("#.##");//处理浮点格式
	
	/**
	 * 判断excel格式
	 */
	private static Boolean isExcelVersion2007(String fileName) {
		boolean excelVer2007 = false;
		String extension = "";
		if ((fileName != null) && (fileName.length() > 0)) {
			int i = fileName.lastIndexOf('.');
			if ((i > -1) && (i < (fileName.length() - 1)))
				extension = fileName.substring(i + 1);
			if ("XLSX".equals(extension.toUpperCase())) {
				excelVer2007 = true;
			}
		}
		return excelVer2007;
	}

	public static List<String[]> readExcel(String filePath) {
		try {
			if (isExcelVersion2007(filePath)) {
				return loadExcelXlsx(filePath);
			} else {
				return loadExcelXls(filePath);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void writeExcel(String filePath, String[] head, String[][] data) {
		try {
			if (isExcelVersion2007(filePath)) {
				writeExcelXlsx(filePath, head, data);
			} else {
				writeExcelXls(filePath, head, data);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 解析excel2003
	 */
	private static List<String[]> loadExcelXls(String filePath) throws Exception {
		List<String[]> contents = new ArrayList<String[]>();
		HSSFWorkbook workbook = null;
		workbook = new HSSFWorkbook(new FileInputStream(filePath));
		
		HSSFSheet aSheet = null;
		HSSFRow aRow = null;
		HSSFCell aCell = null;
		String[] rowData = null;
		Double value = null;
		
		if (workbook == null)
			return contents;
		// ----------------------------------------------------------
		int iSheetNum = workbook.getNumberOfSheets();
		for (int numSheets = 0; numSheets < iSheetNum; numSheets++) {
			if (null != workbook.getSheetAt(numSheets)) {
				aSheet = workbook.getSheetAt(numSheets);
				int iRowNum = aSheet.getLastRowNum();
				for (int rowNumOfSheet = 0; rowNumOfSheet <= iRowNum; rowNumOfSheet++) {
					//去掉除第一个sheet外的sheet的第一行标题
					if (rowNumOfSheet == 0 && contents.size() > 0) {
						continue;
					}
					aRow = aSheet.getRow(rowNumOfSheet);
					if (aRow != null) {
						int iCellNum = aRow.getLastCellNum();
						rowData = new String[iCellNum];
						for (int cellNumOfRow = 0; cellNumOfRow < iCellNum; cellNumOfRow++) {
							aCell = aRow.getCell(cellNumOfRow);
							if (aCell != null) {
								if (aCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {//判断数字 类型
									if (HSSFDateUtil.isCellDateFormatted(aCell)) {//判断是否为时间格式
										rowData[cellNumOfRow] = sdf.format(aCell.getDateCellValue());
									} else {
										value = aCell.getNumericCellValue();
										if (value > value.longValue()) {//判断是否有小数值，转换为long后的值如果比之前值小则证明有小数
											rowData[cellNumOfRow] = df.format(value);
										} else {
											rowData[cellNumOfRow] = String.valueOf(value.longValue());
										}
									}
								} else if (aCell.getCellType() == HSSFCell.CELL_TYPE_BOOLEAN) {
									rowData[cellNumOfRow] = aCell.getBooleanCellValue() ? "是" : "否";
								} else if (aCell.getCellType() == HSSFCell.CELL_TYPE_FORMULA) {
									rowData[cellNumOfRow] = aCell.getCellFormula();
								} else {
									rowData[cellNumOfRow] = aCell.getStringCellValue();
								}
							}
						}
						contents.add(rowData);
					}
				}
			}
		}
		trim(contents);
		return contents;
	}

	private static List<String[]> loadExcelXlsx(String filePath) throws Exception {
		List<String[]> contents = new ArrayList<String[]>();
		XSSFWorkbook xwb = new XSSFWorkbook(filePath);
		
		int columnCount = -1;
		XSSFSheet xSheet = null;
		XSSFRow xRow = null;
		String[] rowData = null;
		XSSFCell xCell = null;
		
		for (int numSheet = 0; numSheet < xwb.getNumberOfSheets(); numSheet++) {
			xSheet = xwb.getSheetAt(numSheet);
			if (xSheet == null) {
				continue;
			}
			for (int rowNum = 0; rowNum <= xSheet.getLastRowNum(); rowNum++) {
				xRow = xSheet.getRow(rowNum);
				if (xRow == null) {
					continue;
				}
				//如果到第二个sheet中时去掉第一行的标题
				if (rowNum == 0 && contents.size() > 0) {
					continue;
				}
				int iCellNum = xRow.getLastCellNum();
				if (rowNum == 0) {
					columnCount = iCellNum;
				}
				rowData = new String[columnCount];
				for (int cellNumOfRow = 0; cellNumOfRow <= iCellNum; cellNumOfRow++) {
					xCell = xRow.getCell(cellNumOfRow);
					if (xCell != null) {
						if (xCell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
							//判断是否为时间格式
							if (HSSFDateUtil.isCellDateFormatted(xCell)) {
								rowData[cellNumOfRow] = sdf.format(xCell.getDateCellValue());
							} else {
								rowData[cellNumOfRow] = xCell.getRawValue();
							}
						} else if (xCell.getCellType() == XSSFCell.CELL_TYPE_BOOLEAN) {
							rowData[cellNumOfRow] = xCell.getBooleanCellValue() ? "是" : "否";
						} else if (xCell.getCellType() == XSSFCell.CELL_TYPE_FORMULA) {
							rowData[cellNumOfRow] = xCell.getCellFormula();
						} else {
							rowData[cellNumOfRow] = xCell.getStringCellValue();
						}
					}
				}
				contents.add(rowData);
			}
		}
		trim(contents);
		return contents;
	}

	private static void writeExcelXls(String filePath, String[] head, String[][] data) throws Exception {
		HSSFWorkbook hssfworkbook = new HSSFWorkbook();
		HSSFSheet hssfSheet = hssfworkbook.createSheet();
		hssfworkbook.setSheetName(0, "sheet1");
		HSSFRow hssfRowHead = hssfSheet.createRow(0);
		// -----------------------------------------------
		for (int j = 0; j < head.length; j++) {
			HSSFCell hssfCell = hssfRowHead.createCell(j);
			hssfCell.setCellValue(head[j]);
		}
		for (int i = 0; i < data.length; i++) {
			HSSFRow hssfRowData = hssfSheet.createRow(i + 1);
			String[] row = data[i];
			for (int j = 0; j < row.length; j++) {
				HSSFCell hssfCell = hssfRowData.createCell(j);
				hssfCell.setCellValue(row[j]);
			}
		}
		FileOutputStream fileoutputstream = new FileOutputStream(filePath);
		hssfworkbook.write(fileoutputstream);
		fileoutputstream.close();
	}

	@SuppressWarnings("static-access")
	private static void writeExcelXlsx(String filePath, String[] head, String[][] data) throws Exception {
		XSSFWorkbook xssfworkbook = new XSSFWorkbook();
		XSSFSheet xssfSheet = xssfworkbook.createSheet();
		xssfworkbook.setSheetName(0, "sheet1");
		XSSFRow hssfRowHead = xssfSheet.createRow(0);
		// -------------------------------------------------
		for (int j = 0; j < head.length; j++) {
			XSSFCell xssfCell = hssfRowHead.createCell(j);
			xssfCell.setCellValue(head[j]);
		}
		for (int i = 0; i < data.length; i++) {
			XSSFRow xssfRowData = xssfSheet.createRow(i + 1);
			String[] row = data[i];
			for (int j = 0; j < row.length; j++) {
				XSSFCell xssfCell = xssfRowData.createCell(j);
				xssfCell.setCellValue(row[j]);
			}
		}
		FileOutputStream fileoutputstream = new FileOutputStream(filePath);
		xssfworkbook.write(fileoutputstream);
		fileoutputstream.close();
	}
	/**
	 * 去除最后一行空数据行
	 */
	private static void trim(List<String[]> list) {
		for (int i = list.size() - 1; i > 1; i--) {
			String[] row = list.get(i);
			boolean hasValue = false;
			for (int j = 0; j < row.length; j++) {
				if ((row[j] != null) && (row[j].length() != 0)) {
					hasValue = true;
					break;
				}
			}
			if (!hasValue) {
				list.remove(i);
			} else {
				break;
			}
		}
	}

	public static void main(String[] args) throws Exception {
		List<String[]> list = ExcelReadUtil.readExcel("d:\\高新企业.xlsx");
		for (String []strs : list) {
			for (String str : strs) {
				System.out.print(str);
				System.out.print("\t");
			}
			System.out.println();
		}
	}
}
