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
 * 生产工单(计划、实际)
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_produce_wo")
public class ProduceWo extends BasicData{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name = "F_PID")
	private String pid;

	/**
	 * 生产工单号
	 */
	@Column(name = "F_PWO_SN")
	private String pwoSn;

	/**
	 * 关联订单号
	 */
	@Column(name = "F_ORDER_ID")
	private String orderId;

	/**
	 * 工单类型(基础类型配置View)
	 */
	@Column(name = "F_PWO_TYPE")
	private String pwoType;
	
	@Transient
	private String pwoTypeName;

	/**
	 * 生产车间ID
	 */
	@Column(name = "F_WORKSHOP_ID")
	private String workshopId;
	
	/**
	 * 生产车间名称
	 */
	@Column(name = "F_WORKSHOP_NAME")
	private String workshopName;
	
	/**
	 * 产出物料编码
	 */
	@Column(name = "F_MAT_CODE")
	private String matCode;
	
	/**
	 * 产出物料名称
	 */
	@Column(name = "F_MAT_NAME")
	private String matName;
	
	/**
	 * 成品编码
	 */
	@Column(name = "F_PRODUCT_CODE")
	private String productCode;
	
	@Transient
	private String productName;
	
	/**
	 * 单位(View取Code)
	 */
	@Column(name = "F_UNIT")
	private String unit;
	
	@Transient
	private String unitName;
	
	/**
	 * 计划生产数量
	 */
	@Column(name = "F_PLAN_QUANTITY")
	private BigDecimal planQuantity;
	
	/**
	 * 计划开始时间
	 */
	@Column(name = "F_PLAN_ST")
	private Timestamp planSt;
	
	/**
	 * 计划完成时间
	 */
	@Column(name = "F_PLAN_ET")
	private Timestamp planEt;
	
	@Transient
    private String planStVo;
    
    @Transient
    private String planEtVo;
    
	/**
	 * 生产班次编码(View取Code)
	 */
	@Column(name = "F_WORKTIME")
	private String worktime;
	
	/**
	 * 生产班组编码(View取Code)
	 */
	@Column(name = "F_WORKTEAM")
	private String worktteam;
	
	/**
	 * 工单下达人
	 */
	@Column(name = "F_ISSUE_USERID")
	private String issueUserid;
	
	/**
	 * 工单下达人姓名
	 */
	@Column(name = "F_ISSUE_USERNAME")
	private String issueUsername;
	
	/**
	 * 工单下达时间
	 */
	@Column(name = "F_ISSUE_TIME")
	private Timestamp issueTime;
	
	@Transient
	private String issueTimeVo;
	
	/**
	 * 产品配方标准
	 */
	@Column(name = "F_RECIPE_STD")
	private String recipeStd;
	
	/**
	 * 生产工艺标准
	 */
	@Column(name = "F_CRAFT_STD")
	private String craftStd;
	
	/**
	 * 实际开始时间
	 */
	@Column(name = "F_FACT_ST")
	private Timestamp factSt;
	
	/**
	 * 实际完成时间
	 */
	@Column(name = "F_FACT_ET")
	private Timestamp factEt;
	
	@Transient
    private String factStVo;
    
    @Transient
    private String factEtVo;
	
	/**
	 * 工单状态(0-草稿,1-下达,10-执行中,20-执行完毕)
	 */
	@Column(name = "F_STATUS")
	private String status;
	
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

	public String getPwoSn() {
		return pwoSn;
	}

	public void setPwoSn(String pwoSn) {
		this.pwoSn = pwoSn;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getPwoType() {
		return pwoType;
	}

	public void setPwoType(String pwoType) {
		this.pwoType = pwoType;
	}

	public String getPwoTypeName() {
		return pwoTypeName;
	}

	public void setPwoTypeName(String pwoTypeName) {
		this.pwoTypeName = pwoTypeName;
	}

	public String getWorkshopId() {
		return workshopId;
	}

	public void setWorkshopId(String workshopId) {
		this.workshopId = workshopId;
	}

	public String getWorkshopName() {
		return workshopName;
	}

	public void setWorkshopName(String workshopName) {
		this.workshopName = workshopName;
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

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public BigDecimal getPlanQuantity() {
		return planQuantity;
	}

	public void setPlanQuantity(BigDecimal planQuantity) {
		this.planQuantity = planQuantity;
	}

	public Timestamp getPlanSt() {
		return planSt;
	}

	public void setPlanSt(Timestamp planSt) {
		this.planSt = planSt;
	}

	public Timestamp getPlanEt() {
		return planEt;
	}

	public void setPlanEt(Timestamp planEt) {
		this.planEt = planEt;
	}

	public String getWorktime() {
		return worktime;
	}

	public void setWorktime(String worktime) {
		this.worktime = worktime;
	}

	public String getWorktteam() {
		return worktteam;
	}

	public void setWorktteam(String worktteam) {
		this.worktteam = worktteam;
	}

	public String getIssueUserid() {
		return issueUserid;
	}

	public void setIssueUserid(String issueUserid) {
		this.issueUserid = issueUserid;
	}

	public String getIssueUsername() {
		return issueUsername;
	}

	public void setIssueUsername(String issueUsername) {
		this.issueUsername = issueUsername;
	}

	public Timestamp getIssueTime() {
		return issueTime;
	}

	public void setIssueTime(Timestamp issueTime) {
		this.issueTime = issueTime;
	}

	public String getIssueTimeVo() {
		return issueTimeVo;
	}

	public void setIssueTimeVo(String issueTimeVo) {
		this.issueTimeVo = issueTimeVo;
	}

	public String getRecipeStd() {
		return recipeStd;
	}

	public void setRecipeStd(String recipeStd) {
		this.recipeStd = recipeStd;
	}

	public String getCraftStd() {
		return craftStd;
	}

	public void setCraftStd(String craftStd) {
		this.craftStd = craftStd;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getPlanStVo() {
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(!StingUtil.isEmpty(planSt)){
			this.planStVo = sdf.format(this.planSt);
		}
		return planStVo;
	}

	public void setPlanStVo(String planStVo) {
		this.planStVo = planStVo;
	}

	public String getPlanEtVo() {
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(!StingUtil.isEmpty(planEt)){
			this.planEtVo = sdf.format(this.planEt);
		}
		return planEtVo;
	}

	public void setPlanEtVo(String planEtVo) {
		this.planEtVo = planEtVo;
	}

	public Timestamp getFactSt() {
		return factSt;
	}

	public void setFactSt(Timestamp factSt) {
		this.factSt = factSt;
	}

	public Timestamp getFactEt() {
		return factEt;
	}

	public void setFactEt(Timestamp factEt) {
		this.factEt = factEt;
	}

	public String getFactStVo() {
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(!StingUtil.isEmpty(factSt)){
			this.factStVo = sdf.format(this.factSt);
		}
		return factStVo;
	}

	public void setFactStVo(String factStVo) {
		this.factStVo = factStVo;
	}

	public String getFactEtVo() {
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(!StingUtil.isEmpty(factEt)){
			this.factEtVo = sdf.format(this.factEt);
		}
		return factEtVo;
	}

	public void setFactEtVo(String factEtVo) {
		this.factEtVo = factEtVo;
	}
	
	
}
