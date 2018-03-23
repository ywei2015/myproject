package com.talkweb.xwzcxt.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.common.AppConstants;
import com.talkweb.twdpe.core.data.DataMap;
import com.talkweb.twdpe.core.data.IData;
import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.xwzcxt.common.XwzcxtCommon.Enum_Xwzcxt;
import com.talkweb.xwzcxt.dal.XwzcxtMngDal;
import com.talkweb.xwzcxt.pojo.TaskMouldPojo;

public class XwzcxtMngImp {

    // 查询DAL类
    @Autowired
    XwzcxtMngDal xwzcxtMngDal;
    @Autowired
    AppConstants appConstants;
    private final String KEY_DELETE =  "task.deleteData";


    // / <summary>
    // / 信息分页查询
    // / </summary>
    // / <param name="pageIndex"></param>
    // / <param name="pageSize"></param>
    // / <param name="sort"></param>
    // / <returns></returns>
    @SuppressWarnings("unchecked")
    public List<TaskMouldPojo> GetCurrentRecordByPage(Object taskMouldPojo, Pagination pagination,Enum_Xwzcxt enumX)
    {
        List<TaskMouldPojo> list_UsrStaffreg = new ArrayList();
        List<TaskMouldPojo> dt = (List<TaskMouldPojo>)xwzcxtMngDal.GetCurrentRecordByPage(pagination, taskMouldPojo,enumX);
        String status = null;
        int rowCount = dt.size();
        if (rowCount > 0)
        {
            try
            {
            	TaskMouldPojo model;
                for (int n = 0; n < rowCount; n++)
                {
                    model = new TaskMouldPojo();
                    model = (TaskMouldPojo) dt.get(n);
                    if(model.getC_trigger_type() != null)
                    {
                        switch(Integer.parseInt(model.getC_trigger_type())){
                            case 1:
                                model.setC_trigger_type_name("时间规则触发");
                                break;
                            case 2:
                                model.setC_trigger_type_name("异常处理");
                                break;
                            case 3:
                                model.setC_trigger_type_name("其它系统触发");
                                break;
                        }
                    }
                    if(model.getC_timerule_id() != null)
                    {
                        switch(Integer.parseInt(model.getC_timerule_id())){
                            case 0:
                                model.setC_timerule_name("无时间规则");
                                break;
                            case 1:
                                model.setC_timerule_name("关联时间规则");
                                break;
                            default:
                                model.setC_timerule_name("无时间规则");
                                break;
                        }
                    }
                    if(model.getC_ok_triggertype() != null)
                    {
                        switch(Integer.parseInt(model.getC_ok_triggertype())){
                            case 0:
                                model.setC_ok_triggertypename("不处理");
                                break;
                            case 1:
                                model.setC_ok_triggertypename("通知班长");
                                break;
                            case 2:
                                model.setC_ok_triggertypename("通知班长与部长");
                                break;
                        }
                    }
                    if(model.getC_err_triggertype() != null)
                    {
                        switch(Integer.parseInt(model.getC_err_triggertype())){
                            case 0:
                                model.setC_err_triggertypename("不处理");
                                break;
                            case 1:
                                model.setC_err_triggertypename("通知班长");
                                break;
                            case 2:
                                model.setC_err_triggertypename("通知班长与部长");
                                break;
                        }
                    }
                    if(model.getC_overtime_triggertype() != null)
                    {
                        switch(Integer.parseInt(model.getC_overtime_triggertype())){
                            case 0:
                                model.setC_overtime_triggertypename("不处理");
                                break;
                            case 1:
                                model.setC_overtime_triggertypename("通知班长");
                                break;
                            case 2:
                                model.setC_overtime_triggertypename("通知班长与部长");
                                break;
                        }     
                    }
                    if(model.getC_std_flag() != null)
                    {
                        switch(Integer.parseInt(model.getC_std_flag())){
                            case 0:
                                model.setC_std_flagname("手动");
                                break;
                            case 1:
                                model.setC_std_flagname("自动");
                                break;
                        }
                    }
                    if(model.getC_check_flag() != null)
                    {
                        switch(Integer.parseInt(model.getC_check_flag())){
                            case 0:
                                model.setC_check_flagname("手动");
                                break;
                            case 1:
                                model.setC_check_flagname("自动");
                                break;
                        }
                    }
                    if(model.getC_errstd_flag() != null)
                    {
                        switch(Integer.parseInt(model.getC_errstd_flag())){
                            case 0:
                                model.setC_errstd_flagname("手动");
                                break;
                            case 1:
                                model.setC_errstd_flagname("自动");
                                break;
                        }
                    }
                    /*
                    if(model.getC_task_type() != null)
                    {
                        switch(Integer.parseInt(model.getC_task_type())){
                          case 1:
                              model.setC_task_type_name("日管控");
                              break;
                          case 2:
                              model.setC_task_type_name("周管理");
                              break;
                          case 3:
                              model.setC_task_type_name("月管控");
                              break;
                          case 10:
                        	  model.setC_task_type_name("异常处理");
                              break;
                          case 20:
                        	  model.setC_task_type_name("临时任务");
                              break;
                          }
                    }
                    */
                    if(model.getC_status() != null)
                    {
                        switch(Integer.parseInt(model.getC_status())){
                          case 0:
                              model.setC_status_name("已生成");
                              break;
                          case 11:
                              model.setC_status_name("已确认");
                              break;
                          case 22:
                              model.setC_status_name("已推送");
                              break;
                          case 33:
                              model.setC_status_name("已执行");
                              break;
                          case 44:
                        	  model.setC_status_name("被取消");
                        	  break;
                        }
                    }
                    //Add By Rita.Zhou for pageMoudle
//                    switch(Integer.parseInt(taskMouldPojo.getC_status())){
//	                    case 0:
//	                    	taskMouldPojo.setC_status_name("反馈提交数据库");
//	                    	break;
//	                    case 11:
//	                    	taskMouldPojo.setC_status_name("反馈被接收待处理");
//	                    	break;
//	                    case 22:
//	                    	taskMouldPojo.setC_status_name("反馈处理完成");
//	                    	break;
//	                    case 33:
//	                    	taskMouldPojo.setC_status_name("执行完成并接收到结果");
//	                    	break;
//                    }
//                    
//                    switch(Integer.parseInt(taskMouldPojo.getC_iskeyctrl())){
//	                    case 0:
//	                    	taskMouldPojo.setC_iskeyctrl_name("非关键管控");
//	                    	break;
//	                    case 1:
//	                    	taskMouldPojo.setC_iskeyctrl_name("关键管控");
//	                    	break;
//                    }
//                    
//                    switch(Integer.parseInt(taskMouldPojo.getC_issequence())){
//	                    case 0:
//	                    	taskMouldPojo.setC_issequence_name("可不按顺序执行");
//	                    	break;
//	                    case 1:
//	                    	taskMouldPojo.setC_issequence_name("需要按顺序执行");
//	                    	break;
//	                }
//                    
//                    switch(Integer.parseInt(taskMouldPojo.getC_isgetnotify())){
//	                    case 0:
//	                    	taskMouldPojo.setC_isgetnotify_name("不接收");
//	                    	break;
//	                    case 1:
//	                    	taskMouldPojo.setC_isgetnotify_name("接收");
//	                    	break;
//	                }
//                    
//                    switch(Integer.parseInt(taskMouldPojo.getC_isrelay())){
//	                    case 0:
//	                    	taskMouldPojo.setC_isrelay_name("不转发");
//	                    	break;
//	                    case 1:
//	                    	taskMouldPojo.setC_isrelay_name("转发");
//	                    	break;
//	                }
//                    
//                    switch(Integer.parseInt(taskMouldPojo.getC_task_type())){
//	                    case 1:
//	                    	taskMouldPojo.setC_task_type_name("日管控");
//	                    	break;
//	                    case 2:
//	                    	taskMouldPojo.setC_task_type_name("周管理");
//	                    	break;
//	                    case 3:
//	                    	taskMouldPojo.setC_task_type_name("月管控");
//	                    	break;
//	                    case 10:
//	                    	taskMouldPojo.setC_task_type_name("异常处理");
//	                    	break;
//	                    case 20:
//	                    	taskMouldPojo.setC_task_type_name("临时任务");
//	                    	break;
//	                }
//                    
//                    switch(Integer.parseInt(taskMouldPojo.getC_trigger_type())){
//	                    case 1:
//	                    	taskMouldPojo.setC_trigger_type_name("时间规则触发");
//	                    	break;
//	                    case 2:
//	                    	taskMouldPojo.setC_trigger_type_name("异常处理");
//	                    	break;
//	                    case 3:
//	                    	taskMouldPojo.setC_trigger_type_name("其它系统触发");
//	                    	break;
//	                }
//                    
//                    switch(Integer.parseInt(taskMouldPojo.getC_isstd())){
//	                    case 0:
//	                    	taskMouldPojo.setC_isstd_name("非标准任务");
//	                    	break;
//	                    case 1:
//	                    	taskMouldPojo.setC_isstd_name("标准任务");
//	                    	break;
//	                }
//                    
//                    switch(Integer.parseInt(taskMouldPojo.getC_iscancel())){
//	                    case 0:
//	                    	taskMouldPojo.setC_iscancel_name("未取消");
//	                    	break;
//	                    case 1:
//	                    	taskMouldPojo.setC_iscancel_name("已取消");
//	                    	break;
//	                }
                    list_UsrStaffreg.add(model);
                }
            } catch (Exception ex)
            {
                // log.Error("/r/n初始化实体对象信息失败:GetBoxingProductRecordByPage" +
                // ex.Message);
            }
        }
        return list_UsrStaffreg;
    }
   
