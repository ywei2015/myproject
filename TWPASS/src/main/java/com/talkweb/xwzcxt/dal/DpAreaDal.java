package com.talkweb.xwzcxt.dal;

import com.talkweb.twdpe.dal.batis.spring.SessionDaoSupport;
import com.talkweb.xwzcxt.pojo.DpAreaPojo;

public class DpAreaDal extends SessionDaoSupport {
	public DpAreaPojo getAreaByID(String areaid) {
		return (DpAreaPojo) this.getSession().selectOne("dpArea.getAreaByID", areaid);
	}
}