package spc.beans.job;

import org.springframework.beans.factory.annotation.Autowired;
 
import spc.beans.service.StatisticsService;

public class SpcStatisticalJob {

	@Autowired
	StatisticsService statisticsService;

	public void doStatisticsJob() throws InterruptedException{  
		statisticsService.doStatistical(); 
	}
	
}