    @SuppressWarnings("unchecked")
    public  List<TaskMouldPojo> GetCurrentRecordByPage(TaskMouldPojo taskMouldPojo,Enum_Xwzcxt enumX)
    {
        List<TaskMouldPojo> list_UsrStaffreg = new ArrayList();
        List<TaskMouldPojo>  dt = (List<TaskMouldPojo>) xwzcxtMngDal.GetCurrentRecordList(taskMouldPojo,enumX);
        String status = null;
        int rowCount = dt.size();
        if (rowCount > 0)
        {
            try
            {
                TaskMouldPojo model;
                for (int n = 0; n < rowCount; n++)
                {
                    model = new TaskMouldPojo();
                    model = (TaskMouldPojo) dt.get(n);
                    if(model.getC_file_path()!= null){
                        model.setC_file_path(appConstants.getIMG_URL()+model.getC_file_path());
                    }
                    if(model.getC_iserror() != null){
                        switch(Integer.parseInt(model.getC_iserror())){
                          case 0:
                              model.setC_iserror_name("未取消");
                              break;
                          case 1:
                              model.setC_iserror_name("已取消");
                              break;
                        }
                    }
                    if(model.getC_isstd() != null)
                    {
                        switch(Integer.parseInt(model.getC_isstd())){
                            case 0:
                                model.setC_isstd_name("非标准动作");
                                break;
                            case 1:
                                model.setC_isstd_name("标准动作");
                                break;
                        }
                    }
                    if(model.getC_status() != null)
                    {
                        switch(Integer.parseInt(model.getC_status())){
                          case 0:
                              model.setC_status_name("已生成");
                              break;
                          case 11:
                              model.setC_status_name("已完成");
                              break;
                        }
                    }
                    list_UsrStaffreg.add(model);
                }
            } catch (Exception ex)
            {
                // log.Error("/r/n初始化实体对象信息失败:GetBoxingProductRecordByPage" +
                // ex.Message);
            }
        }
        return list_UsrStaffreg;
    }
    
    
    public TaskMouldPojo queryTobaccoEntry (Enum_Xwzcxt enumX, TaskMouldPojo taskMouldPojo) {
        TaskMouldPojo resultPojo= (TaskMouldPojo)xwzcxtMngDal.GetCurrentRecord(taskMouldPojo,enumX);
        switch(enumX){
            case TASK_RULE_MOULD:
                String itemId= "";
                TaskMouldPojo pojo = new TaskMouldPojo();
                pojo.setC_tasktemplet_id(resultPojo.getC_tasktemplet_id());
                List<TaskMouldPojo>  dt =   GetCurrentRecordByPage(pojo,Enum_Xwzcxt.TASK_RULE_MOULD_ITEM);
                for(int i=0;i<dt.size();i++){
                    itemId+= dt.get(i).getC_actitem_id()+",";
                }
                resultPojo.setC_actitem_id(itemId.substring(0,itemId.length()-1));
                break;
        }
        return resultPojo;
	}
    
