package com.talkweb.xwzcxt.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.twdpe.web.jcontrols.RuleTree;
import com.talkweb.xwzcxt.service.DpPositionServiceI;
import com.talkweb.xwzcxt.service.TaskSchedularService;
import com.talkweb.xwzcxt.vo.DpPositionVo;

public class DpPositionAction extends baseAction {
	@Autowired
	private DpPositionServiceI dpPositionService;

	@Autowired
	private TaskSchedularService taskSchedularServiceImpl;

	public String getPositionTreeByOrgID() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String orgid = request.getParameter("orgid");
		if (orgid != null && !orgid.isEmpty()) {
			List positionlist = this.dpPositionService
					.getDpPositionByOrgId(orgid);
			if (positionlist != null && positionlist.size() > 0) {
				RuleTree wtree = new RuleTree();
				wtree.setId("positionid");
				wtree.setVal("posiname");
				wtree.setPid("manaposi");
				try {
					List<Map> l = this.initTreeData(positionlist, wtree);
					this.formatData(l);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return SUCCESS;
	}

	public String getPositionByOrgIDforSelect() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String orgid = request.getParameter("orgid");
		if (orgid != null && !orgid.isEmpty()) {
			List positionlist = this.dpPositionService
					.getDpPositionByOrgId(orgid);
			if (positionlist != null && positionlist.size() > 0) {
				RuleTree wtree = new RuleTree();
				wtree.setId("positionid");
				wtree.setVal("posiname");
				wtree.setPid("manaposi");
				try {
					List<Map> l = this.initTreeData(positionlist, wtree);
					this.formatData(l);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return SUCCESS;
	}

	public String getAllDepart() {
		List l = this.taskSchedularServiceImpl.getOrganization();
		if (l != null && l.size() > 0) {
			RuleTree wtree = new RuleTree();
			wtree.setId("orgid");
			wtree.setVal("orgname");
			wtree.setPid("parentorgid");
			List<Map> list;
			try {
				list = this.initTreeData(l, wtree);
				this.formatData(list);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return SUCCESS;
	}

	public String getAllPosition() {
		List<DpPositionVo> positionlist = this.dpPositionService
				.getAllPositon();
		if (positionlist != null && positionlist.size() > 0) {
			RuleTree wtree = new RuleTree();
			// wtree.setId("positionid");POSICODE
			wtree.setId("posicode");
			wtree.setVal("posiname");
			wtree.setPid("upcode");
			// wtree.setPid("manaposi");
			try {
				List<Map> l = this.initTreeData(positionlist, wtree);
				this.formatData(l);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return SUCCESS;
	}

	public String getPositionByOrgID() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String orgid = request.getParameter("orgid");
		if (orgid != null && !orgid.isEmpty()) {
			List positionlist = this.dpPositionService
					.getDpPositionByOrgId(orgid);
			if (positionlist != null && positionlist.size() > 0) {
				this.formatData(positionlist);
			}
		}
		return SUCCESS;
	}

	public String getDepartByPositionID() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String pid = request.getParameter("pid");
		this.formatData(dpPositionService.getDepartByPositionID(pid));
		return SUCCESS;
	}

	public String getUserNameByID() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String pid = request.getParameter("pid");
		this.formatData(dpPositionService.getUserNameByID(pid));
		return SUCCESS;
	}
}