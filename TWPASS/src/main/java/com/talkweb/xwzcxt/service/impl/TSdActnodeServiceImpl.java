package com.talkweb.xwzcxt.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.common.AppConstants;
import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.xwzcxt.dal.TSdActnodeDal;
import com.talkweb.xwzcxt.dal.TSdMediastdDal;
import com.talkweb.xwzcxt.pojo.Actnode;
import com.talkweb.xwzcxt.pojo.MyLogPojo;
import com.talkweb.xwzcxt.pojo.TSdActnodePojo;
import com.talkweb.xwzcxt.pojo.TSdMediastdPojo;
import com.talkweb.xwzcxt.service.MyLogService;
import com.talkweb.xwzcxt.service.TSdActnodeServiceI;

public class TSdActnodeServiceImpl implements TSdActnodeServiceI {
	@Autowired
	private TSdActnodeDal tsdActnodeDal;

	@Autowired
	private TSdMediastdDal tSdMediastdDal;

	@Autowired
	private MyLogService logService;
	
	private void ChangeModel(TSdActnodePojo t) {
		if (t != null) {
			if (t.getcIskeyctrl() != null) {
				t.setcIskeyctrlname(t.getcIskeyctrl().intValue() == 0 ? "否" : "是");
			}
			if (t.getcIssequence() != null) {
				t.setcIssequenceName(t.getcIssequence().longValue() == 0 ? "否" : "是");
			}
			if (t.getcIsrandom() != null) {
				t.setcIsrandomName(t.getcIsrandom().longValue() == 0 ? "否" : "是");
			}
			if (t.getcIsscan() != null) {
				t.setcIsscanName(t.getcIsscan().longValue() == 0 ? "否" : "是");
			}
			if ((t.getcStarttimeExecName() == null) && (t.getcStarttimeExec() != null)) {
				t.setcStarttimeExecName(t.getcStarttimeExec());
			}
			if ((t.getcEndtimeExecName() == null) && (t.getcEndtimeExec() != null)) {
				t.setcEndtimeExecName(t.getcEndtimeExec());
			}
			if ((t.getcTimelimitCheckName() == null) && (t.getcTimelimitCheck() != null)) {
				t.setcTimelimitCheckName(t.getcTimelimitCheck());
			}
			if ((t.getcTimelimitReviewName() == null) && (t.getcTimelimitReview() != null)) {
				t.setcTimelimitReviewName(t.getcTimelimitReview());
			}
		}
	}

	private void ChangeModelList(List<TSdActnodePojo> tlist) {
		if (tlist != null && tlist.size() > 0) {
			for (TSdActnodePojo t : tlist) {
				ChangeModel(t);
			}
		}
	}

	@Override
	public Pagination getAllActNodesByConditions(Actnode actnode, Pagination page) {
		Pagination pagination = tsdActnodeDal.getAllActNodesByConditions(actnode, page);
		ChangeModelList(pagination.getList());
		return pagination;
	}

	@Override
	public List getAllActNodesByConditionsWithoutPage(Actnode actnode) {
		List list = tsdActnodeDal.getAllActNodesByConditionsWithoutPage(actnode);
		ChangeModelList(list);
		return list;
	}

	@Override
	public TSdActnodePojo getActNodeByID(String aid) {
		TSdActnodePojo t = tsdActnodeDal.getActNodeByID(aid);
		ChangeModel(t);
		Map params = new HashMap();
		params.put("type", 1);
		params.put("cid", t.getcActnodeId());
		List<TSdMediastdPojo> l = tSdMediastdDal.getAllMediaStdById(params);
		String cMediaFileId = "", cMediaFileType = "", cMediaFilePath = "";
		for (TSdMediastdPojo tsd : l) {
			cMediaFileId += (tsd.getcSdfileId() + ",");
			cMediaFileType += (tsd.getcSdfileType() + ",");
			cMediaFilePath += (AppConstants.getFilePathPrefix() + tsd.getcSdfilePath() + ",");
		}
		if (!cMediaFileId.equals("") || !cMediaFileType.equals("") || !cMediaFilePath.equals("")) {
			cMediaFileId = cMediaFileId.substring(0, cMediaFileId.length() - 1);
			cMediaFileType = cMediaFileType.substring(0, cMediaFileType.length() - 1);
			cMediaFilePath = cMediaFilePath.substring(0, cMediaFilePath.length() - 1);
		}
		t.setcMediaFileId(cMediaFileId);
		t.setcMediaFileType(cMediaFileType);
		t.setcMediaFilePath(cMediaFilePath);
		return t;
	}

	@Override
	public void addActnode(TSdActnodePojo actnode, HttpServletRequest request, HttpServletResponse response) {
		tsdActnodeDal.addActnode(actnode);
		logService.addLogInfo(request, response, "1", "过程行为支持系统", "", "任务管理", "任务模板制定", MyLogPojo.add, "任务模板制定-模板复制", "成功", "1", "TWXWZC.T_SD_ACTNODE", "", actnode.toString());
	}

	@Override
	public String getDepartmentPath(String positionId) {
		return tsdActnodeDal.getDepartmentPath(positionId);
	}

	@Override
	public String getDepartmentName(String departId) {
		return tsdActnodeDal.getDepartmentName(departId);
	}

	@Override
	public String selectPathNameByID(String departId) {
		String paths[] = tsdActnodeDal.selectPathNameByID(departId).split(",");
		String path = "";
		for (int i = paths.length - 1; i > -1; i--) {
			if (!paths[i].equals("长沙卷烟厂")) {
				path += paths[i] + ",";
			}
		}
		if (!path.equals("")) {
			path = path.substring(0, path.length() - 1);
		}
		return path;
	}
}