    public int deleteStepByCid(String deleteID){
    	return xwzcxtMngDal.deleteStepByCid (deleteID);
    }
    public int cancelTaskById(String deleteID){
    	return xwzcxtMngDal.cancelTaskById (deleteID);
    }
    
    public boolean Delete (Enum_Xwzcxt curPro,String deleteID) {
    	IData param = new DataMap();
    	String action = "task.deleteData";
        switch (curPro)
        {
            case TASK_RULE_MOULD:
                param.put("tablename","t_task_templet");
                param.put("c_tasktemplet_id",deleteID);
                if(xwzcxtMngDal.DeleteData(action,param)){
                    param.put("tablename","t_task_templet_actitem");
                    param.put("c_tasktemplet_id",deleteID);
                }
                break;
            case TASK_RULE_MOULD_ITEM:
                param.put("tablename","t_task_templet_actitem");
                param.put("c_id",deleteID);
                break; 
            case TASK_MOULD:
                param.put("tablename","t_task_templet_actitem");
                param.put("c_tasktemplet_id",deleteID);
                break; 
            case TASK_STEP_DETAIL:
                param.put("tablename","t_task_step");
                param.put("c_id",deleteID);
                break; 
            case TASK_INFO_LIST:
                param.put("tablename","t_task");
                param.put("c_task_id",deleteID);
                break;
        }       
    	return xwzcxtMngDal.DeleteData (action , param);
    }

