package com.talkweb.xwzcxt.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.twdpe.web.session.main.Session;
import com.talkweb.twdpe.web.session.util.SessionFactory;
import com.talkweb.xwzcxt.dal.MyLogDal;
import com.talkweb.xwzcxt.pojo.MyLogPojo;
import com.talkweb.xwzcxt.service.MyLogService;

public class MyLogServiceImpl implements MyLogService {
	@Autowired
	private MyLogDal dal;

	/**
	 * @param request 
	 * @param appid 应用ID
	 * @param appname 应用姓名
	 * @param funcgroupid 模块ID
	 * @param funcgroupname 模块名称
	 * @param resourcename 资源名称
	 * @param actiontype 操作类型
	 * @param actiondescription 操作描述
	 * @param actionresult 结果描述
	 * @param statuscode 操作状态
	 * @param oldvalues 改变前数据
	 * @param newvalues 改变后数据
	 * @return 0失败 1成功
	 */
	@Override
	public int addLogInfo(HttpServletRequest request, HttpServletResponse response, String appid,
			String appname, String funcgroupid, String funcgroupname,
			String resourcename, String actiontype, String actiondescription,
			String actionresult, String statuscode,String datafield, String oldvalues,
			String newvalues) {
		// TODO Auto-generated method stub
		MyLogPojo pojo = new MyLogPojo();
		//获取服务器IP
		String targetIp = request.getHeader("Host");
		//获取客户端IP
		String sourceIp = request.getRemoteAddr();
		//获取资源链接
		String resourceURL = request.getRequestURL().toString();
		//获取session
		Session session = SessionFactory.getInstance(request, response);
		//获取用户Map
		Map user = (Map)session.getAttribute("USERSESSION");
		//获取用户ID
		String account = user.get("id").toString();
		//获取用户姓名
		String accountName = (String)user.get("name");
		//获取用户类型
		String accountTpye = (String)user.get("code");
		//生成日志ID
		String logId = UUID.randomUUID().toString();
		//生成操作时间
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date auditDate = null;
		try {
			auditDate = df.parse(df.format(new Date()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		pojo.setLogid(logId);
		pojo.setSourceip(sourceIp);
		pojo.setTargetip(targetIp);
		pojo.setAuditdate(auditDate);
		pojo.setAccount(account);
		pojo.setAccountname(accountName);
		pojo.setAccounttype(accountTpye);
		pojo.setAppid(appid);
		pojo.setAppname(appname);
		pojo.setFuncgroupid(funcgroupid);
		pojo.setFuncgroupname(funcgroupname);
		pojo.setResourceurl(resourceURL);
		pojo.setResourcename(resourcename);
		pojo.setActiontype(actiontype);
		pojo.setActiondescription(actiondescription);
		pojo.setActionresult(actionresult);
		pojo.setStatuscode(statuscode);
		pojo.setDatafield(datafield);
		pojo.setOldvalues(oldvalues);
		pojo.setNewvalues(newvalues);
		
		int ret = 0;
		ret = dal.addLogInfo(pojo);

		return ret;
	}

	public MyLogDal getDal() {
		return dal;
	}

	public void setDal(MyLogDal dal) {
		this.dal = dal;
	}

	@Override
	public Pagination getAllLogs(Pagination page, Map map) {
		return dal.getAllLogs(page, map);
	}

	@Override
	public MyLogPojo getLogInfo(MyLogPojo log) {
		// TODO Auto-generated method stub
		return dal.getLogInfo(log);
	}
	
}
