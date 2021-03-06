package sail.beans.controller.transfromdata;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
    @Scheduled(cron = "0 0/30 * * * ?")
	@ResponseBody
	@RequestMapping(value="/transformDataJiZu")
	 public void transformDataJiZu(){
		 transfromdataService.transformDataJiZu();
	}
	 /*
	  * 周制丝
	  */
    @Scheduled(cron = "0 0/15 * * * ?")
	@ResponseBody
	@RequestMapping(value="/transformDataSilk")
	 public void transformDataSilk(){
		 transfromdataService.transformDataSilk();
		 transfromdataService.transformDataSilk2();
	}
	 
    @Scheduled(cron = "0 0/30 * * * ?")
	@ResponseBody
	@RequestMapping(value="/transformDataStalk")
	 public void transformDataStalkSilk(){
		 transfromdataService.transformDataStalkSilk();
	} 
    
    @ResponseBody
	@RequestMapping(value="/transformDataBuFa")
	 public void transformDataBuFa(HttpServletRequest request){
    	String batch=request.getParameter("batch");
		 transfromdataService.transformDataSilkBF(batch);
		 transfromdataService.transformDataSilkBF2(batch);
	} 
}
