package com.talkweb.twdpe.webcomponent.component;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.twdpe.base.common.CommonConfig;
import com.talkweb.twdpe.base.tasks.pojo.CronTriggerInfo;
import com.talkweb.twdpe.base.tasks.pojo.JobGroupInfo;
import com.talkweb.twdpe.base.tasks.pojo.JobInfo;
import com.talkweb.twdpe.base.tasks.pojo.SimpleTriggerInfo;
import com.talkweb.twdpe.base.tasks.service.SchedulerService;
import com.talkweb.twdpe.core.data.DataMap;
import com.talkweb.twdpe.core.data.IData;
import com.talkweb.twdpe.core.exception.BizBaseException;
import com.talkweb.twdpe.core.log.Logger;
import com.talkweb.twdpe.core.log.LoggerFactory;
import com.talkweb.twdpe.core.util.DateUtil;
import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.twdpe.web.jcontrols.RuleSelect;
import com.talkweb.twdpe.webcomponent.common.action.BusinessAction;

/**
 * 文件名称:    JobConfigAction.java
 * 内容摘要: 任务调度action
 * @author:   涂园园 
 * @version:  1.0  
 * @Date:     2011-7-8 上午09:50:35  
 * 
 * 修改历史:  
 * 修改日期       修改人员   版本	   修改内容  
 * ----------------------------------------------  
 * 2011-6-28    涂园园     1.0    1.0 XXXX
 *
 * 版权:   版权所有(C)2011
 * 公司:   拓维信息系统股份有限公司
 */
public class JobConfigAction extends BusinessAction {

    private static final Logger logger = LoggerFactory.getLogger(JobConfigAction.class);
    
    @Autowired
    private  SchedulerService schedulerService;
    
    private JobInfo job;
    
    /**
     * @Title: queryJobConfigsByPage
     * @Description: 通过条件组合查询用户任务调度
     */
    public String queryJobConfigsByPage() {
    	
	 Pagination pagination = this.getPagination();

	 IData param = new DataMap();
	 param.put(CommonConfig.page, pagination);
	 if(job != null) {
		 param.put(JobInfo.FIELD_JOB_NAME, job.getJobName());
		 param.put(JobInfo.FIELD_JOB_GROUP, job.getJobGroup());
		 param.put(JobInfo.FIELD_DESCRIPTION, job.getJobDescription());
	 }
	 
	 IData result;
	 
	try {
		result = schedulerService.getJobInfos(param);
	} catch (BizBaseException e) {
	    ServletActionContext.getRequest().setAttribute("throw", e);
		logger.error("",e);
		result = new DataMap();
	}
	
	 List list = (List)result.get(CommonConfig.list);
	 formatDatagirdData(list, pagination);	 
	 
	 return SUCCESS;
	 
    }
    
    /**
     * 分组信息初始化
     * @throws Exception 
     */
	public String searchSelectData() throws Exception {
		List<JobGroupInfo> groups = schedulerService.getGroups();
		
		List form = new ArrayList();
		RuleSelect select = new RuleSelect();
		select.setText("groupLabel");
		select.setValue("groupName");
		
		form.add(initformAttribute("job.jobGroup", initSelectData(groups, select)));
		
		formatData(form);
		
		return SUCCESS;
	}
	
	  /**
     * 
     * @Title: getJobInfo
     * @Description: 获得任务调度详情
     */
    public String getJobInfo() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String jobName = request.getParameter("jobName");
		String jobGroup = request.getParameter("jobGroup");
		try {
			IData jo = new DataMap();
			jo.put("jobName", jobName);
			jo.put("jobGroup", jobGroup);
			
			IData result = schedulerService.getJob(jo);
			JobInfo jobInfo = (JobInfo)result.get(CommonConfig.obj);
			
			IData STriggers = schedulerService.getSimpleTriggers(jo);
			SimpleTriggerInfo[] striggers = (SimpleTriggerInfo[])STriggers.get(CommonConfig.list);
			
			IData CTriggers = schedulerService.getCronTriggers(jo);
			CronTriggerInfo[] ctriggers = (CronTriggerInfo[])CTriggers.get(CommonConfig.list);
			
			if (result != null) {
				Map map = new HashMap();
				map.put("striggers", striggers);
				map.put("ctriggers", ctriggers);
				Map formMap = this.addPrefix(jobInfo, "job.");
				formMap.put("triggers", JSONObject.fromObject(map).toString());
				formatData(formMap);
			} else {
				this.setErrorMessage("找不到相关记录");
			}
		} catch (Exception e) {
			ServletActionContext.getRequest().setAttribute("throw", e);
			this.setErrorMessage("查找任务调度记录失败",e);
			e.printStackTrace();
		}
		
