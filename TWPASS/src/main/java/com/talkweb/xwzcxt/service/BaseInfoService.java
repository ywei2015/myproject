package com.talkweb.xwzcxt.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.xwzcxt.dal.BaseInfoDal;

@SuppressWarnings("rawtypes")
public class BaseInfoService {
	
	@Autowired
	private BaseInfoDal baseinfodal;
	
	public List<?> getAllArea(Map params){
		return baseinfodal.getAllArea(params);
	}

	public Pagination getPageArea(Map params, Pagination page){
		return baseinfodal.getPageArea(params,page);
	}
}
