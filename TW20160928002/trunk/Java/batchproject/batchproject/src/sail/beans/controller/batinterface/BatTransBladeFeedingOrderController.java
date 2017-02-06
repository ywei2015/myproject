package sail.beans.controller.batinterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import sail.beans.service.batinterface.BatTransBladeFeedingOrderService;

/**
 * 批次管理_PRODUCE_生产工单信息
 * @author cyj
 * 2017-02-06
 */
@Controller
public class BatTransBladeFeedingOrderController {
	@Autowired
	private BatTransBladeFeedingOrderService batTransBladeFeedingOrderService = null;
	/**
	 * 新增制丝中控－叶梗投料
	 */
	@Scheduled(cron = "0 49 16 * * ?")
//	@Scheduled(cron = "0 0/10 * * * ?")
	public void saveBatTransBladeFeedingOrder(){
		if(batTransBladeFeedingOrderService==null) return;
		batTransBladeFeedingOrderService.saveBatTransBladeFeedingOrder();
	}
}
