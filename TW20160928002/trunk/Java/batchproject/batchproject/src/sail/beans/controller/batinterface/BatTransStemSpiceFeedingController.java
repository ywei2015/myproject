package sail.beans.controller.batinterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import sail.beans.service.batinterface.BatTransStemSpiceFeedingService;

/**
 * 批次管理_PRODUCE_生产工单信息
 * @author cyj
 * 2017-02-06
 */
@Controller
public class BatTransStemSpiceFeedingController {
	@Autowired
	private BatTransStemSpiceFeedingService batTransStemSpiceFeedingService = null;
	/**
	 * 新增制丝中控－梗丝香精香料投料
	 */
//	@Scheduled(cron = "0 18 10 * * ?")
//	@Scheduled(cron = "0/30 0/2 * * * ?")
	public void saveBatTransStemSpiceFeeding(){
		if(batTransStemSpiceFeedingService==null) return;
		batTransStemSpiceFeedingService.SaveBatTransStemSpiceFeeding();
	}
}
