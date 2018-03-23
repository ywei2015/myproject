package com.talkweb.xwzcxt.vo;

import java.math.BigDecimal;
import java.util.Date;

public class TaskErrTraceVo {
	  //t_error_info表
	  private BigDecimal c_err_id ;        // NUMBER(20) not null,
	  private String c_err_name;          //VARCHAR2(100) not null,
	  private Integer c_err_kind;             // NUMBER(5) not null,
	  private Date c_occur_time;        //      DATE not null,
	  private BigDecimal c_task_id;		//  NUMBER(20),
	  private String c_actnode_id;      //VARCHAR2(50) default '0',
	  private Integer c_err_type;        //NUMBER(5) default 0,
	  private Integer c_err_level;       //NUMBER(5),
	  private String c_area;            //VARCHAR2(20),
	  private String c_areaname;
	  private Long c_obj_id;            //NUMBER(10),
	  private Long c_manage_section;    //NUMBER(10),
	  private Date c_write_time;      //DATE not null,
	  private Long  c_writer;           // NUMBER(10) not null,
	  private String c_writer_name;      // VARCHAR2(20) not null,
	  private Date c_upload_time;      //DATE not null,
	  private String c_isbyself;         //CHAR(1) default '0' not null,
	  private Long c_handle_userid;      //NUMBER(10),
	  private String c_handle_username;  //VARCHAR2(20),
	  private String c_isclose;          //CHAR(1) default '0' not null,
	  private Date c_close_time;       //DATE,
	  private String c_feedback_path;    //VARCHAR2(1000) not null,
	  private String c_isdelete;         //CHAR(1) default '0' not null,
	  private Date c_suggestend_time;  // DATE
	  private Integer type;
	  
	  //T_ERROR_FEEDBACK表
	  private BigDecimal c_feedback_id;        //NUMBER(20) not null,
	  //private BigDecimal c_err_id;             //NUMBER(20),
	  private String c_feedback_title;     //VARCHAR2(50),
	  //private long c_manage_section;     //NUMBER(10),
	  private Integer c_feedback_type ;     //NUMBER(5) default 0,
	  private String c_feedback_lotno;     //VARCHAR2(20),
	  private Long c_receiver_userid;    //NUMBER(10),
	  private Long c_feedbacker;         //NUMBER(10) not null,
	  private Date c_feedback_time;      //DATE not null,
	  private String c_isreceived ;        //CHAR(1) default '0' not null,
	  private Date c_receiver_time;      //DATE,
	  private Integer c_status ;            //NUMBER(5) default 0 not null,
	  private String c_manage_sectionname; //VARCHAR2(20),
	  private Integer c_deal_type;          //NUMBER(5),
	  private String c_receiver_username;  //VARCHAR2(20),
	  private String c_feedbacker_name;    //VARCHAR2(20),
	  private Date c_end_time;           //DATE,
	  private Integer c_factdeal_type;      //NUMBER(5),
	  private Date c_factdeal_time;      //DATE
	  
	  //T_ERROR_AFFIX表
	  private BigDecimal c_errdata_id;      //NUMBER(38),
	  //private BigDecimal c_err_id;          //NUMBER(38) not null,
	  private Integer c_record_type;     //NUMBER(2) default 0 not null,
	  private Date c_record_time;     //DATE,
	  private String c_record_lotno;    //VARCHAR2(50) not null,
	  private BigDecimal c_submit_userid;   //NUMBER(20),
	  private String c_submit_username; //VARCHAR2(20),
	  private Long c_tracefunid;      //NUMBER(10) not null,
	  private String c_isfile;          //CHAR(1) default '1',
	  private String c_value;           //VARCHAR2(2000) not null,
	  //private String c_isdelete ;       //CHAR(1) default '0' not null
	  
