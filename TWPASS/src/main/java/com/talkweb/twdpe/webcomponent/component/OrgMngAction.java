package com.talkweb.twdpe.webcomponent.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.talkweb.twdpe.base.common.exception.DataExistException;
import com.talkweb.twdpe.base.common.exception.IllegalLevelException;
import com.talkweb.twdpe.base.dict.pojo.DictEntryInfo;
import com.talkweb.twdpe.base.dict.service.DictionaryService;
import com.talkweb.twdpe.base.log.pojo.LogConfigParam;
import com.talkweb.twdpe.base.org.constant.OrgConstants;
import com.talkweb.twdpe.base.org.pojo.Area;
import com.talkweb.twdpe.base.org.pojo.Org;
import com.talkweb.twdpe.base.org.pojo.User;
import com.talkweb.twdpe.base.org.service.AreaService;
import com.talkweb.twdpe.base.org.service.OrgService;
import com.talkweb.twdpe.base.org.service.UserService;
import com.talkweb.twdpe.base.right.constant.RightConstants;
import com.talkweb.twdpe.base.right.pojo.App;
import com.talkweb.twdpe.base.right.pojo.Member;
import com.talkweb.twdpe.base.right.pojo.Member.MemberType;
import com.talkweb.twdpe.base.right.pojo.OrgMember;
import com.talkweb.twdpe.base.right.pojo.Permission;
import com.talkweb.twdpe.base.right.pojo.Role;
import com.talkweb.twdpe.base.right.pojo.Rule;
import com.talkweb.twdpe.base.right.service.AppService;
import com.talkweb.twdpe.base.right.service.AuthorityService;
import com.talkweb.twdpe.base.right.service.PermissionService;
import com.talkweb.twdpe.base.right.service.RoleService;
import com.talkweb.twdpe.core.exception.BizBaseException;
import com.talkweb.twdpe.core.log.Logger;
import com.talkweb.twdpe.core.log.LoggerFactory;
import com.talkweb.twdpe.core.util.StringUtil;
import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.twdpe.web.jcontrols.RuleSelect;
import com.talkweb.twdpe.web.jcontrols.RuleTree;
import com.talkweb.twdpe.webcomponent.common.action.BusinessAction;

/**
 * 文件名称: OrgMngAction.java 内容摘要:
 * 
 * @author: zhangwen
 * @version: 1.0
 * @Date: 2011-4-18 下午02:31:03
 * 
 *        修改历史: 修改日期 修改人员 版本 修改内容 ----------------------------------------------
 *        2011-4-18 zhangwen 1.0 1.0 XXXX
 * 
 *        版权: 版权所有(C)2011 公司: 拓维信息系统股份有限公司
 */
