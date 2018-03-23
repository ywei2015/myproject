package com.talkweb.xwzcxt.dal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.talkweb.xwzcxt.pojo.Message;
import com.talkweb.xwzcxt.pojo.StdFileAffixPojo;
import com.talkweb.xwzcxt.pojo.TaskErrorFeedback;
import com.talkweb.xwzcxt.pojo.TaskMouldPojo;
import com.talkweb.xwzcxt.pojo.BasicMouldPojo;
import com.talkweb.xwzcxt.pojo.TaskStatusPojo;
import com.talkweb.xwzcxt.pojo.TaskStepResultPojo;
import com.talkweb.xwzcxt.action.Test;
import com.talkweb.xwzcxt.common.XwzcxtCommon.Enum_Xwzcxt;
import com.talkweb.twdpe.base.org.pojo.User;
import com.talkweb.twdpe.core.data.DataMap;
import com.talkweb.twdpe.core.data.IData;
import com.talkweb.twdpe.core.log.Logger;
import com.talkweb.twdpe.core.log.LoggerFactory;
import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.twdpe.dal.batis.spring.SessionDaoSupport;

public class XwzcxtMngDal extends SessionDaoSupport{
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(XwzcxtMngDal.class);

    // / <summary>
	// / 数据库删除数据
	// / </summary>
	// / <returns>执行结果</returns>
	public int deleteStepByCid (String deleteID){
		return this.getSession().delete("task.deleteStepByCid", deleteID);
	}
	
	public int cancelTaskById (String deleteID){
		return this.getSession().delete("task.cancelTaskById", deleteID);
	}
	
	public boolean DeleteData (String action, Object param) {		
		return this.getSession().delete(action, param) >0 ? true:false;    
	}
	
	public Boolean ExecuteInsert (String action, Object param) {			 
		return this.getSession().insert(action, param) >0 ? true:false;	 
	}
	
	public Boolean ExecuteUpdate (String action, Object param) {			
		return this.getSession().update(action, param) >0 ? true:false;
	}
	
	public List<?> GetCurrentRecordByPage(Pagination pagination, Object param, Enum_Xwzcxt curPro) {
		String action = "basic.getBySingleTablePage";
		 BasicMouldPojo basicMouldPojo = null;
		 TaskMouldPojo taskMouldPojo = null;
		switch (curPro) {
		    case TASK_MOULD:
		        action = "task.getTaskMould";
		        break;
		    case TASK_TEMPLET:
		        action = "task.queryTempletItem";
		        break;
            case TASK_TEMPLET_CHILD:
                action = "task.queryTempletChildItem";
                break;
            case TASK_INFO_LIST:
                action = "task.queryTaskInfoList";
                break;
            case TASK_STEP_LIST:
                action = "task.queryTaskStepList";
                break;
                
            case TASK_BASIC_JOB:  
                action = "basic.getByJobObjPage";   
                basicMouldPojo = (BasicMouldPojo)param;
                param = basicMouldPojo;
                break;
            case TASK_BASIC_CAL:
                action = "basic.getByCalPage";
                basicMouldPojo = (BasicMouldPojo)param;
                param = basicMouldPojo;
                break;
            case TASK_BASIC_TYPE:
                basicMouldPojo = (BasicMouldPojo)param;
                param = basicMouldPojo;
                break;
            case TASK_BASIC_SCHEDULE:
                basicMouldPojo = (BasicMouldPojo)param;
                basicMouldPojo.setTablename("t_worktime");
                param = basicMouldPojo;
                break;
            case TASK_STANDARD_POINT:
                action = "basic.getByPointPage";
                basicMouldPojo = (BasicMouldPojo)param;
                param = basicMouldPojo;
                break; 
            case TASK_STANDARD_TYPE:
                basicMouldPojo = (BasicMouldPojo)param;
                basicMouldPojo.setTablename("t_work_object");
                param = basicMouldPojo;
                break; 
            case TASK_STANDARD_STANDARD:
                action = "basic.getByStandardPage";   
                basicMouldPojo = (BasicMouldPojo)param;
                param = basicMouldPojo;
                break;            
            case TASK_RULE_MOULD:
                action = "task.getByModulePage"; 
                taskMouldPojo = (TaskMouldPojo)param;
                param = taskMouldPojo;
                break;
            case TASK_RULE_MOULD_ITEM:
                action = "task.getByModuleDetailsPage";   
                taskMouldPojo = (TaskMouldPojo)param;
                param = taskMouldPojo;
                break; 
            case TASK_RULE_TIME:
                basicMouldPojo = (BasicMouldPojo)param;
                basicMouldPojo.setTablename("t_timerule");
                basicMouldPojo.setC_isdelete("0");
                param = basicMouldPojo;
                break;  
            //Add By Rita.Zhou for My Task
            case TASK_READY_TASK:
            	action = "task.getReadyTaskByUserId";   
            	break;
            case TASK_DONE_TASK:
            	action = "task.getDoneTaskByUserId";   
            	break;
            case TASK_ERROR_TRACE:
            	String type = (String) ((Map)param).get("type");
            	action = "task.getOwnErrTask";
            	if (type != null){
	            	if (type.equals("2")){
	            		action = "task.getOwnExecErrTask";
	            	}else if (type.equals("3")){
	            		action = "task.getSendErrTask";
	            	}
            	}
            	break;
		}
		return this.getSession().selectList(action, param, pagination);
	}
	

