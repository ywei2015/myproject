package com.talkweb.xwzcxt.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.talkweb.common.AppConstants;
import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.xwzcxt.dal.XwzcxtTaskErrorDal;
import com.talkweb.xwzcxt.pojo.TaskError;
import com.talkweb.xwzcxt.service.XwzcxtTaskErrorService;

/**
 * TODO(描述这个类的作用)
 * 
 * @author: 2013-12-21，FLN，（描述修改内容）
 */
public class XwzcxtTaskErrorServiceImpl implements XwzcxtTaskErrorService
{
    @Autowired
    private XwzcxtTaskErrorDal xwzcxtTaskErrorDal;
    
    @Autowired
    AppConstants appConstants;

    @Override
    public List<TaskError> queryTaskErrorList(TaskError taskError,
            Pagination pagination) {
    	List<TaskError> list_UsrStaffreg = new ArrayList();
    	List<TaskError> dt = xwzcxtTaskErrorDal.queryTaskErrorList(taskError, pagination);
    	for(TaskError model : dt)
    	{
    		 if (model.getC_err_kind() == 0)
            	 model.setC_err_kind_name("任务异常");
             else if(model.getC_err_kind() == 1)
            	 model.setC_err_kind_name("活动异常");
             
             list_UsrStaffreg.add(model);
    	}
    	
    	return list_UsrStaffreg;
    }
    
    public TaskError getTaskErrDetailById(String param){
    	TaskError t = xwzcxtTaskErrorDal.getTaskErrDetailById(param);
    	
    	if (t.getC_err_kind() == 0)
       	 t.setC_err_kind_name("任务异常");
        else if(t.getC_err_kind() == 1)
       	 t.setC_err_kind_name("活动异常");
    	
    	if (t.getC_isclose().equals("0"))
    		t.setC_isclose_name("未完成");
    	else if (t.getC_isclose().equals("1"))
    		t.setC_isclose_name("已完成");
    	
    	//内容为空识别
    	if (t.getC_task_name() == null)
    		t.setC_task_name("无");
    	if (t.getC_pre_err_name() == null)
    		t.setC_pre_err_name("无");
    	
    	return t;
    }
    
    public List<TaskError> getErrFilesByErrTaskId(String param){
    	List<TaskError> list_UsrStaffreg = new ArrayList();
    	List<TaskError> dt = xwzcxtTaskErrorDal.getErrFilesByErrTaskId(param);
    	for(TaskError model : dt)
    	{
    		 if(model.getC_file_path()!= null){
                 model.setC_file_path(appConstants.getIMG_URL()+model.getC_file_path());
             }
             
             list_UsrStaffreg.add(model);
    	}
    	
    	return list_UsrStaffreg;
    }
    
    public List<TaskError> getErrFeedbacksByErrTaskId(String param,Pagination pagination){
    	
    	List<TaskError> list_UsrStaffreg = new ArrayList();
    	List<TaskError> dt = xwzcxtTaskErrorDal.getErrFeedbacksByErrTaskId(param,pagination);
    	for(TaskError model : dt)
    	{
    		 if(model.getC_feedback_type() == 0)
                 model.setC_feedback_type_name("协调人");
    		 else if (model.getC_feedback_type() == 1)
    			 model.setC_feedback_type_name("抄送人");
    		 
    		 /*if (model.getC_isreceived().equals("0"))
    			 model.setC_isreceived_name("未收到");
    		 else if (model.getC_isreceived().equals("1"))
    			 model.setC_isreceived_name("收到");*/
    		 
    		 if (model.getC_isreceived().equals("0"))
    			 model.setC_isreceived_name("待发");
    		 else if (model.getC_isreceived().equals("11"))
    			 model.setC_isreceived_name("送达");
    		 else if (model.getC_isreceived().equals("22"))
    			 model.setC_isreceived_name("已读");
             
             list_UsrStaffreg.add(model);
    	}
    	
    	return list_UsrStaffreg;
    }
    
    public List<TaskError> getErrFeedbacksByErrTaskIdM(String param){
    	
    	List<TaskError> list_UsrStaffreg = new ArrayList();
    	List<TaskError> dt = xwzcxtTaskErrorDal.getErrFeedbacksByErrTaskIdM(param);
    	for(TaskError model : dt)
    	{
    		 if(model.getC_feedback_type() == 0)
                 model.setC_feedback_type_name("协调人");
    		 else if (model.getC_feedback_type() == 1)
    			 model.setC_feedback_type_name("抄送人");
    		 
    		 /*if (model.getC_isreceived().equals("0"))
    			 model.setC_isreceived_name("未收到");
    		 else if (model.getC_isreceived().equals("1"))
    			 model.setC_isreceived_name("收到");*/
    		 
    		 if (model.getC_isreceived().equals("0"))
    			 model.setC_isreceived_name("待发");
    		 else if (model.getC_isreceived().equals("11"))
    			 model.setC_isreceived_name("送达");
    		 else if (model.getC_isreceived().equals("22"))
    			 model.setC_isreceived_name("已读");
             
             list_UsrStaffreg.add(model);
    	}
    	
    	return list_UsrStaffreg;
    }
    
    
    public TaskError getErrTaskSNByErrTaskId(String param){//,Pagination pagination){
    	//List<TaskError> list_UsrStaffreg = new ArrayList();
    	//List<TaskError> dt = xwzcxtTaskErrorDal.getErrTaskSNByErrTaskId(param,pagination);
    	TaskError t = xwzcxtTaskErrorDal.getErrTaskSNByErrTaskId(param);
    	String name="";
    	if (t!=null){
	    	switch(t.getC_sn_status()){
	    	case 0:
	    		name = "已生成";
	    		break;
	    	case 11:
	    		name = "已确认";
	    		break;
	    	case 22:
	    		name = "被接收";
	    		break;
	    	case 33:
	    		name = "已执行";
	    		break;
	    	case 44:
	    		name = "任务取消";
	    		break;
	    	}
			t.setC_sn_status_name(name);
    	}
    	/*for(TaskError model : dt)
    	{
    		switch(model.getC_sn_status()){
	    	case 0:
	    		name = "已生成";
	    		break;
	    	case 11:
	    		name = "已确认";
	    		break;
	    	case 22:
	    		name = "被接收";
	    		break;
	    	case 33:
	    		name = "已执行";
	    		break;
	    	case 44:
	    		name = "任务取消";
	    		break;
	    	}
    		model.setC_sn_status_name(name);
            list_UsrStaffreg.add(model);
    	}*/
    	
    	return t;
    }
}
