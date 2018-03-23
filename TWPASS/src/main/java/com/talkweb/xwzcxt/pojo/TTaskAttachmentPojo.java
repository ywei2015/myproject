package com.talkweb.xwzcxt.pojo;

import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.common.AppConstants;

/**
 * GuveXie 20141211 Create
 * 发起任务附件信息Pojo 对应表 T_TASK_ATTACHMENT  
*/
public class TTaskAttachmentPojo { 
	private String c_attachment_id;
	private String c_task_id;
	private String c_file_id;
	private String c_file_kind;
	private String c_file_title;
	private String c_file_type;
	private String c_file_name;
	private String c_file_extension;
	private String c_file_path;
	private String c_file_size;
	private String c_upload_time;     

	public String getC_attachment_id() {
		return c_attachment_id;
	}
	public void setC_attachment_id(String c_attachment_id) {
		this.c_attachment_id = c_attachment_id;
	}
	public String getC_task_id() {
		return c_task_id;
	}
	public void setC_task_id(String c_task_id) {
		this.c_task_id = c_task_id;
	}
	public String getC_file_id() {
		return c_file_id;
	}
	public void setC_file_id(String c_file_id) {
		this.c_file_id = c_file_id;
	}
	public String getC_file_kind() {
		return c_file_kind;
	}
	public void setC_file_kind(String c_file_kind) {
		this.c_file_kind = c_file_kind;
	}
	public String getC_file_title() {
		return c_file_title;
	}
	public void setC_file_title(String c_file_title) {
		this.c_file_title = c_file_title;
	}
	public String getC_file_type() {
		return c_file_type;
	}
	public void setC_file_type(String c_file_type) {
		this.c_file_type = c_file_type;
	}
	public String getC_file_name() {
		return c_file_name;
	}
	public void setC_file_name(String c_file_name) {
		this.c_file_name = c_file_name;
	}
	public String getC_file_extension() {
		return c_file_extension;
	}
	public void setC_file_extension(String c_file_extension) {
		this.c_file_extension = c_file_extension;
	}
	public String getC_file_path() {
		return c_file_path;
	}
	public void setC_file_path(String c_file_path) {
		this.c_file_path = AppConstants.getFilePathPrefix()+c_file_path;
	}
	public String getC_file_size() {
		return c_file_size;
	}
	public void setC_file_size(String c_file_size) {
		this.c_file_size = c_file_size;
	}
	public String getC_upload_time() {
		return c_upload_time;
	}
	public void setC_upload_time(String c_upload_time) {
		this.c_upload_time = c_upload_time;
	}
}
