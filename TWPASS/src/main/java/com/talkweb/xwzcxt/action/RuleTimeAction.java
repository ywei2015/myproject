package com.talkweb.xwzcxt.action;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.twdpe.core.util.DateUtil;
import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.twdpe.web.jcontrols.RuleSelect;
import com.talkweb.twdpe.web.jcontrols.RuleTree;
import com.talkweb.twdpe.web.session.main.Session;
import com.talkweb.twdpe.web.session.util.SessionFactory;
import com.talkweb.xwzcxt.dal.PositionBindOrgDal;
import com.talkweb.xwzcxt.pojo.MyLogPojo;
import com.talkweb.xwzcxt.service.MyLogService;
import com.talkweb.xwzcxt.service.RuleTimeServiceI;
import com.talkweb.xwzcxt.vo.RuleTimeVo;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class RuleTimeAction extends baseAction {
	@Autowired
	private RuleTimeServiceI ruleTimeService;

	@Autowired
	public PositionBindOrgDal positionBindOrgDal;

	@Autowired
	private MyLogService logService;
	
	public String getAllRuleTimeByCondition() {
		HttpServletRequest request = ServletActionContext.getRequest();
		Map params = new HashMap();
		String cTimeruleName = request.getParameter("cTimeruleName");
		String cTimeruleType = request.getParameter("cTimeruleType");
		String cDepartment = request.getParameter("cDepartment");

		params.put("cTimeruleName", cTimeruleName);
		if (cTimeruleType != null && !cTimeruleType.isEmpty()) {
			params.put("cTimeruleType", new BigDecimal(cTimeruleType));
		}
		if (cDepartment != null && !cDepartment.isEmpty()) {
			params.put("cDepartment", cDepartment.substring(2));
		}

		if (pagination == null) {
			pagination = new Pagination(1, 20);
		}
		Pagination page = null;
		page = ruleTimeService.getAllRuleTime(params, pagination);
		this.formatDatagirdData(page.getList(), page);
		return SUCCESS;
	}

	public String getRuleTimeSelect() {
		HttpServletRequest request = ServletActionContext.getRequest();
		Pagination page = null;
		page = ruleTimeService.getAllRuleTime(null, null);
		List<RuleTimeVo> li = page.getList();
		RuleSelect wtree = new RuleSelect();
		wtree.setValue("cTimeruleId");
		wtree.setText("cTimeruleName");
		try {
			if (li != null && li.size() > 0) {
				List<Map> l = this.initSelectData(page.getList(), wtree);
				this.formatData(l);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String getRuleTimeTree() {
		HttpServletRequest request = ServletActionContext.getRequest();
		Map params = new HashMap();
		params.put("cDepartment", request.getParameter("cDepartment"));

		List<RuleTimeVo> pages = null;
		int type[] = {2, 3, 4, 5, 51};
		for (int i = 0; i < type.length; i++) {
			params.put("cTimeruleType", Integer.toString(type[i]));
			Pagination page = ruleTimeService.getAllRuleTime(params, null);
			List<RuleTimeVo> li = page.getList();
			for (RuleTimeVo v : li) {
				v.setcPid(Integer.toString(type[i]));
			}
			RuleTimeVo v = new RuleTimeVo();
			v.setcTimeruleId(Integer.toString(type[i]));
			v.setcPid("-1");
			switch (i) {
				case 0:
					v.setcTimeruleName("每班");
					break;
				case 1:
					v.setcTimeruleName("每日");
					break;
				case 2:
					v.setcTimeruleName("每周");
					break;
				case 3:
					v.setcTimeruleName("每月一次");
					break;
				case 4:
					v.setcTimeruleName("每月两次");
					break;
				default:
					break;
			}
			li.add(v);
			if (null == pages) {
				pages = li;
			} else {
				pages.addAll(li);
			}
		}
		RuleTimeVo v1 = new RuleTimeVo();
		v1.setcTimeruleId("-1");
		v1.setcTimeruleName("时间规则");
		v1.setcPid("1--1");
		pages.add(v1);
		RuleTimeVo v2 = new RuleTimeVo();
		v2.setcTimeruleId("1--1");
		pages.add(v2);

		RuleTree wtree = new RuleTree();
		wtree.setId("cTimeruleId");
		wtree.setVal("cTimeruleName");
		wtree.setPid("cPid");
		try {
			if (pages != null && pages.size() > 0) {
				List<Map> l = this.initTreeData(pages, wtree);
				this.formatData(l);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String deleteRuleTimeByIds() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String ids = request.getParameter("id");
		try {
			List<RuleTimeVo> delList = new ArrayList<RuleTimeVo>();
			String[] trids = ids.split(",");
			for (int i = 0; i < trids.length; i++) {
				RuleTimeVo delObj = ruleTimeService.getRuleTimeByID(trids[i]);
				ruleTimeService.deleteTimeRuleById(trids[i]);
				if(delObj!=null){
					delList.add(delObj);
				}
			}
			//记录数据日志
			JSONArray addListJson = JSONArray.fromObject(delList);
			String addJsonStr = addListJson.toString();
			logService.addLogInfo(request, response, "1", "过程行为支持系统", "2", "基础管理", "时间规则管理", MyLogPojo.del, "时间规则管理-删除", "成功", "1", "T_TIMERULE", addJsonStr, "");
			
			this.setMessage(1, "RuleTime", "DELETE");
		} catch (Exception e) {
			this.setMessage(0, "RuleTime", "DELETE");
		}
		return SUCCESS;
	}

	public String getRuleTimeByID() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String cTimeruleId = request.getParameter("cTimeruleId");
		if (cTimeruleId != null && !cTimeruleId.isEmpty()) {
			RuleTimeVo p = this.ruleTimeService.getRuleTimeByID(cTimeruleId);
			if (p != null) {
				this.formatData(p);
			} else {
				return ERROR;
			}
		}
		return SUCCESS;
	}

	private String getDateTimeByTime(String time) {
		Date now = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String ymd = formatter.format(now).substring(0, 9);
		return ymd + " " + time;
	}

	@SuppressWarnings("unchecked")
	private void getHourSet(String checkHourModel, String cTimeruleType, String val1, String val2, String val3, Map params) {
		switch (Integer.valueOf(checkHourModel)) {
			case 1:// 执行一次
				if (Integer.valueOf(cTimeruleType) == 2) {
					params.put("cOffsetWorkgroup", new BigDecimal(val1));
					if (val2 != null && !val2.isEmpty()) {
						params.put("cOffsetMinute", new BigDecimal(val2));
					}
				} else {
					if (val1 != null && !val1.isEmpty()) {
						String date = getDateTimeByTime(val1);
						params.put("cFirsttimeExec", DateUtil.getTimestamp(DateUtil.formatStr2Date(date)));
					}
				}
				break;
			case 2:// 循环执行
				if (val3 != null && !val3.isEmpty()) {
					params.put("cOffsetHour", new BigDecimal(val3));
				}
				break;
			default:
				break;
		}
	}

	@SuppressWarnings("unchecked")
	private Map getTimeRuleParams(HttpServletRequest request) {
		Map params = new HashMap();
		String cDepartment = request.getParameter("cDepartment");
		String cDepartments = request.getParameter("cDepartments");
		String cTimeruleName = request.getParameter("cTimeruleName");
		String cTimeruleType = request.getParameter("cTimeruleType");
		String execOnceTime = request.getParameter("execOnceTime");
		String cOffsetDay = request.getParameter("cOffsetDay");
		String cOffsetW = request.getParameter("cOffsetW");// 周偏移
		String cOffsetWeek = request.getParameter("cOffsetWeek");// 周明细
		String cOffsetMonth = request.getParameter("cOffsetMonth");
		//String checkHourModel = request.getParameter("checkHourModel");
		String checkHourModel = "1";//暂只实现单次执行
		String cFirsttimeExec = request.getParameter("cFirsttimeExec");
		String cOffsetWorkgroup = request.getParameter("cOffsetWorkgroup");
		String cOffsetMinute = request.getParameter("cOffsetMinute");
		String cOffsetHour = request.getParameter("cOffsetHour");
		String cTimeruleBegin = request.getParameter("cTimeruleBegin");
		String checkEndModel = request.getParameter("checkEndModel");
		String cTimeruleEnd = request.getParameter("cTimeruleEnd");
		String cRemark = request.getParameter("cRemark");
		switch (Integer.valueOf(cTimeruleType)) {
			case 1:// 单次执行
				params.put("cFirsttimeExec", DateUtil.getTimestamp(DateUtil.formatStr2Date(cFirsttimeExec)));
				break;
			case 2:// 每班
				this.getHourSet(checkHourModel, cTimeruleType, cOffsetWorkgroup, cOffsetMinute, cOffsetHour, params);
				break;
			case 3:// 每天
				if (cOffsetDay != null && !cOffsetDay.isEmpty()) {
					params.put("cOffsetDay", new BigDecimal(cOffsetDay));
				}
				this.getHourSet(checkHourModel, cTimeruleType, cFirsttimeExec, "", cOffsetHour, params);
				break;
			case 4:// 每周
				if (cOffsetW != null && !cOffsetW.isEmpty()) {
					BigDecimal bd = new BigDecimal(cOffsetW);
					BigDecimal week = new BigDecimal(7);
					BigDecimal weekset = week.multiply(bd);
					params.put("cOffsetDay", weekset);
				} else {
					params.put("cOffsetDay", new BigDecimal(7));
				}
				params.put("cOffsetWeek", cOffsetWeek);
				this.getHourSet(checkHourModel, cTimeruleType, cFirsttimeExec, "", cOffsetHour, params);
				break;
			case 5:// 每月一次
				if (cOffsetDay != null && !cOffsetDay.isEmpty()) {
					params.put("cOffsetDay", new BigDecimal(cOffsetDay));
				}
				if (cOffsetMonth != null && !cOffsetMonth.isEmpty()) {
					params.put("cOffsetMonth", new BigDecimal(cOffsetMonth));
				}
				this.getHourSet(checkHourModel, cTimeruleType, cFirsttimeExec, "", cOffsetHour, params);
				break;
			case 51:// 每月两次
				params.put("cOffsetArray", request.getParameter("cOffsetDay1").toString() + " " + request.getParameter("cFirsttimeExec1").toString() + "," + request.getParameter("cOffsetDay2").toString() + " " + request.getParameter("cFirsttimeExec2").toString());
				break;
			default:
				break;
		}
		params.put("cDepartment", cDepartment);
		params.put("cDepartments", cDepartments);
		params.put("cTimeruleName", cTimeruleName);
		params.put("cTimeruleType", new BigDecimal(cTimeruleType));
		params.put("cRemark", cRemark);
		params.put("cFlag", new BigDecimal(1));

		if (Integer.valueOf(cTimeruleType) != 1) {
			if (cTimeruleBegin != null && !cTimeruleBegin.isEmpty()) {
				params.put("cTimeruleBegin", DateUtil.getTimestamp(DateUtil.formatStr2Date(cTimeruleBegin)));
				switch (Integer.valueOf(checkEndModel)) {
					case 1:// 结束日期
						if (cTimeruleEnd != null && !cTimeruleEnd.isEmpty()) {
							params.put("cTimeruleEnd", DateUtil.getTimestamp(DateUtil.formatStr2Date(cTimeruleEnd)));
						}
						break;
					default:
						break;
				}
			}
		}
		return params;
	}

	
	public String addRuleTime() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		Session session = SessionFactory.getInstance(request, response);
		Map user = (Map) session.getAttribute("USERSESSION");
		Map params = getTimeRuleParams(request);
		//0-查看，1-修改，2-新建
		int cModifyType = Integer.parseInt(request.getParameter("cModifyType").toString());
		switch (cModifyType) {
			case 1:
				String ucTimeruleId = request.getParameter("cTimeruleId");
				params.put("cTimeruleId", ucTimeruleId);
				params.put("cDepartment", params.get("cDepartments").toString().substring(2));
				params.put("cModifier", user.get("id"));
				params.put("cModifytime", DateUtil.getTimestamp(new Date()));
				RuleTimeVo updateoldObj = ruleTimeService.getRuleTimeByID(ucTimeruleId);
				ruleTimeService.updateTimeRuleById(params);
				RuleTimeVo updatenewObj = ruleTimeService.getRuleTimeByID(ucTimeruleId);
				
				//记录数据日志
				JSONObject oldObjjson = JSONObject.fromObject(updateoldObj); 
				JSONObject newObjjson = JSONObject.fromObject(updatenewObj);
				String oldJsonStr = oldObjjson.toString();
				String newJsonStr = newObjjson.toString();
				logService.addLogInfo(request, response, "1", "过程行为支持系统", "2", "基础管理", "时间规则管理", MyLogPojo.change, "时间规则管理-修改", "成功", "1", "T_TIMERULE", oldJsonStr, newJsonStr);
				
				this.setMessage(1, "RuleTime", "Modify");
				break;
			case 2:
				String cDepartments = params.get("cDepartments").toString();
				String[] cDepartment = cDepartments.split(",");
				params.put("cCreator", user.get("id"));
				params.put("cCreatetime", DateUtil.getTimestamp(new Date()));
				for (int i = 0; i < cDepartment.length; i++) {
					String cTimeruleId = UUID.randomUUID().toString();
					params.put("cTimeruleId", cTimeruleId);
					params.put("cDepartment", cDepartment[i].substring(2));
					ruleTimeService.addTimeRule(params);
					
					//记录数据日志
					RuleTimeVo addnewObj = ruleTimeService.getRuleTimeByID(cTimeruleId);
					JSONObject addObjjson = JSONObject.fromObject(addnewObj); 
					String addJsonStr = addObjjson.toString();
					logService.addLogInfo(request, response, "1", "过程行为支持系统", "2", "基础管理", "时间规则管理", MyLogPojo.add, "时间规则管理-新增", "成功", "1", "T_TIMERULE", "", addJsonStr);
					
				}
				this.setMessage(1, "RuleTime", "Add");
				break;
			default:
				break;
		}
		return SUCCESS;
	}
}