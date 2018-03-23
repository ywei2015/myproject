package com.talkweb.xwzcxt.dal;

import java.util.List;

import com.talkweb.twdpe.dal.batis.spring.SessionDaoSupport;
import com.talkweb.xwzcxt.pojo.SdActionPojo;
import com.talkweb.xwzcxt.pojo.SdActionPositionPojo;

public class SdActionDal extends SessionDaoSupport {

	public int addSdActionToDB(SdActionPojo data){
		return this.getSession().insert("stdaction.addSdAction", data);
	}
	
	public List<?> getAllAction(){
		return this.getSession().selectList("stdaction.getAllAction");
	}
	
	public Object getActionInfoById(String id){
		return this.getSession().selectOne("stdaction.getActionById", id);
	}
	
	public int modifyActionDataById(SdActionPojo data){
		return this.getSession().update("stdaction.modifyActionDataById", data);
	}
	
	public int deleteActionById(String[] ids){
		for (int i=0; i<ids.length; i++){
			String id = ids[i];
			this.deleteActionPosotionById(id);
			this.getSession().update("stdaction.deleteActionById", id);
		}
		return 1;
	}
	
	public List<?> getActionPositionInfoById(String id){
		return this.getSession().selectList("stdaction.getActionPositionById", id);
	}
	
	public int addSdActionPositionsToDB(List<SdActionPositionPojo> positions){
		int ret = 0;
		for (int i=0; i<positions.size(); i++){
			ret = this.getSession().insert("stdaction.addSdActionPosition", positions.get(i));
		}
		return ret;
	}
	
	public int deleteActionPosotionById(String id){
		return this.getSession().delete("stdaction.deleteActionPosotionById", id);
	}
	
	public String getOrgIdByName(String name){
		Object o = this.getSession().selectOne("stdaction.getOrgIdByName", name);
		if (o == null)  return "";
		return o.toString();
	}
}
