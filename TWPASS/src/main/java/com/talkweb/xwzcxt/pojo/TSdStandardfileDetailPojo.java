package com.talkweb.xwzcxt.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class TSdStandardfileDetailPojo {
    private String cFiledetailId;

    private String cSfileName;

    private BigDecimal cSortId;

    private String cSortName;

    private String cSfileVersion;

    private String cFwQcbm;

    private String cFwQcbmName;

    private BigDecimal cStatus;

    private Date cStatusTime;

    private BigDecimal cSystag;

    private String cFileUrl;

    private String cCreator;

    private Date cCreatetime;

    private String cModifier;

    private Date cModifytime;

    private BigDecimal cIsdelete;

    private String cStatusTimeString;

    private String cCreatetimeString;

    private String cModifytimeString;

    private String cStatusName;

    private String cSystagName;

    public String getcFiledetailId() {
        return cFiledetailId;
    }

    public void setcFiledetailId(String cFiledetailId) {
        this.cFiledetailId = cFiledetailId == null ? null : cFiledetailId.trim();
    }

    public String getcSfileName() {
        return cSfileName;
    }

    public void setcSfileName(String cSfileName) {
        this.cSfileName = cSfileName == null ? null : cSfileName.trim();
    }

    public BigDecimal getcSortId() {
        return cSortId;
    }

    public void setcSortId(BigDecimal cSortId) {
        this.cSortId = cSortId;
    }

    public String getcSortName() {
        return cSortName;
    }

    public void setcSortName(String cSortName) {
        this.cSortName = cSortName;
    }

    public String getcSfileVersion() {
        return cSfileVersion;
    }

    public void setcSfileVersion(String cSfileVersion) {
        this.cSfileVersion = cSfileVersion == null ? null : cSfileVersion.trim();
    }

    public String getcFwQcbm() {
        return cFwQcbm;
    }

    public void setcFwQcbm(String cFwQcbm) {
        this.cFwQcbm = cFwQcbm == null ? null : cFwQcbm.trim();
    }

    public String getcFwQcbmName() {
        return cFwQcbmName;
    }

    public void setcFwQcbmName(String cFwQcbmName) {
        this.cFwQcbmName = cFwQcbmName;
    }

    public BigDecimal getcStatus() {
        return cStatus;
    }

    public void setcStatus(BigDecimal cStatus) {
        this.cStatus = cStatus;
    }

    public Date getcStatusTime() {
        return cStatusTime;
    }

    public void setcStatusTime(Date cStatusTime) {
        this.cStatusTime = cStatusTime;
    }

    public BigDecimal getcSystag() {
        return cSystag;
    }

    public void setcSystag(BigDecimal cSystag) {
        this.cSystag = cSystag;
    }

    public String getcFileUrl() {
        return cFileUrl;
    }

    public void setcFileUrl(String cFileUrl) {
        this.cFileUrl = cFileUrl == null ? null : cFileUrl.trim();
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

    public BigDecimal getcIsdelete() {
        return cIsdelete;
    }

    public void setcIsdelete(BigDecimal cIsdelete) {
        this.cIsdelete = cIsdelete;
    }

    public String getcStatusTimeString() {
        return cStatusTimeString;
    }

    public void setcStatusTimeString(String cStatusTimeString) {
        this.cStatusTimeString = cStatusTimeString;
    }

    public String getcCreatetimeString() {
        return cCreatetimeString;
    }

    public void setcCreatetimeString(String cCreatetimeString) {
        this.cCreatetimeString = cCreatetimeString;
    }

    public String getcModifytimeString() {
        return cModifytimeString;
    }

    public void setcModifytimeString(String cModifytimeString) {
        this.cModifytimeString = cModifytimeString;
    }

    public String getcStatusName() {
        return cStatusName;
    }

    public void setcStatusName(String cStatusName) {
        this.cStatusName = cStatusName;
    }

    public String getcSystagName() {
        return cSystagName;
    }

    public void setcSystagName(String cSystagName) {
        this.cSystagName = cSystagName;
    }

	@Override
	public String toString() {
		return "TSdStandardfileDetailPojo [cFiledetailId=" + cFiledetailId
				+ ", cSfileName=" + cSfileName + ", cSortId=" + cSortId
				+ ", cSortName=" + cSortName + ", cSfileVersion="
				+ cSfileVersion + ", cFwQcbm=" + cFwQcbm + ", cFwQcbmName="
				+ cFwQcbmName + ", cStatus=" + cStatus + ", cStatusTime="
				+ cStatusTime + ", cSystag=" + cSystag + ", cFileUrl="
				+ cFileUrl + ", cCreator=" + cCreator + ", cCreatetime="
				+ cCreatetime + ", cModifier=" + cModifier + ", cModifytime="
				+ cModifytime + ", cIsdelete=" + cIsdelete
				+ ", cStatusTimeString=" + cStatusTimeString
				+ ", cCreatetimeString=" + cCreatetimeString
				+ ", cModifytimeString=" + cModifytimeString + ", cStatusName="
				+ cStatusName + ", cSystagName=" + cSystagName + "]";
	}
    
}