package com.talkweb.xwzcxt.dal;

import java.util.List;

import com.talkweb.twdpe.dal.batis.spring.SessionDaoSupport;
import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.xwzcxt.pojo.Actnode;
import com.talkweb.xwzcxt.pojo.TSdActnodePojo;

public class TSdActnodeDal extends SessionDaoSupport {
	public Pagination getAllActNodesByConditions(Actnode actnode, Pagination page) {
		List li = this.getSession().selectList("TSdActnode.getAllActNodesByConditions", actnode, page);
		if (li != null)
			page.setList(li);
		return page;
	}

	public List getAllActNodesByConditionsWithoutPage(Actnode actnode) {
		return this.getSession().selectList("TSdActnode.getAllActNodesByConditions", actnode);
	}

	public TSdActnodePojo getActNodeByID(String aid) {
		return (TSdActnodePojo) this.getSession().selectOne("TSdActnode.selectActNodesByID", aid);
	}

	public void addActnode(TSdActnodePojo actnode) {
		this.getSession().insert("TSdActnode.addActnode", actnode);
	}

	//部门班组PATH
	public String getDepartmentPath(String positionId) {
		return (String) this.getSession().selectOne("TSdActnode.selectPathById", positionId);
	}

	public String getDepartmentName(String departId) {
		return (String) this.getSession().selectOne("TSdActnode.selectDepartNameById", departId);
	}

	public String selectPathNameByID(String departId) {
		return (String) this.getSession().selectOne("TSdActnode.selectPathNameByID", departId);
	}
}