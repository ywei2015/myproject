package com.talkweb.xwzcxt.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class TPanelPojo {
    private String cPanelId;

    private String cPanelName;

    private BigDecimal cPanelType;

    private String cExId;

    private BigDecimal cUserId;

    private String cUserName;

    private BigDecimal cUsertype;

    private String cUsertypeName;

    private String cIsread;

    private String cIsreadName;

    private Date cReadtime;

    private String cReadtimeString;

    public String getcPanelId() {
        return cPanelId;
    }

    public void setcPanelId(String cPanelId) {
        this.cPanelId = cPanelId == null ? null : cPanelId.trim();
    }

    public String getcPanelName() {
        return cPanelName;
    }

    public void setcPanelName(String cPanelName) {
        this.cPanelName = cPanelName == null ? null : cPanelName.trim();
    }

    public BigDecimal getcPanelType() {
        return cPanelType;
    }

    public void setcPanelType(BigDecimal cPanelType) {
        this.cPanelType = cPanelType;
    }

    public String getcExId() {
        return cExId;
    }

    public void setcExId(String cExId) {
        this.cExId = cExId == null ? null : cExId.trim();
    }

    public BigDecimal getcUserId() {
        return cUserId;
    }

    public void setcUserId(BigDecimal cUserId) {
        this.cUserId = cUserId;
    }

    public String getcUserName() {
        return cUserName;
    }

    public void setcUserName(String cUserName) {
        this.cUserName = cUserName;
    }

    public BigDecimal getcUsertype() {
        return cUsertype;
    }

    public void setcUsertype(BigDecimal cUsertype) {
        this.cUsertype = cUsertype;
    }

    public String getcUsertypeName() {
        return cUsertypeName;
    }

    public void setcUsertypeName(String cUsertypeName) {
        this.cUsertypeName = cUsertypeName;
    }

    public String getcIsread() {
        return cIsread;
    }

    public void setcIsread(String cIsread) {
        this.cIsread = cIsread == null ? null : cIsread.trim();
    }

    public String getcIsreadName() {
        return cIsreadName;
    }

    public void setcIsreadName(String cIsreadName) {
        this.cIsreadName = cIsreadName;
    }

    public Date getcReadtime() {
        return cReadtime;
    }

    public void setcReadtime(Date cReadtime) {
        this.cReadtime = cReadtime;
    }

    public String getcReadtimeString() {
        return cReadtimeString;
    }

    public void setcReadtimeString(String cReadtimeString) {
        this.cReadtimeString = cReadtimeString;
    }
}