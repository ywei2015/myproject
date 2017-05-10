package sail.beans.controller.transfromdata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import sail.beans.service.transfromdata.TransformProduceDateService;

/**
 * 工单产出物转储
 * @author yww
 * 2017-04-23
 * */
@Controller
@RequestMapping(value="transformpro")
public class TransformProduceDate {
	@Autowired
	private TransformProduceDateService transformProduceDateService;
	
    @Scheduled(cron = "0 0/10 * * * ?")
	@ResponseBody
	@RequestMapping(value="/transformProJB")
	public void transformJBProduceDate(){
		transformProduceDateService.transformJBProduceDate("TUB_DIC_2012");
	}
    @Scheduled(cron = "0 0/10 * * * ?")
	@ResponseBody
	@RequestMapping(value="/transformProCX")
	public void transformCXProduceDate(){
		transformProduceDateService.transformJBProduceDate("TUB_DIC_2011");
	}
	
	@Scheduled(cron = "0 0/10 * * * ?")
	@ResponseBody
	@RequestMapping(value="/transformProZS")
	public void transformZSProduceDate(){
		transformProduceDateService.transformZSProduceDate();
	}
	
	@Scheduled(cron = "0 0/10 * * * ?")
	@ResponseBody
	@RequestMapping(value="/transformProGS")
	public void transformGSProduceDate(){
		transformProduceDateService.transformGSProduceDate();
	}
}
