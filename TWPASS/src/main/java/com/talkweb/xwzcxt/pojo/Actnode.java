package com.talkweb.xwzcxt.pojo;

import com.talkweb.twdpe.core.data.BasePOJO;

public class Actnode extends BasePOJO {
	//流程表
	private String c_action_id = "";//节点ID
	private String c_action_pid = "";//节点父节点
	private String c_action_code = "";//编码
	private String c_action_type = "";//流程节点类型（保留），需求规定只有最底层节点才允许添加岗位活动，此类型区分是否为最底层节点
	private String c_section_id = "";//所属版块（1安全、2质量、3成本、4效率、5团队、6环境）
	private String c_action_sname = "";//简称
	private String c_action_fname = "";//全称
	private String c_remark = "";//描述
	private String c_flag = "";//是否使用，默认为1 （保留）应对以后是否取消此项流程节点
	//岗位活动表（属性）
	private String c_actnode_id = "";//岗位活动ID
	private String c_actnode_code = "";//编码
	private String c_actnode_name = "";//名称
	private String c_pdca = "";//PDCA
	private String c_timeplan_id = "";//时间规则ID
	private String c_iskeyctrl = "";//0-非关键管控，1-关键管控
	private String c_issequence = "";//0-可不按顺序执行，1-需要按顺序执行
	private String c_israndom = "";//0-非随机任务，1-随机任务
	private String c_isscan = "";//0-不扫码，1-扫码
	private String c_frequency = "";//执行频率，在此为模版内容展示不是时间规则内容（每天，每周，每月，每季度等）
	private String c_area_id = "";//作业区域ID（ERP）
	private String c_area_name = "";//作业区域（来自模版或ERP）
	private String c_position_id = "";//作业岗位ID（ERP）
	private String c_position_name = "";//作业岗位（来自模版或ERP）
	private String c_std_exec = "";//作业标准
	private String c_starttime_exec = "";//开始执行时间
	private String c_starttime_exec_name = "";//开始执行时间
	private String c_endtime_exec = "";//结束执行时间
	private String c_endtime_exec_name = "";//结束执行时间
	private String c_std_check = "";//检查标准
	private String c_position_id_check = "";//检查岗位ID（ERP）
	private String c_position_name_check = "";//检查岗位（来自模版或ERP）
	private String c_timelimit_check = "";//检查有效时间
	private String c_std_review = "";//评价标准
	private String c_position_id_review = "";//评价岗位ID（ERP）
	private String c_position_name_review = "";//评价岗位（来自模版或ERP）
	private String c_timelimit_review = "";//评价有效时间
	private String c_position_id_feedback1 = "";//默认反馈岗位ID（ERP）
	private String c_position_name_feedback1 = "";//默认反馈岗位（来自模版或ERP）
	private String c_position_id_feedback2 = "";//其他默认反馈岗位ID（ERP）
	private String c_position_name_feedback2 = "";//其他默认反馈岗位（来自模版或ERP）
	private String c_position_id_err1 = "";//异常反馈岗位ID（ERP）
	private String c_position_name_err1 = "";//异常反馈岗位（来自模版或ERP）
	private String c_position_id_err2 = "";//其他异常反馈岗位ID（ERP）
	private String c_position_name_err2 = "";//其他异常反馈岗位（来自模版或ERP）
	private String c_creator = "";//创建人（ERP）
	private String c_createtime = "";//创建时间
	private String c_modifier = "";//修改人（ERP）
	private String c_modifytime = "";//修改时间
	private String c_version = "";//版本编号
	private String c_istimerule = "";//是否已经进行时间规则解析
	private String c_action_name = "";//流程节点名称
	private String c_err_exec = "";//异常处理标准
	private String c_records_exec = "";//作业记录（规范性记录）
	private String c_err_check = "";//异常情况验证标准
	private String c_records_check = "";//验证作业记录（规范性记录）
	private String c_department = "";//部门ID
	private String c_file_id = "";//来自文件
	private String c_actnodetype = "";//岗位活动类型1：作业类，2：管理类
	private String c_manageattr = "";//管理属性（管理类扩展）
	private String c_managestd = "";//管理（技术）标准（管理类扩展）
	private String c_examstd = "";//考核标准（管理类扩展）
	private String c_public_id = "";//通用标准节点
	private String c_ispublic = "";//是否是通用标准
	private String c_media_file_id = "";//多媒体文件ID
	private String c_media_file_type = "";//多媒体文件type
	private String c_media_file_path = "";//多媒体文件path
	//扩展字段
	private String filePath;//excel文件本地路径
	//岗位活动中的活动项
	private String c_actitem_id = "";//活动项ID
	private String c_actitem_index = "";//活动项索引
	private String c_actitem_code = "";//编码
	private String c_actitem_name = "";//名称
	private String c_actitem_std = "";//活动项标准
	private String c_explain = "";//活动项描述
	private String c_getdata_pretext = "";//数据采集名称
	private String c_getdatatype = "";//执行结果上传要求/完工上传要求
	private String c_getdata_unit = "";//数据采集单位
	private String c_getdata_text = "";//执行结果后置文本/完工后置文本
	private String c_actitem_std_check = "";//活动项验证标准
	private String c_checkdata_pretext = "";//验证结果前置文本
	private String c_checkdatatype = "";//验证结果上传要求
	private String c_checkdata_unit = "";//验证结果单位
	private String c_checkdata_text = "";//验证结果后置文本
	private String c_groupindex = "";//分组序号（一步骤对多动作）
	private String c_actitem_media_file_id = "";//活动项多媒体文件ID
	private String c_actitem_media_file_type = "";//活动项多媒体文件type
	private String c_actitem_media_file_path = "";//活动项多媒体文件path

