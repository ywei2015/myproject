package com.talkweb.xwzcxt.action;

import java.awt.print.Pageable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import bsh.StringUtil;

import com.talkweb.common.AppConstants;
import com.talkweb.twdpe.base.org.pojo.User;
import com.talkweb.twdpe.base.org.service.UserService;
import com.talkweb.twdpe.core.util.DateUtil;
import com.talkweb.twdpe.core.util.json.JsonObject;
import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.twdpe.web.session.main.Session;
import com.talkweb.twdpe.web.session.util.SessionFactory;
import com.talkweb.xwzcxt.pojo.TaskMouldPojo;
import com.talkweb.xwzcxt.service.TaskVerifyAndCommentService;

public class TaskVerifyAndCommentAction extends baseAction {
	private static final long serialVersionUID = -4327038267793971139L;

	private static final Logger logger = LoggerFactory
			.getLogger(TaskVerifyAndCommentAction.class);
	
	@Autowired
    private UserService userService;

	@Autowired
	private TaskVerifyAndCommentService taskVerifyAndComment;
	
	@Autowired
	private AppConstants appConstants;
	

	private HttpServletRequest request = ServletActionContext.getRequest();
	private HttpServletResponse response = ServletActionContext.getResponse();

	public String verify() {

		try {

			Map params = new HashMap();
			String userCode = request.getParameter("userCode")==null?"":request.getParameter("userCode");
			String extype = request.getParameter("extype")==null?"":request.getParameter("extype");
			String type = request.getParameter("type")==null?"":request.getParameter("type");
			if(userCode.equals("")&&!extype.equals("1")){
			Session session = SessionFactory.getInstance(request, response);
			Map user = (Map) session.getAttribute("USERSESSION");
			System.out.println(user+"***************************************");
			if(user!=null){
				String orgid=user.get("orgid").toString();
				params.put("orgid", orgid);
				String userid=user.get("id").toString();
				params.put("userid", userid);
			}else{
				String rs="请先登录!";
				this.formatData(rs);
				return SUCCESS;
			}
			}else{
				List<User> loginUsersByUserCode = userService.getLoginUsersByUserCode(userCode);
				params.put("userid", loginUsersByUserCode.get(0).getUserId());
//				params.put("userCode", userCode);
			}
			
			
			String c_task_kind=request.getParameter("c_task_kind");
			if(c_task_kind!=null){
				params.put("c_task_kind", c_task_kind);
			}
			String year=request.getParameter("year");
			String month=request.getParameter("month");
			if(year!=null&&year.trim().length()>0){
				params.put("year", year.trim());
			}
            if(month!=null&&month.trim().length()>0){
            	int m=Integer.parseInt(month.trim());
            	if(m<10){
            		params.put("month", "0"+month);
            	}else{
            		params.put("month", month);
            	}
            }
			String starttime = request.getParameter("startTime");
			String endtime = request.getParameter("endTime");
			if (starttime != null && !starttime.isEmpty()) {
				params.put("cStartTime", DateUtil.getTimestamp(DateUtil
						.formatStr2Date(starttime)));
			}
			if (endtime != null && !endtime.isEmpty()) {
				params.put("cEndTime",
						DateUtil.getTimestamp(DateUtil.formatStr2Date(endtime)));
			}
			String cExecUsername;
			cExecUsername = request.getParameter("execUsername");
			params.put("cExecUsername", cExecUsername);
			String status = request.getParameter("status");
			params.put("status", status);
			String cChkResult = request.getParameter("cChkResult");
			params.put("cChkResult", cChkResult);
			
			String cTaskType=request.getParameter("cTaskType");
			params.put("cTaskType", cTaskType);
			String cManageSection=request.getParameter("cManageSection");
			params.put("cManageSection", cManageSection);
			String isExpired=request.getParameter("isExpired");
			params.put("isExpired", isExpired);
			String cChkUsername=request.getParameter("cChkUsername");
			params.put("cChkUsername", cChkUsername);
			
			
			// pagination
			if (pagination == null) {
				pagination = new Pagination(1, 20);
			}
			if(type.equals("1")){
				Map map = new HashMap();
				pagination = new Pagination(1, 99999999);
				pagination.setOnlyCount(true);
				params.put("status", 33);
				Pagination page = taskVerifyAndComment.getTaskVerifyInfo(params,
						pagination);
				map.put("rwyz", page.getCount());
				this.formatData(map);
				return SUCCESS;
			}else{
			Pagination page = taskVerifyAndComment.getTaskVerifyInfo(params,
					pagination);
			this.formatDatagirdData(page.getList(), page);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String comment() {

		try {

			Map params = new HashMap();
			String type = request.getParameter("type")==null?"":request.getParameter("type");
			String userCode = request.getParameter("userCode")==null?"":request.getParameter("userCode");
			String extype = request.getParameter("extype")==null?"":request.getParameter("extype");
			if(userCode.equals("")&&!extype.equals("1")){
			Session session = SessionFactory.getInstance(request, response);
			Map user = (Map) session.getAttribute("USERSESSION");
			if(user!=null){
				String orgid=user.get("orgid").toString();
				params.put("orgid", orgid);
				String userid=user.get("id").toString();
				params.put("userid", userid);
			}else{
				String rs="请先登录!";
			   this.formatData(rs);
			   return SUCCESS;
			}
			}else{
				List<User> loginUsersByUserCode = userService.getLoginUsersByUserCode(userCode);
				params.put("userid", loginUsersByUserCode.get(0).getUserId());
			}
			String c_task_kind=request.getParameter("c_task_kind");
			if(c_task_kind!=null){
				params.put("c_task_kind", c_task_kind);
			}
			String year=request.getParameter("year");
			String month=request.getParameter("month");
			if(year!=null&&year.trim().length()>0){
				params.put("year", year);
			}
			 if(month!=null&&month.trim().length()>0){
	            	int m=Integer.parseInt(month.trim());
	            	if(m<10){
	            		params.put("month", "0"+month);
	            	}else{
	            		params.put("month", month);
	            	}
	            }
			String starttime = request.getParameter("startTime");
			String endtime = request.getParameter("endTime");
			if (starttime != null && !starttime.isEmpty()) {
				params.put("cStartTime", DateUtil.getTimestamp(DateUtil
						.formatStr2Date(starttime)));
			}
			if (endtime != null && !endtime.isEmpty()) {
				params.put("cEndTime",
						DateUtil.getTimestamp(DateUtil.formatStr2Date(endtime)));
			}
			String cExecUsername;

			cExecUsername = request.getParameter("execUsername");
			params.put("cExecUsername", cExecUsername);
			String status = request.getParameter("status");
			if(status!=null && status.equals("350")){
				params.put("status", "35");
				params.put("c_ex_iemisevent", "0");
				params.put("cEvaluateResult", "NG");
			}else if(status!=null && status.equals("35")){
				params.put("status", "35");
				params.put("c_ex_iemisevent", "1");
			}
			else{
				params.put("status", status);
			}				
			String cEvaluateResult = request.getParameter("cEvaluateResult");
			if(cEvaluateResult!=null &&cEvaluateResult.trim().length()!=0){
				params.put("cEvaluateResult", cEvaluateResult);
			}				
			
			String cTaskType=request.getParameter("cTaskType");
			params.put("cTaskType", cTaskType);
			String cManageSection=request.getParameter("cManageSection");
			params.put("cManageSection", cManageSection);
			String isExpired=request.getParameter("isExpired");
			params.put("isExpired", isExpired);
			String cChkUsername=request.getParameter("cChkUsername");
			params.put("cChkUsername", cChkUsername);
			String cEvaluateUsername=request.getParameter("cEvaluateUsername");
			params.put("cEvaluateUsername", cEvaluateUsername);
            String cChkResult=request.getParameter("cChkResult");
            params.put("cChkResult", cChkResult);
			// pagination
            if(type.equals("1")){
				Map map = new HashMap();
				pagination = new Pagination(1, 99999999);
				pagination.setOnlyCount(true);
				Pagination page = taskVerifyAndComment.getTaskCommentInfo(params,
						pagination);
				map.put("rwpj", page.getCount());
				this.formatData(map);
				return SUCCESS;
			}else{
			if (pagination == null) {
				pagination = new Pagination(1, 20);
			}
			Pagination page = taskVerifyAndComment.getTaskCommentInfo(params,
					pagination);
			this.formatDatagirdData(page.getList(), page);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String getVerifyStd() {
		try {
			Map params = new HashMap();
			String actnodeId = request.getParameter("cActnodeId");
			params.put("actnodeId", actnodeId);
			String verifyStd =null;
			if (com.talkweb.twdpe.core.util.StringUtil.isNumber(actnodeId)) {
				params.put("version", "0");
				verifyStd = taskVerifyAndComment.getVerifyStd(params);
				if(verifyStd==null || verifyStd.equals("")){
					params.put("version", "1");
					verifyStd = taskVerifyAndComment.getVerifyStd(params);
				}
			}else{
				params.put("version", "1");
				verifyStd = taskVerifyAndComment.getVerifyStd(params);
				if(verifyStd==null || verifyStd.equals("")){
					params.put("version", "0");
					verifyStd = taskVerifyAndComment.getVerifyStd(params);
				}
			}
			System.out.println(params+"*****************************");
			
			if (verifyStd != null) {
				verifyStd = verifyStd.replace("\n", "\r\n");
			}else{
				verifyStd="";
			}
			this.formatData(verifyStd);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String getUserCode(){

		Map params = new HashMap();
		Session session = SessionFactory.getInstance(request, response);
		Map user = (Map) session.getAttribute("USERSESSION");
		//System.out.println(user+"****************************************");
		try{
			String userCode="请先登录！";
			if(user!=null){	
				userCode=user.get("code").toString();
			}
			String emis_url = appConstants.getValue("EMIS_URL");
			HashMap<String,String> map =new HashMap<String,String>();
			map.put("userCode", userCode);
			map.put("emis_url", emis_url);
			map.put("userId",user.get("id").toString());
			this.formatData(map);
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	
	public String getCommentStd() {
		try {
			Map params = new HashMap();
			String actnodeId = request.getParameter("cActnodeId");
			params.put("actnodeId", actnodeId);
			String commentStd=null;
			if (com.talkweb.twdpe.core.util.StringUtil.isNumber(actnodeId)) {
				params.put("version", "0");
				commentStd = taskVerifyAndComment.getCommentStd(params);
				if(commentStd==null||commentStd.equals("")){
					params.put("version", "1");
					commentStd = taskVerifyAndComment.getCommentStd(params);
				}
			}else{
				params.put("version", "1");
				commentStd = taskVerifyAndComment.getCommentStd(params);
				if(commentStd==null || commentStd.equals("")){
					params.put("version", "0");
					commentStd = taskVerifyAndComment.getCommentStd(params);
				}
			}
			
			if (commentStd != null) {
				commentStd = commentStd.replace("\n", "\r\n");
			}else{
				commentStd="";
			}
			this.formatData(commentStd);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String verifyStatusUpdate() {
		try {
			String idstr=request.getParameter("cTaskId");
			if(idstr==null||idstr.trim().length()==0){
				this.formatData("请选择未验证的任务！");
				return SUCCESS;
			}
			if(idstr.endsWith(",")){
				idstr=idstr.substring(0,idstr.length()-1);
			}
			String cTaskId[] = idstr.split(",");
			
			Session session = SessionFactory.getInstance(request, response);
			Map user = (Map) session.getAttribute("USERSESSION");
			if(user==null){
				String rs="请先登录!";
				this.formatData(rs);
				return SUCCESS;
			}
			String userid=user.get("id")+"";
			Map map = new HashMap();
			map.put("cTaskId", cTaskId);
			map.put("userid", userid);
			int count = taskVerifyAndComment.verifyStatusUpdate(map);
			String msg = "";
			if (count > 0) {
				msg = "成功提交了" + count + "条验证记录！";
			}
			this.formatData(msg);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String verifyResultUpdate() {
		try {
			Session session = SessionFactory.getInstance(request, response);
			Map user = (Map) session.getAttribute("USERSESSION");
			if(user==null){
				String rs="请先登录!";
				this.formatData(rs);
				return SUCCESS;
			}
			String userid=user.get("id")+"";
			String cTaskId = request.getParameter("cTaskId");
			String cChkResult = request.getParameter("cChkResult");
			Map map = new HashMap();
			map.put("cTaskId", cTaskId);
			map.put("cChkResult", cChkResult);
			map.put("userid", userid);
			int count = taskVerifyAndComment.verifyResultUpdate(map);
			String rs = "成功更新了" + count + "条数据！";
			if (count < 0) {
				rs = "更新失败！";
			}
			this.formatData(rs);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String commentStatusUpdate() {
		try {
			Session session = SessionFactory.getInstance(request, response);
			Map user = (Map) session.getAttribute("USERSESSION");
			if(user==null){
				String rs="请先登录!";
				this.formatData(rs);
				return SUCCESS;
			}
			String userid=user.get("id")+"";
			String cTaskId[] = request.getParameter("cTaskId").split(",");
			Map map = new HashMap();
			map.put("cTaskId", cTaskId);
			map.put("userid", userid);
			int count = taskVerifyAndComment.commentStatusUpdate(map);
			String rs = "提交失败！";
			if (count > 0) {
				rs = "成功更新了" + count + "条数据！";
			}
			this.formatData(rs);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	
	public String getEvaluateTaskCount(){
		try{
			String userid=request.getParameter("userid");
			if(userid==null){
				this.formatData("userid不能为空!");
				return SUCCESS;
			}
			int count=taskVerifyAndComment.getEvaluateTaskCount(Long.parseLong(userid));
			this.formatData(count);
		}catch(Exception e){
			e.printStackTrace();
			this.formatData(0);
		}
		return SUCCESS;
	}
	
	public String getChkTaskCount(){
		try{
			String userid=request.getParameter("userid");
			if(userid==null){
				this.formatData("userid不能为空!");
				return SUCCESS;
			}
			int count=taskVerifyAndComment.getChkTaskCount(Long.parseLong(userid));
			this.formatData(count);
		}catch(Exception e){
			e.printStackTrace();
			this.formatData(0);
		}
		return SUCCESS;
	}

	/*
	 * public String getVerifyResult(){ try{ String
	 * cTaskId=request.getParameter("cTaskId"); String
	 * cChkResult=taskVerifyAndComment.getVerifyResult(cTaskId);
	 * this.formatData(cChkResult); }catch(Exception e){
	 * logger.error(e.getMessage(),e); e.printStackTrace(); } return SUCCESS; }
	 */

	public String commentResultUpdate() {
		try {
			Session session = SessionFactory.getInstance(request, response);
			Map user = (Map) session.getAttribute("USERSESSION");
			if(user==null){
				String rs="请先登录!";
				this.formatData(rs);
				return SUCCESS;
			}
			String userid=user.get("id")+"";
			
			String cTaskId = request.getParameter("cTaskId");
			String cEvaluateResult = request.getParameter("cEvaluateResult");
			Map map = new HashMap();
			map.put("userid", userid);
			map.put("cTaskId", cTaskId);
			map.put("cEvaluateResult", cEvaluateResult);
			int count = taskVerifyAndComment.commentResultUpdate(map);
			String rs = "提交失败！";
			if (count > 0) {
				rs = "成功更新了" + count + "条数据！";
			}
			this.formatData(rs);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String   updateEmisStatus(){
		try{
		String id=request.getParameter("userId");
		if(id==null||"".equals(id)){
			this.formatData("未登录!");
			return SUCCESS;
		}
			
		Long userid=Long.parseLong(id);
		HashMap map =new HashMap();
		map.put("userid", userid);
		String taskId=request.getParameter("taskId");
		map.put("taskId", taskId);
		if(taskId==null||taskId.trim().length()==0){
			this.formatData("失败!");
			return "failed";
		}
		
		taskVerifyAndComment.updateEmisStatus(map);
		this.formatData("成功!");
		}catch(Exception e){
			e.printStackTrace();
			this.formatData("失败!");
			return "failed";
		}
		return SUCCESS;
	}
	
	public String exportComment(){
		try{
	
		Map params = new HashMap();
		Session session = SessionFactory.getInstance(request, response);
		Map user = (Map) session.getAttribute("USERSESSION");
		if(user!=null){
			String orgid=user.get("orgid").toString();
			params.put("orgid", orgid);
			String userid=user.get("id").toString();
			params.put("userid", userid);
		}else{
			String rs="请先登录!";
		   this.formatData(rs);
		   return SUCCESS;
		}
		String c_task_kind=request.getParameter("c_task_kind");
		if(c_task_kind!=null){
			params.put("c_task_kind", c_task_kind);
		}
		String year=request.getParameter("year");
		String month=request.getParameter("month");
		if(year!=null&&year.trim().length()>0){
			params.put("year", year);
		}
		 if(month!=null&&month.trim().length()>0){
            	int m=Integer.parseInt(month.trim());
            	if(m<10){
            		params.put("month", "0"+month);
            	}else{
            		params.put("month", month);
            	}
            }
		String starttime = request.getParameter("startTime");
		String endtime = request.getParameter("endTime");
		if (starttime != null && !starttime.isEmpty()) {
			params.put("cStartTime", DateUtil.getTimestamp(DateUtil
					.formatStr2Date(starttime)));
		}
		if (endtime != null && !endtime.isEmpty()) {
			params.put("cEndTime",
					DateUtil.getTimestamp(DateUtil.formatStr2Date(endtime)));
		}
		String cExecUsername;

		cExecUsername = request.getParameter("execUsername");
		params.put("cExecUsername", cExecUsername);
		String status = request.getParameter("status");
		if(status!=null && status.equals("350")){
			params.put("status", "35");
			params.put("c_ex_iemisevent", "0");
			params.put("cEvaluateResult", "NG");
		}else if(status!=null && status.equals("35")){
			params.put("status", "35");
			params.put("c_ex_iemisevent", "1");
		}
		else{
			params.put("status", status);
		}				
		String cEvaluateResult = request.getParameter("cEvaluateResult");
		if(cEvaluateResult!=null &&cEvaluateResult.trim().length()!=0){
			params.put("cEvaluateResult", cEvaluateResult);
		}				
		
		String cTaskType=request.getParameter("cTaskType");
		params.put("cTaskType", cTaskType);
		String cManageSection=request.getParameter("cManageSection");
		params.put("cManageSection", cManageSection);
		String isExpired=request.getParameter("isExpired");
		params.put("isExpired", isExpired);
		String cChkUsername=request.getParameter("cChkUsername");
		params.put("cChkUsername", cChkUsername);
		String cEvaluateUsername=request.getParameter("cEvaluateUsername");
		params.put("cEvaluateUsername", cEvaluateUsername);
        String cChkResult=request.getParameter("cChkResult");
        params.put("cChkResult", cChkResult);
		List lst =taskVerifyAndComment.getAllCommentInfo(params);
		String[] titles = {"任务名称","板块","任务类型","任务执行状态","执行人","要求完成时间","实际完成时间","执行是否逾期","验证人","验证结果","评价人","评价标准","评价"};
		String[] fields = {"cTaskName","cManageSectionName","typename","cStatusName","cExecUserInfo","cEndTime",
						   "cFactEndtime","isExpired","cChkUserInfo","cChkResult","cEvaluateUserInfo","c_review_std",
						   "c_evaluate_result_name"};
		String url = this.exportExcelData(titles, fields, "RenWuPingJia",  lst, "异常处置查询导出");
		Map map = new HashMap();
		map.put("url", url);
		this.formatData(map);
		
		}catch(Exception e){
			e.printStackTrace();
			this.formatData(null);
		}
		return SUCCESS;
	}
	
	public String exportVerify() {

		try {

			Map params = new HashMap();
			Session session = SessionFactory.getInstance(request, response);
			Map user = (Map) session.getAttribute("USERSESSION");
			System.out.println(user+"***************************************");
			
			
			if(user!=null){
				String orgid=user.get("orgid").toString();
				params.put("orgid", orgid);
				String userid=user.get("id").toString();
				params.put("userid", userid);
			}else{
				String rs="请先登录!";
			   this.formatData(rs);
			   return SUCCESS;
			}
			String c_task_kind=request.getParameter("c_task_kind");
			if(c_task_kind!=null){
				params.put("c_task_kind", c_task_kind);
			}
			String year=request.getParameter("year");
			String month=request.getParameter("month");
			if(year!=null&&year.trim().length()>0){
				params.put("year", year.trim());
			}
            if(month!=null&&month.trim().length()>0){
            	int m=Integer.parseInt(month.trim());
            	if(m<10){
            		params.put("month", "0"+month);
            	}else{
            		params.put("month", month);
            	}
            }
			String starttime = request.getParameter("startTime");
			String endtime = request.getParameter("endTime");
			if (starttime != null && !starttime.isEmpty()) {
				params.put("cStartTime", DateUtil.getTimestamp(DateUtil
						.formatStr2Date(starttime)));
			}
			if (endtime != null && !endtime.isEmpty()) {
				params.put("cEndTime",
						DateUtil.getTimestamp(DateUtil.formatStr2Date(endtime)));
			}
			String cExecUsername;
			cExecUsername = request.getParameter("execUsername");
			params.put("cExecUsername", cExecUsername);
			String status = request.getParameter("status");
			params.put("status", status);
			String cChkResult = request.getParameter("cChkResult");
			params.put("cChkResult", cChkResult);
			
			String cTaskType=request.getParameter("cTaskType");
			params.put("cTaskType", cTaskType);
			String cManageSection=request.getParameter("cManageSection");
			params.put("cManageSection", cManageSection);
			String isExpired=request.getParameter("isExpired");
			params.put("isExpired", isExpired);
			String cChkUsername=request.getParameter("cChkUsername");
			params.put("cChkUsername", cChkUsername);
			
			
			List lst =taskVerifyAndComment.getAllVerifyInfo(params);
				
			String[] titles = {"任务名称", "板块", "任务类型", "任务执行状态","执行人", "要求完成时间", "实际完成时间", "执行是否逾期", "验证人","验证标准","验证","验证描述"};
			String[] fields = {"cTaskName","cManageSectionName","typename","cStatusName","cExecUsername","cEndTime","cFactEndtime","isExpired","cChkUserInfo","c_check_std","c_chk_result_name","c_chk_desc"};
			String url = this.exportExcelData(titles, fields, "RenWuPingJia",  lst, "任务验证导出");
			Map map = new HashMap();
			map.put("url", url);
			this.formatData(map);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String getChkitemStd(){
		try{
			Session session = SessionFactory.getInstance(request, response);
			Map user = (Map) session.getAttribute("USERSESSION");
			if(user==null){
				this.formatData("未登录!");
				return SUCCESS;
			}
			String idstr=request.getParameter("c_actnode_id");
			if(idstr==null||idstr.trim().length()==0){
				this.formatData("[]");
				return SUCCESS;
			}
			List list=taskVerifyAndComment.getChkitemStd(idstr);
			this.formatData(list);
		}catch(Exception e){
			e.printStackTrace();
			this.formatData("[]");
		}
		return SUCCESS;
	}
}
