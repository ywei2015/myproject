package com.talkweb.twdpe.webcomponent.component;

import java.awt.TrayIcon.MessageType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.twdpe.base.dict.pojo.DictEntryInfo;
import com.talkweb.twdpe.base.dict.service.DictionaryService;
import com.talkweb.twdpe.base.org.exception.IllegalPasswordException;
import com.talkweb.twdpe.base.org.exception.UserNotExistException;
import com.talkweb.twdpe.base.org.exception.UserStatusException;
import com.talkweb.twdpe.base.org.pojo.Org;
import com.talkweb.twdpe.base.org.pojo.Position;
import com.talkweb.twdpe.base.org.pojo.User;
import com.talkweb.twdpe.base.org.service.OrgService;
import com.talkweb.twdpe.base.org.service.PositionService;
import com.talkweb.twdpe.base.org.service.UserService;
import com.talkweb.twdpe.base.webmsg.IReceiveManager;
import com.talkweb.twdpe.core.data.DataMap;
import com.talkweb.twdpe.core.data.IData;
import com.talkweb.twdpe.core.log.Logger;
import com.talkweb.twdpe.core.log.LoggerFactory;
import com.talkweb.twdpe.web.session.main.Session;
import com.talkweb.twdpe.web.session.util.SessionFactory;
import com.talkweb.twdpe.webcomponent.common.action.BusinessAction;


/**
 * 文件名称:    PortalMngAction.java
 * 内容摘要:  系统登录及首页展现功能ACTION
 * @author:   李锋 
 * @version:  1.0  
 * @Date:     2011-6-10 上午10:52:10  
 * 
 * 修改历史:  
 * 修改日期       修改人员   版本	   修改内容  
 * ----------------------------------------------  
 * 2011-6-10    李锋     1.0    1.0 XXXX
 *
 * 版权:   版权所有(C)2011
 * 公司:   拓维信息系统股份有限公司
 */
public class PortalMngAction extends BusinessAction {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(PortalMngAction.class);

	/**
	 * 当前登录用户名称
	 */
	public String userName = "匿名";
	/**
	 * 当前用户站内未读信息数
	 */
	public String websiteNewInfoCount = "0";

	private IReceiveManager rcvMng;

