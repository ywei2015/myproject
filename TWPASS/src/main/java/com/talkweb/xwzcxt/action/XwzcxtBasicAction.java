package com.talkweb.xwzcxt.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import com.talkweb.twdpe.web.jcontrols.RuleSelect;
import com.talkweb.twdpe.web.jcontrols.RuleTree;
import com.talkweb.twdpe.base.org.pojo.User;
import com.talkweb.twdpe.core.log.Logger;
import com.talkweb.twdpe.core.log.LoggerFactory;
import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.twdpe.web.session.main.Session;
import com.talkweb.twdpe.web.session.util.SessionFactory;
import com.talkweb.twdpe.webcomponent.common.action.BusinessAction;
import com.talkweb.xwzcxt.common.XwzcxtCommon.Enum_Xwzcxt;
import com.talkweb.xwzcxt.pojo.BasicMouldPojo;
import com.talkweb.xwzcxt.pojo.InitAutoPojo;
import com.talkweb.xwzcxt.service.impl.XwzcxtBasicImp;
import com.talkweb.xwzcxt.pojo.TWorkObjtype;
import com.talkweb.xwzcxt.service.XwzcxtObjTypeService;
public class XwzcxtBasicAction extends BusinessAction {	 
	
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(XwzcxtBasicAction.class);
    private TWorkObjtype tworkobjtype;
     @Autowired
    private XwzcxtObjTypeService xwzcxtObjTypeService;
     
