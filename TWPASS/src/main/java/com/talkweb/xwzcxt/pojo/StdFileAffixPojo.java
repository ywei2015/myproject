package com.talkweb.xwzcxt.pojo;


//GuveXie 20140616
public class StdFileAffixPojo {

	//标准文件表
	private String c_sfile_id	    ="";//	标准文件ID
	
	//文件附件表
	private String c_att_id	       ="";//	附件ID
	private String c_file_id	       ="";//	文件ID
	private String c_atttype_id	   ="";//  附件类型ID（附件、附录）
	private String c_contentid = ""; //	章节编号
	
	//文件表
	private String c_file_name	      ="";//	文件NAME
	private String c_filetype_id	    ="";//	类型ID（EXCEL,WORD,IMG...）
	private String c_file_url	      ="";//	URL保存地址

	public String getC_sfile_id() {
		return c_sfile_id;
	}
	public void setC_sfile_id(String c_sfile_id) {
		this.c_sfile_id = c_sfile_id;
	}
	
	public String getC_file_name() {
		return c_file_name;
	}
	public void setC_file_name(String c_file_name) {
		this.c_file_name = c_file_name;
	}
	public String getC_filetype_id() {
		return c_filetype_id;
	}
	public void setC_filetype_id(String c_filetype_id) {
		this.c_filetype_id = c_filetype_id;
	}
	public String getC_file_url() {
		return c_file_url;
	}
	public void setC_file_url(String c_file_url) {
		this.c_file_url = c_file_url;
	}

	public String getC_contentid() {
		return c_contentid;
	}
	public void setC_contentid(String c_contentid) {
		this.c_contentid = c_contentid;
	}

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
	
}
