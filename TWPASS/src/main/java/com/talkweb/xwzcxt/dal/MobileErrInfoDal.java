package com.talkweb.xwzcxt.dal;

import java.util.List;
import java.util.Map;

import com.talkweb.twdpe.dal.batis.spring.SessionDaoSupport;
import com.talkweb.xwzcxt.pojo.TErrorFeedbackPojo;
import com.talkweb.xwzcxt.pojo.TErrorInfoPojo;

public class MobileErrInfoDal extends SessionDaoSupport {
	public TErrorInfoPojo getTaskErrorInfoDetailByID(String errId) {
		return (TErrorInfoPojo) this.getSession().selectOne("MobileErrInfoMap.getTaskErrorInfoDetailByID", errId);
	}

	public TErrorInfoPojo getVerifyEvalInfo(String errId) {
		return (TErrorInfoPojo) this.getSession().selectOne("MobileErrInfoMap.getVerifyEvalInfo", errId);
	}

	public String getTaskErrorFeedbackNumberByID(String cErrId) {
		return this.getSession().selectOne("MobileErrInfoMap.getTaskErrorFeedbackNumberByID", cErrId).toString();
	}

	public TErrorFeedbackPojo getTaskErrorFeedbackDetailByID(Map params) {
		return (TErrorFeedbackPojo) this.getSession().selectOne("MobileErrInfoMap.getTaskErrorFeedbackDetailByID", params);
	}

	public List getTaskErrorFeedbackLotnoByID(String cErrId) {
		return this.getSession().selectList("MobileErrInfoMap.getTaskErrorFeedbackLotnoByID", cErrId);
	}

	public List getTaskErrorFeedbackDetailByLotno(Map params) {
		return this.getSession().selectList("MobileErrInfoMap.getTaskErrorFeedbackDetailByLotno", params);
	}

	public String getTaskErrorAffixDescription(String cErrId) {
		Object description = this.getSession().selectOne("MobileErrInfoMap.getTaskErrorAffixDescription", cErrId);
		if (null == description) {
			return "";
		} else {
			return description.toString();
		}
	}

	public List getTaskErrorAffixFile(String cErrId) {
		return this.getSession().selectList("MobileErrInfoMap.getTaskErrorAffixFile", cErrId);
	}

	/*GuveXie 20141216新增 */
	public String getErrorAffixDesByMap(Map param) {
		Object description = this.getSession().selectOne("MobileErrInfoMap.getErrorAffixDesByMap", param);
		if (null == description) {
			return "";
		} else {
			return description.toString();
		}
	}

	public List getErrorAffixFileByMap(Map param) {
		return this.getSession().selectList("MobileErrInfoMap.getErrorAffixFileByMap", param);
	}
}