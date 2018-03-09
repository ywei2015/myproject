package tw.business.entity.pub;

public class Count {
	
	private Double start;
	private Double end;
	private Integer events;
	private Double mid;
	private Double freq;
	
	public Count(Double start, Double end, Double mid) {
		super();
		this.start = start;
		this.end = end;
		this.mid = mid;
	}
	public Double getStart() {
		return start;
	}
	public Count() {
		super();
	}
	public void setStart(Double start) {
		this.start = start;
	}
	public Double getEnd() {
		return end;
	}
	public void setEnd(Double end) {
		this.end = end;
	}
	public Integer getEvents() {
		return events;
	}
	public void setEvents(Integer events) {
		this.events = events;
	}
	public Double getMid() {
		return mid;
	}
	public void setMid(Double mid) {
		this.mid = mid;
	}
	public Double getFreq() {
		return freq;
	}
	public void setFreq(Double freq) {
		this.freq = freq;
	}
}
