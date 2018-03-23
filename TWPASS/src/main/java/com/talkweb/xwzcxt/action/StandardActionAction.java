package com.talkweb.xwzcxt.action;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.twdpe.web.jcontrols.RuleTree;
import com.talkweb.twdpe.webcomponent.common.action.BusinessAction;
import com.talkweb.xwzcxt.pojo.SdActionPojo;
import com.talkweb.xwzcxt.pojo.SdActionPositionPojo;
import com.talkweb.xwzcxt.service.ExcelReaderService;
import com.talkweb.xwzcxt.service.SdActionService;


/**
 * TODO(流程节点管理相关Action的实现)
 * 
 * @author: 2014-05-21，FLN，（描述修改内容）
 */
public class StandardActionAction extends BusinessAction {

	private static final long serialVersionUID = 1L;
	
	private SdActionPojo sdActionPojo = null;
	private SdActionPositionPojo sdActionPositionPojo = null;
	
	@Autowired
	private SdActionService sdActionService;
	
	//新增action
	public String addAction(){
		if (sdActionPojo == null)
			sdActionPojo = new SdActionPojo();
		this.excludeNullProperties(sdActionPojo);
		HttpServletRequest request = ServletActionContext.getRequest();
		int result = -1;
		String msg = "";
		Map pagedata = new HashMap();
		
		try{
			sdActionPojo.setC_action_id(UUID.randomUUID().toString());
			//先保存从表数据
			String positionParts = request.getParameter("positionParts");
            List<SdActionPositionPojo> positions = new ArrayList<SdActionPositionPojo>();
            if (positionParts != null)
            {
                JSONArray positionPartsJson = JSONArray.fromObject(positionParts);
                for (int i = 0; i < positionPartsJson.size(); i++)
                {
                    JSONObject positionPartJson = positionPartsJson.getJSONObject(i);
                    SdActionPositionPojo position = new SdActionPositionPojo();
                    position.setC_id(UUID.randomUUID().toString());
                    position.setC_action_id(sdActionPojo.getC_action_id());
                    position.setC_respons_type(positionPartJson.getString("c_respons_type"));
                    position.setC_respons_orgid(positionPartJson.getString("c_respons_orgid"));
                    position.setC_respons_positionid(positionPartJson.getString("c_respons_positionid"));
                    positions.add(position);
                }
            }
            //sdActionService.addSdActionPositionsToDB(positions);
			//再保存主表数据
			sdActionService.addSdActionToDB(sdActionPojo);
			result = 0;
			msg = "新增流程节点成功！";
		}catch(Exception e){
			result = -1;
			msg = "新增流程节点失败，请检查填写的内容！";
			e.printStackTrace();
		}
		pagedata.put("result", result);
		pagedata.put("msg", msg);
		this.formatData(pagedata);
		return SUCCESS;
	}
	
