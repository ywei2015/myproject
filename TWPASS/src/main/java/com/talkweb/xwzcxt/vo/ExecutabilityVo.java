package com.talkweb.xwzcxt.vo;

import java.util.Date;


public class ExecutabilityVo {
	private String orgid;
	private  Integer time_type;
	private  Integer task_type;
	private  String  start_time;
	private  Integer  total_days;
	private  String  userCode;
	private  Integer orgtype;
	
	private  Float  advance_time;
	private  Float  plain_time;
	private  Integer  exist;
	private  String day_of_month;
	private  Integer c_status;
	private  String  result;
	private  Date    end_time;
	private  Date    fact_end_time;
	private  String c_task_name;
	private  Integer details_type;

	
	public String getOrgid() {
		return orgid;
	}
	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}
	public Integer getDetails_type() {
		return details_type;
	}
	public void setDetails_type(Integer details_type) {
		this.details_type = details_type;
	}
	public String getC_task_name() {
		return c_task_name;
	}
	public void setC_task_name(String c_task_name) {
		this.c_task_name = c_task_name;
	}
	public Date getEnd_time() {
		return end_time;
	}
	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}
	public Date getFact_end_time() {
		return fact_end_time;
	}
	public void setFact_end_time(Date fact_end_time) {
		this.fact_end_time = fact_end_time;
	}
	public Integer getOrgtype() {
		return orgtype;
	}
	public void setOrgtype(Integer orgtype) {
		this.orgtype = orgtype;
	}
	public Integer getC_status() {
		return c_status;
	}
	public void setC_status(Integer c_status) {
		this.c_status = c_status;
	}

	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	
	public Integer getTime_type() {
		return time_type;
	}
	public void setTime_type(Integer time_type) {
		this.time_type = time_type;
	}
	public Integer getTask_type() {
		return task_type;
	}
	public void setTask_type(Integer task_type) {
		this.task_type = task_type;
	}

	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getStart_time() {
		int index=start_time.indexOf(".");
		if(index<0){
			return start_time;
		}
		return start_time.substring(0,index);
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public Integer getTotal_days() {
		return total_days;
	}
	public void setTotal_days(Integer total_days) {
		this.total_days = total_days;
	}
	
	 
	public Float getAdvance_time() {
		return advance_time;
	}
	public void setAdvance_time(Float advance_time) {
		this.advance_time = advance_time;
	}
	public Float getPlain_time() {
		return plain_time;
	}
	public void setPlain_time(Float plain_time) {
		this.plain_time = plain_time;
	}
	 
	public Integer getExist() {
		return exist;
	}
	public void setExist(Integer exist) {
		this.exist = exist;
	}
	public String getDay_of_month() {
		return day_of_month;
	}
	public void setDay_of_month(String day_of_month) {
		this.day_of_month = day_of_month;
	}
	
	
	
}
