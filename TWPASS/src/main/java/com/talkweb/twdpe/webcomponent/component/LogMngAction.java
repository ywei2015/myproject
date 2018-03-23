package com.talkweb.twdpe.webcomponent.component;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.twdpe.base.dict.pojo.DictEntryInfo;
import com.talkweb.twdpe.base.dict.service.DictionaryService;
import com.talkweb.twdpe.base.log.pojo.LogConfigParam;
import com.talkweb.twdpe.base.log.pojo.LogInfo;
import com.talkweb.twdpe.base.log.service.LogService;
import com.talkweb.twdpe.base.right.constant.RightConstants;
import com.talkweb.twdpe.base.right.pojo.App;
import com.talkweb.twdpe.base.right.pojo.Resource;
import com.talkweb.twdpe.base.right.service.AppService;
import com.talkweb.twdpe.base.right.service.ResourceService;
import com.talkweb.twdpe.core.log.Logger;
import com.talkweb.twdpe.core.log.LoggerFactory;
import com.talkweb.twdpe.core.util.StringUtil;
import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.twdpe.web.commons.StaticUploadInfo;
import com.talkweb.twdpe.web.jcontrols.RuleSelect;
import com.talkweb.twdpe.web.jcontrols.RuleTree;
import com.talkweb.twdpe.webcomponent.common.action.BusinessAction;

/**
 * 内容摘要:日志管理<br>
 * <br>
 * @author zhangwen
 * @version 1.3.0
 * @date 2012 11:08:10 AM
 * 
 * @修改历史<br> 修改日期 修改人员 版本 修改内容<br>
 * -----------------------------------------
 * 
 * <br>
 * 2012-5-7 chengwei
 * @版权 版权所有(C)2012<br>
 * @公司 拓维信息系统股份有限公司<br>
 */
@SuppressWarnings("serial")
public class LogMngAction extends BusinessAction {

	private static final Logger logger = LoggerFactory.getLogger(LogMngAction.class);

	private LogInfo log;

	@Autowired
	private LogService logService;

	@Autowired
	private ResourceService resourceService;

	@Autowired
	private DictionaryService dictionaryService;

	@Autowired
	private AppService appService;

	@SuppressWarnings("unchecked")
	public String queryLogByPage() {
		try {
			if (pagination == null) {
				pagination = new Pagination(1, 10);
			}
			if (log == null) {
				log = new LogInfo();
				log.setActionType(LogConfigParam.OPERATION_TYPE_LOGIN);
				log.setAccountType(LogConfigParam.OPERATOR_SYS);
				log.setFuncGroupId(LogConfigParam.MODULE_USER_ID);
			}
			this.excludeNullProperties(log);
			pagination = logService.queryLogsByPage(log, pagination);
			DictEntryInfo dict = null;
			for (LogInfo log : ((List<LogInfo>) pagination.getList())) {
				log.setType(log.getActionType());
				dict = dictionaryService.getDictEntryById("LOG_OPERATION_TYPE", log.getActionType());
				if (dict != null) {
					log.setActionType(dict.getValue());
				}
			}
			this.formatDatagirdData(pagination.getList(), pagination);
		} catch (Exception e) {
			ServletActionContext.getRequest().setAttribute("throw", e);
			logger.error(e.getMessage(), e);
		}
		return SUCCESS;
	}

	/**
	 * 初始化日志查询表单
	 * **/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String initLogQueryForm() {
		List list = new ArrayList();
		// 取得组织类型列表中原始数据
		// 设置下拉列表属性规则
		try {
			RuleSelect ruleSelect = new RuleSelect();
			ruleSelect.setText("name");
			ruleSelect.setValue("dictid");
			// 根据字典类型取用户类型字典数据
			List<DictEntryInfo> accountType = dictionaryService.getDictEntrysByType("LOG_OPERATOR_TYPE");
			// 设置表单取值类型初始化属性
			list.add(this.initformAttribute("log.accountType", this.initSelectData(accountType, ruleSelect)));
			// 根据字典类型取用户类型字典数据
			List<DictEntryInfo> operationType = dictionaryService.getDictEntrysByType("LOG_OPERATION_TYPE");
			// 设置操作类型初始化属性
			list.add(this.initformAttribute("log.actionType", this.initSelectData(operationType, ruleSelect)));
		} catch (Exception e) {
			ServletActionContext.getRequest().setAttribute("throw", e);
			logger.error(e.getMessage(), e);
		}
		this.formatData(list);
		return SUCCESS;
	}

