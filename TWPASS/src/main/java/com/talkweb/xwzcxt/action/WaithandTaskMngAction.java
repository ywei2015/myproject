package com.talkweb.xwzcxt.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.talkweb.common.AppConstants;
import com.talkweb.twdpe.base.org.service.UserService;
import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.twdpe.web.session.main.Session;
import com.talkweb.twdpe.web.session.util.SessionFactory;
import com.talkweb.xwzcxt.pojo.StdFileAffixPojo;
import com.talkweb.xwzcxt.pojo.TaskMouldPojo;
import com.talkweb.xwzcxt.pojo.TaskStatusPojo;
import com.talkweb.xwzcxt.pojo.TaskStepResultPojo;
import com.talkweb.xwzcxt.service.ImportFileDBService;
import com.talkweb.xwzcxt.service.TaskSearchService;
public class WaithandTaskMngAction extends BusinessAction {

	/**
	 * 待办任务管理类
	 * YangChen
	 * 2014-7-29 create
	 */
	private static final long serialVersionUID = -1640252084859915375L;
	private static String iserrFlag="0";
	public static String getIserrFlag() {
		return iserrFlag;
	}
	public static void setIserrFlag(String iserrFlag) {
		WaithandTaskMngAction.iserrFlag = iserrFlag;
	}

	//可以支持的执行方式
	private List<String> traceFunSupported=new ArrayList<String>();
	@Autowired
	private TaskSearchService taskSearchService;
	@Autowired
	private UserService userService;
	@Autowired
	private ImportFileDBService importFileDBService;
	@Autowired
	public StdFileAction stdFileAction;
	@Autowired
    AppConstants appConstants;
	
	private TaskMouldPojo taskMouldPojo=new TaskMouldPojo();
	
	private TaskStepResultPojo taskStepResultPojo=new TaskStepResultPojo();
	
	//附件和附录
	private File upfileAccessory;
	private String upfileAccessoryFileName;
	private String upfileAccessoryContentType; 
	public File getUpfileAccessory() {return upfileAccessory;}
	public void setUpfileAccessory(File upfileAccessory) { this.upfileAccessory = upfileAccessory;	}
	public String getUpfileAccessoryFileName() {return upfileAccessoryFileName;	}
	public void setUpfileAccessoryFileName(String upfileAccessoryFileName) {this.upfileAccessoryFileName = upfileAccessoryFileName;}
	public String getUpfileAccessoryContentType() {	return upfileAccessoryContentType;	}
	public void setUpfileAccessoryContentType(String upfileAccessoryContentType) {this.upfileAccessoryContentType = upfileAccessoryContentType;}
	
	{
		traceFunSupported.add("文本");
		traceFunSupported.add("拍照");
		traceFunSupported.add("录音");
		traceFunSupported.add("录视频");
	}
	
	public TaskMouldPojo getTaskMouldPojo() {
		return taskMouldPojo;
	}
	
	public void setTaskMouldPojo(TaskMouldPojo taskMouldPojo) {
		this.taskMouldPojo = taskMouldPojo;
	}
	
	public TaskStepResultPojo getTaskStepResultPojo() {
		return taskStepResultPojo;
	}
	
	public void setTaskStepResultPojo(TaskStepResultPojo taskStepResultPojo) {
		this.taskStepResultPojo = taskStepResultPojo;
	}
	
	public List<String> getTraceFunSupported() {
		return traceFunSupported;
	}

	public void setTraceFunSupported(List<String> traceFunSupported) {
		this.traceFunSupported = traceFunSupported;
	}

	HttpServletRequest request = (HttpServletRequest) ActionContext
            .getContext().get(ServletActionContext.HTTP_REQUEST);
	
	HttpServletResponse response = (HttpServletResponse) ActionContext
			.getContext().get(ServletActionContext.HTTP_RESPONSE);
	
