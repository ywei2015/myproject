package com.talkweb.xwzcxt.pojo;

import java.util.ArrayList;

/**
 * 
 * @author: 2013-12-30，wuhaowen，（描述修改内容）
 */
public class TaskCount
{
    public ArrayList<String> ids = new ArrayList<String>();
    public ArrayList<String> categories = new ArrayList<String>();
    public ArrayList<Series> series = new ArrayList<Series>();

    public ArrayList<String> getCategories()
    {
        return categories;
    }

    public void setCategories(ArrayList<String> categories)
    {
        this.categories = categories;
    }

    public ArrayList<Series> getSeries()
    {
        return series;
    }

    public void setSeries(ArrayList<Series> series)
    {
        this.series = series;
    }
    
    public void addCategories(String categorie)
    {
        this.categories.add(categorie);
        
    }
    public void addSeries(Series serie)
    {
        this.series.add(serie);
        
    }
    public void addIds(String id)
    {
        this.ids.add(id);
        
    }
    public ArrayList<String> getIds()
    {
        return ids;
    }

    public void setIds(ArrayList<String> ids)
    {
        this.ids = ids;
    }
    

}
