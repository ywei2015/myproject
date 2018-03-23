package com.talkweb.xwzcxt.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.xwzcxt.dal.PostActionTypeDal;
import com.talkweb.xwzcxt.pojo.TPostAction;
import com.talkweb.xwzcxt.service.XwzcxtActionPostService;

/**
 * TODO(描述这个类的作用)
 * 
 * @author: 2013-12-17，FLN，（描述修改内容）
 */
public class XwzcxtActionPostServiceImpl implements XwzcxtActionPostService
{

    @Autowired
    private PostActionTypeDal postActionTypeDal;

    @Override
    public List<TPostAction> queryAllActionPostTree()
    {
        return postActionTypeDal.queryAllActionPostTree();
    }
    
    @Override
    public List<Map> queryAllActNodeTree()
    {
        return postActionTypeDal.queryAllActNodeTree();
    }

    @Override
    public List<TPostAction> queryActionPostNodes(TPostAction tPostAction,
            Pagination pagination)
    {
        return postActionTypeDal.queryActionPostNodes(tPostAction, pagination);
    }

    @Override
    public void addActionPost(TPostAction tPostAction)
    {
        postActionTypeDal.addActionPost(tPostAction);

    }

    @Override
    public boolean isExistCode(Map param)
    {
        return postActionTypeDal.isExistCode(param);
    }

    public TPostAction getActionPostById(String id)
    {
        return postActionTypeDal.getActionPostById(id);

    }

    @Override
    public void updateActionPost(TPostAction tPostAction)
    {
        postActionTypeDal.updateActionPost(tPostAction);

    }

    @Override
    public void deleteByIds(String[] ids)
    {
        postActionTypeDal.deleteByIds(ids);
    }
    
    @Override
    public void deleteById(String id)
    {
        postActionTypeDal.deleteById(id);
    }

    @Override
    public boolean hasChild(String id)
    {
        return postActionTypeDal.hasChild(id);
    }

    public PostActionTypeDal getPostActionTypeDal()
    {
        return postActionTypeDal;
    }

    public void setPostActionTypeDal(PostActionTypeDal postActionTypeDal)
    {
        this.postActionTypeDal = postActionTypeDal;
    }

}
