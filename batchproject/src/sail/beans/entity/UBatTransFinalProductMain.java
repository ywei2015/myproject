package sail.beans.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
/**
 * 物流管控平台完成成品调运信息主表
 * @author cyj
 * 2017-01-19
 */
public class UBatTransFinalProductMain implements Serializable{

	private String pid;
	private String transferBill;
	private String date;
	private String plateNumber;
	private String driver;
	private String sendoutDate;
	private String receivedDate;
	private String remark;
	private String synchroFlag;
	private String getdataTime;
	private String synchroTime;
	private Set<UBatTransFinalProductSec> secs = new HashSet<UBatTransFinalProductSec>(0);
	
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
	public Set<UBatTransFinalProductSec> getSecs() {
		return secs;
	}
	public void setSecs(Set<UBatTransFinalProductSec> secs) {
		this.secs = secs;
	}
	
}
