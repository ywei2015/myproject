package com.talkweb.xwzcxt.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.xwzcxt.dal.ZyLoginDal;
import com.talkweb.xwzcxt.service.ZyLoginService;

public class ZyLoginServiceImpl implements ZyLoginService{

	@Autowired
	private ZyLoginDal  zyLoginDal ;
	/*@Override
	public String getUserCode(Map param) {
		return zyLoginDal.getUserCode(param);
	}
	@Override
	public Map getUserInfo(Map param) {
		
		return zyLoginDal.getUserInfo(param);
	}*/

	@Override
	public Map zyUserLogin(Map param) {
		String userCode=zyLoginDal.getUserCode(param);
		param.clear();
		if(userCode==null){
			return null;
		}
		param.put("userCode", userCode);
		return zyLoginDal.getUserInfo(param);
		
	}

	@Override
	public String getUserCode() {
		return null;
	}
	
	

}
