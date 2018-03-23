package com.talkweb.twdpe.webcomponent.component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.twdpe.base.dict.pojo.DictEntryInfo;
import com.talkweb.twdpe.base.dict.service.DictionaryService;
import com.talkweb.twdpe.base.right.pojo.App;
import com.talkweb.twdpe.base.right.pojo.DataBusiness;
import com.talkweb.twdpe.base.right.pojo.DataBusinessList;
import com.talkweb.twdpe.base.right.pojo.Rule;
import com.talkweb.twdpe.base.right.pojo.Scope;
import com.talkweb.twdpe.base.right.service.AppService;
import com.talkweb.twdpe.base.right.service.AuthorityService;
import com.talkweb.twdpe.base.right.service.DataRuleService;
import com.talkweb.twdpe.core.exception.BizBaseException;
import com.talkweb.twdpe.core.log.Logger;
import com.talkweb.twdpe.core.log.LoggerFactory;
import com.talkweb.twdpe.core.util.StringUtil;
import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.twdpe.web.jcontrols.RuleSelect;
import com.talkweb.twdpe.webcomponent.common.action.BusinessAction;

/**
 * 文件名称:    DataRightMngAction.java
 * 内容摘要: 数据权限管理
 * @author:   zhangwen 
 * @version:  1.0  
 * @Date:     2011-7-19 上午10:01:29  
 * 
 * 修改历史:  
 * 修改日期       修改人员   版本	   修改内容  
 * ----------------------------------------------  
 * 2011-7-19    zhangwen     1.0    1.0 XXXX
 *
 * 版权:   版权所有(C)2011
 * 公司:   拓维信息系统股份有限公司
 */
public class DataRightMngAction extends BusinessAction {
    /** @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) */
    private static final long serialVersionUID = 1L;

    private static final Logger logger = LoggerFactory.getLogger(DataRightMngAction.class);

    /**
     * 分页查询数据权限规则信息
     * @Title: queryRulesByPage
     * @Description: TODO(分页查询数据权限规则信息)
     */
    @SuppressWarnings("unchecked")
    public String queryRulesByPage() {
	if (rule == null)
	    rule = new Rule();
	Pagination result = dataRuleService.queryRules(rule, pagination);
	this.formatDatagirdData(this.setDataRuleDictName(result.getList()), result);
	rule = null;
	return SUCCESS;
    }

    /**
     * 增加新的数据权限规则
     * @Title: addRule
     * @Description: TODO(增加新的数据权限规则)
     */
    public String addRule() {
	int result = 0;
	try {
	    // 增加方法没有返回值，根据是否有异常来判断操作是否成功，有异常则操作不成功，没有则操作成功
	    dataRuleService.addRule(rule);
	    result = 1;
	} catch (Exception e) {
	    ServletActionContext.getRequest().setAttribute("throw", e);
	    logger.error(e.getMessage(), e);
	}
	// 设置操作提示信息
	this.setMessage(result, "DATARIGHTRULE", "ADD");
	// addSystemLog(LogConfigParam.MODULE_DATARULE_ID,LogConfigParam.MODULE_DATARULE_NAME,LogConfigParam.OPERATOR_TYPE_ADD,
	// jsonToString(JsonObject.toString(rule)),
	// this.pageData.get("msg").toString());
	return SUCCESS;
    }

    /**
     * 修改数据权限规则
     * @Title: modifyRule
     * @Description: TODO(修改数据权限规则)
     */
    public String modifyRule() {
	int result = 0;
	try {
	    // 修改方法没有返回值，根据是否有异常来判断操作是否成功，有异常则操作不成功，没有则操作成功
	    dataRuleService.modifyRule(rule);
	    result = 1;
	} catch (Exception e) {
	    ServletActionContext.getRequest().setAttribute("throw", e);
	    logger.error(e.getMessage(), e);
	}
	// 设置操作提示信息
	this.setMessage(result, "DATARIGHTRULE", "MODIFY");
	// addSystemLog(LogConfigParam.MODULE_DATARULE_ID,LogConfigParam.MODULE_DATARULE_NAME,LogConfigParam.OPERATOR_TYPE_MODIFY,
	// jsonToString(JsonObject.toString(rule)),
	// this.pageData.get("msg").toString());
	return SUCCESS;
    }

