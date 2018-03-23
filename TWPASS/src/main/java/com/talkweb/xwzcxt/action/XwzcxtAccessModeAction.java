package com.talkweb.xwzcxt.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.xwzcxt.common.XwzcxtCommon;
import com.talkweb.twdpe.base.right.pojo.AccessMode;
import com.talkweb.twdpe.base.right.service.AccessModeService;
import com.talkweb.twdpe.base.right.service.AppService;
import com.talkweb.twdpe.core.log.Logger;
import com.talkweb.twdpe.core.log.LoggerFactory;
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
public class XwzcxtAccessModeAction extends BusinessAction {
    /** @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) */
    private static final long serialVersionUID = 1L;

    private static final Logger logger = LoggerFactory.getLogger(XwzcxtAccessModeAction.class);

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
	    if(module.equals("safety"))
	    	moduleID = XwzcxtCommon.MODULE_P_ID;	   
	    if(module.equals("safety_basic"))
	    	moduleID = XwzcxtCommon.MODULE_B_ID;	
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
