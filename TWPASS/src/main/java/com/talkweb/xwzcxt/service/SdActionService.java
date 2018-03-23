package com.talkweb.xwzcxt.service;

import java.util.List;

import com.talkweb.xwzcxt.pojo.SdActionPojo;
import com.talkweb.xwzcxt.pojo.SdActionPositionPojo;


public interface SdActionService {

	public int addSdActionToDB(SdActionPojo data);
	public int addSdActionPositionsToDB(List<SdActionPositionPojo> positions);
	public List<SdActionPojo> getAllAction();
	public SdActionPojo getActionInfoById(String id);
	public List<SdActionPositionPojo> getActionPositionInfoById(String id);
	public int modifyActionDataById(SdActionPojo data);
	public int deleteActionById(String[] ids);
	public int deleteActionPosotionById(String id);
	
	public String getOrgIdByName(String name);
}
