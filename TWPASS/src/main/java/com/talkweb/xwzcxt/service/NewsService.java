package com.talkweb.xwzcxt.service;

import java.util.List;
import java.util.Map;

import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.xwzcxt.pojo.NewsPojo;

public interface NewsService {
	public void getNewsInfo(Pagination page,Map tb);

	public int addNewsInfo(NewsPojo newsInfo);
	
	public int deleteNews(Map newsId);
	
	public int updateNews(NewsPojo news);
	
	public List<NewsPojo> getNesInfoIds(List<String> ids);
}
