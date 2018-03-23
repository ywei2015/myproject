package com.talkweb.xwzcxt.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import sun.security.krb5.internal.PAData;

import com.talkweb.twdpe.core.util.DateUtil;
import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.twdpe.web.session.main.Session;
import com.talkweb.twdpe.web.session.util.SessionFactory;
import com.talkweb.xwzcxt.service.TaskErrorServiceI;
import com.talkweb.xwzcxt.util.GetRoleUtil;

public class TaskErrorAction extends baseAction {
	@Autowired
	private TaskErrorServiceI taskErrorService;
	@Autowired
	private GetRoleUtil getRoleUtil;

	private Map getQueryParams() {
		HttpServletRequest request = ServletActionContext.getRequest();
		Map params = new HashMap();
		params.put("cErrName", request.getParameter("cErrName"));
		String cWriter = request.getParameter("cWriter");
		if (cWriter != null && !cWriter.isEmpty()) {
			cWriter = cWriter.replaceAll("U-", "");
		} else {
			cWriter = null;
		}
		params.put("cWriter", cWriter);
		params.put("userCode", request.getParameter("userCode"));
		params.put("extype", request.getParameter("extype"));
		params.put("type", request.getParameter("type"));
		params.put("cIsclose", request.getParameter("cIsclose"));
		params.put("cErrKind", request.getParameter("cErrKind"));
		params.put("cManageSection", request.getParameter("cManageSection"));
		String cOccurTime = request.getParameter("cOccurTime");
		String cSuggestendTime = request.getParameter("cSuggestendTime");
		String cCloseTime = request.getParameter("cCloseTime");
		if (cOccurTime != null && !cOccurTime.isEmpty()) {
			cOccurTime = cOccurTime + " 00:00:00";
			params.put("cOccurTime", DateUtil.getTimestamp(DateUtil.formatStr2Date(cOccurTime)));
		}
		if (cSuggestendTime != null && !cSuggestendTime.isEmpty()) {
			cSuggestendTime = cSuggestendTime + " 23:59:59";
			params.put("cSuggestendTime", DateUtil.getTimestamp(DateUtil.formatStr2Date(cSuggestendTime)));
		}
		if (cCloseTime != null && !cCloseTime.isEmpty()) {
			cCloseTime = cCloseTime + " 23:59:59";
			params.put("cCloseTime", DateUtil.getTimestamp(DateUtil.formatStr2Date(cCloseTime)));
		}
		return params;
	}

	public String getAllTaskErrorInfo() {
		try{
//		HttpServletRequest request = ServletActionContext.getRequest();
//		HttpServletResponse response = ServletActionContext.getResponse();
//		Session session = SessionFactory.getInstance(request, response);
//		Map user = (Map) session.getAttribute("USERSESSION");
		Map params = this.getQueryParams();
		String type = params.get("type")==null?"":params.get("type").toString();
		String extype = params.get("extype")==null?"":params.get("extype").toString();
//		String finalRuleCode = getRoleUtil.getFinalRole("ycczcx","view");
//		String orgId = user.get("orgid").toString();
//		if (finalRuleCode.equals("ITMinister")) {
//			orgId = getRoleUtil.getITMinisterOrgId(orgId);
//		}
//		params.put("userCode", user.get("code"));
//		params.put("ruleCode",finalRuleCode);
//		params.put("orgId", orgId);
		
		if (pagination == null) {
			pagination = new Pagination(1, 20);
		}
		Pagination page = taskErrorService.getAllTaskErrorInfo(params, pagination);
		this.formatDatagirdData(page.getList(), page);
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String getAllTaskErrorInfoForMobile() {
		HttpServletRequest request = ServletActionContext.getRequest();
		Map params = new HashMap();
		params.put("cUserId", request.getParameter("userId"));
		Pagination pagination = new Pagination(Integer.parseInt(request.getParameter("index")), Integer.parseInt(request.getParameter("page")));
		Pagination page = taskErrorService.getAllTaskErrorInfo(params, pagination);
		this.formatDatagirdData(page.getList(), page);
		return SUCCESS;
	}

	public String exportTaskErrorInfoExcel() {
		Map params = this.getQueryParams();
		List lst = taskErrorService.getAllTaskErrorInfoWithoutPage(params);
		String[] titles = {"异常主题", "板块", "异常反馈人", "异常类型", "异常状态", "异常发生时间", "异常处理期限", "异常处理完成时间", "关联任务", "发生区域"};
		String[] fields = {"C_ERR_NAME", "C_MANAGE_SECTION_NAME", "C_WRITER_NAME", "C_ERR_KIND_NAME", "C_ISCLOSE_NAME", "C_OCCUR_TIME", "C_SUGGESTEND_TIME", "C_CLOSE_TIME", "C_TASK_NAME", "C_AREA_NAME"};
		String url = this.exportExcelData(titles, fields, "TaskErrorQuery",  lst, "异常处置查询导出");
		Map map = new HashMap();
		map.put("url", url);
		this.formatData(map);
		return SUCCESS;
	}

	public String getTaskErrorInfoDetailByID() {
		HttpServletRequest request = ServletActionContext.getRequest();
		this.formatData(taskErrorService.getTaskErrorInfoDetailByID(request.getParameter("cErrId")));
		return SUCCESS;
	}

	public String deleteTaskErrorById() {
		HttpServletRequest request = ServletActionContext.getRequest();
		taskErrorService.deleteTaskErrorById(request.getParameter("id"));
		Map<String, String> pageData = new HashMap<String, String>();
		pageData.put("msg", "删除成功");
		pageData.put("status", "ok");
		this.formatData(pageData);
		return SUCCESS;
	}

	public String getTaskErrorFeedbackDetailByID() {
		HttpServletRequest request = ServletActionContext.getRequest();
		this.formatData(taskErrorService.getTaskErrorFeedbackDetailByID(request.getParameter("cErrId")));
		return SUCCESS;
	}
}