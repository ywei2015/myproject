package com.talkweb.xwzcxt.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.twdpe.web.commons.StaticUploadInfo;
import com.talkweb.xwzcxt.pojo.YanChongPojo;
import com.talkweb.xwzcxt.service.YanChongService;
import com.talkweb.xwzcxt.service.impl.YanChongServiceImpl;
//导出excel模板
public class OutYcexcel  {
	@Autowired
	private  YanChongService  yanchongservice;	  
	YanChongServiceImpl ycsimpl=new YanChongServiceImpl();
	
    public  void outYCExcel(Map map){   
    	try{
    	HttpServletRequest request = ServletActionContext.getRequest();
    	Workbook wb = null;
    	FileInputStream file=null;
        try {
           file=new FileInputStream("c:/ycdr.xls");
        	wb = new XSSFWorkbook("c:/ycdr.xls");
        } catch (Exception ex) {
        	try {
				wb = new HSSFWorkbook(file);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
        }
        }
        	    Sheet sheet = wb.getSheetAt(0);
        	    List<String> idlist=(List) map.get("c_area_id");
        	    int aa=sheet.getLastRowNum();
                if(0<idlist.size()){
            	   for (int i =0; i < idlist.size(); i++) {
            	   Row row = sheet.createRow(sheet.getLastRowNum()+1);
                       for (int colNum = 0; colNum < 6; colNum++) {
                       // 在当前行的colNum列上创建单元格
                           Cell cell = row.createCell((short) colNum);
                           CellStyle noBorder = wb.createCellStyle();
                           noBorder.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                           noBorder.setBorderBottom(HSSFCellStyle.BORDER_THIN);
                           noBorder.setBorderRight(HSSFCellStyle.BORDER_THIN);
                           noBorder.setBorderTop(HSSFCellStyle.BORDER_THIN);
                           cell.setCellStyle(noBorder);
                      }
                   }
               }
                //获得行
                for(int rowNum = 0; rowNum<idlist.size()+3; rowNum++){  
                    Row hssfRow = sheet.getRow(rowNum);  
                    if(hssfRow == null){  
                      continue;  
                    } 
                    for(int cellNum = 0; cellNum <=1; cellNum++){  
                        Cell xssfCell = hssfRow.getCell( cellNum); 
                        if(xssfCell == null){  
                            continue;  
                          } 
                        if(rowNum==0&&cellNum==0) {
                        	xssfCell.setCellValue(getYCtime());
                        }
                        //区域名称
                        if(rowNum>2&&cellNum==0){
                        	HashMap mapj = new HashMap(); 
                			mapj.put("c_area_id", idlist.get(rowNum-3));
                        	String namej=ycsimpl.getAreaNameById(mapj);
                        	String name =namej.substring(namej.indexOf(",")+1, namej.length());
                        	xssfCell.setCellValue(name); 
                        }
                        if(rowNum>2&&cellNum==1){
                        	xssfCell.setCellValue(idlist.get(rowNum-3));
                        }
                    } 
                }
                    String path = request.getSession().getServletContext()
            				.getRealPath(StaticUploadInfo.upload_system_export);
                    String pathj=getUrl(path);
            		String url = null;
        			url = pathj+"/xwzcxt/mytask/ycdr.xls";
                FileOutputStream fileoutputstream = new FileOutputStream(url);
                wb.write(fileoutputstream);
                fileoutputstream.close(); 
                file.close();
        } catch (Exception e) {
                e.printStackTrace();
        }
}

