package com.talkweb.xwzcxt.pojo;

import java.util.Date;

/**
 * TODO(标准信息POJO,适用新旧两个版本)
 * @author: 2014-08-30，GuveXie，（描述修改内容）
 */
public class CommStdInfo {
	private String c_stditem_id;
	private String c_stditem_name;
	private String c_stdversion; //0-旧版 ; 1-新版
	private String c_dowhat;
	private String c_exec_std;
	private String c_err_std; //异常怎么处置（执行环节）
	private String c_err_std2; //异常怎么处置（验证环节）
	private String c_check_std;
	private String c_review_std;
	private String c_managestd;
	private String c_examstd;
	private String c_actnodetype;
	private String c_creator;
	private Date c_createtime;
	private String c_lastupdator;
	private Date c_lastupadetime;

	public void setC_stditem_id(String value) {
		c_stditem_id = value;
	}

	public String getC_stditem_id() {
		return c_stditem_id;
	}

	public void setC_stditem_name(String value) {
		c_stditem_name = value;
	}

	public String getC_stditem_name() {
		return c_stditem_name;
	}

	public void setC_stdversion(String value) {
		c_stdversion = value;
	}

	public String getC_stdversion() {
		return c_stdversion;
	}

	public void setC_dowhat(String value) {
		c_dowhat = value;
	}

	public String getC_dowhat() {
		return c_dowhat;
	}

	public void setC_exec_std(String value) {
		c_exec_std = value;
	}

	public String getC_exec_std() {
		return c_exec_std;
	}

	public void setC_err_std(String value) {
		c_err_std = value;
	}

	public String getC_err_std() {
		return c_err_std;
	}

	public String getC_err_std2() {
		return c_err_std2;
	}

	public void setC_err_std2(String c_err_std2) {
		this.c_err_std2 = c_err_std2;
	}

	public void setC_check_std(String value) {
		c_check_std = value;
	}

	public String getC_check_std() {
		return c_check_std;
	}

	public void setC_review_std(String value) {
		c_review_std = value;
	}

	public String getC_review_std() {
		return c_review_std;
	}

	public String getC_managestd() {
		return c_managestd;
	}

	public void setC_managestd(String c_managestd) {
		this.c_managestd = c_managestd;
	}

	public String getC_examstd() {
		return c_examstd;
	}

	public void setC_examstd(String c_examstd) {
		this.c_examstd = c_examstd;
	}

	public String getC_actnodetype() {
		return c_actnodetype;
	}

	public void setC_actnodetype(String c_actnodetype) {
		this.c_actnodetype = c_actnodetype;
	}

	public void setC_creator(String value) {
		c_creator = value;
	}

	public String getC_creator() {
		return c_creator;
	}

	public void setC_createtime(Date value) {
		c_createtime = value;
	}

	public Date getC_createtime() {
		return c_createtime;
	}

	public void setC_lastupdator(String value) {
		c_lastupdator = value;
	}

	public String getC_lastupdator() {
		return c_lastupdator;
	}

	public void setC_lastupadetime(Date value) {
		c_lastupadetime = value;
	}

	public Date getC_lastupadetime() {
		return c_lastupadetime;
	}
}