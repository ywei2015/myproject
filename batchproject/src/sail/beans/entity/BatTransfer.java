package sail.beans.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
/**
 * 批次管理_成品调运单据表[接口数据]-FR：物资综合管理平台
 * @author cyj
 * 2017-01-19
 */
public class BatTransfer implements Serializable{

	private String pid;
	private String transferBill;
	private String factory;
	private String date;
	private String plateNumber;
	private String driver;
	private String brandCode;
	private String sendoutDate;
	private String receivedDate;
	private String remark1;
	private String remark2;
	private String remark3;
	private String remark4;
	private String remark5;
	private String remark;
	private String sysFlag;
	private String creator;
	private String createTime;
	private String lastModifier;
	private String lastModifiedTime;
	private Set<BatTransferDetail> details = new HashSet<BatTransferDetail>(0);
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getTransferBill() {
		return transferBill;
	}
	public void setTransferBill(String transferBill) {
		this.transferBill = transferBill;
	}
	public String getFactory() {
		return factory;
	}
	public void setFactory(String factory) {
		this.factory = factory;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getPlateNumber() {
		return plateNumber;
	}
	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}
	public String getDriver() {
		return driver;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}
	public String getBrandCode() {
		return brandCode;
	}
	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}
	public String getSendoutDate() {
		return sendoutDate;
	}
	public void setSendoutDate(String sendoutDate) {
		this.sendoutDate = sendoutDate;
	}
	public String getReceivedDate() {
		return receivedDate;
	}
	public void setReceivedDate(String receivedDate) {
		this.receivedDate = receivedDate;
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
	public String getLastModifier() {
		return lastModifier;
	}
	public void setLastModifier(String lastModifier) {
		this.lastModifier = lastModifier;
	}
	public String getLastModifiedTime() {
		return lastModifiedTime;
	}
	public void setLastModifiedTime(String lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}
	public Set<BatTransferDetail> getDetails() {
		return details;
	}
	public void setDetails(Set<BatTransferDetail> details) {
		this.details = details;
	}
	
}
