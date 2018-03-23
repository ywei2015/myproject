package com.talkweb.xwzcxt.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.xwzcxt.pojo.CheckCardPoJo;
import com.talkweb.xwzcxt.service.CheckCardService;

public class CheckCardExportAction extends baseAction {
	@Autowired
	private CheckCardService checkCradService;
	
	private HttpServletRequest request = ServletActionContext.getRequest();
	private HttpServletResponse response = ServletActionContext.getResponse();
	
	
	public String cardExport(){
		Map param = new HashMap();
		String cBasedataId = request.getParameter("cBasedataId");
		//String cTableTypeId = request.getParameter("cTabletypeId");
		
		if(cBasedataId != null && !"".equals(cBasedataId)){
			param.put("cBasedataId", cBasedataId);
		}
		
		CheckCardPoJo jobInfo = null;
		List<CheckCardPoJo> cardInfos = new ArrayList<CheckCardPoJo>();
		List jobObjects = checkCradService.getJobObjInfo(param);
		if(jobObjects != null && jobObjects.size()>0){
			 jobInfo = (CheckCardPoJo)jobObjects.get(0);
			 String[] headInfo = {jobInfo.getCol1(),jobInfo.getCol4(),jobInfo.getC_basedata_code()};
			 Long typeId = jobInfo.getcTabletypeId();
			 
			 List tasks = getTaskInfo(cBasedataId);
			 if(tasks != null && tasks.size()>0 ){
				 for (int i = 0; i < tasks.size(); i++) {
					 CheckCardPoJo task = (CheckCardPoJo)tasks.get(i);
					 List taskSteps = getTaskSteps(task.getC_task_id().toString());
					 if(typeId == 11006){
						 CheckCardPoJo cardInfo = getXhsCardInfos(task,taskSteps);
						 cardInfos.add(cardInfo);

					 }else{
						 CheckCardPoJo cardInfo = getMhqCardInfos(task,taskSteps);
						 cardInfos.add(cardInfo);
 
					 } 
				}
			 }
			 
			 String url = this.exportExcelCheckCard(headInfo, jobInfo.getcTabletypeId(), "xhsCardExport", cardInfos, "室内消火栓检查记录卡");
			 Map map = new HashMap();
			 map.put("url", url);
			 map.put("result", true);
			 this.formatData(map);
		}else{
			 Map map = new HashMap();
			 map.put("result", false);
			 map.put("msg", "请选择消火栓或者灭火器导出！");
			 this.formatData(map);
			 return SUCCESS;
		}
 
		return SUCCESS;
	}
	
	
	private CheckCardPoJo getMhqCardInfos(CheckCardPoJo task, List taskSteps) {
		Date startTime = task.getC_start_time();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		String time = sdf.format(startTime);
		task.setTime(time);
		
		if(taskSteps != null && taskSteps.size()>0 ){
			for (int i = 0; i < taskSteps.size(); i++) {
				CheckCardPoJo step = (CheckCardPoJo)taskSteps.get(i);
				String prompt = step.getC_step_prompt();
				Integer status = step.getC_status();
				int wg = prompt.indexOf("外观");
				int yl = prompt.indexOf("压力"); 
				
				if(wg != -1){
					if(status == 0){
						task.setWgNormal("√");
					}else if(status == 1){
						task.setWgNormal("×");
					}
					continue;
				} 
				
				if(yl != -1){
					if(status == 0){
						task.setYlNormal("√");
					}else if(status == 1){
						task.setYlNormal("×");
					}
					continue;
				}
			}
		}
		return task;
	}


	private CheckCardPoJo getXhsCardInfos(CheckCardPoJo task, List taskSteps) {
		Date startTime = task.getC_start_time();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		String time = sdf.format(startTime);
		task.setTime(time);
		
		if(taskSteps != null && taskSteps.size()>0 ){
			for (int i = 0; i < taskSteps.size(); i++) {
				CheckCardPoJo step = (CheckCardPoJo)taskSteps.get(i);
				String prompt = step.getC_step_prompt();
				Integer status = step.getC_status();
				int xt = prompt.indexOf("箱体");
				int sq = prompt.indexOf("水枪");
				int sd = prompt.indexOf("水带");
				int jt = prompt.indexOf("接头");
				int fm = prompt.indexOf("阀门");
				if(xt != -1){
					if(status == 0){
						task.setXtNormal("√");
					}else if(status == 1){
						task.setXtNormal("×");
					}
					continue;
				} 
				
				if(sq != -1){
					if(status == 0){
						task.setSqNormal("√");
					}else if(status == 1){
						task.setSqNormal("×");
					}
					continue;
				}
				
				if(sd != -1){
					if(status == 0){
						task.setSdNormal("√");
					}else if(status == 1){
						task.setSdNormal("×");
					}
					continue;
				}
				
				if(jt != -1){
					if(status == 0){
						task.setJtNormal("√");
					}else if(status == 1){
						task.setJtNormal("×");
					}
					continue;
				}
				
				if(fm != -1){
					if(status == 0){
						task.setFmNormal("√");
					}else if(status == 1){
						task.setFmNormal("×");
					}
					continue;
				}
				
			}
		}
		return task;
	}


	private List getTaskInfo(String c_basecode_id){
		Map param = new HashMap();
		if(c_basecode_id != null && !"".equals(c_basecode_id)){
			param.put("c_basecode_id", c_basecode_id);
		}
		List taskInfos = checkCradService.getTaskInfos(param);
		return taskInfos;
	}
 
	
	private List getTaskSteps(String taskId){
		Map param = new HashMap();
		if(taskId != null && !"".equals(taskId)){
			param.put("taskId", taskId);
		}
		List taskSteps = checkCradService.getTaskSteps(param);
		return taskSteps;
	}
	
}
