package com.talkweb.xwzcxt.pojo;

import java.math.BigDecimal;
import java.util.Date;

import com.talkweb.twdpe.core.data.BasePOJO;

public class TSdPublicPojo extends BasePOJO {
    private String cPublicId;

    private String cPublicPid;

    private String cPublicPname;

    private String cPublicCode;

    private BigDecimal cPublicType;

    private BigDecimal cSectionId;

    private String cPublicSname;

    private String cPublicFname;

    private String cRemark;

    private BigDecimal cFlag;

    private String cCreator;

    private Date cCreatetime;

    private String cModifier;

    private Date cModifytime;

    private BigDecimal cIsvaild;

    public String getcPublicId() {
        return cPublicId;
    }

    public void setcPublicId(String cPublicId) {
        this.cPublicId = cPublicId == null ? null : cPublicId.trim();
    }

    public String getCPublicId() {
        return cPublicId;
    }

    public void setCPublicId(String cPublicId) {
        this.cPublicId = cPublicId == null ? null : cPublicId.trim();
    }

    public String getcPublicPname() {
        return cPublicPname;
    }

    public void setcPublicPname(String cPublicPname) {
        this.cPublicPname = cPublicPname;
    }

    public String getcPublicPid() {
        return cPublicPid;
    }

    public void setcPublicPid(String cPublicPid) {
        this.cPublicPid = cPublicPid == null ? null : cPublicPid.trim();
    }

    public String getCPublicPid() {
        return cPublicPid;
    }

    public void setCPublicPid(String cPublicPid) {
        this.cPublicPid = cPublicPid == null ? null : cPublicPid.trim();
    }

    public String getcPublicCode() {
        return cPublicCode;
    }

    public void setcPublicCode(String cPublicCode) {
        this.cPublicCode = cPublicCode == null ? null : cPublicCode.trim();
    }

    public BigDecimal getcPublicType() {
        return cPublicType;
    }

    public void setcPublicType(BigDecimal cPublicType) {
        this.cPublicType = cPublicType;
    }

    public BigDecimal getcSectionId() {
        return cSectionId;
    }

    public void setcSectionId(BigDecimal cSectionId) {
        this.cSectionId = cSectionId;
    }

    public String getcPublicSname() {
        return cPublicSname;
    }

    public void setcPublicSname(String cPublicSname) {
        this.cPublicSname = cPublicSname == null ? null : cPublicSname.trim();
    }

    public String getCPublicSname() {
        return cPublicSname;
    }

    public void setCPublicSname(String cPublicSname) {
        this.cPublicSname = cPublicSname == null ? null : cPublicSname.trim();
    }

    public String getcPublicFname() {
        return cPublicFname;
    }

    public void setcPublicFname(String cPublicFname) {
        this.cPublicFname = cPublicFname == null ? null : cPublicFname.trim();
    }

    public String getCPublicFname() {
        return cPublicFname;
    }

    public void setCPublicFname(String cPublicFname) {
        this.cPublicFname = cPublicFname == null ? null : cPublicFname.trim();
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

    public BigDecimal getcIsvaild() {
        return cIsvaild;
    }

    public void setcIsvaild(BigDecimal cIsvaild) {
        this.cIsvaild = cIsvaild;
    }

	@Override
	public String toString() {
		return "TSdPublicPojo [cPublicId=" + cPublicId + ", cPublicPid="
				+ cPublicPid + ", cPublicPname=" + cPublicPname
				+ ", cPublicCode=" + cPublicCode + ", cPublicType="
				+ cPublicType + ", cSectionId=" + cSectionId
				+ ", cPublicSname=" + cPublicSname + ", cPublicFname="
				+ cPublicFname + ", cRemark=" + cRemark + ", cFlag=" + cFlag
				+ ", cCreator=" + cCreator + ", cCreatetime=" + cCreatetime
				+ ", cModifier=" + cModifier + ", cModifytime=" + cModifytime
				+ ", cIsvaild=" + cIsvaild + "]";
	}
    
}