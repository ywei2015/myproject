package com.talkweb.xwzcxt.service;

import java.util.List;
import java.util.Map;

import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.xwzcxt.pojo.TManageSection;
import com.talkweb.xwzcxt.pojo.TPostActnode;
import com.talkweb.xwzcxt.pojo.TTaskAttachmentPojo;

/**
 * TODO(描述这个类的作用)
 * 
 * @author: 2013-12-21，FLN，（描述修改内容）
 */
public interface XwzcxtActNodeService
{
    public List<TPostActnode> queryTPostActnodeList(TPostActnode tPostActnode,
            Pagination pagination);
    
    public List<TPostActnode> queryBindingTPostActnodeList(TPostActnode tPostActnode,
            Pagination pagination);
    
    public List<TPostActnode> queryNoBindTPostActnodeList(TPostActnode tPostActnode,
            Pagination pagination);

    public void addTPostActnode(TPostActnode tPostActnode);

    public void modifyTPostActnode(TPostActnode tPostActnode);

    public TPostActnode getTPostActnodeById(String id);
    
    public List<TManageSection> queryAllSections();
    
    public boolean isExistCode(Map param);
    
    public void deleteByIds(String[] ids);
    
    public void doBinding(String position, String[] ids);
    
    public void doUnbind(String position, String ids);
  
    public Object getCommStdInfoById(Map param, int version); //获取2个版本的标准信息  GuveXie 2014-08-30  
    
    //获取发起任务附件信息  GuveXie 2014-12-11 
    public List<TTaskAttachmentPojo> getTaskAttachmentByMap(Map param);   
    

}
