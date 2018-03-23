package com.talkweb.xwzcxt.vo;

import java.util.Date;

public class AttendanceVo {
	private String c_id;
	private long c_userid;
	private String c_usercode;
	private String c_dep_name;
	private String c_username;
	private String c_tag_ip;
	private String c_start_time;
	private String c_end_time;
	private String c_attend_time;//考勤时间
	private String c_attend_date;//考勤日期
	
	private int c_attend_times;

	public String getC_id() {
		return c_id;
	}

	public void setC_id(String c_id) {
		this.c_id = c_id;
	}

	public long getC_userid() {
		return c_userid;
	}

	public void setC_userid(long c_userid) {
		this.c_userid = c_userid;
	}

	public String getC_usercode() {
		return c_usercode;
	}

	public void setC_usercode(String c_usercode) {
		this.c_usercode = c_usercode;
	}

	public String getC_username() {
		return c_username;
	}

	public void setC_username(String c_username) {
		this.c_username = c_username;
	}

	public String getC_tag_ip() {
		return c_tag_ip;
	}

	public void setC_tag_ip(String c_tag_ip) {
		this.c_tag_ip = c_tag_ip;
	}

	public String getC_attend_time() {
		return c_attend_time;
	}

	public void setC_attend_time(String c_attend_time) {
		this.c_attend_time = c_attend_time;
	}

	public String getC_attend_date() {
		return c_attend_date;
	}

	public void setC_attend_date(String c_attend_date) {
		this.c_attend_date = c_attend_date;
	}



	public String getC_dep_name() {
		return c_dep_name;
	}

	public void setC_dep_name(String c_dep_name) {
		this.c_dep_name = c_dep_name;
	}

	public int getC_attend_times() {
		return c_attend_times;
	}

	public void setC_attend_times(int c_attend_times) {
		this.c_attend_times = c_attend_times;
	}

	public String getC_start_time() {
		return c_start_time;
	}

	public void setC_start_time(String c_start_time) {
		this.c_start_time = c_start_time;
	}

	public String getC_end_time() {
		return c_end_time;
	}

	public void setC_end_time(String c_end_time) {
		this.c_end_time = c_end_time;
	}
	
	
}
