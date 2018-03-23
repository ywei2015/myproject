package com.talkweb.xwzcxt.dal;

import java.util.List;
import java.util.Map;

import com.talkweb.twdpe.dal.batis.spring.SessionDaoSupport;

public class MapDal extends SessionDaoSupport {
	public List<Map> getAreaIndex(Map params) {
		return this.getSession().selectList("Map.getAreaIndex", params);
	}

	public int getAreaStatus(Map params) {
		return (Integer) this.getSession().selectOne("Map.getAreaStatus", params);
	}

	public List<Map> getAreaIcon(Map params) {
		return this.getSession().selectList("Map.getAreaIcon", params);
	}

	public Map getAreaIconDetailById(Map params) {
		return (Map) this.getSession().selectOne("Map.getAreaIcon", params);
	}
}