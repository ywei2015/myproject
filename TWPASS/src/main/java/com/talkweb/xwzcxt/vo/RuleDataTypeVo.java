package com.talkweb.xwzcxt.vo;

public class RuleDataTypeVo {
    private Long cTracefunId;

    private String cTracefunName;

    private String cIsfile;

    private String cFileFormat;

    private String cCreator;

    private String cCreateTime;

    private String cLastModifier;

    private String cLastModifiedTime;

    private String cRemark;

    private String cIsdelete;

    public Long getcTracefunId() {
        return cTracefunId;
    }

    public void setcTracefunId(Long cTracefunId) {
        this.cTracefunId = cTracefunId;
    }

    public String getcTracefunName() {
        return cTracefunName;
    }

    public void setcTracefunName(String cTracefunName) {
        this.cTracefunName = cTracefunName == null ? null : cTracefunName.trim();
    }

    public String getcIsfile() {
        return cIsfile;
    }

    public void setcIsfile(String cIsfile) {
        this.cIsfile = cIsfile == null ? null : cIsfile.trim();
    }

    public String getcFileFormat() {
        return cFileFormat;
    }

    public void setcFileFormat(String cFileFormat) {
        this.cFileFormat = cFileFormat == null ? null : cFileFormat.trim();
    }

    public String getcCreator() {
        return cCreator;
    }

    public void setcCreator(String cCreator) {
        this.cCreator = cCreator == null ? null : cCreator.trim();
    }

    public String getcCreateTime() {
        return cCreateTime;
    }

    public void setcCreateTime(String cCreateTime) {
        this.cCreateTime = cCreateTime == null ? null : cCreateTime.trim();
    }

    public String getcLastModifier() {
        return cLastModifier;
    }

    public void setcLastModifier(String cLastModifier) {
        this.cLastModifier = cLastModifier == null ? null : cLastModifier.trim();
    }

    public String getcLastModifiedTime() {
        return cLastModifiedTime;
    }

    public void setcLastModifiedTime(String cLastModifiedTime) {
        this.cLastModifiedTime = cLastModifiedTime == null ? null : cLastModifiedTime.trim();
    }

    public String getcRemark() {
        return cRemark;
    }

    public void setcRemark(String cRemark) {
        this.cRemark = cRemark == null ? null : cRemark.trim();
    }

    public String getcIsdelete() {
        return cIsdelete;
    }

    public void setcIsdelete(String cIsdelete) {
        this.cIsdelete = cIsdelete == null ? null : cIsdelete.trim();
    }
}