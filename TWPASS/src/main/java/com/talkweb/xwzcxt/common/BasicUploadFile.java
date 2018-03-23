package com.talkweb.xwzcxt.common;

import java.io.File;

public class BasicUploadFile {

	private String index;
	private File File;
	private String FileName;
	private String ContentType;
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	public File getFile() {
		return File;
	}
	public void setFile(File file) {
		File = file;
	}
	public String getFileName() {
		return FileName;
	}
	public void setFileName(String fileName) {
		FileName = fileName;
	}
	public String getContentType() {
		return ContentType;
	}
	public void setContentType(String contentType) {
		ContentType = contentType;
	}
	
	
}
