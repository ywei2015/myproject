/**
 * 
 */
package tw.business.service.equ;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tw.business.entity.equ.EquWbtask;
import tw.business.entity.equ.EquWbtaskDefine;
import tw.business.entity.equ.EquWbtaskStep;
import tw.business.entity.equ.EquWbtaskStepDefine;
import tw.business.entity.pub.DicView;
import tw.sysbase.dao.GenericDao;
import tw.sysbase.dao.PaginationSupport;
import tw.sysbase.entity.Pagination;
import tw.sysbase.entity.sec.User;
import tw.sysbase.pub.Constants;
import tw.sysbase.pub.ResponseBase;
import tw.sysbase.pub.ResultUtil;
import tw.sysbase.pub.StingUtil;
import tw.sysbase.servie.sec.SecurityService;
import tw.sysbase.servie.sec.UserService;

/**
 * 设备维保
 * @author cyj 2018-01-15
 * 
 */
@Service
@Transactional
public class EquWbTaskDefineService {

	@Resource
	private GenericDao genericDao;
	@Resource
	private SecurityService securityService;
	@Resource
	private UserService userService;
	@Autowired
	private DicViewService dicViewService;
	
	/**
	 * 获取设备维保任务定义列表数据
	 * 
	 * @param equWbtaskDefine
	 * @return
	 */
	public Pagination findEquWbtaskDefineList(EquWbtaskDefine equWbtaskDefine,String type) {
		String wbTaskType = equWbtaskDefine.getWbTasktype();
		if(StringUtils.isNotBlank(type)) {
			wbTaskType=type;
		}
		String taskName = equWbtaskDefine.getTaskName();
		taskName = (StringUtils.isBlank(taskName) ? taskName : "%" +taskName + "%");
		Object[] parameter = { wbTaskType,taskName };
		
		PaginationSupport paginationSupport = genericDao.getPageWithParams(
				"EQU.FETCH.WBTASKDEFINE.LIST", parameter, equWbtaskDefine);
		Pagination pagination = Pagination.init(paginationSupport);
		List<EquWbtaskDefine> rows = pagination.getRows();
		for(EquWbtaskDefine wbtaskDefine:rows) {
			//设置类型名称
			DicView dicView = dicViewService.getById("v_equ_tasktype",wbtaskDefine.getWbTasktype() );
			wbtaskDefine.setWbTasktypeName(dicView.getName());
			//作业对象
			DicView dicView1 = dicViewService.getById("v_equ_info",wbtaskDefine.getWorkObjid());
			wbtaskDefine.setWorkObjName(dicView1.getName());
			//任务接受对象
			DicView dicView2 = dicViewService.getById("v_sec_user",wbtaskDefine.getExecutor());
			wbtaskDefine.setExecutorName(dicView2.getName());
		}
		return pagination;
	}

	/**
	 * 获取设备维保任务定义步骤子表
	 * @param equWbtaskStepDefine
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ResponseBase findEquWbtaskStepDefineList(
			EquWbtaskStepDefine equWbtaskStepDefine) {
		String taskDefinePid = equWbtaskStepDefine.getTaskdefinePid();
		Object[] parameter = { taskDefinePid };
		
		ResponseBase res = new ResponseBase();
		String queryName = "EQU.FETCH.WBTASKSTEPDEFINE.LIST";
		List<EquWbtaskStepDefine> list = genericDao.getListWithVariableParas(queryName, parameter);
		res.setDataset(list, "rows");
		return res;
	}

	/**
	 * 获取设备维保任务定义步骤子表
	 * @param equWbtaskStepDefine
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<EquWbtaskStepDefine> fetchEquWbtaskStepDefineList(
			String taskDefinePid) { 
		Object[] parameter = { taskDefinePid };  
		String queryName = "EQU.FETCH.WBTASKSTEPDEFINE.LIST";
		List<EquWbtaskStepDefine> list = genericDao.getListWithVariableParas(queryName, parameter); 
		return list;
	}

	/**
	 * 获取设备维保任务定义详情
	 * @param id
	 * @return
	 */
	public EquWbtaskDefine getEquWbtaskDefineById(String id) {
		return (EquWbtaskDefine) genericDao.getById(EquWbtaskDefine.class, id);
	}
	
