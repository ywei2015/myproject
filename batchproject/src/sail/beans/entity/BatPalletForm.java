package sail.beans.entity;

import java.io.Serializable;
/**
 * 批次管理_JB_辅料配盘组成
 * @author cyj
 * 2017-01-18
 */
public class BatPalletForm implements Serializable{

	private String pid;
	private BatPalletWorkorder main;
	private String palletSid;
	private String palletCode;
	private String matCode;
	private String matName;
	private String allocationType;
	private String masterBatch;
	private Double slaveCount;
	private String slaveBatch;
	private String quantity;
	private String unit;
	private String location;
	private String operateTime;
	private String operator;
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
	public BatPalletWorkorder getMain() {
		return main;
	}
	public void setMain(BatPalletWorkorder main) {
		this.main = main;
	}
	public String getPalletSid() {
		return palletSid;
	}
	public void setPalletSid(String palletSid) {
		this.palletSid = palletSid;
	}
	public String getPalletCode() {
		return palletCode;
	}
	public void setPalletCode(String palletCode) {
		this.palletCode = palletCode;
	}
	public String getMatCode() {
		return matCode;
	}
	public void setMatCode(String matCode) {
		this.matCode = matCode;
	}
	public String getMatName() {
		return matName;
	}
	public void setMatName(String matName) {
		this.matName = matName;
	}
	public String getAllocationType() {
		return allocationType;
	}
	public void setAllocationType(String allocationType) {
		this.allocationType = allocationType;
	}
	public String getMasterBatch() {
		return masterBatch;
	}
	public void setMasterBatch(String masterBatch) {
		this.masterBatch = masterBatch;
	}
	public Double getSlaveCount() {
		return slaveCount;
	}
	public void setSlaveCount(Double slaveCount) {
		this.slaveCount = slaveCount;
	}
	public String getSlaveBatch() {
		return slaveBatch;
	}
	public void setSlaveBatch(String slaveBatch) {
		this.slaveBatch = slaveBatch;
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
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
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
