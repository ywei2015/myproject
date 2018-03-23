package com.talkweb.xwzcxt.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.twdpe.webcomponent.common.action.BusinessAction;
import com.talkweb.xwzcxt.pojo.TaskError;
import com.talkweb.xwzcxt.service.XwzcxtTaskErrorService;

/**
 * TODO(描述这个类的作用)
 * 
 * @author: 2014-2-20，FLN，（描述修改内容）
 */
public class XwzcxtTaskErrorAction extends BusinessAction
{
    /**
	 * 异常跟踪及异常发布操作类
	 * @author J.B.Chen
	 * 2014-8-15 modify
	 */
	private static final long serialVersionUID = 5561365505345220205L;
	private TaskError taskError;
    @Autowired
    private XwzcxtTaskErrorService xwzcxtTaskErrorService;
    
    public TaskError getTaskError() {
		return taskError;
	}

	public void setTaskError(TaskError taskError) {
		this.taskError = taskError;
	}

	//获取异常任务
    public String queryTaskErrorList()
    {
        
//        long loginUserId=this.getLongUserID();
        
    	try {
    		List<TaskError> list = xwzcxtTaskErrorService.queryTaskErrorList(taskError, pagination);
    		this.formatDatagirdData(list, pagination);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return SUCCESS;
    }
    
    public String getTaskErrDetailById(){
    	 HttpServletRequest request = ServletActionContext.getRequest();
    	 String id = request.getParameter("errId");
    	try{
    		TaskError t = xwzcxtTaskErrorService.getTaskErrDetailById(id);
    		if (t != null){
    			t.setErrdes(t.getC_err_des());
    			this.formatData(t);
    		}
    	}catch (Exception e){
    		e.printStackTrace();
    	}
    	return SUCCESS;
    }
    
    public String getErrFilesByErrTaskId(){
    	HttpServletRequest request = ServletActionContext.getRequest();
    	String id = request.getParameter("errId");
    	try{
    		List<TaskError> list = xwzcxtTaskErrorService.getErrFilesByErrTaskId(id);
    		if (list != null && list.size()>0){
    			this.formatData(list);
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return SUCCESS;
    }
    
    public String getErrFeedbacksByErrTaskId(){
    	HttpServletRequest request = ServletActionContext.getRequest();
   	 	String id = request.getParameter("errId");
    	try{
    		List<TaskError> list = xwzcxtTaskErrorService.getErrFeedbacksByErrTaskId(id,pagination);
    		if (list != null && list.size()>0){
    			this.formatDatagirdData(list, pagination);
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return SUCCESS;
    }
    
    public String getErrFeedbacksByErrTaskIdM(){
    	HttpServletRequest request = ServletActionContext.getRequest();
   	 	String id = request.getParameter("errId");
    	try{
    		List<TaskError> list = xwzcxtTaskErrorService.getErrFeedbacksByErrTaskIdM(id);
    		if (list != null && list.size()>0){
    			this.formatData(list);
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return SUCCESS;
    }
    
    
    
    public String getErrTaskSNByErrTaskId(){
    	 HttpServletRequest request = ServletActionContext.getRequest();
    	 String id = request.getParameter("errId");
    	try{
    		/*List<TaskError> list = xwzcxtTaskErrorService.getErrTaskSNByErrTaskId(id,pagination);
    		if (list != null && list.size()>0)
    			this.formatDatagirdData(list,pagination);*/
    		TaskError t = xwzcxtTaskErrorService.getErrTaskSNByErrTaskId(id);
    		if (t!=null)
    			this.formatData(t);
    	}catch (Exception e){
    		e.printStackTrace();
    	}
    	return SUCCESS;
    }

}