    //基础管理类
    public String queryTaskByPage() {
		if (basicMouldPojo == null)
			basicMouldPojo = new BasicMouldPojo();
		System.out.println("*************************"+basicMouldPojo);
		this.excludeNullProperties(basicMouldPojo);
		try {
            HttpServletRequest request = ServletActionContext.getRequest();
            List<BasicMouldPojo> list  = null;
            String action  = request.getParameter("action");  
            String taskId = null;
            switch(Integer.parseInt(action)){                
                case 5://对象类别管理
                	list = xwzcxtBasicImp.GetCurrentRecordByPage(basicMouldPojo,pagination, Enum_Xwzcxt.TASK_BASIC_TYPE);
                	break;
                case 6://作业对象
                    list = xwzcxtBasicImp.GetCurrentRecordByPage(basicMouldPojo,pagination, Enum_Xwzcxt.TASK_BASIC_JOB);
                    break;
                case 7://排班管理
                    list = xwzcxtBasicImp.GetCurrentRecordByPage(basicMouldPojo,pagination, Enum_Xwzcxt.TASK_BASIC_SCHEDULE);
                    break;
                case 8://日历管理
                    list = xwzcxtBasicImp.GetCurrentRecordByPage(basicMouldPojo,pagination, Enum_Xwzcxt.TASK_BASIC_CAL);
                    break;
                case 9://活动类别管理
                    list = xwzcxtBasicImp.GetCurrentRecordByPage(basicMouldPojo,pagination, Enum_Xwzcxt.TASK_STANDARD_TYPE);
                    break;
                case 10://活动末节点管理
                    list = xwzcxtBasicImp.GetCurrentRecordByPage(basicMouldPojo,pagination, Enum_Xwzcxt.TASK_STANDARD_POINT);
                    break;
                case 11://节点标准管理
                    list = xwzcxtBasicImp.GetCurrentRecordByPage(basicMouldPojo,pagination, Enum_Xwzcxt.TASK_STANDARD_STANDARD);
                    break;              
                case 13://时间规则管理
                    list = xwzcxtBasicImp.GetCurrentRecordByPage(basicMouldPojo,pagination, Enum_Xwzcxt.TASK_RULE_TIME);
                    break;
                case 14://状态分组统计
                    list = xwzcxtBasicImp.GetCurrentRecordByPage(basicMouldPojo,pagination, Enum_Xwzcxt.TASK_TOTAL_STATU);
                    break;
                case 15://个人任务查询
                    list = xwzcxtBasicImp.GetCurrentRecordByPage(basicMouldPojo,pagination, Enum_Xwzcxt.TASK_TOTAL_PERSON);
                    break;
                case 16://单任务查询
                    list = xwzcxtBasicImp.GetCurrentRecordByPage(basicMouldPojo,pagination, Enum_Xwzcxt.TASK_TOTAL_TASK);
                    break;
                	
            }       	    
		    this.formatDatagirdData(list, pagination);//格式化DataGrid控件数据
		} catch (Exception e) {
			ServletActionContext.getRequest().setAttribute("throw", e);
			@SuppressWarnings("rawtypes")
			List emptylist = new ArrayList();
			this.formatDatagirdData(emptylist, pagination);//格式化DataGrid控件数据
		    logger.error(e.getMessage(), e);
		}
		basicMouldPojo = null;
		return SUCCESS;
    }
    public String queryTaskEntry(){
        if (basicMouldPojo == null)
            basicMouldPojo = new BasicMouldPojo();
        this.excludeNullProperties(basicMouldPojo);
        Enum_Xwzcxt enum_Xwzcxt = null;
        HttpServletRequest request = ServletActionContext.getRequest ();
        
        String action = request.getParameter ("action");
        try {
            switch (Integer.parseInt (action))
            {
                
                case 5://对象类别管理
                	basicMouldPojo.setC_objtype_code(request.getParameter(""));
                	break;
                
            }
            basicMouldPojo = xwzcxtBasicImp.queryTobaccoEntry (enum_Xwzcxt, basicMouldPojo);            
            this.formatData (this.addPrefix (basicMouldPojo, "basicMouldPojo."));// 给对象属性增加统一的前缀名，并格化输出
        }
        catch (Exception e) {
            request.setAttribute ("throw", e);
            // 如果出错，则输出一个空的JSON对象
            this.setErrorMessage (e);
            logger.error (e.getMessage (), e);
        }
        return SUCCESS;

    }
    public String editData () {
        if (basicMouldPojo == null)
            basicMouldPojo = new BasicMouldPojo();        
        this.excludeNullProperties(basicMouldPojo); 
        
        Object userID = super.getUserSession().get("id");
        boolean bl = false;
        HttpServletRequest request = ServletActionContext.getRequest ();
        try {          
            
            this.setMessage(bl ? 1 : 0, "Edit", "Edit_Info");
        }
        catch (Exception e) {
            request.setAttribute ("throw", e);
            this.setMessage (0, "Edit", "Edit_info", e);
            logger.error (e.getMessage (), e);
        }
        return SUCCESS;
    }
    public String querySelect(){        
        try {    
        	if(basicMouldPojo == null) {//做空指针判定检查，add by chenlinyang 2014-01-18
        		basicMouldPojo = new BasicMouldPojo();
        	}
            this.excludeNullProperties(basicMouldPojo);            
            HttpServletRequest request = ServletActionContext.getRequest();    
            String c_actitem_id = request.getParameter("value");
            List<InitAutoPojo> list = xwzcxtBasicImp.GetInitBasicItem(c_actitem_id,Integer.parseInt(request.getParameter("action")));        
            this.formatData(list);
        } catch (Exception e) {
            ServletActionContext.getRequest().setAttribute("throw", e);
            @SuppressWarnings("rawtypes")
            List emptylist = new ArrayList();
            this.formatData(emptylist);//格式化DataGrid控件数据
            logger.error(e.getMessage(), e);
        }
        return SUCCESS;
    }
	@Autowired
    private XwzcxtBasicImp xwzcxtBasicImp;
    public String queryObjTypeTree()
    {
        List<TWorkObjtype> list = xwzcxtObjTypeService.queryAllObjTypeTree();
        RuleTree wtree = new RuleTree();
        wtree.setId("c_objtype_id");
        wtree.setVal("c_objtype_name");
        wtree.setPid("c_up_objtype_id");
        Map<String,String> map = new HashMap<String,String>();
        map.put("c_objtype_code", "c_objtype_code");
        map.put("c_up_objtype_code", "c_up_objtype_code");
        wtree.setMap(map);
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

    public String queryTypeNodes()
    {
        HttpServletRequest request = ServletActionContext.getRequest();
        try
        {
            String c_up_objtype_id = request
                    .getParameter("c_up_objtype_id");
            if (null == tworkobjtype){
                tworkobjtype = new TWorkObjtype();
            }
            
            tworkobjtype.setC_up_objtype_id(c_up_objtype_id);
            List<TWorkObjtype> list = xwzcxtObjTypeService
                    .queryTypeNodes(tworkobjtype,pagination);
            this.formatDatagirdData(list, pagination);
        } catch (Exception e)
        {
            request.setAttribute("throw", e);
            this.setErrorMessage(e.getMessage());
            logger.error(e.getMessage(), e);
        }
        return SUCCESS;
    }

    public String addObjType()
    {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        try
        {
            if(null==tworkobjtype)
            {
                
                tworkobjtype = new TWorkObjtype();
            }
            Session session = SessionFactory.getInstance(request, response);
            Map user = (Map) session.getAttribute("USERSESSION");
            if (user != null){
	            String userName = user.get("name").toString();
	            tworkobjtype.setC_creator(userName);
	            tworkobjtype.setC_last_modifier(userName);
	            Map param = new HashMap();
	            param.put("code", tworkobjtype.getC_objtype_code());
	            if(xwzcxtObjTypeService.isExistCode(param))
	            {
	                this.setErrorMessage("新增失败，编码已存在！");
	                return SUCCESS;
	            }
	            xwzcxtObjTypeService.addObjType(tworkobjtype);
	            this.setMessage(1, "TWORKOBJTYPE", "ADD");
            }else{
            	 this.setErrorMessage("网页已过期，请重新登陆！");
            }
        } catch (Exception e)
        {
            request.setAttribute("throw", e);
            this.setErrorMessage(e.getMessage());
            logger.error(e.getMessage(), e);
        }

        return SUCCESS;
    }

    public String removeObjTypeByKey()
    {
        HttpServletRequest request = ServletActionContext.getRequest();
        try
        {
            String c_up_objtype_id = request
                    .getParameter("c_up_objtype_id");
            if (null == tworkobjtype)
            {
                tworkobjtype = new TWorkObjtype();
                tworkobjtype.setC_up_objtype_id(c_up_objtype_id);
            }
            xwzcxtObjTypeService.removeObjTypeByKey(c_up_objtype_id);
        } catch (Exception e)
        {
            request.setAttribute("throw", e);
            this.setErrorMessage(e.getMessage());
            logger.error(e.getMessage(), e);
        }

        return SUCCESS;
    }
    public String getTWorkObjtypeById()
    {
        HttpServletRequest request = ServletActionContext.getRequest();
        try
        {
            String id = request.getParameter("id");
            tworkobjtype = xwzcxtObjTypeService.getTWorkObjtypeById(id);
            this.formatData(this.addPrefix(tworkobjtype, "tworkobjtype."));
        } catch (Exception e)
        {
            request.setAttribute("throw", e);
            this.setErrorMessage(e.getMessage());
            logger.error(e.getMessage(), e);
        }
        return SUCCESS;
    }
    public String updateObjType()
    {
        HttpServletRequest request = ServletActionContext.getRequest();
        try
        {
            if (null == tworkobjtype)
            {
                tworkobjtype = new TWorkObjtype();
            }
            Map param = new HashMap();
            param.put("code", tworkobjtype.getC_objtype_code());
            param.put("id", tworkobjtype.getC_objtype_id());
            if(xwzcxtObjTypeService.isExistCode(param))
            {
                this.setErrorMessage("修改失败，编码已存在！");
                return SUCCESS;
            }
            xwzcxtObjTypeService.updateObjType(tworkobjtype);
            this.setMessage(1, "TWORKOBJTYPE", "UPDATE");
        } catch (Exception e)
        {
            request.setAttribute("throw", e);
            this.setErrorMessage(e.getMessage());
            logger.error(e.getMessage(), e);
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
                if(xwzcxtObjTypeService.hasChild(idArr[i]))
                {
                    
                    this.setErrorMessage("删除失败，删除节点存在子节点！");
                    return SUCCESS;
                }
            }
            this.setMessage(1, "TWORKOBJTYPE", "DELETE");
            xwzcxtObjTypeService.deleteByIds(idArr);
        } catch (Exception e)
        {
            e.printStackTrace();
            this.setErrorMessage("删除出错!", e);
        }
        return SUCCESS;
    }