	  //t_file_info表
	  private String c_file_id;       // VARCHAR2(48) not null,
	  private Integer c_file_kind ;    // NUMBER(5) not null,
	  private String c_file_title ;  //  VARCHAR2(200) not null,
	  private Integer c_file_type ;    // NUMBER(5) not null,
	  private String c_file_name;     // VARCHAR2(100) not null,
	  private String c_file_extension;// NVARCHAR2(50) not null,
	  private String c_file_path;      //VARCHAR2(200) not null,
	  private BigDecimal c_file_size;     // NUMBER(20),
	  // private String c_upload_time;    //VARCHAR2(19),
	  private String  c_upload_userid; // VARCHAR2(20),
	  private String c_remark ;       // VARCHAR2(2000)
	  
	  
	  //P_II_ERROR_FEEDBACK存储过程
/*	  private String c_err_name;        //in varchar2, ---异常主题
      private long c_err_kind ;       //in number, ---异常各类（1.工作执行异常;2.随机发起异常）
      private long c_task_id;         //in NUMBER, ---如果基于任务的异常反馈，需要填写任务ID
      private String c_occur_time;      //in date, ---异常发生时间（必填，且要求<当前时间）
      private long c_manage_section;  //in number, ---异常发生所属版块
      private String c_write_time;      //in date, ---异常记录时间（客户端时间）
      private long c_writer ;         //in number, ---异常发生记录人（UserID）
      private String c_suggestend_time; //in date, ---建议处置完成时间
*/
      private Long c_to_userid ;     //in number, --接收人UserID
      private String c_cc_userid_list; //in varchar2, --抄送人UserID List
      private String c_tracefunids;    //in varchar2, --动作收集方式ID集
      private String c_results ;       //in varchar2, -----收集结果集
      private String c_errdes ;        //in varchar2, ----异常文本描述
      private String dealc_tracefunids ;   //in varchar2, --处置信息-动作收集方式ID集
      private String dealc_results  ;      //in varchar2, -----处置信息-收集结果集
      private String dealc_errdes ;       // in varchar2, ----处置信息-文本描述
      private String c_arer ;      //in varchar2, ---异常发生所在区域位置
/*    private String c_actnode_id; //in varchar2, ---岗位活动末节点ID(预留)
      private long c_err_type ;  //in number, ---异常的类别(预留)
      private long c_err_level;  //in number, ---异常级别(预留)
      private long c_obj_id ;    //in number, ---异常发生的作业对象ID(预留)
      private long c_isbyself ;      //in number, ---是否发现人自己处置 (0.未处置; 1.自己已处置,C_ISCLOSE=1)
      private String c_feedback_lotno; //in varchar2, ---提交批号(时间戳) 例：to_char(sysdate,'yyyymmddHH24MISS')||to_char(current_timestamp,'ff3')
      private long c_err_id; //out number --新插入的异常ID号
      
      
*/      
      
    private Long  userid;
    private Integer   infotype;
    private String areaname;
    
    private Integer issuccess;
    private BigDecimal oc_feedback_id;
    
    private  String c_handle_des;
    private  String c_commit_lotno;
    
    
    private Date c_chk_plantime;
    private Date c_chk_time;
    private Date c_chk_starttime;
    private Date c_evaluate_time;
    private Date c_evaluate_plantime;
    private Date c_evaluate_starttime;
    private Integer c_chk_status;
    private String  c_chk_result;
    private String  c_chk_status_name;
    private Integer c_evaluate_status;
    private String  c_evaluate_result;
    private String  c_evaluate_status_name;
    private Long c_chk_userid;
    private Long c_evaluate_userid;
    private Integer ordertype;
    private String c_chk_value;
    private String c_evaluate_value;
    private String c_isclose_name;
    private String c_evaluate_value_name;
    private String c_chk_value_name;
    private String c_err_kind_name;
    private Integer datatype;
    private Integer c_ex_iemisevent;
    
    
    public String getC_ex_iemiseventStr() {
		return c_ex_iemisevent==null?null:c_ex_iemisevent.toString();
	}
    
