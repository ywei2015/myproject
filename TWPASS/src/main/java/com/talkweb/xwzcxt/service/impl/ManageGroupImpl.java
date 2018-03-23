package com.talkweb.xwzcxt.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.talkweb.twdpe.base.org.pojo.User;
import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.xwzcxt.dal.ManageGroupDal;
import com.talkweb.xwzcxt.pojo.ManageGroupMessagePojo;
import com.talkweb.xwzcxt.pojo.ManageGroupMsgCommentPojo;
import com.talkweb.xwzcxt.pojo.ManageGroupPojo;
import com.talkweb.xwzcxt.service.ManageGroupService;

public class ManageGroupImpl implements ManageGroupService {
	@Autowired
	private ManageGroupDal manageGroupDal;
	
	@Override
	public List getGroupChat(ManageGroupPojo manageGroupPojo,Pagination page) {
	
		return manageGroupDal.getGroupChat(manageGroupPojo,page);
	}

	@Override
	public boolean deleteGroupChat(String id) {
		String c_id[] = id.split(",");
		int i;
		for(i=0;i<c_id.length;i++){
			manageGroupDal.deleteGroupChat(c_id[i]);
		}
		if(i==c_id.length){
			return true;
		}else {
			return false;
		}
	
	}

	@Override
	public int checkUserPower(Map map) {
		
		return manageGroupDal.checkUserPower(map);
	}

	@Override
	public List getGroupMessage(ManageGroupMessagePojo manageGroupMessagePojo,
			Pagination page) {
			return manageGroupDal.getGroupMessage(manageGroupMessagePojo,page);
	}

	@Override
	public boolean deleteGroupMessage(String id) {
		String c_id[] =id.split(",");
		int i;
		for(i=0;i<c_id.length;i++){
			manageGroupDal.deleteGroupMessage(c_id[i]);
		}
		if(i==c_id.length){
			return true;
		}else {
			return false;
		}
	}

	@Override
	public List<ManageGroupMsgCommentPojo> getCommentByMessageId(String c_msg_id) {
		return  manageGroupDal.getCommentByMessageId(c_msg_id);
		
	}
	
	@Override
	
	@Transactional //Spring事务的注释
	public boolean deleteGroupMessageComment(Map map) {
		boolean a=deleteMsgComment((String)map.get("c_id"));
		boolean b=updateMsg((String)map.get("c_msg_id"));
		return a&&b;
	}
	//物理删除评论
	public  boolean deleteMsgComment(String c_id){
		return manageGroupDal.deleteMsgComment( c_id);
	}
	//更新动态的c_comment_time
	public boolean updateMsg(String c_msg_id){
		
		return manageGroupDal.updateMsg( c_msg_id);
	}

	@Override
	public String getOrgId(String userId) {
		return  manageGroupDal.getOrgId(userId);
	}

	@Override
	public ManageGroupPojo getManGroupChatById(String id) {
		return  manageGroupDal.getManGroupChatById(id);
	}

	@Override
	public ManageGroupMessagePojo getManGroupMagById(String id) {
		return  manageGroupDal.getManGroupMagById(id);
	}

	@Override
	public ManageGroupMsgCommentPojo getMsgCommentById(String id) {
		return  manageGroupDal.getMsgCommentById(id);
	}

}
