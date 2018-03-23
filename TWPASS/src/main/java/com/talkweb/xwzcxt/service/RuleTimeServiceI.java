package com.talkweb.xwzcxt.service;

import java.util.Map;

import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.xwzcxt.vo.RuleTimeVo;

public interface RuleTimeServiceI {
	public RuleTimeVo getRuleTimeByID(String id);
	public Pagination getAllRuleTime(Map params, Pagination page);
	public int addTimeRule(Map params);
	public int updateTimeRuleById(Map params);
	public int deleteTimeRuleById(String id);
}
