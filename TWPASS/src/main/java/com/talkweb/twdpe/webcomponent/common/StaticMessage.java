package com.talkweb.twdpe.webcomponent.common;

import java.lang.reflect.Field;

import org.apache.commons.lang.StringUtils;

import com.talkweb.twdpe.core.util.json.JsonObject;

/**
 * 文件名称: StaticMessage.java 内容摘要: 业务操作的结果消息定义类
 * 
 * @author: 丁涛
 * @version: 1.0
 * @Date: 2011-5-12 上午10:42:03
 * 
 *        修改历史: 修改日期 修改人员 版本 修改内容 ----------------------------------------------
 *        2011-4-20 丁涛 1.0 1.0 XXXX
 * 
 *        版权: 版权所有(C)2011 公司: 拓维信息系统股份有限公司
 */
@SuppressWarnings("unused")
public class StaticMessage {

	public StaticMessage() {

	}

	public JsonObject getMessage(String startname, String option) throws IllegalAccessException {
		// 获取类的所有成员变量，包括私有的变量
		Field[] fields = this.getClass().getDeclaredFields();
		JsonObject json = new JsonObject();
		for (Field fs : fields) {
			if (StringUtils.startsWith(fs.getName(), startname + "_" + option)) {
				json.put(fs.getName(), fs.get(this));
			}
		}
		return json;
	}

	// ******************************用户的相关操作的休息定义***********************************

	private String USER_ADD_1 = "新增用户成功";
	private String USER_ADD_0 = "新增用户失败";
	private String USER_MODIFY_1 = "修改用户成功";
	private String USER_MODIFY_0 = "修改用户失败";
	private String USER_CHANGEORG_1 = "调动用户组织成功";
	private String USER_CHANGEORG_0 = "调动用户组织失败";
	private String USER_ADD_2 = "该账号已经存在";
	private String USER_MODIFY_2 = "该账号已经存在";
	private String USER_SORT_1 = "用户排序成功";
	private String USER_SORT_0 = "用户排序失败";
	private String USER_RELROLE_1 = "用户关联角色成功";
	private String USER_RELROLE_0 = "用户关联角色失败";
	private String USER_DELETE_0 = "删除用户失败";
	private String USER_DELETE_1 = "删除用户成功";
	private String USER_DELETE_2 = "删除失败：你选择的用户中有已经关联角色的数据";
	private String USER_PASSWORD_1 = "修改密码成功";
	private String USER_PASSWORD_0 = "修改密码失败";
	private String USER_PASSWORD_2 = "修改密码失败,原密码输入不正确";
	private String USER_DELROLE_1 = "删除关联角色成功";
	private String USER_DELROLE_0 = "删除关联角色失败";

	// ******************************角色的相关操作的休息定义***********************************

	private String ROLE_ADD_1 = "新增角色成功";
	private String ROLE_ADD_0 = "新增角色失败";
	private String ROLE_ADD_2 = "新增角色失败,角色编码有重名";
	private String ROLE_MODIFY_1 = "修改角色成功";
	private String ROLE_MODIFY_0 = "修改角色失败";
	private String ROLE_MODIFY_2 = "修改角色失败,角色编码有重名";
	private String ROLE_MODIFY_3 = "修改角色失败,上级角色不能为当前角色";
	private String ROLE_DELETE_0 = "删除角色失败";
	private String ROLE_DELETE_1 = "删除角色成功";
	private String ROLE_DELETE_2 = "删除角色失败:角色与应用有关联";
	private String ROLE_DELETE_3 = "删除角色失败:角色与功能有关联";
	private String ROLE_DELETE_4 = "删除角色失败:角色与组织有关联";
	private String ROLE_DELETE_5 = "删除角色失败:角色与用户有关联";
	private String ROLE_DELETE_6 = "删除角色失败:角色与岗位有关联";

	// ******************************岗位的相关操作的休息定义***********************************

	private String POSITION_ADD_1 = "新增岗位成功";
	private String POSITION_ADD_2 = "岗位名称已经存在";
	private String POSITION_ADD_0 = "新增岗位失败";
	private String POSITION_MODIFY_1 = "修改岗位成功";
	private String POSITION_MODIFY_2 = "岗位名称已经存在";
	private String POSITION_MODIFY_0 = "修改岗位失败";
	private String POSITION_DELETE_0 = "删除岗位失败";
	private String POSITION_DELETE_1 = "删除岗位成功";
	private String POSITION_DELETE_2 = "删除岗位失败，岗位有角色关联，不可删除";
	private String POSITION_DELETE_3 = "删除岗位失败，岗位有用户关联，不可删除";

