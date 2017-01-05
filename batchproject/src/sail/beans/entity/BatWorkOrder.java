package sail.beans.entity;

import java.io.Serializable;

public class BatWorkOrder implements Serializable{

	private String pid;
	private String workordercode;
	private String workordertype;
	private String producedate;
	private String factory;
	private String workarea;
	private String matcode;
	private String matname;
	private String endbrand;
	private String recipebver;
	private String recipeever;
	private String flavorver;
	private String craftver;
	private String craftsver;
	private String planstarttime;
	private String planendtime;
	private String actualstarttime;
	private String actualendtime;
	private String worktime;
	private String workteam;
	private Double planquantity;
	private Double actualquantity;
	private String unit;
	private String tankno;
	private String zsl;
	private String session;
	private String process;
	private String opuserid;
	private String workorderstate; //1未完成；2完成
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
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getWorkordercode() {
		return workordercode;
	}
	public void setWorkordercode(String workordercode) {
		this.workordercode = workordercode;
	}
	public String getWorkordertype() {
		return workordertype;
	}
	public void setWorkordertype(String workordertype) {
		this.workordertype = workordertype;
	}
	public String getProducedate() {
		return producedate;
	}
	public void setProducedate(String producedate) {
		this.producedate = producedate;
	}
	public String getFactory() {
		return factory;
	}
	public void setFactory(String factory) {
		this.factory = factory;
	}
	public String getWorkarea() {
		return workarea;
	}
	public void setWorkarea(String workarea) {
		this.workarea = workarea;
	}
	public String getMatcode() {
		return matcode;
	}
	public void setMatcode(String matcode) {
		this.matcode = matcode;
	}
	public String getMatname() {
		return matname;
	}
	public void setMatname(String matname) {
		this.matname = matname;
	}
	public String getEndbrand() {
		return endbrand;
	}
	public void setEndbrand(String endbrand) {
		this.endbrand = endbrand;
	}
	public String getRecipebver() {
		return recipebver;
	}
	public void setRecipebver(String recipebver) {
		this.recipebver = recipebver;
	}
	public String getRecipeever() {
		return recipeever;
	}
	public void setRecipeever(String recipeever) {
		this.recipeever = recipeever;
	}
	public String getFlavorver() {
		return flavorver;
	}
	public void setFlavorver(String flavorver) {
		this.flavorver = flavorver;
	}
	public String getCraftver() {
		return craftver;
	}
	public void setCraftver(String craftver) {
		this.craftver = craftver;
	}
	public String getCraftsver() {
		return craftsver;
	}
	public void setCraftsver(String craftsver) {
		this.craftsver = craftsver;
	}
	public String getPlanstarttime() {
		return planstarttime;
	}
	public void setPlanstarttime(String planstarttime) {
		this.planstarttime = planstarttime;
	}
	public String getPlanendtime() {
		return planendtime;
	}
	public void setPlanendtime(String planendtime) {
		this.planendtime = planendtime;
	}
	public String getActualstarttime() {
		return actualstarttime;
	}
	public void setActualstarttime(String actualstarttime) {
		this.actualstarttime = actualstarttime;
	}
	public String getActualendtime() {
		return actualendtime;
	}
	public void setActualendtime(String actualendtime) {
		this.actualendtime = actualendtime;
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
	
	
	public Double getPlanquantity() {
		return planquantity;
	}
	public void setPlanquantity(Double planquantity) {
		this.planquantity = planquantity;
	}
	public Double getActualquantity() {
		return actualquantity;
	}
	public void setActualquantity(Double actualquantity) {
		this.actualquantity = actualquantity;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getTankno() {
		return tankno;
	}
	public void setTankno(String tankno) {
		this.tankno = tankno;
	}
	public String getZsl() {
		return zsl;
	}
	public void setZsl(String zsl) {
		this.zsl = zsl;
	}
	public String getSession() {
		return session;
	}
	public void setSession(String session) {
		this.session = session;
	}
	public String getProcess() {
		return process;
	}
	public void setProcess(String process) {
		this.process = process;
	}
	public String getOpuserid() {
		return opuserid;
	}
	public void setOpuserid(String opuserid) {
		this.opuserid = opuserid;
	}
	public String getWorkorderstate() {
		return workorderstate;
	}
	public void setWorkorderstate(String workorderstate) {
		this.workorderstate = workorderstate;
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
