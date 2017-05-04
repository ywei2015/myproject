package sail.beans.controller.batinterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import sail.beans.service.batinterface.BatTransLeafessenceFeedingService;

/**
 * 批次管理_PRODUCE_生产工单信息
 * @author cyj
 * 2017-02-03
 */
@Controller
public class BatTransLeafessenceFeedingController {
	@Autowired
	private BatTransLeafessenceFeedingService batTransLeafessenceFeedingService = null;
	/**
	 * 新增制丝中控－丝线香精投料
	 */
//	@Scheduled(cron = "0 52 10 * * ?")
	@Scheduled(cron = "0 0/2 * * * ?")
	public void saveBatTransLeafessenceFeedingService(){
		if(batTransLeafessenceFeedingService==null) return;
		batTransLeafessenceFeedingService.SaveBatTransLeafessenceFeeding();
	}
}
