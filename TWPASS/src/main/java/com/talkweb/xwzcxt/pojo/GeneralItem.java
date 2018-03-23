package com.talkweb.xwzcxt.pojo;

import com.talkweb.twdpe.core.data.BasePOJO;

public class GeneralItem extends BasePOJO {
	private static final long serialVersionUID = 1016817236318783936L;
	private String c_id;
	private String c_name;

	public void setC_id(String value) {
		c_id = value;
	}
	public String getC_id() {
		return c_id;
	}	
	
	public void setC_name(String value) {
		c_name = value;
	}
	public String getC_name() {
		return c_name;
	}	
}
