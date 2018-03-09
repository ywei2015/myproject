package tw.sysbase.entity.pub;

import java.util.ArrayList;
import java.util.List;

public class ObjBaseView {
	private String id;
	private String code;
	private String name;
	private String objEntityRefId;
	
	public List<ObjBaseView> children = new ArrayList<ObjBaseView>();

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

	public List<ObjBaseView> getChildren() {
		return children;
	}

	public String getObjEntityRefId() {
		return objEntityRefId;
	}

	public void setObjEntityRefId(String objEntityRefId) {
		this.objEntityRefId = objEntityRefId;
	}
	
}
