package com.talkweb.xwzcxt.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.twdpe.web.session.main.Session;
import com.talkweb.twdpe.web.session.util.SessionFactory;
import com.talkweb.xwzcxt.service.TPanelServiceI;

public class TPanelAction extends baseAction {
	@Autowired
	private TPanelServiceI tPanelService;

	public String getPanelInfoCount() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		Session session = SessionFactory.getInstance(request, response);
		Map user = (Map) session.getAttribute("USERSESSION");
		if (user == null) {
			this.formatData("登录超时，请重新登录");
			return SUCCESS;
		}
		Map params = new HashMap();
		params.put("cUserId", user.get("id"));
		this.formatData(tPanelService.getPanelInfoCount(params));
		return SUCCESS;
	}

	public String getPanelInfoList() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		Session session = SessionFactory.getInstance(request, response);
		Map user = (Map) session.getAttribute("USERSESSION");
		if (user == null) {
			this.formatData("登录超时，请重新登录");
			return SUCCESS;
		}
		Map params = new HashMap();
		params.put("cUserId", user.get("id"));
		params.put("cPanelType", request.getParameter("cPanelType"));
		String isUnRead = request.getParameter("isUnRead");
		if (isUnRead.equals("1")) {
			params.put("cIsread", "0");
		}
		if (pagination == null) {
			pagination = new Pagination(1, 20);
		}
		Pagination page = tPanelService.getPanelInfoList(params, pagination);
		this.formatDatagirdData(page.getList(), page);
		return SUCCESS;
	}

	public String readInformation() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		Session session = SessionFactory.getInstance(request, response);
		Map user = (Map) session.getAttribute("USERSESSION");
		if (user == null) {
			this.formatData("登录超时，请重新登录");
			return SUCCESS;
		}
		Map params = new HashMap();
		params.put("cUserId", user.get("id"));
		params.put("cPanelId", request.getParameter("cPanelId"));
		tPanelService.readInformation(params);
		return SUCCESS;
	}
	
	public void getPanelData(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String userId = request.getParameter("userId");
	}
}