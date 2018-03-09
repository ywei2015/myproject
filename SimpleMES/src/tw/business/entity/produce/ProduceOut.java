package tw.business.entity.produce;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import tw.business.entity.pub.BasicData;
import tw.sysbase.pub.StingUtil;
/**
 * 生产工单产出
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_produce_out")
public class ProduceOut extends BasicData{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name = "F_PID")
	private String pid;

	/**
	 * 生产工单号
	 */
	@Column(name = "F_PWO_ID")
	private String pwoId;

	/**
	 * 产量归集对象类别(1-小单元,2-工序,3-工段,4-生产线,5-车间)
	 */
	@Column(name = "F_COLLECT_TYPE")
	private String collectType;

	/**
	 * 生产对象ID
	 */
	@Column(name = "F_OBJECT_ID")
	private String objectId;
	
	/**
	 * 生产对象编码
	 */
	@Column(name = "F_OBJECT_CODE")
	private String objectCode;

	/**
	 * 生产对象名称
	 */
	@Column(name = "F_OBJECT_NAME")
	private String objectName;
	
	@Column(name = "F_MAT_CODE")
	private String matCode;
	
	@Column(name = "F_MAT_NAME")
	private String matName;
	
	@Column(name = "F_UNIT")
	private String unit;
	
	@Transient
	private String unitName;
	
	@Column(name = "F_QUANTITY")
	private BigDecimal quantity;
	
	@Column(name = "F_NG_QUANTITY")
	private BigDecimal ngQuantity;
	
	@Column(name = "F_LACATION")
	private String lacation;
	
	@Column(name = "F_OUT_ST")
	private Timestamp outSt;
	
	@Transient
	private String outStVo;
	
	@Column(name = "F_OUT_ET")
	private Timestamp outEt;
	
	@Transient
	private String outEtVo;
	
	@Column(name = "F_WAREHOUSE")
	private String warehouse;
	
	@Column(name = "F_MARK1")
	private String mark1;
	
	@Column(name = "F_MARK2")
	private String mark2;
	
	@Column(name = "F_MARK3")
	private String mark3;
	
	@Column(name = "F_MARK4")
	private String mark4;
	
	@Column(name = "F_MARK5")
	private String mark5;

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

	public String getPwoId() {
		return pwoId;
	}

	public void setPwoId(String pwoId) {
		this.pwoId = pwoId;
	}

	public String getCollectType() {
		return collectType;
	}

	public void setCollectType(String collectType) {
		this.collectType = collectType;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public String getMatCode() {
		return matCode;
	}

	public void setMatCode(String matCode) {
		this.matCode = matCode;
	}

	public String getMatName() {
		return matName;
	}

	public void setMatName(String matName) {
		this.matName = matName;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getNgQuantity() {
		return ngQuantity;
	}

	public void setNgQuantity(BigDecimal ngQuantity) {
		this.ngQuantity = ngQuantity;
	}

	public String getLacation() {
		return lacation;
	}

	public void setLacation(String lacation) {
		this.lacation = lacation;
	}

	public Timestamp getOutSt() {
		return outSt;
	}

	public void setOutSt(Timestamp outSt) {
		this.outSt = outSt;
	}

	public Timestamp getOutEt() {
		return outEt;
	}

	public void setOutEt(Timestamp outEt) {
		this.outEt = outEt;
	}

	public String getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}

	public String getMark1() {
		return mark1;
	}

	public void setMark1(String mark1) {
		this.mark1 = mark1;
	}

	public String getMark2() {
		return mark2;
	}

	public void setMark2(String mark2) {
		this.mark2 = mark2;
	}

	public String getMark3() {
		return mark3;
	}

	public void setMark3(String mark3) {
		this.mark3 = mark3;
	}

	public String getMark4() {
		return mark4;
	}

	public void setMark4(String mark4) {
		this.mark4 = mark4;
	}

	public String getMark5() {
		return mark5;
	}

	public void setMark5(String mark5) {
		this.mark5 = mark5;
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

	public String getObjectCode() {
		return objectCode;
	}

	public void setObjectCode(String objectCode) {
		this.objectCode = objectCode;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getOutStVo() {
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(!StingUtil.isEmpty(outSt)){
			this.outStVo = sdf.format(this.outSt);
		}
		return outStVo;
	}

	public void setOutStVo(String outStVo) {
		this.outStVo = outStVo;
	}

	public String getOutEtVo() {
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(!StingUtil.isEmpty(outEt)){
			this.outEtVo = sdf.format(this.outEt);
		}
		return outEtVo;
	}

	public void setOutEtVo(String outEtVo) {
		this.outEtVo = outEtVo;
	}

}
