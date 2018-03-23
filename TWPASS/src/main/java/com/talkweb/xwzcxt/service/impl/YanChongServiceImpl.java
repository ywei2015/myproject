package com.talkweb.xwzcxt.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.xwzcxt.dal.YanChongDal;
import com.talkweb.xwzcxt.pojo.YanChongPojo;
import com.talkweb.xwzcxt.service.YanChongService;

public class YanChongServiceImpl implements YanChongService{
	  @Autowired
      private YanChongDal yanchongdal;
	  private YanChongDal ychongdal=new YanChongDal();

	@Override
	public String getAreaNameById(Map map) {
		
		return ychongdal.getAreaNameById(map);
	}

	@Override
	public void insertYcImportDate(YanChongPojo yanchong) {
		// TODO Auto-generated method stub
		yanchongdal.insertYcImportDate(yanchong);
	}

	@Override
	public List<YanChongPojo> getYImportData(HashMap map, Pagination pagination) {
		// TODO Auto-generated method stub
		return yanchongdal.getYImportData(map,pagination);
	}

	@Override
	public void deleteImportData(String[] s) {
		// TODO Auto-generated method stub
		yanchongdal.deleteImportData(s);
	}

}
