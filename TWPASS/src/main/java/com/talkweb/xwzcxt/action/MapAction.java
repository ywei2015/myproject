package com.talkweb.xwzcxt.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.xwzcxt.service.MapServiceI;

public class MapAction extends baseAction {
	@Autowired
	private MapServiceI mapService;

	public String getAreaInfo() {
		HttpServletRequest request = ServletActionContext.getRequest();
		Map params = new HashMap();
		params.put("cAreaId", request.getParameter("cAreaId"));
		params.put("cObjectTypeid", request.getParameter("cObjectTypeid"));
		this.formatData(mapService.getAreaInfo(params));
		return SUCCESS;
	}

	public String getAreaIcon() {
		HttpServletRequest request = ServletActionContext.getRequest();
		Map params = new HashMap();
		params.put("cAreaId", request.getParameter("cAreaId"));
		params.put("cObjectTypeid", request.getParameter("cObjectTypeid"));
		this.formatData(mapService.getAreaIcon(params));
		return SUCCESS;
	}

	public String getAreaIconDetailById() {
		HttpServletRequest request = ServletActionContext.getRequest();
		Map params = new HashMap();
		params.put("cId", request.getParameter("cId"));
		params.put("cObjectTypeid", request.getParameter("cObjectTypeid"));
		this.formatData(mapService.getAreaIconDetailById(params));
		return SUCCESS;
	}
}