	//将0或1转化为是或否
	public String convertZeroOrOne2String(String zeroOrOne){
		String trueOrFalse="";
		if(zeroOrOne.equals("0")){
			trueOrFalse="否";
		}
		else if(zeroOrOne.equals("1")){
			trueOrFalse="是";
		}
		return trueOrFalse;
	}
	//将状态码进行转换，使之成为状态字符串
	public String convertStatusCode2String(String statusCode){
		String statusString="";
		if(statusCode.equals("0")){
			statusString="已生成";
		}
		else if(statusCode.equals("11")){
			statusString="已下发";
		}
		else if(statusCode.equals("22")){
			statusString="已接收";
		}
		else if(statusCode.equals("33")){
			statusString="已执行";
		}else if(statusCode.equals("34")){
			statusString="已验证";
		}else if(statusCode.equals("35")){
			statusString="已评价";
		}
		else if(statusCode.equals("44")){
			statusString="被取消";
		}
		return statusString;
	}
	
	//内部类
	public class InnerList<T>{
		List<T> list=new ArrayList<T>();

		public List<T> getList() {
			return list;
		}

		public void setList(List<T> list) {
			this.list = list;
		}
	}
	//对分页进行配置
	@SuppressWarnings({ "rawtypes" })
	public Object initPagination(InnerList list,int pageSize,HttpServletRequest request){
		String page=request.getParameter("page");
		String requestPageSizeString=request.getParameter("pageSize");
		int requestPageSize=Integer.parseInt(requestPageSizeString);
		Object currentList=new Object();
		int start=0;
		int end=0;
		if(pagination==null){
			pagination=new Pagination();
		}
		pagination.setCurrPage(Integer.parseInt(page));
		pagination.setCount(list.getList().size());
		//是否请求的pageSize有所改变
		if(requestPageSize==pageSize){
			pagination.setSize(pageSize);
		}
		else{
			pagination.setSize(requestPageSize);
		}
		//如果总记录除以size可以整除，则无需将总页数+1
		if(pagination.getCount()%pagination.getSize()==0){
			pagination.setAllPage(pagination.getCount()/pagination.getSize());
		}
		else{
			pagination.setAllPage(pagination.getCount()/pagination.getSize()+1);
		}
		start=(pagination.getCurrPage()-1)*pagination.getSize();
		//是否为最后一页
		if(pagination.getCurrPage()<pagination.getAllPage()){
			end=pagination.getCurrPage()*pagination.getSize();
		}
		else{
			end=pagination.getCount();
		}
		currentList=list.getList().subList(start, end);
		pagination.setNeedCount(true);
		return currentList;	
	}
	
	public String getArrangeTask(){
		taskMouldPojo.setC_task_kind(3);
		return getDefaultWaithandTasks();
	}
	
	public String getWorkTask(){
		String type = request.getParameter("type")==null?"":request.getParameter("type");
		if(type.equals("1")){
		taskMouldPojo.setC_tasktemplet_id(0);
		taskMouldPojo.setSubmit("提交");
		taskMouldPojo.setC_id(0);
		taskMouldPojo.setType("1");
		}
		
		taskMouldPojo.setC_task_kind(1);
		return getDefaultWaithandTasks();
	}
	
