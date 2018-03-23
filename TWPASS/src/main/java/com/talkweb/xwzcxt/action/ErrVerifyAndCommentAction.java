package com.talkweb.xwzcxt.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;
import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.xwzcxt.service.ErrVerifyAndCommentService;
import com.talkweb.xwzcxt.vo.TaskErrTraceVo;

public class ErrVerifyAndCommentAction extends baseAction implements ModelDriven<TaskErrTraceVo>,ServletRequestAware{
	
	private static final long serialVersionUID = -3611345896595796296L;
	
	@Autowired
	private HttpServletRequest request;
	
	private TaskErrTraceVo params=new TaskErrTraceVo();
	
	@Autowired
	private ErrVerifyAndCommentService service;
	
	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		this.request=arg0;
		
	}

	@Override
	public TaskErrTraceVo getModel() {
	return params!=null?params:new TaskErrTraceVo();
	}
	
	
    public String getErrVerifyInfo(){
    	try{
    		String from=request.getParameter("flag");
    		if(!"mobile".equals(from)){
    			Map userInfo=getUser();
    			if(userInfo==null){
    				this.formatData("未登录!");
    				return SUCCESS;
    			}
    			params.setC_chk_userid((Long)userInfo.get("id"));
    		}
    		
			pagination=pagination==null?new Pagination(1,15):pagination;
			params.setOrdertype(1);   //1表示按验证期限排序
			String isFirst=request.getParameter("isFirst");
			if(isFirst==null||isFirst.trim().length()==0){
				params.setC_chk_status(1);
			}
			params.setDatatype(1);
			List list=service.getErrVerifyAndCommentInfo(params,pagination);
			this.formatDatagirdData(list, pagination);
    	}catch(Exception e){
    		e.printStackTrace();
    		this.formatDatagirdData(null, pagination);
    	}
    	return SUCCESS;
    }
	
    public String getErrCommentInfo(){
    	try{
    		String from=request.getParameter("flag");
    		if(!"mobile".equals(from)){
    			Map userInfo=getUser();
    			if(userInfo==null){
    				this.formatData("未登录!");
    				return SUCCESS;
    			}
    			params.setC_evaluate_userid((Long)userInfo.get("id"));
    		}
			pagination=pagination==null?new Pagination(1,15):pagination;
			params.setOrdertype(2);   //2表示按评价期限排序
			String isFirst=request.getParameter("isFirst");
			if(isFirst==null||isFirst.trim().length()==0){
				params.setC_evaluate_status(1);
			}
			params.setDatatype(2);
			
			Integer status=params.getC_evaluate_status();
			if(status!=null&&status==-200){
				params.setC_evaluate_status(2);
				params.setC_ex_iemisevent(0);
				params.setC_evaluate_result("NG");
			}
			List list=service.getErrVerifyAndCommentInfo(params,pagination);
			this.formatDatagirdData(list, pagination);
    	}catch(Exception e){
    		e.printStackTrace();
    		this.formatDatagirdData(null, pagination);
    	}
    	return SUCCESS;
    }
    
	public  String  updateVerifyStatus(){
		try{
			String from=request.getParameter("flag");
			Map params=new HashMap();
    		if(!"mobile".equals(from)){
    			Map userInfo=getUser();
    			if(userInfo==null){
    				this.formatData("未登录!");
    				return SUCCESS;
    			}
    			params.put("c_chk_userid", userInfo.get("id"));
    		}else{
    			params.put("c_chk_userid", this.params.getC_chk_userid());
    		}
			String idstr=request.getParameter("ids");
			if(idstr==null){
				this.formatData("请选择行!");
				return SUCCESS;
			}
			if(idstr.endsWith(",")){
				idstr=idstr.substring(0,idstr.length()-1);
			}
			
			params.put("ids", idstr.split(","));
    		service.updateVerifyStatus(params);
    		this.formatData("提交验证成功!");
    	}catch(Exception e){
    		e.printStackTrace();
    		this.formatData("提交验证失败!");
    	}
    	return SUCCESS;
	}
	
	public String exportVerify(){
		try{
			Map user=this.getUser();
			if(user==null){
				this.formatData("请先登录！");
				return SUCCESS;
			}
			Long userid=(Long) user.get("id");
			params.setC_chk_userid(userid);
			List lst =service.getErrVerifyAndCommentInfo(params,null);
			
			String[] titles = { "异常主题","异常类型","异常状态","验证状态","所属板块","反馈人",
								"反馈时间","异常发生时间","异常处理期限","异常处理完成时间",
								"验证","验证描述","验证期限"};
			String[] fields = {"c_err_name","c_err_kind_name","c_isclose_name","c_chk_status_name",
							   "c_manage_sectionname","c_writer_name","c_upload_time","c_occur_time",
					           "c_suggestend_time","c_close_time","c_chk_value_name","c_chk_result","c_chk_plantime"};
			String url = this.exportExcelData(titles, fields, "yiChangYanZheng",  lst, "异常验证导出");
			Map map = new HashMap();
			map.put("url", url);
			this.formatData(map);
		}catch(Exception e){
			e.printStackTrace();
			return SUCCESS;
		}
		return SUCCESS;
	}
	
	public String exportComment(){
		try{
			Map user=this.getUser();
			if(user==null){
				this.formatData("请先登录！");
				return SUCCESS;
			}
			Long userid=(Long) user.get("id");
			params.setC_chk_userid(userid);
			List lst =service.getErrVerifyAndCommentInfo(params,null);
			String[] titles={"异常主题","异常类型","异常状态","评价状态","所属板块","反馈人","反馈时间","异常发生时间","异常处理期限",	
					"异常处理完成时间","验证结果","验证描述","验证期限","验证完成时间","评价","评价期限"};
			String[] fields = {"c_err_name","c_err_kind_name","c_isclose_name","c_evaluate_status_name",
					   "c_manage_sectionname","c_writer_name","c_upload_time","c_occur_time",
			           "c_suggestend_time","c_close_time","c_chk_value_name","c_chk_result","c_chk_plantime"
			           ,"c_chk_time","c_evaluate_value_name","c_evaluate_plantime"};
			String url = this.exportExcelData(titles, fields, "yiChangPingJia",  lst, "异常验证导出");
			Map map = new HashMap();
			map.put("url", url);
			this.formatData(map);
		}catch(Exception e){
			e.printStackTrace();
			return SUCCESS;
		}
		return SUCCESS;
	}
	
	public  String  updateVerifyResult(){
		try{
			String from=request.getParameter("flag");
    		if(!"mobile".equals(from)){
    			Map userInfo=getUser();
    			if(userInfo==null){
    				this.formatData("未登录!");
    				return SUCCESS;
    			}
    			params.setC_chk_userid((Long)userInfo.get("id"));
    		}
    		service.updateVerifyResult(params);
    	     
    		this.formatData("评价结果更新成功!");
    	}catch(Exception e){
    		e.printStackTrace();
    		this.formatData("评价结果更新失败!");
    	}
    	return SUCCESS;		
	}
	
	
	
	public  String  updateEvaluateStatus(){
		try{
			String from=request.getParameter("flag");
			Map params=new HashMap();
    		if(!"mobile".equals(from)){
    			Map userInfo=getUser();
    			if(userInfo==null){
    				this.formatData("未登录!");
    				return SUCCESS;
    			}
    			params.put("c_evaluate_userid", userInfo.get("id"));
    		}else{
    			params.put("c_evaluate_userid", this.params.getC_evaluate_userid());
    		}
			String idstr=request.getParameter("ids");
			if(idstr==null){
				this.formatData("请选择行!");
				return SUCCESS;
			}
			if(idstr.endsWith(",")){
				idstr=idstr.substring(0,idstr.length()-1);
			}
			
			params.put("ids", idstr.split(","));
    		service.updateEvaluateStatus(params);
    		this.formatData("提交评价成功!");
    	}catch(Exception e){
    		e.printStackTrace();
    		this.formatData("提交评价失败!");
    	}
    	return SUCCESS;		
	}
	
	public  String  updateEvaluateResult(){
		try{
			String from=request.getParameter("flag");
    		if(!"mobile".equals(from)){
    			Map userInfo=getUser();
    			if(userInfo==null){
    				this.formatData("未登录!");
    				return SUCCESS;
    			}
    			params.setC_evaluate_userid((Long)userInfo.get("id"));
    		}
    		service.updateEvaluateResult(params);
    	     
    		this.formatData("评价结果更新成功!");
    	}catch(Exception e){
    		e.printStackTrace();
    		this.formatData("评价结果更新失败!");
    	}
    	return SUCCESS;			
	}
	
	public String updateEmisStatus(){
		try{
			String s = request.getParameter("userId");
			if(!"".equals(s)||null!=s){
			Long l = Long.parseLong(s);
			params.setUserid(l);			
			service.updateEmisStatus(params);
			}
		}catch(Exception e){
			e.printStackTrace();
			return "failed";
		}
		return SUCCESS;
	}
	
	private Map getUser(){
		return (Map) request.getSession().getAttribute("USERSESSION");
	}
}
