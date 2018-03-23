package com.talkweb.xwzcxt.dal;

import java.util.List;
import java.util.Map;

import com.talkweb.twdpe.dal.batis.spring.SessionDaoSupport;
import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.xwzcxt.pojo.TPostActnode;
import com.talkweb.xwzcxt.pojo.TTaskAttachmentPojo;

/**
 * TODO(描述这个类的作用)
 * 
 * @author: 2013-12-21，FLN，（描述修改内容）
 */
public class XwzcxtActNodeDal extends SessionDaoSupport
{
    public List<TPostActnode> queryTPostActnodeList(TPostActnode tPostActnode,
            Pagination pagination)
    {
        return (List<TPostActnode>) this.getSession().selectList(
                "actionPost.queryPostActnode", tPostActnode, pagination);
    }
    
    public List<TPostActnode> queryBindingTPostActnodeList(TPostActnode tPostActnode,
            Pagination pagination)
    {
        return (List<TPostActnode>) this.getSession().selectList(
                "actionPost.queryBindingPostActnode", tPostActnode, pagination);
    }
    
    public List<TPostActnode> queryNoBindTPostActnodeList(TPostActnode tPostActnode,
            Pagination pagination)
    {
        return (List<TPostActnode>) this.getSession().selectList(
                "actionPost.queryNoBindPostActnode", tPostActnode, pagination);
    }

    public void addTPostActnode(TPostActnode tPostActnode)
    {
        this.getSession().insert("actionPost.insertActNode", tPostActnode);

    }

    public void modifyTPostActnode(TPostActnode tPostActnode)
    {
        this.getSession().update("actionPost.updateActNodeById", tPostActnode);
    }

    public TPostActnode getTPostActnodeById(String id)
    {
        return (TPostActnode) this.getSession().selectOne(
                "actionPost.getActNodeById", id);
    }

    public boolean isExistCode(Map param)
    {
        return ((Integer) this.getSession().selectOne(
                "actionPost.isExistNodeCode", param)) > 0;
    }
    
    public void deleteByIds(String[] ids)
    {
        this.getSession().update("actionPost.batchDeleteNodeByIds", ids);
    }
    
    public void doBinding(Map param)
    {
        this.getSession().insert("actionPost.doBinding", param);
    }
    
    public void doUnbind(Map param)
    {
        this.getSession().delete("actionPost.doUnbind", param);
    }

    //获取2个版本的标准信息  GuveXie 2014-08-30  
    public Object getCommStdInfoById(Map param, int version)
    {
    	if(version==0)
    		return this.getSession().selectOne("actionPost.getStdInfoByIdVer0", param);
    	else
    		return this.getSession().selectOne("actionPost.getStdInfoByIdVer1", param);
    }

    //获取发志任务附件信息  GuveXie 2014-12-11  
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public List<TTaskAttachmentPojo> getTaskAttachmentByMap(Map param)
    {
        return (List<TTaskAttachmentPojo>) this.getSession().selectList(
                "actionPost.getTaskAttachment", param);
    	
    }
}
