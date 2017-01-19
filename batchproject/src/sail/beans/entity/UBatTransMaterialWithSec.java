package sail.beans.entity;

import java.io.Serializable;
/**
 * 烟用材料配盘信息从表
 * @author cyj
 * 2017-01-18
 */
public class UBatTransMaterialWithSec implements Serializable{

	private String pid;
	private UBatTransMaterialWithMain main;
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
	private String remark;
	private String synchroFlag;
	private String getdataTime;
	private String synchroTime;
	
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public UBatTransMaterialWithMain getMain() {
		return main;
	}
	public void setMain(UBatTransMaterialWithMain main) {
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getSynchroFlag() {
		return synchroFlag;
	}
	public void setSynchroFlag(String synchroFlag) {
		this.synchroFlag = synchroFlag;
	}
	public String getGetdataTime() {
		return getdataTime;
	}
	public void setGetdataTime(String getdataTime) {
		this.getdataTime = getdataTime;
	}
	public String getSynchroTime() {
		return synchroTime;
	}
	public void setSynchroTime(String synchroTime) {
		this.synchroTime = synchroTime;
	}
}
