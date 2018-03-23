package com.talkweb.xwzcxt.pojo;

import java.sql.Date;

import com.talkweb.twdpe.core.data.BasePOJO;

public class TaskError extends BasePOJO {
	
	private long c_err_id;
	private String c_err_name;
	private long c_pre_err_id;
	private String c_pre_err_name;
	private long c_err_kind;
	private long c_task_id;
	private long c_actitem_id;
	private long c_step_index;
	private long c_actnode_id;
	private long c_err_type;
	private long c_err_level;
	private String c_err_des;
	private String c_area;
	private String areaname;
	private long c_obj_id;
	private String c_obj_name;
	private long c_manage_section;
	private String c_end_time;
	private String c_write_time;
	private long c_writer;
	private String c_upload_time;
	private String c_isbyself;
	private String c_handle_des;
	private String c_isclose;		//0-未完成，1-已完成
	private String c_close_time;
	private String c_isdelete;
	//查询条件
	private int c_type;
	private Date start;
	private Date end;
	
	
	//Add By Rita.Zhou
	private String c_err_kind_name;
	private String displayname;
	private String c_manage_section_name;
	private String c_isclose_name;
	
	//异常文件
	private long c_err_record_id;
	private String c_file_path;
	private String c_file_id;
	
	//反馈内容
	private long c_feedback_id;
	private String c_feedback_title;
	private long c_feedback_type;
	private String c_feedback_type_name;
	private long c_receiver_userid;
	private String c_feedback_time;
	private String c_isreceived;
	private String c_isreceived_name;
	private String c_receiver_time;
	
