package sail.beans.entity;

import java.io.Serializable;
/**
 * 批次管理_QA_ZS制丝生产工艺质量物理检验参数结果信息
 * @author cyj
 * 2017-05-05
 */
public class BatZsQaParamResult implements Serializable{

	private String pid;
//	private String qasamplePid;
	private BatZsQaSample qasamplePid;
	private String paramId;
	private String paramName;
	private Double normalValue;
	private Double usl;
	private Double lsl;
	private Double ucl;
	private Double lcl;
	private Double ubl;
	private Double lbl;
	private Double max;
	private Double min;
	private Double average;
	private Double sd;
	private Double cpk;
	private Double overCount;
	private Double passPercent;
	private String unit;
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
	
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public BatZsQaSample getQasamplePid() {
		return qasamplePid;
	}
	public void setQasamplePid(BatZsQaSample qasamplePid) {
		this.qasamplePid = qasamplePid;
	}
	public String getParamId() {
		return paramId;
	}
	public void setParamId(String paramId) {
		this.paramId = paramId;
	}
	public String getParamName() {
		return paramName;
	}
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
	public Double getNormalValue() {
		return normalValue;
	}
	public void setNormalValue(Double normalValue) {
		this.normalValue = normalValue;
	}
	public Double getUsl() {
		return usl;
	}
	public void setUsl(Double usl) {
		this.usl = usl;
	}
	public Double getLsl() {
		return lsl;
	}
	public void setLsl(Double lsl) {
		this.lsl = lsl;
	}
	public Double getUcl() {
		return ucl;
	}
	public void setUcl(Double ucl) {
		this.ucl = ucl;
	}
	public Double getLcl() {
		return lcl;
	}
	public void setLcl(Double lcl) {
		this.lcl = lcl;
	}
	public Double getUbl() {
		return ubl;
	}
	public void setUbl(Double ubl) {
		this.ubl = ubl;
	}
	public Double getLbl() {
		return lbl;
	}
	public void setLbl(Double lbl) {
		this.lbl = lbl;
	}
	public Double getMax() {
		return max;
	}
	public void setMax(Double max) {
		this.max = max;
	}
	public Double getMin() {
		return min;
	}
	public void setMin(Double min) {
		this.min = min;
	}
	public Double getAverage() {
		return average;
	}
	public void setAverage(Double average) {
		this.average = average;
	}
	public Double getSd() {
		return sd;
	}
	public void setSd(Double sd) {
		this.sd = sd;
	}
	public Double getCpk() {
		return cpk;
	}
	public void setCpk(Double cpk) {
		this.cpk = cpk;
	}
	public Double getOverCount() {
		return overCount;
	}
	public void setOverCount(Double overCount) {
		this.overCount = overCount;
	}
	public Double getPassPercent() {
		return passPercent;
	}
	public void setPassPercent(Double passPercent) {
		this.passPercent = passPercent;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
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
	
}
