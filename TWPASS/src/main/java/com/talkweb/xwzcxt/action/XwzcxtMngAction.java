package com.talkweb.xwzcxt.action;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.PartSource;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.struts2.ServletActionContext;
import org.springframework.asm.commons.Method;
import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.common.AppConstants;
import com.talkweb.common.Common;
import com.talkweb.twdpe.base.org.pojo.User;
import com.talkweb.twdpe.core.data.DataMap;
import com.talkweb.twdpe.core.data.IData;
import com.talkweb.twdpe.core.log.Logger;
import com.talkweb.twdpe.core.log.LoggerFactory;
import com.talkweb.twdpe.web.jcontrols.RuleSelect;
import com.talkweb.twdpe.web.session.main.Session;
import com.talkweb.twdpe.web.session.util.SessionFactory;
import com.talkweb.xwzcxt.common.BasicUploadFile;
import com.talkweb.xwzcxt.common.XwzcxtCommon.Enum_Xwzcxt;
import com.talkweb.xwzcxt.pojo.BasicMouldPojo;
import com.talkweb.xwzcxt.pojo.GeneralItem;
import com.talkweb.xwzcxt.pojo.TaskMouldPojo;
import com.talkweb.xwzcxt.service.impl.XwzcxtMngImp;

public class XwzcxtMngAction extends BusinessAction {	 
	
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(XwzcxtMngAction.class);
    private String roleID= null;
   
    @Autowired
    AppConstants appConstants;
    
