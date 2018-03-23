package com.talkweb.xwzcxt.vo;

import java.math.BigDecimal;
import java.util.Date;

public class TErrorFeedbackVo {
    private BigDecimal cFeedbackId;

    private BigDecimal cErrId;

    private String cFeedbackTitle;

    private Long cManageSection;

    private Integer cFeedbackType;

    private String cFeedbackLotno;

    private Long cReceiverUserid;

    private Long cFeedbacker;

    private Date cFeedbackTime;

    private String cIsreceived;

    private Date cReceiverTime;

    private Integer cStatus;

    private String cManageSectionname;

    private Integer cDealType;

    private String cReceiverUsername;

    private String cFeedbackerName;

    private Date cEndTime;

    private Integer cFactdealType;

    private Date cFactdealTime;

    private String cStatusName;

    private String cFeedbackTimeString;

	private String cReceiverTimeString;

    private String cEndTimeString;

    private String cFactdealTimeString;

    public BigDecimal getcFeedbackId() {
        return cFeedbackId;
    }

    public void setcFeedbackId(BigDecimal cFeedbackId) {
        this.cFeedbackId = cFeedbackId;
    }

    public BigDecimal getcErrId() {
        return cErrId;
    }

    public void setcErrId(BigDecimal cErrId) {
        this.cErrId = cErrId;
    }

    public String getcFeedbackTitle() {
        return cFeedbackTitle;
    }

    public void setcFeedbackTitle(String cFeedbackTitle) {
        this.cFeedbackTitle = cFeedbackTitle == null ? null : cFeedbackTitle.trim();
    }

    public Long getcManageSection() {
        return cManageSection;
    }

    public void setcManageSection(Long cManageSection) {
        this.cManageSection = cManageSection;
    }

    public Integer getcFeedbackType() {
        return cFeedbackType;
    }

    public void setcFeedbackType(Integer cFeedbackType) {
        this.cFeedbackType = cFeedbackType;
    }

    public String getcFeedbackLotno() {
        return cFeedbackLotno;
    }

    public void setcFeedbackLotno(String cFeedbackLotno) {
        this.cFeedbackLotno = cFeedbackLotno == null ? null : cFeedbackLotno.trim();
    }

    public Long getcReceiverUserid() {
        return cReceiverUserid;
    }

    public void setcReceiverUserid(Long cReceiverUserid) {
        this.cReceiverUserid = cReceiverUserid;
    }

    public Long getcFeedbacker() {
        return cFeedbacker;
    }

    public void setcFeedbacker(Long cFeedbacker) {
        this.cFeedbacker = cFeedbacker;
    }

    public Date getcFeedbackTime() {
        return cFeedbackTime;
    }

    public void setcFeedbackTime(Date cFeedbackTime) {
        this.cFeedbackTime = cFeedbackTime;
    }

    public String getcIsreceived() {
        return cIsreceived;
    }

    public void setcIsreceived(String cIsreceived) {
        this.cIsreceived = cIsreceived == null ? null : cIsreceived.trim();
    }

    public Date getcReceiverTime() {
        return cReceiverTime;
    }

    public void setcReceiverTime(Date cReceiverTime) {
        this.cReceiverTime = cReceiverTime;
    }

    public Integer getcStatus() {
        return cStatus;
    }

    public void setcStatus(Integer cStatus) {
        this.cStatus = cStatus;
    }

    public String getcManageSectionname() {
        return cManageSectionname;
    }

    public void setcManageSectionname(String cManageSectionname) {
        this.cManageSectionname = cManageSectionname == null ? null : cManageSectionname.trim();
    }

    public Integer getcDealType() {
        return cDealType;
    }

    public void setcDealType(Integer cDealType) {
        this.cDealType = cDealType;
    }

    public String getcReceiverUsername() {
        return cReceiverUsername;
    }

    public void setcReceiverUsername(String cReceiverUsername) {
        this.cReceiverUsername = cReceiverUsername == null ? null : cReceiverUsername.trim();
    }

    public String getcFeedbackerName() {
        return cFeedbackerName;
    }

    public void setcFeedbackerName(String cFeedbackerName) {
        this.cFeedbackerName = cFeedbackerName == null ? null : cFeedbackerName.trim();
    }

    public Date getcEndTime() {
        return cEndTime;
    }

    public void setcEndTime(Date cEndTime) {
        this.cEndTime = cEndTime;
    }

    public Integer getcFactdealType() {
        return cFactdealType;
    }

    public void setcFactdealType(Integer cFactdealType) {
        this.cFactdealType = cFactdealType;
    }

    public Date getcFactdealTime() {
        return cFactdealTime;
    }

    public void setcFactdealTime(Date cFactdealTime) {
        this.cFactdealTime = cFactdealTime;
    }

	public String getcStatusName() {
		return cStatusName;
	}

	public void setcStatusName(String cStatusName) {
		this.cStatusName = cStatusName;
	}

	public String getcFeedbackTimeString() {
		return cFeedbackTimeString;
	}

	public void setcFeedbackTimeString(String cFeedbackTimeString) {
		this.cFeedbackTimeString = cFeedbackTimeString;
	}

	public String getcReceiverTimeString() {
		return cReceiverTimeString;
	}

	public void setcReceiverTimeString(String cReceiverTimeString) {
		this.cReceiverTimeString = cReceiverTimeString;
	}

	public String getcEndTimeString() {
		return cEndTimeString;
	}

	public void setcEndTimeString(String cEndTimeString) {
		this.cEndTimeString = cEndTimeString;
	}

	public String getcFactdealTimeString() {
		return cFactdealTimeString;
	}

	public void setcFactdealTimeString(String cFactdealTimeString) {
		this.cFactdealTimeString = cFactdealTimeString;
	}
}