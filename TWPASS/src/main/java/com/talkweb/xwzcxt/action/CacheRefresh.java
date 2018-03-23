package com.talkweb.xwzcxt.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.talkweb.twdpe.core.cache.source.EhCacheSource;
import com.talkweb.twdpe.core.context.ContextManager;

/**
 * TODO(描述这个类的作用)
 * 
 * @author: 2014-1-7，FLN，（描述修改内容）
 */
public class CacheRefresh extends BusinessAction
{
    private static final Logger logger = LoggerFactory
            .getLogger(CacheRefresh.class);

    public String refresh()
    {
        try
        {
            EhCacheSource loader = ContextManager.getComponentManager()
                    .getComponent(EhCacheSource.class);
            loader.clear();
            this.setMessage(1, "CacheRefresh", "刷新成功！");
        } catch (Exception e)
        {
            this.setErrorMessage("刷新失败！");
        }
        return SUCCESS;
    }
}
