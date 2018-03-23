package com.talkweb.xwzcxt.pojo;

public class TPostActnode {
    private long c_actnode_id;

    private String c_ACTNODE_CODE;

    private String c_ACTNODE_NAME;

    private Long c_ACTION_ID;

    private String c_ISKEYCTRL;

    private String c_ISSEQUENCE;

    private String c_ISGETNOTIFY;

    private String c_ISRELAY;

    private String c_PDCA;

    private Integer c_MANAGE_SECTION;

    private String c_CREATOR;

    private String c_CREATE_TIME;

    private String c_LAST_MODIFIER;

    private String c_LAST_MODIFIED_TIME;

    private String c_REMARK;

    private String c_ISDELETE;
    //add by chenlinyang 2014-01-16
    private String c_MANAGE_SECTION_NAME;//所属管理模块，用于列表展示
    private long positionid;

    public long getC_actnode_id()
    {
        return c_actnode_id;
    }

    public void setC_actnode_id(long c_actnode_id)
    {
        this.c_actnode_id = c_actnode_id;
    }

    public String getC_ACTNODE_CODE() {
        return c_ACTNODE_CODE;
    }

    public void setC_ACTNODE_CODE(String c_ACTNODE_CODE) {
        this.c_ACTNODE_CODE = c_ACTNODE_CODE;
    }

    public String getC_ACTNODE_NAME() {
        return c_ACTNODE_NAME;
    }

    public void setC_ACTNODE_NAME(String c_ACTNODE_NAME) {
        this.c_ACTNODE_NAME = c_ACTNODE_NAME;
    }

    public Long getC_ACTION_ID() {
        return c_ACTION_ID;
    }

    public void setC_ACTION_ID(Long c_ACTION_ID) {
        this.c_ACTION_ID = c_ACTION_ID;
    }

    public String getC_ISKEYCTRL() {
        return c_ISKEYCTRL;
    }

    public void setC_ISKEYCTRL(String c_ISKEYCTRL) {
        this.c_ISKEYCTRL = c_ISKEYCTRL;
    }

    public String getC_ISSEQUENCE() {
        return c_ISSEQUENCE;
    }

    public void setC_ISSEQUENCE(String c_ISSEQUENCE) {
        this.c_ISSEQUENCE = c_ISSEQUENCE;
    }

    public String getC_ISGETNOTIFY() {
        return c_ISGETNOTIFY;
    }

    public void setC_ISGETNOTIFY(String c_ISGETNOTIFY) {
        this.c_ISGETNOTIFY = c_ISGETNOTIFY;
    }

    public String getC_ISRELAY() {
        return c_ISRELAY;
    }

    public void setC_ISRELAY(String c_ISRELAY) {
        this.c_ISRELAY = c_ISRELAY;
    }

    public String getC_PDCA() {
        return c_PDCA;
    }

    public void setC_PDCA(String c_PDCA) {
        this.c_PDCA = c_PDCA;
    }

    public Integer getC_MANAGE_SECTION() {
        return c_MANAGE_SECTION;
    }

    public void setC_MANAGE_SECTION(Integer c_MANAGE_SECTION) {
        this.c_MANAGE_SECTION = c_MANAGE_SECTION;
    }

    public String getC_CREATOR() {
        return c_CREATOR;
    }

    public void setC_CREATOR(String c_CREATOR) {
        this.c_CREATOR = c_CREATOR;
    }

    public String getC_CREATE_TIME() {
        return c_CREATE_TIME;
    }

    public void setC_CREATE_TIME(String c_CREATE_TIME) {
        this.c_CREATE_TIME = c_CREATE_TIME;
    }

    public String getC_LAST_MODIFIER() {
        return c_LAST_MODIFIER;
    }

    public void setC_LAST_MODIFIER(String c_LAST_MODIFIER) {
        this.c_LAST_MODIFIER = c_LAST_MODIFIER;
    }

    public String getC_LAST_MODIFIED_TIME() {
        return c_LAST_MODIFIED_TIME;
    }

    public void setC_LAST_MODIFIED_TIME(String c_LAST_MODIFIED_TIME) {
        this.c_LAST_MODIFIED_TIME = c_LAST_MODIFIED_TIME;
    }

    public String getC_REMARK() {
        return c_REMARK;
    }

    public void setC_REMARK(String c_REMARK) {
        this.c_REMARK = c_REMARK;
    }

    public String getC_ISDELETE() {
        return c_ISDELETE;
    }

    public void setC_ISDELETE(String c_ISDELETE) {
        this.c_ISDELETE = c_ISDELETE;
    }

	public String getC_MANAGE_SECTION_NAME() {
		return c_MANAGE_SECTION_NAME;
	}

	public void setC_MANAGE_SECTION_NAME(String c_MANAGE_SECTION_NAME) {
		this.c_MANAGE_SECTION_NAME = c_MANAGE_SECTION_NAME;
	}

	public long getPositionid() {
		return positionid;
	}

	public void setPositionid(long positionid) {
		this.positionid = positionid;
	}
    
}