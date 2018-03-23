package com.talkweb.xwzcxt.service;

import java.util.List;

import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.xwzcxt.vo.TaskErrTraceVo;

public interface TaskErrTraceService {
	public void newError(TaskErrTraceVo taskErrTraceVo);

	public List<TaskErrTraceVo> getFileInfoById(List<String> fileList);

	public void getErrorFeedbackList(TaskErrTraceVo taskErrTraceVo,
			Pagination pagination);

	public void receiveConfirmEror(TaskErrTraceVo taskErrTraceVo);

	public void continueFeedback(TaskErrTraceVo taskErrTraceVo);

	public void finishError(TaskErrTraceVo taskErrTraceVo);
	
	public List getNeedHandleErrInfo(TaskErrTraceVo taskErrTraceVo,Pagination pagination);
}
