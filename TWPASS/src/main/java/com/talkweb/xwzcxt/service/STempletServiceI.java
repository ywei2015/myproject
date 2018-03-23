package com.talkweb.xwzcxt.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.xwzcxt.pojo.TSTempletPojo;
import com.talkweb.xwzcxt.vo.STempletVo;
import com.talkweb.xwzcxt.vo.TStdtaskplanSeriesVo;

public interface STempletServiceI {
	public Pagination getAllTaskTemplet(Map params, Pagination page) ;
	public int addTaskTemplet(Map params, HttpServletRequest request, HttpServletResponse response);
	public STempletVo getTaskTempletById(String tid);
	public int deleteTaskTempletById(String tid, HttpServletRequest request, HttpServletResponse response);
	public int updateTempletById(Map params, HttpServletRequest request, HttpServletResponse response);
	public int addTempletByCopy(STempletVo sTempletVo, HttpServletRequest request, HttpServletResponse response);
	public int setSTempletVaildByID(Map params, HttpServletRequest request, HttpServletResponse response);
	public int generateRandomTask(Map params, HttpServletRequest request, HttpServletResponse response);
	public Pagination getAllPlanSeriesByPid(Map params, Pagination page);
	public Pagination getAllPlanSeries(Map params, Pagination page);
	public TStdtaskplanSeriesVo getPlanSeriesDetailByID(String pid);
	public int deletePlanSeriesById(String pid);
}