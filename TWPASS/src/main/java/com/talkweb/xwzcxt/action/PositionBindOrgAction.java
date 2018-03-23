package com.talkweb.xwzcxt.action;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.talkweb.twdpe.base.org.pojo.Org;
import com.talkweb.twdpe.base.org.pojo.Position;
import com.talkweb.twdpe.base.org.service.OrgService;
import com.talkweb.twdpe.base.org.service.PositionService;
import com.talkweb.twdpe.core.log.Logger;
import com.talkweb.twdpe.core.log.LoggerFactory;
import com.talkweb.twdpe.web.jcontrols.RuleTree;
import com.talkweb.xwzcxt.pojo.OrgBindPositionPojo;
import com.talkweb.xwzcxt.service.impl.PositionBindOrgServiceImpl;

public class PositionBindOrgAction extends BusinessAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8111299464764530121L;
	
	private static final Logger logger = LoggerFactory.getLogger(PositionBindOrgAction.class);
	@Autowired
    private PositionService positionService;
	@Autowired
	private OrgService orgService;
	@Autowired
	private PositionBindOrgServiceImpl positionBindOrgServiceImpl;

	public PositionService getPositionService() {
		return positionService;
	}

	public void setPositionService(PositionService positionService) {
		this.positionService = positionService;
	}
	
	public OrgService getOrgService() {
		return orgService;
	}

	public void setOrgService(OrgService orgService) {
		this.orgService = orgService;
	}
	
	public PositionBindOrgServiceImpl getPositionBindOrgServiceImpl() {
		return positionBindOrgServiceImpl;
	}

	public void setPositionBindOrgServiceImpl(
			PositionBindOrgServiceImpl positionBindOrgServiceImpl) {
		this.positionBindOrgServiceImpl = positionBindOrgServiceImpl;
	}

	/*
	private List<Org> initFirstNode(List<Org> orgs, boolean filter) {
		List<Long> ids = new ArrayList<Long>();
		for (Org org : orgs) {
			if (!ids.contains(org.getOrgId())) {
				ids.add(org.getOrgId());
			}
		}
		List<Org> list = new ArrayList<Org>();
		for (Org org : orgs) {
			if (!ids.contains(org.getParentOrgId())) {
				org.setParentOrgId(-1l);
				list.add(org);
			} else if(!filter) {
				list.add(org);
			}
		}
		return list;
	}
	*/

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected List<Map> initTreeAsynData(List<OrgBindPositionPojo> list) {
		List<Map> l = new ArrayList<Map>();
		if (list != null) {
			for (OrgBindPositionPojo org : list) {
				Map map = new HashMap();
				map.put("id", org.getDataid());
				map.put("val", org.getDataname());
				map.put("pid", org.getPid());
				if (org.getIsLeaf() != null && !org.getIsLeaf()) {
					map.put("isleaf", false);
					map.put("asyn", "true");
				} else {
					map.put("isleaf", true);
					map.put("asyn", "false");
				}
				map.put("font", "font"+org.getDtype());
				l.add(map);
			}
		}
		return l;
	}

	//org:要判断的组织节点
	private boolean isLeaf(Org org,List<Position> posList){
		Org o = new Org();
		o.setParentOrgId(org.getOrgId());
		List<Org> orgList=orgService.queryOrgs(o);
		//如果组织下有组织
		if(orgList.size()>0){
			return false;
		}
		//如果组织下有岗位
		for(Position pos:posList){
			if(pos.getOrgId().equals(org.getOrgId())){
				return false;
			}
		}
		return true;
	}

	@SuppressWarnings({ "unchecked", "unused" })
	public String getAllPosition(){
		HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(
				ServletActionContext.HTTP_REQUEST);
		String pid = request.getParameter("id");
		/*
		Org o = new Org();
		//根节点ID
		if(pid==null) {
			pid = "-1";
		}
		o.setParentOrgId(new Long(pid));
		Pagination page = new Pagination(1, 1000);
		//如果父节点为-1（根节点），则查所有，否则，查父节点为该节点的所有节点
		List<Org> list = orgService.queryOrgs(o, page).getList();
		List<Position> posList=positionService.getPositions();
		for(Org org:list){
			if(this.isLeaf(org, posList)){
				org.setIsLeaf(true);
			}
			else{
				org.setIsLeaf(false);
			}
		}
		if("-1".equals(pid)) {
			//对其进行过滤
			list = this.initFirstNode(list, true);
		}
		
		if(!"-1".equals(pid)){
			for(int i=0;i<posList.size();i++){
				Position position=posList.get(i);
				Org tempOrg=new Org();
				tempOrg.setOrgId(position.getPositionId());
				tempOrg.setOrgName(position.getPosiName());
				tempOrg.setParentOrgId(position.getOrgId());
				/*
				if(this.isLeaf(tempOrg, posList)){
					tempOrg.setIsLeaf(true);
				}
				else{
					tempOrg.setIsLeaf(false);
				}
				System.out.println(tempOrg.getParentOrgId()+"====="+pid);
				if((tempOrg.getParentOrgId().toString()).equals(pid)){
					list.add(tempOrg);
				}
			}
		}
		 */
		if(pid==null) {
			pid = "1--1";
		}
		List<Org> totalList=positionBindOrgServiceImpl.unionPositionAndOrg();
		List<OrgBindPositionPojo> list=new ArrayList<OrgBindPositionPojo>();
		for(Org org:totalList){
			OrgBindPositionPojo pojo=(OrgBindPositionPojo)org;
			if(pojo.getPid().equals(pid)){
				list.add(pojo);
			}
		}
		signal:
		for(int i=0;i<list.size();i++){
			OrgBindPositionPojo org=(OrgBindPositionPojo)list.get(i);
			for(int j=0;j<totalList.size();j++){
				OrgBindPositionPojo o=(OrgBindPositionPojo)totalList.get(j);
				if(o.getPid().equals(org.getDataid())){
					System.out.println(o.getParentOrgId()+"======"+org.getOrgId());
					org.setIsLeaf(false);
					continue signal;
				}
			}
			org.setIsLeaf(true);
		}
		this.formatData(this.initTreeAsynData(list));
		return SUCCESS;
	}

	public String getPositionInOrg() throws UnsupportedEncodingException
	{
		HttpServletRequest request = ServletActionContext.getRequest();
		Map params = new HashMap();
		params.put("orgid", request.getParameter("orgid"));
		params.put("orgname", request.getParameter("orgname"));
		params.put("orgtype", request.getParameter("orgtype"));
		List<Org> orglist = positionBindOrgServiceImpl.unionPositionAndOrg(params);

		try {
			// 设置树控件数据映射关系
			RuleTree wtree = new RuleTree();
			wtree.setId("dataid");
			wtree.setVal("dataname");
			wtree.setPid("pid");

			// 格式化树控件数据,按树控件的ID，VAL，PID的顺序
			List<Map> l = this.initTreeData(orglist, wtree);
			this.formatData(l);
		} catch (Exception e) {
			request.setAttribute("throw", e);
			// e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		return SUCCESS;
	}

	public String getPositionId()
	{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		Map map = (HashMap) session.getAttribute("USERSESSION");
        this.formatData(map);
        return SUCCESS;
	}
	
	/**
	 * 初始化组织树
	 * 
	 * @return
	 */
	/*
	public String initFirstGradeOrgTree() {
		try {
			HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(
					ServletActionContext.HTTP_REQUEST);
			String orgID = request.getParameter("orgID");
			Org org = new Org();
			List<Org> orgTreeData = null;
			if(orgID == null) {
                orgID = "-1";
            }
            if (StringUtil.isNumber(orgID)) {
                org.setOrgId(Long.parseLong(orgID));
                orgTreeData = orgService.getParentAndSiblingOrgs(org);
                List<Position> posList=positionService.getPositions();
    			for(int i=0;i<posList.size();i++){
    				Position position=posList.get(i);
    				Org tempOrg=new Org();
    				tempOrg.setOrgId(position.getPositionId());
    				tempOrg.setOrgName(position.getPosiName());
    				tempOrg.setParentOrgId(position.getOrgId());
    				orgTreeData.add(tempOrg);
    			}
            } else {
                org.setParentOrgId(-1L);
                pagination = new Pagination(1, 10000);
                orgTreeData = orgService.queryOrgs(org, pagination).getList();
            }		
			orgTreeData = this.initFirstNode(orgTreeData, false);		
			this.formatData(this.initTreeAsynData(orgTreeData));
		} catch (Exception e) {
			ServletActionContext.getRequest().setAttribute("throw", e);
			this.setErrorMessage(e);
		}
		return SUCCESS;
	}
	*/
}