package tw.business.controller.equ;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import tw.business.entity.equ.EquRepairform;
import tw.business.entity.pub.DicView;
import tw.business.service.equ.EquRepairApplyService;
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
@RequestMapping("/Repair")
public class EquRepairApplyController {

	@Resource
	private GenericDao genericDao;
	@Resource
	private EquRepairApplyService equRepairApplyService;

	/**
	 * 获取设备申请
	 * @param findInfoList
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/equRepairApplyList")
	public Pagination findEquRepairApplyList(String startTime,String endTime,String equName,String status) {
		Pagination pagination = equRepairApplyService
				.findRepairApplyList(startTime,endTime,equName,status);
		return pagination;
	}	
	
	/**
	 * 通过id查找
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findRepairById")
	public EquRepairform findRepairById(String id) {
		EquRepairform equRepairform = equRepairApplyService.findRepairById(id);
		return equRepairform;
	}
	
	/**
	 * 删除设备台账
	 * @param findInfoList
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deletes")
	public Map<String,Object> deletes(String ids){	
		equRepairApplyService.deletes(ids);
		return ResultUtil.DefaultResult();
	}
	
	/**
	 * 新增/修改
	 * @param equRepairform
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/save")
	public Map<String,Object>  save(EquRepairform equRepairform){
		equRepairApplyService.save(equRepairform);
		return ResultUtil.DefaultResult();
	}
	
	/**
	 * 保存维修记录
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveRecord")
	public Map<String,Object> saveRecord(EquRepairform equRepairform){
		equRepairApplyService.saveRecord(equRepairform);
		return ResultUtil.DefaultResult();
	}
	
	/**
	 * 提交草稿
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/isCheckUpdate")
    public Map<String,Object> isCheckUpdate(String id){	
		equRepairApplyService.isCheckUpdate(id);
		return ResultUtil.DefaultResult();
	}
	
	@ResponseBody
	@RequestMapping(value = "/findEqu")
	public Map<String, Object> findEqu(){
		List<DicView>list=equRepairApplyService.findEqu();
		Map<String, Object> result = ResultUtil.initResult();
		Map<String, Object> defResult = ResultUtil.DefResult(result, "data", list);
		return defResult ;
	}
}
