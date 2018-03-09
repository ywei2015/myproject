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
 * 设备维修单
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_equ_repairform")
public class EquRepairform extends BasicData {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name = "F_PID")
	private String pid;

	/**
	 * 报修类别(1-运行异常,2-开机异常,3-点检异常,4-保养异常,5-润滑异常,6-返修,7-其它异常)
	 */
	@Column(name = "F_REPAIR_TYPE")
	private String repairType;

	@Column(name = "F_SOURCE_ID")
	private String sourceId;
	
	@Transient
	private String repairTypeName;
	/**
	 * 维修单据号(R+类别2位+设备CODE+YYMMDDHHMI)
	 */
	@Column(name = "F_REPAIR_SN")
	private String repairSn;

	/**
	 * 设备ID
	 */
	@Column(name = "F_EQU_ID")
	private String equId;

	/**
	 * RY设备编号
	 */
	@Column(name = "F_EQU_CODE")
	private String equCode;

	/**
	 * RY设备名称
	 */
	@Column(name = "F_EQU_NAME")
	private String equName;

	/**
	 * 异常描述
	 */
	@Column(name = "F_ABNORMAL_DES")
	private String abnormalDes;

	/**
	 * 发生时间
	 */
	@Column(name = "F_OCCUR_TIME")
	private Timestamp occurTime;
	
	/**
	 * 维修申请人
	 */
	@Column(name = "F_APPLY_USERID")
	private String applyUserid;
	
	/**
	 * RY维修申请人姓名
	 */
	@Column(name = "F_APPLY_USERNAME")
	private String applyUsername;
	
	/**
	 * 联系方式(电话)
	 */
	@Column(name = "F_CONTACT")
	private String contact;
	
	/**
	 * 选定维修人
	 */
	@Column(name = "F_REPAIR_USERID")
	private String repairUserid;
	
	/**
	 * RY选定维修人姓名
	 */
	@Column(name = "F_REPAIR_USERNAME")
	private String repairUsername;
	
	/**
	 * 维修单收到时间
	 */
	@Column(name = "F_RECEIVE_TIME")
	private Timestamp receiveTime;
	
	/**
	 * 维修开始时间
	 */
	@Column(name = "F_REPAIR_ST")
	private Timestamp repairSt;
	
	/**
	 * 维修结束时间
	 */
	@Column(name = "F_REPAIR_ET")
	private Timestamp repairEt;
	
	/**
	 * 故障分类
	 */
	@Column(name = "F_ABNORMAL_TYPE")
	private String abnormalType;
	
	@Transient
	private String abnormalTypeName;
	/**
	 * 维修过程描述
	 */
	@Column(name = "F_REPAIR_PRODES")
	private String repairProdes;
	
	/**
	 * 维修结果说明
	 */
	@Column(name = "F_REPAIR_RESULTDES")
	private String repairResultdes;
	
	/**
	 * 是否提交验证(0-不提交,1-提交)
	 */
	@Column(name = "F_ISSUBMIT_CHECK")
	private String issubmitCheck;
	
	/**
	 * 验证人
	 */
	@Column(name = "F_CHECK_USERID")
	private String checkUserid;
	
	/**
	 * 验证人姓名
	 */
	@Column(name = "F_CHECK_USERNAME")
	private String checkUsername;
	
	/**
	 * 验证开始时间
	 */
	@Column(name = "F_CHECK_ST")
	private Timestamp checkSt;
	
	/**
	 * 验证结束时间
	 */
	@Column(name = "F_CHECK_ET")
	private Timestamp checkEt;
	
	/**
	 * 验证结果
	 */
	@Column(name = "F_CHEKC_RESULT")
	private String chekcResult;
	
	/**
	 * 未通过说明
	 */
	@Column(name = "F_INVALID_DES")
	private String invalidDes;
	
	/**
	 * 维修单状态(0-草稿,10-申报待修,20-维修中,29-修毕待验证,30-验证中,40-维修单关闭)
	 */
	@Column(name = "F_STATUS")
	private String status;
	
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

	public String getRepairType() {
		return repairType;
	}

	public void setRepairType(String repairType) {
		this.repairType = repairType;
	}

	public String getSourceId() {
		return sourceId;
	}

	public String getRepairTypeName() {
		return repairTypeName;
	}

	public void setRepairTypeName(String repairTypeName) {
		this.repairTypeName = repairTypeName;
	}
	
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public String getRepairSn() {
		return repairSn;
	}

	public void setRepairSn(String repairSn) {
		this.repairSn = repairSn;
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

	public String getAbnormalDes() {
		return abnormalDes;
	}

	public void setAbnormalDes(String abnormalDes) {
		this.abnormalDes = abnormalDes;
	}

	public Timestamp getOccurTime() {
		return occurTime;
	}

	public void setOccurTime(Timestamp occurTime) {
		this.occurTime = occurTime;
	}

	public String getApplyUserid() {
		return applyUserid;
	}

	public void setApplyUserid(String applyUserid) {
		this.applyUserid = applyUserid;
	}

	public String getApplyUsername() {
		return applyUsername;
	}

	public void setApplyUsername(String applyUsername) {
		this.applyUsername = applyUsername;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getRepairUserid() {
		return repairUserid;
	}

	public void setRepairUserid(String repairUserid) {
		this.repairUserid = repairUserid;
	}

	public String getRepairUsername() {
		return repairUsername;
	}

	public void setRepairUsername(String repairUsername) {
		this.repairUsername = repairUsername;
	}

	public Timestamp getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(Timestamp receiveTime) {
		this.receiveTime = receiveTime;
	}

	public Timestamp getRepairSt() {
		return repairSt;
	}

	public void setRepairSt(Timestamp repairSt) {
		this.repairSt = repairSt;
	}

	public Timestamp getRepairEt() {
		return repairEt;
	}

	public void setRepairEt(Timestamp repairEt) {
		this.repairEt = repairEt;
	}

	public String getAbnormalType() {
		return abnormalType;
	}

	public void setAbnormalType(String abnormalType) {
		this.abnormalType = abnormalType;
	}

	public String getRepairProdes() {
		return repairProdes;
	}

	public void setRepairProdes(String repairProdes) {
		this.repairProdes = repairProdes;
	}

	public String getRepairResultdes() {
		return repairResultdes;
	}

	public void setRepairResultdes(String repairResultdes) {
		this.repairResultdes = repairResultdes;
	}

	public String getIssubmitCheck() {
		return issubmitCheck;
	}

	public void setIssubmitCheck(String issubmitCheck) {
		this.issubmitCheck = issubmitCheck;
	}

	public String getCheckUserid() {
		return checkUserid;
	}

	public void setCheckUserid(String checkUserid) {
		this.checkUserid = checkUserid;
	}

	public String getCheckUsername() {
		return checkUsername;
	}

	public void setCheckUsername(String checkUsername) {
		this.checkUsername = checkUsername;
	}

	public Timestamp getCheckSt() {
		return checkSt;
	}

	public void setCheckSt(Timestamp checkSt) {
		this.checkSt = checkSt;
	}

	public Timestamp getCheckEt() {
		return checkEt;
	}

	public void setCheckEt(Timestamp checkEt) {
		this.checkEt = checkEt;
	}

	public String getChekcResult() {
		return chekcResult;
	}

	public void setChekcResult(String chekcResult) {
		this.chekcResult = chekcResult;
	}

	public String getInvalidDes() {
		return invalidDes;
	}

	public void setInvalidDes(String invalidDes) {
		this.invalidDes = invalidDes;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getAbnormalTypeName() {
		return abnormalTypeName;
	}

	public void setAbnormalTypeName(String abnormalTypeName) {
		this.abnormalTypeName = abnormalTypeName;
	}

}