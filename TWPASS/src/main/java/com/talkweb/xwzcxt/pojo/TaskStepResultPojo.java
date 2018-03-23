package com.talkweb.xwzcxt.pojo;

import java.util.Date;

import com.talkweb.twdpe.core.data.BasePOJO;

public class TaskStepResultPojo extends BasePOJO { 
	private static final long serialVersionUID = 1L;
	
	private long c_id;
	private long c_result_id;
	private long c_task_id;
	private String c_actitem_id;
	private long c_actitem_index;
	private long c_step_index;		//步骤索引
	private String c_step_name;		//步骤名称
	private long c_tracefun_id;		//采取方式
	private String c_tracefun_name;
	private String c_fun_params;
	private String c_isfile;
	private String c_result;
	private Date c_exec_time;
	private Date c_get_time;
	private String c_remark;
	private String c_file_path;
	private String c_file_title;
	private String c_status;		//0-未执行，11-已执行并收到结果
	private String c_step_std;
	

	
	public String getC_step_std() {
		return c_step_std;
	}

	public void setC_step_std(String c_step_std) {
		this.c_step_std = c_step_std;
	}

	public void setC_result_id(long value) {
		c_result_id = value;
	}

	public long getC_result_id() {
		return c_result_id;
	}

	public void setC_task_id(long value) {
		c_task_id = value;
	}

	public long getC_task_id() {
		return c_task_id;
	}

	public void setC_actitem_id(String value) {
		c_actitem_id = value;
	}

	public String getC_actitem_id() {
		return c_actitem_id;
	}

	public void setC_actitem_index(long value) {
		c_actitem_index = value;
	}

	public long getC_actitem_index() {
		return c_actitem_index;
	}

	public void setC_step_index(long value) {
		c_step_index = value;
	}

	public long getC_step_index() {
		return c_step_index;
	}

	public void setC_tracefun_id(long value) {
		c_tracefun_id = value;
	}

	public long getC_tracefun_id() {
		return c_tracefun_id;
	}

	public void setC_tracefun_name(String value) {
		c_tracefun_name = value;
	}

	public String getC_tracefun_name() {
		return c_tracefun_name;
	}

	public void setC_fun_params(String value) {
		c_fun_params = value;
	}

	public String getC_fun_params() {
		return c_fun_params;
	}

	public void setC_isfile(String value) {
		c_isfile = value;
	}

	public String getC_isfile() {
		return c_isfile;
	}

	public void setC_result(String value) {
		c_result = value;
	}

	public String getC_result() {
		return c_result;
	}

	public void setC_exec_time(Date value) {
		c_exec_time = value;
	}

	public Date getC_exec_time() {
		return c_exec_time;
	}

	public void setC_get_time(Date value) {
		c_get_time = value;
	}

	public Date getC_get_time() {
		return c_get_time;
	}

	public void setC_remark(String value) {
		c_remark = value;
	}

	public String getC_remark() {
		return c_remark;
	}

	public void setC_file_path(String value) {
		c_file_path = value;
	}

	public String getC_file_path() {
		return c_file_path;
	}

	public void setC_file_title(String value) {
		c_file_title = value;
	}

	public String getC_file_title() {
		return c_file_title;
	}
	
	public String getC_step_name() {
		return c_step_name;
	}

	public void setC_step_name(String c_step_name) {
		this.c_step_name = c_step_name;
	}

	public long getC_id() {
		return c_id;
	}

	public void setC_id(long c_id) {
		this.c_id = c_id;
	}

	public String getC_status() {
		return c_status;
	}

	public void setC_status(String c_status) {
		this.c_status = c_status;
	}
	
}
