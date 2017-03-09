package sail.beans.controller.batinterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import sail.beans.service.batinterface.BatTransPeduncleCabinetService;

/**
 * 批次管理_PRODUCE_生产工单信息
 * @author cyj
 * 2017-02-06
 */
@Controller
public class BatTransPeduncleCabinetController {
	@Autowired
	private BatTransPeduncleCabinetService batTransPeduncleCabinetService = null;
	/**
	 * 新增制丝中控－梗丝进柜
	 */
//	@Scheduled(cron = "0 49 16 * * ?")
	@Scheduled(cron = "0 0/3 * * * ?")
	public void saveBatTransPeduncleCabinet(){
		if(batTransPeduncleCabinetService==null) return;
		batTransPeduncleCabinetService.saveBatTransPeduncleCabinet();
	}
}
