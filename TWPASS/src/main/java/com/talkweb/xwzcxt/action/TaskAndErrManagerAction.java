package com.talkweb.xwzcxt.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;
import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.xwzcxt.service.TaskAndErrManagerService;
import com.talkweb.xwzcxt.vo.TaskAndErrManagerVo;

public class TaskAndErrManagerAction extends BusinessAction implements ModelDriven<TaskAndErrManagerVo>,ServletRequestAware{
	private static final long serialVersionUID = -4710819927085969931L;

	private TaskAndErrManagerVo params=new TaskAndErrManagerVo();
	private HttpServletRequest request;
	
	@Autowired
	private TaskAndErrManagerService mservice ;
	
	private Map getUser(){
		HttpSession session=request.getSession();
		Map user = (Map) session.getAttribute("USERSESSION");
		return user;
	}
	
	public String getUnexecuteableTask(){
		try{
			Map user=getUser();
			if(pagination==null){
				pagination=new Pagination(1,15);
			}
			if(user==null){
				this.formatDatagirdData(null, pagination);
				return SUCCESS;
			}
			Long userid=(Long) user.get("id");
			params.setUserid(userid);
			List list=mservice.getUnexecuteableTask(params,pagination);
			this.formatDatagirdData(list, pagination);
		}catch(Exception e){
			e.printStackTrace();
			this.formatDatagirdData(null,pagination);
		}
		return SUCCESS;
	}
	
	public String  deleteTask(){
		HttpServletRequest request1 = ServletActionContext.getRequest();
		HttpServletResponse response1 = ServletActionContext.getResponse();
	    try{
	    	Map user=getUser();
	    	if(user==null){
	    		this.formatData("未登录!");
	    		return SUCCESS;
	    	}
	    	
	    	String idStr=request.getParameter("ids");
	    	if(idStr==null||idStr.trim().length()==0){
	    		this.formatData("请选择要删除的数据!");
	    		return SUCCESS;
	    	}
	    	if(idStr.endsWith(",")){	    		
	    		idStr=idStr.substring(0,idStr.length()-1);
	    	}
	    	String[] ids=idStr.split(",");
	    	if(ids.length==0){
	    		this.formatData("请选择要删除的数据!");
	    		return SUCCESS;
	    	}
	    	HashMap map=new HashMap();
	    	map.put("ids", ids);
	    	map.put("userid", user.get("id"));
	    	mservice.deleteTask(map, request1, response1);
	    	this.formatData("成功删除任务!");
	    }catch(Exception e){
	    	e.printStackTrace();
	    	this.formatData("删除失败!");
	    }
	    return SUCCESS;
	}
	
	public TaskAndErrManagerVo getParams() {
		return params;
	}


	public void setParams(TaskAndErrManagerVo params) {
		this.params = params;
	}


	@Override
	public TaskAndErrManagerVo getModel() {
		return params!=null?params:new TaskAndErrManagerVo();
	}



	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		this.request=arg0;
	}

}
