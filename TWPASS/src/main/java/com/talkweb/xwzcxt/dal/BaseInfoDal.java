package com.talkweb.xwzcxt.dal;

import java.util.List;
import java.util.Map;

import com.talkweb.twdpe.dal.batis.spring.SessionDaoSupport;
import com.talkweb.twdpe.dal.jdbc.info.Pagination;

@SuppressWarnings("rawtypes")
public class BaseInfoDal extends SessionDaoSupport  {
	
	public List<?> getAllArea(Map params){
		return this.getSession().selectList("iibasic.getAllArea", params);
	}
	
	public Pagination getPageArea(Map params,Pagination page){
		List li = this.getSession().selectList("iibasic.getAllArea", params, page);
		if (li != null)
			page.setList(li);
		return page;
	}
}
