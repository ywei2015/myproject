package com.talkweb.xwzcxt.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.xwzcxt.dal.TaskVerifyAndCommentDal;
import com.talkweb.xwzcxt.service.TaskVerifyAndCommentService;

public class TaskVerifyAndCommentServiceImpl implements
		TaskVerifyAndCommentService {

	@Autowired
	private TaskVerifyAndCommentDal taskVerifyAndCommentDal;

	@Override
	public Pagination getTaskVerifyInfo(Map task, Pagination pagination) {
		return taskVerifyAndCommentDal.getTaskVerifyInfo(task, pagination);
	}

	@Override
	public Pagination getTaskCommentInfo(Map task, Pagination pagination) {

		return taskVerifyAndCommentDal.getTaskCommentInfo(task, pagination);
	}

	@Override
	public String getVerifyStd(Map params) {

		return taskVerifyAndCommentDal.getVerifyStd(params);
	}

	@Override
	public String getCommentStd(Map params) {
		return taskVerifyAndCommentDal.getCommentStd(params);
	}

	@Override
	public int verifyResultUpdate(Map verifyRecord) {
		return taskVerifyAndCommentDal.verifyResultUpdate(verifyRecord);
	}

	
	@Override
	public int commentResultUpdate(Map commentRecord) {
		return taskVerifyAndCommentDal.commentResultUpdate(commentRecord);
	}
	

	@Override
	public int verifyStatusUpdate(Map status) {
		
		return taskVerifyAndCommentDal.verifyStatusUpdate(status);
	}

	@Override
	public int commentStatusUpdate(Map status) {
		
		return taskVerifyAndCommentDal.commentStatusUpdate(status);
	}

	@Override
	public void updateEmisStatus(Map map) {
		taskVerifyAndCommentDal.updateEmisStatus(map);
	}

	@Override
	public int getChkTaskCount(Long userid) {
		return taskVerifyAndCommentDal.getChkTaskCount(userid);
	}

	@Override
	public int getEvaluateTaskCount(Long userid) {
		return taskVerifyAndCommentDal.getEvaluateTaskCount(userid);
	}

	@Override
	public List getAllVerifyInfo(Map params) {
		return taskVerifyAndCommentDal.getAllVerifyInfo(params);
	}

	@Override
	public List getAllCommentInfo(Map params) {
		return taskVerifyAndCommentDal.getAllCommentInfo(params);
	}

	@Override
	public List getChkitemStd(String c_actnode_id) {
		return taskVerifyAndCommentDal.getChkitemStd(c_actnode_id);
	}

	/*	
	@Override
	 public String getVerifyResult(String cTaskId) {
		return taskVerifyAndCommentDal.getVerifyResult(cTaskId);
	}
*/
	
}
