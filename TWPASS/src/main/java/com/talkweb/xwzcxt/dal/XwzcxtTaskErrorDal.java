package com.talkweb.xwzcxt.dal;

import java.util.List;

import com.talkweb.twdpe.dal.batis.spring.SessionDaoSupport;
import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.xwzcxt.pojo.TaskError;

/**
 * TODO(描述这个类的作用)
 * 
 * @author: 2014-2-21，FLN，（描述修改内容）
 */
public class XwzcxtTaskErrorDal extends SessionDaoSupport
{
    public List<TaskError> queryTaskErrorList(TaskError taskError,
            Pagination pagination) {
    	return (List<TaskError>) this.getSession().selectList("taskError.queryTaskErrorList", taskError, pagination);
    }
    
    public TaskError getTaskErrDetailById(String param){
    	return (TaskError) this.getSession().selectOne("taskError.getTaskErrDetailById", param);
    }
    
    public List<TaskError> getErrFilesByErrTaskId(String param){
    	return (List<TaskError>) this.getSession().selectList("taskError.getErrFilesByErrTaskId", param);
    }
    
    public List<TaskError> getErrFeedbacksByErrTaskId(String param,
    		Pagination pagination){
    	return (List<TaskError>) this.getSession().selectList("taskError.getErrFeedbacksByErrTaskId", param,pagination);
    }
    
    public List<TaskError> getErrFeedbacksByErrTaskIdM(String param){
    	return (List<TaskError>) this.getSession().selectList("taskError.getErrFeedbacksByErrTaskId", param);
    }
    
    public TaskError getErrTaskSNByErrTaskId(String param){//,
    		//Pagination pagination){
    	//return (List<TaskError>) this.getSession().selectList("taskError.getErrTaskSNByTaskId", param);
    	return (TaskError) this.getSession().selectOne("taskError.getErrTaskSNByTaskId", param);
    }
}
