package com.talkweb.xwzcxt.dal;

import java.util.Map;

import com.talkweb.twdpe.dal.batis.spring.SessionDaoSupport;
import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.xwzcxt.pojo.MyLogPojo;

public class MyLogDal extends SessionDaoSupport {
	
	public Pagination getAllLogs(Pagination page, Map map){
		page.setList(getSession().selectList("myLog.getAllLogs", map, page)) ;
		return page;
	} 
	
	public int addLogInfo(MyLogPojo logInfo){
		return this.getSession().insert("myLog.addLog",logInfo);
	}
	
//	public int deleteNews(Map logId){
//		return this.getSession().update("myLog.deleteLog",logId);
//	}

	public MyLogPojo getLogInfo(MyLogPojo log){
		return (MyLogPojo) this.getSession().selectOne("myLog.getLogInfo", log);
	}
	public int updateNews(MyLogPojo log) {
		return this.getSession().update("myLog.updateLog",log);
	}
 
}