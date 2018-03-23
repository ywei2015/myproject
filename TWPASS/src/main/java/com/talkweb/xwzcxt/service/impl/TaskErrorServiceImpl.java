package com.talkweb.xwzcxt.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.xwzcxt.dal.DpAreaDal;
import com.talkweb.xwzcxt.dal.TaskDal;
import com.talkweb.xwzcxt.dal.TaskErrorDal;
import com.talkweb.xwzcxt.dal.XwzcxtMngDal;
import com.talkweb.xwzcxt.pojo.TErrorAffixPojo;
import com.talkweb.xwzcxt.pojo.TErrorFeedbackPojo;
import com.talkweb.xwzcxt.pojo.TErrorInfoPojo;
import com.talkweb.xwzcxt.pojo.TTaskPojo;
import com.talkweb.xwzcxt.service.TaskErrorServiceI;
import com.talkweb.xwzcxt.util.DataTypeParseUtil;
import com.talkweb.xwzcxt.vo.TErrorAffixVo;
import com.talkweb.xwzcxt.vo.TErrorFeedbackVo;
import com.talkweb.xwzcxt.vo.TErrorInfoVo;

public class TaskErrorServiceImpl implements TaskErrorServiceI {
	@Autowired
	private TaskErrorDal taskErrorDal;

	@Autowired
	private DpAreaDal dpAreaDal;

	@Autowired
	private XwzcxtMngDal xwzcxtMngDal;

	@Autowired
	private TaskDal taskDal;