	public String getC_action_id() {
		return c_action_id;
	}
	public void setC_action_id(String c_action_id) {
		this.c_action_id = c_action_id;
	}
	public String getC_action_pid() {
		return c_action_pid;
	}
	public void setC_action_pid(String c_action_pid) {
		this.c_action_pid = c_action_pid;
	}
	public String getC_action_code() {
		return c_action_code;
	}
	public void setC_action_code(String c_action_code) {
		this.c_action_code = c_action_code;
	}
	public String getC_action_type() {
		return c_action_type;
	}
	public void setC_action_type(String c_action_type) {
		this.c_action_type = c_action_type;
	}
	public String getC_section_id() {
		return c_section_id;
	}
	public void setC_section_id(String c_section_id) {
		this.c_section_id = c_section_id;
	}
	public String getC_action_sname() {
		return c_action_sname;
	}
	public void setC_action_sname(String c_action_sname) {
		this.c_action_sname = c_action_sname;
	}
	public String getC_action_fname() {
		return c_action_fname;
	}
	public void setC_action_fname(String c_action_fname) {
		this.c_action_fname = c_action_fname;
	}
	public String getC_remark() {
		return c_remark;
	}
	public void setC_remark(String c_remark) {
		this.c_remark = c_remark;
	}
	public String getC_flag() {
		return c_flag;
	}
	public void setC_flag(String c_flag) {
		this.c_flag = c_flag;
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
	public String getC_pdca() {
		return c_pdca;
	}
	public void setC_pdca(String c_pdca) {
		this.c_pdca = c_pdca;
	}
	public String getC_timeplan_id() {
		return c_timeplan_id;
	}
	public void setC_timeplan_id(String c_timeplan_id) {
		this.c_timeplan_id = c_timeplan_id;
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
	public String getC_israndom() {
		return c_israndom;
	}
	public void setC_israndom(String c_israndom) {
		this.c_israndom = c_israndom;
	}
	public String getC_isscan() {
		return c_isscan;
	}
	public void setC_isscan(String c_isscan) {
		this.c_isscan = c_isscan;
	}
	public String getC_frequency() {
		return c_frequency;
	}
	public void setC_frequency(String c_frequency) {
		this.c_frequency = c_frequency;
	}
	public String getC_area_id() {
		return c_area_id;
	}
	public void setC_area_id(String c_area_id) {
		this.c_area_id = c_area_id;
	}
	public String getC_area_name() {
		return c_area_name;
	}
	public void setC_area_name(String c_area_name) {
		this.c_area_name = c_area_name;
	}
	public String getC_position_id() {
		return c_position_id;
	}
	public void setC_position_id(String c_position_id) {
		this.c_position_id = c_position_id;
	}
	public String getC_position_name() {
		return c_position_name;
	}
	public void setC_position_name(String c_position_name) {
		this.c_position_name = c_position_name;
	}
	public String getC_std_exec() {
		return c_std_exec;
	}
	public void setC_std_exec(String c_std_exec) {
		this.c_std_exec = c_std_exec;
	}
	public String getC_starttime_exec() {
		return c_starttime_exec;
	}
	public void setC_starttime_exec(String c_starttime_exec) {
		this.c_starttime_exec = c_starttime_exec;
	}
	public String getC_endtime_exec() {
		return c_endtime_exec;
	}
	public void setC_endtime_exec(String c_endtime_exec) {
		this.c_endtime_exec = c_endtime_exec;
	}
	public String getC_std_check() {
		return c_std_check;
	}
	public void setC_std_check(String c_std_check) {
		this.c_std_check = c_std_check;
	}
	public String getC_position_id_check() {
		return c_position_id_check;
	}
	public void setC_position_id_check(String c_position_id_check) {
		this.c_position_id_check = c_position_id_check;
	}
	public String getC_position_name_check() {
		return c_position_name_check;
	}
	public void setC_position_name_check(String c_position_name_check) {
		this.c_position_name_check = c_position_name_check;
	}
	public String getC_timelimit_check() {
		return c_timelimit_check;
	}
	public void setC_timelimit_check(String c_timelimit_check) {
		this.c_timelimit_check = c_timelimit_check;
	}
	public String getC_std_review() {
		return c_std_review;
	}
	public void setC_std_review(String c_std_review) {
		this.c_std_review = c_std_review;
	}
	public String getC_position_id_review() {
		return c_position_id_review;
	}
	public void setC_position_id_review(String c_position_id_review) {
		this.c_position_id_review = c_position_id_review;
	}
	public String getC_position_name_review() {
		return c_position_name_review;
	}
	public void setC_position_name_review(String c_position_name_review) {
		this.c_position_name_review = c_position_name_review;
	}
	public String getC_timelimit_review() {
		return c_timelimit_review;
	}
	public void setC_timelimit_review(String c_timelimit_review) {
		this.c_timelimit_review = c_timelimit_review;
	}
	public String getC_position_id_feedback1() {
		return c_position_id_feedback1;
	}
	public void setC_position_id_feedback1(String c_position_id_feedback1) {
		this.c_position_id_feedback1 = c_position_id_feedback1;
	}
	public String getC_position_name_feedback1() {
		return c_position_name_feedback1;
	}
	public void setC_position_name_feedback1(String c_position_name_feedback1) {
		this.c_position_name_feedback1 = c_position_name_feedback1;
	}
	public String getC_position_id_feedback2() {
		return c_position_id_feedback2;
	}
	public void setC_position_id_feedback2(String c_position_id_feedback2) {
		this.c_position_id_feedback2 = c_position_id_feedback2;
	}
	public String getC_position_name_feedback2() {
		return c_position_name_feedback2;
	}
	public void setC_position_name_feedback2(String c_position_name_feedback2) {
		this.c_position_name_feedback2 = c_position_name_feedback2;
	}
	public String getC_position_id_err1() {
		return c_position_id_err1;
	}
	public void setC_position_id_err1(String c_position_id_err1) {
		this.c_position_id_err1 = c_position_id_err1;
	}
	public String getC_position_name_err1() {
		return c_position_name_err1;
	}
	public void setC_position_name_err1(String c_position_name_err1) {
		this.c_position_name_err1 = c_position_name_err1;
	}
	public String getC_position_id_err2() {
		return c_position_id_err2;
	}
	public void setC_position_id_err2(String c_position_id_err2) {
		this.c_position_id_err2 = c_position_id_err2;
	}
	public String getC_position_name_err2() {
		return c_position_name_err2;
	}
	public void setC_position_name_err2(String c_position_name_err2) {
		this.c_position_name_err2 = c_position_name_err2;
	}
	public String getC_creator() {
		return c_creator;
	}
	public void setC_creator(String c_creator) {
		this.c_creator = c_creator;
	}
	public String getC_createtime() {
		return c_createtime;
	}
	public void setC_createtime(String c_createtime) {
		this.c_createtime = c_createtime;
	}
	public String getC_modifier() {
		return c_modifier;
	}
	public void setC_modifier(String c_modifier) {
		this.c_modifier = c_modifier;
	}
	public String getC_modifytime() {
		return c_modifytime;
	}
	public void setC_modifytime(String c_modifytime) {
		this.c_modifytime = c_modifytime;
	}
	public String getC_version() {
		return c_version;
	}
	public void setC_version(String c_version) {
		this.c_version = c_version;
	}
	public String getC_istimerule() {
		return c_istimerule;
	}
	public void setC_istimerule(String c_istimerule) {
		this.c_istimerule = c_istimerule;
	}
	public String getC_action_name() {
		return c_action_name;
	}
	public void setC_action_name(String c_action_name) {
		this.c_action_name = c_action_name;
	}
	public String getC_actitem_id() {
		return c_actitem_id;
	}
	public void setC_actitem_id(String c_actitem_id) {
		this.c_actitem_id = c_actitem_id;
	}
	public String getC_actitem_index() {
		return c_actitem_index;
	}
	public void setC_actitem_index(String c_actitem_index) {
		this.c_actitem_index = c_actitem_index;
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
	public String getC_actitem_std() {
		return c_actitem_std;
	}
	public void setC_actitem_std(String c_actitem_std) {
		this.c_actitem_std = c_actitem_std;
	}
	public String getC_explain() {
		return c_explain;
	}
	public void setC_explain(String c_explain) {
		this.c_explain = c_explain;
	}
	public String getC_getdatatype() {
		return c_getdatatype;
	}
	public void setC_getdatatype(String c_getdatatype) {
		this.c_getdatatype = c_getdatatype;
	}
	public String getC_err_exec() {
		return c_err_exec;
	}
	public void setC_err_exec(String c_err_exec) {
		this.c_err_exec = c_err_exec;
	}
	public String getC_records_exec() {
		return c_records_exec;
	}
	public void setC_records_exec(String c_records_exec) {
		this.c_records_exec = c_records_exec;
	}
	public String getC_err_check() {
		return c_err_check;
	}
	public void setC_err_check(String c_err_check) {
		this.c_err_check = c_err_check;
	}
	public String getC_records_check() {
		return c_records_check;
	}
	public void setC_records_check(String c_records_check) {
		this.c_records_check = c_records_check;
	}
	public String getC_department() {
		return c_department;
	}
	public void setC_department(String c_department) {
		this.c_department = c_department;
	}
	public String getC_file_id() {
		return c_file_id;
	}
	public void setC_file_id(String c_file_id) {
		this.c_file_id = c_file_id;
	}
	public String getC_actitem_std_check() {
		return c_actitem_std_check;
	}
	public void setC_actitem_std_check(String c_actitem_std_check) {
		this.c_actitem_std_check = c_actitem_std_check;
	}
	public String getC_checkdatatype() {
		return c_checkdatatype;
	}
	public void setC_checkdatatype(String c_checkdatatype) {
		this.c_checkdatatype = c_checkdatatype;
	}
	public String getC_actnodetype() {
		return c_actnodetype;
	}
	public void setC_actnodetype(String c_actnodetype) {
		this.c_actnodetype = c_actnodetype;
	}
	public String getC_manageattr() {
		return c_manageattr;
	}
	public void setC_manageattr(String c_manageattr) {
		this.c_manageattr = c_manageattr;
	}
	public String getC_managestd() {
		return c_managestd;
	}
	public void setC_managestd(String c_managestd) {
		this.c_managestd = c_managestd;
	}
	public String getC_examstd() {
		return c_examstd;
	}
	public void setC_examstd(String c_examstd) {
		this.c_examstd = c_examstd;
	}
	public String getC_public_id() {
		return c_public_id;
	}
	public void setC_public_id(String c_public_id) {
		this.c_public_id = c_public_id;
	}
	public String getC_ispublic() {
		return c_ispublic;
	}
	public void setC_ispublic(String c_ispublic) {
		this.c_ispublic = c_ispublic;
	}
	public String getC_media_file_id() {
		return c_media_file_id;
	}
	public void setC_media_file_id(String c_media_file_id) {
		this.c_media_file_id = c_media_file_id;
	}
	public String getC_media_file_type() {
		return c_media_file_type;
	}
	public void setC_media_file_type(String c_media_file_type) {
		this.c_media_file_type = c_media_file_type;
	}
	public String getC_media_file_path() {
		return c_media_file_path;
	}
	public void setC_media_file_path(String c_media_file_path) {
		this.c_media_file_path = c_media_file_path;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getC_starttime_exec_name() {
		return c_starttime_exec_name;
	}
	public void setC_starttime_exec_name(String c_starttime_exec_name) {
		this.c_starttime_exec_name = c_starttime_exec_name;
	}
	public String getC_endtime_exec_name() {
		return c_endtime_exec_name;
	}
	public void setC_endtime_exec_name(String c_endtime_exec_name) {
		this.c_endtime_exec_name = c_endtime_exec_name;
	}
	public String getC_getdata_pretext() {
		return c_getdata_pretext;
	}
	public void setC_getdata_pretext(String c_getdata_pretext) {
		this.c_getdata_pretext = c_getdata_pretext;
	}
	public String getC_getdata_unit() {
		return c_getdata_unit;
	}
	public void setC_getdata_unit(String c_getdata_unit) {
		this.c_getdata_unit = c_getdata_unit;
	}
	public String getC_getdata_text() {
		return c_getdata_text;
	}
	public void setC_getdata_text(String c_getdata_text) {
		this.c_getdata_text = c_getdata_text;
	}
	public String getC_checkdata_pretext() {
		return c_checkdata_pretext;
	}
	public void setC_checkdata_pretext(String c_checkdata_pretext) {
		this.c_checkdata_pretext = c_checkdata_pretext;
	}
	public String getC_checkdata_unit() {
		return c_checkdata_unit;
	}
	public void setC_checkdata_unit(String c_checkdata_unit) {
		this.c_checkdata_unit = c_checkdata_unit;
	}
	public String getC_checkdata_text() {
		return c_checkdata_text;
	}
	public void setC_checkdata_text(String c_checkdata_text) {
		this.c_checkdata_text = c_checkdata_text;
	}
	public String getC_groupindex() {
		return c_groupindex;
	}
	public void setC_groupindex(String c_groupindex) {
		this.c_groupindex = c_groupindex;
	}
	public String getC_actitem_media_file_id() {
		return c_actitem_media_file_id;
	}
	public void setC_actitem_media_file_id(String c_actitem_media_file_id) {
		this.c_actitem_media_file_id = c_actitem_media_file_id;
	}
	public String getC_actitem_media_file_type() {
		return c_actitem_media_file_type;
	}
	public void setC_actitem_media_file_type(String c_actitem_media_file_type) {
		this.c_actitem_media_file_type = c_actitem_media_file_type;
	}
	public String getC_actitem_media_file_path() {
		return c_actitem_media_file_path;
	}
	public void setC_actitem_media_file_path(String c_actitem_media_file_path) {
		this.c_actitem_media_file_path = c_actitem_media_file_path;
	}
	@Override
	public String toString() {
		return "Actnode [c_action_id=" + c_action_id + ", c_action_pid="
				+ c_action_pid + ", c_action_code=" + c_action_code
				+ ", c_action_type=" + c_action_type + ", c_section_id="
				+ c_section_id + ", c_action_sname=" + c_action_sname
				+ ", c_action_fname=" + c_action_fname + ", c_remark="
				+ c_remark + ", c_flag=" + c_flag + ", c_actnode_id="
				+ c_actnode_id + ", c_actnode_code=" + c_actnode_code
				+ ", c_actnode_name=" + c_actnode_name + ", c_pdca=" + c_pdca
				+ ", c_timeplan_id=" + c_timeplan_id + ", c_iskeyctrl="
				+ c_iskeyctrl + ", c_issequence=" + c_issequence
				+ ", c_israndom=" + c_israndom + ", c_isscan=" + c_isscan
				+ ", c_frequency=" + c_frequency + ", c_area_id=" + c_area_id
				+ ", c_area_name=" + c_area_name + ", c_position_id="
				+ c_position_id + ", c_position_name=" + c_position_name
				+ ", c_std_exec=" + c_std_exec + ", c_starttime_exec="
				+ c_starttime_exec + ", c_starttime_exec_name="
				+ c_starttime_exec_name + ", c_endtime_exec=" + c_endtime_exec
				+ ", c_endtime_exec_name=" + c_endtime_exec_name
				+ ", c_std_check=" + c_std_check + ", c_position_id_check="
				+ c_position_id_check + ", c_position_name_check="
				+ c_position_name_check + ", c_timelimit_check="
				+ c_timelimit_check + ", c_std_review=" + c_std_review
				+ ", c_position_id_review=" + c_position_id_review
				+ ", c_position_name_review=" + c_position_name_review
				+ ", c_timelimit_review=" + c_timelimit_review
				+ ", c_position_id_feedback1=" + c_position_id_feedback1
				+ ", c_position_name_feedback1=" + c_position_name_feedback1
				+ ", c_position_id_feedback2=" + c_position_id_feedback2
				+ ", c_position_name_feedback2=" + c_position_name_feedback2
				+ ", c_position_id_err1=" + c_position_id_err1
				+ ", c_position_name_err1=" + c_position_name_err1
				+ ", c_position_id_err2=" + c_position_id_err2
				+ ", c_position_name_err2=" + c_position_name_err2
				+ ", c_creator=" + c_creator + ", c_createtime=" + c_createtime
				+ ", c_modifier=" + c_modifier + ", c_modifytime="
				+ c_modifytime + ", c_version=" + c_version + ", c_istimerule="
				+ c_istimerule + ", c_action_name=" + c_action_name
				+ ", c_err_exec=" + c_err_exec + ", c_records_exec="
				+ c_records_exec + ", c_err_check=" + c_err_check
				+ ", c_records_check=" + c_records_check + ", c_department="
				+ c_department + ", c_file_id=" + c_file_id
				+ ", c_actnodetype=" + c_actnodetype + ", c_manageattr="
				+ c_manageattr + ", c_managestd=" + c_managestd
				+ ", c_examstd=" + c_examstd + ", c_public_id=" + c_public_id
				+ ", c_ispublic=" + c_ispublic + ", c_media_file_id="
				+ c_media_file_id + ", c_media_file_type=" + c_media_file_type
				+ ", c_media_file_path=" + c_media_file_path + ", filePath="
				+ filePath + ", c_actitem_id=" + c_actitem_id
				+ ", c_actitem_index=" + c_actitem_index + ", c_actitem_code="
				+ c_actitem_code + ", c_actitem_name=" + c_actitem_name
				+ ", c_actitem_std=" + c_actitem_std + ", c_explain="
				+ c_explain + ", c_getdata_pretext=" + c_getdata_pretext
				+ ", c_getdatatype=" + c_getdatatype + ", c_getdata_unit="
				+ c_getdata_unit + ", c_getdata_text=" + c_getdata_text
				+ ", c_actitem_std_check=" + c_actitem_std_check
				+ ", c_checkdata_pretext=" + c_checkdata_pretext
				+ ", c_checkdatatype=" + c_checkdatatype
				+ ", c_checkdata_unit=" + c_checkdata_unit
				+ ", c_checkdata_text=" + c_checkdata_text + ", c_groupindex="
				+ c_groupindex + ", c_actitem_media_file_id="
				+ c_actitem_media_file_id + ", c_actitem_media_file_type="
				+ c_actitem_media_file_type + ", c_actitem_media_file_path="
				+ c_actitem_media_file_path + "]";
	}
	
}