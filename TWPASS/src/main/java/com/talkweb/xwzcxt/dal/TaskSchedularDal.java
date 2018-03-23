package com.talkweb.xwzcxt.dal;

import java.util.List;
import java.util.Map;

import com.talkweb.twdpe.dal.batis.spring.SessionDaoSupport;
import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.xwzcxt.pojo.TSchedulingPojo;
import com.talkweb.xwzcxt.vo.PositionTreeVo;
import com.talkweb.xwzcxt.vo.TaskSchedularVo;

public class TaskSchedularDal extends SessionDaoSupport{
	public List getPersonSchedular(Map params){
		return this.getSession().selectList("taskSchedular.personSchedular",params);
	}
	
	public List getUserList(Map params){
		Pagination pagination=(Pagination)params.get("pagination");
		List list;
		if(pagination!=null){
			list=getSession().selectList("taskSchedular.userList", params,pagination);
			pagination.setList(list);
		}else{
			list=getSession().selectList("taskSchedular.userList", params);
		}
		return list;
	}
	
	public List getOrganization( ){
		List list=this.getSession().selectList("taskSchedular.getOrganization");
		return list;
	}
	
	public TSchedulingPojo getShiftUserInfo(Map map){
		return (TSchedulingPojo)getSession().selectOne("taskSchedular.getShiftUserInfo",map);
	}
	
	public List getShiftName(){
		return (List)getSession().selectList("taskSchedular.getShiftName");
	}
	
	public int addShift(TSchedulingPojo param){
		return getSession().insert("taskSchedular.addShift",param);
	}
	
	public int updateUserShift(Map params){
		return getSession().update("taskSchedular.updateUserShift",params);
	}
	
	public Map getOneDayShift(Map params){
		return (Map)getSession().selectOne("taskSchedular.getOneDayShift",params);
	}
	
	public TSchedulingPojo getShiftByUserDate(Map params){
		return (TSchedulingPojo)getSession().selectOne("taskSchedular.getShiftByUserDate",params);
	}
	
	public int deleteOneDayShift(Map params){
		return  getSession().update("taskSchedular.deleteOneDayShift",params);
	}
	
	public List  getAllPostionTreeNodes( ){
		return  getSession().selectList("taskSchedular.getAllPositionTreeNodes");
	}
	
	public List getDymanicPositionTreeNodes(PositionTreeVo node){
		return  getSession().selectList("taskSchedular.getDymanicPositionTreeNodes",node);
	}
	
	public List getDepartmentTree(String type){
		if(type.equals("1")){
		return getSession().selectList("taskSchedular.getDepartmentTree");//班组树
		}
		return getSession().selectList("taskSchedular.getDepartmentTree2");//部门树
	}
}
