package tw.sysbase.servie.sec;

import java.util.List;

import javax.annotation.Resource;

import tw.sysbase.entity.sec.Resources;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.sysbase.entity.sec.Party;
import tw.sysbase.entity.sec.Permission;
import tw.sysbase.entity.sec.PermissionType;
import tw.sysbase.entity.sec.RolePermissionImpl;
import tw.sysbase.entity.sec.UserPermissionImpl;
import tw.sysbase.dao.GenericDao;

/**
 * @author 刘威
 * 
 */
@Service
@Transactional
public class PermissionService {

	// ****************************************************
	//
	// 基本API
	//
	// ****************************************************
	@Resource
	private GenericDao genericDao;
	private Permission savePermission(Permission permission) {
		if (permission==null){
			throw new RuntimeException("permissionManager.savePermission方法中permission对象为空!");
		}
		genericDao.save(permission);
		return permission;
	}

	/**
	 * 创建默认许可证(PermissionType.PERMISSION_GENERAL)
	 * 
	 * @param party
	 *            Party:团体对象
	 * @param resource
	 *            Resource:资源对象
	 * @return 许可证持久对象
	 */
	public Permission createUserPermission(Party party, Resources resource) {
		return createUserPermission(party,resource,PermissionType.PERMISSION_GENERAL);
	}

	/**
	 * 创建可选许可证
	 * 
	 * @param party
	 *            Party:团体对象
	 * @param resource
	 *            Resource:资源对象
	 * @param type
	 *            long:许可证类型(PermissionType.PERMISSION_GENERAL,PermissionType.PERMISSION_MANAGERMENT)
	 * @return 许可证持久对象
	 */
	public Permission createUserPermission(Party party, Resources resource,long type) {
		if (party==null){
			throw new RuntimeException("permissionManager.createUserPermission方法中party对象为空!");
		}
		if (resource==null){
			throw new RuntimeException("permissionManager.createUserPermission方法中resource对象为空!");
		}
		Permission permission = findUserPermByPartyIdAndResId(party.getId(), resource.getId());
		if (permission == null) {
			permission = new UserPermissionImpl(party, resource, type);
		}
		savePermission(permission);
		return permission;
	}
	
	/**
	 * 查找许可证
	 * 
	 * @param permId
	 *            String:团体对象
	 * @param resource
	 *            Resource:资源对象
	 * @param type
	 *            long:许可证类型(PermissionType.PERMISSION_GENERAL,PermissionType.PERMISSION_MANAGERMENT)
	 * @return 许可证持久对象
	 */
	public Permission findUserPermissionById(String permId) {
		if (StringUtils.isBlank(permId)){
			throw new RuntimeException("permissionManager.findUserPermissionById方法中permId为空!");
		}
		Permission permission = (Permission) genericDao.getById(UserPermissionImpl.class, permId);
		return permission;
	}

	public List<Permission> findUserPermByPartyId(String partyId,String type) {
		if (StringUtils.isBlank(partyId)){
			throw new RuntimeException("permissionManager.findRolePermByPartyId方法中partyId为空!");
		}
		if (StringUtils.isBlank(type)){
			return findUserPermByPartyId(partyId);
		}
		String[] parameter = { partyId, type };
		List<Permission> Permission = genericDao.getList(
				"SECURITY.PERMISSION.FIND_USERPERM_BY_PARTYID_AND_TYPE", parameter);
		return Permission;
	}
	
	/**
	 * 查找许可证
	 * 
	 * @param partyId
	 *            String:团体对象
	 * @return 许可证持久对象列表
	 */	
	public List<Permission> findUserPermByPartyId(String partyId) {
		if (StringUtils.isBlank(partyId)){
			throw new RuntimeException("permissionManager.findUserPermByPartyId方法中partyId为空!");
		}
		List<Permission> Permission = genericDao.getList(
				"SECURITY.PERMISSION.FIND_USERPERMISSION_BY_PARTYID", partyId);
		return Permission;
	}

	public List<Permission> findUserPermByResId(String resourceId) {
		if (StringUtils.isBlank(resourceId)){
			throw new RuntimeException("permissionManager.findUserPermByPartyId方法中resourceId为空!");
		}
		List<Permission> Permission = genericDao.getList(
				"SECURITY.PERMISSION.FIND_USERPERMISSION_BY_RESID", resourceId);
		return Permission;
	}