public class OrgMngAction extends BusinessAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8420521724923436180L;

	private static final Logger logger = LoggerFactory.getLogger(OrgMngAction.class);

	private Org org = null;
	/**
	 * 通过注入方式初始化的组织管理类,用于组织管理的增，删，改，查的数据库操作类
	 * */
	@Autowired
	private OrgService orgService;
	/**
	 * 通过注入方式初始化的地区管理类,用于地区管理的增，删，改，查的数据库操作类
	 * */
	@Autowired
	private AreaService areaService;
	/**
	 * 通过注入方式初始化的角色管理类,用于角色管理的增，删，改，查的数据库操作类
	 * */
	@Autowired
	private RoleService roleService;
	/**
	 * 通过注入方式初始化的应用管理类,用于应用管理的增，删，改，查的数据库操作类
	 * */
	@Autowired
	private AppService appService;
	/**
	 * 通过注入方式初始化的用户管理类,用于用户管理的增，删，改，查的数据库操作类
	 * */
	@Autowired
	private UserService userService;
	/**
	 * 通过注入方式初始化的字典管理类,用于字典管理的增，删，改，查的数据库操作类
	 * */
	@Autowired
	private DictionaryService dictionaryService;
	
	@Autowired
	private AuthorityService authorityService;
	
	@Autowired
	private PermissionService permissionService;

	public Org getOrg() {
		return org;
	}

	public void setOrg(Org org) {
		this.org = org;
	}
	
	private Role role = null;		

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	/**
	 * 查询所有组织信息
	 * 
	 * @Title: searchAllOrg
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 */
	@SuppressWarnings("unchecked")
	public String searchAllOrg() {
		HttpServletRequest request = ServletActionContext.getRequest();
		Org o = new Org();
		this.setOrgDataRight(o, request.getParameter("moduleId"), request.getParameter("modeId"));
		List<Org> orglist = orgService.queryOrgs(o);
		try {
			// 设置树控件数据映射关系
			RuleTree wtree = new RuleTree();
			wtree.setId("orgId");
			wtree.setVal("orgName");
			wtree.setPid("parentOrgId");

			// 格式化树控件数据,按树控件的ID，VAL，PID的顺序
			List<Map> l = this.initTreeData(orglist, wtree);
			this.formatData(l);
		} catch (Exception e) {
			request.setAttribute("throw", e);
			// e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		return SUCCESS;
	}
	
	public String getAllOrgWithUser(){
		HttpServletRequest request = ServletActionContext.getRequest();
		Org o = new Org();
		this.setOrgDataRight(o, request.getParameter("moduleId"), request.getParameter("modeId"));
		List<Org> orglist = orgService.queryOrgs(o);
		try {
			// 设置树控件数据映射关系
			RuleTree wtree = new RuleTree();
			wtree.setId("orgId");
			wtree.setVal("orgName");
			wtree.setPid("parentOrgId");
			
			 Map<String,String> map = new HashMap<String,String>();
		     map.put("c_objtype_code", "c_objtype_code");
		     map.put("c_up_objtype_code", "c_up_objtype_code");
		     wtree.setMap(map);

			// 格式化树控件数据,按树控件的ID，VAL，PID的顺序
			List<Map> l = this.initTreeData(orglist, wtree);
			this.formatData(l);
		} catch (Exception e) {
			request.setAttribute("throw", e);
			// e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		return SUCCESS;
	}

	/**
	 * 分页查询组织信息
	 * 
	 * @Title: searchOrg
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 */
	@SuppressWarnings("unchecked")
	public String searchOrg() {
		HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(
				ServletActionContext.HTTP_REQUEST);
		try {
			if (pagination == null) {
				pagination = new Pagination(1, 10);
			}
			if (org == null) {
				org = new Org();
			}
			this.excludeNullProperties(org);
			if (org.getParentOrgId() != null && org.getParentOrgId() == -1) {
				org.setParentOrgId(null);
			}
			// else {
			// this.pagination.setRange((this.pagination.getCurrPage() - 1)
			// * this.pagination.getSize(), this.pagination.getSize());
			// }
			// Pagination page =
			// orgService.queryOrgs(Long.parseLong(pid),orginfo, pagination);
			// 设置组织数据权限
			this.setOrgDataRight(org, request.getParameter("moduleId"), request.getParameter("modeId"));

			Pagination page = orgService.queryOrgs(org, pagination);
			List list = page.getList();
			List result = new ArrayList();
			for (int i = 0; i < list.size(); i++) {
				Org o = (Org) list.get(i);
				String areaid = o.getArea();
				if (areaid != null && !"".equals(areaid)) {
					Area a = areaService.getArea(areaid);
					if(a != null) {
						o.setAreaName(a.getAreaName());
					}
				}
				DictEntryInfo dictEntryInfo = dictionaryService.getDictEntryById("ORG_TYPE", o.getOrgType());
				if (dictEntryInfo != null) {
					o.setOrgTypeName(dictEntryInfo.getName());
				}
				result.add(o);
			}
			this.formatDatagirdData(result, page);
		} catch (Exception e) {
			ServletActionContext.getRequest().setAttribute("throw", e);
			logger.error(e.getMessage(), e);
		}
		return SUCCESS;
	}

	/**
	 * 组织排序
	 * 
	 * @Title: changeOrgOrder
	 * @Description: 组织排序
	 */
	public String changeOrgOrder() {
		HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(
				ServletActionContext.HTTP_REQUEST);
		String data = request.getParameter("data");
		try {
			if(!StringUtil.isEmpty(data)) {
				List<Org> list = new ArrayList<Org>();
				String[] orgIds = data.split(",");
				int orderBy = 1;
				for(String orgId : orgIds) {
					Org o = new Org();
					o.setOrgId(new Long(orgId));
					o.setOrderby(orderBy ++);
					list.add(o);
				}
				orgService.sortOrg(list);
			}
			this.setOkMessage("组织排序成功！");
		} catch (Exception e) {
			ServletActionContext.getRequest().setAttribute("throw", e);
			this.setErrorMessage("组织排序失败！");
			logger.error(e.getMessage(), e);
		}
		return SUCCESS;
	}

	/**
	 * 添加组织信息
	 * 
	 * @Title: addOrg
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 */
	public String addOrg() {
		try {
			if (org.getParentOrgId() == null) {
				org.setParentOrgId(new Long(-1));
			}
			if(org.getBizParentId() == null) {
				org.setBizParentId(-1L);
			}
			this.setOrgLevel(org);
			org.setIsDelete(0);
			orgService.addOrg(org);
			this.formatData(this.addPrefix(org, "org."));
		} catch (DataExistException e) {
			ServletActionContext.getRequest().setAttribute("throw", e);
			this.setMessage(2, "ORG", "ADD");
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			e.printStackTrace();
		}
		org = null;
		return SUCCESS;

	}

	/**
	 * 修改组织信息
	 * 
	 * @Title: modifyOrg
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 */
	public String modifyOrg() {
		try {
			if (org.getParentOrgId() == null)
				org.setParentOrgId(-1L);
			if(org.getBizParentId() == null) {
				org.setBizParentId(-1L);
			}
			this.setOrgLevel(org);
			orgService.modifyOrg(org);
			this.formatData(this.addPrefix(org, "org."));
		} catch (NumberFormatException e) {
			ServletActionContext.getRequest().setAttribute("throw", e);
			this.setMessage(0, "ORG", "MODIFY");
			logger.error(e.getMessage(), e);
		} catch (IllegalLevelException e) {
			ServletActionContext.getRequest().setAttribute("throw", e);
			this.setMessage(2, "ORG", "MODIFY");
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			ServletActionContext.getRequest().setAttribute("throw", e);
			this.setMessage(2, "ORG", "MODIFY");
			logger.error(e.getMessage(), e);
		}
		org = null;
		return SUCCESS;
	}

	/**
	 * 批量删除组织信息
	 * 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 */
	@SuppressWarnings("unchecked")
	public String delOrg() {
		HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(
				ServletActionContext.HTTP_REQUEST);
		String id = request.getParameter("orgid");
		try {
			int res = 1;
			String[] ids = id.split(",");
			List<Member> members = new ArrayList<Member>();
			for (int i = 0; i < ids.length; i++) {
				List list = orgService.getChildOrgs(new Long(ids[i]));
				if (list != null && list.size() > 0) {
					res = 2;
					org = orgService.getOrg(new Long(ids[i]));
					this.setWarnMessage("删除组织失败，“" + org.getOrgName() + "”有子组织");
					break;
				}
				User user = new User();
				user.setOrgId(new Long(ids[i]));
				List userList = userService.queryUsers(user);
				if (userList != null && userList.size() > 0) {
					res = 3;
					org = orgService.getOrg(new Long(ids[i]));
					this.setWarnMessage("删除组织失败，“" + org.getOrgName() + "”有用户");
					break;
				}
				Role role = new Role();
				List<Role> rolelist = roleService.queryMemberRoles(new Long(ids[i]), MemberType.Org, role,
						new Pagination(1, 1000)).getList();
				if (rolelist != null && rolelist.size() > 0) {
					for (Role r : rolelist) {
						Member orgmember = new OrgMember(new Long(new Long(ids[i])), new Long(r.getRoleId()));
						members.add(orgmember);
					}
				}
			}
			if (res == 1) {
				if (members.size() > 0) {
					roleService.deleteRoleMembers(members);
				}
				for (int i = 0; i < ids.length; i++) {
					orgService.removeOrg(new Long(ids[i]));
				}
				this.setMessage(res, "ORG", "DELETE");
			}
		} catch (Exception e) {
			ServletActionContext.getRequest().setAttribute("throw", e);
			this.setMessage(5, "ORG", "DELETE");
			logger.error(e.getMessage(), e);
		}
		return SUCCESS;
	}

	/**
	 * 根据组织ID查找组织信息
	 * 
	 * @Title: getOrgByID
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 */
	public String getOrgByID() {
		HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(
				ServletActionContext.HTTP_REQUEST);
		String orgid = request.getParameter("orgID");
		try {
			Org orginfo = orgService.getOrg(new Long(orgid));
			Long pid = orginfo.getParentOrgId();

			if (pid != null) {
				Org porg = orgService.getOrg(pid);
				if (porg != null) {
					orginfo.setParentOrgName(porg.getOrgName());
				}
			}

			if (orginfo.getArea() != null) {
				Area area = areaService.getArea(orginfo.getArea());
				if(area != null) {
					orginfo.setAreaName(area.getAreaName());
				}
			}
			if (orginfo.getOrgType() != null) {
				DictEntryInfo dictEntryInfo = dictionaryService.getDictEntryById("ORG_TYPE",
						orginfo.getOrgType());
				if (dictEntryInfo != null) {
					orginfo.setOrgTypeName(dictEntryInfo.getName());
				}
			}
			if (orginfo.getBusiOrg() != null) {
				DictEntryInfo dictEntryInfo2 = dictionaryService.getDictEntryById("ORG_BIZ_TYPE",
						orginfo.getBusiOrg());
				if (dictEntryInfo2 != null) {
					orginfo.setBusiOrgName(dictEntryInfo2.getName());
				}
			}
			this.formatData(this.addPrefix(orginfo, "org."));
		} catch (Exception e) {
			ServletActionContext.getRequest().setAttribute("throw", e);
			logger.error(e.getMessage(), e);
		}
		return SUCCESS;
	}

	/**
	 * 初始化组织管理页面的form表单方法
	 * 
	 * @Title: initOrgFromControl
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 */
	@SuppressWarnings("unchecked")
	public String initOrgFromControl() {
		List list = areaService.getAreas();
		try {
			// 设置树控件数据映射关系
			RuleTree wtree = new RuleTree();
			wtree.setId("area");
			wtree.setVal("areaName");
			wtree.setPid("upArea");

			// 格式化树控件数据,按树控件的ID，VAL，PID的顺序
			List<Map> l = this.initTreeData(list, wtree);
			this.formatData(l);
		} catch (Exception e) {
			ServletActionContext.getRequest().setAttribute("throw", e);
			// e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		return SUCCESS;
	}

	/**
	 * 异步请求查询组织树
	 * 
	 * @Title: initGradeOrgTree
	 * @Description: 异步请求查询组织树
	 */
	@SuppressWarnings("unchecked")
	public String initGradeOrgTree() {

		HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(
				ServletActionContext.HTTP_REQUEST);
		String pid = request.getParameter("id");
		String roleId = request.getParameter("roleID");
		try {
			Pagination page = new Pagination(1, 1000);
			Org o = new Org();
			if(pid==null) {
				pid = "-1";
			}else if(StringUtil.isNumber(pid)) {
				o.setParentOrgId(new Long(pid));
			}
			// 设置组织数据权限
			this.setOrgDataRight(o, request.getParameter("moduleId"), request.getParameter("modeId"));
			List<Org> list = orgService.queryOrgs(o, page).getList();
			if("-1".equals(pid)) {
				list = this.initFirstNode(list, true);
			}
			if(StringUtil.isNumber(roleId)) {
				Pagination result = orgService.queryOrgMembers(Long.parseLong(roleId), o, page);
				this.formatData(this.initTreeAsynData(list, result.getList()));
			} else {
				this.formatData(this.initTreeAsynData(list));
			}
		} catch (Exception e) {
			ServletActionContext.getRequest().setAttribute("throw", e);
			logger.error(e.getMessage(), e);
		}
		return SUCCESS;
	}
	
	/**
	 * 异步请求查询组织树
	 * 
	 * @Title: initGradeOrgTree
	 * @Description: 异步请求查询组织树
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String initBusinessOrgTree() {
		
		HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(
				ServletActionContext.HTTP_REQUEST);
		String pid = request.getParameter("id");
		try {
			Org o = null;
			List<Map> treeList = new ArrayList<Map>();
			if(StringUtil.isNumber(pid)) {
				o = orgService.getOrg(new Long(pid));
				Org org = new Org();
				org.setBizParentId(new Long(pid));
				this.setOrgDataRight(org, request.getParameter("moduleId"), request.getParameter("modeId"));
				List<Org> list = null;
				if(o.getOrgType().equals(OrgConstants.ORG_TYPE_COMPANY)) {
					list = orgService.queryTopOrgsWithBusiness(org);
				} else {
					Pagination page = new Pagination(1, 1000);
					list = orgService.queryOrgsWithBusiness(org, page).getList();
				}
				if(list != null) {
					for(Org l : list) {
						Map map = new HashMap();
						map.put("id", l.getOrgId() + "");
						map.put("val", l.getOrgName());
						map.put("pid", pid);
						if (l.getIsLeaf() != null && !l.getIsLeaf()) {
							map.put("isleaf", false);
							map.put("asyn", "true");
						}
						treeList.add(map);
					}
				}
			}
			this.formatData(treeList);
		} catch (Exception e) {
			ServletActionContext.getRequest().setAttribute("throw", e);
			logger.error(e.getMessage(), e);
		}
		return SUCCESS;
	}

	/**
	 * 查找组织已关联角色角色
	 * 
	 * @Title: searchOrgRole
	 * @Description: 查找组织已关联角色角色
	 */
	@SuppressWarnings("unchecked")
	public String searchOrgRole() {
		HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(
				ServletActionContext.HTTP_REQUEST);
		String orgid = request.getParameter("orgid");
		try {
			if (pagination == null) {
				pagination = new Pagination(1, 10);
			}
			// else {
			// this.pagination.setRange((this.pagination.getCurrPage() - 1)
			// * this.pagination.getSize(), this.pagination.getSize());
			// }
			Pagination page = roleService.queryMemberRoles(new Long(orgid), MemberType.Org, new Role(), pagination);
			List list = page.getList();
			List l = new ArrayList();
			// 填充对应的属性名称
			for (int i = 0; i < list.size(); i++) {
				Role role = (Role) list.get(i);
				// 填充组织名称
				if (role.getOrgId() != null) {
					Org org = orgService.getOrg(role.getOrgId());
					if (org != null) {
						role.setOrgName(org.getOrgName());
					}
				}
				// 填充应用名称
				if (role.getAppId() != null) {
					App app = appService.getApp(role.getAppId());
					if (app != null) {
						role.setAppName(app.getAppName());
					}
				}
				l.add(role);
			}
			this.formatDatagirdData(list, page);
		} catch (Exception e) {
			ServletActionContext.getRequest().setAttribute("throw", e);
			logger.error(e.getMessage(), e);
		}
		return SUCCESS;
	}
	
    public String queryOrgTree()
    {
        HttpServletRequest request =
            (HttpServletRequest)ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);
        try
        {
            if (pagination == null)
            {
                pagination = new Pagination(1, 10000);
            }
            Org org = new Org();
            this.excludeNullProperties(org);
            if (org.getParentOrgId() != null && org.getParentOrgId() == -1)
            {
                org.setParentOrgId(null);
            }
            // 设置组织数据权限
            
            Pagination page = orgService.queryOrgs(org, pagination);
            List list = page.getList();
            List result = new ArrayList();
            for (int i = 0; i < list.size(); i++)
            {
                Org o = (Org)list.get(i);
                String areaid = o.getArea();
                if (areaid != null && !"".equals(areaid))
                {
                    Area a = areaService.getArea(areaid);
                    if (a != null)
                    {
                        o.setAreaName(a.getAreaName());
                    }
                }
                DictEntryInfo dictEntryInfo = dictionaryService.getDictEntryById("ORG_TYPE", o.getOrgType());
                if (dictEntryInfo != null)
                {
                    o.setOrgTypeName(dictEntryInfo.getName());
                }
                result.add(o);
            }
            RuleTree wtree = new RuleTree();
            wtree.setId("orgId");
            wtree.setVal("orgName");
            wtree.setPid("parentOrgId");
            List<Map> l = this.initTreeData(list, wtree);
            this.formatData(l);
        }
        catch (Exception e)
        {
            ServletActionContext.getRequest().setAttribute("throw", e);
            logger.error(e.getMessage(), e);
        }
        return SUCCESS;
    }

	/**
	 * 初始化组织新增角色的form表单数据
	 * 
	 * @Title: initUserRoleForm
	 * @Description: 初始化组织新增角色的form表单数据
	 * @return SUCCESS
	 */

	@SuppressWarnings("unchecked")
	public String initUserRoleForm() {
		// 定义一个总的表单初始化属性对象
		List listData = new ArrayList();
		try {
			// 设置下拉列表属性规则
			RuleSelect ruleSelect = new RuleSelect();
			ruleSelect.setText("appName");
			ruleSelect.setValue("appId");
			// 根据字典类型取用户类型字典数据
			List<App> applist = appService.getApps();
			// 设置表单取值类型初始化属性
			listData.add(this.initformAttribute("appid", this.initSelectData(applist, ruleSelect)));

		} catch (BizBaseException e) {
			ServletActionContext.getRequest().setAttribute("throw", e);
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			ServletActionContext.getRequest().setAttribute("throw", e);
			logger.error(e.getMessage(), e);
		}
		// 格式化数据
		this.formatData(listData);
		return SUCCESS;
	}

	/**
	 * 查询用户未关联的角色
	 * 
	 * @Title: queryNoRelRoles
	 * @Description: 查询用户未关联的角色
	 * @return SUCCESS
	 */
	@SuppressWarnings("unchecked")
	public String queryNoRelRoles() {
		HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(
				ServletActionContext.HTTP_REQUEST);
		if(role == null)
			role = new Role();
		String ownorgid = request.getParameter("ownorgid");
		String orgid = request.getParameter("orgid");
//		String appid = request.getParameter("appid");
//		String rolename = request.getParameter("rolename");

		try {
//			Role role = new Role();
//			if (orgid != null && !"".equals(orgid)) {
//				role.setOrgId(new Long(orgid));
//			}
//			if (appid != null && !"".equals(appid)) {
//				role.setAppId(new Long(appid));
//			}
//			if (rolename != null && !"".equals(rolename)) {
//				role.setRoleName(rolename);
//			}
			if (pagination == null) {
				pagination = new Pagination(1, 10);
			}
			// } else {
			// this.pagination.setRange((this.pagination.getCurrPage() - 1)
			// * this.pagination.getSize(), this.pagination.getSize());
			// }

			// 设置数据权限
			role.setCurrentUserId(this.getLongUserID());
			if(!StringUtil.isEmpty(orgid)) {
				role.setOrgId(Long.valueOf(orgid));
			}
			role.setPermissionId(LogConfigParam.MODULE_ROLE_ID, RightConstants.MODE_VIEW);
			Pagination page = roleService.queryMemberNoRoles(new Long(ownorgid), MemberType.Org, role, pagination);
			List list = page.getList();
			List l = new ArrayList();
			// 填充对应的属性名称
			for (int i = 0; i < list.size(); i++) {
				role = (Role) list.get(i);
				// 填充组织名称
				if (role.getOrgId() != null) {
					Org org = orgService.getOrg(role.getOrgId());
					if (org != null) {
						role.setOrgName(org.getOrgName());
					}
				}
				// 填充应用名称
				if (role.getAppId() != null) {
					App app = appService.getApp(role.getAppId());
					if (app != null) {
						role.setAppName(app.getAppName());
					}
				}
				l.add(role);
			}
			this.formatDatagirdData(l, page);
		} catch (Exception e) {
			ServletActionContext.getRequest().setAttribute("throw", e);
			logger.debug(e.getMessage(), e);
		}
		return SUCCESS;
	}

	/**
	 * 添加组织角色
	 * 
	 * @Title: addOrgRole
	 * @Description: 添加组织角色
	 */
	public String addOrgRole() {

		HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(
				ServletActionContext.HTTP_REQUEST);
		String orgID = request.getParameter("orgID");
		String roleIDStr = request.getParameter("roles");
		try {
			String[] roleids = roleIDStr.split(",");
			List<Member> list = new ArrayList<Member>();
			for (int i = 0; i < roleids.length; i++) {
				Member usermember = new OrgMember(new Long(orgID), new Long(roleids[i]));
				list.add(usermember);
			}
			roleService.addRoleMembers(list);
			this.setMessage(1, "ORG", "RELROLE");
		} catch (Exception e) {
			ServletActionContext.getRequest().setAttribute("throw", e);
			this.setMessage(0, "ORG", "RELROLE");
			logger.error(e.getMessage(), e);
		}
		// IData data = new DataMap();
		// data.put("orgID", orgID);
		// data.put("roleIDStr", roleIDStr);
		// addSystemLog(LogConfigParam.MODULE_ORG_ID,LogConfigParam.MODULE_ORG_NAME,LogConfigParam.OPERATOR_TYPE_ADD,
		// jsonToString(JsonObject.toString(data)),
		// this.pageData.get("msg").toString());
		return SUCCESS;
	}

	/**
	 * 批量删除组织角色
	 * 
	 * @Title: moveOrgRole
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 */
	public String moveOrgRole() {
		HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(
				ServletActionContext.HTTP_REQUEST);
		String orgID = request.getParameter("orgID");
		String roleIDStr = request.getParameter("roles");
		try {
			String[] roleids = roleIDStr.split(",");
			List<Member> list = new ArrayList<Member>();
			for (int i = 0; i < roleids.length; i++) {
				Member usermember = new OrgMember(new Long(orgID), new Long(roleids[i]));
				list.add(usermember);
			}
			roleService.deleteRoleMembers(list);
			this.setMessage(1, "ORGROLE", "DELETE");
		} catch (Exception e) {
			ServletActionContext.getRequest().setAttribute("throw", e);
			this.setMessage(0, "ORGROLE", "DELETE");
			// e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		// IData data = new DataMap();
		// data.put("orgID", orgID);
		// data.put("roleIDStr", roleIDStr);
		// addSystemLog(LogConfigParam.MODULE_ORG_ID,LogConfigParam.MODULE_ORG_NAME,LogConfigParam.OPERATOR_TYPE_DELETE,
		// jsonToString(JsonObject.toString(data)),
		// this.pageData.get("msg").toString());

		return SUCCESS;
	}

	public String initOrgAreaTree() {
		try {
			List arealist = areaService.getAreas();
			RuleTree wtree = new RuleTree();
			wtree.setId("area");
			wtree.setVal("areaName");
			wtree.setPid("upArea");
			this.formatData(this.initTreeData(arealist, wtree));
		} catch (Exception e) {
			ServletActionContext.getRequest().setAttribute("throw", e);
			logger.error(e.getMessage(), e);
			this.setErrorMessage(e);
		}
		return SUCCESS;
	}

	/**
	 * 初始化业务上级 <li>公司没有业务上级，只有部门有业务上级</li> <li>业务上级的数据来源，该部门所归属公司的上级公司的部门列表</li>
	 * 
	 * @return
	 * @throws Exception 
	 */
    public String initBizParentOrg() throws Exception
    {
        HttpServletRequest request = ServletActionContext.getRequest();
        List list = null;
        String parentId = request.getParameter("parentId");
        if (parentId != null && !parentId.equals("-1"))
        {
            Org company = orgService.getCompany(Long.valueOf(parentId));
            if ((company != null) && (company.getParentOrgId()!=-1))
            {
                company = orgService.getCompany(company.getParentOrgId());
                if (company != null)
                {
                    Org param = new Org();
                    param.setParentOrgId(company.getOrgId());
                    param.setOrgType(OrgConstants.ORG_TYPE_DEPT);
                    list = orgService.queryOrgs(param);
                }
            }
        }
        if (list == null)
        {
            list = new ArrayList();
        }
        RuleSelect ruleSelect = new RuleSelect();
        ruleSelect.setText("orgName");
        ruleSelect.setValue("orgId");
        this.formatData(this.initSelectData(list, ruleSelect));
        return SUCCESS;
    }

	/**
	 * 初始化组织树
	 * 
	 * @return
	 */
	public String initFirstGradeOrgTree() {
		try {
			HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(
					ServletActionContext.HTTP_REQUEST);
			String orgID = request.getParameter("orgID");
			String roleId = request.getParameter("roleID");
			String resourceId = request.getParameter("moduleId");
			String modeId = request.getParameter("modeId");
			org = new Org();
			List<Org> orgTreeData = null;
			
			Permission permission = permissionService.getPermission(resourceId,modeId);
			List<Rule> dataRules = null;
			if (permission != null)
			{
			    Long userId = (Long)getUserSession().get("id");
			    dataRules = authorityService.getScopeRules(userId,permission.getPermissionId());
			}
			if (dataRules != null && dataRules.size() > 0)
			{
			    setOrgDataRight(org,resourceId,modeId);
	            pagination = new Pagination(1, 10000);
	            orgTreeData = orgService.queryOrgs(org, pagination).getList();
			}
			else
			{
			    if(orgID == null) {
	                orgID = "-1";
	            }
	            if (StringUtil.isNumber(orgID)) {
	                org.setOrgId(Long.parseLong(orgID));
	                orgTreeData = orgService.getParentAndSiblingOrgs(org);
	            } else {
	                org.setParentOrgId(-1L);
	                pagination = new Pagination(1, 10000);
	                orgTreeData = orgService.queryOrgs(org, pagination).getList();
	            }
			}
			
			orgTreeData = this.initFirstNode(orgTreeData, false);
			if(StringUtil.isNumber(roleId)) {
				Pagination result = orgService.queryOrgMembers(Long.parseLong(roleId), org, pagination);
				this.formatData(this.initTreeAsynData(orgTreeData, result.getList()));
			} else {
				this.formatData(this.initTreeAsynData(orgTreeData));
			}
		} catch (Exception e) {
			ServletActionContext.getRequest().setAttribute("throw", e);
			logger.error(e.getMessage(), e);
			this.setErrorMessage(e);
		}
		return SUCCESS;
	}
	
	protected List<Map> initTreeAsynData(List<Org> list, List<Org> roleList) {
		List<Map> l = new ArrayList<Map>();
		if (list != null) {
			for (Org org : list) {
				Map map = new HashMap();
				map.put("id", org.getOrgId());
				map.put("val", org.getOrgName());
				map.put("pid", org.getParentOrgId());
				map.put("chk", "false");
				if (org.getIsLeaf() != null && !org.getIsLeaf()) {
					map.put("isleaf", false);
					map.put("asyn", "true");
				}
				if (roleList.contains(org)) {
					map.put("font", "tree-selected");
					map.put("chk", "true");
				}
				l.add(map);
			}
		}
		return l;
	}
	
	public String initFirstBusinessOrgTree() {
		try {
			HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(
					ServletActionContext.HTTP_REQUEST);
			org = new Org();
			this.setOrgDataRight(org, request.getParameter("moduleId"), request.getParameter("modeId"));
			List<Org> orgTreeData = orgService.getTopCompanyWithBusiness(org);
			this.formatData(this.initTreeAsynData(orgTreeData));
		} catch (Exception e) {
			ServletActionContext.getRequest().setAttribute("throw", e);
			logger.error(e.getMessage(), e);
			this.setErrorMessage(e);
		}
		return SUCCESS;
	}

	private List<Org> initFirstNode(List<Org> orgs, boolean filter) {
		List<Long> ids = new ArrayList<Long>();
		for (Org org : orgs) {
			if (!ids.contains(org.getOrgId())) {
				ids.add(org.getOrgId());
			}
		}
		List<Org> list = new ArrayList<Org>();
		for (Org org : orgs) {
			if (!ids.contains(org.getParentOrgId())) {
				org.setParentOrgId(-1l);
				list.add(org);
			} else if(!filter) {
				list.add(org);
			}
		}
		return list;
	}

	/**
	 * 初始化组织表单
	 * */
	@SuppressWarnings("unchecked")
	public String initOrgForm() {
		HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(
				ServletActionContext.HTTP_REQUEST);
		String orgID = request.getParameter("orgID");
		List listData = new ArrayList();
		try {
			// 设置下拉列表属性规则
			RuleSelect ruleSelect = new RuleSelect();
			ruleSelect.setText("name");
			ruleSelect.setValue("dictid");
			// 根据字典类型取组织类型类型字典数据
			List<DictEntryInfo> orgtype = dictionaryService.getDictEntrysByType("ORG_TYPE");
			// 设置表单取值类型初始化属性
			listData.add(this.initformAttribute("org.orgType", this.initSelectData(orgtype, ruleSelect)));
			// 根据字典类型取组织等级类型字典数据
			List<DictEntryInfo> busorg = dictionaryService.getDictEntrysByType("ORG_BIZ_TYPE");
			// 设置表单取值类型初始化属性
			listData.add(this.initformAttribute("org.busiOrg", this.initSelectData(busorg, ruleSelect)));
			Org company = null;
			if(orgID != null && !"-1".equals(orgID))
			{
				Org org = orgService.getOrg(new Long(orgID));
				if(org.getOrgType().equals(OrgConstants.ORG_TYPE_DEPT))
				{
					company = orgService.getCompany(Long.valueOf(orgID));
				}
			}
			else
			{
			    String parentOrgId = request.getParameter("parentOrgId");
			    if (parentOrgId != null)
			    {
			        company = orgService.getCompany(Long.valueOf(parentOrgId));
			    }
			}
			if (company != null && company.getParentOrgId()!=-1)
			{
				company = orgService.getCompany(company.getParentOrgId());
				if (company != null)
				{
				    Org param = new Org();
				    param.setParentOrgId(company.getOrgId());
				    param.setOrgType(OrgConstants.ORG_TYPE_DEPT);
				    List<Org> orgList = orgService.queryOrgs(param);
					if (orgList != null)
					{
						RuleSelect orgRuleSelect = new RuleSelect();
						orgRuleSelect.setText("orgName");
						orgRuleSelect.setValue("orgId");
						listData.add(this.initformAttribute("org.bizParentId",
						    initSelectData(orgList, orgRuleSelect)));
					}
				}
			}
			this.formatData(listData);
		} catch (Exception e) {
			ServletActionContext.getRequest().setAttribute("throw", e);
			logger.error(e.getMessage(), e);
		}
		return SUCCESS;
	}

	/**
	 * 分页查询角色下组织成员
	 * **/
	@SuppressWarnings("unchecked")
	public String searchRoleOrg() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String roleId = request.getParameter("roleID");
		if (StringUtil.isNumber(roleId)) {
			if (org == null)
				org = new Org();
			List<DictEntryInfo> dictEntrys = null;
			try {
				dictEntrys = dictionaryService.getDictEntrysByType("ORG_TYPE");
			} catch (BizBaseException e) {
				ServletActionContext.getRequest().setAttribute("throw", e);
				logger.error(e.getMessage(), e);
			}
			this.setOrgDataRight(org, request.getParameter("moduleId"), request.getParameter("modeId"));
			Pagination result = orgService.queryOrgMembers(Long.parseLong(roleId), org, pagination);
			List list = result.getList();
			for (Object o : list) {
				Org g = (Org) o;
				if (dictEntrys != null && dictEntrys.size() > 0)
					for (DictEntryInfo d : dictEntrys) {
						if (g.getOrgType().equals(d.getDictid())) {
							g.setOrgTypeName(d.getName());
							break;
						}
					}
			}
			this.formatDatagirdData(list, result);
		}
		return SUCCESS;
	}

	/**
	 * 分页查询角色下没有的组织成员
	 * */
	@SuppressWarnings("unchecked")
	public String queryNoOrgMembers() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String roleId = request.getParameter("roleID");
		if (StringUtil.isNumber(roleId)) {
			if (org == null)
				org = new Org();
			List<DictEntryInfo> dictEntrys = null;
			try {
				dictEntrys = dictionaryService.getDictEntrysByType("ORG_TYPE");
			} catch (BizBaseException e) {
				ServletActionContext.getRequest().setAttribute("throw", e);
				logger.error(e.getMessage(), e);
			}
			this.setOrgDataRight(org, request.getParameter("moduleId"), request.getParameter("modeId"));
			Pagination result = orgService.queryNoOrgMembers(Long.parseLong(roleId), org, pagination);
			List list = result.getList();
			for (Object o : list) {
				Org g = (Org) o;
				if (dictEntrys != null && dictEntrys.size() > 0)
					for (DictEntryInfo d : dictEntrys) {
						if (g.getOrgType().equals(d.getDictid())) {
							g.setOrgTypeName(d.getName());
							break;
						}
					}
			}
			this.formatDatagirdData(list, result);
		}
		org = null;
		return SUCCESS;
	}

	// /**
	// * 初始化权限视图树
	// *
	// * @title initRightViewTree
	// * @description 初始化权限视图树
	// *
	// * */
	// @SuppressWarnings("unchecked")
	// public String initRightViewTree(){
	// HttpServletRequest request = (HttpServletRequest)
	// ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);
	// String orgid = request.getParameter("orgid");
	// List<Map> l = new ArrayList<Map>();
	// try {
	// List<ResourceTree> reslist = resourceService.getResourceTrees();
	// List<ResourceTree> result = authorityService.filterResourceTrees(
	// new Long(orgid), reslist, null, MemberType.Org);
	// List<Map> list = TreeUtils.transforRT2Maps(result);
	//
	// for (int i = 0; i < list.size(); i++) {
	// Map nm = new HashMap();
	// Map m = list.get(i);
	// nm.put("id", m.get("id"));
	// nm.put("val", m.get("name"));
	// nm.put("pid", m.get("parent"));
	// nm.put("type", m.get("type"));
	// l.add(nm);
	// }
	// } catch (Exception e) {
	// ServletActionContext.getRequest().setAttribute("throw", e);
	// logger.error(e.getMessage(), e);
	// }
	// this.formatData(l);
	// return SUCCESS;
	// }
	//
	// /**
	// * 显示用户权限上的数据权限
	// *
	// * @title showUserDataRight
	// * @description 显示用户权限上的数据权限
	// *
	// * */
	// @SuppressWarnings("unchecked")
	// public String showOrgDataRight(){
	// HttpServletRequest request = (HttpServletRequest)
	// ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);
	// String id = request.getParameter("id");
	// String orgid = request.getParameter("orgid");
	// try {
	// id = java.net.URLDecoder.decode(id,"UTF-8");
	// String[] ids = id.split("#");
	// List<Scope> list = authorityService.getPrivilegeScopes(new Long(
	// orgid), ids[0], ids[1], MemberType.Org);
	// List<Map> res = new ArrayList<Map>();
	// for (int i = 0; i < list.size(); i++) {
	// Scope scope = list.get(i);
	// Map map = new HashMap();
	// map.put("ruleId", scope.getRuleId());
	// map.put("ruleName", scope.getDataRule().getRuleName());
	// Long appid = scope.getDataRule().getAppId();
	// if (appid != null && appService.getApp(appid) != null) {
	// App app = appService.getApp(appid);
	// map.put("appName", app.getAppName());
	// } else {
	// map.put("appName", null);
	// }
	// if (scope.getValueType() == 1) {
	// map.put("ruleValue", scope.getMinValue() + ","
	// + scope.getMaxValue());
	// } else if (scope.getValueType() == 2) {
	// map.put("ruleValue", scope.getRuleValue());
	// } else {
	// map.put("ruleValue", null);
	// }
	// res.add(map);
	// }
	// Pagination page = null;
	// if (list != null && list.size() > 10) {
	// page = new Pagination(1, list.size());
	// } else {
	// page = new Pagination(1, 10);
	// }
	//
	// this.formatDatagirdData(res, page);
	// } catch (Exception e) {
	// ServletActionContext.getRequest().setAttribute("throw", e);
	// logger.error(e.getMessage(), e);
	// }
	// return SUCCESS;
	// }

	/**
	 * 设置组织数据权限
	 * **/
	private void setOrgDataRight(Org org, String moduleId, String modeId) {
		if (StringUtil.isEmpty(moduleId)) {
			moduleId = LogConfigParam.MODULE_ORG_ID;
		}
		if (StringUtil.isEmpty(modeId)) {
			modeId = RightConstants.MODE_VIEW;
		}
		org.setCurrentUserId(this.getLongUserID());
		org.setPermissionId(moduleId, modeId);
	}

	private void setOrgLevel(Org org) {
		if (org.getParentOrgId() == -1)
			org.setOrgLevel(1);
		else {
			int level = orgService.getOrgLevel(org.getParentOrgId());
			org.setOrgLevel(level + 1);
		}
	}
}