	public Integer getC_ex_iemisevent() {
		return c_ex_iemisevent;
	}
	public void setC_ex_iemisevent(Integer c_ex_iemisevent) {
		this.c_ex_iemisevent = c_ex_iemisevent;
	}
	public Integer getDatatype() {
		return datatype;
	}
	public void setDatatype(Integer datatype) {
		this.datatype = datatype;
	}
	public String getC_err_kind_name() {
		return c_err_kind_name;
	}
	public void setC_err_kind_name(String c_err_kind_name) {
		this.c_err_kind_name = c_err_kind_name;
	}
	public String getC_evaluate_value_name() {
		return c_evaluate_value_name;
	}
	public void setC_evaluate_value_name(String c_evaluate_value_name) {
		this.c_evaluate_value_name = c_evaluate_value_name;
	}
	public String getC_chk_value_name() {
		return c_chk_value_name;
	}
	public void setC_chk_value_name(String c_chk_value_name) {
		this.c_chk_value_name = c_chk_value_name;
	}
	public String getC_isclose_name() {
		return c_isclose_name;
	}
	public void setC_isclose_name(String c_isclose_name) {
		this.c_isclose_name = c_isclose_name;
	}
	public String getC_chk_value() {
		return c_chk_value;
	}
	public void setC_chk_value(String c_chk_value) {
		this.c_chk_value = c_chk_value;
	}
	public String getC_evaluate_value() {
		return c_evaluate_value;
	}
	public void setC_evaluate_value(String c_evaluate_value) {
		this.c_evaluate_value = c_evaluate_value;
	}
	public String getC_chk_status_name() {
		return c_chk_status_name;
	}
	public void setC_chk_status_name(String c_chk_status_name) {
		this.c_chk_status_name = c_chk_status_name;
	}
	public String getC_evaluate_status_name() {
		return c_evaluate_status_name;
	}
	public void setC_evaluate_status_name(String c_evaluate_status_name) {
		this.c_evaluate_status_name = c_evaluate_status_name;
	}
	public Integer getOrdertype() {
		return ordertype;
	}
	public void setOrdertype(Integer ordertype) {
		this.ordertype = ordertype;
	}
	public Date getC_chk_plantime() {
		return c_chk_plantime;
	}
	public void setC_chk_plantime(Date c_chk_plantime) {
		this.c_chk_plantime = c_chk_plantime;
	}
	public Date getC_chk_time() {
		return c_chk_time;
	}
	public void setC_chk_time(Date c_chk_time) {
		this.c_chk_time = c_chk_time;
	}
	public Date getC_chk_starttime() {
		return c_chk_starttime;
	}
	public void setC_chk_starttime(Date c_chk_starttime) {
		this.c_chk_starttime = c_chk_starttime;
	}
	public Date getC_evaluate_time() {
		return c_evaluate_time;
	}
	public void setC_evaluate_time(Date c_evaluate_time) {
		this.c_evaluate_time = c_evaluate_time;
	}
	public Date getC_evaluate_plantime() {
		return c_evaluate_plantime;
	}
	public void setC_evaluate_plantime(Date c_evaluate_plantime) {
		this.c_evaluate_plantime = c_evaluate_plantime;
	}
	public Date getC_evaluate_starttime() {
		return c_evaluate_starttime;
	}
	public void setC_evaluate_starttime(Date c_evaluate_starttime) {
		this.c_evaluate_starttime = c_evaluate_starttime;
	}
	public Integer getC_chk_status() {
		return c_chk_status;
	}
	public void setC_chk_status(Integer c_chk_status) {
		this.c_chk_status = c_chk_status;
	}
	public String getC_chk_result() {
		return c_chk_result;
	}
	public void setC_chk_result(String c_chk_result) {
		this.c_chk_result = c_chk_result;
	}
	public Integer getC_evaluate_status() {
		return c_evaluate_status;
	}
	public void setC_evaluate_status(Integer c_evaluate_status) {
		this.c_evaluate_status = c_evaluate_status;
	}
	public String getC_evaluate_result() {
		return c_evaluate_result;
	}
	public void setC_evaluate_result(String c_evaluate_result) {
		this.c_evaluate_result = c_evaluate_result;
	}
	public Long getC_chk_userid() {
		return c_chk_userid;
	}
	public void setC_chk_userid(Long c_chk_userid) {
		this.c_chk_userid = c_chk_userid;
	}
	public Long getC_evaluate_userid() {
		return c_evaluate_userid;
	}
	public void setC_evaluate_userid(Long c_evaluate_userid) {
		this.c_evaluate_userid = c_evaluate_userid;
	}
	public String getC_handle_des() {
		return c_handle_des;
	}
	public void setC_handle_des(String c_handle_des) {
		this.c_handle_des = c_handle_des;
	}
	public String getC_commit_lotno() {
		return c_commit_lotno;
	}
	public void setC_commit_lotno(String c_commit_lotno) {
		this.c_commit_lotno = c_commit_lotno;
	}
	public Integer getIssuccess() {
		return issuccess;
	}
	public void setIssuccess(Integer issuccess) {
		this.issuccess = issuccess;
	}
	public BigDecimal getOc_feedback_id() {
		return oc_feedback_id;
	}
	
