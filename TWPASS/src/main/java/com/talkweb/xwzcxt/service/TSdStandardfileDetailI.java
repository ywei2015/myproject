package com.talkweb.xwzcxt.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.xwzcxt.pojo.TSdStandardfileDetailPojo;

public interface TSdStandardfileDetailI {
	public Pagination getAllTSdStandardfileByConditions(Map params, Pagination page);
	public TSdStandardfileDetailPojo getTSdStandardfileDetailById(String id);
	public int addTSdStandardfile(TSdStandardfileDetailPojo t, HttpServletRequest request, HttpServletResponse response);
	public int updateTSdStandardfileById(TSdStandardfileDetailPojo t, HttpServletRequest request, HttpServletResponse response);
	public void deleteTSdStandardfileById(String ids, HttpServletRequest request, HttpServletResponse response);
}