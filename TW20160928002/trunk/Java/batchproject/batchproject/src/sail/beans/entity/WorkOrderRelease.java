package sail.beans.entity;

public class WorkOrderRelease {
	private String orderCode; //工单号
	private String productMachine; //机台
	private String cardCode; //牌号
	private String worktime; //班次
	private String workteam; //班组
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public String getProductMachine() {
		return productMachine;
	}
	public void setProductMachine(String productMachine) {
		this.productMachine = productMachine;
	}
	public String getCardCode() {
		return cardCode;
	}
	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}
	public String getWorktime() {
		return worktime;
	}
	public void setWorktime(String worktime) {
		this.worktime = worktime;
	}
	public String getWorkteam() {
		return workteam;
	}
	public void setWorkteam(String workteam) {
		this.workteam = workteam;
	}
	
}
