package sail.beans.entity;

public class TopDressFlavor {
	private String matCode;
	private String matName;
	private String matBatch;
	private String quantity;
	private String unit;
	private String createTime;
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
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	@Override
	public String toString() {
		return "TopDressFlavor [matCode=" + matCode + ", matName=" + matName + ", matBatch=" + matBatch + ", quantity="
				+ quantity + ", unit=" + unit + ", createTime=" + createTime + "]";
	}
	
}
