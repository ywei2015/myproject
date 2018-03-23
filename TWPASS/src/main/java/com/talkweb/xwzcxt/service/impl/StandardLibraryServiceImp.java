package com.talkweb.xwzcxt.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.twdpe.base.org.pojo.Org;
import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.xwzcxt.dal.STempletDal;
import com.talkweb.xwzcxt.dal.StandardLibraryDal;
import com.talkweb.xwzcxt.dal.TSdActnodeItemDal;
import com.talkweb.xwzcxt.dal.TSdMediastdDal;
import com.talkweb.xwzcxt.dal.TSdTempletScancodeDal;
import com.talkweb.xwzcxt.pojo.Actnode;
import com.talkweb.xwzcxt.pojo.MyLogPojo;
import com.talkweb.xwzcxt.pojo.StandardFile;
import com.talkweb.xwzcxt.pojo.StandardLibraryPojo;
import com.talkweb.xwzcxt.pojo.TSTempletPojo;
import com.talkweb.xwzcxt.pojo.TSdActnodeItemPojo;
import com.talkweb.xwzcxt.pojo.TSdMediastdPojo;
import com.talkweb.xwzcxt.pojo.TSdTempletScancodePojo;
import com.talkweb.xwzcxt.pojo.TStdtaskplanSeries;
import com.talkweb.xwzcxt.service.MyLogService;
import com.talkweb.xwzcxt.service.StandardLibraryService;

public class StandardLibraryServiceImp implements StandardLibraryService {
	@Autowired
	private StandardLibraryDal standardLibraryDal;

	@Autowired
	private STempletDal sTempletDal;

	@Autowired
	private TSdTempletScancodeDal tSdTempletScancodeDal;

	@Autowired
	private TSdActnodeItemDal tSdActnodeItemDal;

	@Autowired
	private TSdMediastdDal tSdMediastdDal;

	@Autowired
	private MyLogService logService;
	@Override
	public List<StandardLibraryPojo> getSdFileSortByPid(String pid) {
		return standardLibraryDal.getSdFileSortByPid(pid);
	}

	@Override
	public Pagination queryManagementProcess(StandardFile standardfile, Pagination page) {
		return standardLibraryDal.queryManagementProcess(standardfile, page);
	}

	@Override
	public List getProcessNodeTree() {
		return standardLibraryDal.getProcessNodeTree();
	}

	@Override
	public List getStandardcontent(StandardFile standardfile) {
		return standardLibraryDal.getStandardcontent(standardfile);
	}

	@Override
	public List getAppendix(StandardFile standardfile) {
		return standardLibraryDal.getAppendix(standardfile);
	}

	@Override
	public String getMaxIdOfChildren(StandardLibraryPojo pojo) {
		return standardLibraryDal.getMaxIdOfChildren(pojo);
	}

	@Override
	public void delManagementProcess(StandardFile standardfile) {
		String c_sfile_ids[] = standardfile.getC_sfile_id().split(",");
		for (int i = 0; i < c_sfile_ids.length; i++) {
			standardfile.setC_sfile_id(c_sfile_ids[i]);
			standardLibraryDal.delStandardfile(standardfile);
			standardLibraryDal.delStandardcontent(standardfile);
			standardLibraryDal.delStandardatt(standardfile);
			standardLibraryDal.delFile(standardfile);
		}
	}

	@Override
	public void delStandardFileTreeNode(List<String> ids) {
		standardLibraryDal.delStandardFileTreeNode(ids);
	}

	@Override
	public void addStandardFileTreeNode(StandardLibraryPojo standardLibraryPojo) {
		standardLibraryDal.addStandardFileTreeNode(standardLibraryPojo);		
	}

