package com.talkweb.xwzcxt.action;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.twdpe.dal.jdbc.info.Pagination;
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
import com.talkweb.xwzcxt.service.STempletServiceI;
import com.talkweb.xwzcxt.service.StandardLibraryService;
import com.talkweb.xwzcxt.service.TSdActnodeItemServiceI;
import com.talkweb.xwzcxt.service.TSdActnodeServiceI;
import com.talkweb.xwzcxt.vo.STempletVo;
import com.talkweb.xwzcxt.vo.TStdtaskplanSeriesVo;

@SuppressWarnings({ "unchecked", "rawtypes", "serial" })
public class TaskTempletAction extends baseAction {
	@Autowired
	private STempletServiceI sTempletService;
	
	@Autowired
	private StandardLibraryService standardLibraryService;

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
	
	public String getAllTaskTemplet() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String pid = request.getParameter("cPositionID");
		Map params = new HashMap();
		params.put("cTempletName", request.getParameter("cTempletName"));
		params.put("cIsscan", request.getParameter("cIsscan"));
		params.put("cExecAreaid", request.getParameter("cExecAreaid"));
		params.put("cPidCheck", request.getParameter("cPidCheck"));
		params.put("cPidReview", request.getParameter("cPidReview"));
		params.put("cPidExec", pid);
		if (pagination == null) {
			pagination = new Pagination(1, 20);
		}
		Pagination page = null;
		if (pid != null && !pid.isEmpty()) {
			page = sTempletService.getAllTaskTemplet(params, pagination);
			this.formatDatagirdData(page.getList(), page);
		}
		return SUCCESS;
	}

	public String getAllTaskTempletForSearch() {
		HttpServletRequest request = ServletActionContext.getRequest();
		Map params = new HashMap();
		params.put("cTempletName", request.getParameter("cTempletName"));
		params.put("cIsscan", request.getParameter("cIsscan"));
		params.put("cExecAreaid", request.getParameter("cExecAreaid"));
		params.put("cPidCheck", request.getParameter("cPidCheck"));
		params.put("cPidReview", request.getParameter("cPidReview"));
		params.put("cPidExec", request.getParameter("cPidExec"));
		params.put("cPidErrFeedback1", request.getParameter("cPidErrFeedback1"));
		if (pagination == null) {
			pagination = new Pagination(1, 20);
		}
		Pagination page = sTempletService.getAllTaskTemplet(params, pagination);
		this.formatDatagirdData(page.getList(), page);
		return SUCCESS;
	}

	public String addTaskTemplet() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();

		//0-查看，1-修改，2-评审，3-新建
		int cModifyType = Integer.parseInt(request.getParameter("cModifyType"));

		Map params = new HashMap();
		params.put("cActnodeId", request.getParameter("cActnodeId"));
		params.put("cIskeyctrl", request.getParameter("cIskeyctrl"));
		params.put("cTempletName", request.getParameter("cTempletName"));
		params.put("cTempletCode", request.getParameter("cTempletCode"));
		params.put("cIsscan", request.getParameter("cIsscan"));
		params.put("cJobObjects", request.getParameter("cJobObjects"));
		params.put("cPidExec", request.getParameter("cPidExec"));
		params.put("cDepartment", request.getParameter("cDepartment"));
		params.put("cExecAreaid", request.getParameter("cExecAreaid"));
		params.put("cPidCheck", request.getParameter("cPidCheck"));
		params.put("cPidReview", request.getParameter("cPidReview"));
		params.put("cPidFeedback1", request.getParameter("cPidFeedback1"));
		params.put("cPidFeedback2", request.getParameter("cPidFeedback2"));
		params.put("cPidErrFeedback1", request.getParameter("cPidErrFeedback1"));
		params.put("cPidErrFeedback2", request.getParameter("cPidErrFeedback2"));
		params.put("cTaskbeginTrid", request.getParameter("cTaskbeginTrid"));
		params.put("cTaskfinishTrid", request.getParameter("cTaskfinishTrid"));
		params.put("cTaskcheckTime", request.getParameter("cTaskcheckTime"));
		params.put("cTaskreviewTime", request.getParameter("cTaskreviewTime"));
		params.put("cTempletMediaFileId", request.getParameter("cTempletMediaFileId"));

		Session session = SessionFactory.getInstance(request, response);
		Map user = (Map) session.getAttribute("USERSESSION");

		int n;
		//0-查看，1-修改，2-评审，3-新建
		switch (cModifyType) {
			case 1:
				params.put("cTempletId", request.getParameter("cTempletId"));
				params.put("cModifier", user.get("id"));
				params.put("cModifytime", new Date());
				params.put("cConfirmstatus", new BigDecimal(0));
				params.put("cFlag", new BigDecimal(1));
				params.put("cAnalysisStatus", new BigDecimal(0));// 初始化为0
				params.put("cAnalysisCnt", new BigDecimal(0));// 初始化为0

				n = sTempletService.updateTempletById(params, request, response);
				this.setMessage(n, "Templet", "Modify");
				break;
			case 2:
				params.put("cTempletId", request.getParameter("cTempletId"));
				params.put("cConfirmor", user.get("id"));
				params.put("cConfirmtime", new Date());
				params.put("cConfirmstatus", new BigDecimal(1));
				params.put("cFlag", new BigDecimal(1));
				params.put("cAnalysisStatus", new BigDecimal(0));// 初始化为0
				params.put("cAnalysisCnt", new BigDecimal(0));// 初始化为0

				n = sTempletService.updateTempletById(params, request, response);
				this.setMessage(n, "Templet", "Confirm");

				TSdActnodePojo tSdActnode = tSdActnodeService.getActNodeByID(request.getParameter("cActnodeId").toString());
				// 仅回写非通用标准
				if (tSdActnode.getcIspublic().intValue() == 0) {
					// 评审后更新岗位活动信息
					Actnode actNode = new Actnode();
					actNode.setC_actnode_id(request.getParameter("cActnodeId"));
					actNode.setC_modifier(user.get("id").toString());
					actNode.setC_area_id(request.getParameter("cExecAreaid"));
					actNode.setC_area_name(request.getParameter("cExecAreaName"));
					actNode.setC_position_id(request.getParameter("cPidExec"));
					actNode.setC_position_name(request.getParameter("cPNameExec"));
					actNode.setC_starttime_exec(request.getParameter("cTaskbeginTrid"));
					actNode.setC_endtime_exec(request.getParameter("cTaskfinishTrid"));
					actNode.setC_position_id_check(request.getParameter("cPidCheck"));
					actNode.setC_position_name_check(request.getParameter("cPNameCheck"));
					actNode.setC_timelimit_check(request.getParameter("cTaskcheckTime"));
					actNode.setC_position_id_review(request.getParameter("cPidReview"));
					actNode.setC_position_name_review(request.getParameter("cPNameReview"));
					actNode.setC_timelimit_review(request.getParameter("cTaskreviewTime"));
					actNode.setC_position_id_feedback1(request.getParameter("cPidFeedback1"));
					actNode.setC_position_name_feedback1(request.getParameter("cPNameFeedback1"));
					actNode.setC_position_id_feedback2(request.getParameter("cPidFeedback2"));
					actNode.setC_position_name_feedback2(request.getParameter("cPNameFeedback2"));
					actNode.setC_position_id_err1(request.getParameter("cPidErrFeedback1"));
					actNode.setC_position_name_err1(request.getParameter("cPNameErrFeedback1"));
					actNode.setC_position_id_err2(request.getParameter("cPidErrFeedback2"));
					actNode.setC_position_name_err2(request.getParameter("cPNameErrFeedback2"));
					actNode.setC_istimerule("1");
					actNode.setC_department(request.getParameter("cDepartment"));
	
					n = standardLibraryService.updateActNodeById(actNode, request, response);
					this.setMessage(n, "ActNode", "Confirm");
				}
				break;
			case 3:
				params.put("cTempletId", UUID.randomUUID().toString());
				params.put("cCreator", user.get("id"));
				params.put("cCreatetime", new Date());
				params.put("cFlag", new BigDecimal(1));
				params.put("cAnalysisStatus", new BigDecimal(0));// 初始化为0
				params.put("cAnalysisCnt", new BigDecimal(0));// 初始化为0
				params.put("cTemplettype", new BigDecimal(0));// 初始化为0
				params.put("cIsvaild", "1");// 初始化为1

				n = sTempletService.addTaskTemplet(params, request, response);
				this.setMessage(n, "Templet", "Add");
				break;
			default:
				break;
		}

		return SUCCESS;
	}

	public String deleteTempletById() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String ids = request.getParameter("id");
		try {
			String[] trids = ids.split(",");
			for (int i = 0; i < trids.length; i++) {
				sTempletService.deleteTaskTempletById(trids[i], request, response);
			}
			this.setMessage(1, "Templet", "DELETE");
		} catch (Exception e) {
			this.setMessage(0, "Templet", "DELETE");
		}
		return SUCCESS;
	}

	public String addTempletByCopy() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		Session session = SessionFactory.getInstance(request, response);

		String pids[] = request.getParameter("pids").split("%2C");
		String tids[] = request.getParameter("tids").split("%2C");
		String aids[] = request.getParameter("aids").split("%2C");
		Map user = (Map) session.getAttribute("USERSESSION");
		Map addMediaFileRet = null;
		String searchDeleteMediaStdByIdResultMap_datafield = "";
		int deleteMediaStdByIdRet;
		List<TSdMediastdPojo> searchDeleteMediaStdByIdResultMap = null;
		for (int i = 0; i < tids.length; i++) {
			STempletVo sTempletVo = sTempletService.getTaskTempletById(tids[i]);
			TSdActnodePojo tSdActnode = tSdActnodeService.getActNodeByID(aids[i]);
			List<TSdActnodeItemPojo> tSdActnodeItems = tSdActnodeItemService.getActnodeItemsByActnodeID(aids[i]);
			for (int j = 0; j < pids.length; j++) {
				Map params = new HashMap();
				params.put("type", 2);
				params.put("cid", sTempletVo.getcTempletId());
				List<TSdMediastdPojo> lt = tSdMediastdDal.getAllMediaStdById(params);
				String cTempletMediaFileId = "";
				for (TSdMediastdPojo tsd : lt) {
					cTempletMediaFileId += (tsd.getcSdfileId() + ",");
				}
				if (!cTempletMediaFileId.equals("")) {
					cTempletMediaFileId = cTempletMediaFileId.substring(0, cTempletMediaFileId.length() - 1);
				}
				STempletVo v = sTempletVo;
				v.setcTempletId(UUID.randomUUID().toString());
				v.setcPidExec(pids[j]);
				v.setcCreator(user.get("id").toString());
				v.setcCreatetime(new Date());

				Actnode actNode = new Actnode();
				actNode.setC_actnode_name(tSdActnode.getcActnodeName());
				actNode.setC_position_id(pids[j]);
				actNode.setC_frequency(tSdActnode.getcFrequency());
				List actnodelist = standardLibraryService.queryActnodeListByParams(actNode);
				if (actnodelist.size() > 0) {
					Actnode a = (Actnode) actnodelist.get(0);
					v.setcActnodeId(a.getC_actnode_id());
				} else {
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
					
					addMediaFileRet = tSdMediastdDal.addMediaFile(cMediaFileId, 1, t.getcCreator(), t.getcActnodeId());
					deleteMediaStdByIdRet = (Integer)addMediaFileRet.get("deleteMediaStdByIdRet");
					if(deleteMediaStdByIdRet != 0){
						searchDeleteMediaStdByIdResultMap = (List<TSdMediastdPojo>) addMediaFileRet.get("searchDeleteMediaStdByIdResultMap");
						searchDeleteMediaStdByIdResultMap_datafield = (String)addMediaFileRet.get("searchDeleteMediaStdByIdResultMap_datafield");
						logService.addLogInfo(request, response, "1", "运维系统平台", "", "任务管理", "任务模板制定", MyLogPojo.add, "任务模板制定-模板复制", "成功", "1", searchDeleteMediaStdByIdResultMap_datafield, "", searchDeleteMediaStdByIdResultMap.toString());
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
						addMediaFileRet = tSdMediastdDal.addMediaFile(cActitemMediaFileId, 3, t.getcCreator(), ti.getcActitemId());
						deleteMediaStdByIdRet = (Integer)addMediaFileRet.get("deleteMediaStdByIdRet");
						if(deleteMediaStdByIdRet != 0){
							searchDeleteMediaStdByIdResultMap = (List<TSdMediastdPojo>) addMediaFileRet.get("searchDeleteMediaStdByIdResultMap");
							searchDeleteMediaStdByIdResultMap_datafield = (String)addMediaFileRet.get("searchDeleteMediaStdByIdResultMap_datafield");
							logService.addLogInfo(request, response, "1", "运维系统平台", "", "任务管理", "任务模板制定", MyLogPojo.add, "任务模板制定-模板复制", "成功", "1", searchDeleteMediaStdByIdResultMap_datafield, "", searchDeleteMediaStdByIdResultMap.toString());
						}
					}
					v.setcActnodeId(cActnodeId);
				}

				int n = sTempletService.addTempletByCopy(v, request, response);
				addMediaFileRet = tSdMediastdDal.addMediaFile(cTempletMediaFileId, 2, v.getcCreator(), v.getcTempletId());
				deleteMediaStdByIdRet = (Integer)addMediaFileRet.get("deleteMediaStdByIdRet");
				if(deleteMediaStdByIdRet != 0){
					searchDeleteMediaStdByIdResultMap = (List<TSdMediastdPojo>) addMediaFileRet.get("searchDeleteMediaStdByIdResultMap");
					searchDeleteMediaStdByIdResultMap_datafield = (String)addMediaFileRet.get("searchDeleteMediaStdByIdResultMap_datafield");
					logService.addLogInfo(request, response, "1", "运维系统平台", "", "任务管理", "任务模板制定", MyLogPojo.add, "任务模板制定-模板复制", "成功", "1", searchDeleteMediaStdByIdResultMap_datafield, "", searchDeleteMediaStdByIdResultMap.toString());
				}
				this.setMessage(n, "RuleTime", "Add");
			}
		}

		Map<String, String> pageData = new HashMap<String, String>();
		pageData.put("msg", "复制成功");
		pageData.put("status", "ok");
		this.formatData(pageData);
		return SUCCESS;
	}

	public String getSTempletDetailByID() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String cTempletId = request.getParameter("cTempletId");
		if (cTempletId != null && !cTempletId.isEmpty()) {
			STempletVo p = sTempletService.getTaskTempletById(cTempletId);
			try {
				if (p != null) {
					this.formatData(p);
				} else {
					return ERROR;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return SUCCESS;
	}

	public String setSTempletVaildByID() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		Map params = new HashMap();
		params.put("cTempletId", request.getParameter("cTempletId"));
		params.put("cIsvaild", request.getParameter("cIsvaild"));
		int n = sTempletService.setSTempletVaildByID(params, request, response );
		this.setMessage(n, "Templet", "Modify");
		return SUCCESS;
	}

	public String generateRandomTask() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		Session session = SessionFactory.getInstance(request, response);
		Map user = (Map) session.getAttribute("USERSESSION");

		Map params = new HashMap();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		params.put("cActnodeId", request.getParameter("cActnodeId"));
		params.put("cActnodeName", request.getParameter("cActnodeName"));
		params.put("cActnodetype", request.getParameter("cActnodetype"));
		params.put("cIskeyctrl", request.getParameter("cIskeyctrl"));
		params.put("cPdca", request.getParameter("cPdca"));
		params.put("cIssequence", request.getParameter("cIssequence"));
		params.put("cTaskName", request.getParameter("cTaskName") + df.format(new Date()));
		params.put("cTaskCode", request.getParameter("cTaskCode"));
		params.put("cIsscan", request.getParameter("cIsscan"));
		params.put("cJobObjects", request.getParameter("cJobObjects"));
		String cPNameExec = request.getParameter("cPNameExec");
		params.put("cPNameExec", cPNameExec.substring(cPNameExec.indexOf("-") + 1));
		params.put("cPidExec", new BigDecimal(request.getParameter("cPidExec")));
		params.put("cFrequency", request.getParameter("cFrequency"));
		params.put("cExecAreaName", request.getParameter("cExecAreaName"));
		params.put("cExecAreaid", new BigDecimal(request.getParameter("cExecAreaid")));
		String cPNameCheck = request.getParameter("cPNameCheck");
		params.put("cPNameCheck", cPNameCheck.substring(cPNameCheck.indexOf("-") + 1));
		params.put("cPidCheck", request.getParameter("cPidCheck"));
		String cPNameReview = request.getParameter("cPNameReview");
		params.put("cPNameReview", cPNameReview.substring(cPNameReview.indexOf("-") + 1));
		params.put("cPidReview", request.getParameter("cPidReview"));
		String cPNameFeedback1 = request.getParameter("cPNameFeedback1");
		params.put("cPNameFeedback1", cPNameFeedback1.substring(cPNameFeedback1.indexOf("-") + 1));
		params.put("cPidFeedback1", request.getParameter("cPidFeedback1"));
		String cPNameFeedback2 = request.getParameter("cPNameFeedback2");
		params.put("cPNameFeedback2", cPNameFeedback2.substring(cPNameFeedback2.indexOf("-") + 1));
		params.put("cPidFeedback2", request.getParameter("cPidFeedback2"));
		String cPNameErrFeedback1 = request.getParameter("cPNameErrFeedback1");
		params.put("cPNameErrFeedback1", cPNameErrFeedback1.substring(cPNameErrFeedback1.indexOf("-") + 1));
		params.put("cPidErrFeedback1", request.getParameter("cPidErrFeedback1"));
		String cPNameErrFeedback2 = request.getParameter("cPNameErrFeedback2");
		params.put("cPNameErrFeedback2", cPNameErrFeedback2.substring(cPNameErrFeedback2.indexOf("-") + 1));
		params.put("cPidErrFeedback2", request.getParameter("cPidErrFeedback2"));
		params.put("cTaskbeginTrid", request.getParameter("cTaskbeginTrid"));
		params.put("cTaskfinishTrid", request.getParameter("cTaskfinishTrid"));

		String cFrequency = request.getParameter("cFrequency");
		SimpleDateFormat dfa = new SimpleDateFormat("yyyy/MM/dd");
		Calendar a = Calendar.getInstance();
		a.add(Calendar.MONTH, 1);
		if (cFrequency.equals("每月")) {
			a.set(Calendar.DAY_OF_MONTH, 20);
			params.put("cTaskcheckTime", dfa.format(a.getTime()));
			a.set(Calendar.DAY_OF_MONTH, 28);
			params.put("cTaskreviewTime", dfa.format(a.getTime()));
		} else if (cFrequency.equals("每周")) {
			a.set(Calendar.DAY_OF_MONTH, 15);
			params.put("cTaskcheckTime", dfa.format(a.getTime()));
			a.set(Calendar.DAY_OF_MONTH, 20);
			params.put("cTaskreviewTime", dfa.format(a.getTime()));
		} else {
			a.set(Calendar.DAY_OF_MONTH, 10);
			params.put("cTaskcheckTime", dfa.format(a.getTime()));
			a.set(Calendar.DAY_OF_MONTH, 15);
			params.put("cTaskreviewTime", dfa.format(a.getTime()));
		}
		params.put("cUserId", new BigDecimal(user.get("id").toString()));
		params.put("cUserName", user.get("name"));

		int n = sTempletService.generateRandomTask(params, request, response);
		this.setMessage(n, "Task", "CREATE");

		return SUCCESS;
	}

	public String getAllPlanSeries() {
		HttpServletRequest request = ServletActionContext.getRequest();

		String pid = request.getParameter("cPositionID");
		Map params = new HashMap();
		params.put("templetname", request.getParameter("cTasktempletName"));
		params.put("positionid", pid);
		if (pagination == null) {
			pagination = new Pagination(1, 20);
		}
		Pagination page = null;

		if (pid != null && !pid.isEmpty()) {
			page = sTempletService.getAllPlanSeries(params, pagination);
			this.formatDatagirdData(page.getList(), page);
		}
		return SUCCESS;
	}

	public String getPlanSeriesDetailByID() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String cTsId = request.getParameter("cTsId");
		if (cTsId != null && !cTsId.isEmpty()) {
			TStdtaskplanSeriesVo p = sTempletService.getPlanSeriesDetailByID(cTsId);
			try {
				if (p != null) {
					this.formatData(p);
				} else {
					return ERROR;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return SUCCESS;
	}

	public String deletePlanSeriesById() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String ids = request.getParameter("id");
		try {
			String[] trids = ids.split(",");
			for (int i = 0; i < trids.length; i++) {
				sTempletService.deletePlanSeriesById(trids[i]);
			}
			this.setMessage(1, "PlanSeries", "DELETE");
		} catch (Exception e) {
			this.setMessage(0, "PlanSeries", "DELETE");
		}
		return SUCCESS;
	}
}