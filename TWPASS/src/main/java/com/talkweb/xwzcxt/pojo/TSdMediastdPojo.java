package com.talkweb.xwzcxt.pojo;

import java.math.BigDecimal;
import java.util.Date;

import com.talkweb.twdpe.core.data.BasePOJO;

public class TSdMediastdPojo extends BasePOJO {
    private String cId;

    private String cActnodeId;

    private String cTempletId;

    private String cStepId;

    private String cSdfileId;

    private String cSdfileType;

    private String cSdfilePath;

    private BigDecimal cMediastdType;

    private String cCreator;

    private Date cCreatetime;

    private String cModifier;

    private Date cModifytime;

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId == null ? null : cId.trim();
    }

    public String getcActnodeId() {
        return cActnodeId;
    }

    public void setcActnodeId(String cActnodeId) {
        this.cActnodeId = cActnodeId == null ? null : cActnodeId.trim();
    }

    public String getcTempletId() {
        return cTempletId;
    }

    public void setcTempletId(String cTempletId) {
        this.cTempletId = cTempletId == null ? null : cTempletId.trim();
    }

    public String getcStepId() {
        return cStepId;
    }

    public void setcStepId(String cStepId) {
        this.cStepId = cStepId == null ? null : cStepId.trim();
    }

    public String getcSdfileId() {
        return cSdfileId;
    }

    public void setcSdfileId(String cSdfileId) {
        this.cSdfileId = cSdfileId == null ? null : cSdfileId.trim();
    }

    public String getcSdfileType() {
        return cSdfileType;
    }

    public void setcSdfileType(String cSdfileType) {
        this.cSdfileType = cSdfileType;
    }

    public String getcSdfilePath() {
        return cSdfilePath;
    }

    public void setcSdfilePath(String cSdfilePath) {
        this.cSdfilePath = cSdfilePath;
    }

    public BigDecimal getcMediastdType() {
        return cMediastdType;
    }

    public void setcMediastdType(BigDecimal cMediastdType) {
        this.cMediastdType = cMediastdType;
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

	@Override
	public String toString() {
		return "TSdMediastdPojo [cId=" + cId + ", cActnodeId=" + cActnodeId
				+ ", cTempletId=" + cTempletId + ", cStepId=" + cStepId
				+ ", cSdfileId=" + cSdfileId + ", cSdfileType=" + cSdfileType
				+ ", cSdfilePath=" + cSdfilePath + ", cMediastdType="
				+ cMediastdType + ", cCreator=" + cCreator + ", cCreatetime="
				+ cCreatetime + ", cModifier=" + cModifier + ", cModifytime="
				+ cModifytime + "]";
	}
    
}