	// ******************************应用的相关操作的休息定义***********************************

	private String APP_ADD_1 = "新增应用成功";
	private String APP_ADD_0 = "新增应用失败";
	private String APP_ADD_2 = "新增应用失败，存在相同应用编码纪录";
	private String APP_MODIFY_1 = "修改应用成功";
	private String APP_MODIFY_0 = "修改应用失败";
	private String APP_MODIFY_2 = "修改应用失败,存在相同应用编码纪录";
	private String APP_DELETE_0 = "删除应用失败";
	private String APP_DELETE_1 = "删除应用成功";
	private String APP_DELETE_2 = "删除失败：你选择的应用中有功能组关联";
	private String APP_DELETE_3 = "删除失败：你选择的应用中有功能关联";
	private String APP_DELETE_4 = "删除失败：你选择的应用中有角色关联";
	private String APP_DELETE_5 = "删除应用失败";

	// ******************************组织相关操作的信息定义***********************************
	private String ORG_ADD_1 = "新增组织成功";
	private String ORG_ADD_0 = "新增组织失败";
	private String ORG_ADD_2 = "新增组织失败,有重复的组织编码";
	private String ORG_MODIFY_1 = "修改组织成功";
	private String ORG_MODIFY_0 = "修改组织失败";
	private String ORG_MODIFY_2 = "修改组织失败，上级组织不能为本组织及本组织的子组织";
	private String ORG_DELETE_1 = "删除组织成功";
	private String ORG_DELETE_2 = "删除组织失败，组织含有子组织";
	private String ORG_DELETE_3 = "删除组织失败，组织下含有用户";
	private String ORG_DELETE_4 = "删除组织失败，组织下有角色";
	private String ORG_DELETE_5 = "删除组织失败";
	private String ORG_RELROLE_1 = "关联角色成功";
	private String ORG_RELROLE_2 = "关联角色失败";
	 //***************************组织角色信息*********************************************
	private String ORGROLE_ADD_0 = "添加组织角色失败";
	private String ORGROLE_ADD_1 = "添加组织角色成功";
	private String ORGROLE_ADD_2 = "添加组织角色失败,检查是否添加了重复的角色";
	private String ORGROLE_DELETE_0 = "批量删除组织角色失败";
	private String ORGROLE_DELETE_1 = "批量删除组织角色成功";	
	
	// ******************************功能相关操作的信息定义***********************************

	private String FUNC_ADD_1 = "新增功能成功";
	private String FUNC_ADD_0 = "新增功能失败";
	private String FUNC_ADD_2 = "新增功能失败,有重复的功能编码";
	private String FUNC_ADD_3 = "上级功能组不能为空";
	private String FUNC_MODIFY_1 = "修改功能成功";
	private String FUNC_MODIFY_2 = "上级功能组不能为空";
	private String FUNC_MODIFY_0 = "修改功能失败";
	private String FUNC_DELETE_0 = "删除功能失败";
	private String FUNC_DELETE_1 = "删除功能成功";

	// ******************************文件上传下载的相关操作的休息定义***********************************

	private String FILE_UPLOAD_1 = "批量导入用户数据成功";
	private String FILE_UPLOAD_0 = "批量导入用户数据失败";
	
	// ******************************访问控件的休息定义***********************************
	
	private String IPCONTROL_ADD_1 = "添加记录成功";
	private String IPCONTROL_ADD_0 = "添加记录失败";
	private String IPCONTROL_MODIFY_1 = "修改记录成功";
	private String IPCONTROL_MODIFY_0 = "修改记录失败";
	private String IPCONTROL_DELETE_1 = "删除记录成功";
	private String IPCONTROL_DELETE_0 = "删除记录失败";
	
	private String IPREFUSECONTROL_ADD_0 = "添加拒绝IP记录失败";
	private String IPREFUSECONTROL_ADD_1 = "添加拒绝IP记录成功";
	private String IPREFUSECONTROL_MODIFY_0 = "修改拒绝IP记录失败";
	private String IPREFUSECONTROL_MODIFY_1 = "修改拒绝IP记录成功";	
	private String IPREFUSECONTROL_DELETE_0 = "修改拒绝IP记录失败";
	private String IPREFUSECONTROL_DELETE_1 = "修改拒绝IP记录成功";	
	private String IPBINDCONTROL_ADD_0 = "添加绑定IP记录失败";
	private String IPBINDCONTROL_ADD_1 = "添加绑定IP记录成功";
	private String IPBINDCONTROL_MODIFY_0 = "修改绑定IP记录失败";
	private String IPBINDCONTROL_MODIFY_1 = "删除绑定IP记录成功";
	private String IPBINDCONTROL_DELETE_0 = "删除绑定IP记录失败";
	private String IPRULE_MODIFY_0 = "修改规则失败";
	private String IPRULE_MODIFY_1 = "修改规则成功";
	