	//根据当前登陆的用户查询待办任务
	@SuppressWarnings("unchecked")
	public String getDefaultWaithandTasks(){
		try{
		
		List<String> statusList=new ArrayList<String>();
		//添加状态id
		if(taskMouldPojo.getC_status()!=null&&!"".equals(taskMouldPojo.getC_status())){
			statusList.add(taskMouldPojo.getC_status());
		}
		else{
			statusList.add("11");
			statusList.add("22");
		}
		taskMouldPojo.setC_status_list(statusList);
		String extype = taskMouldPojo.getExtype()==null?"":taskMouldPojo.getExtype();
		if(!extype.equals("1")){
		taskMouldPojo.setC_confirm_userid(this.getLongUserID()+"");
		}
		if(pagination==null){
			pagination=new Pagination(1,15);
		}
		String type = taskMouldPojo.getType()==null?"":taskMouldPojo.getType();
		if(type.equals("1")){
			Map map = new HashMap();
			pagination = new Pagination(1,99999);
			pagination.setOnlyCount(true);
			List<TaskMouldPojo> taskList=taskSearchService.queryTaskInfoListByCondition(taskMouldPojo,pagination);
			map.put("rwzx", taskList.size());
			this.formatData(map);
			return SUCCESS;
		}
		
		List<TaskMouldPojo> taskList=taskSearchService.queryTaskInfoListByCondition(taskMouldPojo,pagination);
		
		for(TaskMouldPojo pojo:taskList){
//			if(pojo.getC_task_name()!=null&&!"".equals(pojo.getC_task_name())){
//				pojo.setC_task_name("<a name=taskLink onclick=submitHiddenForm('"+pojo.getC_task_id()+
//				"') style=text-decoration:none;display:block; title="+pojo.getC_task_name()+
//				">"+pojo.getC_task_name()+"</a>");
//			}
			if(pojo.getC_iskeyctrl()!=null&&!"".equals(pojo.getC_iskeyctrl())){
				pojo.setC_iskeyctrl_name(this.convertZeroOrOne2String(pojo.getC_iskeyctrl()));
			}
			if(pojo.getC_issequence()!=null&&!"".equals(pojo.getC_issequence())){
				pojo.setC_issequence_name(this.convertZeroOrOne2String(pojo.getC_issequence()));
			}
			if(pojo.getC_status()!=null&&!"".equals(pojo.getC_status())){
				//pojo.setC_status_name(this.convertStatusCode2String(pojo.getC_status()));
				if("11".equals(pojo.getC_status())){
						pojo.setC_status_name("未执行");
					
				}else if("22".equals(pojo.getC_status())){
						pojo.setC_status_name("进行中");
					
				}
			}
			if(pojo.getWatchStdAction()==null||"".equals(pojo.getWatchStdAction())){
				pojo.setWatchStdAction("<a href='#' style='text-decoration:none;display:block;' title='点击查看'>查看</a>");
			}
			
//			if(pojo.getC_task_typename().equals("临时任务")){
//				pojo.setC_task_typename("工作安排");
//			}else if(pojo.getC_task_typename().equals("协调任务")){
//				pojo.setC_task_typename("异常反馈"); style=text-decoration:none;display:none;
//			}
			pojo.setSubmit("<a name=submitButton id='submit' onclick=executeTask('"+pojo.getC_task_id()+"') title=提交任务>"+pojo.getSubmit()+"</a>");
		   
			
		
		}
		
		/*InnerList<TaskMouldPojo> innerList=this.new InnerList<TaskMouldPojo>();
		innerList.setList(taskList);
		List<TaskMouldPojo> currentList=(List<TaskMouldPojo>)(this.initPagination(innerList, 5,request));
		*/
		
		this.formatDatagirdData(taskList, pagination);
		}catch(Exception e){
			e.printStackTrace();
			
		}
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String queryNewTask(){
		try{
			taskMouldPojo.setC_create_userid(this.getLongUserID()+"");
			Map map=new HashMap();
			map.put("c_create_userid", taskMouldPojo.getC_create_userid());
			map.put("c_status", taskMouldPojo.getC_status());
			map.put("c_manage_section", taskMouldPojo.getC_manage_section());
			List taskList=new ArrayList();
			
			if(pagination==null){				
				pagination=new Pagination(1,15);
			}
			
			taskList=taskSearchService.queryNewTask(map,taskList,pagination);
			
			
			/*InnerList<TaskMouldPojo> innerList=this.new InnerList<TaskMouldPojo>();
			innerList.setList(taskList);
			List<TaskMouldPojo> currentList=(List<TaskMouldPojo>)(this.initPagination(innerList, 10,request));
			*/
			
			for(Object ob:taskList){
				TaskMouldPojo pojo=(TaskMouldPojo)ob;
				if(pojo.getC_task_name()!=null&&!"".equals(pojo.getC_task_name())){
					pojo.setC_task_name("<a name=taskLink onclick=submitHiddenForm('"+pojo.getC_task_id()+
					"') style=text-decoration:none;display:block; title="+pojo.getC_task_name()+
					">"+pojo.getC_task_name()+"</a>");
				}
				
				if(pojo.getC_status()!=null&&!"".equals(pojo.getC_status())){
					//pojo.setC_status_name(this.convertStatusCode2String(pojo.getC_status()));
					if("11".equals(pojo.getC_status())){			
							pojo.setC_status_name("未接收");
					}else if("22".equals(pojo.getC_status())){
							pojo.setC_status_name("已接收");
						
					}
				}
				
			}

			this.formatDatagirdData(taskList, pagination);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	//获取执行行动步骤及结果
	@SuppressWarnings("unchecked")
	public String getTaskStepAndResult(){
		try{
		List<TaskStepResultPojo> list=new ArrayList<TaskStepResultPojo>();
		String taskId=request.getParameter("c_task_id");
		if(taskId!=null&&!"".equals(taskId)){
			
			if(pagination==null){
				pagination=new Pagination(1,15);
			}
			list=taskSearchService.getTaskStepAndResult(taskId,pagination);
			
			for(TaskStepResultPojo pojo:list){
				if(pojo.getC_tracefun_name()!=null&&!"".equals(pojo.getC_tracefun_name())){
					if(this.getTraceFunSupported().contains(pojo.getC_tracefun_name())){
				       
						pojo.setC_tracefun_name("<a href='javaScript:void(0);' onclick=showSrcDialog(this) style='text-decoration:none;display:block;' title='点击执行'>"+pojo.getC_tracefun_name()+"</a>");
					}
					else{
						pojo.setC_tracefun_name(pojo.getC_tracefun_name());
					}
					
					if(!pojo.getC_tracefun_name().contains("文本") && pojo.getC_result()!=null && !"".equals(pojo.getC_result())){
					    StdFileAffixPojo file = taskSearchService.queryImgPath(pojo.getC_result());
					    if(file!=null){
    					    String filepath = appConstants.getIMG_URL()+file.getC_file_url();
    					    //System.out.println("filepath=========="+filepath);
    					    pojo.setC_result("<a onclick=showImgDialog('"+filepath+"') style='text-decoration:none;display:block;' title='点击查看'>点击查看</a>");
					    }
					}
				}
			}
		}
		
		
		/*InnerList<TaskStepResultPojo> innerList=this.new InnerList<TaskStepResultPojo>();
		innerList.setList(list);
		List<TaskStepResultPojo> currentList=(List<TaskStepResultPojo>)this.initPagination(innerList, 5,request);
		*/
		
		this.formatDatagirdData(list, pagination);
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	//修改任务步骤的状态和结果
	public String updateTaskStepAndResult(){
		taskSearchService.updateTaskStepInfo(taskStepResultPojo);
		return SUCCESS;
	}
	//附件上传完整版
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String uploadAffixFile() {
		String fileId="";
		Map pageData = new HashMap();
		Map params = new HashMap();
		Map user = new HashMap();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/javascript;charset=UTF-8");
		String filename = null;
		try {
			filename = java.net.URLDecoder.decode(request.getParameter("c_filename"),"UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}			
		Session session = SessionFactory.getInstance(request, response);
        user = (Map) session.getAttribute("USERSESSION");
        if (user != null){
        }else{
            pageData.put("msg", "网页已过期，请重新登陆!");
            pageData.put("result",false);
            this.formatData(pageData);
            return SUCCESS;
        }
		params.put("c_sfile_id", request.getParameter("c_sfile_id"));
		params.put("c_filetypes", request.getParameter("c_filetypes"));
		params.put("c_filename", filename); 
		params.put("c_extens", request.getParameter("c_extens")); 
		params.put("c_filekind", request.getParameter("c_filekind"));
		params.put("debug", request.getParameter("debug"));
		params.put("c_Affix_type", request.getParameter("c_Affix_type"));
		params.put("c_chaptercode", request.getParameter("c_chaptercode"));	

		Method method = null;
		try {
			method = stdFileAction.getClass().getDeclaredMethod("formatFileType", String.class);
			method.setAccessible(true);
		} catch (NoSuchMethodException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String filetype = null;
		try {
			filetype = (String) method.invoke(stdFileAction, this.upfileAccessoryContentType);
			if(filetype.endsWith("gif")||filetype.endsWith("GIF")){
				
				filetype=filetype.substring(filetype.lastIndexOf("/")+1, filetype.length()+1);
				
			}
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalArgumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InvocationTargetException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//String c_file_id = this.SaveFileToServer(this.upfileAccessory, "1",filetype, "2");
		Method saveMethod = null;
		try {
			saveMethod = stdFileAction.getClass().getDeclaredMethod("SaveFileToServer", File.class,String.class,String.class,String.class);
			saveMethod.setAccessible(true);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(saveMethod!=null){
			try {
				fileId=(String) saveMethod.invoke(stdFileAction, this.upfileAccessory, params.get("c_filetypes"),filetype, "2");
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//再修改状态
		taskStepResultPojo.setC_id(Long.parseLong((String)params.get("c_sfile_id")));
		taskStepResultPojo.setC_result(fileId);
		taskSearchService.updateTaskStepInfo(taskStepResultPojo);
		
		pageData.put("result", true);
        pageData.put("msg", "附件上传完毕");
        this.formatData(pageData);
		return SUCCESS;
	}
	
	
	//是否已执行所有步骤
	public void isAllTaskStepsComplete(){
		String taskId=request.getParameter("taskId");
		PrintWriter pw=null;
		boolean flag=true;
		try {
			pw=response.getWriter();
			if(taskId!=null&&!"".equals(taskId)){
				List<TaskStepResultPojo> pojos=taskSearchService.getTaskStepAndResult(taskId,null);
				for(TaskStepResultPojo pojo:pojos){
					if(this.getTraceFunSupported().contains((String)pojo.getC_tracefun_name())){
						if(null==pojo.getC_result()||pojo.getC_result().equals("")){
							flag=false;
							break;
						}
					}
				}
				if(flag){
					pw.print("{\"result\":\"yes\"}");
				}
				else{
					pw.print("{\"result\":\"no\"}");
				}
				pw.flush();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			pw.close();
		}
	}
	//修改任务状态为已执行
	public String executeTask(){
		String  result="更新失败！";
		try{
		String taskId=request.getParameter("taskId");
		TaskStatusPojo pojo=new TaskStatusPojo();
		pojo.setV_task_id(Long.parseLong(taskId));
		pojo.setV_status("33");
		pojo.setV_iserror(iserrFlag);
		pojo.setV_cancel_cause("");
		pojo.setV_handle_des("");
		pojo.setC_return_code("");
		taskSearchService.updateTaskStatusByProc(pojo);	
	    result="成功执行任务!";	
	    this.formatData(result);
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	//保存备注
	public String saveBz(){
		try{
		String taskId=request.getParameter("taskId");
		String beizhu=request.getParameter("beizhu");
		Map z_bei = new HashMap();
		z_bei.put("taskId", taskId);
		z_bei.put("beizhu", beizhu);
		taskSearchService.saveBz(z_bei);	
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	public String getBz(){
		try{
			String taskId=request.getParameter("taskId");
			Map z_bei = new HashMap();
			z_bei.put("taskId", taskId);
			String beizhu=taskSearchService.getBz(z_bei);
			if(beizhu!=null){
			this.formatData(beizhu);
			}
			}catch(Exception e){
				e.printStackTrace();
			}
		return SUCCESS;
	}
	//发起任务
    public String newTask(){
        Session session = SessionFactory.getInstance(request, response);
        Map user = new HashMap();
        String loginUserID ="";
        if (user != null){
            user = (Map) session.getAttribute("USERSESSION");
            loginUserID = user.get("id").toString();
        }

        TaskMouldPojo pojo=new TaskMouldPojo();
        pojo.setV_task_type(taskMouldPojo.getC_task_type());
        pojo.setV_task_name(taskMouldPojo.getC_task_name());
        pojo.setV_manage_section(taskMouldPojo.getC_manage_section());
        pojo.setV_start_time(taskMouldPojo.getC_start_time());
        pojo.setV_end_time(taskMouldPojo.getC_end_time());
        pojo.setV_exec_userid(taskMouldPojo.getC_exec_userid());
        pojo.setV_sender_userid(loginUserID);
        pojo.setV_remark(taskMouldPojo.getC_remark());
        String task_id = taskSearchService.newTask(pojo);
        
        Map pageData = new HashMap();
        if(task_id!=null){
            
            pageData.put("result", true);
            pageData.put("msg", "发起任务成功");
            
        }else{
            pageData.put("result", false);
            pageData.put("msg", "发起任务失败");
        }
        this.formatData(pageData);
        return SUCCESS;
    }
	
/*    public String getAreaInfo(){
    	try{
    	List list=new ArrayList() ,list2=taskSearchService.getAreaInfo();
    	TaskMouldPojo task;
    	for(Object ob:list2){
    		Map map=new HashMap();
    		task=(TaskMouldPojo)ob;
    		String id=task.getArea();
    		String name=task.getAreaname();
    		map.put("text", name);
    		map.put("value", id);
    		list.add(map);
    	}
    	this.formatData(list);
    	}catch(Exception e){
    		this.formatData("");
    		e.printStackTrace();
    	}
    	return SUCCESS;
    }  */


}
