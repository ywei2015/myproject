package com.talkweb.xwzcxt.service;

import java.util.List;
import java.util.Map;

import com.talkweb.xwzcxt.pojo.TSchedulingPojo;
import com.talkweb.xwzcxt.vo.PositionTreeVo;
import com.talkweb.xwzcxt.vo.TaskSchedularVo;

public interface TaskSchedularService {

	public List getPersonSchedular(Map params);
	public List getUserList(Map params );
	public List getOrganization();
	public int addShift(TSchedulingPojo param);
	public List getShiftName();
	public TSchedulingPojo getShiftUserInfo(Map map);
	public int updateUserShift(Map params);
	public Map getOneDayShift(Map params);
	public int deleteOneDayShift(Map params);
	
	public List getAllPositionTreeNodes( );
	public List getDymanicPositionTreeNodes(PositionTreeVo node);
	public List getDepartmentTree(String type);
	public TSchedulingPojo getShiftByUserDate(Map params);
}
