package sail.beans.entity;

public class PersonalFeedingStatistic {
	private String name;
	private String batchsPutIn;//投料批次数 
	private String workorderCount;//工单数
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBatchsPutIn() {
		return batchsPutIn;
	}
	public void setBatchsPutIn(String batchsPutIn) {
		this.batchsPutIn = batchsPutIn;
	}
	public String getWorkorderCount() {
		return workorderCount;
	}
	public void setWorkorderCount(String workorderCount) {
		this.workorderCount = workorderCount;
	}
	@Override
	public String toString() {
		return "PersonalFeedingStatistic [name=" + name + ", batchsPutIn=" + batchsPutIn + ", workorderCount="
				+ workorderCount + "]";
	}
	
	
}
