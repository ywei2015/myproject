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
 * 生产设备台账信息
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_equ_info")
public class EquInfo extends BasicData{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name = "F_PID")
	private String pid;
	
	/**
	 *设备 名称
	 */
	@Column(name = "F_EQU_NAME")
	private String equName;
	
	/**
	 * 设备编号
	 */
	@Column(name = "F_EQU_CODE")
	private String equCode;
	
	/**
	 * 设备种类-关联View
	 */
	@Column(name = "F_EQU_KIND")
	private String equKind;
	
	/**
	 * 种类Name
	 */
	@Transient
	private String kindName;
	
	
	/**
	 * 部门ID
	 */
	@Column(name = "F_ORGID")
	private String orgId;
	
	/**
	 * 部门Name
	 */
	@Transient
	private String orgName;
	
	/**
	 * 固定资产号
	 */
	@Column(name = "F_FIXASSET_CODE")
	private String fixassetCode;

	/**
	 * 生产厂家
	 */
	@Column(name = "F_MANUFACTURER")
	private String manufacturer;

	/**
	 * 设备型号-关联View
	 */
	@Column(name = "F_EQU_MODEL")
	private String equModel;

	/**
	 * 出厂编号
	 */
	@Column(name = "F_FACTORY_NUM")
	private String factoryNum;
	
	/**
	 * 购置日期yyyy-MM-dd
	 */
	@Column(name = "F_PURCHASE_DATE")
	private Date purchaseDate;
	
	/**
	 * 默认10，设备状态(1-正常,10-停用,20-维修,40-报废)
	 */
	@Column(name = "F_STATUS")
	private String status;
	
	@Transient
	private String statusName;
	
	@Column(name = "F_LOCATION")
	private String location;
	
	/**
	 * 间隔时长（单位参考频率）
	 */
	@Column(name = "F_PURPOSE")
	private String purpose;
	
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

	public String getEquName() {
		return equName;
	}

	public void setEquName(String equName) {
		this.equName = equName;
	}

	public String getEquCode() {
		return equCode;
	}

	public void setEquCode(String equCode) {
		this.equCode = equCode;
	}

	public String getEquKind() {
		return equKind;
	}

	public void setEquKind(String equKind) {
		this.equKind = equKind;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getFixassetCode() {
		return fixassetCode;
	}

	public void setFixassetCode(String fixassetCode) {
		this.fixassetCode = fixassetCode;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getEquModel() {
		return equModel;
	}

	public void setEquModel(String equModel) {
		this.equModel = equModel;
	}

	public String getFactoryNum() {
		return factoryNum;
	}

	public void setFactoryNum(String factoryNum) {
		this.factoryNum = factoryNum;
	}

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
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

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getKindName() {
		return kindName;
	}

	public void setKindName(String kindName) {
		this.kindName = kindName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	
	
	
}
