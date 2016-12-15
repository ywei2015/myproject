package sail.beans.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
 
//@Component
public class User  implements Serializable { 
	private String pid;
	private String code;
	private String name;
	private String pwd;
	private String sex;
	private String orgId;
	private String tel;
	private String mobile;
	private String jobNo;
	private String secPwd;
	private String cardNo;
	private String remark;
	private String sysFlag;
	private String creator;
	private String createTime;
	private String lastModifer;
	private String lastModifiedTime;
	private String postId;
	private String companyFactoryFlag;
	private String factoryId;
	private String companyTypeId;
	private String passwordValidity;
	
	private List<PermissionInfo> permissionList = new ArrayList<PermissionInfo>();
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getJobNo() {
		return jobNo;
	}
	public void setJobNo(String jobNo) {
		this.jobNo = jobNo;
	}
	public String getSecPwd() {
		return secPwd;
	}
	public void setSecPwd(String secPwd) {
		this.secPwd = secPwd;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getSysFlag() {
		return sysFlag;
	}
	public void setSysFlag(String sysFlag) {
		this.sysFlag = sysFlag;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getLastModifer() {
		return lastModifer;
	}
	public void setLastModifer(String lastModifer) {
		this.lastModifer = lastModifer;
	}
	public String getLastModifiedTime() {
		return lastModifiedTime;
	}
	public void setLastModifiedTime(String lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}
	public String getPostId() {
		return postId;
	}
	public void setPostId(String postId) {
		this.postId = postId;
	}
	public String getCompanyFactoryFlag() {
		return companyFactoryFlag;
	}
	public void setCompanyFactoryFlag(String companyFactoryFlag) {
		this.companyFactoryFlag = companyFactoryFlag;
	}
	public String getFactoryId() {
		return factoryId;
	}
	public void setFactoryId(String factoryId) {
		this.factoryId = factoryId;
	}
	public String getCompanyTypeId() {
		return companyTypeId;
	}
	public void setCompanyTypeId(String companyTypeId) {
		this.companyTypeId = companyTypeId;
	}
	public String getPasswordValidity() {
		return passwordValidity;
	}
	public void setPasswordValidity(String passwordValidity) {
		this.passwordValidity = passwordValidity;
	}
	public List<PermissionInfo> getPermissionList() {
		return permissionList;
	}
	public void setPermissionList(List<PermissionInfo> permissionList) {
		this.permissionList = permissionList;
	}
	
	

	
}
