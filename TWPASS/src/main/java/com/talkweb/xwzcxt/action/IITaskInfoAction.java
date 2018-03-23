package com.talkweb.xwzcxt.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.common.AppConstants;
import com.talkweb.twdpe.core.log.Logger;
import com.talkweb.twdpe.core.log.LoggerFactory;
import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.twdpe.web.jcontrols.RuleTree;
import com.talkweb.twdpe.web.session.main.Session;
import com.talkweb.twdpe.web.session.util.SessionFactory;
import com.talkweb.twdpe.webcomponent.common.action.BusinessAction;
import com.talkweb.xwzcxt.pojo.IITaskInfoPojo;
import com.talkweb.xwzcxt.pojo.SdActionPojo;
import com.talkweb.xwzcxt.service.IITaskInfoService;


/**
 * TODO(描述这个类的作用)
 * 
 * @author: 2014-8-2，GuveXie，（描述修改内容）
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class IITaskInfoAction extends BusinessAction {

	private static final long serialVersionUID = -4099987109384481259L;

	private static final Logger logger = LoggerFactory.getLogger(IITaskInfoAction.class);

	@Autowired
	AppConstants appConstants;
	
	@Autowired 
	private IITaskInfoService iiTaskinfoService;
	
	public String test(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String str = request.getParameter("info");
		this.formatData(str);
		return SUCCESS;
	}

	/******获取任务信息列表--所有任务******/
	public String getTaskInfoListByMap() 
	{
		HttpServletRequest request = ServletActionContext.getRequest();
		try
		{
			Map params = new HashMap();
			params.put("c_task_id", request.getParameter("c_task_id"));
			params.put("c_task_name", request.getParameter("c_task_name"));
			params.put("c_status", request.getParameter("c_status"));
			params.put("c_exec_username", request.getParameter("c_exec_username"));
			params.put("c_exec_userid", request.getParameter("c_exec_userid"));
			params.put("c_positionid", request.getParameter("c_positionid"));
			Pagination respagination = iiTaskinfoService.getTaskinfoList(params, pagination);
			List taskinfoList = respagination.getList();
			this.formatDatagirdData(taskinfoList, respagination);
	        logger.debug("IITaskInfo", "getTaskInfoListByMap.Action Exec Ok!");
			return SUCCESS;
		}catch(Exception err){
	        logger.debug("IITaskInfo", "getTaskInfoListByMap.Action Exec Fail!");
			return ERROR;
		}
	}
	
	/******获取任务信息列表--登录人所属组织内的任务******/
	public String getTaskInfoListByMapAndOrgid() 
	{
		HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        Session session = SessionFactory.getInstance(request, response);
        try {
            Map<String, Object> userMap = (Map) session.getAttribute("USERSESSION"); 
            if (userMap == null) {
                this.setErrorMessage("用户Session已过期");
            }
            else {
                String orgid = userMap.get("orgid").toString(); //获取登录人的组织ID
    			Map params = new HashMap();
    			params.put("c_task_name", request.getParameter("c_task_name"));
    			params.put("c_task_id", request.getParameter("c_task_id"));
    			params.put("c_task_name", request.getParameter("c_task_name"));
    			params.put("c_status", request.getParameter("c_status"));
    			params.put("c_exec_username", request.getParameter("c_exec_username"));
    			params.put("c_exec_userid", request.getParameter("c_exec_userid"));
    			params.put("c_positionid", request.getParameter("c_positionid"));
    			params.put("c_orgid", orgid);
    			Pagination respagination = iiTaskinfoService.getTaskinfoList(params, pagination);
    			List taskinfoList = respagination.getList();
    			this.formatDatagirdData(taskinfoList, respagination);
    	        logger.debug("IITaskInfo", "getApprovingTaskInfoListByMap.Action Exec Ok!");
            }
			return SUCCESS;
        }catch(Exception e){
        	e.printStackTrace();
	        logger.debug("IITaskInfo", "getTaskInfoListByMap.Action Exec Fail!");
			return ERROR;
        }
	}

	/******获取任务信息 By c_task_id******/
	public String getTaskInfoByTaskId() 
	{
		HttpServletRequest request = ServletActionContext.getRequest();
		Map params = new HashMap();
		params.put("c_task_id", request.getParameter("c_task_id"));
        IITaskInfoPojo data = iiTaskinfoService.getTaskinfoList(params);
        try {
			this.formatData (this.addPrefix (data, "taskinfo."));
		} catch (Exception e) {
            request.setAttribute ("throw", e);
            // 如果出错，则输出一个空的JSON对象
            this.setErrorMessage (e);
            logger.error (e.getMessage (), e);
		}// 给对象属性增加统一的前缀名，并格化输出
    	return SUCCESS;
	}

	/******获取任务流程步骤信息******/
	public String getTaskInfoItemListByMap() 
	{
		HttpServletRequest request = ServletActionContext.getRequest();
		try
		{
			Map params = new HashMap();
			params.put("c_stepitem_id", request.getParameter("c_stepitem_id"));
			params.put("c_task_id", request.getParameter("c_task_id"));
			params.put("c_actitem_index", request.getParameter("c_actitem_index"));
			params.put("c_actitem_id", request.getParameter("c_actitem_id"));
			List taskinfoItemList = iiTaskinfoService.getTaskinfoItemList(params);
			this.formatData(taskinfoItemList);
	        logger.debug("IITaskInfo", "getTaskInfoItemListByMap.Action Exec Ok!");
			return SUCCESS;
		}catch(Exception err){
	        logger.debug("IITaskInfo", "getTaskInfoItemListByMap.Action Exec Fail!");
			return ERROR;
		}
	}
	
	/******获取任务结果步骤信息******/
	public String getTaskStepResultListByMap() 
	{
		HttpServletRequest request = ServletActionContext.getRequest();
		try
		{
			Map params = new HashMap();
			params.put("c_result_id", request.getParameter("c_result_id"));
			params.put("c_task_id", request.getParameter("c_task_id"));
			params.put("c_actitem_index", request.getParameter("c_actitem_index"));
			params.put("c_actitem_id", request.getParameter("c_actitem_id"));
			params.put("c_step_index", request.getParameter("c_step_index"));
			List taskstepresult = iiTaskinfoService.getTaskStepResultList(params);
			this.formatData(taskstepresult);
	        logger.debug("IITaskInfo", "getTaskStepResultListByMap.Action Exec Ok!");
			return SUCCESS;
		}catch(Exception err){
	        logger.debug("IITaskInfo", "getTaskStepResultListByMap.Action Exec Fail!");
			return ERROR;
		}
	}
	
	/******获取任务结果步骤信息******/
	public String execTaskinfoConfirm() 
	{
		HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        Session session = SessionFactory.getInstance(request, response);
        try {
            Map<String, Object> userMap = (Map) session.getAttribute("USERSESSION"); 
            if (userMap == null) {
                this.setErrorMessage("用户Session已过期");
            }
            else {
				Map params = new HashMap();
                String c_confirm_userid = userMap.get("id").toString(); //获取登录人ID
                
				params.put("v_TASK_ID", request.getParameter("c_task_id"));
				params.put("v_CONFIRM_USERID", c_confirm_userid);
				params.put("v_EXEC_USERID", request.getParameter("c_exec_userid"));
				params.put("v_START_TIME", request.getParameter("c_start_time"));
				params.put("v_END_TIME", request.getParameter("c_end_time"));
				params.put("v_PLANDOWN_TIME", request.getParameter("c_plandown_time"));
				params.put("v_AREA_ID", request.getParameter("c_area_id"));
				
				int effectRowCount = iiTaskinfoService.execTaskinfoConfirm(params);
				this.setMessage(1, "TaskInfo", "UPDATE");  //this.setMessage(effectRowCount, "TaskInfo", "UPDATE"); 
				logger.debug("IITaskInfo", "execTaskinfoConfirm.Action Exec Ok!"+effectRowCount);
            }
			return SUCCESS;
		}catch(Exception err){
	        logger.debug("IITaskInfo", "execTaskinfoConfirm.Action Exec Fail!");
			return ERROR;
		}
	}

}
