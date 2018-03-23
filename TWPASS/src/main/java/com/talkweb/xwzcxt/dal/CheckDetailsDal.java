package com.talkweb.xwzcxt.dal;

import com.talkweb.twdpe.dal.batis.spring.SessionDaoSupport;
import com.talkweb.xwzcxt.pojo.CheckDetailsPojo;

public class CheckDetailsDal extends SessionDaoSupport {
	
	public  CheckDetailsPojo getCheckDetailsBillon(String id) {
		return (CheckDetailsPojo) (this.getSession().selectOne(
				"checkDetails.getCheckDetailsBillon", id));
	}

}
