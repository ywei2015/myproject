package com.talkweb.xwzcxt.pojo;

import java.util.List;

import com.talkweb.twdpe.core.data.BasePOJO;

public class TaskMouldPojo extends BasePOJO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8104510025606160279L;
	public TaskMouldPojo()
    {
        super();
    }
	
	
	private int   c_task_kind;
	private String watchStdAction;
	private String c_classgroup_id;
	private String c_orgid;
	private String c_department_id;
	private long c_tasktemplet_id; 
	private String c_cancel_cause;
	private String c_tasktemplet_name; 
	private String c_actnode_id;     
	private String c_action_name;    
    private String c_actnode_name;   
    
    //add by mzl for task table
    private String orgdepartname;   //部门名称
    private String execUserInfo;
    
    //Add by J.B.Chen for task table
    private String isExpired;	//是否逾期
    private String watchTaskDetail;  //查看任务详情
    private String submit="提交";		//提交按钮
    
    private String c_chk_plantime ;
    private String c_evaluate_plantime  ;
    private String extype;//单个页面请求标识
    private String userCode;//用户工号
    private String type;//web首页推板标识
    
    public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getExtype() {
		return extype;
	}
	public void setExtype(String extype) {
		this.extype = extype;
	}
	public String getC_evaluate_plantime() {
		return c_evaluate_plantime;
	}
	public void setC_evaluate_plantime(String c_evaluate_plantime) {
		this.c_evaluate_plantime = c_evaluate_plantime;
	}
	public String getC_orgid() {
		return c_orgid;
	}
	public void setC_orgid(String c_orgid) {
		this.c_orgid = c_orgid;
	}
	public String getC_chk_plantime() {
		return c_chk_plantime;
	}
	public void setC_chk_plantime(String c_chk_plantime) {
		this.c_chk_plantime = c_chk_plantime;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getExecUserInfo() {
		return execUserInfo;
	}
	public void setExecUserInfo(String execUserInfo) {
		this.execUserInfo = execUserInfo;
	}

	public int getC_task_kind() {
		return c_task_kind;
	}
	public void setC_task_kind(int c_task_kind) {
		this.c_task_kind = c_task_kind;
	}
	public String getIsExpired() {
		return isExpired;
	}
	public void setIsExpired(String isExpired) {
		this.isExpired = isExpired;
	}
	public String getWatchTaskDetail() {
		return watchTaskDetail;
	}
	public void setWatchTaskDetail(String watchTaskDetail) {
		this.watchTaskDetail = watchTaskDetail;
	}
	public String getSubmit() {
		return submit;
	}
	public void setSubmit(String submit) {
		this.submit = submit;
	}
	//Add By Rita.Zhou for Task Error Trace
    private String c_task_errtasksearchtype;
	public String getC_actnode_name()
    {
        return c_actnode_name;
    }
    public void setC_actnode_name(String c_actnode_name)
    {
        this.c_actnode_name = c_actnode_name;
    }
    private String c_file_path;
     

    public String getC_file_path()
    {
        return c_file_path;
    }
    public void setC_file_path(String c_file_path)
    {
        this.c_file_path = c_file_path;
    }

    public String getC_task_errtasksearchtype() {
		return c_task_errtasksearchtype;
	}
	public void setC_task_errtasksearchtype(String c_task_errtasksearchtype) {
		this.c_task_errtasksearchtype = c_task_errtasksearchtype;
	}

	private String c_positionid;     
	private String posiname;     
	private String c_arer;   
	private String areaname;     
	private String c_trigger_type;
	private String c_trigger_type_name;//增加界面模型
	private String c_trigger_typename;   
	private String c_timerule_id;    
	private String c_ttimerule_name;  
	private String c_remark;
    private String c_step_prompt;
    private String c_timerule_name;
    private String c_overtime_triggertype;
    private String c_overtime_triggertypename;
    private String c_overtime_postid;
    private String c_overtime_postname;
    private String c_ok_triggertype;
    private String c_ok_triggertypename;
    private String c_ok_postid; 
    private String c_ok_postname;   
    private String c_isokfeedback;
    private String c_ok_templetid;  
    private String c_ok_position;
    private String c_ok_templetname;    
    private String c_err_triggertype;
    private String c_err_triggertypename;
    private String c_err_postid;    
    private String c_err_postname;  
    private String c_iserrfeedback;
    private String c_err_tasktemplet_id;   
    private String c_err_tasktemplet_name;  
    private String c_creator;   
    private String c_create_time;   
    private String c_last_modifier; 
    private String c_last_modified_time;
    private String c_actitem_name;
	private String c_actitem_actstep_index;
	private String c_tracefun_name;
	
	private String c_task_id;
	private String c_task_name;
	private String c_task_type;
	private String c_trigger_cause;
	private String c_iskeyctrl;
	private String c_iskeyctrl_name;//增加界面模型
	private String c_err_sn;
	private String c_manage_section;
	private String c_manage_section_name;
	private String c_pdca;
	private String c_isgetnotify;
	private String c_isgetnotify_name;//增加界面模型
	private String c_isrelay;
	private String c_isrelay_name;//增加界面模型
	private String c_issequence;
	private String c_issequence_name;//增加界面模型
	private String c_area_id;
	private String c_exec_userid;
	private String c_exec_username;
	private String c_start_time;
	private String c_urge_time;
	private String c_end_time;
	private String c_exceed_time;
	private String c_isstd;
	private String c_isstd_name;//增加界面模型
	private String c_iscancel;
	private String c_iscancel_name;//增加界面模型
	private String c_plandown_time;
	private String c_create_userid;
	private String c_create_username;
	private String c_status;
	private String c_status_name;//增加界面模型
	private List<String> c_status_list;
	private String area;
	private String userid;
	
	private String c_handle_des;
	//private String c_iserror;
	private String c_err_status;
	private String c_urgent_level;
	private String c_std_verflag;
	
	private long c_id;
	private String c_step_index;
	private String c_tracefunid;
	private String c_area;
	private String c_object_code;
	private String c_objtype_name;
	private String c_objtype_fullname;
	private String c_obj_id;
	private String c_obj_name;
	private String c_step_params;
	private String c_tracefun_id;
	private String c_objtype_code;
	
	
	private String c_task_typename;
	private String c_down_time;
	private String c_fact_starttime;
	private String c_fact_endtime;
	private String c_up_time;
	private String c_confirm_userid;
	private String c_confirm_username;
	private String c_confirm_time;
	
	
	private String c_tracefun_isfile;
	private String c_actitem_id;
	private String c_result;
	private String c_exec_time;
	private String c_iserror;
    private String c_iserror_name;
    private String c_isfile;
    private String c_file_format;
    
   //验证的字段 
    private String c_chk_result;
    private String c_chk_userid;
    private String c_chk_username;
    private String c_chk_endtime;
    
    //评价的字段
    private String c_evaluate_result;
    private String c_evaluate_userid;
    private String c_evaluate_username;
    private String c_evaluate_time;
    private String rowindex;
       
	
	public String getRowindex() {
		return rowindex;
	}
	public void setRowindex(String rowindex) {
		this.rowindex = rowindex;
	}
	public String getC_chk_result() {
		return c_chk_result;
	}
	public void setC_chk_result(String c_chk_result) {
		this.c_chk_result = c_chk_result;
	}
	public String getC_chk_userid() {
		return c_chk_userid;
	}
	public void setC_chk_userid(String c_chk_userid) {
		this.c_chk_userid = c_chk_userid;
	}
	public String getC_chk_username() {
		return c_chk_username;
	}
	public void setC_chk_username(String c_chk_username) {
		this.c_chk_username = c_chk_username;
	}
	public String getC_chk_endtime() {
		return c_chk_endtime;
	}
	public void setC_chk_endtime(String c_chk_endtime) {
		this.c_chk_endtime = c_chk_endtime;
	}
	public String getC_evaluate_result() {
		return c_evaluate_result;
	}
	public void setC_evaluate_result(String c_evaluate_result) {
		this.c_evaluate_result = c_evaluate_result;
	}
	public String getC_evaluate_userid() {
		return c_evaluate_userid;
	}
	public void setC_evaluate_userid(String c_evaluate_userid) {
		this.c_evaluate_userid = c_evaluate_userid;
	}
	public String getC_evaluate_username() {
		return c_evaluate_username;
	}
	public void setC_evaluate_username(String c_evaluate_username) {
		this.c_evaluate_username = c_evaluate_username;
	}
	public String getC_evaluate_time() {
		return c_evaluate_time;
	}
	public void setC_evaluate_time(String c_evaluate_time) {
		this.c_evaluate_time = c_evaluate_time;
	}
	public String getC_isfile() {
		return c_isfile;
	}
	public void setC_isfile(String c_isfile) {
		this.c_isfile = c_isfile;
	}
	public String getC_file_format() {
		return c_file_format;
	}
	public void setC_file_format(String c_file_format) {
		this.c_file_format = c_file_format;
	}
	public String getC_iserror_name()
    {
        return c_iserror_name;
    }
    public void setC_iserror_name(String c_iserror_name)
    {
        this.c_iserror_name = c_iserror_name;
    }

    private String c_actitem_what;
	private String c_std;
	private String c_std_flag;
    private String c_std_flagname;
	private String c_check_std;
    private String c_check_stdname;
	private String c_check_flag;
    private String c_check_flagname;
	private String c_err_std;
    private String c_err_stdname;
	private String c_errstd_flag;
    private String c_errstd_flagname;
	private String c_know;
	private String c_std_remark;
	
	
	//任务模板管理
	private String c_isdelete;
	private String c_action_id;
	private String positionid;
	private String c_templet_actitem_id;
	private String c_actitem_code;
	private String c_actitem_actnode_id;
	private String c_actitem_actnode_code;
	private String c_actitem_actnode_name;
	
	//Add By Rita.Zhou for unStandard task Step
	private String c_step_count;//非标准任务的步骤数
	private String c_basic_id;
	
	private String c_step_index1;
	private String c_step_index2;
	private String c_step_index3;
	private String c_step_index4;
	
	
	public String getC_step_index1() {
		return c_step_index1;
	}
	public void setC_step_index1(String c_step_index1) {
		this.c_step_index1 = c_step_index1;
	}
	public String getC_step_index2() {
		return c_step_index2;
	}
	public void setC_step_index2(String c_step_index2) {
		this.c_step_index2 = c_step_index2;
	}
	public String getC_step_index3() {
		return c_step_index3;
	}
	public void setC_step_index3(String c_step_index3) {
		this.c_step_index3 = c_step_index3;
	}
	public String getC_step_index4() {
		return c_step_index4;
	}
	public void setC_step_index4(String c_step_index4) {
		this.c_step_index4 = c_step_index4;
	}
	public String getC_basic_id() {
		return c_basic_id;
	}
	public void setC_basic_id(String c_basic_id) {
		this.c_basic_id = c_basic_id;
	}
	public String getC_step_count() {
		return c_step_count;
	}
	public void setC_step_count(String c_step_count) {
		this.c_step_count = c_step_count;
	}
	public long getC_tasktemplet_id()
    {
        return c_tasktemplet_id;
    }
    public void setC_tasktemplet_id(long c_tasktemplet_id)
    {
        this.c_tasktemplet_id = c_tasktemplet_id;
    }
    public String getC_tasktemplet_name()
    {
        return c_tasktemplet_name;
    }
    public void setC_tasktemplet_name(String c_tasktemplet_name)
    {
        this.c_tasktemplet_name = c_tasktemplet_name;
    }
    public String getC_actnode_id()
    {
        return c_actnode_id;
    }
    public void setC_actnode_id(String c_actnode_id)
    {
        this.c_actnode_id = c_actnode_id;
    }
    public String getC_action_name()
    {
        return c_action_name;
    }
    public void setC_action_name(String c_action_name)
    {
        this.c_action_name = c_action_name;
    }
    public String getC_positionid()
    {
        return c_positionid;
    }
    public void setC_positionid(String c_positionid)
    {
        this.c_positionid = c_positionid;
    }
    public String getPosiname()
    {
        return posiname;
    }
    public void setPosiname(String posiname)
    {
        this.posiname = posiname;
    }
    public String getC_arer()
    {
        return c_arer;
    }
    public void setC_arer(String c_arer)
    {
        this.c_arer = c_arer;
    }
    public String getAreaname()
    {
        return areaname;
    }
    public void setAreaname(String areaname)
    {
        this.areaname = areaname;
    }
    public String getC_trigger_type()
    {
        return c_trigger_type;
    }
    public void setC_trigger_type(String c_trigger_type)
    {
        this.c_trigger_type = c_trigger_type;
    }
    public String getC_trigger_typename()
    {
        return c_trigger_typename;
    }
    public void setC_trigger_typename(String c_trigger_typename)
    {
        this.c_trigger_typename = c_trigger_typename;
    }
    public String getC_timerule_id()
    {
        return c_timerule_id;
    }
    public void setC_timerule_id(String c_timerule_id)
    {
        this.c_timerule_id = c_timerule_id;
    }
    public String getC_ttimerule_name()
    {
        return c_ttimerule_name;
    }
    public void setC_ttimerule_name(String c_ttimerule_name)
    {
        this.c_ttimerule_name = c_ttimerule_name;
    }
    public String getC_remark()
    {
        return c_remark;
    }
    public void setC_remark(String c_remark)
    {
        this.c_remark = c_remark;
    }
    public String getC_timerule_name()
    {
        return c_timerule_name;
    }
    public void setC_timerule_name(String c_timerule_name)
    {
        this.c_timerule_name = c_timerule_name;
    }
    public String getC_overtime_triggertype()
    {
        return c_overtime_triggertype;
    }
    public void setC_overtime_triggertype(String c_overtime_triggertype)
    {
        this.c_overtime_triggertype = c_overtime_triggertype;
    }
    public String getC_overtime_triggertypename()
    {
        return c_overtime_triggertypename;
    }
    public void setC_overtime_triggertypename(String c_overtime_triggertypename)
    {
        this.c_overtime_triggertypename = c_overtime_triggertypename;
    }
    public String getC_overtime_postid()
    {
        return c_overtime_postid;
    }
    public void setC_overtime_postid(String c_overtime_postid)
    {
        this.c_overtime_postid = c_overtime_postid;
    }
    public String getC_overtime_postname()
    {
        return c_overtime_postname;
    }
    public void setC_overtime_postname(String c_overtime_postname)
    {
        this.c_overtime_postname = c_overtime_postname;
    }
    public String getC_ok_triggertype()
    {
        return c_ok_triggertype;
    }
    public void setC_ok_triggertype(String c_ok_triggertype)
    {
        this.c_ok_triggertype = c_ok_triggertype;
    }
    public String getC_ok_triggertypename()
    {
        return c_ok_triggertypename;
    }
    public void setC_ok_triggertypename(String c_ok_triggertypename)
    {
        this.c_ok_triggertypename = c_ok_triggertypename;
    }
    public String getC_ok_postid()
    {
        return c_ok_postid;
    }
    public void setC_ok_postid(String c_ok_postid)
    {
        this.c_ok_postid = c_ok_postid;
    }
    public String getC_ok_postname()
    {
        return c_ok_postname;
    }
    public void setC_ok_postname(String c_ok_postname)
    {
        this.c_ok_postname = c_ok_postname;
    }
    public String getC_isokfeedback()
    {
        return c_isokfeedback;
    }
    public void setC_isokfeedback(String c_isokfeedback)
    {
        this.c_isokfeedback = c_isokfeedback;
    }
    public String getC_ok_templetid()
    {
        return c_ok_templetid;
    }
    public void setC_ok_templetid(String c_ok_templetid)
    {
        this.c_ok_templetid = c_ok_templetid;
    }
    public String getC_ok_templetname()
    {
        return c_ok_templetname;
    }
    public void setC_ok_templetname(String c_ok_templetname)
    {
        this.c_ok_templetname = c_ok_templetname;
    }
    public String getC_err_triggertype()
    {
        return c_err_triggertype;
    }
    public void setC_err_triggertype(String c_err_triggertype)
    {
        this.c_err_triggertype = c_err_triggertype;
    }
    public String getC_err_triggertypename()
    {
        return c_err_triggertypename;
    }
    public void setC_err_triggertypename(String c_err_triggertypename)
    {
        this.c_err_triggertypename = c_err_triggertypename;
    }
    public String getC_err_postid()
    {
        return c_err_postid;
    }
    public void setC_err_postid(String c_err_postid)
    {
        this.c_err_postid = c_err_postid;
    }
    public String getC_err_postname()
    {
        return c_err_postname;
    }
    public void setC_err_postname(String c_err_postname)
    {
        this.c_err_postname = c_err_postname;
    }
    public String getC_iserrfeedback()
    {
        return c_iserrfeedback;
    }
    public void setC_iserrfeedback(String c_iserrfeedback)
    {
        this.c_iserrfeedback = c_iserrfeedback;
    }
    public String getC_err_tasktemplet_id()
    {
        return c_err_tasktemplet_id;
    }
    public void setC_err_tasktemplet_id(String c_err_tasktemplet_id)
    {
        this.c_err_tasktemplet_id = c_err_tasktemplet_id;
    }
    public String getC_err_tasktemplet_name()
    {
        return c_err_tasktemplet_name;
    }
    public void setC_err_tasktemplet_name(String c_err_tasktemplet_name)
    {
        this.c_err_tasktemplet_name = c_err_tasktemplet_name;
    }
    public String getC_creator()
    {
        return c_creator;
    }
    public void setC_creator(String c_creator)
    {
        this.c_creator = c_creator;
    }
    public String getC_create_time()
    {
        return c_create_time;
    }
    public void setC_create_time(String c_create_time)
    {
        this.c_create_time = c_create_time;
    }
    public String getC_last_modifier()
    {
        return c_last_modifier;
    }
    public void setC_last_modifier(String c_last_modifier)
    {
        this.c_last_modifier = c_last_modifier;
    }
    public String getC_last_modified_time()
    {
        return c_last_modified_time;
    }
    public void setC_last_modified_time(String c_last_modified_time)
    {
        this.c_last_modified_time = c_last_modified_time;
    }
    public String getC_step_prompt()
    {
        return c_step_prompt;
    }
    public void setC_step_prompt(String c_step_prompt)
    {
        this.c_step_prompt = c_step_prompt;
    }
    public String getC_actitem_name()
    {
        return c_actitem_name;
    }
    public void setC_actitem_name(String c_actitem_name)
    {
        this.c_actitem_name = c_actitem_name;
    }
    public String getC_actitem_actstep_index()
    {
        return c_actitem_actstep_index;
    }
    public void setC_actitem_actstep_index(String c_actitem_actstep_index)
    {
        this.c_actitem_actstep_index = c_actitem_actstep_index;
    }
    public String getC_tracefun_name()
    {
        return c_tracefun_name;
    }
    public void setC_tracefun_name(String c_tracefun_name)
    {
        this.c_tracefun_name = c_tracefun_name;
    }
	public String getC_task_id() {
		return c_task_id;
	}
	public void setC_task_id(String c_task_id) {
		this.c_task_id = c_task_id;
	}
	public String getC_task_name() {
		return c_task_name;
	}
	public void setC_task_name(String c_task_name) {
		this.c_task_name = c_task_name;
	}
	public String getC_task_type() {
		return c_task_type;
	}
	public void setC_task_type(String c_task_type) {
		this.c_task_type = c_task_type;
	}
	public String getC_trigger_cause() {
		return c_trigger_cause;
	}
	public void setC_trigger_cause(String c_trigger_cause) {
		this.c_trigger_cause = c_trigger_cause;
	}
	public String getC_iskeyctrl() {
		return c_iskeyctrl;
	}
	public void setC_iskeyctrl(String c_iskeyctrl) {
		this.c_iskeyctrl = c_iskeyctrl;
	}
	public String getC_err_sn() {
		return c_err_sn;
	}
	public void setC_err_sn(String c_err_sn) {
		this.c_err_sn = c_err_sn;
	}
	public String getC_manage_section() {
		return c_manage_section;
	}
	public void setC_manage_section(String c_manage_section) {
		this.c_manage_section = c_manage_section;
	}
	public String getC_manage_section_name() {
		return c_manage_section_name;
	}
	public void setC_manage_section_name(String c_manage_section_name) {
		this.c_manage_section_name = c_manage_section_name;
	}
	public String getC_pdca() {
		return c_pdca;
	}
	public void setC_pdca(String c_pdca) {
		this.c_pdca = c_pdca;
	}
	public String getC_isgetnotify() {
		return c_isgetnotify;
	}
	public void setC_isgetnotify(String c_isgetnotify) {
		this.c_isgetnotify = c_isgetnotify;
	}
	public String getC_isrelay() {
		return c_isrelay;
	}
	public void setC_isrelay(String c_isrelay) {
		this.c_isrelay = c_isrelay;
	}
	public String getC_issequence() {
		return c_issequence;
	}
	public void setC_issequence(String c_issequence) {
		this.c_issequence = c_issequence;
	}
	public String getC_area_id() {
		return c_area_id;
	}
	public void setC_area_id(String c_area_id) {
		this.c_area_id = c_area_id;
	}
	public String getC_exec_userid() {
		return c_exec_userid;
	}
	public void setC_exec_userid(String c_exec_userid) {
		this.c_exec_userid = c_exec_userid;
	}
	public String getC_exec_username() {
		return c_exec_username;
	}
	public void setC_exec_username(String c_exec_username) {
		this.c_exec_username = c_exec_username;
	}
	public String getC_start_time() {
		return c_start_time;
	}
	public void setC_start_time(String c_start_time) {
		this.c_start_time = c_start_time;
	}
	public String getC_urge_time() {
		return c_urge_time;
	}
	public void setC_urge_time(String c_urge_time) {
		this.c_urge_time = c_urge_time;
	}
	public String getC_end_time() {
		return c_end_time;
	}
	public void setC_end_time(String c_end_time) {
		this.c_end_time = c_end_time;
	}
	public String getC_exceed_time() {
		return c_exceed_time;
	}
	public void setC_exceed_time(String c_exceed_time) {
		this.c_exceed_time = c_exceed_time;
	}
	public String getC_isstd() {
		return c_isstd;
	}
	public void setC_isstd(String c_isstd) {
		this.c_isstd = c_isstd;
	}
	public String getC_plandown_time() {
		return c_plandown_time;
	}
	public void setC_plandown_time(String c_plandown_time) {
		this.c_plandown_time = c_plandown_time;
	}
	public String getC_create_userid() {
		return c_create_userid;
	}
	public void setC_create_userid(String c_create_userid) {
		this.c_create_userid = c_create_userid;
	}
	public String getC_create_username() {
		return c_create_username;
	}
	public void setC_create_username(String c_create_username) {
		this.c_create_username = c_create_username;
	}
	public String getC_status() {
		return c_status;
	}
	public void setC_status(String c_status) {
		this.c_status = c_status;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public long getC_id() {
		return c_id;
	}
	public void setC_id(long c_id) {
		this.c_id = c_id;
	}
	public String getC_step_index() {
		return c_step_index;
	}
	public void setC_step_index(String c_step_index) {
		this.c_step_index = c_step_index;
	}
	public String getC_tracefunid() {
		return c_tracefunid;
	}
	public void setC_tracefunid(String c_tracefunid) {
		this.c_tracefunid = c_tracefunid;
	}
	public String getC_area() {
		return c_area;
	}
	public void setC_area(String c_area) {
		this.c_area = c_area;
	}
	public String getC_object_code() {
		return c_object_code;
	}
	public void setC_object_code(String c_object_code) {
		this.c_object_code = c_object_code;
	}
	public String getC_objtype_name() {
		return c_objtype_name;
	}
	public void setC_objtype_name(String c_objtype_name) {
		this.c_objtype_name = c_objtype_name;
	}
	public String getC_objtype_fullname() {
		return c_objtype_fullname;
	}
	public void setC_objtype_fullname(String c_objtype_fullname) {
		this.c_objtype_fullname = c_objtype_fullname;
	}
	public String getC_obj_id() {
		return c_obj_id;
	}
	public void setC_obj_id(String c_obj_id) {
		this.c_obj_id = c_obj_id;
	}
	public String getC_obj_name() {
		return c_obj_name;
	}
	public void setC_obj_name(String c_obj_name) {
		this.c_obj_name = c_obj_name;
	}
	public String getC_step_params() {
		return c_step_params;
	}
	public void setC_step_params(String c_step_params) {
		this.c_step_params = c_step_params;
	}
	public String getC_tracefun_id() {
		return c_tracefun_id;
	}
	public void setC_tracefun_id(String c_tracefun_id) {
		this.c_tracefun_id = c_tracefun_id;
	}
	public String getC_objtype_code() {
		return c_objtype_code;
	}
	public void setC_objtype_code(String c_objtype_code) {
		this.c_objtype_code = c_objtype_code;
	}
	public String getC_task_typename() {
		return c_task_typename;
	}
	public void setC_task_typename(String c_task_typename) {
		this.c_task_typename = c_task_typename;
	}
	public String getC_down_time() {
		return c_down_time;
	}
	public void setC_down_time(String c_down_time) {
		this.c_down_time = c_down_time;
	}
	public String getC_fact_starttime() {
		return c_fact_starttime;
	}
	public void setC_fact_starttime(String c_fact_starttime) {
		this.c_fact_starttime = c_fact_starttime;
	}
	public String getC_fact_endtime() {
		return c_fact_endtime;
	}
	public void setC_fact_endtime(String c_fact_endtime) {
		this.c_fact_endtime = c_fact_endtime;
	}
	public String getC_up_time() {
		return c_up_time;
	}
	public void setC_up_time(String c_up_time) {
		this.c_up_time = c_up_time;
	}
	public String getC_confirm_userid() {
		return c_confirm_userid;
	}
	public void setC_confirm_userid(String c_confirm_userid) {
		this.c_confirm_userid = c_confirm_userid;
	}
	public String getC_confirm_username() {
		return c_confirm_username;
	}
	public void setC_confirm_username(String c_confirm_username) {
		this.c_confirm_username = c_confirm_username;
	}
	public String getC_confirm_time() {
		return c_confirm_time;
	}
	public void setC_confirm_time(String c_confirm_time) {
		this.c_confirm_time = c_confirm_time;
	}
	public String getC_tracefun_isfile() {
		return c_tracefun_isfile;
	}
	public void setC_tracefun_isfile(String c_tracefun_isfile) {
		this.c_tracefun_isfile = c_tracefun_isfile;
	}
	public String getC_actitem_id() {
		return c_actitem_id;
	}
	public void setC_actitem_id(String c_actitem_id) {
		this.c_actitem_id = c_actitem_id;
	}
	public String getC_result() {
		return c_result;
	}
	public void setC_result(String c_result) {
		this.c_result = c_result;
	}
	public String getC_exec_time() {
		return c_exec_time;
	}
	public void setC_exec_time(String c_exec_time) {
		this.c_exec_time = c_exec_time;
	}
	public String getC_iserror() {
		return c_iserror;
	}
	public void setC_iserror(String c_iserror) {
		this.c_iserror = c_iserror;
	}
	public String getC_actitem_what() {
		return c_actitem_what;
	}
	public void setC_actitem_what(String c_actitem_what) {
		this.c_actitem_what = c_actitem_what;
	}
	public String getC_std() {
		return c_std;
	}
	public void setC_std(String c_std) {
		this.c_std = c_std;
	}
	public String getC_std_flag() {
		return c_std_flag;
	}
	public void setC_std_flag(String c_std_flag) {
		this.c_std_flag = c_std_flag;
	}
	public String getC_check_std() {
		return c_check_std;
	}
	public void setC_check_std(String c_check_std) {
		this.c_check_std = c_check_std;
	}
	public String getC_check_flag() {
		return c_check_flag;
	}
	public void setC_check_flag(String c_check_flag) {
		this.c_check_flag = c_check_flag;
	}
	public String getC_err_std() {
		return c_err_std;
	}
	public void setC_err_std(String c_err_std) {
		this.c_err_std = c_err_std;
	}
	public String getC_errstd_flag() {
		return c_errstd_flag;
	}
	public void setC_errstd_flag(String c_errstd_flag) {
		this.c_errstd_flag = c_errstd_flag;
	}
	public String getC_know() {
		return c_know;
	}
	public void setC_know(String c_know) {
		this.c_know = c_know;
	}
	public String getC_std_remark() {
		return c_std_remark;
	}
	public void setC_std_remark(String c_std_remark) {
		this.c_std_remark = c_std_remark;
	}
	public String getC_isdelete() {
		return c_isdelete;
	}
	public void setC_isdelete(String c_isdelete) {
		this.c_isdelete = c_isdelete;
	}
	public String getC_action_id() {
		return c_action_id;
	}
	public void setC_action_id(String c_action_id) {
		this.c_action_id = c_action_id;
	}
	public String getPositionid() {
		return positionid;
	}
	public void setPositionid(String positionid) {
		this.positionid = positionid;
	}
	 
	public String getC_actitem_actnode_id() {
		return c_actitem_actnode_id;
	}
	public void setC_actitem_actnode_id(String c_actitem_actnode_id) {
		this.c_actitem_actnode_id = c_actitem_actnode_id;
	}
	public String getC_actitem_actnode_code() {
		return c_actitem_actnode_code;
	}
	public void setC_actitem_actnode_code(String c_actitem_actnode_code) {
		this.c_actitem_actnode_code = c_actitem_actnode_code;
	}
	public String getC_actitem_actnode_name() {
		return c_actitem_actnode_name;
	}
	public void setC_actitem_actnode_name(String c_actitem_actnode_name) {
		this.c_actitem_actnode_name = c_actitem_actnode_name;
	}
	public String getC_status_name() {
		return c_status_name;
	}
	public void setC_status_name(String c_status_name) {
		this.c_status_name = c_status_name;
	}
	public String getC_iskeyctrl_name() {
		return c_iskeyctrl_name;
	}
	public void setC_iskeyctrl_name(String c_iskeyctrl_name) {
		this.c_iskeyctrl_name = c_iskeyctrl_name;
	}
	public String getC_issequence_name() {
		return c_issequence_name;
	}
	public void setC_issequence_name(String c_issequence_name) {
		this.c_issequence_name = c_issequence_name;
	}
	public String getC_isgetnotify_name() {
		return c_isgetnotify_name;
	}
	public void setC_isgetnotify_name(String c_isgetnotify_name) {
		this.c_isgetnotify_name = c_isgetnotify_name;
	}
	public String getC_isrelay_name() {
		return c_isrelay_name;
	}
	public void setC_isrelay_name(String c_isrelay_name) {
		this.c_isrelay_name = c_isrelay_name;
	}
	public String getC_trigger_type_name() {
		return c_trigger_type_name;
	}
	public void setC_trigger_type_name(String c_trigger_type_name) {
		this.c_trigger_type_name = c_trigger_type_name;
	}
	
	/*
	public String getC_task_type_name() {
		if(c_task_type_name.equals("临时任务")){
			c_task_type_name="工作安排";
		}else if(c_task_type_name.equals("协调任务")){
			c_task_type_name="异常反馈";
		}
		return c_task_type_name;
	}
	public void setC_task_type_name(String c_task_type_name) {
		this.c_task_type_name = c_task_type_name;
	}*/
	public String getC_isstd_name() {
		return c_isstd_name;
	}
	public void setC_isstd_name(String c_isstd_name) {
		this.c_isstd_name = c_isstd_name;
	}
	public String getC_iscancel() {
		return c_iscancel;
	}
	public void setC_iscancel(String c_iscancel) {
		this.c_iscancel = c_iscancel;
	}
	public String getC_iscancel_name() {
		return c_iscancel_name;
	}
	public void setC_iscancel_name(String c_iscancel_name) {
		this.c_iscancel_name = c_iscancel_name;
	}
    public String getC_std_flagname()
    {
        return c_std_flagname;
    }
    public void setC_std_flagname(String c_std_flagname)
    {
        this.c_std_flagname = c_std_flagname;
    }
    public String getC_check_stdname()
    {
        return c_check_stdname;
    }
    public void setC_check_stdname(String c_check_stdname)
    {
        this.c_check_stdname = c_check_stdname;
    }
    public String getC_check_flagname()
    {
        return c_check_flagname;
    }
    public void setC_check_flagname(String c_check_flagname)
    {
        this.c_check_flagname = c_check_flagname;
    }
    public String getC_err_stdname()
    {
        return c_err_stdname;
    }
    public void setC_err_stdname(String c_err_stdname)
    {
        this.c_err_stdname = c_err_stdname;
    }
    public String getC_errstd_flagname()
    {
        return c_errstd_flagname;
    }
    public void setC_errstd_flagname(String c_errstd_flagname)
    {
        this.c_errstd_flagname = c_errstd_flagname;
    }
    public String getC_templet_actitem_id()
    {
        return c_templet_actitem_id;
    }
    public void setC_templet_actitem_id(String c_templet_actitem_id)
    {
        this.c_templet_actitem_id = c_templet_actitem_id;
    }
    public String getC_actitem_code()
    {
        return c_actitem_code;
    }
    public void setC_actitem_code(String c_actitem_code)
    {
        this.c_actitem_code = c_actitem_code;
    }
    public String getC_ok_position()
    {
        return c_ok_position;
    }
    public void setC_ok_position(String c_ok_position)
    {
        this.c_ok_position = c_ok_position;
    }
    public String getC_cancel_cause()
    {
        return c_cancel_cause;
    }
    public void setC_cancel_cause(String c_cancel_cause)
    {
        this.c_cancel_cause = c_cancel_cause;
    }
	public String getC_department_id() {
		return c_department_id;
	}
	public void setC_department_id(String c_department_id) {
		this.c_department_id = c_department_id;
	}
	public String getC_org_id() {
		return c_orgid;
	}
	public void setC_org_id(String c_org_id) {
		this.c_orgid = c_org_id;
	}
	public String getC_classgroup_id() {
		return c_classgroup_id;
	}
	public void setC_classgroup_id(String c_classgroup_id) {
		this.c_classgroup_id = c_classgroup_id;
	}
	public List<String> getC_status_list() {
		return c_status_list;
	}
	
	public String getC_status_listStr(){
		String listStr="(";
		if(this.c_status_list==null){
			return null;
		}else{
			for(String status:c_status_list){
				listStr+=status+",";
			}
		}
		listStr=listStr.substring(0,listStr.length()-1)+")";
		return listStr;
	}
	
	public void setC_status_list(List<String> c_status_list) {
		this.c_status_list = c_status_list;
	}
	public String getWatchStdAction() {
		return watchStdAction;
	}
	public void setWatchStdAction(String watchStdAction) {
		this.watchStdAction = watchStdAction;
	}
	
	public String getC_handle_des() {
		return c_handle_des;
	}
	public void setC_handle_des(String v_handle_des) {
		this.c_handle_des = v_handle_des;
	}
	public String getC_err_status() {
		return c_err_status;
	}
	public void setC_err_status(String v_err_status) {
		this.c_err_status = v_err_status;
	}
	public String getC_urgent_level() {
		return c_urgent_level;
	}
	public void setC_urgent_level(String v_urgent_level) {
		this.c_urgent_level = v_urgent_level;
	}
	public String getC_std_verflag() {
		return c_std_verflag;
	}
	public void setC_std_verflag(String v_std_verflag) {
		this.c_std_verflag = v_std_verflag;
	}
	
	private String v_task_type;
    private String v_task_name;
    private String v_manage_section;
    private String v_start_time;
    private String v_end_time;
    private String v_exec_userid;
    private String v_sender_userid;
    private String v_remark;
    private String v_new_taskid;
    public String getV_task_type()
    {
        return v_task_type;
    }
    public void setV_task_type(String v_task_type)
    {
        this.v_task_type = v_task_type;
    }
    public String getV_task_name()
    {
        return v_task_name;
    }
    public void setV_task_name(String v_task_name)
    {
        this.v_task_name = v_task_name;
    }
    public String getV_manage_section()
    {
        return v_manage_section;
    }
    public void setV_manage_section(String v_manage_section)
    {
        this.v_manage_section = v_manage_section;
    }
    public String getV_start_time()
    {
        return v_start_time;
    }
    public void setV_start_time(String v_start_time)
    {
        this.v_start_time = v_start_time;
    }
    public String getV_end_time()
    {
        return v_end_time;
    }
    public void setV_end_time(String v_end_time)
    {
        this.v_end_time = v_end_time;
    }
    public String getV_exec_userid()
    {
        return v_exec_userid;
    }
    public void setV_exec_userid(String v_exec_userid)
    {
        this.v_exec_userid = v_exec_userid;
    }
    public String getV_sender_userid()
    {
        return v_sender_userid;
    }
    public void setV_sender_userid(String v_sender_userid)
    {
        this.v_sender_userid = v_sender_userid;
    }
    public String getV_remark()
    {
        return v_remark;
    }
    public void setV_remark(String v_remark)
    {
        this.v_remark = v_remark;
    }
    public String getV_new_taskid()
    {
        return v_new_taskid;
    }
    public void setV_new_taskid(String v_new_taskid)
    {
        this.v_new_taskid = v_new_taskid;
    }
	public String getOrgdepartname() {
		return orgdepartname;
	}
	public void setOrgdepartname(String orgdepartname) {
		this.orgdepartname = orgdepartname;
	}
	
    private String c_groupindex;
    
	public String getC_groupindex() {
		return c_groupindex;
	}
	public void setC_groupindex(String c_groupindex) {
		this.c_groupindex = c_groupindex;
	}
	
}
