package sail.beans.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
/**
 * 成品移库（移出/移入）信息
 * @author cyj
 *
 */
public class UBatTransProductMoveMain implements Serializable{

	private String pid;
	private String transferBill;
	private String date;
	private String lgortGicode;
	private String lgortGiname;
	private String operateUsercode;
	private String operateUsername;
	private String twoStep;
	private String remark;
	private String synchroFlag;
	private String getdataTime;
	private String synchroTime;
	private Set<UBatTransProductMoveSec> secs = new HashSet<UBatTransProductMoveSec>(0);
	
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
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getLgortGicode() {
		return lgortGicode;
	}
	public void setLgortGicode(String lgortGicode) {
		this.lgortGicode = lgortGicode;
	}
	public String getLgortGiname() {
		return lgortGiname;
	}
	public void setLgortGiname(String lgortGiname) {
		this.lgortGiname = lgortGiname;
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
	public String getTwoStep() {
		return twoStep;
	}
	public void setTwoStep(String twoStep) {
		this.twoStep = twoStep;
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
	public Set<UBatTransProductMoveSec> getSecs() {
		return secs;
	}
	public void setSecs(Set<UBatTransProductMoveSec> secs) {
		this.secs = secs;
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
