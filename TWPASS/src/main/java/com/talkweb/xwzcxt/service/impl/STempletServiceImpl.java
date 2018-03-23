package com.talkweb.xwzcxt.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.common.AppConstants;
import com.talkweb.twdpe.base.org.pojo.User;
import com.talkweb.twdpe.base.org.service.UserService;
import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.xwzcxt.dal.DpAreaDal;
import com.talkweb.xwzcxt.dal.STempletDal;
import com.talkweb.xwzcxt.dal.TSdActnodeDal;
import com.talkweb.xwzcxt.dal.TSdMediastdDal;
import com.talkweb.xwzcxt.dal.TSdTempletScancodeDal;
import com.talkweb.xwzcxt.dal.TaskDal;
import com.talkweb.xwzcxt.dal.XwzcxtMngDal;
import com.talkweb.xwzcxt.pojo.MyLogPojo;
import com.talkweb.xwzcxt.pojo.TSTempletPojo;
import com.talkweb.xwzcxt.pojo.TSdActnodePojo;
import com.talkweb.xwzcxt.pojo.TSdMediastdPojo;
import com.talkweb.xwzcxt.pojo.TSdTempletScancodePojo;
import com.talkweb.xwzcxt.pojo.TStdtaskplanSeries;
import com.talkweb.xwzcxt.pojo.TTaskPojo;
import com.talkweb.xwzcxt.service.DpPositionServiceI;
import com.talkweb.xwzcxt.service.MyLogService;
import com.talkweb.xwzcxt.service.RuleTimeServiceI;
import com.talkweb.xwzcxt.service.STempletServiceI;
import com.talkweb.xwzcxt.util.DataTypeParseUtil;
import com.talkweb.xwzcxt.vo.STempletVo;
import com.talkweb.xwzcxt.vo.TStdtaskplanSeriesVo;

public class STempletServiceImpl implements STempletServiceI {
	@Autowired
	private TSdTempletScancodeDal tSdTempletScancodeDal;

	@Autowired
	private TSdMediastdDal tSdMediastdDal;

	@Autowired
	private TSdActnodeDal tSdActnodeDal;

	@Autowired
	private STempletDal sTempletDal;

	@Autowired
	private RuleTimeServiceI ruleTimeService;

	@Autowired
	private DpAreaDal dpAreaDal;

	@Autowired
	private DpPositionServiceI dpPositionService;

	@Autowired
	private TaskDal taskDal;

	@Autowired
	private UserService userService;

	@Autowired
	private XwzcxtMngDal xwzcxtMngDal;
	
	@Autowired
	private MyLogService logService;

	private String getStatusName(Integer status) {
		String name = "";
		switch (status) {
			case 0:
				name = "已解析";
				break;
			case 1:
				name = "已生成";
				break;
			case 2:
				name = "已过期";
				break;
			default:
				break;
		}
		return name;
	}

