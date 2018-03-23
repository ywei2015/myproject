package com.talkweb.xwzcxt.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.talkweb.twdpe.base.org.pojo.Org;
import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.xwzcxt.pojo.Actnode;
import com.talkweb.xwzcxt.pojo.StandardFile;
import com.talkweb.xwzcxt.pojo.StandardLibraryPojo;

public interface StandardLibraryService {
	public List<StandardLibraryPojo> getSdFileSortByPid(String pid);
	public Pagination queryManagementProcess(StandardFile standardfile, Pagination page);
	public List getProcessNodeTree();
	public void delManagementProcess(StandardFile standardfile);
	public void delStandardFileTreeNode(List<String> ids);
	public List getStandardcontent(StandardFile standardfile);
	public List getAppendix(StandardFile standardfile);
	public String getMaxIdOfChildren(StandardLibraryPojo pojo);
	void addStandardFileTreeNode(StandardLibraryPojo standardLibraryPojo);
	void addActnode(Actnode actnode, HttpServletRequest request, HttpServletResponse response);
	void addActnodeItem(Actnode actnode, HttpServletRequest request, HttpServletResponse response);
	Pagination queryActnode(Actnode actnode, Pagination page);
	Pagination queryActnodeByParams(Map params, Pagination page);
	List queryActnodeItem(Actnode actnode);
	public void updateStandardFileTreeNode(StandardLibraryPojo pojo);
	public String getNextActnodeId();
	public void delActNode(String ids, HttpServletRequest request, HttpServletResponse response);
	public Org queryDepartByPositionId(String id);
	public List queryActnodeListByParams(Actnode actnode);
	public List queryActnodeListByParamsForPublic(Actnode actnode);
	public int updateActNodeById(Actnode actNode, HttpServletRequest request, HttpServletResponse response);
	public int updateActnodeItemById(Actnode actNode);
	public int delActnodeItemByActNodeId(String id);
}