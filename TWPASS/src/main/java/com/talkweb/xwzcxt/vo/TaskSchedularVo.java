package com.talkweb.xwzcxt.vo;

import java.util.Calendar;
import java.util.Date;




public class TaskSchedularVo {
    private int dayOfWeek;   //星期几
    private String cUserName;  //用户名
    private String cShiftName;  //班次
    private Date cDate;   //日期
    private String userid;
    private String c_theory_isworkday;
    private String c_fact_isworkday;
    private String c_work_date;
    private String c_userid;
    private String c_username;
    private String c_org_id;
    private int c_datetype;
    private int c_shift_id;
    private String c_shift_name;
    private String c_shift_fullname;
    private String c_shift_typename;
    private int c_shift_index;
    private String c_start_time;
    private String c_iscrossday_start;
    private String c_end_time;
    private String c_iscrossday_end;
    
    
	
	public String getC_work_date() {
		return c_work_date;
	}
	public void setC_work_date(String c_work_date) {
		this.c_work_date = c_work_date;
	}
	public String getC_userid() {
		return c_userid;
	}
	public void setC_userid(String c_userid) {
		this.c_userid = c_userid;
	}
	public String getC_username() {
		return c_username;
	}
	public void setC_username(String c_username) {
		this.c_username = c_username;
	}
	public String getC_org_id() {
		return c_org_id;
	}
	public void setC_org_id(String c_org_id) {
		this.c_org_id = c_org_id;
	}
	public int getC_datetype() {
		return c_datetype;
	}
	public void setC_datetype(int c_datetype) {
		this.c_datetype = c_datetype;
	}
	public int getC_shift_id() {
		return c_shift_id;
	}
	public void setC_shift_id(int c_shift_id) {
		this.c_shift_id = c_shift_id;
	}
	public String getC_shift_name() {
		return c_shift_name;
	}
	public void setC_shift_name(String c_shift_name) {
		this.c_shift_name = c_shift_name;
	}
	public String getC_shift_fullname() {
		return c_shift_fullname;
	}
	public void setC_shift_fullname(String c_shift_fullname) {
		this.c_shift_fullname = c_shift_fullname;
	}
	public String getC_shift_typename() {
		return c_shift_typename;
	}
	public void setC_shift_typename(String c_shift_typename) {
		this.c_shift_typename = c_shift_typename;
	}
	public int getC_shift_index() {
		return c_shift_index;
	}
	public void setC_shift_index(int c_shift_index) {
		this.c_shift_index = c_shift_index;
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
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public int getcDate() {
		Calendar cal=Calendar.getInstance();
		cal.setTime(cDate); 
		int currDate=cal.get(Calendar.DAY_OF_MONTH);
		return currDate;
	}
	public void setcDate(Date cDate) {
		this.cDate = cDate;
	}
	public int getDayOfWeek() {
		return dayOfWeek;
	}
	public void setDayOfWeek(int dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}
	public String getcUserName() {
		return cUserName;
	}
	public void setcUserName(String cUserName) {
		this.cUserName = cUserName;
	}
	public String getcShiftName() {
		return cShiftName;
	}
	public void setcShiftName(String cShiftName) {
		this.cShiftName = cShiftName;
	}
	public String getC_theory_isworkday() {
		return c_theory_isworkday;
	}
	public void setC_theory_isworkday(String c_theory_isworkday) {
		this.c_theory_isworkday = c_theory_isworkday;
	}
	public String getC_fact_isworkday() {
		return c_fact_isworkday;
	}
	public void setC_fact_isworkday(String c_fact_isworkday) {
		this.c_fact_isworkday = c_fact_isworkday;
	}
	public String getC_iscrossday_start() {
		return c_iscrossday_start;
	}
	public void setC_iscrossday_start(String c_iscrossday_start) {
		this.c_iscrossday_start = c_iscrossday_start;
	}
	public String getC_iscrossday_end() {
		return c_iscrossday_end;
	}
	public void setC_iscrossday_end(String c_iscrossday_end) {
		this.c_iscrossday_end = c_iscrossday_end;
	} 
    
}
