package com.talkweb.xwzcxt.dal;

import java.util.List;

import com.talkweb.twdpe.dal.batis.spring.SessionDaoSupport;
import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.xwzcxt.pojo.CalendarPojo;



public class CalendarDal extends SessionDaoSupport{
	
	public List<?> getCurrentRecordByPage(Object param,Pagination pagination) {
		 CalendarPojo calendarPojo = null;
		 String action = "calendar.getCalByPage";
		 return this.getSession().selectList(action, param, pagination);
	}
	
	public Object getCalDataBySeq(String seq){
		return this.getSession().selectOne("calendar.getCalDataBySeq", seq);
	}
    
	public int modifyCalData(Object param){
		return this.getSession().update("calendar.updateCalData", param);
	}
}
