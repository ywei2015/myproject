package com.talkweb.xwzcxt.service;

import java.util.Map;

import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.xwzcxt.vo.RulePositionVo;

public interface RulePositionServiceI {
	public RulePositionVo getRulePositionByID(String id);

	public Pagination getAllRulePositionsByConditions(Map params,
			Pagination page);

	public int addRulePosition(Map params);
}
