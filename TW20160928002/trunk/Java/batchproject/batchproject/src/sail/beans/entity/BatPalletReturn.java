package sail.beans.entity;

import java.io.Serializable;

/**
 * 托盘信息
 * */
public class BatPalletReturn implements Serializable{

	private String pid;
	private String adjustno; //托盘退料单号（托盘号+#+日期）
	private String factory;
	private String depot;
	private String workshop;
	private String workteam;
	private String worktime;
	private String operateuserid;
	private String operateusername;
	private String operatetime;
	private String state;   //记录状态标志（0-未转储，10-已组批，20-已入库，30-已出库）
	private String remark1;      
	private String remark2;       
	private String remark3;   
	private String remark4;   
	private String remark5;  
	private String remark;  
	private String sysflag;
	private String creator;
	private String createtime;
	private String lastmodifier;
	private String lastmodifiedtime;
	private String recombine_time; //组批转储时间
	private String indepot_time;     //入库转储时间
	private String outdepot_time;     //出库转储时间
	private String isrepair;//是否重复 
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getAdjustno() {
		return adjustno;
	}
	public void setAdjustno(String adjustno) {
		this.adjustno = adjustno;
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
	public String getWorkshop() {
		return workshop;
	}
	public void setWorkshop(String workshop) {
		this.workshop = workshop;
	}
	public String getWorkteam() {
		return workteam;
	}
	public void setWorkteam(String workteam) {
		this.workteam = workteam;
	}
	public String getWorktime() {
		return worktime;
	}
	public void setWorktime(String worktime) {
		this.worktime = worktime;
	}
	public String getOperateuserid() {
		return operateuserid;
	}
	public void setOperateuserid(String operateuserid) {
		this.operateuserid = operateuserid;
	}
	public String getOperateusername() {
		return operateusername;
	}
	public void setOperateusername(String operateusername) {
		this.operateusername = operateusername;
	}
	public String getOperatetime() {
		return operatetime;
	}
	public void setOperatetime(String operatetime) {
		this.operatetime = operatetime;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
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
	public String getRecombine_time() {
		return recombine_time;
	}
	public void setRecombine_time(String recombine_time) {
		this.recombine_time = recombine_time;
	}
	public String getIndepot_time() {
		return indepot_time;
	}
	public void setIndepot_time(String indepot_time) {
		this.indepot_time = indepot_time;
	}
	public String getOutdepot_time() {
		return outdepot_time;
	}
	public void setOutdepot_time(String outdepot_time) {
		this.outdepot_time = outdepot_time;
	}
	public String getIsrepair() {
		return isrepair;
	}
	public void setIsrepair(String isrepair) {
		this.isrepair = isrepair;
	}
	
	
}
