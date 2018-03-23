package com.talkweb.xwzcxt.dal;

import java.util.List;
import java.util.Map;

import com.talkweb.twdpe.dal.batis.spring.SessionDaoSupport;

public class CheckCardDal extends SessionDaoSupport {

	public List getJobObjInfo(Map param){
		return getSession().selectList("checkCardObject.getTheObjectById", param);
	}
	
	public List getTaskInfos(Map param){
		return getSession().selectList("checkCardObject.getTaskInfos", param);
	}
	
	public List getTaskSteps(Map param){
		return getSession().selectList("checkCardObject.getTaskSteps", param);
	}
}