	/**
	 * 导出日志记录
	 * **/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String exportLogData() {
		Map pageData = new HashMap();
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			String attrs = request.getParameter("LOGATTRS");
			String path = ServletActionContext.getServletContext().getRealPath(StaticUploadInfo.upload_system_export);
			File file = new File(path);
			if (!file.exists()) {
				file.mkdirs();
			}
			List<String> attrList = new ArrayList<String>();
			String fileName = null;
			if (attrs != null && !"".equals((attrs = attrs.trim()))) {
				for (String attr : attrs.split(",")) {
					attrList.add(attr);
				}
				fileName = logService.exportLogsWithAttrs(log, attrList, path);
			} else {
				fileName = logService.exportLogs(log, path);
			}
			if (fileName != null) {
				pageData.put("RESULT", "1");
				pageData.put("filePath", StaticUploadInfo.upload_system_export + fileName);
				pageData.put("fileName", fileName);
			} else {
				pageData.put("RESULT", "0");
			}
		} catch (Exception e) {
			ServletActionContext.getRequest().setAttribute("throw", e);
			pageData.put("RESULT", "-1");
			logger.error(e.getMessage(), e);
		}
		this.formatData(pageData);
		return SUCCESS;
	}

	/**
	 * 查看日志详情。
	 */
	public String getLogInfo() {
		try {
			String logId = ServletActionContext.getRequest().getParameter("id");
			String type = ServletActionContext.getRequest().getParameter("type");
			if (type == null || "".equals(type = type.trim())) {
				log = logService.getLogById(logId);
			} else {
				log = logService.getLogByIdWithType(logId, type);
			}
			if (log != null) {
				DictEntryInfo dict = dictionaryService.getDictEntryById("LOG_OPERATION_TYPE",
						log.getActionType());
				if (dict != null) {
					log.setActionType(dict.getValue());
				}
				if ("1".equals(log.getStatusCode())) {
					log.setStatusCode("成功");
				} else if ("2".equals(log.getStatusCode())) {
					log.setStatusCode("失败");
				}
			}
			this.formatData(log);
		} catch (Exception e) {
			ServletActionContext.getRequest().setAttribute("throw", e);
			logger.error(e.getMessage(), e);
		}
		return SUCCESS;
	}

	/**
	 * 获取日志属性。
	 */
	public String getLogAttributes() {
		try {
			List<Map<String, String>> attrList = new ArrayList<Map<String, String>>();
			for (Map<String, String> attr : logService.getLogAttributes()) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("value", attr.keySet().iterator().next());
				map.put("text", attr.values().iterator().next());
				attrList.add(map);
			}
			this.formatData(attrList);
		} catch (Exception e) {
			ServletActionContext.getRequest().setAttribute("throw", e);
			logger.error(e.getMessage(), e);
		}
		return SUCCESS;
	}

	/**
	 * 构造日志查询的菜单树
	 */
	@SuppressWarnings("all")
	public String showMenuTree() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String appid = request.getParameter("appid");
		App app = new App();
		if (StringUtil.isNumber(appid)) {
			app.setAppId(Long.parseLong(appid.trim()));
		}
		// 根据条件取相关的应用
		List<Map> list = new ArrayList<Map>();
		RuleTree w = new RuleTree();
		w.setId("resourceCode");
		w.setPid("parentCode");
		w.setVal("resourceName");
		Resource resource = new Resource();
		List<Resource> resources = null;
		List<Map> maps = null;
		for (App a : appService.queryApps(app)) {
			HashMap appmap = new HashMap(5);
			appmap.put("id", "app_" + a.getAppId());
			appmap.put("pid", "-1");
			appmap.put("val", a.getAppName());
			// appmap.put("font", "font2");
			appmap.put("type", "A");
			//appmap.put("ico", "ico4");
			list.add(appmap);// 添加应用节点
			resource.setAppId(a.getAppId());
			resource.setResourceType(RightConstants.RESOURCE_TYPE_MENU);
			resources = resourceService.queryResources(resource);
			try {
				maps = this.initTreeData(resources, w);
				if (maps != null)
					for (Map m : maps) {
						if (m.get("pid").toString().equals("-1")) {
							m.put("pid", "app_" + a.getAppId());
						}
						m.put("type", "R");
						//m.put("ico", "ico10");
						m.put("appid", a.getAppId());
						list.add(m);
					}
			} catch (Exception e) {
				ServletActionContext.getRequest().setAttribute("throw", e);
				e.printStackTrace();
			}
		}
		this.formatData(list);
		return SUCCESS;
	}

	public void setLog(LogInfo log) {
		this.log = log;
	}

	public LogInfo getLog() {
		return log;
	}
}
