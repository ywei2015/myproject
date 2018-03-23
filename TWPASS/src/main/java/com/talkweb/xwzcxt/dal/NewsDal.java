package com.talkweb.xwzcxt.dal;

import java.util.List;
import java.util.Map;

import com.talkweb.twdpe.dal.batis.spring.SessionDaoSupport;
import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.xwzcxt.pojo.NewsPojo;

public class NewsDal extends SessionDaoSupport {
	
	public void getNewsInfo(Pagination page,Map tb){
		page.setList(getSession().selectList("news.getNewsInfo", tb, page)) ;
	} 
	
	public int addNewsInfo(NewsPojo newsInfo){
		return this.getSession().insert("news.addNewsInfo",newsInfo);
	}
	
	public int deleteNews(Map newsId){
		return this.getSession().update("news.deleteNews",newsId);
	}

	public int updateNews(NewsPojo news) {
		return this.getSession().update("news.updateNews",news);
	}
 
	public List<?> getNesInfoIds(List<String> ids) {
		return this.getSession().selectList("news.getNesInfoIds",ids);
	}
}