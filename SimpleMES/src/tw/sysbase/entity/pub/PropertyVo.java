package tw.sysbase.entity.pub;

public class PropertyVo {
	private String id;
	private String objId;
	private String code;
	private String name;
	private String column;
	private Integer pkFlag;
	private String classType;
	private Integer order;
	//属性类型信息,用于生成页面
	private String cateId;
	private String cateCode;
	private String cateName;
	private Integer allowBlank;
	private String verifyRule;
	private Integer sysFlag;
	private String pkFlagName;
	private String allowBlankName;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getColumn() {
		return column;
	}
	public void setColumn(String column) {
		this.column = column;
	}
	public Integer getPkFlag() {
		return pkFlag;
	}
	public void setPkFlag(Integer pkFlag) {
		this.pkFlag = pkFlag;
	}
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
	public String getCateCode() {
		return cateCode;
	}
	public void setCateCode(String cateCode) {
		this.cateCode = cateCode;
	}
	public Integer getAllowBlank() {
		return allowBlank;
	}
	public void setAllowBlank(Integer allowBlank) {
		this.allowBlank = allowBlank;
	}
	public String getVerifyRule() {
		return verifyRule;
	}
	public void setVerifyRule(String verifyRule) {
		this.verifyRule = verifyRule;
	}
	public String getCateName() {
		return cateName;
	}
	public void setCateName(String cateName) {
		this.cateName = cateName;
	}
	public String getCateId() {
		return cateId;
	}
	public void setCateId(String cateId) {
		this.cateId = cateId;
	}
	public Integer getSysFlag() {
		return sysFlag;
	}
	public void setSysFlag(Integer sysFlag) {
		this.sysFlag = sysFlag;
	}
	public String getPkFlagName() {
		return pkFlagName;
	}
	public void setPkFlagName(String pkFlagName) {
		this.pkFlagName = pkFlagName;
	}
	public String getAllowBlankName() {
		return allowBlankName;
	}
	public void setAllowBlankName(String allowBlankName) {
		this.allowBlankName = allowBlankName;
	}
	public String getObjId() {
		return objId;
	}
	public void setObjId(String objId) {
		this.objId = objId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getClassType() {
		return classType;
	}
	public void setClassType(String classType) {
		this.classType = classType;
	}
}
