package com.talkweb.xwzcxt.log;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.twdpe.base.log.aop.LogThreadPoolManager;
import com.talkweb.twdpe.base.log.dal.LogInfoDao;
import com.talkweb.twdpe.base.log.dal.impl.LogInfoDaoImpl;
import com.talkweb.twdpe.base.log.pojo.LogInfo;
import com.talkweb.xwzcxt.service.impl.LoggingServiceIml;


public class LogAop {

	@Autowired
	private LogInfoDao logInfoDao;
	private String packageName;
	private String endString;
    
    public  void doAfterThrowing(JoinPoint joinPoint, Throwable ex) {  
       try{
          execute(joinPoint,ex);
       }catch(Exception e){
    	   e.printStackTrace();
       }
   }  
 
 
    public void after(JoinPoint joinPoint){
          execute(joinPoint,null);
    }
 
    public void execute(JoinPoint joinPoint,Throwable ex){
      try {
    	      HttpServletRequest  request=ServletActionContext.getRequest();
    	      Map user=(Map)request.getSession().getAttribute("USERSESSION");
  			  LogInfo loginfo=new LogInfo();
  			if(user!=null){
  				String userid=user.get("id").toString();
  				String username=(String)user.get("name");
  				loginfo.setAccountName(username);
  				loginfo.setAccount(userid);
  			}
  			
  			String clientIp=request.getRemoteAddr();
  			String serverIp=request.getLocalAddr();
  			
  			StringBuffer url=request.getRequestURL();
  			if(url!=null){
  				loginfo.setResourceUrl(url.toString());
  			}
  			loginfo.setSourceIp(serverIp);
  			loginfo.setTargetIp(clientIp);
		      LogThreadPoolManager.getInstance().execute(new LoggingServiceIml(joinPoint, ex, logInfoDao, loginfo,packageName,endString)	);
          } catch (Exception e) {
        	  e.printStackTrace();
	      }
    }


	public String getPackageName() {
		return packageName;
	}


	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}


	public String getEndString() {
		return endString;
	}


	public void setEndString(String endString) {
		this.endString = endString;
	}
   
    
}
