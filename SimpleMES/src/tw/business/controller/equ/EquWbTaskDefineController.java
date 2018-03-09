package tw.business.controller.equ;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import tw.business.entity.equ.EquWbtask;
import tw.business.entity.equ.EquWbtaskDefine;
import tw.business.entity.equ.EquWbtaskStep;
import tw.business.entity.equ.EquWbtaskStepDefine;
import tw.business.service.equ.EquWbTaskDefineService;
import tw.sysbase.dao.GenericDao;
import tw.sysbase.entity.Pagination;
import tw.sysbase.pub.Constants;
import tw.sysbase.pub.ResponseBase;
import tw.sysbase.pub.ResultUtil;

/**
 * 设备维保
 * @author cyj 2018-01-15
 * @version 1.0
 * 
 */
@Controller
@RequestMapping("/WbtaskDefine")
public class EquWbTaskDefineController {

	@Resource
	private GenericDao genericDao;
	@Resource
	private EquWbTaskDefineService equWbTaskDefineService;

	/**
	 * 获取设备维保任务定义列表数据
	 * @param equWbtaskDefine
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findEquWbtaskDefineList")
	public Pagination findEquWbtaskDefineList(EquWbtaskDefine equWbtaskDefine,String type) {
		Pagination pagination = equWbTaskDefineService
				.findEquWbtaskDefineList(equWbtaskDefine, type);
		return pagination;
	}
	
	/*@ResponseBody
	@RequestMapping(value = "/findEquWbtaskStepDefineList")
	public Pagination findEquWbtaskStepDefineList(EquWbtaskStepDefine equWbtaskStepDefine) {
		Pagination pagination = equWbTaskDefineService
				.findEquWbtaskStepDefineList(equWbtaskStepDefine);
		return pagination;
	}*/
	/**
	 * 获取设备维保任务定义步骤子表
	 * @param equWbtaskStepDefine
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "findEquWbtaskStepDefineList")
	public Pagination findEquWbtaskStepDefineList(EquWbtaskStepDefine equWbtaskStepDefine) {
		ResponseBase res = new ResponseBase();
		Pagination pagination=new Pagination();
			res = equWbTaskDefineService
					.findEquWbtaskStepDefineList(equWbtaskStepDefine);
			List list = (List) res.getDataset().get("rows");
			pagination.setRows(list);
			if(list!=null){
				pagination.setTotal(list.size());
			}
		return pagination;
	}

	/**
	 * 获取设备维保任务定义详情
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getEquWbtaskDefineById")
	public EquWbtaskDefine getEquWbtaskDefineById(String id) {
		EquWbtaskDefine equWbtaskDefine = equWbTaskDefineService.getEquWbtaskDefineById(id);
		return equWbtaskDefine;
	}
	
	/**
	 * 获取设备维保任务定义步骤详情
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getEquWbtaskStepDefineById")
	public EquWbtaskStepDefine getEquWbtaskStepDefineById(String id) {
		EquWbtaskStepDefine equWbtaskStepDefine = equWbTaskDefineService.getEquWbtaskStepDefineById(id);
		return equWbtaskStepDefine;
	}
	
	/**
	 * 添加/修改设备维保任务定义
	 * @param equWbtaskDefine
	 * @return
	 */
	@RequestMapping("saveEquWbtaskDefine")
	@ResponseBody
	public Map<String,Object> saveEquWbtaskDefine(EquWbtaskDefine equWbtaskDefine){	
		equWbtaskDefine = (EquWbtaskDefine) equWbTaskDefineService.saveEquWbtaskDefine(equWbtaskDefine);
		return ResultUtil.DefaultResult();
	}
	
	/**
	 * 添加/修改设备维保任务定义步骤详情
	 * @param equWbtaskStepDefine
	 * @return
	 */
	@RequestMapping("saveEquWbtaskStepDefine")
	@ResponseBody
	public Map<String,Object> saveEquWbtaskStepDefine(EquWbtaskStepDefine equWbtaskStepDefine){	
		equWbtaskStepDefine = (EquWbtaskStepDefine) equWbTaskDefineService.saveEquWbtaskStepDefine(equWbtaskStepDefine);
		return ResultUtil.DefaultResult();
	}
	
	/**
	 * 删除设备维保定义及任务步骤
	 * @param ids
	 * @return
	 */
	@RequestMapping("deleteEquWbtaskDefine")
	@ResponseBody
	public Map<String,Object> deleteEquWbtaskDefine(String ids){
		equWbTaskDefineService.deleteEquWbtaskDefine(ids);
		return ResultUtil.DefaultResult();
	}
	
	/**
	 * 删除设备维保定义任务步骤
	 * @param ids
	 * @return
	 */
	@RequestMapping("deleteEquWbtaskStepDefine")
	@ResponseBody
	public Map<String,Object> deleteEquWbtaskStepDefine(String ids){
		equWbTaskDefineService.deleteEquWbtaskStepDefine(ids);
		return ResultUtil.DefaultResult();
	}
	
	/**
	 * 获取设备维保执行列表数据
	 * @param equWbtask
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findEquWbtaskList")
	public Pagination findEquWbtaskList(EquWbtask equWbtask,String type) {
		Pagination pagination = equWbTaskDefineService
				.findEquWbtaskList(equWbtask,type);
		return pagination;
	}
	
	/**
	 * 获取设备维保执行步骤子表
	 * @param equWbtaskStepDefine
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "findEquWbtaskStepList")
	public Pagination findEquWbtaskStepList(EquWbtaskStep equWbtaskStep) {
		ResponseBase res = new ResponseBase();
		Pagination pagination=new Pagination();
			res = equWbTaskDefineService
					.findEquWbtaskStepList(equWbtaskStep);
			List list = (List) res.getDataset().get("rows");
			pagination.setRows(list);
			if(list!=null){
				pagination.setTotal(list.size());
			}
			
		return pagination;
	}
	/**
	 * 执行维保任务 。---更新操作
	 */
	@ResponseBody
	@RequestMapping("updatEquWbtaskStep")
	public  Map<String,Object> updatEquWbtaskStep(EquWbtaskStep equWbtaskStep,HttpServletRequest request){
		String pid = equWbtaskStep.getPid();
		EquWbtaskStep step = equWbTaskDefineService.getEquWbtaskStepById(pid);
		String uid = request.getSession().getAttribute(Constants.SESSION_KEY_OPERATOR).toString();
		step.setLastModifier(uid);
		step.setAbnormalDes(equWbtaskStep.getAbnormalDes());
		step.setIsabnormal(equWbtaskStep.getIsabnormal());
		step.setState("1");
		step.setLastModifiedTime(new Timestamp(System.currentTimeMillis()));
		equWbTaskDefineService.saveTaskStep(step);
		return ResultUtil.DefaultResult();
	}
	/**
	 * 获取设备维保任务执行界面
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getEquWbtaskStepById")
	public EquWbtaskStep getEquWbtaskStepById(String id) {
		EquWbtaskStep equWbtaskStep = equWbTaskDefineService.getEquWbtaskStepById(id);
		return equWbtaskStep;
	}
	/**
	 * 完成任务，完成任务之前检测是否
	 * @param equWbtask
	 * @return
	 */
	@ResponseBody
	@RequestMapping("updateWbTask")
	public Object updateWbTask(EquWbtask equWbtask){
		
		Object o=equWbTaskDefineService.updateWbtask(equWbtask);
		return o;
	}
}