		return SUCCESS;
    }
    

    /**
     * @Title: addQuery
     * @Description: 新增任务调度
     */
    public String addJobConfig() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String triggers = request.getParameter("triggers");
		try{
			JSONObject triggerJson = JSONObject.fromObject(triggers);
			List<CronTriggerInfo> cli = new ArrayList<CronTriggerInfo>();
			List<SimpleTriggerInfo> sli = new ArrayList<SimpleTriggerInfo>();
			JSONArray striggers = triggerJson.getJSONArray("striggers");
			for(int i = 0; i < striggers.size(); i++) {
				JSONObject trigger = striggers.getJSONObject(i);
				SimpleTriggerInfo stinfo = new SimpleTriggerInfo();
				stinfo.setStartTime(startTime(trigger.getString("startTime")));
				String endTime = endTime(trigger.getString("endTime"));
				if(!validateCurrentTime(endTime)){
					this.setWarnMessage("简单触发器第" + (i+1) + "行结束时间不能比当前时间早!");
					return SUCCESS;
				}
				stinfo.setEndTime(endTime);
				stinfo.setRepeatCount(new Integer(trigger.getString("repeatCount")));
				stinfo.setRepeatInterval(new Long(trigger.getString("repeatInterval")));
				sli.add(stinfo);
			}
			JSONArray ctriggers = triggerJson.getJSONArray("ctriggers");
			for(int i = 0; i < ctriggers.size(); i++) {
				JSONObject trigger = ctriggers.getJSONObject(i);
				CronTriggerInfo ctinfo = new CronTriggerInfo();
				ctinfo.setStartTime(startTime(trigger.getString("startTime")));
				
				String endTime = endTime(trigger.getString("endTime"));
				if(!validateCurrentTime(endTime)){
					this.setWarnMessage("表达式触发器第" + (i+1) + "行结束时间不能比当前时间早!");
					return SUCCESS;
				}
				
				ctinfo.setEndTime(endTime);
				String cronExpression = trigger.getString("cronExpression");
				if(!org.quartz.CronExpression.isValidExpression(cronExpression)) {
					this.setWarnMessage("表达式触发器第" + (i+1) + "行响应时间输入错误!必须是合法的cron表达式(0 0 12 * * ?),请重新输入!");
					return SUCCESS;
				}
				ctinfo.setCronExpression(cronExpression);
				cli.add(ctinfo);
			}
			IData idata = new DataMap();
			idata.put("jobInfo", job);
			idata.put("strigger", sli);
			idata.put("ctrigger", cli);
			this.schedulerService.addJob(idata);
			setMessage(1, "JOBCONFIG", "ADD");
		} catch (Exception e) {
	    ServletActionContext.getRequest().setAttribute("throw", e);
			setMessage(0, "JOBCONFIG", "ADD", e);
			logger.error(e.getMessage(), e);
		}
		
		return SUCCESS;
    } 
    
    private boolean validateCurrentTime(String dateStr){
		if(StringUtils.isNotEmpty(dateStr)){
			Date endDate = DateUtil.formatStr2Date(dateStr);
			Date currentTime = DateUtil.getCurrentTime();
			if(endDate.before(currentTime)){
				return false;
			}
		}
		return true;
    }
    
	private String startTime(Object time) {
		if (time == null || time.toString().trim().length() == 0)
			return "";// yyyy-MM-dd HH:mm:ss
		return time.toString().trim().length() < 19 ? time.toString().trim()
				+ " 00:00:00" : time.toString().trim();
	}

	private String endTime(Object time) {
		if (time == null || time.toString().trim().length() == 0)
			return "";// yyyy-MM-dd HH:mm:ss
		return time.toString().trim().length() < 19 ? time.toString().trim()
				+ " 23:59:59" : time.toString().trim();
	}
    /**
     * 
     * @Title: checkCron
     * @Description: 判断cron表达式合法性
     */
    public String checkCron() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String cronStr = request.getParameter("val");
		boolean b = org.quartz.CronExpression.isValidExpression(cronStr);
		Map map = new HashMap();
		map.put("RESULT", (b ? 1 : 0));
		this.formatData(map);
		return SUCCESS;
    }	
    

    /**
     * 
     * @Title: containsJob
     * @Description: 判断任务名称唯一性
     */
    public String containsJob() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String jobName = request.getParameter("jobName");
		String jobGroup = request.getParameter("jobGroup");
		try {
			IData jo = new DataMap();
			jo.put("jobName", jobName);
			jo.put("jobGroup", jobGroup);
			
			IData result = this.schedulerService.containsJob(jo);
			this.formatData(result);
		} catch (Exception e) {
	    ServletActionContext.getRequest().setAttribute("throw", e);
			logger.error(e.getMessage(),e);
		}
		
		return SUCCESS;
    }
    
    /**
     * @Title: deleteQueries
     * @Description: 删除指定查询集合
     */
	public String deleteJobs() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String jobName = request.getParameter("jobName");
		String jobGroup = request.getParameter("jobGroup");
		IData data = new DataMap();
		data.put("jobName", jobName);
		data.put("jobGroup", jobGroup);

		try {
			this.schedulerService.deleteJob(data);
			setMessage(1, "JOBCONFIG", "DELETE");
		} catch (Exception e) {
	    ServletActionContext.getRequest().setAttribute("throw", e);
			setMessage(0, "JOBCONFIG", "DELETE", e);
			logger.error(e.getMessage(), e);
		}
		return SUCCESS;
	}
	

    /**
     * @Title: modifyJobConfig
     * @Description: 修改用户查询
     */
    public String modifyJobConfig() {
    	HttpServletRequest request = ServletActionContext.getRequest();
		String triggers = request.getParameter("triggers");
		try{
			JSONObject triggerJson = JSONObject.fromObject(triggers);
			List<CronTriggerInfo> cli = new ArrayList<CronTriggerInfo>();
			List<SimpleTriggerInfo> sli = new ArrayList<SimpleTriggerInfo>();
			JSONArray striggers = triggerJson.getJSONArray("striggers");
			for(int i = 0; i < striggers.size(); i++) {
				JSONObject trigger = striggers.getJSONObject(i);
				SimpleTriggerInfo stinfo = new SimpleTriggerInfo();
				stinfo.setTriggerName(trigger.getString("triggerName"));
				stinfo.setTriggerGroup(trigger.getString("triggerGroup"));
				stinfo.setStartTime(startTime(trigger.getString("startTime")));
				String endTime = endTime(trigger.getString("endTime"));
				if(!validateCurrentTime(endTime)){
					this.setWarnMessage("简单触发器第" + (i+1) + "行结束时间不能比当前时间早!");
					return SUCCESS;
				}
				stinfo.setEndTime(endTime);
				stinfo.setRepeatCount(new Integer(trigger.getString("repeatCount")));
				stinfo.setRepeatInterval(new Long(trigger.getString("repeatInterval")));
				sli.add(stinfo);
			}
			JSONArray ctriggers = triggerJson.getJSONArray("ctriggers");
			for(int i = 0; i < ctriggers.size(); i++) {
				JSONObject trigger = ctriggers.getJSONObject(i);
				CronTriggerInfo ctinfo = new CronTriggerInfo();
				ctinfo.setStartTime(startTime(trigger.getString("startTime")));
				ctinfo.setTriggerName(trigger.getString("triggerName"));
				ctinfo.setTriggerGroup(trigger.getString("triggerGroup"));
				String endTime = endTime(trigger.getString("endTime"));
				if(!validateCurrentTime(endTime)){
					this.setWarnMessage("表达式触发器第" + (i+1) + "行结束时间不能比当前时间早!");
					return SUCCESS;
				}
				
				ctinfo.setEndTime(endTime);
				String cronExpression = trigger.getString("cronExpression");
				if(!org.quartz.CronExpression.isValidExpression(cronExpression)) {
					this.setWarnMessage("表达式触发器第" + (i+1) + "行响应时间输入错误!必须是合法的cron表达式(0 0 12 * * ?),请重新输入!");
					return SUCCESS;
				}
				ctinfo.setCronExpression(cronExpression);
				cli.add(ctinfo);
			}
			IData idata = new DataMap();
			idata.put("jobInfo", job);
			idata.put("strigger", sli);
			idata.put("ctrigger", cli);
			this.schedulerService.modifyJob(idata);
			setMessage(1, "JOBCONFIG", "MODIFY");
		} catch (Exception e) {
			ServletActionContext.getRequest().setAttribute("throw", e);
			setMessage(0, "JOBCONFIG", "MODIFY", e);
			logger.error(e.getMessage(), e);
		}
		return SUCCESS;
    }
    

    /**
     * 得到运行中的job实例
     * */
	public String queryJobInstances() {
		HttpServletRequest request = ServletActionContext.getRequest();
		Pagination pagination = this.getPagination();

		IData idata = new DataMap();

		idata.put("PAGE", pagination);
		if(job != null) {
			idata.put("JOB_NAME", job.getJobName());
			idata.put("JOB_GROUP", job.getJobGroup());
		}
		try {
			IData result = schedulerService.getJobInstances(idata);
			List list = (List) result.get(CommonConfig.list);
			formatDatagirdData(list, pagination);
		} catch (Exception e) {
	    ServletActionContext.getRequest().setAttribute("throw", e);
			logger.error(e.getMessage(), e);
		}

		return SUCCESS;
	}


    /**
     * 任务调度日志查询
     * @Title: queryJobLogsByPage
     * @Description: TODO(这里用一句话描述这个方法的作用)
     */
	public String queryJobLogsByPage() {
		HttpServletRequest request = ServletActionContext.getRequest();
		IData idata = new DataMap();
		Pagination pagination = this.getPagination();
		String stime = startTime(request.getParameter("startRuntime"));
		String etime = endTime(request.getParameter("endRuntime"));
		String runResult = request.getParameter("runResult");
		idata.put("STARTTIME", stime);
		idata.put("ENDTIME", etime);
		idata.put("RUNRESULT", runResult);
		idata.put(CommonConfig.page, pagination);
		if(job != null) {
			idata.put("JOBNAME", job.getJobName());
			idata.put("JOBGROUP", job.getJobGroup());
		}
		try {
			IData result = schedulerService.getJobLogs(idata);
			List list = (List) result.get(CommonConfig.list);
			formatDatagirdData(list, pagination);
		} catch (Exception e) {
	    ServletActionContext.getRequest().setAttribute("throw", e);
			logger.error(e.getMessage(), e);
		}
		return SUCCESS;
	}  
	

    /**
     * 得到指定job的触发器运行相关信息
     */
	public String queryTriggerRuntimes() {
		HttpServletRequest request = ServletActionContext.getRequest();

		IData idata = new DataMap();
		Pagination pagination = this.getPagination();

		idata.put("PAGE", pagination);
		String jobName = request.getParameter("jobName");
		String jobGroup = request.getParameter("jobGroup");
		idata.put("jobName", jobName);
		idata.put("jobGroup", jobGroup);
		try {

			IData runtimeTriggers = schedulerService.getTriggerRuntimes(idata);

			List list = (List) runtimeTriggers.get(CommonConfig.list);
			formatDatagirdData(list, pagination);

		} catch (Exception e) {
	    ServletActionContext.getRequest().setAttribute("throw", e);
			logger.error(e.getMessage(), e);
		}

		return SUCCESS;
	}
	

    /**
     * 立即执行一次job任务
     * */
    public String triggerImmediate() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String jobName = request.getParameter("jobName");
		String jobGroup = request.getParameter("jobGroup");
		IData idata = new DataMap();
		idata.put("jobName", jobName);
		idata.put("jobGroup", jobGroup);
		
		try {
			this.schedulerService.triggerImmediate(idata);
			this.setOkMessage("操作成功");
		} catch (Exception e) {
	    ServletActionContext.getRequest().setAttribute("throw", e);
			this.setErrorMessage("操作失败", e);			
			logger.error(e.getMessage(), e);
		}
		
		return SUCCESS;
    }	
    

    /**
     * 暂停指定的一个触发器
     * */
    public String pauseTrigger() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String triggerName = request.getParameter("triggerName");
		String triggerGroup = request.getParameter("triggerGroup");
		IData idata = new DataMap();
		idata.put("triggerName", triggerName);
		idata.put("triggerGroup", triggerGroup);
		
		try {
			this.schedulerService.pauseTrigger(idata);
			this.setOkMessage("操作成功");
		} catch (Exception e) {
	    ServletActionContext.getRequest().setAttribute("throw", e);
			this.setErrorMessage("操作失败", e);	
			logger.error(e.getMessage(), e);
		}
		
		return SUCCESS;
    }    
    

    /**
     * 恢复任务
     */
    public String resumeJob() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String jobName = request.getParameter("jobName");
		String jobGroup = request.getParameter("jobGroup");
		IData idata = new DataMap();
		idata.put("jobName", jobName);
		idata.put("jobGroup", jobGroup);
		
		try {
			this.schedulerService.resumeJob(idata);
			this.setOkMessage("操作成功");
		} catch (Exception e) {
	    ServletActionContext.getRequest().setAttribute("throw", e);
			this.setErrorMessage("操作失败", e);	
			logger.error(e.getMessage(), e);
		}
		return SUCCESS;
    }    
	

    /**
     * 
     * 暂停任务调度
     */
    public String pauseJob() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String jobName = request.getParameter("jobName");
		String jobGroup = request.getParameter("jobGroup");
		IData idata = new DataMap();
		idata.put("jobName", jobName);
		idata.put("jobGroup", jobGroup);
		try {
			this.schedulerService.pauseJob(idata);
			this.setOkMessage("操作成功");
		} catch (Exception e) {
	    ServletActionContext.getRequest().setAttribute("throw", e);
	    this.setErrorMessage("操作失败", e);	
			logger.error(e.getMessage(), e);
		}
		return SUCCESS;
    }
    

    /**
     * 得到Job定义信息
     */
    public String queryJobInfosByPage() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String jobName = request.getParameter("jobName");
		String jobGroup = request.getParameter("jobGroup");

		IData idata = new DataMap();
		Pagination pagination = this.getPagination();
		idata.put("JOB_NAME", jobName);
		idata.put("JOB_GROUP", jobGroup);
		idata.put("PAGE", pagination);
		try {
			IData result = schedulerService.getJobInfos(idata);
			List list = (List)result.get(CommonConfig.list);
			this.formatDatagirdData(list, pagination);
		} catch (Exception e) {
	    ServletActionContext.getRequest().setAttribute("throw", e);
			logger.error(e.getMessage(), e);
		}
		return SUCCESS;
    }    
    

    /**
     * 恢复一个触发器
     * */
    public String resumeTrigger() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String triggerName = request.getParameter("triggerName");
		String triggerGroup = request.getParameter("triggerGroup");
		IData idata = new DataMap();
		idata.put("triggerName", triggerName);
		idata.put("triggerGroup", triggerGroup);
		
		try {
			this.schedulerService.resumeTrigger(idata);
			this.setOkMessage("操作成功");	
		} catch (Exception e) {
	    ServletActionContext.getRequest().setAttribute("throw", e);
	    this.setErrorMessage("操作失败", e);	
			logger.error(e.getMessage(), e);
		}
		
		return SUCCESS;
    }

	public JobInfo getJob() {
		return job;
	}

	public void setJob(JobInfo job) {
		this.job = job;
	}
}
