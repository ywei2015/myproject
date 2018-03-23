package com.talkweb.xwzcxt.service;

import java.util.List;

import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.xwzcxt.pojo.TaskError;

/**
 * TODO(描述这个类的作用)
 * 
 * @author: 2014-2-21，FLN，（描述修改内容）
 */
public interface XwzcxtTaskErrorService
{
    public List<TaskError> queryTaskErrorList(TaskError taskError,
            Pagination pagination);

	public TaskError getTaskErrDetailById(String id);

	public List<TaskError> getErrFilesByErrTaskId(String id);

	public List<TaskError> getErrFeedbacksByErrTaskId(String id, Pagination pagination);
	public List<TaskError> getErrFeedbacksByErrTaskIdM(String id);
	
	public TaskError getErrTaskSNByErrTaskId(String id);//, Pagination pagination);

	
    
}
