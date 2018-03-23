package com.talkweb.xwzcxt.service;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO(描述这个类的作用) 
 * @author: 
 * 2013-12-13，FLN，（描述修改内容）
 */
public interface TwPassCommonService
{
    public <T> T getItemDetail(Map param,Class<T> clazz) throws InstantiationException, IllegalAccessException, InvocationTargetException;
}
