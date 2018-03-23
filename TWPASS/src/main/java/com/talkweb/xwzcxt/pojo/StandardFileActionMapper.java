package com.talkweb.xwzcxt.pojo;

import com.talkweb.twdpe.core.data.BasePOJO;

/**
 * 
 * @author YangChen
 * 标准文件与流程的映射中间表
 *
 */
public class StandardFileActionMapper extends BasePOJO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4295833115317513940L;
	
	private String c_id;			//UUID
	private String c_action_id;		//流程节点ID
	private String c_content_id;	//相关内容ID（内容、附件、附录共用此ID）
	private String c_content_type;  //相关内容类型（1标准文件内容，2附件，3附录）
	
	public StandardFileActionMapper(){
		
	}

	public String getC_id() {
		return c_id;
	}

	public void setC_id(String c_id) {
		this.c_id = c_id;
	}

	public String getC_action_id() {
		return c_action_id;
	}

	public void setC_action_id(String c_action_id) {
		this.c_action_id = c_action_id;
	}

	public String getC_contentid() {
		return c_content_id;
	}

	public void setC_contentid(String c_contentid) {
		this.c_content_id = c_contentid;
	}

	public String getC_content_type() {
		return c_content_type;
	}

	public void setC_content_type(String c_content_type) {
		this.c_content_type = c_content_type;
	}
	
}
