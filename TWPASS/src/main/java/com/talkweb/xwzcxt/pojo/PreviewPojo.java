package com.talkweb.xwzcxt.pojo;

import com.talkweb.twdpe.core.data.BasePOJO;

public class PreviewPojo extends BasePOJO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8104510025606169279L;
	public PreviewPojo()
    {
        super();
    }
	
	private String pid;

	private String id;
	
	private String val;
	
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
