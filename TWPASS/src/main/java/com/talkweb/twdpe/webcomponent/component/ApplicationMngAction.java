package com.talkweb.twdpe.webcomponent.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.twdpe.base.common.exception.DataExistException;
import com.talkweb.twdpe.base.dict.pojo.DictEntryInfo;
import com.talkweb.twdpe.base.dict.service.DictionaryService;
import com.talkweb.twdpe.base.right.pojo.App;
import com.talkweb.twdpe.base.right.service.AppService;
import com.talkweb.twdpe.core.exception.BizBaseException;
import com.talkweb.twdpe.core.log.Logger;
import com.talkweb.twdpe.core.log.LoggerFactory;
import com.talkweb.twdpe.core.util.StringUtil;
import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.twdpe.web.jcontrols.RuleSelect;
import com.talkweb.twdpe.webcomponent.common.action.BusinessAction;
/**
 * 文件名称:    ApplicationMngAction.java
 * 内容摘要: 应用管理Action类
 * @author:   zhangwen 
 * @version:  1.0  
 * @Date:     2012-3-8 下午02:08:07  
 * 
 * 修改历史:  
 * 修改日期       修改人员   版本	   修改内容  
 * ----------------------------------------------  
 * 2012-3-8    zhangwen     1.0    1.0 XXXX
 *
 * 版权:   版权所有(C)2012
 * 公司:   拓维信息系统股份有限公司
 */
