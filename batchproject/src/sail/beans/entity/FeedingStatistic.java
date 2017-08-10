package sail.beans.entity;

public class FeedingStatistic {
	private String workorderCode;
	private String produceDate;
	private String matName;
	private String matBatch;
	private String operateTime;
	private String name;
	public String getMatBatch() {
		return matBatch;
	}
	public void setMatBatch(String matBatch) {
		this.matBatch = matBatch;
	}
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
	public String getMatName() {
		return matName;
	}
	public void setMatName(String matName) {
		this.matName = matName;
	}
	public String getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "FeedingStatistic [workorderCode=" + workorderCode + ", produceDate=" + produceDate + ", matName="
				+ matName + ", matBatch=" + matBatch + ", operateTime=" + operateTime + ", name=" + name + "]";
	}
	
	
}
