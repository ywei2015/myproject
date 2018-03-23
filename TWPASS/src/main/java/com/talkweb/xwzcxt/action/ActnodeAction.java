package com.talkweb.xwzcxt.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.sun.org.apache.regexp.internal.recompile;
import com.talkweb.common.AppConstants;
import com.talkweb.twdpe.base.org.pojo.Org;
import com.talkweb.twdpe.base.org.service.OrgService;
import com.talkweb.twdpe.core.log.Logger;
import com.talkweb.twdpe.core.log.LoggerFactory;
import com.talkweb.twdpe.core.util.ExcelUtil;
import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.twdpe.web.commons.StaticUploadInfo;
import com.talkweb.twdpe.web.session.main.Session;
import com.talkweb.twdpe.web.session.util.SessionFactory;
import com.talkweb.xwzcxt.dal.TSdMediastdDal;
import com.talkweb.xwzcxt.pojo.Actnode;
import com.talkweb.xwzcxt.pojo.MyLogPojo;
import com.talkweb.xwzcxt.pojo.TSdActnodePojo;
import com.talkweb.xwzcxt.pojo.TSdActnodeItemPojo;
import com.talkweb.xwzcxt.pojo.TSdMediastdPojo;
import com.talkweb.xwzcxt.service.DpPositionServiceI;
import com.talkweb.xwzcxt.service.MyLogService;
import com.talkweb.xwzcxt.service.StandardLibraryService;
import com.talkweb.xwzcxt.service.TSdActnodeItemServiceI;
import com.talkweb.xwzcxt.service.TSdActnodeServiceI;
import com.talkweb.xwzcxt.util.FileOperate;

/**
 * @author: 2013-12-21，FLN，（描述修改内容）
 */
public class ActnodeAction extends baseAction {
	private Actnode actnode;

	private static final Logger logger = LoggerFactory.getLogger(ActnodeAction.class);

	@Autowired
	private StandardLibraryService standardLibraryService;

	@Autowired
	private OrgService orgService;

	@Autowired
	private AppConstants appConstants;

	@Autowired
	private TSdActnodeServiceI tSdActnodeService;

	@Autowired
	private TSdActnodeItemServiceI tSdActnodeItemService;

	@Autowired
	private DpPositionServiceI dpPositionService;

	@Autowired
	private TSdMediastdDal tSdMediastdDal;
	
	@Autowired
	private MyLogService logService;

	// 管理流程查询
	public String queryActnode() {
		if (actnode == null)
			actnode = new Actnode();

		Pagination result = standardLibraryService.queryActnode(actnode, pagination);
		List pointList = result.getList();
		this.formatDatagirdData(pointList, result);
		return SUCCESS;
	}

	// 管理流程查询
	public String queryActnodeByParams() {
		HttpServletRequest request = ServletActionContext.getRequest();
		Map params = new HashMap();

		params.put("c_action_id", request.getParameter("Actnode.c_action_id"));
		params.put("c_actnode_name", request.getParameter("Actnode.c_actnode_name"));
		params.put("c_iskeyctrl", request.getParameter("Actnode.c_iskeyctrl"));
		params.put("c_pdca", request.getParameter("Actnode.c_pdca"));
		params.put("c_frequency", request.getParameter("Actnode.c_frequency"));
		params.put("c_area_id", request.getParameter("Actnode.c_area_id"));
		params.put("c_position_id", request.getParameter("Actnode.c_position_id"));

		/*
		 * params.put("c_action_id", ""); params.put("c_actnode_name", "");
		 * params.put("c_iskeyctrl", ""); params.put("c_pdca", "");
		 * params.put("c_frequency", ""); params.put("c_area_id", "");
		 * params.put("c_position_id", "");
		 */

		Pagination result = standardLibraryService.queryActnodeByParams(params, pagination);
		List pointList = result.getList();
		this.formatDatagirdData(pointList, result);
		return SUCCESS;
	}

	// 添加岗位活动
	public String addActnode() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		if (actnode == null)
			actnode = new Actnode();

		actnode.setC_actnode_id(UUID.randomUUID().toString());

		standardLibraryService.addActnode(actnode, request, response);

		Map<String, String> pageData = new HashMap<String, String>();
		pageData.put("msg", "添加成功");
		pageData.put("status", "ok");
		this.formatData(pageData);