    //分页获取任务
    public String queryTaskByPage() {
		if (taskMouldPojo == null)
			taskMouldPojo = new TaskMouldPojo();
		this.excludeNullProperties(taskMouldPojo);
		try {
            HttpServletRequest request = ServletActionContext.getRequest();
            HttpServletResponse response = ServletActionContext.getResponse();
            List<TaskMouldPojo> list  = null;
            String action  = request.getParameter("action");  
            String taskId = null;
            
            String userid="";
            Session session = SessionFactory.getInstance(request, response);
            Map user = (Map) session.getAttribute("USERSESSION");
            if (user == null){
            	Map<String,String> pageData = new HashMap<String,String>();
            	pageData.put("msg", "网页已过期，请重新登陆");
            	return SUCCESS;
            }
            userid = user.get("id").toString();
            
            switch(Integer.parseInt(action)){
                case 1:
                    list = xwzcxtMngImp.GetCurrentRecordByPage(taskMouldPojo,pagination, Enum_Xwzcxt.TASK_MOULD);
                	break;
                case 2:
                    list = xwzcxtMngImp.GetCurrentRecordByPage(taskMouldPojo,pagination, Enum_Xwzcxt.TASK_TEMPLET_CHILD);
                    break;
                case 3://任务主体信息列表
                    list = xwzcxtMngImp.GetCurrentRecordByPage(taskMouldPojo,pagination, Enum_Xwzcxt.TASK_INFO_LIST);
                    break;
                case 4://任务步骤信息列表
                    taskId = request.getParameter("taskId");
                    if(taskId != null){
                        taskMouldPojo.setC_task_id(taskId);
                    }
                    list = xwzcxtMngImp.GetCurrentRecordByPage(taskMouldPojo,pagination, Enum_Xwzcxt.TASK_STEP_LIST);
                    break;  
                case 5://任务模板管理
                    list = xwzcxtMngImp.GetCurrentRecordByPage(taskMouldPojo,pagination, Enum_Xwzcxt.TASK_RULE_MOULD);
                    break;
                case 6://模板标准项明细
                    list = xwzcxtMngImp.GetCurrentRecordByPage(taskMouldPojo,pagination, Enum_Xwzcxt.TASK_RULE_MOULD_ITEM);                    
                    break;
                    
                //Add By Rita.Zhou for MyTask
                case 20://待办任务
                	 list = xwzcxtMngImp.GetCurrentRecordByPage(userid,pagination, Enum_Xwzcxt.TASK_READY_TASK);
                	break;
                case 21://已办任务
                	list = xwzcxtMngImp.GetCurrentRecordByPage(userid,pagination, Enum_Xwzcxt.TASK_DONE_TASK);
                	break;
                case 22://异常跟踪
                	Map m = new HashMap();
                	m.put("id", super.getUserSession().get("id"));
                	m.put("type", taskMouldPojo.getC_task_errtasksearchtype());
                	list = xwzcxtMngImp.GetCurrentRecordByPage(m,pagination, Enum_Xwzcxt.TASK_ERROR_TRACE);
                	break;
            }       	    
		    this.formatDatagirdData(list, pagination);//格式化DataGrid控件数据
		} catch (Exception e) {
			ServletActionContext.getRequest().setAttribute("throw", e);
			@SuppressWarnings("rawtypes")
			List emptylist = new ArrayList();
			this.formatDatagirdData(emptylist, pagination);//格式化DataGrid控件数据
		    logger.error(e.getMessage(), e);
		}
		taskMouldPojo = null;
		return SUCCESS;
    }
    public String queryEntryList(){
        if (taskMouldPojo == null)
            taskMouldPojo = new TaskMouldPojo();
        this.excludeNullProperties(taskMouldPojo);
        List<TaskMouldPojo> list  = null;
        Enum_Xwzcxt enum_Xwzcxt = null;
        HttpServletRequest request = ServletActionContext.getRequest ();
        
        String action = request.getParameter ("action");
        try {
            switch (Integer.parseInt (action))
            {              
                case 1://任务详情步骤信息
                	if (filelist != null && filelist.size()>0)
                		filelist.clear();
                    enum_Xwzcxt = Enum_Xwzcxt.TASK_RULE_MOULD_STEP;
                    break;                
            }
            list = xwzcxtMngImp.GetCurrentRecordByPage(taskMouldPojo,enum_Xwzcxt);    
            this.formatData (list);// 给对象属性增加统一的前缀名，并格化输出                
        }
        catch (Exception e) {
            request.setAttribute ("throw", e);
            // 如果出错，则输出一个空的JSON对象
            this.setErrorMessage (e);
            logger.error (e.getMessage (), e);
        }
        return SUCCESS;
    }
    public String queryTaskEntry(){
        if (taskMouldPojo == null)
            taskMouldPojo = new TaskMouldPojo();
        this.excludeNullProperties(taskMouldPojo);
        Enum_Xwzcxt enum_Xwzcxt = null;
        HttpServletRequest request = ServletActionContext.getRequest ();
        
        String action = request.getParameter ("action");
        try {
            switch (Integer.parseInt (action))
            {
                // 基础项信息
                case 1:
                    //taskMouldPojo.setC_tasktemplet_id(request.getParameter ("taskId"));
                    enum_Xwzcxt = Enum_Xwzcxt.TASK_MOULD_DETAILS;
                    break;  
                case 2://任务主体信息详细
                    taskMouldPojo.setC_task_id(request.getParameter ("taskId"));
                    enum_Xwzcxt = Enum_Xwzcxt.TASK_INFO_DETAIL;
                    break;  
                case 3://任务步骤信息详细
                    taskMouldPojo.setC_task_id(request.getParameter ("taskId"));
                    enum_Xwzcxt = Enum_Xwzcxt.TASK_STEP_DETAIL;
                    break;
                case 5://任务模板管理
                    enum_Xwzcxt = Enum_Xwzcxt.TASK_RULE_MOULD;
                    break;
            }
            taskMouldPojo = xwzcxtMngImp.queryTobaccoEntry (enum_Xwzcxt, taskMouldPojo); 
            String aa=taskMouldPojo.getC_remark();
            this.formatData (this.addPrefix (taskMouldPojo, "taskMouldPojo."));// 给对象属性增加统一的前缀名，并格化输出
        }
        catch (Exception e) {
            request.setAttribute ("throw", e);
            // 如果出错，则输出一个空的JSON对象
            this.setErrorMessage (e);
            logger.error (e.getMessage (), e);
        }
        return SUCCESS;

    }
    
