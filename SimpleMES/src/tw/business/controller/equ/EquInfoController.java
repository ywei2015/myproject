package tw.business.controller.equ;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import tw.business.entity.equ.EquInfo;
import tw.business.service.equ.EquInfoService;
import tw.sysbase.dao.GenericDao;
import tw.sysbase.entity.Pagination;
import tw.sysbase.pub.ResultUtil;

/**
 * 设备维保
 * @author cyj 2018-01-15
 * @version 1.0
 * 
 */
@Controller
@RequestMapping("/TzInfo")
public class EquInfoController {

	@Resource
	private GenericDao genericDao;
	@Resource
	private EquInfoService equInfoService;

	/**
	 * 获取设备台账列表数据
	 * @param findInfoList
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findInfoList")
	public Pagination findInfoList(EquInfo equInfo) {
		Pagination pagination = equInfoService
				.findInfoList(equInfo);
		return pagination;
	}	
	
	/**
	 * 通过id获取设备台账列表数据
	 * @param findInfoList
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findInfoById")
	public EquInfo findInfoById(String id) {
		EquInfo equInfo = equInfoService.findEquInfo(id);
		return equInfo;
	}
	
	/**
	 * 删除设备台账
	 * @param findInfoList
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deletesInfo")
	public Map<String,Object> deletesInfo(String ids){	
		equInfoService.deletesInfo(ids);
		return ResultUtil.DefaultResult();
	}
	
	@ResponseBody
	@RequestMapping(value = "/saveInfo")
	public Map<String,Object> saveInfo(EquInfo equInfo){
		equInfoService.saveInfo(equInfo);
		return ResultUtil.DefaultResult();
	}
}
