package sail.beans.controller.batinterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import sail.beans.service.batinterface.BatPalletWorkorderService;

/**
 * 烟用材料配盘信息
 * @author cyj
 * 2017-01-19 14:52:34
 */
@Controller
public class BatPalletWorkorderController {
	@Autowired
	private BatPalletWorkorderService batPalletWorkorderService = null;
	/**
	 * 新增烟用材料配盘
	 */
//	@Scheduled(cron = "0 27 11 * * ?")
	@Scheduled(cron = "0 0/10 * * * ?")
	public void saveBatPalletWorkorder(){
		if(batPalletWorkorderService==null) return;
		batPalletWorkorderService.saveBatPalletWorkorder();
	}
}
