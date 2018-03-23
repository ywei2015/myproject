package com.talkweb.xwzcxt.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.xwzcxt.dal.TaskErrTraceDal;
import com.talkweb.xwzcxt.service.TaskErrTraceService;
import com.talkweb.xwzcxt.vo.TaskErrTraceVo;

public class TaskErrTraceServiceImpl implements TaskErrTraceService{
    @Autowired
	private TaskErrTraceDal taskErrTraceDal;
	@Override
	public void newError(TaskErrTraceVo taskErrTraceVo) {
		  taskErrTraceDal.newError(taskErrTraceVo);
		  if("1".equals(taskErrTraceVo.getC_isbyself())){			  
			  taskErrTraceDal.setChkAndEvaluateInfo(taskErrTraceVo);
		  }
	}

	@Override
	public List<TaskErrTraceVo> getFileInfoById(List<String>fileList) {
		Map params=new HashMap();
		params.put("fileList", fileList);
		return taskErrTraceDal.getFileInfoById(params);
	}

	@Override
	public void getErrorFeedbackList(TaskErrTraceVo taskErrTraceVo,Pagination pagination) {
		taskErrTraceDal.getErrorFeedbackList(taskErrTraceVo, pagination);
	}

	@Override
	public void receiveConfirmEror(TaskErrTraceVo taskErrTraceVo) {
		taskErrTraceVo.setC_status(33);
		taskErrTraceDal.receiveConfirmEror(taskErrTraceVo);
		
	}

	@Override
	public void continueFeedback(TaskErrTraceVo taskErrTraceVo) {
		taskErrTraceDal.continueFeedback(taskErrTraceVo);
		Integer type=taskErrTraceVo.getType();
		if(type!=null&&type==1){			  
			  taskErrTraceDal.setChkAndEvaluateInfo(taskErrTraceVo);
		  }
	}

	@Override
	public void finishError(TaskErrTraceVo taskErrTraceVo) {
		taskErrTraceDal.finishError(taskErrTraceVo);
		Integer type=taskErrTraceVo.getType();
		if(type!=null&&type==2){			  
			  taskErrTraceDal.setChkAndEvaluateInfo(taskErrTraceVo);
		  }
	}

	@Override
	public List getNeedHandleErrInfo(TaskErrTraceVo taskErrTraceVo,Pagination pagination) {
		return taskErrTraceDal.getNeedHandleErrInfo(taskErrTraceVo,pagination);
	}

}
