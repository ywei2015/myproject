package com.talkweb.xwzcxt.vo;

public class PositionTreeVo {

	private  String  id;
	private  String  pId;
	private  String  name;
	private  String isParent;
	private  Long   orgid;
	private  Long  positionid;
	private  String typeCode;
	
	
	public String getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	public Long getPositionid() {
		return positionid;
	}
	public void setPositionid(Long positionid) {
		this.positionid = positionid;
	}
	public Long getOrgid() {
		return orgid;
	}
	public void setOrgid(Long orgid) {
		this.orgid = orgid;
	}
	public String getIsParent() {
		return isParent;
	}
	public void setIsParent(String isParent) {
		this.isParent = isParent;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
