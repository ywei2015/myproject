package tw.sysbase.controller.sec; 

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;









import tw.sysbase.dao.PaginationSupport;
import tw.sysbase.entity.Pagination;
import tw.sysbase.entity.pub.Dic;
import tw.sysbase.entity.sec.Role;
import tw.sysbase.entity.sec.RoleVo;
import tw.sysbase.entity.sec.User;
import tw.sysbase.entity.sec.UserVo;
import tw.sysbase.exception.LogException;
import tw.sysbase.pub.ResponseBase;
import tw.sysbase.pub.ResultUtil;
import tw.sysbase.servie.pub.DicService;
import tw.sysbase.servie.sec.RoleService;
import tw.sysbase.servie.sec.SecurityService;
/**
* <p>类说明：Spring MVC Test Controller Class</p> 
* 
* <p>Copyright: Copyright (c) 2016</p>
*    
* @author xie&li
* 2017-08-26
*
* @version 1.0 
 * 
 */
@Controller
@RequestMapping("/RoleMgr")
public class RoleMgrController {
	 
	@Resource
	private RoleService roleService;
	@Resource
	private SecurityService securityService;
	
	
	
	/**
	 * @Description: 用户分页查询
	 * @author: zy
	 */
	@RequestMapping("findRoleListPagination")
	@ResponseBody
	public Pagination findRoleListPagination(RoleVo rolevo){
		
		PaginationSupport paginationSupport=roleService.findRoleListPagination(rolevo);
		Pagination pagination = Pagination.init(paginationSupport);
		return pagination;
	}
	
	/**
	 * @Description: 保存
	 * @author: zy
	 */
	@RequestMapping("saveRole")
	@ResponseBody
	public Map<String,Object> saveRole(RoleVo rolevo){	
		securityService.saveRole(rolevo);
		
		return ResultUtil.DefaultResult();
	}
	
	/**
	 * 添加角色时的name不能重复
	 * @param rolevo
	 * @return
	 */
	@RequestMapping("findRoleName")
	@ResponseBody
	public boolean findRoleName(RoleVo rolevo) {
		boolean flag = roleService.findRoleName(rolevo);
		return flag;
	}
	
	/**
	 * @Description: 删除
	 * @author: zy
	 */
	@RequestMapping("deletes")
	@ResponseBody
	public Map<String,Object> deletes(String ids){	
		securityService.deleteRoles(ids);
		
		return ResultUtil.DefaultResult();
	}
	
	/**
	 * 角色下的所有用户
	 * 
	 */
	@RequestMapping("queryUsersByRoleId")
	@ResponseBody
	public Pagination queryUsersByRoleId(String roleId) {
		ResponseBase res = new ResponseBase();
		Pagination pagination=new Pagination();

		List<UserVo> userVos = securityService.findUserById(roleId);
		res.setDataset(userVos, "user");
		List list1 = (List) res.getDataset().get("user");
		pagination.setRows(list1);
		if(list1!=null){
			pagination.setTotal(list1.size());
		}
		return pagination;
	}
	
	/**
	 * 修改时通过id查找角色
	 */
	@RequestMapping("findRole")
	@ResponseBody
	public RoleVo findRole(String roleId) {
		if (StringUtils.isBlank(roleId)) {
			throw new RuntimeException("id:传入的参数为空!");
		}
		Role role = securityService.findRoleById(roleId);
		RoleVo roleVo = new RoleVo(role);
		return roleVo;
	}
	
	/**
	 * 给角色分配用户
	 */
	@RequestMapping("saveUserForRole")
	@ResponseBody
	public Map<String,Object> saveUserForRole(String roleId, String ids) {
		 securityService.saveUserForRole(roleId,ids);
		return ResultUtil.DefaultResult();
	}
	
	

	/**
	 * 删除角色
	 */
	@RequestMapping("deleteUserForRole")
	@ResponseBody
	public Map<String,Object> deleteUserForRole(String roleId, String ids){	
		securityService.deleteUserForRole(roleId,ids);
		
		return ResultUtil.DefaultResult();
	}
	
	/**
	 * @Description: 保存一个dic实体
	 * @author: zw
	 */
//	@RequestMapping
//	@ResponseBody
//	public Map<String,Object> save(Dic dic){	
//		dic=dicService.save(dic);
//		return ResultUtil.DefaultResult();
//	}
	
}
