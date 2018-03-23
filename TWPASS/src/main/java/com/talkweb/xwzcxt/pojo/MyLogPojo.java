package com.talkweb.xwzcxt.pojo;

import java.util.Date;

import com.talkweb.twdpe.core.data.BasePOJO;
/**
 * 
 * @author zhangzhiheng
 * 对应DP_LOG_SYS表
 *
 */
public class MyLogPojo extends BasePOJO {
	private String logid;// 日志id 
	private String sourceip;// 客户端ip
	private String targetip;// 服务器ip 
	private Date auditdate;// 操作时间 
	private String account;// 用户编号
	private String accountname;// 用户姓名 
	private String accounttype;// 用户类型 
	private String appid;// 应用id 
	private String appname;// 应用姓名 
	private String funcgroupid;// 模块id 
	private String funcgroupname;// 模块名称
	private String resourceurl;// 资源链接
	private String resourcename;// 资源名称
	private String actiontype;// 操作类型
	private String actiondescription;// 操作描述
	private String actionresult;// 结果描述
	private String statuscode;// 操作状态
	private String statusName;//操作状态描述
	private String datafield;// 改变的表名
	private String oldvalues;// 改变前数据
	private String newvalues;// 改变后数据 
	
	public static String add = "增加";
	public static String del = "删除";
	public static String change = "修改";
	
	
	public String getStatusName() {
		String r = "1".equals(this.statuscode)? "成功" : "失败";
		return r;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getLogid() {
		return logid;
	}
	public void setLogid(String logid) {
		this.logid = logid;
	}
	public String getSourceip() {
		return sourceip;
	}
	public void setSourceip(String sourceip) {
		this.sourceip = sourceip;
	}
	public String getTargetip() {
		return targetip;
	}
	public void setTargetip(String targetip) {
		this.targetip = targetip;
	}
	public Date getAuditdate() {
		return auditdate;
	}
	public void setAuditdate(Date auditdate) {
		this.auditdate = auditdate;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getAccountname() {
		return accountname;
	}
	public void setAccountname(String accountname) {
		this.accountname = accountname;
	}
	public String getAccounttype() {
		return accounttype;
	}
	public void setAccounttype(String accounttype) {
		this.accounttype = accounttype;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getAppname() {
		return appname;
	}
	public void setAppname(String appname) {
		this.appname = appname;
	}
	public String getFuncgroupid() {
		return funcgroupid;
	}
	public void setFuncgroupid(String funcgroupid) {
		this.funcgroupid = funcgroupid;
	}
	public String getFuncgroupname() {
		return funcgroupname;
	}
	public void setFuncgroupname(String funcgroupname) {
		this.funcgroupname = funcgroupname;
	}
	public String getResourceurl() {
		return resourceurl;
	}
	public void setResourceurl(String resourceurl) {
		this.resourceurl = resourceurl;
	}
	public String getResourcename() {
		return resourcename;
	}
	public void setResourcename(String resourcename) {
		this.resourcename = resourcename;
	}
	public String getActiontype() {
		return actiontype;
	}
	public void setActiontype(String actiontype) {
		this.actiontype = actiontype;
	}
	public String getActiondescription() {
		return actiondescription;
	}
	public void setActiondescription(String actiondescription) {
		this.actiondescription = actiondescription;
	}
	public String getActionresult() {
		return actionresult;
	}
	public void setActionresult(String actionresult) {
		this.actionresult = actionresult;
	}
	public String getStatuscode() {
		return statuscode;
	}
	public void setStatuscode(String statuscode) {
		this.statuscode = statuscode;
	}
	public String getDatafield() {
		return datafield;
	}
	public void setDatafield(String datafield) {
		this.datafield = datafield;
	}
	public String getOldvalues() {
		return oldvalues;
	}
	public void setOldvalues(String oldvalues) {
		this.oldvalues = oldvalues;
	}
	public String getNewvalues() {
		return newvalues;
	}
	public void setNewvalues(String newvalues) {
		this.newvalues = newvalues;
	}
	

}
