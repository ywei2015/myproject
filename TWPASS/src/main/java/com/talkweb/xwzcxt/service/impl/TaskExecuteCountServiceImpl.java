package com.talkweb.xwzcxt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.xwzcxt.dal.TaskExeCountDal;
import com.talkweb.xwzcxt.service.TaskExecuteCountService;
import com.talkweb.xwzcxt.vo.ExecutabilityVo;

public class TaskExecuteCountServiceImpl implements TaskExecuteCountService{

	@Autowired
	private  TaskExeCountDal executabilityDal ;
	
	
	@Override
	public List querySelfExecuteData(ExecutabilityVo executabilityVo){
		return executabilityDal.querySelfExecuteData(executabilityVo);
	}

	@Override
	public List getSelfChkData(ExecutabilityVo exevo) {
		return executabilityDal.getSelfChkData(exevo);
	}


	@Override
	public List getSelfEvaluateData(ExecutabilityVo exevo) {
		return executabilityDal.getSelfEvaluateData(exevo);
	}

	@Override
	public List getDepartChkData(ExecutabilityVo executabilityVo,String dataId) {
		return executabilityDal.getDepartChkData(executabilityVo,dataId);
	}

	@Override
	public List getDepartEvaluateData(ExecutabilityVo executabilityVo,String dataId) {
		return executabilityDal.getDepartEvaluateData(executabilityVo,dataId);
	}

	@Override
	public List getDepartExecuteData(ExecutabilityVo executabilityVo,String dataId) {
		return executabilityDal.getDepartExecuteData(executabilityVo,dataId);
	}

	@Override
	public List getGroupExecuteData(ExecutabilityVo executabilityVo,String dataId) {
		return executabilityDal.getGroupExecuteData(executabilityVo,dataId);
	}

	@Override
	public List getGroupChkData(ExecutabilityVo executabilityVo,String dataId) {
		return executabilityDal.getGroupChkData(executabilityVo,dataId);
	}

	@Override
	public List getGroupEvaluateData(ExecutabilityVo executabilityVo,String dataId) {
		return executabilityDal.getGroupEvaluateData(executabilityVo,dataId);
	}

	@Override
	public List getSelfTaskExecuteDetails(ExecutabilityVo exevo) {
		return executabilityDal.getSelfTaskExecuteDetails( exevo);
	}

	@Override
	public List getSelfChkDetails(ExecutabilityVo exevo) {
		return executabilityDal.getSelfChkDetails( exevo);
	}

	@Override
	public List getSelfEvaluateDetails(ExecutabilityVo exevo) {
		return executabilityDal.getSelfEvaluateDetails( exevo);
	}

	@Override
	public List getDepartChkDetails(ExecutabilityVo executabilityVo) {
		return executabilityDal.getDepartChkDetails( executabilityVo);
	}

	@Override
	public List getDepartEvaluateDetails(ExecutabilityVo executabilityVo) {
		return executabilityDal.getDepartEvaluateDetails( executabilityVo);
	}

	@Override
	public List getDepartExecuteDetails(ExecutabilityVo executabilityVo) {
		return executabilityDal.getDepartExecuteDetails( executabilityVo);
	}

	@Override
	public List getGroupExecuteDetails(ExecutabilityVo executabilityVo) {
		return executabilityDal.getGroupExecuteDetails( executabilityVo);
	}

	@Override
	public List getGroupChkDetails(ExecutabilityVo executabilityVo) {
		return executabilityDal.getGroupChkDetails( executabilityVo);
	}

	@Override
	public List getGroupEvaluateDetails(ExecutabilityVo executabilityVo) {
		return executabilityDal.getGroupEvaluateDetails( executabilityVo);
	}
	

}
