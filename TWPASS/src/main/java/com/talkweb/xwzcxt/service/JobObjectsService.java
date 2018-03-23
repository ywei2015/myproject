package com.talkweb.xwzcxt.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.xwzcxt.pojo.TTaskPointcheckPojo;
import com.talkweb.xwzcxt.pojo.TbIntegrateInfo;

public interface JobObjectsService {

	public List getTableTypeTree();
	public void getObjectInfo(Pagination page,Map tb);
	public List getColumnsConfig(Map tb);
	public int deleteObjects(Map cBasedataId);
	public int updateObject(TbIntegrateInfo tb );
	public int addObject(TbIntegrateInfo tb);
	public String getMaxBasedataId(TbIntegrateInfo tb);
	public int printCode(Map param);
	public String getOrgname(Map cOrgid);
	public TbIntegrateInfo getCurrentPrintCount(Map cBasedataId);
	public TbIntegrateInfo getTabletypeById(Map cTabletypeId );
	public List getDynamicsByType(Map cTabletypeId );
	public int updateTabletype (TbIntegrateInfo tbIntegrateInfo );
	public int addtTabletype (TbIntegrateInfo tbIntegrateInfo);
	public int addDynamicCol( TbIntegrateInfo tbIntegrateInfo);
	public int deleteTabletype(Map cTabletypeId);
	public int deleteTabletypeCol(Map cTabletypeId);
	public int getCountColum(Map cTabletypeId);
	public int updateDynamicCol(Map map);
	public int getDynamicCount(Map cTabletypeId);
	public int updateColCount(Map map);
	public int deleteDynamicById(Map cDycolId);
	public int checkHasIntegrateData(Map cTabletypeId);
	public int verifyRepeatObject( TbIntegrateInfo tbIntegrateInfo);
	public List selectJobObjectTree(Map cBasedataUserCode);
	public List getTableTypeInfo(Map cTabletypeId);
	public int addTaskPointCheck(TTaskPointcheckPojo ttaskPointCheck);
	public void queryPtObject(Pagination page,Map tb);
	public int updatePointCheckByCBasedataId(TTaskPointcheckPojo ttaskPointCheck);
	public List getObjectsToExl(Map param);
	public int deletePointCheckByCBaseDataId(Map cBasedataId);
	public Pagination getAllTaskPointcheckInfo(Map params, Pagination page);
	public List<TTaskPointcheckPojo> getAllTaskPointcheckInfoWithoutPage(Map params);
	public List getAllOrgIds(Map map);
	public List getIntegrateById(List<String> ids);
	public List getTaskPointcheckInfoById(List<String> ids);
	public List getOrgList();
}
