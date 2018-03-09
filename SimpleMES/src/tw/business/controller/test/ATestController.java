package tw.business.controller.test;

import java.text.ParseException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import tw.business.entity.equ.EquRepairform;
import tw.business.entity.equ.EquWbtask;
import tw.business.service.ATestService;  

@Controller
@RequestMapping("/testa")
public class ATestController {
 

	@Resource
	private ATestService testaService;

	/**
	 * 获取设备维保任务定义列表数据
	 * @param equWbtaskDefine
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findbydt")
	public  List<EquWbtask> getbydt() {
		List<EquWbtask> list = testaService.getlist();
		return list;
	}
	
	@ResponseBody
	@RequestMapping(value = "/getrepairlist")
	public List<EquRepairform> getrepairlist() throws ParseException{
		return testaService.getrepairlist();
	}
	
	
	
}