	public boolean ExecuteInsert (Enum_Xwzcxt curPro, TaskMouldPojo param) {
		String action = "";
		switch (curPro)
		{
            case TASK_RULE_MOULD:
                action = "task.insertTaskRuleMould";
                break;  
		    case TASK_RULE_MOULD_ITEM:
		        action = "task.insertTaskRuleMouldItem";
		        break;			
		}		
		return xwzcxtMngDal.ExecuteInsert (action, param);
	}
	public boolean ExecuteUpdate (Enum_Xwzcxt curPro, TaskMouldPojo param) {
	    String action = "";
        
        switch (curPro)
        {
            case TASK_RULE_MOULD:
                action = "task.updateTaskRuleMould";
                break;  
            case TASK_RULE_MOULD_ITEM:
               
                break; 
            case TASK_STEP_DETAIL:
                action = "task.updateTTask";
            	//调用存储过程
                break;  
               
        }       
		return xwzcxtMngDal.ExecuteUpdate (action, param);
	}
    public boolean ExecutePro (Enum_Xwzcxt curPro,TaskMouldPojo taskMouldPojo) {
        String action = "";
        Map<String, String> param = new HashMap<String, String>();  
        switch (curPro)
        {
            case TASK_MOULD:
                param.put("v_TASKTEMPLET_ID", String.valueOf(taskMouldPojo.getC_tasktemplet_id()));
                param.put("v_START_TIME", taskMouldPojo.getC_start_time());
                param.put("v_URGE_TIME", taskMouldPojo.getC_urge_time());
                param.put("v_END_TIME", taskMouldPojo.getC_end_time());
                param.put("v_EXCEED_TIME", taskMouldPojo.getC_exceed_time());
                param.put("v_PLANDOWN_TIME", taskMouldPojo.getC_plandown_time());
                param.put("v_TASK_TYPE", taskMouldPojo.getC_task_type());
                param.put("v_EXEC_USERID", taskMouldPojo.getC_exec_userid());
                param.put("v_AREA_ID", taskMouldPojo.getC_arer());
                action = "task.P_TWXWZC_GENERATE_TASK_STD";
                break;        
        }       
        xwzcxtMngDal.ExecutePro (action,param);
      /* // if()
        String returnValue = (String) session.selectOne("User.proHello", param);  
        System.out.println("message=" + param.get("p_user_name"));  
        System.out.println("result=" + param.get("result"));  
        System.out.println("returnValue=" + returnValue);  */
        return true;
        
    }
	public boolean ExecuteUpdate (Enum_Xwzcxt curPro, IData param) {
		String action = "";
		switch (curPro)
		{
			 
		}
		return xwzcxtMngDal.ExecuteUpdate (action, param);
	}
	public String GetSeq(Enum_Xwzcxt curPro){	    
	    return xwzcxtMngDal.GetSeq(curPro).toString();
	}
	