    public static  String getYCtime(){
    	String s="";
    	try {
    		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd HH:mm:ss");  
        	long start = System.currentTimeMillis(); 
        	String nowtime=sdf.format(start);
        	String riqi=nowtime.substring(0, nowtime.indexOf("月"));
        	String nowday=nowtime.substring(nowtime.indexOf("月")+1, nowtime.indexOf(" "));
        	String zhounumber=""+(Integer.parseInt(nowday)%7>0?Integer.parseInt(nowday)/7+1:Integer.parseInt(nowday)/7);
        	 s="时间"+": "+riqi+"月"+" 第"+zhounumber+"周";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    	
     	return s;
    }
    public static String getUrl(String pathname){
    	String s=pathname;
    	for (int i = 0; i < 3; i++) {
			s=s.substring(0, s.lastIndexOf("\\"));
		}
    	return s;
    }
    //读取上传excel数据
	public static Map getYcImportDate(String path) throws Exception{
		  HashMap map=new HashMap();
		 FileInputStream fileInputStream = new FileInputStream(path);
		 try {
		HttpServletRequest request = ServletActionContext.getRequest();
    	Workbook wb = null;
        try {
        	wb = new XSSFWorkbook(path);
        } catch (Exception ex) {
			wb = new HSSFWorkbook(fileInputStream);
        }
        List<YanChongPojo> yanchongList = new ArrayList<YanChongPojo>();
        	 Sheet sheet = wb.getSheetAt(0);
        	    //获得行
             for(int rowNum = 3; rowNum<=sheet.getLastRowNum(); rowNum++){ 
            	 YanChongPojo yancPojo=new YanChongPojo();
                 Row hssfRow = sheet.getRow(rowNum);  
                 if(hssfRow == null){  
                   continue;
                 } 
                 int ycbh_isnull=0;
                 int ycsl_isnull=0;
                 int ecbh_isnull=0;
                 int ecsl_isnull=0;
                 for(int cellNum = 0; cellNum <=hssfRow.getLastCellNum(); cellNum++){  
                     Cell xssfCell = hssfRow.getCell( cellNum); 
                     if(xssfCell == null){  
                         continue; 
                       } 
                     int a  =  xssfCell.getCellType();
                     if(cellNum==0){
                    	 if(xssfCell.getCellType()!=1) {
                    		 yancPojo.setIs_error("1");
                    		 continue;
                    	 }
                    	 yancPojo.setC_area_name(xssfCell.getStringCellValue());
                     }
                     if(cellNum==1){
                    	 if(xssfCell.getCellType()!=1) {
                    		 yancPojo.setIs_error("1");
                    		 continue;
                    	 }
                    	 yancPojo.setC_area_id(xssfCell.getStringCellValue());
                     }
                     if(cellNum==2) {
                    	 if(xssfCell.getCellType()!=3&&xssfCell.getCellType()!=1) {
                    		 yancPojo.setIs_error("1");
                    		 continue;
                    	 } else if(xssfCell.getCellType()==3)
                    		 ycbh_isnull=1;
                    	 else
                    	 yancPojo.setC_smokey_loca_id(xssfCell.getStringCellValue());
                     }
                     if(cellNum==3){
                    	 if(xssfCell.getCellType()!=3&&xssfCell.getCellType()!=0) {
                    		 yancPojo.setIs_error("1");
                    		 continue;
                    	 }else if(xssfCell.getCellType()==3)
                    		 ycsl_isnull=1;
                    	 else{
                    	 Double number=xssfCell.getNumericCellValue();
                    	 yancPojo.setC_smokey(Math.round(number));
                    	 }
                     }
                     if(cellNum==4){
                    	 if(xssfCell.getCellType()!=3&&xssfCell.getCellType()!=1) {
                    		 yancPojo.setIs_error("1");
                    		 continue;
                    	 }else if(xssfCell.getCellType()==3)
                    		 ecbh_isnull=1;
                    	 else
                    	 yancPojo.setC_month_loca_id(xssfCell.getStringCellValue());
                    	 
                     }
                     if(cellNum==5){
                    	 if(xssfCell.getCellType()!=3&&xssfCell.getCellType()!=0) {
                    		 yancPojo.setIs_error("1");
                    		 continue;
                    	 }else if(xssfCell.getCellType()==3)
                    		 ecsl_isnull=1;
                    	 else{
                    	 Double number=xssfCell.getNumericCellValue();
                    	 yancPojo.setC_month(Math.round(number));
                    	 }
                     }
                 } 
                 if((ycbh_isnull+ycsl_isnull)==1||(ecbh_isnull+ecsl_isnull)==1)
                	 yancPojo.setIs_error("1");
                 yanchongList.add(yancPojo);
             }
             map.put("yanchongList", yanchongList);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			fileInputStream.close();
		}
       
       
		 return map;
	}
	
}
