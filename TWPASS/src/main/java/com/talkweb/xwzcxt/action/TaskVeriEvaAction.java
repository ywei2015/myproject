package com.talkweb.xwzcxt.action;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


import sun.misc.*;

import com.opensymphony.xwork2.ModelDriven;
import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.twdpe.web.session.main.Session;
import com.talkweb.twdpe.web.session.util.SessionFactory;
import com.talkweb.xwzcxt.pojo.TaskMouldPojo;
import com.talkweb.xwzcxt.service.JobObjectsService;
import com.talkweb.xwzcxt.service.impl.TaskVeriEvaImpl;
import com.talkweb.xwzcxt.service.impl.TaskVerifyAndCommentServiceImpl;

public class TaskVeriEvaAction extends BusinessAction implements ModelDriven<TaskMouldPojo>,ServletRequestAware{
	


	private static final long serialVersionUID = 5900325933983439652L;

	private static final Logger logger = LoggerFactory
			.getLogger(TaskVeriEvaAction.class);
	
	@Autowired
	private TaskVeriEvaImpl taskVeriEvaImpl;
	@Autowired
	private JobObjectsService jobObjectsService;
	
	@Autowired
	private TaskVerifyAndCommentServiceImpl taskVerifyAndComment;
	
	HttpServletRequest request;
	HttpServletResponse response = ServletActionContext.getResponse();
	private TaskMouldPojo  taskMouldPojo=new TaskMouldPojo();
	
	public String queryTaskVerify() {
		 String pageindex=request.getParameter("c_pageindex");
		 String pagesize=request.getParameter("c_pagesize");
		 String taskKind=request.getParameter("taskKind");
		try {
			HashMap map=new HashMap();
			Session session = SessionFactory.getInstance(request, response);
			Map<String, Object> userMap = (Map) session.getAttribute("USERSESSION");
			String userid = request.getParameter("userId");
		
            map.put("c_chk_userid", userid);
            map.put("c_pageindex", pageindex);
            map.put("c_pagesize", pagesize);
            map.put("taskKind", taskKind);
            
            List<TaskMouldPojo> list=new ArrayList<TaskMouldPojo>();
            list=taskVeriEvaImpl.queryTaskVerify(map);
			this.formatData(list); 
			} catch (Exception e) {
				this.formatData("[]");
				e.printStackTrace();
				logger.error(e.getMessage(), e);
				return SUCCESS;
			 }
	
		return SUCCESS;
	}
	
	public String queryTaskVerifyCount(){
		
		try {
			HashMap map=new HashMap();
			Session session = SessionFactory.getInstance(request, response);
			Map<String, Object> userMap = (Map) session.getAttribute("USERSESSION");
			String userid = request.getParameter("userId");
			String taskKind=request.getParameter("taskKind");
			map.put("userid", userid);
			map.put("taskKind", taskKind);
			String totalNum="";	
			totalNum=taskVeriEvaImpl.queryTaskVerifyCount(map);
            HashMap pageData=new HashMap();
            pageData.put("totalNum",totalNum);
			this.formatData(pageData); 
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage(), e);
				return SUCCESS;
			 }
	
