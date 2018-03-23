package com.talkweb.xwzcxt.dal;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.talkweb.twdpe.dal.batis.spring.SessionDaoSupport;
import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.xwzcxt.pojo.TTaskPojo;

public class TaskDal extends SessionDaoSupport {
	public TTaskPojo getTaskByID(String id) {
		BigDecimal bd = new BigDecimal(id);
		return (TTaskPojo) (this.getSession().selectOne("taskManage.getTaskByID", bd));
	}

	@SuppressWarnings("unchecked")
	public List<TTaskPojo> getAllTask() {
		return (List<TTaskPojo>) (this.getSession().selectList("taskManage.getAllTask"));
	}

	public String getPositionNameByUserID(String id) {
		BigDecimal bd = new BigDecimal(id);
		return (String) (this.getSession().selectOne("taskManage.getPositionNameByUserid", bd));
	}

	@SuppressWarnings("unchecked")
	public Map<String,String> getDepartmentMapeByUserid(String id) {
		BigDecimal bd = new BigDecimal(id);
		return (Map<String,String>) (this.getSession().selectOne("taskManage.getDepartmentNameByUserid", bd));
	}

	@SuppressWarnings("unchecked")
	public Map<String,String> getDepartmentNameByID(String id) {
		BigDecimal bd = new BigDecimal(id);
		return (Map<String,String>) (this.getSession().selectOne("taskManage.getDepartmentNameByID", bd));
	}

	public Pagination getTasks(Map params, Pagination page) {
		List li = this.getSession().selectList("taskManage.getAllTaskByConditions", params, page);
		if (li != null)
			page.setList(li);
		return page;
	}

	public List<Map> getTasksWithoutPage(Map params) {
		return this.getSession().selectList("taskManage.getAllTaskByConditions", params);
	}

	public int deleteTaskById(List<String> ids) {
		return this.getSession().delete("taskManage.deleteTaskById", ids);
	}
	public boolean updatePanelMatterdetail(Map map){
	return getSession().update("taskManage.updatePanelMatterdetail", map) > 0 ? true
			: false;
	}
}