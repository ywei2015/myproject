package com.talkweb.xwzcxt.service;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;

public interface ExcelReaderService {

	public String[] readExcelTitle(InputStream is);
	public Map<Integer, List<String>> readExcelContent(InputStream is);
	public String getStringCellValue(HSSFCell cell);
	public String getDateCellValue(HSSFCell cell);
	public String getCellFormatValue(HSSFCell cell);
	
	public Map<Integer, List<String>> readExcelContentEx(FileInputStream fis);
	public String getCellFormatValueEx(Cell c);
}
