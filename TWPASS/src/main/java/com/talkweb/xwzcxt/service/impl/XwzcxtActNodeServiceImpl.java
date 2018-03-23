package com.talkweb.xwzcxt.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.xwzcxt.dal.ManSectionDal;
import com.talkweb.xwzcxt.dal.XwzcxtActNodeDal;
import com.talkweb.xwzcxt.pojo.TManageSection;
import com.talkweb.xwzcxt.pojo.TPostActnode;
import com.talkweb.xwzcxt.pojo.TTaskAttachmentPojo;
import com.talkweb.xwzcxt.service.XwzcxtActNodeService;

/**
 * TODO(描述这个类的作用)
 * 
 * @author: 2013-12-21，FLN，（描述修改内容）
 */
public class XwzcxtActNodeServiceImpl implements XwzcxtActNodeService
{
    @Autowired
    private XwzcxtActNodeDal xwzcxtActNodeDal;
    @Autowired
    private ManSectionDal manSectionDal;

    @Override
    public List<TPostActnode> queryTPostActnodeList(TPostActnode tPostActnode,
            Pagination pagination)
    {
        return xwzcxtActNodeDal.queryTPostActnodeList(tPostActnode, pagination);
    }
    
    @Override
    public List<TPostActnode> queryBindingTPostActnodeList(TPostActnode tPostActnode,
            Pagination pagination)
    {
        return xwzcxtActNodeDal.queryBindingTPostActnodeList(tPostActnode, pagination);
    }
    
    @Override
    public List<TPostActnode> queryNoBindTPostActnodeList(TPostActnode tPostActnode,
            Pagination pagination)
    {
        return xwzcxtActNodeDal.queryNoBindTPostActnodeList(tPostActnode, pagination);
    }

    @Override
    public void addTPostActnode(TPostActnode tPostActnode)
    {
        this.xwzcxtActNodeDal.addTPostActnode(tPostActnode);

    }

    @Override
    public void modifyTPostActnode(TPostActnode tPostActnode)
    {
        this.xwzcxtActNodeDal.modifyTPostActnode(tPostActnode);
    }

    @Override
    public TPostActnode getTPostActnodeById(String id)
    {
        return xwzcxtActNodeDal.getTPostActnodeById(id);
    }

    @Override
    public List<TManageSection> queryAllSections()
    {
        return manSectionDal.queryAllSections();
    }

    @Override
    public boolean isExistCode(Map param)
    {
        return xwzcxtActNodeDal.isExistCode(param);
    }

    
    
    @Override
    public void deleteByIds(String[] ids)
    {
        xwzcxtActNodeDal.deleteByIds(ids);
    }

    public XwzcxtActNodeDal getXwzcxtActNodeDal()
    {
        return xwzcxtActNodeDal;
    }

    public void setXwzcxtActNodeDal(XwzcxtActNodeDal xwzcxtActNodeDal)
    {
        this.xwzcxtActNodeDal = xwzcxtActNodeDal;
    }

    public ManSectionDal getManSectionDal()
    {
        return manSectionDal;
    }

    public void setManSectionDal(ManSectionDal manSectionDal)
    {
        this.manSectionDal = manSectionDal;
    }
    
    @Override
    public void doBinding(String position, String[] ids)
    {
    	Map param = new HashMap();
    	param.put("position", position);
    	for (String id : ids) {
    		param.put("id", id);
            xwzcxtActNodeDal.doBinding(param);
		}
    	
    }
    
    @Override
    public void doUnbind(String position, String ids)
    {
    	Map param = new HashMap();
    	param.put("position", position);
    	param.put("ids", ids);
    	//param.put("ids", Arrays.asList(ids));
    	
        xwzcxtActNodeDal.doUnbind(param);
    }

    //获取2个版本的标准信息  GuveXie 2014-08-30  
    public Object getCommStdInfoById(Map param,int version)
    {
        return xwzcxtActNodeDal.getCommStdInfoById(param,version);
    }
    
    //获取发起任务附件信息  GuveXie 2014-12-11 
    public List<TTaskAttachmentPojo> getTaskAttachmentByMap(Map param){
    	return xwzcxtActNodeDal.getTaskAttachmentByMap(param);
    }   

}
