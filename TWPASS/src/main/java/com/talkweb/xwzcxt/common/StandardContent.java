package com.talkweb.xwzcxt.common;

import java.util.Date;

public class StandardContent {

	private String c_contentid;//uuid
	private String c_sectionid;
	private String c_sectionpid;
	private String c_title;
	private String c_content;
	private String c_includeatt;//1 or 0
	private String c_sfile_id;
	private String c_creator;
	private Date c_createtime;
	private String c_modifier;
	private Date c_modifytime;
	public String getC_contentid() {
		return c_contentid;
	}
	public void setC_contentid(String c_contentid) {
		this.c_contentid = c_contentid;
	}
	public String getC_sectionid() {
		return c_sectionid;
	}
	public void setC_sectionid(String c_sectionid) {
		this.c_sectionid = c_sectionid;
	}
	public String getC_sectionpid() {
		return c_sectionpid;
	}
	public void setC_sectionpid(String c_sectionpid) {
		this.c_sectionpid = c_sectionpid;
	}
	public String getC_title() {
		return c_title;
	}
	public void setC_title(String c_title) {
		this.c_title = c_title;
	}
	public String getC_content() {
		return c_content;
	}
	public void setC_content(String c_content) {
		this.c_content = c_content;
	}
	public String getC_includeatt() {
		return c_includeatt;
	}
	public void setC_includeatt(String c_includeatt) {
		this.c_includeatt = c_includeatt;
	}
	public String getC_sfile_id() {
		return c_sfile_id;
	}
	public void setC_sfile_id(String c_sfile_id) {
		this.c_sfile_id = c_sfile_id;
	}
	public String getC_creator() {
		return c_creator;
	}
	public void setC_creator(String c_creator) {
		this.c_creator = c_creator;
	}
	public Date getC_createtime() {
		return c_createtime;
	}
	public void setC_createtime(Date c_createtime) {
		this.c_createtime = c_createtime;
	}
	public String getC_modifier() {
		return c_modifier;
	}
	public void setC_modifier(String c_modifier) {
		this.c_modifier = c_modifier;
	}
	public Date getC_modifytime() {
		return c_modifytime;
	}
	public void setC_modifytime(Date c_modifytime) {
		this.c_modifytime = c_modifytime;
	}
	
}