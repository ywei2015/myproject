package com.talkweb.xwzcxt.dal;

import java.util.List;
import java.util.Map;

import com.talkweb.twdpe.dal.batis.spring.SessionDaoSupport;
import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.xwzcxt.pojo.TaskVerifyAndCommentPojo;
import com.talkweb.xwzcxt.vo.ExecutabilityVo;

public class TaskVerifyAndCommentDal extends SessionDaoSupport{

	public Pagination getTaskVerifyInfo(Map task,Pagination pagination){
		List list=this.getSession().selectList("taskVerifyAndComment.getTaskVerifyInfo", task,pagination);
		if(list!=null){
			pagination.setList(list);
		}
		return pagination;
	}
	
	public List getAllVerifyInfo(Map params){
		return this.getSession().selectList("taskVerifyAndComment.getTaskVerifyInfo",params);
	}
	
	public Pagination getTaskCommentInfo(Map task,Pagination pagination){
		List list=this.getSession().selectList("taskVerifyAndComment.getTaskCommentInfo", task,pagination);
		if(list!=null){
			pagination.setList(list);
		}
		return pagination;
	}
	
	public List getAllCommentInfo(Map params){
		return this.getSession().selectList("taskVerifyAndComment.getTaskCommentInfo",params);
	}
	
	public String getVerifyStd(Map params){
		String selectId="taskVerifyAndComment.getTaskVerifyStd"+params.get("version");
		return (String) this.getSession().selectOne(selectId, params);
	}
	
	public String getCommentStd(Map params){
		String selectId="taskVerifyAndComment.getTaskVerifyStd"+params.get("version");
		return  (String) this.getSession().selectOne(selectId, params);
	}
	
	public int verifyStatusUpdate(Map status){
		return this.getSession().update("taskVerifyAndComment.verifyStatusUpdate", status);
	}
	
	public int verifyResultUpdate(Map verifyRecord){
		return this.getSession().update("taskVerifyAndComment.verifyResultUpdate", verifyRecord);
	}
	
	public int commentStatusUpdate(Map status){
		return this.getSession().update("taskVerifyAndComment.commentStatusUpdate", status);
	}
	
/*
   	public String getVerifyResult(String cTaskId){
		return (String) this.getSession().selectOne("taskVerifyAndComment.getVerifyResult",cTaskId);
	}
 */	

	public int commentResultUpdate(Map commentRecord){
		return this.getSession().update("taskVerifyAndComment.commentResultUpdate", commentRecord);
	}
	
	public void updateEmisStatus(Map map){
		this.getSession().update("taskVerifyAndComment.updateEmisStatus", map);
	}
	
	public List getSelfData(ExecutabilityVo exevo){
		return  this.getSession().selectList("taskExecuteCount.getSelfTaskExecuteCount",exevo);
	}
	
	public int getEvaluateTaskCount(Long userid){
		return (Integer)this.getSession().selectOne("taskVerifyAndComment.getEvaluateTaskCount",  userid);
	}
	
	public int getChkTaskCount(Long userid){
		return (Integer) this.getSession().selectOne("taskVerifyAndComment.getChkTaskCount",  userid);
	}
	
	public List getChkitemStd(String c_actnode_id){
		return this.getSession().selectList("taskVerifyAndComment.getChkitemStd",c_actnode_id);
	}
}