	//获取所有的action
	public String getAllActionForTree(){
		try{
			List<SdActionPojo> list = sdActionService.getAllAction();
			RuleTree wtree = new RuleTree();
	        wtree.setId("c_action_id");
	        wtree.setVal("c_action_sname");
	        wtree.setPid("c_action_pid");
	        List<Map> l = this.initTreeData(list, wtree);
	        this.formatData(l);
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	//获取某个action
	public String getActionInfoById(){
		if (sdActionPojo == null)
			sdActionPojo = new SdActionPojo();
		this.excludeNullProperties(sdActionPojo);
		HttpServletRequest request = ServletActionContext.getRequest();
		
		try{
			String id = request.getParameter("id");
			if (id != null && !id.equals("")){
				SdActionPojo data = sdActionService.getActionInfoById(id);
				if (data != null)
					this.formatData(this.addPrefix(data, "sdActionPojo."));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	//获取某个action对应的position信息
	public String getActionPositionInfoById(){
		if (sdActionPositionPojo == null)
			sdActionPositionPojo = new SdActionPositionPojo();
		this.excludeNullProperties(sdActionPositionPojo);
		HttpServletRequest request = ServletActionContext.getRequest();
		
		try{
			String id = request.getParameter("id");
			if (id != null && !id.equals("")){
				List<SdActionPositionPojo> l = sdActionService.getActionPositionInfoById(id);
				if (l != null && l.size() > 0)
					this.formatDatagirdData(l, pagination);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	//修改某个action
	public String modifyActionDataById(){
		if (sdActionPojo == null)
			sdActionPojo = new SdActionPojo();
		this.excludeNullProperties(sdActionPojo);
		HttpServletRequest request = ServletActionContext.getRequest();
		int result = -1;
		String msg = "";
		Map pagedata = new HashMap();
		
		try{
			String id = request.getParameter("id");
			//将从表中对应id的数据全部删除
			sdActionService.deleteActionPosotionById(id);
			//先保存从表数据
			String positionParts = request.getParameter("positionParts");
            List<SdActionPositionPojo> positions = new ArrayList<SdActionPositionPojo>();
            if (positionParts != null)
            {
                JSONArray positionPartsJson = JSONArray.fromObject(positionParts);
                for (int i = 0; i < positionPartsJson.size(); i++)
                {
                    JSONObject positionPartJson = positionPartsJson.getJSONObject(i);
                    SdActionPositionPojo position = new SdActionPositionPojo();
                    position.setC_id(UUID.randomUUID().toString());
                    position.setC_action_id(id);
                    position.setC_respons_type(positionPartJson.getString("c_respons_type"));
                    position.setC_respons_orgid(positionPartJson.getString("c_respons_orgid"));
                    position.setC_respons_positionid(positionPartJson.getString("c_respons_positionid"));
                    positions.add(position);
                }
            }
            sdActionService.addSdActionPositionsToDB(positions);
            //在保存主表数据
			if (id != null && !id.equals("")){
				sdActionPojo.setC_action_id(id);
				if (sdActionService.modifyActionDataById(sdActionPojo) > 0){
					result = 0;
					msg = "数据修改成功！";
				}else{
					result = -1;
					msg = "数据修改失败，请检查填写内容！";
				}
			}
		}catch(Exception e){
			result = -1;
			msg = "数据修改失败，请检查填写内容！";
			e.printStackTrace();
		}
		
		pagedata.put("result", result);
		pagedata.put("msg", msg);
		this.formatData(pagedata);
		return SUCCESS;
	}
	
	//删除某个action
	public String deleteActionById(){
		if (sdActionPojo == null)
			sdActionPojo = new SdActionPojo();
		this.excludeNullProperties(sdActionPojo);
		HttpServletRequest request = ServletActionContext.getRequest();
		String status ="";
		String msg = "";
		Map pagedata = new HashMap();
		
		try{
			String[] ids = request.getParameter("id").split(",");
			if (ids != null && !ids.equals("")){
				if (sdActionService.deleteActionById(ids) > 0){
					status = "ok";
					msg = "删除节点成功！";
				}else{
					status = "err";
					msg = "删除节点失败！";
				}
			}
		}catch(Exception e){
			status = "err";
			msg = "删除节点失败！";
			e.printStackTrace();
		}
		
		pagedata.put("status", status);
		pagedata.put("msg", msg);
		this.formatData(pagedata);
		return SUCCESS;
	}
	
	//action文件
	//private static String fileuuid="";
	private File uploadaction;
	private String uploadactionFileName;
	private String uploadactionContentType;
		
	//excel文件读取服务
	@Autowired
	private ExcelReaderService excelReaderService;
		
	//上传action文件
	public String uploadActionFile(){
		Map pageData = new HashMap();
		
		if (uploadaction == null){
			pageData.put("msg", "没有接收到文件");
			pageData.put("result", false);
			this.formatData(pageData);
			return SUCCESS;
		}
		
		try{
			//解析文件
			FileInputStream fileis = new FileInputStream(uploadaction);
			Map<Integer,List<String>> content = new HashMap<Integer,List<String>>();
			content = excelReaderService.readExcelContentEx(fileis);
			
			String section="", sectionid="";//板块
			//String m1="", m2="", m1id="", m2id="";//管理活动
			//String s1="", s2="", s3="", s4="";//, s1id="", s2id="", s3id="", s4id="";//子流程
			//String node="", nodeid="";//末节点
			
			String[] ids = new String [7];
			ids[0] = ids[1] = ids[2] = ids[3] = ids[4] = ids[5] = ids[6] = "";
			for (int i=2; i<content.size(); i++){
				List<String> tempc = content.get(i);
				if (tempc.size() >= 10){
					section = (tempc.get(0).equals("")==true?section:tempc.get(0));
					sectionid = getSectionTypeByName(section);
					int sx = Integer.parseInt(sectionid);
					if (sx <=0 || sx > 6){
						pageData.put("msg", "文件中板块名称不符合规范。");
						pageData.put("result", false);
						this.formatData(pageData);
						return SUCCESS;
					}
					
					//action
					for (int j=1; j<8; j++){
						String sid = analyzeActionToDB(j,ids,sectionid,tempc.get(j));
						ids[j-1] = (sid.equals("")==true?ids[j-1]:sid);
					}
					
					//action_position
					if (tempc.get(8) != null && !tempc.get(8).equals("") &&
							tempc.get(9) != null && !tempc.get(9).equals("")){
						List<SdActionPositionPojo> positions = new ArrayList<SdActionPositionPojo>();
						SdActionPositionPojo p = new SdActionPositionPojo();
						p.setC_id(UUID.randomUUID().toString());
						p.setC_action_id(getPid(7,ids));
						p.setC_respons_type(getPositionTypeByName(tempc.get(8)));
						String orgid = getOrgIdByName(tempc.get(9));
						if (orgid == null || orgid.equals("")){
							pageData.put("msg", "文件第"+(i+2)+"行不存在该部门，请检查文件内容！");
							pageData.put("result", false);
							this.formatData(pageData);
							return SUCCESS;
						}
						p.setC_respons_orgid(orgid);
						positions.add(p);
						sdActionService.addSdActionPositionsToDB(positions);
					}
				}else{
					pageData.put("msg", "文件格式不合法，请检查文件内容！");
					pageData.put("result", false);
					this.formatData(pageData);
					return SUCCESS;
				}
			}
			
			pageData.put("msg", "文件解析成功！");
			pageData.put("result", true);
		}catch(Exception e){
			pageData.put("msg", "文件解析失败，请检查文件内容！");
			pageData.put("result", false);
			e.printStackTrace();
		}
		
		this.formatData(pageData);
		return SUCCESS;
	}
	
	private String analyzeActionToDB(int index, String[] ids, String sectionid, String content){
		String ret = "";
		if (content != null && !content.equals("")){
			SdActionPojo tnode = analyzeActionToPojo(getPid(index-1,ids),sectionid,content);
			if (tnode != null){
				sdActionService.addSdActionToDB(tnode);
				ret = tnode.getC_action_id();
			}
		}
		return ret;
	}
	private SdActionPojo analyzeActionToPojo(String pid, String sid, String content){
		SdActionPojo t = new SdActionPojo();
		t.setC_action_id(UUID.randomUUID().toString());
		t.setC_action_pid(pid.equals("")==true?"-1":pid);
		t.setC_section_id(sid);
		t.setC_action_sname(content);
		t.setC_action_fname(content);
		
		return t;
	}
	
	private String getPid(int index, String[] ids){
		if (index > 7) return "";
		
		String ret = "-1";
		for (int i=(index-1); i>=0; i--){
			if (ids[i] != null && !ids[i].equals("")){
				ret = ids[i];
				break;
			}
		}
		return ret;
	}
	
	private String getOrgIdByName(String orgname){
		orgname = orgname.trim();
		orgname = orgname.replaceAll("^[　*| *]*","").replaceAll("[　*| *]*$","");
		return sdActionService.getOrgIdByName(orgname);
	}
	private String getSectionTypeByName(String sectionname){
		String ret = "0";
		//去除多余的空格
		sectionname = sectionname.trim();
		//去除全角空格
		sectionname = sectionname.replaceAll("^[　*| *]*","").replaceAll("[　*| *]*$","");
		if (sectionname.equals("安全")){
			ret = "1";
		}else if (sectionname.equals("质量")){
			ret = "2";
		}else if (sectionname.equals("成本")){
			ret = "3";
		}else if (sectionname.equals("效率")){
			ret = "4";
		}else if (sectionname.equals("团队")){
			ret = "5";
		}else if (sectionname.equals("环境")){
			ret = "6";
		}
		
		return ret;
	}
	
	private String getPositionTypeByName(String positiontype){
		String ret = "0";
		//去除多余的空格
		positiontype = positiontype.trim();
		//去除全角空格
		positiontype = positiontype.replaceAll("^[　*| *]*","").replaceAll("[　*| *]*$","");
		if (positiontype.equals("主管")){
			ret = "1";
		}else if (positiontype.equals("协管")){
			ret = "2";
		}else if (positiontype.equals("监管")){
			ret = "3";
		}
		
		return ret;
	}
	

	public SdActionPojo getSdActionPojo() {
		return sdActionPojo;
	}

	public void setSdActionPojo(SdActionPojo sdActionPojo) {
		this.sdActionPojo = sdActionPojo;
	}
	
	public SdActionPositionPojo getSdActionPositionPojo() {
		return sdActionPositionPojo;
	}

	public void setSdActionPositionPojo(SdActionPositionPojo sdActionPositionPojo) {
		this.sdActionPositionPojo = sdActionPositionPojo;
	}

	public File getUploadaction() {
		return uploadaction;
	}

	public void setUploadaction(File uploadaction) {
		this.uploadaction = uploadaction;
	}

	public String getUploadactionFileName() {
		return uploadactionFileName;
	}

	public void setUploadactionFileName(String uploadactionFileName) {
		this.uploadactionFileName = uploadactionFileName;
	}

	public String getUploadactionContentType() {
		return uploadactionContentType;
	}

	public void setUploadactionContentType(String uploadactionContentType) {
		this.uploadactionContentType = uploadactionContentType;
	}

	public ExcelReaderService getExcelReaderService() {
		return excelReaderService;
	}

	public void setExcelReaderService(ExcelReaderService excelReaderService) {
		this.excelReaderService = excelReaderService;
	}
}
