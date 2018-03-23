package com.talkweb.xwzcxt.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.xwzcxt.dal.StatisticalDal;
import com.talkweb.xwzcxt.pojo.StaticalPojo;

public class StatisticalImpl {
	
	@Autowired
	private StatisticalDal statisticalDal;

	//个人未生成
		public int personUncreated(Map map){		
			return  statisticalDal.personUncreated(map);
		}
		//个人未完成
		public int personUnfinished(Map map){		
			return statisticalDal.personUnfinished(map);
		}
		
		//个人已完成
		public int personfinished(Map map){		
			return statisticalDal.personfinished(map);
		}
		
		//班组未生成
		public int teamUncreated(Map map,String dataId){		
			return statisticalDal.teamUncreated(map,dataId);
		}
		
		
		//班组未完成
		public int teamUnfinished(Map map,String dataId){		
			return statisticalDal.teamUnfinished(map,dataId);
		}
		
		//班组已完成
		public int teamfinished(Map map,String dataId){		
			return statisticalDal.teamfinished(map,dataId);
		}
		
		//部门未生成
		public int departUncreated(Map map,String dataId){		
			return statisticalDal.departUncreated(map,dataId);
		}
		
		//部门未完成
		public int departUnfinished(Map map,String dataId){		
			return statisticalDal.departUnfinished(map,dataId);
		}
		
		//部门已完成
		public int departfinished(Map map,String dataId){		
			return statisticalDal.departfinished(map,dataId);
		}
       
		//个人未生成列表
		public List<StaticalPojo > queryPersonUncreated(Map map) {
			return statisticalDal.queryPersonUncreated(map);
		}
		
		//个人未完成和完成列表
		public List<StaticalPojo > queryPersonTask(Map map) {
			return statisticalDal.queryPersonTask(map);
		}
		
		public List<StaticalPojo > queryteamUncreated(Map map) {
			return statisticalDal.queryteamUncreated(map);
		}
		
		public List<StaticalPojo > queryteamTask(Map map) {
			return statisticalDal.queryteamTask(map);
		}
		
		public List<StaticalPojo > querydepartUncreated(Map map) {
			return statisticalDal.querydepartUncreated(map);
		}
		
		public List<StaticalPojo > querydepartTask(Map map) {
			return statisticalDal.querydepartTask(map);
		}
	
		public List queryErrCount(Map map) {
			return statisticalDal.queryErrCount(map);
		}
		
		public List queryteamErrCount(Map map,String dataId) {
			return statisticalDal.queryteamErrCount(map,dataId);
		}
		
		public List querydepartErrCount(Map map,String dataId) {
			return statisticalDal.querydepartErrCount(map,dataId);
		}
		
		public int getpersonErrTotalCount(Map map) {
			return statisticalDal.getpersonErrTotalCount(map);
		}
		
		
		public int getteamErrTotalCount(Map map,String dataId) {
			return statisticalDal.getteamErrTotalCount(map,dataId);
		}
		
		public int getdepartErrTotalCount(Map map,String dataId) {
			return statisticalDal.getdepartErrTotalCount(map,dataId);
			
		}
		
		public int getpersonTaskCount(Map map) {
			return  statisticalDal.getpersonTaskCount(map);
		}
		
		public int getteamTaskCount(Map map) {
			return  statisticalDal.getteamTaskCount(map);
		}
		
		public int getdepartTaskCount(Map map) {
			return statisticalDal.getdepartTaskCount(map);
		}
		
		public List<StaticalPojo> queryPersonErrList(Map map) {
			return statisticalDal.queryPersonErrList(map);
		}
		
		public List<StaticalPojo> queryPersonErrList2(Map map) {
			return statisticalDal.queryPersonErrList2(map);
		}
		
		public List<StaticalPojo> queryTeamErrList(Map map) {
			return statisticalDal.queryTeamErrList(map);
		}
		
		public List<StaticalPojo> queryTeamErrList2(Map map) {
			return statisticalDal.queryTeamErrList2(map);
		}
		
		public List<StaticalPojo> queryDepartErrList(Map map) {
			return statisticalDal.queryDepartErrList(map);
		}
		
		public List<StaticalPojo> queryDepartErrList2(Map map) {
			return statisticalDal.queryDepartErrList2(map);
		}
		
		public int checkTempStatus(Map map) {
			return statisticalDal.checkTempStatus(map);
		}
		
		public int checkEnergyStatus(Map map) {
			return statisticalDal.checkEnergyStatus(map);
		}
		
		public List<StaticalPojo> getAreaIdName(String area) {
			return statisticalDal.getAreaIdName(area);
		}
}
