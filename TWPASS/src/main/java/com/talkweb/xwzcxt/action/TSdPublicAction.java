package com.talkweb.xwzcxt.action;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.twdpe.web.jcontrols.RuleTree;
import com.talkweb.twdpe.web.session.main.Session;
import com.talkweb.twdpe.web.session.util.SessionFactory;
import com.talkweb.xwzcxt.pojo.Actnode;
import com.talkweb.xwzcxt.pojo.TSdPublicPojo;
import com.talkweb.xwzcxt.service.TSdPublicServiceI;

public class TSdPublicAction extends BusinessAction {
	@Autowired
	private TSdPublicServiceI tSdPublicServiceImpl;

	public String getPublicNodesTree() {
		List<TSdPublicPojo> li = tSdPublicServiceImpl.getPublicNodesTree();
		try {
			RuleTree wtree = new RuleTree();
			wtree.setId("cPublicId");
			wtree.setVal("cPublicSname");
			wtree.setPid("cPublicPid");
			List<Map> l = this.initTreeData(li, wtree);
			this.formatData(l);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String getPublicNodeById() {
		HttpServletRequest request = ServletActionContext.getRequest();
		this.formatData(tSdPublicServiceImpl.getPublicNodeById(request.getParameter("cPublicId")));
		return SUCCESS;
	}

	public String addPublicNode() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();

		//1-修改，2-新建
		int cModifyType = Integer.parseInt(request.getParameter("cModifyType"));

		TSdPublicPojo t = new TSdPublicPojo();
		t.setcPublicPid(request.getParameter("cPublicPid"));
		t.setcPublicCode(request.getParameter("cPublicCode"));
		t.setcSectionId(new BigDecimal(request.getParameter("cSectionId")));
		t.setcPublicSname(request.getParameter("cPublicSname"));
		t.setcPublicFname(request.getParameter("cPublicSname"));
		t.setcRemark(request.getParameter("cRemark"));

		Session session = SessionFactory.getInstance(request, response);
		Map user = (Map) session.getAttribute("USERSESSION");

		int n;
		//1-修改，2-新建
		switch (cModifyType) {
			case 1:
				t.setcPublicId(request.getParameter("cPublicId"));
				t.setcModifier(user.get("id").toString());
				t.setcModifytime(new Date());
				t.setcFlag(new BigDecimal(1));// 初始化为1
				t.setcIsvaild(new BigDecimal(1));// 初始化为1
				n = tSdPublicServiceImpl.updatePublicNodeById(t, request, response);

				this.setMessage(n, "PublicNode", "Modify");
				break;
			case 2:
				t.setcPublicId(UUID.randomUUID().toString());
				t.setcCreator(user.get("id").toString());
				t.setcCreatetime(new Date());
				t.setcFlag(new BigDecimal(1));// 初始化为1
				t.setcIsvaild(new BigDecimal(1));// 初始化为1
				n = tSdPublicServiceImpl.addPublicNode(t, request, response);

				this.setMessage(n, "PublicNode", "Add");
				break;
			default:
				break;
		}

		return SUCCESS;
	}

	public String deletePublicNodesById() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		Session session = SessionFactory.getInstance(request, response);
		Map user = (Map) session.getAttribute("USERSESSION");
		TSdPublicPojo t = new TSdPublicPojo();
		t.setcPublicId(request.getParameter("cPublicId"));
		t.setcModifier(user.get("id").toString());
		t.setcModifytime(new Date());
		t.setcFlag(new BigDecimal(0));// 更新为0
		t.setcIsvaild(new BigDecimal(0));// 更新为0
		tSdPublicServiceImpl.deletePublicNodesById(t, request, response);
		this.setMessage(1, "PublicNode", "Delete");
		return SUCCESS;
	}
}