    /**
     * 删除数据权限规则
     * @Title: moveOrg
     * @Description: TODO(删除数据权限规则)
     */
    public String deleteRule() {
	HttpServletRequest request = ServletActionContext.getRequest();
	String ids = request.getParameter("id");
	String[] ruleids = StringUtils.split(ids, ",");
	List<String> rulelist = Arrays.asList(ruleids);
	List<Long> ruleIds = new ArrayList<Long>();
	for (String id : rulelist) {
	    if (id.trim().length() > 0)
		ruleIds.add(Long.parseLong(id));
	}
	int result = 0;
	try {
	    dataRuleService.deleteRules(ruleIds);
	    result = 1;
	} catch (Exception e) {
	    ServletActionContext.getRequest().setAttribute("throw", e);
	    logger.error(e.getMessage(), e);
	}
	this.setMessage(result, "DATARIGHTRULE", "DELETE");
	// addSystemLog(LogConfigParam.MODULE_DATARULE_ID,LogConfigParam.MODULE_DATARULE_NAME,LogConfigParam.OPERATOR_TYPE_DELETE,
	// ids, this.pageData.get("msg").toString());
	return SUCCESS;
    }

    /**
     * 查询数据权限规则详细信息
     * @Title: getRuleById
     * @Description: TODO(查询数据权限规则详细信息)
     */
    @SuppressWarnings("unchecked")
    public String getRuleById() {
	HttpServletRequest request = ServletActionContext.getRequest();
	String ruleId = request.getParameter("ruleId");
	Rule rule = dataRuleService.getRule(Long.parseLong(ruleId));
	try {
	    Map m = this.addPrefix(rule, "rule.");
	    this.formatData(m);
	} catch (Exception e) {
	    ServletActionContext.getRequest().setAttribute("throw", e);
	    // TODO Auto-generated catch block
	    // e.printStackTrace();
	    logger.error(e.getMessage(), e);
	}
	return SUCCESS;
    }
    
