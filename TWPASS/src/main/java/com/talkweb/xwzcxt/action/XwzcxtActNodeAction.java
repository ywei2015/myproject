package com.talkweb.xwzcxt.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.twdpe.web.jcontrols.RuleSelect;
import com.talkweb.twdpe.web.session.main.Session;
import com.talkweb.twdpe.web.session.util.SessionFactory;
import com.talkweb.twdpe.webcomponent.common.action.BusinessAction;
import com.talkweb.xwzcxt.pojo.CommStdInfo;
import com.talkweb.xwzcxt.pojo.TManageSection;
import com.talkweb.xwzcxt.pojo.TPostActnode;
import com.talkweb.xwzcxt.pojo.TTaskAttachmentPojo;
import com.talkweb.xwzcxt.pojo.TaskMouldPojo;
import com.talkweb.xwzcxt.service.XwzcxtActNodeService;

/**
 * TODO(描述这个类的作用)
 * 
 * @author: 2013-12-21，FLN，（描述修改内容）
 */
public class XwzcxtActNodeAction extends BusinessAction
{
    private TPostActnode tpostactnode;
    @Autowired
    private XwzcxtActNodeService xwzcxtActNodeService;
    
    
    //modify by chenlinyang 2014-01-16
    public String queryTPostActnodeList()
    {

        List<TPostActnode> list = xwzcxtActNodeService.queryTPostActnodeList(tpostactnode,
                pagination);
        List<TManageSection> seclist = xwzcxtActNodeService.queryAllSections();
        for (TPostActnode tPostActnode : list) {
			for (TManageSection tManageSection : seclist) {
				if(tManageSection.getC_manage_section() == tPostActnode.getC_MANAGE_SECTION()) {
					tPostActnode.setC_MANAGE_SECTION_NAME(tManageSection.getC_MANAGE_SECTION_NAME());
					break;
				}
			}
		}
        
        this.formatDatagirdData(list, pagination);
        return SUCCESS;
    }

