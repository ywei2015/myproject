package com.talkweb.xwzcxt.pojo;

import java.math.BigDecimal;
import java.util.Date;

import com.talkweb.twdpe.core.data.BasePOJO;

public class TSdTempletScancodePojo extends BasePOJO {
    private String cId;

    private String cTempletId;

    private String cBasecodeId;

    private String cBasecodeName;

    private String cCreator;

    private Date cCreatetime;

    private String cModifier;

    private Date cModifytime;

    private BigDecimal cTaskId;

    private String cType;

    private String cIsdelete;

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId == null ? null : cId.trim();
    }

    public String getcTempletId() {
        return cTempletId;
    }

    public void setcTempletId(String cTempletId) {
        this.cTempletId = cTempletId == null ? null : cTempletId.trim();
    }

    public String getcBasecodeId() {
        return cBasecodeId;
    }

    public void setcBasecodeId(String cBasecodeId) {
        this.cBasecodeId = cBasecodeId == null ? null : cBasecodeId.trim();
    }

    public String getcBasecodeName() {
        return cBasecodeName;
    }

    public void setcBasecodeName(String cBasecodeName) {
        this.cBasecodeName = cBasecodeName;
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

    public BigDecimal getcTaskId() {
        return cTaskId;
    }

    public void setcTaskId(BigDecimal cTaskId) {
        this.cTaskId = cTaskId;
    }

    public String getcType() {
        return cType;
    }

    public void setcType(String cType) {
        this.cType = cType == null ? null : cType.trim();
    }

    public String getcIsdelete() {
        return cIsdelete;
    }

    public void setcIsdelete(String cIsdelete) {
        this.cIsdelete = cIsdelete == null ? null : cIsdelete.trim();
    }

	@Override
	public String toString() {
		return "TSdTempletScancodePojo [cId=" + cId + ", cTempletId="
				+ cTempletId + ", cBasecodeId=" + cBasecodeId
				+ ", cBasecodeName=" + cBasecodeName + ", cCreator=" + cCreator
				+ ", cCreatetime=" + cCreatetime + ", cModifier=" + cModifier
				+ ", cModifytime=" + cModifytime + ", cTaskId=" + cTaskId
				+ ", cType=" + cType + ", cIsdelete=" + cIsdelete + "]";
	}
    
}