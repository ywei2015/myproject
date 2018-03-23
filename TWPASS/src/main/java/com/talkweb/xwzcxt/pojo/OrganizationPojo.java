package com.talkweb.xwzcxt.pojo;

import java.util.Date;

public class OrganizationPojo {
    private Long orgid;

    private String orgcode;

    private Long parentorgid;

    private String parentorgcode;

    private Long bizparentid;

    private String bizparentcode;

    private String toporgcode;

    private Long toporgid;

    private String hrcode;

    private String shortname;

    private String orgname;

    private Short orglevel;

    private String orgdegree;

    private String orgtype;

    private String orgaddr;

    private String zipcode;

    private Long manaposition;

    private Long managerid;

    private String linkman;

    private String linktel;

    private String email;

    private String webname;

    private String weburl;

    private Date startdate;

    private Date enddate;

    private int status;

    private String area;

    private Date createtime;

    private Date lastupdate;

    private Long updator;

    private int orderby;

    private String busiorg;

    private String path;

    private int isdelete;

    private String remark;

    private String backup1;

    private String backup2;

    private String backup3;

    private String backup4;

    private String backup5;

    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
    }

    public String getOrgcode() {
        return orgcode;
    }

    public void setOrgcode(String orgcode) {
        this.orgcode = orgcode == null ? null : orgcode.trim();
    }

    public Long getParentorgid() {
        return parentorgid;
    }

    public void setParentorgid(Long parentorgid) {
        this.parentorgid = parentorgid;
    }

    public String getParentorgcode() {
        return parentorgcode;
    }

    public void setParentorgcode(String parentorgcode) {
        this.parentorgcode = parentorgcode == null ? null : parentorgcode.trim();
    }

    public Long getBizparentid() {
        return bizparentid;
    }

    public void setBizparentid(Long bizparentid) {
        this.bizparentid = bizparentid;
    }

    public String getBizparentcode() {
        return bizparentcode;
    }

    public void setBizparentcode(String bizparentcode) {
        this.bizparentcode = bizparentcode == null ? null : bizparentcode.trim();
    }

    public String getToporgcode() {
        return toporgcode;
    }

    public void setToporgcode(String toporgcode) {
        this.toporgcode = toporgcode == null ? null : toporgcode.trim();
    }

    public Long getToporgid() {
        return toporgid;
    }

    public void setToporgid(Long toporgid) {
        this.toporgid = toporgid;
    }

    public String getHrcode() {
        return hrcode;
    }

    public void setHrcode(String hrcode) {
        this.hrcode = hrcode == null ? null : hrcode.trim();
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname == null ? null : shortname.trim();
    }

    public String getOrgname() {
        return orgname;
    }

    public void setOrgname(String orgname) {
        this.orgname = orgname == null ? null : orgname.trim();
    }

    public Short getOrglevel() {
        return orglevel;
    }

    public void setOrglevel(Short orglevel) {
        this.orglevel = orglevel;
    }

    public String getOrgdegree() {
        return orgdegree;
    }

    public void setOrgdegree(String orgdegree) {
        this.orgdegree = orgdegree == null ? null : orgdegree.trim();
    }

    public String getOrgtype() {
        return orgtype;
    }

    public void setOrgtype(String orgtype) {
        this.orgtype = orgtype == null ? null : orgtype.trim();
    }

    public String getOrgaddr() {
        return orgaddr;
    }

    public void setOrgaddr(String orgaddr) {
        this.orgaddr = orgaddr == null ? null : orgaddr.trim();
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode == null ? null : zipcode.trim();
    }

    public Long getManaposition() {
        return manaposition;
    }

    public void setManaposition(Long manaposition) {
        this.manaposition = manaposition;
    }

    public Long getManagerid() {
        return managerid;
    }

    public void setManagerid(Long managerid) {
        this.managerid = managerid;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman == null ? null : linkman.trim();
    }

    public String getLinktel() {
        return linktel;
    }

    public void setLinktel(String linktel) {
        this.linktel = linktel == null ? null : linktel.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getWebname() {
        return webname;
    }

    public void setWebname(String webname) {
        this.webname = webname == null ? null : webname.trim();
    }

    public String getWeburl() {
        return weburl;
    }

    public void setWeburl(String weburl) {
        this.weburl = weburl == null ? null : weburl.trim();
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getLastupdate() {
        return lastupdate;
    }

    public void setLastupdate(Date lastupdate) {
        this.lastupdate = lastupdate;
    }

    public Long getUpdator() {
        return updator;
    }

    public void setUpdator(Long updator) {
        this.updator = updator;
    }

    public int getOrderby() {
        return orderby;
    }

    public void setOrderby(int orderby) {
        this.orderby = orderby;
    }

    public String getBusiorg() {
        return busiorg;
    }

    public void setBusiorg(String busiorg) {
        this.busiorg = busiorg == null ? null : busiorg.trim();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    public int getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(int isdelete) {
        this.isdelete = isdelete;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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

    public String getBackup3() {
        return backup3;
    }

    public void setBackup3(String backup3) {
        this.backup3 = backup3 == null ? null : backup3.trim();
    }

    public String getBackup4() {
        return backup4;
    }

    public void setBackup4(String backup4) {
        this.backup4 = backup4 == null ? null : backup4.trim();
    }

    public String getBackup5() {
        return backup5;
    }

    public void setBackup5(String backup5) {
        this.backup5 = backup5 == null ? null : backup5.trim();
    }
}