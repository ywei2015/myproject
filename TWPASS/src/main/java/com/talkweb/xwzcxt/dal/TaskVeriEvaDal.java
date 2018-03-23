package com.talkweb.xwzcxt.dal;

import java.util.List;
import java.util.Map;

import com.talkweb.twdpe.dal.batis.spring.SessionDaoSupport;
import com.talkweb.xwzcxt.pojo.TaskMouldPojo;

public class TaskVeriEvaDal extends SessionDaoSupport{
	
	public List<TaskMouldPojo> queryTaskVerify(Map params){
		return (List<TaskMouldPojo>) this.getSession().selectList("TaskVerifyEval.queryTaskVerify",params); 
		
	}
	
	public String queryTaskVerifyCount(Map map){
		
		return (String)this.getSession().selectOne("TaskVerifyEval.queryTaskVerifyCount",map);
	}
	
    public TaskMouldPojo updateChkResult(TaskMouldPojo taskMouldPojo){
    	return  (TaskMouldPojo) this.getSession().selectOne("TaskVerifyEval.updateChkResult",taskMouldPojo);
    	
    }
    
	public List<TaskMouldPojo> queryTaskEvaluate(Map params){
		return (List<TaskMouldPojo>) this.getSession().selectList("TaskVerifyEval.queryTaskEvaluate",params); 
		
	}
	
	public String queryTaskEvaluateCount(Map map){
		
		return (String)this.getSession().selectOne("TaskVerifyEval.queryTaskEvaluateCount",map);
	}
	
    public TaskMouldPojo updateEvalResult(TaskMouldPojo taskMouldPojo){
    	return  (TaskMouldPojo) this.getSession().selectOne("TaskVerifyEval.updateEvalResult",taskMouldPojo);
    	
    }
    
	public String getUserCode(String userid){
		
		return (String)this.getSession().selectOne("TaskVerifyEval.getUserCode",userid);
	}
    
}
