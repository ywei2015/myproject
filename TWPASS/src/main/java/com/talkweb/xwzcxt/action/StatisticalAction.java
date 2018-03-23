package com.talkweb.xwzcxt.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.talkweb.twdpe.web.session.main.Session;
import com.talkweb.twdpe.web.session.util.SessionFactory;
import com.talkweb.twdpe.webcomponent.common.action.BusinessAction;
import com.talkweb.xwzcxt.pojo.StaticalPojo;
import com.talkweb.xwzcxt.service.impl.StatisticalImpl;
import com.talkweb.xwzcxt.util.DateUtils;

/*任务统计查询条数*/

public class StatisticalAction extends BusinessAction{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory
			.getLogger(StatisticalAction.class);
	
	@Autowired
	private StatisticalImpl statisticalImpl;
	
	private StaticalPojo staticalPojo;
	HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);
	HttpServletResponse response = ServletActionContext.getResponse();
	
	private  String getCode(){
		String flag=request.getParameter("flag");
		String userCode ="";
		if("1".equals(flag)){
			Session session = SessionFactory.getInstance(request, response);
			 Map   user = (Map) session.getAttribute("USERSESSION");
              
            if (user != null){
                userCode = user.get("code").toString();
            }else{
            	this.formatData("请先登录!");
				return SUCCESS;
            }
		}else{
			   userCode=request.getParameter("userCode");	
		}	
		
		return userCode;
	}
	
	
	public String statisticalTask(){
		String userCode=getCode();	
		Integer y=Integer.parseInt(request.getParameter("y"));
		String c_status="";
		int sum=0;
		int num1=0,num2=0,num3=0,num4=0,num5=0,num6=0;
		Map map1=new HashMap();
		Map map2=new HashMap();
		Map map3=new HashMap();
		Map map4=new HashMap();
		Map map5=new HashMap();
		Map map6=new HashMap();
		try{
		  switch(y){
			case 0:
			  for(int i=0;i<6;i++){
				switch(i){
					case 0:         
						 num1=unCreatedTask(userCode); //未生成
						 map1.put("value", num1);
						 map1.put("name", "未生成");
						  break;
					case 1: c_status="0";  //未下发
					     num2=unFinishedTask(c_status,userCode);
					     map2.put("value", num2);
						 map2.put("name", "未下发");
						  break;
					case 2: c_status="11"; //未接收
					    num3=unFinishedTask(c_status,userCode);
					    map3.put("value", num3);
						map3.put("name", "未接收");
					      break;
					case 3: c_status="22"; //未执行
					    num4=unFinishedTask(c_status,userCode);
					    map4.put("value", num4);
					    map4.put("name", "未执行");
					      break;
					case 4: c_status="33"; //未验证
					    num5=unFinishedTask(c_status,userCode);
					    map5.put("value", num5);
					    map5.put("name", "未验证");
					      break;
					case 5: c_status="34";//未评价
					    num6=unFinishedTask(c_status,userCode);
					    map6.put("value", num6);
					    map6.put("name", "未评价");
					      break;			
					}			
			 }
			sum=num1+num2+num3+num4+num5+num6;
			break;
			case 1:
			   for(int i=0;i<6;i++){
					switch(i){
					case 0:  c_status="0";    //已生成				        					
					      num1=finishedTask(c_status,userCode);
					      map1.put("value", num1);
						  map1.put("name", "已生成");
						  break;
					case 1: c_status="11"; //已下发	
					      num2=finishedTask(c_status,userCode);
					      map2.put("value", num2);
						  map2.put("name", "已下发");
					      break;
					case 2: c_status="22"; //已接收
					      num3=finishedTask(c_status,userCode);
					      map3.put("value", num3);
						  map3.put("name", "已接收");
					      break;
					case 3: c_status="33"; //已执行
					      num4=finishedTask(c_status,userCode);
						  map4.put("value", num4);
						  map4.put("name", "已执行");
					      break;
					case 4: c_status="34";//已验证
					      num5=finishedTask(c_status,userCode);
					      map5.put("value", num5);
						  map5.put("name", "已验证");
					      break;
					case 5: c_status="35";//已评价
					      num6=finishedTask(c_status,userCode);
					      map6.put("value", num6);
						  map6.put("name", "已评价");
				          break;	
					}
			   }
			   sum=num1+num2+num3+num4+num5+num6;
			break;	
		} 
		
		 List list=new ArrayList<Object>();
		 list.add(map1);
		 list.add(map2);
		 list.add(map3);
		 list.add(map4);
		 list.add(map5);
		 list.add(map6);
		 Map pageData=new HashMap();
		 pageData.put("sum",sum);
		 pageData.put("data",list);
		 this.formatData(pageData);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		return SUCCESS;
	}
	
	
	public int unFinishedTask(String c_status,String userCode){
		String dataId = request.getParameter("dataId");
		Integer x=Integer.parseInt(request.getParameter("x"));		
		Map map=new HashMap();
		String startTime="";
		String endTime="";	
		int count=0;
		try{
	      switch(x){
	      case 0:
	    	startTime=getStartTime();
	    	endTime=getEndTime();
	        map.put("startTime",startTime);
  		    map.put("endTime", endTime);
	    	map.put("status", c_status);
	    	map.put("userId", userCode);
	    	count=statisticalImpl.personUnfinished(map);
	        break;
          case 1:
        	startTime=getStartTime();
	    	endTime=getEndTime();
	        map.put("startTime",startTime);
  		    map.put("endTime", endTime);
        	map.put("status", c_status);
	    	map.put("userId", userCode);
	    	count=statisticalImpl.teamUnfinished(map,dataId);
	    	break;
          case 2:
        	startTime=getStartTime();
	    	endTime=getEndTime();
	        map.put("startTime",startTime);
  		    map.put("endTime", endTime);
        	map.put("status", c_status);
	    	map.put("userId", userCode);
	    	count=statisticalImpl.departUnfinished(map,dataId);
	    	break;
      }
	} catch (Exception e) {
		e.printStackTrace();
		logger.error(e.getMessage(), e);
	}
	    return count;
	}
	

	
	public int finishedTask(String c_status,String userCode){
		String dataId = request.getParameter("dataId"); 
		Integer x=Integer.parseInt(request.getParameter("x"));
		Map map=new HashMap();
		String startTime="";
		String endTime="";	
		int count=0;
	try{
	    switch(x){
	    case 0:
	    	startTime=getStartTime();
	    	endTime=getEndTime();
	        map.put("startTime",startTime);
  		    map.put("endTime", endTime);
	    	map.put("status", c_status);
	    	map.put("userId", userCode);
	    	count=statisticalImpl.personfinished(map);
	        break;
        case 1:
        	startTime=getStartTime();
	    	endTime=getEndTime();
	        map.put("startTime",startTime);
  		    map.put("endTime", endTime);
        	map.put("status", c_status);
	    	map.put("userId", userCode);
	    	count=statisticalImpl.teamfinished(map,dataId);
	    	break;
        case 2:
        	startTime=getStartTime();
	    	endTime=getEndTime();
	        map.put("startTime",startTime);
  		    map.put("endTime", endTime);
        	map.put("status", c_status);
	    	map.put("userId", userCode);
	    	count=statisticalImpl.departfinished(map,dataId);
	    	break;
     }
	} catch (Exception e) {
		e.printStackTrace();
		logger.error(e.getMessage(), e);
	}
	   return count;
	}
	
	
	public int unCreatedTask(String userCode){
		String dataId = request.getParameter("dataId");
		Integer x=Integer.parseInt(request.getParameter("x"));
		Map map=new HashMap();
		String startTime="";
		String endTime="";
		int count=0;
	try{
	    switch(x){
	    case 0:
	    	startTime=getStartTime();
	    	endTime=getEndTime();
	        map.put("startTime",startTime);
  		    map.put("endTime", endTime);
	    	map.put("userId", userCode);
	    	count=statisticalImpl.personUncreated(map);
	        break;
        case 1:
        	startTime=getStartTime();
	    	endTime=getEndTime();
	        map.put("startTime",startTime);
  		    map.put("endTime", endTime);
	    	map.put("userId", userCode);
	    	count=statisticalImpl.teamUncreated(map,dataId);
	    	break;
        case 2:
        	startTime=getStartTime();
	    	endTime=getEndTime();
	        map.put("startTime",startTime);
  		    map.put("endTime", endTime);
	    	map.put("userId", userCode);
	    	count=statisticalImpl.departUncreated(map,dataId);
	    	break;
     }
	} catch (Exception e) {
		e.printStackTrace();
		logger.error(e.getMessage(), e);
	}
	    return count;
	}
   	
	public String getStartTime(){
		Integer date=Integer.parseInt(request.getParameter("time"));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String startTime="";
		switch(date){
		 	   case 1:
		 		   startTime=sdf.format(getTimesmorning()); //按本日
		 		   break;
		 	   case 2: 
		 		   startTime=sdf.format(getTimesWeekmorning());//按本周
		 		   break;
		 	   case 3:
		 		   startTime=sdf.format(getTimesMonthmorning());//按本月
		 		   break;
 	   }
		return startTime;
	}
	
	public String getEndTime(){
		Integer date=Integer.parseInt(request.getParameter("time"));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String endTime="";
		switch(date){
		 	   case 1: //按本日
		 		   endTime=sdf.format(getTimesnight());
		 		   break;
		 	   case 2: //按本周
		 		   endTime=sdf.format(getTimesWeeknight());
		 		   break;
		 	   case 3://按本月
		 		   endTime=sdf.format(getTimesMonthnight());
		 		   break;
 	   }
		return endTime;
	}
	
	public Date getTimesmorning() {		// 获得当天0点时间	
		Calendar cal = Calendar.getInstance();		
		cal.set(Calendar.HOUR_OF_DAY, 0);		
		cal.set(Calendar.SECOND, 0);		
		cal.set(Calendar.MINUTE, 0);		
		cal.set(Calendar.MILLISECOND, 0);		
		return cal.getTime();	
		}	
	
	public  Date getTimesnight() {	// 获得当天24点时间
		Calendar cal = Calendar.getInstance();		
		cal.set(Calendar.HOUR_OF_DAY, 24);		
		cal.set(Calendar.SECOND, 0);		
		cal.set(Calendar.MINUTE, 0);		
		cal.set(Calendar.MILLISECOND, 0);		
		return cal.getTime();	
		}	
	
	public  Date getTimesWeekmorning() {		// 获得本周一0点时间	
		Calendar cal = Calendar.getInstance();		
		cal.set(cal.get(Calendar.YEAR),
				cal.get(Calendar.MONDAY),
				cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);	
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);		
		return  cal.getTime();	
		}	
	public  Date getTimesWeeknight() {	 // 获得本周日24点时间	
		Calendar cal = Calendar.getInstance();		
		cal.setTime(getTimesWeekmorning());		
		cal.add(Calendar.DAY_OF_WEEK, 7);		
		return cal.getTime();	
		}	
	public Date getTimesMonthmorning() {		// 获得本月第一天0点时间
		Calendar cal = Calendar.getInstance();		
		cal.set(cal.get(Calendar.YEAR),
				cal.get(Calendar.MONDAY),
				cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		cal.set(Calendar.DAY_OF_MONTH, 
				cal.getActualMinimum(Calendar.DAY_OF_MONTH));		
		return cal.getTime();	
		}	
	public  Date getTimesMonthnight() {	// 获得本月最后一天24点时间		
		Calendar cal = Calendar.getInstance();		
		cal.set(cal.get(Calendar.YEAR), 
				cal.get(Calendar.MONDAY), 
				cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);	
		cal.set(Calendar.DAY_OF_MONTH, 
				cal.getActualMaximum(Calendar.DAY_OF_MONTH));		
		cal.set(Calendar.HOUR_OF_DAY, 24);		
		return cal.getTime();	
		}
	
	
	public void system(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		System.out.println(sdf.format(new Date()));
 		System.out.println("当天0点时间："+sdf.format(getTimesmorning()));		
 		System.out.println("当天24点时间："+ sdf.format(getTimesnight()));	
 		System.out.println("本周周一0点时间："+ sdf.format(getTimesWeekmorning()));		
 		System.out.println("本周周日24点时间："+ sdf.format(getTimesWeeknight()));		
 		System.out.println("本月初0点时间："+ sdf.format(getTimesMonthmorning()));		
 		System.out.println("本月未24点时间："+ sdf.format(getTimesMonthnight()));	
    	/*	2.结果
		当前时间：2014-10-17 15:23:26
		当天0点时间：2014-10-17 0:00:00
		当天24点时间：2014-10-18 0:00:00
		本周周一0点时间：2014-10-13 0:00:00
		本周周日24点时间：2014-10-20 0:00:00
		本月初0点时间：2014-10-1 0:00:00
		本月未24点时间：2014-11-1 0:00:00   */ 	
	 }
   
	
	public String queryTaskList(){//查询任务详情列表
		String userCode=getCode();		
		Integer y=Integer.parseInt(request.getParameter("y"));
		Integer statusStr=Integer.parseInt(request.getParameter("status"));
		String c_status="";
		List list=new ArrayList<StaticalPojo>();
		int sum=0;
		int num1=0,num2=0,num3=0,num4=0,num5=0,num6=0;
		Map map1=new HashMap();
		Map map2=new HashMap();
		Map map3=new HashMap();
		Map map4=new HashMap();
		Map map5=new HashMap();
		Map map6=new HashMap();
		try{
		  switch(y){
			case 0:
			    switch(statusStr){
				    case 0:
				    	 list=queryUnCreatedTask(userCode);
				    	 num1=unCreatedTask(userCode); //未生成
						 map1.put("value", num1);
				    	break;
				    case 1:
				    	 c_status="0";  //未下发
					     list=queryTaskList(c_status,userCode);
					     num2=unFinishedTask(c_status,userCode);
					     map2.put("value", num2);
				    	 break;
				    case 2:
				    	 c_status="11"; //未接收
				    	 list=queryTaskList(c_status,userCode);
				 	     num3=unFinishedTask(c_status,userCode);
					     map3.put("value", num3);
				    	 break;
				    case 3:
				    	c_status="22"; //未执行
				    	list=queryTaskList(c_status,userCode);
				        num4=unFinishedTask(c_status,userCode);
						map4.put("value", num4);
				    	 break;
				    case 4:
				    	c_status="33"; //未验证
				    	list=queryTaskList(c_status,userCode);
				    	num5=unFinishedTask(c_status,userCode);
						map5.put("value", num5);
				    	 break;
				    case 5:
				    	c_status="34";//未评价
				    	list=queryTaskList(c_status,userCode);
				        num6=unFinishedTask(c_status,userCode);
					    map6.put("value", num6);
				    	break;			    
			    }
			break;
			case 1:
				   switch(statusStr){
				    case 0:
				    	c_status="0";    //已生成	
				    	list=queryTaskList(c_status,userCode);
				    	num1=finishedTask(c_status,userCode);
					    map1.put("value", num1);
				    	break;
				    case 1:
				    	 c_status="11";  //已下发
					     list=queryTaskList(c_status,userCode);
					     num2=finishedTask(c_status,userCode);
					     map2.put("value", num2);
				    	 break;
				    case 2:
				    	 c_status="22"; //已接收
				    	 list=queryTaskList(c_status,userCode);
				    	 num3=finishedTask(c_status,userCode);
					     map3.put("value", num3);
				    	 break;
				    case 3:
				    	c_status="33"; //已执行
				    	list=queryTaskList(c_status,userCode);
				        num4=finishedTask(c_status,userCode);
						map4.put("value", num4);
				    	 break;
				    case 4:
				    	c_status="34"; //已验证
				    	list=queryTaskList(c_status,userCode);
				    	num5=finishedTask(c_status,userCode);
						map5.put("value", num5);
				    	 break;
				    case 5:
				    	c_status="35";//已评价
				    	list=queryTaskList(c_status,userCode);
					    num6=finishedTask(c_status,userCode);
					    map6.put("value", num6);
				    	break;			    
			    }
	        break;
		 }
		 List d=new ArrayList<Object>();
		 d.add(map1);
		 d.add(map2);
		 d.add(map3);
		 d.add(map4);
		 d.add(map5);
		 d.add(map6);
		 Map pageData=new HashMap();
		 pageData.put("row",d);
		 pageData.put("data",list);
		 this.formatData(pageData);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		return SUCCESS;
	}
	
	
	
	public List<StaticalPojo> queryTaskList(String c_status,String userCode){
		Integer x=Integer.parseInt(request.getParameter("x"));		
		Map map=new HashMap();
		String startTime="";
		String endTime="";	
		List l=new ArrayList<StaticalPojo>();
		try{
	      switch(x){
	      case 0:
	    	startTime=getStartTime();
	    	endTime=getEndTime();
	        map.put("startTime",startTime);
  		    map.put("endTime", endTime);
	    	map.put("status", c_status);
	    	map.put("userId", userCode);
	    	l=statisticalImpl.queryPersonTask(map);
	        break;
          case 1:
        	startTime=getStartTime();
	    	endTime=getEndTime();
	        map.put("startTime",startTime);
  		    map.put("endTime", endTime);
        	map.put("status", c_status);
	    	map.put("userId", userCode);
	    	l=statisticalImpl.queryteamTask(map);
	    	break;
          case 2:
        	startTime=getStartTime();
	    	endTime=getEndTime();
	        map.put("startTime",startTime);
  		    map.put("endTime", endTime);
        	map.put("status", c_status);
	    	map.put("userId", userCode);
	    	l=statisticalImpl.querydepartTask(map);
	    	break;
      }
	} catch (Exception e) {
		e.printStackTrace();
		logger.error(e.getMessage(), e);
	}
	    return l;
	}
	
	
	
	public List<StaticalPojo> queryUnCreatedTask(String userCode){
		Integer x=Integer.parseInt(request.getParameter("x"));
		Map map=new HashMap();
		String startTime="";
		String endTime="";
		List l=new ArrayList<StaticalPojo>();
	try{
	    switch(x){
	    case 0:
	    	startTime=getStartTime();
	    	endTime=getEndTime();
	        map.put("startTime",startTime);
  		    map.put("endTime", endTime);
	    	map.put("userId", userCode);
	    	l=statisticalImpl.queryPersonUncreated(map);
	        break;
        case 1:
        	startTime=getStartTime();
	    	endTime=getEndTime();
	        map.put("startTime",startTime);
  		    map.put("endTime", endTime);
	    	map.put("userId", userCode);
	    	l=statisticalImpl.queryteamUncreated(map);
	    	break;
        case 2:
        	startTime=getStartTime();
	    	endTime=getEndTime();
	        map.put("startTime",startTime);
  		    map.put("endTime", endTime);
	    	map.put("userId", userCode);
	    	l=statisticalImpl.querydepartUncreated(map);
	    	break;
     }
	} catch (Exception e) {
		e.printStackTrace();
		logger.error(e.getMessage(), e);
	}
	    return l;
	}
   	
	
	public String queryErrCount(){   //查询异常数的工作执行任务数，异常反馈数，时间
		String userCode=getCode();
		String dataId = request.getParameter("dataId");
		Integer x=Integer.parseInt(request.getParameter("x"));
		Integer time=Integer.parseInt(request.getParameter("time"));
		int monthCount=0;
		Map map=new HashMap();
		String day=DateUtils.getDays(DateUtils.DAY).get(0);
		String week=DateUtils.getDays(DateUtils.WEEK).get(0);
		String month=DateUtils.getDays(DateUtils.MONTH).get(0);
		monthCount=DateUtils.getDays(DateUtils.MONTH).size();
		List list=new ArrayList();
		try{
		switch(x){
		case 0:
			switch(time){
			case 1:
				map.put("startTime",day );
				map.put("totalDay",1);
				break;
			case 2:
				map.put("startTime", week);
				map.put("totalDay",7);
				break;
			case 3:
				map.put("startTime", month);
				map.put("totalDay",monthCount);
				break;		
			}
			map.put("userId", userCode);
			list=statisticalImpl.queryErrCount(map);
			break;
		case 1:
			switch(time){
			case 1:
				map.put("startTime",day );
				map.put("totalDay",1);
				break;
			case 2:
				map.put("startTime", week);
				map.put("totalDay",7);
				break;
			case 3:
				map.put("startTime", month);
				map.put("totalDay",monthCount);
				break;		
			}
			map.put("userId", userCode);
			list=statisticalImpl.queryteamErrCount(map,dataId);
			break;
		case 2:
			switch(time){
			case 1:
				map.put("startTime",day );
				map.put("totalDay",1);
				break;
			case 2:
				map.put("startTime", week);
				map.put("totalDay",7);
				break;
			case 3:
				map.put("startTime", month);
				map.put("totalDay",monthCount);
				break;		
			}
			map.put("userId", userCode);
			list=statisticalImpl.querydepartErrCount(map,dataId);
			break;
		}
		
		List l=new ArrayList();//放时间的
		List l2=new ArrayList();//放任务数的
		List l3=new ArrayList();//放执行异常数的
		List l4=new ArrayList();//放反馈异常数的
		
		Map map2=new HashMap();
		
		for(Object s:list){
			StaticalPojo p=(StaticalPojo)s;
			l.add(p.getMydate());
			l2.add(p.getSum());
			l3.add(p.getSum2());
			l4.add(p.getSum3());
		}
		map2.put("mydate", l);
		map2.put("sum", l2);
		map2.put("sum2", l3);
		map2.put("sum3", l4);
	    this.formatData(map2);
  } catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
			return SUCCESS;
  }
	
	public String getErrTotalCount(){  //异常总数
		String dataId = request.getParameter("dataId");
		String userCode=getCode();
		Integer x=Integer.parseInt(request.getParameter("x"));
		Map map=new HashMap();
		String startTime="";
		String endTime="";	
		Integer errCount=0;
		try{
        switch(x){
        case 0:
        		startTime=getStartTime();
    	    	endTime=getEndTime();
        		map.put("startTime", startTime);
        		map.put("endTime", endTime);   
		        map.put("userId", userCode);
		        errCount=statisticalImpl.getpersonErrTotalCount(map);
        break;
        case 1:
           		startTime=getStartTime();
    	    	endTime=getEndTime();
        		map.put("startTime", startTime);
        		map.put("endTime", endTime);
		        map.put("userId", userCode);
		        errCount=statisticalImpl.getteamErrTotalCount(map,dataId);
        break;
        case 2:
        	startTime=getStartTime();
	    	endTime=getEndTime();
    		map.put("startTime", startTime);
    		map.put("endTime", endTime);
	        map.put("userId", userCode);
	        errCount=statisticalImpl.getdepartErrTotalCount(map,dataId);
        break;
        }
        
        this.formatData(errCount);
	} catch (Exception e) {
		e.printStackTrace();
		logger.error(e.getMessage(), e);
	}
		return SUCCESS;
		
	}
	
	
	public String queryErrList(){//异常详情列表
		String userCode=getCode();	
		Integer x=Integer.parseInt(request.getParameter("x"));
		Integer y=Integer.parseInt(request.getParameter("y"));
		String startTime=request.getParameter("time");
		String endTime="";
		Map map=new HashMap();
		List list=new ArrayList<StaticalPojo>();
		
		try {			
           SimpleDateFormat  formatter  = new   SimpleDateFormat( "yyyy-MM-dd");
		   Date   date   =   formatter.parse(startTime);
		   Calendar cal = Calendar.getInstance();
	       cal.setTime(date);
	       cal.add(Calendar.DAY_OF_MONTH, 1);
	       System.out.println(cal.getTime());
	       endTime=formatter.format(cal.getTime());
		
		   map.put("userId", userCode);
		   map.put("startTime", startTime);
		   map.put("endTime", endTime);
		   switch(x){
			case 0:
				switch(y){
				case 0:
					list=statisticalImpl.queryPersonErrList(map);
					break;
				case 1:
					list=statisticalImpl.queryPersonErrList2(map);
					break;
				}
				
				break;
			case 1:
				switch(y){
				case 0:
					list=statisticalImpl.queryTeamErrList(map);
					break;
				case 1:
					list=statisticalImpl.queryTeamErrList2(map);
					break;
				}
				
				break;
			case 2:
				switch(y){
				case 0:
					list=statisticalImpl.queryDepartErrList(map);
					break;
				case 1:
					list=statisticalImpl.queryDepartErrList2(map);
					break;
				}
				
				break;
			}
		  this.formatData(list); 
	  } catch (Exception e) {
		e.printStackTrace();
		logger.error(e.getMessage(), e);
	  }
		return SUCCESS;
   }
	
	
	public String checkTempStatus(){
		Map map=new HashMap();
		Integer c;
		String arr[]={"1000100000009","1000100000005","1000100000015"};
		List list=new ArrayList();
		try {
			for(String a:arr){
				map.put("upArea", a);
				c=statisticalImpl.checkTempStatus(map);
				list.add(c);
			}
			this.formatData(list);
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return SUCCESS;
	}
	
	public String checkEnergyStatus(){
		Map map=new HashMap();
		Integer c;
		String arr[]={"1000100000009","1000100000005","1000100000015"};
		List list=new ArrayList();
		try {
			for(String a:arr){
				map.put("upArea", a);
				c=statisticalImpl.checkEnergyStatus(map);
				list.add(c);
			}
			this.formatData(list);
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return SUCCESS;
	}
	
	public String getSecondAreaCount(){
		String area=request.getParameter("area");
		List<StaticalPojo> list=new ArrayList<StaticalPojo>();
		Integer count;
		List list2=new ArrayList();
		try {
			list=statisticalImpl.getAreaIdName(area);
			for(StaticalPojo pojo:list){
				Map map=new HashMap();
				map.put("upArea", pojo.getArea());
				count=statisticalImpl.checkTempStatus(map);
				map.put("name", pojo.getAreaname());
				map.put("count", count);
				list2.add(map);
			}
			this.formatData(list2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String getSecondAreaCount2(){
		String area=request.getParameter("area");
		List<StaticalPojo> list=new ArrayList<StaticalPojo>();
		Integer count;
		List list2=new ArrayList();
		try {
			list=statisticalImpl.getAreaIdName(area);
			for(StaticalPojo pojo:list){
				Map map=new HashMap();
				map.put("upArea", pojo.getArea());
				count=statisticalImpl.checkEnergyStatus(map);
				map.put("name", pojo.getAreaname());
				map.put("count", count);
				list2.add(map);
			}
			this.formatData(list2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
}
