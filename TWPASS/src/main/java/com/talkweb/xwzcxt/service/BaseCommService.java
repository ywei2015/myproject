package com.talkweb.xwzcxt.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.xwzcxt.dal.BaseCommDal;

@SuppressWarnings("rawtypes")
public class BaseCommService {

	@Autowired
	private BaseCommDal basecommdal;
	
	public List<?> getPositionByOrgID(Map params){
		return basecommdal.getPositionByOrgID(params);
	}
	
	public List<?> getOrgPositionByParams(Map params){
		return basecommdal.getOrgPositionByParams(params);
	}

	public List<?> getAllMsgType(){
		return basecommdal.getAllMsgType();
	}
}