	@Override
	public void addActnode(Actnode actNode, HttpServletRequest request, HttpServletResponse response) {
		Map addMediaFile_Ret = tSdMediastdDal.addMediaFile(actNode.getC_media_file_id(), 1, actNode.getC_creator(), actNode.getC_actnode_id());
		int deleteMediaStdById_Type = (Integer)addMediaFile_Ret.get("deleteMediaStdByIdRet");
		if(deleteMediaStdById_Type != 0){
			List<TSdMediastdPojo> searchDeleteMediaStdByIdResultMap =(List<TSdMediastdPojo>) addMediaFile_Ret.get("searchDeleteMediaStdByIdResultMap");
			String searchDeleteMediaStdByIdResultMap_datafield = (String)addMediaFile_Ret.get("searchDeleteMediaStdByIdResultMap_datafield");
			logService.addLogInfo(request, response, "1", "过程行为支持系统", "", "标准管理", "5W2H标准管理", MyLogPojo.del, "5W2H标准制定-新增", "成功", "1", searchDeleteMediaStdByIdResultMap_datafield, searchDeleteMediaStdByIdResultMap.toString(), "");
		}
		List<TSdMediastdPojo> r = (List<TSdMediastdPojo>)addMediaFile_Ret.get("r");
		String r_datafield = (String)addMediaFile_Ret.get("r_datafield");
		if(r != null && r_datafield != null){
			logService.addLogInfo(request, response, "1", "过程行为支持系统", "", "标准管理", "5W2H标准管理", MyLogPojo.add, "5W2H标准制定-新增", "成功", "1", r_datafield, "", r.toString());
		}
		
		
		standardLibraryDal.addActnode(actNode);
		logService.addLogInfo(request, response, "1", "过程行为支持系统", "", "标准管理", "5W2H标准管理", MyLogPojo.add, "5W2H标准制定-新增", "成功", "1", "t_sd_actnode", "", actNode.toString());
	}

	@Override
	public void addActnodeItem(Actnode actNode, HttpServletRequest request, HttpServletResponse response) {
		Map addMediaFile_Ret = tSdMediastdDal.addMediaFile(actNode.getC_actitem_media_file_id(), 3, actNode.getC_creator(), actNode.getC_actitem_id());
		int deleteMediaStdById_Type = (Integer)addMediaFile_Ret.get("deleteMediaStdByIdRet");
		if(deleteMediaStdById_Type != 0){
			List<TSdMediastdPojo> searchDeleteMediaStdByIdResultMap =(List<TSdMediastdPojo>) addMediaFile_Ret.get("searchDeleteMediaStdByIdResultMap");
			String searchDeleteMediaStdByIdResultMap_datafield = (String)addMediaFile_Ret.get("searchDeleteMediaStdByIdResultMap_datafield");
			logService.addLogInfo(request, response, "1", "过程行为支持系统", "", "标准管理", "5W2H标准管理", MyLogPojo.del, "5W2H标准制定-新增", "成功", "1", searchDeleteMediaStdByIdResultMap_datafield, searchDeleteMediaStdByIdResultMap.toString(), "");
		}
		List<TSdMediastdPojo> r = (List<TSdMediastdPojo>)addMediaFile_Ret.get("r");
		String r_datafield = (String)addMediaFile_Ret.get("r_datafield");
		if(r != null && r_datafield != null){
			logService.addLogInfo(request, response, "1", "过程行为支持系统", "", "标准管理", "5W2H标准管理", MyLogPojo.add, "5W2H标准制定-新增", "成功", "1", r_datafield, "", r.toString());
		}
		
		standardLibraryDal.addActnodeItem(actNode);
		logService.addLogInfo(request, response, "1", "过程行为支持系统", "", "标准管理", "5W2H标准管理", MyLogPojo.add, "5W2H标准制定-新增", "成功", "1", "t_sd_actnode_item", "", actNode.toString());
	}

	@Override
	public Pagination queryActnode(Actnode actnode, Pagination page) {
		return standardLibraryDal.queryActnode(actnode, page);
	}

	@Override
	public Pagination queryActnodeByParams(Map params, Pagination page){
		return standardLibraryDal.queryActnodeByParams(params, page);
	}

	@Override
	public List queryActnodeItem(Actnode actnode) {
		return standardLibraryDal.queryActnodeItem(actnode);
	}

