package sail.beans.entity;

import java.io.Serializable;

public class BatDepotIoDetailList implements Serializable{

	private String pid;
	private String billdetailpid;
	private String slavebatch;
	private double quantity;
	private String unit;
	private String relatetime;
	private String repeated;
	private String remark1;
	private String remark2;
	private String remark3;
	private String remark4;
	private String remark5;//是否已经出入库  1为已经入库 2为已经出库
	private String remark;
	private String reason;
	private String sysflag;
	private String creator;
	private String createtime;
	private String lastmodifier;
	private String lastmodifiedtime;
	private String billpid;
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getBilldetailpid() {
		return billdetailpid;
	}
	public void setBilldetailpid(String billdetailpid) {
		this.billdetailpid = billdetailpid;
	}
	public String getSlavebatch() {
		return slavebatch;
	}
	public void setSlavebatch(String slavebatch) {
		this.slavebatch = slavebatch;
	}
	public double getQuantity() {
		return quantity;
	}
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getRelatetime() {
		return relatetime;
	}
	public void setRelatetime(String relatetime) {
		this.relatetime = relatetime;
	}
	public String getRemark1() {
		return remark1;
	}
	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}
	public String getRemark2() {
		return remark2;
	}
	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}
	public String getRemark3() {
		return remark3;
	}
	public void setRemark3(String remark3) {
		this.remark3 = remark3;
	}
	public String getRemark4() {
		return remark4;
	}
	public void setRemark4(String remark4) {
		this.remark4 = remark4;
	}
	public String getRemark5() {
		return remark5;
	}
	public void setRemark5(String remark5) {
		this.remark5 = remark5;
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
	public String getBillpid() {
		return billpid;
	}
	public void setBillpid(String billpid) {
		this.billpid = billpid;
	}
	public String getRepeated() {
		return repeated;
	}
	public void setRepeated(String repeated) {
		this.repeated = repeated;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	
	

}
