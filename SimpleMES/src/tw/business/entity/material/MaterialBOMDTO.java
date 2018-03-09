package tw.business.entity.material;

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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_mat_bomver")
public class MaterialBOMDTO {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "F_PID")
    private String fPid;
    
    @NotNull(message = "[BOM名称]不能为空")
    @Size(min=2,max=50,message="[BOM名称]过短")
    @Column(name = "F_BOM_NAME")
    private String fBomName;
    
    @NotNull(message = "[BOM类型]不能为空")
    @Size(min=2,max=50,message="[BOM类型]过短")
    @Column(name = "F_BOM_TYPE")
    private String fBomType;
    
    @NotNull(message = "[产出物编码]不能为空")
    @Size(min=2,max=50,message="[产出物料编码]过短")
    @Column(name = "F_MAT_CODE")
    private String fMatCode;

    @NotNull(message = "[产出物料名]不能为空")
    @Size(min=2,max=50,message="[产出物料名称]过短")
    @Column(name = "F_MAT_NAME")
    private String fMatName;

    @Column(name = "F_QUANTITY")
    private BigDecimal fQuantity;
    
    @NotNull(message = "[单位]不能为空")
    @Size(min=2,max=50,message="[单位]不能为空")
    @Column(name = "F_UNIT")
    private String fUnit;

    @Column(name = "F_VERSION")
    private String fVersion;

    @Column(name = "F_ENABLE_DT")
    private Timestamp fEnableDt;
    
    @Transient
    private String fEnableDt_vo;
    
    @Transient
    private String fDisableDt_vo;

    @Column(name = "F_DISABLE_DT")
    private Timestamp fDisableDt;

    @NotNull(message = "[状态]不能为空")
    @Size(min=1,max=1,message="[状态]不能为空")
    @Column(name = "F_STATUS")
    private String fStatus;

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
    
    public void tranTimesamp(){
        DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        if (this.fEnableDt != null && this.fDisableDt != null) {
            this.fEnableDt_vo=sdf.format(this.fEnableDt);
            this.fDisableDt_vo=sdf.format(this.fDisableDt);
        }
    }

    public String getfPid() {
        return fPid;
    }

    public void setfPid(String fPid) {
        this.fPid = fPid == null ? null : fPid.trim();
    }

    public String getfBomName() {
        return fBomName;
    }

    public void setfBomName(String fBomName) {
        this.fBomName = fBomName == null ? null : fBomName.trim();
    }

    public String getfBomType() {
        return fBomType;
    }

    public void setfBomType(String fBomType) {
        this.fBomType = fBomType == null ? null : fBomType.trim();
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

    public String getfVersion() {
        return fVersion;
    }

    public void setfVersion(String fVersion) {
        this.fVersion = fVersion == null ? null : fVersion.trim();
    }

    public Timestamp getfEnableDt() {
        return fEnableDt;
    }

    public void setfEnableDt(Timestamp fEnableDt) {
        this.fEnableDt = fEnableDt;
    }

    public Timestamp getfDisableDt() {
        return fDisableDt;
    }

    public void setfDisableDt(Timestamp fDisableDt) {
        this.fDisableDt = fDisableDt;
    }

    public String getfStatus() {
        return fStatus;
    }

    public void setfStatus(String fStatus) {
        this.fStatus = fStatus == null ? null : fStatus.trim();
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

    public Timestamp getfCreateTime() {
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

    public Timestamp getfLastModifiedTime() {
        return fLastModifiedTime;
    }

    public void setfLastModifiedTime(Timestamp fLastModifiedTime) {
        this.fLastModifiedTime = fLastModifiedTime;
    }

    public String getfEnableDt_vo() {
        return fEnableDt_vo;
    }

    public void setfEnableDt_vo(String fEnableDt_vo) {
        this.fEnableDt_vo = fEnableDt_vo;
    }

    public String getfDisableDt_vo() {
        return fDisableDt_vo;
    }

    public void setfDisableDt_vo(String fDisableDt_vo) {
        this.fDisableDt_vo = fDisableDt_vo;
    }

}