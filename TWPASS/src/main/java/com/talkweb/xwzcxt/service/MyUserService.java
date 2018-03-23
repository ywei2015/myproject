package com.talkweb.xwzcxt.service;

public interface MyUserService {
	public boolean changePassword(String userid,String newPassword);
	public boolean checkUserCodePassword(String userid,String oldPassword);
}
