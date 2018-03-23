package com.talkweb.xwzcxt.util;

import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;


@SuppressWarnings("unchecked")
public class ExcelUtil {
	/**
	 * 生成excel文件
	 * @param titles[]标题
	 * @param fields[]属性值
	 * @param fileName文件名
	 * @param resultData结果集
	 */
	public static void writeExcel(String filePath, String[] titles, String[] fields,List resultData) throws Exception {
		writeExcel(filePath, getFields(titles, fields), resultData);
	}
	/**
	 * 生成excel文件
	 * @param fields[]属性值（复杂表头）
	 * @param fileName文件名
	 * @param resultData结果集
	 */
	public static void writeExcel(String filePath, Object[] fields,List resultData) throws Exception {
		writeExcel(filePath, getFields(fields), resultData);
	}
	
	/**
	 * 生成excel文件
	 * @param fields[]标题，属性集合
	 * @param fileName文件名
	 * @param resultData结果集
	 */
	public static void writeExcel(String filePath, Field[] fields, List resultData) throws Exception {
		writeExcel(filePath, fields, resultData, "sheet1");
	}
	
	
	
	
	
	
	
	/**
	 * 生成excel文件
	 * @param fields[]标题，属性集合
	 * @param fileName文件名
	 * @param resultData结果集
	 * @param sheetName sheet名称
	 */
	public static void writeExcel(String filePath, Field[] fields, List resultData, String sheetName) throws Exception {
		HSSFWorkbook hssfworkbook = new HSSFWorkbook();
		HSSFSheet hssfSheet = hssfworkbook.createSheet();
		hssfworkbook.setSheetName(0, sheetName);
		
		//设置标题样式
		Font font = hssfworkbook.createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		font.setFontHeight((short)200);
		CellStyle style = hssfworkbook.createCellStyle();
		style.setFont(font);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setAlignment(CellStyle.ALIGN_CENTER);
		
		createTitle(fields, hssfSheet, style, 0, 0);
		HSSFCell hssfCell = null;
		
		String[][] data = changeDataFormat(fields, resultData, true);
		HSSFRow hssfRowData = null;
		for (int i = 0, size = resultData.size(); i < size; i++) {
			hssfRowData = hssfSheet.createRow(i + 1);
			String[] row = data[i];
			for (int j = 0; j < row.length; j++) {
				hssfCell = hssfRowData.createCell(j);
				hssfCell.setCellValue(row[j]);
			}
		}
		FileOutputStream fileoutputstream = new FileOutputStream(filePath);
		hssfworkbook.write(fileoutputstream);
		fileoutputstream.close();
	}
	
	private static void createTitle(Field[] fields, HSSFSheet sheet, CellStyle style, int row, int index) {
		HSSFRow hssfRowHead = sheet.getRow(row);
		if (hssfRowHead == null) {
			hssfRowHead = sheet.createRow(row);
		}
		CellRangeAddress range = null;
		HSSFCell hssfCell = null;
		
		Field[] fs = null;
		int length = 0;
		int col = 0;
		
		for (int i = 0; i < fields.length; i++) {
			if (fields[i] == null) {
				continue;
			}
			col = index + i;
			hssfCell = hssfRowHead.createCell(col);
			hssfCell.setCellStyle(style);
			//合并单元格
			range = new CellRangeAddress(row, row + fields[i].getSpanRow() - 1, col, col + fields[i].getSpanCol() - 1);
			sheet.addMergedRegion(range);
			
			fs = new Field[length];
			hssfCell.setCellValue(fields[i].getTitle());
			
			if (fields[i].getFields() != null) {
				length = fields[i].getFields().size();
				if (length > 0) {
					createTitle(fields[i].getFields().toArray(fs), sheet, style, row + 1, col);
				}
			}
			index += fields[i].getSpanCol() - 1;
		}
	}

