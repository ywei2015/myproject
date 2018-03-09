package tw.business.controller.produce;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import tw.business.entity.produce.ProduceIn;
import tw.business.entity.produce.ProduceOut;
import tw.business.entity.produce.ProduceWo;
import tw.business.service.produce.ProduceService;
import tw.sysbase.dao.GenericDao;
import tw.sysbase.dao.PaginationSupport;
import tw.sysbase.entity.Pagination;
import tw.sysbase.pub.ResultUtil;

/**
 * 生产工单
 * @author cyj 2018-01-23
 * @version 1.0
 * 
 */
@Controller
@RequestMapping("/Produce")
public class ProduceController {

	@Resource
	private GenericDao genericDao;
	@Resource
	private ProduceService produceService;

	/**
	 * 获取生产工单列表数据
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findProduceWoList")
	public Pagination findProduceWoList(String startTime,String endTime,String pwoType) {
		PaginationSupport ps = produceService.findProduceWoList(startTime, endTime,pwoType); 
        Pagination pt = Pagination.init(ps);
        return pt;
	}

	/**
	 * 获取生产工单详情
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getProduceWoById")
	public ProduceWo getProduceWoById(String id) {
		ProduceWo produceWo = produceService.getProduceWoById(id);
		return produceWo;
	}
	
	/**
	 * 添加/修改生产工单
	 * @param produceWo
	 * @return
	 */
	@RequestMapping("saveProduceWo")
	@ResponseBody
	public Map<String,Object> saveProduceWo(ProduceWo produceWo){	
		produceWo = (ProduceWo) produceService.saveProduceWo(produceWo);
		return ResultUtil.DefaultResult();
	}
	
	/**
	 * 工单下达
	 * @param ids
	 * @return
	 */
	@RequestMapping("saveIssueProduceWo")
	@ResponseBody
	public Map<String,Object> saveIssueProduceWo(String ids){	
		produceService.saveIssueProduceWo(ids);
		return ResultUtil.DefaultResult();
	}
	
	/**
	 * 删除生产工单记录
	 * @param ids
	 * @return
	 */
	@RequestMapping("deleteProduceWo")
	@ResponseBody
	public Map<String,Object> deleteProduceWo(String ids){
		produceService.deleteProduceWo(ids);
		return ResultUtil.DefaultResult();
	}
	
	/**
	 * 获取生产工单执行列表数据
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findProducePerformList")
	public Pagination findProducePerformList(String startTime,String endTime) {
		PaginationSupport ps = produceService.findProducePerformList(startTime, endTime); 
        Pagination pt = Pagination.init(ps);
        return pt;
	}
	
	/**
	 * 开始工单
	 * @param ids
	 * @return
	 */
	@RequestMapping("saveBeginProduceWo")
	@ResponseBody
	public Map<String,Object> saveBeginProduceWo(String ids){	
		produceService.saveBeginProduceWo(ids);
		return ResultUtil.DefaultResult();
	}
	
	/**
	 * 结束工单
	 * @param ids
	 * @return
	 */
	@RequestMapping("saveEndProduceWo")
	@ResponseBody
	public Map<String,Object> saveEndProduceWo(String ids){	
		produceService.saveEndProduceWo(ids);
		return ResultUtil.DefaultResult();
	}
	
	/**
	 * 获取生产工单投入
	 * @param produceIn
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findProduceInList")
	public Pagination findProduceInList(ProduceIn produceIn) {
		PaginationSupport ps = produceService.findProduceInList(produceIn);
        Pagination pt = Pagination.init(ps);
        return pt;
	}

	/**
	 * 获取生产工单产出
	 * @param produceOut
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findProduceOutList")
	public Pagination findProduceOutList(ProduceOut produceOut) {
		PaginationSupport ps = produceService.findProduceOutList(produceOut);
        Pagination pt = Pagination.init(ps);
        return pt;
	}
	
	/**
	 * 获取生产工单投入详情
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getProduceInById")
	public ProduceIn getProduceInById(String id) {
		ProduceIn produceIn = produceService.getProduceInById(id);
		return produceIn;
	}
	
	/**
	 * 添加/修改生产工单投入
	 * @param produceIn
	 * @return
	 */
	@RequestMapping("saveProduceIn")
	@ResponseBody
	public Map<String,Object> saveProduceIn(ProduceIn produceIn){	
		produceIn = (ProduceIn) produceService.saveProduceIn(produceIn);
		return ResultUtil.DefaultResult();
	}
	
	/**
	 * 删除生产工单投入记录
	 * @param ids
	 * @return
	 */
	@RequestMapping("deleteProduceIn")
	@ResponseBody
	public Map<String,Object> deleteProduceIn(String ids){
		produceService.deleteProduceIn(ids);
		return ResultUtil.DefaultResult();
	}
	
	/**
	 * 添加/修改生产工单产出
	 * @param produceIn
	 * @return
	 */
	@RequestMapping("saveProduceOut")
	@ResponseBody
	public Map<String,Object> saveProduceOut(ProduceOut produceOut){	
		produceOut = (ProduceOut) produceService.saveProduceOut(produceOut);
		return ResultUtil.DefaultResult();
	}
	
	/**
	 * 删除生产工单产出记录
	 * @param ids
	 * @return
	 */
	@RequestMapping("deleteProduceOut")
	@ResponseBody
	public Map<String,Object> deleteProduceOut(String ids){
		produceService.deleteProduceOut(ids);
		return ResultUtil.DefaultResult();
	}
	
	/**
	 * 获取生产工单产出详情
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getProduceOutById")
	public ProduceOut getProduceOutById(String id) {
		ProduceOut produceOut = produceService.getProduceOutById(id);
		return produceOut;
	}
	
}
