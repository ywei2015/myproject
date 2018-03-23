package com.talkweb.xwzcxt.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.talkweb.twdpe.base.org.pojo.Area;
import com.talkweb.twdpe.core.log.Logger;
import com.talkweb.twdpe.core.log.LoggerFactory;

import com.talkweb.twdpe.dal.exception.DalException;
import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.twdpe.web.jcontrols.RuleSelect;
import com.talkweb.twdpe.web.session.main.Session;
import com.talkweb.twdpe.web.session.util.SessionFactory;
import com.talkweb.twdpe.webcomponent.common.action.BusinessAction;
import com.talkweb.xwzcxt.pojo.AreaInfoPojo;
import com.talkweb.xwzcxt.pojo.MyLogPojo;
import com.talkweb.xwzcxt.pojo.RuleDataTypePojo;
import com.talkweb.xwzcxt.pojo.TWorkObjtype;
import com.talkweb.xwzcxt.service.MyLogService;
import com.talkweb.xwzcxt.service.impl.AreaInfoImpl;

import net.sf.json.JSONObject;

/**
 * 文件名称: AreaMngAction.java 内容摘要:
 * 
 * @author: 袁刚
 * @version: 1.0
 * @Date: 2011-4-13 下午02:51:02
 * 
 * 版权: 版权所有(C)2011 公司: 拓维信息系统股份有限公司
 */