	public String getOc_feedback_idStr(){
		return oc_feedback_id==null?null:oc_feedback_id.toString();
	}
	
	public void setOc_feedback_id(BigDecimal oc_feedback_id) {
		this.oc_feedback_id = oc_feedback_id;
	}
	public String getAreaname() {
		return areaname;
	}
	public void setAreaname(String areaname) {
		this.areaname = areaname;
	}
	public Integer getInfotype() {
		return infotype;
	}
	public void setInfotype(Integer infotype) {
		this.infotype = infotype;
	}
	public BigDecimal getC_err_id() {
		return c_err_id;
	}
	public String getC_err_idStr(){
		return c_err_id==null?null:c_err_id.toString();
	}
	public void setC_err_id(BigDecimal c_err_id) {
		this.c_err_id = c_err_id;
	}
	public String getC_err_name() {
		return c_err_name;
	}
	public void setC_err_name(String c_err_name) {
		this.c_err_name = c_err_name;
	}
	public Integer getC_err_kind() {
		return c_err_kind;
	}
	public void setC_err_kind(Integer c_err_kind) {
		this.c_err_kind = c_err_kind;
	}
	public Date getC_occur_time() {
		return c_occur_time;
	}
	public void setC_occur_time(Date c_occur_time) {
		this.c_occur_time = c_occur_time;
	}
	public BigDecimal getC_task_id() {
		return c_task_id;
	}
	public void setC_task_id(BigDecimal c_task_id) {
		this.c_task_id = c_task_id;
	}
	public String getC_actnode_id() {
		return c_actnode_id;
	}
	public void setC_actnode_id(String c_actnode_id) {
		this.c_actnode_id = c_actnode_id;
	}
	public Integer getC_err_type() {
		return c_err_type;
	}
	public void setC_err_type(Integer c_err_type) {
		this.c_err_type = c_err_type;
	}
	public Integer getC_err_level() {
		return c_err_level;
	}
	public void setC_err_level(Integer c_err_level) {
		this.c_err_level = c_err_level;
	}
	public String getC_area() {
		return c_area;
	}
	public void setC_area(String c_area) {
		this.c_area = c_area;
	}
	public String getC_areaname() {
		return c_areaname;
	}
	public void setC_areaname(String c_areaname) {
		this.c_areaname = c_areaname;
	}
	public Long getC_obj_id() {
		return c_obj_id;
	}
	public void setC_obj_id(Long c_obj_id) {
		this.c_obj_id = c_obj_id;
	}
	public Long getC_manage_section() {
		return c_manage_section;
	}
	public void setC_manage_section(Long c_manage_section) {
		this.c_manage_section = c_manage_section;
	}
	public Date getC_write_time() {
		return c_write_time;
	}
	public void setC_write_time(Date c_write_time) {
		this.c_write_time = c_write_time;
	}
	public Long getC_writer() {
		return c_writer;
	}
	public void setC_writer(Long c_writer) {
		this.c_writer = c_writer;
	}
	public String getC_writer_name() {
		return c_writer_name;
	}
	public void setC_writer_name(String c_writer_name) {
		this.c_writer_name = c_writer_name;
	}
	public Date getC_upload_time() {
		return c_upload_time;
	}
	public void setC_upload_time(Date c_upload_time) {
		this.c_upload_time = c_upload_time;
	}
	public String getC_isbyself() {
		return c_isbyself;
	}
	public void setC_isbyself(String c_isbyself) {
		this.c_isbyself = c_isbyself;
	}
	public Long getC_handle_userid() {
		return c_handle_userid;
	}
	public void setC_handle_userid(Long c_handle_userid) {
		this.c_handle_userid = c_handle_userid;
	}
	public String getC_handle_username() {
		return c_handle_username;
	}
	public void setC_handle_username(String c_handle_username) {
		this.c_handle_username = c_handle_username;
	}
	public String getC_isclose() {
		return c_isclose;
	}
	public void setC_isclose(String c_isclose) {
		this.c_isclose = c_isclose;
	}
	public Date getC_close_time() {
		return c_close_time;
	}
	public void setC_close_time(Date c_close_time) {
		this.c_close_time = c_close_time;
	}
	public String getC_feedback_path() {
		return c_feedback_path;
	}
	public void setC_feedback_path(String c_feedback_path) {
		this.c_feedback_path = c_feedback_path;
	}
	public String getC_isdelete() {
		return c_isdelete;
	}
	public void setC_isdelete(String c_isdelete) {
		this.c_isdelete = c_isdelete;
	}
	public Date getC_suggestend_time() {
		return c_suggestend_time;
	}
	public void setC_suggestend_time(Date c_suggestend_time) {
		this.c_suggestend_time = c_suggestend_time;
	}
	public BigDecimal getC_feedback_id() {
		return c_feedback_id;
	}
	public void setC_feedback_id(BigDecimal c_feedback_id) {
		this.c_feedback_id = c_feedback_id;
	}
	public String getC_feedback_title() {
		return c_feedback_title;
	}
	public void setC_feedback_title(String c_feedback_title) {
		this.c_feedback_title = c_feedback_title;
	}
	public Integer getC_feedback_type() {
		return c_feedback_type;
	}
	public void setC_feedback_type(Integer c_feedback_type) {
		this.c_feedback_type = c_feedback_type;
	}
	
