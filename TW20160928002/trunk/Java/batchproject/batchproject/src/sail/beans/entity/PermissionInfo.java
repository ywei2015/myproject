package sail.beans.entity;

import java.io.Serializable;

public class PermissionInfo implements Serializable{
	
	private String pid;
	private String userCode;
	private String code;
	private String name;
	private String isPower;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIsPower() {
		return isPower;
	}
	public void setIsPower(String isPower) {
		this.isPower = isPower;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	
}
