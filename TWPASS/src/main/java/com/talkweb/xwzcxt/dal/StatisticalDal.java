package com.talkweb.xwzcxt.dal;

import java.util.List;
import java.util.Map;

import com.talkweb.twdpe.dal.batis.spring.SessionDaoSupport;
import com.talkweb.xwzcxt.pojo.StaticalPojo;
import com.talkweb.xwzcxt.pojo.TTaskPojo;

public class StatisticalDal extends SessionDaoSupport{
	
	//个人未生成
	public int personUncreated(Map map){		
		return (Integer) this.getSession().selectOne("statistical.personUncreated",map);
	}
	
	
	//个人未完成
	public int personUnfinished(Map map){		
		return (Integer) this.getSession().selectOne("statistical.personUnfinished",map);
	}
	
	//个人已完成
	public int personfinished(Map map){		
		return (Integer) this.getSession().selectOne("statistical.personfinished",map);
	}
	
	//班组未生成
	public int teamUncreated(Map map,String dataId){
		if(dataId!=null&&!dataId.equals("null")){
			dataId = dataId.substring(dataId.indexOf("-")+1, dataId.length());
			map.put("dataId", dataId);
			return (Integer) this.getSession().selectOne("statistical.teamUncreatedforId",map);
		}else{
		return (Integer) this.getSession().selectOne("statistical.teamUncreated",map);
		}
	}
	
	//班组未完成
	public int teamUnfinished(Map map,String dataId){
		if(dataId!=null&&!dataId.equals("null")){
			dataId = dataId.substring(dataId.indexOf("-")+1, dataId.length());
			map.put("dataId", dataId);
			return (Integer) this.getSession().selectOne("statistical.teamFinishedForteamId",map);
		}else{
		return (Integer) this.getSession().selectOne("statistical.teamUnfinished",map);
		}
	}
	
	//班组已完成
	public int teamfinished(Map map,String dataId){
		if(dataId!=null&&!dataId.equals("null")){
			dataId = dataId.substring(dataId.indexOf("-")+1, dataId.length());
			map.put("dataId", dataId);
			return (Integer) this.getSession().selectOne("statistical.teamFinishedForteamId",map);
		}else{
		return (Integer) this.getSession().selectOne("statistical.teamfinished",map);
		}
	}
	
	//部门未生成
	public int departUncreated(Map map,String dataId){
		if(dataId!=null&&!dataId.equals("null")){
			dataId = dataId.substring(dataId.indexOf("-")+1, dataId.length());
			map.put("dataId", dataId);
			return (Integer) this.getSession().selectOne("statistical.departUncreatedForDataId",map);
		}else{
		return (Integer) this.getSession().selectOne("statistical.departUncreated",map);
		}
	}
	
	//部门未完成
	public int departUnfinished(Map map,String dataId){	
		if(dataId!=null&&!dataId.equals("null")){
			dataId = dataId.substring(dataId.indexOf("-")+1, dataId.length());
			map.put("dataId", dataId);
			return (Integer) this.getSession().selectOne("statistical.departUnfinishedForDataId",map);
		}else{
		return (Integer) this.getSession().selectOne("statistical.departUnfinished",map);
		}
	}
	
	//部门已完成
	public int departfinished(Map map,String dataId){		
		if(dataId!=null&&!dataId.equals("null")){
			dataId = dataId.substring(dataId.indexOf("-")+1, dataId.length());
			map.put("dataId", dataId);
			return (Integer) this.getSession().selectOne("statistical.departfinishedForDataId",map);
		}else{
		return (Integer) this.getSession().selectOne("statistical.departfinished",map);
		}
	}
	
    //查询未完成和已完成任务列表
	@SuppressWarnings("unchecked")
	public List<StaticalPojo > queryPersonTask(Map map) {
		return (List<StaticalPojo >) this.getSession().selectList("statistical.queryPersonTask",map);
	}
	//查询未生成任务列表
	@SuppressWarnings("unchecked")
	public List<StaticalPojo > queryPersonUncreated(Map map) {
		return (List<StaticalPojo >) this.getSession().selectList("statistical.queryPersonUncreated",map);
	}
	
	@SuppressWarnings("unchecked")
	public List<StaticalPojo > queryteamUncreated(Map map) {
		return (List<StaticalPojo >) this.getSession().selectList("statistical.queryteamUncreated",map);
	}
	
	@SuppressWarnings("unchecked")
	public List<StaticalPojo > queryteamTask(Map map) {
		return (List<StaticalPojo >) this.getSession().selectList("statistical.queryteamTask",map);
	}
	
	@SuppressWarnings("unchecked")
	public List<StaticalPojo > querydepartUncreated(Map map) {
		return (List<StaticalPojo >) this.getSession().selectList("statistical.querydepartUncreated",map);
	}
	
