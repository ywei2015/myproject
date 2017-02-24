package sail.beans.controller.transfromdata;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import sail.beans.service.transfromdata.TransfromdataService;

/*
 * 生产工单转储服务类
 */
@Controller
@RequestMapping("/transfrom")
public class TransformData {
	
	@Resource
	private TransfromdataService transfromdataService;
	 /*
	  * 日卷包
	  */
	// @Scheduled(cron = "0 0 0 * * ?")
	@ResponseBody
	@RequestMapping(value="/transformDataJiZu")
	 public void transformDataJiZu(){
		 transfromdataService.transformDataJiZu();
	}
	 /*
	  * 周制丝
	  */
	 @Scheduled(cron = "0 0 0 * * ?")
	 public void transformDataSilk(){
		 transfromdataService.transformDataSilk();
	}
	 
	 @Scheduled(cron = "0 0 0 * * ?")
	 public void transformDataStalkSilk(){
		 transfromdataService.transformDataStalkSilk();
	} 
}
