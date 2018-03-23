package com.talkweb.xwzcxt.vo;

import java.math.BigDecimal;
import java.util.Date;

public class TStdtaskplanSeriesVo {
    private BigDecimal cTsId;

    private String cTasktempletId;

    private String cTasktempletName;

    private String cTimeruleId;

    private Integer cTaskType;

    private String cTaskTypeName;

    private String cStateName;

	public String getcStateName() {
		return cStateName;
	}

	public void setcStateName(String cStateName) {
		this.cStateName = cStateName;
	}

	public String getcTasktempletName() {
		return cTasktempletName;
	}

	public void setcTasktempletName(String cTasktempletName) {
		this.cTasktempletName = cTasktempletName;
	}

	public String getcTaskTypeName() {
		return cTaskTypeName;
	}

	public void setcTaskTypeName(String cTaskTypeName) {
		this.cTaskTypeName = cTaskTypeName;
	}

	private Date cPlandownTime;

    private Date cStartTime;

    private Date cEndTime;

    private Double cChkHours;

    private Date cEvaluateTime;

    private BigDecimal cExecUserid;

    private String cExecUserName;

    public String getcExecUserName() {
		return cExecUserName;
	}

	public void setcExecUserName(String cExecUserName) {
		this.cExecUserName = cExecUserName;
	}

	private BigDecimal cChkUserid;

	private String cChkUserName;

    private BigDecimal cEvaluateUserid;

    private String cEvaluateUserName;

    private BigDecimal cOkfbUserid;

    private String cOkfbUserName;

    private String cOkfbUlist;

    private String cOkfbUlistName;

    private BigDecimal cNgfbUserid;

    private String cNgfbUserName;

    private String cNgfbUlist;

    private String cNgfbUlistName;

    public String getcChkUserName() {
		return cChkUserName;
	}

	public void setcChkUserName(String cChkUserName) {
		this.cChkUserName = cChkUserName;
	}

	public String getcEvaluateUserName() {
		return cEvaluateUserName;
	}

	public void setcEvaluateUserName(String cEvaluateUserName) {
		this.cEvaluateUserName = cEvaluateUserName;
	}

	public String getcOkfbUserName() {
		return cOkfbUserName;
	}

	public void setcOkfbUserName(String cOkfbUserName) {
		this.cOkfbUserName = cOkfbUserName;
	}

	public String getcOkfbUlistName() {
		return cOkfbUlistName;
	}

	public void setcOkfbUlistName(String cOkfbUlistName) {
		this.cOkfbUlistName = cOkfbUlistName;
	}

	public String getcNgfbUserName() {
		return cNgfbUserName;
	}

	public void setcNgfbUserName(String cNgfbUserName) {
		this.cNgfbUserName = cNgfbUserName;
	}

	public String getcNgfbUlistName() {
		return cNgfbUlistName;
	}

	public void setcNgfbUlistName(String cNgfbUlistName) {
		this.cNgfbUlistName = cNgfbUlistName;
	}

	private Date cDecodeTime;

    private Date cDoneTime;

    private BigDecimal cTaskId;

    private String cTaskName;

    private Integer cState;

    public BigDecimal getcTsId() {
        return cTsId;
    }

    public void setcTsId(BigDecimal cTsId) {
        this.cTsId = cTsId;
    }

    public String getcTasktempletId() {
        return cTasktempletId;
    }

    public void setcTasktempletId(String cTasktempletId) {
        this.cTasktempletId = cTasktempletId == null ? null : cTasktempletId.trim();
    }

    public String getcTimeruleId() {
        return cTimeruleId;
    }

    public void setcTimeruleId(String cTimeruleId) {
        this.cTimeruleId = cTimeruleId == null ? null : cTimeruleId.trim();
    }

    public Integer getcTaskType() {
        return cTaskType;
    }

    public void setcTaskType(Integer cTaskType) {
        this.cTaskType = cTaskType;
    }

    public Date getcPlandownTime() {
        return cPlandownTime;
    }

    public void setcPlandownTime(Date cPlandownTime) {
        this.cPlandownTime = cPlandownTime;
    }

    public Date getcStartTime() {
        return cStartTime;
    }

    public void setcStartTime(Date cStartTime) {
        this.cStartTime = cStartTime;
    }

    public Date getcEndTime() {
        return cEndTime;
    }

    public void setcEndTime(Date cEndTime) {
        this.cEndTime = cEndTime;
    }

    public Double getcChkHours() {
        return cChkHours;
    }

    public void setcChkHours(Double cChkHours) {
        this.cChkHours = cChkHours;
    }

    public Date getcEvaluateTime() {
        return cEvaluateTime;
    }

    public void setcEvaluateTime(Date cEvaluateTime) {
        this.cEvaluateTime = cEvaluateTime;
    }

    public BigDecimal getcExecUserid() {
        return cExecUserid;
    }

    public void setcExecUserid(BigDecimal cExecUserid) {
        this.cExecUserid = cExecUserid;
    }

    public BigDecimal getcChkUserid() {
        return cChkUserid;
    }

    public void setcChkUserid(BigDecimal cChkUserid) {
        this.cChkUserid = cChkUserid;
    }

    public BigDecimal getcEvaluateUserid() {
        return cEvaluateUserid;
    }

    public void setcEvaluateUserid(BigDecimal cEvaluateUserid) {
        this.cEvaluateUserid = cEvaluateUserid;
    }

    public BigDecimal getcOkfbUserid() {
        return cOkfbUserid;
    }

    public void setcOkfbUserid(BigDecimal cOkfbUserid) {
        this.cOkfbUserid = cOkfbUserid;
    }

    public String getcOkfbUlist() {
        return cOkfbUlist;
    }

    public void setcOkfbUlist(String cOkfbUlist) {
        this.cOkfbUlist = cOkfbUlist == null ? null : cOkfbUlist.trim();
    }

    public BigDecimal getcNgfbUserid() {
        return cNgfbUserid;
    }

    public void setcNgfbUserid(BigDecimal cNgfbUserid) {
        this.cNgfbUserid = cNgfbUserid;
    }

    public String getcNgfbUlist() {
        return cNgfbUlist;
    }

    public void setcNgfbUlist(String cNgfbUlist) {
        this.cNgfbUlist = cNgfbUlist == null ? null : cNgfbUlist.trim();
    }

    public Date getcDecodeTime() {
        return cDecodeTime;
    }

    public void setcDecodeTime(Date cDecodeTime) {
        this.cDecodeTime = cDecodeTime;
    }

    public Date getcDoneTime() {
        return cDoneTime;
    }

    public void setcDoneTime(Date cDoneTime) {
        this.cDoneTime = cDoneTime;
    }

    public BigDecimal getcTaskId() {
        return cTaskId;
    }

    public void setcTaskId(BigDecimal cTaskId) {
        this.cTaskId = cTaskId;
    }

    public Integer getcState() {
        return cState;
    }

    public void setcState(Integer cState) {
        this.cState = cState;
    }

	public String getcTaskName() {
		return cTaskName;
	}

	public void setcTaskName(String cTaskName) {
		this.cTaskName = cTaskName;
	}
}