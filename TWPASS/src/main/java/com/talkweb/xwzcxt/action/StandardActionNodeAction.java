package com.talkweb.xwzcxt.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.twdpe.base.org.pojo.Org;
import com.talkweb.twdpe.base.org.pojo.Position;
import com.talkweb.twdpe.base.org.service.OrgService;
import com.talkweb.twdpe.base.org.service.PositionService;
import com.talkweb.twdpe.webcomponent.component.OrgMngAction;
import com.talkweb.xwzcxt.pojo.SdActionPojo;
import com.talkweb.xwzcxt.pojo.SdActionPositionPojo;
import com.talkweb.xwzcxt.pojo.StandardLibraryPojo;
import com.talkweb.xwzcxt.pojo.StdFile;
import com.talkweb.xwzcxt.service.SdActionService;
import com.talkweb.xwzcxt.service.StandardLibraryService;
import com.talkweb.xwzcxt.service.StdFileService;

/**
 * 
 * @author YangChen
 * TODO:标准库流程节点管理action
 *
 */
public class StandardActionNodeAction extends BusinessAction {

	private static final long serialVersionUID = 7729783672646717345L;
	//标准文件类别实体
	private StandardLibraryPojo sortPojo;
	//流程节点实体
	private SdActionPojo pojo;
	@Autowired
	private SdActionService sdActionService;
	private String data;
	private OrgMngAction orgMngAction=new OrgMngAction();
	//操作组织接口
	private OrgService orgService=null;
	//操作岗位接口
	@Autowired
    private PositionService positionService;
	@Autowired
	private StandardLibraryService standardLibraryService;
	@Autowired
	private StdFileService stdFileService;
	
