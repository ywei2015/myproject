package spc.beans.job;

import org.springframework.beans.factory.annotation.Autowired;

import spc.beans.service.GatherDataService;

  
public class GatherJob {

	@Autowired
	GatherDataService gatherService;  
	
	//@Scheduled(cron="0/3 * * * * ?")
	public void doGatherJob() throws InterruptedException{  
		/*
		System.out.println( String.format("getDBServerCurTime: %s " , AppInfo.getAppDuration()));
		System.out.println( String.format("SourceDBConnected: %s   | targetDBConnected: %s " , AppInfo.isSourceDBConnected(), AppInfo.isTargetDBConnected())); 
		BaseBuffer.ReloadSqlMap();//重新装载SQL字段组装 
		System.out.println( String.format("BaseBuffer.getSql: %s " , BaseBuffer.getSql("JXGX_HZ","2017-09-07 16:36:57")));
		*/ 
		gatherService.ReadOpcList();   
		//System.out.println( String.format("-----PointDataQueue Count: %s ----" , PointDataQueue.getSize()));
	}
}
