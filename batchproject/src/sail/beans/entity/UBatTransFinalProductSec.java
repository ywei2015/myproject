package sail.beans.entity;

import java.io.Serializable;
/**
 * 物流管控平台完成成品调运信息明细
 * @author cyj
 * 2017-01-19
 */
public class UBatTransFinalProductSec implements Serializable{

	private String pid;
	private UBatTransFinalProductMain main;
	private String contractCode;
	private String brandCode;
	private String brandName;
	private Double quantity;
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
	public UBatTransFinalProductMain getMain() {
		return main;
	}
	public void setMain(UBatTransFinalProductMain main) {
		this.main = main;
	}
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public String getBrandCode() {
		return brandCode;
	}
	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public Double getQuantity() {
		return quantity;
	}
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
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
