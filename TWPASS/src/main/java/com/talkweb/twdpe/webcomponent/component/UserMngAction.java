package com.talkweb.twdpe.webcomponent.component;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.talkweb.twdpe.base.common.exception.DataExistException;
import com.talkweb.twdpe.base.dict.pojo.DictEntryInfo;
import com.talkweb.twdpe.base.dict.service.DictionaryService;
import com.talkweb.twdpe.base.log.pojo.LogConfigParam;
import com.talkweb.twdpe.base.org.constant.OrgConstants;
import com.talkweb.twdpe.base.org.exception.IllegalPasswordException;
import com.talkweb.twdpe.base.org.pojo.Org;
import com.talkweb.twdpe.base.org.pojo.Position;
import com.talkweb.twdpe.base.org.pojo.Staff;
import com.talkweb.twdpe.base.org.pojo.User;
import com.talkweb.twdpe.base.org.service.OrgService;
import com.talkweb.twdpe.base.org.service.PositionService;
import com.talkweb.twdpe.base.org.service.UserService;
import com.talkweb.twdpe.base.right.constant.RightConstants;
import com.talkweb.twdpe.base.right.pojo.App;
import com.talkweb.twdpe.base.right.pojo.GrantNode;
import com.talkweb.twdpe.base.right.pojo.Member;
import com.talkweb.twdpe.base.right.pojo.Member.MemberType;
import com.talkweb.twdpe.base.right.pojo.Role;
import com.talkweb.twdpe.base.right.pojo.Rule;
import com.talkweb.twdpe.base.right.pojo.Scope;
import com.talkweb.twdpe.base.right.pojo.UserMember;
import com.talkweb.twdpe.base.right.service.AppService;
import com.talkweb.twdpe.base.right.service.AuthorityService;
import com.talkweb.twdpe.base.right.service.ResourceService;
import com.talkweb.twdpe.base.right.service.RoleService;
import com.talkweb.twdpe.base.right.util.TreeUtils;
import com.talkweb.twdpe.core.data.DataMap;
import com.talkweb.twdpe.core.data.IData;
import com.talkweb.twdpe.core.exception.BizBaseException;
import com.talkweb.twdpe.core.log.Logger;
import com.talkweb.twdpe.core.log.LoggerFactory;
import com.talkweb.twdpe.core.util.StringUtil;
import com.talkweb.twdpe.core.util.json.fastjson.Json;
import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.twdpe.web.jcontrols.RuleSelect;
import com.talkweb.twdpe.web.jcontrols.RuleTree;
import com.talkweb.twdpe.web.session.main.Session;
import com.talkweb.twdpe.web.session.util.SessionFactory;
import com.talkweb.twdpe.webcomponent.common.action.BusinessAction;
import com.talkweb.xwzcxt.service.MyLogService;
import com.talkweb.xwzcxt.service.MyUserService;
import com.talkweb.xwzcxt.service.impl.MyLogServiceImpl;

/**
 * 文件名称: dsfasd.java 内容摘要:
 * 
 * @author: zyg
 * @version: 1.0
 * @Date: 2012-2-28 下午04:33:11
 * 
 *        修改历史: 修改日期 修改人员 版本 修改内容 ----------------------------------------------
 *        2012-2-28 zyg 1.0 1.0 XXXX
 * 
 *        版权: 版权所有(C)2012 公司: 拓维信息系统股份有限公司
 */
@SuppressWarnings({ "unchecked", "serial" })
public class UserMngAction extends BusinessAction
{
    private static final Logger logger = LoggerFactory
            .getLogger(UserMngAction.class);
    /** 上传的文件 */
    public File upload;
    /** 上传的路径 */
    public String savePath;
    /** 上传的用户模板类型 */
    public String usertype;
    /** 下载的路径 */
    public String inputPath;
    /** 要下载的文件名 */
    public String filename;
    
    @Autowired
    public MyUserService myUserService;
    /**
     * 用户对象，用于添加和修改用户时封装的对象
     * */
    private User user = null;

    public void setUser(User user)
    {
        this.user = user;
    }

    public User getUser()
    {
        return user;
    }

    /**
     * 员工对象，用于添加和修改员工时封装的对象
     * */
    private Staff staff = null;

    public Staff getStaff()
    {
        return staff;
    }

    public void setStaff(Staff staff)
    {
        this.staff = staff;
    }

    /**
     * 角色对象，用于查询用户关联角色时封装的对象
     * */
    private Role role = null;

    public Role getRole()
    {
        return role;
    }

    public void setRole(Role role)
    {
        this.role = role;
    }

    /**
     * 通过注入方式初始化的用户信息管理类,用于用户管理的增，删，改，查的数据库操作类
     * */
    @Autowired
    private UserService userService;

    /**
     * 通过注入方式初始化的字典管理类,用于字典管理的增，删，改，查的数据库操作类
     * */
    @Autowired
    private DictionaryService dictionaryService;

    /**
     * 通过注入方式初始化的组织管理类,用于组织管理的增，删，改，查的数据库操作类
     * */
    @Autowired
    private OrgService orgService;

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
     * 通过注入方式初始化的应用管理类,用于应用管理的增，删，改，查的数据库操作类
     * */
    @Autowired
    private ResourceService resourceService;
    /**
     * 通过注入方式初始化的岗位管理类,用于岗位管理的增，删，改，查的数据库操作类
     * */
    @Autowired
    private PositionService positionService;
    /**
     * 通过注入方式初始化的分配权限管理类
     * */
    @Autowired
    private AuthorityService authorityService;

