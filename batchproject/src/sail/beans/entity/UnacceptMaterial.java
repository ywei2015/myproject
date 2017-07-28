package sail.beans.entity;

public class UnacceptMaterial {
	private String billGiveDate;
	private String matCode;
	private String matName;
	private String batchCountTotal;
	private String batchCountY;
	private String batchCountN;
	private String quantity;
	
	public String getBillGiveDate() {
		return billGiveDate;
	}
	public void setBillGiveDate(String billGiveDate) {
		this.billGiveDate = billGiveDate;
	}
	public String getMatCode() {
		return matCode;
	}
	public void setMatCode(String matCode) {
		this.matCode = matCode;
	}
	public String getMatName() {
		return matName;
	}
	public void setMatName(String matName) {
		this.matName = matName;
	}
	public String getBatchCountTotal() {
		return batchCountTotal;
	}
	public void setBatchCountTotal(String batchCountTotal) {
		this.batchCountTotal = batchCountTotal;
	}
	public String getBatchCountY() {
		return batchCountY;
	}
	public void setBatchCountY(String batchCountY) {
		this.batchCountY = batchCountY;
	}
	public String getBatchCountN() {
		return batchCountN;
	}
	public void setBatchCountN(String batchCountN) {
		this.batchCountN = batchCountN;
	}
	
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	@Override
	public String toString() {
		return "UnacceptMaterial [billGiveDate=" + billGiveDate + ", matCode=" + matCode + ", matName=" + matName
				+ ", batchCountTotal=" + batchCountTotal + ", batchCountY=" + batchCountY + ", batchCountN="
				+ batchCountN + ", quantity=" + quantity + "]";
	}
	
	
	
	
}
