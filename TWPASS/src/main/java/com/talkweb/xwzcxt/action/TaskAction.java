package com.talkweb.xwzcxt.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.twdpe.base.org.pojo.User;
import com.talkweb.twdpe.base.org.service.UserService;
import com.talkweb.twdpe.core.util.DateUtil;
import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.twdpe.web.jcontrols.RuleSelect;
import com.talkweb.twdpe.web.session.main.Session;
import com.talkweb.twdpe.web.session.util.SessionFactory;
import com.talkweb.xwzcxt.pojo.GeneralItem;
import com.talkweb.xwzcxt.service.DpPositionServiceI;
import com.talkweb.xwzcxt.service.TaskServiceI;
import com.talkweb.xwzcxt.service.impl.XwzcxtMngImp;
import com.talkweb.xwzcxt.util.GetRoleUtil;
import com.talkweb.xwzcxt.vo.TTaskVo;

public class TaskAction extends baseAction {
	@Autowired
	private TaskServiceI taskService;

	@Autowired
	private DpPositionServiceI dpPositionService;
	
	@Autowired
	private GetRoleUtil getRoleUtil;

	@Autowired
    private UserService userService;
	
	@Autowired
	private XwzcxtMngImp xwzcxtMngImp;

	public String getTaskByID() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String cTaskId = request.getParameter("cTaskId");
		if (cTaskId != null && !cTaskId.isEmpty()) {
			TTaskVo vo = taskService.getTaskByID(cTaskId);
			if (vo != null) {
				this.formatData(vo);
			} else {
				return ERROR;
			}
		}
		return SUCCESS;
	}

	private Map getQueryParams() {
		HttpServletRequest request = ServletActionContext.getRequest();
		Map params = new HashMap();
		params.put("cTaskName", request.getParameter("cTaskName"));
		params.put("errortask", request.getParameter("errortask"));//是否为任务异常
		params.put("checkerror", request.getParameter("checkerror"));//验证异常
		params.put("type", request.getParameter("type"));
		params.put("userCode", request.getParameter("userCode"));
		params.put("commenterror", request.getParameter("commenterror"));
		params.put("extype", request.getParameter("extype"));
		String cExecUserid = request.getParameter("cExecUserid");
		if (cExecUserid != null && !cExecUserid.isEmpty()) {
			cExecUserid = cExecUserid.replaceAll("U-", "");
		} else {
			cExecUserid = null;
		}
		params.put("cExecUserid", cExecUserid);
		params.put("cTaskKind", request.getParameter("cTaskKind"));
		params.put("cTaskType", request.getParameter("cTaskType"));
		params.put("cStatus", request.getParameter("cStatus"));
		params.put("cAreaId",request.getParameter("cAreaId"));
		params.put("cManageSection", request.getParameter("cManageSection"));
		String starttime = request.getParameter("cStartTime");
		String endtime = request.getParameter("cEndTime");
		if (starttime != null && !starttime.isEmpty()) {
			starttime = starttime + " 00:00:00";
			params.put("cStartTime", DateUtil.getTimestamp(DateUtil.formatStr2Date(starttime)));
		}
		if (endtime != null && !endtime.isEmpty()) {
			endtime = endtime + " 23:59:59";
			params.put("cEndTime", DateUtil.getTimestamp(DateUtil.formatStr2Date(endtime)));
		}
		params.put("cTimeStatus", request.getParameter("cTimeStatus"));
		return params;
	}

	public String getAllTask() {
//		HttpServletRequest request = ServletActionContext.getRequest();
//		HttpServletResponse response = ServletActionContext.getResponse();
		Map params = this.getQueryParams();
		String type = params.get("type")==null?"":params.get("type").toString();
//		if(!extype.equals("1")){
//			Session session = SessionFactory.getInstance(request, response);
//			Map user = (Map) session.getAttribute("USERSESSION");
//			String finalRuleCode = getRoleUtil.getFinalRole("RWCX","view");//数据级权限控制
//			String orgId = user.get("orgid").toString();
//			params.put("ruleCode",finalRuleCode);
//			params.put("orgid", orgId);
//			params.put("userCode", user.get("code"));
//			if (finalRuleCode.equals("ITMinister")) {
//				orgId = getRoleUtil.getITMinisterOrgId(orgId);
//			}
//		}
//		String checklate = params.get("checklate")==null?"":params.get("checklate").toString();
		String commenterror = params.get("commenterror")==null?"":params.get("commenterror").toString();
		String errortask = params.get("errortask")==null?"":params.get("errortask").toString();
		String extype = params.get("extype")==null?"":params.get("extype").toString();
		String checkerror = params.get("checkerror")==null?"":params.get("checkerror").toString();
		String userCode = params.get("userCode")==null?"":params.get("userCode").toString();
		   if(errortask.equals("yes")){//任务异常
			  if(userCode.equals("")){
				  HttpServletRequest request = ServletActionContext.getRequest();
					HttpServletResponse response = ServletActionContext.getResponse();
					Session session = SessionFactory.getInstance(request, response);
					Map user = (Map) session.getAttribute("USERSESSION");
					userCode = user.get("code").toString();
					params.put("userCode",userCode);
			  }
			   if(type.equals("1")){//推板任务异常数据获取
				   Map map = new HashMap();
					pagination = new Pagination(1, 20);
					pagination.setOnlyCount(true);
//					params.put("cTimeStatus", 1);
					Pagination page = taskService.getTasks(params, pagination);
					map.put("rwyq", page.getCount());
					this.formatData(map);
					return SUCCESS;
			   }
			}
		 if(type.equals("1")&&!errortask.equals("yes")){//任务执行逾期数据获取
				Map map = new HashMap();
				pagination = new Pagination(1, 20);
				pagination.setOnlyCount(true);
				params.put("cTimeStatus", 1);//逾期标识
				Pagination page = taskService.getTasks(params, pagination);
				map.put("rwzxyq", page.getCount());
				this.formatData(map);
				return SUCCESS;
			   }
		 if(type.equals("2")){//验证逾期数据获取
			 Map map = new HashMap();
				pagination = new Pagination(1, 20);
				pagination.setOnlyCount(true);
				params.put("cTimeStatus", 2);//逾期标识
				Pagination page = taskService.getTasks(params, pagination);
				map.put("rwyzyq", page.getCount());
				this.formatData(map);
				return SUCCESS;
		  }
		 if(type.equals("3")){//验证异常数据获取
			 Map map = new HashMap();
				pagination = new Pagination(1, 20);
				pagination.setOnlyCount(true);
				params.put("checkerror", "yes");
				HttpServletRequest request = ServletActionContext.getRequest();
				HttpServletResponse response = ServletActionContext.getResponse();
				Session session = SessionFactory.getInstance(request, response);
				Map user = (Map) session.getAttribute("USERSESSION");
				userCode = user.get("code").toString();
				params.put("userCode",userCode);
				Pagination page = taskService.getTasks(params, pagination);
				map.put("rwyzyc", page.getCount());
				this.formatData(map);
				return SUCCESS;
		 }
		 if(commenterror.equals("yes")){//评价异常页面数据获取
			 if (pagination == null) {
					pagination = new Pagination(1, 20);
				}
			 if(userCode.equals("")){
				 HttpServletRequest request = ServletActionContext.getRequest();
					HttpServletResponse response = ServletActionContext.getResponse();
					Session session = SessionFactory.getInstance(request, response);
					Map user = (Map) session.getAttribute("USERSESSION");
					userCode = user.get("code").toString();
					params.put("userCode",userCode);
			 }
				Pagination page = taskService.getTasks(params , pagination);
				this.formatDatagirdData(page.getList(), page);
				return SUCCESS;
		 }
		 if(type.equals("4")){//评价逾期数据获取
			 Map map = new HashMap();
				pagination = new Pagination(1, 20);
				pagination.setOnlyCount(true);
				params.put("cTimeStatus", 3);//逾期标识
				Pagination page = taskService.getTasks(params, pagination);
				map.put("rwpjyq", page.getCount());
				this.formatData(map);
				return SUCCESS;
		 }
		 if(type.equals("5")){//评价异常推板数据获取
			 Map map = new HashMap();
				pagination = new Pagination(1, 20);
				pagination.setOnlyCount(true);
				params.put("commenterror", "yes");
				HttpServletRequest request = ServletActionContext.getRequest();
				HttpServletResponse response = ServletActionContext.getResponse();
				Session session = SessionFactory.getInstance(request, response);
				Map user = (Map) session.getAttribute("USERSESSION");
				userCode = user.get("code").toString();
				params.put("userCode",userCode);
				Pagination page = taskService.getTasks(params, pagination);
				map.put("rwpjyc", page.getCount());
				this.formatData(map);
				return SUCCESS;
		 }
		 else if(extype.equals("1")&&checkerror.equals("yes")){
			 if(userCode.equals("")){
			 HttpServletRequest request = ServletActionContext.getRequest();
				HttpServletResponse response = ServletActionContext.getResponse();
				Session session = SessionFactory.getInstance(request, response);
				Map user = (Map) session.getAttribute("USERSESSION");
				 userCode = user.get("code").toString();
				params.put("userCode",userCode);
				if (pagination == null) {
					pagination = new Pagination(1, 20);
				}
				Pagination page = taskService.getTasks(params , pagination);
				this.formatDatagirdData(page.getList(), page);
				return SUCCESS;
			 }
		 }
		   else{
		if (pagination == null) {
			pagination = new Pagination(1, 20);
		}
		Pagination page = taskService.getTasks(params , pagination);
		this.formatDatagirdData(page.getList(), page);
			}
		
		return SUCCESS;
	}

	public String getAllTaskForMobile() {
		HttpServletRequest request = ServletActionContext.getRequest();
		Map params = new HashMap();
		params.put("cUserId", request.getParameter("userId"));
		params.put("cTaskKind", request.getParameter("type"));
		Pagination pagination = new Pagination(Integer.parseInt(request.getParameter("index")), Integer.parseInt(request.getParameter("page")));
		Pagination page = taskService.getTasks(params, pagination);
		this.formatDatagirdData(page.getList(), page);
		return SUCCESS;
	}

	public String updateMatterdetail() {
	
		HttpServletRequest request = ServletActionContext.getRequest();
		try{
		Map params = new HashMap();
		String taskId = request.getParameter("taskId");
		String userCode = request.getParameter("userCode");
		String type = request.getParameter("type");
		params.put("taskId", taskId);
		params.put("type", type);
		List<User> loginUsersByUserCode = userService.getLoginUsersByUserCode(userCode);
		params.put("userId", loginUsersByUserCode.get(0).getUserId());
		boolean b = taskService.updatePanelMatterdetail(params);
		}catch(Exception e){
			e.printStackTrace();
		}
//		this.formatDatagirdData(page.getList(), page);
		return SUCCESS;
	}
	
	public String getPositionNameByUserid() {
		String name = taskService.getPositionNameByUserid("1001004");
		this.formatData(name);
		return SUCCESS;
	}

	public String exportTaskExcel() {
		Map params = this.getQueryParams();
		List lst = taskService.getTasksWithoutPage(params);
		String[] titles = {"任务名称", "任务编号", "任务类型", "任务类别", "所属板块", "PDCA", "执行区域", "触发方式", "是否关键管控", "是否顺序执行", "任务创建人", "任务下达人", "任务状态", "执行人", "系统发送任务时间", "任务开始时间", "任务结束时间", "任务实际接收时间", "任务实际完成时间", "验证人", "验证期限", "验证实际完成时间", "评价人", "评价期限", "评价实际完成时间", "5W2H类别", "执行标准/管理要求", "异常怎么处置（执行环节）/异常怎么处置", "验证标准（如何核查）/验证标准（如何复核）", "异常怎么处置（验证环节）（作业类特有）", "评价标准（作业类特有）", "管理/技术标准参考（管理类特有）", "考核标准参考（管理类特有）", "做什么/管什么", "执行结果上传要求/完工上传要求", "验证结果上传要求/验证结果上传要求"};
		String[] fields = {"C_TASK_NAME", "C_TASK_ID", "C_TASK_KIND_NAME", "C_TASK_TYPENAME", "C_MANAGE_SECTION_NAME", "C_PDCA", "C_AREANAME", "C_TRIGGER_TYPE_NAME", "C_ISKEYCTRL_NAME", "C_ISSEQUENCE_NAME", "C_CREATE_USERNAME", "C_CONFIRM_USERNAME", "C_STATUS_NAME", "C_EXEC_USERNAME", "C_CONFIRM_TIME", "C_START_TIME", "C_END_TIME", "C_DOWN_TIME", "C_FACT_ENDTIME", "C_CHK_USERNAME", "C_CHK_PLANTIME", "C_CHK_ENDTIME", "C_EVALUATE_USERNAME", "C_EVALUATE_PLANTIME", "C_EVALUATE_TIME", "C_ACTNODETYPE", "C_STD_EXEC", "C_ERR_EXEC", "C_STD_CHECK", "C_ERR_CHECK", "C_STD_REVIEW", "C_MANAGESTD", "C_EXAMSTD", "C_ACTITEMNAME_NAME", "C_GETDATATYPENAME", "C_CHECKDATATYPENAME"};
		String url = this.exportExcelData(titles, fields, "TaskQuery", lst, "任务执行查询导出");
		Map map = new HashMap();
		map.put("url", url);
		this.formatData(map);
		return SUCCESS;
	}

	public String deleteTaskById() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String id = request.getParameter("id");
        taskService.deleteTaskById(id);
        Map<String, String> pageData = new HashMap<String, String>();
        pageData.put("msg", "删除成功");
        pageData.put("status", "ok");
        this.formatData(pageData);
        return SUCCESS;
    }

	public String getTaskTypeList() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String cTaskKind = request.getParameter("cTaskKind");
		if (null == cTaskKind) {
			this.formatData("");
		} else {
			try {
				List<GeneralItem> data = (List<GeneralItem>) xwzcxtMngImp.getTaskTypeView();
				if (data != null && data.size() > 0) {
					if (cTaskKind.equals("1")) {
						for (int i = 0; i < data.size(); i++) {
							GeneralItem item = data.get(i);
							if (item.getC_id().equals("20")) {
								data.remove(i--);
							}
						}
					} else if (cTaskKind.equals("3")) {
						for (int i = 0; i < data.size(); i++) {
							GeneralItem item = data.get(i);
							if (item.getC_id().equals("1") || item.getC_id().equals("2") || item.getC_id().equals("3") || item.getC_id().equals("5") || item.getC_id().equals("6")) {
								data.remove(i--);
							}
						}
					}
					RuleSelect wtree = new RuleSelect();
					wtree.setValue("c_id");
					wtree.setText("c_name");
					List<Map> l = this.initSelectData(data, wtree);
					this.formatData(l);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return SUCCESS;
	}
}