package sail.beans.controller.batinterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import sail.beans.service.batinterface.BatHighBayDepotInService;

/**
 * 成品（成品/在制品）入库信息
 * @author cyj
 * 2017-01-12 14:52:34
 */
@Controller
public class BatHighBayDepotInController {
	@Autowired
	private BatHighBayDepotInService batHighBayDepotInService = null;
	/**
	 * 新增入库
	 */
//	@Scheduled(cron = "0/5 * * * * ?")
	@Scheduled(cron = "0 0/5 * * * ?")
	public void saveBatHighBayDepotIn(){
		if(batHighBayDepotInService==null) return;
		batHighBayDepotInService.saveBatHighBayDepotIn();
	}
}
