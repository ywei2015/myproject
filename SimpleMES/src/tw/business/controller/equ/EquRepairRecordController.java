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
import tw.business.service.equ.EquRepairRecordService;
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
@RequestMapping("/Record")
public class EquRepairRecordController {

	@Resource
	private GenericDao genericDao;
	@Resource
	private EquRepairRecordService equRepairRecordService;

	/**
	 * 获取设备申请
	 * @param findInfoList
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findRecordList")
	public Pagination findRecordList(String equName,String status) {
		Pagination pagination = equRepairRecordService
				.findRecordList(equName,status);
		return pagination;
	}	
	
	/**
	 * 提交验证
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/isCheck")
    public Map<String,Object> isCheck(String id){	
		equRepairRecordService.isCheck(id);
		return ResultUtil.DefaultResult();
	}
	
	/**
	 * 获取设备申请
	 * @param findInfoList
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findRecordById")
	public Pagination findRecordById(String id) {
		Pagination pagination = equRepairRecordService
				.findRecordById(id);
		return pagination;
	}
	
	/**
	 * 获取验证数据
	 * @param findInfoList
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findVerificat")
	public Pagination findVerificat(String equName,String status) {
		Pagination pagination = equRepairRecordService
				.findVerificat( equName, status);
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
		EquRepairform equRepairform = equRepairRecordService.findRepairById(id);
		return equRepairform;
	}
	
	/**
	 * 删除
	 * @param findInfoList
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deletes")
	public Map<String,Object> deletes(String id){	
		equRepairRecordService.deletes(id);
		return ResultUtil.DefaultResult();
	}
	
	
	/**
	 * 保存维修记录
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveRecord")
	public Map<String,Object> saveRecord(EquRepairform equRepairform){
		equRepairRecordService.saveRecord(equRepairform);
		return ResultUtil.DefaultResult();
	}
	/**
	 * 保存验证saveVerificat
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveVerificat")
	public Map<String,Object> saveVerificat(EquRepairform equRepairform){
		equRepairRecordService.saveVerificat(equRepairform);
		return ResultUtil.DefaultResult();
	}
	
	@ResponseBody
	@RequestMapping(value = "/findEqu")
	public Map<String, Object> findEqu(){
		List<DicView>list=equRepairRecordService.findEqu();
		Map<String, Object> result = ResultUtil.initResult();
		Map<String, Object> defResult = ResultUtil.DefResult(result, "data", list);
		return defResult ;
	}
}
