package com.talkweb.xwzcxt.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.xwzcxt.dal.DpAreaDal;
import com.talkweb.xwzcxt.dal.MobileErrInfoDal;
import com.talkweb.xwzcxt.dal.TaskDal;
import com.talkweb.xwzcxt.dal.XwzcxtMngDal;
import com.talkweb.xwzcxt.pojo.TErrorAffixPojo;
import com.talkweb.xwzcxt.pojo.TErrorFeedbackPojo;
import com.talkweb.xwzcxt.pojo.TErrorInfoPojo;
import com.talkweb.xwzcxt.pojo.TTaskPojo;
import com.talkweb.xwzcxt.service.MobileErrInfoService; 
import com.talkweb.xwzcxt.util.DataTypeParseUtil;
import com.talkweb.xwzcxt.vo.TErrorAffixVo;
import com.talkweb.xwzcxt.vo.TErrorFeedbackVo;
import com.talkweb.xwzcxt.vo.TErrorInfoVo;

public class MobileErrInfoServiceImpl implements MobileErrInfoService {
	@Autowired
	private MobileErrInfoDal mobileerrinfodal;

	@Autowired
	private DpAreaDal dpAreaDal;

	@Autowired
	private XwzcxtMngDal xwzcxtMngDal;

	@Autowired
	private TaskDal taskDal;

	private TErrorInfoVo ChangeModelTErrorInfo(TErrorInfoPojo tp) {
		TErrorInfoVo tv = new TErrorInfoVo();
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
	public TErrorInfoVo getTaskErrorInfoDetailByID(String cErrId) {
		return ChangeModelTErrorInfo(mobileerrinfodal.getTaskErrorInfoDetailByID(cErrId));
	}

	@Override
	public TErrorInfoPojo 	getVerifyEvalInfo(String errId) {
		return (TErrorInfoPojo) mobileerrinfodal.getVerifyEvalInfo(errId);
	}

	@Override
	public Map getTaskErrorFeedbackDetailByID(String cErrId) {
		Map map = new HashMap();
		int num = Integer.parseInt(mobileerrinfodal.getTaskErrorFeedbackNumberByID(cErrId));
		Map params = new HashMap();
		params.put("cErrId", cErrId);
		params.put("cDealType", 2);
		TErrorFeedbackPojo t = mobileerrinfodal.getTaskErrorFeedbackDetailByID(params);
		if (null != t) {
			num--;
		}
		List l = mobileerrinfodal.getTaskErrorFeedbackLotnoByID(cErrId);
		String cFeedbackLotno = "";
		for (int i = 0; i < l.size(); i++) {
			Map temp = (Map) l.get(i);
			cFeedbackLotno += (temp.get("C_FEEDBACK_LOTNO") + ",");
		}
		if (!cFeedbackLotno.isEmpty()) {
			cFeedbackLotno = cFeedbackLotno.substring(0, cFeedbackLotno.length() - 1);
		}
		map.put("num", num);
		map.put("cFeedbackLotno", cFeedbackLotno);
		return map;
	}

	@Override
	public List getTaskErrorFeedbackDetailByLotno(Map params) {
		return ChangeModelTaskErrorFeedback(mobileerrinfodal.getTaskErrorFeedbackDetailByLotno(params));
	}

	@Override
	public Map getTaskErrorAffixDetailByID(String cErrId) {
		Map map = new HashMap();
		map.put("description", mobileerrinfodal.getTaskErrorAffixDescription(cErrId));
		List l = mobileerrinfodal.getTaskErrorAffixFile(cErrId);
		String path = "";
		for (int i = 0; i < l.size(); i++) {
			TErrorAffixPojo t = (TErrorAffixPojo) l.get(i);
			path += (t.getcFileType() + "," + t.getcFilePath() + ";");
		}
		if (!path.isEmpty()) {
			path = path.substring(0, path.length() - 1);
		}
		map.put("path", path);
		return map;
	}

	/*GuveXie 20141216新增 */
	@Override
	public Map getErrorAffixDetailByIdAndLotNo(Map param) {
		Map map = new HashMap();
		map.put("description", mobileerrinfodal.getErrorAffixDesByMap(param));
		List l = mobileerrinfodal.getErrorAffixFileByMap(param);
		String path = "";
		for (int i = 0; i < l.size(); i++) {
			TErrorAffixPojo t = (TErrorAffixPojo) l.get(i);
			path += (t.getcFilePath() + ",");
		}
		if (!path.isEmpty()) {
			path = path.substring(0, path.length() - 1);
		}
		map.put("path", path);
		return map;
	}
}