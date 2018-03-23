package com.talkweb.xwzcxt.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.common.AppConstants;
import com.talkweb.xwzcxt.dal.TSdActnodeItemDal;
import com.talkweb.xwzcxt.dal.TSdMediastdDal;
import com.talkweb.xwzcxt.pojo.MyLogPojo;
import com.talkweb.xwzcxt.pojo.TSdActnodeItemPojo;
import com.talkweb.xwzcxt.pojo.TSdMediastdPojo;
import com.talkweb.xwzcxt.service.MyLogService;
import com.talkweb.xwzcxt.service.TSdActnodeItemServiceI;

public class TSdActnodeItemServiceImpl implements TSdActnodeItemServiceI {
	@Autowired
	private TSdActnodeItemDal tSdActnodeItemDal;

	@Autowired
	private TSdMediastdDal tSdMediastdDal;
	
	@Autowired
	private MyLogService logService;

	@Override
	public List<TSdActnodeItemPojo> getActnodeItemsByActnodeID(String aid) {
		List<TSdActnodeItemPojo> l = tSdActnodeItemDal.getActnodeItemsByActnodeID(aid);
		for (TSdActnodeItemPojo t : l) {
			Map params = new HashMap();
			params.put("type", 3);
			params.put("cid", t.getcActitemId());
			List<TSdMediastdPojo> lsd = tSdMediastdDal.getAllMediaStdById(params);
			String cActitemMediaFileId = "", cActitemMediaFileType = "", cActitemMediaFilePath = "";
			for (TSdMediastdPojo tsd : lsd) {
				cActitemMediaFileId += (tsd.getcSdfileId() + ",");
				cActitemMediaFileType += (tsd.getcSdfileType() + ",");
				cActitemMediaFilePath += (AppConstants.getFilePathPrefix() + tsd.getcSdfilePath() + ",");
			}
			if (!cActitemMediaFileId.equals("") || !cActitemMediaFileType.equals("") || !cActitemMediaFilePath.equals("")) {
				cActitemMediaFileId = cActitemMediaFileId.substring(0, cActitemMediaFileId.length() - 1);
				cActitemMediaFileType = cActitemMediaFileType.substring(0, cActitemMediaFileType.length() - 1);
				cActitemMediaFilePath = cActitemMediaFilePath.substring(0, cActitemMediaFilePath.length() - 1);
			}
			t.setcActitemMediaFileId(cActitemMediaFileId);
			t.setcActitemMediaFileType(cActitemMediaFileType);
			t.setcActitemMediaFilePath(cActitemMediaFilePath);
		}
		return l;
	}

	@Override
	public void addActnodeItem(TSdActnodeItemPojo actnodeItem, HttpServletRequest request, HttpServletResponse response) {
		tSdActnodeItemDal.addActnodeItem(actnodeItem);
		logService.addLogInfo(request, response, "1", "过程行为支持系统", "", "任务管理", "任务模板制定", MyLogPojo.add, "任务模板制定-模板复制", "成功", "1", "TWXWZC.T_SD_ACTNODE_ITEM", "", actnodeItem.toString());
	}
}