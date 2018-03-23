package com.talkweb.xwzcxt.dal;

import java.util.List;
import java.util.Map;

import com.talkweb.twdpe.dal.batis.spring.SessionDaoSupport;
import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.xwzcxt.pojo.IITaskInfoPojo;

/**
 * TODO(描述这个类的作用)
 * 
 * @author: 2014-7-18，GuveXie，（描述修改内容）
 */

@SuppressWarnings("rawtypes")
public class IITaskInfoDal extends SessionDaoSupport {

	public Pagination getTaskinfoList(Map params, Pagination page) {
		List li = this.getSession().selectList("iitaskinfo.getTaskinfoList", params, page);
		if (li != null)
			page.setList(li);
		return page;
	}
	
	public IITaskInfoPojo getTaskinfoByID(Map params){
		return (IITaskInfoPojo)this.getSession().selectOne("iitaskinfo.getTaskinfoList", params);
	}

	public List<?> getTaskinfoItemList(Map params){
		return (List<?>) this.getSession().selectList("iitaskinfo.getTaskinfoItemList", params);
	}
	
	public List<?> getTaskStepResultList(Map params){
		return (List<?>) this.getSession().selectList("iitaskinfo.getTaskStepResultList", params);
	}
	
	public int execTaskinfoConfirm(Map params){
		return this.getSession().update("P_TWXWZC_II_CONFIRM_TASKINFO", params);
	}
}
