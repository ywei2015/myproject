package com.talkweb.twdpe.webcomponent.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.twdpe.base.common.CommonConfig;
import com.talkweb.twdpe.base.dict.pojo.DictEntryInfo;
import com.talkweb.twdpe.base.dict.pojo.DictTypeInfo;
import com.talkweb.twdpe.base.dict.service.DictionaryService;
import com.talkweb.twdpe.core.data.DataMap;
import com.talkweb.twdpe.core.data.DatasetList;
import com.talkweb.twdpe.core.data.IData;
import com.talkweb.twdpe.core.data.IDataset;
import com.talkweb.twdpe.core.exception.BizBaseException;
import com.talkweb.twdpe.core.log.Logger;
import com.talkweb.twdpe.core.log.LoggerFactory;
import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.twdpe.web.jcontrols.RuleTree;
import com.talkweb.twdpe.webcomponent.common.action.BusinessAction;

/**
 * 文件名称: DictMngAction.java 内容摘要:
 * @author: 苏东辉 张文
 * @version: 1.0
 * @Date: 2011-5-30 上午09:44:46
 * 
 *        修改历史: 修改日期 修改人员 版本 修改内容 ----------------------------------------------
 *        2011-5-30 张文 1.0 1.0 XXXX
 * 
 *        版权: 版权所有(C)2011 公司: 拓维信息系统股份有限公司
 */
