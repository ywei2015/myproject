package sail.beans.controller; 
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import sail.beans.base.ResponseBase;
import sail.beans.entity.BatPdaLoginLog;
import sail.beans.entity.User;
import sail.beans.service.UserService;
import sail.beans.support.DateBean;


/**
* <p>类说明：Spring MVC Test Controller Class</p> 
* 
* <p>Copyright: Copyright (c) 2016</p>
*    
* @author xie&li
* 2016-08-26
*
* @version 1.0 
 * 
 */
@Controller
@RequestMapping("/user")
public class UserController{
	 
	@Resource
	private UserService userService; 

	/**
	 * 用户登录
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getUser")	 
	public ResponseBase getUser(HttpServletRequest request){  
		ResponseBase res = new ResponseBase();
		String userCode = request.getParameter("f_user_code");
		String userPwd = request.getParameter("f_password");
		String clientType = request.getParameter("f_client_type");
		String deviceIp = request.getParameter("f_device_ip");
		String deviceUuid = request.getParameter("f_device_uuid");
		String deviceModel = request.getParameter("f_device_model");
		String deviceOs = request.getParameter("f_device_os");
		String appVersion = request.getParameter("f_app_version");
		HttpSession	session = request.getSession();
		User user  = userService.getUser(userCode, userPwd);
		if (user == null){
			res.setResponseData("0", "请检查用户名或密码是否正确!");
		}else{
			//记录用户登录日志
			BatPdaLoginLog batPdaLoginLog = new BatPdaLoginLog();
			batPdaLoginLog.setUserCode(userCode);
			batPdaLoginLog.setClientType(clientType);
			batPdaLoginLog.setDeviceIp(deviceIp);
			batPdaLoginLog.setDeviceUuid(deviceUuid);
			batPdaLoginLog.setDeviceModel(deviceModel);
			batPdaLoginLog.setDeviceOs(deviceOs);
			batPdaLoginLog.setAppVersion(appVersion);
			batPdaLoginLog.setSessionId(session.getId());
			batPdaLoginLog.setLoginTime(DateBean.getSysdateTime());
			batPdaLoginLog.setState("1");
			userService.saveUserLog(batPdaLoginLog);
			res.setResponseData("1", "登陆成功!");
			res.setDataset(user, "user");
			session.setAttribute("user", user);
			session.setAttribute("isLogin", "true");
		}
		return res;
	}
}