	@SuppressWarnings("unchecked")
	public List<StaticalPojo > querydepartTask(Map map) {
		return (List<StaticalPojo >) this.getSession().selectList("statistical.querydepartTask",map);
	}
	
	@SuppressWarnings("unchecked")
	public List queryErrCount(Map map) {
		return (List) this.getSession().selectList("statistical.queryErrCount",map);
	}
	
	@SuppressWarnings("unchecked")
	public List queryteamErrCount(Map map,String dataId) {
		if(dataId!=null&&!dataId.equals("null")){
//			map.remove("userId");
			dataId = dataId.substring(dataId.indexOf("-")+1, dataId.length());
			map.put("dataId", dataId);
			return (List) this.getSession().selectList("statistical.queryteamErrCountForDataId",map);
		}else{
		return (List) this.getSession().selectList("statistical.queryteamErrCount",map);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List querydepartErrCount(Map map,String dataId) {
		if(dataId!=null&&!dataId.equals("null")){
			dataId = dataId.substring(dataId.indexOf("-")+1, dataId.length());
			map.put("dataId", dataId);
			return (List) this.getSession().selectList("statistical.querydepartErrCountForDataId",map);
		}else{
		return (List) this.getSession().selectList("statistical.querydepartErrCount",map);
		}
	}
	
	@SuppressWarnings("unchecked")
	public int getpersonErrTotalCount(Map map) {
		return (Integer) this.getSession().selectOne("statistical.getpersonErrTotalCount",map);
	}
	
	@SuppressWarnings("unchecked")
	public int getteamErrTotalCount(Map map,String dataId) {
		if(dataId!=null&&!dataId.equals("null")){
			dataId = dataId.substring(dataId.indexOf("-")+1, dataId.length());
			map.put("dataId", dataId);
			return (Integer) this.getSession().selectOne("statistical.getteamErrTotalCountForDataId",map);
		}else{
		return (Integer) this.getSession().selectOne("statistical.getteamErrTotalCount",map);
		}
	}
	
	@SuppressWarnings("unchecked")
	public int getdepartErrTotalCount(Map map,String dataId) {
		if(dataId!=null&&!dataId.equals("null")){
			dataId = dataId.substring(dataId.indexOf("-")+1, dataId.length());
			map.put("dataId", dataId);
			return (Integer) this.getSession().selectOne("statistical.getdepartErrTotalCountForDataId",map);
		}else{
		return (Integer) this.getSession().selectOne("statistical.getdepartErrTotalCount",map);
		}
	}
	
	@SuppressWarnings("unchecked")
	public int getpersonTaskCount(Map map) {  //个人任务总数
		return (Integer) this.getSession().selectOne("statistical.getpersonTaskCount",map);
	}
	
	@SuppressWarnings("unchecked")
	public int getteamTaskCount(Map map) {
		return (Integer) this.getSession().selectOne("statistical.getteamTaskCount",map);
	}
	
	@SuppressWarnings("unchecked")
	public int getdepartTaskCount(Map map) {
		return (Integer) this.getSession().selectOne("statistical.getdepartTaskCount",map);
	}
	
	@SuppressWarnings("unchecked")
	public List<StaticalPojo> queryPersonErrList(Map map) {
		return (List<StaticalPojo>) this.getSession().selectList("statistical.queryPersonErrList",map);
	}
	
	@SuppressWarnings("unchecked")
	public List<StaticalPojo> queryPersonErrList2(Map map) {
		return (List<StaticalPojo>) this.getSession().selectList("statistical.queryPersonErrList2",map);
	}
	
	@SuppressWarnings("unchecked")
	public List<StaticalPojo> queryTeamErrList(Map map) {
		return (List<StaticalPojo>) this.getSession().selectList("statistical.queryTeamErrList",map);
	}
	
	@SuppressWarnings("unchecked")
	public List<StaticalPojo> queryTeamErrList2(Map map) {
		return (List<StaticalPojo>) this.getSession().selectList("statistical.queryTeamErrList2",map);
	}
	
	@SuppressWarnings("unchecked")
	public List<StaticalPojo> queryDepartErrList(Map map) {
		return (List<StaticalPojo>) this.getSession().selectList("statistical.queryDepartErrList",map);
	}
	
	@SuppressWarnings("unchecked")
	public List<StaticalPojo> queryDepartErrList2(Map map) {
		return (List<StaticalPojo>) this.getSession().selectList("statistical.queryDepartErrList2",map);
	}

	public int checkTempStatus(Map map) {
		return (Integer) this.getSession().selectOne("statistical.checkTempStatus",map);
	}
	
	public int checkEnergyStatus(Map map) {
		return (Integer) this.getSession().selectOne("statistical.checkEnergyStatus",map);
	}
	
	@SuppressWarnings("unchecked")
	public List<StaticalPojo> getAreaIdName(String area) {
		return (List<StaticalPojo>) this.getSession().selectList("statistical.getAreaIdName",area);
	}
	
}
