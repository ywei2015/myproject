package com.talkweb.xwzcxt.dal;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.talkweb.twdpe.dal.batis.spring.SessionDaoSupport;
import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.xwzcxt.pojo.TTaskPointcheckPojo;
import com.talkweb.xwzcxt.pojo.TbIntegrateInfo;

public class JobObjectsDal extends SessionDaoSupport{

	public List getTableTypeTree(){
		return getSession().selectList("jobObjects.getTableTypeTree");
	}
	
	public void getObjectInfo(Pagination page,Map tb){
		page.setList(getSession().selectList("jobObjects.getObjectInfo", tb, page)) ;
	}
	
	public List getColumnsConfig(Map cTabletypeId){
		return (List)getSession().selectList("jobObjects.getColumnsConfig", cTabletypeId);
		
	}
	
	public TbIntegrateInfo getCurrentPrintCount(Map cBasedataId){
		return (TbIntegrateInfo)getSession().selectOne("jobObjects.getCurrentPrintCount", cBasedataId);
	}
	
	public int deleteObjects(Map cBasedataId){
		
		return getSession().update("jobObjects.deleteObjects",cBasedataId);
	}
	
	public int updateObject(TbIntegrateInfo tb ){
		return getSession().update("jobObjects.updateObject",tb);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int addObject(TbIntegrateInfo tb){	
		if(tb.getcBasedataId()==null)
		{
			Map map=new HashMap();
			map.put("cTabletypeId", tb.getcTabletypeId());
			String cBasedataId=(String)getSession().selectOne("jobObjects.getMaxBasedataId",map);
			tb.setcBasedataId(new BigDecimal(cBasedataId));
		}
		return getSession().insert("jobObjects.addObject",tb);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getMaxBasedataId(TbIntegrateInfo tb){
		Map map=new HashMap();
		map.put("cTabletypeId", tb.getcTabletypeId());
		return (String)getSession().selectOne("jobObjects.getMaxBasedataId",map);
	}
	
	public int printCode(Map param) {
		return getSession().update("jobObjects.printCode",param);
	}
	
	public String getOrgname(Map cOrgid){
		return (String)getSession().selectOne("jobObjects.getOrgname", cOrgid);
	}
	
	public TbIntegrateInfo getTabletypeById(Map cTabletypeId ){
		return (TbIntegrateInfo)getSession().selectOne("jobObjects.getTabletypeById",cTabletypeId);
	}
	
	public List getDynamicsByType(Map cTabletypeId ){
		return (List)getSession().selectList("jobObjects.getDynamicsByType",cTabletypeId);
	}
	
	public  int updateTabletype (TbIntegrateInfo tbIntegrateInfo ){
		return (Integer)getSession().update("jobObjects.updateTabletype",tbIntegrateInfo);
	}
	
	public int addtTabletype (TbIntegrateInfo tbIntegrateInfo){
		return getSession().insert("jobObjects.addTabletype",tbIntegrateInfo);
	}
	
	public int addDynamicCol( TbIntegrateInfo tbIntegrateInfo){
		return getSession().insert("jobObjects.addDynamicCol",tbIntegrateInfo);
	}
	
	public int deleteTabletype(Map cTabletypeId){
		return getSession().update("jobObjects.deleteTabletype",cTabletypeId);
	}
	
	public int deleteTabletypeCol(Map cTabletypeId){
		return getSession().update("jobObjects.deleteTabletypeCol",cTabletypeId);
	}
	
	public int getCountColum(Map cTabletypeId){
		return (Integer)getSession().selectOne("jobObjects.getCountColum",cTabletypeId);
	}
	
	
	public int updateDynamicCol(Map map){
		return getSession().update("jobObjects.updateDynamicCol",map);
	}
	
	public int getDynamicCount(Map cTabletypeId){
		return (Integer)getSession().selectOne("jobObjects.getDynamicCount", cTabletypeId);
	}
	
	public int updateColCount(Map map){
		return getSession().update("jobObjects.updateColCount",map);
	}
	
	public int deleteDynamicById(Map cDycolId){
		return getSession().update("jobObjects.deleteDynamicById",cDycolId);
	}
	
	public int checkHasIntegrateData(Map cTabletypeId){
		return (Integer)getSession().selectOne("jobObjects.checkHasIntegrateData",cTabletypeId);
	}
	
	public int verifyRepeatObject(TbIntegrateInfo tbIntegrateInfo){
		return (Integer)getSession().selectOne("jobObjects.verifyRepeatObject",tbIntegrateInfo);
	}

	public List selectJobObjectTree(Map cBasedataUserCode) {
		return getSession().selectList("jobObjects.selectJobObjectTree",cBasedataUserCode);
	}
	
	public List getTableTypeInfo(Map cTabletypeId){
		return getSession().selectList("jobObjects.getTableTypeInfo",cTabletypeId);
	}

	public int addTaskPointCheck(TTaskPointcheckPojo ttaskPointCheck) {
		return getSession().insert("tTaskPointcheck.addPointCheck", ttaskPointCheck);
	}
	
	public void queryPtObject(Pagination page,Map tb){
		page.setList(getSession().selectList("jobObjects.queryPtObject", tb, page)) ;
	}

	public int updatePointCheckByCBasedataId(TTaskPointcheckPojo ttaskPointCheck) {
		return getSession().update("tTaskPointcheck.updatePointCheckByCBasedataId", ttaskPointCheck);
	}

	public List getObjectToExl(Map param) {
		return getSession().selectList("jobObjects.getObjectInfo",param);
	}

	public int deletePointCheckByCBaseDataId(Map cBasedataId) {
		return getSession().delete("tTaskPointcheck.deletePointCheckByCBaseDataId", cBasedataId);
	}
	
	public Pagination getAllTaskPointcheckInfo(Map params, Pagination page) {
		List li = this.getSession().selectList("tTaskPointcheck.getAllTaskPointcheckInfo", params, page);
		if (li != null)
			page.setList(li);
		return page;
	}
	
	public List<TTaskPointcheckPojo> getAllTaskPointcheckInfoWithoutPage(Map params) {
		return this.getSession().selectList("tTaskPointcheck.getAllTaskPointcheckInfo", params);
	}

	public List getOrgAllIds(Map orgId) {
		return getSession().selectList("jobObjects.getOrgIds",orgId);
	}
	
	public List getIntegrateById(List<String> ids){
		return getSession().selectList("jobObjects.getIntegrateById",ids);
	}
	
	public List getTaskPointcheckInfoById(List<String> ids){
		return getSession().selectList("tTaskPointcheck.getTaskPointcheckInfoById",ids);
	}
	public List getOrgList(){
		return getSession().selectList("jobObjects.getOrgList");
	}
}
