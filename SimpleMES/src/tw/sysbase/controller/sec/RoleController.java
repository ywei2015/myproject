package tw.sysbase.controller.sec; 

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import tw.sysbase.exception.LogException;
import tw.sysbase.pub.ResponseBase;
import tw.sysbase.servie.sec.RoleService;
import tw.sysbase.servie.sec.SecurityService;
/**
* <p>Copyright: Copyright (c) 2017</p>
*    
* @author cyj
* 2017-09-08
*
* @version 1.0 
 * 
 */
@Controller
@RequestMapping("/Role")
public class RoleController {
	 
	@Resource
	private SecurityService securityService; 
	@Resource
	private RoleService roleService; 

	/**
	 * 获取角色编辑界面数据
	 * @param userId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/findRoleById")	
	public ResponseBase findRoleById(String roleId){  
		ResponseBase res = new ResponseBase();
		try {
			res = roleService.findRole(roleId);
		} catch (Exception e) { 
			e.printStackTrace();
			LogException.logEx(e,RoleController.class,"findRoleById");
		} 
		return res;
	} 
}
