package sail.beans.entity;

public class ProductionWorkorder {
	private String workorderCode;
	private String produceDate;
	private String workorderState;
	private String batchsPutIn;
	private String actualQuantity;
	private String rlsStatus;  //完成情况
	public String getWorkorderCode() {
		return workorderCode;
	}
	public void setWorkorderCode(String workorderCode) {
		this.workorderCode = workorderCode;
	}
	public String getProduceDate() {
		return produceDate;
	}
	public void setProduceDate(String produceDate) {
		this.produceDate = produceDate;
	}
	public String getWorkorderState() {
		return workorderState;
	}
	public void setWorkorderState(String workorderState) {
		this.workorderState = workorderState;
	}
	public String getBatchsPutIn() {
		return batchsPutIn;
	}
	public void setBatchsPutIn(String batchsPutIn) {
		this.batchsPutIn = batchsPutIn;
	}
	public String getActualQuantity() {
		return actualQuantity;
	}
	public void setActualQuantity(String actualQuantity) {
		this.actualQuantity = actualQuantity;
	}
	public String getRlsStatus() {
		return rlsStatus;
	}
	public void setRlsStatus(String rlsStatus) {
		this.rlsStatus = rlsStatus;
	}
	@Override
	public String toString() {
		return "Pojo1 [workorderCode=" + workorderCode + ", produceDate=" + produceDate + ", workorderState="
				+ workorderState + ", batchsPutIn=" + batchsPutIn + ", actualQuantity=" + actualQuantity
				+ ", rlsStatus=" + rlsStatus + "]";
	}
	
}
