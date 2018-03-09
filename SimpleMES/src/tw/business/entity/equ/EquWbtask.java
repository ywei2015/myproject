package tw.business.entity.equ;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import tw.business.entity.pub.BasicData;
/**
 * 设备维保具体任务
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_equ_wbtask")
public class EquWbtask extends BasicData{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name = "F_PID")
	private String pid;

	/**
	 * 任务名称
	 */
	@Column(name = "F_TASK_NAME")
	private String taskName;

	/**
	 * 任务类别
	 */
	@Column(name = "F_WB_TASKTYPE")
	private String wbTasktype;
	@Transient
	private String wbTasktypeName;
	
	public String getWbTasktypeName() {
		return wbTasktypeName;
	}

	public void setWbTasktypeName(String wbTasktypeName) {
		this.wbTasktypeName = wbTasktypeName;
	}

	/**
	 * 任务描述
	 */
	@Column(name = "F_TASK_DES")
	private String taskDes;

	/**
	 * 任务定义ID
	 */
	@Column(name = "F_WBTASKDEFINE_PID")
	private String wbtaskdefinePid;

	/**
	 * 作业设备ID
	 */
	@Column(name = "F_EQU_ID")
	private String equId;

	/**
	 * RY作业设备编码
	 */
	@Column(name = "F_EQU_CODE")
	private String equCode;

	/**
	 * RY作业设备名称
	 */
	@Column(name = "F_EQU_NAME")
	private String equName;

	/**
	 * 任务执行者
	 */
	@Column(name = "F_EXECUTOR")
	private String executor;
	
	/**
	 * RY任务执行者姓名
	 */
	@Column(name = "F_EXECUTOR_NAME")
	private String executorName;
	
	/**
	 * 执行频率(1-班,10-天,20-周,30-月)
	 */
	@Column(name = "F_FREQUENCY")
	private String frequency;
	
	/**
	 * 任务日期(下达)
	 */
	@Column(name = "F_DATE")
	private Date date;
	
	/**
	 * 任务班次
	 */
	@Column(name = "F_WORKTIME_PID")
	private String worktimePid;
	
	/**
	 * 任务下达时间(接收)
	 */
	@Column(name = "F_TRANSMIT_TIME")
	private Timestamp transmitTime;
	
	/**
	 * 任务可开始时间
	 */
	@Column(name = "F_PLAN_BEGINTIME")
	private Timestamp planBegintime;
	
	/**
	 * 任务逾期时间
	 */
	@Column(name = "F_PLAN_ENDTIME")
	private Timestamp planEndtime;
	
	/**
	 * 实际开始执行时间
	 */
	@Column(name = "F_EXEC_BEGINTIME")
	private Timestamp execBegintime;
	
	/**
	 * 实际执行结束时间
	 */
	@Column(name = "F_EXEC_DONETIME")
	private Timestamp execDonetime;
	
	/**
	 * RY执行时长(分钟)
	 */
	@Column(name = "F_EXEC_DURATION")
	private Double execDuration;
	
	/**
	 * 执行结果类型
	 */
	@Column(name = "F_RESULT")
	private String result;
	
	/**
	 * 执行结果描述
	 */
	@Column(name = "F_RESULT_DES")
	private String resultDes;
	
	/**
	 * 任务状态(0-草稿,1-下达,10-执行中,20-正常完成,21-任务取消)
	 */
	@Column(name = "F_STATUS")
	private String status;
	
	/**
	 * 关联报修单
	 */
	@Column(name = "F_REPAIR_ID")
	private String repairId;
	
	@Column(name = "F_REMARK")
	private String remark;
	
	@Column(name = "F_SYS_FLAG")
	private String sysFlag;
	
	@Column(name = "F_CREATOR")
	private String creator;
	
	@Column(name = "F_CREATE_TIME")
	private Timestamp createTime;
	
	@Column(name = "F_LAST_MODIFIER")
	private String lastModifier;
	
	@Column(name = "F_LAST_MODIFIED_TIME")
	private Timestamp lastModifiedTime;

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getWbTasktype() {
		return wbTasktype;
	}

	public void setWbTasktype(String wbTasktype) {
		this.wbTasktype = wbTasktype;
	}

	public String getTaskDes() {
		return taskDes;
	}

	public void setTaskDes(String taskDes) {
		this.taskDes = taskDes;
	}

	public String getWbtaskdefinePid() {
		return wbtaskdefinePid;
	}

	public void setWbtaskdefinePid(String wbtaskdefinePid) {
		this.wbtaskdefinePid = wbtaskdefinePid;
	}

	public String getEquId() {
		return equId;
	}

	public void setEquId(String equId) {
		this.equId = equId;
	}

	public String getEquCode() {
		return equCode;
	}

	public void setEquCode(String equCode) {
		this.equCode = equCode;
	}

	public String getEquName() {
		return equName;
	}

	public void setEquName(String equName) {
		this.equName = equName;
	}

	public String getExecutor() {
		return executor;
	}

	public void setExecutor(String executor) {
		this.executor = executor;
	}

	public String getExecutorName() {
		return executorName;
	}

	public void setExecutorName(String executorName) {
		this.executorName = executorName;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getWorktimePid() {
		return worktimePid;
	}

	public void setWorktimePid(String worktimePid) {
		this.worktimePid = worktimePid;
	}

	public Timestamp getTransmitTime() {
		return transmitTime;
	}

	public void setTransmitTime(Timestamp transmitTime) {
		this.transmitTime = transmitTime;
	}

	public Timestamp getPlanBegintime() {
		return planBegintime;
	}

	public void setPlanBegintime(Timestamp planBegintime) {
		this.planBegintime = planBegintime;
	}

	public Timestamp getPlanEndtime() {
		return planEndtime;
	}

	public void setPlanEndtime(Timestamp planEndtime) {
		this.planEndtime = planEndtime;
	}

	public Timestamp getExecBegintime() {
		return execBegintime;
	}

	public void setExecBegintime(Timestamp execBegintime) {
		this.execBegintime = execBegintime;
	}

	public Timestamp getExecDonetime() {
		return execDonetime;
	}

	public void setExecDonetime(Timestamp execDonetime) {
		this.execDonetime = execDonetime;
	}

	public Double getExecDuration() {
		return execDuration;
	}

	public void setExecDuration(Double execDuration) {
		this.execDuration = execDuration;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getResultDes() {
		return resultDes;
	}

	public void setResultDes(String resultDes) {
		this.resultDes = resultDes;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRepairId() {
		return repairId;
	}

	public void setRepairId(String repairId) {
		this.repairId = repairId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSysFlag() {
		return sysFlag;
	}

	public void setSysFlag(String sysFlag) {
		this.sysFlag = sysFlag;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getLastModifier() {
		return lastModifier;
	}

	public void setLastModifier(String lastModifier) {
		this.lastModifier = lastModifier;
	}

	public Timestamp getLastModifiedTime() {
		return lastModifiedTime;
	}

	public void setLastModifiedTime(Timestamp lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}

	
}
