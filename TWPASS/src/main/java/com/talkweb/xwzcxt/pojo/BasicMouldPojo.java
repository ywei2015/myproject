package com.talkweb.xwzcxt.pojo;

import com.talkweb.twdpe.core.data.BasePOJO;

public class BasicMouldPojo extends BasePOJO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8104510025606160279L;
	public BasicMouldPojo()
    {
        super();
    }
	private String tablename;
	//基础管理
    private String c_objtype_code;
    private String c_objtype_id;
    private String c_up_objtype_code;
    private String c_obj_fullname;
    private String c_objtype_name;
    private String c_objtype_fullname;
    private String c_node_deep;
    private String c_remark;
    private String c_creator;
    private String c_create_time;
    private String c_last_modifier;
    private String c_last_modifued_time;
    private String c_isdelete;
    
    private long c_obj_id;
    private String c_obj_name;
    private String c_obj_model;
    private String c_obj_uid;
    private String c_scan_code;
    private String c_area;
    private String c_orgid;
    private String c_conservator;
    private String c_config_date;
    private String c_valid_date;
    private String c_state;
    private String c_count;
    private String backup1;
    private String backup2;
    
    private String displayname;
    private String orgid;
    private String userid;
    
    private long c_id;
    private String c_calendar_type;
    private String c_calendar_name;
    private String c_date;
    
    private String positionid;
    private String posiname;
    private String area;
    private String areaname;
    
    private String c_day_of_week;
    private String c_theory_isworkday;
    private String c_theory_isworkday_name;//增加界面模型
    private String c_fact_isworkday;
    private String c_fact_isworkday_name;//增加界面模型
    private String c_work_date;

    private String c_statu;
    private String c_statu_name;
    private String c_shift_id;
    private String c_organize_id;
    private String c_order;
    private String c_shift_name;
    private String c_start_time;
    private String c_end_time;
    private String c_exec_time;
    private String c_stop_time;
    
    
    
    //标准管理
    private String c_action_id;
    private String c_action_code;
    private String c_action_name;
    private String c_up_action_code;
    private String c_up_action_id;
    
    private String c_actnode_id;
    private String c_actnode_code;
    private String c_actnode_name;
    private String c_iskeyctrl;
    private String c_iskeyctrl_name;//增加界面模型
    private String c_issequence;
    private String c_issequence_name;//增加界面模型
    private String c_isgetnotify;
    private String c_isgetnotify_name;//增加界面模型
    private String c_isrelay;
    private String c_isrelay_name;//增加界面模型
    private String c_pdca;
    private String c_manage_section;
    private String c_manage_section_name;
    
    private long c_actitem_id;
    private String c_actitem_code;
    private String c_actitem_name;
    private String c_actitem_what;
    private String c_what_files;
    private String c_std;
    private String c_std_files;
    private String c_std_flag;
    private String c_std_flag_name;//增加界面模型
    private String c_check_std;
    private String c_checkstd_files;
    private String c_check_flag;
    private String c_check_flag_name;//增加界面模型
    private String c_err_std;
    private String c_errstd_files;
    private String c_errstd_flag;
    private String c_errstd_flag_name;//增加界面模型
    private String c_know;
    private String c_review_std;
    
    private String c_actstep_index;
    private String c_tracefun_id;
    private String c_tracefun_name;
    private String c_step_prompt;
    
    private long c_tasktemplet_id;
    private String c_tasktemplet_name;
    //时间规则
    private long c_timerule_id;
    private String c_timerule_name;
    private String c_ctrl_type;
    private String c_ctrl_type_name;
    private String c_month_set;
    private String c_day_set;
    private String c_week_set;
    private String c_date_relate;
    private String c_date_relate_name;
    private String c_time_relate;
    private String c_time_relate_name;
    private String c_urge_time;
    private String c_exceed_time;
    private String c_rule_define;
    
    private String orgname;
    
    
    
    
	public String getDisplayname() {
		return displayname;
	}
	public void setDisplayname(String displayname) {
		this.displayname = displayname;
	}
	public String getOrgid() {
		return orgid;
	}
	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getOrgname() {
		return orgname;
	}
	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}
	public String getC_objtype_code() {
		return c_objtype_code;
	}
	public void setC_objtype_code(String c_objtype_code) {
		this.c_objtype_code = c_objtype_code;
	}
	public String getC_up_objtype_code() {
		return c_up_objtype_code;
	}
	public void setC_up_objtype_code(String c_up_objtype_code) {
		this.c_up_objtype_code = c_up_objtype_code;
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
	public String getC_node_deep() {
		return c_node_deep;
	}
	public void setC_node_deep(String c_node_deep) {
		this.c_node_deep = c_node_deep;
	}
	public String getC_remark() {
		return c_remark;
	}
	public void setC_remark(String c_remark) {
		this.c_remark = c_remark;
	}
	public String getC_creator() {
		return c_creator;
	}
	public void setC_creator(String c_creator) {
		this.c_creator = c_creator;
	}
	public String getC_create_time() {
		return c_create_time;
	}
	public void setC_create_time(String c_create_time) {
		this.c_create_time = c_create_time;
	}
	public String getC_last_modifier() {
		return c_last_modifier;
	}
	public void setC_last_modifier(String c_last_modifier) {
		this.c_last_modifier = c_last_modifier;
	}
	public String getC_last_modifued_time() {
		return c_last_modifued_time;
	}
	public void setC_last_modifued_time(String c_last_modifued_time) {
		this.c_last_modifued_time = c_last_modifued_time;
	}
	public String getC_isdelete() {
		return c_isdelete;
	}
	public void setC_isdelete(String c_isdelete) {
		this.c_isdelete = c_isdelete;
	}
	public String getC_obj_name() {
		return c_obj_name;
	}
	public void setC_obj_name(String c_obj_name) {
		this.c_obj_name = c_obj_name;
	}
	public String getC_obj_model() {
		return c_obj_model;
	}
	public void setC_obj_model(String c_obj_model) {
		this.c_obj_model = c_obj_model;
	}
	public String getC_obj_uid() {
		return c_obj_uid;
	}
	public void setC_obj_uid(String c_obj_uid) {
		this.c_obj_uid = c_obj_uid;
	}
	public String getC_scan_code() {
		return c_scan_code;
	}
	public void setC_scan_code(String c_scan_code) {
		this.c_scan_code = c_scan_code;
	}
	public String getC_area() {
		return c_area;
	}
	public void setC_area(String c_area) {
		this.c_area = c_area;
	}
	public String getC_orgid() {
		return c_orgid;
	}
	public void setC_orgid(String c_orgid) {
		this.c_orgid = c_orgid;
	}
	public String getC_conservator() {
		return c_conservator;
	}
	public void setC_conservator(String c_conservator) {
		this.c_conservator = c_conservator;
	}
	public String getC_config_date() {
		return c_config_date;
	}
	public void setC_config_date(String c_config_date) {
		this.c_config_date = c_config_date;
	}
	public String getC_valid_date() {
		return c_valid_date;
	}
	public void setC_valid_date(String c_valid_date) {
		this.c_valid_date = c_valid_date;
	}
	public String getC_state() {
		return c_state;
	}
	public void setC_state(String c_state) {
		this.c_state = c_state;
	}
	public String getC_count() {
		return c_count;
	}
	public void setC_count(String c_count) {
		this.c_count = c_count;
	}
	public String getBackup1() {
		return backup1;
	}
	public void setBackup1(String backup1) {
		this.backup1 = backup1;
	}
	public String getBackup2() {
		return backup2;
	}
	public void setBackup2(String backup2) {
		this.backup2 = backup2;
	}
	public long getC_id() {
		return c_id;
	}
	public void setC_id(long c_id) {
		this.c_id = c_id;
	}
	public String getC_calendar_type() {
		return c_calendar_type;
	}
	public void setC_calendar_type(String c_calendar_type) {
		this.c_calendar_type = c_calendar_type;
	}
	public String getC_calendar_name() {
		return c_calendar_name;
	}
	public void setC_calendar_name(String c_calendar_name) {
		this.c_calendar_name = c_calendar_name;
	}
	public String getC_date() {
		return c_date;
	}
	public void setC_date(String c_date) {
		this.c_date = c_date;
	}
	public String getC_day_of_week() {
		return c_day_of_week;
	}
	public void setC_day_of_week(String c_day_of_week) {
		this.c_day_of_week = c_day_of_week;
	}
	public String getC_theory_isworkday() {
		return c_theory_isworkday;
	}
	public void setC_theory_isworkday(String c_theory_isworkday) {
		this.c_theory_isworkday = c_theory_isworkday;
	}
	public String getC_fact_isworkday() {
		return c_fact_isworkday;
	}
	public void setC_fact_isworkday(String c_fact_isworkday) {
		this.c_fact_isworkday = c_fact_isworkday;
	}
	public String getC_work_date() {
		return c_work_date;
	}
	public void setC_work_date(String c_work_date) {
		this.c_work_date = c_work_date;
	}
	public String getC_shift_id() {
		return c_shift_id;
	}
	public void setC_shift_id(String c_shift_id) {
		this.c_shift_id = c_shift_id;
	}
	public String getC_organize_id() {
		return c_organize_id;
	}
	public void setC_organize_id(String c_organize_id) {
		this.c_organize_id = c_organize_id;
	}
	public String getC_order() {
		return c_order;
	}
	public void setC_order(String c_order) {
		this.c_order = c_order;
	}
	public String getC_shift_name() {
		return c_shift_name;
	}
	public void setC_shift_name(String c_shift_name) {
		this.c_shift_name = c_shift_name;
	}
	public String getC_start_time() {
		return c_start_time;
	}
	public void setC_start_time(String c_start_time) {
		this.c_start_time = c_start_time;
	}
	public String getC_end_time() {
		return c_end_time;
	}
	public void setC_end_time(String c_end_time) {
		this.c_end_time = c_end_time;
	}
	public String getC_exec_time() {
		return c_exec_time;
	}
	public void setC_exec_time(String c_exec_time) {
		this.c_exec_time = c_exec_time;
	}
	public String getC_stop_time() {
		return c_stop_time;
	}
	public void setC_stop_time(String c_stop_time) {
		this.c_stop_time = c_stop_time;
	}
	public String getC_action_id() {
		return c_action_id;
	}
	public void setC_action_id(String c_action_id) {
		this.c_action_id = c_action_id;
	}
	public String getC_action_code() {
		return c_action_code;
	}
	public void setC_action_code(String c_action_code) {
		this.c_action_code = c_action_code;
	}
	public String getC_action_name() {
		return c_action_name;
	}
	public void setC_action_name(String c_action_name) {
		this.c_action_name = c_action_name;
	}
	public String getC_up_action_code() {
		return c_up_action_code;
	}
	public void setC_up_action_code(String c_up_action_code) {
		this.c_up_action_code = c_up_action_code;
	}
	public String getC_up_action_id() {
		return c_up_action_id;
	}
	public void setC_up_action_id(String c_up_action_id) {
		this.c_up_action_id = c_up_action_id;
	}
	public String getC_actnode_id() {
		return c_actnode_id;
	}
	public void setC_actnode_id(String c_actnode_id) {
		this.c_actnode_id = c_actnode_id;
	}
	public String getC_actnode_code() {
		return c_actnode_code;
	}
	public void setC_actnode_code(String c_actnode_code) {
		this.c_actnode_code = c_actnode_code;
	}
	public String getC_actnode_name() {
		return c_actnode_name;
	}
	public void setC_actnode_name(String c_actnode_name) {
		this.c_actnode_name = c_actnode_name;
	}
	public String getC_iskeyctrl() {
		return c_iskeyctrl;
	}
	public void setC_iskeyctrl(String c_iskeyctrl) {
		this.c_iskeyctrl = c_iskeyctrl;
	}
	public String getC_issequence() {
		return c_issequence;
	}
	public void setC_issequence(String c_issequence) {
		this.c_issequence = c_issequence;
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
	public String getC_pdca() {
		return c_pdca;
	}
	public void setC_pdca(String c_pdca) {
		this.c_pdca = c_pdca;
	}
	public String getC_manage_section() {
		return c_manage_section;
	}
	public void setC_manage_section(String c_manage_section) {
		this.c_manage_section = c_manage_section;
	}
	public String getC_actitem_code() {
		return c_actitem_code;
	}
	public void setC_actitem_code(String c_actitem_code) {
		this.c_actitem_code = c_actitem_code;
	}
	public String getC_actitem_name() {
		return c_actitem_name;
	}
	public void setC_actitem_name(String c_actitem_name) {
		this.c_actitem_name = c_actitem_name;
	}
	public String getC_actitem_what() {
		return c_actitem_what;
	}
	public void setC_actitem_what(String c_actitem_what) {
		this.c_actitem_what = c_actitem_what;
	}
	public String getC_what_files() {
		return c_what_files;
	}
	public void setC_what_files(String c_what_files) {
		this.c_what_files = c_what_files;
	}
	public String getC_std() {
		return c_std;
	}
	public void setC_std(String c_std) {
		this.c_std = c_std;
	}
	public String getC_std_files() {
		return c_std_files;
	}
	public void setC_std_files(String c_std_files) {
		this.c_std_files = c_std_files;
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
	public String getC_checkstd_files() {
		return c_checkstd_files;
	}
	public void setC_checkstd_files(String c_checkstd_files) {
		this.c_checkstd_files = c_checkstd_files;
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
	public String getC_errstd_files() {
		return c_errstd_files;
	}
	public void setC_errstd_files(String c_errstd_files) {
		this.c_errstd_files = c_errstd_files;
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
	public String getC_review_std() {
		return c_review_std;
	}
	public void setC_review_std(String v_review_std) {
		this.c_review_std = v_review_std;
	}
	public String getC_actstep_index() {
		return c_actstep_index;
	}
	public void setC_actstep_index(String c_actstep_index) {
		this.c_actstep_index = c_actstep_index;
	}
	public String getC_tracefun_id() {
		return c_tracefun_id;
	}
	public void setC_tracefun_id(String c_tracefun_id) {
		this.c_tracefun_id = c_tracefun_id;
	}
	public String getC_step_prompt() {
		return c_step_prompt;
	}
	public void setC_step_prompt(String c_step_prompt) {
		this.c_step_prompt = c_step_prompt;
	}
	public long getC_timerule_id() {
		return c_timerule_id;
	}
	public void setC_timerule_id(long c_timerule_id) {
		this.c_timerule_id = c_timerule_id;
	}
	public String getC_timerule_name() {
		return c_timerule_name;
	}
	public void setC_timerule_name(String c_timerule_name) {
		this.c_timerule_name = c_timerule_name;
	}
	public String getC_ctrl_type() {
		return c_ctrl_type;
	}
	public void setC_ctrl_type(String c_ctrl_type) {
		this.c_ctrl_type = c_ctrl_type;
	}
	public String getC_month_set() {
		return c_month_set;
	}
	public void setC_month_set(String c_month_set) {
		this.c_month_set = c_month_set;
	}
	public String getC_day_set() {
		return c_day_set;
	}
	public void setC_day_set(String c_day_set) {
		this.c_day_set = c_day_set;
	}
	public String getC_week_set() {
		return c_week_set;
	}
	public void setC_week_set(String c_week_set) {
		this.c_week_set = c_week_set;
	}
	public String getC_date_relate() {
		return c_date_relate;
	}
	public void setC_date_relate(String c_date_relate) {
		this.c_date_relate = c_date_relate;
	}
	public String getC_time_relate() {
		return c_time_relate;
	}
	public void setC_time_relate(String c_time_relate) {
		this.c_time_relate = c_time_relate;
	}
	public String getC_urge_time() {
		return c_urge_time;
	}
	public void setC_urge_time(String c_urge_time) {
		this.c_urge_time = c_urge_time;
	}
	public String getC_exceed_time() {
		return c_exceed_time;
	}
	public void setC_exceed_time(String c_exceed_time) {
		this.c_exceed_time = c_exceed_time;
	}
	public String getC_rule_define() {
		return c_rule_define;
	}
	public void setC_rule_define(String c_rule_define) {
		this.c_rule_define = c_rule_define;
	}
	public String getC_std_flag_name() {
		return c_std_flag_name;
	}
	public void setC_std_flag_name(String c_std_flag_name) {
		this.c_std_flag_name = c_std_flag_name;
	}
	public String getC_check_flag_name() {
		return c_check_flag_name;
	}
	public void setC_check_flag_name(String c_check_flag_name) {
		this.c_check_flag_name = c_check_flag_name;
	}
	public String getC_errstd_flag_name() {
		return c_errstd_flag_name;
	}
	public void setC_errstd_flag_name(String c_errstd_flag_name) {
		this.c_errstd_flag_name = c_errstd_flag_name;
	}
	public String getC_theory_isworkday_name() {
		return c_theory_isworkday_name;
	}
	public void setC_theory_isworkday_name(String c_theory_isworkday_name) {
		this.c_theory_isworkday_name = c_theory_isworkday_name;
	}
	public String getC_fact_isworkday_name() {
		return c_fact_isworkday_name;
	}
	public void setC_fact_isworkday_name(String c_fact_isworkday_name) {
		this.c_fact_isworkday_name = c_fact_isworkday_name;
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
	public String getTablename()
    {
        return tablename;
    }
    public void setTablename(String tablename)
    {
        this.tablename = tablename;
    }
    public String getC_obj_fullname()
    {
        return c_obj_fullname;
    }
    public void setC_obj_fullname(String c_obj_fullname)
    {
        this.c_obj_fullname = c_obj_fullname;
    }
    public String getC_statu()
    {
        return c_statu;
    }
    public void setC_statu(String c_statu)
    {
        this.c_statu = c_statu;
    }
    public String getC_statu_name()
    {
        return c_statu_name;
    }
    public void setC_statu_name(String c_statu_name)
    {
        this.c_statu_name = c_statu_name;
    }
    public String getC_ctrl_type_name()
    {
        return c_ctrl_type_name;
    }
    public void setC_ctrl_type_name(String c_ctrl_type_name)
    {
        this.c_ctrl_type_name = c_ctrl_type_name;
    }
    public String getC_date_relate_name()
    {
        return c_date_relate_name;
    }
    public void setC_date_relate_name(String c_date_relate_name)
    {
        this.c_date_relate_name = c_date_relate_name;
    }
    public String getC_time_relate_name()
    {
        return c_time_relate_name;
    }
    public void setC_time_relate_name(String c_time_relate_name)
    {
        this.c_time_relate_name = c_time_relate_name;
    }
    public String getC_manage_section_name()
    {
        return c_manage_section_name;
    }
    public void setC_manage_section_name(String c_manage_section_name)
    {
        this.c_manage_section_name = c_manage_section_name;
    }
    public String getPositionid()
    {
        return positionid;
    }
    public void setPositionid(String positionid)
    {
        this.positionid = positionid;
    }
    public String getPosiname()
    {
        return posiname;
    }
    public void setPosiname(String posiname)
    {
        this.posiname = posiname;
    }
    public String getArea()
    {
        return area;
    }
    public void setArea(String area)
    {
        this.area = area;
    }
    public String getAreaname()
    {
        return areaname;
    }
    public void setAreaname(String areaname)
    {
        this.areaname = areaname;
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
	public String getC_objtype_id() {
		return c_objtype_id;
	}
	public void setC_objtype_id(String c_objtype_id) {
		this.c_objtype_id = c_objtype_id;
	}
	public long getC_obj_id() {
		return c_obj_id;
	}
	public void setC_obj_id(long c_obj_id) {
		this.c_obj_id = c_obj_id;
	}
	public long getC_actitem_id() {
		return c_actitem_id;
	}
	public void setC_actitem_id(long c_actitem_id) {
		this.c_actitem_id = c_actitem_id;
	}
	public String getC_tracefun_name() {
		return c_tracefun_name;
	}
	public void setC_tracefun_name(String c_tracefun_name) {
		this.c_tracefun_name = c_tracefun_name;
	}
}
