package com.talkweb.xwzcxt.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.twdpe.core.log.Logger;
import com.talkweb.twdpe.core.log.LoggerFactory;
import com.talkweb.twdpe.core.util.json.JsonObject;
import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.xwzcxt.pojo.ManageGroupMsgCommentPojo;
import com.talkweb.xwzcxt.pojo.ManageGroupPojo;
import com.talkweb.xwzcxt.pojo.MyLogPojo;
import com.talkweb.xwzcxt.pojo.ManageGroupMessagePojo;
import com.talkweb.xwzcxt.service.ManageGroupService;
import com.talkweb.xwzcxt.service.MyLogService;

import net.sf.json.JSONObject;

public class ManageGroupAction extends baseAction {
	private ManageGroupPojo manageGroupPojo;
	private ManageGroupMessagePojo manageGroupMessagePojo;
	private static final Logger logger = LoggerFactory.getLogger(PositionBindOrgAction.class);
	
	public ManageGroupMessagePojo getManageGroupMessagePojo() {
		return manageGroupMessagePojo;
	}
	public void setManageGroupMessagePojo(
			ManageGroupMessagePojo manageGroupMessagePojo) {
		this.manageGroupMessagePojo = manageGroupMessagePojo;
	}
	public ManageGroupPojo getManageGroupPojo() {
		return manageGroupPojo;
	}
	public void setManageGroupPojo(ManageGroupPojo manageGroupPojo) {
		this.manageGroupPojo = manageGroupPojo;
	}
	@Autowired
	private ManageGroupService manageGroupService;
	@Autowired
	private MyLogService logService;
	
	private HttpServletRequest request = ServletActionContext.getRequest();
	private HttpServletResponse response = ServletActionContext.getResponse();
	
