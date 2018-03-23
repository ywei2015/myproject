package com.talkweb.xwzcxt.service;

import java.util.Map;

public interface ZyLoginService {
	/*public String getUserCode(Map param);
	public Map getUserInfo(Map param);*/
	
	public Map zyUserLogin(Map param);
	
	public String getUserCode();
}
