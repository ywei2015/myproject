
package tw.sysbase.controller.sec; 

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import tw.sysbase.dao.PaginationSupport;
import tw.sysbase.entity.Log;
import tw.sysbase.entity.Pagination;
import tw.sysbase.entity.pub.BuildTree;
import tw.sysbase.entity.pub.Tree;
import tw.sysbase.entity.pub.TreeMenu;
import tw.sysbase.entity.sec.Permission;
import tw.sysbase.entity.sec.Resources;
import tw.sysbase.entity.sec.Role;
import tw.sysbase.entity.sec.User;
import tw.sysbase.exception.LogException;
import tw.sysbase.pub.Constants;
import tw.sysbase.pub.ResponseBase;
import tw.sysbase.pub.ResultUtil;
import tw.sysbase.pub.StingUtil;
import tw.sysbase.servie.sec.PermissionService;
import tw.sysbase.servie.sec.ResourceService;
import tw.sysbase.servie.sec.RoleService;
import tw.sysbase.servie.sec.SecurityService;
import tw.sysbase.servie.sec.UserService;
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
@RequestMapping("/Security")
public class SecurityController {
	 
	@Resource
	private SecurityService securityService; 
	@Resource
	private UserService userService; 
	@Resource
	private PermissionService permissionService;
	@Resource
	private ResourceService resourceService;
	@Resource
	private RoleService roleService;
	

	/** 登录MES
	 * @param pid
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/loginUser")	
	public Map<String,Object> loginUser(String userCode, String userPwd,HttpServletRequest req){ 
		String userIp = req.getRemoteAddr();
		User user = securityService.loginUser(userCode, userPwd, userIp);
		HttpSession session = req.getSession(true);
		Map<String,Object> map=new HashMap<>();
		if(user!=null){
				//将user的id保存到session中
			session.setAttribute(Constants.SESSION_KEY_OPERATOR, user.getId());
			session.setAttribute(Constants.SESSION_KEY_ISLOGIN, "true");
			map.put(Constants.STATUS, Constants.SUCCESS);
			map.put(Constants.SESSION_KEY_OPERATOR, user.getId());
			map.put(Constants.SESSION_KEY_OPERNAME, user.getName());
			return 	map;
		}
		return ResultUtil.ErrorResult();
	} 
	
	@ResponseBody
	@RequestMapping(value="/logonOut")	
	public Map<String,Object> logonOut(HttpServletRequest req){  
		req.getSession().removeAttribute(Constants.SESSION_KEY_OPERATOR);
		req.getSession().removeAttribute(Constants.SESSION_KEY_ISLOGIN);
		return ResultUtil.DefaultResult();
	}
	
	/**
	 * 用户登录获取菜单权限
	 * @param userId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getMenuByUserId")	
	public ResponseBase getMenuByUserId(HttpServletRequest request){  
		ResponseBase res = new ResponseBase();
		String userId =(String) request.getSession().getAttribute(Constants.SESSION_KEY_OPERATOR);
		try {
			if(!StringUtils.isBlank(userId)){
				List<Tree<TreeMenu>> trees = new ArrayList<Tree<TreeMenu>>();
				List<TreeMenu> tests = new ArrayList<TreeMenu>();
				Tree<TreeMenu> t = null;
				tests = securityService.getMenuByUserId(userId);
				for (TreeMenu test : tests) {
					Tree<TreeMenu> tree = new Tree<TreeMenu>();
					tree.setId(test.getChildId());
					tree.setText(test.getChildName());
					tree.setParentId(test.getParentId());
					tree.setIcon(test.getIcon());
					if(!StringUtils.isEmpty(test.getMenuUrl())){
						tree.setUrl(test.getMenuUrl());
					}
					tree.setRootId(test.getRootId());
					if(!StingUtil.isEmpty(test.getMenuUrl())){
						tree.setTargetType("iframe-tab");
					}
					trees.add(tree);
				}
				if (tests.size() > 0) {
					t = BuildTree.build(trees);
				}
				res.setDataset(t, "menutree");
			}
		} catch (Exception e) { 
			e.printStackTrace();
			LogException.logEx(e,SecurityController.class,"sysbase.menutree");
		} 
		return res;
	}
	
	@ResponseBody
	@RequestMapping(value="/getTreeByStructId")	
	public ResponseBase getTreeByStructId(String structureId){  
		ResponseBase res = new ResponseBase();
		try {
			List<Tree<TreeMenu>> trees = new ArrayList<Tree<TreeMenu>>();
			List<TreeMenu> tests = new ArrayList<TreeMenu>();
			tests = securityService.getTreeByStructId(structureId);
			for (TreeMenu test : tests) {
				Tree<TreeMenu> tree = new Tree<TreeMenu>();
				tree.setId(test.getChildId());
				tree.setText(test.getChildName());
				tree.setParentId(test.getParentId());
				tree.setUrl(test.getMenuUrl());
				trees.add(tree);
			}
			Tree<TreeMenu> t = BuildTree.build(trees);
			res.setDataset(t, "trees");
		} catch (Exception e) { 
			e.printStackTrace();
			LogException.logEx(e,SecurityController.class,"sysbase.tree");
		} 
		return res;
	}
	
	/**
	 * 角色授权保存
	 * @param userId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="authorizeRole")
	public Map<String,Object> authorizeRole(String roleId, String funIds, String treeId) {
//		if (StringUtils.isEmpty(treeId)) {
//			return ;
//		}
		Role role = securityService.findRoleById(roleId);
		for (Permission permission : role.getPermissions()) {
			// add yxz 2009-11-25 ----只删除要保存treeId权限
			if (permission.getResource().getType().equals(treeId))
				permissionService.deletePermission(permission);
		}
		String[] funIdArray = funIds.split(Constants.SEPARATOR_COMMA);
		for (int i = 0; i < funIdArray.length; i++) {
			String funId = funIdArray[i];
			if (StringUtils.isEmpty(funId)) {
				continue;
			}
			Resources resource =  resourceService.findResourceByTargetIdAndType(
					funId, treeId);
			if (resource == null) {
				resource =  resourceService.createResource(funId, treeId);
			}
			Permission permission = permissionService
					.findRolePermByPartyIdAndResId(roleId, resource.getId());
			if (permission == null) {
				permission = permissionService.createRolePermission(role,
						resource);
			}
			permission.setParty(role);
			role.getPermissions().add(permission);
		}

		roleService.saveRole(role);
		return ResultUtil.DefaultResult();
	}
	
	/**
	 * 用户授权保存
	 * @param userId
	 * @param funIds
	 * @param treeId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="authorizeUser")
	public Map<String,Object> authorizeUser(String userId, String funIds, String treeId) {
		
		User user = userService.findUserById(userId);
		for (Permission permission : user.getPermissions()) {
			if (permission.getResource().getType().equals(treeId))
				permissionService.deletePermission(permission);
		}

		String[] funIdArray = funIds.split(Constants.SEPARATOR_COMMA);
		for (int i = 0; i < funIdArray.length; i++) {
			String funId = funIdArray[i];
			if (StringUtils.isEmpty(funId)) {
				continue;
			}

			/*
			 * Dic funDic = dicManager.getDicData(funId); if (funDic == null) {
			 * continue; }
			 */
			Resources resource = resourceService.findResourceByTargetIdAndType(
					funId, treeId);
			if (resource == null) {
				resource = resourceService.createResource(funId, treeId);
			}

