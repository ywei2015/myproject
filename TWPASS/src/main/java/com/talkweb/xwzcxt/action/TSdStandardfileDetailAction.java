package com.talkweb.xwzcxt.action;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.twdpe.core.util.DateUtil;
import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.twdpe.web.session.main.Session;
import com.talkweb.twdpe.web.session.util.SessionFactory;
import com.talkweb.xwzcxt.pojo.TSdStandardfileDetailPojo;
import com.talkweb.xwzcxt.service.TSdStandardfileDetailI;

public class TSdStandardfileDetailAction extends baseAction {
	@Autowired
	private TSdStandardfileDetailI tSdStandardfileDetail;

	public String getAllTSdStandardfileByConditions() {
		HttpServletRequest request = ServletActionContext.getRequest();
		Map params = new HashMap();
		params.put("cSortId", request.getParameter("cSortId"));
		params.put("cSfileVersion", request.getParameter("cSfileVersion"));
		params.put("cSfileName", request.getParameter("cSfileName"));
		String fwQcbm = request.getParameter("cFwQcbm");
		if (fwQcbm != null && !fwQcbm.isEmpty()) {
			params.put("cFwQcbm", fwQcbm.replaceAll("1-", ""));
		}
		params.put("cSystag", request.getParameter("cSystag"));
		String starttime = request.getParameter("cStartTime");
		String endtime = request.getParameter("cEndTime");
		if (starttime != null && !starttime.isEmpty()) {
			starttime = starttime + " 00:00:00";
			params.put("cStartTime", DateUtil.getTimestamp(DateUtil.formatStr2Date(starttime)));
		}
		if (endtime != null && !endtime.isEmpty()) {
			endtime = endtime + " 23:59:59";
			params.put("cEndTime", DateUtil.getTimestamp(DateUtil.formatStr2Date(endtime)));
		}
		params.put("cStatus", request.getParameter("cStatus"));
		if (pagination == null) {
			pagination = new Pagination(1, 20);
		}
		Pagination page = tSdStandardfileDetail.getAllTSdStandardfileByConditions(params, pagination);
		this.formatDatagirdData(page.getList(), page);
		return SUCCESS;
	}

	public String getTSdStandardfileDetailById() {
		HttpServletRequest request = ServletActionContext.getRequest();
		this.formatData(tSdStandardfileDetail.getTSdStandardfileDetailById(request.getParameter("cFiledetailId")));
		return SUCCESS;
	}

	public String addTSdStandardfile() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();

		//0-查看，1-修改，2-新建
		int cModifyType = Integer.parseInt(request.getParameter("cModifyType"));

		TSdStandardfileDetailPojo t = new TSdStandardfileDetailPojo();
		t.setcSortId(new BigDecimal(request.getParameter("cSortId")));
		t.setcSfileVersion(request.getParameter("cSfileVersion"));
		t.setcSfileName(request.getParameter("cSfileName"));
		String fwQcbm = request.getParameter("cFwQcbm");
		if (fwQcbm != null && !fwQcbm.isEmpty()) {
			t.setcFwQcbm(fwQcbm.replaceAll("1-", ""));
		}
		t.setcSystag(new BigDecimal(request.getParameter("cSystag")));
		t.setcStatus(new BigDecimal(request.getParameter("cStatus")));
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		try {
			t.setcStatusTime(sdf.parse(request.getParameter("cStatusTime")));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		t.setcFileUrl(request.getParameter("cFileUrl"));
		t.setcIsdelete(new BigDecimal(0));

		Session session = SessionFactory.getInstance(request, response);
		Map user = (Map) session.getAttribute("USERSESSION");

		int n;
		switch (cModifyType) {
			case 1:
				t.setcFiledetailId(request.getParameter("cFiledetailId"));
				t.setcModifier(user.get("id").toString());
				t.setcModifytime(new Date());

				n = tSdStandardfileDetail.updateTSdStandardfileById(t, request, response);
				this.setMessage(n, "Standardfile", "Modify");
				break;
			case 2:
				t.setcFiledetailId(UUID.randomUUID().toString());
				t.setcCreator(user.get("id").toString());
				t.setcCreatetime(new Date());

				n = tSdStandardfileDetail.addTSdStandardfile(t, request, response);
				this.setMessage(n, "Standardfile", "Add");
				break;
			default:
				break;
		}

		return SUCCESS;
	}

	public String deleteTSdStandardfileById() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		tSdStandardfileDetail.deleteTSdStandardfileById(request.getParameter("id"), request, response);
		Map<String, String> pageData = new HashMap<String, String>();
		pageData.put("msg", "删除成功");
		pageData.put("status", "ok");
		this.formatData(pageData);
		return SUCCESS;
	}
}