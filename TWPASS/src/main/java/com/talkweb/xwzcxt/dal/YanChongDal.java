package com.talkweb.xwzcxt.dal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.talkweb.twdpe.dal.batis.spring.SessionDaoSupport;
import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.xwzcxt.pojo.YanChongPojo;

public class YanChongDal extends SessionDaoSupport{


	public String getAreaNameById(Map map) {
		// TODO Auto-generated method stub
		 return (String)this.getSession().selectOne("yanchong.getAreaNameById", map);
	}

	public void insertYcImportDate(YanChongPojo yanchong) {
		// TODO Auto-generated method stub
		this.getSession().insert("yanchong.insertYcImportDate", yanchong);
	}

	public List<YanChongPojo> getYImportData(HashMap map, Pagination pagination) {
		// TODO Auto-generated method stub
		return (List<YanChongPojo>)this.getSession().selectList("yanchong.getYImportData", map,pagination);
	}

	public void deleteImportData(String[] s) {
		// TODO Auto-generated method stub
		this.getSession().delete("yanchong.deleteImportData",s);
	}

}
