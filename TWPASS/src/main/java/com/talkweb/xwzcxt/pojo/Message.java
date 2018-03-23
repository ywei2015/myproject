package com.talkweb.xwzcxt.pojo;

import com.talkweb.twdpe.core.data.BasePOJO;

public class Message extends BasePOJO{

	/**
	 * 消息表 t_message
	 * J.B.Chen 8-6 create
	 */
	private static final long serialVersionUID = -1509362378524424420L;
	
	private long c_msg_id;
	private String c_msg_title;
	private String c_msg_content;
	//后续建表关联 10~19表示任务相关 20~29表示异常相关
	private int c_msg_type;
	//1-普通，2-重要但不紧急，3-紧急但不重要，4-非常重要且非常紧急
	private int c_msg_level;
	private String c_msg_level_string;
	//1-文本短消息，2-切到APP应用并触发事件
	private int c_notify_type;
	private String c_from;
	private String c_create_time;
	//计划时间
	private String c_plan_time;
	//截止时间
	private String c_expiry_time;
	//-1 均可,0-客户端拉,1-服务端主动推
	private int c_send_type;
	//发送人ID
	private long c_sender;
	//发送人姓名
	private String c_sender_name;
	//接收人ID
	private long c_receiver;
	//接收人姓名
	private String c_receiver_name;
	//实际发送时间
	private String c_fact_sendtime;
	//实际接收时间
	private String c_fact_gettime;
	private String c_device_sn;
	private int c_send_count;
	//0待发、11送达、22已读
	private String c_status;
	private String c_status_string;
	//任务执行状态
	private String c_task_status;
	//任务执行状态的字符串
	private String c_task_status_string;
	//0-未删除，1-已删除。
	private int c_isdelete;
	//关联业务主键ID
	private String c_pid;
	private String c_remark;
	//查看消息明细
	private String watchMessageDetail="查看消息明细";
	
	public Message(){}

	public long getC_msg_id() {
		return c_msg_id;
	}

	public void setC_msg_id(long c_msg_id) {
		this.c_msg_id = c_msg_id;
	}

	public String getC_msg_title() {
		return c_msg_title;
	}

	public void setC_msg_title(String c_msg_title) {
		this.c_msg_title = c_msg_title;
	}

	public String getC_msg_content() {
		return c_msg_content;
	}

	public void setC_msg_content(String c_msg_content) {
		this.c_msg_content = c_msg_content;
	}

	public int getC_msg_type() {
		return c_msg_type;
	}

	public void setC_msg_type(int c_msg_type) {
		this.c_msg_type = c_msg_type;
	}

	public int getC_msg_level() {
		return c_msg_level;
	}

	public void setC_msg_level(int c_msg_level) {
		this.c_msg_level = c_msg_level;
	}

	public int getC_notify_type() {
		return c_notify_type;
	}

	public void setC_notify_type(int c_notify_type) {
		this.c_notify_type = c_notify_type;
	}

	public String getC_from() {
		return c_from;
	}

	public void setC_from(String c_from) {
		this.c_from = c_from;
	}

	public String getC_create_time() {
		return c_create_time;
	}

	public void setC_create_time(String c_create_time) {
		this.c_create_time = c_create_time;
	}

	public String getC_plan_time() {
		return c_plan_time;
	}

	public void setC_plan_time(String c_plan_time) {
		this.c_plan_time = c_plan_time;
	}

	public String getC_expiry_time() {
		return c_expiry_time;
	}

	public void setC_expiry_time(String c_expiry_time) {
		this.c_expiry_time = c_expiry_time;
	}

	public int getC_send_type() {
		return c_send_type;
	}

	public void setC_send_type(int c_send_type) {
		this.c_send_type = c_send_type;
	}

	public long getC_sender() {
		return c_sender;
	}

	public void setC_sender(long c_sender) {
		this.c_sender = c_sender;
	}

	public long getC_receiver() {
		return c_receiver;
	}

	public void setC_receiver(long c_receiver) {
		this.c_receiver = c_receiver;
	}

	public String getC_fact_sendtime() {
		return c_fact_sendtime;
	}

	public void setC_fact_sendtime(String c_fact_sendtime) {
		this.c_fact_sendtime = c_fact_sendtime;
	}

	public String getC_fact_gettime() {
		return c_fact_gettime;
	}

	public void setC_fact_gettime(String c_fact_gettime) {
		this.c_fact_gettime = c_fact_gettime;
	}

	public String getC_device_sn() {
		return c_device_sn;
	}

	public void setC_device_sn(String c_device_sn) {
		this.c_device_sn = c_device_sn;
	}

	public int getC_send_count() {
		return c_send_count;
	}

	public void setC_send_count(int c_send_count) {
		this.c_send_count = c_send_count;
	}

	public String getC_status() {
		return c_status;
	}

	public void setC_status(String c_status) {
		this.c_status = c_status;
	}

	public int getC_isdelete() {
		return c_isdelete;
	}

	public void setC_isdelete(int c_isdelete) {
		this.c_isdelete = c_isdelete;
	}

	public String getC_pid() {
		return c_pid;
	}

	public void setC_pid(String c_pid) {
		this.c_pid = c_pid;
	}

	public String getC_remark() {
		return c_remark;
	}

	public void setC_remark(String c_remark) {
		this.c_remark = c_remark;
	}

	public String getC_task_status() {
		return c_task_status;
	}

	public void setC_task_status(String c_task_status) {
		this.c_task_status = c_task_status;
	}

	public String getC_task_status_string() {
		return c_task_status_string;
	}

	public void setC_task_status_string(String c_task_status_string) {
		this.c_task_status_string = c_task_status_string;
	}

	public String getWatchMessageDetail() {
		return watchMessageDetail;
	}

	public void setWatchMessageDetail(String watchMessageDetail) {
		this.watchMessageDetail = watchMessageDetail;
	}

	public String getC_msg_level_string() {
		return c_msg_level_string;
	}

	public void setC_msg_level_string(String c_msg_level_string) {
		this.c_msg_level_string = c_msg_level_string;
	}

	public String getC_receiver_name() {
		return c_receiver_name;
	}

	public void setC_receiver_name(String c_receiver_name) {
		this.c_receiver_name = c_receiver_name;
	}

	public String getC_sender_name() {
		return c_sender_name;
	}

	public void setC_sender_name(String c_sender_name) {
		this.c_sender_name = c_sender_name;
	}

    public String getC_status_string()
    {
        return c_status_string;
    }

    public void setC_status_string(String c_status_string)
    {
        this.c_status_string = c_status_string;
    }
	
}
