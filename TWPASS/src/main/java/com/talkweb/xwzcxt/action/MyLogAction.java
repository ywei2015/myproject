package com.talkweb.xwzcxt.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.twdpe.base.dict.service.DictionaryService;
import com.talkweb.twdpe.base.right.service.AppService;
import com.talkweb.twdpe.base.right.service.ResourceService;
import com.talkweb.twdpe.core.util.DateUtil;
import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.twdpe.webcomponent.common.action.BusinessAction;
import com.talkweb.xwzcxt.pojo.MyLogPojo;
import com.talkweb.xwzcxt.service.MyLogService;

/**
 * 内容摘要:日志管理<br>
 * @author ZhangZhiheng
 * @版权 版权所有(C)2012<br>
 * @公司 拓维信息系统股份有限公司<br>
 */
@SuppressWarnings("serial")
public class MyLogAction extends BusinessAction {


	@Autowired
	private MyLogService logService;

	@Autowired
	private ResourceService resourceService;

	@Autowired
	private DictionaryService dictionaryService;

	@Autowired
	private AppService appService;

	private static final Logger logger = LoggerFactory.getLogger(MyLogAction.class);

	@SuppressWarnings("unchecked")
	public String queryLogByPage() {
		Map log = new HashMap();
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			if (pagination == null) {
				pagination = new Pagination(1, 10);
			}
			log.put("searchAccountID", request.getParameter("searchAccountID"));
			log.put("searchAccountName", request.getParameter("searchAccountName"));
			log.put("searchFuncGroupId", request.getParameter("searchFuncGroupId"));
			log.put("searchActionType", request.getParameter("searchActionType"));
			log.put("searchStatusCode", request.getParameter("searchStatusCode"));
			
			String searchStartDate = request.getParameter("searchStartDate");
			String searchEndDate = request.getParameter("searchEndDate");
			if (searchStartDate != null && !searchStartDate.isEmpty()) {
				searchStartDate = searchStartDate + " 00:00:00";
				log.put("searchStartDate", DateUtil.getTimestamp(DateUtil.formatStr2Date(searchStartDate)));
			}
			if (searchEndDate != null && !searchEndDate.isEmpty()) {
				searchEndDate = searchEndDate + " 23:59:59";
				log.put("searchEndDate", DateUtil.getTimestamp(DateUtil.formatStr2Date(searchEndDate)));
			}
			
			
			pagination = logService.getAllLogs(pagination, log);
			this.formatDatagirdData(pagination.getList(), pagination);
		} catch (Exception e) {
			ServletActionContext.getRequest().setAttribute("throw", e);
			logger.error(e.getMessage(), e);
		}
		return SUCCESS;
	}


	/**
	 * 查看日志详情。
	 */
	public String getLogInfo() {
		MyLogPojo log = new MyLogPojo();
		try {
			String logId = ServletActionContext.getRequest().getParameter("id");
			log.setLogid(logId);
			log = logService.getLogInfo(log);
			this.formatData(log);
		} catch (Exception e) {
			ServletActionContext.getRequest().setAttribute("throw", e);
			logger.error(e.getMessage(), e);
		}
		return SUCCESS;
	}


}
