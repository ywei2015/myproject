package com.talkweb.xwzcxt.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.twdpe.core.log.Logger;
import com.talkweb.twdpe.core.log.LoggerFactory;
import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.twdpe.web.jcontrols.RuleTree;
import com.talkweb.twdpe.webcomponent.common.action.BusinessAction;
import com.talkweb.xwzcxt.pojo.IIAreaPojo;
import com.talkweb.xwzcxt.service.BaseInfoService;

/**
 * TODO(描述这个类的作用)
 * 
 * @author: 2014-8-2，GuveXie，（描述修改内容）
 */
@SuppressWarnings("rawtypes")
public class IIAreaAction extends BusinessAction {
	private static final long serialVersionUID = -860920841732194187L;
	private static final Logger logger = LoggerFactory.getLogger(IIAreaAction.class);

	@Autowired
	private BaseInfoService baseinfoservice;
	
	//获取所有的action
	@SuppressWarnings("unchecked")
	public String getAllAreaForTree(){
		try{
			HttpServletRequest request = ServletActionContext.getRequest();
			Map params = new HashMap();
			params.put("v_area_id", request.getParameter("v_area_id"));
			List<IIAreaPojo> list = (List<IIAreaPojo>) baseinfoservice.getAllArea(params);
			RuleTree wtree = new RuleTree();
	        wtree.setId("c_area_id");
	        wtree.setVal("c_area_name");
	        wtree.setPid("c_area_upid");
	        /*HashMap map = new HashMap();
	        map.put("c_area_code", "c_area_code");
	        map.put("c_area_fullname", "c_area_fullname");
	        map.put("c_scan_code", "c_scan_code");
	        map.put("c_area_upcode", "c_area_upcode");
	        map.put("c_node_level", "c_node_level");
	        map.put("c_area_type", "c_area_type");
	        map.put("c_remark", "c_remark");
	        wtree.setMap(map);*/
	        List<Map> l = this.initTreeData(list, wtree);
	        this.formatData(l);
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	//获取区域信息
	@SuppressWarnings("unchecked")
	public String getPageAreaById(){
		try{
			Map params = new HashMap();
			params.put("v_area_id", "");
			Pagination respagination = baseinfoservice.getPageArea(params, pagination);
			List tmpList = respagination.getList();
			this.formatDatagirdData(tmpList, respagination);
	        logger.debug("IIAreaInfo", "getAllAreaInfo.Action Exec Ok!");
		}catch(Exception e){
			e.printStackTrace();
		} 
		return SUCCESS;
	}
}
