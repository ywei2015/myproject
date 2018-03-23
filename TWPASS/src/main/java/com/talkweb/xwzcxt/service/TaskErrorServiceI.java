package com.talkweb.xwzcxt.service;

import java.util.List;
import java.util.Map;

import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.xwzcxt.vo.TErrorInfoVo;

public interface TaskErrorServiceI {
	public Pagination getAllTaskErrorInfo(Map params, Pagination page);
	public List<Map> getAllTaskErrorInfoWithoutPage(Map params);
	public TErrorInfoVo getTaskErrorInfoDetailByID(String cErrId);
	public void deleteTaskErrorById(String ids);
	public Map getTaskErrorFeedbackDetailByID(String cErrId);
	public List getTaskErrorFeedbackDetailByLotno(Map params);
	//public String getErreanameById(Map map);
}