package com.talkweb.twdpe.webcomponent.common.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import com.talkweb.twdpe.core.log.Logger;
import com.talkweb.twdpe.core.log.LoggerFactory;

import com.talkweb.twdpe.core.data.DataMap;
import com.talkweb.twdpe.core.data.IData;
import com.talkweb.twdpe.core.util.UniqueIdUtils;
import com.talkweb.twdpe.core.util.json.JsonObject;
import com.talkweb.twdpe.webcomponent.common.StaticUploadInfo;


public class UploadAction extends ComponentActionSupport{
	private static final Logger logger = LoggerFactory.getLogger(UploadAction.class);
	 private List<File> files;
	 private List<String> area;
	 private List<String> filesFileName;
	 private String inputName;
	 String uploadresult;
	 IData map =  new DataMap();
	 
	/**
	 * 上传路径定义：系统管理模块上传路径定义
	 */
	//系统管理：上传文件URL前缀
	private String upload_system_url = StaticUploadInfo.upload_system_url;
	//系统管理：默认上传路径
	private String upload_systemt_default = StaticUploadInfo.upload_system_default;
	//系统管理：附件上传路径
	private String upload_system_attach = StaticUploadInfo.upload_system_attach;
	//系统管理：图片上传路径
	private String upload_system_image = StaticUploadInfo.upload_system_image;
	//系统管理：文件导出路径
	private String upload_system_export = StaticUploadInfo.upload_system_export;
	//系统管理：文件导入路径
	private String upload_system_import = StaticUploadInfo.upload_system_import;
	//系统管理：临时文件上传路径
	private String upload_system_temp = StaticUploadInfo.upload_system_temp;	 
    
	public String uploadfiles() throws IOException{
		HttpServletRequest request = ServletActionContext.getRequest();
		String realpath  = ServletActionContext.getRequest().getRealPath(upload_system_import);
		File file = new File(realpath);
				if(!file.exists()){
					file.mkdirs();
				}		
			try{		
				for(int i = 0; i<files.size();i++){
				//System.out.println(fileFileName.get(i).toString());
				String sufix = StringUtils.substringAfter(filesFileName.get(i).toString(), ".");
				String radomname = UniqueIdUtils.getUID();
				String path = realpath+"/"+radomname+"."+sufix;
				FileOutputStream fos = new FileOutputStream(path);
				FileInputStream fis = new FileInputStream(files.get(i));
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = fis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}
				fis.close();
				fos.flush();
				fos.close();
				uploadresult = StringUtils.replace(upload_system_import+radomname+"."+sufix, "\\", "/");
				map.put(area.get(i).toString(), uploadresult);
				}
				
			}catch(Exception e){
				uploadresult = "0";
				e.printStackTrace();
				logger.error(e.getMessage(),e);
				request.setAttribute("result", "0");
				//printToView("{RESULT:0}");
			}
			request.setAttribute("result", JsonObject.toString(map));
			//printToView(JSONUtil.iDataToJson(map));
		return "success";
	
	}
	/**
	 * 
	 * @Title: uploadfile 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @return
	 * @throws IOException
	 */
	public String uploadfile() throws IOException{
		HttpServletRequest request = ServletActionContext.getRequest();
		String realpath  = ServletActionContext.getRequest().getRealPath(upload_system_import);
		File file = new File(realpath);
				if(!file.exists()){
					file.mkdirs();
				}		
			try{		
				for(int i = 0; i<files.size();i++){
				//System.out.println(fileFileName.get(i).toString());
				String sufix = StringUtils.substringAfter(filesFileName.get(i).toString(), ".");
				String radomname = UniqueIdUtils.getUID();
				String path = realpath+"/"+radomname+"."+sufix;
				FileOutputStream fos = new FileOutputStream(path);
				FileInputStream fis = new FileInputStream(files.get(i));
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = fis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}
				fis.close();
				fos.flush();
				fos.close();
				uploadresult = StringUtils.replace(upload_system_import+radomname+"."+sufix, "\\", "/");
				map.put("inputName", inputName);
				map.put("filePath", uploadresult);
				}
				
			}catch(Exception e){
				uploadresult = "0";
				e.printStackTrace();
				logger.error(e.getMessage(),e);
				request.setAttribute("result", "0");
			}
			request.setAttribute("result", JsonObject.toString(map));
		return "success";
	
	}
//******************************get和set方法**********************************************

	public List<String> getFilesFileName() {
		return filesFileName;
	}


	public void setFilesFileName(List<String> filesFileName) {
		this.filesFileName = filesFileName;
	}


	public List<File> getFiles() {
		return files;
	}


	public void setFiles(List<File> files) {
		this.files = files;
	}

	public List<String> getArea() {
		return area;
	}

	public void setArea(List<String> area) {
		this.area = area;
	}
	public String getInputName() {
		return inputName;
	}
	public void setInputName(String inputName) {
		this.inputName = inputName;
	}

	

}