	//处理情况
	private long c_sn_task_id;
	private String c_sn_task_name;
	private String c_sn_exec_username;
	private int c_sn_status;
	private String c_sn_status_name;
	
	
	//page model
	private String errdes;
	
	
	public String getAreaname() {
		return areaname;
	}
	public void setAreaname(String areaname) {
		this.areaname = areaname;
	}
	public String getErrdes() {
		return errdes;
	}
	public void setErrdes(String errdes) {
		this.errdes = errdes;
	}
	public String getC_sn_status_name() {
		return c_sn_status_name;
	}
	public void setC_sn_status_name(String c_sn_status_name) {
		this.c_sn_status_name = c_sn_status_name;
	}
	public long getC_sn_task_id() {
		return c_sn_task_id;
	}
	public void setC_sn_task_id(long c_sn_task_id) {
		this.c_sn_task_id = c_sn_task_id;
	}
	public String getC_sn_task_name() {
		return c_sn_task_name;
	}
	public void setC_sn_task_name(String c_sn_task_name) {
		this.c_sn_task_name = c_sn_task_name;
	}
	public String getC_sn_exec_username() {
		return c_sn_exec_username;
	}
	public void setC_sn_exec_username(String c_sn_exec_username) {
		this.c_sn_exec_username = c_sn_exec_username;
	}
	public int getC_sn_status() {
		return c_sn_status;
	}
	public void setC_sn_status(int c_sn_status) {
		this.c_sn_status = c_sn_status;
	}
	public String getC_isreceived_name() {
		return c_isreceived_name;
	}
	public void setC_isreceived_name(String c_isreceived_name) {
		this.c_isreceived_name = c_isreceived_name;
	}
	public String getC_pre_err_name() {
		return c_pre_err_name;
	}
	public void setC_pre_err_name(String c_pre_err_name) {
		this.c_pre_err_name = c_pre_err_name;
	}
	public String getC_isclose_name() {
		return c_isclose_name;
	}
	public void setC_isclose_name(String c_isclose_name) {
		this.c_isclose_name = c_isclose_name;
	}
	public String getC_obj_name() {
		return c_obj_name;
	}
	public void setC_obj_name(String c_obj_name) {
		this.c_obj_name = c_obj_name;
	}
	public String getC_feedback_type_name() {
		return c_feedback_type_name;
	}
	public void setC_feedback_type_name(String c_feedback_type_name) {
		this.c_feedback_type_name = c_feedback_type_name;
	}
	public long getC_err_record_id() {
		return c_err_record_id;
	}
	public void setC_err_record_id(long c_err_record_id) {
		this.c_err_record_id = c_err_record_id;
	}
	public String getC_file_path() {
		return c_file_path;
	}
	public void setC_file_path(String c_file_path) {
		this.c_file_path = c_file_path;
	}
	public String getC_file_id() {
		return c_file_id;
	}
	public void setC_file_id(String c_file_id) {
		this.c_file_id = c_file_id;
	}
	public long getC_feedback_id() {
		return c_feedback_id;
	}
	public void setC_feedback_id(long c_feedback_id) {
		this.c_feedback_id = c_feedback_id;
	}
	public String getC_feedback_title() {
		return c_feedback_title;
	}
	public void setC_feedback_title(String c_feedback_title) {
		this.c_feedback_title = c_feedback_title;
	}
	public long getC_feedback_type() {
		return c_feedback_type;
	}
	public void setC_feedback_type(long c_feedback_type) {
		this.c_feedback_type = c_feedback_type;
	}
	public long getC_receiver_userid() {
		return c_receiver_userid;
	}
	public void setC_receiver_userid(long c_receiver_userid) {
		this.c_receiver_userid = c_receiver_userid;
	}
	public String getC_feedback_time() {
		return c_feedback_time;
	}
	public void setC_feedback_time(String c_feedback_time) {
		this.c_feedback_time = c_feedback_time;
	}
	public String getC_isreceived() {
		return c_isreceived;
	}
	public void setC_isreceived(String c_isreceived) {
		this.c_isreceived = c_isreceived;
	}
	public String getC_receiver_time() {
		return c_receiver_time;
	}
	public void setC_receiver_time(String c_receiver_time) {
		this.c_receiver_time = c_receiver_time;
	}
	public String getC_manage_section_name() {
		return c_manage_section_name;
	}
	public void setC_manage_section_name(String c_manage_section_name) {
		this.c_manage_section_name = c_manage_section_name;
	}
	public String getDisplayname() {
		return displayname;
	}
	public void setDisplayname(String displayname) {
		this.displayname = displayname;
	}
	public String getC_err_kind_name() {
		return c_err_kind_name;
	}
	public void setC_err_kind_name(String c_err_kind_name) {
		this.c_err_kind_name = c_err_kind_name;
	}
	public long getC_err_id() {
		return c_err_id;
	}
	public void setC_err_id(long c_err_id) {
		this.c_err_id = c_err_id;
	}
	public String getC_err_name() {
		return c_err_name;
	}
	public void setC_err_name(String c_err_name) {
		this.c_err_name = c_err_name;
	}
	public long getC_pre_err_id() {
		return c_pre_err_id;
	}
	public void setC_pre_err_id(long c_pre_err_id) {
		this.c_pre_err_id = c_pre_err_id;
	}
	public long getC_err_kind() {
		return c_err_kind;
	}
	public void setC_err_kind(long c_err_kind) {
		this.c_err_kind = c_err_kind;
	}
	public long getC_task_id() {
		return c_task_id;
	}
	public void setC_task_id(long c_task_id) {
		this.c_task_id = c_task_id;
	}
	public long getC_actitem_id() {
		return c_actitem_id;
	}
	public void setC_actitem_id(long c_actitem_id) {
		this.c_actitem_id = c_actitem_id;
	}
	public long getC_step_index() {
		return c_step_index;
	}
	public void setC_step_index(long c_step_index) {
		this.c_step_index = c_step_index;
	}
	public long getC_actnode_id() {
		return c_actnode_id;
	}
	public void setC_actnode_id(long c_actnode_id) {
		this.c_actnode_id = c_actnode_id;
	}
	public long getC_err_type() {
		return c_err_type;
	}
	public void setC_err_type(long c_err_type) {
		this.c_err_type = c_err_type;
	}
	public long getC_err_level() {
		return c_err_level;
	}
	public void setC_err_level(long c_err_level) {
		this.c_err_level = c_err_level;
	}
	public String getC_err_des() {
		return c_err_des;
	}
	public void setC_err_des(String c_err_des) {
		this.c_err_des = c_err_des;
	}
	public String getC_area() {
		return c_area;
	}
	public void setC_area(String c_area) {
		this.c_area = c_area;
	}
	public long getC_obj_id() {
		return c_obj_id;
	}
	public void setC_obj_id(long c_obj_id) {
		this.c_obj_id = c_obj_id;
	}
	public long getC_manage_section() {
		return c_manage_section;
	}
	public void setC_manage_section(long c_manage_section) {
		this.c_manage_section = c_manage_section;
	}
	public String getC_end_time() {
		return c_end_time;
	}
	public void setC_end_time(String c_end_time) {
		this.c_end_time = c_end_time;
	}
	public String getC_write_time() {
		return c_write_time;
	}
	public void setC_write_time(String c_write_time) {
		this.c_write_time = c_write_time;
	}
	public long getC_writer() {
		return c_writer;
	}
	public void setC_writer(long c_writer) {
		this.c_writer = c_writer;
	}
	public String getC_upload_time() {
		return c_upload_time;
	}
	public void setC_upload_time(String c_upload_time) {
		this.c_upload_time = c_upload_time;
	}
	public String getC_isbyself() {
		return c_isbyself;
	}
	public void setC_isbyself(String c_isbyself) {
		this.c_isbyself = c_isbyself;
	}
	public String getC_handle_des() {
		return c_handle_des;
	}
	public void setC_handle_des(String c_handle_des) {
		this.c_handle_des = c_handle_des;
	}
	public String getC_isclose() {
		return c_isclose;
	}
	public void setC_isclose(String c_isclose) {
		this.c_isclose = c_isclose;
	}
	public String getC_close_time() {
		return c_close_time;
	}
	public void setC_close_time(String c_close_time) {
		this.c_close_time = c_close_time;
	}
	public String getC_isdelete() {
		return c_isdelete;
	}
	public void setC_isdelete(String c_isdelete) {
		this.c_isdelete = c_isdelete;
	}
	public int getC_type() {
		return c_type;
	}
	public void setC_type(int c_type) {
		this.c_type = c_type;
	}
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
	//列表显示
	private String c_task_name;
	private String c_actnode_name;
	public String getC_task_name() {
		return c_task_name;
	}
	public void setC_task_name(String c_task_name) {
		this.c_task_name = c_task_name;
	}
	public String getC_actnode_name() {
		return c_actnode_name;
	}
	public void setC_actnode_name(String c_actnode_name) {
		this.c_actnode_name = c_actnode_name;
	}
	
}
