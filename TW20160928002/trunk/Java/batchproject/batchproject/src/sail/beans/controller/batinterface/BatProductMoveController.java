package sail.beans.controller.batinterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import sail.beans.service.batinterface.BatProductMoveService;

/**
 * 成品移库（移出/移入）信息
 * @author cyj
 * 2017-02-19 15:47:21
 */
@Controller
public class BatProductMoveController {
	@Autowired
	private BatProductMoveService batProductMoveService = null;
	/**
	 * 新增移库
	 */
//	@Scheduled(cron = "0/5 * * * * ?")
	@Scheduled(cron = "0 0/5 * * * ?")
	public void saveBatProductMove(){
		if(batProductMoveService==null) return;
		batProductMoveService.saveBatProductMove();
	}
}
