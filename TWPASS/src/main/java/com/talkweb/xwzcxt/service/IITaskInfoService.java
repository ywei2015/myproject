package com.talkweb.xwzcxt.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.xwzcxt.dal.IITaskInfoDal;
import com.talkweb.xwzcxt.pojo.IITaskInfoPojo;


/**
 * TODO(描述这个类的作用)
 * 
 * @author: 2014-7-19，GuveXie，（描述修改内容）
 */
public class IITaskInfoService {

	@Autowired
	private IITaskInfoDal iiTaskinfoDal;
	
/*	public List<IITaskInfoPojo> getTaskinfoList(IITaskInfoPojo taskinfo)
	{
		return iiTaskinfoDal.getTaskinfoList(taskinfo);
	}*/

	public Pagination getTaskinfoList(Map params, Pagination page)
	{
		return iiTaskinfoDal.getTaskinfoList(params, page);
	}
	
	public IITaskInfoPojo getTaskinfoList(Map params)
	{
		return iiTaskinfoDal.getTaskinfoByID(params);
	}
	
	public List<?> getTaskinfoItemList(Map params){
		return iiTaskinfoDal.getTaskinfoItemList(params);
	}

	public List<?> getTaskStepResultList(Map params){
		return iiTaskinfoDal.getTaskStepResultList(params);
	}
	
	public int execTaskinfoConfirm(Map params){
		return iiTaskinfoDal.execTaskinfoConfirm(params);
	}
}