	{
		try {
			Field field=orgMngAction.getClass().getDeclaredField("orgService");
			field.setAccessible(true);
			orgService=(OrgService)field.get(orgMngAction);
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public StandardLibraryPojo getSortPojo() {
		return sortPojo;
	}
	public void setSortPojo(StandardLibraryPojo sortPojo) {
		this.sortPojo = sortPojo;
	}
	public SdActionPojo getPojo() {
		return pojo;
	}
	public void setPojo(SdActionPojo pojo) {
		this.pojo = pojo;
	}
	public SdActionService getSdActionService() {
		return sdActionService;
	}
	public void setSdActionService(SdActionService sdActionService) {
		this.sdActionService = sdActionService;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public OrgService getOrgService() {
		return orgService;
	}
	public void setOrgService(OrgService orgService) {
		this.orgService = orgService;
	}
	public PositionService getPositionService() {
		return positionService;
	}
	public void setPositionService(PositionService positionService) {
		this.positionService = positionService;
	}
	public StandardLibraryService getStandardLibraryService() {
		return standardLibraryService;
	}
	public void setStandardLibraryService(
			StandardLibraryService standardLibraryService) {
		this.standardLibraryService = standardLibraryService;
	}
	public StdFileService getStdFileService() {
		return stdFileService;
	}
	public void setStdFileService(StdFileService stdFileService) {
		this.stdFileService = stdFileService;
	}
	
	private String switchResponseType(String responseType){
		String responseTypeName="";
		switch(Integer.parseInt(responseType)){
			case 1:
				responseTypeName="主管";
				break;
			case 2:
				responseTypeName="监管";
				break;
			case 3:
				responseTypeName="协管";
				break;
		}
		return responseTypeName;
	}
	
	//得到Excel工作表对象
	@SuppressWarnings("unused")
	private HSSFSheet getSheetObject(String filePath){
		File fileObject=new File(filePath);
		FileInputStream fis=null;
		POIFSFileSystem fs=null;
		HSSFWorkbook wb=null;
		HSSFSheet sheet=null;
		try {
			fis=new FileInputStream(fileObject);
			fs=new POIFSFileSystem(fis);
			wb=new HSSFWorkbook(fs);
			sheet=wb.getSheetAt(0);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			if(fis!=null){
				try {
					fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return sheet;
	}
	
	//查询某excel表的最大行和列
	@SuppressWarnings("unused")
	private Map<String,Integer> getMaxColsAndRowsNum(HSSFSheet sheet){
		Map<String,Integer> map=new HashMap<String,Integer>();
		int rowsNum=sheet.getPhysicalNumberOfRows();
		HSSFRow row=sheet.getRow(0);
		int colsNum=row.getPhysicalNumberOfCells();
		map.put("rowsNum", rowsNum);
		map.put("colsNum", colsNum);
		return map;
	}
	
	public void deleteSdActionFromDB(){
		String [] pojoArray=new String [1];
		pojoArray[0]=pojo.getC_action_id();
		sdActionService.deleteActionPosotionById(pojoArray[0]);
		sdActionService.deleteActionById(pojoArray);
	}
	
	public String updateSdActionToDB(){
		HttpServletResponse response=ServletActionContext.getResponse();
		PrintWriter out=null;
		try {
			out=response.getWriter();
			sdActionService.modifyActionDataById(pojo);
			out.print(pojo.getC_action_id());
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			out.close();
		}
		return SUCCESS;
	}
	
	public String addSdActionToDB(){
		HttpServletResponse response=ServletActionContext.getResponse();
		PrintWriter out=null;
		try {
			out=response.getWriter();
			pojo.setC_action_id(UUID.randomUUID().toString());
			sdActionService.addSdActionToDB(pojo);
			out.print(pojo.getC_action_id());
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			if(out!=null){
				out.close();
			}
		}
		return SUCCESS;
	}
	
	public String addSdActionPositionsToDB(){
		List<SdActionPositionPojo> list=new ArrayList<SdActionPositionPojo>();
		JSONArray array=JSONArray.fromObject(this.getData());
		for(int i=0;i<array.size();i++){
			SdActionPositionPojo tempPojo=new SdActionPositionPojo();
			JSONObject obj=(JSONObject) array.get(i);
			tempPojo.setC_id(UUID.randomUUID().toString());
			tempPojo.setC_action_id(pojo.getC_action_id());
			tempPojo.setC_respons_orgid(obj.optString("c_respons_orgid"));
			tempPojo.setC_respons_orgname(obj.optString("c_respons_orgname"));
			tempPojo.setC_respons_positionid(obj.optString("c_respons_positionid"));
			tempPojo.setC_respons_positionname(obj.optString("c_respons_positionname"));
			tempPojo.setC_respons_type(obj.optString("c_respons_type"));
			list.add(tempPojo);
		}
		sdActionService.addSdActionPositionsToDB(list);
		return SUCCESS;
	}
	
	public String updateSdActionPositionsToDB(){
		sdActionService.deleteActionPosotionById(pojo.getC_action_id());
		return this.addSdActionPositionsToDB();
	}
	
	public String getActionInfo(){
		HttpServletResponse response=ServletActionContext.getResponse();
		PrintWriter out=null;
		String str="";
		boolean hasParent=false;
		try {
			response.setCharacterEncoding("UTF-8");
			out=response.getWriter();
			//获取流程以及岗位职责的实体
			SdActionPojo tempPojo=sdActionService.getActionInfoById(pojo.getC_action_id());
			List<SdActionPositionPojo> tempPosList=sdActionService.getActionPositionInfoById(pojo.getC_action_id());
			//作打印流JSON字符串拼接
			if(null!=tempPojo.getC_action_pid()&&!"".equals(tempPojo.getC_action_pid())){
				hasParent=true;
			}
			str += "{";
			str += "\"c_action_code\":" + "\"" + tempPojo.getC_action_code() + "\",";
			str += "\"c_section_id\":" + "\"" + tempPojo.getC_section_id() + "\",";
			str += "\"c_action_fname\":" + "\"" + tempPojo.getC_action_fname() + "\",";
			str += "\"c_remark\":" + "\"" + tempPojo.getC_remark() + "\"";
			if(hasParent){
				SdActionPojo parentPojo=sdActionService.getActionInfoById(tempPojo.getC_action_pid());
				if(null!=parentPojo){
					str += ",\"c_action_pid\":" + "\"" + parentPojo.getC_action_fname() + "\"";
				}
			}
			//拼接岗位职责信息
			if(tempPosList.size()>0){
				Org org=null;
				Position pos=null;
				str += ",\"postion_info\":";
				str += "[";
				for(int i=0;i<tempPosList.size();i++){
					SdActionPositionPojo tempPositionPojo=tempPosList.get(i);
					//获取职责部门
					if(null!=tempPositionPojo.getC_respons_orgid()){
						org=orgService.getOrg(Long.parseLong(tempPositionPojo.getC_respons_orgid()));
					}
					//获取职责岗位
					if(null!=tempPositionPojo.getC_respons_positionid()){
						pos=positionService.getPosition(Long.parseLong(tempPositionPojo.getC_respons_positionid()));
					}
					str += "{";
					if(null!=tempPositionPojo.getC_respons_type()){
						str += "\"c_respons_type\":" + "\"" + switchResponseType(tempPositionPojo.getC_respons_type()) + "\"";
					}
					if(org!=null){
						str += ",\"c_respons_orgname\":" + "\"" + org.getOrgName() + "\"";
					}
					if(pos!=null){
						str += ",\"c_respons_positionname\":" + "\"" + pos.getPosiName() + "\"";
					}
					str += "}";
					if(i<tempPosList.size()-1){
						str += ",";
					}
				}
				str += "]";
			}
			str += "}";
			System.out.println(str);
			out.print(str);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}	
		finally{
			if(out!=null){
				out.close();
			}
		}
		return SUCCESS;
	}
	
	//获取所有的标准文件类别
	public void getAllSdFileSort(){
		HttpServletResponse response=ServletActionContext.getResponse();
		PrintWriter out=null;
		String jsonString="";
		try {
			response.setCharacterEncoding("UTF-8");
			out=response.getWriter();
			List<StandardLibraryPojo> sortList=standardLibraryService.getSdFileSortByPid("0");
			jsonString += "[";
			for(int i=0;i<sortList.size();i++){
				StandardLibraryPojo pojo=sortList.get(i);
				jsonString += "{";
				jsonString += "\"value\":" + "\"" + pojo.getC_sort_id() + "\"" + ",";
				jsonString += "\"text\":" + "\"" + pojo.getC_sort_name() + "\"";
				jsonString += "}";
				if(i<sortList.size()-1){
					jsonString += ",";
				}
			}
			jsonString += "]";
			out.print(jsonString);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			if(out!=null){
				out.close();
			}
		}
	}
	
	//获取某类别ID下的所有标准文件
	public void getSdFileBySortId(){
		HttpServletResponse response=ServletActionContext.getResponse();
		PrintWriter out=null;
		String jsonString="";
		try {
			response.setCharacterEncoding("UTF-8");
			out=response.getWriter();
			List<StdFile> fileList=stdFileService.getStdFileBySortId(sortPojo.getC_sort_id());
			jsonString += "[";
			for(int i=0;i<fileList.size();i++){
				StdFile file=fileList.get(i);
				jsonString += "{";
				jsonString += "\"id\":" + "\"" + file.getC_sfile_id() + "\"" + ",";
				jsonString += "\"pid\":" + "\"" + "0" + "\"" + ",";
				jsonString += "\"val\":" + "\"" + file.getC_sfile_name() + "\"";
				jsonString += "}";
				if(i<fileList.size()-1){
					jsonString += ",";
				}
			}
			jsonString += "]";
			out.print(jsonString);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			if(out!=null){
				out.close();
			}
		}
	}
	
	public static void main(String[] args){
		
		StandardActionNodeAction action=new StandardActionNodeAction();
		/*
		HSSFSheet sheet=action.getSheetObject("D:\\Book1.xls");
		System.out.println(action.getMaxColsAndRowsNum(sheet));
		*/
		action.getAllSdFileSort();
	}
}
