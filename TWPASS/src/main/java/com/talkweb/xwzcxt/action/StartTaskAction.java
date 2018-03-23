package com.talkweb.xwzcxt.action;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import com.talkweb.xwzcxt.service.ImportFileDBService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.common.AppConstants;
import com.talkweb.twdpe.core.log.Logger;
import com.talkweb.twdpe.core.log.LoggerFactory;
import com.talkweb.twdpe.web.session.main.Session;
import com.talkweb.twdpe.web.session.util.SessionFactory;
import com.talkweb.xwzcxt.common.Accessory;
import com.talkweb.xwzcxt.pojo.StartTaskPojo;
import com.talkweb.xwzcxt.service.impl.StartTaskImpl;

public class StartTaskAction extends BusinessAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6734919053604553776L;
	private static final Logger logger = LoggerFactory.getLogger( StartTaskAction .class);
	@Autowired
	private StartTaskImpl startTaskImpl ;

	@Autowired
    AppConstants appConstants;
	 
	private StartTaskPojo startTaskPojo=new StartTaskPojo();

	private HttpServletRequest request = ServletActionContext.getRequest();
	private HttpServletResponse response = ServletActionContext.getResponse();


	
	//发起任务
    public StartTaskPojo getStartTaskPojo() {
		return startTaskPojo;
	}

	public void setStartTaskPojo(StartTaskPojo startTaskPojo) {
		this.startTaskPojo = startTaskPojo;
	}

	public String startTask(){
    	
        try{
    		Session session = SessionFactory.getInstance(request, response);
            Map user = null;
            String loginUserID ="";
  
            user = (Map) session.getAttribute("USERSESSION");
            if(user!=null){
            	loginUserID = user.get("id").toString();
            }else{
            	Map pageData2 = new HashMap();
                pageData2.put("msg", "网页已过期，请重新登陆!");
                this.formatData(pageData2);
                return SUCCESS;
            }

            StartTaskPojo pojo=new StartTaskPojo();
            pojo.setV_task_type(startTaskPojo.getC_task_type());
            pojo.setV_task_name(startTaskPojo.getC_task_name());
            pojo.setV_manage_section(startTaskPojo.getC_manage_section());
            pojo.setV_start_time(startTaskPojo.getC_start_time());
            pojo.setV_end_time(startTaskPojo.getC_end_time());
            pojo.setV_exec_userid(startTaskPojo.getC_exec_userid());
            pojo.setV_sender_userid(loginUserID);
            pojo.setV_remark(startTaskPojo.getC_remark());
            pojo.setC_chk_plantime(startTaskPojo.getC_chk_plantime());
            pojo.setC_evaluate_plantime(startTaskPojo.getC_evaluate_plantime());
            pojo.setC_chk_userid(startTaskPojo.getC_chk_userid());
            pojo.setC_evaluate_userid(startTaskPojo.getC_evaluate_userid());
            String ccUsers=request.getParameter("copyUserTree");
            
            if(ccUsers==null){
            	return SUCCESS;
            }else{
            	String ccUserList="";
            	ccUserList=ccUsers.replace("U-", "");
               pojo.setV_cc_userid_list(ccUserList);
            }
          
        	String fileids = request.getParameter("filestr");
        	if(fileids==null){
        		return SUCCESS;
        	}else{
	        	String fielidsList = "";
	        	if((fileids.length()-1)==-1){
	        		pojo.setV_fileids(fielidsList);
	        	}else{
	        	 fielidsList=fileids.substring(0,fileids.length()-1);
    		     pojo.setV_fileids(fielidsList);
	        	}
        	}
        	
            String task_id = startTaskImpl.startTask(pojo);
            
            Map pageData = new HashMap();
            if(task_id!=null){
                
                pageData.put("result", true);
                pageData.put("msg", "发起任务成功");
                
            }else{
                pageData.put("result", false);
                pageData.put("msg", "发起任务失败");
            }
     
            this.formatData(pageData);
    		
        }catch(Exception e){
       	
        	e.printStackTrace();
    		logger.error(e.getMessage(),e);
        }
      return SUCCESS;
      
}
    
	//---------------------------------文件附件相关Action方法
 	//附件和附录
	private File upfileAccessory;
	private String upfileAccessoryFileName;
	private String upfileAccessoryContentType; 
	public File getUpfileAccessory() {return upfileAccessory;}
	public void setUpfileAccessory(File upfileAccessory) { this.upfileAccessory = upfileAccessory;	}
	public String getUpfileAccessoryFileName() {return upfileAccessoryFileName;	}
	public void setUpfileAccessoryFileName(String upfileAccessoryFileName) {this.upfileAccessoryFileName = upfileAccessoryFileName;}
	public String getUpfileAccessoryContentType() {	return upfileAccessoryContentType;	}
	public void setUpfileAccessoryContentType(String upfileAccessoryContentType) {this.upfileAccessoryContentType = upfileAccessoryContentType;}
 
	private String SaveFileToServer(File file,String filetypes,String extens, String filekind){
		HttpClient client = new HttpClient( ); 
		//String url = "http://10.75.97.147:8080/CyxwglWebInService/filemanageraction!UpdateFileInfo.action";
		//String url = appConstants.getFILE_URL()+"/ActionFileUpload!submitFile.action";
		String url = appConstants.getFILE_URL()+"/filemanageraction!UpdateFileInfo.action";
		PostMethod method = new PostMethod(url); 
		Part[] params = new Part[5]; 
		String ret = "";
		try{
			StringPart stringPart1 = new StringPart("c_filetypes",filetypes);  //"1"); 
			StringPart stringPart2 = new StringPart("c_extens", extens);  //ormatFileType(f.getAccessoryType()));  
			StringPart stringPart3 = new StringPart("c_filekind", filekind); //"1");  
			
			params[0]=stringPart1;
			params[1]=stringPart2;
			params[2]=stringPart3;
			
			if(file==null) return "";
			//if(file.isFile()&&file.canRead()) return "";
			FilePart filePart = new FilePart("files", file); 
			params[3]=filePart;
			//禁用服务调试模式
			params[4]=new StringPart("debug","0");
			MultipartRequestEntity post =  new MultipartRequestEntity(params, method.getParams());  
			method.setRequestEntity(post);  
			client.executeMethod(method); 
			System.out.print("--------------------\n"+method.getResponseBodyAsString());
			JSONObject obj = JSONObject.fromObject(method.getResponseBodyAsString());
			ret = obj.getString("fileID");  //Here get file id(T_FILE_INFO.c_file_id)
		}
		catch(Exception e){
			e.printStackTrace();
		}
		method.releaseConnection();
		return ret;
	}

 	//附件上传完整版
	public String uploadAffixFile() {
		Map pageData = new HashMap();
		String msg = "";
		boolean reb = false; 
		
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
        
		Session session = SessionFactory.getInstance(request, response);
		String userid = "";
		Map user = (Map) session.getAttribute("USERSESSION");
		if (user != null) {
			userid = user.get("id").toString();
		} else {
			pageData.put("msg", "网页已过期，请重新登陆!");
			pageData.put("result", false);
			this.formatData(pageData);
			return SUCCESS;
		}
		String fileExtens = formatFileType(this.upfileAccessoryContentType);
    	String filetype=request.getParameter("c_filetypes").toString();
		String c_file_id = this.SaveFileToServer(this.upfileAccessory, filetype,fileExtens, "2");	//upload file and get T_FILE_INFO.C_File_ID	
		msg = "附件已被保存，ID为"+c_file_id;
		if(c_file_id==""||c_file_id==null) {
			reb = false;
			pageData.put("fileid", "");
		}
		else{
			reb = true; 
			//Add c_file_id  to filelist => fileids 
			pageData.put("fileid", c_file_id);
		}

    	//int n = importFileDBService.addAccessoryFileToDB(tmpAccessory);
    	//if(n>0) msg = msg + "\n且附件信息成功添加至数据库."; 

		pageData.put("result", reb);
		pageData.put("msg", msg);
		this.formatData(pageData);
		return SUCCESS;
	}
	
	
	private String formatFileType(String contentType){
		String type=contentType;
		if (contentType!=null && !contentType.equals("")){
			if (contentType.equals("application/msword"))
				type = "doc";
			else if (contentType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document"))
				type = "docx";
			else if (contentType.equals("application/vnd.ms-excel"))
				type = "xls";
			else if (contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
				type = "xlsx";
			else if (contentType.equals("image/jpeg"))
				type = "jpg";
			else if (contentType.equals("image/bmp"))
				type = "bmp";
			else if (contentType.equals("image/png"))
				type = "png";
			else if(contentType.equals("video/mp4"))
				type="mp4";
			else if(contentType.equals("video/mpeg4"))
				type="mp4";
			else if(contentType.equals("audio/mp3"))
				type="mp3";
			
		}
		return type;
	}
}

