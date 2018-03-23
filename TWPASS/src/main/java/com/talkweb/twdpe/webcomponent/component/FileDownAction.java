package com.talkweb.twdpe.webcomponent.component;

import java.io.File;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import com.talkweb.twdpe.core.log.Logger;
import com.talkweb.twdpe.core.log.LoggerFactory;

import com.talkweb.twdpe.webcomponent.common.action.ComponentActionSupport;

@SuppressWarnings("unused")
public class FileDownAction extends ComponentActionSupport {

    private static final Logger logger = LoggerFactory.getLogger(FileDownAction.class);

    /** 上传的文件 */
    public File upload;
    /** 上传的路径 */
    public String savePath;
    /** 上传的用户模板类型 */
    public String usertype;
    /** 下载的路径 */
    public String inputPath;
    /** 要下载的文件名 */
    public String filename;

    public String uploadfile() {
	return null;
    }

    /**
     * 下载文件
     * 
     * @return
     */
    public String getFileDown() {
	return SUCCESS;
    }

    // **************************一些变量的get和set方法*******************************************
    public String getUsertype() {
	return usertype;
    }

    public void setUsertype(String usertype) {
	this.usertype = usertype;
    }

    @SuppressWarnings("deprecation")
    public String getSavePath() {
	return ServletActionContext.getRequest().getRealPath(savePath);
    }

    public void setSavePath(String savePath) {
	this.savePath = savePath;
    }

    public File getUpload() {
	return upload;
    }

    public void setUpload(File upload) {
	this.upload = upload;
    }

    public void setInputPath(String inputPath) {
	this.inputPath = inputPath;
    }

    public String getFilename() {
	return filename;
    }

    public void setFilename(String filename) {
	this.filename = filename;
    }

    public InputStream getTargetFile() {
	HttpServletRequest request = ServletActionContext.getRequest();
	String downloadfile = inputPath + request.getParameter("filename");
	return ServletActionContext.getServletContext().getResourceAsStream(downloadfile);
    }
}