package com.talkweb.xwzcxt.pojo;

import com.talkweb.twdpe.core.data.BasePOJO;
/**
 * @author Administrator
 * 职责信息实体
 */
public class SdActionPositionPojo extends BasePOJO {

	private String c_id;
	private String c_action_id;				//流程ID
	private String c_respons_orgid;			//部门组织ID
	private String c_respons_orgname;
	private String c_respons_type;
	private String c_respons_positionid;	//岗位ID
	private String c_respons_positionname;
	
	public String getC_id() {
		return c_id;
	}
	public void setC_id(String c_id) {
		this.c_id = c_id;
	}
	public String getC_action_id() {
		return c_action_id;
	}
	public void setC_action_id(String c_action_id) {
		this.c_action_id = c_action_id;
	}
	public String getC_respons_orgid() {
		return c_respons_orgid;
	}
	public void setC_respons_orgid(String c_respons_orgid) {
		this.c_respons_orgid = c_respons_orgid;
	}
	public String getC_respons_type() {
		return c_respons_type;
	}
	public void setC_respons_type(String c_respons_type) {
		this.c_respons_type = c_respons_type;
	}
	public String getC_respons_positionid() {
		return c_respons_positionid;
	}
	public void setC_respons_positionid(String c_respons_positionid) {
		this.c_respons_positionid = c_respons_positionid;
	}
	public String getC_respons_orgname() {
		return c_respons_orgname;
	}
	public void setC_respons_orgname(String c_respons_orgname) {
		this.c_respons_orgname = c_respons_orgname;
	}
	public String getC_respons_positionname() {
		return c_respons_positionname;
	}
	public void setC_respons_positionname(String c_respons_positionname) {
		this.c_respons_positionname = c_respons_positionname;
	}
}
