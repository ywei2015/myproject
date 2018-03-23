package com.talkweb.xwzcxt.dal;

import java.util.List;
import java.util.Map;

import com.talkweb.twdpe.dal.batis.spring.SessionDaoSupport;
import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.xwzcxt.pojo.TPostAction;
import com.talkweb.xwzcxt.pojo.TPostActnode;

/**
 * TODO(描述这个类的作用)
 * 
 * @author: 2013-12-17，FLN，（描述修改内容）
 */
public class PostActionTypeDal extends SessionDaoSupport
{
    public List<TPostAction> queryAllActionPostTree()
    {
        return this.getSession().selectList("actionPost.queryActionPostTree");

    }
    
    public List<Map> queryAllActNodeTree()
    {
        return this.getSession().selectList("actionPost.queryActNodeTree");
    }

    public List<TPostAction> queryActionPostNodes(TPostAction tPostAction,
            Pagination pagination)
    {
        return this.getSession().selectList("actionPost.queryActionPostNodes",
                tPostAction, pagination);

    }

    public List<TPostActnode> queryAllPostActNodeTree()
    {
        return this.getSession().selectList("actionPost.queryPostActnodeTree");

    }

    public void addActionPost(TPostAction tPostAction)
    {
        this.getSession().insert("actionPost.insertActionPost", tPostAction);
    }

    public void updateActionPost(TPostAction tPostAction)
    {

        this.getSession()
                .update("actionPost.updateActionPostById", tPostAction);
    }

    public TPostAction getActionPostById(String c_actnode_id)
    {
        return (TPostAction) this.getSession().selectOne(
                "actionPost.getActionPostById", c_actnode_id);
    }
    
    public boolean isExistCode(Map param)
    {

        return ((Integer) this.getSession().selectOne("actionPost.isExistCode", param)) > 0;
    }
    
    
    public void deleteByIds(String[] ids)
    {
        this.getSession().update("actionPost.batchDeleteByIds", ids);
    }
    
    public void deleteById(String id)
    {
        this.getSession().update("actionPost.deleteById", id);
    }
    
    public boolean hasChild(String id)
    {
        return ((Integer) this.getSession().selectOne("actionPost.hasChild",
                id)) > 0;
    }
}
