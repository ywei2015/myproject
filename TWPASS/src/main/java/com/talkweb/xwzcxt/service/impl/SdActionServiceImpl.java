package com.talkweb.xwzcxt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.xwzcxt.dal.ImportFileDBDal;
import com.talkweb.xwzcxt.dal.SdActionDal;
import com.talkweb.xwzcxt.pojo.SdActionPojo;
import com.talkweb.xwzcxt.pojo.SdActionPositionPojo;
import com.talkweb.xwzcxt.service.SdActionService;

public class SdActionServiceImpl implements SdActionService {
	
	@Autowired
    private SdActionDal sdActionDal;

	@Override
	public int addSdActionToDB(SdActionPojo data) {
		return sdActionDal.addSdActionToDB(data);
	}

	@Override
	public List<SdActionPojo> getAllAction() {
		return (List<SdActionPojo>) sdActionDal.getAllAction();
	}

	@Override
	public SdActionPojo getActionInfoById(String id) {
		return (SdActionPojo) sdActionDal.getActionInfoById(id);
	}

	@Override
	public int modifyActionDataById(SdActionPojo data) {
		return sdActionDal.modifyActionDataById(data);
	}

	@Override
	public int deleteActionById(String[] ids) {
		return sdActionDal.deleteActionById(ids);
	}

	@Override
	public List<SdActionPositionPojo> getActionPositionInfoById(String id) {
		return (List<SdActionPositionPojo>) sdActionDal.getActionPositionInfoById(id);
	}

	@Override
	public int addSdActionPositionsToDB(List<SdActionPositionPojo> positions) {
		return sdActionDal.addSdActionPositionsToDB(positions);
	}

	@Override
	public int deleteActionPosotionById(String id) {
		return sdActionDal.deleteActionPosotionById(id);
	}

	@Override
	public String getOrgIdByName(String name) {
		return sdActionDal.getOrgIdByName(name);
	}

}
