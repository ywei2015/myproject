package com.talkweb.xwzcxt.service;

import java.util.List;
import java.util.Map;

import com.talkweb.xwzcxt.pojo.TErrorInfoPojo;
import com.talkweb.xwzcxt.vo.TErrorInfoVo;

public interface MobileErrInfoService {
	public TErrorInfoVo getTaskErrorInfoDetailByID(String cErrId);
	public Map getTaskErrorFeedbackDetailByID(String cErrId);
	public TErrorInfoPojo getVerifyEvalInfo(String errId);
	public List getTaskErrorFeedbackDetailByLotno(Map params);
	public Map getTaskErrorAffixDetailByID(String cErrId);
	public Map getErrorAffixDetailByIdAndLotNo(Map param);
}