		return SUCCESS;
	}
	
	public String updateChkResult() {
		String id=taskMouldPojo.getC_task_id();
        if(id==null || "".equals(id)){
        	this.formatData("");
        	return SUCCESS;
        }
        
    	try {
    		taskVeriEvaImpl.updateChkResult(taskMouldPojo);
			this.formatData("提交成功");  //后台传给前台的数据
		} catch (Exception e) {
			e.printStackTrace();
			this.formatData("提交失败！");
			return SUCCESS;
		}
		return SUCCESS;
	}  
	
	public String queryTaskEvaluate() {
		 String pageindex=request.getParameter("c_pageindex");
		 String pagesize=request.getParameter("c_pagesize");
		 String taskKind=request.getParameter("taskKind");
		try {
			HashMap map=new HashMap();
			Session session = SessionFactory.getInstance(request, response);
			Map<String, Object> userMap = (Map) session.getAttribute("USERSESSION");
			String userid = request.getParameter("userId");	
           map.put("c_evaluate_userid", userid);
           map.put("c_pageindex", pageindex);
           map.put("c_pagesize", pagesize);
           map.put("taskKind", taskKind);
           List<TaskMouldPojo> list=new ArrayList<TaskMouldPojo>();
           list=taskVeriEvaImpl.queryTaskEvaluate(map);
			this.formatData(list); 
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage(), e);
			 }
	
		return SUCCESS;
	}
	
	public String queryTaskEvaluateCount(){
		
		try {
			HashMap map=new HashMap();
			Session session = SessionFactory.getInstance(request, response);
			Map<String, Object> userMap = (Map) session.getAttribute("USERSESSION");
			String evaUserid = request.getParameter("userId");
			String taskKind=request.getParameter("taskKind");
			map.put("evaUserid", evaUserid);
			map.put("taskKind", taskKind);
			String totalNum="";	
		   totalNum=taskVeriEvaImpl.queryTaskEvaluateCount(map);
           HashMap pageData=new HashMap();
           pageData.put("totalNum",totalNum);
			this.formatData(pageData); 
			} catch (Exception e) {
				ServletActionContext.getRequest().setAttribute("throw", e);
				logger.error(e.getMessage(), e);
			 }
	
		return SUCCESS;
	}
	
	public String updateEvalResult() {
		String id=taskMouldPojo.getC_task_id();
        if(id==null || "".equals(id)){ 
        	return SUCCESS;
        }
        
    	try {
    		taskVeriEvaImpl.updateEvalResult(taskMouldPojo);
			this.formatData("提交成功");  //后台传给前台的数据
		} catch (Exception e) {
			e.printStackTrace();
			this.formatData("提交失败！");
			return SUCCESS;
		}
		return SUCCESS;
	}
	
	public String getObjectInfo() {
		try {
			
			String cBasedataId=request.getParameter("cBasedataId");
			String cTableTypeId=request.getParameter("cTabletypeId");
			String cBasedataName=request.getParameter("cBasedataName");
			Map map=new HashMap();
			map.put("cTabletypeId", cTableTypeId);
			map.put("cBasedataName", cBasedataName);
			map.put("cBasedataId", cBasedataId);
			if(pagination==null){
				pagination=new Pagination(1,20);
			}
			jobObjectsService.getObjectInfo(pagination, map);
			this.formatDatagirdData(pagination.getList(), pagination);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		return SUCCESS;
	}
   
	//任务批量验证
	public String verifyStatusUpdate() {
		try {
			String cTaskId[] = request.getParameter("cTaskId").split(",");
			Session session = SessionFactory.getInstance(request, response);
			String userid=request.getParameter("userId");
			Map map = new HashMap();
			map.put("cTaskId", cTaskId);
			map.put("userid", userid);
			int count = taskVerifyAndComment.verifyStatusUpdate(map);
			String msg = "";
			if (count > 0) {
				msg = "成功提交了" + count + "条验证数据！";
			}
			this.formatData(msg);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	
	//任务批量评价
	public String commentStatusUpdate() {
		try {
			Session session = SessionFactory.getInstance(request, response);
			String userid=request.getParameter("userId");
			/*Map user = (Map) session.getAttribute("USERSESSION");
			if(user==null){
				String rs="请先登录!";
				this.formatData(rs);
				return SUCCESS;
			}
			String userid=user.get("id")+"";*/
			String cTaskId[] = request.getParameter("cTaskId").split(",");
			Map map = new HashMap();
			map.put("cTaskId", cTaskId);
			map.put("userid", userid);
			int count = taskVerifyAndComment.commentStatusUpdate(map);
			String rs = "提交失败！";
			if (count > 0) {
				rs = "成功提交了 " + count + "条评价数据！";
			}
			this.formatData(rs);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String getUserCode(){
		String userid=request.getParameter("userId");
		String code="";
		String usercode="";
		try {
			code=taskVeriEvaImpl.getUserCode(userid);
			usercode=getBase64(code);
			this.formatData(usercode);
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
   private  String getBase64(String str) {  
        byte[] b = null;  
        String s = null;  
        try {  
            b = str.getBytes("utf-8");  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
        if (b != null) {  
            s = new BASE64Encoder().encode(b);  
        }  
        return s;  
    }
	   
	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		this.request=arg0;
	}

	@Override
	public TaskMouldPojo getModel() {
	   if(taskMouldPojo==null){
		   return new TaskMouldPojo();
	   }
	   return taskMouldPojo;
	}

	
}
