/**
 * 
 */
package tw.sysbase.entity.sec;

import java.util.Set;

/**
 * @author 刘威
 * 
 */
public interface Party {

	String getId();

	String getName();

	Set<Permission> getPermissions();

	String getSysFlag();

	String getRemark();

	User getCreator();

	String getCreateTime();

	User getLastModifier();
	
	String getLastModifiedTime();



	void setId(String id);

	void setName(String name);

	void setPermissions(Set<Permission> permissions);

	void setSysFlag(String sysFlag);

	void setRemark(String remark);

	void setCreator(User creator);

	void setCreateTime(String createTime);

	void setLastModifier(User lastModifier);

	void setLastModifiedTime(String lastModifiedTime);

}