	public String getC_feedback_typeStr() {
		return c_feedback_type==null?null:c_feedback_type.toString();
	}

	public String getC_feedback_lotno() {
		return c_feedback_lotno;
	}
	public void setC_feedback_lotno(String c_feedback_lotno) {
		this.c_feedback_lotno = c_feedback_lotno;
	}
	public Long getC_receiver_userid() {
		return c_receiver_userid;
	}
	public void setC_receiver_userid(Long c_receiver_userid) {
		this.c_receiver_userid = c_receiver_userid;
	}
	public Long getC_feedbacker() {
		return c_feedbacker;
	}
	public void setC_feedbacker(Long c_feedbacker) {
		this.c_feedbacker = c_feedbacker;
	}
	public Date getC_feedback_time() {
		return c_feedback_time;
	}
	public void setC_feedback_time(Date c_feedback_time) {
		this.c_feedback_time = c_feedback_time;
	}
	public String getC_isreceived() {
		return c_isreceived;
	}
	public void setC_isreceived(String c_isreceived) {
		this.c_isreceived = c_isreceived;
	}
	public Date getC_receiver_time() {
		return c_receiver_time;
	}
	public void setC_receiver_time(Date c_receiver_time) {
		this.c_receiver_time = c_receiver_time;
	}
	public Integer getC_status() {
		return c_status;
	}
	public void setC_status(Integer c_status) {
		this.c_status = c_status;
	}
	public String getC_manage_sectionname() {
		return c_manage_sectionname;
	}
	public void setC_manage_sectionname(String c_manage_sectionname) {
		this.c_manage_sectionname = c_manage_sectionname;
	}
	public Integer getC_deal_type() {
		return c_deal_type;
	}
	public String getC_deal_typeStr(){
		return c_deal_type==null?null:c_deal_type.toString();
	}
	
