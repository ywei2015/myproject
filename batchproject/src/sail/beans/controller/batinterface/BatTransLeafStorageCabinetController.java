package sail.beans.controller.batinterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import sail.beans.service.batinterface.BatTransLeafStorageCabinetService;

/**
 * 批次管理_PRODUCE_生产工单信息
 * @author cyj
 * 2017-02-03
 */
@Controller
public class BatTransLeafStorageCabinetController {
	@Autowired
	private BatTransLeafStorageCabinetService batTransLeafStorageCabinetService = null;
	/**
	 * 新增制丝中控－叶片入配叶柜
	 */
//	@Scheduled(cron = "0 49 16 * * ?")
	@Scheduled(cron = "0 0/3 * * * ?")
	public void saveBatTransLeafStorageCabinet(){
		if(batTransLeafStorageCabinetService==null) return;
		batTransLeafStorageCabinetService.saveBatTransLeafStorageCabinet();
	}
}
