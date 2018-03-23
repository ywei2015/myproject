package com.talkweb.xwzcxt.service.impl;

import java.lang.reflect.Method;
import java.util.Date;

import org.aspectj.lang.JoinPoint;

import com.talkweb.twdpe.base.log.aop.LoggingService;
import com.talkweb.twdpe.base.log.dal.LogInfoDao;
import com.talkweb.twdpe.base.log.pojo.LogInfo;
import com.talkweb.twdpe.core.log.Logger;
import com.talkweb.twdpe.core.log.LoggerFactory;
import com.talkweb.xwzcxt.log.Log;

public class LoggingServiceIml extends LoggingService implements Runnable{
	private static Logger logger = LoggerFactory.getLogger(LoggingService.class);

	private LogInfo log;

	private JoinPoint jp;

	private Throwable ex;
	
	private LogInfoDao dao;
	
	private String packageName;
	
	private String endString;
	
	public LoggingServiceIml(JoinPoint jp, Throwable ex, LogInfoDao dao, LogInfo log,String packageName,String endString){
		super(dao, log);
		this.log=log;
		this.dao=dao;
		this.jp = jp;
		this.ex=ex;
		this.packageName=packageName;
		this.endString=endString;
	}

	@Override
	public void run() {
		try {
			if (jp != null) {
				handle(jp,ex);
			}		
		   log.setAuditDate(new Date());
		   log.setLogId(dao.nextId());
		   dao.insertLogInfo(log);
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error(e.getMessage(), e);
			}
		}
	}
	
	
	public void handle(JoinPoint joinPoint,Throwable ex){
	      try {
	    	Object[] objArray= joinPoint.getArgs();
	        int len=0;
	        if(objArray!=null){
	        	 len=objArray.length;
	        }
	        Class[] paramArrayOfClass=new Class[len];
	        for(int i=0;i<len;i++){
				paramArrayOfClass[i]=objArray[i].getClass();
			}
	        
	       	String methodName=joinPoint.getSignature().getName();
	       	String target=joinPoint.getTarget().getClass().getName();
	       	target=target.substring(target.lastIndexOf(".")+1);
	        String logPath=packageName+"."+target+endString;
	        Object result=null;
	      
	        try{
			  Class<?> cls=Class.forName(logPath);
			  Method m=null;
			
			  m=cls.getDeclaredMethod(methodName, paramArrayOfClass);
			  result=m.invoke(cls.newInstance(),objArray);
			}catch (ClassNotFoundException e) {
				//logger.error(e.getMessage(), e);
		    } catch(Exception e2){
		    	//logger.error(e2.getMessage(), e2);
			}
			
			LogInfo loginfo=null;
			if(result!=null){			
				loginfo=(LogInfo)result;
			}else{
				loginfo=new LogInfo();
			}
			
			loginfo.setAccount(this.log.getAccount());
			loginfo.setAccountName(this.log.getAccountName());
			loginfo.setResourceUrl(this.log.getResourceUrl());
			loginfo.setSourceIp(this.log.getSourceIp());
			loginfo.setTargetIp(this.log.getTargetIp());
			
			Log log=joinPoint.getTarget().getClass().getDeclaredMethod(methodName, paramArrayOfClass).getAnnotation(Log.class);
			if(ex==null){
				if("0".equals(loginfo.getStatusCode())){
					loginfo.setStatusCode("0");
					if(loginfo.getActionResult()==null||"".equals(loginfo.getActionResult())){						
						loginfo.setActionResult(log.breakMessage());
					}
				}
				if(loginfo.getActionResult()==null){
					loginfo.setActionResult(log.successMessage());
					loginfo.setStatusCode("1");
				}
				
			}else{
				loginfo.setStatusCode("2");
				loginfo.setActionResult(log.failedMessage());
			}
			
			
			if(loginfo.getAccountType()==null){			
				loginfo.setAccountType(log.accountType());
			}
			
			if(loginfo.getType()==null){			
				loginfo.setType(log.type());
				loginfo.setActionType(log.type());
			}else{
				loginfo.setActionType(loginfo.getType());
			}
			if(loginfo.getActionDescription()==null){			
				loginfo.setActionDescription(log.actionDescription());
			}
			if(loginfo.getAppId()==null){			
				loginfo.setAppId(log.appId());
			}
			if(loginfo.getAppName()==null){			
				loginfo.setAppName(log.appName());
			}
			if(loginfo.getFuncGroupId()==null){
				loginfo.setFuncGroupId(log.funcGroupId());
			}
			if(loginfo.getFuncGroupName()==null){
				loginfo.setFuncGroupName(log.funcGroupName());
			}
			
			if("".equals(log.resourceName().trim())&&loginfo.getResourceName()==null){
				String url=loginfo.getResourceUrl();
  				loginfo.setResourceName(url.toString()+"$"+methodName);
  			}
			
			this.log=loginfo;
	      } catch (NoSuchMethodException e) {
			e.printStackTrace();
	      } catch (SecurityException e) {
			e.printStackTrace();
	      } catch (Exception e) {
			e.printStackTrace();
		}
	    }
	

	
}
