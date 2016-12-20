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
	private String orgid;
	private String tel;
	private String mobile;
	private String jobno;
	private String secpwd;
	private String cardno;
	private String remark;
	private String sysflag;
	private String creator;
	private String createtime;
	private String lastmodifer;
	private String lastmodifiedtime;
	private String postid;
	private String companyfactoryflag;
	private String factoryid;
	private String companytypeid;
	private String passwordvalidity;
	
	private List<PermissionInfo> permissionlist = new ArrayList<PermissionInfo>();

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

	public String getOrgid() {
		return orgid;
	}

	public void setOrgid(String orgid) {
		this.orgid = orgid;
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

	public String getJobno() {
		return jobno;
	}

	public void setJobno(String jobno) {
		this.jobno = jobno;
	}

	public String getSecpwd() {
		return secpwd;
	}

	public void setSecpwd(String secpwd) {
		this.secpwd = secpwd;
	}

	public String getCardno() {
		return cardno;
	}

	public void setCardno(String cardno) {
		this.cardno = cardno;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSysflag() {
		return sysflag;
	}

	public void setSysflag(String sysflag) {
		this.sysflag = sysflag;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	

	public String getLastmodifer() {
		return lastmodifer;
	}

	public void setLastmodifer(String lastmodifer) {
		this.lastmodifer = lastmodifer;
	}

	public String getLastmodifiedtime() {
		return lastmodifiedtime;
	}

	public void setLastmodifiedtime(String lastmodifiedtime) {
		this.lastmodifiedtime = lastmodifiedtime;
	}

	public String getPostid() {
		return postid;
	}

	public void setPostid(String postid) {
		this.postid = postid;
	}

	public String getCompanyfactoryflag() {
		return companyfactoryflag;
	}

	public void setCompanyfactoryflag(String companyfactoryflag) {
		this.companyfactoryflag = companyfactoryflag;
	}

	public String getFactoryid() {
		return factoryid;
	}

	public void setFactoryid(String factoryid) {
		this.factoryid = factoryid;
	}

	public String getCompanytypeid() {
		return companytypeid;
	}

	public void setCompanytypeid(String companytypeid) {
		this.companytypeid = companytypeid;
	}

	public String getPasswordvalidity() {
		return passwordvalidity;
	}

	public void setPasswordvalidity(String passwordvalidity) {
		this.passwordvalidity = passwordvalidity;
	}

	public List<PermissionInfo> getPermissionlist() {
		return permissionlist;
	}

	public void setPermissionlist(List<PermissionInfo> permissionlist) {
		this.permissionlist = permissionlist;
	}

	
	
	
	
	
	

	
}
