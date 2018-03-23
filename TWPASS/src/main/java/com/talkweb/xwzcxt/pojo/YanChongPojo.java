package com.talkweb.xwzcxt.pojo;

import java.util.Date;

public class YanChongPojo {
	private String c_id; //表主键
	private String c_area_name;
	private String c_area_id;
	private String  c_smokey_loca_id=""; //烟虫编号
	private long   c_smokey ;// 烟虫数
	private String  c_month_loca_id="";//烟蛾编号
	private long c_month;// 蛾数
	private Date  c_info_time;//时间 
	private String c_times ;//第几周
	private String c_userid;
	private Date  c_starttime;
	private Date c_endtime ;
	private String is_error="0"; //导入数据是否成功
	
	public String getC_area_name() {
		return c_area_name;
	}
	public void setC_area_name(String c_area_name) {
		this.c_area_name = c_area_name;
	}
	public String getC_area_id() {
		return c_area_id;
	}
	public void setC_area_id(String c_area_id) {
		this.c_area_id = c_area_id;
	}
	public String getC_smokey_loca_id() {
		return c_smokey_loca_id;
	}
	public void setC_smokey_loca_id(String c_smokey_loca_id) {
		this.c_smokey_loca_id = c_smokey_loca_id;
	}
	public String getC_month_loca_id() {
		return c_month_loca_id;
	}
	public void setC_month_loca_id(String c_month_loca_id) {
		this.c_month_loca_id = c_month_loca_id;
	}
	public Long getC_month() {
		return c_month;
	}
	public void setC_month(Long c_month) {
		this.c_month = c_month;
	}
	public Date getC_info_time() {
		return c_info_time;
	}
	public void setC_info_time(Date c_info_time) {
		this.c_info_time = c_info_time;
	}
	public String getC_times() {
		return c_times;
	}
	public void setC_times(String c_times) {
		this.c_times = c_times;
	}
	public String getC_userid() {
		return c_userid;
	}
	public void setC_userid(String c_userid) {
		this.c_userid = c_userid;
	}
	public Date getC_starttime() {
		return c_starttime;
	}
	public void setC_starttime(Date c_starttime) {
		this.c_starttime = c_starttime;
	}
	public Date getC_endtime() {
		return c_endtime;
	}
	public void setC_endtime(Date c_endtime) {
		this.c_endtime = c_endtime;
	}
	public long getC_smokey() {
		return c_smokey;
	}
	public void setC_smokey(long c_smokey) {
		this.c_smokey = c_smokey;
	}
	public String getIs_error() {
		return is_error;
	}
	public void setIs_error(String is_error) {
		this.is_error = is_error;
	}
	public String getC_id() {
		return c_id;
	}
	public void setC_id(String c_id) {
		this.c_id = c_id;
	}

}
