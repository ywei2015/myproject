package tw.business.entity.equ;

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
 * 设备维保任务定义
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_equ_wbtask_define")
public class EquWbtaskDefine extends BasicData{

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
	
	/**
	 * 任务类别名称
	 */
	@Transient
	private String wbTasktypeName;

	/**
	 * 任务描述
	 */
	@Column(name = "F_TASK_DES")
	private String taskDes;

	/**
	 * 作业对象类型(1-机台,2-机台类型)
	 */
	@Column(name = "F_WORK_OBJTYPE")
	private String workObjtype;
	
	/**
	 * 作业对象类型名称
	 */
	@Transient
	private String workObjtypeName;

	/**
	 * 作业对象ID
	 */
	@Column(name = "F_WORK_OBJID")
	private String workObjid;
	
	/**
	 * 作业对象名称
	 */
	@Transient
	private String workObjName;

	/**
	 * 任务执行者类型(1-指定人,2-指定岗位)
	 */
	@Column(name = "F_SENDEE_TYPE")
	private String sendeeType;

	/**
	 * 任务执行者类型名称
	 */
	@Transient
	private String sendeeTypeName;
	/**
	 * 任务执行者
	 */
	@Column(name = "F_EXECUTOR")
	private String executor;
	//执行者名称
	@Transient
	private String executorName;
	public String getExecutorName() {
		return executorName;
	}

	public void setExecutorName(String executorName) {
		this.executorName = executorName;
	}

	/**
	 * 仅工作日执行(1-是,0-否)
	 */
	@Column(name = "F_EXEC_TIMEFLAG")
	private String execTimeflag;
	
	/**
	 * 执行频率(1-班,10-天,20-周,30-月)
	 */
	@Column(name = "F_FREQUENCY")
	private String frequency;
	
	/**
	 * 间隔时长（单位参考频率）
	 */
	@Column(name = "F_INTERVAL")
	private Integer interval;
	
	/**
	 * 任务有效时长(天)-可根据频率生成
	 */
	@Column(name = "F_VALID_TIMELONG")
	private Integer validTimelong;
	
	/**
	 * 任务执行步骤数
	 */
	@Column(name = "F_STEP_COUNT")
	private Integer stepCount;
	
	@Column(name = "F_REMARK")
	private String remark;
	
	@Column(name = "F_SYS_FLAG")
	private String sysFlag="1";
	
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

	public String getWorkObjtype() {
		return workObjtype;
	}

	public void setWorkObjtype(String workObjtype) {
		this.workObjtype = workObjtype;
	}

	public String getWorkObjid() {
		return workObjid;
	}

	public void setWorkObjid(String workObjid) {
		this.workObjid = workObjid;
	}

	public String getSendeeType() {
		return sendeeType;
	}

	public void setSendeeType(String sendeeType) {
		this.sendeeType = sendeeType;
	}

	public String getExecutor() {
		return executor;
	}

	public void setExecutor(String executor) {
		this.executor = executor;
	}

	public String getExecTimeflag() {
		return execTimeflag;
	}

	public void setExecTimeflag(String execTimeflag) {
		this.execTimeflag = execTimeflag;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public Integer getInterval() {
		return interval;
	}

	public void setInterval(Integer interval) {
		this.interval = interval;
	}

	public Integer getValidTimelong() {
		return validTimelong;
	}

	public void setValidTimelong(Integer validTimelong) {
		this.validTimelong = validTimelong;
	}

	public Integer getStepCount() {
		return stepCount;
	}

	public void setStepCount(Integer stepCount) {
		this.stepCount = stepCount;
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

	public String getWbTasktypeName() {
		return wbTasktypeName;
	}

	public void setWbTasktypeName(String wbTasktypeName) {
		this.wbTasktypeName = wbTasktypeName;
	}

	public String getWorkObjtypeName() {
		return workObjtypeName;
	}

	public void setWorkObjtypeName(String workObjtypeName) {
		this.workObjtypeName = workObjtypeName;
	}

	public String getWorkObjName() {
		return workObjName;
	}

	public void setWorkObjName(String workObjName) {
		this.workObjName = workObjName;
	}

	public String getSendeeTypeName() {
		return sendeeTypeName;
	}

	public void setSendeeTypeName(String sendeeTypeName) {
		this.sendeeTypeName = sendeeTypeName;
	}

	
}
