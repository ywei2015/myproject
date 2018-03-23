package com.talkweb.twdpe.webcomponent.component;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.twdpe.base.right.pojo.App;
import com.talkweb.twdpe.base.right.pojo.Permission;
import com.talkweb.twdpe.base.right.service.AppService;
import com.talkweb.twdpe.base.right.service.PermissionService;
import com.talkweb.twdpe.core.log.Logger;
import com.talkweb.twdpe.core.log.LoggerFactory;
import com.talkweb.twdpe.core.util.StringUtil;
import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.twdpe.webcomponent.common.action.BusinessAction;

/**
 * 文件名称:    PermissionMngAction.java
 * 内容摘要: 
 * @author:   zhangwen 
 * @version:  1.0  
 * @Date:     2012-3-2 下午04:06:46  
 * 
 * 修改历史:  
 * 修改日期       修改人员   版本	   修改内容  
 * ----------------------------------------------  
 * 2012-3-2    zhangwen     1.0    1.0 XXXX
 *
 * 版权:   版权所有(C)2012
 * 公司:   拓维信息系统股份有限公司
 */
@SuppressWarnings("serial")
public class PermissionMngAction extends BusinessAction {
    private static final Logger logger = LoggerFactory.getLogger(PermissionMngAction.class);

    /**
     * 添加资源与访问模式的关联关系
     * */
    public String addPermission() {
	HttpServletRequest request = ServletActionContext.getRequest();
	String resourceCode = request.getParameter("resourceCode");
	String mode = request.getParameter("modes");
	String[] modes = StringUtils.split(mode, ",");

	List<Permission> permissions = new ArrayList<Permission>();
	for (String modeId : modes) {
	    Permission p = new Permission();
	    p.setModeId(modeId);
	    p.setResourceCode(resourceCode);
	    permissions.add(p);
	}
	int result = 0;
	try {
	    permissionService.addPermissions(permissions);
	    result = 1;
	} catch (Exception e) {
	    ServletActionContext.getRequest().setAttribute("throw", e);
	    logger.error(e.getMessage(), e);
	}
	this.setMessage(result, "PERMISSION", "ADD");
	// addSystemLog(LogConfigParam.MODULE_RESOURCE_ID,LogConfigParam.MODULE_RESOURCE_NAME,LogConfigParam.OPERATOR_TYPE_ADD,
	// jsonToString(JsonArray.toString(permissions)),
	// this.pageData.get("msg").toString());
	return SUCCESS;
    }

    /**
     * 删除资源与访问模式的关联关系
     * **/
    public String deletePermission() {
	HttpServletRequest request = ServletActionContext.getRequest();
	String id = request.getParameter("id");
	String[] ids = StringUtils.split(id, ",");
	List<Long> list = new ArrayList<Long>();
	for (String s : ids) {
	    if (StringUtil.isNumber(s))
		list.add(Long.parseLong(s));
	}
	int result = 0;
	try {
	    permissionService.deletePermissions(list);
	    result = 1;
	} catch (Exception e) {
	    ServletActionContext.getRequest().setAttribute("throw", e);
	    logger.error(e.getMessage(), e);
	}
	this.setMessage(result, "PERMISSION", "DELETE");
	// addSystemLog(LogConfigParam.MODULE_RESOURCE_ID,LogConfigParam.MODULE_RESOURCE_NAME,LogConfigParam.OPERATOR_TYPE_DELETE,
	// id, this.pageData.get("msg").toString());
	return SUCCESS;
    }

    /**
     * 分页查询权限，会查出对应的资源与访问方式
     * */
    @SuppressWarnings("unchecked")
    public String queryPermissions() {
	Pagination result = permissionService.queryPermissions(permission, pagination);
	List<Permission> list = result.getList();
	App app;
	for (Permission p : list) {
	    if (p.getMode() != null && p.getMode().getAppId() !=null){
	        app = appService.getApp(p.getMode().getAppId());
	        if(app.getAppName()!=null) p.getMode().setAppName(app.getAppName());
	    }
	}
	this.formatDatagirdData(list, result);
	return SUCCESS;
    }

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private AppService appService;

    private Permission permission;

    public Permission getPermission() {
	return permission;
    }

    public void setPermission(Permission permission) {
	this.permission = permission;
    }

}