    public String editData () {
        if (taskMouldPojo == null)
            taskMouldPojo = new TaskMouldPojo();        
        this.excludeNullProperties(taskMouldPojo); 
        String userID = "";
        Map<String ,Object> map = super.getUserSession();
        if(map != null){
            userID = map.get("id").toString();
        }else{
            userID = "1";
        }
        boolean bl = false;
        HttpServletRequest request = ServletActionContext.getRequest ();
        Enum_Xwzcxt enum_Xwzcxt = null;
        String action = request.getParameter ("action");
        String edit = request.getParameter ("edit");
        try {
            IData param = new DataMap();
            String nowTime = common.GetNowTime();
            switch(Integer.parseInt (action)){
                case 1:
                    enum_Xwzcxt = Enum_Xwzcxt.TASK_RULE_MOULD;                    
                    taskMouldPojo.setC_creator("admin");              
                    taskMouldPojo.setC_last_modifier("admin");            
                    taskMouldPojo.setC_last_modified_time(nowTime);
                    taskMouldPojo.setC_isdelete("0");
                    if(edit.equals("update")){
                        bl = xwzcxtMngImp.ExecuteUpdate (enum_Xwzcxt, taskMouldPojo);
                    }else{               
                        taskMouldPojo.setC_create_time(nowTime);  
                        bl = xwzcxtMngImp.ExecuteInsert (enum_Xwzcxt, taskMouldPojo);
                    }
                    if(bl){
                        //删除模板对应的数据项
                        xwzcxtMngImp.Delete (Enum_Xwzcxt.TASK_MOULD,String.valueOf(taskMouldPojo.getC_tasktemplet_id()));
                        enum_Xwzcxt = Enum_Xwzcxt.TASK_RULE_MOULD_ITEM;    
                        String[] arrBasic = taskMouldPojo.getC_actitem_id().split(",");
                        for(int i=0;i<arrBasic.length;i++){                 
                            taskMouldPojo.setC_actitem_id(arrBasic[i]);
                            bl = xwzcxtMngImp.ExecuteInsert(enum_Xwzcxt, taskMouldPojo);
                            if(!bl)
                                break;
                        }
                    }
                break;
                //任务下发
                case 2:
                    bl = xwzcxtMngImp.ExecutePro(Enum_Xwzcxt.TASK_MOULD,taskMouldPojo);
                    break;
                //确认下发
                case 3:
                    //0-刚生成，11-刚确认，22-刚送达或被接收，33-执行完成并接收到结果
                    taskMouldPojo.setC_status("11");
                    taskMouldPojo.setC_confirm_time(common.GetNowTime());                    
                    taskMouldPojo.setC_confirm_userid(userID);
                    bl = xwzcxtMngImp.ExecuteUpdate(Enum_Xwzcxt.TASK_STEP_DETAIL,taskMouldPojo);
                    break;
            }
            this.setMessage(bl ? 1 : 0, "Edit", "Edit_Info");
        }
        catch (Exception e) {
            request.setAttribute ("throw", e);
            this.setMessage (0, "Edit", "Edit_info", e);
            logger.error (e.getMessage (), e);
        }
        return SUCCESS;
    }
    
    public String deleteStepByCid(){
    	 if (taskMouldPojo == null)
             taskMouldPojo = new TaskMouldPojo();        
         this.excludeNullProperties(taskMouldPojo); 
         
         HttpServletRequest request = ServletActionContext.getRequest ();
         Enum_Xwzcxt enum_Xwzcxt = null;
         String deleteID = request.getParameter("deleteID");
         try{
	         if (xwzcxtMngImp.deleteStepByCid(deleteID) > 0){
	        	 this.setMessage(1, "Edit", "Edit_Info");
	         }
         }catch(Exception e){
        	 request.setAttribute ("throw", e);
             this.setMessage (0, "Edit", "Edit_info", e);
             logger.error (e.getMessage (), e);
         }
         return SUCCESS;
    }
    
