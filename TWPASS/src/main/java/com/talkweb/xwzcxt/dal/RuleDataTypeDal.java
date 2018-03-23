package com.talkweb.xwzcxt.dal;

import java.util.List;

import com.talkweb.twdpe.dal.batis.spring.SessionDaoSupport;

public class RuleDataTypeDal extends SessionDaoSupport {
	public String getDataTypeNameByID(String cTracefunId) {
		return (this.getSession().selectOne("tRuleDataType.getDataTypeNameByID", cTracefunId)).toString();
	}

	public List getRuleDataTypeSelect() {
		return this.getSession().selectList("tRuleDataType.getRuleDataTypeSelect");
	}
}