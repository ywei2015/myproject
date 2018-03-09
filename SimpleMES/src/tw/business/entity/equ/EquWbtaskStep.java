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
/**
 * 设备维保具体任务步骤子表
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_equ_wbtaskstep")
public class EquWbtaskStep {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name = "F_PID")
	private String pid;

	/**
	 * 任务PID
	 */
	@Column(name = "F_TASK_PID")
	private String taskPid;

	/**
	 * 步骤序号
	 */
	@Column(name = "F_STEP_INDEX")
	private Integer stepIndex;

	/**
	 * 步骤作业要求
	 */
	@Column(name = "F_EXECSTD_DES")
	private String execstdDes;

	/**
	 * (预留字段)填写类型(1-判断是否正确+异常时描述)
	 */
	@Column(name = "F_FILL_TYPE")
	private String fillType;

	/**
	 * 是否异常(0-正常,1-异常)
	 */
	@Column(name = "F_ISABNORMAL")
	private String isabnormal;

	@Column(name = "F_ABNORMAL_DES")
	private String abnormalDes;

	/**
	 * 状态(0-初建,1-已执行)
	 */
	@Column(name = "F_STATE")
	private String state;

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
	
	@Transient
	private String lastModifierName;
	
	@Column(name = "F_LAST_MODIFIED_TIME")
	private Timestamp lastModifiedTime;

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getTaskPid() {
		return taskPid;
	}

	public void setTaskPid(String taskPid) {
		this.taskPid = taskPid;
	}

	public Integer getStepIndex() {
		return stepIndex;
	}

	public void setStepIndex(Integer stepIndex) {
		this.stepIndex = stepIndex;
	}

	public String getExecstdDes() {
		return execstdDes;
	}

	public void setExecstdDes(String execstdDes) {
		this.execstdDes = execstdDes;
	}

	public String getFillType() {
		return fillType;
	}

	public void setFillType(String fillType) {
		this.fillType = fillType;
	}

	public String getIsabnormal() {
		return isabnormal;
	}

	public void setIsabnormal(String isabnormal) {
		this.isabnormal = isabnormal;
	}

	public String getAbnormalDes() {
		return abnormalDes;
	}

	public void setAbnormalDes(String abnormalDes) {
		this.abnormalDes = abnormalDes;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
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

	public String getLastModifierName() {
		return lastModifierName;
	}

	public void setLastModifierName(String lastModifierName) {
		this.lastModifierName = lastModifierName;
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
