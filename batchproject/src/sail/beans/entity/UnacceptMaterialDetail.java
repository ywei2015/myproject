package sail.beans.entity;

public class UnacceptMaterialDetail {
	private String billGiveDate;
	private String matCode;
	private String matName;
	private String matBatch;
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
	public String getMatBatch() {
		return matBatch;
	}
	public void setMatBatch(String matBatch) {
		this.matBatch = matBatch;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	@Override
	public String toString() {
		return "UnacceptMaterialDetail [billGiveDate=" + billGiveDate + ", matCode=" + matCode + ", matName=" + matName
				+ ", matBatch=" + matBatch + ", quantity=" + quantity + "]";
	}
	
	
}
