package tw.sysbase.servie.sec;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.sysbase.dao.GenericDao;
import tw.sysbase.dao.PaginationSupport;
import tw.sysbase.entity.sec.Role;
import tw.sysbase.entity.sec.RoleVo;
import tw.sysbase.entity.sec.User;
import tw.sysbase.entity.sec.UserImpl;
import tw.sysbase.entity.sec.UserVo;
import tw.sysbase.pub.Constants;
import tw.sysbase.pub.DateBean;
import tw.sysbase.pub.QueryCorrelation;
import tw.sysbase.pub.ResponseBase;
import tw.sysbase.pub.Utility;
import tw.sysbase.servie.pub.DicService;

/**
 * @author cyj
 * 
 */
@Service
@Transactional
public class UserService {
	@Resource
	private GenericDao genericDao;
	@Autowired
	private DicService dicService;
	
	private static final String USER_HQL = "SELECT P FROM tw.sysbase.entity.sec.UserImpl P";
	/**
	 * 获取用户列表
	 * @param name
	 * @param code
	 * @param qryRelation
	 * @return
	 */
	public PaginationSupport findUserList(UserVo userVo) {
		StringBuffer sb = new StringBuffer(USER_HQL);
		sb.append(" WHERE 1=1");
		if (StringUtils.isBlank(userVo.getName()) == false) {
			sb.append(" AND P.name like " + "'%" + userVo.getName() + "%'");
		}
		if (StringUtils.isBlank(userVo.getCode()) == false) {
			sb.append(" AND P.code like " + "'%" + userVo.getCode() + "%'");
		}
		if (StringUtils.isBlank(userVo.getJobno()) == false) {
			sb.append(" AND P.organization.id = '"+ userVo.getJobno() +"'");
		}
		sb.append(" order by P.name");
		Object[] parameter = { userVo.getName(), userVo.getCode() };
//		PaginationSupport resultPagination = genericDao
//				.getPageWithVariableParasByHql(sb.toString(), null, qryRelation);
		PaginationSupport resultPagination = genericDao.getPageWithParasByHql(
				sb.toString(), null,
				userVo);
		
		List<UserVo> result = new ArrayList<UserVo>();
		for (Iterator iterator = resultPagination.getItems().iterator(); iterator
				.hasNext();) {
			User user = (User) iterator.next();
			UserVo userVo1 = new UserVo(user);
			result.add(userVo1);
		}
		resultPagination.setItems(result);
		return resultPagination;
	}
	
	/**
	 * 获取有效用户列表
	 * @param name
	 * @param code
	 * @param qryRelation
	 * @return
	 */
	public PaginationSupport findUserListByTrue(UserVo userVo) {
		StringBuffer sb = new StringBuffer(USER_HQL);
		sb.append(" WHERE 1=1 AND P.sysFlag='1'");
		if (StringUtils.isBlank(userVo.getName()) == false) {
			sb.append(" AND P.name like " + "'%" + userVo.getName() + "%'");
		}
		if (StringUtils.isBlank(userVo.getCode()) == false) {
			sb.append(" AND P.code like " + "'%" + userVo.getCode() + "%'");
		}
		if (StringUtils.isBlank(userVo.getOrgId()) == false) {
			sb.append(" AND P.organization.id = '"+ userVo.getOrgId() +"'");
		}
		sb.append(" order by P.name");
		Object[] parameter = { userVo.getName(), userVo.getCode() };
//		PaginationSupport resultPagination = genericDao
//				.getPageWithVariableParasByHql(sb.toString(), null, qryRelation);
		PaginationSupport resultPagination = genericDao.getPageWithParasByHql(
				sb.toString(), null,
				userVo);
		
		List<UserVo> result = new ArrayList<UserVo>();
		for (Iterator iterator = resultPagination.getItems().iterator(); iterator
				.hasNext();) {
			User user = (User) iterator.next();
			UserVo userVo1 = new UserVo(user);
			result.add(userVo1);
		}
		resultPagination.setItems(result);
		return resultPagination;
	}
	
	/**
	 * 获取用户编辑界面数据
	 * @param userId
	 * @return
	 */
	public UserVo findUser(String userId) {
		ResponseBase res = new ResponseBase();
		if (StringUtils.isBlank(userId)) {
			throw new RuntimeException("id:传入的参数为空!");
		}
		User user = this.findUserById(userId);
		UserVo userVo = new UserVo(user);
		//res.setDataset(userVo, "user");
		return userVo;
	}
	
	/**
	 * 创建用户
	 * edit by xzq 20101115 　不同用户，用户名(code)不同，工号也不同(jobno);同一用户的用户名(code)和工号(jobno)可以相同
	 * @param user
	 *            User:用户
	 * @return 用户持久对象
	 */
	public User saveUser(User user) {
		if (user == null) {
			throw new RuntimeException("保存用户时,用户对象不能为空!");
		}
		String userCode = user.getCode();
		String userJobno = user.getJobno();
		if (StringUtils.isBlank(userCode)) {
			throw new RuntimeException("保存用户时,用户名不能为空!");
		}
		if (StringUtils.isBlank(userJobno)) {
			throw new RuntimeException("保存用户时,用户工号不能为空!");
		}
		
		User existUserCode =  findUserByCode(userCode);
		User existUserJobno = findUserByCode(userJobno);		
		if (existUserCode != null
				&& existUserCode.getId().equals(user.getId()) == false) {
			throw new RuntimeException("保存用户时,用户名为:'" + user.getCode()
					+ "'用户已存在!");
		}
		if (existUserJobno != null
				&& existUserJobno.getId().equals(user.getId()) == false) {
			throw new RuntimeException("保存用户时,工号为:'" + user.getJobno()
					+ "'用户已存在!");
		}
		genericDao.save(user);
		return user;
	}

