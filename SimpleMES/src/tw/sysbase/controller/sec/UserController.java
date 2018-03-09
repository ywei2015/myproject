package tw.sysbase.controller.sec; 

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import tw.sysbase.dao.PaginationSupport;
import tw.sysbase.entity.Pagination;
import tw.sysbase.entity.sec.User;
import tw.sysbase.entity.sec.UserVo;
import tw.sysbase.exception.LogException;
import tw.sysbase.pub.QueryCorrelation;
import tw.sysbase.pub.ResponseBase;
import tw.sysbase.pub.ResultUtil;
import tw.sysbase.servie.sec.SecurityService;
import tw.sysbase.servie.sec.UserService;
/**
* <p>Copyright: Copyright (c) 2017</p>
*    
* @author cyj
* 2017-09-07
*
* @version 1.0 
 * 
 */
@Controller
@RequestMapping("/User")
public class UserController {
	 
	@Resource
	private SecurityService securityService; 
	@Resource
	private UserService userService; 

	/**
	 * 获取用户列表
	 * @param name
	 * @param code
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/findUserList")	
	public Pagination findUserList(UserVo userVo){  

		PaginationSupport ps=null ;
		
		try {
			ps = userService.findUserList(userVo);		
		} catch (Exception e) { 
			e.printStackTrace();
			LogException.logEx(e,UserController.class,"findUserList");
		} 
		return Pagination.init(ps);
	} 
	
	/**
	 * 获取有效用户列表
	 * @param name
	 * @param code
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/findUserListByTrue")	
	public Pagination findUserListByTrue(UserVo userVo){  

		PaginationSupport ps=null ;
		
		try {
			ps = userService.findUserListByTrue(userVo);		
		} catch (Exception e) { 
			e.printStackTrace();
			LogException.logEx(e,UserController.class,"findUserList");
		} 
		return Pagination.init(ps);
	} 
	
	/**
	 * 获取用户编辑界面数据
	 * @param userId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/findUserById")	
	public UserVo findUserById(String id){  
		UserVo userVo=null;
		try {
			userVo = userService.findUser(id);
		} catch (Exception e) { 
			e.printStackTrace();
			LogException.logEx(e,UserController.class,"findUserById");
		} 
		return userVo;
	} 
	
	/**
	 * @Description: 删除
	 * @author: zy
	 */
	@RequestMapping("deletes")
	@ResponseBody
	public Map<String,Object> deletes(String ids){	
		securityService.deleteUsers(ids);
		return ResultUtil.DefaultResult();
	}
	
	/**
	 * @Description: 新增
	 * @author: zy
	 */
	@RequestMapping("save")
	@ResponseBody
	public Map<String,Object> save(UserVo userVo){	
		securityService.saveUser(userVo);
		
		return ResultUtil.DefaultResult();
	}
	
	/**
	 * 个人中心修改密码
	 */
	@RequestMapping("updatPwd")
	@ResponseBody
	public boolean updatPwd(String oldPwd,String newPwd){
		
		boolean flag=securityService.updatPwd(oldPwd,newPwd);
		return flag;
	}
	
	/**
	 * 个人中心  信息
	 * @param id
	 * @return
	 */
	@RequestMapping("findUser")
	@ResponseBody
	public UserVo findUser(){  
		UserVo userVo=null;
		try {
			userVo = securityService.findUser();
		} catch (Exception e) { 
			e.printStackTrace();
			LogException.logEx(e,UserController.class,"findUserById");
		} 
		return userVo;
	}  
	
	/**
	 * 个人中心  信息
	 * @param id
	 * @return
	 */
	@RequestMapping("findUserName")
	@ResponseBody
	public boolean findUserName(UserVo userVo) {
		return userService.findUserName(userVo);
	}
}

	

    
