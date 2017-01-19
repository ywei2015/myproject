package sail.beans.controller.batinterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import sail.beans.service.batinterface.BatTransferService;

/**
 * 成品调运信息信息
 * @author cyj
 * 2017-01-19 15:19:42
 */
@Controller
public class BatTransferController {
	@Autowired
	private BatTransferService batTransferService = null;
	/**
	 * 新增成品调运信息
	 */
//	@Scheduled(cron = "0 33 15 * * ?")
	@Scheduled(cron = "0 0/10 * * * ?")
	public void saveBatTransfer(){
		if(batTransferService==null) return;
		batTransferService.saveBatTransfer();
	}
}