	// ******************************功能组管理信息定义***********************************
	private String FUNCGROUP_DELETE_1 = "删除功能组成功";
	private String FUNCGROUP_DELETE_2 = "删除功能组失败,功能组下有功能关联";
	private String FUNCGROUP_DELETE_3 = "删除功能组失败,功能组下有角色关联";
	private String FUNCGROUP_DELETE_4 = "删除功能组失败,未知错误";
	private String FUNCGROUP_DELETE_5 = "删除功能组失败,功能组关联了数据权限规则";
	private String FUNCGROUP_ADD_0 = "添加功能组失败";
	private String FUNCGROUP_ADD_1 = "添加功能组成功";
	private String FUNCGROUP_MODIFY_0 = "修改功能组失败";
	private String FUNCGROUP_MODIFY_1 = "修改功能组成功";	
	private String FUNCGROUP_MODIFY_2 = "修改功能组失败，上级功能组不能为本功能组及本功能组的子功能组";
	
	// ******************************消息模板管理信息定义***********************************
	private String MSGMANGE_ADD_0 = "增加消息模板失败";
	private String MSGMANGE_ADD_1 = "增加消息模板成功";
	private String MSGMANGE_ADD_2 = "消息模板名称已经存在";
	private String MSGMANGE_MODIFY_0 = "修改消息模板失败";
	private String MSGMANGE_MODIFY_1 = "修改消息模板成功";
	private String MSGMANGE_MODIFY_2 = "消息模板名称已经存在";
	private String MSGMANGE_DELETE_0 = "删除消息模板失败";
	private String MSGMANGE_DELETE_1 = "删除消息模板成功";
	// ******************************消息发送信息定义***********************************
	private String MSGMANGE_SEND_0="消息发送失败";
	private String MSGMANGE_SEND_1="消息发送成功";
	private String MSGMANGE_SEND_2="您的消息将在系统审核后自动发送！";
	private String MSGMANGE_VERIFYMODIFY_0="消息审核修改失败";
	private String MSGMANGE_VERIFYMODIFY_1="您的群发消息已修改并申请审核，审核通过后系统将自动发送。";
	// ******************************消息审核信息定义***********************************
	private String MSGMANGE_VERIFY_0="消息审核失败";
	private String MSGMANGE_VERIFY_1="审核通过！此信息已发送给接收人！";
	private String MSGMANGE_VERIFY_2="审核未通过,系统已发送站内信通知发送人!";
	// ******************************群发任务查询定义***********************************
	private String MSGMANGE_DELETEVERIFY_0 = "删除群发任务失败";
	private String MSGMANGE_DELETEVERIFY_1 = "删除群发任务成功";
	private String MSGMANGE_DELJOB_0 = "撤销失败";
	private String MSGMANGE_DELJOB_1 = "撤销成功";
	// ******************************字典管理信息定义***********************************
	private String DICTTYPE_ADD_0 = "添加字典类型失败";
	private String DICTTYPE_ADD_1 = "添加字典类型成功";
	private String DICTTYPE_ADD_2 = "添加字典类型失败，有重复的字典类型编码";
	private String DICTTYPE_DELETE_0 = "删除字典类型失败";
	private String DICTTYPE_DELETE_1 = "删除字典类型成功";
	private String DICTTYPE_DELETE_2 = "存在字典项";
	private String DICTTYPE_DELETE_3 = "存在子字典类型";
	private String DICTTYPE_MODIFY_0 = "修改字典类型失败";
	private String DICTTYPE_MODIFY_1 = "修改字典类型成功";	
	private String DICTTYPE_MODIFY_2 = "修改字典类型失败，上级字典类型不能为自己或自己的子类型";	
	private String DICT_DELETE_1 = "删除字典项成功";
	private String DICT_DELETE_0 = "删除字典项失败";
	private String DICTVALUE_ADD_1 = "增加字典项成功";
	private String DICTVALUE_ADD_0 = "增加字典项失败";
	private String DICTVALUE_ADD_2 = "增加字典项失败，有重复的字典编码";
	private String DICTVALUE_MODIFY_1 = "修改记录成功";
	private String DICTVALUE_MODIFY_0 = "修改记录失败";
	// ******************************邮件信息定义***********************************
	private String INFOMANGE_DELETE_1 = "删除邮件成功";
	private String INFOMANGE_DELETE_0 = "删除邮件失败";
	
