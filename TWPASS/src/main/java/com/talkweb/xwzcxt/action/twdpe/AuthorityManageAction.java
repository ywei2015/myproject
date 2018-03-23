package com.talkweb.xwzcxt.action.twdpe;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.twdpe.base.log.pojo.LogConfigParam;
import com.talkweb.twdpe.base.right.pojo.Permission;
import com.talkweb.twdpe.base.right.pojo.Role;
import com.talkweb.twdpe.base.right.pojo.Scope;
import com.talkweb.twdpe.base.right.service.AuthorityService;
import com.talkweb.twdpe.base.right.service.PermissionService;
import com.talkweb.twdpe.base.right.service.RoleService;
import com.talkweb.twdpe.web.session.main.Session;
import com.talkweb.twdpe.web.session.util.SessionFactory;
import com.talkweb.twdpe.webcomponent.common.action.BusinessAction;

public class AuthorityManageAction extends BusinessAction {
	@Autowired
	private RoleService roleServiceImpl;

	@Autowired
	private PermissionService permissionServiceImpl;

	@Autowired
    private AuthorityService authorityServiceImpl;

	/**
	 * 获取当前用户对应用资源的访问方式ID
	 **/
	public String getPrivilegeResourceModes() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String module = request.getParameter("module");
		if (module.equals("app"))
			module = LogConfigParam.MODULE_APP_ID;
		if (module.equals("role"))
			module = LogConfigParam.MODULE_ROLE_ID;
		if (module.equals("dataright"))
			module = LogConfigParam.MODULE_DATARULE_ID;
		if (module.equals("resource"))
			module = LogConfigParam.MODULE_RESOURCE_ID;
		if (module.equals("accessmode"))
			module = LogConfigParam.MODULE_MODE_ID;
		if (module.equals("dic"))
			module = LogConfigParam.MODULE_DICT_ID;
		if (module.equals("user"))
			module = LogConfigParam.MODULE_USER_ID;
		if (module.equals("position"))
			module = LogConfigParam.MODULE_POSITION_ID;
		if (module.equals("org"))
			module = LogConfigParam.MODULE_ORG_ID;
		if (module.equals("area"))
			module = LogConfigParam.MODULE_AREA_ID;
		if (module.equals("permission"))
			module = LogConfigParam.MODULE_PERMISSION_ID;
		if (module.equals("ip"))
			module = LogConfigParam.MODULE_IP_ID;
		if (module.equals("rejectip"))
			module = LogConfigParam.MODULE_REJECT_IP_ID;
		if (module.equals("log"))
			module = LogConfigParam.MODULE_LOG_ID;
		List<String> list = this.getPrivilegeResourceModes(module);
		this.formatData(list);
		return SUCCESS;
	}

	/**
	 * 获取当前用户对应用资源的数据规则ID
	 **/
	public String getPrivilegeDataModes() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		Session session = SessionFactory.getInstance(request, response);
		Map user = (Map) session.getAttribute("USERSESSION");
		long userID = (Long) user.get("id");

		Map result = new HashMap();
		result.put("positionid", user.get("positionid"));

		List<Role> roles = roleServiceImpl.getAllUserRoles(userID);
		Permission param = new Permission();
		param.setResourceCode(request.getParameter("module"));
		param.setModeId("view");
		List<Permission> permissions = permissionServiceImpl.queryPermissions(param);
		String scope = "";
		for (Role r : roles) {
			for (Permission p : permissions) {
				List<Scope> scopes = authorityServiceImpl.getRoleScopes(r.getRoleId(), p.getPermissionId());
				for (Scope s : scopes) {
					String rv = s.getRule().getRuleValue();
					if (scope.indexOf(rv) < 0) {
						scope += (rv + ",");
					}
				}
			}
		}
		if (!scope.isEmpty()) {
			scope = scope.substring(0, scope.length() - 1);
		}
		result.put("scope", scope);

		this.formatData(result);
		return SUCCESS;
	}
}