	/**
	 * 验证当前SESSION是否过期
	 * @Title: validateSessionExpire
	 * @Description: 验证当前SESSION是否过期
	 */
	@SuppressWarnings("unchecked")
	public String validateSessionExpire() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		Session session = SessionFactory.getInstance(request, response);
		String result = "";
		try {
			result = ((Map<String, Object>) session.getAttribute("USERSESSION")).get("id").toString();
			setOkMessage(result);
		} catch (Exception e) {
			this.setMessage(MessageType.Timeout, "登录超时，请重新登录");
		}
		return SUCCESS;
	}

	/**
	 * 用户登录验证（不含验证码）
	 * @return
	 */
	public String login() {
		return doLogin(false);
	}

	/**
	 * 单点登录方法。
	 * @return
	 */
	public String singleSignOn() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String username = request.getParameter("user");
		List<User> list = userService.getUsers(username);
		if (list != null && !list.isEmpty()) {
			Session session = SessionFactory.getInstance(request, ServletActionContext.getResponse());
			Map<String, Object> user = new HashMap<String, Object>(2);
			user.put("id", list.get(0).getUserId());
			user.put("name", list.get(0).getDisplayName());
			session.setAttribute("USERSESSION", user);
		}
		return SUCCESS;
	}

	/**
	 * 用户登录检验（含验证码）
	 * @Title: LoginIn
	 * @Description: 登录成功则定向到系统首页；不成功则显示错误信息
	 * @return 登录后重定向的页面
	 */
	public String loginIn() {
		return doLogin(true);
	}

	@SuppressWarnings("unchecked")
	private String doLogin(boolean validate) {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		Session session = SessionFactory.getInstance(request, response);
		// 从form中取得的数据（json格式）
		// 登录帐号
		String userid = request.getParameter("USERID");
		// 用户密码
		String pass = request.getParameter("PASSWORD");
		int result = -1;
		User user = new User();
		user.setUserCode(userid);
		Map pageData = new HashMap();
		if (validate) {
			String validateCode = request.getParameter("validateCode");
			Object code = session.getAttribute("loginValidateCode");
			if (code == null || !code.equals(validateCode)) {
				// 验证码不正确
				pageData.put("msg", -4);
				this.formatData(pageData);
				return SUCCESS;
			}
		}
		List<User> list = null;
		try {
			list = userService.getUsers(userid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (list != null && list.size() > 0) {
			// User u = list.get(0);
			// String pwd = u.getPassword();
			// boolean bl = PwdEncoder.instance.isPasswordValid(pwd, pass);
			User u = list.get(0);
			try {
				userService.login(u.getUserId(), pass);
				// 密码验证成功
				result = 2;
			} catch (IllegalPasswordException e) {
				ServletActionContext.getRequest().setAttribute("throw", e);
				result = -2;
				logger.error(e.getMessage(), e);
			} catch (UserStatusException e) {
				ServletActionContext.getRequest().setAttribute("throw", e);
				result = -2;
				logger.error(e.getMessage(), e);
			} catch (UserNotExistException e) {
				ServletActionContext.getRequest().setAttribute("throw", e);
				result = -2;
				logger.error(e.getMessage(), e);
			}
			if (result == 2) {
				 if(list.size() > 1){
				 result = 1;
				 pageData.put("usercode", userid);
				 session.setAttribute("hasJianzhi", "true");
				 session.setAttribute("usercode", userid);
				 } else {
				// 当前用户登录核验成功，保存用户ID，用户名称
				result = 0;
				session.setAttribute("usercode", userid);
				Map<String, Object> userMap = new HashMap<String, Object>();
				userMap.put("id", u.getUserId());
				userMap.put("name", u.getDisplayName());
				userMap.put("code", u.getUserCode());
				
				userMap.put("positionid", u.getPositionId()); //GuveXie 2014-07-24
				userMap.put("orgid", u.getOrgId()); //GuveXie 2014-07-24
				session.setAttribute("USERSESSION", userMap);
				 }
			}
		} else {
			result = -1;// 用户不存在
		}
		pageData.put("msg", result);
		this.formatData(pageData);
		return SUCCESS;
	}

	/**
	 * 查询用户登录帐号
	 * @Title: userSelect
	 * @Description: 查询用户登录帐号
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String userSelect() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		Session session = SessionFactory.getInstance(request, response);
		String usercode = session.getAttribute("usercode").toString();// request.getParameter("usercode");
		
		List<User> list = userService.getLoginUsersByUserCode(usercode);
		List<Map> reslist = new ArrayList<Map>();
		try {
			for (int i = 0; i < list.size(); i++) {
				User user = list.get(i);
				Map map = new HashMap();
				map.put("value", user.getUserId());
				String text = user.getUserCode();
				if (user.getOrgId() != null) {
					Org org = orgService.getOrg(user.getOrgId());
					if (org != null && org.getOrgName() != null) {
						text += "  " + org.getOrgName();
					} else {
						text += "        ";
					}
				}
				if (user.getPositionId() != null) {
					Position pos = positionService.getPosition(user.getPositionId());
					if (pos != null && pos.getPosiName() != null) {
						text += "  " + pos.getPosiName();
					} else {
						text += "        ";
					}
				}
				// 填充用户岗位类型
				DictEntryInfo dictEntryInfo3 = dictionaryService.getDictEntryById("ORG_DUTY_TYPE",user.getDutyType());
				if (dictEntryInfo3 != null) {
					text += "  " + dictEntryInfo3.getName();
				} else {
					text += "      ";
				}
				map.put("text", text);
				reslist.add(map);
			}
		} catch (Exception e) {
			ServletActionContext.getRequest().setAttribute("throw", e);
			logger.error(e.getMessage(), e);
		}
		this.formatData(reslist);
		return SUCCESS;
	}

	/**
	 * 多用户选择帐号后登录
	 * @Title: userSelectLongin
	 * @Description: 多用户选择帐号后登录
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String userSelectLongin() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		Session session = SessionFactory.getInstance(request, response);
		String userid = request.getParameter("userid");
		int res = 0;
		try {
			User u = userService.getUser(new Long(userid));
			Map<String, Object> userMap = new HashMap<String, Object>();
			userMap.put("id", u.getUserId());
			userMap.put("name", u.getDisplayName());
			userMap.put("usercode", u.getUserCode());

			userMap.put("positionid", u.getPositionId()); //GuveXie 2014-07-24
			userMap.put("orgid", u.getOrgId()); //GuveXie 2014-07-24
			session.setAttribute("USERSESSION", userMap);
			res = 1;
		} catch (Exception e) {
			ServletActionContext.getRequest().setAttribute("throw", e);
			logger.error(e.getMessage(), e);
		}
		Map pageData = new HashMap();
		pageData.put("res", res);
		this.formatData(pageData);
		return SUCCESS;
	}

	/**
	 * 用户注销功能
	 * @Title: loginOut
	 * @Description: 用户注销
	 */
	@SuppressWarnings("unchecked")
	public String loginOut() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		Session session = SessionFactory.getInstance(request, response);
		if (session.getAttribute("USERSESSION") != null) {
			Map<String, Object> userserssion = (Map<String, Object>) session.getAttribute("USERSESSION");
			// 当前登录用户的登录ID编号
			String loginUserID = userserssion.get("id").toString();
			// 当前登录用户的用户名称
			String loginUserName = userserssion.get("name").toString();

			// 系统运维人员登录
			// addSystemLog(loginUserID,loginUserName,LogConfigParam.SYS_MGR_ID,
			// LogConfigParam.SYS_MGR_NAME, LogConfigParam.OPERATOR_TYPE_LOGOUT,
			// loginUserName+"注销"+LogConfigParam.APP_SYS_NAME, "注销成功");
		}
		session.invalidate();
		return SUCCESS;
	}

	/**
	 * 初始化用于首页展现时显示的信息
	 * @Title: ini_topframe
	 * @Description: 用于初始化首页上层frame信息
	 */
	@SuppressWarnings("unchecked")
	public void iniTopframe() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		Session session = SessionFactory.getInstance(request, response);
		Map<String, Object> userMap = ((Map<String, Object>) session.getAttribute("USERSESSION"));
		// 当前登录用户的登录ID编号
		String loginUserID = userMap.get("id").toString();
		// String loginUserID = "1";
		// 查找用户名称信息
		try {
			userName = userMap.get("name").toString();

			// 查找用户名称信息
			IData map = new DataMap();
			map.put("RECEIVER", loginUserID);
			int newcount = rcvMng.countAllMsgNew(map);
			websiteNewInfoCount = String.valueOf(newcount);
		} catch (Exception e) {
			ServletActionContext.getRequest().setAttribute("throw", e);
			userName = "匿名";
			// logger.error(e.getMessage(),e);
		}
		//this.formatData(o)
	}

	/**
	 * userName 获取当前登录用户名称
	 * @return the userName
	 * @since CodingExample Ver(编码范例查看) 1.0
	 */

	public String getUserName() {
		return userName;
	}

	/**
	 * websiteNewInfoCount 当前用户站内未读信息数
	 * @return the websiteNewInfoCount
	 * @since CodingExample Ver(编码范例查看) 1.0
	 */

	public String getWebsiteNewInfoCount() {
		return websiteNewInfoCount;
	}

	/**
	 * getLoginUserInfo 取登录用户信息
	 */
	@SuppressWarnings("unchecked")
	public String getLoginUserInfo() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		Session session = SessionFactory.getInstance(request, response);
		Map<String, Object> userMap = ((Map<String, Object>) session.getAttribute("USERSESSION"));
		userName = userMap.get("name").toString();
		int size = 1;
		try {
			String loginUserID = userMap.get("id").toString();
			// 查找用户名称信息
			IData map = new DataMap();
			map.put("RECEIVER", loginUserID);
			String usercode = session.getAttribute("usercode").toString();// request.getParameter("usercode");
			List<User> list = userService.getUsers(usercode);
			if (list != null && list.size() > 0) {
				size = list.size();
			}
			size = list.size();
			// 取站内信息方法现在有问题，暂时屏蔽
			// int newcount = rcvMng.countAllMsgNew(map);
			// websiteNewInfoCount = String.valueOf(newcount);
		} catch (Exception e) {
			ServletActionContext.getRequest().setAttribute("throw", e);
			userName = "匿名";
			// logger.error(e.getMessage(),e);
		}
		Map pageData = new HashMap();
		pageData.put("size", size);
		pageData.put("userName", userName);
		pageData.put("websiteNewInfoCount", websiteNewInfoCount);
		this.formatData(pageData);
		return SUCCESS;
	}

	public IReceiveManager getRcvMng() {
		return rcvMng;
	}

	public void setRcvMng(IReceiveManager rcvMng) {
		this.rcvMng = rcvMng;
	}

	@Autowired
	private UserService userService;

	@Autowired
	private OrgService orgService;

	@Autowired
	private PositionService positionService;
	@Autowired
	private DictionaryService dictionaryService;	
	
	

}
