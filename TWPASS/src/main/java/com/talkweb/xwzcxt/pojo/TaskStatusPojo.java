package com.talkweb.xwzcxt.pojo;

import com.talkweb.twdpe.core.data.BasePOJO;

/**
 * @author J.B.Chen
 * 2014-8-18 create
 * 待办任务修改状态存储过程对应表
 * proc:p_twxwzc_update_task_status
 */
public class TaskStatusPojo extends BasePOJO{
	
	private static final long serialVersionUID = 2501870297028334520L;
	
	private long v_task_id;			//任务ID
	private String v_status;		//33表示已执行
	private String v_cancel_cause;  //取消原因
	private String v_handle_des;	//自处理描述
	private String v_iserror;		//是否有异常
	private String c_return_code;   //返回标志
	
	public String getC_return_code() {
		return c_return_code;
	}

	public void setC_return_code(String c_return_code) {
		this.c_return_code = c_return_code;
	}

	public TaskStatusPojo(){}
	
	public long getV_task_id() {
		return v_task_id;
	}
	public void setV_task_id(long v_task_id) {
		this.v_task_id = v_task_id;
	}
	public String getV_status() {
		return v_status;
	}
	public void setV_status(String v_status) {
		this.v_status = v_status;
	}
	public String getV_cancel_cause() {
		return v_cancel_cause;
	}
	public void setV_cancel_cause(String v_cancel_cause) {
		this.v_cancel_cause = v_cancel_cause;
	}
	public String getV_handle_des() {
		return v_handle_des;
	}
	public void setV_handle_des(String v_handle_des) {
		this.v_handle_des = v_handle_des;
	}
	public String getV_iserror() {
		return v_iserror;
	}
	public void setV_iserror(String v_iserror) {
		this.v_iserror = v_iserror;
	}
}
