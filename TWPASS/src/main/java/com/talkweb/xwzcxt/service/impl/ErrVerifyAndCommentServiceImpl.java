package com.talkweb.xwzcxt.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.xwzcxt.dal.ErrVerifyAndCommentDal;
import com.talkweb.xwzcxt.service.ErrVerifyAndCommentService;
import com.talkweb.xwzcxt.vo.TaskErrTraceVo;

public class ErrVerifyAndCommentServiceImpl implements ErrVerifyAndCommentService{

	@Autowired
	private ErrVerifyAndCommentDal dal;
	@Override
	public List getErrVerifyAndCommentInfo(TaskErrTraceVo params,Pagination pagination) {
		return dal.getErrVerifyAndCommentInfo(params,pagination);
	}

	@Override
	public int updateVerifyStatus(Map params) {
		return dal.updateVerifyStatus(params);
	}

	@Override
	public int updateVerifyResult(TaskErrTraceVo params) {
		return dal.updateVerifyResult(params);
	}

	@Override
	public int updateEvaluateStatus(Map params) {
		return dal.updateEvaluateStatus(params);
	}

	@Override
	public int updateEvaluateResult(TaskErrTraceVo params) {
		return dal.updateEvaluateResult(params);
	}

	@Override
	public int updateEmisStatus(TaskErrTraceVo params) {
		return dal.updateEmisStatus(params);
	}

}
