package sail.beans.controller.transfromdata;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import sail.beans.service.transfromdata.TransformInputDateService;

@Controller
@RequestMapping("/transfrominput")
public class TransformInputData {
	@Resource
	private TransformInputDateService transformInputDateService;
	
	 @ResponseBody
	 @Scheduled(cron = "0 0 * * * ?")
	 @RequestMapping(value="/transfromadjust")
	public void TransformAdjustData(){
		transformInputDateService.TransformAdjustData();
	}
	 @ResponseBody
	 @Scheduled(cron = "0 0 * * * ?")
	 @RequestMapping(value="/transfromfiltertipput")
	public void TransformFiltertipPut(){
		transformInputDateService.TransformFiltertipPut();
	}
}
