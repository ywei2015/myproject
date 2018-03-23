package com.talkweb.xwzcxt.pojo;

import com.talkweb.twdpe.core.data.BasePOJO;
/**
 * @author J.B.Chen
 * 2014-8-21 create
 * 我的任务：异常反馈表
 */
public class TaskErrorFeedback extends BasePOJO {

	private static final long serialVersionUID = 1692989914040369653L;
	
	private long c_feedback_id;
	private long c_err_id;
	private String c_feedback_title;
	private int c_manage_section;
	//0.请求协调处理异常--生成新的任务（任务表）1.抄送异常情况以求关注--生成消息（通知消息表）
	private int c_feedback_type;
	//该异常反馈的批次号 接口处以日期时间yyyyMMDDHHmmss生成
	private String c_feedback_lotno;
	private long c_receiver_userid;
	private long c_feedbacker;
	private String c_coordinator;//协调人ID
	private String c_copyfor;//抄送人ID
	private String c_feedback_time;
	private String c_end_time;
	private String c_err_des;
	private String c_err_name;
	private String c_writer;//反馈人ID
	private String c_isreceived;
	private String c_receiver_time;
	//0-反馈提交数据库，11-反馈被接收待处理，22-反馈处理完成
	private int c_status;
	
	public TaskErrorFeedback(){}

	public long getC_feedback_id() {
		return c_feedback_id;
	}

	public void setC_feedback_id(long c_feedback_id) {
		this.c_feedback_id = c_feedback_id;
	}

	public long getC_err_id() {
		return c_err_id;
	}

	public void setC_err_id(long c_err_id) {
		this.c_err_id = c_err_id;
	}

	public String getC_feedback_title() {
		return c_feedback_title;
	}

	public void setC_feedback_title(String c_feedback_title) {
		this.c_feedback_title = c_feedback_title;
	}

	public int getC_manage_section() {
		return c_manage_section;
	}

	public void setC_manage_section(int c_manage_section) {
		this.c_manage_section = c_manage_section;
	}

	public int getC_feedback_type() {
		return c_feedback_type;
	}

	public void setC_feedback_type(int c_feedback_type) {
		this.c_feedback_type = c_feedback_type;
	}

	public String getC_feedback_lotno() {
		return c_feedback_lotno;
	}

	public void setC_feedback_lotno(String c_feedback_lotno) {
		this.c_feedback_lotno = c_feedback_lotno;
	}

	public long getC_receiver_userid() {
		return c_receiver_userid;
	}

	public void setC_receiver_userid(long c_receiver_userid) {
		this.c_receiver_userid = c_receiver_userid;
	}

	public long getC_feedbacker() {
		return c_feedbacker;
	}

	public void setC_feedbacker(long c_feedbacker) {
		this.c_feedbacker = c_feedbacker;
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

	public int getC_status() {
		return c_status;
	}

	public void setC_status(int c_status) {
		this.c_status = c_status;
	}

    public String getC_coordinator()
    {
        return c_coordinator;
    }

    public void setC_coordinator(String c_coordinator)
    {
        this.c_coordinator = c_coordinator;
    }

    public String getC_copyfor()
    {
        return c_copyfor;
    }

    public void setC_copyfor(String c_copyfor)
    {
        this.c_copyfor = c_copyfor;
    }

    public String getC_end_time()
    {
        return c_end_time;
    }

    public void setC_end_time(String c_end_time)
    {
        this.c_end_time = c_end_time;
    }

    public String getC_err_des()
    {
        return c_err_des;
    }

    public void setC_err_des(String c_err_des)
    {
        this.c_err_des = c_err_des;
    }

    public String getC_writer()
    {
        return c_writer;
    }

    public void setC_writer(String c_writer)
    {
        this.c_writer = c_writer;
    }

    public String getC_err_name()
    {
        return c_err_name;
    }

    public void setC_err_name(String c_err_name)
    {
        this.c_err_name = c_err_name;
    }
	
}
