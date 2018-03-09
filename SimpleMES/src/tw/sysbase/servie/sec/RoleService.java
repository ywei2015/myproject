/**
 * 
 */
package tw.sysbase.servie.sec;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.sysbase.dao.GenericDao;
import tw.sysbase.dao.PaginationSupport;
import tw.sysbase.entity.sec.Role;
import tw.sysbase.entity.sec.RoleImpl;
import tw.sysbase.entity.sec.RoleVo;
import tw.sysbase.entity.sec.User;
import tw.sysbase.entity.sec.UserImpl;
import tw.sysbase.pub.Constants;
import tw.sysbase.pub.DateBean;
import tw.sysbase.pub.ResponseBase;

/**
 * @author 刘威
 * 
 */
@Service
@Transactional
public class RoleService {

	@Resource
	private GenericDao genericDao;
	/**
	 * 创建角色
	 * 
	 * @param role
	 *            Role:角色
	 * @return 角色持久对象
	 */
	public Role saveRole(Role role) {
		if (role == null) {
			throw new RuntimeException("保存角色时,角色对象为空!");
		}

		String roleName = role.getName();
		if (StringUtils.isBlank(roleName)) {
			throw new RuntimeException("保存角色时,角色名称不能为空!");
		}
//		Role existRole = findRoleByName(roleName);
//		if (existRole != null
//				&& existRole.getId().equals(role.getId()) == false) {
//			throw new RuntimeException("保存角色时,角色名称:'" + roleName + "'重复!");
//		}
		genericDao.save(role);
		return role;
	}
	
	public boolean findRoleName(RoleVo roleVo) {
		Object[] parameter = {roleVo.getName()};
		List<Role> roles = genericDao.getList("SECURITY.ROLE.FIND_ROLE_NAME",parameter);
		if(roles!=null && !roles.isEmpty()) {
			if(roles.size()==1) {
				Role crole = roles.iterator().next();
				if(roleVo.getId().equals(crole.getId())) {
					return true;
				}
				return false;
			}
			return false;
		}else {
			return true;
		}
	}
	
	
	public ResponseBase findRole(String roleId) {
		ResponseBase res = new ResponseBase();
		if (StringUtils.isBlank(roleId)) {
			throw new RuntimeException("id:传入的参数为空!");
		}
		Role role = this.findRoleById(roleId);
		RoleVo roleVo = new RoleVo(role);
		res.setDataset(roleVo, "rolevo");
		return res;
	}

	/**
	 * 得到所有角色
	 * 
	 * @return 角色持久对象
	 */
	public Role findRoleById(String roleId) {
		if (StringUtils.isBlank(roleId)) {
			throw new RuntimeException("查找角色时,角色编号为空!");
		}
		return (Role) genericDao.getById(RoleImpl.class, roleId);
	}
	
	public User findUserById(String userId) {
		//System.out.println("aaaaaaaaaaaaaaaaaa:" + UserImpl.class);
		//System.out.println("bbbbbbbbbbbbbbbbbb:" + userId);
		return (User) genericDao.getById(UserImpl.class, userId);
	}
	
	public Role findRoleByName(String name) {
		Object[] parameter = { name, Constants.SYS_FLAG_USEING , Constants.SYS_FLAG_DELETED};
		List<Role> roles = genericDao.getList(
				"SECURITY.ROLE.FIND_ROLE_BY_NAME", parameter);
		if (roles.isEmpty()) {
			return null;
		}
		if (roles.size() >= 2) {
			throw new RuntimeException("角色名称有重复记录!");
		}
		return roles.iterator().next();
	}

	public void deleteRole(String roleId) {
		if (StringUtils.isBlank(roleId)) {
			throw new RuntimeException("删除角色时,角色编号为空!");
		}
		Role role = findRoleById(roleId);
		role.getUsers().clear();
		role.getRoles().clear();
		role.getContainRoles().clear();
		role.setSysFlag(Constants.SYS_FLAG_OVER);//add by yxz 2009-12-8  --SysFlag为2时，表示删除
		String lastModifiedTime = DateBean.getSysdateTime();
		role.setLastModifiedTime(lastModifiedTime);
		saveRole(role);
	}
	
	/**
	 * 
	 * @param rolevo
	 * @return
	 */
	public PaginationSupport findRoleListPagination(RoleVo rolevo) {
//		String code=dic.getCode();
//		Integer sysFlag=dic.getSysFlag();
//		code = (StringUtils.isBlank(code) ? code : "%" + code + "%");
//		name = (StringUtils.isBlank(name) ? name : "%" + name + "%");
//		String pid= dic.getId();
//		Object[] paras ={code,name,sysFlag,pid};
//		//genericDao.getPageWithVariableParas(queryName, value)
//		PaginationSupport paginationSupport = genericDao.getPageWithVariableParas("OBJBASE.FINDLISTPAGINATION.GET_BYPID", paras,dic);
//		return paginationSupport;
		
		
		String name = rolevo.getName();
		String sysFalg = rolevo.getSysFlag();
		name = (StringUtils.isBlank(name) ? name : "%" + name + "%");
		Object[] parameter = { name, sysFalg };
		PaginationSupport resultPagination=null;
		if("2".equals(sysFalg)==false){
			resultPagination = genericDao.getPageWithParams(
					"SECURITY.ROLE.FIND_ROLE_BY_NAME_SYSFLAG", parameter,
					rolevo);
		}else{
			resultPagination = genericDao.getPageWithParams(
					"SECURITY.ROLE.FIND_ROLE_BY_NAME_SYSFLAGNO", parameter,
					rolevo);
		}

		List<RoleVo> result = new ArrayList<RoleVo>();
		for (Iterator iterator = resultPagination.getItems().iterator(); iterator
				.hasNext();) {
			Role role = (Role) iterator.next();
			RoleVo roleVo = new RoleVo(role);
			result.add(roleVo);
		}
		resultPagination.setItems(result);
		return resultPagination;
	}

}
