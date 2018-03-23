package com.talkweb.xwzcxt.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.xwzcxt.pojo.YanChongPojo;

public interface YanChongService {

	String getAreaNameById(Map map);

	void insertYcImportDate(YanChongPojo yanchong);

	List<YanChongPojo> getYImportData(HashMap map, Pagination pagination);

	void deleteImportData(String[] s);

}
