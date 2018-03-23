package com.talkweb.xwzcxt.dal;

import java.util.List;
import java.util.Map;

import com.talkweb.twdpe.dal.batis.spring.SessionDaoSupport;
import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.xwzcxt.pojo.TRulePosition;


public class RulePositionDal extends SessionDaoSupport {

	public TRulePosition getRulePositionByID(String id)
	{
		return (TRulePosition)(this.getSession().selectOne("tRulePosition.selectPositionRuleByID", id));
	}
	
	
	public Pagination getAllRulePositions(Map params, Pagination page) {
		List li = this.getSession().selectList("tRulePosition.getAllRulePositionsByConditions",params,page);
		if (li != null)
			page.setList(li);
		return page;
	}
	
	public int addRulePosition(Map params)
	{
		return this.getSession().insert("tRulePosition.addRulePosition", params);
	}
}
