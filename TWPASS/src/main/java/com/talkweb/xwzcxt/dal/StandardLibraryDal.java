package com.talkweb.xwzcxt.dal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.talkweb.twdpe.base.org.pojo.Org;
import com.talkweb.twdpe.dal.batis.spring.SessionDaoSupport;
import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.xwzcxt.pojo.Actnode;
import com.talkweb.xwzcxt.pojo.StandardFile;
import com.talkweb.xwzcxt.pojo.StandardLibraryPojo;

public class StandardLibraryDal extends SessionDaoSupport {
	@SuppressWarnings("unchecked")
	public List<StandardLibraryPojo> getSdFileSortByPid(String pid) {
		return this.getSession().selectList("standardLibrary.getSdFileSortByPid", pid);
	}

	public Pagination queryManagementProcess(StandardFile standardfile, Pagination page) {
		List li = this.getSession().selectList("standardLibrary.queryManagementProcess", standardfile, page);
		if (li != null)
			page.setList(li);
		return page;
	}

	public List getProcessNodeTree() {
		List li = this.getSession().selectList("standardLibrary.getProcessNodeTree");
		return li;
	}

	public List getStandardcontent(StandardFile standardfile) {
		List li = this.getSession().selectList("standardLibrary.getStandardcontent", standardfile);
		return li;
	}

	public List getAppendix(StandardFile standardfile) {
		List li = this.getSession().selectList("standardLibrary.getAppendix", standardfile);
		return li;
	}

	public String getMaxIdOfChildren(StandardLibraryPojo pojo) {
		Object obj = this.getSession().selectOne("standardLibrary.getMaxIdOfChildren", pojo);
		return (String)obj;
	}

	public void delStandardfile(StandardFile standardfile) {
		this.getSession().delete("standardLibrary.delStandardfile", standardfile);
	}

	public void delStandardcontent(StandardFile standardfile) {
		this.getSession().delete("standardLibrary.delStandardcontent", standardfile);
	}

	public void delStandardatt(StandardFile standardfile) {
		this.getSession().delete("standardLibrary.delStandardatt", standardfile);
	}

	public void delFile(StandardFile standardfile) {
		this.getSession().delete("standardLibrary.delFile", standardfile);
	}

	public void delStandardFileTreeNode(List<String> ids) {
		this.getSession().delete("standardLibrary.delStandardFileTreeNode", ids);
	}

	public void addStandardFileTreeNode(StandardLibraryPojo standardLibraryPojo) {
		this.getSession().insert("standardLibrary.addStandardFileTreeNode", standardLibraryPojo);
	}

	public void addActnode(Actnode actnode) {
		this.getSession().insert("standardLibrary.addActnode", actnode);
	}

	public void addActnodeItem(Actnode actnode) {
		this.getSession().insert("standardLibrary.addActnodeItem", actnode);
	}

	public Pagination queryActnode(Actnode actnode, Pagination page) {
		List li = this.getSession().selectList("standardLibrary.queryActnode", actnode, page);
		page.setList(li);
		return page;
	}

	public Pagination queryActnodeByParams(Map params, Pagination page) {
		List li =  this.getSession().selectList("standardLibrary.queryActnodeByParams", params, page);
		if (li != null)
			page.setList(li);
		return page;
	}	

	public List queryActnodeItem(Actnode actnode) {
		List li = this.getSession().selectList("standardLibrary.queryActnodeItem", actnode);
		return li;
	}

	public void updateStandardFileTreeNode(StandardLibraryPojo pojo) {
		this.getSession().update("standardLibrary.updateStandardFileTreeNode", pojo);
	}

	public String getNextActnodeId() {
		Object obj = this.getSession().selectOne("standardLibrary.getNextActnodeId");
		return (String)obj;
	}

	public Map delActNode(List<String> ids) {
		Map ret = new HashMap();
		List<Actnode> searchDelActNodeRet = this.searchDelActNode(ids);
		int type = this.getSession().delete("standardLibrary.delActNode", ids);
		ret.put("type", type);
		if(type != 0){
			ret.put("searchDelActNodeRet", searchDelActNodeRet);
			ret.put("searchDelActNodeRet_datafield", "T_SD_ACTNODE");
		}
		return ret;
	}
	
	/**
	 * @author ZhangZhiheng
	 * @param ids 与delActNode参数相同
	 * @return 返回删除的数据
	 */
	public List<Actnode> searchDelActNode(List<String> ids){
		return this.getSession().selectList("standardLibrary.searchDelActNode", ids);
	}

	public Map delActItem(List<String> ids) {
		Map ret = new HashMap();
		List<Actnode> searchDelActItemRet = this.searchDelActItem(ids);
		
		int type = this.getSession().delete("standardLibrary.delActItem", ids);
		ret.put("type", type);
		if(type != 0){
			ret.put("searchDelActItemRet", searchDelActItemRet);
			ret.put("searchDelActItemRet_datafield", "T_SD_ACTNODE_ITEM");
		}
		return ret;
	}

	/**
	 * @author ZhangZhiheng
	 * @param ids 与delActItem的参数一致
	 * @return 返回delActItem删除的数据
	 */
	public List<Actnode> searchDelActItem(List<String> ids){
		return this.getSession().selectList("standardLibrary.searchDelActItem",ids );
	}
	public Org queryDepartByPositionId(String id) {
		Map map = new HashMap();
		map.put("id", id);
		Object obj = this.getSession().selectOne("standardLibrary.queryDepartByPositionId", map);
		return (Org)obj;
	}

	public List queryActnodeListByParams(Actnode actnode) {
		return this.getSession().selectList("standardLibrary.queryActnodeListByParams", actnode);
	}

	public List queryActnodeListByParamsForPublic(Actnode actnode) {
		return this.getSession().selectList("standardLibrary.queryActnodeListByParamsForPublic", actnode);
	}

	public Map updateActNodeById(Actnode actNode) {
		Map ret = new HashMap();
		List<String> t = new ArrayList<String>();
		t.add(actNode.getC_actnode_id());
		List<Actnode> oldValue = this.searchDelActNode(t);
		int type = this.getSession().update("standardLibrary.updateActNodeByID", actNode);
		ret.put("type", type);
		if(type != 0){
			ret.put("oldValue", oldValue);
			ret.put("oldValue_datafield", "t_sd_actnode");
		}
		return ret;
	}

	public int updateActnodeItemById(Actnode actNode) {
		return this.getSession().update("standardLibrary.updateActnodeItemByID", actNode);
	}

	public int delActnodeItemByActNodeId(String id) {
		return this.getSession().update("standardLibrary.delActnodeItemByActNodeId", id);
	}
}