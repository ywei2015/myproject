package com.talkweb.xwzcxt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.xwzcxt.dal.DpAreaDal;
import com.talkweb.xwzcxt.pojo.DpAreaPojo;
import com.talkweb.xwzcxt.service.DpAreaServiceI;

public class DpAreaServiceImpl implements DpAreaServiceI {
	@Autowired
	private DpAreaDal dpAreaDal;

	@Override
	public DpAreaPojo getAreaByID(String areaid) {
		return dpAreaDal.getAreaByID(areaid);
	}
}