	@SuppressWarnings("unchecked")
	public List<TaskMouldPojo> queryTaskInfoListByCondition(TaskMouldPojo taskMouldPojo,Pagination pagination){
		if(pagination==null||pagination.getSize()<=0||pagination.getCurrPage()<=0){
			return this.getSession().selectList("task.queryTaskInfoListByCondition", taskMouldPojo);
		}
	
		return this.getSession().selectList("task.queryTaskInfoListByCondition", taskMouldPojo,pagination);
	}
	
	public List queryNewTask(Map map,List list,Pagination pagination){
		 list=this.getSession().selectList("task.queryNewTask",map,pagination);
		 return list;
	}
	
    public List<?> GetCurrentRecordList(Object param, Enum_Xwzcxt curPro) {
        String action = "";
        switch (curPro) {       
            case TASK_MOULD_DETAILS:
                action = "task.getTaskMouldDetails";
                break;
            case TASK_TEMPLET_DETAILS:
                action = "task.getTempletItemDetail";
                break;
            case TASK_INFO_LIST:
                action = "task.queryTaskInfoList";
                break;
            case TASK_STEP_LIST:
                action = "task.queryTaskStepList";
                break;
            case TASK_RULE_MOULD_ITEM:
                action = "task.getByModuleDetailsPage";
                break; 
            case TASK_BASIC_JOB:
                action = "basic.";
                break;
            case TASK_RULE_MOULD_STEP:
                action = "task.getTaskStepList";
                break;
        }
        return this.getSession().selectList(action, param);
    }
    
    public List<?> GetCurrentTreeList(String query,int actionEnum) {
        IData param = new DataMap();
        
        String action = "basic.getBySingleTablePage";
        switch (actionEnum) {
            //岗位末节点
            case 1:
                param.put("c_isdelete", "0");
                param.put("tablename", "t_post_actnode");
                break;
            //岗位
            case 2:
                param.put("tablename", "dp_position");
                break;
            //区域
            case 3:
                param.put("tablename", "dp_area");
                break;
            //时间规则
            case 4:
                param.put("c_isdelete", "0");
                param.put("tablename", "t_timerule");
                break;
            //任务列表
            case 5:
                param.put("c_isdelete", "0");
                param.put("tablename", "t_task_templet");
                break;
            //标准数据项
            case 6:
                if(query !=null){
                    param.put("c_actnode_id", query);                    
                }
                param.put("c_isdelete", "0");
                param.put("tablename", "t_actnode_item");
                break;
            //根据模版活动末节点查询标准数据项
            case 7:
                if(query !=null){
                    param.put("c_tasktemplet_id", query);                    
                }
                param.put("c_isdelete", "0");
                param.put("tablename", "t_actnode_item");
                break;
        }
        return this.getSession().selectList(action, param);
    }
    
	public Object GetCurrentRecord(Object param, Enum_Xwzcxt curPro) {
		String action = "";
		switch (curPro) {		
		    case TASK_MOULD_DETAILS:
                action = "task.getTaskMouldDetails";
		        break;
		    case TASK_TEMPLET_DETAILS:
                action = "task.getTempletItemDetail";
                break;
		    case TASK_INFO_DETAIL:
                action = "task.getTaskInfoDetail";
                break;
		    case TASK_STEP_DETAIL:
                action = "task.getTaskStepDetail";
                break;           
            case TASK_RULE_MOULD:
                action = "task.getByModulePage";
                break;
		}
		return this.getSession().selectOne(action, param);
	}
	
