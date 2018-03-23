package com.talkweb.xwzcxt.service;

import java.util.Map;

import com.talkweb.twdpe.dal.jdbc.info.Pagination;

public interface TPanelServiceI {
	public Map getPanelInfoCount(Map params);
	public Pagination getPanelInfoList(Map params, Pagination page);
	public int readInformation(Map params);
}