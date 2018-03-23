package com.talkweb.xwzcxt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.xwzcxt.dal.CheckDetailsDal;
import com.talkweb.xwzcxt.pojo.CheckDetailsPojo;

public class CheckDetailsImpl {
	@Autowired
	private  CheckDetailsDal checkDetailsDal;
	
	public CheckDetailsPojo  getCheckDetailsBillon(String id) {
		CheckDetailsPojo a=( CheckDetailsPojo)checkDetailsDal.getCheckDetailsBillon(id);	    
		return  a;
	}

}
