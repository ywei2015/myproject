package com.talkweb.xwzcxt.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.xwzcxt.dal.DpAreaDal;
import com.talkweb.xwzcxt.dal.TaskDal;
import com.talkweb.xwzcxt.dal.XwzcxtMngDal;
import com.talkweb.xwzcxt.pojo.TSdActnodeItemPojo;
import com.talkweb.xwzcxt.pojo.TTaskPojo;
import com.talkweb.xwzcxt.service.DpPositionServiceI;
import com.talkweb.xwzcxt.service.TSdActnodeItemServiceI;
import com.talkweb.xwzcxt.service.TaskServiceI;
import com.talkweb.xwzcxt.util.DataTypeParseUtil;
import com.talkweb.xwzcxt.vo.DpPositionVo;
import com.talkweb.xwzcxt.vo.TTaskVo;

public class TaskServiceImpl implements TaskServiceI {
	@Autowired
	private TaskDal taskDal;

	@Autowired
	private DpPositionServiceI dpPositionService;

	@Autowired
	private DpAreaDal dpAreaDal;

	@Autowired
	private XwzcxtMngDal xwzcxtMngDal;

	@Autowired
	private TSdActnodeItemServiceI tSdActnodeItemService;

	// 根据ID查询Task
	@Override
	public TTaskVo getTaskByID(String id) {
		return ChangeModel(taskDal.getTaskByID(id));
	}

	// 实体对象 转换为 页面对象t
	private TTaskVo ChangeModel(TTaskPojo tp) {
		TTaskVo tv = new TTaskVo();
		if (tp != null) {
			BeanUtils.copyProperties(tp, tv);
			if (tv.getcTaskKind() == 1) {
				tv.setcTaskKindName("工作任务");
			} else if (tv.getcTaskKind() == 3) {
				tv.setcTaskKindName("工作安排");
			}
			if ((tv.getcTaskType() != 10) && (tv.getcTaskType() != 21)) {
				tv.setcTaskTypename(xwzcxtMngDal.getTaskTypeNameById(tv.getcTaskType().toString()));
			}
			tv.setcStatusName(xwzcxtMngDal.getTaskStatusNameById(tv.getcStatus().toString()));
			tv.setcManageSectionName(xwzcxtMngDal.getManageSectionNameById(tv.getcManageSection().toString()));
			tv.setExecpositionname(this.getPositionNameByUserid(tv.getcExecUserid().toString()));
			String[] orglist = this.getDepartmentByUserid(tv.getcExecUserid().toString());
			tv.setOrgid(orglist[0]);
			tv.setOrgpath(orglist[2]);
			tv.setOrgdepartname(orglist[3]);

			if (null != tv.getcStartTime()) {
				tv.setcStartTime(DataTypeParseUtil.DateConvert(tv.getcStartTime()));
				tv.setcStartTimeString(DataTypeParseUtil.getDateString(tv.getcStartTime()));
			}
			if (null != tv.getcEndTime()) {
				tv.setcEndTime(DataTypeParseUtil.DateConvert(tv.getcEndTime()));
				tv.setcEndTimeString(DataTypeParseUtil.getDateString(tv.getcEndTime()));
			}
			if (null != tv.getcCreateTime()) {
				tv.setcCreateTime(DataTypeParseUtil.DateConvert(tv.getcCreateTime()));
				tv.setcCreateTimeString(DataTypeParseUtil.getDateString(tv.getcCreateTime()));
			}
			if (null != tv.getcPlandownTime()) {
				tv.setcPlandownTime(DataTypeParseUtil.DateConvert(tv.getcPlandownTime()));
				tv.setcPlandownTimeString(DataTypeParseUtil.getDateString(tv.getcPlandownTime()));
			}
			if (null != tv.getcDownTime()) {
				tv.setcDownTime(DataTypeParseUtil.DateConvert(tv.getcDownTime()));
				tv.setcDownTimeString(DataTypeParseUtil.getDateString(tv.getcDownTime()));
			}
			if (null != tv.getcFactEndtime()) {
				tv.setcFactEndtime(DataTypeParseUtil.DateConvert(tv.getcFactEndtime()));
				tv.setcFactEndtimeString(DataTypeParseUtil.getDateString(tv.getcFactEndtime()));
			}
			if (null != tv.getcChkEndtime()) {
				tv.setcChkEndtime(DataTypeParseUtil.DateConvert(tv.getcChkEndtime()));
				tv.setcChkEndtimeString(DataTypeParseUtil.getDateString(tv.getcChkEndtime()));
			}
			if (null != tv.getcEvaluateTime()) {
				tv.setcEvaluateTime(DataTypeParseUtil.DateConvert(tv.getcEvaluateTime()));
				tv.setcEvaluateTimeString(DataTypeParseUtil.getDateString(tv.getcEvaluateTime()));
			}
		}
		return tv;
	}

