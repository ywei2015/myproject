package com.talkweb.xwzcxt.action;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;
import com.talkweb.twdpe.core.util.DateUtil;
import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.xwzcxt.service.RulePositionServiceI;
import com.talkweb.xwzcxt.vo.RulePositionVo;

public class RulePositionAction extends baseAction implements
		ModelDriven<RulePositionVo> {

	private static final long serialVersionUID = 1L;

	private RulePositionVo rulePosition;
	
	@Autowired
	private RulePositionServiceI rulePositionService;

	@Override
	public RulePositionVo getModel() {
		return rulePosition;
	}
	
	public String getAllRulePositionsByConditions()
	{
		HttpServletRequest request = ServletActionContext.getRequest();
		Map params = new HashMap();
		params.put("rulepositionname", request.getParameter("rulepositionname"));
		params.put("positionexe", request.getParameter("positionexe"));
		params.put("positioncheck", request.getParameter("positioncheck"));
		params.put("positionreview", request.getParameter("positionreview"));
		params.put("positionfeed1", request.getParameter("positionfeed1"));
		params.put("positionfeed2", request.getParameter("positionfeed2"));
		params.put("positionerr1", request.getParameter("positionerr1"));
		params.put("positionerr2", request.getParameter("positionerr2"));
		if (pagination == null) {
			pagination = new Pagination(1, 20);
		}
		Pagination page = null;
		page = rulePositionService.getAllRulePositionsByConditions(params, pagination);
		this.formatDatagirdData(page.getList(), page);
		return SUCCESS;
	}
	
	public String getRulePositionByID()
	{
		HttpServletRequest request = ServletActionContext.getRequest();
		String cPrid = request.getParameter("cPrid");
		if(cPrid != null && !cPrid.isEmpty())
		{
			RulePositionVo p = this.rulePositionService.getRulePositionByID(cPrid);
			if(p != null)
			{
				this.formatData(p);
			}
			else {
				return ERROR;
			}
		}
		return SUCCESS;
	}

	public String addRulePosition()
	{
		HttpServletRequest request = ServletActionContext.getRequest();
		Map params = new HashMap();
		params.put("cPridName", request.getParameter("rulepositionname"));
		params.put("cPridCode", request.getParameter("cPridCode"));
		params.put("cPidExec", request.getParameter("positionexe"));
		params.put("cPidCheck", request.getParameter("positioncheck"));
		params.put("cPidReview", request.getParameter("positionreview"));
		params.put("cPidFeedback1", request.getParameter("positionfeed1"));
		params.put("cPidFeedback2", request.getParameter("positionfeed2"));
		params.put("cPidErrFeedback1", request.getParameter("positionerr1"));
		params.put("cPidErrFeedback2", request.getParameter("positionerr2"));
		params.put("cRemark", request.getParameter("cRemark"));
		String cPrid = UUID.randomUUID().toString();
		params.put("cPrid",cPrid );
		params.put("cFlag", new BigDecimal(1));
		params.put("cCreator", "Admin");
		params.put("cCreatetime", new Date());
		
		int n = rulePositionService.addRulePosition(params);
		this.setMessage(1, "RulePosition", "Add");
		return SUCCESS;
	}
}
