package sail.beans.controller; 
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import sail.beans.base.ResponseBase;
import sail.beans.entity.User;
import sail.beans.service.UserService;


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
		String userCode = request.getParameter("userCode");
		String userPwd = request.getParameter("userPwd");
		HttpSession	session = request.getSession();
		User user  = userService.getUser(userCode, userPwd);
		if (user == null){
			res.setResponseData(false, "请检查用户名或密码是否正确!");
		}else{
			//记录用户登录日志
			userService.saveUserLog(userCode, session.getId());
			res.setResponseData(true, "登陆成功!");
			res.setDataset(user, "user");
			session.setAttribute("user", user);
			session.setAttribute("isLogin", "true");
		}
		return res;
	}
}
