package sail.beans.entity;

import java.io.Serializable;

public class BatWorkOrderOutput implements Serializable{

	private String pid;
	private BatWorkOrder main;
	private String matbatch;
	private String water;
	private String location;
	private String locationname;
	private String stime2;
	private String etime2;
	private String location3;
	private String locationname3;
	private String stime3;
	private String etime3;
	private Double quantity;
	private String unit;
	private String depot;
	private String operateuserid;
	private String remark1;       
	private String remark2;
	private String remark3;
	private String remark4;
	private String remark5;
	private String remark;  //批次状态
	private String sysflag;
	private String creator;
	private String createtime;
	private String lastmodifier;
	private String lastmodifiedtime;
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public BatWorkOrder getMain() {
		return main;
	}
	public void setMain(BatWorkOrder main) {
		this.main = main;
	}
	public String getMatbatch() {
		return matbatch;
	}
	public void setMatbatch(String matbatch) {
		this.matbatch = matbatch;
	}
	public String getWater() {
		return water;
	}
	public void setWater(String water) {
		this.water = water;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getLocationname() {
		return locationname;
	}
	public void setLocationname(String locationname) {
		this.locationname = locationname;
	}
	public String getStime2() {
		return stime2;
	}
	public void setStime2(String stime2) {
		this.stime2 = stime2;
	}
	public String getEtime2() {
		return etime2;
	}
	public void setEtime2(String etime2) {
		this.etime2 = etime2;
	}
	public String getLocation3() {
		return location3;
	}
	public void setLocation3(String location3) {
		this.location3 = location3;
	}
	public String getLocationname3() {
		return locationname3;
	}
	public void setLocationname3(String locationname3) {
		this.locationname3 = locationname3;
	}
	public String getStime3() {
		return stime3;
	}
	public void setStime3(String stime3) {
		this.stime3 = stime3;
	}
	public String getEtime3() {
		return etime3;
	}
	public void setEtime3(String etime3) {
		this.etime3 = etime3;
	}
	public Double getQuantity() {
		return quantity;
	}
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getDepot() {
		return depot;
	}
	public void setDepot(String depot) {
		this.depot = depot;
	}
	public String getOperateuserid() {
		return operateuserid;
	}
	public void setOperateuserid(String operateuserid) {
		this.operateuserid = operateuserid;
	}
	public String getRemark1() {
		return remark1;
	}
	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}
	public String getRemark2() {
		return remark2;
	}
	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}
	public String getRemark3() {
		return remark3;
	}
	public void setRemark3(String remark3) {
		this.remark3 = remark3;
	}
	public String getRemark4() {
		return remark4;
	}
	public void setRemark4(String remark4) {
		this.remark4 = remark4;
	}
	public String getRemark5() {
		return remark5;
	}
	public void setRemark5(String remark5) {
		this.remark5 = remark5;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getSysflag() {
		return sysflag;
	}
	public void setSysflag(String sysflag) {
		this.sysflag = sysflag;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getLastmodifier() {
		return lastmodifier;
	}
	public void setLastmodifier(String lastmodifier) {
		this.lastmodifier = lastmodifier;
	}
	public String getLastmodifiedtime() {
		return lastmodifiedtime;
	}
	public void setLastmodifiedtime(String lastmodifiedtime) {
		this.lastmodifiedtime = lastmodifiedtime;
	}
}
