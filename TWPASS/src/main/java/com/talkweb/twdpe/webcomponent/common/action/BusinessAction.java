package com.talkweb.twdpe.webcomponent.common.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.talkweb.twdpe.base.log.pojo.LogConfigParam;
import com.talkweb.twdpe.base.log.pojo.LogInfo;
import com.talkweb.twdpe.base.log.service.LogService;
import com.talkweb.twdpe.base.org.pojo.Org;
import com.talkweb.twdpe.base.right.service.AuthorityService;
import com.talkweb.twdpe.core.util.IpUtil;
import com.talkweb.twdpe.core.util.StringUtil;
import com.talkweb.twdpe.web.action.ComponentAction;
import com.talkweb.twdpe.web.session.main.Session;
import com.talkweb.twdpe.web.session.util.SessionFactory;

/**
 * @author: 2012-12-17，范秋海
 */
public class BusinessAction extends ComponentAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5143175422682523630L;

	/**
	 * 运维系统平台：添加操作日志记录
	 * 
	 * @Title: addSystemLog
	 * @Description: 将操作日志记录到日志表中
	 * @param moduleid
	 *            模块编号：com.talkweb.twdpe.base.log.pojo.LogConfigParam中常量
	 * @param modulename
	 *            模块名称：com.talkweb.twdpe.base.log.pojo.LogConfigParam中常量
	 * @param actiontype
	 *            操作类型：com.talkweb.twdpe.base.log.pojo.LogConfigParam中常量，如：
	 *            OPERATOR_TYPE_LOGIN
	 * @param content
	 *            操作的内容（如：调用方法 XXX来增加用户401的基本信息）
	 * @param result
	 *            操作结果（如：失败）
	 * @return void
	 */
	protected void addSystemLog(String moduleid, String modulename, String actiontype, String content, String result) {
		Map<String, Object> map = this.getUserSession();
		if (map != null) {
			// 当前登录用户的登录ID编号
			String loginUserID = map.get("id").toString();
			// 当前登录用户的用户名称
			String loginUserName = map.get("name").toString();
			addSystemLog(loginUserID, loginUserName, moduleid, modulename, actiontype, content, result);
		}
	}

	/**
	 * 运维系统平台：添加操作日志记录（带访问资源信息）
	 * 
	 * @Title: addSystemLog
	 * @Description: 将操作日志记录到日志表中
	 * @param loginUserID
	 *            登录用户编号
	 * @param loginUserName
	 *            登录用户名称
	 * @param moduleid
	 *            模块编号：com.talkweb.twdpe.base.log.pojo.LogConfigParam中常量
	 * @param modulename
	 *            模块名称：com.talkweb.twdpe.base.log.pojo.LogConfigParam中常量
	 * @param actiontype
	 *            操作类型：com.talkweb.twdpe.base.log.pojo.LogConfigParam中常量，如：
	 *            OPERATOR_TYPE_LOGIN
	 * @param content
	 *            操作的内容（如：调用方法 XXX来增加用户401的基本信息）
	 * @param result
	 *            操作结果（如：失败）
	 * @param resourcename
	 *            被访问资源名
	 * @return void
	 */
	protected void addSystemLog(String moduleid, String modulename, String actiontype, String content, String result,
			String resourcename) {
		Map<String, Object> map = this.getUserSession();
		if (map != null) {
			// 当前登录用户的登录ID编号
			String loginUserID = map.get("id").toString();
			// 当前登录用户的用户名称
			String loginUserName = map.get("name").toString();
			addSystemLog(loginUserID, loginUserName, moduleid, modulename, actiontype, content, result, resourcename);
		}
	}

	/**
	 * 运维系统平台：添加操作日志记录
	 * 
	 * @Title: addSystemLog
	 * @Description: 将操作日志记录到日志表中
	 * @param loginUserID
	 *            登录用户编号
	 * @param loginUserName
	 *            登录用户名称
	 * @param moduleid
	 *            模块编号：com.talkweb.twdpe.base.log.pojo.LogConfigParam中常量
	 * @param modulename
	 *            模块名称：com.talkweb.twdpe.base.log.pojo.LogConfigParam中常量
	 * @param actiontype
	 *            操作类型：com.talkweb.twdpe.base.log.pojo.LogConfigParam中常量，如：
	 *            OPERATOR_TYPE_LOGIN
	 * @param content
	 *            操作的内容（如：调用方法 XXX来增加用户401的基本信息）
	 * @param result
	 *            操作结果（如：失败）
	 * @return void
	 */
	protected void addSystemLog(String loginUserID, String loginUserName, String moduleid, String modulename,
			String actiontype, String content, String result) {
		addSystemLog(loginUserID, loginUserName, moduleid, modulename, actiontype, content, result, "");
	}

	/**
	 * 运维系统平台：添加操作日志记录
	 * 
	 * @Title: addSystemLog
	 * @Description: 将操作日志记录到日志表中
	 * @param loginUserID
	 *            登录用户编号
	 * @param loginUserName
	 *            登录用户名称
	 * @param moduleid
	 *            模块编号：com.talkweb.twdpe.base.log.pojo.LogConfigParam中常量
	 * @param modulename
	 *            模块名称：com.talkweb.twdpe.base.log.pojo.LogConfigParam中常量
	 * @param actiontype
	 *            操作类型：com.talkweb.twdpe.base.log.pojo.LogConfigParam中常量，如：
	 *            OPERATOR_TYPE_LOGIN
	 * @param content
	 *            操作的内容（如：调用方法 XXX来增加用户401的基本信息）
	 * @param result
	 *            操作结果（如：失败）
	 * @param resourcename
	 *            被访问资源名
	 * @return void
	 */
	protected void addSystemLog(String loginUserID, String loginUserName, String moduleid, String modulename,
			String actiontype, String content, String result, String resourcename) {
		HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(
				ServletActionContext.HTTP_REQUEST);
		// 参数IDATA
		LogInfo log = new LogInfo();
		log.setSourceIp(IpUtil.getServerIpAddr(request));
		log.setTargetIp(IpUtil.getClientIpAddr(request));
		log.setAccount(loginUserID);
		log.setAccountName(loginUserName);
		log.setAccountType(LogConfigParam.OPERATOR_SYS);
		log.setAppId(LogConfigParam.APP_SYS_ID);
		log.setAppName(LogConfigParam.APP_SYS_NAME);
		log.setFuncGroupId(moduleid);
		log.setFuncGroupName(modulename);
		log.setActionDescription(content);
		log.setActionResult(result);
		log.setActionType(actiontype);
		log.setResourceName(resourcename);
		log.setResourceUrl(request.getRequestURL().toString());
		logService.addLog(log);
	}

	/**
	 * 将JSON格式的字符转换成一段非JSON格式的字符集
	 * 
	 * @Title: jsonToString
	 * @Description: 将JSON格式的字符转换成一段非JSON格式的字符集
	 * @param json
	 *            String 指定的JSON格式字符串
	 * @return
	 */
	protected String jsonToString(String json) {
		json = json.replaceAll(":", "=");
		json = json.replaceAll("[${}]", "#");
		json = json.replaceAll("\"", "");
		return json;
	}

	/**
	 * 将JSON对象转换成一段非JSON格式的字符集
	 * 
	 * @Title: jsonToString
	 * @Description: 将JSON对象转换成一段非JSON格式的字符集
	 * @param jo
	 *            JSONObject 指定的JSON对象
	 * @return
	 */
	protected String jsonToString(JSONObject jo) {
		String json = jo.toString();
		json = json.replaceAll(":", "=");
		json = json.replaceAll("[${}]", "#");
		json = json.replaceAll("\"", "");
		return json;
	}

	/**
	 * 获取登录用户信息包括id,name两个属性
	 * 
	 * @return Map<String,Object>
	 * **/
	@SuppressWarnings("unchecked")
	protected Map<String, Object> getUserSession() {
		// 获得当前的请求
		HttpServletRequest request = ServletActionContext.getRequest();
		// 获取当前页面响应
		HttpServletResponse response = ServletActionContext.getResponse();
		Session session = SessionFactory.getInstance(request, response);
		Map<String, Object> map = null;
		Object userSession = session.getAttribute("USERSESSION");
		if (userSession != null)
			map = (Map<String, Object>) userSession;
		return map;
	}

	/**
	 * 日志AOP存根方法
	 * 
	 * @return 会话对象
	 */
	public Map<String, Object> log() {
		Map<String, Object> session = new HashMap<String, Object>();
		Map<String, Object> user = getUserSession();
		if (user != null) {
			session.put("session-userid", user.get("id"));
			session.put("session-username", user.get("name"));
			session.put("session-usertype", "1");
		}
		return session;
	}

	/**
	 * 获取用户(<i>userId</i>)对某个资源(<i>resourceCode</i>)的所有有权的访问方式ID
	 * 
	 * @param userId
	 *            用户ID值
	 * @param resourceCode
	 *            资源编码
	 * @param 有权限的访问方式ID列表
	 *            ，查询结果为空时，列表大小为 <b>0</b>
	 */
	protected List<String> getPrivilegeResourceModes(String resourceCode) {
		List<String> list = null;
		Map<String, Object> map = this.getUserSession();
		if (map != null) {
			String loginUserID = map.get("id").toString();
			if (StringUtil.isNumber(loginUserID))
				list = authorityService.getPrivilegeResourceModes(Long.parseLong(loginUserID), resourceCode);
		}
		return list;
	}

	/**
	 * 判定某个用户(<i>userId</i>)在指定访问方式(<i>modeId</i>)下对某个资源(<i>resourceCode</i>)
	 * 是否有访问权限
	 * 
	 * @param userId
	 *            用户ID值
	 * @param resourceCode
	 *            资源编码
	 * @param modeId
	 *            访问方式ID值
	 * @return 判定结果：<li>true</li> 该用户拥有该项权限 <li>false</li> 该用户没有该项权限
	 */
	protected boolean hasPrivilege(String resourceCode, String modeId) {
		boolean bl = false;
		Map<String, Object> map = this.getUserSession();
		if (map != null) {
			String loginUserID = map.get("id").toString();
			if (StringUtil.isNumber(loginUserID))
				bl = authorityService.hasPrivilege(Long.parseLong(loginUserID), resourceCode, modeId);
		}
		return bl;
	}

	/**
	 * 获取登录用户的ID
	 * 
	 * @return 返回登录用户的ID
	 */
	protected long getLongUserID() {
		long userID = 0;
		Map<String, Object> map = this.getUserSession();
		if (map != null) {
			String loginUserID = map.get("id").toString();
			userID = Long.parseLong(loginUserID);
		}
		return userID;
	}

	/***
	 * 统一处理下拉组织树数据格式
	 * 
	 * @param List
	 *            list
	 * */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected List<Map> initTreeAsynData(List<Org> list) {
		List<Map> l = new ArrayList<Map>();
		if (list != null) {
			for (Org org : list) {
				Map map = new HashMap();
				map.put("id", org.getOrgId());
				map.put("val", org.getOrgName());
				map.put("pid", org.getParentOrgId());
				if (org.getIsLeaf() != null && !org.getIsLeaf()) {
					map.put("isleaf", false);
					map.put("asyn", "true");
				} else {
					map.put("isleaf", true);
					map.put("asyn", "false");
				}
				l.add(map);
			}
		}
		return l;
	}

	@Autowired
	private LogService logService;

	@Autowired
	private AuthorityService authorityService;
}