	private String INFOMANGE_ADD_1 = "发送邮件成功";
	private String INFOMANGE_ADD_0 = "发送邮件失败";	
	
	//***************************修改操作员密码*********************************************
	private String USER_MODIFYPWD_0 = "修改失败";
	private String USER_MODIFYPWD_1 = "修改成功";
	private String USER_MODIFYPWD_2 = "原始密码错误";
	private String USER_MODIFYPWD_3 = "新密码为空";
	private String USER_MODIFYPWD_4 = "用户不存在";
	
	//***************************地区管理*********************************************
	private String AREA_ADD_0 = "添加地区失败";
	private String AREA_ADD_1 = "添加地区成功";
	private String AREA_ADD_2 = "添加地区失败,地区编号重复";
	private String AREA_MODIFY_0 = "修改地区失败";
	private String AREA_MODIFY_1 = "修改地区成功";
	private String AREA_MODIFY_2 = "修改地区失败，上级地区不能为本地区及本地区的子地区";
	private String AREA_DELETE_0 = "删除地区失败";
	private String AREA_DELETE_1 = "删除地区成功";
	private String AREA_DELETE_2 = "删除地区失败，地区下包含子节点，不允许删除";
	
	
	//***************************角色组织信息*********************************************
	private String ROLEORG_ADD_0 = "添加组织角色失败";
	private String ROLEORG_ADD_1 = "添加组织角色成功";
	private String ROLEORG_DELETE_0 = "批量删除组织角色失败";
	private String ROLEORG_DELETE_1 = "批量删除组织角色成功";
	//***************************用户群组信息*********************************************
	private String CUSTOMER_ADD_0 = "增加用户群组失败";
	private String CUSTOMER_ADD_1 = "增加用户群组成功";
	private String CUSTOMER_MODIFY_0 = "修改用户群组失败";
	private String CUSTOMER_MODIFY_1 = "修改用户群组成功";
	private String CUSTOMER_DELETE_0 = "删除用户群组失败";
	private String CUSTOMER_DELETE_1 = "删除用户群组成功";	
	
	//***************************黑名单管理*********************************************
	private String BLACKUSER_ADD_0 = "添加黑名单失败";
	private String BLACKUSER_ADD_1 = "添加黑名单成功";
	private String BLACKUSER_ADD_2 = "添加黑名单失败，用户ID不存在";
	private String BLACKUSER_DELETE_0 = "删除黑名单失败";
	private String BLACKUSER_DELETE_1 = "删除黑名单成功";
	
	//***************************任务调度管理*********************************************
	private String JOBCONFIG_ADD_0="新增调度任务失败";
	private String JOBCONFIG_ADD_1="新增调度任务成功";
	private String JOBCONFIG_DELETE_0="删除调度任务失败";
	private String JOBCONFIG_DELETE_1="删除调度任务成功";
	private String JOBCONFIG_MODIFY_0="修改调度任务失败";
	private String JOBCONFIG_MODIFY_1="修改调度任务成功";
	
	//***************************导出用户同步日志记录*********************************************
	private String SYNUSERLOG_EXPORT_0 = "导出用户同步日志记录失败";
	private String SYNUSERLOG_EXPORT_1 = "导出用户同步日志记录成功";
	
	//***************************数据权限管理*********************************************
	private String DATARIGHTRULE_ADD_0 = "添加数据权限规则失败";
	private String DATARIGHTRULE_ADD_1 = "添加数据权限规则成功";
	private String DATARIGHTRULE_MODIFY_0 = "修改数据权限规则失败";
	private String DATARIGHTRULE_MODIFY_1 = "修改数据权限规则成功";	
	private String DATARIGHTRULE_DELETE_0 = "删了数据权限规则失败";
	private String DATARIGHTRULE_DELETE_1 = "删除数据权限规则成功";
	private String DATARIGHTRULE_DELETE_2 = "规则关联了功能组，删除数据权限规则失败";
	private String FUNCDATARIGHTRULE_ADD_0 = "添加功能数据权限规则失败";
	private String FUNCDATARIGHTRULE_ADD_1 = "添加功能数据权限规则成功";
	private String FUNCDATARIGHTRULE_DELETE_0 = "删除功能数据权限规则失败";
	private String FUNCDATARIGHTRULE_DELETE_1 = "删除功能数据权限规则成功";
	
