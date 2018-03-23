package com.talkweb.twdpe.webcomponent.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.twdpe.base.common.exception.ExistChildrenException;
import com.talkweb.twdpe.base.dict.pojo.DictEntryInfo;
import com.talkweb.twdpe.base.dict.service.DictionaryService;
import com.talkweb.twdpe.base.right.constant.RightConstants;
import com.talkweb.twdpe.base.right.pojo.App;
import com.talkweb.twdpe.base.right.pojo.Resource;
import com.talkweb.twdpe.base.right.service.AppService;
import com.talkweb.twdpe.base.right.service.AuthorityService;
import com.talkweb.twdpe.base.right.service.ResourceService;
import com.talkweb.twdpe.core.exception.BizBaseException;
import com.talkweb.twdpe.core.log.Logger;
import com.talkweb.twdpe.core.log.LoggerFactory;
import com.talkweb.twdpe.core.util.StringUtil;
import com.talkweb.twdpe.web.jcontrols.RuleNavigation;
import com.talkweb.twdpe.web.jcontrols.RuleSelect;
import com.talkweb.twdpe.web.jcontrols.RuleTree;
import com.talkweb.twdpe.webcomponent.common.action.BusinessAction;


/**
 * 文件名称:    ResourceMngAction.java
 * 内容摘要: 资源管理action类
 * @author:   zhangwen 
 * @version:  1.0  
 * @Date:     2012-3-1 上午09:44:30  
 * 
 * 修改历史:  
 * 修改日期       修改人员   版本	   修改内容  
 * ----------------------------------------------  
 * 2012-3-1    zhangwen     1.0    1.0 XXXX
 *
 * 版权:   版权所有(C)2012
 * 公司:   拓维信息系统股份有限公司
 */
@SuppressWarnings("serial")
public class ResourceMngAction extends BusinessAction {
    private static final Logger logger = LoggerFactory.getLogger(ResourceMngAction.class);

