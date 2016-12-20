package sail.beans.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sail.beans.dao.GenericDao;
import sail.beans.entity.BatPdaLoginLog;
import sail.beans.entity.PermissionInfo;
import sail.beans.entity.User;
import sail.beans.support.DateBean;

@Service
public class UserService {

	@Autowired
	private GenericDao genericDao;  
	
	/**
	 * 用户登录信息
	 * @param userCode
	 * @param userPwd
	 * @return
	 */
	public User getUser(String userCode,String userPwd){
		User user = null;
		List<User> userList = genericDao.getListWithVariableParas("USER.USERLIST.LIST", new Object[]{userCode,userPwd});
		if (userList != null && userList.size() > 0){
			user = userList.get(0);
			user.setPermissionlist(this.getPermissionInfo(userCode));
		}
		return user;
	}
	
	/**
	 * 记录用户日志记录
	 * @param userCode
	 * @param sessionId
	 */
	public void saveUserLog(BatPdaLoginLog batPdaLoginLog){
		genericDao.save(batPdaLoginLog);
		
	}
	
	/**
	 * 获取用户权限数据
	 * @param userCode
	 * @return
	 */
	public List<PermissionInfo> getPermissionInfo(String userCode){
		List<PermissionInfo> permissionList = genericDao.getListWithVariableParas("USER.PERMISSIONINFOLIST.LIST", new Object[]{userCode});
		return permissionList;
	}
	
	
}