public class DictMngAction extends BusinessAction {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DictMngAction.class);

    /**
     * @Title: addDict
     * @Description: 增加字典项信息
     * @return
     */
    public String addDictValue() {    	
    	
    	this.setDictEntryDefaultValue();
    	
		if (dictEntryInfo.getDicttypeid() == null) {
		    this.setErrorMessage("字典类型不能为空");
		} else {
		    try {
				String entryid = dictEntryInfo.getDictid();
				String typeid = dictEntryInfo.getDicttypeid();
				DictEntryInfo info = dictionaryService.getDictEntryById(typeid,entryid);
				if (info != null && info.getDictid() != null && !"".equals(info.getDictid())) {
				    this.setMessage(2, "DICTVALUE", "ADD");//设置操作提示
				} else {
				    boolean bl = dictionaryService.addDictEntry(dictEntryInfo);
				    if (bl) {
						this.setMessage(1, "DICTVALUE", "ADD", new String[] { CommonConfig.keyid },
							new Object[] { dictEntryInfo.getDictid() });//设置操作提示，并添加额外的属性值
				    } else {
				    	this.setMessage(0, "DICTVALUE", "ADD");
				    }
				}
		    } catch (Exception e) {
		    	ServletActionContext.getRequest().setAttribute("throw", e);
	    		this.setMessage(0, "DICTVALUE", "ADD", e);
				logger.error(e.getMessage(), e);
		    }
		}
		this.dictEntryInfo = null;
		return SUCCESS;
    }
    
    private void setDictEntryDefaultValue(){
    	if("true".equals(dictEntryInfo.getAllowdelete())) {
    		dictEntryInfo.setAllowdelete("1");
    	} else {
    		dictEntryInfo.setAllowdelete("0");
    	}
		if ("true".equals(dictEntryInfo.getAllowmodify())) {
		    dictEntryInfo.setAllowmodify("1");
		} else {
    		dictEntryInfo.setAllowmodify("0");
    	}
		if ("true".equals(dictEntryInfo.getAllownull())) {
		    dictEntryInfo.setAllownull("1");
		} else {
    		dictEntryInfo.setAllownull("0");
    	}
		if ("true".equals(dictEntryInfo.getIsshow())) {
		    dictEntryInfo.setIsshow("1");	
		} else {
    		dictEntryInfo.setIsshow("0");
    	}
    }

    /**
     * 
     * @Title: modifyDictEntry
     * @Description: 修改字典项
     */
    public String modifyDictEntry() {
    	
    	this.setDictEntryDefaultValue();
	
		if (dictEntryInfo.getDicttypeid() == null) {
		    this.setErrorMessage("字典类型不能为空");
		} else {
		    try {
				boolean bl = dictionaryService.modifyDictEntry(dictEntryInfo);
				this.setMessage(bl ? 1 : 0, "DICTVALUE", "MODIFY");
		    } catch (Exception e) {
		    	ServletActionContext.getRequest().setAttribute("throw", e);
	    		this.setMessage(0, "DICTVALUE", "MODIFY", e);
				logger.error(e.getMessage(), e);
		    }
		}
		this.dictEntryInfo = null;
		return SUCCESS;
    }

    /**
     * 
     * @Title: getDictEntry
     * @Description: 根据ID号获取实体
     */
	public String getDictEntryById() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String dictID = request.getParameter("dictid");
		String DICTTYPEID = request.getParameter("dicttypeid");
		try {
		    DictEntryInfo dictEntryInfo = dictionaryService.getDictEntryById(DICTTYPEID,dictID);
		    if(dictEntryInfo.getDicttypeid()!=null && dictEntryInfo.getDicttypeid().equals("ROOT"))
		    	dictEntryInfo.setDicttypename("顶级节点");
		    this.formatData(this.addPrefix(dictEntryInfo, "dictEntryInfo."));//给对象属性增加统一的前缀名，并格化输出
		} catch (Exception e) {
			request.setAttribute("throw", e);
			//如果出错，则输出一个空的JSON对象
			this.setErrorMessage(e.getMessage(),e);
		    logger.error(e.getMessage(), e);
		}
		return SUCCESS;
    }

    /**
     * @Title: queryDictEntryByPage
     * @Description: 分页查询所有字典项，通过分页参数定义可以不同的查询结果数 
     * */
    public String queryDictEntryByPage() {
		if (dictEntryInfo == null)
		    dictEntryInfo = new DictEntryInfo();
		this.excludeNullProperties(dictEntryInfo);
		try {
		    List<DictEntryInfo> list = dictionaryService.queryDictEntryByPage(pagination, dictEntryInfo);
		    this.formatDatagirdData(list, pagination);//格式化DataGrid控件数据
		} catch (BizBaseException e) {
			ServletActionContext.getRequest().setAttribute("throw", e);
			@SuppressWarnings("rawtypes")
			List emptylist = new ArrayList();
			this.formatDatagirdData(emptylist, pagination);//格式化DataGrid控件数据
		    logger.error(e.getMessage(), e);
		}
		dictEntryInfo = null;
		return SUCCESS;
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public String initDictEntryTree()
    {
        String viewInfo = ServletActionContext.getRequest().getParameter("viewInfo");
        if (dictEntryInfo == null)
            dictEntryInfo = new DictEntryInfo();
        this.excludeNullProperties(dictEntryInfo);
        List<DictEntryInfo> list = dictionaryService
                .queryDictEntryTree(dictEntryInfo);
        List<String> ids = new ArrayList<String>();
        for (DictEntryInfo dict : list)
        {
            ids.add(dict.getDictid());

        }
        List treeList = new ArrayList();
        for (DictEntryInfo dict : list)
        {
            Map treeMap = new HashMap();
            if (dict.getParentId() == null || !ids.contains(dict.getParentId()))
            {
                treeMap.put("pid", "ROOT");
            } else
            {
                treeMap.put("pid", dict.getParentId());
            }
            if ("true".equals(viewInfo))
            {
                treeMap.put("val",dict.getName()
                    + "　[<a href='javascript:void(0);' class='btn' onClick='doViewDict4Tree(\""
                    + dict.getDictid() + "\")'>" + dict.getDictid()
                    + "</a>]");
            } else
            {
                treeMap.put("val", dict.getName());
            }
            treeMap.put("id", dict.getDictid());
            treeMap.put("allowdelete", dict.getAllowdelete() + "");
            treeMap.put("allowmodify", dict.getAllowmodify() + "");
            treeList.add(treeMap);
        }
        this.formatData(treeList);
        return SUCCESS;
    }

    /**
     * @throws Exception 
     * @throws  
     * @Title: initDictTypeTree
     * @Description: 初始化字典类型树 by struts2-json-plugin 
     * */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public String initDictTypeTree() throws Exception {
        int rows = 10000;
        int page = 1;
        Pagination pagination = new Pagination(page, rows);
        HttpServletRequest request = ServletActionContext.getRequest();
        List<DictTypeInfo> dictList = dictionaryService
                .queryDictTypeAll(pagination);
        // 设置树控件数据映射关系
        RuleTree wtree = new RuleTree();
        wtree.setId("dicttypeid");
        wtree.setVal("dicttypename");
        wtree.setPid("parentid");
        HashMap map = new HashMap();
        map.put("allowmodify", "allowmodify");
        map.put("type", "type");
        // 设置树节点额外的属性
        wtree.setMap(map);
        // 格式化树控件数据,按树控件的ID，VAL，PID的顺序
        List<Map> l = this.initTreeData(dictList, wtree);
        String formInit = request.getParameter("formInit");
        if (formInit != null)
        {
            List list = new ArrayList();
            // 初始化表单属性
            list.add(this.initformAttribute("dictTypeInfo.parentid", l));
            // 格式化数据
            this.formatData(list);
        } else
        {
            // 格式化数据
            this.formatData(l);
        }
        return SUCCESS;
    }

    /**
     * @Title: addDictType
     * @Description: 增加字典类型
     * */
    public String addDictType() {
		try {
		    String typeId = dictTypeInfo.getDicttypeid();
		    DictTypeInfo info = dictionaryService.getDictTypeByID(typeId);
		    if (info != null && info.getDicttypeid() != null && !"".equals(info.getDicttypeid())) {
		    	this.setMessage(2, "DICTTYPE", "ADD");
		    } else {
				boolean bl = dictionaryService.addDictType(dictTypeInfo);
				if (bl) {
				    this.setMessage(1, "DICTTYPE", "ADD", new String[] { "dicttypeid","allowmodify","type","parentid","dicttypename" },
					    new Object[] { dictTypeInfo.getDicttypeid(),dictTypeInfo.getAllowmodify(),dictTypeInfo.getType(),dictTypeInfo.getParentid(),dictTypeInfo.getDicttypename() });
				} else
				    this.setMessage(0, "DICTTYPE", "ADD");
		    }		    
		} catch (Exception e) {
		    ServletActionContext.getRequest().setAttribute("throw", e);
		    this.setMessage(0, "DICTTYPE", "ADD", e);
		    logger.error(e.getMessage(), e);		   
		}
		return SUCCESS;
    }

    /**
     * @throws Exception 
     * @Title: modifyDictType
     * @Description: 修改字典类型
     * */
	public String modifyDictType() throws Exception {
		    int result = 0;
		    if (!dictionaryService.checkParentDicttype(dictTypeInfo.getDicttypeid(), dictTypeInfo.getParentid()))
		    {
		    	result = 2;
		    }
		    else
		    {
				boolean bl = dictionaryService.modifyDictType(dictTypeInfo);
				result = bl == true ? 1 : 0;
		    }
		    this.setMessage(result, "DICTTYPE", "MODIFY", new String[] { "dicttypeid","allowmodify","type","parentid","dicttypename" },
				    new Object[] { dictTypeInfo.getDicttypeid(),dictTypeInfo.getAllowmodify(),dictTypeInfo.getType(),dictTypeInfo.getParentid(),dictTypeInfo.getDicttypename() });
		return SUCCESS;
    }

    /**
     * @Title: deleteDictType
     * @Description: 批量删除字典类型
     * */
    @SuppressWarnings("rawtypes")
	public String deleteDictType() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String dictTypeID = request.getParameter("id");
		List<String> list = new ArrayList<String>();
		if (dictTypeID != null) {
		    for (String s : dictTypeID.split(",")) {
			if (s != null && s.trim().length() > 0) {
			    list.add(s.trim());
			}
		    }
		}
		int result = 0;
		try {
		    if (null != list){
			    // 查询其下是否含有字典项和子字典类型，必须删除所有子项后才能删除
			    Iterator itTypes = list.iterator();
			    while (itTypes.hasNext()) {
					try {
					    String delID = (String) itTypes.next();
					    IData idMap = new DataMap();
					    idMap.put("DICTTYPEID", delID);
			
					    // 是否有字典项 sResult:2
					    List<DictEntryInfo> entryInfos = dictionaryService.queryDictEntryByTypeID(delID);
					    if (null != entryInfos && entryInfos.size() > 0) {
						result = 2;
						continue;
					    }
			
					    // 是否有子字典类型 sResult:3
					    List<DictTypeInfo> typeInfos = dictionaryService.queryDictTypeByParentID(delID);
					    if (null != typeInfos && typeInfos.size() > 0) {
						result = 3;
						continue;
					    }
					} finally {
					    if (result != 0) {
						break;
					    }
					}
			    }
		    }
		    if (result == 0) {
				// 物理删除
				IDataset typeids = new DatasetList(list);
		
				int[] dels = dictionaryService.deleteDictType(typeids);
				if (null != dels) {
				    for (int i = 0; i < dels.length; i++) {
					if (dels[i] != 1)
					    throw new BizBaseException("物理删除字典类型操作失败：【" + i + "】");
				    }
				}
				result = 1;
		    }
		    this.setMessage(result, "DICTTYPE", "DELETE");
		} catch (Exception e) {
			request.setAttribute("throw", e);
	    	this.setMessage(0, "DICTTYPE", "DELETE", e);
		    logger.error(e.getMessage(), e);
		}
		return SUCCESS;
    }

    /**
     * @throws Exception 
     * @Title: deleteDictValue
     * @Description: 批量删除字典项
     * */
    public String deleteDictValue() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String typeid = request.getParameter("typeid");
		String dictid = request.getParameter("dictid");
		List<String> listtype = new ArrayList<String>();
		List<String> listdict = new ArrayList<String>();
		if (typeid != null) {
		    for (String s : typeid.split(",")) {
				if (s != null && s.trim().length() > 0) {
				    listtype.add(s.trim());
				}
		    }
		}
		if (dictid != null) {
		    for (String s : dictid.split(",")) {
				if (s != null && s.trim().length() > 0) {
				    listdict.add(s.trim());
				}
		    }
		}

		int result = 0;
			boolean bl = false;
		    if (typeid != null && dictid != null) {		    
			    for (int i = 0; i < listtype.size(); i++) {
					String entryTypeID = listtype.get(i);
					String entryID = listdict.get(i);
					bl = dictionaryService.deleteDictEntry(entryTypeID, entryID);
					if (!bl) {
					    logger.error("物理删除字典项操作失败：【" + i + "】");
					    throw new BizBaseException("物理删除字典项操作失败：【" + i + "】");
					}
			    }
		    }
		    result = bl ? 1 : 0;
		    this.setMessage(result, "DICT", "DELETE");
		return SUCCESS;
    }

    /**
     * @throws Exception 
     * @Title: initAddDictType
     * @Description: 初始化表单字典类型数据
     * */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public String initAddDictType() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		Map pageData = new HashMap();
		    DictTypeInfo dictTypeInfo = dictionaryService.getDictTypeByID(id);		    
		    pageData.put("parentDictTypeName", dictTypeInfo.getDicttypename());
		    pageData.put("parentid", dictTypeInfo.getDicttypeid());
		    pageData.put("parentname", dictTypeInfo.getDicttypename());
		return SUCCESS;
    }

    /**
     * @throws Exception 
     * @Title: initAddDictType
     * @Description: 初始化修改表单字典类型数据
     * */
	public String initModifyDictType() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		DictTypeInfo dictTypeInfo = dictionaryService.getDictTypeByID(id);
		formatData(this.addPrefix(dictTypeInfo, "dictTypeInfo."));
		return SUCCESS;
    }

    /**
     * @Title: queryDictTypeByParentID
     * @Description: 根据父字典类型ID查询其下所有子节点
     * */
	@SuppressWarnings("rawtypes")
	public String queryDictTypeByParentID() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String PARENTID = request.getParameter("dicttypeid");
		try {
			List<DictTypeInfo> list = dictionaryService.queryDictTypeByParentID(PARENTID);
		    this.formatData(list);
		} catch (BizBaseException e) {
			request.setAttribute("throw", e);
			List emptyList = new ArrayList();
			this.formatData(emptyList);
			logger.error(e.getMessage(),e);
		}
		return SUCCESS;
    }

    /**
     * 通过注入方式初始化的字典管理类,用于字典管理的增，删，改，查的数据库操作类
     * */
	@Autowired
    private DictionaryService dictionaryService;

    /**
     * 字典类型对象，用于添加和修改字典类型时封装的对象
     * */
    private DictTypeInfo dictTypeInfo = null;

    public DictTypeInfo getDictTypeInfo() {
    	return dictTypeInfo;
    }

    public void setDictTypeInfo(DictTypeInfo dictTypeInfo) {
    	this.dictTypeInfo = dictTypeInfo;
    }

    /**
     * 字典项对象，用于添加和修改字典项时封装的对象
     * */
    private DictEntryInfo dictEntryInfo = null;

    public DictEntryInfo getDictEntryInfo() {
    	return dictEntryInfo;
    }

    public void setDictEntryInfo(DictEntryInfo dictEntryInfo) {
    	this.dictEntryInfo = dictEntryInfo;
    }

}