    public XwzcxtObjTypeService getXwzcxtObjTypeService()
    {
        return xwzcxtObjTypeService;
    }

    public void setXwzcxtObjTypeService(
            XwzcxtObjTypeService xwzcxtObjTypeService)
    {
        this.xwzcxtObjTypeService = xwzcxtObjTypeService;
    }

    private BasicMouldPojo basicMouldPojo = null;

    public BasicMouldPojo getBasicMouldPojo()
    {
        return basicMouldPojo;
    }

    public void setBasicMouldPojo(BasicMouldPojo basicMouldPojo)
    {
        this.basicMouldPojo = basicMouldPojo;
    }

    public TWorkObjtype getTworkobjtype()
    {
        return tworkobjtype;
    }

    public void setTworkobjtype(TWorkObjtype tworkobjtype)
    {
        this.tworkobjtype = tworkobjtype;
    }
    
    
    //Add by Rita.Zhou for add functions
    
    public String addBasic(){
    	HttpServletRequest request = ServletActionContext.getRequest();
    	int result = -1;
    	String msg = "信息插入失败，请检查填写内容";
		Map pageData = new HashMap();
		try{
			if (xwzcxtBasicImp.addWorkObject(basicMouldPojo) > 0){
				msg = "信息插入成功！";
				result = 0;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		pageData.put("msg", msg);
		pageData.put("result", result);
		this.formatData(pageData);
    	return SUCCESS;
    }
    
    /**
     * 新增标准项
     * @return
     */
    public String addActiveStandard(){
		try{
			if (xwzcxtBasicImp.addActiveStandard(basicMouldPojo) > 0){
				this.setOkMessage("新增成功");
			}
			else {
				this.setErrorMessage("新增失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			this.setErrorMessage("新增失败");
		}
    	return SUCCESS;
    }
    
    /**
     * 修改标准项
     * @return
     */
    public String updateActiveStandard(){
    	try{
    		if (xwzcxtBasicImp.updateActiveStandard(basicMouldPojo) > 0){
    			this.setOkMessage("修改成功");
    		} 
    		else {
				this.setErrorMessage("修改失败");
			}
    	}catch(Exception e){
    		e.printStackTrace();
    		this.setErrorMessage("修改失败");
    	}
    	return SUCCESS;
    }
    
    public String getStandardById(){
    	HttpServletRequest request = ServletActionContext.getRequest();
    	try{
    		BasicMouldPojo data = xwzcxtBasicImp.getStandardById(request.getParameter("cid"));
    		if (data != null){
    			this.formatData(this.addPrefix(data, "basicMouldPojo."));
    		}
    		
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return SUCCESS;
    }
    
    /**
     * 删除标准项
     * @return
     */
    public String deleteStandardStepById(){
    	HttpServletRequest request = ServletActionContext.getRequest();
		try{
			if(xwzcxtBasicImp.deleteStandardStepById(request.getParameter("cid")) > 0){
				this.setOkMessage("删除成功");
			}
			else {
				this.setErrorMessage("删除失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			this.setErrorMessage("删除失败");
		}
    	return SUCCESS;
    }
    
    /**
     * 批量删除
     * @return
     */
    public String deleteStandardStepByIds(){
    	HttpServletRequest request = ServletActionContext.getRequest();
		try{
			String[] ids = request.getParameter("ids").split(",");
			if(xwzcxtBasicImp.batchDeleteStdStepById(ids) > 0){
				this.setOkMessage("删除成功");
			}
			else {
				this.setErrorMessage("删除失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			this.setErrorMessage("删除失败");
		}
    	return SUCCESS;
    }
    
    /**
     * 删除步骤
     * @return
     */
    public String deleteStepDataById(){
    	HttpServletRequest request = ServletActionContext.getRequest();
		try{
			if (xwzcxtBasicImp.deleteStepDataById(request.getParameter("cid")) >= 0){
				this.setOkMessage("删除成功");
			}
			else {
				this.setErrorMessage("删除失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			this.setErrorMessage("删除失败");
		}
    	return SUCCESS;
    }
    
    public String getAllActNode(){
    	HttpServletRequest request = ServletActionContext.getRequest();
    	try{
    		List<BasicMouldPojo> datas = xwzcxtBasicImp.getAllActNode();
    		if(datas != null && datas.size()>0){
    			RuleSelect wtree = new RuleSelect();
    			wtree.setValue("c_actnode_id");
    			wtree.setText("c_actnode_name");
    			List<Map> l = this.initSelectData(datas, wtree);
    			this.formatData(l);
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return SUCCESS;
    }
    
    public String getCalDataBySeq(){
    	HttpServletRequest request = ServletActionContext.getRequest();
		String seq = request.getParameter("seq");
		try{
			BasicMouldPojo data = (BasicMouldPojo) xwzcxtBasicImp.getCalDataBySeq(seq);
			if (data != null){
				this.formatData(this.addPrefix(data, "basicMouldPojo."));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
    	return SUCCESS;
    }
    
    
    public String modifyCalData(){
    	HttpServletRequest request = ServletActionContext.getRequest();
    	int result = -1;
    	String msg = "";
		Map pageData = new HashMap();
		basicMouldPojo.setC_id(Integer.parseInt(request.getParameter("seq")));
		try{
			if (xwzcxtBasicImp.modifyCalData(basicMouldPojo) > 0){
				result = 0;
				msg = "修改成功！";
			}else{
				result = -1;
				msg = "信息修改失败，请检查填写内容";
			}
			
		}catch(Exception e){
			result = -1;
			msg = "信息修改失败，请检查填写内容";
			e.printStackTrace();
		}
		pageData.put("result", result);
		pageData.put("msg", msg);
		this.formatData(pageData);
    	return SUCCESS;
    }
    
    public String getStandardStepById(){
    	try{
    		List<BasicMouldPojo> list = xwzcxtBasicImp.getStandardStepById(basicMouldPojo.getC_actitem_id()+"");
    		if (list != null && list.size()>0){
    			this.formatDatagirdData(list, new Pagination(false,10));
    		}
    		
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return SUCCESS;
    }
    
    public String deleteObjectById(){
    	HttpServletRequest request = ServletActionContext.getRequest();
    	int result = -1;
    	String msg = "";
		Map pageData = new HashMap();
		//basicMouldPojo.setC_obj_id(Integer.parseInt());
		try{
			if (xwzcxtBasicImp.deleteObjectById(request.getParameter("cid")) > 0){
				result = 0;
				msg = "删除成功！";
			}else{
				result = -1;
				msg = "删除失败。";
			}
			
		}catch(Exception e){
			result = -1;
			msg = "删除失败";
			e.printStackTrace();
		}
		pageData.put("result", result);
		pageData.put("msg", msg);
		this.formatData(pageData);
    	return SUCCESS;
    }
    
    public String modifyBasicData(){
    	HttpServletRequest request = ServletActionContext.getRequest();
    	int result = -1;
    	String msg = "";
		Map pageData = new HashMap();
		basicMouldPojo.setC_obj_id(Integer.parseInt(request.getParameter("objId")));
		try{
			if (xwzcxtBasicImp.modifyBasicData(basicMouldPojo) > 0){
				result = 0;
				msg = "修改成功！";
			}else{
				result = -1;
				msg = "信息修改失败，请检查填写内容";
			}
			
		}catch(Exception e){
			result = -1;
			msg = "信息修改失败，请检查填写内容";
			e.printStackTrace();
		}
		pageData.put("result", result);
		pageData.put("msg", msg);
		this.formatData(pageData);
    	return SUCCESS;
    }
    
    public String getBasicDataByObjId(){
    	HttpServletRequest request = ServletActionContext.getRequest();
    	try{
    		BasicMouldPojo data = xwzcxtBasicImp.getBasicDataByObjId(request.getParameter("objId"));
    		if (data != null){
    			this.formatData(this.addPrefix(data, "basicMouldPojo."));
    		}
    		
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return SUCCESS;
    }
    
    public String getAllTraceFunName(){
    	try{
	    	List<BasicMouldPojo> list = xwzcxtBasicImp.getAllTraceFunName();
	    	if (list != null && list.size()>0){
				RuleSelect wtree = new RuleSelect();
				wtree.setValue("c_tracefun_id");
				wtree.setText("c_tracefun_name");
				List<Map> l = this.initSelectData(list, wtree);
				this.formatData(l);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
    	return SUCCESS;
    }
    
    /**
     * 新增步骤
     * @return
     */
    public String addStepData(){
    	try{
    		if (xwzcxtBasicImp.addStepData(basicMouldPojo) > 0){
    			this.setOkMessage("新增成功");
    		}else{
    			this.setErrorMessage("新增失败");
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    		this.setErrorMessage("新增失败");
    	}
    	return SUCCESS;
    }
    
    public String getStepDataByCid(){
    	try{
    		HttpServletRequest request = ServletActionContext.getRequest();
    		String cid = request.getParameter("c_id");
    		BasicMouldPojo data = xwzcxtBasicImp.getStepDataByCid(cid);
    		if (data != null){
    			this.formatData(this.addPrefix(data, "basicMouldPojo."));
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return SUCCESS;
    }
    
    /**
     * 修改步骤
     * @return
     */
    public String modifyStepDataByCid(){
    	try{
    		if (xwzcxtBasicImp.modifyStepDataByCid(basicMouldPojo) > 0){
    			this.setOkMessage("修改成功");
    		}else{
    			this.setErrorMessage("修改失败");
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    		this.setErrorMessage("修改失败");
    	}
    	return SUCCESS;
    }
    
    public String addTimeRule(){
    	int result = -1;
    	String msg = "";
		Map pageData = new HashMap();
    	try{
    		if (xwzcxtBasicImp.addTimeRule(basicMouldPojo) > 0){
    			result = 0;
				msg = "新增成功！";
    		}else{
    			result = -1;
				msg = "信息新增失败，请检查填写内容";
    		}
    	}catch(Exception e){
    		result = -1;
			msg = "信息新增失败，请检查填写内容";
    		e.printStackTrace();
    	}
    	pageData.put("result", result);
		pageData.put("msg", msg);
		this.formatData(pageData);
    	return SUCCESS;
    }
    
    public String getTimeRuleById(){
    	try{
    		HttpServletRequest request = ServletActionContext.getRequest();
    		String timeruleid = request.getParameter("c_timerule_id");
    		BasicMouldPojo data = xwzcxtBasicImp.getTimeRuleById(timeruleid);
    		if (data != null){
    			this.formatData(this.addPrefix(data, "basicMouldPojo."));
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return SUCCESS;
    }
    
    public String modifyTimeRuleById(){
    	int result = -1;
    	String msg = "";
		Map pageData = new HashMap();
    	try{
    		if (xwzcxtBasicImp.modifyTimeRuleById(basicMouldPojo) > 0){
    			result = 0;
				msg = "修改成功！";
    		}else{
    			result = -1;
				msg = "信息修改失败，请检查填写内容";
    		}
    	}catch(Exception e){
    		result = -1;
			msg = "信息修改失败，请检查填写内容";
    		e.printStackTrace();
    	}
    	pageData.put("result", result);
		pageData.put("msg", msg);
		this.formatData(pageData);
    	return SUCCESS;
    }
    
    public String deleteTimeruleById(){
    	HttpServletRequest request = ServletActionContext.getRequest();
    	int result = -1;
    	String msg = "";
		Map pageData = new HashMap();
		String id = request.getParameter("c_timerule_id");
		try{
			if(xwzcxtBasicImp.deleteTimeruleById(id) > 0){
				msg = "信息删除成功！";
				result = 0;
			}else{
				msg = "信息删除失败，请检查填写内容";
				result = -1;
			}
			
		}catch(Exception e){
			msg = "信息删除失败，请检查填写内容";
			result = -1;
			e.printStackTrace();
		}
    	pageData.put("msg", msg);
		pageData.put("result", result);
		this.formatData(pageData);
    	return SUCCESS;
    }
    
    public String submitStepData(){
    	HttpServletRequest request = ServletActionContext.getRequest();
    	int result = -1;
    	String msg = "";
		Map pageData = new HashMap();
		Map param = new HashMap();
		param.put("cid", request.getParameter("cid"));
		param.put("index", request.getParameter("index"));
		
		String area = request.getParameter("area");
		if (area.equals("undefined"))
			area = null;
		String obj = request.getParameter("obj");
		if (obj.equals("undefined"))
			obj = null;
		param.put("area", area);
		param.put("obj", obj);
		try{
			if(xwzcxtBasicImp.submitStepData(param) > 0){
				msg = "信息提交成功！";
				result = 0;
			}else{
				msg = "信息更新失败，请检查填写内容";
				result = -1;
			}
			
		}catch(Exception e){
			msg = "信息更新失败，请检查填写内容";
			result = -1;
			e.printStackTrace();
		}
    	pageData.put("msg", msg);
		pageData.put("result", result);
		this.formatData(pageData);
    	return SUCCESS;
    }
    
    
    @SuppressWarnings("rawtypes")
	public String getAllUsersForSel(){
    	try{
	    	List<BasicMouldPojo> list = xwzcxtBasicImp.getAllUsers();
	    	if (list != null && list.size()>0){
				RuleSelect wtree = new RuleSelect();
				wtree.setValue("userid");
				wtree.setText("displayname");
				List<Map> l = this.initSelectData(list, wtree);
				this.formatData(l);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
    	return SUCCESS;
    }
    
	//获取登录用户及其下属用户信息  GuveXie 20140814
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String getUsersByUpUserID(){
		HttpServletRequest request = ServletActionContext.getRequest();
	    HttpServletResponse response = ServletActionContext.getResponse();
	    Session session = SessionFactory.getInstance(request, response);
	    try {
	        Map<String, Object> userMap = (Map) session.getAttribute("USERSESSION"); 
	        if (userMap == null) {
	            this.setErrorMessage("用户Session已过期");
	        }
	        else {
	        	Map params = new HashMap();
	            params.put("uid", userMap.get("id").toString());//获取登录人ID
		    	List<User> list = xwzcxtBasicImp.getUsersByUpUserID(params);
		    	//this.formatData(list);
		    	if (list != null && list.size()>0){
		    		RuleTree wtree = new RuleTree();
			        wtree.setId("userId");
			        wtree.setVal("displayName");
			        wtree.setPid("majorOrgId");
			        List<Map> l = this.initTreeData(list, wtree);
					this.formatData(l);
				}
	        }
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}

	//获取登录用户及其下属用户信息  GuveXie 20140814 
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String getUsersListByUpUserID(){
		HttpServletRequest request = ServletActionContext.getRequest();
	    HttpServletResponse response = ServletActionContext.getResponse();
	    Session session = SessionFactory.getInstance(request, response);
	    try {
	        Map<String, Object> userMap = (Map) session.getAttribute("USERSESSION"); 
	        if (userMap == null) {
	            this.setErrorMessage("用户Session已过期");
	        }
	        else {
	        	Map params = new HashMap();
	            params.put("uid", userMap.get("id").toString());//获取登录人ID
		    	List<User> list = xwzcxtBasicImp.getUsersByUpUserID(params);
		    	if (list != null && list.size()>0){
		            RuleSelect st = new RuleSelect();
		            st.setText("displayName");
		            st.setValue("userId");
		            this.formatData(this.initSelectData(list, st));
				}
	        }
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
		
	
}