	/**
	 * 获取设备维保任务定义步骤详情
	 * @param id
	 * @return
	 */
	public EquWbtaskStepDefine getEquWbtaskStepDefineById(String id) {
		return (EquWbtaskStepDefine) genericDao.getById(EquWbtaskStepDefine.class, id);
	}
	/**
	 * 添加/修改设备维保任务定义
	 * @param equWbtaskDefine
	 * @return
	 */
	public EquWbtaskDefine saveEquWbtaskDefine(EquWbtaskDefine equWbtaskDefine) {
		Timestamp time = new Timestamp(System.currentTimeMillis()); 
		if(!StringUtils.isBlank(equWbtaskDefine.getPid()) == false){
			equWbtaskDefine.setPid(null);
			//equWbtaskDefine.setValidTimelong(10);
			equWbtaskDefine.setSysFlag("1");
			equWbtaskDefine.setCreator(securityService.getOperator());
			equWbtaskDefine.setCreateTime(time);
			genericDao.save(equWbtaskDefine);
		}else{
			EquWbtaskDefine define = (EquWbtaskDefine) this.genericDao.getById(EquWbtaskDefine.class, equWbtaskDefine.getPid());
			define.setTaskName(equWbtaskDefine.getTaskName());
			define.setTaskDes(equWbtaskDefine.getTaskDes());
			define.setValidTimelong(equWbtaskDefine.getValidTimelong());
			define.setExecTimeflag(equWbtaskDefine.getExecTimeflag());
			define.setWorkObjid(equWbtaskDefine.getWorkObjid());
			define.setWorkObjtype(equWbtaskDefine.getWorkObjtype());
			define.setSendeeType(equWbtaskDefine.getSendeeType());
			define.setExecutor(equWbtaskDefine.getExecutor());
			define.setInterval(equWbtaskDefine.getInterval());
			define.setFrequency(equWbtaskDefine.getFrequency());
			define.setRemark(equWbtaskDefine.getRemark());
			define.setLastModifier(securityService.getOperator());
			define.setLastModifiedTime(time);
			genericDao.save(define);
		}
		return null;
	}

	/**
	 * 添加/修改设备维保任务定义步骤详情
	 * @param equWbtaskStepDefine
	 * @return
	 */
	public EquWbtaskStepDefine saveEquWbtaskStepDefine(EquWbtaskStepDefine equWbtaskStepDefine) {
		Timestamp time = new Timestamp(System.currentTimeMillis()); 
		if(!StringUtils.isBlank(equWbtaskStepDefine.getPid()) == false){
			equWbtaskStepDefine.setPid(null);
			equWbtaskStepDefine.setSysFlag("1");
			equWbtaskStepDefine.setCreator(securityService.getOperator());
			equWbtaskStepDefine.setCreateTime(time);
			genericDao.save(equWbtaskStepDefine);
		}else{
			EquWbtaskStepDefine step = (EquWbtaskStepDefine) this.genericDao.getById(EquWbtaskStepDefine.class, equWbtaskStepDefine.getPid());
			step.setStepIndex(equWbtaskStepDefine.getStepIndex());
			step.setExecstdDes(equWbtaskStepDefine.getExecstdDes());
			step.setLastModifier(securityService.getOperator());
			step.setLastModifiedTime(time);
			genericDao.save(step);
		}
		return null;
	}
	
	/**
	 * 删除设备维保定义及任务步骤
	 * @param ids
	 */
	public void deleteEquWbtaskDefine(String ids) {
		Timestamp time = new Timestamp(System.currentTimeMillis()); 
		if (StringUtils.isBlank(ids)) {
			throw new RuntimeException("ids: IDS传入的参数为空!");
		}
		String[] idArray = ids.split(Constants.SEPARATOR_COLON);
		for (int i = 0; i < idArray.length; i++) {
			String id = idArray[i];
			List<EquWbtaskStepDefine> list = genericDao.getListWithVariableParas("EQU.FETCH.WBTASKSTEPDEFINE.LIST", new Object[]{id});
			for(int j = 0; j < list.size(); j++){
				EquWbtaskStepDefine step = list.get(j);
				step.setSysFlag(Constants.SYS_FLAG_DELETED);
				step.setLastModifier(securityService.getOperator());
				step.setLastModifiedTime(time);
				genericDao.save(step);
			}
			EquWbtaskDefine define = (EquWbtaskDefine) genericDao.getById(EquWbtaskDefine.class, id);
			define.setSysFlag(Constants.SYS_FLAG_DELETED);
			define.setLastModifier(securityService.getOperator());
			define.setLastModifiedTime(time);
			genericDao.save(define);
		}
	}
	
