package sail.beans.controller.batinterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import sail.beans.service.batinterface.BatTransLeafSpiceFeedingService;

/**
 * 批次管理_PRODUCE_生产工单信息
 * @author cyj
 * 2017-02-06
 */
@Controller
public class BatTransLeafSpiceFeedingController {
	@Autowired
	private BatTransLeafSpiceFeedingService batTransLeafSpiceFeedingService = null;
	/**
	 * 新增制丝中控－叶线香料稀释液投料
	 */
//	@Scheduled(cron = "0 39 09 * * ?")
//	@Scheduled(cron = "0/30 0/2 * * * ?")
	public void saveBatTransLeafSpiceFeeding(){
		if(batTransLeafSpiceFeedingService==null) return;
		batTransLeafSpiceFeedingService.SaveBatTransLeafSpiceFeeding();
	}
}
