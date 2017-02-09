package sail.beans.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
/**
 * HIGHBAY_成品移库单据表[接口数据]
 * @author cyj
 *
 */
public class BatProductMove implements Serializable{

	private String pid;
	private String transferBill;
	private String date;
	private String factory;
	private String lgortGi;
	private String lgortGr;
	private String operator;
	private String twoStep;
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
	private String giType;
	private String bizType;
	private String operatorGr;
	
	private Set<BatProductMoveDetail> details = new HashSet<BatProductMoveDetail>(0);

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getTransferBill() {
		return transferBill;
	}

	public void setTransferBill(String transferBill) {
		this.transferBill = transferBill;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getFactory() {
		return factory;
	}

	public void setFactory(String factory) {
		this.factory = factory;
	}

	public String getLgortGi() {
		return lgortGi;
	}

	public void setLgortGi(String lgortGi) {
		this.lgortGi = lgortGi;
	}

	public String getLgortGr() {
		return lgortGr;
	}

	public void setLgortGr(String lgortGr) {
		this.lgortGr = lgortGr;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getTwoStep() {
		return twoStep;
	}

	public void setTwoStep(String twoStep) {
		this.twoStep = twoStep;
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

	public String getGiType() {
		return giType;
	}

	public void setGiType(String giType) {
		this.giType = giType;
	}

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	public String getOperatorGr() {
		return operatorGr;
	}

	public void setOperatorGr(String operatorGr) {
		this.operatorGr = operatorGr;
	}

	public Set<BatProductMoveDetail> getDetails() {
		return details;
	}

	public void setDetails(Set<BatProductMoveDetail> details) {
		this.details = details;
	}
}
