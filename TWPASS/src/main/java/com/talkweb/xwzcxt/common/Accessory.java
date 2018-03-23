package com.talkweb.xwzcxt.common;

import java.io.File;
import java.util.Date;

public class Accessory {

	private String accessoryFileName = "";
	private String accessoryType = "0";
	private String filetype="";//文件类型在数据库中的表示
	private String chapterNO ="";
	private File  accessoryFile;
	private String sfileuuid="";//保存到T_SD_STANDARDFILE中的文件ID
	private String accessoryuuid="";
	private String fileid="";//保存到T_SD_FILE表中的文件ID
	private String attuuid="";
	private String atttype="";//0-附件，1-附录
	private String fileurl="";
	
	private String creator="";
	private Date createtime;
	private String modifier;
	private Date modifytime;
	
	public String getAccessoryFileName() {
		return accessoryFileName;
	}
	public void setAccessoryFileName(String accessoryFileName) {
		this.accessoryFileName = accessoryFileName;
	}
	public String getAccessoryType() {
		return accessoryType;
	}
	public void setAccessoryType(String accessoryType) {
		this.accessoryType = accessoryType;
	}
	public String getChapterNO() {
		return chapterNO;
	}
	public void setChapterNO(String chapterNO) {
		this.chapterNO = chapterNO;
	}
	public File getAccessoryFile() {
		return accessoryFile;
	}
	public void setAccessoryFile(File accessoryFile) {
		this.accessoryFile = accessoryFile;
	}
	public String getSfileuuid() {
		return sfileuuid;
	}
	public void setSfileuuid(String sfileuuid) {
		this.sfileuuid = sfileuuid;
	}
	public String getAccessoryuuid() {
		return accessoryuuid;
	}
	public void setAccessoryuuid(String accessoryuuid) {
		this.accessoryuuid = accessoryuuid;
	}
	public String getFileid() {
		return fileid;
	}
	public void setFileid(String fileid) {
		this.fileid = fileid;
	}
	public String getAtttype() {
		return atttype;
	}
	public void setAtttype(String atttype) {
		this.atttype = atttype;
	}
	public String getFileurl() {
		return fileurl;
	}
	public void setFileurl(String fileurl) {
		this.fileurl = fileurl;
	}
	public String getFiletype() {
		return filetype;
	}
	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public String getModifier() {
		return modifier;
	}
	public void setModifier(String modifier) {
		this.modifier = modifier;
	}
	public Date getModifytime() {
		return modifytime;
	}
	public void setModifytime(Date modifytime) {
		this.modifytime = modifytime;
	}
	public String getAttuuid() {
		return attuuid;
	}
	public void setAttuuid(String attuuid) {
		this.attuuid = attuuid;
	}
	
	
}
