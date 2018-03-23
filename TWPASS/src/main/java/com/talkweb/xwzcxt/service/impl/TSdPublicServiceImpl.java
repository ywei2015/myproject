package com.talkweb.xwzcxt.service.impl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.xwzcxt.dal.TSdPublicDal;
import com.talkweb.xwzcxt.pojo.MyLogPojo;
import com.talkweb.xwzcxt.pojo.TSdPublicPojo;
import com.talkweb.xwzcxt.service.TSdPublicServiceI;

public class TSdPublicServiceImpl implements TSdPublicServiceI {
	@Autowired
	private TSdPublicDal tSdPublicDal;
	
	@Autowired
	private MyLogServiceImpl logService;

	@Override
	public List getPublicNodesTree() {
		return tSdPublicDal.getPublicNodesTree();
	}

	@Override
	public TSdPublicPojo getPublicNodeById(String cPublicId) {
		return tSdPublicDal.getPublicNodeById(cPublicId);
	}

	@Override
	public int addPublicNode(TSdPublicPojo t, HttpServletRequest request, HttpServletResponse response) {
		int type = tSdPublicDal.addPublicNode(t);
		if(type != 0){
			logService.addLogInfo(request, response, "1", "过程行为支持系统", "", "标准管理", "5W2H标准管理", MyLogPojo.add, "5W2H标准制定(通用)-通用标准树-新增", "成功", "1", "TWXWZC.T_SD_PUBLIC", "", t.toString());
		}
		return type;
		
	}

	@Override
	public int updatePublicNodeById(TSdPublicPojo t, HttpServletRequest request, HttpServletResponse response) {
		Map ret = tSdPublicDal.updatePublicNodeById(t);
		int type =(Integer) ret.get("type");
		if(type != 0){
			TSdPublicPojo oldValue = (TSdPublicPojo)ret.get("oldValue");
			String oldValue_datafield = (String)ret.get("oldValue_datafield");
			if(oldValue != null && oldValue_datafield != null){
				logService.addLogInfo(request, response, "1", "过程行为支持系统", "", "标准管理", "5W2H标准管理", MyLogPojo.change, "5W2H标准制定(通用)-通用标准树-修改", "成功", "1", oldValue_datafield, oldValue.toString(), t.toString());
			}
		}
		return type;
	}

	@Override
	public int deletePublicNodesById(TSdPublicPojo t, HttpServletRequest request, HttpServletResponse response) {
		Map ret = tSdPublicDal.deletePublicNodesById(t);
		int type =(Integer) ret.get("type");
		if(type != 0){
			TSdPublicPojo oldValue = (TSdPublicPojo)ret.get("oldValue");
			String oldValue_datafield = (String)ret.get("oldValue_datafield");
			if(oldValue != null && oldValue_datafield != null){
				logService.addLogInfo(request, response, "1", "过程行为支持系统", "", "标准管理", "5W2H标准管理", MyLogPojo.del, "5W2H标准制定(通用)-通用标准树-删除", "成功", "1", oldValue_datafield, oldValue.toString(), "");
			}
		}
		return type;
	}
}