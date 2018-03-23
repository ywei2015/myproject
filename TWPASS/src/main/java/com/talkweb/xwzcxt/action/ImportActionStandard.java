package com.talkweb.xwzcxt.action;

import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.common.AppConstants;
import com.talkweb.twdpe.web.commons.StaticUploadInfo;
import com.talkweb.twdpe.web.upload.exception.IllegalEnctypeException;
import com.talkweb.twdpe.web.upload.exception.NotFoundStoragePathException;
import com.talkweb.xwzcxt.dal.TSdMediastdDal;
import com.talkweb.xwzcxt.pojo.Actnode;
import com.talkweb.xwzcxt.service.ExcelReaderService;
import com.talkweb.xwzcxt.service.FileUploadService;
import com.talkweb.xwzcxt.service.ImportFileDBService;
import com.talkweb.xwzcxt.service.impl.FileUploadServiceImpl;

public class ImportActionStandard extends StdFileAction {
	private Actnode actnode = null;
	//标准excel文件
	private File upfileExcel;
	private String upfileExcelFileName;
	private String upfileExcelContentType;
	//excel文件读取服务
	@Autowired
	private ExcelReaderService excelReaderService;
	//写数据库服务
	@Autowired
	private ImportFileDBService importFileDBService;
	@Autowired
	private TSdMediastdDal tSdMediastdDal;

	public String fileUpload() {
		HttpServletRequest request = ServletActionContext.getRequest();
		List<com.talkweb.xwzcxt.pojo.File> files = null;
		try {
			files = this.getUploadFile();
		} catch (IllegalArgumentException e1) {
			ServletActionContext.getRequest().setAttribute("throw", e1);
		} catch (IllegalEnctypeException e1) {
			ServletActionContext.getRequest().setAttribute("throw", e1);
		} catch (FileUploadException e1) {
			ServletActionContext.getRequest().setAttribute("throw", e1);
		} catch (NotFoundStoragePathException e1) {
			ServletActionContext.getRequest().setAttribute("throw", e1);
		}
		List<String[]> list = null;
		if (files != null && files.size() > 0) {
			String filePath = files.get(0).toString();
			String fileName ="/xwzcxt"+ filePath.substring(filePath.lastIndexOf(StaticUploadInfo.upload_system_url) + StaticUploadInfo.upload_system_url.length());
			String path = request.getSession().getServletContext().getRealPath(fileName);
			File myDelFile = new File(path);
			//myDelFile.delete();
			Map map = new HashMap();
			map.put("filePath", filePath);
			this.formatData(map);
		}
		return SUCCESS;
	}

	public String uploadFileToFileServer() {
		String fileType[] = this.formatFileType(this.getUpfileAccessoryContentType());
		String fileId = this.SaveFileToServer(this.getUpfileAccessory(), fileType[0], fileType[1], "1");
		Map fileInfo = tSdMediastdDal.getFileInfoById(fileId);
		fileInfo.put("C_FILE_PATH", AppConstants.getFilePathPrefix() + fileInfo.get("C_FILE_PATH"));
		this.formatData(fileInfo);
		return SUCCESS;
}
	
	private File filedata;
	private String filedataFileName;
	private String filedataContentType;
	
	
	public File getFiledata() {
		return filedata;
	}

	public void setFiledata(File filedata) {
		this.filedata = filedata;
	}

	public String getFiledataFileName() {
		return filedataFileName;
	}

	public void setFiledataFileName(String filedataFileName) {
		this.filedataFileName = filedataFileName;
	}

	public String getFiledataContentType() {
		return filedataContentType;
	}

	public void setFiledataContentType(String filedataContentType) {
		this.filedataContentType = filedataContentType;
	}

	public String uploadFileToFileServerToNews() {
		String fileType[] = this.formatFileType(this.getUpfileAccessoryContentType());
		String fileId = this.SaveFileToServer(this.getUpfileAccessory(), fileType[0], fileType[1], "3");
		Map fileInfo = tSdMediastdDal.getFileInfoById(fileId);
		fileInfo.put("C_FILE_PATH", AppConstants.getFilePathPrefix() + fileInfo.get("C_FILE_PATH"));
		this.formatData(fileInfo);
		return SUCCESS;
}
	
	
	@SuppressWarnings("unchecked")
	public String uploadEditorFileToFileServer() {
		String err="";
		String fileName="";
		PrintWriter out=null;
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			out=response.getWriter();
			String fileType[] = this.formatFileType(this.getFiledataContentType());
			String fileId = this.SaveFileToServer(this.getFiledata(), fileType[0], fileType[1], "3");
			Map fileInfo = tSdMediastdDal.getFileInfoById(fileId);
			fileInfo.put("C_FILE_PATH", AppConstants.getFilePathPrefix() + fileInfo.get("C_FILE_PATH"));
			
			fileName=fileInfo.get("C_FILE_PATH").toString();
		} catch (Exception e) {
			e.printStackTrace();
			fileName="";
			err="错误: "+e.getMessage();
		}