    public String cancelTaskById(){
    	if (taskMouldPojo == null)
            taskMouldPojo = new TaskMouldPojo();        
        this.excludeNullProperties(taskMouldPojo); 
        
        HttpServletRequest request = ServletActionContext.getRequest ();
        Enum_Xwzcxt enum_Xwzcxt = null;
        String deleteID = request.getParameter("deleteID");
        try{
	        if (xwzcxtMngImp.cancelTaskById(deleteID) > 0){
	        	this.setMessage(1, "Edit", "Edit_Info");
	        }
        }catch(Exception e){
       	 request.setAttribute ("throw", e);
            this.setMessage (0, "Edit", "Edit_info", e);
            logger.error (e.getMessage (), e);
        }
        return SUCCESS;
    }
	
    public String deleteInfo(){
        if (taskMouldPojo == null)
            taskMouldPojo = new TaskMouldPojo();        
        this.excludeNullProperties(taskMouldPojo); 
        
        //Object userID = super.getUserSession().get("id");
        HttpServletRequest request = ServletActionContext.getRequest ();
        Enum_Xwzcxt enum_Xwzcxt = null;
        String action = request.getParameter ("action");
        String deleteID = request.getParameter("deleteID");
        try {
            switch(Integer.parseInt (action)){
                case 1:
                    enum_Xwzcxt = Enum_Xwzcxt.TASK_RULE_MOULD_ITEM;
                    break;
                case 2:
                    enum_Xwzcxt = Enum_Xwzcxt.TASK_RULE_MOULD;
                    break;
                case 3:
                    enum_Xwzcxt = Enum_Xwzcxt.TASK_STEP_DETAIL;
                    break;
                case 4:
                    enum_Xwzcxt = Enum_Xwzcxt.TASK_INFO_LIST;
                    break;
            }
            xwzcxtMngImp.Delete (enum_Xwzcxt,deleteID);
            this.setMessage(1, "Edit", "Edit_Info");
        }
        catch (Exception e) {
            request.setAttribute ("throw", e);
            this.setMessage (0, "Edit", "Edit_info", e);
            logger.error (e.getMessage (), e);
        }
        return SUCCESS;
    }
    @Autowired
    private XwzcxtMngImp xwzcxtMngImp;
    @Autowired
    private Common common;

    private TaskMouldPojo taskMouldPojo = null;
    private BasicMouldPojo basicMouldPojo = null;

    public TaskMouldPojo getTaskMouldPojo()
    {
        return taskMouldPojo;
    }
    public void setTaskMouldPojo(TaskMouldPojo taskMouldPojo)
    {
        this.taskMouldPojo = taskMouldPojo;
    }
	public BasicMouldPojo getBasicMouldPojo() {
		return basicMouldPojo;
	}
	public void setBasicMouldPojo(BasicMouldPojo basicMouldPojo) {
		this.basicMouldPojo = basicMouldPojo;
	}

