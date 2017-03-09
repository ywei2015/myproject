package sail.beans.controller.batinterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import sail.beans.service.batinterface.BatTransToBaccoInCabinetService;

/**
 * 批次管理_PRODUCE_生产工单信息
 * @author cyj
 * 2017-02-03
 */
@Controller
public class BatTransToBaccoInCabinetController {
	@Autowired
	private BatTransToBaccoInCabinetService batTransToBaccoInCabinetService = null;
	/**
	 * 新增制丝中控－成品烟丝进柜
	 */
//	@Scheduled(cron = "0 49 16 * * ?")
	@Scheduled(cron = "0 0/10 * * * ?")
	public void saveBatTransToBaccoInCabinet(){
		if(batTransToBaccoInCabinetService==null) return;
		batTransToBaccoInCabinetService.saveBatTransToBaccoInCabinet();
	}
}
