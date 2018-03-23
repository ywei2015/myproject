package com.talkweb.xwzcxt.dal;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.talkweb.twdpe.dal.batis.spring.SessionDaoSupport;
import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.xwzcxt.pojo.TSTempletPojo;
import com.talkweb.xwzcxt.pojo.TStdtaskplanSeries;
import com.talkweb.xwzcxt.vo.STempletVo;

public class STempletDal extends SessionDaoSupport {
	public Pagination getAllTaskTemplet(Map params, Pagination page) {
		List li = this.getSession().selectList("sTemplet.getAllTaskTemplet", params, page);
		if (li != null)
			page.setList(li);
		return page;
	}

	public List<TSTempletPojo> getTaskTempletByActnodeID(String id) {
		return this.getSession().selectList("sTemplet.getTaskTempletByActnodeID", id);
	}

	public Map addTaskTemplet(Map params) {
		Map r = new HashMap();
		int type = this.getSession().insert("sTemplet.addTaskTemplet", params);
		r.put("type", type);
		r.put("obj", params);
		r.put("obj_datafield", "TWXWZC.T_SD_TASK_TEMPLET");
		return r;
	}

	public TSTempletPojo getTaskTempletById(String tid) {
		return (TSTempletPojo) this.getSession().selectOne("sTemplet.getTaskTempletById", tid);
	}

	public Map deleteTaskTempletById(String tid) {
		Map ret = new HashMap();
		TSTempletPojo oldValue = this.getTaskTempletById(tid);
		int type = this.getSession().delete("sTemplet.deleteTempletByID", tid);
		ret.put("deleteTaskTempletByIdType", type);
		if(type != 0){
			ret.put("deleteTaskTempletByIdOldValue", oldValue);
			ret.put("deleteTaskTempletByIdOldValue_datafield", "TWXWZC.T_SD_TASK_TEMPLET");
		}
		return ret;
	}

	public int deleteTaskTempletByActNodeID(List<String> ids) {
		return this.getSession().delete("sTemplet.deleteTaskTempletByActNodeID", ids);
	}

	public Map updateTempletById(Map params) {
		Map ret = new HashMap();
		TSTempletPojo oldValue = this.getTaskTempletById(params.get("cTempletId").toString());
		int type = this.getSession().update("sTemplet.updateTempletByID", params);
		ret.put("type", type);
		if(type != 0){
			ret.put("oldValue", oldValue);
			ret.put("oldValue_datafield", "TWXWZC.T_SD_TASK_TEMPLET");
			ret.put("params", params);
		}
		return ret;
	}

	public int addTempletByCopy(STempletVo sTempletVo) {
		return this.getSession().update("sTemplet.addTempletByCopy", sTempletVo);
	}

	public BigDecimal getNextTaskId() {
		return (BigDecimal) this.getSession().selectOne("sTemplet.getNextTaskId");
	}

	public void generateRandomTask(Map params) {
		this.getSession().selectOne("sTemplet.generateRandomTask", params);
	}

	public Pagination getAllPlanSeriesByPid(Map params, Pagination page) {
		List li = this.getSession().selectList("TStdtaskplanSeries.selectByPositionID", params, page);
		if (li != null)
			page.setList(li);
		return page;
	}

	public Pagination getAllPlanSeries(Map params, Pagination page) {
		List li = this.getSession().selectList("TStdtaskplanSeries.getAllPlanSeries", params, page);
		if (li != null)
			page.setList(li);
		return page;
	}

	public TStdtaskplanSeries getPlanSeriesDetailByID(String pid) {
		return (TStdtaskplanSeries) this.getSession().selectOne("TStdtaskplanSeries.getPlanSeriesDetailByID", pid);
	}

	public int deletePlanSeriesById(String pid) {
		return this.getSession().delete("TStdtaskplanSeries.deletePlanSeriesById", pid);
	}

	public Map deletePlanSeriesByTaskTempletID(List<String> tids) {
		Map ret = new HashMap();
		List<TStdtaskplanSeries> oldValue = this.searchDeletePlanSeriesByTaskTempletID(tids);
		int type = this.getSession().delete("TStdtaskplanSeries.deletePlanSeriesByTaskTempletID", tids);
		ret.put("type", type);
		if(type != 0){
			ret.put("oldValue", oldValue);
			ret.put("oldValue_datafield", "TWXWZC.T_STDTASKPLAN_SERIES");
		}
		return ret;
	}
	/**
	 * @author ZhangZhiheng
	 * @param tids 与deletePlanSeriesByTaskTempletID参数一致
	 * @return 返回deletePlanSeriesByTaskTempletID删除的数据
	 */
	public List<TStdtaskplanSeries> searchDeletePlanSeriesByTaskTempletID(List<String> tids){
		return this.getSession().selectList("TStdtaskplanSeries.searchDeletePlanSeriesByTaskTempletID", tids);
	}
}