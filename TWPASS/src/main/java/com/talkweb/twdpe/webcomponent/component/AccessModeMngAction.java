package com.talkweb.twdpe.webcomponent.component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.twdpe.base.common.exception.DataExistException;
import com.talkweb.twdpe.base.log.pojo.LogConfigParam;
import com.talkweb.twdpe.base.right.pojo.AccessMode;
import com.talkweb.twdpe.base.right.pojo.App;
import com.talkweb.twdpe.base.right.service.AccessModeService;
import com.talkweb.twdpe.base.right.service.AppService;
import com.talkweb.twdpe.core.log.Logger;
import com.talkweb.twdpe.core.log.LoggerFactory;
import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.twdpe.web.jcontrols.RuleSelect;
import com.talkweb.twdpe.webcomponent.common.action.BusinessAction;

/**
 * 文件名称:    AccessModeMngAction.java
 * 内容摘要: 访问模式管理Action类
 * @author:   zhangwen 
 * @version:  1.0  
 * @Date:     2012-2-29 上午09:24:32  
 * 
 * 修改历史:  
 * 修改日期       修改人员   版本	   修改内容  
 * ----------------------------------------------  
 * 2012-2-29    zhangwen     1.0    1.0 XXXX
 *
 * 版权:   版权所有(C)2012
 * 公司:   拓维信息系统股份有限公司
 */
public class AccessModeMngAction extends BusinessAction {
    /** @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) */
    private static final long serialVersionUID = 1L;

    private static final Logger logger = LoggerFactory.getLogger(AccessModeMngAction.class);

    /**
     * 分页查询访问模式
     */
    @SuppressWarnings("unchecked")
    public String queryModes() {
	if (accessMode == null)
	    accessMode = new AccessMode();
	excludeNullProperties(accessMode);
	Pagination result = accessModeService.queryModes(accessMode, pagination);
	List list = result.getList();
	List<App> apps = appService.getApps();
	for (Object o : list) {
	    AccessMode am = (AccessMode) o;
	    Iterator<App> it = apps.iterator();
	    while (it.hasNext()) {
		App a = (App) it.next();
		if (a.getAppId().longValue() == am.getAppId().longValue())
		    am.setAppName(a.getAppName());
	    }
	}
	this.formatDatagirdData(list, result);
	accessMode = null;
	return SUCCESS;
    }

    /**
     * 修改访问模式
     * @throws DataExistException 
     */
    public String modifyMode() throws DataExistException
    {
        HttpServletRequest request = ServletActionContext.getRequest();
        String oldid = request.getParameter("oldid");
        int result = 0;
        if (!oldid.equals(accessMode.getModeId())
            && this.existSameRecord(accessMode.getModeId()))
        {
            result = 2;
        }
        else
        {
            accessModeService.modifyMode(accessMode);
            result = 1;
        }
        setMessage(result,"ACCESSMODE","MODIFY");
        return SUCCESS;
    }

    /**
     * 增加访问模式
     * @throws Exception 
     */
    public String addMode() throws Exception
    {
        int result = 0;
        if (existSameRecord(accessMode.getModeId()))
        {
            result = 2;
        }
        else
        {
            accessModeService.addMode(accessMode);
            result = 1;
        }
        setMessage(result, "ACCESSMODE", "ADD");
        return SUCCESS;
    }

    /**
     * 根据ID判断是否存在相同的访问模式纪录
     * */
    private boolean existSameRecord(String modeId) {
	boolean result = false;
	AccessMode am = accessModeService.getMode(modeId);
	if (am != null && !StringUtils.isEmpty(am.getModeId()))
	    result = true;
	return result;
    }

    /**
     * 删除访问模式
     */
    public String deleteMode()
    {
        int result = 0;
        HttpServletRequest request = ServletActionContext.getRequest();
        String id = request.getParameter("id");
        if (!StringUtils.isEmpty(id))
        {
            String[] ids = StringUtils.split(id, ",");
            List<String> list = Arrays.asList(ids);
            accessModeService.deleteModes(list);
            result = 1;
            setMessage(result, "ACCESSMODE", "DELETE");
        }
        return SUCCESS;
    }

    @SuppressWarnings("unchecked")
    public String initForm() throws Exception
    {
        // 设置应用下拉列表属性规则
        RuleSelect appRuleSelect = new RuleSelect();
        appRuleSelect.setText("appName");
        appRuleSelect.setValue("appId");
        // 查找所有的应用
        List<App> apps = appService.getApps();
        // 设置表单所属应用初始化属性
        List<Map> listMap = new ArrayList<Map>();
        Map m = initformAttribute("accessMode.appId",
            initSelectData(apps, appRuleSelect));
        listMap.add(m);
        
        formatData(listMap);
        return SUCCESS;
    }

