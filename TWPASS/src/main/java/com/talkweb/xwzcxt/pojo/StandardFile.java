package com.talkweb.xwzcxt.pojo;



import com.talkweb.twdpe.core.data.BasePOJO;


public class StandardFile extends BasePOJO {

	//标准文件表
	private String c_sfile_id	    ="";//	标准文件ID
	private String c_sfile_name	  ="";//	标准文件NAME
	private String c_sort_id	      ="";//  类别编号
	private String c_sfile_version	="";//	版本编号
	private String c_releaseunit	  ="";//	发布单位
	private String c_releasetime	  ="";//  发布时间
	private String c_impletime	    ="";//   实施时间
	private String c_foreword1	    ="";//		前言1
	private String c_fw_zdyj	      ="";//	制定依据
	private String c_fw_tcdw	      ="";//	提出单位
	private String c_fw_qcbm	      ="";//	起草部门ID（ERP）
	private String c_fw_gkbm	      ="";//	归口部门ID（ERP）
	private String c_fw_pzr	      ="";//	批准人ID（ERP）
	private String c_fw_qcr	      ="";//	起草人ID（ERP）
	private String c_fw_fbsj	      ="";//  发布时间
	private String c_fw_xdcs	      ="";//   修订次数
	private String c_fw_xdnr1	    ="";//		修订内容1
	private String c_fw_xdnr2	    ="";//		修订内容2
	private String c_fw_xdnr3	    ="";//		修订内容3
	private String c_fw_xdnr4	    ="";//		修订内容4
	private String c_fw_xdnr5	    ="";//		修订内容5
	private String c_fw_xdnrn	    ="";//		修订内容其他
	private String c_action_id	    ="";//	流程节点ID
	private String c_creator	     ="";//	创建人（ERP）
	private String c_createtime	 ="";//
	private String c_modifier	   ="";//	修改人（ERP）
	private String c_modifytime	 ="";//
	
	//流程节点表
	private String c_action_pid	 ="";//	节点父节点
	private String c_action_code	 ="";//	编码
	private String c_action_type	 ="";// 流程节点类型（保留），需求规定只有最底层节点才允许添加岗位活动，此类型区分是否为最底层节点
	private String c_section_id	 ="";// 所属版块（1安全、2质量、3成本、4效率、5团队、6环境）
	private String c_action_sname	="";//	 简称
	private String c_action_fname	="";//	全称
	private String c_remark	     ="";//		描述
	private String c_flag	       ="";//  是否使用，默认为1 （保留）应对以后是否取消此项流程节点
	
	//文件类别表
	private String c_sort_code	="";
	
	private String c_sort_pid	  ="";
	private String c_sort_name	="";
	
	//文件内容表
	private String c_contentid	     ="";//	内容编号
	private String c_sectionid	     ="";//	章节编号
	private String c_sectionpid	   ="";//	父章节编号
	private String c_title	         ="";//		章节描述
	private String c_content	       ="";//		章节内容（段落）
	private String c_includeatt	   ="";//  包含附件（1,0）
	
	
	//文件附件表
	private String c_att_id	       ="";//	附件ID
	private String c_file_id	       ="";//	文件ID
	private String c_atttype_id	   ="";//  附件类型ID（附件、附录）
	
	//文件表
	private String c_file_name	      ="";//	文件NAME
	private String c_filetype_id	    ="";//	类型ID（EXCEL,WORD,IMG...）
	private String c_file_url	      ="";//	URL保存地址
	
	public String getC_file_name() {
		return c_file_name;
	}
	public void setC_file_name(String c_file_name) {
		this.c_file_name = c_file_name;
	}
	public String getC_filetype_id() {
		return c_filetype_id;
	}
	public void setC_filetype_id(String c_filetype_id) {
		this.c_filetype_id = c_filetype_id;
	}
	public String getC_file_url() {
		return c_file_url;
	}
	public void setC_file_url(String c_file_url) {
		this.c_file_url = c_file_url;
	}

