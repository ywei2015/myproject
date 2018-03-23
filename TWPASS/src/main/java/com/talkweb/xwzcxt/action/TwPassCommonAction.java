package com.talkweb.xwzcxt.action;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.twdpe.webcomponent.common.action.BusinessAction;
import com.talkweb.xwzcxt.service.TwPassCommonService;

/**
 * TODO(描述这个类的作用)
 * 
 * @author: 2013-12-13，FLN，（描述修改内容）
 */
public class TwPassCommonAction extends BusinessAction
{
    @Autowired
    private TwPassCommonService twPassCommonService;
    private Object twPassResult;

    /**
     * 从request中获得参数Map，并返回可读的Map
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    private Map getParameterMap(HttpServletRequest request)
    {
        // 参数Map
        Map properties = request.getParameterMap();
        // 返回值Map
        Map returnMap = new HashMap();
        Iterator entries = properties.entrySet().iterator();
        Map.Entry entry;
        String name = "";
        String value = "";
        while (entries.hasNext())
        {
            entry = (Map.Entry) entries.next();
            name = (String) entry.getKey();
            Object valueObj = entry.getValue();
            if (null == valueObj)
            {
                value = "";
            } else if (valueObj instanceof String[])
            {
                String[] values = (String[]) valueObj;
                for (int i = 0; i < values.length; i++)
                {
                    value = values[i] + ",";
                }
                value = value.substring(0, value.length() - 1);
            } else
            {
                value = valueObj.toString();
            }
            returnMap.put(name, value);
        }
        return returnMap;
    }

    public String getItemDetail()
    {
        HttpServletRequest request = ServletActionContext.getRequest();
        Map param = this.getParameterMap(request);
        String resultClassName = (String) param.get("ClassName");
        param.remove("ClassName");
        param.remove("cache_id");
        param.remove("page");
        param.remove("pageSize");

        try
        {
            Class clazz = Class.forName("com.talkweb.xwzcxt.pojo."
                    + resultClassName);
            twPassResult = twPassCommonService.getItemDetail(param, clazz);
            this.formatData(twPassResult);
        } catch (Exception e)
        {
            this.setErrorMessage("查询失败！", e);
            e.printStackTrace();
        }
        return SUCCESS;
    }

    public TwPassCommonService getTwPassCommonService()
    {
        return twPassCommonService;
    }

    public void setTwPassCommonService(TwPassCommonService twPassCommonService)
    {
        this.twPassCommonService = twPassCommonService;
    }

    public Object getTwPassResult()
    {
        return twPassResult;
    }

    public void setTwPassResult(Object twPassResult)
    {
        this.twPassResult = twPassResult;
    }

}
