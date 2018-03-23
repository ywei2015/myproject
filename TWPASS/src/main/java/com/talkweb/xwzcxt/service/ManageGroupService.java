package com.talkweb.xwzcxt.service;

import java.util.List;
import java.util.Map;

import com.talkweb.twdpe.base.org.pojo.User;
import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.xwzcxt.pojo.ManageGroupMessagePojo;
import com.talkweb.xwzcxt.pojo.ManageGroupMsgCommentPojo;
import com.talkweb.xwzcxt.pojo.ManageGroupPojo;

public interface ManageGroupService {
	public List getGroupChat(ManageGroupPojo manageGroupPojo,Pagination page);
	public boolean deleteGroupChat(String c_id);
	public int checkUserPower(Map map);
	public List getGroupMessage(ManageGroupMessagePojo manageGroupMessagePojo,Pagination page);
	public boolean deleteGroupMessage(String c_id);
	public List<ManageGroupMsgCommentPojo> getCommentByMessageId(String c_msg_id);
	public boolean deleteGroupMessageComment(Map map);
	public String getOrgId(String userId);
	public ManageGroupPojo getManGroupChatById(String id);
	public ManageGroupMessagePojo getManGroupMagById(String id);
	public ManageGroupMsgCommentPojo getMsgCommentById(String id);
}
