package com.talkweb.xwzcxt.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.twdpe.base.right.pojo.Permission;
import com.talkweb.twdpe.base.right.pojo.Role;
import com.talkweb.twdpe.base.right.pojo.Scope;
import com.talkweb.twdpe.base.right.service.AuthorityService;
import com.talkweb.twdpe.base.right.service.PermissionService;
import com.talkweb.twdpe.base.right.service.RoleService;
import com.talkweb.twdpe.web.session.main.Session;
import com.talkweb.twdpe.web.session.util.SessionFactory;
import com.talkweb.xwzcxt.service.DpPositionServiceI;

public class GetRoleUtil {
	@Autowired
	private RoleService roleServiceImpl;
	@Autowired
	private AuthorityService authorityServiceImpl;
	@Autowired
	private PermissionService permissionServiceImpl;
	@Autowired
	private DpPositionServiceI dpPositionService;
	/**
	 * 方法用途：通过userID和模块链接代码获得级别权限
	 * 参数：Long userID,String module
	 * 返回类型：Map
	 * 编写时间：2015年8月4日下午4:03:53
	 * 编写人：caoyong
	 */
	public String getFinalRole(String modeId,String resourceCode) {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
//		String module = request.getParameter("module");
		Session session = SessionFactory.getInstance(request, response);
		Map user = (Map) session.getAttribute("USERSESSION");
		long userID = (Long) user.get("id");
		List<Role> roles = roleServiceImpl.getAllUserRoles(userID);
		Permission param = new Permission();
		param.setResourceCode(modeId);
		param.setModeId(resourceCode);
		List<Permission> permissions = permissionServiceImpl
				.queryPermissions(param);
		String scope = "";
		for (Role r : roles) {
			for (Permission p : permissions) {
				List<Scope> scopes = authorityServiceImpl.getRoleScopes(
						r.getRoleId(), p.getPermissionId());
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
		String finalRuleCode = "";
		String[] ruleCodes = scope.split(",");
		for (int i = 0; i < ruleCodes.length; i++) {
			String string = ruleCodes[i];
			if (string.startsWith("A")) {
				finalRuleCode = string;
				break;
			}
			if (ruleCodes.length == 4 && string.startsWith("I")) {
				finalRuleCode = string;
			}
			if (ruleCodes.length == 3 && string.startsWith("V")) {
				finalRuleCode = string;
			} else if (ruleCodes.length == 3 && string.startsWith("I")) {
				finalRuleCode = string;
			}
			if (ruleCodes.length == 2 && string.startsWith("L")) {
				finalRuleCode = string;
			} else if (ruleCodes.length == 2
					&& (string.startsWith("V") || string.startsWith("I"))) {
				finalRuleCode = "ITMinister";
			}
			if (ruleCodes.length == 1) {
				finalRuleCode = string;
			}

		}
		return finalRuleCode;
	}
	
	/**
	 * 方法用途：部长级权限的所管部门id获取
	 * 参数:
	 * 返回类型：String
	 * 编写时间：2015年8月7日下午3:50:37
	 * 编写人：caoyong
	 */
	public String getITMinisterOrgId(String orgId){
		String itMinisterOrgId = dpPositionService.getITMinisterOrgId(orgId);
		if (itMinisterOrgId != null && !itMinisterOrgId.equals("") && !itMinisterOrgId.equals("null")) {
			if (itMinisterOrgId.contains(";")) {
				String[] ss = itMinisterOrgId.split(";");
				int y = ss.length;
				if(y>=1){
				return ss[1];
				}else{
					return ss[0];
				}
			}
		}
		return "";
	}
}
