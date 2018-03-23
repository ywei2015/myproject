package com.talkweb.xwzcxt.action;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.aspectj.weaver.NewParentTypeMunger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.twdpe.core.util.DateUtil;
import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.twdpe.web.commons.StaticUploadInfo;
import com.talkweb.twdpe.web.jcontrols.RuleSelect;
import com.talkweb.twdpe.web.jcontrols.RuleTree;
import com.talkweb.twdpe.web.session.main.Session;
import com.talkweb.twdpe.web.session.util.SessionFactory;
import com.talkweb.xwzcxt.log.Log;
import com.talkweb.xwzcxt.pojo.GeneralItem;
import com.talkweb.xwzcxt.pojo.MyLogPojo;
import com.talkweb.xwzcxt.pojo.TTaskPointcheckPojo;
import com.talkweb.xwzcxt.pojo.TbIntegrateInfo;
import com.talkweb.xwzcxt.service.JobObjectsService;
import com.talkweb.xwzcxt.service.MyLogService;
import com.talkweb.xwzcxt.util.ExcelReadUtil;
import com.talkweb.xwzcxt.util.FileOperate;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

public class JobObjectsAction extends baseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8058213201981056018L;

	private static final Logger logger = LoggerFactory
			.getLogger(JobObjectsAction.class);

	@Autowired
	private JobObjectsService jobObjectsService;

	@Autowired
	private MyLogService logService;
	
	private HttpServletRequest request = ServletActionContext.getRequest();
	private HttpServletResponse response = ServletActionContext.getResponse();
	private TbIntegrateInfo tbIntegrateInfo = new TbIntegrateInfo();
	private TTaskPointcheckPojo ttaskPointCheck = new TTaskPointcheckPojo();
	
	
	public TTaskPointcheckPojo getTtaskPointCheck() {
		return ttaskPointCheck;
	}

	public void setTtaskPointCheck(TTaskPointcheckPojo ttaskPointCheck) {
		this.ttaskPointCheck = ttaskPointCheck;
	}

	public TbIntegrateInfo getTbIntegrateInfo() {
		return this.tbIntegrateInfo;
	}

	public void setTbIntegrateInfo(TbIntegrateInfo tbIntegrateInfo) {
		this.tbIntegrateInfo = tbIntegrateInfo;
	}

	private boolean isLogin() {
		Session session = SessionFactory.getInstance(request, response);
		Map user = (Map) session.getAttribute("USERSESSION");
		if (user != null) {
			return true;
		}
		return false;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String getTableTypeTree() {
		try {
			if (!isLogin()) {
				this.formatData("请先登录！");
				return SUCCESS;
			}
			List list = jobObjectsService.getTableTypeTree();
			RuleTree wtree = new RuleTree();
			wtree.setId("cTabletypeId");
			wtree.setVal("cTabletypeName");
			wtree.setPid("cTabletypeUpid");
			HashMap map = new HashMap();
	        map.put("allowmodify", "allowmodify");
	        map.put("type", "type");
	        // 设置树节点额外的属性
	        wtree.setMap(map);
			List<Map> l = this.initTreeData(list, wtree);
			this.formatData(l);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		return SUCCESS;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	public String getOrgList(){
		HttpServletRequest request = ServletActionContext.getRequest ();
		try{
			List data=jobObjectsService.getOrgList();
			if(data != null && data.size()>0){
				RuleSelect wtree = new RuleSelect();
				wtree.setValue("orgid");
				wtree.setText("orgname");
				List<Map> l = this.initSelectData(data, wtree);
				this.formatData(l);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}		
	
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	public String getObjectInfo() {
		try {
			String cBasedataId = request.getParameter("cBasedataId");
			String cTableTypeId = request.getParameter("cTabletypeId");
			String cBasedataName = request.getParameter("cBasedataName");
			String cOrgid = request.getParameter("cOrgid");
			String cBasedataUserCode = request.getParameter("cBasedataUserCode");
			String positionId = request.getParameter("positionId");
			String c_basedata_code = request.getParameter("tbIntegrateInfo.c_basedata_code");
			String c_basedata_name = request.getParameter("tbIntegrateInfo.c_basedata_name");
			String c_basedata_username = request.getParameter("tbIntegrateInfo.c_basedata_username");
			Map map = new HashMap();

			if (c_basedata_code != null && !c_basedata_code.equals("")) {
				map.put("c_basedata_code", "%" + c_basedata_code + "%");
			}
			if (c_basedata_name != null && !c_basedata_name.equals("")) {
				map.put("c_basedata_name", "%" + c_basedata_name + "%");
			}
			if (c_basedata_username != null && !c_basedata_username.equals("")) {
				map.put("c_basedata_username", "%" + c_basedata_username + "%");
			}
 
			if (cBasedataUserCode != null && !cBasedataUserCode.equals("")) {
				map.put("c_basedata_usercode", "%" + cBasedataUserCode + "%");
			}
			for (int i = 1; i <= 30; i++) {
				String col = request.getParameter("tbIntegrateInfo.col" + i);
				if (col != null && !col.trim().equals("")) {
					map.put("col" + i, "%" + col + "%");
				}
			}

			map.put("cTabletypeId", cTableTypeId);
			map.put("cBasedataName", cBasedataName);
			map.put("positionId", positionId);

			if (cBasedataId != null && !cBasedataId.equals("null")
					&& !cBasedataId.equals("")) {
				map.put("cBasedataId", cBasedataId);
			}

			Long cPrintUser = null;
			String user = request.getParameter("cPrintUser");
			if (user != null && !user.equals("")) {
				cPrintUser = Long.parseLong(user);
				map.put("cPrintUser", cPrintUser);
			}

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String printTime = request.getParameter("cPrintTime");
			if (printTime != null && !printTime.equals("")) {
				map.put("cPrintTime", sdf.parse(printTime));
			}

			String org = request.getParameter("cOrgid");
			if (org != null && !org.equals("")) { 				 
				map.put("cOrgid", org);				
			}

			String cScanCode = request.getParameter("cScanCode");
			if (cScanCode != null && !cScanCode.trim().equals("")) {
				map.put("cScanCode", "%" + cScanCode + "%");
			}
			String ptStatus=null;
			ptStatus=request.getParameter("ptStatus");
			if (ptStatus != null && !ptStatus.trim().equals("")) {
				map.put("ptStatus", ptStatus);
			}
			String pcount=request.getParameter("c_print_count");
			if (pcount != null && !pcount.trim().equals("")) {
				map.put("c_print_count", pcount);
			}
			if (pagination == null) {
				pagination = new Pagination(1, 20);
			}
			jobObjectsService.getObjectInfo(pagination, map);
			this.formatDatagirdData(pagination.getList(), pagination);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		return SUCCESS;
	}

	private String columnsConfig(final String[][] baseColumns) {
		try {
			if (!isLogin()) {
				this.formatData("请先登录！");
				return SUCCESS;
			}
			Map params = new HashMap();
			String cTabletypeId = request.getParameter("cTabletypeId");
			params.put("cTabletypeId", cTabletypeId);
			List list = jobObjectsService.getColumnsConfig(params);

			// 基本列配置

			List l = new ArrayList();
			for (int i = 0; baseColumns != null && i < baseColumns.length; i++) {
				Map map = new HashMap();
				map.put("name", baseColumns[i][0]);
				map.put("text", baseColumns[i][1]);
				map.put("isshow", baseColumns[i][2]);
				map.put("width", "150px");
				// map.put("renderer",
				// "renderer(value,'"+baseColumns[0][i]+"')");
				l.add(map);
			}
			for (Object ob : list) {
				TbIntegrateInfo tb = (TbIntegrateInfo) ob;
				Map map = new HashMap();
				String isShowScan = tb.getcIsShowScan();
				map.put("text", tb.getC_column_name().toLowerCase());
				map.put("name", tb.getC_infocol_name().toLowerCase());
				map.put("isshow", isShowScan==null?"":isShowScan.toLowerCase());
				map.put("width", "150px");
				// map.put("renderer",
				// "renderer("+tb.getC_infocol_name().toLowerCase()+")");
				l.add(map);
			}

			this.formatData(l);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		return SUCCESS;
	}

	public String getColumnsConfig() {
		// 基本列配置
		final String[][] baseColumns = null;
		return columnsConfig(baseColumns);
	}

	public String getDynamicColums() {
		return columnsConfig(null);
	}

	public String deleteObjects() {
		try {
			if (!isLogin()) {
				this.formatData("请先登录!");
				return SUCCESS;
			}
			String ids[] = request.getParameter("cBasedataId").split(",");
			if (ids == null) {
				this.formatData("请选择数据!");
				return SUCCESS;
			}
			Map map = new HashMap();
			List l = new ArrayList();
			for (String id : ids) {
				l.add(id);
			}
			map.put("cBasedataId", l);
			List<?>  inteList = jobObjectsService.getIntegrateById(l);
			
			int count = jobObjectsService.deleteObjects(map);
			
			//记录数据日志
			JSONArray inteListjson = JSONArray.fromObject(inteList);  
			logService.addLogInfo(request, response, "1", "过程行为支持系统", "2", "基础管理", "作业对象管理", MyLogPojo.del, "作业对象管理-删除对象", "成功", "1", "TB_INTEGRATE_INFO", inteListjson.toString(), "");
			
			if (count > 0) {
				int result = jobObjectsService.deletePointCheckByCBaseDataId(map);
				if (result < 1) {
					this.formatData("删除点检记录失败！");
				} else {
					this.formatData("成功删除了" + count + "条数据!");
				}
			} else {
				this.formatData("删除失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		return SUCCESS;
	}

//	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String updateObject() {
		try {
			if (!isLogin()) {
				this.formatData("请先登录!");
				return SUCCESS;
			}
			String cOrgid = request.getParameter("cOrgid");
			String cTabletypeId = request.getParameter("cTabletypeId");

			String cBasedataId = request.getParameter("cBasedataId");
			String cScanDetail = makeScanDetails(cTabletypeId);
			if (cBasedataId != null) {
				tbIntegrateInfo.setcBasedataId((new BigDecimal(cBasedataId)));
			}
			if (cTabletypeId != null) {
				tbIntegrateInfo.setcTabletypeId(Long.parseLong(cTabletypeId));
			}
			if (cOrgid != null) {
				tbIntegrateInfo.setcOrgid(Long.parseLong(cOrgid));
			}
			tbIntegrateInfo.setcScanDetail(cScanDetail);

			List l = new ArrayList();
			l.add(cBasedataId);
			List<?>  oldinteList = jobObjectsService.getIntegrateById(l);
			int count = jobObjectsService.updateObject(tbIntegrateInfo);
			
			//记录数据日志
			JSONObject newobjjson = JSONObject.fromObject(tbIntegrateInfo);
			JSONArray oldjson = JSONArray.fromObject(oldinteList);
			logService.addLogInfo(request, response, "1", "过程行为支持系统", "2", "基础管理", "作业对象管理", MyLogPojo.change, "作业对象管理-修改对象", "成功", "1", "TB_INTEGRATE_INFO", oldjson.toString(), newobjjson.toString());
			
			if (count > 0) {
				ttaskPointCheck.setcBasedataId(tbIntegrateInfo.getcBasedataId().toString());
				ttaskPointCheck.setcScanCode(tbIntegrateInfo.getcScanCode());
				ttaskPointCheck.setcExecUserid(tbIntegrateInfo.getcBasedataUserCode());
				if (cTabletypeId != null) {
					ttaskPointCheck.setcObjectTypeid(new BigDecimal(cTabletypeId));
				}
				ttaskPointCheck.setcIsdelete("0");
				int result = jobObjectsService.updatePointCheckByCBasedataId(ttaskPointCheck);
				if (result < 1) {
					this.formatData("修改点检记录失败！");
				} else {
					this.formatData("成功修改了" + count + "条数据!");
				}
			} else {
				this.formatData("修改失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		return SUCCESS;
	}

	public String addObject() {
		try {
			Session session = SessionFactory.getInstance(request, response);
			Map user = (Map) session.getAttribute("USERSESSION");
			if (user == null) {
				this.formatData("请先登录!");
				return SUCCESS;
			}
			String cOrgid = request.getParameter("cOrgid");
			String cTabletypeId = request.getParameter("cTabletypeId");
			// String cScanDetail=request.getParameter("cScanDetail");
			// String cIsscandata=request.getParameter("cIsscandata");
			String json = makeScanDetails(cTabletypeId);

			tbIntegrateInfo.setcScanDetail(json);
			if (cTabletypeId != null) {
				tbIntegrateInfo.setcTabletypeId(Long.parseLong(cTabletypeId));
			}
			if (cOrgid != null && !cOrgid.trim().equals("")) {
				tbIntegrateInfo.setcOrgid(Long.parseLong(cOrgid));
			}
			// tbIntegrateInfo.setcScanDetail(cScanDetail);

			String cUpdateUser = user.get("name").toString();
			tbIntegrateInfo.setcUpdateUser(cUpdateUser);

			String cBasedataId = jobObjectsService.getMaxBasedataId(tbIntegrateInfo);
			tbIntegrateInfo.setcBasedataId(new BigDecimal(cBasedataId));
			int count = jobObjectsService.addObject(tbIntegrateInfo);
			
			//记录数据日志
			JSONObject addjson = JSONObject.fromObject(tbIntegrateInfo);
			logService.addLogInfo(request, response, "1", "过程行为支持系统", "2", "基础管理", "作业对象管理", MyLogPojo.add, "作业对象管理-新增对象", "成功", "1", "TB_INTEGRATE_INFO", "", addjson.toString());
			
			if (count < 1) {
				this.formatData("新增作业对象失败！");
			} else {
				ttaskPointCheck.setcBasedataId(tbIntegrateInfo.getcBasedataId().toString());
				ttaskPointCheck.setcScanCode(tbIntegrateInfo.getcScanCode());
				ttaskPointCheck.setcExecUserid(tbIntegrateInfo.getcBasedataUserCode());
				if (cTabletypeId != null) {
					ttaskPointCheck.setcObjectTypeid(new BigDecimal(cTabletypeId));
				}
				ttaskPointCheck.setcIsdelete("0");
				int result = jobObjectsService.addTaskPointCheck(ttaskPointCheck);
				if (result < 1) {
					this.formatData("新增点检记录失败！");
				} else {
					this.formatData("成功新增了" + count + "条数据!");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.formatData("新增失败！");
			logger.error(e.getMessage(), e);
		}
		return SUCCESS;
	}

	public String printCode() {
		try {
			Session session = SessionFactory.getInstance(request, response);
			Map user = (Map) session.getAttribute("USERSESSION");
			if (user == null) {
				this.formatData("请先登录!");
				return SUCCESS;
			}
			String username = user.get("name").toString();
			String cBaseDataId = request.getParameter("cBasedataId");
			String cScanCode = request.getParameter("cScanCode");
			String cPrintCount = request.getParameter("cPrintCount");
			HashMap param = new HashMap();

			param.put("cBasedataId", cBaseDataId);
			TbIntegrateInfo tb = null;
			if (pagination == null) {
				pagination = new Pagination(1, 15);
			}
			
			jobObjectsService.getObjectInfo(pagination, param);
			if (pagination != null) {
				List list = pagination.getList();
				if (list != null) {
					tb = (TbIntegrateInfo) list.get(0);
				}
				//cScanCode = Integer.parseInt(cPrintCount)>1?tb.getcScanCode():cScanCode;
				String json = makeScanDetails(tb.getcTabletypeId().toString(),
						tb);
				BigDecimal cBasedataId = tb.getcBasedataId();
				tb = new TbIntegrateInfo();
				tb.setcBasedataId(cBasedataId);
				tb.setcScanDetail(json);
				jobObjectsService.updateObject(tb);
			}
			
			param.put("cScanCode", cScanCode);
			param.put("cPrintUser", username);
			param.put("cPrintCount", cPrintCount);
			
			int count = jobObjectsService.printCode(param);
			if (count < 1) {
				this.formatData("服务器响应失败！");
			} else {
				ttaskPointCheck.setcBasedataId(cBaseDataId);
				ttaskPointCheck.setcScanCode(cScanCode);
				int result = jobObjectsService.updatePointCheckByCBasedataId(ttaskPointCheck);
				if (result < 1) {
					this.formatData("服务器响应失败！");
				} else {
					this.formatData("服务器响应成功！");
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String getOrgname() {
		try {
			if (!isLogin()) {
				return SUCCESS;
			}
			String orgid = request.getParameter("cOrgid");
			HashMap map = new HashMap();
			map.put("cOrgid", orgid);
			String orgname = jobObjectsService.getOrgname(map);
			this.formatData(orgname);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String getCurrentPrintCount() {
		try {
			if (!isLogin()) {
				return SUCCESS;
			}
			String cBasedataId = request.getParameter("cBasedataId");
			// System.out.println(cBasedataId+"############################");
			HashMap map = new HashMap();
			map.put("cBasedataId", cBasedataId);
			//String count = jobObjectsService.getCurrentPrintCount(map);
			TbIntegrateInfo tb = jobObjectsService.getCurrentPrintCount(map);
			
			StringBuffer sb = new StringBuffer();
			sb.append("{'cPrintCount':'");
			sb.append(tb.getcPrintCount() + "',");
			sb.append("'cScanCode':'");
			sb.append(tb.getcScanCode() + "'");
			
			if (tb != null) {
				sb.append("}");
				this.formatData(sb.toString());
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	private String makeScanDetails(String cTabletypeId,
			TbIntegrateInfo tbIntegrateInfo) {
		this.tbIntegrateInfo = tbIntegrateInfo;
		return makeScanDetails(cTabletypeId);
	}

	private String makeScanDetails(String cTabletypeId) {
		try {
			HashMap map = new HashMap();
			map.put("cIsscandata", 1);
			map.put("cTabletypeId", Long.parseLong(cTabletypeId));
			List list = jobObjectsService.getColumnsConfig(map); // 获得扫码详情包含列
			Class<TbIntegrateInfo> tb = (Class<TbIntegrateInfo>) tbIntegrateInfo
					.getClass();
			String json = "{";
			for (Object ob : list) {
				TbIntegrateInfo info = (TbIntegrateInfo) ob;
				String colname = info.getcColumnName();
				String key = info.getC_infocol_name().toLowerCase();
				String methodName = (key.charAt(0) + "").toUpperCase()
						+ key.substring(1);
				Method m = tb.getDeclaredMethod("get" + methodName);
				String value = (String) m.invoke(tbIntegrateInfo);
				json += "\"" + colname + "\":\"" + value + "\",";
			}
			json = json.substring(0, json.length() - 1) + "}";
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String addTabletype() {
		try {
			Session session = SessionFactory.getInstance(request, response);
			Map user = (Map) session.getAttribute("USERSESSION");
			if (user == null) {
				this.formatData("请先登录！");
				return SUCCESS;
			}

			String colCount = request.getParameter("colCount");
			long cColumnCount = 0;
			if (colCount != null && !colCount.trim().equals("")) {
				cColumnCount = Long.parseLong(colCount);
			}
			Class tb = tbIntegrateInfo.getClass();
			BigDecimal cTabletypeUpid;
			String upid = request.getParameter("cTabletypeUpid");
			String cisproperty = request.getParameter("cIsproperty");
			cTabletypeUpid = new BigDecimal(upid);
			String cRemark = request.getParameter("c_remark");
			tbIntegrateInfo.setcTabletypeUpid(cTabletypeUpid);
			tbIntegrateInfo.setcRemark(cRemark);
			tbIntegrateInfo.setcTabletypeDes(request.getParameter("cTabletypeDes"));
			tbIntegrateInfo.setcTabletypeName(request.getParameter("cTabletypeName"));
			tbIntegrateInfo.setcIsproperty(cisproperty);
			tbIntegrateInfo.setcCreator(user.get("name") + "");
			tbIntegrateInfo.setcTabletypeId(0L);
			jobObjectsService.addtTabletype(tbIntegrateInfo);
			long cTabletypeId = tbIntegrateInfo.getcTabletypeId();
			if (cTabletypeId == 0) {
				this.formatData("作业对象类型添加失败！");
				return SUCCESS;
			}
			if(cisproperty.equals("1")){
				TbIntegrateInfo tb1 = new TbIntegrateInfo();
				tb1.setcRemark(request.getParameter("c_remark1"));
				tb1.setcIsscandata(request.getParameter("c_isscandata1"));
				tb1.setcIsShowScan("3");
				tb1.setC_infocol_name("C_BASEDATA_NAME");
				tb1.setC_column_name(tbIntegrateInfo.getC_basedata_name());
				tb1.setcIndex(new BigDecimal(1));
				tb1.setcTabletypeId(cTabletypeId);
				int count = jobObjectsService.addDynamicCol(tb1);
				if (count < 1) {
					this.formatData("作业对象动态列配置失败！");
					return SUCCESS;
				}
	
				tb1.setcRemark(request.getParameter("c_remark2"));
				tb1.setcIsscandata(request.getParameter("c_isscandata2"));
				tb1.setcIsShowScan("3");
				tb1.setC_infocol_name("C_BASEDATA_CODE");
				tb1.setC_column_name(tbIntegrateInfo.getC_basedata_code());
				tb1.setcIndex(new BigDecimal(2));
				count = jobObjectsService.addDynamicCol(tb1);
				if (count < 1) {
					this.formatData("作业对象动态列配置失败！");
					return SUCCESS;
				}
	
				tb1.setcRemark(request.getParameter("c_remark3"));
				tb1.setcIsscandata(request.getParameter("c_isscandata3"));
				tb1.setcIsShowScan("3");
				tb1.setC_infocol_name("C_BASEDATA_USERCODE");
				tb1.setC_column_name(tbIntegrateInfo.getC_basedata_usercode());
				tb1.setcIndex(new BigDecimal(3));
				count = jobObjectsService.addDynamicCol(tb1);
				if (count < 1) {
					this.formatData("作业对象动态列配置失败！");
					return SUCCESS;
				}
				
				for (int i = 1, j = 4; i <= cColumnCount; i++, j++) {
					Method m = tb.getDeclaredMethod("getCol" + i);
					String col = (String) m.invoke(tbIntegrateInfo);
					if (col != null && !col.trim().equals("")) {
						tb1.setcRemark(request.getParameter("c_remark" + j));
						tb1.setcIsscandata(request.getParameter("c_isscandata" + j));
						String isCon=request.getParameter("c_isshowscan"+j);
						if("true".equals(isCon)){
							tb1.setcIsShowScan("2");
						}else if("false".equals(isCon)){
							tb1.setcIsShowScan("1");
						}
						tb1.setC_infocol_name("COL" + i);
						tb1.setcIndex(new BigDecimal(j));
						tb1.setC_column_name(col);
						count = jobObjectsService.addDynamicCol(tb1);
						if (count < 1) {
							this.formatData("作业对象动态列配置失败！");
							return SUCCESS;
						}
	
					}
				}
				HashMap map = new HashMap();
				map.put("cTabletypeId", cTabletypeId);
				map.put("cColCount", cColumnCount + 3);
				count = jobObjectsService.updateColCount(map);
				if (count < 1) {
					this.formatData("作业对象的动态列总数更新失败！");
					return SUCCESS;
				}
			}
			
			//记录数据日志
			HashMap logmap = new HashMap();
			logmap.put("cTabletypeId", cTabletypeId);
			TbIntegrateInfo tabTypelog = jobObjectsService.getTabletypeById(logmap);
			List<TbIntegrateInfo> dyListlog= jobObjectsService.getDynamicsByType(logmap);
			JSONObject jsonValue1 = JSONObject.fromObject(tabTypelog);
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
			JSONArray  jsonValue2 = JSONArray.fromObject(dyListlog,jsonConfig);
			if(dyListlog.size()>0){
				String newlogString = "TB_TABLETYPE表插入行："+jsonValue1.toString()+"<BR>TB_DYNAMIC_COLUMN插入行："+jsonValue2.toString();
				logService.addLogInfo(request, response, "1", "过程行为支持系统", "2", "基础管理", "作业对象管理", MyLogPojo.add, "作业对象管理-新增类型", "成功", "1", "TB_TABLETYPE、TB_DYNAMIC_COLUMN", "", newlogString);
				
			}else{
				String newlogString = jsonValue1.toString();
				logService.addLogInfo(request, response, "1", "过程行为支持系统", "2", "基础管理", "作业对象管理", MyLogPojo.add, "作业对象管理-新增类型", "成功", "1", "TB_TABLETYPE", "", newlogString);
			}
			
			this.formatData("数据提交成功！");

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			this.formatData("数据提交失败！");
		}
		return SUCCESS;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String updateTabletype() {
		String errsta = "0";
		try {
			Session session = SessionFactory.getInstance(request, response);
			Map user = (Map) session.getAttribute("USERSESSION");
			if (user == null) {
				this.formatData("请先登录！");
				return SUCCESS;
			}
			String colCount = request.getParameter("colCount");
			long cColumnCount = 0;
			if (colCount != null && !colCount.trim().equals("")) {
				cColumnCount = Long.parseLong(colCount);
			}
			int count = 0;
			String cTabletypeId = request.getParameter("cTabletypeId").trim();
			if (cTabletypeId == null || cTabletypeId.equals("")) {
				this.formatData("该类型不存在!");
				return SUCCESS;
			}
			
			//记录数据日志
			HashMap oldlogmap = new HashMap();
			oldlogmap.put("cTabletypeId", cTabletypeId);
			TbIntegrateInfo oldtabTypelog = jobObjectsService.getTabletypeById(oldlogmap);
			List<TbIntegrateInfo> olddyListlog= jobObjectsService.getDynamicsByType(oldlogmap);
			
			tbIntegrateInfo.setcRemark(request.getParameter("c_remark"));
			tbIntegrateInfo.setcTabletypeUpid(new BigDecimal(request.getParameter("cTabletypeUpid")));
			tbIntegrateInfo.setcTabletypeDes(request.getParameter("cTabletypeDes"));
			tbIntegrateInfo.setcTabletypeName(request.getParameter("cTabletypeName"));
			String cisproperty = request.getParameter("cIsproperty");
			tbIntegrateInfo.setcIsproperty(cisproperty);
			tbIntegrateInfo.setcUpdateUser(user.get("name").toString());
			tbIntegrateInfo.setcTabletypeId(Long.parseLong(cTabletypeId));
			count = jobObjectsService.updateTabletype(tbIntegrateInfo);
			if (count < 1) {
				this.formatData("作业对象类型更新失败！");
				return SUCCESS;
			}

			String oldispr = oldtabTypelog.getcIsproperty();
			if(cisproperty.equals("1")){
				if(!"1".equals(oldispr)){
					TbIntegrateInfo tb1 = new TbIntegrateInfo();
					tb1.setcRemark(request.getParameter("c_remark1"));
					tb1.setcIsscandata(request.getParameter("c_isscandata1"));
					tb1.setcIsShowScan("3");
					tb1.setC_infocol_name("C_BASEDATA_NAME");
					tb1.setC_column_name(tbIntegrateInfo.getC_basedata_name());
					tb1.setcIndex(new BigDecimal(1));
					tb1.setcTabletypeId(tbIntegrateInfo.getcTabletypeId());
					count = jobObjectsService.addDynamicCol(tb1);
					if (count < 1) {
						errsta = "2";
					}
		
					tb1.setcRemark(request.getParameter("c_remark2"));
					tb1.setcIsscandata(request.getParameter("c_isscandata2"));
					tb1.setcIsShowScan("3");
					tb1.setC_infocol_name("C_BASEDATA_CODE");
					tb1.setC_column_name(tbIntegrateInfo.getC_basedata_code());
					tb1.setcIndex(new BigDecimal(2));
					count = jobObjectsService.addDynamicCol(tb1);
					if (count < 1) {
						errsta = "2";
					}
		
					tb1.setcRemark(request.getParameter("c_remark3"));
					tb1.setcIsscandata(request.getParameter("c_isscandata3"));
					tb1.setcIsShowScan("3");
					tb1.setC_infocol_name("C_BASEDATA_USERCODE");
					tb1.setC_column_name(tbIntegrateInfo.getC_basedata_usercode());
					tb1.setcIndex(new BigDecimal(3));
					count = jobObjectsService.addDynamicCol(tb1);
					if (count < 1) {
						errsta = "2";
					}
					
					Class tb = tbIntegrateInfo.getClass();
					for (int i = 1, j = 4; i <= cColumnCount; i++, j++) {
						Method m = tb.getDeclaredMethod("getCol" + i);
						String col = (String) m.invoke(tbIntegrateInfo);
						if (col != null && !col.trim().equals("")) {
							tb1.setcRemark(request.getParameter("c_remark" + j));
							tb1.setcIsscandata(request.getParameter("c_isscandata" + j));
							String isCon=request.getParameter("c_isshowscan"+j);
							if("true".equals(isCon)){
								tb1.setcIsShowScan("2");
							}else if("false".equals(isCon)){
								tb1.setcIsShowScan("1");
							}
							tb1.setC_infocol_name("COL" + i);
							tb1.setcIndex(new BigDecimal(j));
							tb1.setC_column_name(col);
							count = jobObjectsService.addDynamicCol(tb1);
							if (count < 1) {
								errsta = "2";
							}
		
						}
					}
					HashMap map = new HashMap();
					map.put("cTabletypeId", cTabletypeId);
					map.put("cColCount", cColumnCount + 3);
					count = jobObjectsService.updateColCount(map);
					if (count < 1) {
						errsta = "3";
					}
				}else{
					HashMap map = new HashMap();
					map.put("cTabletypeId", cTabletypeId);
					map.put("cColumnName", tbIntegrateInfo.getC_basedata_name());
					map.put("cIsscandata", request.getParameter("c_isscandata1"));
					map.put("cIsShowScan", "3");
					map.put("cRemark", request.getParameter("c_remark1"));
					map.put("cDycolId", request.getParameter("colid1"));
					map.put("cInfocolName", "C_BASEDATA_NAME");
					
					count = jobObjectsService.updateDynamicCol(map);
					if (count < 1) {
						errsta = "2";
						/*this.formatData("作业对象的动态列配置信息表更新失败！");
						return SUCCESS;*/
					}

					map.put("cColumnName", tbIntegrateInfo.getC_basedata_code());
					map.put("cIsscandata", request.getParameter("c_isscandata2"));
					map.put("cIsShowScan", "3");
					map.put("cRemark", request.getParameter("c_remark2"));
					map.put("cDycolId", request.getParameter("colid2"));
					map.put("cInfocolName", "C_BASEDATA_CODE");
					count = jobObjectsService.updateDynamicCol(map);
					if (count < 1) {
						errsta = "2";
						/*this.formatData("作业对象的动态列配置信息表更新失败！");
						return SUCCESS;*/
					}

					map.put("cColumnName", tbIntegrateInfo.getC_basedata_usercode());
					map.put("cIsscandata", request.getParameter("c_isscandata3"));
					map.put("cIsShowScan", "3");
					map.put("cRemark", request.getParameter("c_remark3"));
					map.put("cDycolId", request.getParameter("colid3"));
					map.put("cInfocolName", "C_BASEDATA_USERCODE");
					count = jobObjectsService.updateDynamicCol(map);
					if (count < 1) {
						errsta = "2";
						/*this.formatData("作业对象的动态列配置信息表更新失败！");
						return SUCCESS;*/
					}
					
					int dyoclCurrentCount = jobObjectsService.getDynamicCount(map);

					for (int i = 1; i <= cColumnCount; i++) {
						String colid = request.getParameter("colid" + (i + 3));
						String colname = request.getParameter("tbIntegrateInfo.col" + i);
						if (colname != null && !colname.trim().equals("")) {
							if (i > dyoclCurrentCount - 3) {
								tbIntegrateInfo.setcColumnName(colname);
								tbIntegrateInfo.setcIsscandata(request.getParameter("c_isscandata" + (i + 3)));
								String isShow=request.getParameter("c_isshowscan" + (i + 3));
								if("true".equals(isShow)){
									tbIntegrateInfo.setcIsShowScan("2");
								}else if("false".equals(isShow)){
									tbIntegrateInfo.setcIsShowScan("1");
								}
								tbIntegrateInfo.setcRemark(request.getParameter("c_remark" + (i + 3)));
								tbIntegrateInfo.setcIndex(new BigDecimal(i + 3));
								tbIntegrateInfo.setcInfocolName("COL" + i);
								count = jobObjectsService.addDynamicCol(tbIntegrateInfo);
								if (count < 1) {
									errsta = "2";
									/*this.formatData("动态列数据配置新增失败！");
									return SUCCESS;*/
								}
								continue;
							}
							if (colid != null && !colid.trim().equals("")) {
								map.put("cDycolId", colid);
								map.put("cColumnName", colname);
								map.put("cRemark",
										request.getParameter("c_remark" + (i + 3)));
								map.put("cIsscandata",
										request.getParameter("c_isscandata" + (i + 3)));
								map.put("cInfocolName","COL" + i);
								String showCon=request.getParameter("c_isshowscan" + (i + 3));
								if("true".equals(showCon)){
									map.put("cIsShowScan", "2");
								}else if("false".equals(showCon)){
									map.put("cIsShowScan", "1");
								}
								count = jobObjectsService.updateDynamicCol(map);
							}

							if (count < 1) {
								errsta = "2";
								/*this.formatData("动态列数据配置更新失败！");
								return SUCCESS;*/
							}
						}
					}
					
					if (dyoclCurrentCount < cColumnCount + 3) {
						map.put("cColCount", cColumnCount + 3);
						jobObjectsService.updateColCount(map);
						if (count < 1) {
							errsta = "3";
							/*this.formatData("作业对象的动态列总数更新失败！");
							return SUCCESS;*/
						}
					}
				}
			}
			

			if(errsta.equals("3"))
			{
				this.formatData("作业对象的动态列总数更新失败！");
			}else if (errsta.equals("2")){
				this.formatData("作业对象的动态列配置信息表更新失败！");
			}else{
				this.formatData("作业对象更新成功!");
			}
			
			//记录数据日志
			JSONObject oldjsonValue1 = JSONObject.fromObject(oldtabTypelog);
			JsonConfig oldjsonConfig = new JsonConfig();
			oldjsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
			JSONArray oldjsonValue2 = JSONArray.fromObject(olddyListlog,oldjsonConfig);
			String oldlogString = "TB_TABLETYPE数据："+oldjsonValue1.toString()+"<BR>TB_DYNAMIC_COLUMN数据："+oldjsonValue2.toString();
			HashMap logmap = new HashMap();
			logmap.put("cTabletypeId", cTabletypeId);
			TbIntegrateInfo tabTypelog = jobObjectsService.getTabletypeById(logmap);
			List<TbIntegrateInfo> dyListlog= jobObjectsService.getDynamicsByType(logmap);
			JSONObject newjsontype = JSONObject.fromObject(tabTypelog);
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
			JSONArray  newcoljson = JSONArray.fromObject(dyListlog,jsonConfig);
			String newlogString = "TB_TABLETYPE数据："+newjsontype.toString()+"<BR>TB_DYNAMIC_COLUMN数据："+newcoljson.toString();
			logService.addLogInfo(request, response, "1", "过程行为支持系统", "2", "基础管理", "作业对象管理", MyLogPojo.change, "作业对象管理-修改类型", "成功", "1", "TB_TABLETYPE、TB_DYNAMIC_COLUMN", oldlogString, newlogString);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			this.formatData("作业对象更新失败！");
		}
		return SUCCESS;
	}

	public String deleteTabletype() {
		try {
			if (!isLogin()) {
				this.formatData("请先登录！");
				return SUCCESS;
			}
			String cTabletypeId = request.getParameter("cTabletypeId");
			HashMap param = new HashMap();
			param.put("cTabletypeId", cTabletypeId);
			int count = jobObjectsService.checkHasIntegrateData(param);
			if (count == 1) {
				this.formatData("该作业对象或者该作业对象的子节点存在详细数据，不能删除！若要删除，请先删除所有详细数据!");
				return SUCCESS;
			}
			
			TbIntegrateInfo tabTypelog = jobObjectsService.getTabletypeById(param);
			List<TbIntegrateInfo> dyListlog= jobObjectsService.getDynamicsByType(param);
			JSONObject jsonValue1 = JSONObject.fromObject(tabTypelog);
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
			JSONArray  jsonValue2 = JSONArray.fromObject(dyListlog,jsonConfig);
			
			int countColum = jobObjectsService.getCountColum(param);
			if(countColum>0){
				count = jobObjectsService.deleteTabletypeCol(param);
				if (count < 1) {
					this.formatData("作业对象类型的列信息配置删除失败！");
					return SUCCESS;
				}
			}
			
			String oldlogString = "<BR>TB_DYNAMIC_COLUMN数据："+jsonValue2.toString();
			
			count = jobObjectsService.deleteTabletype(param);
			if (count < 1) {
				//记录数据日志
				oldlogString += "<BR>TB_TABLETYPE数据："+jsonValue1.toString();
				logService.addLogInfo(request, response, "1", "过程行为支持系统", "2", "基础管理", "作业对象管理", MyLogPojo.change, "作业对象管理-删除类型", "失败", "2", "TB_TABLETYPE、TB_DYNAMIC_COLUMN", oldlogString, "");
				
				this.formatData("作业对象类型删除失败！");
				return SUCCESS;
			}
			
			//记录数据日志
			oldlogString += "<BR>TB_TABLETYPE数据："+jsonValue1.toString();
			logService.addLogInfo(request, response, "1", "过程行为支持系统", "2", "基础管理", "作业对象管理", MyLogPojo.change, "作业对象管理-删除类型", "成功", "1", "TB_TABLETYPE、TB_DYNAMIC_COLUMN", oldlogString, "");
			
			
			this.formatData("作业对象删除成功！");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			this.formatData("数据删除失败！");
		}
		return SUCCESS;
	}

	public String getTabletypeById() {
		try {
			if (!isLogin()) {
				this.formatData("请先登录！");
				return SUCCESS;
			}
			String cTabletypeId = request.getParameter("cTabletypeId");
			Map param = new HashMap();
			param.put("cTabletypeId", cTabletypeId);
			TbIntegrateInfo tb = jobObjectsService.getTabletypeById(param);
			tb.setcTabletypeId(Long.parseLong(cTabletypeId));
			List list = jobObjectsService.getDynamicsByType(param);

			StringBuffer sb = new StringBuffer();

			sb.append("{'cTabletypeId':'");
			sb.append(cTabletypeId + "',");
			sb.append("'cTabletypeName':'");
			sb.append(tb.getcTabletypeName() + "'");
			sb.append(",'cTabletypeDes':'");
			sb.append(tb.getcTabletypeDes() + "'");
			sb.append(",'colCount':'");
			sb.append(tb.getcColCount() - 3 + "'");
			sb.append(",'c_remark':'");
			sb.append(tb.getcRemark() + "'");
			sb.append(",'cTabletypeUpid':'");
			sb.append(tb.getcTabletypeUpid() + "'");
			sb.append(",'cIsproperty':'");
			sb.append(tb.getcIsproperty() + "'");
			
			if (list == null || list.size() == 0) {
				sb.append("}");
				this.formatData(sb.toString());
				return SUCCESS;
			}

			int j = 4;
			
			TbIntegrateInfo tb1 = (TbIntegrateInfo) list.get(0);
			sb.append(",'tbIntegrateInfo.c_basedata_name':'");
			sb.append(tb1.getC_column_name() + "'");
			sb.append(",'c_remark1':'");
			sb.append(tb1.getcRemark() + "'");
			sb.append(",'c_isscandata1':'");
			sb.append(tb1.getcIsscandata() + "'");
//			sb.append(",'c_isshowscan1':'");
//			sb.append(tb1.getcIsShowScan() + "'");
			sb.append(",'colid1':'");
			sb.append(tb1.getcDycolId() + "'");

			tb1 = (TbIntegrateInfo) list.get(1);
			sb.append(",'tbIntegrateInfo.c_basedata_code':'");
			sb.append(tb1.getC_column_name() + "'");
			sb.append(",'c_remark2':'");
			sb.append(tb1.getcRemark() + "'");
			sb.append(",'c_isscandata2':'");
			sb.append(tb1.getcIsscandata() + "'");
//			sb.append(",'c_isshowscan2':'");
//			sb.append(tb1.getcIsShowScan() + "'");
			sb.append(",'colid2':'");
			sb.append(tb1.getcDycolId() + "'");

			tb1 = (TbIntegrateInfo) list.get(2);
			if(!tb1.getC_infocol_name().equals("C_BASEDATA_USERCODE"))
			{
				j--;
				sb.append(",'tbIntegrateInfo.c_basedata_usercode':'");
				sb.append("'");
				sb.append(",'c_remark3':'");
				sb.append("'");
				sb.append(",'c_isscandata3':'");
				sb.append("'");
				sb.append(",'colid3':'");
				sb.append("'");
			}
			else
			{
				sb.append(",'tbIntegrateInfo.c_basedata_usercode':'");
				sb.append(tb1.getC_column_name() + "'");
				sb.append(",'c_remark3':'");
				sb.append(tb1.getcRemark() + "'");
				sb.append(",'c_isscandata3':'");
				sb.append(tb1.getcIsscandata() + "'");
				sb.append(",'colid3':'");
				sb.append(tb1.getcDycolId() + "'");
			}
			
			// map.put("", value)
			for (;list != null && j < list.size() + 1; j++) {
				TbIntegrateInfo dycol = (TbIntegrateInfo) list.get(j - 1);
				sb.append(",'colid" + j + "':'");
				sb.append(dycol.getcDycolId() + "'");
				sb.append(",'c_remark" + j + "':'");
				sb.append(dycol.getcRemark() + "'");
				sb.append(",'c_isscandata" + j + "':'");
				sb.append(dycol.getcIsscandata() + "'");
				sb.append(",'c_isshowscan" + j + "':'");
				sb.append(dycol.getcIsShowScan() + "'");
				sb.append(",'tbIntegrateInfo.col" + (j - 3) + "':'");
				String colname = dycol.getC_column_name();
				sb.append(colname + "'");
			}
			sb.append("}");
			String data = sb.toString();
			this.formatData(data);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			this.formatData("查询失败！");
		}
		return SUCCESS;
	}
	
	public String addObjectsByImport() {
		int count = 0;
		String path = "";
		try {
			Session session = SessionFactory.getInstance(request, response);
			Map user = (Map) session.getAttribute("USERSESSION");
			if (user == null) {
				this.formatData("请先登录!");
				return SUCCESS;
			}
			
//			String[] title = getTitle();
			String[] title = getColumNamesImp();
			int typeNameLength=getTypeNames().size();
			String filePath = request.getParameter("filePath");
			String fileName = filePath.substring(filePath
					.lastIndexOf(StaticUploadInfo.upload_system_url)
					+ StaticUploadInfo.upload_system_url.length());
			path = request.getSession().getServletContext()
					.getRealPath(fileName);
			List<String[]> list = ExcelReadUtil.readExcel(path);
			if (path != null && !"".equals(path)) {
				delFile(path);
			}
			if (list == null || list.size() < 2) {
				this.formatData("表格为空！");
				return SUCCESS;
			}
			String[] title2 = list.get(0);
			if (title.length != title2.length) {
				this.formatData("模版错误!");
				return SUCCESS;
			}

			for (int i = 0; i < title.length; i++) {
				if (!title[i].trim().replace("\n", "")
						.equals(title2[i].trim().replace("\n", ""))) {
					this.formatData("模版错误!");
					return SUCCESS;
				}
			}

			String cOrgid = request.getParameter("cOrgid");
			String cTabletypeId = request.getParameter("cTabletypeId");
			Long typeid = null;
			Long orgid = null;

			if (cTabletypeId != null) {
				typeid = Long.parseLong(cTabletypeId);
			}
			if (cOrgid != null && !cOrgid.trim().equals("")) {
				orgid = Long.parseLong(cOrgid);
			}

			if (orgid == null || typeid == null) {
				this.formatData("请先选择作业对象类型和所属部门!");
				return SUCCESS;
			}

			String cUpdateUser = user.get("name").toString();
			String msg="";
			int failCount=0;
			String repeatMsg="";
			List<TbIntegrateInfo> tbintegList = new ArrayList<TbIntegrateInfo>();
			for (int i = 1; i < list.size(); i++) {
				try {
					tbIntegrateInfo = new TbIntegrateInfo();
					tbIntegrateInfo.setcTabletypeId(typeid);
					tbIntegrateInfo.setcOrgid(orgid);
					tbIntegrateInfo.setcUpdateUser(cUpdateUser);
					String[] s = list.get(i);
					tbIntegrateInfo.setC_basedata_name(s[typeNameLength]);
					tbIntegrateInfo.setC_basedata_code(s[typeNameLength+1]);
					tbIntegrateInfo.setC_basedata_usercode(s[typeNameLength+2]);
					Class<TbIntegrateInfo> cls = TbIntegrateInfo.class;
					boolean isNotNull = false;
					
					if(tbIntegrateInfo.getC_basedata_name()!=null&&tbIntegrateInfo.getcBasedataCode()!=null&&tbIntegrateInfo.getcBasedataUserCode()!=null){
						isNotNull=true;
					}
 
					for (int j = typeNameLength+3; j < s.length; j++) {

						if (s[j] == null || s[j].trim().equals("")) {
							continue;
						}
						Method m = cls.getMethod("setCol" + (j - 2 -typeNameLength),
								String.class);
						m.invoke(tbIntegrateInfo, s[j]);
					}
					if (isNotNull) {
						int countObject=jobObjectsService.verifyRepeatObject(tbIntegrateInfo);
						if(countObject>0){
							failCount++;
							msg+=(1+i)+",";
							repeatMsg+=(i+1)+",";
							continue;
						}
						String json = makeScanDetails(cTabletypeId);
						tbIntegrateInfo.setcScanDetail(json);
						String cBasedataId=(String)jobObjectsService.getMaxBasedataId(tbIntegrateInfo);
						tbIntegrateInfo.setcBasedataId(new BigDecimal(cBasedataId));
						jobObjectsService.addObject(tbIntegrateInfo);

						tbintegList.add(tbIntegrateInfo);
						//新增点检记录
						TTaskPointcheckPojo ttaskPointCheck = new TTaskPointcheckPojo();
						ttaskPointCheck.setcBasedataId(tbIntegrateInfo.getcBasedataId().toString());
						ttaskPointCheck.setcScanCode(tbIntegrateInfo.getcScanCode());
						ttaskPointCheck.setcExecUserid(tbIntegrateInfo.getcBasedataUserCode());
						if (cTabletypeId != null) {
							ttaskPointCheck.setcObjectTypeid(new BigDecimal(cTabletypeId));
						}
						ttaskPointCheck.setcIsdelete("0");
						jobObjectsService.addTaskPointCheck(ttaskPointCheck);
						count++;
					}
				} catch (Exception e) {
					failCount++;
					msg+=(i+1)+",";
					if(failCount%5==0){
						msg+="\n\r";
					}
					e.printStackTrace();
				}
			}
			int end=msg.lastIndexOf(",");
			if(end>0){				
				msg=msg.substring(0,end);
				msg="\n\r第"+msg+"行数据导入失败!";
			}else{
				msg="";
			}
			if(repeatMsg.length()>0){
				repeatMsg=repeatMsg.substring(0,repeatMsg.length()-1);
				repeatMsg="\n第"+repeatMsg+"行数据重复!";
			}
			
			//记录数据日志
			JSONArray  listjson = JSONArray.fromObject(tbintegList);
			logService.addLogInfo(request, response, "1", "过程行为支持系统", "2", "基础管理", "作业对象管理", MyLogPojo.add, "作业对象管理-导入对象", "成功", "1", "TB_INTEGRATE_INFO", "", listjson.toString());
			
			msg="成功导入了" + count + "条数据,导入失败"+failCount+"条!"+msg+repeatMsg;
			this.formatData(msg);

		} catch (Exception e) {
			e.printStackTrace();
			this.formatData("导入失败!");
		}

		return SUCCESS;
	}

	private void delFile(String path) {
		FileOperate fo = new FileOperate();
		fo.delFile(path);
	}

	public String getImportTemplete() {
		try {
			if (!isLogin()) {
				this.formatData("请先登录！");
				return SUCCESS;
			}
			
			
			String[] nameAndColum=getColumNamesImp();
			List<String> names=getTypeNames();
			
			String fileName = "ZuoYeDuixiangDaoRu";

			String path = request.getSession().getServletContext()
					.getRealPath(StaticUploadInfo.upload_system_export);
			this.creatDirectory(path);
			String filePath = path + "\\" + fileName + ".xls"; // 统一输出2003格式的excel
			String[][] data = new String[1][names.size()];
			for (int i = 0; i < names.size(); i++) {
				data[0][i]=names.get(i);
			}
			ExcelReadUtil.writeExcel(filePath, nameAndColum, data);

			String projectPath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ request.getContextPath() + "/";

			filePath = projectPath
					+ StaticUploadInfo.upload_system_export.substring(1)
					+ fileName + ".xls";

			Map map = new HashMap();
			map.put("url", filePath);
			this.formatData(map);

		} catch (Exception e) {
			e.printStackTrace();
			this.formatData(false);
		}
		return SUCCESS;
	}

	private String[] getColumNames() {
		List<String> typeNames=getTypeNames();
		String[] title = getTitle();
		
		String[] titles1 = {"系统编号", "扫描码", "是否已设置坐标", "打印次数", "最后打印人", "最后打印时间", "部门"};
		
		int nameLength=typeNames.size();
		int columLength=title.length;
		int fixedColLength = titles1.length;
		String[] nameAndColum=new String[titles1.length+nameLength+columLength];
		
		for (int i = 0; i < nameLength; i++) {
			nameAndColum[i]=getNumToStr(i+1)+"级节点";
		}
		
		for (int j = 0; j < fixedColLength; j++) {
			nameAndColum[nameLength+j]=titles1[j];
		}
		
		for (int j = 0; j < columLength; j++) {
			nameAndColum[nameLength+fixedColLength+j]=title[j];
		}
		return nameAndColum;
		
	}
	
	private String[] getColumNamesImp() {
		List<String> typeNames=getTypeNames();
		String[] title = getTitle();
		
		int nameLength=typeNames.size();
		int columLength=title.length;
		String[] nameAndColum=new String[nameLength+columLength];
		
		for (int i = 0; i < nameLength; i++) {
			nameAndColum[i]=getNumToStr(i+1)+"级节点";
		}
		
		for (int j = 0; j < columLength; j++) {
			nameAndColum[nameLength+j]=title[j];
		}
		return nameAndColum;
		
	}
	
	private String getNumToStr(int num){
		String ss="";
		String[] str={"一","二","三","四","五","六","七","八","九","十"};
		String numStr=new Integer(num).toString();
		for (int i = numStr.length()-1; i >= 0; i--) {
			char j=numStr.charAt(i);
			
			if(i==numStr.length()-1){
				ss=str[j-1-48]+ss;
			}else if(i==numStr.length()-2){
				ss=str[j-1-48]+"十"+ss;
			}else if(i==numStr.length()-3){
				ss=str[j-1-48]+"百"+ss;
			}
		}
		return ss;
		
	}

	//获得根节点以下的所有节点名称
	private List<String> getTypeNames() {
		List<String> names=new ArrayList<String>();
		List list=new ArrayList();
		String cTabletypeId = request.getParameter("cTabletypeId");
		List typeObjs=getNode(new BigDecimal(cTabletypeId), list);
		if(typeObjs!=null&&typeObjs.size()>0){
			for (int i = typeObjs.size()-1; i >=0; i--) {
				TbIntegrateInfo tb=(TbIntegrateInfo)typeObjs.get(i);
				String typeName=tb.getCTabletypeName();
				names.add(typeName);
			}
		}
		return names;
	}

	private void creatDirectory(String path) {
		java.io.File f = new java.io.File(path);
		if (!f.exists())
			f.mkdirs();
	}

	public String[] getTitle() {
		Map params = new HashMap();
		String cTabletypeId = request.getParameter("cTabletypeId");
		params.put("cTabletypeId", cTabletypeId);
		List list = jobObjectsService.getColumnsConfig(params);
		String t[]=new String[list.size()];
		for (int i = 0; list != null && i < list.size(); i++) {
			TbIntegrateInfo tb = (TbIntegrateInfo) list.get(i);
			t[i]=tb.getC_column_name();
		}
		return t;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String selectJobObjectTree()
	{
		try {
			if (!isLogin()) {
				this.formatData("请先登录！");
				return SUCCESS;
			}
			Map params = new HashMap();
			String positionId = request.getParameter("positionId");
			params.put("positionId", positionId);
			List list = jobObjectsService.selectJobObjectTree(params);
			List pl = new ArrayList();
			for (Object obj : list) {
				TbIntegrateInfo info = (TbIntegrateInfo)obj;
				info.setNodeId(info.getcBasedataId().longValue());
				info.setNodeName(info.getC_basedata_name());
				info.setNodePid(info.getcTabletypeId());
				pl.add(info.getCTabletypeId());
			}
			Map map = new HashMap();
			if(pl.size()<=0)
			{
				map.put("cTabletypeId", null);
			}
			else
			{
				map.put("cTabletypeId", pl);
			}
			List tplist = jobObjectsService.getTableTypeInfo(map);
			for(Object obj : tplist){
				TbIntegrateInfo info = (TbIntegrateInfo)obj;
				info.setNodeId(info.getCTabletypeId());
				info.setNodeName(info.getCTabletypeName());
				info.setNodePid(info.getCTabletypeUpid().longValue());
				list.add(info);
				List rtlist = new ArrayList();
				List pnode = getNode(info.getCTabletypeUpid(),rtlist);
				for(Object pobj : pnode)
				{
					TbIntegrateInfo pinfo = (TbIntegrateInfo)pobj;
					pinfo.setNodeId(pinfo.getCTabletypeId());
					pinfo.setNodeName(pinfo.getCTabletypeName());
					pinfo.setNodePid(pinfo.getCTabletypeUpid().longValue());
					list.add(pinfo);
				}
			}
			RuleTree wtree = new RuleTree();
			wtree.setId("nodeId");
			wtree.setVal("nodeName");
			wtree.setPid("nodePid");
			List<Map> l = this.initTreeData(list, wtree);
			this.formatData(l);
		} catch (Exception e) {
			e.printStackTrace();
			this.formatData("生成树失败!");
			logger.error(e.getMessage(), e);
		}
		return SUCCESS;
	}
	
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List getOrgIds(String pid,List dplist){
		List list = new ArrayList();
		list.add(pid);
		Map map = new HashMap();
		map.put("orgId", list);
		List orgList = jobObjectsService.getAllOrgIds(map);
		if(orgList !=null && orgList.size()!=0){
			for(int i=0;i<orgList.size();i++){
				Long orgid=Long.parseLong(orgList.get(i).toString());
				if(orgid!=null&&orgid!=0){
					dplist.add(orgid);
					getOrgIds(orgid.toString(),dplist);
				}
 
			}
		}
 
		return dplist;
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes",})
	private List getNode(BigDecimal pid,List rtlist)
	{
		List pl = new ArrayList();
		pl.add(pid);
		Map map = new HashMap();
		map.put("cTabletypeId", pl);
		List list = jobObjectsService.getTableTypeInfo(map);
		if(list !=null && list.size()!=0){//判断是否查到,没有了表示小的了 
			for(int i=0;i<list.size();i++){
				TbIntegrateInfo info = (TbIntegrateInfo)list.get(i);
				rtlist.add(info);
		        getNode(info.getCTabletypeUpid(),rtlist); //根据当前id查询子 
		     }
		}
		return rtlist;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String positionJobObjectTree()
	{
		try {
			if (!isLogin()) {
				this.formatData("请先登录！");
				return SUCCESS;
			}
			Session session = SessionFactory.getInstance(request, response);
			Map user = (Map) session.getAttribute("USERSESSION");
			Map params = new HashMap();
			params.put("positionId", user.get("positionid"));
			List list = jobObjectsService.selectJobObjectTree(params);
			List treelist = new ArrayList();
			List pl = new ArrayList();
			for (Object obj : list) {
				TbIntegrateInfo info = (TbIntegrateInfo)obj;
				info.setNodeId(info.getcBasedataId().longValue());
				info.setNodeName(info.getC_basedata_name());
				info.setNodePid(info.getcTabletypeId());
				pl.add(info.getCTabletypeId());
			}
			Map map = new HashMap();
			if(pl.size()<=0)
			{
				map.put("cTabletypeId", null);
			}
			else
			{
				map.put("cTabletypeId", pl);
			}
			List tplist = jobObjectsService.getTableTypeInfo(map);
			for(Object obj : tplist){
				TbIntegrateInfo info = (TbIntegrateInfo)obj;
				info.setNodeId(info.getCTabletypeId());
				info.setNodeName(info.getCTabletypeName());
				info.setNodePid(info.getCTabletypeUpid().longValue());
				treelist.add(info);
				List rtlist = new ArrayList();
				List pnode = getNode(info.getCTabletypeUpid(),rtlist);
				for(Object pobj : pnode)
				{
					TbIntegrateInfo pinfo = (TbIntegrateInfo)pobj;
					pinfo.setNodeId(pinfo.getCTabletypeId());
					pinfo.setNodeName(pinfo.getCTabletypeName());
					pinfo.setNodePid(pinfo.getCTabletypeUpid().longValue());
					treelist.add(pinfo);
				}
			}
			RuleTree wtree = new RuleTree();
			wtree.setId("nodeId");
			wtree.setVal("nodeName");
			wtree.setPid("nodePid");
			List<Map> l = this.initTreeData(treelist, wtree);
			this.formatData(l);
		} catch (Exception e) {
			e.printStackTrace();
			this.formatData("生成树失败!");
			logger.error(e.getMessage(), e);
		}
		return SUCCESS;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String queryPtObject() {
		try {
			if (!isLogin()) {
				this.formatData("请先登录！");
				return SUCCESS;
			}

			Map map = new HashMap();
			Session session = SessionFactory.getInstance(request, response);
			Map user = (Map) session.getAttribute("USERSESSION");
			String cTableTypeId = request.getParameter("cTabletypeId");
			String ptStatus = request.getParameter("ptStatus");
			map.put("positionId", user.get("positionid"));
			map.put("cTabletypeId", cTableTypeId);
			map.put("ptStatus", ptStatus);
			
			if (pagination == null) {
				pagination = new Pagination(1, 20);
			}
			jobObjectsService.queryPtObject(pagination, map);
			this.formatDatagirdData(pagination.getList(), pagination);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		return SUCCESS;
	}
	
	public String addTaskPosition() {
		try {
			Session session = SessionFactory.getInstance(request, response);
			Map user = (Map) session.getAttribute("USERSESSION");
			if (user == null) {
				this.formatData("请先登录!");
				return SUCCESS;
			}
			ttaskPointCheck.setcBasedataId(request.getParameter("cBasedataId").toString());
			ttaskPointCheck.setcObjectLocation(request.getParameter("x").toString() + "," + request.getParameter("y").toString() + ",0,0");
			ttaskPointCheck.setcAreaId(request.getParameter("cAreaId").toString());
			List<String> l = new ArrayList();
			l.add(request.getParameter("cBasedataId").toString());
			List<?> oldList = jobObjectsService.getTaskPointcheckInfoById(l);
			int result = jobObjectsService.updatePointCheckByCBasedataId(ttaskPointCheck);
			List<?> newList = jobObjectsService.getTaskPointcheckInfoById(l);
			//记录数据日志
			JSONArray newjsonObj = JSONArray.fromObject(newList);
			JSONArray oldjsonObj = JSONArray.fromObject(oldList);
			String oldjsonString = newjsonObj.toString();
			String newjsonString = oldjsonObj.toString();
			logService.addLogInfo(request, response, "1", "过程行为支持系统", "2", "基础管理", "地理坐标管理", MyLogPojo.add, "地理坐标管理-设置坐标", "成功", "1", "T_TASK_POINTCHECK", oldjsonString, newjsonString);
			
			if (result < 1) {
				this.formatData("数据提交失败！");
			} else {
				this.formatData("数据提交成功！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			this.formatData("数据提交失败！");
		}
		return SUCCESS;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getObjectsByExport(){
		try {

			Map param = new HashMap();
			
			String cBasedataId = request.getParameter("cBasedataId");
			String cTableTypeId = request.getParameter("cTabletypeId");
			String cBasedataName = request.getParameter("cBasedataName");
			String cBasedataUserCode = request.getParameter("cBasedataUserCode");
			String positionId = request.getParameter("positionId");
			String c_basedata_code = request.getParameter("tbIntegrateInfo.c_basedata_code");
			String c_basedata_name = request.getParameter("tbIntegrateInfo.c_basedata_name");
			String c_basedata_username = request.getParameter("tbIntegrateInfo.c_basedata_username");
			String user = request.getParameter("cPrintUser");
			String printTime = request.getParameter("cPrintTime");
			String cScanCode = request.getParameter("cScanCode");
			String ptStatus=request.getParameter("ptStatus");
			String org = request.getParameter("cOrgid");
			
			if (c_basedata_code != null && !c_basedata_code.equals("")) {
				param.put("c_basedata_code", "%" + c_basedata_code + "%");
			}
			if (c_basedata_name != null && !c_basedata_name.equals("")) {
				param.put("c_basedata_name", "%" + c_basedata_name + "%");
			}
			if (c_basedata_username != null && !c_basedata_username.equals("")) {
				param.put("c_basedata_username", "%" + c_basedata_username + "%");
			}

			if (cBasedataUserCode != null && !cBasedataUserCode.equals("")) {
				param.put("c_basedata_usercode", "%" + cBasedataUserCode + "%");
			}
			for (int i = 1; i <= 30; i++) {
				String col = request.getParameter("tbIntegrateInfo.col" + i);
				if (col != null && !col.trim().equals("")) {
					param.put("col" + i, "%" + col + "%");
				}
			}

			if(cTableTypeId != null && !cTableTypeId.equals("null")&& !cTableTypeId.equals(""))
			{
				param.put("cTabletypeId", cTableTypeId);
			}
			if(cBasedataName != null && !cBasedataName.equals("null")&& !cBasedataName.equals(""))
			{
				param.put("cBasedataName", cBasedataName);
			}
			if(positionId != null && !positionId.equals("null")&& !positionId.equals(""))
			{
				param.put("positionId", positionId);
			}

			if (cBasedataId != null && !cBasedataId.equals("null")&& !cBasedataId.equals("")) {
				param.put("cBasedataId", cBasedataId);
			}

			Long cPrintUser = null;
			if (user != null && !user.equals("")) {
				cPrintUser = Long.parseLong(user);
				param.put("cPrintUser", cPrintUser);
			}

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if (printTime != null && !printTime.equals("")) {
				param.put("cPrintTime", sdf.parse(printTime));
			}

			
			if (org != null && !org.equals("")) { 				 
				param.put("cOrgid", org);				
			}

			if (cScanCode != null && !cScanCode.trim().equals("")) {
				param.put("cScanCode", "%" + cScanCode + "%");
			}
			
			if (ptStatus != null && !ptStatus.trim().equals("")) {
				param.put("ptStatus", ptStatus);
			}
			
			String pcount=request.getParameter("c_print_count");
			if (pcount != null && !pcount.trim().equals("")) {
				param.put("c_print_count", pcount);
			}
			
			List list=jobObjectsService.getObjectsToExl(param);
			
			String[] titles=this.getColumNames();
			
			List<String> rootNames=this.getTypeNames();
			for(int k=0;k<list.size();k++){
				TbIntegrateInfo integerInfo=(TbIntegrateInfo)list.get(k);
				this.setValue(integerInfo, rootNames);
			}
			int titleLength=titles.length;
			String[] fileds=new String[titleLength];
			int nameLength=rootNames.size();
			for(int i=0;i<nameLength;i++){
				fileds[i]="root"+(i+1);
			}
			fileds[nameLength]="cBasedataId";
			fileds[nameLength+1]="cScanCode";
			fileds[nameLength+2]="locStatusName";
			fileds[nameLength+3]="cPrintCount";
			fileds[nameLength+4]="cPrintUser";
			fileds[nameLength+5]="cPrintTime";
			fileds[nameLength+6]="cOrgName";
			
			fileds[nameLength+7]="c_basedata_name";
			fileds[nameLength+8]="c_basedata_code";
			fileds[nameLength+9]="c_basedata_usercode";
			if(titleLength>nameLength+10){
				for(int j=nameLength+10;j<titleLength;j++){
					fileds[j]="col"+(j-nameLength-9);
				}
			}
			String url=this.exportExcelData(titles, fileds, "JobObjectsQuery", list, "作业对象查询导出");
			Map map=new HashMap();
			map.put("url", url);
			this.formatData(map);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 反射set值
	 * @param tvo
	 * @param names
	 * @return
	 */
	private TbIntegrateInfo setValue(TbIntegrateInfo tvo,List<String> names) {
//		TbIntegrateInfo tvo = new TbIntegrateInfo();
		Class userCla = (Class) tvo.getClass();
		Field[] fs = userCla.getDeclaredFields();
		try {
			for (int j = 0; j < names.size(); j++) {
				for (int i = 0; i < fs.length; i++) {
					Field f = fs[i];
					f.setAccessible(true);
					if (f.getName().equals("root" + (j+1))) {
						f.set(tvo, names.get(j));
					}
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return tvo;
	}
	
	private Map getQueryParams() {
		Map params = new HashMap();
		String cStartTime = request.getParameter("cStartTime");
		String cEndTime = request.getParameter("cEndTime");
		if (cStartTime != null && !cStartTime.isEmpty()) {
			cStartTime = cStartTime + " 00:00:00";
			params.put("cStartTime", DateUtil.getTimestamp(DateUtil.formatStr2Date(cStartTime)));
		}
		if (cEndTime != null && !cEndTime.isEmpty()) {
			cEndTime = cEndTime + " 23:59:59";
			params.put("cEndTime", DateUtil.getTimestamp(DateUtil.formatStr2Date(cEndTime)));
		}
		params.put("cObjectTypeid", request.getParameter("cObjectTypeid"));
		params.put("cIslate", request.getParameter("cIslate"));
		params.put("cFinalStatus", request.getParameter("cFinalStatus"));
		params.put("cIserror", request.getParameter("cIserror"));
		params.put("org", request.getParameter("org"));
		return params;
	}
	
	public String getAllTaskPointcheckInfo() {
		Session session = SessionFactory.getInstance(request, response);
		String type = request.getParameter("type");//首页右侧paneL页数据获取
		String extype = request.getParameter("extype")==null?"":request.getParameter("extype");
		if(!extype.equals("1")){
		Map user = (Map) session.getAttribute("USERSESSION");
		if (user == null) {
			this.formatData("请先登录!");
			return SUCCESS;
		}
		}
		try{
		if (pagination == null) {
			pagination = new Pagination(1, 20);
		}
		Pagination page = null;
		if((type!=null&&type.equals("1"))){
			Map map = new HashMap();
			Map map2 = new HashMap();
			pagination = new Pagination(1,1);
			pagination.setOnlyCount(true);
			map.put("cIslate", "0");//点检逾期
			page = jobObjectsService.getAllTaskPointcheckInfo(map, pagination);
			map2.put("djyq", page.getCount());
			map.clear();
			map.put("cIserror", 1);//点检异常
			page = jobObjectsService.getAllTaskPointcheckInfo(map, pagination);
			map2.put("djyc",page.getCount());
			map.clear();
			map.put("cFinalStatus",2);//整改逾期
			page = jobObjectsService.getAllTaskPointcheckInfo(map, pagination);
			map2.put("zgyq", page.getCount());
			map.clear();
			map.put("cFinalStatus", 0);//整改未完成
			page = jobObjectsService.getAllTaskPointcheckInfo(map, pagination);
			map2.put("zgwwc", page.getCount());
			this.formatData(map2);
		}else{
			Map params = this.getQueryParams();
		page = jobObjectsService.getAllTaskPointcheckInfo(params, pagination);
		this.formatDatagirdData(page.getList(), page);
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String exportTaskPointcheckInfoExcel() {
		Session session = SessionFactory.getInstance(request, response);
		Map user = (Map) session.getAttribute("USERSESSION");
		if (user == null) {
			this.formatData("请先登录!");
			return SUCCESS;
		}
		
		Map params = this.getQueryParams();
		List lst = jobObjectsService.getAllTaskPointcheckInfoWithoutPage(params);
		String[] titles = {"时间", "作业对象", "作业区域", "部门", "管理责任人", "检查期限", "检查时间", "检查及时性", "检查状态", "异常原因", "整改情况", "最终处置情况"};
		String[] fields = {"cResettimeString", "cObjectTypename", "cAreaFullname", "cOrgName", "cExecUsername", "cPlanchecktimeString", "cChecktimeString", "cIslate", "cIserrorName", "cErrorreason", "cRectifyStatus", "cFinalStatus"};
		String url = this.exportExcelData(titles, fields, "TaskPointcheckQuery",  lst, "点检信息导出");
		Map map = new HashMap();
		map.put("url", url);
		this.formatData(map);
		return SUCCESS;
	}
	
	public String getTableTypeTreeForMobile() {
		this.formatData(jobObjectsService.getTableTypeTree());
		return SUCCESS;
	}
}
