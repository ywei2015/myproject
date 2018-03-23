package com.talkweb.xwzcxt.service;

import java.util.List;
import java.util.Map;

import com.talkweb.twdpe.dal.jdbc.info.Pagination;


public interface TaskVerifyAndCommentService {

	public Pagination getTaskVerifyInfo(Map task,Pagination pagination);
	
	public Pagination getTaskCommentInfo(Map task,Pagination pagination);

	public String getVerifyStd(Map params);

	public String getCommentStd(Map params);

	public int verifyStatusUpdate(Map status);
	
	public int verifyResultUpdate(Map verifyRecord);
	
	public int commentStatusUpdate(Map status);
	
	public int commentResultUpdate(Map commentRecord);
	
	//public String getVerifyResult(String cTaskId);
	public void updateEmisStatus(Map map);
	
	public int getChkTaskCount(Long userid);
	public int getEvaluateTaskCount(Long userid);

	public List getAllVerifyInfo(Map params);
	public List getAllCommentInfo(Map params);
	
	public List getChkitemStd(String c_actnode_id);
}
