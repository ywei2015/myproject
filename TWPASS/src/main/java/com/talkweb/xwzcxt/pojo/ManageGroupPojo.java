package com.talkweb.xwzcxt.pojo;

import java.util.Date;

public class ManageGroupPojo {
	private String c_id;//聊天记录的id
	private String c_user_name;//聊天记录发送人
	private String c_chat_text;//聊天内容
	private Date c_chat_time;//聊天记录发送时间
	private String c_group_name;//聊天群组名字
	private String groupcreator;//聊天群组创建人
	private String orgId;
	private String userId;
	private int powerClass;//当前用户的权限等级
	private String c_chat_type;
	private String c_chat_imgurl;
	private String c_chat_audiourl;
	private String c_isdelete;
	
	
	
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public int getPowerClass() {
		return powerClass;
	}
	public void setPowerClass(int powerClass) {
		this.powerClass = powerClass;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getC_id() {
		return c_id;
	}
	public void setC_id(String c_id) {
		this.c_id = c_id;
	}
	public String getC_user_name() {
		return c_user_name;
	}
	public void setC_user_name(String c_user_name) {
		this.c_user_name = c_user_name;
	}
	public String getC_chat_text() {
		return c_chat_text;
	}
	public void setC_chat_text(String c_chat_text) {
		this.c_chat_text = c_chat_text;
	}
	public Date getC_chat_time() {
		return c_chat_time;
	}
	public void setC_chat_time(Date c_chat_time) {
		this.c_chat_time = c_chat_time;
	}
	public String getC_group_name() {
		return c_group_name;
	}
	public void setC_group_name(String c_group_name) {
		this.c_group_name = c_group_name;
	}
	public String getGroupcreator() {
		return groupcreator;
	}
	public void setGroupcreator(String groupcreator) {
		this.groupcreator = groupcreator;
	}
	public String getC_chat_type() {
		return c_chat_type;
	}
	public void setC_chat_type(String c_chat_type) {
		this.c_chat_type = c_chat_type;
	}
	public String getC_chat_imgurl() {
		return c_chat_imgurl;
	}
	public void setC_chat_imgurl(String c_chat_imgurl) {
		this.c_chat_imgurl = c_chat_imgurl;
	}
	public String getC_chat_audiourl() {
		return c_chat_audiourl;
	}
	public void setC_chat_audiourl(String c_chat_audiourl) {
		this.c_chat_audiourl = c_chat_audiourl;
	}
	public String getC_isdelete() {
		return c_isdelete;
	}
	public void setC_isdelete(String c_isdelete) {
		this.c_isdelete = c_isdelete;
	}
	
	
	
}