	// 实体对象List 转换为 页面对象List
	private List<TTaskVo> ChangeModelList(List<TTaskPojo> tlist) {
		List<TTaskVo> vlist = new ArrayList<TTaskVo>();
		if (tlist != null && tlist.size() > 0) {
			for (TTaskPojo t : tlist) {
				TTaskVo v = ChangeModel(t);
				vlist.add(v);
			}
		}
		return vlist;
	}

	private void ChangeModelListMap(List<Map> plist) {
		if (plist != null && plist.size() > 0) {
			for (Map m : plist) {
				String step = "", tyep1 = "", type2 = "";
				if (null != m.get("C_ACTNODE_ID")) {
					List<TSdActnodeItemPojo> l = tSdActnodeItemService.getActnodeItemsByActnodeID(m.get("C_ACTNODE_ID").toString());
					if (l != null && l.size() > 0) {
						for (TSdActnodeItemPojo la : l) {
							step += (la.getcActitemName() + "     ");
							tyep1 += (la.getcGetdatatypename() + "     ");
							type2 += (la.getcCheckdatatypename() + "     ");
						}
					}
				}
				m.put("C_ACTITEMNAME_NAME", step);
				m.put("C_GETDATATYPENAME", tyep1);
				m.put("C_CHECKDATATYPENAME", type2);
				String cTaskKind = m.get("C_TASK_KIND").toString();
				if (cTaskKind.equals("1")) {
					m.put("C_TASK_KIND_NAME", "工作任务");
				} else if (cTaskKind.equals("3")) {
					m.put("C_TASK_KIND_NAME", "工作安排");
				}
				String cIskeyctrl = m.get("C_ISKEYCTRL").toString();
				if (cIskeyctrl.equals("0")) {
					m.put("C_ISKEYCTRL_NAME", "否");
				} else if (cIskeyctrl.equals("1")) {
					m.put("C_ISKEYCTRL_NAME", "是");
				}
				String cIssequence = m.get("C_ISSEQUENCE").toString();
				if (cIssequence.equals("0")) {
					m.put("C_ISSEQUENCE_NAME", "否");
				} else if (cIssequence.equals("1")) {
					m.put("C_ISSEQUENCE_NAME", "是");
				}
				String cStatusName = m.get("C_STATUS_NAME").toString();
				if (cStatusName.equals("已接收")) {
					m.put("C_STATUS_NAME", "已接收未执行");
				} else if (cStatusName.equals("已执行")) {
					m.put("C_STATUS_NAME", "已执行未验证");
				} else if (cStatusName.equals("已验证")) {
					m.put("C_STATUS_NAME", "已验证未评价");
				}
				String cTriggerType = m.get("C_TRIGGER_TYPE").toString();
				if (cTriggerType.equals("1")) {
					m.put("C_TRIGGER_TYPE_NAME", "按5W2H标准自动触发");
				} else if (cTriggerType.equals("2")) {
					m.put("C_TRIGGER_TYPE_NAME", "其它");
				} else if (cTriggerType.equals("3")) {
					m.put("C_TRIGGER_TYPE_NAME", "发起任务手动触发");
				} else if (cTriggerType.equals("4")) {
					m.put("C_TRIGGER_TYPE_NAME", "其它系统触发");
				}
				if (null == m.get("C_ACTNODETYPE")) {
					m.put("C_ACTNODETYPE", "作业类");
				} else {
					String cActnodetype = m.get("C_ACTNODETYPE").toString();
					if (cActnodetype.equals("1")) {
						m.put("C_ACTNODETYPE", "作业类");
					} else if (cActnodetype.equals("2")) {
						m.put("C_ACTNODETYPE", "管理类");
					}
				}
				try {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					if (null != m.get("C_CREATE_TIME")) {
						m.put("C_CREATE_TIME", DataTypeParseUtil.getDateString(sdf.parse(m.get("C_CREATE_TIME").toString())));
					}
					if (null != m.get("C_PLANDOWN_TIME")) {
						m.put("C_PLANDOWN_TIME", DataTypeParseUtil.getDateString(sdf.parse(m.get("C_PLANDOWN_TIME").toString())));
					}
					if (null != m.get("C_DOWN_TIME")) {
						m.put("C_DOWN_TIME", DataTypeParseUtil.getDateString(sdf.parse(m.get("C_DOWN_TIME").toString())));
					}
					if (null != m.get("C_CONFIRM_TIME")) {
						m.put("C_CONFIRM_TIME", DataTypeParseUtil.getDateString(sdf.parse(m.get("C_CONFIRM_TIME").toString())));
					}
					if (null != m.get("C_START_TIME")) {
						m.put("C_START_TIME", DataTypeParseUtil.getDateString(sdf.parse(m.get("C_START_TIME").toString())));
					}
					if (null != m.get("C_END_TIME")) {
						m.put("C_END_TIME", DataTypeParseUtil.getDateString(sdf.parse(m.get("C_END_TIME").toString())));
					}
					if (null != m.get("C_FACT_ENDTIME")) {
						m.put("C_FACT_ENDTIME", DataTypeParseUtil.getDateString(sdf.parse(m.get("C_FACT_ENDTIME").toString())));
					}
					if (null != m.get("C_CHK_PLANTIME")) {
						m.put("C_CHK_PLANTIME", DataTypeParseUtil.getDateString(sdf.parse(m.get("C_CHK_PLANTIME").toString())));
					}
					if (null != m.get("C_CHK_ENDTIME")) {
						m.put("C_CHK_ENDTIME", DataTypeParseUtil.getDateString(sdf.parse(m.get("C_CHK_ENDTIME").toString())));
					}
					if (null != m.get("C_EVALUATE_PLANTIME")) {
						m.put("C_EVALUATE_PLANTIME", DataTypeParseUtil.getDateString(sdf.parse(m.get("C_EVALUATE_PLANTIME").toString())));
					}
					if (null != m.get("C_EVALUATE_TIME")) {
						m.put("C_EVALUATE_TIME", DataTypeParseUtil.getDateString(sdf.parse(m.get("C_EVALUATE_TIME").toString())));
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// 获取所有Task
	@Override
	public List<TTaskVo> getAllTask() {
		return ChangeModelList(taskDal.getAllTask());
	}

	// 根据Userid获取DpPosition对象
	@SuppressWarnings("unused")
	private DpPositionVo getPositionByUserid(String uid) {
		return dpPositionService.getPositionByUserID(uid);
	}

	// 根据Userid获取PositionName
	public String getPositionNameByUserid(String id) {
		return taskDal.getPositionNameByUserID(id);
	}

	// 根据用户ID获取部门信息 0.orgid, 1.orgname, 2.orgpath, 3 orgdepartname
	private String[] getDepartmentByUserid(String uid) {
		Map orglist = taskDal.getDepartmentMapeByUserid(uid);
		String[] strorginfo = new String[4];

		if (orglist != null && orglist.containsKey("PATH")) {
			strorginfo[0] = orglist.get("ORGID").toString();
			strorginfo[1] = orglist.get("ORGNAME").toString();
			String orgpath = orglist.get("PATH").toString();
			strorginfo[2] = orgpath;
			String[] strlist = orgpath.split(";");
			Map dpt = taskDal.getDepartmentNameByID(strlist[1]);
			if (dpt != null && dpt.containsKey("ORGNAME")) {
				strorginfo[3] = dpt.get("ORGNAME").toString();
			}
		}
		return strorginfo;
	}

	// 条件查询任务（含分页）
	@SuppressWarnings("unchecked")
	@Override
	public Pagination getTasks(Map params, Pagination page) {
		Pagination pagination = taskDal.getTasks(params, page);
		ChangeModelListMap(pagination.getList());
		return pagination;
	}

	// 条件查询任务（无分页）
	@SuppressWarnings("unchecked")
	@Override
	public List<Map> getTasksWithoutPage(Map params) {
		List<Map> plist = taskDal.getTasksWithoutPage(params);
		ChangeModelListMap(plist);
		return plist;
	}

	@Override
	public void deleteTaskById(String ids) {
		String idsArray[] = ids.split(",");
		List<String> idsList = new ArrayList<String>();
		for (String id: idsArray) {
			idsList.add(id);
		}
		taskDal.deleteTaskById(idsList);
	}

	@Override
	public boolean updatePanelMatterdetail(Map map) {
		return taskDal.updatePanelMatterdetail(map);
	}
}