	public Permission findUserPermByPartyIdAndResId(String partyId,
			String resourceId) {
		if (StringUtils.isBlank(partyId)){
			throw new RuntimeException("permissionManager.findUserPermByPartyIdAndResId方法中partyId为空!");
		}
		if (StringUtils.isBlank(resourceId)){
			throw new RuntimeException("permissionManager.findUserPermByPartyIdAndResId方法中resourceId为空!");
		}	
		String[] parameter = { partyId, resourceId };
		List<Permission> permissions = genericDao.getList(
				"SECURITY.PERMISSION.FIND_USERPERM_BY_PARTYID_AND_RESID",
				parameter);
		if (permissions.isEmpty()){
			return null;
		}				
		if (permissions.size()>=2){
			throw new RuntimeException("同一个团体对资源不允许有重复许可!");
		}
		return permissions.iterator().next();
	}

	/*
	 * 以下是RolePermission
	 */
	
	public Permission createRolePermission(Party party, Resources resource) {
		return createRolePermission(party,resource,PermissionType.PERMISSION_GENERAL);
	}
	
	
	
	public Permission createRolePermission(Party party, Resources resource,
			long type) {
		if (party==null){
			throw new RuntimeException("permissionManager.createUserPermission方法中party对象为空!");
		}
		if (resource==null){
			throw new RuntimeException("permissionManager.createUserPermission方法中resource对象为空!");
		}		
		Permission permission = findRolePermByPartyIdAndResId(party.getId(), resource.getId());
		if (permission == null) {
			permission = new RolePermissionImpl(party, resource,type);
		}
		savePermission(permission);
		return permission;
	}
	
	public Permission findRolePermissionById(String permId) {
		if (StringUtils.isBlank(permId)){
			throw new RuntimeException("permissionManager.findUserPermissionById方法中permId为空!");
		}
		Permission permission = (Permission) genericDao.getById(RolePermissionImpl.class, permId);
		return permission;
	}

	public List<Permission> findRolePermByPartyId(String partyId) {
		if (StringUtils.isBlank(partyId)){
			throw new RuntimeException("permissionManager.findRolePermByPartyId方法中partyId为空!");
		}
		List<Permission> Permission = genericDao.getList(
				"SECURITY.PERMISSION.FIND_ROLEPERM_BY_PARTYID", partyId);
		return Permission;
	}

	public List<Permission> findRolePermByPartyId(String partyId,String type) {
		if (StringUtils.isBlank(partyId)){
			throw new RuntimeException("permissionManager.findRolePermByPartyId方法中partyId为空!");
		}
		if (StringUtils.isBlank(type)){
			return findRolePermByPartyId(partyId);
		}
		String[] parameter = { partyId, type };
		List<Permission> Permission = genericDao.getList(
				"SECURITY.PERMISSION.FIND_ROLEPERM_BY_PARTYID_AND_TYPE", parameter);
		return Permission;
	}
	
	public List<Permission> findRolePermByResId(String resourceId) {
		if (StringUtils.isBlank(resourceId)){
			throw new RuntimeException("permissionManager.findRolePermByPartyId方法中resourceId为空!");
		}
		List<Permission> Permission = genericDao.getList(
				"SECURITY.PERMISSION.FIND_ROLEPERM_BY_RESID", resourceId);
		return Permission;
	}

	public Permission findRolePermByPartyIdAndResId(String partyId,
			String resourceId) {
		if (StringUtils.isBlank(partyId)){
			throw new RuntimeException("permissionManager.findRolePermByPartyIdAndResId方法中partyId为空!");
		}
		if (StringUtils.isBlank(resourceId)){
			throw new RuntimeException("permissionManager.findRolePermByPartyIdAndResId方法中resourceId为空!");
		}	
		String[] parameter = { partyId, resourceId };
		List<Permission> permissions = genericDao.getList(
				"SECURITY.PERMISSION.FIND_ROLEPERM_BY_PARTYID_AND_RESID",
				parameter);
		if (permissions.isEmpty()){
			return null;
		}			
		if (permissions.size()>=2){
			throw new RuntimeException("同一个团体对资源不允许有重复许可!");
		}
		return permissions.iterator().next();
	}
	
	public void deletePermission(Permission permission) {
		genericDao.delete(permission);
	}

}
