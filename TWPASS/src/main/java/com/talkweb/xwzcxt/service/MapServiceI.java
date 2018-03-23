package com.talkweb.xwzcxt.service;

import java.util.List;
import java.util.Map;

public interface MapServiceI {
	public List<Map> getAreaInfo(Map params);
	public List<Map> getAreaIcon(Map params);
	public Map getAreaIconDetailById(Map params);
}