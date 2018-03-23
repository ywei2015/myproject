package com.talkweb.xwzcxt.vo;

import java.math.BigDecimal;
import java.util.Date;

import com.talkweb.common.AppConstants;

public class TErrorAffixVo {
    private BigDecimal cErrdataId;

    private BigDecimal cErrId;

    private Short cRecordType;

    private Date cRecordTime;

    private String cRecordLotno;

    private BigDecimal cSubmitUserid;

    private String cSubmitUsername;

    private Long cTracefunid;

    private String cIsfile;

    private String cValue;

    private String cIsdelete;

    private String cFileType;

    private String cFilePath;

    public BigDecimal getcErrdataId() {
        return cErrdataId;
    }

    public void setcErrdataId(BigDecimal cErrdataId) {
        this.cErrdataId = cErrdataId;
    }

    public BigDecimal getcErrId() {
        return cErrId;
    }

    public void setcErrId(BigDecimal cErrId) {
        this.cErrId = cErrId;
    }

    public Short getcRecordType() {
        return cRecordType;
    }

    public void setcRecordType(Short cRecordType) {
        this.cRecordType = cRecordType;
    }

    public Date getcRecordTime() {
        return cRecordTime;
    }

    public void setcRecordTime(Date cRecordTime) {
        this.cRecordTime = cRecordTime;
    }

    public String getcRecordLotno() {
        return cRecordLotno;
    }

    public void setcRecordLotno(String cRecordLotno) {
        this.cRecordLotno = cRecordLotno == null ? null : cRecordLotno.trim();
    }

    public BigDecimal getcSubmitUserid() {
        return cSubmitUserid;
    }

    public void setcSubmitUserid(BigDecimal cSubmitUserid) {
        this.cSubmitUserid = cSubmitUserid;
    }

    public String getcSubmitUsername() {
        return cSubmitUsername;
    }

    public void setcSubmitUsername(String cSubmitUsername) {
        this.cSubmitUsername = cSubmitUsername == null ? null : cSubmitUsername.trim();
    }

    public Long getcTracefunid() {
        return cTracefunid;
    }

    public void setcTracefunid(Long cTracefunid) {
        this.cTracefunid = cTracefunid;
    }

    public String getcIsfile() {
        return cIsfile;
    }

    public void setcIsfile(String cIsfile) {
        this.cIsfile = cIsfile == null ? null : cIsfile.trim();
    }

    public String getcValue() {
        return cValue;
    }

    public void setcValue(String cValue) {
        this.cValue = cValue == null ? null : cValue.trim();
    }

    public String getcIsdelete() {
        return cIsdelete;
    }

    public void setcIsdelete(String cIsdelete) {
        this.cIsdelete = cIsdelete == null ? null : cIsdelete.trim();
    }

    public String getcFileType() {
        return cFileType;
    }

    public void setcFileType(String cFileType) {
        this.cFileType = cFileType;
    }

    public String getcFilePath() {
        return cFilePath;
    }

    public void setcFilePath(String cFilePath) {
        this.cFilePath = AppConstants.getFilePathPrefix() + cFilePath;
    }
}