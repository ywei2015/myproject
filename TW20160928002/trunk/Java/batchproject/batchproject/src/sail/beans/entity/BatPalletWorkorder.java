package sail.beans.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
/**
 * 批次管理_JB_烟用材料配盘工单及接口数据
 * @author cyj
 * 2017-01-18
 */
public class BatPalletWorkorder implements Serializable{

	private String pid;
	private String palletWorkorder;
	private String mesjbWorkorder;
	private String palletSid;
	private String produceDate;
	private String factory;
	private String workarea;
	private String worktime;
	private String workteam;
	private String workorderState;
	private String remark1;
	private String remark2;
	private String remark3;
	private String remark4;
	private String remark5;
	private String remark;
	private String sysFlag;
	private String creator;
	private String createTime;
	private String lastmodifier;
	private String lastmodifiedTime;
	private Set<BatPalletForm> details = new HashSet<BatPalletForm>(0);
	
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getPalletWorkorder() {
		return palletWorkorder;
	}
	public void setPalletWorkorder(String palletWorkorder) {
		this.palletWorkorder = palletWorkorder;
	}
	public String getMesjbWorkorder() {
		return mesjbWorkorder;
	}
	public void setMesjbWorkorder(String mesjbWorkorder) {
		this.mesjbWorkorder = mesjbWorkorder;
	}
	public String getPalletSid() {
		return palletSid;
	}
	public void setPalletSid(String palletSid) {
		this.palletSid = palletSid;
	}
	public String getProduceDate() {
		return produceDate;
	}
	public void setProduceDate(String produceDate) {
		this.produceDate = produceDate;
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
	public String getWorkorderState() {
		return workorderState;
	}
	public void setWorkorderState(String workorderState) {
		this.workorderState = workorderState;
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
	public String getSysFlag() {
		return sysFlag;
	}
	public void setSysFlag(String sysFlag) {
		this.sysFlag = sysFlag;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getLastmodifier() {
		return lastmodifier;
	}
	public void setLastmodifier(String lastmodifier) {
		this.lastmodifier = lastmodifier;
	}
	public String getLastmodifiedTime() {
		return lastmodifiedTime;
	}
	public void setLastmodifiedTime(String lastmodifiedTime) {
		this.lastmodifiedTime = lastmodifiedTime;
	}
	public Set<BatPalletForm> getDetails() {
		return details;
	}
	public void setDetails(Set<BatPalletForm> details) {
		this.details = details;
	}
	
}
