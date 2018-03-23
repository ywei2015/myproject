package com.talkweb.xwzcxt.service;

import java.util.List;
import java.util.Map;

import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.xwzcxt.pojo.TPostAction;

/**
 * TODO(描述这个类的作用)
 * 
 * @author: 2013-12-17，FLN，（描述修改内容）
 */
public interface XwzcxtActionPostService
{
    public List<TPostAction> queryAllActionPostTree();
    
    public List<Map> queryAllActNodeTree();

    public List<TPostAction> queryActionPostNodes(TPostAction tPostAction,
            Pagination pagination);

    public void addActionPost(TPostAction tPostAction);

    public void updateActionPost(TPostAction tPostAction);

    public TPostAction getActionPostById(String id);
    
    public boolean isExistCode(Map param);
    
    public void deleteByIds(String[] ids);
    
    public void deleteById(String id);
    
    public boolean hasChild(String id);
}