public class AreaInfoAction extends BusinessAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2061872160007336103L;

	private static final Logger logger = LoggerFactory.getLogger( AreaInfoAction .class);

	/**
	 * 通过注入方式初始化的地区管理类,用于地区管理的增，删，改，查的数据库操作类
	 * */
	@Autowired
	private AreaInfoImpl areaInfoImpl;

	@Autowired
	private MyLogService logService;
	
	/**
	 * 地区类型对象，用于添加和修改地区类型时封装的对象
	 * */
	private AreaInfoPojo area = null;

	public AreaInfoPojo getArea() {
		return area;
	}

	public void setArea(AreaInfoPojo area) {
		this.area = area;
	}
    
	/**
	 * 初始化表单控件默认值
	 * 
	 * @Title: initFromControl
	 * @Description: 初始化表单控件默认值
	 * @throws IOException
	 * @throws DalException
	 */
	public String initFromControl() {
		HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(
				ServletActionContext.HTTP_REQUEST);
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			Session session = SessionFactory.getInstance(request, response);
			Map user = (Map) session.getAttribute("USERSESSION");
			if (user == null) {
				this.formatData("请先登录!");
				return SUCCESS;
			}
			} catch (Exception e) {
				ServletActionContext.getRequest().setAttribute("throw", e);
				logger.error(e.getMessage(), e);
			}
			
		String id = request.getParameter("id");
		area = areaInfoImpl.getAreaInfoByID(id);
		if(id==null){
			return SUCCESS;
		}
		area.setC_basedata_id(id);
		try {

			
			if (area != null) {
				this.formatData(this.addPrefix(area, "area."));
			}
		} catch (Exception e) {
			ServletActionContext.getRequest().setAttribute("throw", e);
			logger.error(e.getMessage(), e);
		}
		area = null;
		return SUCCESS;
	}

	/**
	 * 根据地区id获取地区信息
	 * 
	 * @Title: getAreaById
	 * @Description: 初始化表单控件默认值
	 * @throws IOException
	 * @throws DalException
	 */
	public String getAreaTypeById() {
		HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(
				ServletActionContext.HTTP_REQUEST);

		List<AreaInfoPojo> list=areaInfoImpl.getAreaType();
		RuleSelect wtree = new RuleSelect();
		wtree.setValue("typeid");
		wtree.setText("typename");

		try {
			if (list != null && list.size() > 0) {
				List<Map> l = this.initSelectData(list, wtree);
				this.formatData(l);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 修改指定地区信息
	 * 
	 * @Title: updateArea
	 * @Description: 修改指定地区信息
	 */
	@SuppressWarnings("unchecked")
	public String updateArea() {
		HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        Session session = SessionFactory.getInstance(request, response);
        AreaInfoPojo areainfo = new AreaInfoPojo();
        String id=request.getParameter("area.c_basedata_id");
        
        if (areainfo.getC_area_upid() == null || "".equals(areainfo.getC_area_upid())) {
    			areainfo.setC_area_upid("-1");
    	}
    	
        if(id==null && "".equals(id)){
        	return SUCCESS;
        }else{

        areainfo.setC_basedata_id(id);
        areainfo.setC_area_upid(request.getParameter("area.uparea"));
        areainfo.setC_area_name(request.getParameter("area.c_area_name"));
        areainfo.setC_area_type(request.getParameter("area.c_area_type"));
        areainfo.setC_area_fullname(request.getParameter("area.c_area_fullname"));
        areainfo.setC_isaor(request.getParameter("area.c_isaor"));
        areainfo.setC_org_name(request.getParameter("area.c_org_name"));
        areainfo.setC_firstpic_username(request.getParameter("area.c_firstpic_username"));
        areainfo.setC_mainpic_username(request.getParameter("area.c_mainpic_username"));
        areainfo.setC_directpic_username(request.getParameter("area.c_directpic_username"));
        areainfo.setC_directpic_tel(request.getParameter("area.c_directpic_tel"));
        areainfo.setC_print_count(Long.parseLong(request.getParameter("area.c_print_count")));
        
        }
    
		try {
			Map<String, Object> userMap = (Map) session
					.getAttribute("USERSESSION");
			if (userMap == null) {
				this.setErrorMessage("请先登录！");
			} else {
				String update_user = userMap.get("name").toString(); // 获取登录人的姓名
				areainfo.setC_update_user(update_user);
			} 
			
			AreaInfoPojo oldlogareainfo = areaInfoImpl.getAreaInfoByID(areainfo.getC_basedata_id());
			JSONObject oldObj = JSONObject.fromObject(oldlogareainfo); 
			
			areaInfoImpl.updateArea(areainfo);	
			areaInfoImpl.updateScanDtails(areainfo);
			
			//记录数据日志
			AreaInfoPojo logareainfo = areaInfoImpl.getAreaInfoByID(areainfo.getC_basedata_id());
			JSONObject newObj = JSONObject.fromObject(logareainfo); 
			logService.addLogInfo(request, response, "1", "过程行为支持系统", "2", "基础管理", "作业区域管理", MyLogPojo.change, "作业区域管理-修改", "成功", "1", "TB_AREAINFO", oldObj.toString(), newObj.toString());
			
			
			Map<String, String> pageData = new HashMap<String, String>();
			pageData.put("id", id);
			pageData.put("msg", "修改区域成功");
			pageData.put("status", "ok");
			this.formatData(pageData);  //后台传给前台的数据
			//this.setMessage(1, "AREA", "MODIFY");
		} catch (Exception e) {
			ServletActionContext.getRequest().setAttribute("throw", e);
			Map<String, String> pageData = new HashMap<String, String>();
			pageData.put("msg", "修改区域失败");
			pageData.put("status", "error");
			//this.setMessage(0, "AREA", "MODIFY");
			logger.error(e.getMessage(), e);
		}
		area = null;
		return SUCCESS;
	}   

	
/*	
	 * 修改指定地区二维码扫描码
	 * 
	 * @Title: updateScan
	 * @Description: 修改指定地区扫描码信息
	 
	
	@SuppressWarnings("unchecked")
	public String updateScanCode()
    {
		HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        Session session = SessionFactory.getInstance(request, response);
        AreaInfoPojo areainfo = new AreaInfoPojo();
        String id=request.getParameter("area.c_basedata_id");
        try {
        	
        if (areainfo.getC_area_upid() == null || "".equals(areainfo.getC_area_upid())) {
    			areainfo.setC_area_upid("-1");
    	}
    	
        if(id==null && "".equals(id)){
        	return SUCCESS;
        }else{
        
        areainfo.setC_basedata_id(id);
        areainfo.setC_print_count(Long.parseLong(request.getParameter("area.c_print_count")));
        
        }
  		
       areaInfoImpl.updateScanCode(areainfo);
	   } catch (Exception e) {
		   logger.error(e.getMessage(),e);
		   e.printStackTrace();
	    }
	   area = null;
	   return SUCCESS;

    }*/
	
	
	/**
	 * 增加地区信息
	 * 
	 * @Title: addArea
	 * @Description: 增加地区信息
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public String addArea() {
		HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        Session session = SessionFactory.getInstance(request, response);

		AreaInfoPojo areainfo =  new AreaInfoPojo();
		if (areainfo.getC_area_upid() == null || "".equals(areainfo.getC_area_upid())) {
			areainfo.setC_area_upid("-1");
		}
		if (areainfo != null && areainfo.getC_basedata_id() != null && !"".equals(areainfo.getC_basedata_id())) {
			this.setMessage(2, "AREA", "ADD");
		} else {
            //areainfo.setC_basedata_id(request.getParameter("area.c_basedata_id"));
			String c_basedata_id = areaInfoImpl.getNextAeraId();
			areainfo.setC_basedata_id(c_basedata_id);
	        areainfo.setC_area_upid(request.getParameter("area.uparea"));
	        areainfo.setC_area_name(request.getParameter("area.c_area_name"));
	        areainfo.setC_area_fullname(request.getParameter("area.c_area_fullname"));
	        areainfo.setC_area_type(request.getParameter("area.c_area_type"));
	        areainfo.setC_isaor(request.getParameter("area.c_isaor"));
	        areainfo.setC_org_name(request.getParameter("area.c_org_name"));
	        areainfo.setC_firstpic_username(request.getParameter("area.c_firstpic_username"));
	        areainfo.setC_mainpic_username(request.getParameter("area.c_mainpic_username"));
	        areainfo.setC_directpic_username(request.getParameter("area.c_directpic_username"));
	        areainfo.setC_directpic_tel(request.getParameter("area.c_directpic_tel"));
	        areainfo.setC_print_count(Long.parseLong(request.getParameter("area.c_print_count")));
	        
			try {
				@SuppressWarnings("rawtypes")
				Map<String, Object> userMap = (Map) session
						.getAttribute("USERSESSION");
				if (userMap == null) {
					this.setErrorMessage("请先登录!");
				} else {
					String update_user = userMap.get("name").toString(); // 获取登录人的姓名
					areainfo.setC_update_user(update_user);
				}
			
				areaInfoImpl.addArea(areainfo);
				areaInfoImpl.updateScanDtails(areainfo);
				areaInfoImpl.updateScanCode(areainfo);
				
				//记录数据日志
				AreaInfoPojo logareainfo = areaInfoImpl.getAreaInfoByID(areainfo.getC_basedata_id());
				JSONObject jsonValue = JSONObject.fromObject(logareainfo); 
				logService.addLogInfo(request, response, "1", "过程行为支持系统", "2", "基础管理", "作业区域管理", MyLogPojo.add, "作业区域管理-新增", "成功", "1", "TB_AREAINFO", "", jsonValue.toString());
				
				Map<String, String> pageData = new HashMap<String, String>();
				pageData.put("id", c_basedata_id);
				pageData.put("msg", "添加区域成功");
				pageData.put("status", "ok");
				this.formatData(pageData);  //后台传给前台的数据
				//this.setMessage(1, "AREA", "ADD");
			} catch (Exception e) {
				ServletActionContext.getRequest().setAttribute("throw", e);
				Map<String, String> pageData = new HashMap<String, String>();
				pageData.put("msg", "添加区域失败");
				pageData.put("status", "error");
				//this.setMessage(0, "AREA", "ADD");
				logger.error(e.getMessage(), e);
			}
		}

		area = null;
		return SUCCESS;
	}
	
	public String deleteArea(){
		HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
		try{
			Session session = SessionFactory.getInstance(request, response);
			Map user = (Map) session.getAttribute("USERSESSION");
			if (user == null) {
				this.formatData("请先登录!");
				return SUCCESS;
			}
			
			String oldString = "";
			String ids[] = request.getParameter("ids").split(",");
			for(int i = 0; i < ids.length; i++) {
				AreaInfoPojo logareainfo = areaInfoImpl.getAreaInfoByID(ids[i]);
				JSONObject jsonValue = JSONObject.fromObject(logareainfo);
				oldString+= jsonValue;
				
				Map map=new HashMap();
				map.put("c_basedata_id", ids[i]);
				areaInfoImpl.deleteArea(map);
			}
			
			//记录数据日志
			logService.addLogInfo(request, response, "1", "过程行为支持系统", "2", "基础管理", "作业区域管理", MyLogPojo.del, "作业区域管理-删除", "成功", "1", "TB_AREAINFO", oldString, "");
			
			
			Map<String, String> pageData = new HashMap<String, String>();
			pageData.put("msg", "删除成功！");
			pageData.put("status", "ok");
			this.formatData(pageData);
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage(),e);
		}
		return SUCCESS;
	}
	
	public String printCode(){
		HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
		try{
			Session session = SessionFactory.getInstance(request, response);
			Map user = (Map) session.getAttribute("USERSESSION");
			if (user == null) {
				this.formatData("请先登录!");
				return SUCCESS;
			}
			String username=user.get("name").toString();
			String c_basedata_id=request.getParameter("area.c_basedata_id");
			
			HashMap param=new HashMap();		
			param.put("c_basedata_id",c_basedata_id);
			param.put("c_print_user", username);
			
			Map<String, String> pageData = new HashMap<String, String>();
			pageData.put("msg", "打印成功！");
			pageData.put("status", "ok");
			
			int count=areaInfoImpl.printCode(param);
			if(count<1){
				this.formatData("打印失败！");
			}else{
				this.formatData(pageData);
			
			}
			
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String getCurrentPrintCount(){
		HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
		try{
				Session session = SessionFactory.getInstance(request, response);
				Map user = (Map) session.getAttribute("USERSESSION");
				if (user == null) {
					this.formatData("请先登录!");
					return SUCCESS;
				}
			String c_basedata_id=request.getParameter("area.c_basedata_id");
			String count=areaInfoImpl.getCurrentPrintCount(c_basedata_id);
			if(count!=null){
				this.formatData(Integer.parseInt(count));
			}
			
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

}