	public Object ExecutePro(String action,Object param){
        return this.getSession().selectOne(action, param);
	}
	
	public Object GetSeq(Enum_Xwzcxt curPro) {
        IData data = new DataMap();
        String action = "task.getSeq";
        switch (curPro) {       
            case TASK_RULE_MOULD:
                data.put("seq", "SEQ_TASKTEMPLET_ID");
                break;       
            case TASK_RULE_MOULD_ITEM:
                data.put("seq", "SEQ_TEMPLET_STEP_ID");
                break;
        }
        return this.getSession().selectOne(action, data);
    }
	
	public int insertWorkObject(Object param){
		return this.getSession().insert("basic.insertWorkObject", param);
	}
	
	public int insertActnodeItem(Object param){
		return this.getSession().insert("basic.insertActnodeItem", param);
	}
	
	public int updateActiveStandard(Object param){
		return this.getSession().update("basic.updateActiveStandard", param);
	}
	
	public Object getStandardById(String param){
		return this.getSession().selectOne("basic.getStandardById", param);
	}
	
	public int deleteStandardStepById(String param){
		if ((Integer)this.getSession().selectOne("basic.getCountStandardStepById", param) > 0){
			if (this.getSession().update("basic.deleteStepDataByItemId", param) > 0){
				return this.getSession().update("basic.deleteStandardStepById", param);
			}else{
				return -1;
			}
		}else{
			return this.getSession().update("basic.deleteStandardStepById", param);
		}
	}
	
	public int batchDeleteStdStepById(String[] ids){
		return this.getSession().update("basic.batchDeleteStdItemStepById", ids);
	}
	
	public int batchDeleteStdStepActitemById(String[] ids){
		return this.getSession().update("basic.batchDeleteStdStepActitemById", ids);
	}
	
	public int deleteStepDataById(String param){
		if (this.getSession().update("basic.deleteStepDataById", param) > 0){
			return this.getSession().update("basic.updateStepIndexAfterDelete", param);
		}
		return -1;
	}
	
	public List<?> selectAllActNode(){
		return this.getSession().selectList("basic.selectAllActNode");
	}
	
	public Object getCalDataBySeq(String seq){
		return this.getSession().selectOne("basic.getCalDataBySeq", seq);
	}
	
	public int modifyCalData(Object param){
		return this.getSession().update("basic.updateCalData", param);
	}
	
	public List<?> getStandardStepById(String param){
		return this.getSession().selectList("basic.getStandardStepById", param);
	}
	
	public int deleteObjectById(String param){
		return this.getSession().update("basic.deleteObjectById", param);
	}
	
	public int modifyBasicData(Object param){
		return this.getSession().update("basic.updateBasicData", param);
	}
	
	public Object getBasicDataByObjId(String param){
		return this.getSession().selectOne("basic.getBasicDataByObjId", param);
	}
	
	public List<?> getAllTraceFunName(){
		return this.getSession().selectList("basic.getAllTraceFunName");
	}
	
	public int addStepData(Object param){
		return this.getSession().insert("basic.addStepData", param);
	}
	
	public Object getStepDataByCid(String param){
		return this.getSession().selectOne("basic.getStepDataByCid", param);
	}
	
	public int modifyStepDataByCid(Object param){
		return this.getSession().update("basic.modifyStepDataByCid", param);
	}
	
	public int addTimeRule(Object param){
		return this.getSession().insert("basic.addTimeRule", param);
	}
	
	public Object getTimeRuleById(String param){
		return this.getSession().selectOne("basic.getTimeRuleById", param);
	}
	
	public int modifyTimeRuleById(Object param){
		return this.getSession().update("basic.modifyTimeRuleById", param);
	}
	
	public int deleteTimeruleById(String param){
		return this.getSession().update("basic.deleteTimeruleById", param);
	}
	
	public int submitStandardStepDetails(Object param){
		return this.getSession().update("task.submitStandardStepDetails",param);
	}
	
	public int submitUnstandardStepDetails(Object param){
		return this.getSession().update("task.submitUnstandardStepDetails", param);
	}
	
	public int submitTaskStep(Object param){
		return this.getSession().update("task.submitTaskStep",param);
	}
	
	public int deleteObjectByIds(String[] ids){
		return this.getSession().update("task.deleteObjectByIds", ids);
	}
	
	public Object getStepDetails(String id){
		return this.getSession().selectOne("task.getStepDetailsByStepId", id);
	}
	
