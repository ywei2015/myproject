package com.talkweb.xwzcxt.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.xwzcxt.pojo.Actnode;
import com.talkweb.xwzcxt.pojo.TSdActnodePojo;

public interface TSdActnodeServiceI {
	public Pagination getAllActNodesByConditions(Actnode actnode, Pagination page);
	public List getAllActNodesByConditionsWithoutPage(Actnode actnode);
	public TSdActnodePojo getActNodeByID(String aid);
	public void addActnode(TSdActnodePojo actnode, HttpServletRequest request, HttpServletResponse response);
	public String getDepartmentPath(String positionId);
	public String getDepartmentName(String departId);
	public String selectPathNameByID(String departId);
}