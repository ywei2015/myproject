package tw.sysbase.entity.sec;

import java.util.HashSet;
import java.util.Set;

import tw.sysbase.entity.pub.Dic;
import tw.sysbase.entity.sec.Permission;
import tw.sysbase.entity.sec.Role;
import tw.sysbase.entity.sec.User;

/**
 * @author 刘威
 * 
 */
public class UserImpl implements User {

	private String id;
	private String name;
	private String code;
	private String jobno;
	private Dic sex;
	private String password;
	private String sysFlag;
	private String remark;
	private User creator;
	private String createTime;
	private User lastModifier;
	private String lastModifiedTime;
	
	private String tel;//20100318
	private String mobile;//20100318
	private String secPassword;//20101201 --by wufan
	private String cardNO;//20120116 --by wufan
	private String userCode;//中烟信息系统人员编码
	
	
	

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getJobno() {
		return jobno;
	}

	public void setJobno(String jobno) {
		this.jobno = jobno;
	}

	private Set<Dic> posts= new HashSet<Dic>(0);
	private Dic organization;
	private Set<Role> roles = new HashSet<Role>(0);
	private Set<Permission> permissions = new HashSet<Permission>(0);

	public UserImpl() {

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

	public Dic getSex() {
		return sex;
	}

	public void setSex(Dic sex) {
		this.sex = sex;
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

	public Set<Dic> getPosts() {
		return posts;
	}

	public void setPosts(Set<Dic> posts) {
		this.posts = posts;
	}

	public Dic getOrganization() {
		return organization;
	}

	public void setOrganization(Dic organization) {
		this.organization = organization;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Set<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
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

	public void setSecPassword(String secPassword) {
		this.secPassword = secPassword;
	}

	public String getSecPassword() {
		return secPassword;
	}

	public String getCardNO() {
		return cardNO;
	}

	public void setCardNO(String cardNO) {
		this.cardNO = cardNO;
	}

}
