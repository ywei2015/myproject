package sail.beans.entity;

import java.io.Serializable;

public class CarCode implements Serializable{
	
	private String pid;
	private String matcode;
	private String matname;
	private Double amount;
	private String unit;
	private String unitname;
	private String value2;//1为大件 2为小件,3产出表,4稀释液转动表
	private String value3;
	private String value4;
	private String value5;
	private String value6;
	private String value7;
	private String state; //
    private String factory; //工厂编码
    private String depot;//仓库编码
	private String oldbatch;//小件原大件批次号
	private String stroecode;//供应商编码
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	
	public String getMatcode() {
		return matcode;
	}
	public void setMatcode(String matcode) {
		this.matcode = matcode;
	}
	public String getValue2() {
		return value2;
	}
	public void setValue2(String value2) {
		this.value2 = value2;
	}
	public String getValue3() {
		return value3;
	}
	public void setValue3(String value3) {
		this.value3 = value3;
	}
	public String getValue4() {
		return value4;
	}
	public void setValue4(String value4) {
		this.value4 = value4;
	}
	public String getValue5() {
		return value5;
	}
	public void setValue5(String value5) {
		this.value5 = value5;
	}
	public String getValue6() {
		return value6;
	}
	public void setValue6(String value6) {
		this.value6 = value6;
	}
	public String getValue7() {
		return value7;
	}
	public void setValue7(String value7) {
		this.value7 = value7;
	}
	public String getMatname() {
		return matname;
	}
	public void setMatname(String matname) {
		this.matname = matname;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getUnitname() {
		return unitname;
	}
	public void setUnitname(String unitname) {
		this.unitname = unitname;
	}
	public String getOldbatch() {
		return oldbatch;
	}
	public void setOldbatch(String oldbatch) {
		this.oldbatch = oldbatch;
	}
	public String getStroecode() {
		return stroecode;
	}
	public void setStroecode(String stroecode) {
		this.stroecode = stroecode;
	}
	public String getFactory() {
		return factory;
	}
	public void setFactory(String factory) {
		this.factory = factory;
	}
	public String getDepot() {
		return depot;
	}
	public void setDepot(String depot) {
		this.depot = depot;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	
	
}
