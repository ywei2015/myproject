package com.talkweb.xwzcxt.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class TRulePosition {
	private String cPrid;

	private String cPridCode;

	private String cPridName;

	private String cPidExec;

	private String cPidCheck;

	private String cPidReview;

	private String cPidFeedback1;

	private String cPidFeedback2;

	private String cPidErrFeedback1;

	private String cPidErrFeedback2;

	private String cCreator;

	private Date cCreatetime;

	private String cModifier;

	private Date cModifytime;

	private String cRemark;

	private BigDecimal cFlag;

	public String getcPrid() {
		return cPrid;
	}

	public void setcPrid(String cPrid) {
		this.cPrid = cPrid == null ? null : cPrid.trim();
	}

	public String getcPridCode() {
		return cPridCode;
	}

	public void setcPridCode(String cPridCode) {
		this.cPridCode = cPridCode == null ? null : cPridCode.trim();
	}

	public String getcPridName() {
		return cPridName;
	}

	public void setcPridName(String cPridName) {
		this.cPridName = cPridName == null ? null : cPridName.trim();
	}

	public String getcPidExec() {
		return cPidExec;
	}

	public void setcPidExec(String cPidExec) {
		this.cPidExec = cPidExec == null ? null : cPidExec.trim();
	}

	public String getcPidCheck() {
		return cPidCheck;
	}

	public void setcPidCheck(String cPidCheck) {
		this.cPidCheck = cPidCheck == null ? null : cPidCheck.trim();
	}

	public String getcPidReview() {
		return cPidReview;
	}

	public void setcPidReview(String cPidReview) {
		this.cPidReview = cPidReview == null ? null : cPidReview.trim();
	}

	public String getcPidFeedback1() {
		return cPidFeedback1;
	}

	public void setcPidFeedback1(String cPidFeedback1) {
		this.cPidFeedback1 = cPidFeedback1 == null ? null : cPidFeedback1
				.trim();
	}

	public String getcPidFeedback2() {
		return cPidFeedback2;
	}

	public void setcPidFeedback2(String cPidFeedback2) {
		this.cPidFeedback2 = cPidFeedback2 == null ? null : cPidFeedback2
				.trim();
	}

	public String getcPidErrFeedback1() {
		return cPidErrFeedback1;
	}

	public void setcPidErrFeedback1(String cPidErrFeedback1) {
		this.cPidErrFeedback1 = cPidErrFeedback1 == null ? null
				: cPidErrFeedback1.trim();
	}

	public String getcPidErrFeedback2() {
		return cPidErrFeedback2;
	}

	public void setcPidErrFeedback2(String cPidErrFeedback2) {
		this.cPidErrFeedback2 = cPidErrFeedback2 == null ? null
				: cPidErrFeedback2.trim();
	}

	public String getcCreator() {
		return cCreator;
	}

	public void setcCreator(String cCreator) {
		this.cCreator = cCreator == null ? null : cCreator.trim();
	}

	public Date getcCreatetime() {
		return cCreatetime;
	}

	public void setcCreatetime(Date cCreatetime) {
		this.cCreatetime = cCreatetime;
	}

	public String getcModifier() {
		return cModifier;
	}

	public void setcModifier(String cModifier) {
		this.cModifier = cModifier == null ? null : cModifier.trim();
	}

	public Date getcModifytime() {
		return cModifytime;
	}

	public void setcModifytime(Date cModifytime) {
		this.cModifytime = cModifytime;
	}

	public String getcRemark() {
		return cRemark;
	}

	public void setcRemark(String cRemark) {
		this.cRemark = cRemark == null ? null : cRemark.trim();
	}

	public BigDecimal getcFlag() {
		return cFlag;
	}

	public void setcFlag(BigDecimal cFlag) {
		this.cFlag = cFlag;
	}
}