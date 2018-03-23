package com.talkweb.xwzcxt.dal;

import java.util.HashMap;
import java.util.Map;

import com.talkweb.twdpe.dal.batis.spring.SessionDaoSupport;

public class MyUserDal extends SessionDaoSupport {

	public boolean changePassword(String userCode,
			String newPassword) {
		Map map=new HashMap();
		map.put("userCode", userCode);
		map.put("newPassword", newPassword);
		this.getSession().update("MyUser.changePassword",map);
		return  true;
	}

	public boolean checkUserCodePassword(String userCode, String oldPassword) {
		Map map=new HashMap();
		map.put("userCode", userCode);
		map.put("oldPassword", oldPassword);
		Object o=this.getSession().selectOne("MyUser.checkUserCodePassword",map);
		if(o!=null){
			return true;
		}else{
			return false;
		}
	}

}
