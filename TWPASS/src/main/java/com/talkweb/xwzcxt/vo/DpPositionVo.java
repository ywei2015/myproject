package com.talkweb.xwzcxt.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class DpPositionVo {
	private Long positionid;

	private List<DpPositionVo> positionChilds;

	public List<DpPositionVo> getPositionChilds() {
		return positionChilds;
	}

	public void setPositionChilds(List<DpPositionVo> positionChilds) {
		this.positionChilds = positionChilds;
	}

	private String posicode;

	private String posiname;

	private Short posilevel;

	private Long manaposi;

	private Long dutyid;

	private Long orgid;

	private String positionseq;

	private String positype;

	private Date createtime;

	private Date lastupdate;

	private Long updator;

	private Date startdate;

	private Date enddate;

	private BigDecimal status;

	private BigDecimal isdelete;

	private String upcode;

	private String backup1;

	public String getUpcode() {
		return upcode;
	}

	public void setUpcode(String upcode) {
		this.upcode = upcode;
	}

	public String getBackup1() {
		return backup1;
	}

	public void setBackup1(String backup1) {
		this.backup1 = backup1;
	}

	public Long getPositionid() {
		return positionid;
	}

	public void setPositionid(Long positionid) {
		this.positionid = positionid;
	}

	public String getPosicode() {
		return posicode;
	}

	public void setPosicode(String posicode) {
		this.posicode = posicode == null ? null : posicode.trim();
	}

	public String getPosiname() {
		return posiname;
	}

	public void setPosiname(String posiname) {
		this.posiname = posiname == null ? null : posiname.trim();
	}

	public Short getPosilevel() {
		return posilevel;
	}

	public void setPosilevel(Short posilevel) {
		this.posilevel = posilevel;
	}

	public Long getManaposi() {
		return manaposi;
	}

	public void setManaposi(Long manaposi) {
		this.manaposi = manaposi;
	}

	public Long getDutyid() {
		return dutyid;
	}

	public void setDutyid(Long dutyid) {
		this.dutyid = dutyid;
	}

	public Long getOrgid() {
		return orgid;
	}

	public void setOrgid(Long orgid) {
		this.orgid = orgid;
	}

	public String getPositionseq() {
		return positionseq;
	}

	public void setPositionseq(String positionseq) {
		this.positionseq = positionseq == null ? null : positionseq.trim();
	}

	public String getPositype() {
		return positype;
	}

	public void setPositype(String positype) {
		this.positype = positype == null ? null : positype.trim();
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getLastupdate() {
		return lastupdate;
	}

	public void setLastupdate(Date lastupdate) {
		this.lastupdate = lastupdate;
	}

	public Long getUpdator() {
		return updator;
	}

	public void setUpdator(Long updator) {
		this.updator = updator;
	}

	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public Date getEnddate() {
		return enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	public BigDecimal getStatus() {
		return status;
	}

	public void setStatus(BigDecimal status) {
		this.status = status;
	}

	public BigDecimal getIsdelete() {
		return isdelete;
	}

	public void setIsdelete(BigDecimal isdelete) {
		this.isdelete = isdelete;
	}

}