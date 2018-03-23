package com.talkweb.twdpe.webcomponent.component;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.talkweb.twdpe.base.common.exception.ExistChildrenException;
import com.talkweb.twdpe.base.dict.pojo.DictEntryInfo;
import com.talkweb.twdpe.base.dict.service.DictionaryService;
import com.talkweb.twdpe.base.log.pojo.LogConfigParam;
import com.talkweb.twdpe.base.org.pojo.Org;
import com.talkweb.twdpe.base.org.service.OrgService;
import com.talkweb.twdpe.base.right.constant.RightConstants;
import com.talkweb.twdpe.base.right.pojo.AccessMode;
import com.talkweb.twdpe.base.right.pojo.AccessModeList;
import com.talkweb.twdpe.base.right.pojo.App;
import com.talkweb.twdpe.base.right.pojo.Authorization;
import com.talkweb.twdpe.base.right.pojo.GrantNode;
import com.talkweb.twdpe.base.right.pojo.GrantRow;
import com.talkweb.twdpe.base.right.pojo.LimitAuthorization;
import com.talkweb.twdpe.base.right.pojo.Member;
import com.talkweb.twdpe.base.right.pojo.Member.MemberType;
import com.talkweb.twdpe.base.right.pojo.NormalAuthorization;
import com.talkweb.twdpe.base.right.pojo.OrgMember;
import com.talkweb.twdpe.base.right.pojo.Permission;
import com.talkweb.twdpe.base.right.pojo.PositionMember;
import com.talkweb.twdpe.base.right.pojo.Resource;
import com.talkweb.twdpe.base.right.pojo.ResourceList;
import com.talkweb.twdpe.base.right.pojo.Role;
import com.talkweb.twdpe.base.right.pojo.Rule;
import com.talkweb.twdpe.base.right.pojo.Scope;
import com.talkweb.twdpe.base.right.pojo.UserMember;
import com.talkweb.twdpe.base.right.service.AccessModeService;
import com.talkweb.twdpe.base.right.service.AppService;
import com.talkweb.twdpe.base.right.service.AuthorityService;
import com.talkweb.twdpe.base.right.service.PermissionService;
import com.talkweb.twdpe.base.right.service.ResourceService;
import com.talkweb.twdpe.base.right.service.RoleService;
import com.talkweb.twdpe.base.right.util.TreeUtils;
import com.talkweb.twdpe.core.exception.BizBaseException;
import com.talkweb.twdpe.core.log.Logger;
import com.talkweb.twdpe.core.log.LoggerFactory;
import com.talkweb.twdpe.core.util.StringUtil;
import com.talkweb.twdpe.core.util.json.JsonArray;
import com.talkweb.twdpe.core.util.json.JsonObject;
import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.twdpe.web.jcontrols.RuleSelect;
import com.talkweb.twdpe.web.jcontrols.RuleTree;
import com.talkweb.twdpe.web.session.main.Session;
import com.talkweb.twdpe.web.session.util.SessionFactory;
import com.talkweb.twdpe.webcomponent.common.action.BusinessAction;

/**
 * 文件名称: RoleMngAction.java 内容摘要:
 * 
 * @author: 丁涛
 * @version: 1.0
 * @Date: 2011-4-18 上午10:26:02
 * 
 *        修改历史: 修改日期 修改人员 版本 修改内容 ----------------------------------------------
 *        2011-4-18 丁涛 1.0 1.0 XXXX
 * 
 *        版权: 版权所有(C)2011 公司: 拓维信息系统股份有限公司
 */

@SuppressWarnings("serial")
public class RoleMngAction extends BusinessAction {

	private static final Logger logger = LoggerFactory.getLogger(RoleMngAction.class);

	/**
	 * 
	 * @Title: addRole
	 * @Description: 添加角色
	 * @return
	 */
	public String addRole() {
		int result = 0;
		try {
			Role r = this.getRoleByRoleCode(role.getRoleCode());
			// 判断是否有重复编码的纪录
			if (r != null)
				result = 2;
			else {
				// 如果parnetRoleId的值小于0，则表示是选的应用节点，统一将值转换成-1
				if (role.getParentRoleId() == null || role.getParentRoleId() < 0) {
					role.setParentRoleId(Long.parseLong("-1"));
				}
				roleService.addRole(role);
				result = 1;
			}
		} catch (Exception e) {
			ServletActionContext.getRequest().setAttribute("throw", e);
			logger.error(e.getMessage(), e);
		}
		this.setMessage(result, "ROLE", "ADD");
		// addSystemLog(LogConfigParam.MODULE_ROLE_ID,LogConfigParam.MODULE_ROLE_NAME,LogConfigParam.OPERATOR_TYPE_ADD,
		// jsonToString(JsonObject.toString(role)),
		// pageData.get("msg").toString());
		return SUCCESS;
	}

