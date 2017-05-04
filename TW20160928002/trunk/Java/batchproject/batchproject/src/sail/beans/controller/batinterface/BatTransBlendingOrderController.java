package sail.beans.controller.batinterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import sail.beans.service.batinterface.BatTransBlendingOrderService;

/**
 * 批次管理_PRODUCE_生产工单信息
 * @author cyj
 * 2017-02-03
 */
@Controller
public class BatTransBlendingOrderController {
	@Autowired
	private BatTransBlendingOrderService batTransBlendingOrderService = null;
	/**
	 * 新增制丝中控－五丝掺配
	 */
//	@Scheduled(cron = "0 47 10 * * ?")
	@Scheduled(cron = "0 0/2 * * * ?")
	public void saveBatTransBlendingOrder(){
		if(batTransBlendingOrderService==null) return;
		batTransBlendingOrderService.SaveBatTransBlendingOrder();
	}
}