			Permission permission = permissionService
					.findUserPermByPartyIdAndResId(userId, resource.getId());

			if (permission == null) {
				permission = permissionService.createUserPermission(user,
						resource);
			}
			permission.setParty(user);
			user.getPermissions().add(permission);
		}
		userService.saveUser(user);
		return ResultUtil.DefaultResult();
	}
	
	/**
	 * 得到操作权限
	 * @param userId
	 * @param url
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="getOperationByUrl")
	public ResponseBase getOperationByUrl(String url) {
		ResponseBase res = new ResponseBase();
		try {
			if(!StringUtils.isEmpty(url)){
				String jsonStr = securityService.getOperation(securityService.getDicIdByUrl(url));
				res.setDataset(jsonStr, "jsonStr");
			}
		} catch (Exception e) {
			e.printStackTrace();
			LogException.logEx(e,SecurityController.class,"sysbase.menutree");
		}
		return res;
	}
	
	/**
	 * 登录日志列表
	 * @param entity
	 * @return
	 */
	@RequestMapping("getLogList")
	@ResponseBody
	public Pagination getLogList(Log entity){
		PaginationSupport paginationSupport=securityService.getLogList(entity);
		return Pagination.init(paginationSupport);
	}
	
	/*@ResponseBody
	@RequestMapping(value="/getSilkWorkshopTree")	
	public ResponseBase getSilkWorkshopTree(String objStructureId){  
		ResponseBase res = new ResponseBase();
		
		try {
			List<Tree<TreeMenu>> trees = new ArrayList<Tree<TreeMenu>>();
			List<TreeMenu> tests = new ArrayList<TreeMenu>();
			tests = securityService.getSilkWorkshopTree(objStructureId);
			for (TreeMenu test : tests) {
				Tree<TreeMenu> tree = new Tree<TreeMenu>();
				tree.setId(test.getChildId());
				tree.setName(test.getChildName());
				tree.setParentId(test.getParentId());
				tree.setParentName(test.getParentName());
				tree.setMenuUrl(test.getMenuUrl());
				tree.setRootId(test.getRootId());
				trees.add(tree);
			}
			Tree<TreeMenu> t = BuildTree.build(trees);
			res.setDataset(t, "menutree");
		} catch (Exception e) { 
			e.printStackTrace();
			LogException.logEx(e,SecurityController.class,"sysbase.menutree");
		} 
		return res;
	}*/
}
