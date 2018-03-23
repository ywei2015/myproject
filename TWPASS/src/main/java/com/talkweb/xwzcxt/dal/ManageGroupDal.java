package com.talkweb.xwzcxt.dal;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.talkweb.twdpe.dal.batis.spring.SessionDaoSupport;
import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.xwzcxt.pojo.ManageGroupMessagePojo;
import com.talkweb.xwzcxt.pojo.ManageGroupMsgCommentPojo;
import com.talkweb.xwzcxt.pojo.ManageGroupPojo;

public class ManageGroupDal extends SessionDaoSupport {
	
	
	public List<ManageGroupPojo> getGroupChat(ManageGroupPojo manageGroupPojo,Pagination page){
		List list=this.getSession().selectList("ManageGroup.getGroupChat",manageGroupPojo, page);
		return list;
	}
	public void deleteGroupChat(String  c_id){
		Map map=new HashMap();
		map.put("c_id",c_id);
		
		this.getSession().delete("ManageGroup.deleteGroupChat",map);
	}
	/**
	 * 
	 * @param userid 用户id
	 * @return 用户权限
	 * 
	 * 一个用户可能有多个角色，所以返回的值为角色List，只要List中的rolename有一个为工作圈一级权限即可
	 */
	public int checkUserPower(Map map){
	
	String s = (String) this.getSession().selectOne("ManageGroup.getPowerClass",map);
		return Integer.parseInt(s==null||s.equals("")?"0":s) ;
	}
	public List getGroupMessage(ManageGroupMessagePojo manageGroupMessagePojo,Pagination page) {
		List list=this.getSession().selectList("ManageGroup.getGroupMessage",manageGroupMessagePojo, page);
		return list;
	}
	public void deleteGroupMessage(String c_msg_id) {
		Map map=new HashMap();
		Date date=new Date();
		
		map.put("c_msg_id",c_msg_id);
		map.put("c_deletetime",date );
		this.getSession().delete("ManageGroup.deleteGroupMessage",map);
	}
	public List<ManageGroupMsgCommentPojo> getCommentByMessageId(String c_msg_id) {
		Map map=new HashMap();
		map.put("c_msg_id", c_msg_id);
		return this.getSession().selectList("ManageGroup.getCommentByMessageId",map);
		
	}

	public boolean deleteMsgComment(String c_id) {
		Map map=new HashMap();
		map.put("c_id",c_id);
		this.getSession().delete("ManageGroup.deleteMsgComment", c_id);
		return true;
	}
	public boolean updateMsg(String c_msg_id) {
		Map map=new HashMap();
		map.put("c_msg_id",c_msg_id);
		Date date=new Date();
		map.put("date",date);
		try{
		this.getSession().update("ManageGroup.updateMsg", map);
		}catch(Exception e){
			e.printStackTrace();
		}
		return true;
	}
	public String getOrgId(String userId) {
		Map map=new HashMap();
		map.put("userId",userId);
		return (String)this.getSession().selectOne("ManageGroup.getOrgId",userId);
	}
	public ManageGroupPojo getManGroupChatById(String id) {
		return (ManageGroupPojo) getSession().selectOne("ManageGroup.getManGroupChatById",id);
	}
	public ManageGroupMessagePojo getManGroupMagById(String id) {
		return (ManageGroupMessagePojo) getSession().selectOne("ManageGroup.getManGroupMagById",id);
	}
	
	public ManageGroupMsgCommentPojo getMsgCommentById(String id) {
		return (ManageGroupMsgCommentPojo) getSession().selectOne("ManageGroup.getMsgCommentById",id);
	}
	

}
