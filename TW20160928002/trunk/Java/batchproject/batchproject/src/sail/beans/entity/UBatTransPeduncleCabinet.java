package sail.beans.entity;

import java.io.Serializable;
/**
 * 制丝中控－梗丝进柜
 * @author cyj
 * 2017-01-20
 */
public class UBatTransPeduncleCabinet implements Serializable{

	private String pid;
	private String workorderCode;
	private String matBatch;
	private String matCode;
	private String matName;
	private String location;
	private String locationName;
	private String actualStarttime;
	private String actualEndtime;
	private Double quantity;
	private String unit;
	private String workteamCode;
	private String workteamName;
	private String operateUsercode;
	private String operateUsername;
	private String operateTime;
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
	public String getWorkorderCode() {
		return workorderCode;
	}
	public void setWorkorderCode(String workorderCode) {
		this.workorderCode = workorderCode;
	}
	public String getMatBatch() {
		return matBatch;
	}
	public void setMatBatch(String matBatch) {
		this.matBatch = matBatch;
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
	public Double getQuantity() {
		return quantity;
	}
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getWorkteamCode() {
		return workteamCode;
	}
	public void setWorkteamCode(String workteamCode) {
		this.workteamCode = workteamCode;
	}
	public String getWorkteamName() {
		return workteamName;
	}
	public void setWorkteamName(String workteamName) {
		this.workteamName = workteamName;
	}
	public String getOperateUsercode() {
		return operateUsercode;
	}
	public void setOperateUsercode(String operateUsercode) {
		this.operateUsercode = operateUsercode;
	}
	public String getOperateUsername() {
		return operateUsername;
	}
	public void setOperateUsername(String operateUsername) {
		this.operateUsername = operateUsername;
	}
	public String getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public String getActualStarttime() {
		return actualStarttime;
	}
	public void setActualStarttime(String actualStarttime) {
		this.actualStarttime = actualStarttime;
	}
	public String getActualEndtime() {
		return actualEndtime;
	}
	public void setActualEndtime(String actualEndtime) {
		this.actualEndtime = actualEndtime;
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
