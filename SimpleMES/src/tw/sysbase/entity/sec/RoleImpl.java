package tw.sysbase.entity.sec;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import tw.sysbase.entity.sec.Permission;
import tw.sysbase.entity.sec.Role;
import tw.sysbase.entity.sec.User;

public class RoleImpl implements Role {

	private String id;
	private String name;
	private String sysFlag;
	private String remark;
	private User creator;
	private String createTime;
	private User lastModifier;
	private String lastModifiedTime;
	
	private Set<User> users = new HashSet<User>(0);
	private Set<Permission> permissions = new HashSet<Permission>(0);
	private Role parent;
	private List<Role> children;
	
	private Set<Role> roles = new HashSet<Role>(0);
	
	private Set<Role> containRoles = new HashSet<Role>(0);

	public RoleImpl() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSysFlag() {
		return sysFlag;
	}

	public void setSysFlag(String sysFlag) {
		this.sysFlag = sysFlag;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public Set<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}

	public Role getParent() {
		return parent;
	}

	public void setParent(Role parent) {
		this.parent = parent;
	}

	public List<Role> getChildren() {
		return children;
	}

	public void setChildren(List<Role> children) {
		this.children = children;
	}

	public User getLastModifier() {
		return lastModifier;
	}

	public void setLastModifier(User lastModifier) {
		this.lastModifier = lastModifier;
	}

	public String getLastModifiedTime() {
		return lastModifiedTime;
	}

	public void setLastModifiedTime(String lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Set<Role> getContainRoles() {
		return containRoles;
	}

	public void setContainRoles(Set<Role> containRoles) {
		this.containRoles = containRoles;
	}

}
