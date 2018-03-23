package com.talkweb.xwzcxt.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.twdpe.core.log.Logger;
import com.talkweb.twdpe.core.log.LoggerFactory;
import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.twdpe.web.session.main.Session;
import com.talkweb.twdpe.web.session.util.SessionFactory;
import com.talkweb.twdpe.webcomponent.common.action.BusinessAction;
import com.talkweb.xwzcxt.pojo.CalendarPojo;
import com.talkweb.xwzcxt.service.impl.CalendarImpl;

public class CalendarAction extends BusinessAction{
	private static final long serialVersionUID = 2061872160007336103L;
	private static final Logger logger = LoggerFactory.getLogger(CalendarAction.class);
	
	@Autowired
	 private CalendarImpl calendarImpl;
	 private CalendarPojo calendarPojo=null;
	 
	public CalendarPojo getCalendarPojo() {
		return calendarPojo;
	}
	public void setCalendarPojo(CalendarPojo calendarPojo) {
		this.calendarPojo = calendarPojo;
	}
	 
	private HttpServletRequest request = ServletActionContext.getRequest();
	private HttpServletResponse response = ServletActionContext.getResponse();
	 
		private boolean isLogin(){
			Session session=SessionFactory.getInstance(request, response);
			Map user =(Map)session.getAttribute("USERSESSION");
			if(user==null){
				return false;
			}
			    return true;
		}
	   //查询日历
       public String queryCalendarByPage() {
    	   if (calendarPojo == null)
    		   calendarPojo = new CalendarPojo();
   		    System.out.println("*************************"+calendarPojo);
   		this.excludeNullProperties(calendarPojo);
		 try {
			if(!isLogin()){
				   this.formatData("请先登录！");
				   return SUCCESS;
			   }
            String startTime =request.getParameter("startTime");
            String endTime=request.getParameter("endTime");
            HashMap<String,String> map=new HashMap<String,String>();
            map.put("startTime",startTime);
            map.put("endTime",endTime);
            List<CalendarPojo> list  = null;
            if(pagination==null){
      		   pagination=new Pagination(1,20);
      	   }
            list=calendarImpl.getCurrentRecordByPage(map, pagination);  
		    this.formatDatagirdData(list, pagination);//格式化DataGrid控件数据
		} catch (Exception e) {
			ServletActionContext.getRequest().setAttribute("throw", e);
			@SuppressWarnings("rawtypes")
			List emptylist = new ArrayList();
			this.formatDatagirdData(emptylist, pagination);//格式化DataGrid控件数据
		    logger.error(e.getMessage(), e);
		}
		return SUCCESS;
    }
    
     public String getCalDataBySeq(){
		String seq = request.getParameter("seq");
		try{
			CalendarPojo data = (CalendarPojo) calendarImpl.getCalDataBySeq(seq);
			if (data != null){
				this.formatData(this.addPrefix(data, "calendarPojo."));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
    	return SUCCESS;
    }
     
     public String modifyCalData(){
     	int result = -1;
     	String msg = "";
 		Map pageData = new HashMap();
 		calendarPojo.setC_id(Integer.parseInt(request.getParameter("seq")));
 		String remark=request.getParameter("calendarPojo.c_remark");		
 	        char[] ch = remark.toCharArray();
 	        String r="";
 	        for(int i=0;i<ch.length;i++){
 	        	
 	        	if(ch[i]=='n' || ch[i]=='\\' || ch[i]=='\0'){
 	        			ch[i]='\0';
 	        	   }else{
 	        		   ch[i]=ch[i];
 	        	   }
 	        	r+=ch[i];        	
 	        }
 	       System.out.println(r); 
 	       
 		calendarPojo.setC_remark(r);
 		try{
 			if(!isLogin()){
				   this.formatData("请先登录！");
				   return SUCCESS;
			   }
 			if (calendarImpl.modifyCalData(calendarPojo) > 0){
 				result = 0;
 				msg = "修改成功！";
 			}else{
 				result = -1;
 				msg = "信息修改失败，请检查填写内容";
 			}
 			
 		}catch(Exception e){
 			result = -1;
 			msg = "信息修改失败，请检查填写内容";
 			e.printStackTrace();
 		}
 		pageData.put("result", result);
 		pageData.put("msg", msg);
 		this.formatData(pageData);
     	return SUCCESS;
     }

}