	public void setC_deal_type(Integer c_deal_type) {
		this.c_deal_type = c_deal_type;
	}
	public String getC_receiver_username() {
		return c_receiver_username;
	}
	public void setC_receiver_username(String c_receiver_username) {
		this.c_receiver_username = c_receiver_username;
	}
	public String getC_feedbacker_name() {
		return c_feedbacker_name;
	}
	public void setC_feedbacker_name(String c_feedbacker_name) {
		this.c_feedbacker_name = c_feedbacker_name;
	}
	public Date getC_end_time() {
		return c_end_time;
	}
	public void setC_end_time(Date c_end_time) {
		this.c_end_time = c_end_time;
	}
	public Integer getC_factdeal_type() {
		return c_factdeal_type;
	}
	public void setC_factdeal_type(Integer c_factdeal_type) {
		this.c_factdeal_type = c_factdeal_type;
	}
	public Date getC_factdeal_time() {
		return c_factdeal_time;
	}
	public void setC_factdeal_time(Date c_factdeal_time) {
		this.c_factdeal_time = c_factdeal_time;
	}
	public BigDecimal getC_errdata_id() {
		return c_errdata_id;
	}
	public void setC_errdata_id(BigDecimal c_errdata_id) {
		this.c_errdata_id = c_errdata_id;
	}
	public Integer getC_record_type() {
		return c_record_type;
	}
	public void setC_record_type(Integer c_record_type) {
		this.c_record_type = c_record_type;
	}
	public Date getC_record_time() {
		return c_record_time;
	}
	public void setC_record_time(Date c_record_time) {
		this.c_record_time = c_record_time;
	}
	public String getC_record_lotno() {
		return c_record_lotno;
	}
	public void setC_record_lotno(String c_record_lotno) {
		this.c_record_lotno = c_record_lotno;
	}
	public BigDecimal getC_submit_userid() {
		return c_submit_userid;
	}
	public void setC_submit_userid(BigDecimal c_submit_userid) {
		this.c_submit_userid = c_submit_userid;
	}
	public String getC_submit_username() {
		return c_submit_username;
	}
	public void setC_submit_username(String c_submit_username) {
		this.c_submit_username = c_submit_username;
	}
	public Long getC_tracefunid() {
		return c_tracefunid;
	}
	public void setC_tracefunid(Long c_tracefunid) {
		this.c_tracefunid = c_tracefunid;
	}
	public String getC_isfile() {
		return c_isfile;
	}
	public void setC_isfile(String c_isfile) {
		this.c_isfile = c_isfile;
	}
	public String getC_value() {
		return c_value;
	}
	public void setC_value(String c_value) {
		this.c_value = c_value;
	}
	public String getC_file_id() {
		return c_file_id;
	}
	public void setC_file_id(String c_file_id) {
		this.c_file_id = c_file_id;
	}
	public Integer getC_file_kind() {
		return c_file_kind;
	}
	public void setC_file_kind(Integer c_file_kind) {
		this.c_file_kind = c_file_kind;
	}
	public String getC_file_title() {
		return c_file_title;
	}
	public void setC_file_title(String c_file_title) {
		this.c_file_title = c_file_title;
	}
	public Integer getC_file_type() {
		return c_file_type;
	}
	public void setC_file_type(Integer c_file_type) {
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
		this.c_file_path = c_file_path;
	}
	public BigDecimal getC_file_size() {
		return c_file_size;
	}
	public void setC_file_size(BigDecimal c_file_size) {
		this.c_file_size = c_file_size;
	}
	public String getC_upload_userid() {
		return c_upload_userid;
	}
	public void setC_upload_userid(String c_upload_userid) {
		this.c_upload_userid = c_upload_userid;
	}
	public String getC_remark() {
		return c_remark;
	}
	public void setC_remark(String c_remark) {
		this.c_remark = c_remark;
	}
	public Long getC_to_userid() {
		return c_to_userid;
	}
	public void setC_to_userid(Long c_to_userid) {
		this.c_to_userid = c_to_userid;
	}
	public String getC_cc_userid_list() {
		return c_cc_userid_list;
	}
	public void setC_cc_userid_list(String c_cc_userid_list) {
		this.c_cc_userid_list = c_cc_userid_list;
	}
	public String getC_tracefunids() {
		return c_tracefunids;
	}
	public void setC_tracefunids(String c_tracefunids) {
		this.c_tracefunids = c_tracefunids;
	}
	public String getC_results() {
		return c_results;
	}
	public void setC_results(String c_results) {
		this.c_results = c_results;
	}
	public String getC_errdes() {
		return c_errdes;
	}
	public void setC_errdes(String c_errdes) {
		this.c_errdes = c_errdes;
	}
	public String getDealc_tracefunids() {
		return dealc_tracefunids;
	}
	public void setDealc_tracefunids(String dealc_tracefunids) {
		this.dealc_tracefunids = dealc_tracefunids;
	}
	public String getDealc_results() {
		return dealc_results;
	}
	public void setDealc_results(String dealc_results) {
		this.dealc_results = dealc_results;
	}
	public String getDealc_errdes() {
		return dealc_errdes;
	}
	public void setDealc_errdes(String dealc_errdes) {
		this.dealc_errdes = dealc_errdes;
	}
	public String getC_arer() {
		return c_arer;
	}
	public void setC_arer(String c_arer) {
		this.c_arer = c_arer;
	}
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	public String getC_task_idStr() {
		return c_task_id==null?null:c_task_id.toString();
	}
	public String getC_feedback_idStr() {
		return c_feedback_id==null?null:c_feedback_id.toString();
	}
	public String getC_errdata_idStr() {
		return c_errdata_id==null?null:c_errdata_id.toString();
	}
	public String getC_submit_useridStr() {
		return c_submit_userid==null?null:c_submit_userid.toString();
	}
	public String getC_file_sizeStr() {
		return c_file_size==null?null:c_file_size.toString();
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	  
	  
}