	/**
	 * 转换对象的数据格式
	 * @param fields List<Object> Excel文件要显示的表头，及属性的字段信息数组对象
	 * @param resultData List 要转换的数据对象
	 * @return String[][]
	 * **/
	public static String[][] changeDataFormat(Field[] fields, List resultData, boolean formatData) throws Exception {
		String[][] excelData = new String[resultData.size()][fields.length];
		Object value = null;
		Map map = null;
		
		for (int i = 0, size = resultData.size(); i < size; i++) {
			Object obj = resultData.get(i);
			if (obj instanceof Map) {
				map = (Map) obj;
				for (int j = 0; j < fields.length; j++) {
					Object v = map.get(fields[j].getField());
					if (formatData)
						v = changeValueFormat(v);
					if (v instanceof String)
						excelData[i][j] = v.toString();
					else
						excelData[i][j] = v != null ? String.valueOf(v) : "";
				}
			} else {
				for (int j = 0; j < fields.length; j++) {
					value = getFieldValueByName(obj, fields[j].getField());
					if (formatData) {
						value = changeValueFormat(value);
					}
					excelData[i][j] = value.toString();
				}
			}
		}
		return excelData;
	}
	/**
	 * 将数组格式的表头及属性名数据转换成数组对象格式
	 * @param titles Object[] Excel文件要显示的表头，按顺序显示
	 * @param fields Object[] 对象的属性名，对应相应的表头
	 * */
	public static Field[] getFields(String[] titles, String[] fields) {
		int length = Math.min(titles.length, fields.length);
		Field[] outputs = new Field[length];
		
		for (int i = 0; i < length; i++) {
			outputs[i] = new Field();
			outputs[i].setTitle(titles[i]);
			outputs[i].setField(fields[i]);
		}
		return outputs;
	}
	//解析复杂表头结构
	public static Field[] getFields(Object[] fields) {
		Field[] outputs = processFields(fields, 0);
		int maxRow = 0;
		//求得最大合并行数
		for (Field f : outputs) {
			if (f.getSpanRow() > maxRow) {
				maxRow = f.getSpanRow();
			}
		}
		processFieldsSpanRow(outputs, maxRow, 0);
		return outputs;
	}
	//设置合并行数
	private static void processFieldsSpanRow(Field[] outputs, int size, int level) {
		for (Field f : outputs) {
			//如果没有子行，合并下面所有行
			if (f.getFields() == null) {
				f.setSpanRow(size - level);
			} else {
				f.setSpanRow(1);//很重要
				processFieldsSpanRow(f.getFields().toArray(new Field[f.getFields().size()]), size, level + 1);
			}
		}
	}
	
	private static Field[] processFields(Object[] fields, int level) {
		int length = fields.length;
		Field[] outputs = new Field[length];
		for (int i = 0; i < length; i++) {
			outputs[i] = processFields(fields[i], level + 1);
		}
		return outputs;
	}
	
	/**
	 * 解析结构
	 */
	private static Field processFields(Object field, int level) {
		Field o = new Field();
		Object[] fields = (Object[])field;
		o.setTitle((String)fields[0]);
		if (fields[1] instanceof String) {
			o.setField((String)fields[1]);
			o.setSpanRow(level);
		} else {
			Object[] objs = (Object[])fields[1];
			int length = objs.length;
			if (length > 0) {
				Field[] fds = processFields(objs, level);
				int col = 0;
				for (int i = 0; i < fds.length; i++) {
					o.addFields(fds[i]);
					//获取合并列数
					col += fds[i].getSpanCol();
					//获取最大合并行数
					if (fds[i].getSpanRow() > o.getSpanRow()) {
						o.setSpanRow(fds[i].getSpanRow());
					}
				}
				o.setSpanCol(col);
			}
		}
		return o;
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
			return null;
		}
	}

	private static Object changeValueFormat(Object o) {
    	if(o instanceof Double) {
    		if (((Double)o) >= 1) {
	        	DecimalFormat df = new DecimalFormat("#.##");
	    		return df.format(o);
    		} else {
	        	DecimalFormat df = new DecimalFormat("#.####");
	    		return df.format(o);
    		}
		} else if (o instanceof Date) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			return sdf.format(o);
		}
		return o;
	}
	
	public static void main(String[] args) {
		Object[] fields = new Object[]{
			new String[]{"标题一", "title1"},
			new String[]{"标题一", "title2"},
			new Object[]{"标题三", new Object[]{
					new Object[]{"子标题一", new Object[]{
							new String[]{"子子标题一", "tt1"},
							new String[]{"子子标题二", "tt2"},
							new String[]{"子子标题三", "tt2"}
						}
					},
					new Object[]{"子标题二", new Object[]{
							new String[]{"子子标题二", "tt2"},
							new String[]{"子子标题三", "tt2"},
							new Object[]{"子标题一", new Object[]{
									new String[]{"子子标题一", "tt1"},
									new String[]{"子子标题二", "tt2"},
									new String[]{"子子标题三", "tt2"}
								}
							}
						}
					}
				}
			}
		};
		try {
			ExcelUtil.writeExcel("C:/test.xls", fields, new ArrayList());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
class Field {
	private String title;
	private String field;
	private List<Field> fields;
	private int row = 1;
	private int col = 1;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public List<Field> getFields() {
		return fields;
	}
	public void addFields(Field field) {
		if (this.fields == null) {
			this.fields = new ArrayList<Field>();
		}
		this.fields.add(field);
	}
	public void setSpanRow(int row) {
		this.row = row;
	}
	public int getSpanRow() {
		return row;
	}
	public void setSpanCol(int col) {
		this.col = col;
	}
	public int getSpanCol() {
		return col;
	}
}