package com.talkweb.xwzcxt.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.xwzcxt.dal.CheckCardDal;
import com.talkweb.xwzcxt.service.CheckCardService;

public class CheckCardServiceImp implements CheckCardService {
	@Autowired
	private CheckCardDal checkCardDal;
	
	@Override
	public List getJobObjInfo(Map param) {
		return checkCardDal.getJobObjInfo(param);
	}

	@Override
	public List getTaskInfos(Map param) {
		return checkCardDal.getTaskInfos(param);
	}

	@Override
	public List getTaskSteps(Map param) {
		return checkCardDal.getTaskSteps(param);
	}

}