	@Override
	public void updateStandardFileTreeNode(StandardLibraryPojo pojo) {
		standardLibraryDal.updateStandardFileTreeNode(pojo);
	}

	@Override
	public String getNextActnodeId() {
		return standardLibraryDal.getNextActnodeId();
	}

	@Override
	public void delActNode(String ids, HttpServletRequest request, HttpServletResponse response) {
		String idsArray[] = ids.split(",");
		List<String> idsList = new ArrayList<String>();
		List<String> idsListTemplet = new ArrayList<String>();
		List<String> idsListActitem = new ArrayList<String>();
		List<TSTempletPojo> lt = new ArrayList<TSTempletPojo>();
		for (String id: idsArray) {
			idsList.add(id);
			lt = sTempletDal.getTaskTempletByActnodeID(id);
			for (TSTempletPojo t : lt) {
				idsListTemplet.add(t.getcTempletId());
			}
			List<TSdActnodeItemPojo> la = tSdActnodeItemDal.getActnodeItemsByActnodeID(id);
			for (TSdActnodeItemPojo t : la) {
				idsListActitem.add(t.getcActitemId());
			}
		}
		Map delActNodeRet = standardLibraryDal.delActNode(idsList);
		int delActNodeRet_Type = (Integer)delActNodeRet.get("type");
		if(delActNodeRet_Type != 0){
			List<Actnode> searchDelActNodeRet = (List<Actnode>)delActNodeRet.get("searchDelActNodeRet");
			String searchDelActNodeRet_datafield = (String)delActNodeRet.get("searchDelActNodeRet_datafield");
			logService.addLogInfo(request, response, "1", "过程行为支持系统", "", "标准管理", "5W2H标准管理", MyLogPojo.del, "5W2H标准制定-删除", "成功", "1", searchDelActNodeRet_datafield, searchDelActNodeRet.toString(), "");
		}
		Map delActItemRet = standardLibraryDal.delActItem(idsList);
		int delActItemRet_Type = (Integer)delActItemRet.get("type");
		if(delActItemRet_Type != 0){
			List<Actnode> searchDelActItemRet = (List<Actnode>)delActItemRet.get("searchDelActItemRet");
			String searchDelActItemRet_datafield = (String)delActItemRet.get("searchDelActItemRet_datafield");
			logService.addLogInfo(request, response, "1", "过程行为支持系统", "", "标准管理", "5W2H标准管理", MyLogPojo.del, "5W2H标准制定-删除", "成功", "1", searchDelActItemRet_datafield, searchDelActItemRet.toString(), "");
		}
		Map params = new HashMap();
		if (idsListTemplet.size() > 0) {
			Map deletePlanSeriesByTaskTempletIDRet = sTempletDal.deletePlanSeriesByTaskTempletID(idsListTemplet);
			int type = (Integer)deletePlanSeriesByTaskTempletIDRet.get("type");
			if(type != 0){
				List<TStdtaskplanSeries> deletePlanSeriesByTaskTempletIDOldValue = (List<TStdtaskplanSeries>)deletePlanSeriesByTaskTempletIDRet.get("oldValue");
				String deletePlanSeriesByTaskTempletID_datafield = (String)deletePlanSeriesByTaskTempletIDRet.get("oldValue_datafield");
				logService.addLogInfo(request, response, "1", "过程行为支持系统", "", "标准管理", "5W2H标准管理", MyLogPojo.del, "5W2H标准制定-删除", "成功", "1", deletePlanSeriesByTaskTempletID_datafield, deletePlanSeriesByTaskTempletIDOldValue.toString(), "");
			}
			
			params.put("type", 1);
			params.put("cid", idsListTemplet);
			Map deleteScancodesByIdRet = tSdTempletScancodeDal.deleteScancodesById(params);
			int deleteScancodesByIdRetType = (Integer)deleteScancodesByIdRet.get("deleteScancodesByIdoldValueType");
			if(deleteScancodesByIdRetType != 0){
				List<TSdTempletScancodePojo> deleteScancodesByIdRetOldValue = (List<TSdTempletScancodePojo>)deleteScancodesByIdRet.get("deleteScancodesByIdoldValue");
				String deleteScancodesByIdRet_datafield = (String)deleteScancodesByIdRet.get("deleteScancodesByIdoldValue_datafield");
				logService.addLogInfo(request, response, "1", "过程行为支持系统", "", "标准管理", "5W2H标准管理", MyLogPojo.del, "5W2H标准制定-删除", "成功", "1", deleteScancodesByIdRet_datafield, deleteScancodesByIdRetOldValue.toString(), "");
			}
			
			
		}
		int deleteTaskTempletByActNodeIDTag = sTempletDal.deleteTaskTempletByActNodeID(idsList);
		if(deleteTaskTempletByActNodeIDTag != 0){
			logService.addLogInfo(request, response, "1", "过程行为支持系统", "", "标准管理", "5W2H标准管理", MyLogPojo.del, "5W2H标准制定-删除", "成功", "1", "TWXWZC.T_SD_TASK_TEMPLET", lt.toString(), "");
		}
		Map deleteMediaStdByIdRet = new HashMap();
		List<TSdMediastdPojo> searchDeleteMediaStdByIdResultMap = new ArrayList<TSdMediastdPojo>();
		String searchDeleteMediaStdByIdResultMap_datafield = "";
		int tag;
		params.put("type", 1);
		params.put("cid", idsList);
		deleteMediaStdByIdRet = tSdMediastdDal.deleteMediaStdById(params);
		tag = (Integer)deleteMediaStdByIdRet.get("deleteMediaStdByIdRet");
		if(tag != 0){
			searchDeleteMediaStdByIdResultMap = (List<TSdMediastdPojo>)deleteMediaStdByIdRet.get("searchDeleteMediaStdByIdResultMap");
			searchDeleteMediaStdByIdResultMap_datafield = (String)deleteMediaStdByIdRet.get("searchDeleteMediaStdByIdResultMap_datafield");
			logService.addLogInfo(request, response, "1", "过程行为支持系统", "", "标准管理", "5W2H标准管理", MyLogPojo.del, "5W2H标准制定-删除", "成功", "1", searchDeleteMediaStdByIdResultMap_datafield, searchDeleteMediaStdByIdResultMap.toString(), "");
		}
		if (idsListTemplet.size() > 0) {
			params.put("type", 2);
			params.put("cid", idsListTemplet);
			deleteMediaStdByIdRet = tSdMediastdDal.deleteMediaStdById(params);
			tag = (Integer)deleteMediaStdByIdRet.get("deleteMediaStdByIdRet");
			if(tag != 0){
				searchDeleteMediaStdByIdResultMap = (List<TSdMediastdPojo>)deleteMediaStdByIdRet.get("searchDeleteMediaStdByIdResultMap");
				searchDeleteMediaStdByIdResultMap_datafield = (String)deleteMediaStdByIdRet.get("searchDeleteMediaStdByIdResultMap_datafield");
				logService.addLogInfo(request, response, "1", "过程行为支持系统", "", "标准管理", "5W2H标准管理", MyLogPojo.del, "5W2H标准制定-删除", "成功", "1", searchDeleteMediaStdByIdResultMap_datafield, searchDeleteMediaStdByIdResultMap.toString(), "");
			}
		}
		params.put("type", 3);
		params.put("cid", idsListActitem);
		deleteMediaStdByIdRet = tSdMediastdDal.deleteMediaStdById(params);
		tag = (Integer)deleteMediaStdByIdRet.get("deleteMediaStdByIdRet");
		if(tag != 0){
			searchDeleteMediaStdByIdResultMap = (List<TSdMediastdPojo>)deleteMediaStdByIdRet.get("searchDeleteMediaStdByIdResultMap");
			searchDeleteMediaStdByIdResultMap_datafield = (String)deleteMediaStdByIdRet.get("searchDeleteMediaStdByIdResultMap_datafield");
			logService.addLogInfo(request, response, "1", "过程行为支持系统", "", "标准管理", "5W2H标准管理", MyLogPojo.del, "5W2H标准制定-删除", "成功", "1", searchDeleteMediaStdByIdResultMap_datafield, searchDeleteMediaStdByIdResultMap.toString(), "");
		}
	}

