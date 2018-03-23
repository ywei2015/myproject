package com.talkweb.xwzcxt.dal;

import java.util.List;
import java.util.Map;

import com.talkweb.twdpe.dal.batis.spring.SessionDaoSupport;
import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.xwzcxt.vo.TaskErrTraceVo;

public class TaskErrTraceDal  extends SessionDaoSupport{
	public int newError(TaskErrTraceVo taskErrTraceVo) {
		return getSession().insert("taskErrTrace.newError",taskErrTraceVo);
	}
	
	public int setChkAndEvaluateInfo(TaskErrTraceVo taskErrTraceVo){
		return getSession().insert("taskErrTrace.setChkAndEvaluateInfo",taskErrTraceVo);
	}

	@SuppressWarnings("unchecked")
	public List<TaskErrTraceVo> getFileInfoById(Map params) {
		return (List<TaskErrTraceVo>)getSession().selectList("taskErrTrace.getFileInfoById",params);
	}
	
   public void  getErrorFeedbackList(TaskErrTraceVo taskErrTraceVo,Pagination pagination){
	   List list=getSession().selectList("taskErrTrace.getErrorFeedbackList",taskErrTraceVo,pagination);
       pagination.setList(list);
   }
   
   public void receiveConfirmEror(TaskErrTraceVo taskErrTraceVo){
	   getSession().insert("taskErrTrace.receiveConfirmEror",taskErrTraceVo);
   }

   public void continueFeedback(TaskErrTraceVo taskErrTraceVo){
	   getSession().insert("taskErrTrace.continueFeedback",taskErrTraceVo);
   }
   public void finishError(TaskErrTraceVo taskErrTraceVo){
	   getSession().insert("taskErrTrace.finishError",taskErrTraceVo);
   }
   
   public List getNeedHandleErrInfo(TaskErrTraceVo taskErrTraceVo,Pagination pagination){
	   return getSession().selectList("taskErrTrace.getNeedHandleErrInfo",taskErrTraceVo,pagination);
   }
}
