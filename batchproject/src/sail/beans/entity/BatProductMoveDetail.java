package sail.beans.entity;

import java.io.Serializable;
/**
 * HIGHBAY_成品移库明细表[接口数据]
 * @author cyj
 *
 */
public class BatProductMoveDetail implements Serializable{

	private String pid;
	private BatProductMove bill;
	private String batchNo;
	private String codeType;
	private String giTime;
	private String grTime;
	private String trayNo;
	private String remark1;
	private String remark2;
	private String remark3;
	private String remark4;
	private String remark5;
	private String remark;
	private String sysFlag;
	private String creator;
	private String createTime;
	private String lastmodifier;
	private String lastmodifiedTime;
	
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public BatProductMove getBill() {
		return bill;
	}
	public void setBill(BatProductMove bill) {
		this.bill = bill;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getCodeType() {
		return codeType;
	}
	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}
	public String getGiTime() {
		return giTime;
	}
	public void setGiTime(String giTime) {
		this.giTime = giTime;
	}
	public String getGrTime() {
		return grTime;
	}
	public void setGrTime(String grTime) {
		this.grTime = grTime;
	}
	public String getTrayNo() {
		return trayNo;
	}
	public void setTrayNo(String trayNo) {
		this.trayNo = trayNo;
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
	public String getSysFlag() {
		return sysFlag;
	}
	public void setSysFlag(String sysFlag) {
		this.sysFlag = sysFlag;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getLastmodifier() {
		return lastmodifier;
	}
	public void setLastmodifier(String lastmodifier) {
		this.lastmodifier = lastmodifier;
	}
	public String getLastmodifiedTime() {
		return lastmodifiedTime;
	}
	public void setLastmodifiedTime(String lastmodifiedTime) {
		this.lastmodifiedTime = lastmodifiedTime;
	}
}