	/**
	 * 删除任务定义步骤
	 * @param ids
	 */
	public void deleteEquWbtaskStepDefine(String ids) {
		Timestamp time = new Timestamp(System.currentTimeMillis()); 
		if (StringUtils.isBlank(ids)) {
			throw new RuntimeException("ids: IDS传入的参数为空!");
		}
		String[] idArray = ids.split(Constants.SEPARATOR_COLON);
		for (int i = 0; i < idArray.length; i++) {
			String id = idArray[i];
			EquWbtaskStepDefine step = (EquWbtaskStepDefine) genericDao.getById(EquWbtaskStepDefine.class, id);
			step.setSysFlag(Constants.SYS_FLAG_DELETED);
			step.setLastModifier(securityService.getOperator());
			step.setLastModifiedTime(time);
			genericDao.save(step);
		}
	}
	
	/**
	 * 获取设备维保执行列表数据
	 * @param equWbtask
	 * @param type
	 * @return
	 */
	public Pagination findEquWbtaskList(EquWbtask equWbtask,String type) {
		String taskName=equWbtask.getTaskName();
		 taskName = (StringUtils.isBlank(taskName) ? taskName : "%" +taskName + "%");
		Object[] parameter = { taskName,type,equWbtask.getEquId(),equWbtask.getStatus() };

		PaginationSupport paginationSupport = genericDao.getPageWithParams(
				"EQU.FETCH.WBTASK.LIST", parameter, equWbtask);
		Pagination pagination = Pagination.init(paginationSupport);
		List<EquWbtask>rows=pagination.getRows();
		for(EquWbtask wbtask:rows) {
			//设置类型名称
			DicView dicView = dicViewService.getById("v_equ_tasktype",wbtask.getWbTasktype());
			wbtask.setWbTasktypeName(dicView.getName());
		
		}
		return pagination;
	}
	
	/**
	 * 获取设备维保执行步骤子表
	 * @param equWbtaskStep
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ResponseBase findEquWbtaskStepList(
			EquWbtaskStep equWbtaskStep) {
		String taskPid = equWbtaskStep.getTaskPid();
		Object[] parameter = { taskPid };
		ResponseBase res = new ResponseBase();
		String queryName = "EQU.FETCH.WBTASKSTEP.LIST";
		List<EquWbtaskStep> list = genericDao.getListWithVariableParas(queryName, parameter);
		for(int i=0;i<list.size();i++){
			if(StingUtil.isEmpty(list.get(i).getLastModifier()) == false){
				User user = userService.findUserById(list.get(i).getLastModifier());
				list.get(i).setLastModifierName(user.getName());
			}
		}
		res.setDataset(list, "rows");
		return res;
	}
	
	/**
	 * 获取设备维保任务执行界面
	 * @param id
	 * @return
	 */
	public EquWbtaskStep getEquWbtaskStepById(String id) {
		return (EquWbtaskStep) genericDao.getById(EquWbtaskStep.class, id);
	}
	/**
	 * 保存taskStep
	 * @param step
	 */
	public void saveTaskStep(EquWbtaskStep step) {
		genericDao.save(step);
		
	}
	
	/**
	 * 根据id获取equwbtask
	 * @param equId
	 * @return
	 */
	public EquWbtask getEquWbtask(String equId) {
		EquWbtask equWbtask = (EquWbtask) genericDao.getById(EquWbtask.class, equId);
		return equWbtask;
	}
	/**
	 * 根据id 更新任务状态 status =20 正常完成 ，完成的条件, 完成之前判断是否完成
	 * @param equWbtask
	 */
	public Object updateWbtask(EquWbtask equWbtask) {
		String pid = equWbtask.getPid();
		String sql="select count(*) from t_equ_wbtaskstep t where t.f_task_pid = ? and t.f_state  != ? ";
		Object[]params= {pid,1};
		BigInteger count=(BigInteger)genericDao.getUniqueResult(sql, params);
		if(count.intValue()!=0) {
			Map<String, Object> errorResult = ResultUtil.ErrorResult();
			errorResult.put(Constants.MESSAGE, "任务未完成，不能结束！");
			return errorResult;
		}
		equWbtask=this.getEquWbtask(pid);
		equWbtask.setStatus("20");
		equWbtask.setLastModifiedTime(new Timestamp(System.currentTimeMillis()));
		genericDao.save(equWbtask);
		return ResultUtil.DefaultResult();
	}
}
