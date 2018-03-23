package com.talkweb.xwzcxt.action;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.talkweb.twdpe.core.log.Logger;
import com.talkweb.twdpe.core.log.LoggerFactory;
import com.talkweb.twdpe.web.session.main.Session;
import com.talkweb.twdpe.web.session.util.SessionFactory;
import com.talkweb.twdpe.webcomponent.common.action.BusinessAction;
import com.talkweb.xwzcxt.pojo.CheckDetailsPojo;
import com.talkweb.xwzcxt.service.impl.CheckDetailsImpl;
import sun.misc.*;

public class CheckDetailsAction extends BusinessAction{
	private static final Logger logger = LoggerFactory.getLogger(XwzcxtMngAction.class);

	@Autowired
	private CheckDetailsImpl checkDetailsImpl;

	public String getCheckDetailsBillon() {
		HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);
		CheckDetailsPojo checkDetailsPojo = new CheckDetailsPojo();
		try {
			String c_task_id = request.getParameter("taskId");
			if (c_task_id == null) {
				return SUCCESS;
			}

			checkDetailsPojo = checkDetailsImpl.getCheckDetailsBillon(c_task_id);
			Map<String, String> pageData = new HashMap<String, String>();

			if (checkDetailsPojo != null) {
				pageData.put("c_ex_dataid", checkDetailsPojo.getC_ex_dataid());
				pageData.put("c_ex_type", checkDetailsPojo.getC_ex_type());
				pageData.put("billon", checkDetailsPojo.getBillon());
				pageData.put("billtype", checkDetailsPojo.getBilltype());
			}

			this.formatData(pageData); //后台传给前台的数据
		} catch (Exception e) {
			ServletActionContext.getRequest().setAttribute("throw", e);
			logger.error(e.getMessage(), e);
		}
		checkDetailsPojo = null;
		return SUCCESS;
	}

	public String getUserCode() {
		String userCode = "";
		String jiamiCode = "";
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpServletResponse response = ServletActionContext.getResponse();
			Session session = SessionFactory.getInstance(request, response);
			Map user = (Map) session.getAttribute("USERSESSION");

			if (user != null) {
				userCode = user.get("code").toString();
			} else {
				this.formatData("请先登录!");
				return SUCCESS;
			}
			jiamiCode = getBase64(userCode);
			this.formatData(jiamiCode);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage(), e);
			}
		return SUCCESS;
	}

	private String getBase64(String str) {
		byte[] b = null;
		String s = null;
		try {
			b = str.getBytes("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (b != null) {
			s = new BASE64Encoder().encode(b);
		}
		return s;
	}
}