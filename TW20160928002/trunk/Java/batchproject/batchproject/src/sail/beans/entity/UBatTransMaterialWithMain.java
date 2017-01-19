package sail.beans.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
/**
 * 烟用材料配盘信息主表
 * @author cyj
 * 2017-01-18
 */
public class UBatTransMaterialWithMain implements Serializable{

	private String pid;
	private String palletWorkorder;
	private String ebeln;
	private String palletSid;
	private String produceDate;
	private String lgortCode;
	private String lgortName;
	private String workareaCode;
	private String workareaName;
	private String worktimeCode;
	private String worktimeName;
	private String workteamCode;
	private String workteamName;
	private String workorderState;
	private String remark;
	private String synchroFlag;
	private String getdataTime;
	private String synchroTime;
	private Set<UBatTransMaterialWithSec> secs = new HashSet<UBatTransMaterialWithSec>(0);
	
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getPalletWorkorder() {
		return palletWorkorder;
	}
	public void setPalletWorkorder(String palletWorkorder) {
		this.palletWorkorder = palletWorkorder;
	}
	public String getEbeln() {
		return ebeln;
	}
	public void setEbeln(String ebeln) {
		this.ebeln = ebeln;
	}
	public String getPalletSid() {
		return palletSid;
	}
	public void setPalletSid(String palletSid) {
		this.palletSid = palletSid;
	}
	public String getProduceDate() {
		return produceDate;
	}
	public void setProduceDate(String produceDate) {
		this.produceDate = produceDate;
	}
	public String getLgortCode() {
		return lgortCode;
	}
	public void setLgortCode(String lgortCode) {
		this.lgortCode = lgortCode;
	}
	public String getLgortName() {
		return lgortName;
	}
	public void setLgortName(String lgortName) {
		this.lgortName = lgortName;
	}
	public String getWorkareaCode() {
		return workareaCode;
	}
	public void setWorkareaCode(String workareaCode) {
		this.workareaCode = workareaCode;
	}
	public String getWorkareaName() {
		return workareaName;
	}
	public void setWorkareaName(String workareaName) {
		this.workareaName = workareaName;
	}
	public String getWorktimeCode() {
		return worktimeCode;
	}
	public void setWorktimeCode(String worktimeCode) {
		this.worktimeCode = worktimeCode;
	}
	public String getWorktimeName() {
		return worktimeName;
	}
	public void setWorktimeName(String worktimeName) {
		this.worktimeName = worktimeName;
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
	public String getWorkorderState() {
		return workorderState;
	}
	public void setWorkorderState(String workorderState) {
		this.workorderState = workorderState;
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
	public Set<UBatTransMaterialWithSec> getSecs() {
		return secs;
	}
	public void setSecs(Set<UBatTransMaterialWithSec> secs) {
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