	//Add By Rita.Zhou for Do My Task
	private String saveFile(BasicUploadFile f){
		HttpClient client = new HttpClient( ); 
		//String url = "http://10.75.97.147:8080/CyxwglWebInService/filemanageraction!UpdateFileInfo.action";
		String url = appConstants.getFILE_URL()+"/filemanageraction!UpdateFileInfo.action";
		PostMethod method = new PostMethod(url);
		Part[] params = new Part[4]; 
		String ret = "";
		try{
			StringPart stringPart1 = new StringPart("c_filetypes","1");  
			StringPart stringPart2 = new StringPart("c_extens",f.getContentType());  
			StringPart stringPart3 = new StringPart("c_filekind","2");  
			
			params[0]=stringPart1;
			params[1]=stringPart2;
			params[2]=stringPart3;
			
			FilePart filePart = new FilePart("files", f.getFile());  
			params[3]=filePart;
			params[4]=new StringPart("debug","0");
			MultipartRequestEntity post =  new MultipartRequestEntity(params, method.getParams());  

			method.setRequestEntity(post);  
			client.executeMethod(method); 
			JSONObject obj = JSONObject.fromObject(method.getResponseBodyAsString());
			ret = obj.getString("fileID");
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return ret;
	}
	
	public String submitStepDetails(){
		HttpServletRequest request = ServletActionContext.getRequest ();
		int ret = -1;
		int result = -1;
    	String msg = "";
		Map pageData = new HashMap();
        Map param = new HashMap();
        try{
        	param.put("taskid", request.getParameter("TaskId"));
        	param.put("now", new Date());
        	param.put("handledes", request.getParameter("HandleDes"));
        	int stepCount = Integer.parseInt(request.getParameter("StepCount"));
	        if (request.getParameter("IsStd").equals("1")){//标准任务
	        	for (int i = 1; i<=stepCount; i++){
	        		String index = request.getParameter("StepIndex"+i);
	        		param.put("stepindex", index);
	        		if (request.getParameter("IsFile"+index).equals("1")){//是文件
	        			//调用辉辉的接口，将文件存储到数据库
	        			String retValue="";
	        			for (int k1=0; k1<filelist.size();k1++){
	        				BasicUploadFile temp = filelist.get(k1);
	        				if (temp.getIndex().equals(i+"")){
	        					retValue = saveFile(temp);
	        					break;
	        				}
	        			}
	        			//将接口返回值填入数据库
	        			param.put("result", retValue);
	        		}else{//是文本
	        			param.put("result",request.getParameter("Result"+index));
	        		}
	        		ret = xwzcxtMngImp.submitStandardStepDetails(param);
	        	}
	        }else{//非标准任务
	        	for (int j = 1; j<=stepCount; j++){
	        		String index = request.getParameter("StepIndex"+j);
	        		param.put("stepindex", index);
	        		param.put("tracefunid", request.getParameter("TracefunId"+index));
	        		if (request.getParameter("IsFile"+index).equals("1")){//是文件
	        			//调用辉辉的接口，将文件存储到数据库
	        			String retValue="";
	        			for (int k2=0; k2<filelist.size();k2++){
	        				BasicUploadFile temp = filelist.get(k2);
	        				if (temp.getIndex().equals(j+"")){
	        					retValue = saveFile(temp);
	        					break;
	        				}
	        			}
	        			//将接口返回值填入数据库
	        			param.put("result", retValue);
	        		}else{//是文本
	        			param.put("result",request.getParameter("Result"+index));
	        		}
	        		ret = xwzcxtMngImp.submitUnstandardStepDetails(param);
	        	}
	        }
	        
	        if (ret > 0){//对step表操作正常，则写入task表
	        	ret = xwzcxtMngImp.submitTaskStep(param);
	        }
	        
	        if (ret > 0){
	        	msg = "执行成功！";
				result = 0;
				
				if (filelist != null)
					filelist.clear();
	        }else{
	        	msg = "执行失败，请检查填写内容";
				result = -1;
	        }
        }catch(Exception e){
        	msg = "执行失败，请检查填写内容";
			result = -1;
			e.printStackTrace();
        }
        
        pageData.put("msg", msg);
		pageData.put("result", result);
		this.formatData(pageData);
		return SUCCESS;
	}
	
	public String getTaskMouldById(){
    	HttpServletRequest request = ServletActionContext.getRequest();
    	try{
    		TaskMouldPojo data = xwzcxtMngImp.getTaskMouldById(request.getParameter("cid"));
    		if (data != null){
    			this.formatData(this.addPrefix(data, "taskMouldPojo."));
    		}
    		
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return SUCCESS;
    }
	
	public String deleteObjectByIds(){
		HttpServletRequest request = ServletActionContext.getRequest ();
		int result = -1;
    	String msg = "";
		Map pageData = new HashMap();
		String ids = request.getParameter("deleteID");
		String[] idArr = ids.split(",");
		try{
			if(xwzcxtMngImp.deleteObjectByIds(idArr) > 0){
				msg = "执行成功！";
				result = 0;
			}else{
				msg = "删除失败";
				result = -1;
			}
			
		}catch(Exception e){
			msg = "删除失败";
			result = -1;
			e.printStackTrace();
		}
		
		pageData.put("msg", msg);
		pageData.put("result", result);
		this.formatData(pageData);
		return SUCCESS;
	}
	
	public String getStepDetails(){
		HttpServletRequest request = ServletActionContext.getRequest ();
		int result = -1;
    	String msg = "";
		Map pageData = new HashMap();
		String id = request.getParameter("cid");
		try{
			TaskMouldPojo data = xwzcxtMngImp.getStepDetails(id);
			if(data != null){
				msg = "执行成功！";
				result = 0;
			}else{
				msg = "获取步骤信息失败";
				result = -1;
			}
		}catch(Exception e){
			msg = "获取步骤信息失败";
			result = -1;
			e.printStackTrace();
		}
		pageData.put("msg", msg);
		pageData.put("result", result);
		this.formatData(pageData);
		return SUCCESS;
	}
	
	public String editStepDetails(){
		HttpServletRequest request = ServletActionContext.getRequest ();
		int result = -1;
    	String msg = "";
		Map pageData = new HashMap();
		String id = request.getParameter("cid");
		taskMouldPojo.setC_id(Integer.parseInt(id));
		try{
			if(xwzcxtMngImp.editStepDetails(taskMouldPojo) > 0){
				msg = "修改步骤信息成功！";
				result = 0;
			}else{
				msg = "修改步骤信息失败";
				result = -1;
			}
		}catch(Exception e){
			msg = "修改步骤信息失败";
			result = -1;
			e.printStackTrace();
		}
		pageData.put("msg", msg);
		pageData.put("result", result);
		this.formatData(pageData);
		return SUCCESS;
	}
	
	public String getAllWorkObjectType(){
		HttpServletRequest request = ServletActionContext.getRequest ();
		int result = -1;
    	String msg = "";
		Map pageData = new HashMap();
		try{
			List<TaskMouldPojo> data = xwzcxtMngImp.getAllWorkObjectType();
			if(data != null && data.size()>0){
				msg = "修改步骤信息成功！";
				result = 0;
			}else{
				msg = "修改步骤信息失败";
				result = -1;
			}
		}catch(Exception e){
			msg = "修改步骤信息失败";
			result = -1;
			e.printStackTrace();
		}
		pageData.put("msg", msg);
		pageData.put("result", result);
		this.formatData(pageData);
		return SUCCESS;
	}
	
	public String getAllWorkObject(){
		HttpServletRequest request = ServletActionContext.getRequest ();
		try{
			List<TaskMouldPojo> data = xwzcxtMngImp.getAllWorkObject();
			if(data != null && data.size()>0){
				RuleSelect wtree = new RuleSelect();
				wtree.setValue("c_obj_id");
				wtree.setText("c_obj_name");
				List<Map> l = this.initSelectData(data, wtree);
				this.formatData(l);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String sendTTask(){
		HttpServletRequest request = ServletActionContext.getRequest ();
		try{
			if (xwzcxtMngImp.sendTTask(taskMouldPojo)){
				this.setMessage(1, "Edit", "Edit_Info");
			}else{
				this.setMessage (0, "Edit", "Edit_info");
			}
		}catch(Exception e){
			this.setMessage (0, "Edit", "Edit_info", e);
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	private File uploadfile;
	private String uploadfileFileName;
	private String uploadfileContentType;

	private static List<BasicUploadFile> filelist;

	public File getUploadfile() {
		return uploadfile;
	}
	public void setUploadfile(File uploadfile) {
		this.uploadfile = uploadfile;
	}
	public String getUploadfileFileName() {
		return uploadfileFileName;
	}
	public void setUploadfileFileName(String uploadfileFileName) {
		this.uploadfileFileName = uploadfileFileName;
	}
	public String getUploadfileContentType() {
		return uploadfileContentType;
	}
	public void setUploadfileContentType(String uploadfileContentType) {
		this.uploadfileContentType = uploadfileContentType;
	}
	//文件上传
	public String uploadStepDetailsFile(){
		HttpServletRequest request = ServletActionContext.getRequest ();
		String index = request.getParameter("index");
		
		if (filelist == null)
			filelist = new ArrayList<BasicUploadFile>();
		
		if (index != null && !index.equals("") && Integer.parseInt(index)>0){
			BasicUploadFile temp = new BasicUploadFile();
			temp.setIndex(index);
			temp.setFile(uploadfile);
			temp.setFileName(uploadfileFileName);
			temp.setContentType(uploadfileContentType);
			filelist.add(temp);
		}
		return SUCCESS;
	}

	//批量确认任务下发 GuveXie 20140813
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String multiTaskConfirm(){
		HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        Session session = SessionFactory.getInstance(request, response);
        try {
            Map<String, Object> userMap = (Map) session.getAttribute("USERSESSION"); 
            if (userMap == null) {
                this.setErrorMessage("用户Session已过期");
            }
            else {
				Map params = new HashMap();
                params.put("c_confirm_userid", userMap.get("id").toString());//获取登录人ID 
                params.put("c_confirm_username", userMap.get("name").toString());//获取登录人ID 
				params.put("ids", request.getParameter("taskids")); 
				int effectRowCount = xwzcxtMngImp.updateMultiTaskConfirm(params); 
				this.setMessage(1, "multiTaskConfirm", "UPDATE");  //this.setMessage(effectRowCount, "TaskInfo", "UPDATE"); 
				logger.debug("multiTaskConfirm", "multiTaskConfirm.Action Exec Ok!"+effectRowCount); 
            }
			return SUCCESS;
		}catch(Exception err){
	        logger.debug("multiTaskConfirm", "multiTaskConfirm.Action Exec Fail!");
			return ERROR;
		}
	}
	
	//获取任务状态List  GuveXie 20140819 : task_getTaskStatusList.action
	@SuppressWarnings({ "unchecked", "unused", "rawtypes" })
	public String getTaskStatusList(){
		HttpServletRequest request = ServletActionContext.getRequest ();
		try{
			List<GeneralItem> data = (List<GeneralItem>) xwzcxtMngImp.getTaskStatusView();
			if(data != null && data.size()>0){
				RuleSelect wtree = new RuleSelect();
				wtree.setValue("c_id");
				wtree.setText("c_name");
				List<Map> l = this.initSelectData(data, wtree);
				this.formatData(l);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}

	//获取任务类别List  GuveXie 20140819 : task_getTaskTypeList.action
	@SuppressWarnings({ "unchecked", "unused", "rawtypes" })
	public String getTaskTypeList(){
		HttpServletRequest request = ServletActionContext.getRequest ();
		try{
			List<GeneralItem> data = (List<GeneralItem>) xwzcxtMngImp.getTaskTypeView();
			if(data != null && data.size()>0){
				RuleSelect wtree = new RuleSelect();
				wtree.setValue("c_id");
				wtree.setText("c_name");
				List<Map> l = this.initSelectData(data, wtree);
				this.formatData(l);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}

	//获取任务类别List  GuveXie 20140819 : task_getTaskTypeList.action
	@SuppressWarnings({ "unchecked", "unused", "rawtypes" })
	public String getTaskTriggerTypeList(){
		HttpServletRequest request = ServletActionContext.getRequest ();
		try{
			List<GeneralItem> data = (List<GeneralItem>) xwzcxtMngImp.getTaskTriggerTypeView();
			if(data != null && data.size()>0){
				RuleSelect wtree = new RuleSelect();
				wtree.setValue("c_id");
				wtree.setText("c_name");
				List<Map> l = this.initSelectData(data, wtree);
				this.formatData(l);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	//获取管理板块List  GuveXie 20140830 : task_getManageSectionList.action
	@SuppressWarnings({ "unchecked", "unused", "rawtypes" })
	public String getManageSectionList(){
		HttpServletRequest request = ServletActionContext.getRequest ();
		try{
			List<GeneralItem> data = (List<GeneralItem>) xwzcxtMngImp.getManageSectionList();
			if(data != null && data.size()>0){
				RuleSelect wtree = new RuleSelect();
				wtree.setValue("c_id");
				wtree.setText("c_name");
				List<Map> l = this.initSelectData(data, wtree);
				this.formatData(l);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}		
}
