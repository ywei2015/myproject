package com.talkweb.xwzcxt.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

public class TSchedulingPojo implements Serializable {
	private static final long serialVersionUID = 7304860146050578556L;
	
	private BigDecimal c_id; // NUMBER(38) not null,
	private int c_ym_id; // NUMBER(6) not null,
	private long c_userid; // NUMBER(10) not null,
	private String c_username; // VARCHAR2(50) not null,
	private long c_org_id; // NUMBER(10),
	private BigDecimal c_calendar_id; // NUMBER(20),
	private String c_date; // DATE not null,
	private int c_datetype; // NUMBER(2) default 1 not null,
	private long c_shift_id; // NUMBER(10) default 0 not null,
	private String c_shift_name; // VARCHAR2(100),
	private String c_creator; // VARCHAR2(32),
	private String c_create_time; // DATE,
	private String c_last_modifier; // VARCHAR2(32),
	private String c_last_modified_time; // DATE,
	private String c_remark; // VARCHAR2(2000),
	private String c_isdelete; // CHAR(1) default '0' not null
	private String  c_usercode  ;     // VARCHAR2(50) not null,
	private String  c_shiftdes  ;      //VARCHAR2(500) not null,
	private String  c_create_usercode ;//VARCHAR2(50) not null,
	private String  c_create_username ;//VARCHAR2(50),
    private String  c_shift_fullname;
	public BigDecimal getC_id() {
		return c_id;
	}
	public void setC_id(BigDecimal c_id) {
		this.c_id = c_id;
	}
	public int getC_ym_id() {
		return c_ym_id;
	}
	public void setC_ym_id(int c_ym_id) {
		this.c_ym_id = c_ym_id;
	}
	public long getC_userid() {
		return c_userid;
	}
	public void setC_userid(long c_userid) {
		this.c_userid = c_userid;
	}
	public String getC_username() {
		return c_username;
	}
	public void setC_username(String c_username) {
		this.c_username = c_username;
	}
	public long getC_org_id() {
		return c_org_id;
	}
	public void setC_org_id(long c_org_id) {
		this.c_org_id = c_org_id;
	}
	public BigDecimal getC_calendar_id() {
		return c_calendar_id;
	}
	public void setC_calendar_id(BigDecimal c_calendar_id) {
		this.c_calendar_id = c_calendar_id;
	}
	public String getC_date() {
		return c_date;
	}
	public void setC_date(String c_date) {
		this.c_date = c_date;
	}
	public int getC_datetype() {
		return c_datetype;
	}
	public void setC_datetype(int c_datetype) {
		this.c_datetype = c_datetype;
	}
	public long getC_shift_id() {
		return c_shift_id;
	}
	public void setC_shift_id(long c_shift_id) {
		this.c_shift_id = c_shift_id;
	}
	public String getC_shift_name() {
		return c_shift_name;
	}
	public void setC_shift_name(String c_shift_name) {
		this.c_shift_name = c_shift_name;
	}
	public String getC_creator() {
		return c_creator;
	}
	public void setC_creator(String c_creator) {
		this.c_creator = c_creator;
	}
	public String getC_create_time() {
		return c_create_time;
	}
	public void setC_create_time(String c_create_time) {
		this.c_create_time = c_create_time;
	}
	public String getC_last_modifier() {
		return c_last_modifier;
	}
	public void setC_last_modifier(String c_last_modifier) {
		this.c_last_modifier = c_last_modifier;
	}
	public String getC_last_modified_time() {
		return c_last_modified_time;
	}
	public void setC_last_modified_time(String c_last_modified_time) {
		this.c_last_modified_time = c_last_modified_time;
	}
	public String getC_remark() {
		return c_remark;
	}
	public void setC_remark(String c_remark) {
		this.c_remark = c_remark;
	}
	public String getC_isdelete() {
		return c_isdelete;
	}
	public void setC_isdelete(String c_isdelete) {
		this.c_isdelete = c_isdelete;
	}
	public String getC_usercode() {
		return c_usercode;
	}
	public void setC_usercode(String c_usercode) {
		this.c_usercode = c_usercode;
	}
	public String getC_shiftdes() {
		return c_shiftdes;
	}
	public void setC_shiftdes(String c_shiftdes) {
		this.c_shiftdes = c_shiftdes;
	}
	public String getC_create_usercode() {
		return c_create_usercode;
	}
	public void setC_create_usercode(String c_create_usercode) {
		this.c_create_usercode = c_create_usercode;
	}
	public String getC_create_username() {
		return c_create_username;
	}
	public void setC_create_username(String c_create_username) {
		this.c_create_username = c_create_username;
	}
	public String getC_shift_fullname() {
		return c_shift_fullname;
	}
	public void setC_shift_fullname(String c_shift_fullname) {
		this.c_shift_fullname = c_shift_fullname;
	}
	

}