	public String getC_contentid() {
		return c_contentid;
	}
	public void setC_contentid(String c_contentid) {
		this.c_contentid = c_contentid;
	}
	public String getC_sectionid() {
		return c_sectionid;
	}
	public void setC_sectionid(String c_sectionid) {
		this.c_sectionid = c_sectionid;
	}
	public String getC_sectionpid() {
		return c_sectionpid;
	}
	public void setC_sectionpid(String c_sectionpid) {
		this.c_sectionpid = c_sectionpid;
	}
	public String getC_title() {
		return c_title;
	}
	public void setC_title(String c_title) {
		this.c_title = c_title;
	}
	public String getC_content() {
		return c_content;
	}
	public void setC_content(String c_content) {
		this.c_content = c_content;
	}
	public String getC_includeatt() {
		return c_includeatt;
	}
	public void setC_includeatt(String c_includeatt) {
		this.c_includeatt = c_includeatt;
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
	public String getC_att_id() {
		return c_att_id;
	}
	public void setC_att_id(String c_att_id) {
		this.c_att_id = c_att_id;
	}
	public String getC_file_id() {
		return c_file_id;
	}
	public void setC_file_id(String c_file_id) {
		this.c_file_id = c_file_id;
	}
	public String getC_atttype_id() {
		return c_atttype_id;
	}
	public void setC_atttype_id(String c_atttype_id) {
		this.c_atttype_id = c_atttype_id;
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
	public String getC_sort_code() {
		return c_sort_code;
	}
	public void setC_sort_code(String c_sort_code) {
		this.c_sort_code = c_sort_code;
	}
	public String getC_sort_pid() {
		return c_sort_pid;
	}
	public void setC_sort_pid(String c_sort_pid) {
		this.c_sort_pid = c_sort_pid;
	}
	public String getC_sort_name() {
		return c_sort_name;
	}
	public void setC_sort_name(String c_sort_name) {
		this.c_sort_name = c_sort_name;
	}
	public String getC_sfile_id() {
		return c_sfile_id;
	}
	public void setC_sfile_id(String c_sfile_id) {
		this.c_sfile_id = c_sfile_id;
	}
	public String getC_sfile_name() {
		return c_sfile_name;
	}
	public void setC_sfile_name(String c_sfile_name) {
		this.c_sfile_name = c_sfile_name;
	}
	public String getC_sort_id() {
		return c_sort_id;
	}
	public void setC_sort_id(String c_sort_id) {
		this.c_sort_id = c_sort_id;
	}
	public String getC_sfile_version() {
		return c_sfile_version;
	}
	public void setC_sfile_version(String c_sfile_version) {
		this.c_sfile_version = c_sfile_version;
	}
	public String getC_releaseunit() {
		return c_releaseunit;
	}
	public void setC_releaseunit(String c_releaseunit) {
		this.c_releaseunit = c_releaseunit;
	}
	public String getC_releasetime() {
		return c_releasetime;
	}
	public void setC_releasetime(String c_releasetime) {
		this.c_releasetime = c_releasetime;
	}
	public String getC_impletime() {
		return c_impletime;
	}
	public void setC_impletime(String c_impletime) {
		this.c_impletime = c_impletime;
	}
	public String getC_foreword1() {
		return c_foreword1;
	}
	public void setC_foreword1(String c_foreword1) {
		this.c_foreword1 = c_foreword1;
	}
	public String getC_fw_zdyj() {
		return c_fw_zdyj;
	}
	public void setC_fw_zdyj(String c_fw_zdyj) {
		this.c_fw_zdyj = c_fw_zdyj;
	}
	public String getC_fw_tcdw() {
		return c_fw_tcdw;
	}
	public void setC_fw_tcdw(String c_fw_tcdw) {
		this.c_fw_tcdw = c_fw_tcdw;
	}
	public String getC_fw_qcbm() {
		return c_fw_qcbm;
	}
	public void setC_fw_qcbm(String c_fw_qcbm) {
		this.c_fw_qcbm = c_fw_qcbm;
	}
	public String getC_fw_gkbm() {
		return c_fw_gkbm;
	}
	public void setC_fw_gkbm(String c_fw_gkbm) {
		this.c_fw_gkbm = c_fw_gkbm;
	}
	public String getC_fw_pzr() {
		return c_fw_pzr;
	}
	public void setC_fw_pzr(String c_fw_pzr) {
		this.c_fw_pzr = c_fw_pzr;
	}
	public String getC_fw_qcr() {
		return c_fw_qcr;
	}
	public void setC_fw_qcr(String c_fw_qcr) {
		this.c_fw_qcr = c_fw_qcr;
	}
	public String getC_fw_fbsj() {
		return c_fw_fbsj;
	}
	public void setC_fw_fbsj(String c_fw_fbsj) {
		this.c_fw_fbsj = c_fw_fbsj;
	}
	public String getC_fw_xdcs() {
		return c_fw_xdcs;
	}
	public void setC_fw_xdcs(String c_fw_xdcs) {
		this.c_fw_xdcs = c_fw_xdcs;
	}
	public String getC_fw_xdnr1() {
		return c_fw_xdnr1;
	}
	public void setC_fw_xdnr1(String c_fw_xdnr1) {
		this.c_fw_xdnr1 = c_fw_xdnr1;
	}
	public String getC_fw_xdnr2() {
		return c_fw_xdnr2;
	}
	public void setC_fw_xdnr2(String c_fw_xdnr2) {
		this.c_fw_xdnr2 = c_fw_xdnr2;
	}
	public String getC_fw_xdnr3() {
		return c_fw_xdnr3;
	}
	public void setC_fw_xdnr3(String c_fw_xdnr3) {
		this.c_fw_xdnr3 = c_fw_xdnr3;
	}
	public String getC_fw_xdnr4() {
		return c_fw_xdnr4;
	}
	public void setC_fw_xdnr4(String c_fw_xdnr4) {
		this.c_fw_xdnr4 = c_fw_xdnr4;
	}
	public String getC_fw_xdnr5() {
		return c_fw_xdnr5;
	}
	public void setC_fw_xdnr5(String c_fw_xdnr5) {
		this.c_fw_xdnr5 = c_fw_xdnr5;
	}
	public String getC_fw_xdnrn() {
		return c_fw_xdnrn;
	}
	public void setC_fw_xdnrn(String c_fw_xdnrn) {
		this.c_fw_xdnrn = c_fw_xdnrn;
	}
	public String getC_action_id() {
		return c_action_id;
	}
	public void setC_action_id(String c_action_id) {
		this.c_action_id = c_action_id;
	}
	

	
	
}
