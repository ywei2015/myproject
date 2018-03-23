package com.talkweb.xwzcxt.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.xwzcxt.dal.RuleTimeDal;
import com.talkweb.xwzcxt.dal.TaskDal;
import com.talkweb.xwzcxt.pojo.TRuleTime;
import com.talkweb.xwzcxt.service.RuleTimeServiceI;
import com.talkweb.xwzcxt.util.DataTypeParseUtil;
import com.talkweb.xwzcxt.vo.RuleTimeVo;

public class RuleTimeServiceImpl implements RuleTimeServiceI {
	@Autowired
	private RuleTimeDal ruleTimeDal;

	@Autowired
	private TaskDal taskDal;

	private RuleTimeVo ChangeModel(TRuleTime p) {
		RuleTimeVo v = new RuleTimeVo();
		if (p != null) {
			BeanUtils.copyProperties(p, v);
			v.setcTimeruleTypeName(this.getTypeNameByID(v.getcTimeruleType().intValue()));
			if (v.getcTimeruleBegin() != null) {
				v.setcTimeruleBeginName(DataTypeParseUtil.getDateString(v.getcTimeruleBegin()));
			}
			if (v.getcTimeruleEnd() != null) {
				v.setcTimeruleEndName(DataTypeParseUtil.getDateString(v.getcTimeruleEnd()));
			}
			if (v.getcDepartment() != null) {
				v.setcDepartmentName(this.taskDal.getDepartmentNameByID(v.getcDepartment()).get("ORGNAME").toString());
				v.setcDepartment("1-" + v.getcDepartment());
			}
			if (v.getcFirsttimeExec() != null) {
				String cFirsttimeExecString = DataTypeParseUtil.getDateString(v.getcFirsttimeExec());
				String[] cFirsttimeExecStrings = cFirsttimeExecString.substring(cFirsttimeExecString.indexOf(":") - 2).split(":");
				v.setcFirsttimeExecHour(cFirsttimeExecStrings[0]);
				v.setcFirsttimeExecMinute(cFirsttimeExecStrings[1]);
				v.setcFirsttimeExecSecond(cFirsttimeExecStrings[2]);
			}
			if (v.getcOffsetArray() != null) {
				String cOffsetArray[] = v.getcOffsetArray().split(",");
				String cOffsetArray1[] = cOffsetArray[0].split(" ");
				String cOffsetArray11[] = cOffsetArray1[1].split(":");
				String cOffsetArray2[] = cOffsetArray[1].split(" ");
				String cOffsetArray21[] = cOffsetArray2[1].split(":");
				v.setcFirsttimeExec1(cOffsetArray[0]);
				v.setcOffsetDay1(cOffsetArray1[0]);
				v.setcFirsttimeExecHour1(cOffsetArray11[0]);
				v.setcFirsttimeExecMinute1(cOffsetArray11[1]);
				v.setcFirsttimeExecSecond1(cOffsetArray11[2]);
				v.setcFirsttimeExec2(cOffsetArray[1]);
				v.setcOffsetDay2(cOffsetArray2[0]);
				v.setcFirsttimeExecHour2(cOffsetArray21[0]);
				v.setcFirsttimeExecMinute2(cOffsetArray21[1]);
				v.setcFirsttimeExecSecond2(cOffsetArray21[2]);
			}
		}
		return v;
	}

	private List<RuleTimeVo> ChangeModelList(List<TRuleTime> plist) {
		List<RuleTimeVo> vlist = new ArrayList<RuleTimeVo>();
		if (plist != null && plist.size() > 0) {
			for (TRuleTime p : plist) {
				RuleTimeVo v = ChangeModel(p);
				vlist.add(v);
			}
		}
		return vlist;
	}

	private String getTypeNameByID(int tid) {
		String typename = "";
		switch (tid) {
			case 1:
				typename = "单次";
				break;
			case 2:
				typename = "每班";
				break;
			case 3:
				typename = "每天";
				break;
			case 4:
				typename = "每周";
				break;
			case 5:
				typename = "每月一次";
				break;
			case 51:
				typename = "每月两次";
				break;
			default:
				typename = "其他";
				break;
		}
		return typename;
	}

	@Override
	public RuleTimeVo getRuleTimeByID(String id) {
		RuleTimeVo v = ChangeModel(ruleTimeDal.getRuleTimeByID(id));
		v.setcDepartments(v.getcDepartment());
		return v;
	}

	@Override
	public Pagination getAllRuleTime(Map params, Pagination page) {
		Pagination pagination = ruleTimeDal.getAllRuleTime(params, page);
		pagination.setList(ChangeModelList(pagination.getList()));
		return pagination;
	}

	@Override
	public int addTimeRule(Map params) {
		return ruleTimeDal.addTimeRule(params);
	}

	@Override
	public int updateTimeRuleById(Map params) {
		return ruleTimeDal.updateTimeRuleById(params);
	}

	@Override
	public int deleteTimeRuleById(String id) {
		return ruleTimeDal.deleteTimeRuleById(id);
	}
}