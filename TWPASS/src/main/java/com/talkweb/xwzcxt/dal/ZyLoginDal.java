package com.talkweb.xwzcxt.dal;

import java.util.Map;

import com.talkweb.twdpe.dal.batis.spring.SessionDaoSupport;

public class ZyLoginDal extends SessionDaoSupport{

	public String getUserCode(Map param){
		
		return (String)this.getSession().selectOne("zyLogin.getUserCode",param);
	}
	
	public Map getUserInfo(Map param){
		return (Map) this.getSession().selectOne("zyLogin.getUserInfo",param);
	}
}