	public int submitStandardStepDetails(Object param){
		return xwzcxtMngDal.submitStandardStepDetails(param);
	}
	public int submitUnstandardStepDetails(Object param){
		return xwzcxtMngDal.submitUnstandardStepDetails(param);
	}
	public int submitTaskStep(Object param){
		return xwzcxtMngDal.submitTaskStep(param);
	}
	public int deleteObjectByIds(String[] ids){
		return xwzcxtMngDal.deleteObjectByIds(ids);
	}
	public TaskMouldPojo getStepDetails(String id){
		return (TaskMouldPojo)xwzcxtMngDal.getStepDetails(id);
	}
	public int editStepDetails(Object param){
		return xwzcxtMngDal.editStepDetails(param);
	}
	public List<TaskMouldPojo> getAllWorkObjectType(){
		return (List<TaskMouldPojo>)xwzcxtMngDal.getAllWorkObjectType();
	}
	public List<TaskMouldPojo> getAllWorkObject(){
		return (List<TaskMouldPojo>)xwzcxtMngDal.getAllWorkObject();
	}
	
	
	public TaskMouldPojo getTaskMouldById(String param){
		return (TaskMouldPojo) xwzcxtMngDal.getTaskMouldById(param);
	}
	
	public boolean sendTTask(TaskMouldPojo taskMouldPojo){
	     Map<String, Object> param = new HashMap<String, Object>();  
	     
	     param.put("v_TASK_ID",Integer.parseInt(taskMouldPojo.getC_task_id()));
	     param.put("v_START_TIME",taskMouldPojo.getC_start_time());
	     param.put("v_URGE_TIME",taskMouldPojo.getC_urge_time());
	     param.put("v_END_TIME",taskMouldPojo.getC_end_time());
	     param.put("v_EXCEED_TIME",taskMouldPojo.getC_exceed_time());
	     param.put("v_PLANDOWN_TIME",taskMouldPojo.getC_plandown_time());
	     param.put("v_TASK_TYPE",Integer.parseInt(taskMouldPojo.getC_task_type()));
	     param.put("v_EXEC_USERID",Integer.parseInt(taskMouldPojo.getC_exec_userid()));
	     param.put("v_AREA_ID",taskMouldPojo.getC_area_id());
	     param.put("v_CONFIRM_USERID",Integer.parseInt(taskMouldPojo.getC_exec_userid()));
	    	
	     xwzcxtMngDal.sendTTask ("task.P_TWXWZC_CONFIRM_TASK",param);
		 return true;
	}
	
	//批量确认任务下发 GuveXie 20140813
	@SuppressWarnings("rawtypes")
	public int updateMultiTaskConfirm(Map params){
		return xwzcxtMngDal.updateMultiTaskConfirm(params);
	}
	
	//获取任务状态List  GuveXie 20140819
	public List<?> getTaskStatusView(){
		return xwzcxtMngDal.getTaskStatusView();
	}
	
	//获取任务状态List  GuveXie 20140819
	public List<?> getTaskTypeView(){
		return xwzcxtMngDal.getTaskTypeView();
	}

	//获取任务触发类别List  GuveXie 20140828
	public List<?> getTaskTriggerTypeView(){
		return xwzcxtMngDal.getTaskTriggerTypeView();
	}
	
	//获取管理板块List  GuveXie 20140830
	public List<?> getManageSectionList(){
		return xwzcxtMngDal.getManageSectionList();
	}
	
}
