package com.talkweb.xwzcxt.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.xwzcxt.pojo.TErrorInfoPojo;
import com.talkweb.xwzcxt.service.MobileErrInfoService;

public class MobileErrInfoAction extends baseAction {
	@Autowired
	private MobileErrInfoService mobileerrinfoservice;

	public String getTaskErrorInfoDetailByID() {
		HttpServletRequest request = ServletActionContext.getRequest();
		this.formatData(mobileerrinfoservice.getTaskErrorInfoDetailByID(request.getParameter("cErrId")));
		return SUCCESS;
	}

	public String getVerifyEvalInfo() {
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			TErrorInfoPojo t = mobileerrinfoservice.getVerifyEvalInfo(request.getParameter("errId"));
			this.formatData(t);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String getTaskErrorFeedbackDetailByID() {
		HttpServletRequest request = ServletActionContext.getRequest();
		this.formatData(mobileerrinfoservice.getTaskErrorFeedbackDetailByID(request.getParameter("cErrId")));
		return SUCCESS;
	}

	public String getTaskErrorFeedbackDetailByLotno() {
		HttpServletRequest request = ServletActionContext.getRequest();
		Map params = new HashMap();
		params.put("cErrId", request.getParameter("cErrId"));
		params.put("cFeedbackLotno", request.getParameter("cFeedbackLotno"));
		this.formatData(mobileerrinfoservice.getTaskErrorFeedbackDetailByLotno(params));
		return SUCCESS;
	}

	public String getTaskErrorAffixDetailByID() {
		HttpServletRequest request = ServletActionContext.getRequest();
		this.formatData(mobileerrinfoservice.getTaskErrorAffixDetailByID(request.getParameter("cErrId")));
		return SUCCESS;
	}

	/*GuveXie 20141216新增 */
	public String getErrorAffixDetailByIdAndLotNo() {
		HttpServletRequest request = ServletActionContext.getRequest();
		Map param = new HashMap();
		param.put("cErrId",request.getParameter("cErrId"));
		param.put("cLotNo",request.getParameter("cLotNo"));
		this.formatData(mobileerrinfoservice.getErrorAffixDetailByIdAndLotNo(param));
		return SUCCESS;
	}
}