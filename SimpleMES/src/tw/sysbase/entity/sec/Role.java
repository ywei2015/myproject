/**
 * 
 */
package tw.sysbase.entity.sec;

import java.util.List;
import java.util.Set;

/**
 * @author 刘威
 * 
 */
public interface Role extends Party {

	Set<User> getUsers();

	void setUsers(Set<User> users);
	
	Set<Role> getRoles();

	void setRoles(Set<Role> roles);
	
	Set<Role> getContainRoles();

	void setContainRoles(Set<Role> containRoles);

	Role getParent();

	void setParent(Role Parent);

	List<Role> getChildren();

	void setChildren(List<Role> children);
}