	private TStdtaskplanSeriesVo ChangeModel_planSeries(TStdtaskplanSeries p) {
		TStdtaskplanSeriesVo v = new TStdtaskplanSeriesVo();
		if (p != null) {
			BeanUtils.copyProperties(p, v);
			User u;
			if (v.getcExecUserid() != null && v.getcExecUserid().longValue() != 0) {
				u = userService.getUser(v.getcExecUserid().longValue());
				v.setcExecUserName(u.getDisplayName()); // 执行名称
			}
			if (v.getcChkUserid() != null && v.getcChkUserid().longValue() != 0) {
				u = userService.getUser(v.getcChkUserid().longValue());
				v.setcChkUserName(u.getDisplayName()); // 验证名称
			}
			if (v.getcEvaluateUserid() != null && v.getcEvaluateUserid().longValue() != 0) {
				u = userService.getUser(v.getcEvaluateUserid().longValue());
				v.setcEvaluateUserName(u.getDisplayName()); // 评价名称
			}
			if (v.getcOkfbUserid() != null && v.getcOkfbUserid().longValue() != 0) {
				u = userService.getUser(v.getcOkfbUserid().longValue());
				v.setcOkfbUserName(u.getDisplayName()); // 默认反馈名称
			}
			if (v.getcOkfbUlist() != null && Long.valueOf(v.getcOkfbUlist()).longValue() != 0) {
				u = userService.getUser(Long.valueOf(v.getcOkfbUlist()).longValue());
				v.setcOkfbUlistName(u.getDisplayName()); // 其他默认反馈名称
			}
			if (v.getcNgfbUserid() != null && v.getcNgfbUserid().longValue() != 0) {
				u = userService.getUser(v.getcNgfbUserid().longValue());
				v.setcNgfbUserName(u.getDisplayName()); // 异常反馈名称
			}
			if (v.getcNgfbUlist() != null && Long.valueOf(v.getcNgfbUlist()).longValue() != 0) {
				u = userService.getUser(Long.valueOf(v.getcNgfbUlist()).longValue());
				v.setcNgfbUlistName(u.getDisplayName()); // 其他异常反馈名称
			}
			if (v.getcTaskId() != null) {
				TTaskPojo t = taskDal.getTaskByID(v.getcTaskId().toString());
				v.setcTaskName(t.getcTaskName()); // 任务名称
			}
			if (v.getcTasktempletId() != null && !v.getcTasktempletId().isEmpty()) {
				STempletVo t = getTaskTempletById(v.getcTasktempletId());
				v.setcTasktempletName(t.getcTempletName());
			}
			v.setcTaskTypeName(xwzcxtMngDal.getTaskTypeNameById(v.getcTaskType().toString()));
			v.setcStateName(getStatusName(v.getcState()));	
		}
		return v;
	}

	private List<TStdtaskplanSeriesVo> ChangeModelList_planSeries(List<TStdtaskplanSeries> plist) {
		List<TStdtaskplanSeriesVo> vlist = new ArrayList<TStdtaskplanSeriesVo>();
		if (plist != null && plist.size() > 0) {
			for (TStdtaskplanSeries p : plist) {
				TStdtaskplanSeriesVo v = ChangeModel_planSeries(p);
				vlist.add(v);
			}
		}
		return vlist;
	}

