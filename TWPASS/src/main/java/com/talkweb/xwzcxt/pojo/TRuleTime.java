package com.talkweb.xwzcxt.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class TRuleTime {
	private String cTimeruleId;

	private String cTimeruleCode;

	private String cTimeruleName;

	private String cRemark;

	private BigDecimal cTimeruleType;

	private BigDecimal cOffsetMonth;

	private BigDecimal cOffsetDay;

	private String cOffsetWeek;

	private BigDecimal cOffsetHour;

	private BigDecimal cOffsetMinute;

	private BigDecimal cOffsetWorkgroup;

	private Date cTimeruleBegin;

	private Date cTimeruleEnd;

	private String cCreator;

	private Date cCreatetime;

	private String cModifier;

	private Date cModifytime;

	private Date cFirsttimeExec;

	private Date cLasttimeExec;

	private BigDecimal cFlag;

	private String cDepartment;

	private String cOffsetArray;

	public String getcTimeruleId() {
		return cTimeruleId;
	}

	public void setcTimeruleId(String cTimeruleId) {
		this.cTimeruleId = cTimeruleId == null ? null : cTimeruleId.trim();
	}

	public String getcTimeruleCode() {
		return cTimeruleCode;
	}

	public void setcTimeruleCode(String cTimeruleCode) {
		this.cTimeruleCode = cTimeruleCode == null ? null : cTimeruleCode.trim();
	}

	public String getcTimeruleName() {
		return cTimeruleName;
	}

	public void setcTimeruleName(String cTimeruleName) {
		this.cTimeruleName = cTimeruleName == null ? null : cTimeruleName.trim();
	}

	public String getcRemark() {
		return cRemark;
	}

	public void setcRemark(String cRemark) {
		this.cRemark = cRemark == null ? null : cRemark.trim();
	}

	public BigDecimal getcTimeruleType() {
		return cTimeruleType;
	}

	public void setcTimeruleType(BigDecimal cTimeruleType) {
		this.cTimeruleType = cTimeruleType;
	}

	public BigDecimal getcOffsetMonth() {
		return cOffsetMonth;
	}

	public void setcOffsetMonth(BigDecimal cOffsetMonth) {
		this.cOffsetMonth = cOffsetMonth;
	}

	public BigDecimal getcOffsetDay() {
		return cOffsetDay;
	}

	public void setcOffsetDay(BigDecimal cOffsetDay) {
		this.cOffsetDay = cOffsetDay;
	}

	public String getcOffsetWeek() {
		return cOffsetWeek;
	}

	public void setcOffsetWeek(String cOffsetWeek) {
		this.cOffsetWeek = cOffsetWeek == null ? null : cOffsetWeek.trim();
	}

	public BigDecimal getcOffsetHour() {
		return cOffsetHour;
	}

	public void setcOffsetHour(BigDecimal cOffsetHour) {
		this.cOffsetHour = cOffsetHour;
	}

	public BigDecimal getcOffsetMinute() {
		return cOffsetMinute;
	}

	public void setcOffsetMinute(BigDecimal cOffsetMinute) {
		this.cOffsetMinute = cOffsetMinute;
	}

	public BigDecimal getcOffsetWorkgroup() {
		return cOffsetWorkgroup;
	}

	public void setcOffsetWorkgroup(BigDecimal cOffsetWorkgroup) {
		this.cOffsetWorkgroup = cOffsetWorkgroup;
	}

	public Date getcTimeruleBegin() {
		return cTimeruleBegin;
	}

	public void setcTimeruleBegin(Date cTimeruleBegin) {
		this.cTimeruleBegin = cTimeruleBegin;
	}

	public Date getcTimeruleEnd() {
		return cTimeruleEnd;
	}

	public void setcTimeruleEnd(Date cTimeruleEnd) {
		this.cTimeruleEnd = cTimeruleEnd;
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

	public Date getcFirsttimeExec() {
		return cFirsttimeExec;
	}

	public void setcFirsttimeExec(Date cFirsttimeExec) {
		this.cFirsttimeExec = cFirsttimeExec;
	}

	public Date getcLasttimeExec() {
		return cLasttimeExec;
	}

	public void setcLasttimeExec(Date cLasttimeExec) {
		this.cLasttimeExec = cLasttimeExec;
	}

	public BigDecimal getcFlag() {
		return cFlag;
	}

	public void setcFlag(BigDecimal cFlag) {
		this.cFlag = cFlag;
	}

	public String getcDepartment() {
		return cDepartment;
	}

	public void setcDepartment(String cDepartment) {
		this.cDepartment = cDepartment == null ? null : cDepartment.trim();
	}

	public String getcOffsetArray() {
		return cOffsetArray;
	}

	public void setcOffsetArray(String cOffsetArray) {
		this.cOffsetArray = cOffsetArray == null ? null : cOffsetArray.trim();
	}
}