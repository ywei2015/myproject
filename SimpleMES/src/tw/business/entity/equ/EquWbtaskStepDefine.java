package tw.business.entity.equ;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import tw.business.entity.pub.BasicData;
/**
 * 设备维保任务定义步骤子表
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_equ_wbtaskstep_define")
public class EquWbtaskStepDefine extends BasicData{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name = "F_PID")
	private String pid;

	/**
	 * 任务PID
	 */
	@Column(name = "F_TASKDEFINE_PID")
	private String taskdefinePid;

	/**
	 * 步骤序号
	 */
	@Column(name = "F_STEP_INDEX")
	private Integer stepIndex;

	/**
	 * 任务描述
	 */
	@Column(name = "F_EXECSTD_DES")
	private String execstdDes;

	/**
	 * (预留字段)填写类型(1-判断是否正确+异常时描述)
	 */
	@Column(name = "F_FILL_TYPE")
	private String fillType;

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

	public String getTaskdefinePid() {
		return taskdefinePid;
	}

	public void setTaskdefinePid(String taskdefinePid) {
		this.taskdefinePid = taskdefinePid;
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
