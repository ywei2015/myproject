package sail.beans.entity;

import java.io.Serializable;
/**
 * 成品移库（移出/移入）信息从表
 * @author cyj
 *
 */
public class UBatTransProductMoveSec implements Serializable{

	private String pid;
	private UBatTransProductMoveMain main;
	private String batchNo;
	private String codeType;
	private String giTime;
	private String trayNo;
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
	public UBatTransProductMoveMain getMain() {
		return main;
	}
	public void setMain(UBatTransProductMoveMain main) {
		this.main = main;
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
	public String getTrayNo() {
		return trayNo;
	}
	public void setTrayNo(String trayNo) {
		this.trayNo = trayNo;
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
