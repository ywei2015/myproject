package sail.beans.entity.vo;

import java.io.Serializable;

public class BatWorkOrderVo implements Serializable{
	private String pid;
	private String workordercode;
	private String workordertype;
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getWorkordercode() {
		return workordercode;
	}
	public void setWorkordercode(String workordercode) {
		this.workordercode = workordercode;
	}
	public String getWorkordertype() {
		return workordertype;
	}
	public void setWorkordertype(String workordertype) {
		this.workordertype = workordertype;
	}
	
	
}
