package com.talkweb.xwzcxt.action;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;
import com.talkweb.xwzcxt.service.TaskExecuteCountService;
import com.talkweb.xwzcxt.util.DateUtils;
import com.talkweb.xwzcxt.vo.ExecutabilityVo;

public class TaskExecuteCountAction extends BusinessAction implements
		ModelDriven<ExecutabilityVo>, ServletRequestAware {
	private static final long serialVersionUID = -4863823364601616791L;

	private HttpServletRequest request;
	private ExecutabilityVo executabilityVo = new ExecutabilityVo();

	@Autowired
	private TaskExecuteCountService executabilityService;
	
	private  void setCode(){
		String flag=request.getParameter("flag");
		String userCode ="";
		if("1".equals(flag)){
			HttpSession session = request.getSession();
            Map user =null;  
            user = (Map) session.getAttribute("USERSESSION");
            if (user != null){
                userCode = user.get("code").toString();
                executabilityVo.setUserCode(userCode);
            }
		}
		
	}

	public String getSelfExecutability() {
		setCode();
		if (executabilityVo.getUserCode() == null
				|| executabilityVo.getUserCode().trim().length() == 0) {
			this.formatData(null);
			return SUCCESS;
		}
		Integer time_type = executabilityVo.getTime_type();
		if (time_type == null) {
			time_type = 2;
		}
		Integer task_type = executabilityVo.getTask_type();
		List<String> days = DateUtils.getDays(time_type);
		try {
			switch (task_type) {
			case 0:
				return getSelfExecuteData(days);
			case 1:
				return getSelfChkData(days);
			case 2:
				return getSelfEvaluateData(days);
			default:
				return SUCCESS;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	private String getSelfExecuteData(List<String> days) {
		try {
			executabilityVo.setStart_time(days.get(0));
			executabilityVo.setTotal_days(days.size());
			List list = executabilityService
					.querySelfExecuteData(executabilityVo);
			return getData(days, list,1);
		} catch (Exception e) {
			e.printStackTrace();
			this.formatData(null);
		}
		return SUCCESS;
	}

	
	private String getSelfChkData(List<String> days) {
		executabilityVo.setStart_time(days.get(0));
		executabilityVo.setTotal_days(days.size());
		List list = executabilityService.getSelfChkData(executabilityVo);
		return getData(days, list,2);
	}

	private String getSelfEvaluateData(List<String> days) {
		executabilityVo.setStart_time(days.get(0));
		executabilityVo.setTotal_days(days.size());
		List list = executabilityService.getSelfEvaluateData(executabilityVo);

		return getData(days, list,3);
	}
	
	public  String getGroupExecutability(){
		String dataId = request.getParameter("dataId");
		setCode();
		if (executabilityVo.getUserCode() == null
				|| executabilityVo.getUserCode().trim().length() == 0) {
			this.formatData(null);
			return SUCCESS;
		}
		Integer time_type = executabilityVo.getTime_type();
		if (time_type == null) {
			time_type = 2;
		}
		Integer task_type = executabilityVo.getTask_type();
		List<String> days = DateUtils.getDays(time_type);
		try {
			switch (task_type) {
			case 0:
				return getGroupExecuteData(days,dataId);
			case 1:
				return getGroupChkData(days,dataId);
			case 2:
				return getGroupEvaluateData(days,dataId);
			default:
				return SUCCESS;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	private String getGroupExecuteData(List<String> days,String dataId) {
		executabilityVo.setStart_time(days.get(0));
		executabilityVo.setTotal_days(days.size());
		List list = executabilityService
				.getGroupExecuteData(executabilityVo,dataId);
		return getData(days, list,1);
	}
	
	private String getGroupChkData(List<String> days,String dataId) {
		executabilityVo.setStart_time(days.get(0));
		executabilityVo.setTotal_days(days.size());
		List list = executabilityService
				.getGroupChkData(executabilityVo,dataId);
		return getData(days, list,2);
	}
	
	private String getGroupEvaluateData(List<String> days,String dataId) {
		executabilityVo.setStart_time(days.get(0));
		executabilityVo.setTotal_days(days.size());
		List list = executabilityService
				.getGroupEvaluateData(executabilityVo,dataId);
		return getData(days, list,3);
	}

	public String getDepartExecutability(){
		setCode();
		String dataId = request.getParameter("dataId");
		if (executabilityVo.getUserCode() == null
				|| executabilityVo.getUserCode().trim().length() == 0) {
			this.formatData(null);
			return SUCCESS;
		}
		Integer time_type = executabilityVo.getTime_type();
		if (time_type == null) {
			time_type = 2;
		}
		Integer task_type = executabilityVo.getTask_type();
		List<String> days = DateUtils.getDays(time_type);
		try {
			switch (task_type) {
			case 0:
				return getDepartExecuteData(days,dataId);
			case 1:
				return getDepartChkData(days,dataId);
			case 2:
				return getDepartEvaluateData(days,dataId);
			default:
				return SUCCESS;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	private String getDepartExecuteData(List<String> days,String dataId) {
		executabilityVo.setStart_time(days.get(0));
		executabilityVo.setTotal_days(days.size());
		List list = executabilityService
				.getDepartExecuteData(executabilityVo,dataId);
		return getData(days, list,1);
	}
	
	private String getDepartChkData(List<String> days,String dataId) {
		executabilityVo.setStart_time(days.get(0));
		executabilityVo.setTotal_days(days.size());
		List list = executabilityService
				.getDepartChkData(executabilityVo,dataId);
		return getData(days, list,2);
	}
	
	private String getDepartEvaluateData(List<String> days,String dataId) {
		executabilityVo.setStart_time(days.get(0));
		executabilityVo.setTotal_days(days.size());
		List list = executabilityService
				.getDepartEvaluateData(executabilityVo,dataId);
		return getData(days, list,3);
	}

	private String getData(List<String> days,List list,int type){
		try{
			ExecutabilityVo vo = new ExecutabilityVo();
			vo.setAdvance_time(0f);
			vo.setDay_of_month("maxday+1");
			vo.setPlain_time(1f);
			vo.setExist(0);
			vo.setResult(null);
			vo.setC_status(11);
			list.add(vo);
			int len = days.size();
			List advanceCountList = new ArrayList();
			List lateCountList = new ArrayList();
			List advanceCountRateList = new ArrayList();
			List lateCountRateList = new ArrayList();
			List advanceTimeTotalRateList = new ArrayList();
			List lateTimeTotalRateList = new ArrayList();

			Integer advanceCount = 0; // 每天提前完成的任务的个数
			Integer lateCount = 0; // 每天逾期完成的任务的个数
			float advanceTimeTotal = 0f; // 每天提前完成任务的时间
			float lateTimeTotal = 0f; // 每天逾期完成任务的时间
			float taskTimeTotal = 0f; // 每天要完成的任务的所需的时间和
			String date = null;
			int dayTaskCount = 0;
			int sumTaskCount = 0;
			float sumTimeCount = 0;
			float sumAdvanceTimeCount = 0;
			float sumLateTimeCount = 0;
			int sumAdvanceCount = 0;
			int sumLateCount = 0;

			int dayHeGeCount = 0;
			int dayBuHeGeCount = 0;
			List dayHeGeCountList = new ArrayList();
			List dayBuHeGeCountList = new ArrayList();
			int sumHeGe = 0;
			int sumBuHeGe = 0;

			int priorExist = 0;
			DecimalFormat df = new DecimalFormat("#0.00");
			for (int j = 0; list != null && j < list.size(); j++) {
				Object ob = list.get(j);
				ExecutabilityVo exe = (ExecutabilityVo) ob;
				String date2 = exe.getDay_of_month();
				boolean isSameDay = date2.equals(date);
				int exist = exe.getExist();
				int status=0;
				// System.out.println(exe.getDay_of_month()+":\t"+exe.getAdvance_time()+"\t"+exist);
				if (exist == 1) {
					status=exe.getC_status();
					sumTaskCount++;
					if(exe.getPlain_time()!=null){						
						sumTimeCount += exe.getPlain_time();
					}
				}
				Float advanceTime = 0f;
				if(exe.getAdvance_time()!=null){
					advanceTime=exe.getAdvance_time();
				}
				if (exist == 1 && isSameDay) {
					boolean shouldCount=false;
					if(type==1&&(status==33||status==34||status==35)){
						shouldCount=true;
					}else if(type==2&&(status==34||status==35)){
						shouldCount=true;
					}else if(type==3&&status==35){
						shouldCount=true;
					}
					if(exe.getPlain_time()!=null){						
						taskTimeTotal += exe.getPlain_time();
					}
					dayTaskCount++; // 如果是同一天的任务，当天任务数加1
					if (advanceTime!=null&&advanceTime >= 0&&shouldCount) {
						advanceCount++;
						advanceTimeTotal += advanceTime;
						sumAdvanceTimeCount += advanceTime;
						sumAdvanceCount++;
					} else if(advanceTime!=null&&advanceTime < 0&&shouldCount){
						lateCount++;
						lateTimeTotal += advanceTime;
						sumLateTimeCount += advanceTime;
						sumLateCount++;
					}

					if (exe.getC_status() == 35
							&& (type==1&&exe.getResult() == null || "OK"
									.equals(exe.getResult()))) {
						dayHeGeCount++;
						sumHeGe++;
					} else if (type==1&&exe.getC_status() == 35) {
						dayBuHeGeCount++;
						sumBuHeGe++;
					}

				} else if (!isSameDay) {

					if (priorExist == 1) {
						dayHeGeCountList.add(dayHeGeCount);
						dayBuHeGeCountList.add(-dayBuHeGeCount);
						advanceCountList.add(advanceCount);
						lateCountList.add(-lateCount);

						float rate = (float) advanceCount * 100 / dayTaskCount;

						advanceCountRateList.add(df.format(rate));

						rate = (float) lateCount * 100 / dayTaskCount;

						lateCountRateList.add(df.format(rate));

						if (taskTimeTotal == 0) {
							lateTimeTotalRateList.add("-");
							advanceTimeTotalRateList.add("-");
						} else {

							rate = Math.abs(advanceTimeTotal / taskTimeTotal) * 100;

							advanceTimeTotalRateList.add(df.format(rate));

							rate = Math.abs(lateTimeTotal / taskTimeTotal) * 100;
							// System.out.println(advanceTimeTotal+","+lateTimeTotal+","+taskTimeTotal);
							lateTimeTotalRateList.add(df.format(rate));
						}

					}
					taskTimeTotal = 0;
					if (exist == 1) {
						priorExist = 1;
						dayTaskCount = 1;
						
						if (type==1&&exe.getC_status() == 35
								&& (exe.getResult() == null
										|| "OK".equals(exe
												.getResult()) || ""
											.equals(exe.getResult()
													.trim()))) {
							dayHeGeCount++;
							sumHeGe++;
						} else if (type==1&&exe.getC_status() == 35) {
							dayBuHeGeCount++;
							sumBuHeGe++;
						}
						
						boolean shouldCount=false;
						if(type==1&&(status==33||status==34||status==35)){
							shouldCount=true;
						}else if(type==2&&(status==34||status==35)){
							shouldCount=true;
						}else if(type==3&&status==35){
							shouldCount=true;
						}
						if (advanceTime!=null&&advanceTime >= 0&&shouldCount) {
							lateCount = 0;
							lateTimeTotal = 0;
							advanceCount = 1;
							sumAdvanceTimeCount = advanceTime;
							advanceTimeTotal += advanceTime;
							sumAdvanceCount++;
						} else if(advanceTime!=null&&advanceTime < 0&&shouldCount) {
							advanceCount = 0;
							advanceTimeTotal = 0;
							lateCount = 1;
							lateTimeTotal = advanceTime;
							sumLateTimeCount += advanceTime;
							sumLateCount++;
						}else{
							lateCount = 0;
							advanceCount = 0;
							advanceTimeTotal = 0;
							lateTimeTotal = 0;
							dayTaskCount = 0;
						}
						if(exe.getPlain_time()!=null){							
							taskTimeTotal = exe.getPlain_time();
						}
					} else {
						priorExist = 0; // 设置前一天的任务为没有，供下一次统计使用
						if (!"maxday+1".equals(date2)) {
							dayBuHeGeCount = 0;
							dayHeGeCount = 0;
							dayBuHeGeCountList.add("-");
							dayHeGeCountList.add("-");
							dayTaskCount = 0;
							priorExist = 0;
							advanceCount = 0;
							lateCount = 0;
							taskTimeTotal = 0;
							advanceTimeTotal = 0;
							lateTimeTotal = 0;
							advanceCountList.add("-");
							lateCountList.add("-");
							advanceCountRateList.add("-");
							lateCountRateList.add("-");
							advanceTimeTotalRateList.add("-");
							lateTimeTotalRateList.add("-");
						}
					}

					date = date2; // 如果不是同一天，当前统计的时间置为任务数据的时间
					if (exist == 0) {
						dayTaskCount = 0; // 如果不是同一天且当天没有任务，当天任务数置为0
					} else {
						dayTaskCount = 1; // 如果不是同一天当天有任务，当天任务数置为1
					}

				}
			}

			HashMap map = new HashMap();
			map.put("days", days);
			map.put("advanceCountList", advanceCountList);
			map.put("lateCountList", lateCountList);
			map.put("advanceCountRateList", advanceCountRateList);
			map.put("lateCountRateList", lateCountRateList);
			map.put("advanceTimeTotalRateList", advanceTimeTotalRateList);
			map.put("sumAdvanceCount", sumAdvanceCount);
			map.put("sumLateCount", sumLateCount);
			map.put("lateTimeTotalRateList", lateTimeTotalRateList);

			map.put("dayHeGeCountList", dayHeGeCountList);
			map.put("dayBuHeGeCountList", dayBuHeGeCountList);
			map.put("sumHeGe", sumHeGe);
			map.put("sumBuHeGe", sumBuHeGe);

			float rate = 0;
			if (sumTaskCount == 0) {
				map.put("sumAdvanceCountRate", "-");
				map.put("sumLateCountRate", "-");
			} else {
				rate = (float)sumAdvanceCount * 100 / sumTaskCount;
				System.out.println(sumAdvanceCount + "," + sumTaskCount);
				map.put("sumAdvanceCountRate", df.format(rate) + "");

				rate = (float)sumLateCount * 100 / sumTaskCount;
				map.put("sumLateCountRate", df.format(rate) + "");

			}

			if (sumTimeCount > 0) {
				rate = Math.abs(sumAdvanceTimeCount / sumTimeCount) * 100;
				map.put("sumAdvancTimeRate", df.format(rate) + "");

				rate = Math.abs(sumLateTimeCount / sumTimeCount) * 100;
				map.put("sumLateTimeRate", df.format(rate) + "");
			} else {
				map.put("sumAdvancTimeRate", "-");
				map.put("sumLateTimeRate", "-");
			}

			this.formatData(map);
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public  String getDataDetails(){
		try{
			setCode();
			String  userCode=executabilityVo.getUserCode();
			Integer task_type=executabilityVo.getTask_type();
			Integer orgtype=executabilityVo.getOrgtype();
			String  start_time=executabilityVo.getStart_time();
			if(start_time!=null&&start_time.trim().length()>0){				
				String st[]=start_time.split("-");
				if(st.length!=3){
					this.formatData("[]");
					return SUCCESS;
				}
				if(st[1].length()<2){
					st[1]="0"+st[1];
				}
				if(st[2].length()<2){
					st[2]="0"+st[2];
				}
			   start_time=st[0]+"-"+st[1]+"-"+st[2];
			   executabilityVo.setStart_time(start_time);
			}
			Integer details_type=executabilityVo.getDetails_type();
			if(details_type==null||userCode==null||start_time==null||task_type==null||orgtype==null){
				this.formatData("[]");
				return SUCCESS;
			}
			
			switch(orgtype){
				case 0: return getSelfDataDetails(task_type);
				case 1: return getGroupDataDetails(task_type);
				case 2: return getDepartDataDetails(task_type);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	private String getSelfDataDetails(Integer task_type){
		try{
			List list=null;
			switch(task_type){
			   case 0:  list= executabilityService.getSelfTaskExecuteDetails(executabilityVo);break;
			   case 1:  list= executabilityService.getSelfChkDetails(executabilityVo);break;
			   case 2:  list= executabilityService.getSelfEvaluateDetails(executabilityVo);break;
		   }
			this.formatData(list);
		}catch(Exception e){
			e.printStackTrace();
			this.formatData("[]");
		}
		
		return SUCCESS;
	}
	
	
	private String getGroupDataDetails(Integer task_type){
		try{
			List list=null;
			switch(task_type){
			   case 0:  list= executabilityService.getGroupExecuteDetails(executabilityVo);break;
			   case 1:  list= executabilityService.getGroupChkDetails(executabilityVo);break;
			   case 2:  list= executabilityService.getGroupEvaluateDetails(executabilityVo);break;
		   }
			this.formatData(list);
		}catch(Exception e){
			e.printStackTrace();
			this.formatData("[]");
		}
		
		return SUCCESS;
	}
	
	private String getDepartDataDetails(Integer task_type){
		try{
			List list=null;
			switch(task_type){
			   case 0:  list= executabilityService.getDepartExecuteDetails(executabilityVo);break;
			   case 1:  list= executabilityService.getDepartChkDetails(executabilityVo);break;
			   case 2:  list= executabilityService.getDepartEvaluateDetails(executabilityVo);break;
		   }
			this.formatData(list);
		}catch(Exception e){
			e.printStackTrace();
			this.formatData("[]");
		}
		
		return SUCCESS;
	}

	public ExecutabilityVo getExecutablityVo() {
		return executabilityVo;
	}

	public void setExecutablityVo(ExecutabilityVo executablityVo) {
		this.executabilityVo = executablityVo;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	@Override
	public ExecutabilityVo getModel() {
		return executabilityVo != null ? executabilityVo
				: new ExecutabilityVo();
	}

}