	//***************************关键指标管理*********************************************
	private String SALEVALUATION_ADD_0 = "添加关键指标失败";
	private String SALEVALUATION_ADD_1 = "添加关键指标成功";
	private String SALEVALUATION_MODIFY_0 = "修改关键指标失败";
	private String SALEVALUATION_MODIFY_1 = "修改关键指标成功";
	private String SALEVALUATION_DELETE_0 = "删除关键指标失败";
	private String SALEVALUATION_DELETE_1 = "删除关键指标成功";
	private String SALEVALUATION_DELETEALL_0 = "批量删除关键指标失败";
	private String SALEVALUATION_DELETEALL_1 = "批量删除关键指标成功";
	private String SALEVALUATION_DELETEALL_2 = "选中内容中有已关联的内容，删除失败";

	//***************************规则引擎*********************************************
	private String RULE_ADD_0 = "添加规则信息失败";
	private String RULE_ADD_1 = "添加规则信息成功";
	private String RULE_MODIFY_0 = "修改规则信息失败";
	private String RULE_MODIFY_1 = "修改规则信息成功";
	private String RULE_DELETE_0 = "删除规则信息失败";
	private String RULE_DELETE_1 = "删除规则信息成功";
	
	private String RULEPARM_ADD_0="添加元数据失败";
	private String RULEPARM_ADD_1="添加元数据成功";
	private String RULEPARM_MODIFY_0="修改元数据失败";
	private String RULEPARM_MODIFY_1="修改元数据成功";
	private String RULEPARM_DELETE_0="删除元数据失败";
	private String RULEPARM_DELETE_1="删除元数据成功";
	
	private String RULEBODY_ADD_0="添加规则元数据失败";
	private String RULEBODY_ADD_1="添加规则元数据成功";
	private String RULEBODY_MODIFY_0="修改规则元数据失败";
	private String RULEBODY_MODIFY_1="修改规则元数据成功";
	private String RULEBODY_DELETE_0="删除规则元数据失败";
	private String RULEBODY_DELETE_1="删除规则元数据成功";
	
	/**********************************访问方式管理 ********************************/
	String ACCESSMODE_ADD_0 = "添加访问模式失败";
	String ACCESSMODE_ADD_1 = "添加访问模式成功";
	String ACCESSMODE_ADD_2 = "添加访问模式失败，模式代码已经存在";
	String ACCESSMODE_MODIFY_0 = "修改访问模式失败";
	String ACCESSMODE_MODIFY_1 = "修改访问模式成功";
	String ACCESSMODE_MODIFY_2 = "修改访问模式失败，模式代码已经存在";
	String ACCESSMODE_DELETE_0 = "删除访问模式失败";
	String ACCESSMODE_DELETE_1 = "删除访问模式成功";
	
	/**********************************资源管理*********************************/
	String RESOURCE_ADD_0 = "添加资源失败";
	String RESOURCE_ADD_1 = "添加资源成功";
	String RESOURCE_ADD_2 = "添加资源失败，资源编码已经存在";
	String RESOURCE_ADD_3 = "添加资源失败，父资源代码不能为自己";
	String RESOURCE_MODIFY_0 = "修改资源失败";
	String RESOURCE_MODIFY_1 = "修改资源成功";
	String RESOURCE_MODIFY_2 = "修改资源失败，资源编码已经存在";
	String RESOURCE_MODIFY_3 = "修改资源失败，父资源代码不能为自己";
	String RESOURCE_DELETE_0 = "删除资源失败";
	String RESOURCE_DELETE_1 = "删除资源成功";
	
	/**********************************权限管理************************************/
	String PERMISSION_ADD_0 = "添加权限失败";
	String PERMISSION_ADD_1 = "添加权限成功";
	String PERMISSION_DELETE_0 = "删除权限失败";
	String PERMISSION_DELETE_1 = "删除权限成功";
	String PERMISSION_BIND_0 = "绑定数据规则失败";
	String PERMISSION_BIND_1 = "绑定数据规则成功";
	String PERMISSION_UNBIND_0 = "解除绑定数据规则失败";
	String PERMISSION_UNBIND_1 = "解除绑定数据规则成功";	
}
