package com.talkweb.xwzcxt.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.xwzcxt.dal.TwpassCommonDal;
import com.talkweb.xwzcxt.service.TwPassCommonService;

/**
 * TODO(描述这个类的作用) 
 * @author: 
 * 2013-12-13，FLN，（描述修改内容）
 */
public class TwPassCommonServiceImpl implements TwPassCommonService
{
    @Autowired
    private TwpassCommonDal twpassCommonDal;
    @Override
    public <T> T getItemDetail(Map param, Class<T> clazz) throws InstantiationException, IllegalAccessException, InvocationTargetException
    {
        
        return twpassCommonDal.getItemDetail(param, clazz);
    }
    public TwpassCommonDal getTwpassCommonDal()
    {
        return twpassCommonDal;
    }
    public void setTwpassCommonDal(TwpassCommonDal twpassCommonDal)
    {
        this.twpassCommonDal = twpassCommonDal;
    }
    

}
