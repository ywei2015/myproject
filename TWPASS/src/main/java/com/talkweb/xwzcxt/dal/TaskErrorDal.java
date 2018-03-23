package com.talkweb.xwzcxt.dal;

import java.util.List;
import java.util.Map;

import com.talkweb.twdpe.dal.batis.spring.SessionDaoSupport;
import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.xwzcxt.pojo.TErrorFeedbackPojo;
import com.talkweb.xwzcxt.pojo.TErrorInfoPojo;

public class TaskErrorDal extends SessionDaoSupport {
	public Pagination getAllTaskErrorInfo(Map params, Pagination page) {
		List li = this.getSession().selectList("tErrorInfo.getAllTaskErrorInfoByConditions", params, page);
		if (li != null)
			page.setList(li);
		return page;
	}

	public List<Map> getAllTaskErrorInfoWithoutPage(Map params) {
		return this.getSession().selectList("tErrorInfo.getAllTaskErrorInfoByConditions", params);
	}

	public TErrorInfoPojo getTaskErrorInfoDetailByID(String cErrId) {
		return (TErrorInfoPojo) this.getSession().selectOne("tErrorInfo.getTaskErrorInfoDetailByID", cErrId);
	}

	public int deleteTaskErrorById(List<String> ids) {
		return this.getSession().delete("tErrorInfo.deleteTaskErrorById", ids);
	}

	public Map getDpUserByID(Long id) {
		return (Map) this.getSession().selectOne("tErrorInfo.getDpUserByID", id);
	}

	public String getTaskErrorFeedbackNumberByID(String cErrId) {
		return this.getSession().selectOne("tErrorFeedback.getTaskErrorFeedbackNumberByID", cErrId).toString();
	}

	public TErrorFeedbackPojo getTaskErrorFeedbackDetailByID(Map params) {
		return (TErrorFeedbackPojo) this.getSession().selectOne("tErrorFeedback.getTaskErrorFeedbackDetailByID", params);
	}

	public List getTaskErrorFeedbackLotnoByID(String cErrId) {
		return this.getSession().selectList("tErrorFeedback.getTaskErrorFeedbackLotnoByID", cErrId);
	}

	public List getTaskErrorFeedbackDetailByLotno(Map params) {
		return this.getSession().selectList("tErrorFeedback.getTaskErrorFeedbackDetailByLotno", params);
	}

	public List getTaskErrorAffixLotnoByID(String cErrId) {
		return this.getSession().selectList("tErrorAffix.getTaskErrorAffixLotnoByID", cErrId);
	}

	public List getTaskErrorAffixDescription(Map params) {
		return this.getSession().selectList("tErrorAffix.getTaskErrorAffixDescription", params);
	} 

	public List getTaskErrorAffixFile(Map params) {
		return this.getSession().selectList("tErrorAffix.getTaskErrorAffixFile", params);
	}

	public String getErreanameById(Map cErrId) {
		return (String)this.getSession().selectOne("tErrorAffix.getErreanameById", cErrId);
	}
}