	private STempletVo ChangeModel(TSTempletPojo p, String model) {
		STempletVo v = new STempletVo();
		if (p != null) {
			BeanUtils.copyProperties(p, v);
			TSdActnodePojo tSdActnode = tSdActnodeDal.getActNodeByID(v.getcActnodeId());
			// 岗位活动名称
			v.setcActnodeName(tSdActnode.getcActnodeName());

			// 二维码
			Map params = new HashMap();
			params.put("type", 1);
			params.put("cid", v.getcTempletId());
			List<TSdTempletScancodePojo> lsc = tSdTempletScancodeDal.getScancodeByTempletId(params);
			String cJobObjects = "", cJobObjectsName = "";
			for (TSdTempletScancodePojo tsc : lsc) {
				cJobObjects += (tsc.getcBasecodeId() + ",");
				cJobObjectsName += (tsc.getcBasecodeName() + ",");
			}
			if (!cJobObjects.equals("") || !cJobObjectsName.equals("")) {
				cJobObjects = cJobObjects.substring(0, cJobObjects.length() - 1);
				cJobObjectsName = cJobObjectsName.substring(0, cJobObjectsName.length() - 1);
			}
			v.setcJobObjects(cJobObjects);
			v.setcJobObjectsName(cJobObjectsName);

			// 多媒体
			params.put("type", 2);
			params.put("cid", v.getcTempletId());
			List<TSdMediastdPojo> lsd = tSdMediastdDal.getAllMediaStdById(params);
			String cTempletMediaFileId = "", cTempletMediaFileType = "", cTempletMediaFilePath = "";
			for (TSdMediastdPojo tsd : lsd) {
				cTempletMediaFileId += (tsd.getcSdfileId() + ",");
				cTempletMediaFileType += (tsd.getcSdfileType() + ",");
				cTempletMediaFilePath += (AppConstants.getFilePathPrefix() + tsd.getcSdfilePath() + ",");
			}
			if (!cTempletMediaFileId.equals("") || !cTempletMediaFileType.equals("") || !cTempletMediaFilePath.equals("")) {
				cTempletMediaFileId = cTempletMediaFileId.substring(0, cTempletMediaFileId.length() - 1);
				cTempletMediaFileType = cTempletMediaFileType.substring(0, cTempletMediaFileType.length() - 1);
				cTempletMediaFilePath = cTempletMediaFilePath.substring(0, cTempletMediaFilePath.length() - 1);
			}
			v.setcTempletMediaFileId(cTempletMediaFileId);
			v.setcTempletMediaFileType(cTempletMediaFileType);
			v.setcTempletMediaFilePath(cTempletMediaFilePath);

			// 执行时间
			if (v.getcTaskbeginTrid() != null && !v.getcTaskbeginTrid().isEmpty()) {
				v.setcExecTime(ruleTimeService.getRuleTimeByID(v.getcTaskbeginTrid()).getcTimeruleName());
			}

			// 执行岗位
			v.setcPNameExec(dpPositionService.getPositionNameByID(v.getcPidExec()) + "-" + dpPositionService.getUserNameByID(v.getcPidExec()));

			// 作业区域
			String fullname = dpAreaDal.getAreaByID(v.getcExecAreaid()).getFullname();
			if (-1 < fullname.indexOf("长沙卷烟厂,")) {
				fullname = fullname.replace("长沙卷烟厂,", "");
			}
			if (-1 < fullname.indexOf(",")) {
				fullname = fullname.replaceAll(",", "");
			}
			v.setcExecAreaName(fullname);

			// 岗位活动类型1：作业类，2：管理类
			v.setcActnodetype(tSdActnode.getcActnodetype());

			// 是否关键管控
			v.setcIskeyctrl(tSdActnode.getcIskeyctrl());
			v.setcIskeyctrlname(v.getcIskeyctrl().intValue() == 0 ? "否" : "是");

			// 时态
			v.setcFrequency(tSdActnode.getcFrequency());

			if (model == "detail") {
				if (v.getcTaskfinishTrid() != null && !v.getcTaskfinishTrid().isEmpty()) {
					v.setcExecEndTime(ruleTimeService.getRuleTimeByID(v.getcTaskfinishTrid()).getcTimeruleName());// 执行结束时间
				}
				if (v.getcTaskcheckTime() != null && !v.getcTaskcheckTime().isEmpty()) {
					v.setcCheckTime(ruleTimeService.getRuleTimeByID(v.getcTaskcheckTime()).getcTimeruleName());// 检查时间
				}
				if (v.getcTaskreviewTime() != null && !v.getcTaskreviewTime().isEmpty()) {
					v.setcReviewTime(ruleTimeService.getRuleTimeByID(v.getcTaskreviewTime()).getcTimeruleName());// 验证时间
				}
				if (v.getcPidCheck() != null && !v.getcPidCheck().isEmpty()) {
					v.setcPNameCheck(dpPositionService.getPositionNameByID(v.getcPidCheck()) + "-" + dpPositionService.getUserNameByID(v.getcPidCheck())); // 验证岗位
				}
				if (v.getcPidReview() != null && !v.getcPidReview().isEmpty()) {
					v.setcPNameReview(dpPositionService.getPositionNameByID(v.getcPidReview()) + "-" + dpPositionService.getUserNameByID(v.getcPidReview())); // 评价岗位
				}
				if (v.getcPidFeedback1() != null && !v.getcPidFeedback1().isEmpty()) {
					v.setcPNameFeedback1(dpPositionService.getPositionNameByID(v.getcPidFeedback1()) + "-" + dpPositionService.getUserNameByID(v.getcPidFeedback1())); // 反1岗位
				}
				if (v.getcPidFeedback2() != null && !v.getcPidFeedback2().isEmpty()) {
					v.setcPNameFeedback2(dpPositionService.getPositionNameByID(v.getcPidFeedback2()) + "-" + dpPositionService.getUserNameByID(v.getcPidFeedback2())); // 反2岗位
				}
				if (v.getcPidErrFeedback1() != null && !v.getcPidErrFeedback1().isEmpty()) {
					v.setcPNameErrFeedback1(dpPositionService.getPositionNameByID(v.getcPidErrFeedback1()) + "-" + dpPositionService.getUserNameByID(v.getcPidErrFeedback1())); // 异常1岗位
				}
				if (v.getcPidErrFeedback2() != null && !v.getcPidErrFeedback2().isEmpty()) {
					v.setcPNameErrFeedback2(dpPositionService.getPositionNameByID(v.getcPidErrFeedback2()) + "-" + dpPositionService.getUserNameByID(v.getcPidErrFeedback2())); // 异常2岗位
				}
				if (v.getcDepartment() != null && !v.getcDepartment().isEmpty()) {
					Map dpt = taskDal.getDepartmentNameByID(v.getcDepartment());
					if (dpt != null && dpt.containsKey("ORGNAME")) {
						v.setcDepartmentName(dpt.get("ORGNAME").toString()); // 部门信息
					}
				}
				if (v.getcConfirmor() != null && !v.getcConfirmor().isEmpty()) {
					User u = userService.getUser(Long.parseLong(v.getcConfirmor()));
					v.setcConfirmorName(u.getDisplayName()); // 审核人名称
				}
			}
		}

		return v;
	}

