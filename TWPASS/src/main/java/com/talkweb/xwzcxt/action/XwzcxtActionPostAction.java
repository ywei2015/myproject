package com.talkweb.xwzcxt.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.twdpe.web.jcontrols.RuleTree;
import com.talkweb.twdpe.web.session.main.Session;
import com.talkweb.twdpe.web.session.util.SessionFactory;
import com.talkweb.twdpe.webcomponent.common.action.BusinessAction;
import com.talkweb.xwzcxt.pojo.TPostAction;
import com.talkweb.xwzcxt.pojo.TWorkObjtype;
import com.talkweb.xwzcxt.service.XwzcxtActionPostService;

/**
 * TODO(描述这个类的作用)
 * 
 * @author: 2013-12-17，wuhaowen，（描述修改内容）
 */
public class XwzcxtActionPostAction extends BusinessAction
{

    private TPostAction tpostaction;
    @Autowired
    private XwzcxtActionPostService xwzcxtActionPostService;
    
    //获取活动类别树
    public String initActionPostTree()
    {
        List<TPostAction> list = xwzcxtActionPostService
                .queryAllActionPostTree();
        RuleTree wtree = new RuleTree();
        wtree.setId("c_action_id");
        wtree.setVal("c_ACTION_NAME");
        wtree.setPid("c_UP_ACTION_ID");
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("c_UP_ACTION_CODE", "c_UP_ACTION_CODE");
//        map.put("c_ACTION_CODE", "c_ACTION_CODE");
//        wtree.setMap(map);

        try
        {
            List<Map> l = this.initTreeData(list, wtree);
            this.formatData(l);
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return SUCCESS;
    }
    
    //获取整棵活动树 add by chenlinyang 2014-01-16
    public String initActNodeTree()
    {
        List<Map> list = xwzcxtActionPostService
                .queryAllActNodeTree();
        RuleTree wtree = new RuleTree();
        wtree.setId("ID");
        wtree.setVal("VAL");
        wtree.setPid("PID");

        try
        {
            List<Map> l = this.initTreeData(list, wtree);
            this.formatData(l);
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return SUCCESS;
    }

    public String queryActionPostNodes()
    {
        HttpServletRequest request = ServletActionContext.getRequest();
        try
        {
            String c_UP_ACTION_ID = request.getParameter("c_UP_ACTION_ID");
            if (null == tpostaction)
            {
                tpostaction = new TPostAction();
                tpostaction.setC_UP_ACTION_ID(c_UP_ACTION_ID);
            }
            List<TPostAction> list = xwzcxtActionPostService
                    .queryActionPostNodes(tpostaction, pagination);
            this.formatDatagirdData(list, pagination);
        } catch (Exception e)
        {
            request.setAttribute("throw", e);
            this.setErrorMessage(e.getMessage());
        }
        return SUCCESS;

    }
    
    //modify by chenlinyang 2014-01-15
    public String addActionPost()
    {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        try
        {
            Session session = SessionFactory.getInstance(request, response);
            Map user = (Map) session.getAttribute("USERSESSION");
            if (user != null){
	            String userName = user.get("name").toString();
	            tpostaction.setC_CREATOR(userName);
	            tpostaction.setC_LAST_MODIFIER(userName);
	            /*Map param = new HashMap();
	            param.put("code", tpostaction.getC_ACTION_CODE());
	            if(xwzcxtActionPostService.isExistCode(param))
	            {
	                this.setErrorMessage("新增失败，编码已存在！");
	                return SUCCESS;
	            }*/
	            tpostaction.setC_ACTION_CODE("0");
	            xwzcxtActionPostService.addActionPost(tpostaction);
	            //this.setMessage(1, "TWORKOBJTYPE", "ADD");
	            this.formatData(this.addPrefix(tpostaction, "tpostaction."));
            }else{
            	this.setErrorMessage("网页已过期，请重新登陆！");
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            this.setErrorMessage("添加活动类别失败", e);
        }
        return SUCCESS;
    }

    public String updateActionPost()
    {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        try
        {
            Session session = SessionFactory.getInstance(request, response);
            Map user = (Map) session.getAttribute("USERSESSION");
            if (user != null){
	            String userName = user.get("name").toString();
	            tpostaction.setC_LAST_MODIFIER(userName);
	            /*Map param = new HashMap();
	            param.put("code", tpostaction.getC_ACTION_CODE());
	            param.put("id", tpostaction.getC_action_id());
	            if(xwzcxtActionPostService.isExistCode(param))
	            {
	                this.setErrorMessage("修改失败，编码已存在！");
	                return SUCCESS;
	            }*/
	            xwzcxtActionPostService.updateActionPost(tpostaction);
	            //this.setMessage(1, "TWORKOBJTYPE", "UPDATE");
	            this.formatData(this.addPrefix(tpostaction, "tpostaction."));
            }else{
            	this.setErrorMessage("网页已过期，请重新登陆！");
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            this.setErrorMessage("修改活动类别失败", e);
        }
        return SUCCESS;

    }

    public String getActionPostById()
    {
        HttpServletRequest request = ServletActionContext.getRequest();
        String id = request.getParameter("id");

        try
        {
            TPostAction tpostaction = xwzcxtActionPostService
                    .getActionPostById(id);
            this.formatData(this.addPrefix(tpostaction, "tpostaction."));
        } catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
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
            for (int i = 0; i < idArr.length; i++)
            {
                if(xwzcxtActionPostService.hasChild(idArr[i]))
                {
                    
                    this.setErrorMessage("删除失败，删除节点存在子节点！");
                    return SUCCESS;
                }
            }
            this.setMessage(1, "TWORKOBJTYPE", "DELETE");
            xwzcxtActionPostService.deleteByIds(idArr);
            
        } catch (Exception e)
        {
            e.printStackTrace();
            this.setErrorMessage("删除出错!", e);
        }
        return SUCCESS;
    }
    
    public String deleteNodesById()
    {
        HttpServletRequest request = ServletActionContext.getRequest();
        String id = request.getParameter("id");
        try
        {
        	if(xwzcxtActionPostService.hasChild(id))
            {
                
                this.setErrorMessage("删除失败，删除节点存在子节点！");
                return SUCCESS;
            }
            xwzcxtActionPostService.deleteById(id);
            this.setMessage(1, "TWORKOBJTYPE", "DELETE");
        } catch (Exception e)
        {
            e.printStackTrace();
            this.setErrorMessage("删除出错!", e);
        }
        return SUCCESS;
    }
    
    public XwzcxtActionPostService getXwzcxtActionPostService()
    {
        return xwzcxtActionPostService;
    }

    public void setXwzcxtActionPostService(
            XwzcxtActionPostService xwzcxtActionPostService)
    {
        this.xwzcxtActionPostService = xwzcxtActionPostService;
    }

    public TPostAction getTpostaction()
    {
        return tpostaction;
    }

    public void setTpostaction(TPostAction tpostaction)
    {
        this.tpostaction = tpostaction;
    }

}
