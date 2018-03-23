package com.talkweb.xwzcxt.action;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
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

}