	private List<STempletVo> ChangeModelList(List<TSTempletPojo> plist) {
		List<STempletVo> vlist = new ArrayList<STempletVo>();
		if (plist != null && plist.size() > 0) {
			for (TSTempletPojo p : plist) {
				STempletVo v = ChangeModel(p, "detail");
				vlist.add(v);
			}
		}
		return vlist;
	}

	@Override
	public Pagination getAllTaskTemplet(Map params, Pagination page) {
		Pagination pagination = sTempletDal.getAllTaskTemplet(params, page);
		List<Map> plist = pagination.getList();
		if (plist != null && plist.size() > 0) {
			for (Map m : plist) {
				String fullname = m.get("C_EXEC_AREANAME").toString();
				if (-1 < fullname.indexOf("长沙卷烟厂,")) {
					fullname = fullname.replace("长沙卷烟厂,", "");
				}
				if (-1 < fullname.indexOf(",")) {
					fullname = fullname.replaceAll(",", "");
				}
				m.put("C_EXEC_AREANAME", fullname);
				if (m.get("C_CONFIRMTIME") != null) {
					try {
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
						m.put("C_CONFIRMTIME", DataTypeParseUtil.getDateString(sdf.parse(m.get("C_CONFIRMTIME").toString())));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				m.put("C_ISVAILD", m.get("C_ISVAILD").toString().equals("1") ? "启用" : "禁用");
			}
		}
		return pagination;
	}

	@Override
	public int addTaskTemplet(Map params, HttpServletRequest request, HttpServletResponse response) {
		
		Map addcJobObjects = tSdTempletScancodeDal.addcJobObjects(params.get("cIsscan").toString(), params.get("cJobObjects").toString(), 1, params.get("cCreator").toString(), params.get("cTempletId").toString());
		List<TSdMediastdPojo> searchDeleteScancodesById = (List<TSdMediastdPojo>)addcJobObjects.get("deleteScancodesByIdoldValue");
		if(searchDeleteScancodesById != null){
			String searchDeleteScancodesById_datafield = (String)addcJobObjects.get("deleteScancodesByIdoldValue_datafield");
			for (TSdMediastdPojo e : searchDeleteScancodesById) {
				logService.addLogInfo(request, response, "1", "过程行为支持系统", "", "任务管理", "任务模板制定", MyLogPojo.del, "任务模板制定", "任务模板制定成功", "1", searchDeleteScancodesById_datafield, e.toString(), "");
			}
		}
		List<TSdTempletScancodePojo> r1 = (List<TSdTempletScancodePojo>)addcJobObjects.get("r");
		if(r1 != null){
			String r_datafield = (String)addcJobObjects.get("r_datafield");
			for (TSdTempletScancodePojo tSdTempletScancodePojo : r1) {
				logService.addLogInfo(request, response, "1", "过程行为支持系统", "", "任务管理", "任务模板制定", MyLogPojo.add, "任务模板制定", "任务模板制定成功", "1", r_datafield, "", tSdTempletScancodePojo.toString());
			}
		}

		Map addMediaFile = tSdMediastdDal.addMediaFile(params.get("cTempletMediaFileId").toString(), 2, params.get("cCreator").toString(), params.get("cTempletId").toString());
		List<TSdMediastdPojo> searchDeleteMediaStdByIdResultMap = (List<TSdMediastdPojo>)addMediaFile.get("searchDeleteMediaStdByIdResultMap");
		if(searchDeleteMediaStdByIdResultMap != null){
			String searchDeleteMediaStdByIdResultMap_datafield = (String)addMediaFile.get("searchDeleteMediaStdByIdResultMap_datafield");
			for (TSdMediastdPojo tSdMediastdPojo : searchDeleteMediaStdByIdResultMap) {
				logService.addLogInfo(request, response, "1", "过程行为支持系统", "", "任务管理", "任务模板制定", MyLogPojo.del, "任务模板制定", "任务模板制定成功", "1", searchDeleteMediaStdByIdResultMap_datafield, tSdMediastdPojo.toString(), "");
			}
		}
		List<TSdMediastdPojo> TSdMediastdPojo = (List<TSdMediastdPojo>)addMediaFile.get("r");
		if(TSdMediastdPojo != null){
			String r_datafield = (String)addMediaFile.get("r_datafield");
			for (TSdMediastdPojo tSdMediastdPojo2 : TSdMediastdPojo) {
				logService.addLogInfo(request, response, "1", "过程行为支持系统", "", "任务管理", "任务模板制定", MyLogPojo.add, "任务模板制定", "任务模板制定成功", "1", r_datafield, "", tSdMediastdPojo2.toString());
			}
		}
		Map r = sTempletDal.addTaskTemplet(params);
		int type = (Integer) r.get("type");
		if(type == 1){
			Map p = (Map) r.get("obj");
			String obj_datafield = (String) r.get("obj_datafield");
			logService.addLogInfo(request, response, "1", "过程行为支持系统", "", "任务管理", "任务模板制定", MyLogPojo.add, "任务模板制定", "任务模板制定成功", "1",obj_datafield, "", p.toString());
		}
		return type;
	}
	@Override
	public STempletVo getTaskTempletById(String tid) {
		return ChangeModel(sTempletDal.getTaskTempletById(tid), "detail");
	}
	@Override
	public int addTempletByCopy(STempletVo sTempletVo, HttpServletRequest request, HttpServletResponse response) {
		int type = sTempletDal.addTempletByCopy(sTempletVo);
		if(type != 0){
			logService.addLogInfo(request, response, "1", "过程行为支持系统", "", "任务管理", "任务模板制定", MyLogPojo.add, "任务模板制定-模板复制", "成功", "1","TWXWZC.T_SD_TASK_TEMPLET", "", sTempletVo.toString());
		}
		return type;
	}
	@Override
	public int deleteTaskTempletById(String tid, HttpServletRequest request, HttpServletResponse response) {
		List<String> tids = new ArrayList<String>();
		tids.add(tid);
		Map deletePlanSeriesByTaskTempletIDRet = sTempletDal.deletePlanSeriesByTaskTempletID(tids);
		int deletePlanSeriesByTaskTempletIDType =(Integer) deletePlanSeriesByTaskTempletIDRet.get("type");
		Map params = new HashMap();
		params.put("type", 1);
		params.put("cid", tids);
		Map deleteScancodesByIdRet = tSdTempletScancodeDal.deleteScancodesById(params);
		int deleteScancodesByIdType = (Integer)deleteScancodesByIdRet.get("deleteScancodesByIdoldValueType");
		params.put("type", 2);
		params.put("cid", tids);
		tSdMediastdDal.deleteMediaStdById(params);
		Map deleteTaskTempletByIdRet = sTempletDal.deleteTaskTempletById(tid);
		int ret = (Integer)deleteTaskTempletByIdRet.get("deleteTaskTempletByIdType");
		if(ret != 0){
			if(deletePlanSeriesByTaskTempletIDType != 0){
				List<TStdtaskplanSeries> deletePlanSeriesByTaskTempletIDoldValue = (List<TStdtaskplanSeries>)deletePlanSeriesByTaskTempletIDRet.get("oldValue");
				String deletePlanSeriesByTaskTempletIDoldValue_datafield = (String)deletePlanSeriesByTaskTempletIDRet.get("oldValue_datafield");
				logService.addLogInfo(request, response, "1", "过程行为支持系统", "", "任务管理", "任务模板制定", MyLogPojo.del, "任务模板制定-删除", "成功", "1", deletePlanSeriesByTaskTempletIDoldValue_datafield, deletePlanSeriesByTaskTempletIDoldValue.toString(), "");
			}
			if(deleteScancodesByIdType != 0){
				List<TSdTempletScancodePojo> deleteScancodesByIdoldValue = (List<TSdTempletScancodePojo>)deleteScancodesByIdRet.get("deleteScancodesByIdoldValue");
				String deleteScancodesByIdoldValue_datafield = (String)deleteScancodesByIdRet.get("deleteScancodesByIdRet");
				logService.addLogInfo(request, response, "1", "过程行为支持系统", "", "任务管理", "任务模板制定", MyLogPojo.del, "任务模板制定-删除", "成功", "1", deleteScancodesByIdoldValue_datafield, deleteScancodesByIdoldValue.toString(), "");
			}
			TSTempletPojo deleteTaskTempletByIdOldValue =(TSTempletPojo)deleteTaskTempletByIdRet.get("deleteTaskTempletByIdOldValue");
			String deleteTaskTempletByIdOldValue_datafield = (String)deleteTaskTempletByIdRet.get("deleteTaskTempletByIdOldValue_datafield");
			logService.addLogInfo(request, response, "1", "过程行为支持系统", "", "任务管理", "任务模板制定", MyLogPojo.del, "任务模板制定-删除", "成功", "1", deleteTaskTempletByIdOldValue_datafield, deleteTaskTempletByIdOldValue.toString(), "");
			
		}
		
		return ret;
	}

	@Override
	public int updateTempletById(Map params, HttpServletRequest request, HttpServletResponse response) {
		String id = null;

		//模块名称
		String funcgroupname = "";
		//资源名称
		String resourcename = "";
		//操作描述
		String actiondescription = "";
		
		if (params.get("cModifier") != null) {
			id = params.get("cModifier").toString();
			funcgroupname = "任务管理";
			resourcename = "任务模板制定";
			actiondescription = "任务模板制定-修改";
		} else if (params.get("cConfirmor") != null) {
			id = params.get("cConfirmor").toString();
			funcgroupname = "任务管理";
			resourcename = "任务模板审核";
			actiondescription = "任务模板审核-评审";
		}
		Map addcJobObjects = tSdTempletScancodeDal.addcJobObjects(params.get("cIsscan").toString(), params.get("cJobObjects").toString(), 1, id, params.get("cTempletId").toString());
		
		List<TSdTempletScancodePojo> searchDeleteScancodesById = (List<TSdTempletScancodePojo>)addcJobObjects.get("deleteScancodesByIdoldValue");
		if(searchDeleteScancodesById != null){
			String searchDeleteScancodesById_datafield = (String)addcJobObjects.get("deleteScancodesByIdoldValue_datafield");
			for (TSdTempletScancodePojo e : searchDeleteScancodesById) {
				logService.addLogInfo(request, response, "1", "过程行为支持系统", "", funcgroupname, resourcename, MyLogPojo.del, actiondescription, "成功", "1",searchDeleteScancodesById_datafield, e.toString(), "");
			}
		}
		List<TSdTempletScancodePojo> r1 = (List<TSdTempletScancodePojo>)addcJobObjects.get("r");
		if(r1 != null){
			String r_datafield = (String)addcJobObjects.get("r_datafield");
			for (TSdTempletScancodePojo tSdTempletScancodePojo : r1) {
				logService.addLogInfo(request, response, "1", "过程行为支持系统", "", funcgroupname, resourcename, MyLogPojo.add, actiondescription, "成功", "1",r_datafield, "", tSdTempletScancodePojo.toString());
			}
		}
		
		tSdMediastdDal.addMediaFile(params.get("cTempletMediaFileId").toString(), 2, id, params.get("cTempletId").toString());
		
		Map updateTempletById = sTempletDal.updateTempletById(params);
		int type = (Integer)updateTempletById.get("type");
		if(type != 0){
			TSTempletPojo oldValue = (TSTempletPojo)updateTempletById.get("oldValue");
			String oldValue_datafield = (String)updateTempletById.get("oldValue_datafield");
			logService.addLogInfo(request, response, "1", "过程行为支持系统", "", funcgroupname, resourcename, MyLogPojo.change, actiondescription, "成功", "1",oldValue_datafield, oldValue.toString(), params.toString());
		}
		
		
		return type;
	}

	
	
	@Override
	public int setSTempletVaildByID(Map params, HttpServletRequest request, HttpServletResponse response) {
		Map updateTempletById = sTempletDal.updateTempletById(params);
		int type = (Integer)updateTempletById.get("type");
		String cIsvaild = (String)params.get("cIsvaild");
		String actiondescription = "任务模板制定-启用";
		if(Integer.parseInt(cIsvaild) == 0){
			actiondescription = "任务模板制定-禁用";
		}
		
		if(type != 0){
			TSTempletPojo oldValue = (TSTempletPojo)updateTempletById.get("oldValue");
			String oldValue_datafield = (String)updateTempletById.get("oldValue_datafield");
			logService.addLogInfo(request, response, "1", "过程行为支持系统", "", "任务管理", "任务模板制定", MyLogPojo.change, actiondescription, "成功", "1", oldValue_datafield, oldValue.toString(), params.toString());
		}
		return type;
	}

	@Override
	public int generateRandomTask(Map params, HttpServletRequest request, HttpServletResponse response) {
		int tag = 0;
		params.put("cTaskId", sTempletDal.getNextTaskId());
		Map ret = tSdTempletScancodeDal.addcJobObjects(params.get("cIsscan").toString(), params.get("cJobObjects").toString(), 2, params.get("cUserId").toString(), params.get("cTaskId").toString());
		int deleteScancodesByIdoldValueType = (Integer)ret.get("deleteScancodesByIdoldValueType");

		try {
			sTempletDal.generateRandomTask(params);
		} catch (Exception e) {
			tag = 1;
		}
		
		if(tag == 0){
			logService.addLogInfo(request, response, "1", "过程行为支持系统", "", "任务管理", "临时任务下发", MyLogPojo.add, "发起临时任务", "成功", "1", "t_task", "", params.toString());
		}
		if (params.get("vRETURNCODE").toString().equals("SUCCESS")) {
			if(deleteScancodesByIdoldValueType != 0){
				List<TSdTempletScancodePojo> deleteScancodesByIdoldValue = (List<TSdTempletScancodePojo>)ret.get("deleteScancodesByIdoldValue");
				String deleteScancodesByIdoldValue_datafield = (String)ret.get("deleteScancodesByIdoldValue_datafield");
				logService.addLogInfo(request, response, "1", "过程行为支持系统", "", "任务管理", "临时任务下发", MyLogPojo.del, "发起临时任务", "成功", "1", deleteScancodesByIdoldValue_datafield, deleteScancodesByIdoldValue.toString(), "");
			}
			List<TSdTempletScancodePojo> r = (List<TSdTempletScancodePojo>)ret.get("r");
			if(r != null){
				String r_datafield = (String)ret.get("r_datafield");
				logService.addLogInfo(request, response, "1", "过程行为支持系统", "", "任务管理", "临时任务下发", MyLogPojo.add, "发起临时任务", "成功", "1", r_datafield, "", r.toString());
			}
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public Pagination getAllPlanSeriesByPid(Map params, Pagination page) {
		Pagination pagination = sTempletDal.getAllPlanSeriesByPid(params, page);
		pagination.setList(ChangeModelList_planSeries(pagination.getList()));
		return pagination;
	}

	@Override
	public Pagination getAllPlanSeries(Map params, Pagination page) {
		Pagination pagination = sTempletDal.getAllPlanSeries(params, page);
		List<Map> plist = pagination.getList();
		if (plist != null && plist.size() > 0) {
			for (Map m : plist) {
				m.put("C_STATE", getStatusName(Integer.parseInt(m.get("C_STATE").toString())));
			}
		}
		return pagination;
	}

	@Override
	public TStdtaskplanSeriesVo getPlanSeriesDetailByID(String pid) {
		return ChangeModel_planSeries(sTempletDal.getPlanSeriesDetailByID(pid));
	}

	@Override
	public int deletePlanSeriesById(String pid) {
		return sTempletDal.deletePlanSeriesById(pid);
	}

}