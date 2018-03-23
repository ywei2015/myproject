package com.talkweb.xwzcxt.pojo;

import com.talkweb.twdpe.core.data.BasePOJO;

public class InitAutoPojo extends BasePOJO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8104510025606160279L;
	public InitAutoPojo()
    {
        super();
    }
	private String value;
	private String text;
	private String pid;
	private String id;
	private String val;
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getVal() {
		return val;
	}
	public void setVal(String val) {
		this.val = val;
	}

}