	//获得当前用户
			Long userid=getUserId();
	//查询聊天记录
	public String searchGroupChat(){
		try{
		if (manageGroupPojo == null){
			manageGroupPojo = new ManageGroupPojo();
		}
		int powerClass=checkUserPower();
		if(powerClass==1||powerClass==2){
			if(powerClass==2){
				String orgId=getOrgId(userid.toString());
				manageGroupPojo.setOrgId(orgId);
			}
			manageGroupPojo.setPowerClass(powerClass);
			//得到聊天记录list
			List<ManageGroupPojo> groupChatList = manageGroupService.getGroupChat(manageGroupPojo,pagination);
		
			this.formatDatagirdData(groupChatList, pagination);
		}else{
			
		}
		
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		
		return SUCCESS;
	}
	//删除聊天记录(逻辑删除，只是将isdelete字段shezhi)
	public String deleteGroupChat(){
		Map<String, String> pageData = new HashMap<String, String>();
		try{
			if (manageGroupPojo == null){
				manageGroupPojo = new ManageGroupPojo();
			}
		String c_id = request.getParameter("c_id");
		ManageGroupPojo delmanageGroupPojo = manageGroupService.getManGroupChatById(c_id);
		boolean b=manageGroupService.deleteGroupChat(c_id);
		
		//记录日志
		JSONObject deljsonObj = JSONObject.fromObject(delmanageGroupPojo);
		String deljsonStr = deljsonObj.toString();
		logService.addLogInfo(request, response, "1", "过程行为支持系统", "2", "基础管理", "工作圈管理", MyLogPojo.del, "工作圈管理-删除聊天记录", "成功", "1", "T_CIRCLE_GROUPCHAT", deljsonStr, "");
		if(b){
			pageData.put("msg", "成功删除");
			pageData.put("status", "ok");
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		this.formatData(pageData);
		
		return SUCCESS;
	}
	//查询动态
	public String searchMessage(){
		try{
		if (manageGroupMessagePojo == null){
			manageGroupMessagePojo=new ManageGroupMessagePojo();
		}
	
		int powerClass=checkUserPower();
		if(powerClass==1||powerClass==2){
			if(powerClass==2){
			manageGroupMessagePojo.setOrgId(getOrgId(userid.toString()));
			}
			manageGroupMessagePojo.setPowerClass(powerClass);
			//得到动态list
			List<ManageGroupMessagePojo> groupMessageList = manageGroupService.getGroupMessage(manageGroupMessagePojo,pagination);
			this.formatDatagirdData(groupMessageList, pagination);
		}else{
			
		}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
	
		return SUCCESS;
	}
	//删除动态(逻辑删除，只是将isdelete字段设置为1)
		public String deleteGroupMessage(){
			Map<String, String> pageData = new HashMap<String, String>();
			try{
				if (manageGroupMessagePojo == null){
					manageGroupMessagePojo = new  ManageGroupMessagePojo();
				}
			HttpServletRequest request = ServletActionContext.getRequest();
			String c_msg_id = request.getParameter("c_msg_id");
			ManageGroupMessagePojo delObj = manageGroupService.getManGroupMagById(c_msg_id);
			boolean b=manageGroupService.deleteGroupMessage(c_msg_id);
			if(b){
				pageData.put("msg", "成功删除");
				pageData.put("status", "ok");
			}
			
			//记录日志
			JSONObject deljsonObj = JSONObject.fromObject(delObj);
			String deljsonStr = deljsonObj.toString();
			logService.addLogInfo(request, response, "1", "过程行为支持系统", "2", "基础管理", "工作圈管理", MyLogPojo.del, "工作圈管理-删除动态", "成功", "1", "T_CIRCLE_MESSAGE", deljsonStr, "");
			
			}catch(Exception e){
				e.printStackTrace();
				logger.error(e.getMessage(), e);
			}
			this.formatData(pageData);
			
			return SUCCESS;
		}
	//查询评论
	public String getCommentByMessageId(){
		try{
		String c_msg_id=request.getParameter("c_msg_id");
		List<ManageGroupMsgCommentPojo> commentList=manageGroupService.getCommentByMessageId(c_msg_id);
		this.formatData(commentList);
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		return SUCCESS;
	} 
	//删除评论
	public String deleteGroupMessageComment(){
		try{
		String c_id=request.getParameter("c_id");
		String c_msg_id=request.getParameter("c_msg_id");
		Map map=new HashMap();
		map.put("c_id", c_id);
		map.put("c_msg_id",c_msg_id);
		ManageGroupMsgCommentPojo delObj = manageGroupService.getMsgCommentById(c_id);
		boolean b=manageGroupService.deleteGroupMessageComment(map);
		Map<String, String> pageData = new HashMap<String, String>();
		if(b){
			pageData.put("msg", "成功删除");
			pageData.put("status", "ok");
		}
		
		//记录日志
		JSONObject deljsonObj = JSONObject.fromObject(delObj);
		String deljsonStr = deljsonObj.toString();
		logService.addLogInfo(request, response, "1", "过程行为支持系统", "2", "基础管理", "工作圈管理", MyLogPojo.del, "工作圈管理-删除动态评论", "成功", "1", "T_CIRCLE_MSG_COMMENT", deljsonStr, "");
		
		this.formatData(pageData);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		return SUCCESS;
	}
	
	//得到当前用户id
	public  Long  getUserId(){
		try{
			HttpSession session=request.getSession();
			 userid=(Long)((Map<String, Object>) session.getAttribute("USERSESSION")).get("id");
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
			return userid;
		}
	//得到当前用户的部门id
	public String getOrgId(String userId){
		String path=manageGroupService.getOrgId(userId);
		String pathStr[]=path.split(";");
		return pathStr[1];
		
	}

	/**
	 * 	//得到用户权限等级
	 * @param userid 当前用户的id
	 * @return 权限等级("0":没有权限;"1":一级权限;"2":"二级权限")
	 * 			
	 */
	public  int checkUserPower(){
		int powerClass=0;
		Map map = new HashMap();
		map.put("userid", userid);
		map.put("roleId", "1000219");
		powerClass = manageGroupService.checkUserPower(map);
		if(powerClass==0){
			map.clear();
			map.put("userid", userid);
			map.put("roleId", "1000220");
			powerClass = manageGroupService.checkUserPower(map);
			if(powerClass!=0){
				return 2;
			}else{
				return 0;
			}
			
		}else{
			return 1;
		}

	}
}
