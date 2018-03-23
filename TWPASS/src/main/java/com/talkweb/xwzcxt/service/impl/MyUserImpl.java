package com.talkweb.xwzcxt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.xwzcxt.dal.MyUserDal;
import com.talkweb.xwzcxt.service.MyUserService;

public class MyUserImpl implements MyUserService {
	
	@Autowired
	private MyUserDal myUserDal;
	@Override
	public boolean changePassword(String userid,
			String newPassword) {
		return myUserDal.changePassword(userid,newPassword);
	}
	@Override
	public boolean checkUserCodePassword(String userid, String oldPassword) {
		return myUserDal.checkUserCodePassword(userid,oldPassword);
	}

}
