package com.talkweb.xwzcxt.pojo;



import com.talkweb.twdpe.core.data.BasePOJO;


public class standardatt extends BasePOJO {

	private String c_att_id	       ="";//	附件ID
	private String c_file_id	       ="";//	文件ID
	private String c_atttype_id	   ="";//  附件类型ID（附件、附录）
	private String c_contentid	     ="";//	相关内容ID（为空及无关联）
	private String c_sfile_id	     ="";//	标准文件ID
	
	public String getC_att_id() {
		return c_att_id;
	}
	public void setC_att_id(String c_att_id) {
		this.c_att_id = c_att_id;
	}
	public String getC_file_id() {
		return c_file_id;
	}
	public void setC_file_id(String c_file_id) {
		this.c_file_id = c_file_id;
	}
	public String getC_atttype_id() {
		return c_atttype_id;
	}
	public void setC_atttype_id(String c_atttype_id) {
		this.c_atttype_id = c_atttype_id;
	}
	public String getC_contentid() {
		return c_contentid;
	}
	public void setC_contentid(String c_contentid) {
		this.c_contentid = c_contentid;
	}
	public String getC_sfile_id() {
		return c_sfile_id;
	}
	public void setC_sfile_id(String c_sfile_id) {
		this.c_sfile_id = c_sfile_id;
	}
	
	
}
