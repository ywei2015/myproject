package com.talkweb.xwzcxt.pojo;

import java.util.Date;

/**
 * TODO(描述这个类的作用)
 * 
 * @author: 2013-12-31，FLN，（描述修改内容）
 */
public class TaskCondition
{

    private String group;
    private String scope;
    private String start;
    private String end;
    private String userid;
    
    public String getGroup()
    {
        return group;
    }

    public void setGroup(String group)
    {
        this.group = group;
    }

    public String getScope()
    {
        return scope;
    }

    public void setScope(String scope)
    {
        this.scope = scope;
    }

 
    public String getUserid()
    {
        return userid;
    }

    public void setUserid(String userid)
    {
        this.userid = userid;
    }

    public String getStart()
    {
        return start;
    }

    public void setStart(String start)
    {
        this.start = start;
    }

    public String getEnd()
    {
        return end;
    }

    public void setEnd(String end)
    {
        this.end = end;
    }

}