	public int editStepDetails(Object param){
		return this.getSession().update("task.submitStepDetailsByStepId", param);
	}
	
	public Object getTaskMouldById(String param){
		return this.getSession().selectOne("task.getTaskMouldById", param);
	}
	
	public List<?> getAllWorkObjectType(){
		return this.getSession().selectList("task.getAllWorkObjectType");
	}
	
	public List<?> getAllWorkObject(){
		return this.getSession().selectList("task.getAllWorkObject");
	}
	
	public int submitStepData(Object param){
		return this.getSession().update("basic.submitStepData", param);
	}
	
	public List<?> getAllUsers(){
		return this.getSession().selectList("basic.getAllUsers");
	}
	
	public Object sendTTask(String action,Object param){
        return this.getSession().selectOne(action, param);
	}
	
	//待办任务执行步骤及结果查询
	@SuppressWarnings("unchecked")
	public List<TaskStepResultPojo> getTaskStepAndResult(String taskId,Pagination pagination){
		if(pagination==null||(pagination.getCurrPage()<=0||pagination.getCurrPage()<=0)){
			return this.getSession().selectList("task.getTaskStepAndResult", taskId);
		}
		return this.getSession().selectList("task.getTaskStepAndResult", taskId,pagination);
	}
	
	//消息通知 查询当前用户接收的消息
	@SuppressWarnings("unchecked")
	public List<Message> getMessageByUserId(String userId){
		return this.getSession().selectList("message.getMessageByUserId", userId);
	}
	
	//获取消息通知通过条件
	@SuppressWarnings("unchecked")
	public List<Message> getMessageByCondition(Message message){
		return this.getSession().selectList("message.getMessageByCondition", message);
	}
	
	//通过ID查询消息通知
	public Message getMessageById(String messageId){
		return (Message) this.getSession().selectOne("message.getMessageById", messageId);
	}
	
	//修改消息通知状态
    public void updateMessageState(long c_msg_id){
        Map<String, Object> map = new HashMap<String, Object>(); 
        map.put("vC_MSG_ID", c_msg_id);           
        map.put("vC_STATUS", 22);   
        this.getSession().update("message.updateMessageState", map);
    }
    
	//修改任务步骤的信息
	public void updateTaskStepInfo(TaskStepResultPojo pojo){
		this.getSession().update("task.updateTaskStepInfo", pojo);
	}
	
	//批量确认任务下发 GuveXie 20140813
	@SuppressWarnings("rawtypes")
	public int updateMultiTaskConfirm(Map params){
		return this.getSession().update("task.MultiTaskConfirm", params);
	}
	
	//获取登录用户及其下属用户信息  GuveXie 20140814
	@SuppressWarnings("unchecked")
	public List<User> getUsersByUpUserID(Map params){
		return this.getSession().selectList("basic.getUsersByUpUserID", params);
	}
	
	//获取任务状态List  GuveXie 20140819
	public List<?> getTaskStatusView(){
		return this.getSession().selectList("task.getTaskStatusView");
	}
	
	//获取任务状态List  GuveXie 20140819
	public List<?> getTaskTypeView(){
		return this.getSession().selectList("task.getTaskTypeView");
	}

	//获取任务触发类别List  GuveXie 20140828
	public List<?> getTaskTriggerTypeView(){
		return this.getSession().selectList("task.getTaskTriggerTypeView");
	}
		
	//获取管理板块List  GuveXie 20140830
	public List<?> getManageSectionList(){
		return this.getSession().selectList("task.getManageSectionList");
	}
	//执行待办任务，修改状态
	public void updateTaskStatusByProc(TaskStatusPojo pojo){
	   this.getSession().update("mytask.updateTaskStatusByProc", pojo);	
	   this.getSession().update("mytask.updateFactEndtime",pojo);
	}
	
	//发起任务
    public String newTask(TaskMouldPojo taskMouldPojo){
        Map<String, Object> map = new HashMap<String, Object>(); 
        map.put("v_TASK_TYPE", taskMouldPojo.getV_task_type());           
        map.put("v_TASK_NAME", taskMouldPojo.getV_task_name());           
        map.put("v_MANAGE_SECTION",  taskMouldPojo.getV_manage_section());        
        map.put("v_START_TIME",   taskMouldPojo.getV_start_time());            
        map.put("v_END_TIME",   taskMouldPojo.getV_end_time());            
        map.put("v_EXEC_USERID",   taskMouldPojo.getV_exec_userid());           
        map.put("v_SENDER_USERID",   taskMouldPojo.getV_sender_userid());           
        map.put("v_REMARK",   taskMouldPojo.getV_remark()); 
        map.put("v_new_taskid", "0");
        getSession().selectOne("mytask.newTask", map); 
        return map.get("v_new_taskid").toString();

    }
	
