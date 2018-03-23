package com.talkweb.xwzcxt.pojo;

import com.talkweb.twdpe.core.data.BasePOJO;

public class IITaskinfoItemPojo extends BasePOJO {
	private static final long serialVersionUID = 1L;
	
	private long c_stepitem_id;
	private long c_task_id;
	private long c_actitem_index;
	private String c_actitem_name;
	private String c_actitem_id;
	private String c_exec_getdatatype;
	private String c_check_getdatatype;

	public void setC_stepitem_id(long value) {
		c_stepitem_id = value;
	}

	public long getC_stepitem_id() {
		return c_stepitem_id;
	}

	public void setC_task_id(long value) {
		c_task_id = value;
	}

	public long getC_task_id() {
		return c_task_id;
	}

	public void setC_actitem_index(long value) {
		c_actitem_index = value;
	}

	public long getC_actitem_index() {
		return c_actitem_index;
	}

	public void setC_actitem_name(String value) {
		c_actitem_name = value;
	}

	public String getC_actitem_name() {
		return c_actitem_name;
	}

	public void setC_actitem_id(String value) {
		c_actitem_id = value;
	}

	public String getC_actitem_id() {
		return c_actitem_id;
	}

	public void setC_exec_getdatatype(String value) {
		c_exec_getdatatype = value;
	}

	public String getC_exec_getdatatype() {
		return c_exec_getdatatype;
	}

	public void setC_check_getdatatype(String value) {
		c_check_getdatatype = value;
	}

	public String getC_check_getdatatype() {
		return c_check_getdatatype;
	}

}
