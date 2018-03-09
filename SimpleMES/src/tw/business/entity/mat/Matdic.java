package tw.business.entity.mat;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;

/**
 * 生产物料
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_mat_dic")
public class Matdic {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name = "F_PID")
	private String pid;
	
	/**
	 * 物料编码
	 */
	@Column(name = "F_MAT_CODE")
	@NotNull(message = "[物料编码]不能为空")
	@Size(min=1,max=20,message="[物料编码]过短")
	private String matCode;

	/**
	 * 物料名称(短名称)
	 */
	@NotNull(message = "[名称]不能为空")
	@Size(min=2,max=20,message="[名称]过短")
	@Column(name = "F_MAT_NAME")
	private String matName;

	/**
	 * 物料完整名称
	 */
	@NotNull(message = "[全名称]不能为空")
	@Size(min=2,max=20,message="[全名称]过短")
	@Column(name = "F_MAT_FULLNAME")
	private String matFullname;

	/**
	 * 规格描述
	 */
	@Column(name = "F_SPEC")
	private String spec;

	/**
	 * 物料大类(RAW-原料,AUX-辅料,BCP-半成品,CP-成品)
	 */
	@NotNull(message = "[物料大类]不能为空")
	@Size(min=2,max=20,message="[物料大类]过短")
	@Column(name = "F_MAINTYPE")
	private String type;
	
	//物料子类
	@Column(name = "F_SUBTYPE")
	private String sub_type;

    /**
	 * 计数单位
	 */
	@NotNull(message = "[单位]不能为空")
    @Size(min=2,max=50,message="[单位]不能为空")
	@Column(name = "F_UNIT")
	private String unit;

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

	public String getMatFullname() {
		return matFullname;
	}

	public void setMatFullname(String matFullname) {
		this.matFullname = matFullname;
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
    public String getSub_type() {
        return sub_type;
    }

    public void setSub_type(String sub_type) {
        this.sub_type = sub_type;
    }
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
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
