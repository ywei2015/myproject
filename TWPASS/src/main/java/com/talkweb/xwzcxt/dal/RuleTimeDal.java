package com.talkweb.xwzcxt.dal;

import java.util.List;
import java.util.Map;

import com.talkweb.twdpe.dal.batis.spring.SessionDaoSupport;
import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.xwzcxt.pojo.TRuleTime;

public class RuleTimeDal extends SessionDaoSupport {

	public TRuleTime getRuleTimeByID(String id) {
		return (TRuleTime) (this.getSession().selectOne(
				"tRuleTime.getTimeRuleByID", id));
	}

	public Pagination getAllRuleTime(Map params, Pagination page) {
		List li = null;
		if (page != null) {
			li = this.getSession().selectList(
					"tRuleTime.getTimeRuleByConditions", params, page);
		} else if (params != null) {
			page = new Pagination();
			li = this.getSession().selectList(
					"tRuleTime.getTimeRuleByConditions", params);
		} else {
			page = new Pagination();
			li = this.getSession().selectList(
					"tRuleTime.getTimeRuleByConditions");
		}
		if (li != null) {
			page.setList(li);
		}
		return page;
	}

	public int addTimeRule(Map params) {
		return this.getSession().insert("tRuleTime.addTimeRule", params);
	}

	public int updateTimeRuleById(Map params) {
		return this.getSession().insert("tRuleTime.updateTimeRuleById", params);
	}

	public int deleteTimeRuleById(String id) {
		return this.getSession().delete("tRuleTime.deleteTimeRuleById", id);
	}
}
