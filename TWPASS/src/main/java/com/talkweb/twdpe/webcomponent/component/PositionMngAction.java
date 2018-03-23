package com.talkweb.twdpe.webcomponent.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.talkweb.twdpe.base.dict.pojo.DictEntryInfo;
import com.talkweb.twdpe.base.dict.service.DictionaryService;
import com.talkweb.twdpe.base.log.pojo.LogConfigParam;
import com.talkweb.twdpe.base.org.pojo.Org;
import com.talkweb.twdpe.base.org.pojo.Position;
import com.talkweb.twdpe.base.org.pojo.User;
import com.talkweb.twdpe.base.org.service.OrgService;
import com.talkweb.twdpe.base.org.service.PositionService;
import com.talkweb.twdpe.base.org.service.UserService;
import com.talkweb.twdpe.base.right.constant.RightConstants;
import com.talkweb.twdpe.base.right.pojo.App;
import com.talkweb.twdpe.base.right.pojo.Member;
import com.talkweb.twdpe.base.right.pojo.OrgMember;
import com.talkweb.twdpe.base.right.pojo.Member.MemberType;
import com.talkweb.twdpe.base.right.pojo.PositionMember;
import com.talkweb.twdpe.base.right.pojo.Role;
import com.talkweb.twdpe.base.right.service.AppService;
import com.talkweb.twdpe.base.right.service.AuthorityService;
import com.talkweb.twdpe.base.right.service.ResourceService;
import com.talkweb.twdpe.base.right.service.RoleService;
import com.talkweb.twdpe.core.data.DataMap;
import com.talkweb.twdpe.core.data.IData;
import com.talkweb.twdpe.core.exception.BizBaseException;
import com.talkweb.twdpe.core.log.Logger;
import com.talkweb.twdpe.core.log.LoggerFactory;
import com.talkweb.twdpe.core.util.StringUtil;
import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.twdpe.web.jcontrols.RuleSelect;
import com.talkweb.twdpe.web.jcontrols.RuleTree;
import com.talkweb.twdpe.web.session.main.Session;
import com.talkweb.twdpe.web.session.util.SessionFactory;
import com.talkweb.twdpe.webcomponent.common.action.BusinessAction;


