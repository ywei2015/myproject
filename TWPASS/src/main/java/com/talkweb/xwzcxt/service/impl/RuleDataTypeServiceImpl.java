package com.talkweb.xwzcxt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.xwzcxt.dal.RuleDataTypeDal;
import com.talkweb.xwzcxt.service.RuleDataTypeServiceI;

public class RuleDataTypeServiceImpl implements RuleDataTypeServiceI {
	@Autowired
	private RuleDataTypeDal ruleDataTypeDal;

	@Override
	public String getRuleTimeByID(String cTracefunId) {
		return ruleDataTypeDal.getDataTypeNameByID(cTracefunId);
	}

	@Override
	public List getRuleDataTypeSelect() {
		return ruleDataTypeDal.getRuleDataTypeSelect();
	}
}