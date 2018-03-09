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
 * 生产工单投入
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_produce_in")
public class ProduceIn extends BasicData{

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
	 * 生产线
	 */
	@Column(name = "F_PRODUCE_LINE")
	private String produceLine;
	
	@Transient
	private String produceLineName;

	/**
	 * 生产设备编码
	 */
	@Column(name = "F_EQU_CODE")
	private String equCode;

	/**
	 * RY生产设备名称
	 */
	@Column(name = "F_EQU_NAME")
	private String equName;
	
	/**
	 * 投入物料编码
	 */
	@Column(name = "F_MAT_CODE")
	private String matCode;
	
	/**
	 * RY投入物料名称
	 */
	@Column(name = "F_MAT_NAME")
	private String matName;
	
	/**
	 * 投入物料单位
	 */
	@Column(name = "F_UNIT")
	private String unit;
	
	@Transient
	private String unitName;
	
	/**
	 * 投入物料数量
	 */
	@Column(name = "F_QUANTITY")
	private BigDecimal quantity;
	
	/**
	 * 投料开始时间
	 */
	@Column(name = "F_INPUT_ST")
	private Timestamp inputSt;
	
	@Transient
	private String inputStVo;
	
	/**
	 * 投料结束时间
	 */
	@Column(name = "F_INPUT_ET")
	private Timestamp inputEt;
	
	@Transient
	private String inputEtVo;
	
	/**
	 * 投入物料批号
	 */
	@Column(name = "F_MAT_LOTNO")
	private String matLotno;
	
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

	public String getProduceLine() {
		return produceLine;
	}

	public void setProduceLine(String produceLine) {
		this.produceLine = produceLine;
	}

	public String getProduceLineName() {
		return produceLineName;
	}

	public void setProduceLineName(String produceLineName) {
		this.produceLineName = produceLineName;
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

	public Timestamp getInputSt() {
		return inputSt;
	}

	public void setInputSt(Timestamp inputSt) {
		this.inputSt = inputSt;
	}

	public Timestamp getInputEt() {
		return inputEt;
	}

	public void setInputEt(Timestamp inputEt) {
		this.inputEt = inputEt;
	}

	public String getMatLotno() {
		return matLotno;
	}

	public void setMatLotno(String matLotno) {
		this.matLotno = matLotno;
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

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getInputStVo() {
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(!StingUtil.isEmpty(inputSt)){
			this.inputStVo = sdf.format(this.inputSt);
		}
		return inputStVo;
	}

	public void setInputStVo(String inputStVo) {
		this.inputStVo = inputStVo;
	}

	public String getInputEtVo() {
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(!StingUtil.isEmpty(inputEt)){
			this.inputEtVo = sdf.format(this.inputEt);
		}
		return inputEtVo;
	}

	public void setInputEtVo(String inputEtVo) {
		this.inputEtVo = inputEtVo;
	}
	
}
