package tw.business.service.equ;
 
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.business.entity.equ.EquInfo;
import tw.business.entity.equ.EquWbtask;
import tw.business.entity.equ.EquWbtaskDefine;
import tw.business.entity.equ.EquWbtaskStep;
import tw.business.entity.equ.EquWbtaskStepDefine;
import tw.business.service.workcalendar.WorkCalendarService;
import tw.sysbase.dao.GenericDao;
import tw.sysbase.entity.sec.User;
import tw.sysbase.servie.sec.UserService;
import tw.utils.DatetimeEx; 

/**
 * 设备维保任务自动生成方法
 * @author XSH
 *
 */
@Service
@Transactional
public class EquTaskGenerateService {
	Logger logger = Logger.getLogger(EquTaskGenerateService.class);

	@Resource
	private GenericDao genericDao;

	@Resource
	private EquWbTaskDefineService equWbTaskDefineService;

	@Resource
	private WorkCalendarService workCalendarService;
	
	@Resource
	private EquInfoService equInfoService;
	@Resource
	private UserService userService;   
	
	public String generateTaskByDefineId(String equWbTaskDefinePid){ 
		EquWbtaskDefine taskdefine =  equWbTaskDefineService.getEquWbtaskDefineById(equWbTaskDefinePid);
		Date dt = new Date(new java.util.Date().getTime()); 
		if(taskdefine==null||"".equals(taskdefine)) return null; 
		if(WbTaskConstant.FREQUENCY_BY_SHIFT.equals(taskdefine.getFrequency())){
			return null;
		}else if(WbTaskConstant.FREQUENCY_BY_DAY.equals(taskdefine.getFrequency())){
			boolean b = workCalendarService.isWorkDay(WbTaskConstant.CALENDAR_TYPE_NORMAL, DatetimeEx.toStr10(dt));
			if(!b) return null;
			Timestamp sdt = DatetimeEx.sqlTimestampST(dt);
			Timestamp edt = DatetimeEx.sqlTimestampET(dt);
			if(WbTaskConstant.WBTASK_WORKOBJTYPE_EQUID.equals(taskdefine.getWorkObjtype())){ //当设置为机台时 
				EquInfo equobj = equInfoService.findEquInfo(taskdefine.getWorkObjid()); 
				User exector = userService.findUserById(taskdefine.getExecutor());
				if(equobj==null||exector==null) return null;
				String taskname = String.format("%s%s-%s", taskdefine.getTaskName(), equobj.getEquCode(), DatetimeEx.toStr(dt, "yyMMdd")); 
				String s = generateTask(taskdefine, dt,sdt,edt, taskname,equobj,exector);
				return s;
			}else{
				//当设置为机型时暂不考虑 
			}
		}else if(WbTaskConstant.FREQUENCY_BY_WEEK.equals(taskdefine.getFrequency())){    
			Timestamp sdt = DatetimeEx.getBeginDayOfWeek(dt);
			Timestamp edt = DatetimeEx.getEndDayOfWeek(dt);
			if(WbTaskConstant.WBTASK_WORKOBJTYPE_EQUID.equals(taskdefine.getWorkObjtype())){ //当设置为机台时 
				EquInfo equobj = equInfoService.findEquInfo(taskdefine.getWorkObjid()); 
				User exector = userService.findUserById(taskdefine.getExecutor());
				if(equobj==null||exector==null) return null;
				String taskname = String.format("%s%s-wk%s", taskdefine.getTaskName(), equobj.getEquCode(), DatetimeEx.getWeekofYear(dt)); 
				String s = generateTask(taskdefine, dt,sdt,edt, taskname,equobj,exector);
				return s;
			}else{
				//当设置为机型时暂不考虑 
			}
		}else if(WbTaskConstant.FREQUENCY_BY_MONTH.equals(taskdefine.getFrequency())){   
			Timestamp sdt = DatetimeEx.getBeginDayOfMonth(dt);
			Timestamp edt = DatetimeEx.getEndDayOfMonth(dt);
			if(WbTaskConstant.WBTASK_WORKOBJTYPE_EQUID.equals(taskdefine.getWorkObjtype())){ //当设置为机台时 
				EquInfo equobj = equInfoService.findEquInfo(taskdefine.getWorkObjid()); 
				User exector = userService.findUserById(taskdefine.getExecutor());
				if(equobj==null||exector==null) return null;
				String taskname = String.format("%s%s-M%s", taskdefine.getTaskName(), equobj.getEquCode(), DatetimeEx.getWeekofYear(dt)); 
				String s = generateTask(taskdefine, dt,sdt,edt, taskname,equobj,exector);
				return s;
			}else{
				//当设置为机型时暂不考虑 
			}
		}
		return ""; 
	}
	/**
	 * 是否生成过任务 ,存在返回true
	 */
	private Boolean hasGenerateTask(EquWbtaskDefine taskdefine) {
		Object[]params= { taskdefine.getPid(),taskdefine.getWbTasktype(),new java.util.Date()};
		List list = genericDao.getObjectsWithSql("SELECT T FROM tw.business.entity.equ.EquWbtask T WHERE T.sysFlag='1' and T.wbtaskdefinePid = ?" + 
				"				and T.wbTasktype = ? and T.date = ? ",params);
		if(list!=null && list.size()>=1) {
			return true;
		}
		return false;
	}
	//生成一个任务
	public String generateTask(EquWbtaskDefine taskdefine, Date thedate, Timestamp startDT, Timestamp endDT, String taskname, EquInfo equinfo, User executor){
		//String task_uuid = null;
		Boolean flag = this.hasGenerateTask(taskdefine);
		if(flag) {
			return "1";
		}
		logger.info(String.format("generateTask: %s,%s,%s,%s,%s", taskdefine.getPid(),thedate,taskname,equinfo.getEquName(), executor.getName()));
		EquWbtask equWbTask = new EquWbtask();
		//task_uuid = UUID.randomUUID().toString(); 
		//equWbTask.setPid(task_uuid); 
		equWbTask.setTaskName(taskname); 
		equWbTask.setWbTasktype(taskdefine.getWbTasktype());  
		equWbTask.setTaskDes(taskdefine.getTaskDes());  
		equWbTask.setWbtaskdefinePid(taskdefine.getPid()); 
		equWbTask.setEquId(equinfo.getPid()); 
		equWbTask.setEquCode(equinfo.getEquCode()); 
		equWbTask.setEquName(equinfo.getEquName()); 
		equWbTask.setExecutor(executor.getId());
		equWbTask.setExecutorName(executor.getName());
		equWbTask.setFrequency(taskdefine.getFrequency());
		equWbTask.setDate(thedate); 
		equWbTask.setStatus(WbTaskConstant.WBTASK_STATUS_ISSUED);
		equWbTask.setTransmitTime(startDT);
		equWbTask.setPlanBegintime(startDT);
		equWbTask.setPlanEndtime(endDT);  
		equWbTask.setSysFlag(WbTaskConstant.DefaultSysFlag);
		equWbTask.setCreateTime(DatetimeEx.sqlTimestamp());
		equWbTask.setCreator(WbTaskConstant.CREATOR_USERID); //admin
		genericDao.save(equWbTask);
		logger.info(equWbTask.getPid()+" Steps:"+generateTaskStep(taskdefine, equWbTask.getPid()));
		return equWbTask.getPid();
	}
	
	//生成一个任务的步骤
	public Integer generateTaskStep(EquWbtaskDefine taskdefine, String newtaskid){
		int n = 0;
		List<EquWbtaskStepDefine> taskstepdefinelist =  equWbTaskDefineService.fetchEquWbtaskStepDefineList(taskdefine.getPid());
		if(taskstepdefinelist==null||taskstepdefinelist.isEmpty()) return 0;		
		for(int i=0;i<taskstepdefinelist.size();i++){
			EquWbtaskStepDefine taskstepdefine = taskstepdefinelist.get(i);
			EquWbtaskStep taskstep = new EquWbtaskStep();
			taskstep.setTaskPid(newtaskid);
			taskstep.setFillType(taskstepdefine.getFillType());
			taskstep.setStepIndex(taskstepdefine.getStepIndex());
			taskstep.setExecstdDes(taskstepdefine.getExecstdDes());
			taskstep.setState("0"); //默认状态为零未执行
			taskstep.setSysFlag(WbTaskConstant.DefaultSysFlag);
			taskstep.setCreateTime(DatetimeEx.sqlTimestamp());
			taskstep.setCreator(WbTaskConstant.CREATOR_USERID);
			genericDao.save(taskstep);
			n++;
		}
		return n;
	}
	
}
