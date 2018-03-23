package com.talkweb.xwzcxt.service;

import java.util.List;

import com.talkweb.xwzcxt.vo.DpPositionVo;

public interface DpPositionServiceI {
	public List<DpPositionVo> getDpPositionByOrgId(String id);
	public List<DpPositionVo> getPositionByPositionID(String pid);
	public DpPositionVo getPositionByUserID(String uid);
	public String getPositionNameByID(String pid);
	public String getUserNameByID(String pid);
	public List<DpPositionVo> getAllPositon();
	public String[] getDepartByPositionID(String pid);
	
	public String getITMinisterOrgId(String orgId);
}

