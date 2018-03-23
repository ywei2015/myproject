package com.talkweb.xwzcxt.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.twdpe.base.org.service.PositionService;
import com.talkweb.twdpe.core.log.Logger;
import com.talkweb.twdpe.core.log.LoggerFactory;
import com.talkweb.twdpe.web.jcontrols.RuleSelect;
import com.talkweb.twdpe.web.jcontrols.RuleTree;
import com.talkweb.twdpe.web.session.main.Session;
import com.talkweb.twdpe.web.session.util.SessionFactory;
import com.talkweb.twdpe.webcomponent.common.action.BusinessAction;
import com.talkweb.xwzcxt.pojo.DpPosition;
import com.talkweb.xwzcxt.pojo.GeneralItem;
import com.talkweb.xwzcxt.pojo.OrgBindPositionPojo;
import com.talkweb.xwzcxt.service.BaseCommService;

//GuveXie 2014-08-19 公用基础数据 Action
public class BaseCommAction extends BusinessAction {
	private static final long serialVersionUID = -2586161684435728690L;

    private static final Logger logger = LoggerFactory.getLogger(BaseCommAction.class);
        
    @Autowired
    private BaseCommService basecommservice;

    @SuppressWarnings({ "rawtypes", "unchecked" })
	public String getPositionListByParams(){
		HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        Session session = SessionFactory.getInstance(request, response);
        String orgid = request.getParameter("orgid");
        String positionid = request.getParameter("positionid");
        try {
            Map<String, Object> userMap = (Map) session.getAttribute("USERSESSION"); 
            if (userMap == null) {
                this.setErrorMessage("用户Session已过期");
            }
            else {
            	Map params = new HashMap();
            	if(orgid==null){ //.isEmpty()){
            		orgid = userMap.get("orgid").toString();
            	}
            	params.put("orgid", orgid);
            	params.put("positionid", positionid);
	            List<DpPosition> poslist = (List<DpPosition>) basecommservice.getPositionByOrgID(params);
	            if(poslist != null && poslist.size()>0){
					RuleSelect rls = new RuleSelect();
					rls.setValue("positionid");
					rls.setText("posiname");
					List<Map> l = this.initSelectData(poslist, rls);
					this.formatData(l);
				}
            }
        }
        catch (Exception e) {
            request.setAttribute("throw", e);
            logger.error(e.getMessage(), e);
        }
        return SUCCESS; 
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public String getOrgPositionTreeByParams(){
		HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        Session session = SessionFactory.getInstance(request, response);
        String orgid = request.getParameter("orgid");
        //Position position = null;
        try {
            Map<String, Object> userMap = (Map) session.getAttribute("USERSESSION"); 
            if (userMap == null) {
                this.setErrorMessage("用户Session已过期");
            }
            else {
            	Map params = new HashMap();
            	if(orgid==null){ //.isEmpty()){
            		orgid = userMap.get("orgid").toString();
            	}
            	params.put("orgid", orgid);
	            List<OrgBindPositionPojo> poslist = (List<OrgBindPositionPojo>) basecommservice.getOrgPositionByParams(params);
	            if(poslist != null && poslist.size()>0){
		            RuleTree wtree = new RuleTree();
		            wtree.setId("dataid");
		            wtree.setVal("dataname");
		            wtree.setPid("pid");
		            Map<String, String> map = new HashMap<String, String>();
		            map.put("dtype", "dtype");
		            map.put("cid", "cid");
		            wtree.setMap(map);
		            List<Map> l = this.initTreeData(poslist, wtree);
					this.formatData(l);
				}
            }
        }
        catch (Exception e) {
            request.setAttribute("throw", e);
            logger.error(e.getMessage(), e);
        }
        return SUCCESS; 
    }

	//获取消息类别List  GuveXie 20140820 : basecomm_getAllMsgTypeList.action
	@SuppressWarnings({ "unchecked", "unused", "rawtypes" })
	public String getAllMsgTypeList(){
		HttpServletRequest request = ServletActionContext.getRequest ();
		try{
			List<GeneralItem> data = (List<GeneralItem>) basecommservice.getAllMsgType();
			if(data != null && data.size()>0){
				RuleSelect wtree = new RuleSelect();
				wtree.setValue("c_id");
				wtree.setText("c_name");
				List<Map> l = this.initSelectData(data, wtree);
				this.formatData(l);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
		    
}
