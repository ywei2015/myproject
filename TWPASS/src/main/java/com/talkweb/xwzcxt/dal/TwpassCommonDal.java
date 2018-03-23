package com.talkweb.xwzcxt.dal;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.talkweb.twdpe.dal.batis.spring.SessionDaoSupport;

/**
 * TODO(描述这个类的作用)
 * 
 * @author: 2013-12-13，FLN，（描述修改内容）
 */
public class TwpassCommonDal extends SessionDaoSupport
{
    public <T> T getItemDetail(Map param, Class<T> clazz)
            throws InstantiationException, IllegalAccessException,
            InvocationTargetException
    {

        Map<String, String> result = (Map<String, String>) this.getSession()
                .selectOne("twpass_common.getItemDetail", param);
        T object = clazz.newInstance();
        Map<String, String> populateMap = new HashMap<String, String>();
        for (Map.Entry entry : result.entrySet())
        {
                populateMap.put(entry.getKey().toString().toLowerCase(),entry.getValue() != null? entry
                        .getValue().toString():"");
        }
        BeanUtils.populate(object, populateMap);
        return object;

    }
}
