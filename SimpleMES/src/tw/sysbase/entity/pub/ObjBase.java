package tw.sysbase.entity.pub;

import java.util.Set;

import tw.business.entity.pub.BasicData;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ObjBase extends BasicData {

	public ObjBase(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public ObjBase(String id, String name, ObjBase superClass) {
		super();
		this.id = id;
		this.name = name;
		this.superClass = superClass;
	}
	private String id;
	private String code;
	private String name;
	private Integer sysFlag;

	private Integer type;
	private String  version;
	private String  remark;
	private String  source;
	private String  table;
	private String  className;
	private String  classFullName;
	private String createTime;
	private String objEntityRefPid;
	@JsonIgnore
	private ObjBase superClass;
	@JsonIgnore
	private Set<ObjBase> subClasses;

	public ObjBase(Set<ObjBase> subClasses) {
		super();
		this.subClasses = subClasses;
	}
	public ObjBase(String id, String code, String name, Integer sysFlag, String remark) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
		this.sysFlag = sysFlag;
		this.remark = remark;
	}
	public ObjBase(String id, String code, String name, Integer sysFlag, String remark,String objEntityRefPid) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
		this.sysFlag = sysFlag;
		this.remark = remark;
		this.objEntityRefPid = objEntityRefPid;
	}
	@JsonIgnore
	private Set<ObjAttribute> properties;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public ObjBase(String id, String name, Set<ObjBase> subClasses) {
		super();
		this.id = id;
		this.name = name;
		this.subClasses = subClasses;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getSysFlag() {
		return sysFlag;
	}
	public void setSysFlag(Integer sysFlag) {
		this.sysFlag = sysFlag;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getTable() {
		return table;
	}
	public void setTable(String table) {
		this.table = table;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getClassFullName() {
		return classFullName;
	}
	public void setClassFullName(String classFullName) {
		this.classFullName = classFullName;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getObjEntityRefPid() {
		return objEntityRefPid;
	}
	public void setObjEntityRefPid(String objEntityRefPid) {
		this.objEntityRefPid = objEntityRefPid;
	}
	public ObjBase getSuperClass() {
		return superClass;
	}
	public void setSuperClass(ObjBase superClass) {
		this.superClass = superClass;
	}
	public void setSubClasses(Set<ObjBase> subClasses) {
		this.subClasses = subClasses;
	}
	public Set<ObjBase> getSubClasses() {
		return subClasses;
	}
	public ObjBase() {
		super();
	}
	public Set<ObjAttribute> getProperties() {
		return properties;
	}
	public void setProperties(Set<ObjAttribute> properties) {
		this.properties = properties;
	}

	@Override
    public String toString() {
        return "ObjBase [id=" + id + ", name=" + name + "]";
    }
	
	private String pid;

	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}

	private boolean isParent  ;
	public void SetIsParent(boolean isparent) {
		this.isParent=isparent;
	}
	public boolean getIsParent() {
		return this.isParent;
	}
	

	
	
}
