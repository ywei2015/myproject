package com.talkweb.xwzcxt.pojo;

import com.talkweb.twdpe.core.data.BasePOJO;


/**
 * TODO(描述这个类的作用)
 * 
 * @author: 2014-5-27，GuveXie，（描述修改内容）
 */
public class StdFile extends BasePOJO {
	private static final long serialVersionUID = 1L;

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
	
}