    public String addTPostActnode()
    {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        try
        {
            Session session = SessionFactory.getInstance(request, response);
            Map user = (Map) session.getAttribute("USERSESSION");
            if (user == null){
            	this.setErrorMessage("网页已过期，请重新登陆！");
            }else{
	            String userName = user.get("name").toString();
	            tpostactnode.setC_CREATOR(userName);
	            tpostactnode.setC_LAST_MODIFIER(userName);
	            Map param = new HashMap();
	            param.put("code", tpostactnode.getC_ACTNODE_CODE());
	            if (xwzcxtActNodeService.isExistCode(param))
	            {
	                this.setErrorMessage("新增失败，编码已存在！");
	                return SUCCESS;
	            }
	            xwzcxtActNodeService.addTPostActnode(tpostactnode);
	            this.setMessage(1, "XWZCXTACTNODE", "ADD");
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            this.setErrorMessage("操作失败", e);

        }
        return SUCCESS;
    }

    public String modifyTPostActnode()
    {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        try
        {
            Session session = SessionFactory.getInstance(request, response);
            Map user = (Map) session.getAttribute("USERSESSION");
            if (user != null){
	            String userName = user.get("name").toString();
	            tpostactnode.setC_LAST_MODIFIER(userName);
	            Map param = new HashMap();
	            param.put("code", tpostactnode.getC_ACTNODE_CODE());
	            param.put("id", tpostactnode.getC_actnode_id());
	            if (xwzcxtActNodeService.isExistCode(param))
	            {
	                this.setErrorMessage("修改失败，编码已存在！");
	                return SUCCESS;
	            }
	            xwzcxtActNodeService.modifyTPostActnode(tpostactnode);
	            this.setMessage(1, "XWZCXTACTNODE", "MODIFY");
            }else{
            	this.setErrorMessage("网页已过期，请重新登陆！");
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            this.setErrorMessage("操作失败", e);

        }
        return SUCCESS;
    }

    public String getTPostActnodeById()
    {

        try
        {
            HttpServletRequest request = ServletActionContext.getRequest();
            String id = request.getParameter("id");
            TPostActnode o = xwzcxtActNodeService.getTPostActnodeById(id);
            this.formatData(this.addPrefix(o, "tpostactnode."));
        } catch (Exception e)
        {
            e.printStackTrace();
            this.setErrorMessage("操作失败", e);
        }
        return SUCCESS;
    }

    public String queryAllSections()
    {

        try
        {
            List<TManageSection> list = xwzcxtActNodeService.queryAllSections();
            RuleSelect st = new RuleSelect();
            st.setText("c_MANAGE_SECTION_NAME");
            st.setValue("c_manage_section");
            this.formatData(this.initSelectData(list, st));
        } catch (Exception e)
        {
            e.printStackTrace();
            this.setErrorMessage("查询错误", e);
        }
        return SUCCESS;
    }

    public String deleteNodesByIds()
    {
        HttpServletRequest request = ServletActionContext.getRequest();
        String ids = request.getParameter("ids");
        String[] idArr = ids.split(",");
        try
        {
            xwzcxtActNodeService.deleteByIds(idArr);
            this.setMessage(1, "TWORKOBJTYPE", "DELETE");
        } catch (Exception e)
        {
            e.printStackTrace();
            this.setErrorMessage("删除出错!", e);
        }
        return SUCCESS;
    }
    
    public String queryBindingTPostActnodeList()
    {

        List<TPostActnode> list = xwzcxtActNodeService.queryBindingTPostActnodeList(tpostactnode,
                pagination);
        
        this.formatDatagirdData(list, pagination);
        return SUCCESS;
    }
    
    public String queryNoBindTPostActnodeList()
    {

        List<TPostActnode> list = xwzcxtActNodeService.queryNoBindTPostActnodeList(tpostactnode,
                pagination);
        
        this.formatDatagirdData(list, pagination);
        return SUCCESS;
    }
    
    public String doBinding()
    {
        HttpServletRequest request = ServletActionContext.getRequest();
        String position = request.getParameter("position");
        String ids = request.getParameter("ids");
        String[] idArr = ids.split(",");
        try
        {
            xwzcxtActNodeService.doBinding(position, idArr);
            this.setOkMessage("绑定成功");
        } catch (Exception e)
        {
            e.printStackTrace();
            this.setErrorMessage("绑定出错！", e);
        }
        return SUCCESS;
    }
    
    public String doUnbind()
    {
        HttpServletRequest request = ServletActionContext.getRequest();
        String position = request.getParameter("position");
        String ids = request.getParameter("ids");
//        String[] idArr = ids.split(",");
        try
        {
            xwzcxtActNodeService.doUnbind(position, ids);
            this.setOkMessage("解除绑定成功");
        } catch (Exception e)
        {
            e.printStackTrace();
            this.setErrorMessage("解除绑定出错！", e);
        }
        return SUCCESS;
    }

    public XwzcxtActNodeService getXwzcxtActNodeService()
    {
        return xwzcxtActNodeService;
    }

    public void setXwzcxtActNodeService(
            XwzcxtActNodeService xwzcxtActNodeService)
    {
        this.xwzcxtActNodeService = xwzcxtActNodeService;
    }

    public TPostActnode getTpostactnode()
    {
        return tpostactnode;
    }

    public void setTpostactnode(TPostActnode tpostactnode)
    {
        this.tpostactnode = tpostactnode;
    }
    
   //获取2个版本的标准信息  GuveXie 2014-08-30  ep:actNode_getCommStdInfoById.action?actnodeid=328&version=0
	public String getCommStdInfoById(){
    	HttpServletRequest request = ServletActionContext.getRequest();
    	try{
    		Map params = new HashMap();
    		params.put("stditemid", request.getParameter("actnodeid"));
    		//params.put("stdversion", request.getParameter("version"));
    		int version = Integer.parseInt(request.getParameter("version").toString());
    		CommStdInfo data = (CommStdInfo) xwzcxtActNodeService.getCommStdInfoById(params, version);
    		if (data != null){
    			this.formatData(this.addPrefix(data, "StdInfo."));
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return SUCCESS;
    }    

   //获取发起任务附件信息  GuveXie 2014-12-11  ep:actNode_getTaskAttachmentByMap.action?taskid=6201
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String getTaskAttachmentByMap(){
    	HttpServletRequest request = ServletActionContext.getRequest();
    	try{
    		Map params = new HashMap();
    		params.put("c_task_id", request.getParameter("taskid"));  
            List<TTaskAttachmentPojo> list = xwzcxtActNodeService.getTaskAttachmentByMap(params); 
            this.formatData(list);
            return SUCCESS;	    
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return SUCCESS;
    }    	
}
