package tw.sysbase.entity.pub;

import java.util.ArrayList;
import java.util.List;

public class OBTypeVo {
	public String id;
	public String code;
	public String name;
	public String superClassId;
	public String sysFlag;
	private List<PropertyVo> inherPros = new ArrayList<PropertyVo>();
	private List<PropertyVo> pros = new ArrayList<PropertyVo>();
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public List<PropertyVo> getInherPros() {
		return inherPros;
	}
	public void setInherPros(List<PropertyVo> inherPros) {
		this.inherPros = inherPros;
	}
	public List<PropertyVo> getPros() {
		return pros;
	}
	public void setPros(List<PropertyVo> pros) {
		this.pros = pros;
	}
	public String getSuperClassId() {
		return superClassId;
	}
	public void setSuperClassId(String superClassId) {
		this.superClassId = superClassId;
	}
	public String getSysFlag() {
		return sysFlag;
	}
	public void setSysFlag(String sysFlag) {
		this.sysFlag = sysFlag;
	}
	
}
