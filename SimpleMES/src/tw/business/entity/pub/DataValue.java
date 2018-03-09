package tw.business.entity.pub;
//o.f_pid,o.f_batch,o.f_data,o.f_state
public class DataValue {
	private Integer number;
	private String id;
	private String batch;
	private Double data;
	private Integer state;
	
	private String createTime;
	private Double deviation;

	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	private Integer manualWeed;//是否剔除
	public Integer getManualWeed() {
		return manualWeed;
	}
	public void setManualWeed(Integer manualWeed) {
		this.manualWeed = manualWeed;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Double getDeviation() {
		return deviation;
	}
	public void setDeviation(Double deviation) {
		this.deviation = deviation;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}
	public Double getData() {
		return data;
	}
	public void setData(Double data) {
		this.data = data;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	
	

}