/**
 * 文件名称: PositionMngAction.java 内容摘要:
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
public class PositionMngAction extends BusinessAction {
    /**
	 * 
	 */
    private static final long serialVersionUID = 4002889027742924154L;

    private static final Logger logger = LoggerFactory.getLogger(PositionMngAction.class);

    /**
     * 岗位管理对像，用于岗位的新增、修改的封装
     * */
    private Position position = null;

    /**
     * 通过注入方式初始化的岗位管理类,用于岗位管理的增，删，改，查的数据库操作类
     * */
    @Autowired
    private PositionService positionService;
    /**
     * 通过注入方式初始化的组织管理类,用于组织管理的增，删，改，查的数据库操作类
     * */
    @Autowired
    private OrgService orgService;

    /**
     * 通过注入方式初始化的字典管理类,用于字典管理的增，删，改，查的数据库操作类
     * */
    @Autowired
    private DictionaryService dictionaryService;
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
     * 通过注入方式初始化的 授权管理服务管理类
     * */
    @Autowired
    private AuthorityService authorityService;
    /**
     * 通过注入方式初始化的 资源管理类
     * */
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private UserService userService;

    private Role role = null;


    public Role getRole() {
        return role;
    }


    public void setRole(Role role) {
        this.role = role;
    }


    public void setPosition(Position position) {
        this.position = position;
    }


    public Position getPosition() {
        return this.position;
    }


    /**
     * 添加岗位
     * 
     * @Title: addPosition
     * @Description: 添加岗位
     * @return
     */
    @SuppressWarnings("unchecked")
    public String addPosition() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        Session session = SessionFactory.getInstance(request, response);
        try {
            Map<String, Object> userMap = (Map) session.getAttribute("USERSESSION");
            if (userMap == null) {
                this.setErrorMessage("用户Session已过期");
            }
            else {
                String userid = userMap.get("id").toString();
                position.setUpdator(new Long(userid));
                positionService.addPosition(position);

                this.setMessage(1, "POSITION", "ADD");
            }
        }
        catch (Exception e) {
            ServletActionContext.getRequest().setAttribute("throw", e);
            this.setMessage(0, "POSITION", "ADD", e);
            logger.error(e.getMessage(), e);
            // e.printStackTrace();
        }
        // addSystemLog(LogConfigParam.MODULE_POSITION_ID,LogConfigParam.MODULE_POSITION_NAME,LogConfigParam.OPERATOR_TYPE_ADD,
        // jsonToString(JsonObject.toString(position)),
        // this.pageData.get("msg").toString());
        position = null;
        return SUCCESS;
    }


    /**
     * 
     * @Title: pgetPosition
     * @Description: 查找岗位信息
     * @return
     */
    @SuppressWarnings("unchecked")
    public String getPositionInfo() {
        HttpServletRequest request =
                (HttpServletRequest) ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);
        String id = request.getParameter("positionid");
        try {
            Position pos = positionService.getPosition(new Long(id));
            // 填充组织名称
            if (pos.getOrgId() != null) {
                Org org = orgService.getOrg(pos.getOrgId());
                if (org != null) {
                    pos.setOrgName(org.getOrgName());
                }
            }
            // 填充岗位类型名称
            if (pos.getPosiType() != null) {
                DictEntryInfo dictEntryInfo =
                        dictionaryService.getDictEntryById("ORG_POSITION_TYPE", pos.getPosiType());
                if (dictEntryInfo != null) {
                    pos.setPosiTypeName(dictEntryInfo.getName());
                }
            }
            // 填充岗位状态
            DictEntryInfo dictEntryInfo2 =
                    dictionaryService.getDictEntryById("DICT_STATUS", pos.getStatus().toString());
            if (dictEntryInfo2 != null) {
                pos.setStatusName(dictEntryInfo2.getName());
            }
            this.formatData(this.addPrefix(pos, "position."));
        }
        catch (Exception e) {
            ServletActionContext.getRequest().setAttribute("throw", e);
            logger.error(e.getMessage(), e);
        }
        return SUCCESS;
    }


    /**
     * 根据查询条件查询岗位列表
     * 
     * @Title: queryPositions
     * @Description: 根据查询条件查询岗位列表
     */
    @SuppressWarnings("unchecked")
    public String queryPositions() {
        HttpServletRequest request = ServletActionContext.getRequest();
        if (position == null) {
            position = new Position();
        }
        else {
            this.excludeNullProperties(position);
        }

        if (pagination == null) {
            pagination = new Pagination(1, 10);
        }
        // else {
        // this.pagination.setRange((this.pagination.getCurrPage() - 1)
        // * this.pagination.getSize(), this.pagination.getSize());
        // }
        // 设置岗位数据权限
        this.setPositionDataRight(position, request.getParameter("moduleId"), request.getParameter("modeId"));

        try {

            Pagination page = null;
            if (position.getOrgId() == null) {
                page = positionService.queryPositions(position, pagination);
            }
            else {
                page = positionService.queryPositions(position.getOrgId(), position, pagination);
            }
            List poslist = page.getList();
            List list = new ArrayList();
            for (int i = 0; i < poslist.size(); i++) {
                Position pos = (Position) poslist.get(i);
                /*
                 * // 填充组织名称 if (pos.getOrgId() != null) { Org org =
                 * orgService.getOrg(pos.getOrgId()); if (org != null) {
                 * pos.setOrgName(org.getOrgName()); } }
                 */
                // 填充岗位类型名称
                if (pos.getPosiType() != null) {
                    DictEntryInfo dictEntryInfo =
                            dictionaryService.getDictEntryById("ORG_POSITION_TYPE", pos.getPosiType());
                    if (dictEntryInfo != null) {
                        pos.setPosiTypeName(dictEntryInfo.getName());
                    }
                }
                // 填充岗位状态
                if (pos.getStatus() != null) {
                    DictEntryInfo dictEntryInfo2 =
                            dictionaryService.getDictEntryById("DICT_STATUS", pos.getStatus().toString());
                    if (dictEntryInfo2 != null) {
                        pos.setStatusName(dictEntryInfo2.getName());
                    }
                }
                list.add(pos);
            }
            this.formatDatagirdData(list, page);
        }
        catch (Exception e) {
            request.setAttribute("throw", e);
            logger.error(e.getMessage(), e);
        }
        position = null;
        return SUCCESS;
    }


    public String queryPositionTree() {

        HttpServletRequest request = ServletActionContext.getRequest();
        if (position == null) {
            position = new Position();
        }
        else {
            this.excludeNullProperties(position);
        }

        if (pagination == null) {
            pagination = new Pagination(1, 10000);
        }
        this.setPositionDataRight(position, request.getParameter("moduleId"), request.getParameter("modeId"));

        try {

            Pagination page = null;
            if (position.getOrgId() == null) {
                page = positionService.queryPositions(position, pagination);
            }
            else {
                page = positionService.queryPositions(position.getOrgId(), position, pagination);
            }
            List poslist = page.getList();
            List list = new ArrayList();
            for (int i = 0; i < poslist.size(); i++) {
                Position pos = (Position) poslist.get(i);
                /*
                 * // 填充组织名称 if (pos.getOrgId() != null) { Org org =
                 * orgService.getOrg(pos.getOrgId()); if (org != null) {
                 * pos.setOrgName(org.getOrgName()); } }
                 */
                // 填充岗位类型名称
                if (pos.getPosiType() != null) {
                    DictEntryInfo dictEntryInfo =
                            dictionaryService.getDictEntryById("ORG_POSITION_TYPE", pos.getPosiType());
                    if (dictEntryInfo != null) {
                        pos.setPosiTypeName(dictEntryInfo.getName());
                    }
                }
                // 填充岗位状态
                if (pos.getStatus() != null) {
                    DictEntryInfo dictEntryInfo2 =
                            dictionaryService.getDictEntryById("DICT_STATUS", pos.getStatus().toString());
                    if (dictEntryInfo2 != null) {
                        pos.setStatusName(dictEntryInfo2.getName());
                    }
                }
                list.add(pos);
            }
            RuleTree wtree = new RuleTree();
            wtree.setId("positionId");
            wtree.setVal("posiName");
            wtree.setPid("manaPosi");
            Map<String, String> map = new HashMap<String, String>();
            map.put("orgId", "orgId");
            wtree.setMap(map);
            List<Map> l = this.initTreeData(list, wtree);
            this.formatData(l);

        }
        catch (Exception e) {
            request.setAttribute("throw", e);
            logger.error(e.getMessage(), e);
        }
        position = null;
        return SUCCESS;
    }


    /**
     * 修改岗位
     * 
     * @Title: modifyPosition
     * @Description: 修改岗位
     * @return
     */
    @SuppressWarnings("unchecked")
    public String modifyPosition() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        Session session = SessionFactory.getInstance(request, response);
        try {
            Map<String, Object> userMap = (Map) session.getAttribute("USERSESSION");
            if (userMap == null) {
                this.setErrorMessage("用户Session已过期");
            }
            else {
                String userid = userMap.get("id").toString();
                position.setUpdator(new Long(userMap.get("id").toString()));
                positionService.modifyPosition(position);
                this.setMessage(1, "POSITION", "MODIFY");
            }
        }
        catch (Exception e) {
            ServletActionContext.getRequest().setAttribute("throw", e);
            this.setMessage(0, "POSITION", "MODIFY", e);
            logger.error(e.getMessage(), e);
            // e.printStackTrace();
        }
        // addSystemLog(LogConfigParam.MODULE_POSITION_ID,LogConfigParam.MODULE_POSITION_NAME,LogConfigParam.OPERATOR_TYPE_MODIFY,
        // jsonToString(JsonObject.toString(position)),
        // this.pageData.get("msg").toString());
        position = null;
        return SUCCESS;
    }


    /**
     * 查询岗位已经关联的角色列表
     * 
     * @Title: queryRoles
     * @Description: 查询岗位已经关联的角色列表
     */
    @SuppressWarnings("unchecked")
    public String queryHasRelRoles() {
        HttpServletRequest request =
                (HttpServletRequest) ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);
        try {
            if (pagination == null) {
                pagination = new Pagination(1, 10);
            }
            String positionid = request.getParameter("positionid");
            Pagination page =
                    roleService.queryMemberRoles(new Long(positionid), MemberType.Position, new Role(),
                        pagination);
            List list = page.getList();
            List l = new ArrayList();
            // 填充对应的属性名称
            for (int i = 0; i < list.size(); i++) {
                Role role = (Role) list.get(i);
                // 填充组织名称
                if (role.getOrgId() != null) {
                    Org org = orgService.getOrg(role.getOrgId());
                    if (org != null && org.getOrgName() != null) {
                        role.setOrgName(org.getOrgName());
                    }
                }
                // 填充应用名称
                if (role.getAppId() != null) {
                    App app = appService.getApp(role.getAppId());
                    if (app != null && app.getAppName() != null) {
                        role.setAppName(app.getAppName());
                    }
                }
                l.add(role);
            }
            this.formatDatagirdData(l, page);
        }
        catch (Exception e) {
            ServletActionContext.getRequest().setAttribute("throw", e);
            logger.error(e.getMessage(), e);
        }
        return SUCCESS;
    }


    /**
     * 
     * @Title: addRoleWithposition
     * @Description: 岗位关联角色
     * @return
     */
    @SuppressWarnings("unchecked")
    public String addRoleWithposition() {
        HttpServletRequest request =
                (HttpServletRequest) ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);
        String positionid = request.getParameter("positionid");
        String roleid = request.getParameter("roleids");
        String[] roleids = StringUtils.split(roleid, ",");
        try {
            List<Member> list = new ArrayList<Member>();
            for (int i = 0; i < roleids.length; i++) {
                Member member = new PositionMember(new Long(positionid), new Long(roleids[i]));
                list.add(member);
            }
            roleService.addRoleMembers(list);
            this.setOkMessage("关联角色成功！");
        }
        catch (Exception e) {
            ServletActionContext.getRequest().setAttribute("throw", e);
            this.setErrorMessage("关联角色失败！, 错误原因：" + e.getMessage());
            logger.error(e.getMessage(), e);
        }
        // addSystemLog(LogConfigParam.MODULE_POSITION_ID,LogConfigParam.MODULE_POSITION_NAME,LogConfigParam.OPERATOR_TYPE_ADD,
        // jsonToString(JsonObject.toString(data)),
        // this.pageData.get("msg").toString());

        return SUCCESS;
    }


    /**
     * 
     * @Title: delHasRelRoles
     * @Description: 删除岗位已经关联的角色
     */
    @SuppressWarnings("unchecked")
    public String delHasRelRoles() {
        HttpServletRequest request =
                (HttpServletRequest) ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);
        String positionid = request.getParameter("positionid");
        String roleids = request.getParameter("id");
        Map map = new HashMap();
        try {
            String[] rids = StringUtils.split(roleids, ",");
            List<Member> list = new ArrayList<Member>();

            for (String roleid : rids) {
                Member member = new PositionMember(new Long(positionid), new Long(roleid));
                list.add(member);
            }
            roleService.deleteRoleMembers(list);

            this.setOkMessage("删除关联角色成功！");
        }
        catch (Exception e) {
            ServletActionContext.getRequest().setAttribute("throw", e);
            this.setErrorMessage("删除关联角色失败， 错误原因：" + e.getMessage());
            logger.debug(e.getMessage(), e);
        }
        // addSystemLog(LogConfigParam.MODULE_POSITION_ID,LogConfigParam.MODULE_POSITION_NAME,LogConfigParam.OPERATOR_TYPE_DELETE,
        // jsonToString(JsonObject.toString(data)),
        // this.pageData.get("msg").toString());

        return SUCCESS;
    }


    /**
     * 
     * @Title: queryNoRelRoles
     * @Description: 未关联的角色数据列表
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public String queryNoRelRoles() {
        HttpServletRequest request =
                (HttpServletRequest) ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);
        String positionid = request.getParameter("realpositionid");
        String orgid = request.getParameter("orgid");
        // String appid = request.getParameter("appid");
        // String rolename = request.getParameter("rolename");

        try {
            if (role == null) {
                role = new Role();
            }
            if (orgid != null && !"".equals(orgid)) {
                role.setOrgId(new Long(orgid));
            }
            // if (appid != null && !"".equals(appid)) {
            // role.setAppId(new Long(appid));
            // }
            // if (rolename != null && !"".equals(rolename)) {
            // role.setRoleName(rolename);
            // }
            if (pagination == null) {
                pagination = new Pagination(1, 10);
            }

            role.setCurrentUserId(this.getLongUserID());
            role.setPermissionId(LogConfigParam.MODULE_ROLE_ID, RightConstants.MODE_VIEW);

            Pagination page =
                    roleService.queryMemberNoRoles(new Long(positionid), MemberType.Position, role,
                        pagination);
            List list = page.getList();
            List l = new ArrayList();
            // 填充对应的属性名称
            for (int i = 0; i < list.size(); i++) {
                role = (Role) list.get(i);
                // 填充组织名称
                if (role.getOrgId() != null) {
                    Org org = orgService.getOrg(role.getOrgId());
                    if (org != null && org.getOrgName() != null) {
                        role.setOrgName(org.getOrgName());
                    }
                }
                // 填充应用名称
                if (role.getAppId() != null) {
                    App app = appService.getApp(role.getAppId());
                    if (app != null && app.getAppName() != null) {
                        role.setAppName(app.getAppName());
                    }
                }
                l.add(role);
            }
            this.formatDatagirdData(l, page);
        }
        catch (Exception e) {
            ServletActionContext.getRequest().setAttribute("throw", e);
            // e.printStackTrace();
            logger.debug(e.getMessage(), e);
        }
        return SUCCESS;
    }


    /**
     * 初始化岗位查询的字典表等数据(只填充下拉框)
     * 
     * @Title: initPositionSelect
     * @Description: 初始化岗位查询的字典表等数据(只填充下拉框)
     */
    @SuppressWarnings("unchecked")
    public String initPositionSelect() {
        // 设置下拉列表属性规则
        List list = new ArrayList();
        try {
            RuleSelect ruleSelect = new RuleSelect();
            ruleSelect.setText("name");
            ruleSelect.setValue("dictid");
            // 根据字典类型取用户类型字典数据
            List<DictEntryInfo> posiType = dictionaryService.getDictEntrysByType("ORG_POSITION_TYPE");
            // 设置表单取值类型初始化属性
            list.add(this.initformAttribute("position.posiType", this.initSelectData(posiType, ruleSelect)));
            // 根据字典类型取用户状态字典数据
            List<DictEntryInfo> status = dictionaryService.getDictEntrysByType("DICT_STATUS");
            // 设置表单关联类型初始化属性
            list.add(this.initformAttribute("position.status", this.initSelectData(status, ruleSelect)));
            this.formatData(list);
        }
        catch (BizBaseException e) {
            ServletActionContext.getRequest().setAttribute("throw", e);
            logger.error(e.getMessage(), e);
        }
        catch (Exception e) {
            ServletActionContext.getRequest().setAttribute("throw", e);
            logger.error(e.getMessage(), e);
        }
        this.formatData(list);
        return SUCCESS;
    }


    /**
     * 初始化组织树
     * 
     * @Title: initOrgTree
     * @Description: 初始化组织树
     */
    @SuppressWarnings("unchecked")
    public String initOrgTree() {
        List<Org> orglist = orgService.getOrgs();
        try {
            // 设置树控件数据映射关系
            RuleTree wtree = new RuleTree();
            wtree.setId("orgId");
            wtree.setVal("orgName");
            wtree.setPid("parentOrgId");

            // 格式化树控件数据,按树控件的ID，VAL，PID的顺序
            List<Map> l = this.initTreeData(orglist, wtree);
            this.formatData(l);
        }
        catch (Exception e) {
            ServletActionContext.getRequest().setAttribute("throw", e);
            // e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return SUCCESS;
    }


    /**
     * 
     * @Title: initRoleData
     * @Description: 初始化增加角色时的字典表等数据
     * @return
     */
    @SuppressWarnings("unchecked")
    public String initPositionData() {
        HttpServletRequest request =
                (HttpServletRequest) ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);
        String positionid = request.getParameter("positionid");
        List list = new ArrayList();
        try {
            RuleSelect ruleSelect = new RuleSelect();
            ruleSelect.setText("name");
            ruleSelect.setValue("dictid");
            // 根据字典类型取用户类型字典数据
            List<DictEntryInfo> posiType = dictionaryService.getDictEntrysByType("ORG_POSITION_TYPE");
            // 设置表单取值类型初始化属性
            list.add(this.initformAttribute("position.posiType", this.initSelectData(posiType, ruleSelect)));
            // 根据字典类型取用户状态字典数据
            List<DictEntryInfo> status = dictionaryService.getDictEntrysByType("DICT_STATUS");
            // 设置表单关联类型初始化属性
            list.add(this.initformAttribute("position.status", this.initSelectData(status, ruleSelect)));

            // 设置组织数据权限
            // Org o = new Org();
            // o.setCurrentUserId(this.getLongUserID());
            // o.setPermissionId(LogConfigParam.MODULE_ORG_ID,
            // RightConstants.MODE_VIEW);

            // List<Map> l = new ArrayList<Map>();
            Position position = positionService.getPosition(new Long(positionid));
            // if (orgId!=null && !orgId.equals("") && !orgId.equals("-1")){
            // Org p =
            // orgService.getTreeforOrgid(Long.parseLong(orgId));//先查到次根节点
            // List list =
            // orgService.getChildOrgsbySql(p.getOrgId());//再查及其全部子节点
            // l = initTreeAsynData(list);
            // }
            //
            // //2.得到根节点下包括的第一层子节点
            // //Org o = new Org();
            // o.setParentOrgId(Long. parseLong("-1"));
            //
            // Pagination pa = new Pagination(1, 1000);
            // pa = orgService.queryOrgs(o, pa);
            // List listRoot = pa.getList();
            // if (listRoot != null) {
            // for ( int i = 0; i < listRoot.size(); i++) {
            // Map map2 = new HashMap();
            // Org org = (Org) listRoot.get(i);
            // map2.put( "id", org.getOrgId());
            // map2.put( "val", org.getOrgName());
            // map2.put( "pid", org.getParentOrgId());
            // if (!org.getIsLeaf()) {
            // map2.put( "isleaf", false);
            // map2.put( "asyn", "1");
            // } //3.合并
            // l.add(map2);
            // }
            // }
            //
            // this.initformAttribute(map, "position.orgId", l);
            List<Org> orgTreeData = orgService.getOrgTreeData(position.getOrgId(), this.getLongUserID());
            list.add(this.initformAttribute("position.orgId", this.initTreeAsynData(orgTreeData)));

        }
        catch (BizBaseException e) {
            ServletActionContext.getRequest().setAttribute("throw", e);
            logger.error(e.getMessage(), e);
        }
        catch (Exception e) {
            ServletActionContext.getRequest().setAttribute("throw", e);
            logger.error(e.getMessage(), e);
        }
        this.formatData(list);
        return SUCCESS;
    }


    /**
     * 删除岗位信息
     * 
     * @Title: delPositions
     * @Description: 删除岗位信息
     * @return
     */
    @SuppressWarnings("unchecked")
    public String delPositions() {
        HttpServletRequest request =
                (HttpServletRequest) ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);
        String ids = request.getParameter("id");
        String[] positionids = StringUtils.split(ids, ",");
        List<Member> members = new ArrayList<Member>();
        try {
            /**
             * 判断岗位是否有角色关联，如果有角色关联则不可删除
             * */
            for (int i = 0; i < positionids.length; i++) {
                List<Role> list = roleService.getMemberRoles(new Long(positionids[i]), MemberType.Position);
                if (list != null && list.size() > 0) {
                    for (Role role : list) {
                        Member member =
                                new PositionMember(new Long(new Long(positionids[i])), new Long(
                                    role.getRoleId()));
                        members.add(member);
                    }
                }
                User user = new User();
                user.setPositionId(Long.parseLong(positionids[i]));
                List<User> l = userService.queryUsers(user);
                if (l != null && l.size() > 0) {
                    position = positionService.getPosition(user.getPositionId());
                    this.setWarnMessage("岗位“" + position.getPosiName() + "”有用户数据，不能删除");
                    break;
                }
            }
            List<Long> posidlist = new ArrayList<Long>();
            for (int i = 0; i < positionids.length; i++) {
                posidlist.add(new Long(positionids[i]));
            }
            if (posidlist.size() > 0) {
                if (members.size() > 0) {
                    roleService.deleteRoleMembers(members);
                }
                positionService.removePositions(posidlist);
            }
            this.setMessage(1, "POSITION", "DELETE");
        }
        catch (Exception e) {
            ServletActionContext.getRequest().setAttribute("throw", e);
            this.setMessage(0, "POSITION", "DELETE", e);
            // e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        // addSystemLog(LogConfigParam.MODULE_POSITION_ID,LogConfigParam.MODULE_POSITION_NAME,LogConfigParam.OPERATOR_TYPE_DELETE,
        // jsonToString(JsonObject.toString(ids)),
        // this.pageData.get("msg").toString());
        return SUCCESS;
    }


    /**
     * @Title: searchPositionRoleByRole
     * @Description: 按照角色编号查询岗位关联关系
     */
    @SuppressWarnings("unchecked")
    public String searchPositionRoleByRole() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String roleID = request.getParameter("roleID");
        if (StringUtil.isNumber(roleID)) {
            if (position == null)
                position = new Position();
            Pagination result =
                    positionService.queryPositionMembers(Long.parseLong(roleID), position, pagination);
            List list = result.getList();
            this.setPositionOrgName(list);
            this.formatDatagirdData(list, result);
        }
        return SUCCESS;
    }


    /**
     * 分页查询角色下没有的岗位信息
     * */
    @SuppressWarnings("unchecked")
    public String queryNoPositionMembers() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String roleID = request.getParameter("roleID");
        if (StringUtil.isNumber(roleID)) {
            if (position == null)
                position = new Position();
            this.setPositionDataRight(position, request.getParameter("moduleId"),
                request.getParameter("modeId"));
            Pagination result =
                    positionService.queryNoPositionMembers(Long.parseLong(roleID), position, pagination);
            List list = result.getList();
            this.setPositionOrgName(list);
            this.formatDatagirdData(list, result);
        }
        return SUCCESS;
    }


    /**
     * 初始化权限视图树
     * 
     * @title initRightViewTree
     * @description 初始化权限视图树
     * 
     * */
    // @SuppressWarnings("unchecked")
    // public String initRightViewTree(){
    // HttpServletRequest request = (HttpServletRequest)
    // ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);
    // String positionid = request.getParameter("positionid");
    // List<Map> l = new ArrayList<Map>();
    // try {
    // List<ResourceTree> reslist = resourceService.getResourceTrees();
    // List<ResourceTree> result = authorityService.filterResourceTrees(
    // new Long(positionid), reslist, null, MemberType.Position);
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

    /**
     * 显示用户权限上的数据权限
     * 
     * @title showUserDataRight
     * @description 显示用户权限上的数据权限
     * 
     * */
    @SuppressWarnings("unchecked")
    public String showPosDataRight() {
        // HttpServletRequest request = (HttpServletRequest)
        // ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);
        // String id = request.getParameter("id");
        // String positionid = request.getParameter("positionid");
        // try {
        // id = java.net.URLDecoder.decode(id,"UTF-8");
        // String[] ids = id.split("#");
        // List<Scope> list = authorityService.getPrivilegeScopes(new Long(
        // positionid), ids[0], ids[1], MemberType.Position);
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
        return SUCCESS;
    }


    private void setPositionDataRight(Position o, String moduleId, String modeId) {

        if (StringUtils.isEmpty(moduleId)) {
            moduleId = LogConfigParam.MODULE_POSITION_ID;
        }
        if (StringUtils.isEmpty(modeId)) {
            modeId = RightConstants.MODE_VIEW;
        }
        o.setCurrentUserId(this.getLongUserID());
        o.setPermissionId(moduleId, modeId);
    }


    // 根据用户对象列表获取组织用户所属组织的ID字符串
    // private List<Long> getPositionOrgIds(List list){
    // List<Long> ids = new ArrayList<Long>();
    // for(Position p:(List<Position>)list)
    // {
    // ids.add(p.getOrgId());
    // }
    // return ids;
    // }

    /**
     * 设置岗位的组织名称
     * **/
    private void setPositionOrgName(List list) {
        if (list != null && list.size() > 0) {
            List<Long> ids = new ArrayList<Long>();
            List<Position> positions = (List<Position>) list;
            for (Position p : positions) {
                ids.add(p.getOrgId());
            }
            List<Org> orgs = orgService.getOrgByIds(ids);
            for (Position o : positions) {
                Position p = (Position) o;
                for (Org g : orgs) {
                    if (p.getOrgId().equals(g.getOrgId())) {
                        p.setOrgName(g.getOrgName());
                        break;
                    }
                }
            }
        }
    }

}
