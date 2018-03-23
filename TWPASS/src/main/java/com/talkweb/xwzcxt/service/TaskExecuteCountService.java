package com.talkweb.xwzcxt.service;

import java.util.List;

import com.talkweb.xwzcxt.vo.ExecutabilityVo;

public interface TaskExecuteCountService {
	public List querySelfExecuteData(ExecutabilityVo executabilityVo);
	public List getSelfChkData(ExecutabilityVo exevo);
	public List getSelfEvaluateData(ExecutabilityVo exevo);
	public List getDepartChkData(ExecutabilityVo executabilityVo,String dataId);
	public List getDepartEvaluateData(ExecutabilityVo executabilityVo,String dataId);
	public List getDepartExecuteData(ExecutabilityVo executabilityVo,String dataId);
	public List getGroupExecuteData(ExecutabilityVo executabilityVo,String dataId);
	public List getGroupChkData(ExecutabilityVo executabilityVo,String dataId);
	public List getGroupEvaluateData(ExecutabilityVo executabilityVo,String dataId);
	
	
	public List getSelfTaskExecuteDetails(ExecutabilityVo exevo);
	public List getSelfChkDetails(ExecutabilityVo exevo);
	public List getSelfEvaluateDetails(ExecutabilityVo exevo);
	public List getDepartChkDetails(ExecutabilityVo executabilityVo) ;
	public List getDepartEvaluateDetails(ExecutabilityVo executabilityVo);
	public List getDepartExecuteDetails(ExecutabilityVo executabilityVo) ;
	public List getGroupExecuteDetails(ExecutabilityVo executabilityVo) ;
	public List getGroupChkDetails(ExecutabilityVo executabilityVo);
	public List getGroupEvaluateDetails(ExecutabilityVo executabilityVo);
	

}
