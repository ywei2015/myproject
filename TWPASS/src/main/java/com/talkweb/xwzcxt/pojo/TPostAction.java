package com.talkweb.xwzcxt.pojo;

import com.talkweb.twdpe.core.data.BasePOJO;

public class TPostAction  extends BasePOJO{
    private long c_action_id;

    private String c_ACTION_CODE;

    private String c_ACTION_NAME;

    private String c_UP_ACTION_CODE;

    private String c_UP_ACTION_ID;

    private String c_CREATOR;

    private String c_CREATE_TIME;

    private String c_LAST_MODIFIER;

    private String c_LAST_MODIFIED_TIME;

    private String c_REMARK;

    private String c_ISDELETE;

    private Long c_NODE_DEEP;



    public long getC_action_id()
    {
        return c_action_id;
    }

    public void setC_action_id(long c_action_id)
    {
        this.c_action_id = c_action_id;
    }

    public String getC_ACTION_CODE() {
        return c_ACTION_CODE;
    }

    public void setC_ACTION_CODE(String c_ACTION_CODE) {
        this.c_ACTION_CODE = c_ACTION_CODE;
    }

    public String getC_ACTION_NAME() {
        return c_ACTION_NAME;
    }

    public void setC_ACTION_NAME(String c_ACTION_NAME) {
        this.c_ACTION_NAME = c_ACTION_NAME;
    }

    public String getC_UP_ACTION_CODE() {
        return c_UP_ACTION_CODE;
    }

    public void setC_UP_ACTION_CODE(String c_UP_ACTION_CODE) {
        this.c_UP_ACTION_CODE = c_UP_ACTION_CODE;
    }



    public String getC_UP_ACTION_ID()
    {
        return c_UP_ACTION_ID;
    }

    public void setC_UP_ACTION_ID(String c_UP_ACTION_ID)
    {
        this.c_UP_ACTION_ID = c_UP_ACTION_ID;
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

    public Long getC_NODE_DEEP() {
        return c_NODE_DEEP;
    }

    public void setC_NODE_DEEP(Long c_NODE_DEEP) {
        this.c_NODE_DEEP = c_NODE_DEEP;
    }
}