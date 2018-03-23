package com.talkweb.xwzcxt.dal;

import java.util.List;
import java.util.Map;

import com.talkweb.twdpe.dal.batis.spring.SessionDaoSupport;
import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.xwzcxt.pojo.TPanelPojo;

public class TPanelDal extends SessionDaoSupport {
	public int getPanelInfoCount(Map params) {
		return (Integer) this.getSession().selectOne("TPanel.getPanelInfoCount", params);
	}

	public Pagination getPanelInfoList(Map params, Pagination page) {
		List li = this.getSession().selectList("TPanel.getPanelInfoList", params, page);
		if (li != null)
			page.setList(li);
		return page;
	}

	public int readInformation(Map params) {
		return this.getSession().update("TPanel.readInformation", params);
	}
}