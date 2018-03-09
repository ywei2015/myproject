package tw.business.entity.pub;

public class RealTimeDate {
	private String time;
	private Double val;
	
	public RealTimeDate() {
	
	}
	public RealTimeDate(String time, Double val) {
		super();
		this.time = time;
		this.val = val;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public Double getVal() {
		return val;
	}
	public void setVal(Double val) {
		this.val = val;
	}
}