	private void ChangeModelListMap(List<Map> plist) {
		if (plist != null && plist.size() > 0) {
			for (Map m : plist) {
				String cErrKind = m.get("C_ERR_KIND").toString();
				if (cErrKind.equals("1")) {
					m.put("C_ERR_KIND_NAME", "工作执行异常");
				} else {
					m.put("C_ERR_KIND_NAME", "人工发起异常");
				}
				if (m.get("C_AREA_NAME") != null) {
					String cAreaName = m.get("C_AREA_NAME").toString();
					if (-1 < cAreaName.indexOf("长沙卷烟厂,")) {
						cAreaName = cAreaName.replace("长沙卷烟厂,", "");
					}
					if (-1 < cAreaName.indexOf(",")) {
						cAreaName = cAreaName.replaceAll(",", "");
					}
					m.put("C_AREA_NAME", cAreaName);
				}
				try {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					if (m.get("C_OCCUR_TIME") != null) {
						m.put("C_OCCUR_TIME", DataTypeParseUtil.getDateString(sdf.parse(m.get("C_OCCUR_TIME").toString())));
					}
					if (m.get("C_WRITE_TIME") != null) {
						m.put("C_WRITE_TIME", DataTypeParseUtil.getDateString(sdf.parse(m.get("C_WRITE_TIME").toString())));
					}
					if (m.get("C_UPLOAD_TIME") != null) {
						m.put("C_UPLOAD_TIME", DataTypeParseUtil.getDateString(sdf.parse(m.get("C_UPLOAD_TIME").toString())));
					}
					if (m.get("C_CLOSE_TIME") != null) {
						m.put("C_CLOSE_TIME", DataTypeParseUtil.getDateString(sdf.parse(m.get("C_CLOSE_TIME").toString())));
					}
					if (m.get("C_SUGGESTEND_TIME") != null) {
						m.put("C_SUGGESTEND_TIME", DataTypeParseUtil.getDateString(sdf.parse(m.get("C_SUGGESTEND_TIME").toString())));
					}
					if (m.get("C_CHK_PLANTIME") != null) {
						m.put("C_CHK_PLANTIME", DataTypeParseUtil.getDateString(sdf.parse(m.get("C_CHK_PLANTIME").toString())));
					}
					if (m.get("C_CHK_TIME") != null) {
						m.put("C_CHK_TIME", DataTypeParseUtil.getDateString(sdf.parse(m.get("C_CHK_TIME").toString())));
					}
					if (m.get("C_CHK_STARTTIME") != null) {
						m.put("C_CHK_STARTTIME", DataTypeParseUtil.getDateString(sdf.parse(m.get("C_CHK_STARTTIME").toString())));
					}
					if (m.get("C_EVALUATE_PLANTIME") != null) {
						m.put("C_EVALUATE_PLANTIME", DataTypeParseUtil.getDateString(sdf.parse(m.get("C_EVALUATE_PLANTIME").toString())));
					}
					if (m.get("C_EVALUATE_TIME") != null) {
						m.put("C_EVALUATE_TIME", DataTypeParseUtil.getDateString(sdf.parse(m.get("C_EVALUATE_TIME").toString())));
					}
					if (m.get("C_EVALUATE_STARTTIME") != null) {
						m.put("C_EVALUATE_STARTTIME", DataTypeParseUtil.getDateString(sdf.parse(m.get("C_EVALUATE_STARTTIME").toString())));
					}
					String cIsclose = m.get("C_ISCLOSE").toString();
					if (cIsclose.equals("0")) {
						m.put("C_ISCLOSE_NAME", "未完成");
					} else {
						if (sdf.parse(m.get("C_CLOSE_TIME").toString()).getTime() > sdf.parse(m.get("C_SUGGESTEND_TIME").toString()).getTime()) {
							m.put("C_ISCLOSE_NAME", "逾期完成");
						} else {
							m.put("C_ISCLOSE_NAME", "已完成");
						}
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private TErrorInfoVo ChangeModelTErrorInfo(TErrorInfoPojo tp,String...cerrid) {
		TErrorInfoVo tv = new TErrorInfoVo();
		String[] errIdO=cerrid;
		Map map=new HashMap();
		map.put("errId", errIdO[0]);
		if (tp != null) {
			BeanUtils.copyProperties(tp, tv);
			if (tv.getcErrKind() != null) {
				tv.setcErrKindName(tv.getcErrKind() == 1 ? "工作执行异常" : "人工发起异常");
			}
			String cIsclose = tv.getcIsclose();
			if (cIsclose != null && !cIsclose.isEmpty()) {
				if (cIsclose.equals("0")) {
					tv.setcIscloseName("未完成");
				} else {
					if (tv.getcCloseTime().getTime() > tv.getcSuggestendTime().getTime()) {
						tv.setcIscloseName("逾期完成");
					} else {
						tv.setcIscloseName("已完成");
					}
				}
			}
			if (tv.getcArea() != null && !tv.getcArea().isEmpty()) {
				String fullname = dpAreaDal.getAreaByID(tv.getcArea()).getFullname();
				if (-1 < fullname.indexOf("长沙卷烟厂,")) {
					fullname = fullname.replace("长沙卷烟厂,", "");
				}
				if (-1 < fullname.indexOf(",")) {
					fullname = fullname.replaceAll(",", "");
				}
				tv.setcAreaName(fullname);
			}
			if (tv.getcManageSection() != null) {
				tv.setcManageSectionName(xwzcxtMngDal.getManageSectionNameById(tv.getcManageSection().toString()));
			}
			if (tv.getcTaskId() != null) {
				TTaskPojo t = taskDal.getTaskByID(tv.getcTaskId().toString());
				if (t != null) {
					tv.setcTaskName(t.getcTaskName());
				}
			}
			if (tv.getcOccurTime() != null) {
				tv.setcOccurTimeString(DataTypeParseUtil.getDateString(tv.getcOccurTime()));
			}
			if (tv.getcWriteTime() != null) {
				tv.setcWriteTimeString(DataTypeParseUtil.getDateString(tv.getcWriteTime()));
			}
			if (tv.getcUploadTime() != null) {
				tv.setcUploadTimeString(DataTypeParseUtil.getDateString(tv.getcUploadTime()));
			}
			if (tv.getcCloseTime() != null) {
				tv.setcCloseTimeString(DataTypeParseUtil.getDateString(tv.getcCloseTime()));
			}
			if (tv.getcSuggestendTime() != null) {
				tv.setcSuggestendTimeString(DataTypeParseUtil.getDateString(tv.getcSuggestendTime()));
			}
			if (tv.getcChkPlantime() != null) {
				tv.setcChkPlantimeString(DataTypeParseUtil.getDateString(tv.getcChkPlantime()));
			}
			if (tv.getcChkTime() != null) {
				tv.setcChkTimeString(DataTypeParseUtil.getDateString(tv.getcChkTime()));
			}
			if (tv.getcChkStarttime() != null) {
				tv.setcChkStarttimeString(DataTypeParseUtil.getDateString(tv.getcChkStarttime()));
			}
			if (tv.getcEvaluatePlantime() != null) {
				tv.setcEvaluatePlantimeString(DataTypeParseUtil.getDateString(tv.getcEvaluatePlantime()));
			}
			if (tv.getcEvaluateTime() != null) {
				tv.setcEvaluateTimeString(DataTypeParseUtil.getDateString(tv.getcEvaluateTime()));
			}
			if (tv.getcEvaluateStarttime() != null) {
				tv.setcEvaluateStarttimeString(DataTypeParseUtil.getDateString(tv.getcEvaluateStarttime()));
			}
			if (tv.getcChkUserid() != null) {
				tv.setcChkUsername(taskErrorDal.getDpUserByID(tv.getcChkUserid()).get("DISPLAYNAME").toString());
			}
			if (tv.getcEvaluateUserid() != null) {
				tv.setcEvaluateUsername(taskErrorDal.getDpUserByID(tv.getcEvaluateUserid()).get("DISPLAYNAME").toString());
			}
		}
		try{
			String erreaName=getErreanameById(map)==null?"":getErreanameById(map);
			tv.setcAreaName(erreaName);
		}catch(Exception e){
			e.printStackTrace();
			}	
		return tv;
	}

	private List<TErrorInfoVo> ChangeModelListTErrorInfo(List<TErrorInfoPojo> tlist) {
		List<TErrorInfoVo> vlist = new ArrayList<TErrorInfoVo>();
		if (tlist != null && tlist.size() > 0) {
			for (TErrorInfoPojo t : tlist) {
				TErrorInfoVo v = ChangeModelTErrorInfo(t);
				vlist.add(v);
			}
		}
		return vlist;
	}

	private TErrorFeedbackVo ChangeModelTaskErrorFeedback(TErrorFeedbackPojo tp) {
		TErrorFeedbackVo tv = new TErrorFeedbackVo();
		if (tp != null) {
			BeanUtils.copyProperties(tp, tv);
			if (tv.getcStatus() != null) {
				tv.setcStatusName(xwzcxtMngDal.getTaskStatusNameById(tv.getcStatus().toString()));
			}
			if (tv.getcFeedbackTime() != null) {
				tv.setcFeedbackTimeString(DataTypeParseUtil.getDateString(tv.getcFeedbackTime()));
			}
			if (tv.getcReceiverTime() != null) {
				tv.setcReceiverTimeString(DataTypeParseUtil.getDateString(tv.getcReceiverTime()));
			}
			if (tv.getcEndTime() != null) {
				tv.setcEndTimeString(DataTypeParseUtil.getDateString(tv.getcEndTime()));
			}
			if (tv.getcFactdealTime() != null) {
				tv.setcFactdealTimeString(DataTypeParseUtil.getDateString(tv.getcFactdealTime()));
			}
		}
		
		return tv;
	}

	private List<TErrorFeedbackVo> ChangeModelTaskErrorFeedback(List<TErrorFeedbackPojo> tlist) {
		List<TErrorFeedbackVo> vlist = new ArrayList<TErrorFeedbackVo>();
		if (tlist != null && tlist.size() > 0) {
			for (TErrorFeedbackPojo t : tlist) {
				TErrorFeedbackVo v = ChangeModelTaskErrorFeedback(t);
				vlist.add(v);
			}
		}
		return vlist;
	}

	private TErrorAffixVo ChangeModelTaskErrorAffix(TErrorAffixPojo tp) {
		TErrorAffixVo tv = new TErrorAffixVo();
		if (tp != null) {
			BeanUtils.copyProperties(tp, tv);
		}
		return tv;
	}

	private List<TErrorAffixVo> ChangeModelTaskErrorAffix(List<TErrorAffixPojo> tlist) {
		List<TErrorAffixVo> vlist = new ArrayList<TErrorAffixVo>();
		if (tlist != null && tlist.size() > 0) {
			for (TErrorAffixPojo t : tlist) {
				TErrorAffixVo v = ChangeModelTaskErrorAffix(t);
				vlist.add(v);
			}
		}
		return vlist;
	}

	@Override
	public Pagination getAllTaskErrorInfo(Map params, Pagination page) {
		Pagination pagination = taskErrorDal.getAllTaskErrorInfo(params, page);
		ChangeModelListMap(pagination.getList());
		return pagination;
	}

	@Override
	public List<Map> getAllTaskErrorInfoWithoutPage(Map params) {
		List<Map> plist = taskErrorDal.getAllTaskErrorInfoWithoutPage(params);
		ChangeModelListMap(plist);
		return plist;
	}

	@Override
	public TErrorInfoVo getTaskErrorInfoDetailByID(String cErrId) {
		return ChangeModelTErrorInfo(taskErrorDal.getTaskErrorInfoDetailByID(cErrId),cErrId);
	}

	@Override
	public void deleteTaskErrorById(String ids) {
		String idsArray[] = ids.split(",");
		List<String> idsList = new ArrayList<String>();
		for (String id: idsArray) {
			idsList.add(id);
		}
		taskErrorDal.deleteTaskErrorById(idsList);
	}

	@Override
	public Map getTaskErrorFeedbackDetailByID(String cErrId) {
		Map map = new HashMap();
		int num = Integer.parseInt(taskErrorDal.getTaskErrorFeedbackNumberByID(cErrId));
		Map params = new HashMap();
		params.put("cErrId", cErrId);
		params.put("cDealType", 2);
		TErrorFeedbackPojo t = taskErrorDal.getTaskErrorFeedbackDetailByID(params);
		if (null != t) {
			num--;
		}
		List l1 = taskErrorDal.getTaskErrorFeedbackLotnoByID(cErrId);
		List l2 = taskErrorDal.getTaskErrorAffixLotnoByID(cErrId);
		List l = new ArrayList();
		for (int i = 0; i < l1.size(); i++) {
			l.add(((Map) l1.get(i)).get("C_FEEDBACK_LOTNO"));
		}
		for (int i = 0; i < l2.size(); i++) {
			String cRecordLotno = (String) ((Map) l2.get(i)).get("C_RECORD_LOTNO");
			if (!l.contains(cRecordLotno)) {
				l.add(cRecordLotno);
			}
		}
		List data = new ArrayList();
		for (int i = 0; i < l.size(); i++) {
			Map paramsTemp = new HashMap();
			paramsTemp.put("cErrId", cErrId);
			paramsTemp.put("cFeedbackLotno", l.get(i));
			Map mapTemp = new HashMap();
			List dataTemp = ChangeModelTaskErrorFeedback(taskErrorDal.getTaskErrorFeedbackDetailByLotno(paramsTemp));
			List descriptionList = taskErrorDal.getTaskErrorAffixDescription(paramsTemp);
			List description = new ArrayList();
			for (int j = 0; j < descriptionList.size(); j++) {
				TErrorAffixPojo tp = (TErrorAffixPojo) descriptionList.get(j);
				Map pathMap = new HashMap();
				pathMap.put("id", tp.getcSubmitUserid());
				pathMap.put("name", tp.getcSubmitUsername());
				pathMap.put("description", tp.getcValue());
				description.add(pathMap);
			}
			List pathList = taskErrorDal.getTaskErrorAffixFile(paramsTemp);
			List file = new ArrayList();
			for (int j = 0; j < pathList.size(); j++) {
				TErrorAffixPojo tp = (TErrorAffixPojo) pathList.get(j);
				Map pathMap = new HashMap();
				pathMap.put("id", tp.getcSubmitUserid());
				pathMap.put("name", tp.getcSubmitUsername());
				pathMap.put("type", tp.getcFileType());
				pathMap.put("path", tp.getcFilePath());
				file.add(pathMap);
			}
			mapTemp.put("data", dataTemp);
			mapTemp.put("description", description);
			mapTemp.put("file", file);
			data.add(mapTemp);
		}
		map.put("num", num);
		map.put("data", data);
		return map;
	}

	@Override
	public List getTaskErrorFeedbackDetailByLotno(Map params) {
		return ChangeModelTaskErrorFeedback(taskErrorDal.getTaskErrorFeedbackDetailByLotno(params));
	}


	public String getErreanameById(Map cErrId) {
		return taskErrorDal.getErreanameById(cErrId);
	}
}