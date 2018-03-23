package com.talkweb.xwzcxt.action;

import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

public class FileDownloadAction extends ActionSupport{
	private static final long serialVersionUID = -6972313491458853948L;
	private String fileName;
	
	
	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public InputStream getDownload() throws Exception{
		 this.fileName = "Help.pdf" ;  
         //获取资源路径  
		 String path = ServletActionContext.getServletContext().getRealPath("/component/Help.pdf");
		 InputStream s= new FileInputStream(path);
		 return s;
	}

}
