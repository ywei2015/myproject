package com.talkweb.xwzcxt.dal;

import java.util.List;
import java.util.Map;

import com.talkweb.twdpe.dal.batis.spring.SessionDaoSupport;


//GuveXie 2014-08-19 公用基础数据 Action
public class BaseCommDal extends SessionDaoSupport {
	
	public List<?> getPositionByOrgID(Map params){
		return this.getSession().selectList("baseComm.selectPositionByOrgID", params);
	}
	
	public List<?> getOrgPositionByParams(Map params){
		return this.getSession().selectList("baseComm.selectOrgPositionByParams", params);
	}

	public List<?> getAllMsgType(){
		return this.getSession().selectList("baseComm.getMessageTypeView");
	}

}