@SuppressWarnings("serial")
public class ApplicationMngAction extends BusinessAction {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationMngAction.class);

    /**
     * 分页查询应用列表
     * */
    @SuppressWarnings("unchecked")
    public String queryApps() {
	if (app == null)
	    app = new App();
	excludeNullProperties(app);
	Pagination result = appService.queryApps(app, pagination);
	List list = result.getList();

	List<DictEntryInfo> dictEntrys = this.getAppType();
	// 设置应用类型名称值
	if (dictEntrys != null && dictEntrys.size() > 0) {
	    for (Object o : list) {
		App a = (App) o;
		for (DictEntryInfo d : dictEntrys) {
		    if (a.getAppType().equals(d.getDictid()))
			a.setAppTypeName(d.getName());
		}
		if (a.getIsOpen() == 1)
		    a.setIsOpenName("开通");
		else
		    a.setIsOpenName("未开通");
	    }
	}

	this.formatDatagirdData(list, result);
	return SUCCESS;
    }

    /**
     * 增加应用
     * */
    public String addApp() {
		int result = 0;
		try {
		    if (!this.existSameRecord(app.getAppCode())) {
		    	app.setIsDelete(0);
		    	appService.addApp(app);
		    	result = 1;
		    } else
		    	result = 2;
		} catch (DataExistException e) {
		    ServletActionContext.getRequest().setAttribute("throw", e);
		    // TODO Auto-generated catch block
		    // e.printStackTrace();
		    logger.error(e.getMessage(), e);
		}
		this.setMessage(result, "APP", "ADD");
		return SUCCESS;
    }

    /**
     * 修改基本信息
     * */
    public String modifyApp() {
	int result = 0;
	try {

	    App a = appService.getApp(app.getAppId());
	    if (a.getAppCode().equals(app.getAppCode())) {
		appService.modifyApp(app);
		result = 1;
	    } else {
		if (this.existSameRecord(app.getAppCode()))
		    result = 2;
	    }
	} catch (Exception e) {
	    ServletActionContext.getRequest().setAttribute("throw", e);
	    // TODO Auto-generated catch block
	    // e.printStackTrace();
	    logger.error(e.getMessage(), e);
	}
	this.setMessage(result, "APP", "MODIFY");
	return SUCCESS;
    }

    /**
     * 删除多个应用
     * */
    public String removeApps() {
	int result = 0;
	HttpServletRequest request = ServletActionContext.getRequest();
	String id = request.getParameter("ids");
	String[] ids = StringUtils.split(id, ",");
	if (ids != null && ids.length > 0) {
	    List<Long> list = new ArrayList<Long>();
	    for (String s : ids) {
		if (StringUtil.isNumber(s))
		    list.add(Long.parseLong(s));
	    }

	    try {
		if (list.size() > 0) {
		    appService.removeApps(list);
		    result = 1;
		}
	    } catch (Exception e) {
	    ServletActionContext.getRequest().setAttribute("throw", e);
		// TODO Auto-generated catch block
		// e.printStackTrace();
		logger.error(e.getMessage(), e);
	    }
	}
	this.setMessage(result, "APP", "DELETE");
	// addSystemLog(LogConfigParam.MODULE_APP_ID,LogConfigParam.MODULE_APP_NAME,LogConfigParam.OPERATOR_TYPE_DELETE,
	// id, this.pageData.get("msg").toString());
	return SUCCESS;
    }

    /**
     * 得到应用
     * */
    @SuppressWarnings("unchecked")
    public String getAppById() {
	HttpServletRequest request = ServletActionContext.getRequest();
	String id = request.getParameter("id");
	if (StringUtil.isNumber(id)) {
	    App a = appService.getApp(Long.parseLong(id));
	    if (a.getIsOpen() == 1)
		a.setIsOpenName("开通");
	    else
		a.setIsOpenName("未开通");
	    List<DictEntryInfo> dictEntrys = this.getAppType();
	    if (dictEntrys != null && dictEntrys.size() > 0)
		for (DictEntryInfo d : dictEntrys) {
		    if (a.getAppType().equals(d.getDictid()))
			a.setAppTypeName(d.getName());
		}
	    Map m = null;
	    try {
		m = this.addPrefix(a, "app.");
	    } catch (Exception e) {
	    ServletActionContext.getRequest().setAttribute("throw", e);
		// TODO Auto-generated catch block
		// e.printStackTrace();
		logger.error(e.getMessage(), e);
	    }
	    this.formatData(m);
	}
	return SUCCESS;
    }

    /**
     * 通过应用编码判断是否存在相同的纪录
     * */
    @SuppressWarnings("unchecked")
    public String getAppByAppCode() {
	HttpServletRequest request = ServletActionContext.getRequest();
	String id = request.getParameter("id");
	HashMap pageData = new HashMap();
	pageData.put("exist", this.existSameRecord(id));
	return SUCCESS;
    }

    /**
     * 私有方法，根据应用编码判断是否有相同编码的纪录
     * */
    private boolean existSameRecord(String appCode) {
	boolean bl = false;
	App a = new App();
	a.setAppCode(appCode);
	List<App> list = appService.queryApps(a);
	if (list != null && list.size() > 0)
	    bl = true;
	return bl;
    }

    /**
     * 取应用类型
     * */
    private List<DictEntryInfo> getAppType() {
	List<DictEntryInfo> dictEntrys = null;
	try {
	    dictEntrys = dictionaryService.getDictEntrysByType("RIGHT_APP_TYPE");
	} catch (BizBaseException e) {
	    ServletActionContext.getRequest().setAttribute("throw", e);
	    logger.error(e.getMessage(), e);
	}
	return dictEntrys;
    }

    /**
	 * 
	 * */
    @SuppressWarnings("unchecked")
    public String initFromControl()
    {
	List<DictEntryInfo> list = this.getAppType();
	RuleSelect ruleAppType = new RuleSelect();
	ruleAppType.setText("name");
	ruleAppType.setValue("dictid");
	List<Map> listMap = new ArrayList<Map>();
	try {
		Map map = this.initformAttribute("app.appType", this.initSelectData(list, ruleAppType));
		listMap.add(map);
	} catch (Exception e) {
	    ServletActionContext.getRequest().setAttribute("throw", e);
	    logger.error(e.getMessage(), e);
	}
	this.formatData(listMap);
	return SUCCESS;
    }

    private App app;

    public App getApp() {
	return app;
    }

    public void setApp(App app) {
	this.app = app;
    }

    @Autowired
    private AppService appService;

    @Autowired
    private DictionaryService dictionaryService;

}