    /**
     * 
     * @Title: addUser
     * @Description: 增加用户信息
     * @return
     */
    public String addUser()
    {
        HttpServletRequest request = ServletActionContext.getRequest();
        try
        {
            staff.setRealName(staff.getStaffName());
            staff.setStatus(user.getStatus());
            staff.setUserType(user.getUserType());
            user.setStaff(staff);
            String userParts = request.getParameter("userParts");
            List<User> partUsers = new ArrayList<User>();
            if (userParts != null)
            {
                JSONArray userPartsJson = JSONArray.fromObject(userParts);
                for (int i = 0; i < userPartsJson.size(); i++)
                {
                    JSONObject userPartJson = userPartsJson.getJSONObject(i);
                    User partUser = new User();
                    partUser.setOrgId(new Long(userPartJson.getString("orgId")));
                    String positionId = userPartJson.getString("positionId");
                    if (!StringUtil.isEmpty(positionId))
                    {
                        partUser.setPositionId(new Long(positionId));
                    }
                    String upUserId = userPartJson.getString("upUserId");
                    if (!StringUtil.isEmpty(upUserId))
                    {
                        partUser.setUpUserId(new Long(upUserId));
                    }
                    partUsers.add(partUser);
                }
            }
            userService.addUser(user, partUsers);
            this.setMessage(1, "USER", "ADD");
        } catch (DataExistException e)
        {
            request.setAttribute("throw", e);
            this.setErrorMessage("添加用户失败", e);
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        } catch (Exception e)
        {
            request.setAttribute("throw", e);
            this.setErrorMessage("添加用户失败", e);
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        user = null;
        return SUCCESS;
    }

    /**
     * 
     * @Title: modifyUser
     * @Description: 更新用户信息
     * @return
     */
    public String modifyUser()
    {
        HttpServletRequest request = (HttpServletRequest) ActionContext
                .getContext().get(ServletActionContext.HTTP_REQUEST);
        List<User> updatePartUsers = new ArrayList<User>();
        List<User> newPartUsers = new ArrayList<User>();
        List<Long> deletePartUsers = new ArrayList<Long>();
        try
        {
            String userParts = request.getParameter("userParts");
            if (userParts != null)
            {
                JSONArray userPartsJson = JSONArray.fromObject(userParts);
                for (int i = 0; i < userPartsJson.size(); i++)
                {
                    JSONObject userPartJson = userPartsJson.getJSONObject(i);
                    String op = userPartJson.getString("oper");
                    if ("insert".equals(op))
                    {
                        User partUser = new User();
                        partUser.setOrgId(new Long(userPartJson
                                .getString("orgId")));
                        String positionId = userPartJson
                                .getString("positionId");
                        if (!StringUtil.isEmpty(positionId))
                        {
                            partUser.setPositionId(new Long(positionId));
                        }
                        String upUserId = userPartJson.getString("upUserId");
                        if (!StringUtil.isEmpty(upUserId))
                        {
                            partUser.setUpUserId(new Long(upUserId));
                        }
                        newPartUsers.add(partUser);
                    } else if ("delete".equals(op))
                    {
                        deletePartUsers.add(new Long(userPartJson
                                .getString("userId")));
                    } else
                    {
                        User partUser = new User();
                        partUser.setUserId(new Long(userPartJson
                                .getString("userId")));
                        if (userPartJson.containsKey("orgId"))
                        {
                            partUser.setOrgId(new Long(userPartJson
                                    .getString("orgId")));
                        }
                        if (userPartJson.containsKey("positionId"))
                        {
                            String positionId = userPartJson
                                    .getString("positionId");
                            if (!StringUtil.isEmpty(positionId))
                            {
                                partUser.setPositionId(new Long(positionId));
                            }
                        }
                        if (userPartJson.containsKey("upUserId"))
                        {
                            String upUserId = userPartJson
                                    .getString("upUserId");
                            if (!StringUtil.isEmpty(upUserId))
                            {
                                partUser.setUpUserId(new Long(upUserId));
                            }
                        }
                        updatePartUsers.add(partUser);
                    }
                }
            }
            staff.setRealName(staff.getStaffName());
            user.setStaff(staff);
            user.setStaffId(staff.getStaffId());
            user.setBackup1(null);
            user.setUserCode(staff.getUserCode()); //GuveXie Add 20140716
            if (user.getUpUserId() == null)
            {
                user.setUpUserId(OrgConstants.UP_USER_DEFAULT);
            }
            userService.modifyUser(user, newPartUsers, updatePartUsers,
                    deletePartUsers);

            // userService.addPartUser(staffid, orgId, positionId)
            this.setMessage(1, "USER", "MODIFY");
        } catch (Exception e)
        {
            ServletActionContext.getRequest().setAttribute("throw", e);
            this.setErrorMessage("修改用户失败", e);
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        user = null;
        staff = null;
        return SUCCESS;
    }

    /**
     * 初始化用户修改表单
     * 
     * @Title: ininModifyForm
     * @Description: 初始化用户修改表单
     * @return
     */
    public String ininModifyForm()
    {
        // 定义一个总的表单初始化属性对象
        HttpServletRequest request = (HttpServletRequest) ActionContext
                .getContext().get(ServletActionContext.HTTP_REQUEST);
        String userid = request.getParameter("userid");
        List listData = new ArrayList();
        try
        {
            // 设置下拉列表属性规则
            RuleSelect ruleSelect = new RuleSelect();
            ruleSelect.setText("name");
            ruleSelect.setValue("dictid");
            // 根据字典类型取用户类型字典数据
            List<DictEntryInfo> usertype = dictionaryService
                    .getDictEntrysByType("ORG_USER_TYPE");
            // 设置表单取值类型初始化属性
            listData.add(this.initformAttribute("user.userType",
                    this.initSelectData(usertype, ruleSelect)));
            // 根据字典类型取用户状态字典数据
            List<DictEntryInfo> userstatus = dictionaryService
                    .getDictEntrysByType("DICT_STATUS");
            // 设置表单关联类型初始化属性
            listData.add(this.initformAttribute("user.status",
                    this.initSelectData(userstatus, ruleSelect)));

            // 设置表单用户官位关联类型初始化属性
            List result = new ArrayList();
            Pagination page = new Pagination(1, 10000);
            Position pos = new Position();
            User userinfo = userService.getUser(new Long(userid));

            // 1.根据用户的组织id找到所树根节点的下一层子节点及以下全部子节点
            // Org p =
            // orgService.getTreeforOrgid(Long.parseLong(userinfo.getOrgId().toString()));
            // List list = orgService.getChildOrgsbySql(p.getOrgId());
            // List<Map> l = initTreeAsynData(list);
            //
            // // 2.得到根节点下包括的第一层子节点
            // Org o = new Org();
            // o.setParentOrgId(Long.parseLong("-1"));
            //
            // Pagination pa = new Pagination(1, 1000);
            // pa = orgService.queryOrgs(o, pa);
            // List listRoot = pa.getList();
            // if (listRoot != null) {
            // for (int i = 0; i < listRoot.size(); i++) {
            // Map map = new HashMap();
            // Org org = (Org) listRoot.get(i);
            // map.put("id", org.getOrgId());
            // map.put("val", org.getOrgName());
            // map.put("pid", org.getParentOrgId());
            // if (!org.getIsLeaf()) {
            // map.put("isleaf", false);
            // map.put("asyn", "1");
            // }// 3.合并
            // l.add(map);
            // }
            // }
            // this.initformAttribute(m, "user.orgId", l);

            List<Org> orgTreeData = orgService.getOrgTreeData(
                    userinfo.getOrgId(), this.getLongUserID());
            listData.add(this.initformAttribute("user.orgId",
                    initTreeAsynData(orgTreeData)));

            if (userinfo.getPositionId() != null)
            {
                pos.setOrgId(userinfo.getPositionId());
                pos.setCurrentUserId(this.getLongUserID());
                pos.setPermissionId(LogConfigParam.MODULE_USER_ID, "modify");
                Pagination res = positionService.queryPositions(pos, page);
                result = res.getList();
            }
            ruleSelect.setText("positionId");
            ruleSelect.setValue("posiName");
            listData.add(this.initformAttribute("user.positionId",
                    this.initSelectData(result, ruleSelect)));

        } catch (BizBaseException e)
        {
            ServletActionContext.getRequest().setAttribute("throw", e);
            logger.error(e.getMessage(), e);
        } catch (Exception e)
        {
            ServletActionContext.getRequest().setAttribute("throw", e);
            logger.error(e.getMessage(), e);
        }
        // 格式化数据
        this.formatData(listData);
        return SUCCESS;
    }

    /**
     * 初始化用户修改信息
     * 
     * @Title: getModifyUserInfo
     * @Description: 初始化用户修改信息
     * @return
     */
    public String getModifyUserInfo()
    {
        HttpServletRequest request = (HttpServletRequest) ActionContext
                .getContext().get(ServletActionContext.HTTP_REQUEST);
        String staffid = request.getParameter("staffid");
        Map map = new HashMap();
        try
        {
            User userinfo = userService.getMajorUser(new Long(staffid));
            if (userinfo.getUpUserId() != null)
            {
                User u = userService.getUser(userinfo.getUpUserId());
                if (u != null)
                {
                    userinfo.setBackup1(u.getDisplayName());
                } else
                {
                    userinfo.setBackup1(null);
                }
            } else
            {
                userinfo.setBackup1(null);
            }
            Staff staffinfo = userinfo.getStaff();
            map.putAll(this.addPrefix(userinfo, "user."));
            map.putAll(this.addPrefix(staffinfo, "staff."));
            map.put("user.status", map.get("user.status").toString());
        } catch (BizBaseException e)
        {
            ServletActionContext.getRequest().setAttribute("throw", e);
            // TODO Auto-generated catch block
            // e.printStackTrace();
            logger.error(e.getMessage(), e);
        } catch (Exception e)
        {
            ServletActionContext.getRequest().setAttribute("throw", e);
            // TODO Auto-generated catch block
            // e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        this.formatData(map);
        return SUCCESS;
    }

    /**
     * 查找用户信息
     * 
     * @Title: getUserInfo
     * @Description: 查找用户信息
     * @return
     */
    public String getUserInfo()
    {
        HttpServletRequest request = (HttpServletRequest) ActionContext
                .getContext().get(ServletActionContext.HTTP_REQUEST);
        String userid = request.getParameter("userid");
        Map map = new HashMap();
        try
        {
            User userinfo = userService.getUser(new Long(userid));
            Staff satff = userinfo.getStaff();
            // 填充用户状态类型名称
            DictEntryInfo dictEntryInfo;

            dictEntryInfo = dictionaryService.getDictEntryById("DICT_STATUS",
                    userinfo.getStatus().toString());

            if (dictEntryInfo != null)
            {
                userinfo.setStatusName(dictEntryInfo.getName());
            }
            // 填充用户类型名称
            DictEntryInfo dictEntryInfo2 = dictionaryService.getDictEntryById(
                    "ORG_USER_TYPE", userinfo.getUserType());
            if (dictEntryInfo2 != null)
            {
                userinfo.setUserTypeName(dictEntryInfo2.getName());
            }

            if (userinfo.getOrgId() != null)
            {
                Org org = orgService.getOrg(userinfo.getOrgId());
                userinfo.setOrgName(org.getOrgName());
            }
            if (userinfo.getPositionId() != null)
            {
                if (positionService.getPosition(userinfo.getPositionId()) != null)
                {
                    userinfo.setPositionName(positionService.getPosition(
                            userinfo.getPositionId()).getPosiName());
                }
            }
            if (userinfo.getUpUserId() != null)
            {
                User u = userService.getUser(userinfo.getUpUserId());
                if (u != null)
                {
                    userinfo.setBackup1(u.getDisplayName());
                } else
                {
                    userinfo.setBackup1(null);
                }
            } else
            {
                userinfo.setBackup1(null);
            }

            if (satff.getGender() != null && !"".equals(satff.getGender()))
            {
                if ("1".equals(satff.getGender()))
                {
                    satff.setGenderName("男");
                } else
                {
                    satff.setGenderName("女");
                }
            }

            map.putAll(this.addPrefix(userinfo, "user."));
            map.putAll(this.addPrefix(satff, "staff."));
        } catch (BizBaseException e)
        {
            ServletActionContext.getRequest().setAttribute("throw", e);
            logger.error(e.getMessage(), e);
        } catch (Exception e)
        {
            ServletActionContext.getRequest().setAttribute("throw", e);
            logger.error(e.getMessage(), e);
        }
        this.formatData(map);
        return SUCCESS;
    }

    /**
     * 根据用户id查找所有用户兼职信息
     * 
     * @Title: getPartUser
     * @Description: 查找用户信息
     * @return
     */
    public String getPartUser()
    {
        HttpServletRequest request = (HttpServletRequest) ActionContext
                .getContext().get(ServletActionContext.HTTP_REQUEST);
        String staffId = request.getParameter("staffId");
        List<User> userlist = userService.getPartUsers(new Long(staffId));
        List<Map> list = new ArrayList<Map>();
        try
        {
            for (int i = 0; i < userlist.size(); i++)
            {
                Map map = new HashMap();
                User userinfo = userlist.get(i);
                Long orgid = userinfo.getOrgId();
                map.put("orgId", orgid);
                map.put("positionId", userinfo.getPositionId());
                if (orgid != null)
                {
                    String orgname = orgService.getOrg(orgid).getOrgName();
                    userinfo.setOrgName(orgname);
                    map.put("orgName", orgname);
                } else
                {
                    map.put("orgName", null);
                }
                if (userinfo.getPositionId() != null)
                {
                    String positionname = positionService.getPosition(
                            userinfo.getPositionId()).getPosiName();
                    map.put("positionName", positionname);
                } else
                {
                    map.put("positionName", null);
                }
                map.put("upUserId", userinfo.getUpUserId());
                if (userinfo.getUpUserId() != null)
                {
                    User u = userService.getUser(userinfo.getUpUserId());
                    if (u != null)
                    {
                        map.put("upUserName", u.getDisplayName());
                    }
                } else
                {
                    map.put("upUserName", null);
                }
                map.put("userId", userinfo.getUserId());
                map.put("userCode", userinfo.getUserCode());
                list.add(map);
            }
        } catch (Exception e)
        {
            ServletActionContext.getRequest().setAttribute("throw", e);
            logger.error(e.getMessage(), e);
        }
        Pagination page = null;
        if (list.size() > 10)
        {
            page = new Pagination(1, list.size());
        } else
        {
            page = new Pagination(1, 10);
        }
        this.formatDatagirdData(list, page);
        return SUCCESS;
    }

    /**
     * 添加兼职
     * 
     * @Title: addPartUser
     * @Description: 添加兼职
     * @return
     */
    public String addPartUser()
    {
        HttpServletRequest request = (HttpServletRequest) ActionContext
                .getContext().get(ServletActionContext.HTTP_REQUEST);
        String staffid = request.getParameter("staffid");
        String orgid = request.getParameter("orgid");
        String positionid = request.getParameter("positionid");
        Map pageData = new HashMap();

        try
        {
            User u = new User();
            u.setStaffId(new Long(staffid));
            u.setOrgId(new Long(orgid));
            if (positionid != null && !"".equals(positionid))
            {
                u.setPositionId(new Long(positionid));
            }
            userService.addPartUser(u);
            this.setOkMessage("添加兼职成功！");
        } catch (NumberFormatException e)
        {
            // TODO Auto-generated catch block
            ServletActionContext.getRequest().setAttribute("throw", e);
            logger.error(e.getMessage(), e);
            // e.printStackTrace();
            this.setErrorMessage("添加兼职失败！" + e.getMessage());

        } catch (DataExistException e)
        {
            // TODO Auto-generated catch block
            ServletActionContext.getRequest().setAttribute("throw", e);
            logger.error(e.getMessage(), e);
            this.setErrorMessage("添加兼职失败，不能在同一组织下兼职");
        }
        this.formatData(pageData);
        return SUCCESS;
    }

    /**
     * 删除兼职
     * 
     * @Title: delPartUser
     * @Description: 删除兼职
     * @return
     */
    public String delPartUser()
    {
        HttpServletRequest request = (HttpServletRequest) ActionContext
                .getContext().get(ServletActionContext.HTTP_REQUEST);
        String ids = request.getParameter("id");
        int result = 1;
        try
        {
            String[] userids = ids.split(",");
            // 判断该用户是否拥有角色，如果有角色关联，则不可删除
            for (int i = 0; i < userids.length; i++)
            {
                List<Role> list = roleService.getAllUserRoles(new Long(
                        userids[i]));
                if (list != null && list.size() > 0)
                {
                    result = 0;
                    break;
                }
            }
            if (result == 1)
            {
                for (int i = 0; i < userids.length; i++)
                {
                    userService.removeUser(new Long(userids[i]));
                }
                this.setMessage(1, "USER", "DELETE");
            } else
            {
                this.setMessage(2, "USER", "DELETE");
            }

        } catch (Exception e)
        {
            ServletActionContext.getRequest().setAttribute("throw", e);
            this.setMessage(0, "USER", "DELETE");
            logger.error(e.getMessage(), e);
        }
        return SUCCESS;
    }

    /**
     * 初始化用户查询表单
     * 
     * @Title: initUserData
     * @Description: 初始化增加用户时的字典表等数据
     * @return
     */
    public String ininUserForm()
    {
        // 定义一个总的表单初始化属性对象
        List listData = new ArrayList();
        try
        {
            // 设置下拉列表属性规则
            RuleSelect ruleSelect = new RuleSelect();
            ruleSelect.setText("name");
            ruleSelect.setValue("dictid");
            // 根据字典类型取用户类型字典数据
            List<DictEntryInfo> usertype = dictionaryService
                    .getDictEntrysByType("ORG_USER_TYPE");
            // 设置表单取值类型初始化属性
            listData.add(this.initformAttribute("user.userType",
                    this.initSelectData(usertype, ruleSelect)));
            // 根据字典类型取用户状态字典数据
            List<DictEntryInfo> userstatus = dictionaryService
                    .getDictEntrysByType("DICT_STATUS");
            // 设置表单关联类型初始化属性
            listData.add(this.initformAttribute("user.status",
                    this.initSelectData(userstatus, ruleSelect)));
            

        } catch (BizBaseException e)
        {
            ServletActionContext.getRequest().setAttribute("throw", e);
            logger.error(e.getMessage(), e);
        } catch (Exception e)
        {
            ServletActionContext.getRequest().setAttribute("throw", e);
            logger.error(e.getMessage(), e);
        }
        // 格式化数据
        this.formatData(listData);
        return SUCCESS;
    }

    /**
     * 
     * @Title: initUserData
     * @Description: 初始化增加用户时的字典表等数据
     * @return
     */
    public String initUserData()
    {
        // 定义一个总的表单初始化属性对象
        List listData = new ArrayList();
        try
        {
            // 设置下拉列表属性规则
            RuleSelect ruleSelect = new RuleSelect();
            ruleSelect.setText("name");
            ruleSelect.setValue("dictid");
            // 根据字典类型取用户类型字典数据
            List<DictEntryInfo> usertype = dictionaryService
                    .getDictEntrysByType("ORG_USER_TYPE");
            // 设置表单取值类型初始化属性
            listData.add(this.initformAttribute("user.userType",
                    this.initSelectData(usertype, ruleSelect)));
            // 根据字典类型取用户状态字典数据
            List<DictEntryInfo> userstatus = dictionaryService
                    .getDictEntrysByType("DICT_STATUS");
            // 设置表单关联类型初始化属性
            listData.add(this.initformAttribute("user.status",
                    this.initSelectData(userstatus, ruleSelect)));
        } catch (BizBaseException e)
        {
            ServletActionContext.getRequest().setAttribute("throw", e);
            logger.error(e.getMessage(), e);
        } catch (Exception e)
        {
            ServletActionContext.getRequest().setAttribute("throw", e);
            logger.error(e.getMessage(), e);
        }
        // 格式化数据
        this.formatData(listData);
        return SUCCESS;
    }

    /**
     * 初始化增加用户时的根据组织显示岗位信息
     * 
     * @Title:
     * @Description: 初始化增加用户时的根据组织显示岗位信息
     * @return
     */
    public String initUserPos()
    {
        HttpServletRequest request = (HttpServletRequest) ActionContext
                .getContext().get(ServletActionContext.HTTP_REQUEST);
        String orgid = request.getParameter("orgid");
        List result = new ArrayList();
        try
        {
            Position pos = new Position();
            if (orgid != null && !"".equals(orgid))
            {
                pos.setOrgId(new Long(orgid));

                String modeId = request.getParameter("modeId");
                if (StringUtil.isEmpty(modeId))
                {
                    modeId = RightConstants.MODE_VIEW;
                }
                // 设置权限数据规则
                pos.setCurrentUserId(this.getLongUserID());
                pos.setPermissionId(LogConfigParam.MODULE_USER_ID, modeId);

                result = positionService.queryOrgPathPositions(
                        Long.parseLong(orgid), pos);
                // 设置下拉列表属性规则
                RuleSelect ruleSelect = new RuleSelect();
                ruleSelect.setText("posiName");
                ruleSelect.setValue("positionId");
                result = this.initSelectData(result, ruleSelect);
                // result = res.getList();
            }
        } catch (Exception e)
        {
            ServletActionContext.getRequest().setAttribute("throw", e);
            logger.error(e.getMessage(), e);
        }
        this.formatData(result);
        return SUCCESS;
    }

    private void deleteRoleMembers(String[] userIds)
    {
        List<Member> members = new ArrayList<Member>();
        for (int i = 0; i < userIds.length; i++)
        {
            List<Role> list = roleService.queryMemberRoles(
                    new Long(userIds[i]), MemberType.User, new Role(),
                    new Pagination(0, 10)).getList();
            if (list != null)
            {
                for (Role role : list)
                {
                    members.add(new UserMember(new Long(userIds[i]), role
                            .getRoleId()));
                }
            }
        }
        roleService.deleteRoleMembers(members);
    }

    /**
     * 删除用户信息
     * 
     * @Title: delUser
     * @Description: 删除用户信息
     * @return
     */
    public String delUser()
    {
        HttpServletRequest request = (HttpServletRequest) ActionContext
                .getContext().get(ServletActionContext.HTTP_REQUEST);
        String ids = request.getParameter("id");
        try
        {
            String[] userids = ids.split(",");
            // 判断该用户是否拥有角色，如果有角色关联，则删除关联的角色信息
            deleteRoleMembers(userids);
            for (int i = 0; i < userids.length; i++)
            {
                Long userId = new Long(userids[i]);
                User u = userService.getUser(userId);
                if (u != null
                        && u.getDutyType() != null
                        && u.getDutyType().equals(
                                OrgConstants.USER_DUTYTYPE_MAJOR))
                    userService.removeMajorAccount(u.getStaffId());
                else
                    userService.removeUser(userId);
            }
            this.setMessage(1, "USER", "DELETE");

        } catch (Exception e)
        {
            ServletActionContext.getRequest().setAttribute("throw", e);
            this.setMessage(0, "USER", "DELETE");
            logger.error(e.getMessage(), e);
        }
        return SUCCESS;
    }

    /**
     * 初始化组织结构树
     * 
     * @Title: initOrgTree
     * @Description: 初始化组织结构树
     * @return
     * @throws IOException
     */
    public String initOrgTree()
    {
        List<Org> orglist = orgService.getOrgs();
        try
        {
            // 设置树控件数据映射关系
            RuleTree wtree = new RuleTree();
            wtree.setId("orgId");
            wtree.setVal("orgName");
            wtree.setPid("parentOrgId");

            // 格式化树控件数据,按树控件的ID，VAL，PID的顺序
            List l = this.initTreeData(orglist, wtree);
            this.formatData(l);
        } catch (Exception e)
        {
            ServletActionContext.getRequest().setAttribute("throw", e);
            // e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return SUCCESS;
    }

    /**
     * @Title: queryEmpInfos
     * @Description: 根据查询条件查询用户信息
     */
    public String queryEmpInfos()
    {
        HttpServletRequest request = ServletActionContext.getRequest();
        if (user == null)
        {
            
            user = new User();
            // user.setOrgId(Long.parseLong("-1"));
        } else
        {
            if ("".equals(user.getDisplayName()))
            {
                user.setDisplayName(null);
            }
            if ("".equals(user.getUserCode()))
            {
                user.setUserCode(null);
            }
            if ("".equals(user.getUserType()))
            {
                user.setUserType(null);
            }
        }

        // 设置用户数据权限
        this.setUserDataRight(user, request.getParameter("moduleId"),
                request.getParameter("modeId"));

        if (pagination == null)
        {
            pagination = new Pagination(1, 10);
        }
        try
        {

            Pagination page = null;
            if (user.getOrgId() != null && user.getOrgId() != -1)
            {

                page = userService.queryOrgUsers(user.getOrgId(), user,
                        pagination);
            } else
            {
                user.setOrgId(null);
                page = userService.queryUsers(user, pagination);
            }
            List list = page.getList();
            // 填充用户信息中的字典字段名称和外键关联的属性名称
            List l = new ArrayList();
            this.setUserOrgName(list);
            if (list != null && list.size() > 0)
                for (int i = 0; i < list.size(); i++)
                {
                    User user = (User) list.get(i);
                    // 填充用户类型
                    String usertype = user.getUserType();
                    DictEntryInfo dictEntryInfo = dictionaryService
                            .getDictEntryById("ORG_USER_TYPE", usertype);
                    user.setUserTypeName(dictEntryInfo.getName());
                    DictEntryInfo dictEntryInfo2 = dictionaryService
                            .getDictEntryById("DICT_STATUS", user.getStatus()
                                    .toString());
                    user.setStatusName(dictEntryInfo2.getName());
                    DictEntryInfo dictEntryInfo3 = dictionaryService
                            .getDictEntryById("ORG_DUTY_TYPE",
                                    user.getDutyType());
                    user.setDutyTypeName(dictEntryInfo3.getName());
                    l.add(user);
                }
            this.formatDatagirdData(l, page);
        } catch (Exception e)
        {
            request.setAttribute("throw", e);
            logger.debug(e.getMessage(), e);
            e.printStackTrace();
        }
        user = null;

        return SUCCESS;
    }

    /**
     * @Title: queryOrderusers
     * @Description: 根据组织查询用户信息
     */
    public String queryOrderusers()
    {
        HttpServletRequest request = (HttpServletRequest) ActionContext
                .getContext().get(ServletActionContext.HTTP_REQUEST);
        String orgid = request.getParameter("orgid");
        if (user == null)
        {
            user = new User();
        }

        if (orgid != null && !"".equals(orgid))
        {
            user.setOrgId(Long.parseLong(orgid));
        }
        if (pagination == null)
        {
            pagination = new Pagination(1, 10);
        }
        // 设置用户数据权限
        this.setUserDataRight(user, request.getParameter("moduleId"),
                request.getParameter("modeId"));
        try
        {
            Pagination page = null;
            page = userService.queryUsers(user, pagination);

            List list = page.getList();
            this.setUserOrgName(list);
            // 填充用户信息中的字典字段名称和外键关联的属性名称
            List l = new ArrayList();
            if (list != null && list.size() > 0)
            {
                for (int i = 0; i < list.size(); i++)
                {
                    User user = (User) list.get(i);
                    // 填充用户类型
                    String usertype = user.getUserType();
                    DictEntryInfo dictEntryInfo = dictionaryService
                            .getDictEntryById("ORG_USER_TYPE", usertype);
                    user.setUserTypeName(dictEntryInfo.getName());
                    DictEntryInfo dictEntryInfo3 = dictionaryService
                            .getDictEntryById("ORG_DUTY_TYPE",
                                    user.getDutyType());
                    user.setDutyTypeName(dictEntryInfo3.getName());
                    l.add(user);
                }
            }
            this.formatDatagirdData(l, page);
        } catch (Exception e)
        {
            ServletActionContext.getRequest().setAttribute("throw", e);
            e.printStackTrace();
            logger.debug(e.getMessage(), e);
        }
        return SUCCESS;
    }

    /**
     * @Title: queryUsersByOrgForSel
     * @Description: 根据组织查询用户信息
     */
    public String queryUsersByOrgForSel()
    {
        HttpServletRequest request = (HttpServletRequest) ActionContext
                .getContext().get(ServletActionContext.HTTP_REQUEST);
        String orgid = request.getParameter("orgid");
        if (user == null)
        {
            user = new User();
        }

        if (orgid != null && !"".equals(orgid))
        {
            user.setOrgId(Long.parseLong(orgid));
        }
        else //GuveXie 2014-08-16
        {
            HttpServletResponse response = ServletActionContext.getResponse();
            Session session = SessionFactory.getInstance(request, response);
            try {
                Map<String, Object> userMap = (Map) session.getAttribute("USERSESSION"); 
                if (userMap == null) {
                    this.setErrorMessage("用户Session已过期");
                }
                else {
                    String loginuser_orgid = userMap.get("orgid").toString(); //获取登录人ID
                    user.setOrgId(Long.parseLong(loginuser_orgid));
                }
            }catch(Exception err){
    	        logger.debug("queryUsersByOrgForSel", "queryUsersByOrgForSel.Action Exec Fail!");
    			return ERROR;
    		}
        }
        //pagination = new Pagination(1, 10000);
        pagination = new Pagination(1, 1000);

        // 设置用户数据权限
        this.setUserDataRight(user, request.getParameter("moduleId"), request.getParameter("modeId"));
        try
        {
            Pagination page = null;
            page = userService.queryUsers(user, pagination);

            List list = page.getList();
            this.setUserOrgName(list);
            // 填充用户信息中的字典字段名称和外键关联的属性名称
            List l = new ArrayList();
            if (list != null && list.size() > 0)
            {
                for (int i = 0; i < list.size(); i++)
                {
                    User user = (User) list.get(i);
                    // 填充用户类型
                    String usertype = user.getUserType();
                    DictEntryInfo dictEntryInfo = dictionaryService
                            .getDictEntryById("ORG_USER_TYPE", usertype);
                    user.setUserTypeName(dictEntryInfo.getName());
                    DictEntryInfo dictEntryInfo3 = dictionaryService
                            .getDictEntryById("ORG_DUTY_TYPE",
                                    user.getDutyType());
                    user.setDutyTypeName(dictEntryInfo3.getName());
                    l.add(user);
                }
            }
            RuleSelect st = new RuleSelect();
            st.setText("displayName");
            st.setValue("userId");
            this.formatData(this.initSelectData(l, st));
        } catch (Exception e)
        {
            ServletActionContext.getRequest().setAttribute("throw", e);
            e.printStackTrace();
            logger.debug(e.getMessage(), e);
        }
        return SUCCESS;
    }
    
    /**
     * @Title: queryUsersByPostForSel
     * @Description: 根据岗位查询用户信息
     */
    public String queryUsersByPostForSel()
    {
        HttpServletRequest request = (HttpServletRequest) ActionContext
                .getContext().get(ServletActionContext.HTTP_REQUEST);
        String postid = request.getParameter("postid");
        if (user == null)
        {
            user = new User();
        }

        if (postid != null && !"".equals(postid))
        {
            user.setPositionId(Long.parseLong(postid));
        }
        pagination = new Pagination(1, 10000);

        // 设置用户数据权限
        this.setUserDataRight(user, request.getParameter("moduleId"),
                request.getParameter("modeId"));
        try
        {
            Pagination page = null;
            page = userService.queryUsers(user, pagination);

            List list = page.getList();
            this.setUserOrgName(list);
            // 填充用户信息中的字典字段名称和外键关联的属性名称
            List l = new ArrayList();
            if (list != null && list.size() > 0)
            {
                for (int i = 0; i < list.size(); i++)
                {
                    User user = (User) list.get(i);
                    // 填充用户类型
                    String usertype = user.getUserType();
                    DictEntryInfo dictEntryInfo = dictionaryService
                            .getDictEntryById("ORG_USER_TYPE", usertype);
                    user.setUserTypeName(dictEntryInfo.getName());
                    DictEntryInfo dictEntryInfo3 = dictionaryService
                            .getDictEntryById("ORG_DUTY_TYPE",
                                    user.getDutyType());
                    user.setDutyTypeName(dictEntryInfo3.getName());
                    l.add(user);
                }
            }
            RuleSelect st = new RuleSelect();
            st.setText("displayName");
            st.setValue("userId");
            this.formatData(this.initSelectData(l, st));
        } catch (Exception e)
        {
            ServletActionContext.getRequest().setAttribute("throw", e);
            e.printStackTrace();
            logger.debug(e.getMessage(), e);
        }
        return SUCCESS;
    }

    /**
     * 
     * @Title: initModifyPassWordForm
     * @Description: 初始化用户修改密码
     * @return
     */
    public String initModifyPassWordForm()
    {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        Session session = SessionFactory.getInstance(request, response);
        Map<String, Object> userMap = ((Map<String, Object>) session
                .getAttribute("USERSESSION"));
        try
        {
            // 当前登录用户的登录ID编号
            String userCode = userMap.get("code").toString();

   
            this.formatData(userCode);
        } catch (Exception e)
        {
            ServletActionContext.getRequest().setAttribute("throw", e);
            logger.error(e.getMessage(), e);
        }

        return SUCCESS;
    }

    /**
     * 
     * @Title: moveEmpFromOrgToOrg
     * @Description: 将某个用户转移到另外的一个组织下
     * @return
     */

    public String changeUserOrder()
    {
        HttpServletRequest request = (HttpServletRequest) ActionContext
                .getContext().get(ServletActionContext.HTTP_REQUEST);
        String data = request.getParameter("data");
        try
        {
            if (!StringUtil.isEmpty(data))
            {
                List<User> list = new ArrayList<User>();
                String[] users = data.split(",");
                int orderBy = 1;
                for (String userId : users)
                {
                    User u = new User();
                    u.setUserId(new Long(userId));
                    u.setOrderby(orderBy++);
                    list.add(u);
                }
                userService.sortUser(list);
            }
            this.setOkMessage("用户排序成功！");
        } catch (Exception e)
        {
            ServletActionContext.getRequest().setAttribute("throw", e);
            this.setErrorMessage("用户排序失败！", e);
            logger.error(e.getMessage(), e);
        }
        return SUCCESS;
    }

    /**
     * 
     * @Title: moveEmpFromOrgToOrg
     * @Description: 将某个用户转移到另外的一个组织下
     * @return
     */
    public String moveEmpFromOrgToOrg()
    {
        HttpServletRequest request = (HttpServletRequest) ActionContext
                .getContext().get(ServletActionContext.HTTP_REQUEST);
        String userid = request.getParameter("empids");
        String orgid = request.getParameter("orgid");
        try
        {
            String[] userids = userid.split(",");
            for (int i = 0; i < userids.length; i++)
            {
                userService.moveUserOrg(new Long(userids[i]), new Long(orgid));
            }
            this.setMessage(1, "USER", "CHANGEORG");
        } catch (Exception e)
        {
            ServletActionContext.getRequest().setAttribute("throw", e);
            this.setMessage(0, "USER", "CHANGEORG", e);
            logger.error(e.getMessage(), e);
        }
        return SUCCESS;
    }

    /**
     * 重置用户密码
     * 
     * @return
     */
    public String resetPassWord()
    {
        HttpServletRequest request = ServletActionContext.getRequest();
        String userId = request.getParameter("userId");
        try
        {
            DictEntryInfo dictEntryInfo = dictionaryService.getDictEntryById(
                    "RIGHT_PASSWORD", "default");
            if (dictEntryInfo == null)
            {
                this.setErrorMessage("字典里没有找到用户密码数据，用户密码重置失败");
            } else
            {
                String password = dictEntryInfo.getValue();
                userService.resetPassword(new Long(userId), password);
                this.setOkMessage("用户密码重置成功，重置之后密码为：" + password);
            }
        } catch (Exception e)
        {
            request.setAttribute("throw", e);
            this.setMessage(0, "USER", "CHANGEORG", e);
            logger.error(e.getMessage(), e);
        }
        return SUCCESS;
    }

    /**
     * 查询用户已经关联的角色列表
     * 
     * @Title: queryRoles
     * @Description: 查询用户已经关联的角色列表
     */
    public String queryHasRelRoles()
    {
        HttpServletRequest request = (HttpServletRequest) ActionContext
                .getContext().get(ServletActionContext.HTTP_REQUEST);
        String userid = request.getParameter("userid");
        try
        {
            if (pagination == null)
            {
                pagination = new Pagination(1, 10);
            }
            Pagination page = roleService.queryMemberRoles(new Long(userid),
                    MemberType.User, new Role(), pagination);
            List list = page.getList();
            List l = new ArrayList();
            // 填充对应的属性名称
            for (int i = 0; i < list.size(); i++)
            {
                Role role = (Role) list.get(i);
                // 填充组织名称
                if (role.getOrgId() != null)
                {
                    Org org = orgService.getOrg(role.getOrgId());
                    if (org != null && org.getOrgName() != null)
                    {
                        role.setOrgName(org.getOrgName());
                    }
                }

                // 填充应用名称
                if (role.getAppId() != null)
                {
                    App app = appService.getApp(role.getAppId());
                    if (app != null && app.getAppName() != null)
                    {
                        role.setAppName(app.getAppName());
                    }
                }
                l.add(role);
            }
            this.formatDatagirdData(list, page);
        } catch (Exception e)
        {
            ServletActionContext.getRequest().setAttribute("throw", e);
            logger.error(e.getMessage(), e);
        }
        return SUCCESS;
    }

    /**
     * 查询用户未关联的角色
     * 
     * @Title: queryNoRelRoles
     * @Description: 查询用户未关联的角色
     * @return SUCCESS
     */
    public String queryNoRelRoles()
    {
        HttpServletRequest request = (HttpServletRequest) ActionContext
                .getContext().get(ServletActionContext.HTTP_REQUEST);
        String userid = request.getParameter("userid");
        String orgid = request.getParameter("orgid");
        if (role == null)
            role = new Role();

        // String roletype = request.getParameter("roletype");
        // String orgid = request.getParameter("orgid");
        // String appid = request.getParameter("appid");
        // String rolename = request.getParameter("rolename");

        try
        {
            if (orgid != null && !"".equals(orgid))
            {
                role.setOrgId(new Long(orgid));
            }
            // if (appid != null && !"".equals(appid)) {
            // role.setAppId(new Long(appid));
            // }
            // if (rolename != null && !"".equals(rolename)) {
            // role.setRoleName(rolename);
            // }
            // if(!"".equals(roletype))
            // role.setRoleType(roletype);
            if (pagination == null)
            {
                pagination = new Pagination(1, 10);
            }
            Pagination page = roleService.queryMemberNoRoles(new Long(userid),
                    MemberType.User, role, pagination);
            List list = page.getList();
            List l = new ArrayList();
            // 填充对应的属性名称
            for (int i = 0; i < list.size(); i++)
            {
                role = (Role) list.get(i);
                // 填充组织名称
                if (role.getOrgId() != null)
                {
                    Org org = orgService.getOrg(role.getOrgId());
                    if (org != null && org.getOrgName() != null)
                    {
                        role.setOrgName(org.getOrgName());
                    }
                }
                // 填充应用名称
                if (role.getAppId() != null)
                {
                    App app = appService.getApp(role.getAppId());
                    if (app != null && app.getAppName() != null)
                    {
                        role.setAppName(app.getAppName());
                    }
                }
                l.add(role);
            }
            this.formatDatagirdData(l, page);
        } catch (Exception e)
        {
            ServletActionContext.getRequest().setAttribute("throw", e);
            // e.printStackTrace();
            logger.debug(e.getMessage(), e);
        }
        return SUCCESS;
    }

    /**
     * 初始化用户新增角色的form表单数据
     * 
     * @Title: initUserRoleForm
     * @Description: 初始化用户新增角色的form表单数据
     * @return SUCCESS
     */
    public String initUserRoleForm()
    {
        // 定义一个总的表单初始化属性对象
        List listData = new ArrayList();
        try
        {
            // 设置下拉列表属性规则
            RuleSelect ruleSelect = new RuleSelect();
            ruleSelect.setText("appName");
            ruleSelect.setValue("appId");
            // 根据字典类型取用户类型字典数据
            List<App> applist = appService.getApps();
            // 设置表单取值类型初始化属性
            listData.add(this.initformAttribute("appid",
                    this.initSelectData(applist, ruleSelect)));

        } catch (BizBaseException e)
        {
            ServletActionContext.getRequest().setAttribute("throw", e);
            logger.error(e.getMessage(), e);
        } catch (Exception e)
        {
            ServletActionContext.getRequest().setAttribute("throw", e);
            logger.error(e.getMessage(), e);
        }
        // 格式化数据
        this.formatData(listData);
        return SUCCESS;
    }

    /**
     * 增加用户关联角色
     * 
     * @Title: addRoleWithuser
     * @Description: 增加用户关联角色
     * @return
     */
    public String addRoleWithuser()
    {
        HttpServletRequest request = (HttpServletRequest) ActionContext
                .getContext().get(ServletActionContext.HTTP_REQUEST);
        HttpServletResponse response = ServletActionContext.getResponse();
        String userid = request.getParameter("userid");
        String roleid = request.getParameter("roleids");
        try
        {
            String[] roleids = roleid.split(",");
            List<Member> list = new ArrayList<Member>();
            for (int i = 0; i < roleids.length; i++)
            {
                Member usermember = new UserMember(new Long(userid), new Long(
                        roleids[i]));
                list.add(usermember);
            }
            roleService.addRoleMembers(list);
            this.setMessage(1, "USER", "RELROLE");
            
            //记录数据日志
            MyLogService myLogService = new MyLogServiceImpl();
            myLogService.addLogInfo(request, response, "1", "过程行为支持系统", "3", "基础管理", "用户管理", "新增", "用户管理-关联角色", "成功", "1", "", "DP_USERROLE", list.toString());
        } catch (Exception e)
        {
            ServletActionContext.getRequest().setAttribute("throw", e);
            this.setMessage(0, "USER", "RELROLE");
            logger.error(e.getMessage(), e);
        }
        IData data = new DataMap();
        data.put("userid", userid);
        data.put("roleid", roleid);
        return SUCCESS;
    }

    /**
     * 删除用户已经关联角色
     * 
     * @Title: delHasRelRoles
     * @Description: 删除用户已经关联角色
     * @return
     */
    public String delHasRelRoles()
    {
        HttpServletRequest request = (HttpServletRequest) ActionContext
                .getContext().get(ServletActionContext.HTTP_REQUEST);
        String userid = request.getParameter("userid");
        String roleid = request.getParameter("id");
        try
        {
            String[] roleids = roleid.split(",");
            List<Member> list = new ArrayList<Member>();
            for (int i = 0; i < roleids.length; i++)
            {
                Member usermember = new UserMember(new Long(userid), new Long(
                        roleids[i]));
                list.add(usermember);
            }
            roleService.deleteRoleMembers(list);
            this.setMessage(1, "USER", "DELROLE");
        } catch (Exception e)
        {
            ServletActionContext.getRequest().setAttribute("throw", e);
            this.setMessage(0, "USER", "DELROLE");
            // e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        IData data = new DataMap();
        data.put("userid", userid);
        data.put("roleid", roleid);
        return SUCCESS;
    }

    /**
     * 查询用户的私有角色
     * 
     * @Title: searchRoleUserInfos
     * @Description: 根据角色查询人员信息
     */
    public String searUserRole()
    {
    	
        HttpServletRequest request = ServletActionContext.getRequest();
        try{
        String userid = request.getParameter("userid");

        Role role = roleService.getPrivateRole(new Long(userid),
                MemberType.User, true);
        Map pageData = new HashMap();
        pageData.put("roleid", role.getRoleId());
        pageData.put("rolename", role.getRoleName());
        this.formatData(pageData);
        }catch(Exception e){
        	e.printStackTrace();
        }
        return SUCCESS;
    }

    /**
     * 根据角色查询人员信息
     * 
     * @Title: searchRoleUserInfos
     * @Description: 根据角色查询人员信息
     */
    public String searchRoleUserInfos()
    {
        HttpServletRequest request = ServletActionContext.getRequest();
        String roleID = request.getParameter("roleID");
        if (StringUtil.isNumber(roleID))
        {
            if (user == null)
                user = new User();
            this.setUserDataRight(user, request.getParameter("moduleId"),
                    request.getParameter("modeId"));
            Pagination result = userService.queryUserMembers(
                    Long.parseLong(roleID), user, pagination);
            List list = result.getList();
            this.setUserOrgName(list);
            this.formatDatagirdData(list, pagination);
            user = null;
        }
        return SUCCESS;
    }

    /**
     * 查询角色没有关联的用户成员
     * */
    public String queryNoUserMembers()
    {
        HttpServletRequest request = ServletActionContext.getRequest();
        String roleID = request.getParameter("roleID");
        if (StringUtil.isNumber(roleID))
        {
            if (user == null)
                user = new User();
            this.setUserDataRight(user, request.getParameter("moduleId"),
                    request.getParameter("modeId"));
            Pagination result = userService.queryNoUserMembers(
                    Long.parseLong(roleID), user, pagination);
            List list = result.getList();
            this.setUserOrgName(list);
            this.formatDatagirdData(list, pagination);
            user = null;
        }
        return SUCCESS;
    }
	//MD5加密
    public String MD5(String str){
   	 MessageDigest md;
   	StringBuffer op = new StringBuffer();
		try {
			md = MessageDigest.getInstance("MD5");
			 byte [] odlP=md.digest(str.getBytes());
		   	 for (int i = 0; i < odlP.length; i++){
		   		 int val = ((int) odlP[i]) & 0xff;
		   		 if (val < 16){
		   			 op.append("0");
		   		 }
		   		 op.append(Integer.toHexString(val));
		   	 }
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
   
   	 return op.toString();
    }
    /**
     * @title modifyPassWord
     * @description 修改操作人员密码
     */
    public String modifyPassWord()
    {
        HttpServletRequest request = (HttpServletRequest) ActionContext
                .getContext().get(ServletActionContext.HTTP_REQUEST);
        String userid = request.getParameter("userid");
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
    
        try
        {
        	Map<String, String> pageData = new HashMap<String, String>();
        	//验证用户名密码；
        	 boolean b=myUserService.checkUserCodePassword(userid, MD5(oldPassword));
        	 if(b){
        		//修改密码
        		boolean bo= myUserService.changePassword(userid, MD5(newPassword));
        		if(bo){
        			pageData.clear();
        				pageData.put("msg", "修改成功");
        				pageData.put("status", "ok");
        		
        		}else{
        			pageData.clear();
        			pageData.put("msg", "修改失败");
    				pageData.put("status", "error");
        		}
        	 }else{
        		 pageData.clear();
        		pageData.put("msg", "您输入的用户名与密码不正确");
 				pageData.put("status", "error");
        	 }
        	this.formatData(pageData);
        	
          //  userService.modifyPassword(new Long(userid), newPassword,
             //       oldPassword);
           // this.setMessage(1, "USER", "PASSWORD");
        } catch (NumberFormatException e)
        {
            ServletActionContext.getRequest().setAttribute("throw", e);
            this.setMessage(0, "USER", "PASSWORD");
            logger.error(e.getMessage(), e);
        }
        catch (Exception e)
        {
            ServletActionContext.getRequest().setAttribute("throw", e);
            this.setMessage(0, "USER", "PASSWORD");
            logger.error(e.getMessage(), e);
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
    public String initRightViewTree()
    {
        HttpServletRequest request = (HttpServletRequest) ActionContext
                .getContext().get(ServletActionContext.HTTP_REQUEST);
        String userid = request.getParameter("userid");
        List<Map> l = new ArrayList<Map>();
        try
        {
            List<GrantNode> res = authorityService
                    .getUserGrantAuthTreeView(Long.parseLong(userid));
            l = TreeUtils.transfor2Maps(res);
        } catch (Exception e)
        {
            ServletActionContext.getRequest().setAttribute("throw", e);
            logger.error(e.getMessage(), e);
        }
        this.formatData(l);
        return SUCCESS;
    }

    /**
     * 显示用户权限上的数据权限
     * 
     * @title showUserDataRight
     * @description 显示用户权限上的数据权限
     * 
     * */
    public String showUserDataRight()
    {
        HttpServletRequest request = (HttpServletRequest) ActionContext
                .getContext().get(ServletActionContext.HTTP_REQUEST);
        String id = request.getParameter("id");
        String userid = request.getParameter("userid");
        try
        {
            List<Scope> list = authorityService.getPrivilegeScopes(new Long(
                    userid), Long.parseLong(id));
            List<Rule> rules = new ArrayList<Rule>();
            List<Map> res = new ArrayList<Map>();
            for (int i = 0; i < list.size(); i++)
            {
                Scope scope = list.get(i);
                rules.add(scope.getRule());
            }
            Pagination page = null;
            if (list != null && list.size() > 10)
            {
                page = new Pagination(1, list.size());
            } else
            {
                page = new Pagination(1, 10);
            }

            this.formatDatagirdData(rules, page);
        } catch (Exception e)
        {
            ServletActionContext.getRequest().setAttribute("throw", e);
            logger.error(e.getMessage(), e);
        }
        return SUCCESS;
    }

    // 设置用户数据权限
    private void setUserDataRight(User user, String moduleId, String modeId)
    {
        if (StringUtil.isEmpty(moduleId))
        {
            moduleId = LogConfigParam.MODULE_USER_ID;
        }
        if (StringUtil.isEmpty(modeId))
        {
            modeId = RightConstants.MODE_VIEW;
        }
        user.setCurrentUserId(this.getLongUserID());
        user.setPermissionId(moduleId, modeId);
    }

    /**
     * 设置用户组织名称以及本职组织
     * **/
    private void setUserOrgName(List list)
    {
        if (list != null && list.size() > 0)
        {
            List<Long> ids = new ArrayList<Long>();
            Map<Long, Long> map = new HashMap<Long, Long>();
            for (User u : (List<User>) list)
            {
                ids.add(u.getOrgId());
                if (map.containsKey(u.getStaffId()))
                {
                    u.setMajorOrgId(map.get(u.getStaffId()));
                } else
                {
                    if (u.getDutyType()
                            .equals(OrgConstants.USER_DUTYTYPE_MAJOR))
                    {
                        map.put(u.getStaffId(), u.getOrgId());
                        u.setMajorOrgId(u.getOrgId());
                    } else
                    {
                        User majorUser = userService.getMajorUser(u
                                .getStaffId());
                        map.put(u.getStaffId(), majorUser.getOrgId());
                        u.setMajorOrgId(majorUser.getOrgId());
                    }
                }
            }
            List<Org> orgs = orgService.getOrgByIds(ids);
            for (Object o : list)
            {
                User u = (User) o;
                for (Org g : orgs)
                {
                    if (u.getOrgId().equals(g.getOrgId()))
                    {
                        u.setOrgName(g.getOrgName());
                        break;
                    }
                }
            }
        }
    }
    //检测账号是否已存在
    public void checkLoginIdExists(){
    	HttpServletRequest request=ServletActionContext.getRequest();
    	HttpServletResponse response=ServletActionContext.getResponse();
    	PrintWriter out=null;
    	String flag="false";
    	try {
			request.setCharacterEncoding("UTF-8");
			out=response.getWriter();
			String loginId=request.getParameter("loginId");
			User user=new User();
			user.setUserCode(loginId);
			List<User> userList=userService.queryUsers(user);
			if(userList.size()>0){
				flag="true";
			}
			out.print(flag);
			out.flush();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	finally{
    		if(out!=null){
    			out.close();
    		}
    	}
    }
    // **************************一些变量的get和set方法*******************************************
    public String getUsertype()
    {
        return usertype;
    }

    public void setUsertype(String usertype)
    {
        this.usertype = usertype;
    }

    @SuppressWarnings("deprecation")
    public String getSavePath()
    {
        return ServletActionContext.getRequest().getRealPath(savePath);
    }

    public void setSavePath(String savePath)
    {
        this.savePath = savePath;
    }

    public File getUpload()
    {
        return upload;
    }

    public void setUpload(File upload)
    {
        this.upload = upload;
    }

    public void setInputPath(String inputPath)
    {
        this.inputPath = inputPath;
    }

    public String getFilename()
    {
        return filename;
    }

    public void setFilename(String filename)
    {
        this.filename = filename;
    }
    
    /**
     * @Title: queryUsersByParams
     * @Description: 根据岗位查询用户信息
     */
    @SuppressWarnings("rawtypes")
	public String queryUsersByParams()
    {
        HttpServletRequest request = (HttpServletRequest) ActionContext
                .getContext().get(ServletActionContext.HTTP_REQUEST);
        String postid = request.getParameter("postid");
        if (user == null)
        {
            user = new User();
        }
        if (postid != null && !"".equals(postid))
        {
            user.setPositionId(Long.parseLong(postid));
        }
        pagination = new Pagination(1, 10000);

        try
        {
            Pagination page = null;
            page = userService.queryUsers(user, pagination);

            List list = page.getList();
	    	if (list != null && list.size()>0){
	    		RuleTree wtree = new RuleTree();
		        wtree.setId("userId");
		        wtree.setVal("displayName");
		        wtree.setPid("isDelete");
		        List<Map> l = this.initTreeData(list, wtree);
				this.formatData(l);
			}
        } catch (Exception e)
        {
            ServletActionContext.getRequest().setAttribute("throw", e);
            e.printStackTrace();
            logger.debug(e.getMessage(), e);
        }
        return SUCCESS;
    }

}