    /**
     * 查询绑定到权限上的数据规则
     * */
    public String queryBindedRules() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		if (StringUtil.isNumber(id)) {
		    long permissionId = Long.parseLong(id);
		    Pagination result = dataRuleService.queryBindedRules(permissionId, pagination);
		    this.formatDatagirdData(result.getList(), result);
		}
		return SUCCESS;
    }
    
    /**
     * 查询没有绑定到权限上数据规则集
     * */
    public String queryNoBindRules() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		if (StringUtil.isNumber(id)) {
		    long permissionId = Long.parseLong(id);
		    Pagination result = dataRuleService.queryNoBindRules(permissionId, pagination);
		    this.formatDatagirdData(result.getList(), result);
		}
		return SUCCESS;
    }    

    /**
     * 绑定多个数据规则集至权限
     * **/
    public String bindPermissions() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String pid = request.getParameter("pid");
		long permissionId = 0;
		if (StringUtil.isNumber(pid))
		    permissionId = Long.parseLong(pid);
		String ruleIds = request.getParameter("ruleId");
		List<Long> list = new ArrayList<Long>();
		if (ruleIds != null) {
		    String[] rules = StringUtils.split(ruleIds, ",");
		    for (String s : rules) {
			if (StringUtil.isNumber(s))
			    list.add(Long.parseLong(s));
		    }
		}
		int result = 0;
		if (permissionId > 0 && list.size() > 0) {
		    try {
			dataRuleService.bindPermissions(permissionId, list);
			result = 1;
		    } catch (Exception e) {
		    ServletActionContext.getRequest().setAttribute("throw", e);
			logger.error(e.getMessage(), e);
		    }
		}
		this.setMessage(result, "PERMISSION", "BIND");
		return SUCCESS;
    }
    
    /**
     * 绑定多个数据规则集至权限
     * **/
    public String batchBindPermissions() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String pids = request.getParameter("pids");
		List<Long> ps = new ArrayList<Long>();
		if(pids!=null)
		{
			String[] arry = StringUtils.split(pids,",");
			for(String s:arry)
				if(StringUtil.isNumber(s))
					ps.add(Long.parseLong(s));
		}

		String ruleIds = request.getParameter("ruleId");
		List<Long> list = new ArrayList<Long>();
		if (ruleIds != null) {
		    String[] rules = StringUtils.split(ruleIds, ",");
		    for (String s : rules) {
			if (StringUtil.isNumber(s))
			    list.add(Long.parseLong(s));
		    }
		}
		int result = 0;
		if (ps.size() > 0 && list.size() > 0) {
			dataRuleService.unbindPermissions(ps, list);
		    try {
		    	for(Long permissionId:ps){
		    			dataRuleService.bindPermissions(permissionId, list);
		    	}
		    	result = 1;
		    } catch (Exception e) {
			    ServletActionContext.getRequest().setAttribute("throw", e);
				logger.error(e.getMessage(), e);
		    }
		}
		this.setMessage(result, "PERMISSION", "BIND");
		return SUCCESS;
    }    

    /**
     * 解除数据规则与权限的多个绑定
     * **/
    public String unbindPermissions() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String pid = request.getParameter("pid");
		long permissionId = 0;
		if (StringUtil.isNumber(pid))
		    permissionId = Long.parseLong(pid);
		String ruleIds = request.getParameter("ruleIds");
		List<Long> list = new ArrayList<Long>();
		if (ruleIds != null) {
		    String[] ruleId = StringUtils.split(ruleIds, ",");
		    for (String s : ruleId) {
			if (StringUtil.isNumber(s))
			    list.add(Long.parseLong(s));
		    }
		}
		int result = 0;
		if (permissionId > 0 && list.size() > 0) {
		    try {
			dataRuleService.unbindPermissions(permissionId, list);
			result = 1;
		    } catch (Exception e) {
		    ServletActionContext.getRequest().setAttribute("throw", e);
			logger.error(e.getMessage(), e);
		    }
		}
		this.setMessage(result, "PERMISSION", "UNBIND");
		return SUCCESS;
    }

    /**
     * 初始化数据规则表单
     * @Title: initForm
     * @Description: TODO(初始化数据规则表单)
     * */
    @SuppressWarnings("unchecked")
    public String initForm() {
		// 定义一个总的表单初始化属性对象
		List<Map> maps = new ArrayList<Map>();
		try {
		    //设置下拉列表属性规则
		    RuleSelect ruleSelect = new RuleSelect();
		    ruleSelect.setText("name");
		    ruleSelect.setValue("dictid");
		    
		    //数据类型
		    List<DictEntryInfo> ruleDataType = dictionaryService.getDictEntrysByType("RIGHT_LIMIT_DATA_TYPE");
		    Map m1 = this.initformAttribute("rule.dataType", this.initSelectData(ruleDataType, ruleSelect));
		    maps.add(m1);
		    
		    //来源类型
		    List<DictEntryInfo> ruleValueType = dictionaryService.getDictEntrysByType("RIGHT_LIMIT_RULE_TYPE");
		    Map m2 = this.initformAttribute("rule.sourceType", this.initSelectData(ruleValueType, ruleSelect));
		    maps.add(m2);
		    
		    //业务编码
		    List<DataBusiness> dataBusinesses = dataRuleService.getDataBusinesses();
		    ruleSelect = new RuleSelect();
            ruleSelect.setText("bizName");
            ruleSelect.setValue("bizCode");
		    Map m4 = this.initformAttribute("rule.bizCode", this.initSelectData(dataBusinesses, ruleSelect));
		    maps.add(m4);		
		    
		} catch (BizBaseException e) {
		    ServletActionContext.getRequest().setAttribute("throw", e);
		    // TODO Auto-generated catch block
		    // e.printStackTrace();
		    logger.error(e.getMessage(), e);
		} catch (Exception e) {
		    ServletActionContext.getRequest().setAttribute("throw", e);
		    // TODO Auto-generated catch block
		    // e.printStackTrace();
		    logger.error(e.getMessage(), e);
		}
		// 格式化数据
		this.formatData(maps);
		return SUCCESS;
    }

    /**
     * 根据规则ID取数据权限规则下拉列表数据
     * @Title: getSourceListItems
     * @Description: TODO(根据规则ID取数据权限规则下拉列表数据)
     */
    public String getSourceListItems() {
	// HttpServletRequest request = ServletActionContext.getRequest();
	// String ruleId = request.getParameter("ruleid");
	// if(StringUtil.isNumber(ruleId)){
	// try {
	// List<RelationValue> list =
	// dataRuleService.getRelateSqlDatas(Long.parseLong(ruleId));
	// RuleSelect select = new RuleSelect();
	// select.setText("text");
	// select.setValue("value");
	// this.formatData(this.initSelectData(list, select));
	// } catch (NumberFormatException e) {
//	    ServletActionContext.getRequest().setAttribute("throw", e);
	// // TODO Auto-generated catch block
	// //e.printStackTrace();
	// logger.error(e.getMessage(), e);
	// } catch (RuleBadSqlException e) {
//	    ServletActionContext.getRequest().setAttribute("throw", e);
	// // TODO Auto-generated catch block
	// //e.printStackTrace();
	// logger.error(e.getMessage(), e);
	// } catch (Exception e) {
//	    ServletActionContext.getRequest().setAttribute("throw", e);
	// // TODO Auto-generated catch block
	// //e.printStackTrace();
	// logger.error(e.getMessage(), e);
	// }
	// }
	return SUCCESS;
    }

    /**
     * 根据规则ID取数据权限规则下拉树列表数据
     * @Title: getSourceTreeItems
     * @Description: TODO(根据规则ID取数据权限规则下拉树列表数据)
     */
    public String getSourceTreeItems() {
	// HttpServletRequest request = ServletActionContext.getRequest();
	// String ruleId = request.getParameter("ruleid");
	// if(StringUtil.isNumber(ruleId)){
	// try {
	// List<RelationValue> list =
	// dataRuleService.getRelateSqlDatas(Long.parseLong(ruleId));
	// this.formatData(list);
	// } catch (NumberFormatException e) {
//	    ServletActionContext.getRequest().setAttribute("throw", e);
	// // TODO Auto-generated catch block
	// //e.printStackTrace();
	// logger.error(e.getMessage(),e);
	// } catch (RuleBadSqlException e) {
//	    ServletActionContext.getRequest().setAttribute("throw", e);
	// // TODO Auto-generated catch block
	// //e.printStackTrace();
	// logger.error(e.getMessage(),e);
	// }
	// }
	return SUCCESS;
    }

    /**
     * 按条件查询角色数据权限
     * @Title: queryRoleRule
     * @Description: TODO(按条件查询角色数据权限)
     */
    public String queryRoleRule() {
	HttpServletRequest request = ServletActionContext.getRequest();
	String roleId = request.getParameter("roleId");
	String permissionId = request.getParameter("pid");
	if (StringUtil.isNumber(roleId) && StringUtil.isNumber(permissionId)) {
	    Scope scope = new Scope();
	    scope.setPermissionId(Long.parseLong(permissionId));
	    scope.setRoleId(Long.parseLong(roleId));
	    List<Scope> list = authorityService.queryScopes(scope);
	    this.formatData(list);
	}
	return SUCCESS;
    }


    /**
     * 根据权限ID获取绑定的数据规则
     * */
    @SuppressWarnings("unchecked")
    public String getDataRuleSetsByPermissionId() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String pid = request.getParameter("permissionID");
		
		//获取角色ID，如果showType为user类型，则是用户的私用角色ID
		String roleId = request.getParameter("roleID");
		//获取用户ID
		String userId = request.getParameter("userID");
		//showType值为user则是用户分配权限查看数据规则，否则为角色分配权限查看数据规则
		String showType = request.getParameter("showType");
		
		List<Scope> scopes = null;
		long permissionId = Long.parseLong(pid);
		if("user".equals(showType))
		{
			if(StringUtil.isNumber(userId))
				scopes = authorityService.getUserRoleScopes(Long.parseLong(userId),permissionId);
		}else{
			if(StringUtil.isNumber(roleId))
				scopes = authorityService.getRoleScopes(Long.parseLong(roleId), permissionId);
		}
		if (StringUtil.isNumber(pid)) {
		    List<Rule> list = dataRuleService.getBindedRules(Long.parseLong(pid));
		    
		    List<Map> maps = new ArrayList<Map>();
		    for(Rule ruleSet:list)
		    {
		    	Map map = new HashMap();
		    	map.put("ruleId", ruleSet.getRuleSetId());
		    	map.put("ruleName", ruleSet.getRuleName());
		    	if(scopes!=null && scopes.size()>0){
		    		for(Scope scope:scopes)
		    		{
		    			if(scope.getRuleId().longValue()==ruleSet.getRuleSetId().longValue())
		    			{
		    				map.put("chk", "true");
		    				break;
		    			}
		    		}
		    		//如果是用户分配权限中查看数据规则，则判断已有的数据规则是否为用户私有角色所有，如果不是则不可编辑节点，否则可以编辑
		    		if("true".equals(map.get("chk")) && "user".equals(showType)){
		    			boolean exist = false;
			    		for(Scope scope:scopes)
			    		{			    		
		    				if(scope.getRuleId().longValue()==ruleSet.getRuleSetId().longValue() && scope.getRoleId().longValue()==Long.parseLong(roleId))
		    					exist = true;
			    		}		    
			    		if(!exist)
			    			map.put("readonly", "true");
		    		}
		    	}
		    	maps.add(map);
		    }
		    
		    this.formatData(maps);
		}
		return SUCCESS;
    }
    
    /**
     * 根据权限ID获取绑定的数据规则
     * */
    @SuppressWarnings("unchecked")
    public String getDataRulesByPermissionId() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String pid = request.getParameter("permissionID");
		
		//获取角色ID，如果showType为user类型，则是用户的私用角色ID
		String roleId = request.getParameter("roleID");
		//获取用户ID
		String userId = request.getParameter("userID");
		//showType值为user则是用户分配权限查看数据规则，否则为角色分配权限查看数据规则
		String showType = request.getParameter("showType");
		String[] pids;
		if(StringUtil.isEmpty(pid)) {
			pids = new String[0];
		} else {
			pids = pid.split("%2C");
		}
		Map<Long, List<Scope>> scopeMap = new HashMap<Long, List<Scope>>();
		List<Rule> rules = new ArrayList<Rule>();
		for(String permissionIdStr : pids) {
			long permissionId = Long.parseLong(permissionIdStr);
			if("user".equals(showType)) {
				scopeMap.put(permissionId, authorityService.getUserRoleScopes(Long.parseLong(userId),permissionId));
			} else {
				scopeMap.put(permissionId, authorityService.getRoleScopes(Long.parseLong(roleId), permissionId));
			}
			rules.addAll(dataRuleService.getBindedRules(permissionId));
		}
		//剔除rules中的重复值
		rules = new ArrayList<Rule>(new LinkedHashSet<Rule>(rules));
	    List<Map> maps = new ArrayList<Map>();
	    for(Rule rule:rules)
	    {
	    	Map map = new HashMap();
	    	map.put("ruleId", rule.getRuleId());
	    	map.put("ruleName", rule.getRuleName());
	    	int checkedSize = 0;
	    	for(Long permissionId : scopeMap.keySet()) {
	    		List<Scope> scopes = scopeMap.get(permissionId);
	    		if(scopes != null && scopes.size() > 0) {
	    			for(Scope scope:scopes) {
	    				if(scope.getRuleId().longValue() == rule.getRuleId().longValue()) {
	    					checkedSize ++;
	    					break;
	    				}
	    			}
	    		}
	    	}
	    	if(checkedSize == 0) {
	    		map.put("chk", "false");
	    	} else if(checkedSize == scopeMap.size()) {
	    		map.put("chk", "true");
	    	} else {
	    		map.put("chk", "part");
	    	}
	    	
//	    	if(scopes!=null && scopes.size()>0){
//	    		//如果是用户分配权限中查看数据规则，则判断已有的数据规则是否为用户私有角色所有，如果不是则不可编辑节点，否则可以编辑
//	    		if("true".equals(map.get("chk")) && "user".equals(showType)){
//	    			boolean exist = false;
//		    		for(Scope scope:scopes)
//		    		{			    		
//	    				if(scope.getRuleId().longValue()==ruleSet.getRuleSetId().longValue() && scope.getRoleId().longValue()==Long.parseLong(roleId))
//	    					exist = true;
//		    		}		    
//		    		if(!exist)
//		    			map.put("readonly", "true");
//	    		}
//	    	}
	    	maps.add(map);
	    }
	    this.formatData(maps);
		return SUCCESS;
    }    

    /**
     * 私有方法，设置数据规则取值类型和关联类型的字典名称
     * */
    private List setDataRuleDictName(List list) {
	try {
	    // 根据字典类型取字典数据
	    List<DictEntryInfo> valuetype = dictionaryService.getDictEntrysByType("RIGHT_LIMIT_VALUE_TYPE");
	    // 根据字典类型取字典数据
	    List<DictEntryInfo> releationtype = dictionaryService.getDictEntrysByType("RIGHT_LIMIT_RELATION_TYPE");
	    for (Object o : list) {
		Rule dr = (Rule) o;
		// for(DictEntryInfo d:valuetype)
		// {
		// if(dr.getValueType()==Integer.parseInt(d.getDictid()))
		// dr.setValueTypeName(d.getName());
		// }
		// for(DictEntryInfo d:releationtype)
		// {
		// if(dr.getRelationType()==Integer.parseInt(d.getDictid()))
		// dr.setRelationTypeName(d.getName());
		// }
	    }
	} catch (NumberFormatException e) {
	    ServletActionContext.getRequest().setAttribute("throw", e);
	    // TODO Auto-generated catch block
	    // e.printStackTrace();
	    logger.error(e.getMessage(), e);
	} catch (BizBaseException e) {
	    ServletActionContext.getRequest().setAttribute("throw", e);
	    // TODO Auto-generated catch block
	    // e.printStackTrace();
	    logger.error(e.getMessage(), e);
	}
	return list;
    }

    /**
     * 初始化规则组表单方法
     * */
    public String initRuleSetForm() {
		// 定义一个总的表单初始化属性对象
		List<Map> maps = new ArrayList<Map>();
		try {
		    // 设置下拉列表属性规则
		    RuleSelect ruleSelect = new RuleSelect();
		    ruleSelect.setText("name");
		    ruleSelect.setValue("dictid");
	
		    // 设置应用下拉列表属性规则
		    RuleSelect appRuleSelect = new RuleSelect();
		    appRuleSelect.setText("appName");
		    appRuleSelect.setValue("appId");
		    // 查找所有的应用
		    List<App> apps = appService.getApps();
		    // 设置表单所属应用初始化属性
		    Map m1 = this.initformAttribute("ruleSet.appId", this.initSelectData(apps, appRuleSelect));
		    maps.add(m1);
		    // 取数据规则类型字典数据
		    List<DictEntryInfo> relation = dictionaryService.getDictEntrysByType("RULE_SET_RELATION");
		    // 初始化数据规则类型字典数据
		    Map m2 = this.initformAttribute("ruleSet.relation", this.initSelectData(relation, ruleSelect));
		    maps.add(m2);
	
		} catch (BizBaseException e) {
		    ServletActionContext.getRequest().setAttribute("throw", e);
		    logger.error(e.getMessage(), e);
		} catch (Exception e) {
		    ServletActionContext.getRequest().setAttribute("throw", e);
		    logger.error(e.getMessage(), e);
		}
		// 格式化数据
		this.formatData(maps);
		return SUCCESS;
    }

    /**
     * 获取角色下权限绑定的数据规则组
     * **/
    public String getRoleScopes() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String roleId = request.getParameter("roleId");
		String permissionId = request.getParameter("permissionId");
		if (StringUtil.isNumber(roleId) && StringUtil.isNumber(permissionId)) {
		    List<Scope> list = authorityService.getRoleScopes(Long.parseLong(roleId), Long.parseLong(permissionId));
		    this.formatData(list);
		}
		return SUCCESS;
    }
    
    /**
     * 分页查询数据规则
     * */
    public String queryRule(){
    	if(rule==null)
    		rule = new Rule();
    	excludeNullProperties(rule);
    	Pagination result = dataRuleService.queryRules(rule,pagination);
    	List<Rule> list = result.getList();
    	DataBusinessList bizList = dataRuleService.getDataBusinesses();
    	for (Rule element : list)
    	{
    	    DataBusiness biz = bizList.get(element.getBizCode());
    	    if (biz != null)
    	    {
    	        element.setBizName(biz.getBizName());
    	    }
    	}
    	this.formatDatagirdData(result.getList(), result);
    	return SUCCESS;
    }

    @Autowired
    private DataRuleService dataRuleService;

    @Autowired
    private DictionaryService dictionaryService;

    @Autowired
    private AppService appService;

    @Autowired
    AuthorityService authorityService;

    private Rule rule = null;

    public Rule getRule() {
	return rule;
    }

    public void setRule(Rule rule) {
	this.rule = rule;
    }
}
