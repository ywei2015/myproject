package tw.sysbase.entity.sec;

import java.util.Iterator;

import tw.business.entity.pub.BasicData;
import tw.sysbase.entity.pub.Dic;
import tw.sysbase.pub.Constants;

public class UserVo extends BasicData {

	private String id;
	private String name;
	private String code;
	private String jobno;
	private String sex;
	private String sexName;
	private String password;
	private String sysFlag;
	private String sysFlagName;
	private String remark;
	private String createTime;
	private String lastModifiedTime;

	private String creatorId;
	private String creatorName;
	private String lastModifierId;
	private String lastModifierName;
	private String rolesId;
	private String rolesName;
	private String postsId;
	

	private String postsName;
	private String orgId;
	private String orgName;
	
	private String tel;//20100318
	private String mobile;//20100318
	private String cardNO;//20120116

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public UserVo() {

	}

	public UserVo(User user) {
		copy(user);
	}

	public void copy(User user) {
		id = user.getId();
		name = user.getName();
		code = user.getCode();
		jobno = user.getJobno();
		tel=user.getTel();
		mobile=user.getMobile();
		sysFlag = user.getSysFlag();
		sysFlagName = ("1".equals(sysFlag)) ? "有效" : "无效";

		Dic sexDic = user.getSex();
		if (sexDic != null) {
			sex = sexDic.getId();
			sexName = sexDic.getName();
		}
		remark = user.getRemark();
		cardNO = user.getCardNO();
		createTime = user.getCreateTime();
		lastModifiedTime = user.getLastModifiedTime();

		User creator = user.getCreator();
		if (creator != null) {
			creatorId = creator.getId();
			creatorName = creator.getName();
		}
		User lastModified = user.getLastModifier();
		if (lastModified != null) {
			lastModifierId = lastModified.getId();
			lastModifierName = lastModified.getName();
		}
		Dic org = user.getOrganization();
		if (org != null) {
			orgId = org.getId();
			orgName = org.getName();
		}
		StringBuffer idBuffer = new StringBuffer();
		StringBuffer nameBuffer = new StringBuffer();
		for (Iterator it = user.getRoles().iterator(); it.hasNext();) {
			Role role = (Role) it.next();
			idBuffer.append(role.getId());
			nameBuffer.append(role.getName());
			if (it.hasNext()) {
				idBuffer.append(Constants.SEPARATOR_COMMA);
				nameBuffer.append(Constants.SEPARATOR_COMMA);
			}
		}
		if (idBuffer.length() == 0) {
			rolesId = " ";
		} else {
			rolesId = idBuffer.toString();
		}

		if (nameBuffer.length() == 0) {
			rolesName = " ";
		} else {
			rolesName = nameBuffer.toString();
		}
		idBuffer.delete(0, idBuffer.length());
		nameBuffer.delete(0, nameBuffer.length());
		for (Iterator it = user.getPosts().iterator(); it.hasNext();) {
			Dic post = (Dic) it.next();
			idBuffer.append(post.getId());
			nameBuffer.append(post.getName());
			if (it.hasNext()) {
				idBuffer.append(Constants.SEPARATOR_COMMA);
				nameBuffer.append(Constants.SEPARATOR_COMMA);
			}
		}
		if (idBuffer.length() == 0) {
			postsId = " ";
		} else {
			postsId = idBuffer.toString();
		}

		if (nameBuffer.length() == 0) {
			postsName = " ";
		} else {
			postsName = nameBuffer.toString();
		}
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getJobno() {
		return jobno;
	}

	public void setJobno(String jobno) {
		this.jobno = jobno;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getSexName() {
		return sexName;
	}

	public void setSexName(String sexName) {
		this.sexName = sexName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getLastModifiedTime() {
		return lastModifiedTime;
	}

	public void setLastModifiedTime(String lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
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

	public String getPostsId() {
		return postsId;
	}

	public void setPostsId(String postsId) {
		this.postsId = postsId;
	}

	public String getPostsName() {
		return postsName;
	}

	public void setPostsName(String postsName) {
		this.postsName = postsName;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public void setCardNO(String cardNO) {
		this.cardNO = cardNO;
	}

	public String getCardNO() {
		return cardNO;
	}

}
