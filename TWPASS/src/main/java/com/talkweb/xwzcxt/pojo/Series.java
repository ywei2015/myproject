package com.talkweb.xwzcxt.pojo;

import java.util.ArrayList;

/**
 * TODO(描述这个类的作用) 
 * @author: 
 * 2013-12-31，wuhaowen，（描述修改内容）
 */
public class Series
{
    public String name;
    public ArrayList<String> data = new ArrayList<String>();

    public Series(String name)
    {
        super();
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public ArrayList<String> getData()
    {
        return data;
    }

    public void setData(ArrayList<String> data)
    {
        this.data = data;
    }

    public void addData(String data)
    {
        this.data.add(data);
        
    }

}
