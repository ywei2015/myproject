package com.talkweb.xwzcxt.service;

import java.util.List;
import java.util.Map;

import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.xwzcxt.vo.TaskErrTraceVo;


public interface ErrVerifyAndCommentService {
	public List getErrVerifyAndCommentInfo(TaskErrTraceVo params,Pagination pagination);
	
	public int updateVerifyStatus(Map params);
	
	public int updateVerifyResult(TaskErrTraceVo params);
	
	public int updateEvaluateStatus(Map params);
	
	public int updateEvaluateResult(TaskErrTraceVo params);
	
	public int updateEmisStatus(TaskErrTraceVo params);
}