    /**
     * 根据访问方式ID查找相应的纪录
     * @throws Exception 
     * */
    @SuppressWarnings("unchecked")
    public String getAccessModeById() throws Exception {
	HttpServletRequest request = ServletActionContext.getRequest();
	String id = request.getParameter("id");
	String mode = request.getParameter("mode");
	AccessMode am = accessModeService.getMode(id);
	if (am != null && am.getAppId().longValue() > 0) {
	    App app = appService.getApp(am.getAppId());
	    am.setAppName(app.getAppName());
	}
	if (StringUtils.isEmpty(mode)) {
	    Map m = null;
		m = addPrefix(am, "accessMode.");

		formatData(m);
	} else {
	    HashMap pageData = new HashMap();
	    if (am != null && !StringUtils.isEmpty(am.getModeId()))
		pageData.put("exist", true);
	    else
		pageData.put("exist", false);
	}
	return SUCCESS;
    }

    /**
     * 根据资源取访问模式
     * **/
    @SuppressWarnings("unchecked")
    public String getAccessModeByResource() {
	HttpServletRequest request = ServletActionContext.getRequest();
	String resourceCode = request.getParameter("id");
	if (resourceCode != null) {
	    Pagination result = accessModeService.queryResourceAccessModes(resourceCode, pagination);
	    this.formatDatagirdData(result.getList(), result);
	} else
	    this.formatDatagirdData(new ArrayList(), pagination);
	return SUCCESS;
    }

    /**
     * 取资源没有关联的访问模式
     * */
    @SuppressWarnings("unchecked")
    public String getExtraneousAccessModeByResouce() {
	HttpServletRequest request = ServletActionContext.getRequest();
	String resourceCode = request.getParameter("id");
	if (resourceCode != null) {
	    Pagination result = accessModeService.queryResourceNotAccessModes(resourceCode, pagination);
	    this.formatDatagirdData(result.getList(), result);
	} else
	    this.formatDatagirdData(new ArrayList(), pagination);
	return SUCCESS;
    }

    /***
     * 
     * 获取当前用户对应用资源的所有有权的访问方式ID
     * */
    public String getPrivilegeResourceModes() {
	HttpServletRequest request = ServletActionContext.getRequest();
	String module = request.getParameter("module");
	List<String> list = null;
	if (module != null) {
	    String moduleID = "";
	    // 应用模块
	    if (module.equals("app"))
		moduleID = LogConfigParam.MODULE_APP_ID;
	    // 角色模块
	    if (module.equals("role"))
		moduleID = LogConfigParam.MODULE_ROLE_ID;
	    // 数据规则模块
	    if (module.equals("dataright"))
		moduleID = LogConfigParam.MODULE_DATARULE_ID;
	    // 资源模块
	    if (module.equals("resource"))
		moduleID = LogConfigParam.MODULE_RESOURCE_ID;
	    // 访问方式模块
	    if (module.equals("accessmode"))
		moduleID = LogConfigParam.MODULE_MODE_ID;
	    if (module.equals("dic"))
	        moduleID = LogConfigParam.MODULE_DICT_ID;
	    if (module.equals("user"))
		moduleID = LogConfigParam.MODULE_USER_ID;
	    if (module.equals("position"))
		moduleID = LogConfigParam.MODULE_POSITION_ID;
	    if (module.equals("org"))
		moduleID = LogConfigParam.MODULE_ORG_ID;
	    if (module.equals("area"))
		moduleID = LogConfigParam.MODULE_AREA_ID;
	    if (module.equals("permission"))
		moduleID = LogConfigParam.MODULE_PERMISSION_ID;
	    if (module.equals("ip"))
		moduleID = LogConfigParam.MODULE_IP_ID;
	    if (module.equals("rejectip"))
		moduleID = LogConfigParam.MODULE_REJECT_IP_ID;
	    if(module.equals("log"))
	    	moduleID = LogConfigParam.MODULE_LOG_ID;
	    if (moduleID.length() > 0)
		list = this.getPrivilegeResourceModes(moduleID);
	}
	this.formatData(list);
	return SUCCESS;
    }

    @Autowired
    private AccessModeService accessModeService;

    @Autowired
    private AppService appService;

    private AccessMode accessMode;

    public AccessMode getAccessMode() {
	return accessMode;
    }

    public void setAccessMode(AccessMode accessMode) {
	this.accessMode = accessMode;
    }

}
