package com.talkweb.xwzcxt.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.xwzcxt.pojo.TaskMouldPojo;
import com.talkweb.xwzcxt.service.TaskSearchService;

public class AlreadyhandTaskMngAction extends WaithandTaskMngAction {

	/**
	 * 已办任务管理类
	 * YangChen
	 * 2014-8-4 create
	 */
	private static final long serialVersionUID = 1124153772681754755L;
	
	@Autowired
	private TaskSearchService taskSearchService;
	
	HttpServletRequest request = (HttpServletRequest) ActionContext
            .getContext().get(ServletActionContext.HTTP_REQUEST);
	
	private TaskMouldPojo taskMouldPojo=new TaskMouldPojo();
	
	public TaskMouldPojo getTaskMouldPojo() {
		return taskMouldPojo;
	}
	
	public void setTaskMouldPojo(TaskMouldPojo taskMouldPojo) {
		this.taskMouldPojo = taskMouldPojo;
	}
	
	public AlreadyhandTaskMngAction() {
		// TODO Auto-generated constructor stub
	}
	//对数据库时间进行转换成毫秒
	public static long convertDBTime2MilliSeconds(String DBTime){
		Calendar calendar=Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
		String date=DBTime.split(" ")[0];
		String time=DBTime.split(" ")[1];
		calendar.set(Integer.parseInt(date.split("-")[0]), Integer.parseInt(date.split("-")[1]), Integer.parseInt(date.split("-")[2]), Integer.parseInt(time.split(":")[0]), Integer.parseInt(time.split(":")[1]));
		return calendar.getTimeInMillis();	
	}
	//根据当前登陆的用户查询已办任务
	@SuppressWarnings({ "unchecked", "static-access" })
	public String getDefaultAlreadyhandTasks(){
		
		try{
		List<String> statusList=new ArrayList<String>();
		//待办任务状态为33
		statusList.add("33");
		taskMouldPojo.setC_status_list(statusList);
		taskMouldPojo.setC_confirm_userid(this.getLongUserID()+"");
		
		
		List<TaskMouldPojo> taskList=null;
		if(pagination==null){
		 	pagination=new Pagination(1,15);
		}
		taskList=taskSearchService.queryTaskInfoListByCondition(taskMouldPojo,pagination);
		
		/*List<TaskMouldPojo> taskList=taskSearchService.queryTaskInfoListByCondition(taskMouldPojo);
		WaithandTaskMngAction waithandTaskMngAction=new WaithandTaskMngAction();
		//调用父类的内部类，对任务列表List进行泛型规定
		InnerList<TaskMouldPojo> innerList=waithandTaskMngAction.new InnerList<TaskMouldPojo>();
		innerList.setList(taskList);
		//调用继承父类的初始化分页方法
		List<TaskMouldPojo> currentList=(List<TaskMouldPojo>)(this.initPagination(innerList, 20,request));
		*/
		
		for(TaskMouldPojo pojo:taskList){
			String tempLinkName = pojo.getC_task_name();
			StringBuffer taskLink=new StringBuffer();
			//拼接任务链接
			taskLink.append("<a style = text-decoration:none;display:block; ");
			   taskLink.append("title = 查看任务详情" +" ");
			   taskLink.append("onclick = doOpenTaskDetails('" + pojo.getC_task_id() + "')>" + tempLinkName);
			taskLink.append("</a>");
			String taskLinkString=taskLink.toString();
			pojo.setC_status_name(this.convertStatusCode2String(pojo.getC_status()));
			pojo.setIsExpired("");
			pojo.setC_task_name(taskLinkString);
			//替换掉title属性的内容
			//taskLinkString=taskLinkString.replaceFirst(tempLinkName, "查看任务详情");
			//替换掉文本
			//taskLinkString=taskLinkString.replaceFirst(tempLinkName, "查看");
			//查看详情
			pojo.setWatchTaskDetail(taskLinkString);
			if(pojo.getC_fact_endtime()==null || pojo.getC_fact_endtime().equals("")){
				//pojo.setIsExpired("按期完成");
			}
			else{
				if(this.convertDBTime2MilliSeconds(pojo.getC_fact_endtime())>this.convertDBTime2MilliSeconds(pojo.getC_end_time())){
					pojo.setIsExpired("已逾期");
				}else{
					pojo.setIsExpired("按期完成");
				}
			}
			
			if(pojo.getC_iskeyctrl()!=null&&!"".equals(pojo.getC_iskeyctrl())){
				pojo.setC_iskeyctrl_name(this.convertZeroOrOne2String(pojo.getC_iskeyctrl()));
			}
			if(pojo.getC_issequence()!=null&&!"".equals(pojo.getC_issequence())){
				pojo.setC_issequence_name(this.convertZeroOrOne2String(pojo.getC_issequence()));
			}
			
			if(pojo.getC_task_typename().equals("临时任务")){
				pojo.setC_task_typename("工作安排");
			}else if(pojo.getC_task_typename().equals("协调任务")){
				pojo.setC_task_typename("异常反馈");
			}
		}
		this.formatDatagirdData(taskList, this.pagination);
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;		
	}
	
	public static void main(String[] args) {
		System.out.println(convertDBTime2MilliSeconds("2014-07-22 22:19:44.0"));
		System.out.println(convertDBTime2MilliSeconds("2014-07-23 23:40:44.0"));
	}

}
