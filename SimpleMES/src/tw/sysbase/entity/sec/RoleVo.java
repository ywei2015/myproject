package tw.sysbase.entity.sec;

import java.util.Iterator;

import tw.business.entity.pub.BasicData;
import tw.sysbase.pub.Constants;

public class RoleVo extends BasicData {

	private String id;
	private String name;
	private String sysFlag;
	private String sysFlagName;
	private String remark;
	private String creatorId;
	private String creatorName;

	private String createTime;
	private String lastModifierId;
	private String lastModifierName;
	private String lastModifiedTime;
	
	private String usersId;
	private String usersName;
	
	

	private String rolesId;
	private String rolesName;
	

	public RoleVo() {

	}

	public RoleVo(Role role) {
		id = role.getId();
		name = role.getName();

		sysFlag = role.getSysFlag();
		sysFlagName = ("1".equals(sysFlag)) ? "有效" : "无效";

		remark = role.getRemark();
		User creator = role.getCreator();
		if (creator != null) {
			creatorId = creator.getId();
			creatorName = creator.getName();
		}
		User lastModifier = role.getLastModifier();
		if (lastModifier != null) {
			lastModifierId = lastModifier.getId();
			lastModifierName = lastModifier.getName();
		}
		createTime = role.getCreateTime();
		lastModifiedTime = role.getLastModifiedTime();
		//add by yxz 2009-11-30
		StringBuffer idBuffer = new StringBuffer();
		StringBuffer nameBuffer = new StringBuffer();
		for (Iterator it = role.getUsers().iterator(); it.hasNext();) {
			User user = (User) it.next();
			idBuffer.append(user.getId());
			nameBuffer.append(user.getName());
			if (it.hasNext()) {
				idBuffer.append(Constants.SEPARATOR_COMMA);
				nameBuffer.append(Constants.SEPARATOR_COMMA);
			}
		}
		if (idBuffer.length() == 0) {
			usersId = " ";
		} else {
			usersId = idBuffer.toString();
		}

		if (nameBuffer.length() == 0) {
			usersName = " ";
		} else {
			usersName = nameBuffer.toString();
		}
		//end
		
		StringBuffer roleIdBuffer = new StringBuffer();
		StringBuffer rolenameBuffer = new StringBuffer();
		for (Iterator it = role.getRoles().iterator(); it.hasNext();) {
			Role r = (Role) it.next();
			roleIdBuffer.append(r.getId());
			rolenameBuffer.append(r.getName());
			if (it.hasNext()) {
				roleIdBuffer.append(Constants.SEPARATOR_COMMA);
				rolenameBuffer.append(Constants.SEPARATOR_COMMA);
			}
		}
		if (roleIdBuffer.length() == 0) {
			rolesId = " ";
		} else {
			rolesId = roleIdBuffer.toString();
		}

		if (rolenameBuffer.length() == 0) {
			rolesName = " ";
		} else {
			rolesName = rolenameBuffer.toString();
		}
	}

	public String getUsersId() {
		return usersId;
	}

	public void setUsersId(String usersId) {
		this.usersId = usersId;
	}

	public String getUsersName() {
		return usersName;
	}

	public void setUsersName(String usersName) {
		this.usersName = usersName;
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

	public String getSysFlagName() {
		return sysFlagName;
	}

	public void setSysFlagName(String sysFlagName) {
		this.sysFlagName = sysFlagName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getLastModifierId() {
		return lastModifierId;
	}

	public void setLastModifierId(String lastModifierId) {
		this.lastModifierId = lastModifierId;
	}

	public String getLastModifierName() {
		return lastModifierName;
	}

	public void setLastModifierName(String lastModifierName) {
		this.lastModifierName = lastModifierName;
	}

	public String getLastModifiedTime() {
		return lastModifiedTime;
	}

	public void setLastModifiedTime(String lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}
	
	public String getRolesId() {
		return rolesId;
	}

	public void setRolesId(String rolesId) {
		this.rolesId = rolesId;
	}

	public String getRolesName() {
		return rolesName;
	}

	public void setRolesName(String rolesName) {
		this.rolesName = rolesName;
	}

}
