package com.talkweb.xwzcxt.dal;

import java.util.List;
import java.util.Map;

import com.talkweb.twdpe.dal.batis.spring.SessionDaoSupport;
import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.xwzcxt.vo.TaskErrTraceVo;

public class ErrVerifyAndCommentDal extends SessionDaoSupport{

	public List getErrVerifyAndCommentInfo(TaskErrTraceVo params,Pagination pagination){
		if(pagination==null){
			return this.getSession().selectList("errVerifyAndComment.errVerifyAndCommentInfo",params);
		}
		return this.getSession().selectList("errVerifyAndComment.errVerifyAndCommentInfo",params, pagination);
	}
	
	public int updateVerifyStatus(Map params){
		return this.getSession().update("errVerifyAndComment.updateVerifyStatus",params);
	}
	
	public int updateVerifyResult(TaskErrTraceVo params){
		return this.getSession().update("errVerifyAndComment.updateVerifyResult",params);
	}
	
	public int updateEvaluateStatus(Map params){
		return this.getSession().update("errVerifyAndComment.updateEvaluateStatus",params);
	}
	
	public int updateEvaluateResult(TaskErrTraceVo params){
		return this.getSession().update("errVerifyAndComment.updateEvaluateResult",params);
	}
	
	public int updateEmisStatus(TaskErrTraceVo params){
		return this.getSession().update("errVerifyAndComment.updateEmisStatus",params);
	}
}
