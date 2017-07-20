package sail.beans.entity;

import java.io.Serializable;
/**
 * 批次追溯
 * */
public class BatchRetrospect  implements Serializable{

	private String pid;
	private String codetype;    //码类别
	private String codetypename;
	private String brandcode;    //牌号名称  
	private String batchname;    //牌号名称
	private String producetime;      //贴码时间
	private String ucode;        
	private String lcode;
	private String worktime;
	private String workteam;
	private String process;
	private String operator;
	private String sysflag;
	private String creator;
	private String createtime;
	private String lastmodifier;
	private String lastmodifiedtime;
	private String isrepeat;
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getCodetype() {
		return codetype;
	}
	public void setCodetype(String codetype) {
		this.codetype = codetype;
	}
	public String getBrandcode() {
		return brandcode;
	}
	public void setBrandcode(String brandcode) {
		this.brandcode = brandcode;
	}
	public String getBatchname() {
		return batchname;
	}
	public void setBatchname(String batchname) {
		this.batchname = batchname;
	}
	public String getProducetime() {
		return producetime;
	}
	public void setProducetime(String producetime) {
		this.producetime = producetime;
	}
	public String getUcode() {
		return ucode;
	}
	public void setUcode(String ucode) {
		this.ucode = ucode;
	}
	public String getLcode() {
		return lcode;
	}
	public void setLcode(String lcode) {
		this.lcode = lcode;
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
	public String getProcess() {
		return process;
	}
	public void setProcess(String process) {
		this.process = process;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
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
	public String getIsrepeat() {
		return isrepeat;
	}
	public void setIsrepeat(String isrepeat) {
		this.isrepeat = isrepeat;
	}
	public String getCodetypename() {
		return codetypename;
	}
	public void setCodetypename(String codetypename) {
		this.codetypename = codetypename;
	}
	
}
