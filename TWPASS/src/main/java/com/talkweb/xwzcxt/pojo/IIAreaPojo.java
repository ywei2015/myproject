package com.talkweb.xwzcxt.pojo;

import java.util.Date;

import com.talkweb.twdpe.core.data.BasePOJO;

/**
 * TODO(描述这个类的作用)
 * 
 * @author: 2014-7-18，GuveXie，（描述修改内容）
 */
public class IIAreaPojo  extends BasePOJO  {
	private static final long serialVersionUID = 884276556299260942L;
	
	private long c_area_id;
	private String c_area_code;
	private String c_area_name;
	private String c_area_fullname;
	private String c_scan_code;
	private long c_area_upid;
	private String c_area_upname;
	private String c_area_upcode;
	private long c_node_level;
	private String c_area_type;
	private String c_area_typename;
	private String c_creator;
	private Date c_create_time;
	private String c_last_modifier;
	private Date c_last_modified_time;
	private String c_remark;
	private String c_isdelete;


	public void setC_area_id(long value) {
		c_area_id = value;
	}

	public long getC_area_id() {
		return c_area_id;
	}

	public void setC_area_code(String value) {
		c_area_code = value;
	}

	public String getC_area_code() {
		return c_area_code;
	}

	public void setC_area_name(String value) {
		c_area_name = value;
	}

	public String getC_area_name() {
		return c_area_name;
	}

	public void setC_area_fullname(String value) {
		c_area_fullname = value;
	}

	public String getC_area_fullname() {
		return c_area_fullname;
	}

	public void setC_scan_code(String value) {
		c_scan_code = value;
	}

	public String getC_scan_code() {
		return c_scan_code;
	}

	public void setC_area_upid(long value) {
		c_area_upid = value;
	}

	public long getC_area_upid() {
		return c_area_upid;
	}

	public void setC_area_upname(String value) {
		c_area_upname = value;
	}

	public String getC_area_upname() {
		return c_area_upname;
	}
	
	public void setC_area_upcode(String value) {
		c_area_upcode = value;
	}

	public String getC_area_upcode() {
		return c_area_upcode;
	}

	public void setC_node_level(long value) {
		c_node_level = value;
	}

	public long getC_node_level() {
		return c_node_level;
	}

	public void setC_area_type(String value) {
		c_area_type = value;
	}

	public String getC_area_type() {
		return c_area_type;
	}

	public void setC_area_typename(String value) {
		c_area_typename = value;
	}

	public String getC_area_typename() {
		return c_area_typename;
	}
	
	public void setC_creator(String value) {
		c_creator = value;
	}

	public String getC_creator() {
		return c_creator;
	}

	public void setC_create_time(Date value) {
		c_create_time = value;
	}

	public Date getC_create_time() {
		return c_create_time;
	}

	public void setC_last_modifier(String value) {
		c_last_modifier = value;
	}

	public String getC_last_modifier() {
		return c_last_modifier;
	}

	public void setC_last_modified_time(Date value) {
		c_last_modified_time = value;
	}

	public Date getC_last_modified_time() {
		return c_last_modified_time;
	}

	public void setC_remark(String value) {
		c_remark = value;
	}

	public String getC_remark() {
		return c_remark;
	}

	public void setC_isdelete(String value) {
		c_isdelete = value;
	}

	public String getC_isdelete() {
		return c_isdelete;
	}

}
