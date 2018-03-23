package com.talkweb.xwzcxt.dal;

import java.util.List;

import com.talkweb.twdpe.dal.batis.spring.SessionDaoSupport;
import com.talkweb.xwzcxt.vo.ExecutabilityVo;

public class TaskExeCountDal extends SessionDaoSupport{
	public List querySelfExecuteData(ExecutabilityVo exevo){
		return  this.getSession().selectList("taskExecuteCount.getSelfTaskExecuteCount",exevo);
	}
	
	public List getSelfChkData(ExecutabilityVo exevo){
		return  this.getSession().selectList("taskExecuteCount.getSelfChkData",exevo);
	}
	
	public List getSelfEvaluateData(ExecutabilityVo exevo){
		return  this.getSession().selectList("taskExecuteCount.getSelfEvaluateData",exevo);
	}

	public List getDepartChkData(ExecutabilityVo executabilityVo,String dataId) {
		if(dataId!=null&&!dataId.equals("null")){
			dataId = dataId.substring(dataId.indexOf("-")+1, dataId.length());
			executabilityVo.setOrgid(dataId);
			return  this.getSession().selectList("taskExecuteCount.getDepartChkDataForDataId",executabilityVo);
		}
		return  this.getSession().selectList("taskExecuteCount.getDepartChkData",executabilityVo);
	}

	public List getDepartEvaluateData(ExecutabilityVo executabilityVo,String dataId) {
		if(dataId!=null&&!dataId.equals("null")){
			dataId = dataId.substring(dataId.indexOf("-")+1, dataId.length());
			executabilityVo.setOrgid(dataId);
			return this.getSession().selectList("taskExecuteCount.getDepartEvaluateDataForDataId",executabilityVo);
		}else{
		return  this.getSession().selectList("taskExecuteCount.getDepartEvaluateData",executabilityVo);
		}
	}

	public List getDepartExecuteData(ExecutabilityVo executabilityVo,String dataId) {
		if(dataId!=null&&!dataId.equals("null")){
			dataId = dataId.substring(dataId.indexOf("-")+1, dataId.length());
			executabilityVo.setOrgid(dataId);
			return  this.getSession().selectList("taskExecuteCount.getDepartExecuteDataForDataId",executabilityVo);
		}else{
		return  this.getSession().selectList("taskExecuteCount.getDepartExecuteData",executabilityVo);
		}
	}

	public List getGroupExecuteData(ExecutabilityVo executabilityVo,String dataId) {
		if(dataId!=null&&!dataId.equals("null")){
			dataId = dataId.substring(dataId.indexOf("-")+1, dataId.length());
			executabilityVo.setOrgid(dataId);
		}
		return  this.getSession().selectList("taskExecuteCount.getGroupExecuteData",executabilityVo);
	}

	public List getGroupChkData(ExecutabilityVo executabilityVo,String dataId) {
		if(dataId!=null&&!dataId.equals("null")){
			dataId = dataId.substring(dataId.indexOf("-")+1, dataId.length());
			executabilityVo.setOrgid(dataId);
		}
		return  this.getSession().selectList("taskExecuteCount.getGroupChkData",executabilityVo);
	}

	public List getGroupEvaluateData(ExecutabilityVo executabilityVo,String dataId) {
		if(dataId!=null&&!dataId.equals("null")){
			dataId = dataId.substring(dataId.indexOf("-")+1, dataId.length());
			executabilityVo.setOrgid(dataId);
		}
		return  this.getSession().selectList("taskExecuteCount.getGroupEvaluateData",executabilityVo);
	}
	
	
	public List getSelfTaskExecuteDetails(ExecutabilityVo exevo){
		return  this.getSession().selectList("taskExecuteCount.getSelfTaskExecuteDetails",exevo);
	}
	
	public List getSelfChkDetails(ExecutabilityVo exevo){
		return  this.getSession().selectList("taskExecuteCount.getSelfChkDetails",exevo);
	}
	
	public List getSelfEvaluateDetails(ExecutabilityVo exevo){
		return  this.getSession().selectList("taskExecuteCount.getSelfEvaluateDetails",exevo);
	}

	public List getDepartChkDetails(ExecutabilityVo executabilityVo) {
		return  this.getSession().selectList("taskExecuteCount.getDepartChkDetails",executabilityVo);
	}

	public List getDepartEvaluateDetails(ExecutabilityVo executabilityVo) {
		return  this.getSession().selectList("taskExecuteCount.getDepartEvaluateDetails",executabilityVo);
	}

	public List getDepartExecuteDetails(ExecutabilityVo executabilityVo) {
		return  this.getSession().selectList("taskExecuteCount.getDepartExecuteDetails",executabilityVo);
	}

	public List getGroupExecuteDetails(ExecutabilityVo executabilityVo) {
		return  this.getSession().selectList("taskExecuteCount.getGroupExecuteDetails",executabilityVo);
	}

	public List getGroupChkDetails(ExecutabilityVo executabilityVo) {
		return  this.getSession().selectList("taskExecuteCount.getGroupChkDetails",executabilityVo);
	}

	public List getGroupEvaluateDetails(ExecutabilityVo executabilityVo) {
		return  this.getSession().selectList("taskExecuteCount.getGroupEvaluateDetails",executabilityVo);
	}
	
}
