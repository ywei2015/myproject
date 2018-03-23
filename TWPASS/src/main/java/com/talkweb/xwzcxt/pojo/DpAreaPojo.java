package com.talkweb.xwzcxt.pojo;

import java.math.BigDecimal;

public class DpAreaPojo {
    private String area;

    private String uparea;

    private String areaname;

    private String fullname;

    private BigDecimal nodelvl;

    private BigDecimal orderby;

    private BigDecimal isdelete;

    private String backup1;

    private String backup2;

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    public String getUparea() {
        return uparea;
    }

    public void setUparea(String uparea) {
        this.uparea = uparea == null ? null : uparea.trim();
    }

    public String getAreaname() {
        return areaname;
    }

    public void setAreaname(String areaname) {
        this.areaname = areaname == null ? null : areaname.trim();
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname == null ? null : fullname.trim();
    }

    public BigDecimal getNodelvl() {
        return nodelvl;
    }

    public void setNodelvl(BigDecimal nodelvl) {
        this.nodelvl = nodelvl;
    }

    public BigDecimal getOrderby() {
        return orderby;
    }

    public void setOrderby(BigDecimal orderby) {
        this.orderby = orderby;
    }

    public BigDecimal getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(BigDecimal isdelete) {
        this.isdelete = isdelete;
    }

    public String getBackup1() {
        return backup1;
    }

    public void setBackup1(String backup1) {
        this.backup1 = backup1 == null ? null : backup1.trim();
    }

    public String getBackup2() {
        return backup2;
    }

    public void setBackup2(String backup2) {
        this.backup2 = backup2 == null ? null : backup2.trim();
    }
}