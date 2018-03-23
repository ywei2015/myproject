package com.talkweb.xwzcxt.pojo;

import com.talkweb.twdpe.core.data.BasePOJO;

public class SdActionPojo extends BasePOJO {

	private String c_action_id;
	private String c_action_pid;
	private String c_action_code;  //  编码
	private String c_action_type;  //  流程节点类型（保留），需求规定只有最底层节点才允许添加岗位活动，此类型区分是否为最底层节点
	private String c_section_id;   //  所属版块（1安全、2质量、3成本、4效率、5团队、6环境）
	private String c_action_sname; //  简称
	private String c_action_fname; //  全称
	private String c_remark;	   //  描述
	private String c_flag;		   //  是否使用，默认为1 （保留）应对以后是否取消此项流程节点
	
	public String getC_action_id() {
		return c_action_id;
	}
	public void setC_action_id(String c_action_id) {
		this.c_action_id = c_action_id;
	}
	public String getC_action_pid() {
		return c_action_pid;
	}
	public void setC_action_pid(String c_action_pid) {
		this.c_action_pid = c_action_pid;
	}
	public String getC_action_code() {
		return c_action_code;
	}
	public void setC_action_code(String c_action_code) {
		this.c_action_code = c_action_code;
	}
	public String getC_action_type() {
		return c_action_type;
	}
	public void setC_action_type(String c_action_type) {
		this.c_action_type = c_action_type;
	}
	public String getC_section_id() {
		return c_section_id;
	}
	public void setC_section_id(String c_section_id) {
		this.c_section_id = c_section_id;
	}
	public String getC_action_sname() {
		return c_action_sname;
	}
	public void setC_action_sname(String c_action_sname) {
		this.c_action_sname = c_action_sname;
	}
	public String getC_action_fname() {
		return c_action_fname;
	}
	public void setC_action_fname(String c_action_fname) {
		this.c_action_fname = c_action_fname;
	}
	public String getC_remark() {
		return c_remark;
	}
	public void setC_remark(String c_remark) {
		this.c_remark = c_remark;
	}
	public String getC_flag() {
		return c_flag;
	}
	public void setC_flag(String c_flag) {
		this.c_flag = c_flag;
	}
}
