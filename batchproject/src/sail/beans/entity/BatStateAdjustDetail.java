package sail.beans.entity;

import java.io.Serializable;

public class BatStateAdjustDetail implements Serializable{

	private String pid;
	private String adjustpid;
	private String matbatch;
	private String newstate;
	private String oldstate;
	private String matcode;
	private String matname;
	private String quantity;
	private String unit;
	private String cause;
	private String operator;
	private String operatetime;
	private String remark;
	private String sysflag;
	private String creator;
	private String createtime;
	private String lastmodifier;
	private String lastmodifiedtime;
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getAdjustpid() {
		return adjustpid;
	}
	public void setAdjustpid(String adjustpid) {
		this.adjustpid = adjustpid;
	}
	public String getMatbatch() {
		return matbatch;
	}
	public void setMatbatch(String matbatch) {
		this.matbatch = matbatch;
	}
	public String getNewstate() {
		return newstate;
	}
	public void setNewstate(String newstate) {
		this.newstate = newstate;
	}
	public String getOldstate() {
		return oldstate;
	}
	public void setOldstate(String oldstate) {
		this.oldstate = oldstate;
	}
	public String getMatcode() {
		return matcode;
	}
	public void setMatcode(String matcode) {
		this.matcode = matcode;
	}
	public String getMatname() {
		return matname;
	}
	public void setMatname(String matname) {
		this.matname = matname;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getCause() {
		return cause;
	}
	public void setCause(String cause) {
		this.cause = cause;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getOperatetime() {
		return operatetime;
	}
	public void setOperatetime(String operatetime) {
		this.operatetime = operatetime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getSysflag() {
		return sysflag;
	}
	public void setSysflag(String sysflag) {
		this.sysflag = sysflag;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getLastmodifier() {
		return lastmodifier;
	}
	public void setLastmodifier(String lastmodifier) {
		this.lastmodifier = lastmodifier;
	}
	public String getLastmodifiedtime() {
		return lastmodifiedtime;
	}
	public void setLastmodifiedtime(String lastmodifiedtime) {
		this.lastmodifiedtime = lastmodifiedtime;
	}
	
	
}