	@Override
	public Org queryDepartByPositionId(String id) {
		return standardLibraryDal.queryDepartByPositionId(id);
	}

	@Override
	public List queryActnodeListByParams(Actnode actnode) {
		return standardLibraryDal.queryActnodeListByParams(actnode);
	}

	@Override
	public List queryActnodeListByParamsForPublic(Actnode actnode) {
		return standardLibraryDal.queryActnodeListByParamsForPublic(actnode);
	}

	@Override
	public int updateActNodeById(Actnode actNode, HttpServletRequest request, HttpServletResponse response) {
		Map addMediaFile_Ret = tSdMediastdDal.addMediaFile(actNode.getC_media_file_id(), 1, actNode.getC_modifier(), actNode.getC_actnode_id());
		int deleteMediaStdById_Type = (Integer)addMediaFile_Ret.get("deleteMediaStdByIdRet");
		if(deleteMediaStdById_Type != 0){
			List<TSdMediastdPojo> searchDeleteMediaStdByIdResultMap =(List<TSdMediastdPojo>) addMediaFile_Ret.get("searchDeleteMediaStdByIdResultMap");
			String searchDeleteMediaStdByIdResultMap_datafield = (String)addMediaFile_Ret.get("searchDeleteMediaStdByIdResultMap_datafield");
			logService.addLogInfo(request, response, "1", "过程行为支持系统", "", "标准管理", "5W2H标准管理", MyLogPojo.del, "5W2H标准制定-修改", "成功", "1", searchDeleteMediaStdByIdResultMap_datafield, searchDeleteMediaStdByIdResultMap.toString(), "");
		}
		List<TSdMediastdPojo> r = (List<TSdMediastdPojo>)addMediaFile_Ret.get("r");
		String r_datafield = (String)addMediaFile_Ret.get("r_datafield");
		if(r != null && r_datafield != null){
			logService.addLogInfo(request, response, "1", "过程行为支持系统", "", "标准管理", "5W2H标准管理", MyLogPojo.add, "5W2H标准制定-修改", "成功", "1", r_datafield, "", r.toString());
		}
		Map updateActNodeById_ret = standardLibraryDal.updateActNodeById(actNode);
		int type = (Integer)updateActNodeById_ret.get("type");
		if(type != 0){
			List<Actnode> oldValue = (List<Actnode>)updateActNodeById_ret.get("oldValue");
			String oldValue_datafield = (String)updateActNodeById_ret.get("oldValue_datafield");
			logService.addLogInfo(request, response, "1", "过程行为支持系统", "", "标准管理", "5W2H标准管理", MyLogPojo.change, "5W2H标准制定-修改", "成功", "1", oldValue_datafield,  oldValue.toString(), actNode.toString());
		}
		return type;
	}

	@Override
	public int updateActnodeItemById(Actnode actNode) {
		return standardLibraryDal.updateActnodeItemById(actNode);
	}

	@Override
	public int delActnodeItemByActNodeId(String id) {
		List<String> idsListActitem = new ArrayList<String>();
		if (idsListActitem.size() > 0) {
			List<TSdActnodeItemPojo> la = tSdActnodeItemDal.getActnodeItemsByActnodeID(id);
			for (TSdActnodeItemPojo t : la) {
				idsListActitem.add(t.getcActitemId());
			}
			Map params = new HashMap();
			params.put("type", 3);
			params.put("cid", idsListActitem);
			tSdMediastdDal.deleteMediaStdById(params);
		}
		return standardLibraryDal.delActnodeItemByActNodeId(id);
	}
}