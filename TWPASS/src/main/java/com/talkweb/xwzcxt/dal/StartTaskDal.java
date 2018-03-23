package com.talkweb.xwzcxt.dal;

import java.util.HashMap;
import java.util.Map;

import com.talkweb.twdpe.core.log.Logger;
import com.talkweb.twdpe.core.log.LoggerFactory;
import com.talkweb.twdpe.dal.batis.spring.SessionDaoSupport;
import com.talkweb.xwzcxt.pojo.StartTaskPojo;
import com.talkweb.xwzcxt.pojo.TaskStatusPojo;


public class StartTaskDal extends SessionDaoSupport{
	
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(StartTaskDal.class);
	//执行待办任务，修改状态
	public void updateTaskStatusByProc(TaskStatusPojo pojo){
	   this.getSession().update("mytask.updateTaskStatusByProc", pojo);	
	   this.getSession().update("mytask.updateFactEndtime",pojo);
	}
	
	//发起任务
    public String startTask(StartTaskPojo startTaskPojo ){
        Map<String, Object> map = new HashMap<String, Object>(); 
        map.put("v_TASK_TYPE", startTaskPojo.getV_task_type());           
        map.put("v_TASK_NAME", startTaskPojo.getV_task_name());           
        map.put("v_MANAGE_SECTION",  startTaskPojo.getV_manage_section());        
        map.put("v_START_TIME",   startTaskPojo.getV_start_time());            
        map.put("v_END_TIME",   startTaskPojo.getV_end_time());            
        map.put("v_EXEC_USERID",   startTaskPojo.getV_exec_userid());           
        map.put("v_SENDER_USERID",   startTaskPojo.getV_sender_userid());           
        map.put("v_REMARK",   startTaskPojo.getV_remark()); 
        map.put("v_CC_USERID_LIST",startTaskPojo.getV_cc_userid_list());
        map.put("v_FILEIDS", startTaskPojo.getV_fileids());
        map.put("v_new_taskid", "0");
        
        this.getSession().selectOne("startTask.startTask", map); 
        String  taskid=map.get("v_new_taskid").toString();
        
        map.clear();
        map.put("c_task_id", taskid);
        map.put("c_chk_userid", startTaskPojo.getC_chk_userid());
        map.put("c_evaluate_userid", startTaskPojo.getC_evaluate_userid());
        map.put("c_chk_plantime", startTaskPojo.getC_chk_plantime());
        map.put("c_evaluate_plantime", startTaskPojo.getC_evaluate_plantime());
        this.getSession().update("startTask.setChkAndEvaInfo", map);
        return taskid;

    }
}
