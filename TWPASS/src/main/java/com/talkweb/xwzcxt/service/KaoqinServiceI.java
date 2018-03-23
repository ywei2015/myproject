package com.talkweb.xwzcxt.service;

import java.util.List;
import java.util.Map;

import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.xwzcxt.vo.TTaskVo;

public interface KaoqinServiceI {
	public List getKaoqinData(Map params, Pagination page);
	
}