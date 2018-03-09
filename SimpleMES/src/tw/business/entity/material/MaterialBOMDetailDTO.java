package tw.business.entity.material;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_mat_bomver_detail")
public class MaterialBOMDetailDTO {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "F_PID")
    private String fPid;
    
    @NotNull(message = "BOM外键不能为空")
    @Column(name = "F_BOM_PID")
    private String fBomPid;

    @Column(name = "F_SUBTYPE")
    private String fSubtype;
    
    @NotNull(message = "[投入产出标记]不能为空")
    @Size(min=1,max=1,message="[投入产出标记]不能为空")
    @Column(name = "F_IO_FLAG")
    private String fIoFlag;

    @NotNull(message = "[排序号]不能为空")
    @Min(value = 1 ,message = "[排序号]过小")
    @Max(value = 30,message = "[排序号]过大")
    @Column(name = "F_INDEX")
    private Short fIndex;

    @NotNull(message = "[物料编码]不能为空")
    @Size(min=2,max=50,message="[物料编码]过短")
    @Column(name = "F_MAT_CODE")
    private String fMatCode;

    @NotNull(message = "[物料名称]不能为空")
    @Size(min=2,max=50,message="[物料名称]过短")
    @Column(name = "F_MAT_NAME")
    private String fMatName;

    @Column(name = "F_QUANTITY")
    private BigDecimal fQuantity;
    
    @NotNull(message = "[单位]不能为空")
    @Size(min=2,max=50,message="[单位]不能为空")
    @Column(name = "F_UNIT")
    private String fUnit;

    @Column(name = "F_REMARK")
    private String fRemark;

    @Column(name = "F_SYS_FLAG")
    private String fSysFlag;

    @Column(name = "F_CREATOR")
    private String fCreator;

    @Column(name = "F_CREATE_TIME")
    private Timestamp fCreateTime;

    @Column(name = "F_LAST_MODIFIER")
    private String fLastModifier;

    @Column(name = "F_LAST_MODIFIED_TIME")
    private Timestamp fLastModifiedTime;

    public String getfPid() {
        return fPid;
    }

    public void setfPid(String fPid) {
        this.fPid = fPid == null ? null : fPid.trim();
    }

    public String getfBomPid() {
        return fBomPid;
    }

    public void setfBomPid(String fBomPid) {
        this.fBomPid = fBomPid == null ? null : fBomPid.trim();
    }

    public String getfSubtype() {
        return fSubtype;
    }

    public void setfSubtype(String fSubtype) {
        this.fSubtype = fSubtype == null ? null : fSubtype.trim();
    }

    public String getfIoFlag() {
        return fIoFlag;
    }

    public void setfIoFlag(String fIoFlag) {
        this.fIoFlag = fIoFlag == null ? null : fIoFlag.trim();
    }

    public Short getfIndex() {
        return fIndex;
    }

    public void setfIndex(Short fIndex) {
        this.fIndex = fIndex;
    }

    public String getfMatCode() {
        return fMatCode;
    }

    public void setfMatCode(String fMatCode) {
        this.fMatCode = fMatCode == null ? null : fMatCode.trim();
    }

    public String getfMatName() {
        return fMatName;
    }

    public void setfMatName(String fMatName) {
        this.fMatName = fMatName == null ? null : fMatName.trim();
    }

    public BigDecimal getfQuantity() {
        return fQuantity;
    }

    public void setfQuantity(BigDecimal fQuantity) {
        this.fQuantity = fQuantity;
    }

    public String getfUnit() {
        return fUnit;
    }

    public void setfUnit(String fUnit) {
        this.fUnit = fUnit == null ? null : fUnit.trim();
    }

    public String getfRemark() {
        return fRemark;
    }

    public void setfRemark(String fRemark) {
        this.fRemark = fRemark == null ? null : fRemark.trim();
    }

    public String getfSysFlag() {
        return fSysFlag;
    }

    public void setfSysFlag(String fSysFlag) {
        this.fSysFlag = fSysFlag == null ? null : fSysFlag.trim();
    }

    public String getfCreator() {
        return fCreator;
    }

    public void setfCreator(String fCreator) {
        this.fCreator = fCreator == null ? null : fCreator.trim();
    }

    public Date getfCreateTime() {
        return fCreateTime;
    }

    public void setfCreateTime(Timestamp fCreateTime) {
        this.fCreateTime = fCreateTime;
    }

    public String getfLastModifier() {
        return fLastModifier;
    }

    public void setfLastModifier(String fLastModifier) {
        this.fLastModifier = fLastModifier == null ? null : fLastModifier.trim();
    }

    public Date getfLastModifiedTime() {
        return fLastModifiedTime;
    }

    public void setfLastModifiedTime(Timestamp fLastModifiedTime) {
        this.fLastModifiedTime = fLastModifiedTime;
    }
}