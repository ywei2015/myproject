package tw.sysbase.entity.pub;

import java.util.List;

import tw.business.entity.pub.BasicData;

public class ObjStructure  extends BasicData{
	private String id;
	private String code;
	private String name;
	private Integer sysFlag;
	private String remark;
	private String accessControlId;
	private String accessControlName;
	private List<ObjEntityRef> children;
	
	public List<ObjEntityRef> getChildren() {
		return children;
	}
	public void setChildren(List<ObjEntityRef> children) {
		this.children = children;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getSysFlag() {
		return sysFlag;
	}
	public void setSysFlag(Integer sysFlag) {
		this.sysFlag = sysFlag;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getAccessControlId() {
		return accessControlId;
	}
	public void setAccessControlId(String accessControlId) {
		this.accessControlId = accessControlId;
	}
	public String getAccessControlName() {
		return accessControlName;
	}
	public void setAccessControlName(String accessControlName) {
		this.accessControlName = accessControlName;
	}
	
}
