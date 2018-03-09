package spc.beans.job;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;


import com.sun.istack.internal.logging.Logger;

import spc.beans.buffer.AppInfo;
import spc.beans.initial.BaseInfoService;
//import spc.beans.service.TargetDBService; 
import spc.beans.service.GatherDataService;
 
//@Component
public class RefreshDBConnectJob {

	@Autowired
	GatherDataService gatherService;  
	@Autowired
	BaseInfoService baseService;    
//	@Autowired
//	TargetDBService targetdbservice;   
	  
	public void RefreshSourceDBConnect(){ 
		//System.out.println(String.format("AppDuration: %s ",AppInfo.getAppDuration()));
		try{
			Date dt1 = gatherService.getDBServerCurTime();
			if(dt1!=null) {
				AppInfo.sourceDBRequestTime = new Date();
				AppInfo.sourceDBTime = dt1;
			}
		}catch(Exception e){
			Logger.getLogger(RefreshDBConnectJob.class).info(e.getMessage());
		}
		//System.out.println(String.format("AppDuration: %s ",AppInfo.getAppDuration()));
	}
	public void RefreshTargetDBConnect(){ 
		//System.out.println(String.format("AppDuration: %s ",AppInfo.getAppDuration()));
		try{
			Date dt2 = baseService.getDBServerTime();
//			Date dt2 = targetdbservice.getDBServerTime();
			if(dt2!=null) {
				AppInfo.targetDBRequestTime = new Date();
				AppInfo.targetDBTime = dt2;
			}
		}catch(Exception e){
			Logger.getLogger(RefreshDBConnectJob.class).info(e.getMessage());
		}
	}
	
	
}
