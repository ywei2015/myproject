package sail.beans.controller.batinterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import sail.beans.service.batinterface.BatTransLeafFeedingOrderService;

/**
 * 批次管理_PRODUCE_生产工单信息
 * @author cyj
 * 2017-02-06
 */
@Controller
public class BatTransLeafFeedingOrderController {
	@Autowired
	private BatTransLeafFeedingOrderService batTransLeafFeedingOrderService = null;
	/**
	 * 新增制丝中控－叶片投料
	 */
//	@Scheduled(cron = "0 49 16 * * ?")
	@Scheduled(cron = "0 0/3 * * * ?")
	public void saveBatTransLeafFeedingOrder(){
		if(batTransLeafFeedingOrderService==null) return;
		batTransLeafFeedingOrderService.saveBatTransLeafFeedingOrder();
	}
}
