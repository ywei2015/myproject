package sail.beans.entity;

import java.io.Serializable;

public class PermissionInfo implements Serializable{
	
	private String pid;
	private String usercode;
	private String code;
	private String name;
	private String ispower;
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getUsercode() {
		return usercode;
	}
	public void setUsercode(String usercode) {
		this.usercode = usercode;
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
	public String getIspower() {
		return ispower;
	}
	public void setIspower(String ispower) {
		this.ispower = ispower;
	}
	
	
	
	
}
