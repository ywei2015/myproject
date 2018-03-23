package com.talkweb.xwzcxt.vo;

import java.math.BigDecimal;
import java.util.Date;

public class TaskAndErrManagerVo {
	 private BigDecimal c_task_id;
	 private String c_task_name;
	 private Long c_exec_userid;
	 private Date c_start_time;
	 private Date c_end_time;
	 private Integer c_status;
	 private String  c_iskeyctrl_name;
	 private String  c_task_kind_name  ;
	 private String c_issequence_name;
	 private String areaname;
	 private String c_manage_section_name;
	 private String posiname;
	 private String orgname;
	 private String status_name;
	 private String displayname;
	 
	 private Integer isFirstTime;
	 
	 private Long orgid;
	 private Long positionid;
	 private Long c_area_id;
	 private Integer  c_iskeyctrl;
	 private Integer  c_task_kind;
	 private Integer c_issequence;
	 private Long userid;
	 private Long c_manage_section;
	 private Integer typeid;
	 private String  typename;
	
	 
	public Integer getIsFirstTime() {
		return isFirstTime;
	}
	public void setIsFirstTime(int isFirstTime) {
		this.isFirstTime = isFirstTime;
	}
	public Integer getTypeid() {
		return typeid;
	}
	public void setTypeid(Integer typeid) {
		this.typeid = typeid;
	}
	public String getTypename() {
		return typename;
	}
	public void setTypename(String typename) {
		this.typename = typename;
	}
	public Long getC_manage_section() {
		return c_manage_section;
	}
	public void setC_manage_section(Long c_manage_section) {
		this.c_manage_section = c_manage_section;
	}
	public String getDisplayname() {
		return displayname;
	}
	public void setDisplayname(String displayname) {
		this.displayname = displayname;
	}
	public String getStatus_name() {
		return status_name;
	}
	public void setStatus_name(String status_name) {
		this.status_name = status_name;
	}
	public Integer getC_iskeyctrl() {
		return c_iskeyctrl;
	}
	public String getC_iskeyctrlStr(){
		return c_iskeyctrl==null?null:c_iskeyctrl.toString();
	}
	public void setC_iskeyctrl(Integer c_iskeyctrl) {
		this.c_iskeyctrl = c_iskeyctrl;
	}
	public Integer getC_task_kind() {
		return c_task_kind;
	}
	public void setC_task_kind(Integer c_task_kind) {
		this.c_task_kind = c_task_kind;
	}
	public Integer getC_issequence() {
		return c_issequence;
	}
	public String getC_issequenceStr() {
		return c_issequence==null?null:c_issequence.toString();
	}
	public void setC_issequence(Integer c_issequence) {
		this.c_issequence = c_issequence;
	}
	public BigDecimal getC_task_id() {
		return c_task_id;
	}
	public String getC_task_idStr() {
		return c_task_id!=null?c_task_id.toString():null;
	}
	public void setC_task_id(BigDecimal c_task_id) {
		this.c_task_id = c_task_id;
	}
	public String getC_task_name() {
		return c_task_name;
	}
	public void setC_task_name(String c_task_name) {
		this.c_task_name = c_task_name;
	}
	public Long getC_exec_userid() {
		return c_exec_userid;
	}
	public void setC_exec_userid(Long c_exec_userid) {
		this.c_exec_userid = c_exec_userid;
	}
	public Date getC_start_time() {
		return c_start_time;
	}
	public void setC_start_time(Date c_start_time) {
		this.c_start_time = c_start_time;
	}
	public Date getC_end_time() {
		return c_end_time;
	}
	public void setC_end_time(Date c_end_time) {
		this.c_end_time = c_end_time;
	}
	public Integer getC_status() {
		return c_status;
	}
	
	public String getC_statusStr(){
		return c_status!=null?c_status.toString():null;
	}
	public void setC_status(Integer c_status) {
		this.c_status = c_status;
	}
	public String getC_iskeyctrl_name() {
		return c_iskeyctrl_name;
	}
	public void setC_iskeyctrl_name(String c_iskeyctrl_name) {
		this.c_iskeyctrl_name = c_iskeyctrl_name;
	}
	public String getC_task_kind_name() {
		return c_task_kind_name;
	}
	public void setC_task_kind_name(String c_task_kind_name) {
		this.c_task_kind_name = c_task_kind_name;
	}
	public String getC_issequence_name() {
		return c_issequence_name;
	}
	public void setC_issequence_name(String c_issequence_name) {
		this.c_issequence_name = c_issequence_name;
	}
	public String getAreaname() {
		return areaname;
	}
	public void setAreaname(String areaname) {
		this.areaname = areaname;
	}
	public String getC_manage_section_name() {
		return c_manage_section_name;
	}
	public void setC_manage_section_name(String c_manage_section_name) {
		this.c_manage_section_name = c_manage_section_name;
	}
	public String getPosiname() {
		return posiname;
	}
	public void setPosiname(String posiname) {
		this.posiname = posiname;
	}
	public String getOrgname() {
		return orgname;
	}
	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}
	public Long getOrgid() {
		return orgid;
	}
	public void setOrgid(Long orgid) {
		this.orgid = orgid;
	}
	public Long getPositionid() {
		return positionid;
	}
	public void setPositionid(Long positionid) {
		this.positionid = positionid;
	}
	public Long getC_area_id() {
		return c_area_id;
	}
	public void setC_area_id(Long c_area_id) {
		this.c_area_id = c_area_id;
	}
	public void setUserid(Long userid) {
		this.userid=userid;
	}
	
	public Long getUserid() {
		return this.userid;
	}
	 
}
