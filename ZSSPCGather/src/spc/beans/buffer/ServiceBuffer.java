package spc.beans.buffer; 

import org.springframework.web.context.WebApplicationContext;
 

import spc.beans.initial.BaseInfoService;
import spc.beans.service.GatherDataService;
import spc.beans.service.TargetDBService;

public final class ServiceBuffer {
	public static WebApplicationContext appContext = null; //WebApplicationContext 
	
	private static BaseInfoService baseinfoService = null;
	public static BaseInfoService getBaseinfoService(){
		if(baseinfoService==null)
			if(appContext!=null)
				baseinfoService = appContext.getBean(BaseInfoService.class);
		return baseinfoService;
	}
	private static GatherDataService gatherService = null;
	public static GatherDataService getGatherService(){
		if(gatherService==null)
			if(appContext!=null)
				gatherService =  appContext.getBean(GatherDataService.class);
		return gatherService;
	}
	
	private static TargetDBService targetDbService = null;
	public static TargetDBService getTargetService(){
		if(targetDbService==null)
			if(appContext!=null)
				targetDbService = appContext.getBean(TargetDBService.class);
		return targetDbService;
	}
	
}