		return SUCCESS;
	}

	// 添加岗位活动步骤
	public String addActnodeItem() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		if (actnode == null)
			actnode = new Actnode();

		actnode.setC_actitem_id(UUID.randomUUID().toString());

		standardLibraryService.addActnodeItem(actnode, request, response);

		Map<String, String> pageData = new HashMap<String, String>();
		pageData.put("msg", "添加成功");
		pageData.put("status", "ok");
		this.formatData(pageData);

		return SUCCESS;
	}

	// 查询活动步骤
	public String queryActnodeItem() {
		if (actnode == null)
			actnode = new Actnode();

		try {
			List<Actnode> list = standardLibraryService.queryActnodeItem(actnode);
			if (list != null && list.size() > 0) {
				this.formatDatagirdData(list, new Pagination(false, 10));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public Actnode getActnode() {
		return actnode;
	}

	public void setActnode(Actnode actnode) {
		this.actnode = actnode;
	}

	/*****************************************************************************************************
	 * 
	 * 新岗位活动标准管理
	 * 
	 * 
	 * ZZ
	 * 
	 ******************************************************************************************************/

	public String getAllActNodesByConditions() {
		if (pagination == null) {
			pagination = new Pagination(1, 20);
		}
		Pagination page = null;
		if (actnode != null) {
			String pid = actnode.getC_position_id();
			if (pid != null && !pid.isEmpty()) {
				actnode.setC_ispublic("0");
				page = tSdActnodeService.getAllActNodesByConditions(actnode, pagination);
				this.formatDatagirdData(page.getList(), page);
			}
		}
		return SUCCESS;
	}

	public String getAllActNodesByConditionsByPublic() {
		if (pagination == null) {
			pagination = new Pagination(1, 20);
		}
		Pagination page = null;
		if (actnode != null) {
			String pid = actnode.getC_public_id();
			if (pid != null && !pid.isEmpty()) {
				actnode.setC_ispublic("1");
				page = tSdActnodeService.getAllActNodesByConditions(actnode, pagination);
				this.formatDatagirdData(page.getList(), page);
			}
		}
		return SUCCESS;
	}

	public String getAllActNodesByConditionsForSearch() {
		if (pagination == null) {
			pagination = new Pagination(1, 20);
		}
		Pagination page = tSdActnodeService.getAllActNodesByConditions(actnode, pagination);
		this.formatDatagirdData(page.getList(), page);
		return SUCCESS;
	}

	public String getActNodeItemByActNodeID() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String aid = request.getParameter("cActnodeId");
		this.formatData(tSdActnodeItemService.getActnodeItemsByActnodeID(aid));
		return SUCCESS;
	}

	public String getActNodeByActNodeID() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String aid = request.getParameter("cActnodeId");
		this.formatData(tSdActnodeService.getActNodeByID(aid));
		return SUCCESS;
	}

	public String getActNodeByActNodeIDGrid() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String aid = request.getParameter("cActnodeId");
		if (pagination == null) {
			pagination = new Pagination(1, 20);
		}
		List<TSdActnodePojo> li = new ArrayList<TSdActnodePojo>();
		li.add(tSdActnodeService.getActNodeByID(aid));
		this.formatDatagirdData(li, pagination);
		return SUCCESS;
	}

	/*****************************************************************************************************
	 * 
	 * 岗位活动管理
	 * 
	 * 
	 * CC
	 * 
	 ******************************************************************************************************/

	public String importActNode() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		Session session = SessionFactory.getInstance(request, response);
		String filePath = actnode.getFilePath();
		String path = "";//request.getRealPath("");
		String fileName = filePath.substring(filePath.lastIndexOf(StaticUploadInfo.upload_system_url) + StaticUploadInfo.upload_system_url.length());
		path = request.getSession().getServletContext().getRealPath(fileName);
		this.setErrorMessage("操作失败");

		String userid = "";
		Map user = (Map) session.getAttribute("USERSESSION");
		if (user != null) {
			userid = user.get("id").toString();
		}

		List<Actnode> ActnodeList = null;
		String errorMessage = "<table border=1 style='BORDER-COLLAPSE: collapse' borderColor='#000000' cellPadding=1><tr><td>行</td><td>列</td><td>校验异常信息</td></tr>";
		int errorNumber = 0;
		int successes = 0;
		int failures = 0;
		if (filePath != null) {
			List<String[]> list = null;
			try {
				if (ExcelUtil.isExcelVersion2007(path))
					list = ExcelUtil.loadExcelXlsx(path);
				else
					list = ExcelUtil.loadExcelXls(path);
			} catch (Exception e) {
				ServletActionContext.getRequest().setAttribute("throw", e);
				this.setOkMessage("模板格式有误，请下载系统提供的模版！");
				//删除上传的文件
				delFile(path);
				return SUCCESS;
			}

			Map map = getData(list, actnode);
			String errMsg = (String) map.get("errMsg");
			if (!errMsg.equals("")) {
				this.setOkMessage(errMsg);
				//删除上传的文件
				delFile(path);
				return SUCCESS;
			}
			ActnodeList = (List<Actnode>) map.get("actnodeList");
			String lastActnodeid = "";
			int actitemindex = 0;
			int index = 3;
			for (Actnode act:ActnodeList) {
				index++;
				List actnodelist = null;
				if (actnode.getC_ispublic().equals("0")) {
					actnodelist = standardLibraryService.queryActnodeListByParams(act);
				} else if (actnode.getC_ispublic().equals("1")) {
					actnodelist = standardLibraryService.queryActnodeListByParamsForPublic(act);
				}
				if (actnodelist.size() > 0) {
					actitemindex = 0;
					failures++;
					continue;
				}
				if (act.getC_actnode_name() != null && !act.getC_actnode_name().equals("")) {
					actitemindex = 1;
					String actnodeid = UUID.randomUUID().toString();
					lastActnodeid = actnodeid;
					act.setC_actnode_id(actnodeid);
					act.setC_actitem_id(UUID.randomUUID().toString());
					act.setC_creator(userid);
					act.setC_actitem_index(Integer.toString(actitemindex++));
					act.setC_flag("1");
					act.setC_ispublic(actnode.getC_ispublic());
					try {
						successes++;
						standardLibraryService.addActnode(act, request, response);
						standardLibraryService.addActnodeItem(act, request, response);
					} catch (Exception e) {
						if (e.toString().contains("ORA-12899")) {
							errorNumber++;
							if (e.toString().contains("C_AREA_NAME")) {
								if (actnode.getC_actnodetype().equals("1")) {
									errorMessage += "<tr><td>" + index + "</td><td>6</td><td>作业区域的长度过大</td></tr>";
								} else if (actnode.getC_actnodetype().equals("2")) {
									errorMessage += "<tr><td>" + index + "</td><td>7</td><td>工作区域的长度过大</td></tr>";
								}
							} else if (e.toString().contains("C_POSITION_NAME")) {
								if (actnode.getC_actnodetype().equals("1")) {
									errorMessage += "<tr><td>" + index + "</td><td>7</td><td>执行岗位的长度过大</td></tr>";
								} else if (actnode.getC_actnodetype().equals("2")) {
									errorMessage += "<tr><td>" + index + "</td><td>8</td><td>管理岗位的长度过大</td></tr>";
								}
							}
						}
					}
				} else {
					if (actitemindex == 0) {
						continue;
					}
					act.setC_actnode_id(lastActnodeid);
					act.setC_actitem_id(UUID.randomUUID().toString());
					act.setC_actitem_index(Integer.toString(actitemindex++));
					act.setC_flag("1");
					standardLibraryService.addActnodeItem(act, request, response);
				}
			}
		}

		if (0 == errorNumber) {
			String okMessage = "导入完成<br/>成功：" + successes + "条<br/>失败：" + failures + "条";
			if (0 < failures) {
				okMessage += "<br/>注：失败原因——标准重复";
			}
			this.setOkMessage(okMessage);
		} else {
			this.setOkMessage(errorMessage);
		}
		//删除上传的文件
		delFile(path);

		return SUCCESS;
	}

	private Map getData(List<String[]> list, Actnode act) {
		if (actnode.getC_actnodetype().equals("1")) {
			//作业岗位校验与导入
			return getZuoyeData(list, act);
		} else if (actnode.getC_actnodetype().equals("2")) {
			//管理岗位校验与导入
			return getGuanliData(list, act);
		} else {
			return null;
		}
	}

	private Map getZuoyeData(List<String[]> list, Actnode act) {
		Map map = new HashMap();
		List<Actnode> ActnodeList = new ArrayList<Actnode>();
		String errMsg = "";
		int errNum = 0;
		//获取模板title属性
		String[] attr = list.get(1);
		String[] _attr = list.get(2);
		//校验模板是否为提供的标准模版
		if (!attr[0].replace("\n", "").trim().equals("对应流程末节点(why)")
			|| !attr[1].replace("\n", "").trim().equals("岗位活动名称")
			|| !attr[2].replace("\n", "").trim().equals("PDCA属性")
			|| !attr[3].replace("\n", "").trim().equals("是否为关键活动")
			|| !attr[4].replace("\n", "").trim().equals("时态(when)")
			|| !attr[5].replace("\n", "").trim().equals("作业区域(where)")
			|| !attr[6].replace("\n", "").trim().equals("执行岗位(who)")
			|| !attr[7].replace("\n", "").trim().equals("做什么(what)")
			|| !attr[8].replace("\n", "").trim().equals("是否按顺序执行")
			|| !attr[9].replace("\n", "").trim().equals("是否为随机任务")
			|| !attr[10].replace("\n", "").trim().equals("是否扫码")
			|| !attr[11].replace("\n", "").trim().equals("执行标准（怎么做，做到何程度）(how、howmuch)")
			|| !attr[12].replace("\n", "").trim().equals("数据采集名称")
			|| !attr[13].replace("\n", "").trim().equals("数据采集单位")
			|| !attr[14].replace("\n", "").trim().equals("执行结果上传要求")
			|| !attr[15].replace("\n", "").trim().equals("执行结果后置文本")
			|| !attr[16].replace("\n", "").trim().equals("异常怎么处置（执行环节）")
			|| !attr[17].replace("\n", "").trim().equals("规范性记录（执行环节）")
			|| !attr[18].replace("\n", "").trim().equals("验证岗位")
			|| !attr[19].replace("\n", "").trim().equals("验证标准（如何核查）")
			|| !attr[20].replace("\n", "").trim().equals("验证结果上传要求")
			|| !attr[21].replace("\n", "").trim().equals("异常怎么处置（验证环节）")
			|| !attr[22].replace("\n", "").trim().equals("规范性记录（验证环节）")
			|| !attr[23].replace("\n", "").trim().equals("评价岗位")
			|| !attr[24].replace("\n", "").trim().equals("评价标准")
			|| !attr[25].replace("\n", "").trim().equals("执行期限")
			|| !attr[27].replace("\n", "").trim().equals("验证期限")
			|| !attr[28].replace("\n", "").trim().equals("评价期限")
			|| !attr[29].replace("\n", "").trim().equals("正常结果反馈岗位")
			|| !attr[31].replace("\n", "").trim().equals("异常结果反馈岗位")
			|| !_attr[25].replace("\n", "").trim().equals("开始时间")
			|| !_attr[26].replace("\n", "").trim().equals("完成时间")
			|| !_attr[27].replace("\n", "").trim().equals("完成时间")
			|| !_attr[28].replace("\n", "").trim().equals("完成时间")
			|| !_attr[29].replace("\n", "").trim().equals("默认岗位")
			|| !_attr[30].replace("\n", "").trim().equals("其他岗位")
			|| !_attr[31].replace("\n", "").trim().equals("默认岗位")
			|| !_attr[32].replace("\n", "").trim().equals("其他岗位")) {
			errMsg+="请使用系统提供的《作业岗位5W2H行为模版》进行导入操作！";
		}
		if (!errMsg.equals("")) {
			map.put("errMsg", errMsg);
			return map;
		}

		errMsg = "<table border=1 style='BORDER-COLLAPSE: collapse' borderColor='#000000' cellPadding=1><tr><td>行</td><td>列</td><td>校验异常信息</td></tr>";

		//从第四行记录开始解析
		int index = 0;
		int groupIndex = 0;
		boolean isActnode = false;
		for (int i = 3; i < list.size(); i++) {
			String[] s = list.get(i);
			if (s != null && s.length == 33) {
				Actnode currentAct = new Actnode();

				if (!s[0].equals("")) {
					index = ActnodeList.size();
					groupIndex = 0;
					isActnode = true;
				} else {
					isActnode = false;
				}

				currentAct.setC_action_name(s[0]);//对应流程末节点

				currentAct.setC_actnode_name(s[1]);//岗位活动名称

				if (s[2].toUpperCase().indexOf("P") != -1) {
					currentAct.setC_pdca("P");//PCDA属性
				} else if (s[2].toUpperCase().indexOf("D") != -1) {
					currentAct.setC_pdca("D");//PCDA属性
				} else if (s[2].toUpperCase().indexOf("C") != -1) {
					currentAct.setC_pdca("C");//PCDA属性
				} else if (s[2].toUpperCase().indexOf("A") != -1) {
					currentAct.setC_pdca("A");//PCDA属性
				}
				//校验pcda属性是否符合格式
				if (!s[2].equals("P(计划)") && !s[2].equals("D(执行)") && !s[2].equals("C(检查)") && !s[2].equals("A(改进)") && (!s[1].equals("") && !s[6].equals(""))) {
					errMsg += "<tr><td>" + (i + 1) + "</td><td>3</td><td>应输入：P(计划)、D(执行)、C(检查)、A(改进)</td></tr>";
					errNum++;
				}

				if (s[3].equals("是")) {
					currentAct.setC_iskeyctrl("1");//是否为关键活动
				} else {
					currentAct.setC_iskeyctrl("0");//是否为关键活动
				}
				//校验是否为关键活动属性是否符合格式
				if (!s[3].equals("是") && !s[3].equals("否") && (!s[1].equals("") && !s[6].equals(""))) {
					errMsg += "<tr><td>" + (i + 1) + "</td><td>4</td><td>应输入：是、否</td></tr>";
					errNum++;
				}

				currentAct.setC_frequency(s[4]);//时态
				//校验时态属性是否符合格式
				if (!s[4].equals("每日") && !s[4].equals("每周") && !s[4].equals("每月") &&
					!s[4].equals("接班前") && !s[4].equals("接班后") && !s[4].equals("下班前") && !s[4].equals("下班后") &&
					(!s[1].equals("") && !s[6].equals(""))) {
					errMsg += "<tr><td>" + (i + 1) + "</td><td>5</td><td>应输入：每日、每周、每月、接班前、接班后、下班前、下班后</td></tr>";
					errNum++;
				}

				currentAct.setC_area_name(s[5]);//作业区域
				//校验作业区域是否符合格式
				if (s[5].contains(",") || s[5].contains(";") || s[5].contains(".") || s[5].contains("、") || s[5].contains("，") || s[5].contains("；") || s[5].contains("。")) {
					errMsg += "<tr><td>" + (i + 1) + "</td><td>6</td><td>应只能输入一个作业区域</td></tr>";
					errNum++;
				}

				currentAct.setC_position_name(s[6]);//执行岗位
				//校验执行岗位是否符合格式
				if (s[6].contains(",") || s[6].contains(";") || s[6].contains(".") || s[6].contains("、") || s[6].contains("，") || s[6].contains("；") || s[6].contains("。")) {
					errMsg += "<tr><td>" + (i + 1) + "</td><td>7</td><td>应只能输入一个执行岗位</td></tr>";
					errNum++;
				}

				if (s[8].equals("是")) {
					currentAct.setC_issequence("1");//是否按顺序执行
				} else {
					currentAct.setC_issequence("0");//是否按顺序执行
				}
				//校验是否按顺序执行属性是否符合格式
				if (!s[8].equals("是") && !s[8].equals("否") && (!s[1].equals("") && !s[6].equals(""))) {
					errMsg += "<tr><td>" + (i + 1) + "</td><td>9</td><td>应输入：是、否</td></tr>";
					errNum++;
				}

				if (s[9].equals("是")) {
					currentAct.setC_israndom("1");//是否为随机任务
				} else {
					currentAct.setC_israndom("0");//是否为随机任务
				}
				//校验是否扫码属性是否符合格式
				if (!s[9].equals("是") && !s[9].equals("否") && (!s[1].equals("") && !s[6].equals(""))) {
					errMsg += "<tr><td>" + (i + 1) + "</td><td>10</td><td>应输入：是、否</td></tr>";
					errNum++;
				}

				if (s[10].equals("是")) {
					currentAct.setC_isscan("1");//是否扫码
				} else {
					currentAct.setC_isscan("0");//是否扫码
				}
				//校验是否扫码属性是否符合格式
				if (!s[10].equals("是") && !s[10].equals("否") && (!s[1].equals("") && !s[6].equals(""))) {
					errMsg += "<tr><td>" + (i + 1) + "</td><td>11</td><td>应输入：是、否</td></tr>";
					errNum++;
				}

				if (!s[11].equals("")) {
					if (isActnode) {
						currentAct.setC_std_exec(s[11]);//执行标准
					} else {
						ActnodeList.get(index).setC_std_exec(ActnodeList.get(index).getC_std_exec() + "\n" + s[11]);//执行标准
					}
				}

				currentAct.setC_getdata_pretext(s[12]);//数据采集名称

				currentAct.setC_getdata_unit(s[13]);//数据采集单位

				//执行结果上传要求
				if (s[14].indexOf("拍照") != -1) {//执行结果上传要求，对应item的执行数据获取方式（1：拍照；2：录音；3：录像；10：人工录入；10：文本；11：判断检查结果；20：扫码；30：GPS定位；98：无）
					currentAct.setC_getdatatype("1");
				} else if (s[14].indexOf("录音") != -1) {
					currentAct.setC_getdatatype("2");
				} else if (s[14].indexOf("录像") != -1) {
					currentAct.setC_getdatatype("3");
				} else if (s[14].indexOf("人工录入") != -1) {
					currentAct.setC_getdatatype("10");
				} else if (s[14].indexOf("文本") != -1) {
					currentAct.setC_getdatatype("10");
				} else if (s[14].indexOf("判断检查结果") != -1) {
					currentAct.setC_getdatatype("11");
				} else if (s[14].indexOf("扫码") != -1) {
					currentAct.setC_getdatatype("20");
				} else if (s[14].indexOf("GPS定位") != -1) {
					currentAct.setC_getdatatype("30");
				} else if (s[14].isEmpty()) {
					currentAct.setC_getdatatype(ActnodeList.get(ActnodeList.size() - 1).getC_getdatatype());
				} else {
					currentAct.setC_getdatatype("98");
				}
				//校验执行结果上传要求
				if (!s[14].equals("无") && !s[14].equals("拍照") && !s[14].equals("录音") && !s[14].equals("录像") &&
					!s[14].equals("人工录入") &&!s[14].equals("文本") && !s[14].equals("判断检查结果") && !s[14].equals("扫码") &&
					!s[14].equals("GPS定位") && (!s[1].equals("") && !s[6].equals(""))) {
					errMsg += "<tr><td>" + (i + 1) + "</td><td>15</td><td>应输入：无、拍照、录音、录像、人工录入、文本、判断检查结果、扫码、GPS定位</td></tr>";
					errNum++;
				}

				currentAct.setC_getdata_text(s[15]);//执行结果后置文本

				currentAct.setC_err_exec(s[16]);//异常怎么处置

				currentAct.setC_records_exec(s[17]);//规范性记录

				currentAct.setC_position_name_check(s[18]);//验证岗位

				if (!s[19].equals("")) {
					if (isActnode) {
						currentAct.setC_std_check(s[19]);//验证标准
					} else {
						ActnodeList.get(index).setC_std_check(ActnodeList.get(index).getC_std_check() + "\n" + s[19]);//验证标准
					}
				}

				//验证结果上传要求
				if (s[20].indexOf("拍照") != -1) {//验证结果上传要求，对应item的验证数据获取方式（1：拍照；2：录音；3：录像；10：人工录入；10：文本；11：判断检查结果；20：扫码；30：GPS定位；98：无）
					currentAct.setC_checkdatatype("1");
				} else if (s[20].indexOf("录音") != -1) {
					currentAct.setC_checkdatatype("2");
				} else if (s[20].indexOf("录像") != -1) {
					currentAct.setC_checkdatatype("3");
				} else if (s[20].indexOf("人工录入") != -1) {
					currentAct.setC_checkdatatype("10");
				} else if (s[20].indexOf("文本") != -1) {
					currentAct.setC_checkdatatype("10");
				} else if (s[20].indexOf("判断检查结果") != -1) {
					currentAct.setC_checkdatatype("11");
				} else if (s[20].indexOf("扫码") != -1) {
					currentAct.setC_checkdatatype("20");
				} else if (s[20].indexOf("GPS定位") != -1) {
					currentAct.setC_checkdatatype("30");
				} else if (s[20].isEmpty()) {
					currentAct.setC_checkdatatype(ActnodeList.get(ActnodeList.size() - 1).getC_checkdatatype());
				} else {
					currentAct.setC_checkdatatype("98");
				}
				//校验验证结果上传要求
				if (!s[20].equals("无") && !s[20].equals("拍照") && !s[20].equals("录音") && !s[20].equals("录像") &&
					!s[20].equals("人工录入") && !s[20].equals("文本") && !s[20].equals("判断检查结果") && !s[20].equals("扫码") &&
					!s[20].equals("GPS定位") && (!s[1].equals("") && !s[6].equals(""))) {
					errMsg += "<tr><td>" + (i + 1) + "</td><td>21</td><td>应输入：无、拍照、录音、录像、人工录入、文本、判断检查结果、扫码、GPS定位</td></tr>";
					errNum++;
				}

				currentAct.setC_err_check(s[21]);//异常怎么处置（验证环节）

				currentAct.setC_records_check(s[22]);//规范性记录（验证环节）

				currentAct.setC_position_name_review(s[23]);//评价岗位

				currentAct.setC_std_review(s[24]);//评价标准

				currentAct.setC_starttime_exec(s[25]);//开始时间

				currentAct.setC_endtime_exec(s[26]);//完成时间

				String checkTime = null, reviewTime = null;
				if (s[4].equals("每月")) {
					checkTime = "下月20日前";
					reviewTime = "下月28日前";
				} else if (s[4].equals("每周")) {
					checkTime = "下月15日前";
					reviewTime = "下月20日前";
				} else {
					checkTime = "下月10日前";
					reviewTime = "下月15日前";
				}
				currentAct.setC_timelimit_check(checkTime);//验证期限
				currentAct.setC_timelimit_review(reviewTime);//评价期限

				currentAct.setC_position_name_feedback1(s[29]);//正常结果反馈岗位-默认岗位

				currentAct.setC_position_name_feedback2(s[30]);//正常结果反馈岗位-其他岗位

				currentAct.setC_position_name_err1(s[31]);//异常结果反馈岗位-默认岗位

				currentAct.setC_position_name_err2(s[32]);//异常结果反馈岗位-其他岗位

				if (isActnode) {
					if (act.getC_ispublic().equals("0")) {
						currentAct.setC_department(act.getC_department());//部门
						currentAct.setC_position_id(act.getC_position_id());//作业岗位ID
						currentAct.setC_position_name(dpPositionService.getPositionNameByID(act.getC_position_id()));//作业岗位名称
					} else if (act.getC_ispublic().equals("1")) {
						currentAct.setC_position_name("在行为系统中勾选");//作业岗位名称
						currentAct.setC_public_id(act.getC_public_id());//通用标准节点
					}
				}

				currentAct.setC_actnodetype(act.getC_actnodetype());//岗位活动类型

				if (!s[7].equals("")) {
					currentAct.setC_actitem_name(s[7]);//做什么，对应item的名称
					groupIndex++;
				} else {
					currentAct.setC_actitem_name(ActnodeList.get(ActnodeList.size() - 1).getC_actitem_name());//做什么，对应item的名称
				}
				if (!s[11].equals("")) {
					currentAct.setC_actitem_std(s[11]);//执行标准，对应item的活动项标准
				} else {
					currentAct.setC_actitem_std(ActnodeList.get(ActnodeList.size() - 1).getC_actitem_std());//执行标准，对应item的活动项标准
				}
				if (!s[19].equals("")) {
					currentAct.setC_actitem_std_check(s[19]);//验证标准，对应item的活动项验证标准
				} else {
					currentAct.setC_actitem_std_check(ActnodeList.get(ActnodeList.size() - 1).getC_actitem_std_check());//验证标准，对应item的活动项验证标准
				}
				currentAct.setC_groupindex(Integer.toString(groupIndex));//分组序号（一步骤对多动作）

				ActnodeList.add(currentAct);
			}
		}

		errMsg += "</table>";
		if (errMsg.equals("<table border=1 style='BORDER-COLLAPSE: collapse' borderColor='#000000' cellPadding=1><tr><td>行</td><td>列</td><td>校验异常信息</td></tr></table>")) {
			errMsg = "";
		} else {
			errMsg = "总计" + errNum + "个错误</br>" + errMsg;
		}

		map.put("errMsg", errMsg);
		map.put("actnodeList", ActnodeList);
		return map;
	}

	private Map getGuanliData(List<String[]> list, Actnode act) {
		Map map = new HashMap();
		List<Actnode> ActnodeList = new ArrayList<Actnode>();
		String errMsg = "";
		int errNum = 0;
		//获取模板title属性
		String[] attr = list.get(1);
		String[] _attr = list.get(2);
		//校验模板是否为提供的标准模版
		if (!attr[0].replace("\n", "").trim().equals("对应流程末节点(why)")
			|| !attr[1].replace("\n", "").trim().equals("岗位活动名称")
			|| !attr[2].replace("\n", "").trim().equals("管理属性")
			|| !attr[3].replace("\n", "").trim().equals("PDCA属性")
			|| !attr[4].replace("\n", "").trim().equals("是否为关键活动")
			|| !attr[5].replace("\n", "").trim().equals("时态(when)")
			|| !attr[6].replace("\n", "").trim().equals("工作区域(where)")
			|| !attr[7].replace("\n", "").trim().equals("管理岗位(who)")
			|| !attr[8].replace("\n", "").trim().equals("管什么(what)")
			|| !attr[9].replace("\n", "").trim().equals("是否按顺序执行")
			|| !attr[10].replace("\n", "").trim().equals("是否为随机任务")
			|| !attr[11].replace("\n", "").trim().equals("是否扫码")
			|| !attr[12].replace("\n", "").trim().equals("管理要求（怎么做，做到何程度）(how、howmuch)")
			|| !attr[13].replace("\n", "").trim().equals("管理/技术标准（系统推送）")
			|| !attr[14].replace("\n", "").trim().equals("考核标准")
			|| !attr[15].replace("\n", "").trim().equals("数据采集名称")
			|| !attr[16].replace("\n", "").trim().equals("数据采集单位")
			|| !attr[17].replace("\n", "").trim().equals("完工上传要求")
			|| !attr[18].replace("\n", "").trim().equals("完工后置文本")
			|| !attr[19].replace("\n", "").trim().equals("异常怎么处置")
			|| !attr[20].replace("\n", "").trim().equals("规范性记录")
			|| !attr[21].replace("\n", "").trim().equals("验证岗位")
			|| !attr[22].replace("\n", "").trim().equals("验证标准（如何核查）")
			|| !attr[23].replace("\n", "").trim().equals("验证结果上传要求")
			|| !attr[24].replace("\n", "").trim().equals("监督岗位")
			|| !attr[25].replace("\n", "").trim().equals("完工期限")
			|| !attr[27].replace("\n", "").trim().equals("验证期限")
			|| !attr[28].replace("\n", "").trim().equals("监督期限")
			|| !attr[29].replace("\n", "").trim().equals("正常结果反馈岗位")
			|| !attr[31].replace("\n", "").trim().equals("异常结果反馈岗位")
			|| !_attr[25].replace("\n", "").trim().equals("开始时间")
			|| !_attr[26].replace("\n", "").trim().equals("完成时间")
			|| !_attr[27].replace("\n", "").trim().equals("完成时间")
			|| !_attr[28].replace("\n", "").trim().equals("完成时间")
			|| !_attr[29].replace("\n", "").trim().equals("默认岗位")
			|| !_attr[30].replace("\n", "").trim().equals("其他岗位")
			|| !_attr[31].replace("\n", "").trim().equals("默认岗位")
			|| !_attr[32].replace("\n", "").trim().equals("其他岗位")) {
			errMsg += "请使用系统提供的《管理岗位5W2H行为模版》进行导入操作！";
		}
		if (!errMsg.equals("")) {
			map.put("errMsg", errMsg);
			return map;
		}

		errMsg = "<table border=1 style='BORDER-COLLAPSE: collapse' borderColor='#000000' cellPadding=1><tr><td>行</td><td>列</td><td>校验异常信息</td></tr>";

		//从第四行记录开始解析
		int index = 0;
		int groupIndex = 0;
		boolean isActnode = false;
		for (int i = 3; i < list.size(); i++) {
			String[] s = list.get(i);
			if (s != null && s.length == 33) {
				Actnode currentAct = new Actnode();

				if (!s[0].equals("")) {
					index = ActnodeList.size();
					groupIndex = 0;
					isActnode = true;
				} else {
					isActnode = false;
				}

				currentAct.setC_action_name(s[0]);//对应流程末节点

				currentAct.setC_actnode_name(s[1]);//岗位活动名称

				currentAct.setC_manageattr(s[2]);//管理属性

				if (s[3].toUpperCase().indexOf("P") != -1) {
					currentAct.setC_pdca("P");//PCDA属性
				} else if (s[3].toUpperCase().indexOf("D") != -1) {
					currentAct.setC_pdca("D");//PCDA属性
				} else if (s[3].toUpperCase().indexOf("C") != -1) {
					currentAct.setC_pdca("C");//PCDA属性
				} else if (s[3].toUpperCase().indexOf("A") != -1) {
					currentAct.setC_pdca("A");//PCDA属性
				}
				//校验pcda属性是否符合格式
				if (!s[3].equals("P(计划)") && !s[3].equals("D(执行)") && !s[3].equals("C(检查)") && !s[3].equals("A(改进)") && (!s[1].equals("") && !s[7].equals(""))) {
					errMsg += "<tr><td>" + (i + 1) + "</td><td>4</td><td>应输入：P(计划)、D(执行)、C(检查)、A(改进)</td></tr>";
					errNum++;
				}

				if (s[4].equals("是")) {
					currentAct.setC_iskeyctrl("1");//是否为关键活动
				} else {
					currentAct.setC_iskeyctrl("0");//是否为关键活动
				}
				//校验是否为关键活动属性是否符合格式
				if (!s[4].equals("是") && !s[4].equals("否") && (!s[1].equals("") && !s[7].equals(""))) {
					errMsg += "<tr><td>" + (i + 1) + "</td><td>5</td><td>应输入：是、否</td></tr>";
					errNum++;
				}

				currentAct.setC_frequency(s[5]);//时态
				//校验时态属性是否符合格式
				if (!s[5].equals("每日") && !s[5].equals("每周") && !s[5].equals("每月") &&
					!s[5].equals("接班前") && !s[5].equals("接班后") && !s[5].equals("下班前") && !s[5].equals("下班后") &&
					(!s[1].equals("") && !s[7].equals(""))) {
					errMsg += "<tr><td>" + (i + 1) + "</td><td>6</td><td>应输入：每日、每周、每月、接班前、接班后、下班前、下班后</td></tr>";
					errNum++;
				}

				currentAct.setC_area_name(s[6]);//工作区域
				//校验工作区域是否符合格式
				if (s[6].contains(",") || s[6].contains(";") || s[6].contains(".") || s[6].contains("、") || s[6].contains("，") || s[6].contains("；") || s[6].contains("。")) {
					errMsg += "<tr><td>" + (i + 1) + "</td><td>7</td><td>应只能输入一个工作区域</td></tr>";
					errNum++;
				}

				currentAct.setC_position_name(s[7]);//管理岗位
				//校验管理岗位是否符合格式
				if (s[7].contains(",") || s[7].contains(";") || s[7].contains(".") || s[7].contains("、") || s[7].contains("，") || s[7].contains("；") || s[7].contains("。")) {
					errMsg += "<tr><td>" + (i + 1) + "</td><td>8</td><td>应只能输入一个管理岗位</td></tr>";
					errNum++;
				}

				if (s[9].equals("是")) {
					currentAct.setC_issequence("1");//是否按顺序执行
				} else {
					currentAct.setC_issequence("0");//是否按顺序执行
				}
				//校验是否按顺序执行属性是否符合格式
				if (!s[9].equals("是") && !s[9].equals("否") && (!s[1].equals("") && !s[7].equals(""))) {
					errMsg += "<tr><td>" + (i + 1) + "</td><td>10</td><td>应输入：是、否</td></tr>";
					errNum++;
				}

				if (s[10].equals("是")) {
					currentAct.setC_israndom("1");//是否为随机任务
				} else {
					currentAct.setC_israndom("0");//是否为随机任务
				}
				//校验是否扫码属性是否符合格式
				if (!s[10].equals("是") && !s[10].equals("否") && (!s[1].equals("") && !s[7].equals(""))) {
					errMsg += "<tr><td>" + (i + 1) + "</td><td>11</td><td>应输入：是、否</td></tr>";
					errNum++;
				}

				if (s[11].equals("是")) {
					currentAct.setC_isscan("1");//是否扫码
				} else {
					currentAct.setC_isscan("0");//是否扫码
				}
				//校验是否扫码属性是否符合格式
				if (!s[11].equals("是") && !s[11].equals("否") && (!s[1].equals("") && !s[7].equals(""))) {
					errMsg += "<tr><td>" + (i + 1) + "</td><td>12</td><td>应输入：是、否</td></tr>";
					errNum++;
				}

				if (!s[12].equals("")) {
					if (isActnode) {
						currentAct.setC_std_exec(s[12]);//管理要求
					} else {
						ActnodeList.get(index).setC_std_exec(ActnodeList.get(index).getC_std_exec() + "\n" + s[12]);//管理要求
					}
				}

				currentAct.setC_managestd(s[13]);//管理/技术标准参考

				currentAct.setC_examstd(s[14]);//考核标准参考

				currentAct.setC_getdata_pretext(s[15]);//数据采集名称

				currentAct.setC_getdata_unit(s[16]);//数据采集单位

				//完工上传要求
				if (s[17].indexOf("拍照") != -1) {//完工上传要求，对应item的验证数据获取方式（1：拍照；2：录音；3：录像；10：人工录入；10：文本；11：判断检查结果；20：扫码；30：GPS定位；99：无）
					currentAct.setC_getdatatype("1");
				} else if (s[17].indexOf("录音") != -1) {
					currentAct.setC_getdatatype("2");
				} else if (s[17].indexOf("录像") != -1) {
					currentAct.setC_getdatatype("3");
				} else if (s[17].indexOf("人工录入") != -1) {
					currentAct.setC_getdatatype("10");
				} else if (s[17].indexOf("文本") != -1) {
					currentAct.setC_getdatatype("10");
				} else if (s[17].indexOf("判断检查结果") != -1) {
					currentAct.setC_getdatatype("11");
				} else if (s[17].indexOf("扫码") != -1) {
					currentAct.setC_getdatatype("20");
				} else if (s[17].indexOf("GPS定位") != -1) {
					currentAct.setC_getdatatype("30");
				} else if (s[17].isEmpty()) {
					currentAct.setC_getdatatype(ActnodeList.get(ActnodeList.size() - 1).getC_getdatatype());
				} else {
					currentAct.setC_getdatatype("99");
				}
				//校验完工上传要求
				if (!s[17].equals("无") && !s[17].equals("拍照") && !s[17].equals("录音") && !s[17].equals("录像") &&
					!s[17].equals("人工录入") && !s[17].equals("文本") && !s[17].equals("判断检查结果") && !s[17].equals("扫码") &&
					!s[17].equals("GPS定位") && (!s[1].equals("") && !s[7].equals(""))) {
					errMsg += "<tr><td>" + (i + 1) + "</td><td>18</td><td>应输入：无、拍照、录音、录像、人工录入、文本、判断检查结果、扫码、GPS定位</td></tr>";
					errNum++;
				}

				currentAct.setC_getdata_text(s[18]);//执行结果后置文本

				currentAct.setC_err_exec(s[19]);//异常怎么处置

				currentAct.setC_records_exec(s[20]);//规范性记录

				currentAct.setC_position_name_check(s[21]);//验证岗位

				if (!s[22].equals("")) {
					if (isActnode) {
						currentAct.setC_std_check(s[22]);//验证标准
					} else {
						ActnodeList.get(index).setC_std_check(ActnodeList.get(index).getC_std_check() + "\n" + s[22]);//验证标准
					}
				}

				//验证结果上传要求
				if (s[23].indexOf("拍照") != -1) {//验证结果上传要求，对应item的验证数据获取方式（1：拍照；2：录音；3：录像；10：人工录入；10：文本；11：判断检查结果；20：扫码；30：GPS定位；99：无）
					currentAct.setC_checkdatatype("1");
				} else if (s[23].indexOf("录音") != -1) {
					currentAct.setC_checkdatatype("2");
				} else if (s[23].indexOf("录像") != -1) {
					currentAct.setC_checkdatatype("3");
				} else if (s[23].indexOf("人工录入") != -1) {
					currentAct.setC_checkdatatype("10");
				} else if (s[23].indexOf("文本") != -1) {
					currentAct.setC_checkdatatype("10");
				} else if (s[23].indexOf("判断检查结果") != -1) {
					currentAct.setC_checkdatatype("11");
				} else if (s[23].indexOf("扫码") != -1) {
					currentAct.setC_checkdatatype("20");
				} else if (s[23].indexOf("GPS定位") != -1) {
					currentAct.setC_checkdatatype("30");
				} else if (s[23].isEmpty()) {
					currentAct.setC_checkdatatype(ActnodeList.get(ActnodeList.size() - 1).getC_checkdatatype());
				} else {
					currentAct.setC_checkdatatype("99");
				}
				//校验验证结果上传要求
				if (!s[23].equals("无") && !s[23].equals("拍照") && !s[23].equals("录音") && !s[23].equals("录像") &&
					!s[23].equals("人工录入") && !s[23].equals("文本") && !s[23].equals("判断检查结果") && !s[23].equals("扫码") &&
					!s[23].equals("GPS定位") && (!s[1].equals("") && !s[7].equals(""))){
					errMsg += "<tr><td>" + (i + 1) + "</td><td>24</td><td>应输入：无、拍照、录音、录像、人工录入、文本、判断检查结果、扫码、GPS定位</td></tr>";
					errNum++;
				}

				currentAct.setC_position_name_review(s[24]);//监督岗位

				currentAct.setC_starttime_exec(s[25]);//完工期限开始时间

				currentAct.setC_endtime_exec(s[26]);//完工期限完成时间

				String checkTime = null, reviewTime = null;
				if (s[5].equals("每月")) {
					checkTime = "下月20日前";
					reviewTime = "下月28日前";
				} else if (s[5].equals("每周")) {
					checkTime = "下月15日前";
					reviewTime = "下月20日前";
				} else {
					checkTime = "下月10日前";
					reviewTime = "下月15日前";
				}
				currentAct.setC_timelimit_check(checkTime);//验证期限
				currentAct.setC_timelimit_review(reviewTime);//监督期限

				currentAct.setC_position_name_feedback1(s[29]);//正常结果反馈岗位-默认岗位

				currentAct.setC_position_name_feedback2(s[30]);//正常结果反馈岗位-其他岗位

				currentAct.setC_position_name_err1(s[31]);//异常结果反馈岗位-默认岗位

				currentAct.setC_position_name_err2(s[32]);//异常结果反馈岗位-其他岗位

				if (isActnode) {
					if (act.getC_ispublic().equals("0")) {
						currentAct.setC_department(act.getC_department());//部门
						currentAct.setC_position_id(act.getC_position_id());//管理岗位ID
						currentAct.setC_position_name(dpPositionService.getPositionNameByID(act.getC_position_id()));//管理岗位名称
					} else if (act.getC_ispublic().equals("1")) {
						currentAct.setC_position_name("在行为系统中勾选");//管理岗位名称
						currentAct.setC_public_id(act.getC_public_id());//通用标准节点
					}
				}

				currentAct.setC_actnodetype(act.getC_actnodetype());//岗位活动类型

				if (!s[8].equals("")) {
					currentAct.setC_actitem_name(s[8]);//管什么，对应item的名称
					groupIndex++;
				} else {
					currentAct.setC_actitem_name(ActnodeList.get(ActnodeList.size() - 1).getC_actitem_name());//管什么，对应item的名称
				}
				if (!s[12].equals("")) {
					currentAct.setC_actitem_std(s[12]);//管理要求,对应item的活动项标准
				} else {
					currentAct.setC_actitem_std(ActnodeList.get(ActnodeList.size() - 1).getC_actitem_std());//管理要求,对应item的活动项标准
				}
				if (!s[22].equals("")) {
					currentAct.setC_actitem_std_check(s[22]);//验证标准, 对应item的活动项验证标准
				} else {
					currentAct.setC_actitem_std_check(ActnodeList.get(ActnodeList.size() - 1).getC_actitem_std_check());//验证标准, 对应item的活动项验证标准
				}
				currentAct.setC_groupindex(Integer.toString(groupIndex));//分组序号（一步骤对多动作）

				ActnodeList.add(currentAct);
			}
		}

		errMsg += "</table>";
		if (errMsg.equals("<table border=1 style='BORDER-COLLAPSE: collapse' borderColor='#000000' cellPadding=1><tr><td>行</td><td>列</td><td>校验异常信息</td></tr></table>")) {
			errMsg = "";
		} else {
			errMsg = "总计" + errNum + "个错误</br>" + errMsg;
		}

		map.put("errMsg", errMsg);
		map.put("actnodeList", ActnodeList);
		return map;
	}

	private void delFile(String path) {
		FileOperate fo = new FileOperate();
		fo.delFile(path);
	}

	public String delActNode() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String id = request.getParameter("id");
		standardLibraryService.delActNode(id, request, response);
		Map<String, String> pageData = new HashMap<String, String>();
		pageData.put("msg", "删除成功");
		pageData.put("status", "ok");
		this.formatData(pageData);
		return SUCCESS;
	}

	public String queryDepartByPositionId() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		Org org = standardLibraryService.queryDepartByPositionId(id);
		Map<String, String> pageData = new HashMap<String, String>();
		if (org != null && !org.getPath().equals("") && org.getPath().indexOf(";") != -1) {
			String depart_id = org.getPath().split(";")[1];
			Org depart = orgService.getOrg(Long.parseLong(depart_id));
			pageData.put("orgid", depart_id);
			pageData.put("orgname", depart.getOrgName());
		}
		this.formatData(pageData);
		return SUCCESS;
	}

	public String addActNodeByEdit() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		Session session = SessionFactory.getInstance(request, response);

		//0-查看，1-修改，2-新建
		int cModifyType = Integer.parseInt(request.getParameter("cModifyType"));
		Map user = (Map) session.getAttribute("USERSESSION");

		Actnode actNode = new Actnode();
		actNode.setC_actnodetype(request.getParameter("cActnodetype"));
		actNode.setC_department(request.getParameter("cDepartment"));
		actNode.setC_public_id(request.getParameter("cPublicId"));
		actNode.setC_ispublic(request.getParameter("cIspublic"));
		actNode.setC_media_file_id(request.getParameter("cMediaFileId"));
		actNode.setC_action_name(request.getParameter("cActionName"));
		actNode.setC_actnode_name(request.getParameter("cActnodeName"));
		actNode.setC_actnode_code(request.getParameter("cActnodeCode"));
		actNode.setC_manageattr(request.getParameter("cManageattr"));
		actNode.setC_pdca(request.getParameter("cPdca"));
		actNode.setC_iskeyctrl(request.getParameter("cIskeyctrl"));
		actNode.setC_frequency(request.getParameter("cFrequency"));
		actNode.setC_position_name(request.getParameter("cPositionName"));
		actNode.setC_position_id(request.getParameter("cPositionId"));
		actNode.setC_area_name(request.getParameter("cAreaName"));
		actNode.setC_area_id(request.getParameter("cAreaId"));
		actNode.setC_issequence(request.getParameter("cIssequence"));
		actNode.setC_israndom(request.getParameter("cIsrandom"));
		actNode.setC_isscan(request.getParameter("cIsscan"));
		actNode.setC_starttime_exec(request.getParameter("cStarttimeExec"));
		actNode.setC_endtime_exec(request.getParameter("cEndtimeExec"));
		actNode.setC_records_exec(request.getParameter("cRecordsExec"));
		actNode.setC_std_exec(request.getParameter("cStdExec"));
		actNode.setC_managestd(request.getParameter("cManagestd"));
		actNode.setC_examstd(request.getParameter("cExamstd"));
		actNode.setC_err_exec(request.getParameter("cErrExec"));
		actNode.setC_position_name_check(request.getParameter("cPositionNameCheck"));
		actNode.setC_position_id_check(request.getParameter("cPositionIdCheck"));
		actNode.setC_timelimit_check(request.getParameter("cTimelimitCheck"));
		actNode.setC_records_check(request.getParameter("cRecordsCheck"));
		actNode.setC_std_check(request.getParameter("cStdCheck"));
		actNode.setC_err_check(request.getParameter("cErrCheck"));
		actNode.setC_position_name_review(request.getParameter("cPositionNameReview"));
		actNode.setC_position_id_review(request.getParameter("cPositionIdReview"));
		actNode.setC_timelimit_review(request.getParameter("cTimelimitReview"));
		actNode.setC_std_review(request.getParameter("cStdReview"));
		actNode.setC_position_name_feedback1(request.getParameter("cPositionNameFeedback1"));
		actNode.setC_position_id_feedback1(request.getParameter("cPositionIdFeedback1"));
		actNode.setC_position_name_feedback2(request.getParameter("cPositionNameFeedback2"));
		actNode.setC_position_id_feedback2(request.getParameter("cPositionIdFeedback2"));
		actNode.setC_position_name_err1(request.getParameter("cPositionNameErr1"));
		actNode.setC_position_id_err1(request.getParameter("cPositionIdErr1"));
		actNode.setC_position_name_err2(request.getParameter("cPositionNameErr2"));
		actNode.setC_position_id_err2(request.getParameter("cPositionIdErr2"));
		actNode.setC_istimerule("1");

		List actnodelist = standardLibraryService.queryActnodeListByParams(actNode);
		Map<String, String> pageData = new HashMap<String, String>();
		//0-查看，1-修改，2-新建
		switch (cModifyType) {
			case 1:
				if (actnodelist.size() > 1) {
					pageData.put("msg", "修改失败，标准已存在\n请查看（岗位活动名称、时态）是否重复");
					pageData.put("status", "ng");
				} else {
					actNode.setC_actnode_id(request.getParameter("cActnodeId"));
					actNode.setC_modifier(user.get("id").toString());
					standardLibraryService.updateActNodeById(actNode, request, response);
					pageData.put("msg", "修改成功");
					pageData.put("status", "ok");
					pageData.put("id", actNode.getC_actnode_id());
				}
				break;
			case 2:
				if (actnodelist.size() > 0) {
					pageData.put("msg", "添加失败，标准已经存在\n请查看（岗位活动名称、时态）是否重复");
					pageData.put("status", "ng");
				} else {
					actNode.setC_actnode_id(UUID.randomUUID().toString());
					actNode.setC_creator(user.get("id").toString());
					standardLibraryService.addActnode(actNode, request, response);
					pageData.put("msg", "添加成功");
					pageData.put("status", "ok");
					pageData.put("id", actNode.getC_actnode_id());
				}
				break;
			default:
				break;
		}
		this.formatData(pageData);
		return SUCCESS;
	}

	public String addActNodeItemByEdit() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		Session session = SessionFactory.getInstance(request, response);
		Map user = (Map) session.getAttribute("USERSESSION");

		Actnode actNode = new Actnode();
		actNode.setC_actitem_media_file_id(request.getParameter("cActitemMediaFileId"));
		actNode.setC_actitem_name(request.getParameter("cActitemName"));
		actNode.setC_actitem_code(request.getParameter("cActitemCode"));
		actNode.setC_getdata_pretext(request.getParameter("cGetdataPretext"));
		actNode.setC_getdata_unit(request.getParameter("cGetdataUnit"));
		actNode.setC_getdatatype(request.getParameter("cGetdatatype"));
		actNode.setC_getdata_text(request.getParameter("cGetdataText"));
		actNode.setC_checkdata_pretext(request.getParameter("cCheckdataPretext"));
		actNode.setC_checkdata_unit(request.getParameter("cCheckdataUnit"));
		actNode.setC_checkdatatype(request.getParameter("cCheckdatatype"));
		actNode.setC_checkdata_text(request.getParameter("cCheckdataText"));
		actNode.setC_actitem_std(request.getParameter("cActitemStd"));
		actNode.setC_actitem_std_check(request.getParameter("cActitemStdCheck"));
		actNode.setC_remark(request.getParameter("cRemark"));
		actNode.setC_creator(user.get("id").toString());
		String index = request.getParameter("index");
		actNode.setC_actitem_index(request.getParameter("index"));
		actNode.setC_groupindex(request.getParameter("groupIndex"));
		String length = request.getParameter("length");
		String id = request.getParameter("cActnodeId");
		actNode.setC_actnode_id(id);
		actNode.setC_flag(Integer.toString(1));

		if (index.equals("1")) {
			standardLibraryService.delActnodeItemByActNodeId(id);
		}
		if (!length.equals("0")) {
			actNode.setC_actitem_id(UUID.randomUUID().toString());
			standardLibraryService.addActnodeItem(actNode, request, response);
		}

		return SUCCESS;
	}

	public String addActNodeByCopy() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		Session session = SessionFactory.getInstance(request, response);

		String pids[] = request.getParameter("pids").split("%2C");
		String ids[] = request.getParameter("ids").split("%2C");
		Map user = (Map) session.getAttribute("USERSESSION");
		int failures = 0;
		Map addMediaFile_Ret = new HashMap();
		int deleteMediaStdById_Type;
		List<TSdMediastdPojo> r = new ArrayList<TSdMediastdPojo>();
		String r_datafield = "";
		for (int i = 0; i < ids.length; i++) {
			TSdActnodePojo tSdActnode = tSdActnodeService.getActNodeByID(ids[i]);
			List<TSdActnodeItemPojo> tSdActnodeItems = tSdActnodeItemService.getActnodeItemsByActnodeID(ids[i]);
			for (int j = 0; j < pids.length; j++) {
				Actnode actNode = new Actnode();
				actNode.setC_actnode_name(tSdActnode.getcActnodeName());
				actNode.setC_position_id(pids[j]);
				actNode.setC_frequency(tSdActnode.getcFrequency());
				List actnodelist = standardLibraryService.queryActnodeListByParams(actNode);
				if (actnodelist.size() > 0) {
					failures++;
				} else {
					Map params = new HashMap();
					params.put("type", 1);
					params.put("cid", tSdActnode.getcActnodeId());
					List<TSdMediastdPojo> l = tSdMediastdDal.getAllMediaStdById(params);
					String cMediaFileId = "";
					for (TSdMediastdPojo tsd : l) {
						cMediaFileId += (tsd.getcSdfileId() + ",");
					}
					if (!cMediaFileId.equals("")) {
						cMediaFileId = cMediaFileId.substring(0, cMediaFileId.length() - 1);
					}
					TSdActnodePojo t = tSdActnode;
					String cActnodeId = UUID.randomUUID().toString();
					t.setcActnodeId(cActnodeId);
					t.setcPositionId(pids[j]);
					t.setcPositionName(dpPositionService.getPositionNameByID(pids[j]));
					t.setcCreator(user.get("id").toString());
					tSdActnodeService.addActnode(t, request, response);
					addMediaFile_Ret = tSdMediastdDal.addMediaFile(cMediaFileId, 1, t.getcCreator(), t.getcActnodeId());
					deleteMediaStdById_Type = (Integer)addMediaFile_Ret.get("deleteMediaStdByIdRet");
					if(deleteMediaStdById_Type != 0){
						List<TSdMediastdPojo> searchDeleteMediaStdByIdResultMap =(List<TSdMediastdPojo>) addMediaFile_Ret.get("searchDeleteMediaStdByIdResultMap");
						String searchDeleteMediaStdByIdResultMap_datafield = (String)addMediaFile_Ret.get("searchDeleteMediaStdByIdResultMap_datafield");
						logService.addLogInfo(request, response, "1", "过程行为支持系统", "", "标准管理", "5W2H标准管理", MyLogPojo.del, "5W2H标准制定-标准复制", "成功", "1", searchDeleteMediaStdByIdResultMap_datafield, searchDeleteMediaStdByIdResultMap.toString(), "");
					}
					r = (List<TSdMediastdPojo>)addMediaFile_Ret.get("r");
					r_datafield = (String)addMediaFile_Ret.get("r_datafield");
					if(r != null && r_datafield != null){
						logService.addLogInfo(request, response, "1", "过程行为支持系统", "", "标准管理", "5W2H标准管理", MyLogPojo.add, "5W2H标准制定-标准复制", "成功", "1", r_datafield, "", r.toString());
					}
					
					for (int k = 0; k < tSdActnodeItems.size(); k++) {
						params.put("type", 3);
						params.put("cid", tSdActnodeItems.get(k).getcActitemId());
						List<TSdMediastdPojo> li = tSdMediastdDal.getAllMediaStdById(params);
						String cActitemMediaFileId = "";
						for (TSdMediastdPojo tsd : li) {
							cActitemMediaFileId += (tsd.getcSdfileId() + ",");
						}
						if (!cActitemMediaFileId.equals("")) {
							cActitemMediaFileId = cActitemMediaFileId.substring(0, cActitemMediaFileId.length() - 1);
						}
						TSdActnodeItemPojo ti = tSdActnodeItems.get(k);
						ti.setcActitemId(UUID.randomUUID().toString());
						ti.setcActnodeId(cActnodeId);
						tSdActnodeItemService.addActnodeItem(ti, request, response);
						addMediaFile_Ret = tSdMediastdDal.addMediaFile(cActitemMediaFileId, 3, t.getcCreator(), ti.getcActitemId());
						deleteMediaStdById_Type = (Integer)addMediaFile_Ret.get("deleteMediaStdByIdRet");
						if(deleteMediaStdById_Type != 0){
							List<TSdMediastdPojo> searchDeleteMediaStdByIdResultMap =(List<TSdMediastdPojo>) addMediaFile_Ret.get("searchDeleteMediaStdByIdResultMap");
							String searchDeleteMediaStdByIdResultMap_datafield = (String)addMediaFile_Ret.get("searchDeleteMediaStdByIdResultMap_datafield");
							logService.addLogInfo(request, response, "1", "过程行为支持系统", "", "标准管理", "5W2H标准管理", MyLogPojo.del, "5W2H标准制定-标准复制", "成功", "1", searchDeleteMediaStdByIdResultMap_datafield, searchDeleteMediaStdByIdResultMap.toString(), "");
						}
						r = (List<TSdMediastdPojo>)addMediaFile_Ret.get("r");
						r_datafield = (String)addMediaFile_Ret.get("r_datafield");
						if(r != null && r_datafield != null){
							logService.addLogInfo(request, response, "1", "过程行为支持系统", "", "标准管理", "5W2H标准管理", MyLogPojo.add, "5W2H标准制定-标准复制", "成功", "1", r_datafield, "", r.toString());
						}
					}
				}
			}
		}

		Map<String, String> pageData = new HashMap<String, String>();
		String msg = "复制完成<br/>成功：" + ((pids.length * ids.length) - failures) + "条<br/>失败：" + failures + "条<br/>下一步请进行任务模板的配置";
		if (0 < failures) {
			msg += "<br/>注：失败原因——标准重复";
		}
		pageData.put("msg", msg);
		pageData.put("status", "ok");
		this.formatData(pageData);
		return SUCCESS;
	}

	/**
	 * 方法用途：5w2h标准汇总作业类导出Excel action
	 * 参数：
	 * 返回类型：String
	 * 编写时间：2015年7月22日上午10:51:41
	 * 编写人：caoyong
	 **/
	public String exportOperation() {
		actnode.setC_actnodetype("1"); //作业类标识
		List<TSdActnodePojo> ls = tSdActnodeService.getAllActNodesByConditionsWithoutPage(actnode);
		for (int i = 0; i < ls.size(); i++) {
			TSdActnodePojo tsap = ls.get(i);
			String PositionId = tsap.getcPositionId();
			if (PositionId != null && !PositionId.equals("") && !PositionId.equals("null")) {
				String path = tSdActnodeService.getDepartmentPath(PositionId); //部门和班组id字符
				if (path != null && path.contains(";")) {
					String[] ss = path.split(";");
					int y = ss.length;
					if (y == 4) {
						String departName = tSdActnodeService.getDepartmentName(ss[1]);
						tsap.setDepartmentName(departName);
						String workTeamName = tSdActnodeService.getDepartmentName(ss[3]);
						tsap.setWorkTeamName(workTeamName);
					} else if (y == 3) {
						String departName = tSdActnodeService.getDepartmentName(ss[1]);
						tsap.setDepartmentName(departName);
						String workTeamName = tSdActnodeService.getDepartmentName(ss[2]);
						tsap.setWorkTeamName(workTeamName);
					} else if (y == 2) {
						if (ss[0].equals("1000509")) { //1000509代表长沙卷烟厂
							String departName = tSdActnodeService.getDepartmentName(ss[1]);
							tsap.setDepartmentName(departName);
						} else {
							String departName = tSdActnodeService.getDepartmentName(ss[0]);
							tsap.setDepartmentName(departName);
							String workTeamName = tSdActnodeService.getDepartmentName(ss[1]);
							tsap.setWorkTeamName(workTeamName);
						}
					}
				}
			}
		}
		String[] titles = {"部门", "班组", "岗位活动名称", "PDCA属性", "是否为关键活动", "时态", "作业区域", "执行岗位", "是否按顺序执行", "是否为随机任务", "是否扫码",
			"执行标准", "异常怎么处置(执行环节)", "规范性记录(执行环节)", "执行开始时间", "执行完成时间", "验证岗位", "验证标准(如何核查)", "异常怎么处置(验证环节)", "规范性记录(验证环节)",
			"验证完成时间", "评价岗位", "评价标准", "评价完成时间", "默认反馈岗位", "其他默认反馈岗位", "异常反馈岗位", "其他异常反馈岗位"};
		String[] fields = {"departmentName", "workTeamName", "cActnodeName", "cPdca", "cIskeyctrlname", "cFrequency", "cAreaName", "cPositionName", "cIssequenceName",
			"cIsrandomName", "cIsscanName", "cStdExec", "cErrExec", "cRecordsExec", "cStarttimeExecName", "cEndtimeExecName",
			"cPositionNameCheck", "cStdCheck", "cErrCheck", "cRecordsCheck", "cTimelimitCheckName", "cPositionNameReview",
			"cStdReview", "cTimelimitReviewName", "cPositionNameFeedback1", "cPositionNameFeedback2", "cPositionNameErr1", "cPositionNameErr2"};
		String url = this.exportExcelData(titles, fields, "zuoyelei",  ls, "5w2h标准汇总作业类导出");
		Map map = new HashMap();
		map.put("url", url);
		this.formatData(map);
		return SUCCESS;
	}

	/**
	 * 方法用途：5w2h标准汇总管理类导出Excel action
	 * 参数：
	 * 返回类型：String
	 * 编写时间：2015年7月22日下午5:29:20
	 * 编写人：caoyong
	 **/
	public String exportManage() {
		actnode.setC_actnodetype("2"); //管理类标识
		List<TSdActnodePojo> ls = tSdActnodeService.getAllActNodesByConditionsWithoutPage(actnode);
		for (int i = 0; i < ls.size(); i++) {
			TSdActnodePojo tsap = ls.get(i);
			String PositionId = tsap.getcPositionId();
			if (PositionId != null && !PositionId.equals("") && !PositionId.equals("null")) {
				String path = tSdActnodeService.getDepartmentPath(PositionId); //部门和班组id字符
				if (path != null && path.contains(";")) {
					String[] ss = path.split(";");
					int y = ss.length;
					if(y==4){
						String departName = tSdActnodeService.getDepartmentName(ss[1]);
						tsap.setDepartmentName(departName);
						String workTeamName = tSdActnodeService.getDepartmentName(ss[3]);
						tsap.setWorkTeamName(workTeamName);
					}
					else if (y == 3) {
						String departName = tSdActnodeService.getDepartmentName(ss[1]);
						tsap.setDepartmentName(departName);
						String workTeamName = tSdActnodeService.getDepartmentName(ss[2]);
						tsap.setWorkTeamName(workTeamName);
					} else if (y == 2) {
						if (ss[0].equals("1000509")) { //1000509代表长沙卷烟厂
							String departName = tSdActnodeService.getDepartmentName(ss[1]);
							tsap.setDepartmentName(departName);
						} else {
							String departName = tSdActnodeService.getDepartmentName(ss[0]);
							tsap.setDepartmentName(departName);
							String workTeamName = tSdActnodeService.getDepartmentName(ss[1]);
							tsap.setWorkTeamName(workTeamName);
						}
					}
				}
			}
		}
		String[] titles = {"部门", "班组", "岗位活动名称", "管理属性", "PDCA属性", "是否为关键活动", "时态", "工作区域", "管理岗位", "是否按顺序执行", "是否为随机任务", "是否扫码",
			"管理要求", "管理/技术标准", "考核标准", "异常怎么处置", "规范性记录", "完工开始时间", "完工完成时间", "验证岗位", "验证标准（如何复核）",
			"验证完成时间", "监督岗位", "监督完成时间", "默认反馈岗位", "其他默认反馈岗位", "异常反馈岗位", "其他异常反馈岗位"};
		String[] fields = {"departmentName", "workTeamName", "cActnodeName", "cManageattr", "cPdca", "cIskeyctrlname", "cFrequency", "cAreaName", "cPositionName", "cIssequenceName",
			"cIsrandomName", "cIsscanName", "cStdExec", "cManagestd", "cExamstd", "cErrExec", "cRecordsExec",
			"cEndtimeExecName", "cEndtimeExecName", "cPositionNameCheck", "cStdCheck", "cTimelimitCheckName", "cPositionNameReview",
			"cTimelimitReviewName", "cPositionNameFeedback1", "cPositionNameFeedback2", "cPositionNameErr1", "cPositionNameErr1", "cPositionNameErr2"};
		String url = this.exportExcelData(titles, fields, "guanglilei",  ls, "5w2h标准汇总管理类导出");
		Map map = new HashMap();
		map.put("url", url);
		this.formatData(map);
		return SUCCESS;
	}

	public String selectPathNameByID() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String departId = request.getParameter("departId");
		this.formatData(tSdActnodeService.selectPathNameByID(departId));
		return SUCCESS;
	}
}