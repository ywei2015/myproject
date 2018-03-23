package com.talkweb.xwzcxt.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.xwzcxt.dal.TaskVeriEvaDal;
import com.talkweb.xwzcxt.pojo.TaskMouldPojo;

public class TaskVeriEvaImpl {
	
	@Autowired
	private TaskVeriEvaDal taskVeriEvaDal;
	
	public List<TaskMouldPojo> queryTaskVerify(Map params) {
		return  taskVeriEvaDal.queryTaskVerify(params);	    
	}
	
	public String queryTaskVerifyCount(Map map){
		
		return  taskVeriEvaDal.queryTaskVerifyCount(map);
	}
	
	public TaskMouldPojo updateChkResult(TaskMouldPojo taskMouldPojo){
		return taskVeriEvaDal.updateChkResult(taskMouldPojo);
	}
	
	public List<TaskMouldPojo> queryTaskEvaluate(Map params) {
		return  taskVeriEvaDal.queryTaskEvaluate(params);	    
	}
	
	public String queryTaskEvaluateCount(Map map){
		
		return  taskVeriEvaDal.queryTaskEvaluateCount(map);
	}
	public TaskMouldPojo updateEvalResult(TaskMouldPojo taskMouldPojo){
		return  taskVeriEvaDal.updateEvalResult(taskMouldPojo);
	}

	public String getUserCode(String userid){
		
		return taskVeriEvaDal.getUserCode(userid);
	}
}