	/**
	 * 
	 * @Title: getRole
	 * @Description: 查找角色信息
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getRoleById() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("ROLEID");
		String mode = request.getParameter("mode");
		if (StringUtil.isNumber(id)) {
			long roleId = Long.parseLong(id);
			Role role = roleService.getRole(roleId);
			if (role.getAppId() > 0) {
				App app = appService.getApp(role.getAppId());
				role.setAppName(app.getAppName());
			}

			if (StringUtils.isEmpty(mode)) {
				try {
					this.formatData(this.addPrefix(role, "role."));
				} catch (Exception e) {
					ServletActionContext.getRequest().setAttribute("throw", e);
					// TODO Auto-generated catch block
					// e.printStackTrace();
					logger.error(e.getMessage(), e);
				}
			} else {
				HashMap data = new HashMap();
				if (role != null)
					data.put("exist", true);
				else
					data.put("exist", false);
			}
		} else
			this.formatData("{}");
		return SUCCESS;
	}

	/**
	 * 判断是否存在相同编码的纪录
	 * **/
	@SuppressWarnings("unchecked")
	public String existSameRecord() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String roleCode = request.getParameter("roleCode");
		HashMap pageData = new HashMap();
		if (!StringUtils.isEmpty(roleCode)) {
			Role r = this.getRoleByRoleCode(roleCode);
			if (r != null)
				pageData.put("exist", true);
			else
				pageData.put("exist", false);
		} else
			pageData.put("exist", false);
		this.formatData(pageData);
		return SUCCESS;
	}

	/**
	 * 私有方法，通过角色编码查找对应的纪录
	 * **/
	private Role getRoleByRoleCode(String roleCode) {
		return roleService.getRole(roleCode);
	}

	/**
	 * 
	 * @Title: queryRoles
	 * @Description: 根据查询条件查询角色列表
	 */
	@SuppressWarnings("unchecked")
	public String queryRoles() throws Exception {

		HttpServletRequest request = ServletActionContext.getRequest();
		if (role == null)
			role = new Role();
		this.setDataRight(role, request.getParameter("moduleId"), request.getParameter("modeId"));
		Pagination result = roleService.queryRoles(role, pagination);
		List<DictEntryInfo> dictEntrys = this.getRoleType();
		List list = result.getList();
		List<Long> orgIds = new ArrayList<Long>();
		for (Object o : list) {
			Role r = (Role) o;
			for (DictEntryInfo d : dictEntrys) {
				if (r.getRoleType().equals(d.getDictid()))
					r.setRoleTypeName(d.getValue());
			}
			if (r.getOrgId() != null && r.getOrgId() > 0)
				orgIds.add(r.getOrgId());
		}
		if (orgIds.size() > 0) {
			List<Org> orgs = orgService.getOrgByIds(orgIds);
			for (Object o : list) {
				Role r = (Role) o;
				if (r.getOrgId() != null && r.getOrgId() > 0)
					for (Org org : orgs) {
						if (r.getOrgId().longValue() == org.getOrgId().longValue())
							r.setOrgName(org.getOrgName());
					}
			}
		}
		this.formatDatagirdData(list, result);
		role = null;
		return SUCCESS;
	}

	/**
	 * 
	 * @Title: modifyRole
	 * @Description: 修改角色
	 * @return
	 */
	public String modifyRole() {
		int result = 0;
		try {
			// 判断上级角色ID是不是当前角色ID，以及自己的子角色ID
	        if ((role.getRoleId() != null && role.getParentRoleId() != null)
	                &&!roleService.checkParentRole(role.getRoleId(),role.getParentRoleId()))
	        {
				result = 3;
			} else {
				// 根据ID查找数据库纪录比对角色编码是否有改动，如果有改动，则判断是否有重复的角色编码纪录
				Role r = roleService.getRole(role.getRoleId());
				if (!r.getRoleCode().equals(role.getRoleCode())) {
					Role newRole = this.getRoleByRoleCode(role.getRoleCode());
					if (newRole != null)
						result = 2;
				}
				if (result == 0) {
					// 如果parnetRoleId的值小于0，则表示是选的应用节点，统一将值转换成-1
					if (role.getParentRoleId() == null
							|| (role.getParentRoleId() != null && role.getParentRoleId() < 0)) {
						role.setParentRoleId(Long.parseLong("-1"));
					}
					roleService.modifyRole(role);
					result = 1;
				}
			}
		} catch (Exception e) {
			ServletActionContext.getRequest().setAttribute("throw", e);
			logger.error(e.getMessage(), e);
		}
		this.setMessage(result, "ROLE", "MODIFY");
		// addSystemLog(LogConfigParam.MODULE_ROLE_ID,LogConfigParam.MODULE_ROLE_NAME,LogConfigParam.OPERATOR_TYPE_MODIFY,
		// jsonToString(JsonObject.toString(role)),
		// pageData.get("msg").toString());
		return SUCCESS;
	}

	/**
	 * 
	 * @Title: initRoleData
	 * @Description: 初始化增加角色时的字典表等数据
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String initRoleData() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String appId = request.getParameter("appId");
		String id = request.getParameter("ROLEID");
		Map m = new HashMap();
		List<Map> maps = new ArrayList<Map>();
		// 格式化表单所属应用的初始化数据
		if (appId != null) {
			List<Map> list = this.getRoleTreeByAppId(appId, false);
			Map m1 = this.initformAttribute("role.parentRoleId", list);
			maps.add(m1);
		}
		// 设置角色类型数据规则
		RuleSelect roleTypeRule = new RuleSelect();
		roleTypeRule.setText("name");
		roleTypeRule.setValue("dictid");

		List<App> apps = this.getApps(null);
		// 设置应用数据规则
		RuleSelect ruleSelect = new RuleSelect();
		ruleSelect.setText("appName");
		ruleSelect.setValue("appId");

		try {
			// 格式化表单所属组织的初始化数据
			if (StringUtil.isNumber(id)) {
				Role role = roleService.getRole(Long.parseLong(id));
				if (role != null && role.getOrgId() != null) {
					List<Org> orgTreeData = orgService.getOrgTreeData(role.getOrgId(), this.getLongUserID());
					Map m2 = this.initformAttribute("role.orgId", initTreeAsynData(orgTreeData));
					maps.add(m2);
				}
			}
			// 格式化表单应用初始化数据
			Map m4 = this.initformAttribute("role.appId", this.initSelectData(apps, ruleSelect));
			maps.add(m4);
		} catch (Exception e) {
			ServletActionContext.getRequest().setAttribute("throw", e);
			// TODO Auto-generated catch block
			// e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		this.formatData(maps);
		return SUCCESS;
	}

	/**
	 * 
	 * @Title: initSearchFromData
	 * @Description: 初始化搜索表单数据
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String initSearchFromData() {
		// 设置角色类型数据规则
		RuleSelect roleTypeRule = new RuleSelect();
		roleTypeRule.setText("name");
		roleTypeRule.setValue("dictid");

		// 设置应用数据规则
		RuleSelect ruleSelect = new RuleSelect();
		ruleSelect.setText("appName");
		ruleSelect.setValue("appId");

		List<Map> maps = new ArrayList<Map>();
		List<App> apps = this.getApps(null);

		// 格式化表单角色类型初始化数据
		try {
			Map m = this.initformAttribute("role.roleType", this.initSelectData(this.getRoleType(), roleTypeRule));
			maps.add(m);
			// 格式化表单应用初始化数据
			Map m4 = this.initformAttribute("role.appId", this.initSelectData(apps, ruleSelect));
			maps.add(m4);
		} catch (Exception e) {
			ServletActionContext.getRequest().setAttribute("throw", e);
			// TODO Auto-generated catch block
			// e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		this.formatData(maps);
		return SUCCESS;
	}

	/**
	 * 
	 * @Title: delRoles
	 * @Description: 删除角色信息
	 * @return
	 * @throws ExistChildrenException 
	 */
    public String delRoles() throws ExistChildrenException
    {
        int result = 0;
        HttpServletRequest request = ServletActionContext.getRequest();
        String ids = request.getParameter("id");
        String[] roleids = StringUtils.split(ids, ",");
        List<Long> rolelist = new ArrayList<Long>();
        if (roleids != null && roleids.length > 0)
        {
            for (String s : roleids)
            {
                if (StringUtil.isNumber(s))
                    rolelist.add(Long.parseLong(s));
            }
        }
        if (rolelist.size() > 0)
        {
            roleService.deleteRoles(rolelist);
            result = 1;
        }
        setMessage(result, "ROLE", "DELETE");
        return SUCCESS;
    }

	/**
	 * 显示角色树
	 * */
	@SuppressWarnings("unchecked")
	public String showRoleTree() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String appid = request.getParameter("appid");
		String showApp = request.getParameter("showApp");// 是否展示应用节点
		List<Map> list = null;
		if (showApp != null)
			list = this.getRoleTreeByAppId(appid, false);
		else
			list = this.getRoleTreeByAppId(appid);
		this.formatData(list);
		return SUCCESS;
	}

	/**
	 * 增加多个角色成员
	 */
	@SuppressWarnings("unchecked")
	public String addRoleMembers() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String roleId = request.getParameter("roldId");
		String type = request.getParameter("type");
		int result = 0;
		List<Member> members = new ArrayList<Member>();
		if (StringUtil.isNumber(roleId) && StringUtils.isNotEmpty(type)) {
			String id = request.getParameter("id");
			String[] ids = StringUtils.split(id, ",");
			if (ids != null && ids.length > 0)
				for (String s : ids) {
					Member m = null;
					if (StringUtil.isNumber(s)) {
						if (type.equals("user"))
							m = new UserMember(Long.parseLong(s), Long.parseLong(roleId));

						if (type.equals("org"))
							m = new OrgMember(Long.parseLong(s), Long.parseLong(roleId));

						if (type.equals("position"))
							m = new PositionMember(Long.parseLong(s), Long.parseLong(roleId));
						members.add(m);
					}
				}
			if (members.size() > 0)
				try {
					roleService.addRoleMembers(members);
					this.setOkMessage("添加角色成员成功");
				} catch (Exception e) {
					this.setErrorMessage("添加角色成员失败", e);
					ServletActionContext.getRequest().setAttribute("throw", e);
					logger.error(e.getMessage(), e);
				}
		}
		return SUCCESS;
	}

	/**
	 * 删除多个角色成员
	 */
	@SuppressWarnings("unchecked")
	public String deleteRoleMembers() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String roleId = request.getParameter("roleId");
		String type = request.getParameter("type");
		int result = 0;
		List<Member> members = new ArrayList<Member>();
		if (StringUtil.isNumber(roleId) && StringUtils.isNotEmpty(type)) {
			String id = request.getParameter("id");
			String[] ids = StringUtils.split(id, ",");
			if (ids != null && ids.length > 0)
				for (String s : ids) {
					Member m = null;
					if (StringUtil.isNumber(s)) {
						if (type.equals("user"))
							m = new UserMember(Long.parseLong(s), Long.parseLong(roleId));

						if (type.equals("org"))
							m = new OrgMember(Long.parseLong(s), Long.parseLong(roleId));

						if (type.equals("position"))
							m = new PositionMember(Long.parseLong(s), Long.parseLong(roleId));
						members.add(m);
					}
				}
			if (members.size() > 0)
				try {
					roleService.deleteRoleMembers(members);
					this.setOkMessage("删除角色成员成功");
				} catch (Exception e) {
					this.setErrorMessage("删除角色成员失败", e);
					ServletActionContext.getRequest().setAttribute("throw", e);
					logger.error(e.getMessage(), e);
				}
		} else {
			this.setErrorMessage("删除角色成员失败：参数无效。");
		}
		return SUCCESS;
	}

	/**
	 * 私有方法，生成角色树代码，只在在本类内调用
	 * **/
	@SuppressWarnings("unchecked")
	private List<Map> getRoleTreeByAppId(String appid) {
		return this.getRoleTreeByAppId(appid, true);
	}

	/***
	 * 私有方法，生成角色树代码，只在在本类内调用
	 * 
	 * @param appid
	 *            应用ID
	 * @param bl
	 *            是否带应用记录
	 * @return List<Map>
	 * */
	@SuppressWarnings("unchecked")
	private List<Map> getRoleTreeByAppId(String appid, boolean bl) {

		HttpServletRequest request = ServletActionContext.getRequest();
		// 根据条件取相关的应用
		List<App> apps = this.getApps(appid);
		List<Map> list = new ArrayList<Map>();
		RuleTree w = new RuleTree();
		w.setId("roleId");
		w.setPid("parentRoleId");
		w.setVal("roleName");
		Role role = new Role();
		int index = -2;
		for (App a : apps) {
			if (bl) {
				HashMap appmap = new HashMap();
				appmap.put("id", index);
				appmap.put("pid", "-1");
				appmap.put("val", a.getAppName());
				appmap.put("type", "A");
				appmap.put("originalId", a.getAppId());// 设置应用的真实ID
				list.add(appmap);// 添加应用节点
			}
			role.setAppId(a.getAppId());
			// 设置数据权限
			this.setDataRight(role, request.getParameter("moduleId"), request.getParameter("modeId"));

			List<Role> roles = roleService.queryRoles(role);
			List<Long> ids = new ArrayList<Long>();
			for (Role r : roles) {
				ids.add(r.getRoleId());
			}
			try {
				List<Map> maps = this.initTreeData(roles, w);
				if (maps != null && maps.size() > 0)
					for (Map m : maps) {
						if (bl)
							if (m.get("pid").toString().equals("-1")) {
								m.put("pid", index);
							}
						if (!ids.contains(m.get("pid"))) {
							m.put("pid", -1);
						}
						m.put("type", "R");
						list.add(m);
					}
			} catch (Exception e) {
				request.setAttribute("throw", e);
				// TODO Auto-generated catch block
				// e.printStackTrace();
				logger.error(e.getMessage(), e);
			}
			index--;
		}
		return list;
	}

    /**
     * 私有方法，取字典类型数据，只在本类中调用
     * */
    private List<DictEntryInfo> getRoleType() {
	List<DictEntryInfo> dictEntrys = null;
	try {
	    dictEntrys = dictionaryService.getDictEntrysByType("RIGHT_ROLE_TYPE");
	} catch (BizBaseException e) {
	    ServletActionContext.getRequest().setAttribute("throw", e);
	    // TODO Auto-generated catch block
	    // e.printStackTrace();
	    logger.error(e.getMessage(), e);
	}
	return dictEntrys;
	}

	/**
	 * 私有方法，取应用数据，只在本类中调用
	 * */
	private List<App> getApps(String appid) {
		App app = new App();
		if (StringUtil.isNumber(appid))
			app.setAppId(Long.parseLong(appid.trim()));
		List<App> apps = appService.queryApps(app);
		return apps;
	}

	private boolean isLimitPermission(long permissionId, List<Permission> roleLimitPermissions) {
		boolean isLimitPermission = false;
		if (roleLimitPermissions != null && roleLimitPermissions.size() > 0)
			for (Permission p : roleLimitPermissions) {
				if (p.getPermissionId() == permissionId)
					isLimitPermission = true;
			}
		return isLimitPermission;
	}

	public String getGrantAuthView() {
		HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);
		String showType = request.getParameter("showType");
		String resouceCode = request.getParameter("resourceCode");
		String appId = request.getParameter("appId");
		String oid = null;
		try {
			List<GrantRow> list = null;
			MemberType type = null;
			if ("user".equals(showType)) {
				oid = request.getParameter("userId");
				type = MemberType.User;
			} else if("org".equals(showType)) {
				oid = request.getParameter("orgId");
				type = MemberType.Org;
			} else if("position".equals(showType)) {
				oid = request.getParameter("positionId");
				type = MemberType.Position;
			} else if("role".equals(showType)) {
				oid = request.getParameter("roleId");
				type = null;
			}
			if (!StringUtil.isEmpty(oid)) {
				if(StringUtil.isEmpty(appId)) {
					list = authorityService.getGrantAuthView(Long.valueOf(oid), type);
				} else {
					list = authorityService.getGrantAuthView(Long.valueOf(oid), Long.valueOf(appId), resouceCode, type);
				}
			} else {
				list = new ArrayList<GrantRow>();
			}
			this.formatDatagirdData(list, new Pagination(1, Integer.MAX_VALUE));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 取当前角色权限树，用于权限视图
	 * @return
	 */
	public String showPermissionTree() {
		HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);
		String showType = request.getParameter("showType");
		Long objectId = null;
		MemberType type = null;
		if("user".equals(showType)) {
			objectId = Long.valueOf(request.getParameter("userId"));
			type = MemberType.User;
		} else if("org".equals(showType)) {
			objectId = Long.valueOf(request.getParameter("orgId"));
			type = MemberType.Org;
		} else if("position".equals(showType)) {
			objectId = Long.valueOf(request.getParameter("positionId"));
			type = MemberType.Position;
		} else if("role".equals(showType)) {
			objectId = Long.valueOf(request.getParameter("roleId"));
			type = null;
		}
		try{
			List<GrantNode> nodes = authorityService.getGrantAuthTrees(objectId, type);
			List<String> ids = new ArrayList<String>();
			List<String> appIds = new ArrayList<String>();
			for(GrantNode node : nodes) {
				ids.add(node.getId());
				Object appId = node.getMap().get("appid");
				if(appId != null) {
					String appid = appId + "";
					if(!appIds.contains(appid)) {
						appIds.add(appid);
					}
				}
			}
			Map<Long, App> appMap = new HashMap<Long, App>();
			List<Map> list = new ArrayList<Map>(); 
			for(String appId : appIds) {
				Map map = new HashMap();
				map.put("id", "app_" + appId);
				App app = appService.getApp(Long.valueOf(appId));
				map.put("val", app.getAppName());
				map.put("appid", appId);
				map.put("pid", "-1");
				map.put("type", "A");
				list.add(map);
			}
			for(GrantNode node : nodes) {
				Map map = new HashMap();
				map.put("id", node.getId());
				map.put("val", node.getName());
				String appId = node.getAppId().toString();
				map.put("appid", appId);
				map.put("type", "R");
				if(ids.contains(node.getParent())) {
					map.put("pid", node.getParent());
				} else {
					map.put("pid", "-1");
				}
				list.add(map);
			}
			this.formatData(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 取当前用户，对角色可授权的权限树
	 * */
	@SuppressWarnings("unchecked")
	public String showRolePermissionTree() {
		long currentUserId = 0;
		// 获得当前的请求
		HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);
		// 获取当前页面响应
		HttpServletResponse response = (HttpServletResponse) ActionContext.getContext().get(ServletActionContext.HTTP_RESPONSE);
		Session session = SessionFactory.getInstance(request, response);
		String showType = request.getParameter("showType");
		boolean isNormal = true;
		Map<String, Object> map = null;
		Object userSession = session.getAttribute("USERSESSION");

		if (userSession != null) {
			map = (Map<String, Object>) userSession;
			currentUserId = Long.parseLong(map.get("id").toString());
			List<GrantNode> list = null;
			List<Permission> roleLimitPermissions = null;
			List<Map> rt = new ArrayList<Map>();
			try{
				if ("user".equals(showType)) {
					String uId = request.getParameter("userId");
					list = authorityService.getGrantAuthTrees(currentUserId, Long.valueOf(uId), false);
				} else {
					String rid = request.getParameter("roleId");
					list = authorityService.getGrantAuthTrees(currentUserId, Long.valueOf(rid), true);
				}
				for (GrantNode node : list) {
					Map current = new HashMap();
					current.put("id", node.getId());
					current.put("val", node.getName());
					current.put("type", node.getType());
					current.put("pid", node.getParent());
					current.put("disabled", node.getReadOnly() + "");
					String check = "";
					int nowCheck = node.getStatus() == null ? 1 : node.getStatus();
					if (nowCheck == 1) {
						check = "false";
					} else if (nowCheck == 3) {
						check = "true";
					} else {
						check = "part";
					}
					current.put("chk", check);
					if ("P".equals(node.getType())) {
						if ("user".equals(showType) ) {
							current.put("ptype", node.getPtype());
						}
						current.put("modeId", node.getModeId());
						rt.add(current);
					} else {
						current.putAll(node.getMap());
						rt.add(current);
					}
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
			this.formatData(rt);
		}
		return SUCCESS;
	}

	public String userPrivilege() {
		int result = 0;
		HttpServletRequest request = ServletActionContext.getRequest();
		String oldPids = request.getParameter("oldPids");
		String rid = request.getParameter("roleId");
		List<NormalAuthorization> auths = new ArrayList<NormalAuthorization>();
		List<Authorization> authsLimit = new ArrayList<Authorization>();
		if (StringUtil.isNumber(rid)) {
			long roleId = Long.parseLong(rid);
			String roleRuleFuncValue = request.getParameter("roleRuleFuncValue");
			try {
				roleRuleFuncValue = java.net.URLDecoder.decode(roleRuleFuncValue, "UTF-8");
			} catch (UnsupportedEncodingException e1) {
				logger.error(e1.getMessage(), e1);
			}
			JsonObject jo = JsonObject.fromObject(roleRuleFuncValue);
			String pids = request.getParameter("pids");
			
			Map map = this.getPidByType(pids, oldPids);
			ArrayList<Long> newPidList = (ArrayList<Long>) map.get("new");// 新增的权限
			ArrayList<Long> oldPidList = (ArrayList<Long>) map.get("old");// 去掉的权限
			// String[] permissionids = StringUtils.split(pids, ",");
			// for (String pid : permissionids) {
			// if (StringUtil.isNumber(pid)) {
			// 判断数据权限，如果用户只做了数据权限操作
			if (!jo.isEmpty())
				for (Object jkey : jo.keySet()) {
					long pid = Long.parseLong(jkey.toString());
					if (!newPidList.contains(pid))
						newPidList.add(pid);
				}

			for (long permissionId : newPidList) {
				// long permissionId = Long.parseLong(pid);
				NormalAuthorization a = new NormalAuthorization(roleId, permissionId);
				// 给用户私用角色加权限时，要先删除已有的限制权限，增加一条限制权限的记录
				LimitAuthorization b = new LimitAuthorization(roleId, permissionId);
				List<Scope> list = new ArrayList<Scope>();
				if (!jo.isEmpty() && jo.get(String.valueOf(permissionId)) != null) {
					JsonArray jay = jo.getJsonArray(String.valueOf(permissionId));
					if (jay != null && !jay.toString().equals("null") && jay.size() > 0) {
						for (Object o : jay.toArray()) {
							Scope s = new Scope();
							s.setRoleId(roleId);
							s.setPermissionId(permissionId);
							s.setRuleId(Long.parseLong(o.toString()));
							list.add(s);
						}
						a.setScopes(list);
					}
				}
				auths.add(a);
				authsLimit.add(b);
			}

			List<LimitAuthorization> addAuthsLimit = new ArrayList<LimitAuthorization>();
			for (long permissionId : oldPidList) {
				LimitAuthorization auth = new LimitAuthorization(roleId, permissionId);
				addAuthsLimit.add(auth);
			}
			try {
				// if (auths.size() > 0){
				if (auths != null && auths.size() > 0) {
					for (NormalAuthorization na : auths) {
						authorityService.privilege(na);
					}
					authorityService.deletePrivilege(authsLimit);// 删除私有角色已有限制权限
				}
				if (addAuthsLimit != null && addAuthsLimit.size() > 0) {
					for (LimitAuthorization la : addAuthsLimit) {
						authorityService.privilege(la);
					}
				}
				result = 1;
			} catch (Exception e) {
				ServletActionContext.getRequest().setAttribute("throw", e);
				logger.error(e.getMessage(), e);
				e.printStackTrace();
			}

		}
		HashMap pageData = new HashMap();
		pageData.put("result", result);
		this.formatData(pageData);
		return SUCCESS;
	}

	@SuppressWarnings("rawtypes")
	private Map getPidByType(String newPid, String oldPid) {
		Map hmpid = new HashMap();
		String[] newPids = newPid.split(",");
		String[] oldPids = oldPid.split(",");
		List<Long> newPidList = new ArrayList<Long>();
		List<Long> oldPidList = new ArrayList<Long>();
		boolean exist = false;
		for (String nid : newPids) {
			if (StringUtil.isEmpty(nid))
				continue;
			exist = false;
			for (String oid : oldPids) {
				if (nid.equals(oid)) {
					exist = true;
					break;
				}
			}
			if (!exist && StringUtil.isNumber(nid))
				newPidList.add(Long.parseLong(nid));
		}

		for (String oid : oldPids) {
			if (StringUtil.isEmpty(oid))
				continue;
			exist = false;
			for (String nid : newPids) {
				if (oid.equals(nid)) {
					exist = true;
					break;
				}
			}
			if (!exist && StringUtil.isNumber(oid))
				oldPidList.add(Long.parseLong(oid));
		}
		hmpid.put("new", newPidList);
		hmpid.put("old", oldPidList);
		return hmpid;
	}
	
	/**
	 * 恢复权限
	 * @return
	 */
	public String resetRights() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String rid = request.getParameter("roleId");
		Long roleId = Long.valueOf(rid);
		try {
			List<Permission> rolePermissions = permissionService.getRolePermissions(roleId, true);
			List<Authorization> auths = new ArrayList<Authorization>();
			for (Permission permission : rolePermissions) {
				auths.add(new NormalAuthorization(roleId, permission.getPermissionId()));
			}
			List<Permission> roleLimitPermissions = permissionService.getRolePermissions(roleId, false);
			for (Permission permission : roleLimitPermissions) {
				auths.add(new LimitAuthorization(roleId, permission.getPermissionId()));
			}
			authorityService.deletePrivilege(auths);
			setOkMessage("还原权限操作成功");
		} catch (Exception e) {
			ServletActionContext.getRequest().setAttribute("throw", e);
			setErrorMessage("还原权限操作失败", e);
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		return SUCCESS;
	}
	
	public String deleteLimitRights() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String pids = request.getParameter("pids");
		String rid = request.getParameter("rid");
		String[] pidArr = pids.split(",");
		Long roleId = Long.valueOf(rid);
		List<Authorization> auths = new ArrayList<Authorization>();
		try {
			for (String pid : pidArr) {
				LimitAuthorization auth = new LimitAuthorization(roleId, Long.valueOf(pid));
				auths.add(auth);
			}
			authorityService.deletePrivilege(auths);
			setOkMessage("限制权限恢复成功");
		} catch (Exception e) {
			ServletActionContext.getRequest().setAttribute("throw", e);
			setErrorMessage("限制权限恢复失败", e);
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		return SUCCESS;
	}
	
	/**
	 * 查看限制权限
	 * @return
	 */
	public String getLimitRights() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String rid = request.getParameter("roleId");
		Long roleId = Long.valueOf(rid);
		try {
			List<Permission> rolePermissions = permissionService.getRolePermissions(roleId, false);
			AccessModeList accessModeList = accessModeService.selectAll();
			ResourceList resourceList = resourceService.queryResources();
			List list = new ArrayList();
			Map map = null;
			for(Permission permission : rolePermissions) {
				Resource resource = resourceList.get(permission.getResourceCode());
				List<Resource> childrens = resourceList.getChildren(resource);
				if(childrens == null || childrens.size() == 0){
					List<Resource> resources = resourceService.getResourcePath(permission.getResourceCode());
					Collections.reverse(resources);
					String resourceName = "";
					int index = 0;
					for(Resource res : resources) {
						resourceName += res.getResourceName();
						if(index < resources.size() - 1) {
							resourceName += "-";
						}
						index ++;
					}
					map = new HashMap();
					map.put("permissionId", permission.getPermissionId());
					map.put("resourceCode", permission.getResourceCode());
					map.put("resourceName", resourceName);
					AccessMode mode = accessModeList.get(permission.getModeId());
					map.put("moduleId", mode.getModeId());
					map.put("modeName", mode.getModeName());
					list.add(map);
				}
				
			}
			this.formatDatagirdData(list, pagination);
		} catch (Exception e) {
			ServletActionContext.getRequest().setAttribute("throw", e);
			setErrorMessage("获取限制权限操作失败", e);
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		return SUCCESS;
	}

	/**
	 * 角色赋权
	 * **/
	@SuppressWarnings("unchecked")
	public String privilege() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String rid = request.getParameter("roleId");
		String newPids = request.getParameter("newPids");
		String roleRuleFuncValue = request.getParameter("roleRuleFuncValue");
		String showType = request.getParameter("showType");
		
		String delPids = request.getParameter("delPids");
		
		long roleId = Long.parseLong(rid);
		List<Authorization> auths = new ArrayList<Authorization>();

		List<Authorization> deleteAuths = new ArrayList<Authorization>();
		
		try {
			if(!StringUtil.isEmpty(newPids)) {
				JSONArray jsonArr = JSONArray.fromObject(newPids);
				for(int i = 0; i < jsonArr.size(); i++) {
					JSONObject json = jsonArr.getJSONObject(i);
					long id = json.getLong("id");
					if("role".equals(showType)) {
						NormalAuthorization a = new NormalAuthorization(roleId, id);
						auths.add(a);
					} else {
						String ptype = json.getString("ptype");
						if("R".equals(ptype)) {
							NormalAuthorization a = new NormalAuthorization(roleId, id);
							auths.add(a);
						} else {
							deleteAuths.add(new LimitAuthorization(roleId, id));
						}
					}
					
				}
			}
			
			if(!StringUtil.isEmpty(delPids)) {
				JSONArray jsonArr = JSONArray.fromObject(delPids);
				for(int i = 0; i < jsonArr.size(); i++) {
					JSONObject json = jsonArr.getJSONObject(i);
					long id = json.getLong("id");
					if("role".equals(showType)) {
						deleteAuths.add(new NormalAuthorization(roleId, id));
					} else {
						String ptype = json.getString("ptype");
						if("R".equals(ptype)) {
							auths.add(new LimitAuthorization(roleId, id));
						} else {
							deleteAuths.add(new NormalAuthorization(roleId, id));
						}
					}
				}
			}
			
			if(!StringUtil.isEmpty(roleRuleFuncValue)) {
				JsonArray jos = JsonArray.fromArray(roleRuleFuncValue);
				for(int i = 0; i < jos.size(); i++) {
					JsonObject jo = jos.getJsonObject(i);
					long permissionId = jo.getLong("pid");
					Scope scope = new Scope();
					scope.setPermissionId(permissionId);
					scope.setRoleId(roleId);
					authorityService.deleteScope(scope);
					String ruleIds = jo.getString("ruleIds");
                    if ((ruleIds != null) && ruleIds.length() != 0)
                    {
                        String[] ruleIdArray = ruleIds.split(",");
                        for (String ruleId : ruleIdArray)
                        {
                            scope = new Scope();
                            scope.setRoleId(roleId);
                            scope.setPermissionId(permissionId);
                            scope.setRuleId(Long.valueOf(ruleId));
                            authorityService.insertScope(scope);
                        }
                    }
				}
			}

		
			if (auths.size() > 0) {
				for (Authorization auth : auths) {
					authorityService.privilege(auth);
				}
			}
			if (deleteAuths.size() > 0)
				authorityService.deletePrivilege(deleteAuths);
			setOkMessage("角色赋权操作成功");
		} catch (Exception e) {
			ServletActionContext.getRequest().setAttribute("throw", e);
			setErrorMessage("角色赋权操作失败", e);
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		return SUCCESS;
	}

	/**
	 * 分配角色限制权限
	 * **/
	@SuppressWarnings("unchecked")
	public String restrictPermissions() {
		int result = 0;
		HttpServletRequest request = ServletActionContext.getRequest();
		String rid = request.getParameter("roleId");
		List<LimitAuthorization> auths = new ArrayList<LimitAuthorization>();
		if (StringUtil.isNumber(rid)) {
			long roleId = Long.parseLong(rid);
			String pids = request.getParameter("pids");
			String[] permissionids = StringUtils.split(pids, ",");
			for (String pid : permissionids) {
				if (StringUtil.isNumber(pid)) {
					long permissionId = Long.parseLong(pid);
					LimitAuthorization a = new LimitAuthorization(roleId, permissionId);
					auths.add(a);
				}
			}

			try {
				if (auths.size() > 0)
					authorityService.privilege(auths);
				else
					authorityService.deletePrivilege(new LimitAuthorization(roleId));
				result = 1;
			} catch (Exception e) {
				ServletActionContext.getRequest().setAttribute("throw", e);
				// TODO Auto-generated catch block
				// e.printStackTrace();
				logger.error(e.getMessage(), e);
			}

		}
		HashMap pageData = new HashMap();
		pageData.put("result", result);
		String message = "";
		if (result == 1)
			message = "分配角色限制权限操作成功";
		else
			message = "分配角色限制权限操作失败";
		// addSystemLog(LogConfigParam.MODULE_ROLE_ID,LogConfigParam.MODULE_ROLE_NAME,LogConfigParam.OPERATOR_TYPE_ADD,
		// this.jsonToString(JsonArray.toString(auths)), message);
		this.formatData(pageData);
		return SUCCESS;
	}

	/**
	 * 获取角色的权限视图
	 * */
	@SuppressWarnings("unchecked")
	public String getRoleGrantAuthTreeView() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String roleId = request.getParameter("roleId");
		if (StringUtil.isNumber(roleId)) {
			List<GrantNode> list = authorityService.getRoleGrantAuthTreeView(Long.parseLong(roleId));
			List<Map> nodes = TreeUtils.transfor2Maps(list);
			this.formatData(nodes);
		}
		return SUCCESS;
	}

	/**
	 * 获取角色数据范围
	 * */
	@SuppressWarnings("unchecked")
	public String getRoleScopes() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String roleId = request.getParameter("roleId");
		String permissionId = request.getParameter("pid");
		if (StringUtil.isNumber(roleId) && StringUtil.isNumber(permissionId)) {
			List<Scope> list = authorityService.getRoleScopes(Long.parseLong(roleId), Long.parseLong(permissionId));
			List<Rule> rules = new ArrayList<Rule>();
			for (int i = 0; i < list.size(); i++) {
				Scope scope = list.get(i);
				rules.add(scope.getRule());
			}
			Pagination page = null;
			if (list != null && list.size() > 10) {
				page = new Pagination(1, list.size());
			} else {
				page = new Pagination(1, 10);
			}
			page.setCount(list.size());
			page.setAllPage(1);
			this.formatDatagirdData(rules, page);
		}
		return SUCCESS;
	}

	private void setDataRight(Role o, String moduleId, String modeId) {
		if (StringUtils.isEmpty(moduleId)) {
			moduleId = LogConfigParam.MODULE_ROLE_ID;
		}
		if (StringUtils.isEmpty(modeId)) {
			modeId = RightConstants.MODE_VIEW;
		}
		o.setCurrentUserId(this.getLongUserID());
		o.setPermissionId(moduleId, modeId);
	}

	@Autowired
	private RoleService roleService;

	@Autowired
	private AppService appService;

	@Autowired
	private DictionaryService dictionaryService;

	@Autowired
	private OrgService orgService;

	@Autowired
	private AuthorityService authorityService;

	@Autowired
	private PermissionService permissionService;
	
	@Autowired
	private AccessModeService accessModeService;
	
	@Autowired
	private ResourceService resourceService;

	private Role role;

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}
