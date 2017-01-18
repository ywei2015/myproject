package sail.beans.entity;

import java.io.Serializable;
/**
 * 批次管理_HIGHBAYDEPOT_成品入高架库单据明细表 (高架库系统接口数据）
 * @author cyj
 *
 */
public class BatHighBayDepotInDetail implements Serializable{

	private String pid;
//	private String inbillPid;
	private BatHighBayDepotIn bill;
	private String codeType;
	private String boxCode;
	private String batch;
	private String lot;
	private String inspectNo;
	private String brandCode;
	private String locationCode;
	private String locationName;
	private String tray;
	private String receiver;
	private String receiverTime;
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
	public BatHighBayDepotIn getBill() {
		return bill;
	}
	public void setBill(BatHighBayDepotIn bill) {
		this.bill = bill;
	}
	public String getCodeType() {
		return codeType;
	}
	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}
	public String getBoxCode() {
		return boxCode;
	}
	public void setBoxCode(String boxCode) {
		this.boxCode = boxCode;
	}
	public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}
	public String getLot() {
		return lot;
	}
	public void setLot(String lot) {
		this.lot = lot;
	}
	public String getInspectNo() {
		return inspectNo;
	}
	public void setInspectNo(String inspectNo) {
		this.inspectNo = inspectNo;
	}
	public String getBrandCode() {
		return brandCode;
	}
	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}
	public String getLocationCode() {
		return locationCode;
	}
	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public String getTray() {
		return tray;
	}
	public void setTray(String tray) {
		this.tray = tray;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getReceiverTime() {
		return receiverTime;
	}
	public void setReceiverTime(String receiverTime) {
		this.receiverTime = receiverTime;
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
