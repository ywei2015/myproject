/**
 * 
 */
package tw.sysbase.entity.sec;

import java.util.Set;

import tw.sysbase.entity.pub.Dic;
 

/**
 * @author 刘威
 * 
 */
public interface User extends Party {

	String getPassword();

	Set<Role> getRoles();

	void setPassword(String password);

	void setRoles(Set<Role> roles);

	void setCode(String code);

	void setJobno(String jobno);
	
	void setSex(Dic sex);
	
	void setOrganization(Dic organization);

	String getCode();
	
	String getJobno();

	Set<Dic> getPosts();

	Dic getOrganization();

	Dic getSex();
	
	void setTel(String tel);//20100318
	
	String getTel();//20100318
	
	void setMobile(String mobile);//20100318
	
	String getMobile();//20100318
	
	void setSecPassword(String secPassword); //20101201 --by wufan

	String getSecPassword();//20101201 --by wufan
	
	void setCardNO(String cardNO); //20120116 --by wufan

	String getCardNO();//20120116 --by wufan
	
	void setUserCode(String userCode);  

	String getUserCode();
}