    /**
     * 显示资源树
     * **/
    @SuppressWarnings("unchecked")
    public String showResourceTree() {
	HttpServletRequest request = ServletActionContext.getRequest();
	String appid = request.getParameter("appid");
	App app = new App();
	if (StringUtil.isNumber(appid))
	    app.setAppId(Long.parseLong(appid.trim()));
	// 根据条件取相关的应用
	List<App> apps = appService.queryApps(app);
	List<Map> list = new ArrayList<Map>();
	RuleTree w = new RuleTree();
	w.setId("resourceCode");
	w.setPid("parentCode");
	w.setVal("resourceName");
	Resource resource = new Resource();
	for (App a : apps) {
	    HashMap appmap = new HashMap();
	    appmap.put("id", "app_" + a.getAppId());
	    appmap.put("pid", "-1");
	    appmap.put("val", a.getAppName());
	    // appmap.put("font", "font2");
	    appmap.put("ico", "ico4");
	    appmap.put("appid", a.getAppId());
	    appmap.put("type", "A");
	    list.add(appmap);// 添加应用节点
	    resource.setAppId(a.getAppId());
	    List<Resource> resources = resourceService.queryResources(resource);
	    try {
		List<Map> maps = this.initTreeData(resources, w);
		if (maps != null)
		    for (Map m : maps) {
			if (m.get("pid").toString().equals("-1")) {
			    m.put("pid", "app_" + a.getAppId());
			}
			m.put("ico", "ico10");
			m.put("type", "R");
			m.put("appid", a.getAppId());
			list.add(m);
		    }
	    } catch (Exception e) {
	    ServletActionContext.getRequest().setAttribute("throw", e);
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
	this.formatData(list);
	return SUCCESS;
    }

    /**
     * 添加资源
     * */
    public String addResource() {
	if (resource.getResourceCode().equals(resource.getParentCode()))// 判断父资源代码是否为自己
		this.setMessage(3, "RESOURCE", "ADD");
	if (this.existSameRecord(resource.getResourceCode()))// 判断是否存在相同的纪录
		this.setMessage(2, "RESOURCE", "ADD");
	else {
	    if (resource.getParentCode() == null)
		resource.setParentCode("-1");
	    try {
		resourceService.addResource(resource);
		this.formatData(this.addPrefix(resource, "resource."));
	    } catch (Exception e) {
	    ServletActionContext.getRequest().setAttribute("throw", e);
		logger.error(e.getMessage(), e);
		this.setErrorMessage("添加资源操作失败", e);
	    }
	}
	return SUCCESS;
    }

    /**
     * 修改资源
     * */
    public String modifyResource() {
	HttpServletRequest request = ServletActionContext.getRequest();
	int result = 0;
	String oldCode = request.getParameter("oldCode");
	if (resource.getResourceCode().equals(resource.getParentCode()))// 判断父资源代码是否为自己
	{
		this.setMessage(3, "RESOURCE", "MODIFY");
	} else if (!oldCode.equals(resource.getResourceCode()) && this.existSameRecord(resource.getResourceCode()))// 判断是否存在相同编码的纪录
	{
		this.setMessage(2, "RESOURCE", "MODIFY");
	} else {
	    if (resource.getParentCode() == null)
		resource.setParentCode("-1");
	    try {
		resourceService.modifyResource(resource);
		this.formatData(this.addPrefix(resource, "resource."));
	    } catch (Exception e) {
	    ServletActionContext.getRequest().setAttribute("throw", e);
		logger.error(e.getMessage(), e);
		this.setErrorMessage("修改资源操作失败", e);
	    }
	}
	// addSystemLog(LogConfigParam.MODULE_RESOURCE_ID,LogConfigParam.MODULE_RESOURCE_NAME,LogConfigParam.OPERATOR_TYPE_MODIFY,
	// jsonToString(JsonObject.toString(resource)),
	// this.data.get("msg").toString());
	return SUCCESS;
    }

    /**
     * 删除资源
     * */
    public String deleteResource() {
	HttpServletRequest request = ServletActionContext.getRequest();
	String resourceCode = request.getParameter("id");
	int result = 0;
	try {
	    resourceService.deleteResource(resourceCode);
	    result = 1;
	} catch (ExistChildrenException e) {
	    ServletActionContext.getRequest().setAttribute("throw", e);
	    logger.error(e.getMessage(), e);
	}
	this.setMessage(result, "RESOURCE", "DELETE");
	return SUCCESS;
    }

    /**
     * 根据编码判断是否存在相同编码的纪录
     * */
    private boolean existSameRecord(String resourceCode) {
	boolean bl = false;
	Resource resource = resourceService.getResource(resourceCode);
	if (resource != null)
	    bl = true;
	return bl;
    }

    /**
     * 根据资源编码取资源纪录，如果mode不为空，则判断纪录存不存在
     * */
    @SuppressWarnings("unchecked")
    public String getResourceById()
    {
        HttpServletRequest request = ServletActionContext.getRequest();
        String resourceCode = request.getParameter("id");
        String mode = request.getParameter("mode");
        Resource resource = resourceService.getResource(resourceCode);
        if (mode != null)
        {
            HashMap data = new HashMap<String, Boolean>();
            if (resource != null && resource.getResourceCode() != null)
                data.put("exist", true);
            else
                data.put("exist", false);
        }
	    else
	    {
	        try
	        {
		        Map m = this.addPrefix(resource, "resource.");
		        if(resource.getAppId() != null)
		        {
			        m.put("resource.appId",resource.getAppId().toString());
			        App app = appService.getApp(resource.getAppId());
			        if (app != null)
			        {
			            m.put("resource.appName",app.getAppName());
			        }
		        }
		        formatData(m);
	        }
	        catch (Exception e)
	        {
	            ServletActionContext.getRequest().setAttribute("throw",e);
		        e.printStackTrace();
	        }
	    }
	    return SUCCESS;
    }

    /**
     * 初始化表单
     * */
    @SuppressWarnings("unchecked")
    public String initForm() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String APPID = request.getParameter("appid");
	
		// 设置应用下拉列表属性规则
		RuleSelect appRuleSelect = new RuleSelect();
		appRuleSelect.setText("appName");
		appRuleSelect.setValue("appId");
		// 查找所有的应用
		List<App> apps = appService.getApps();
	
		// 设置资源树属性规则
		RuleTree ruleTree = new RuleTree();
		ruleTree.setId("resourceCode");
		ruleTree.setPid("parentCode");
		ruleTree.setVal("resourceName");
	
		// 设置资源类型下拉列表属性规则
		RuleSelect ruleSelect = new RuleSelect();
		ruleSelect.setText("name");
		ruleSelect.setValue("dictid");
	
		List<DictEntryInfo> dictEntry = null;
		List<Map> maps = new ArrayList<Map>();
		try {
		    dictEntry = dictionaryService.getDictEntrysByType("RIGHT_RESOURCE_TYPE");
		} catch (BizBaseException e1) {
		    ServletActionContext.getRequest().setAttribute("throw", e1);
		    logger.error(e1.getMessage(), e1);
		}
		try {
		    // 设置表单所属应用初始化属性
		    Map m1 = this.initformAttribute("resource.appId", this.initSelectData(apps, appRuleSelect));
		    maps.add(m1);
		    // 设置表单资源类型属性
		    Map m2 = this.initformAttribute("resource.resourceType", this.initSelectData(dictEntry, ruleSelect));
		    maps.add(m2);
		} catch (Exception e) {
		    ServletActionContext.getRequest().setAttribute("throw", e);
		    logger.error(e.getMessage(), e);
		}
		if (APPID != null) {
		    Resource r = new Resource();
		    r.setAppId(Long.parseLong(APPID));
		    List<Resource> resources = resourceService.queryResources(r);
		    // 设置表单上级资源初始化属性
		    try {
			Map m3 = this.initformAttribute("resource.parentCode", this.initTreeData(resources, ruleTree));
			maps.add(m3);
		    } catch (Exception e) {
		    ServletActionContext.getRequest().setAttribute("throw", e);
			logger.error(e.getMessage(), e);
		    }
		}
	
		this.formatData(maps);
		return SUCCESS;
    }
    
    public List<Resource> getRootResource(Long userId) {
    	List<Resource >resources = resourceService.getRootResourcesByType("menu");
		resources = authorityService.filterResources(userId, resources, "view");
		return resources;
    }
    
    public String showTopMenu() {
    	List<Resource> resources = null;
    	Map<String, Object> user = this.getUserSession();
    	try {
			if (user != null) {
				resources = getRootResource(Long.parseLong(user.get("id").toString()));
			} else {
				resources = new ArrayList<Resource>();
			}
		} catch (Exception e) {
			ServletActionContext.getRequest().setAttribute("throw", e);
		    logger.error(e.getMessage(),e);
			resources = new ArrayList<Resource>();
		}
    	this.formatData(resources);
		return SUCCESS;
    }

    /**
     * 菜单方法
     * */
    @SuppressWarnings("unchecked")
    public String showMenu() {
		HttpServletRequest request = ServletActionContext.getRequest();
		List<Resource> list = new ArrayList<Resource>();
		Map<String, Object> user = this.getUserSession();
		if (user != null) {
			try{
			    String parentCode = request.getParameter("parentCode");
			    String appId = request.getParameter("appid");
				if(StringUtil.isEmpty(parentCode)) {
					List<Resource> rootResources = getRootResource(1L);
					if(rootResources.size() > 0) {
						Resource resource = rootResources.get(0);
						parentCode = resource.getResourceCode();
						appId = resource.getAppId() + "";
					}
				}
			    List<Resource> resources;
			    if (!StringUtil.isEmpty(parentCode))
			    {
			        resources = resourceService.getResourceList(Long.parseLong(appId),parentCode,RightConstants.RESOURCE_TYPE_MENU);
			        list = authorityService.filterResources(Long.parseLong(user.get("id").toString()), resources, "view"); // 根据用户权限与访问方式(查看)过滤菜单资源	    
			        for(Resource resource : resources) {
				    	if(resource.getParentCode().equals(parentCode)) {
				    		resource.setParentCode("-1");
				    	}
				    }
			    }
			}catch(Exception e) {
				e.printStackTrace();
				ServletActionContext.getRequest().setAttribute("throw", e);
			    logger.error(e.getMessage(),e);
			    return SUCCESS;
			}
		}
		List<Map> menu = null;
		if(list!=null && list.size()>0){
			RuleNavigation ruleNavigation = new RuleNavigation();
			ruleNavigation.setId("resourceCode");
			ruleNavigation.setGroup("parentCode");
			ruleNavigation.setText("resourceName");
			ruleNavigation.setUrl("url");
			ruleNavigation.setIco("icon");
			try {
			    menu = this.initNavigationData(list, ruleNavigation);
			    if(menu!=null){
				    for (Map m : menu) {
						if (m.get("url") == null || m.get("url").toString().trim().length() == 0) {
						    m.put("url", "");
						    continue;
						}
						// 给URL增加工程上下文路径
						String url = m.get("url").toString();
						if(url.toLowerCase().startsWith("http://")||url.startsWith("/"))//以“http://”or "/"绝对路径开头,不做处理
						{
						    continue; 
						
						}else
						{
						    url = request.getContextPath() +"/"+ url; //以相对路径开头，加上上下文路径。
						}
						m.put("url", url);
				    }
			    }
			} catch (Exception e) {
				e.printStackTrace();
			    ServletActionContext.getRequest().setAttribute("throw", e);
			    logger.error(e.getMessage(),e);
			}
		}
		this.formatData(menu);		 
	 return SUCCESS;
    }

    /**
     * 构造日志查询的菜单树
     * */
    @SuppressWarnings("unchecked")
    public String showMenuTree() {
	HttpServletRequest request = ServletActionContext.getRequest();
	String APPID = request.getParameter("appid");
	Resource r = new Resource();
	r.setAppId(Long.parseLong(APPID));
	r.setResourceType(RightConstants.RESOURCE_TYPE_MENU);
	List<Resource> list = resourceService.queryResources(r);

	List<Map> menu = null;
	try {
	    // 设置树控件数据映射关系
	    RuleTree wtree = new RuleTree();
	    wtree.setId("resourceCode");
	    wtree.setVal("resourceName");
	    wtree.setPid("parentCode");

	    // 格式化树控件数据,按树控件的ID，VAL，PID的顺序
	    menu = this.initTreeData(list, wtree);
	} catch (Exception e) {
	    ServletActionContext.getRequest().setAttribute("throw", e);
	    e.printStackTrace();
	}
	this.formatData(menu);

	return SUCCESS;
    }

    /**
     * 初始化资源表单资源下拉树数据
     * */
    public String initResourceFormTree() {
	RuleTree ruleTree = new RuleTree();
	ruleTree.setId("resourceCode");
	ruleTree.setPid("parentCode");
	ruleTree.setVal("resourceName");

	HttpServletRequest request = ServletActionContext.getRequest();
	String APPID = request.getParameter("appid");
	Resource r = new Resource();
	r.setAppId(Long.parseLong(APPID));
	List<Map> resources = new ArrayList<Map>();	
	List<Resource> list = resourceService.queryResources(r);
	if(list!=null && list.size()>0){
		try {
		    resources = this.initTreeData(list, ruleTree);
		} catch (Exception e) {
		    ServletActionContext.getRequest().setAttribute("throw", e);
		    logger.error(e.getMessage(), e);
		}
	}
	this.formatData(resources);
	return SUCCESS;
    }
    
    /**
     * 获取某个应用下的所有根资源列表
     * */    
    public String queryResources(){
    	HttpServletRequest request = ServletActionContext.getRequest();
    	String APPID = request.getParameter("appid");
    	String parentCode = request.getParameter("parentCode");
    	List<Map> result = new ArrayList<Map>(0);
    	if(StringUtil.isNumber(APPID)){
    		RuleNavigation ruleNavigation = new RuleNavigation();
    		ruleNavigation.setId("resourceCode");
    		ruleNavigation.setGroup("parentCode");
    		ruleNavigation.setText("resourceName");
    		ruleNavigation.setUrl("url");    		
    		Resource r = new Resource();
    		r.setAppId(Long.parseLong(APPID));
    		r.setResourceType(RightConstants.RESOURCE_TYPE_MENU);
    		r.setParentCode(parentCode);
    		List<Resource> list = resourceService.queryResources(r);
    		try {
				result = this.initNavigationData(list, ruleNavigation);
			} catch (Exception e) {
				ServletActionContext.getRequest().setAttribute("throw", e);
				logger.error(e.getMessage(),e);
			}
    	}
    	this.formatData(result);
    	return SUCCESS;
    }

    @Autowired
    private AppService appService;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private DictionaryService dictionaryService;

    @Autowired
    private AuthorityService authorityService;

    private Resource resource;

    public Resource getResource() {
	return resource;
    }

    public void setResource(Resource resource) {
	this.resource = resource;
    }
}
