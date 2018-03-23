package com.talkweb.xwzcxt.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.xwzcxt.pojo.MyLogPojo;

public interface MyLogService {
	/**
	 * @author ZhangZhiheng
	 * @param page 用来分页
	 * @return 查询全部数据日志
	 */
   	public Pagination getAllLogs(Pagination page, Map map);

   	/**
   	 * @author ZhangZhiheng
   	 * @param log 传入ID
   	 * @return 返回日志详情
   	 */
   	public MyLogPojo getLogInfo(MyLogPojo log);
	/**
	 * @param request 
	 * @param response
	 * @param appid 应用ID
	 * @param appname 应用姓名
	 * @param funcgroupid 模块ID
	 * @param funcgroupname 模块名称
	 * @param resourcename 资源名称
	 * @param actiontype 操作类型
	 * @param actiondescription 操作描述
	 * @param actionresult 结果描述
	 * @param statuscode 操作状态
	 * @param datafield 改变的表名
	 * @param oldvalues 改变前数据
	 * @param newvalues 改变后数据
	 * @return
	 */
	public int addLogInfo(HttpServletRequest request, HttpServletResponse response, String appid, String appname, String funcgroupid, String funcgroupname, String resourcename, String actiontype, String actiondescription, String actionresult, String statuscode,String datafield, String oldvalues, String newvalues );
	
//	public int deleteLog();
	
//	public int updateLog();
}
