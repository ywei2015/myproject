package com.talkweb.xwzcxt.service.impl;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.xwzcxt.dal.TaskSchedularDal;
import com.talkweb.xwzcxt.pojo.TSchedulingPojo;
import com.talkweb.xwzcxt.service.TaskSchedularService;
import com.talkweb.xwzcxt.vo.PositionTreeVo;
import com.talkweb.xwzcxt.vo.TaskSchedularVo;

public class TaskSchedularServiceImpl implements TaskSchedularService{

	@Autowired
	private TaskSchedularDal  taskSchedularDal;
	
	
	@Override
	public List getPersonSchedular(Map params) {
		return taskSchedularDal.getPersonSchedular(params);
	}

	@Override
	public List getUserList(Map params) {
		return taskSchedularDal.getUserList(params);
	}

	@Override
	public List getOrganization( ) {
		return taskSchedularDal.getOrganization( );
	}

	@Override
	public int addShift(TSchedulingPojo param) {
		return taskSchedularDal.addShift(param);
	}

	@Override
	public List getShiftName() {
		return taskSchedularDal.getShiftName();
	}

	@Override
	public TSchedulingPojo getShiftUserInfo(Map map) {
		return taskSchedularDal.getShiftUserInfo(map);
	}

	@Override
	public int updateUserShift(Map params) {
		return taskSchedularDal.updateUserShift(params);
	}

	@Override
	public TSchedulingPojo getShiftByUserDate(Map params) {
		return taskSchedularDal.getShiftByUserDate(params);
	}

	@Override
	public Map getOneDayShift(Map params) {
		return taskSchedularDal.getOneDayShift(params);
	}
	
	@Override
	public int deleteOneDayShift(Map params) {
		return taskSchedularDal.deleteOneDayShift(params);
	}

	@Override
	public List getAllPositionTreeNodes() {
		return  taskSchedularDal.getAllPostionTreeNodes();
	}

	@Override
	public List getDymanicPositionTreeNodes(PositionTreeVo node) {
		return taskSchedularDal.getDymanicPositionTreeNodes(node);
	}

	@Override
	public List getDepartmentTree(String type) {
		return taskSchedularDal.getDepartmentTree(type);
	}
	
	

}