	public List<User> getAllUser() {
		Object[] parameter = {Constants.SYS_FLAG_USEING };
		List<User> users = genericDao.getList(
				"SECURITY.USER.FIND_USER_BY_SYSFLAG", parameter);
		return users;
	}

	/**
	 * 查找用户通过用户ID
	 * 
	 * @param userId
	 *            String:用户ID
	 * 
	 * @return 用户持久对象
	 */
	public User findUserById(String userId) {
		Utility.raiseIfWrong(userId, "userManager.findUserById方法中userId为空!");
		
		return (User) genericDao.getById(UserImpl.class, userId);
	}

	/**
	 * 查找用户通过用户名或工号(code)
	 * edit by xzq 20101115
	 * @param userId
	 *            String:用户名(code)
	 * 
	 * @return 用户持久对象
	 */
	public User findUserByCode(String userCode) {
		Utility.raiseIfWrong(userCode, "userManager.findUserById方法中userCode为空!");
		String hqlstr = "SELECT P FROM tw.sysbase.entity.sec.UserImpl P WHERE ( P.code = '"+userCode+"' or P.jobno = '"+userCode+"' or P.cardNO = '"+userCode+"' ) AND P.sysFlag = '1'";
		List<User> users = genericDao.getListByHql(hqlstr);
		if (users.isEmpty()) {
			return null;
		}
		if (users.size() >= 2) {
			throw new RuntimeException("用户工号或用户名有重复记录!");
		}
		return users.iterator().next();
	}
	
	public List<User> findUserByPostId(String postId) {
		Utility.raiseIfWrong(postId, "userManager.findUserByPostId方法中postId为空!");
		
		Object[] parameter = { postId, Constants.SYS_FLAG_USEING };
		List<User> users = genericDao.getList(
				"SECURITY.USER.FIND_USER_BY_CODE", parameter);
		return users;
	}

	public List<User> findUserByOrgId(String orgId) {
		Utility.raiseIfWrong(orgId, "userManager.findUserByOrgId方法中orgId为空!");
		
		Object[] parameter = { orgId, Constants.SYS_FLAG_USEING };
		List<User> users = genericDao.getList(
				"SECURITY.USER.FIND_USER_BY_ORG", parameter);
		return users;
	}

	/**
	 * 删除用户通过用户ID
	 * 
	 * @param userId
	 *            String:用户ID
	 * 
	 */
	public void deleteUser(String userId) {
		if (StringUtils.isBlank(userId)) {
			throw new RuntimeException("删除用户时,用户编号为空!");
		}
		User user = findUserById(userId);
		user.setSysFlag(Constants.SYS_FLAG_OVER);//SysFlag为2时，表示删除
		String lastModifiedTime = DateBean.getSysdateTime();
		user.setLastModifiedTime(lastModifiedTime);
		saveUser(user);
	}
	
	// ids必须以","分隔,返回姓名也以","分隔
	public String GetPersonName(String ids)
	{ 		
	   if(ids==null||"".equals(ids))
	   {
		   return null;
	   }
	   String[] arrId= ids.split(",");
	   String tempStr="";
	   for(int i=0;i<arrId.length;i++)
	   {
		   User user=findUserById(arrId[i]);
		   tempStr+=user.getName()+",";
	   }
	  tempStr=tempStr.substring(0,tempStr.lastIndexOf(","));
	   return tempStr;
	}
	
	
	// ids必须以","分隔,返回姓名也以","分隔
	public String GetPersonNameByCodes(String codes)
	{ 		
	   if(codes==null||"".equals(codes))
	   {
		   return null;
	   }
	   String[] arrId= codes.split(",");
	   String tempStr="";
	   for(int i=0;i<arrId.length;i++)
	   {   
		   User user=findUserByCode(arrId[i]);
		   if(user!=null)
		   tempStr+=user.getName()+",";
	   }
	   if(!tempStr.equals(""))
	    tempStr=tempStr.substring(0,tempStr.lastIndexOf(","));
	   else
		tempStr=null;
	   return tempStr;
	}
	
	public String findNameById(String id) {
		UserImpl user = (UserImpl) genericDao.getById(UserImpl.class, id);
		//UserVo user =  (UserVo) genericDao.getById(UserVo.class, id);
		return user.getName();
	}
	
	/**
	 * 通过名字和工号 查询是否重复
	 * @param userName
	 * @param jobno
	 * @return
	 */
	public boolean findUserName(UserVo userVo) {
			Object[] parameter = {userVo.getCode(),userVo.getJobno()};
			List<UserImpl> users = genericDao.getList("SECURITY.USER.FIND_USER_NAME",parameter);
			if(users!=null && !users.isEmpty()) {
				if(users.size()==1) {
					UserImpl user = users.iterator().next();
					if(userVo.getId().equals(user.getId())) {
						return true;
					}
					return false;
				}
				return false;
			}else {
				return true;
			}
		}
	}