		out.println("{\"err\":\"" + err + "\",\"msg\":\"" + fileName + "\"}");
		out.flush();
		out.close();
		//this.formatData(fileInfo);
		return SUCCESS;
}

	/**
	 * 获取上传文件的文件信息
	 **/
	public List<com.talkweb.xwzcxt.pojo.File> getUploadFile() throws IllegalArgumentException, IllegalEnctypeException, FileUploadException, NotFoundStoragePathException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String path = request.getRealPath("") + "\\xwzcxt\\standardlibrary\\";
		this.creatDirectory(path);
		FileUploadService server = new FileUploadServiceImpl();
		server.setStoragePath(path);
		server.setAllowStrutsAgent(true);
		server.setRetainFileName(true);
		String maxSize = request.getParameter("maxSize");
		if (maxSize != null) {
			server.setMaxSize(Long.parseLong(maxSize));
		}
		server.setServletContext(ServletActionContext.getServletContext());
		List<com.talkweb.xwzcxt.pojo.File> files = server.getFilesInLocal(request);
		return files;
	}

	/**
	 * 判断文件目录存不存在，如果不存在，则创建文件目录
	 * @param path 目录路径
	 **/
	private void creatDirectory(String path) {
		java.io.File f = new java.io.File(path);
		if(!f.exists())
			f.mkdirs();
	}

	public File getUpfileExcel() {
		return upfileExcel;
	}

	public void setUpfileExcel(File upfileExcel) {
		this.upfileExcel = upfileExcel;
	}

	public String getUpfileExcelFileName() {
		return upfileExcelFileName;
	}

	public void setUpfileExcelFileName(String upfileExcelFileName) {
		this.upfileExcelFileName = upfileExcelFileName;
	}

	public String getUpfileExcelContentType() {
		return upfileExcelContentType;
	}

	public void setUpfileExcelContentType(String upfileExcelContentType) {
		this.upfileExcelContentType = upfileExcelContentType;
	}

	public Actnode getActnode() {
		return actnode;
	}

	public void setActnode(Actnode actnode) {
		this.actnode = actnode;
	}

	private String[] formatFileType(String contentType) {
		String type[] = {"", contentType};
		if (contentType!=null && !contentType.equals("")) {
			if (contentType.equals("image/bmp")) {
				type[0] = "1";
				type[1] = "bmp";
			} else if (contentType.equals("image/gif")) {
				type[0] = "1";
				type[1] = "gif";
			} else if (contentType.equals("image/jpg")) {
				type[0] = "1";
				type[1] = "jpg";
			} else if (contentType.equals("image/jpeg")) {
				type[0] = "1";
				type[1] = "jpeg";
			} else if (contentType.equals("audio/mp3")) {
				type[0] = "2";
				type[1] = "mp3";
			} else if (contentType.equals("video/mp4")) {
				type[0] = "3";
				type[1] = "mp4";
			} else if (contentType.equals("video/mpeg4")) {
				type[0] = "3";
				type[1] = "mp4";
			} else if (contentType.equals("image/png")) {
				type[0] = "1";
				type[1] = "png";
			}
		}
		return type;
	}

	/**
	 * 调用文件上传服务接口
	 * filetypes 文件类型(1图片、2音频、3视频、4其它)
	 * filekind 文件业务分类（1.标准;2.过程记录<任务步骤结果及异常>;3...）
	 * 返回---成功保存后的文件信息表中的ID，否则为空字符串
	 **/
	private String SaveFileToServer(File file, String filetypes, String extens, String filekind) {
		HttpClient client = new HttpClient();
		String url = appConstants.getFILE_URL() + "/filemanageraction!UpdateFileInfo.action";
		PostMethod method = new PostMethod(url);
		Part[] params = new Part[5];
		String ret = "";
		try {
			StringPart stringPart1 = new StringPart("c_filetypes",filetypes);
			StringPart stringPart2 = new StringPart("c_extens", extens);
			StringPart stringPart3 = new StringPart("c_filekind", filekind);

			params[0] = stringPart1;
			params[1] = stringPart2;
			params[2] = stringPart3;

			if (file == null)
				return "";
			//if (file.isFile() && file.canRead())
				//return "";
			FilePart filePart = new FilePart("files", file);
			params[3] = filePart;
			//禁用服务调试模式
			params[4] = new StringPart("debug", "0");
			MultipartRequestEntity post = new MultipartRequestEntity(params, method.getParams());
			method.setRequestEntity(post);
			client.executeMethod(method);
			System.out.print("--------------------\n" + method.getResponseBodyAsString());
			JSONObject obj = JSONObject.fromObject(method.getResponseBodyAsString());
			ret = obj.getString("fileID");
		}
		catch (Exception e) {
			e.printStackTrace();
			method.releaseConnection();
		}
		method.releaseConnection();
		return ret;
	}
}