	public StdFileAffixPojo queryImgPath(String c_file_id){
	    return (StdFileAffixPojo) this.getSession().selectOne("task.queryImgPath", c_file_id);
	}
	
	//异常反馈
    public String errorFeedback(TaskErrorFeedback taskErrorFeedback){
        Map<String, Object> map = new HashMap<String, Object>(); 
        map.put("vC_ERR_NAME", taskErrorFeedback.getC_err_name());           
        map.put("vC_MANAGE_SECTION", taskErrorFeedback.getC_manage_section());           
        map.put("vC_ERR_DES",  taskErrorFeedback.getC_err_des());        
        map.put("vC_END_TIME",   taskErrorFeedback.getC_end_time());            
        map.put("vC_WRITER",   taskErrorFeedback.getC_writer());            
        map.put("vC_RECEIVER_USERID",   taskErrorFeedback.getC_coordinator());           
        map.put("vC_RECEIVER_USERID2",   taskErrorFeedback.getC_copyfor()); 
        map.put("vC_ERR_ID", "0");
        
        map.put("vC_ERR_KIND", "1");
        map.put("vC_ACTITEM_ID", "0");
        map.put("vC_STEP_INDEX", "0");
        map.put("vC_ERR_TYPE", "0");
        map.put("vC_ERR_LEVEL", "0");
        map.put("vC_WRITE_TIME", "2013-11-19 16:00:00");
        
        getSession().selectOne("taskError.errorfeedback", map); 
        return map.get("vC_ERR_ID").toString();
    }
    
    //发起消息
    public String addMessage(Message message){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        
        Map<String, Object> map = new HashMap<String, Object>(); 
        map.put("vC_MSG_TITLE", message.getC_msg_title());           
        map.put("vC_MSG_CONTENT", message.getC_msg_content());           
        map.put("vC_MSG_TYPE",  message.getC_msg_type());        
        map.put("vC_MSG_LEVEL",   1);            
        map.put("vC_NOTIFY_TYPE",   1); 
        map.put("vC_FROM",   message.getC_from());
        map.put("vC_CREATE_TIME",   df.format(new Date()));           
        map.put("vC_PLAN_TIME",   df.format(new Date()));  
        map.put("vC_EXPIRY_TIME",  message.getC_expiry_time()); 
        map.put("vC_SEND_TYPE",   -1); 
        map.put("vC_SENDER",   message.getC_sender()); 
        map.put("vC_RECEIVER",   message.getC_receiver()); 
        map.put("vC_REMARK",    message.getC_msg_title()); 
        map.put("vC_PID",   0); 
        
        map.put("NEW_MSGID", 0);
        getSession().selectOne("message.addMessage", map); 
        return map.get("NEW_MSGID").toString();

    }
    
  	public String getTaskStatusNameById(String cStatusId) {
  		Object cStatusName = this.getSession().selectOne("task.getTaskStatusNameById", cStatusId);
		if(cStatusName != null) {
			return cStatusName.toString();
		} else {
			return "";
		}
  	}
  	
  	public String getTaskTypeNameById(String cTaskTypeId) {
  		Object cTaskTypeName = this.getSession().selectOne("task.getTaskTypeNameById", cTaskTypeId);
		if(cTaskTypeName != null) {
			return cTaskTypeName.toString();
		} else {
			return "";
		}
  	}
  	
  	public String getTaskTriggerNameById(String cTriggerId) {
  		Object cTriggerName = this.getSession().selectOne("task.getTaskTriggerNameById", cTriggerId);
		if(cTriggerName != null) {
			return cTriggerName.toString();
		} else {
			return "";
		}
  	}
  	
  	public String getManageSectionNameById(String cManageSectionId) {
  		Object cManageSectionName = this.getSession().selectOne("task.getManageSectionNameById", cManageSectionId);
		if(cManageSectionName != null) {
			return cManageSectionName.toString();
		} else {
			return "";
		}
  	}

	public void saveBz(Map z_bei) {
		// TODO Auto-generated method stub
		this.getSession().update("task.saveBz", z_bei);
	}

	public String getBz(Map z_bei) {
		// TODO Auto-generated method stub
		return (String)this.getSession().selectOne("task.getBz", z_bei);
	}
}