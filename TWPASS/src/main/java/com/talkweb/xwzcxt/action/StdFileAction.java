package com.talkweb.xwzcxt.action;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
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
import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.xwzcxt.common.Accessory;
import com.talkweb.xwzcxt.pojo.StandardContentPojo;
import com.talkweb.xwzcxt.pojo.StdFile;
import com.talkweb.xwzcxt.pojo.StdFileAffixPojo;
import com.talkweb.xwzcxt.service.ImportFileDBService;
import com.talkweb.xwzcxt.service.StdFileService;
import com.talkweb.twdpe.web.session.main.Session;
import com.talkweb.twdpe.web.session.util.SessionFactory;
import com.talkweb.twdpe.webcomponent.common.action.BusinessAction;

/**
 * TODO(描述这个类的作用)
 * 
 * @author: 2014-5-27，GuveXie，（描述修改内容）
 */
public class StdFileAction extends BusinessAction {
	
	private static final long serialVersionUID = -4099987109384481259L;

	private static final Logger logger = LoggerFactory.getLogger(StandardLibraryAction.class);
	
	@Autowired
	AppConstants appConstants;
	
	@Autowired
	private StdFileService stdFileService;
	
	//写数据库服务
	@Autowired
	private ImportFileDBService importFileDBService;
	
	public String getStdFilesBySortId() 
	{
		HttpServletRequest request = ServletActionContext.getRequest();
        String sortid = request.getParameter("c_sort_id");
        List<StdFile> result = stdFileService.getStdFileBySortId(sortid);
        this.formatDatagirdData(result, new Pagination(false,10));
        logger.debug("StdFileAction", "getStdFilesBySortId.Action Exec Ok!");
		return SUCCESS;
	}
	
	public String getStdFilesPageBySortId() 
	{
		HttpServletRequest request = ServletActionContext.getRequest();
        String sortid = request.getParameter("c_sort_id");
        Pagination respagination = stdFileService.getStdFileBySortId(sortid, pagination);
        List stdfileList = respagination.getList();
        this.formatDatagirdData(stdfileList, respagination);
        logger.debug("StdFileAction", "getStdFilesPageBySortId.Action Exec Ok!");
		return SUCCESS;
	}

	public String getStdFilesPageByParams() 
	{
		HttpServletRequest request = ServletActionContext.getRequest();
		Map params = new HashMap();
		/*params.put("c_sort_id", request.getParameter("c_sort_id"));
		params.put("c_sfile_name", request.getParameter("c_sfile_name"));
		params.put("c_releaseunit", request.getParameter("c_releaseunit"));
		params.put("c_fw_qcbm", request.getParameter("c_fw_qcbm"));
		params.put("c_fw_gkbm", request.getParameter("c_fw_gkbm"));
		params.put("c_releasetime", request.getParameter("c_releasetime"));
		params.put("c_impletime", request.getParameter("c_impletime"));*/
		
		params.put("c_sort_id", request.getParameter("standardfile.c_sort_id"));
		params.put("c_sfile_name", request.getParameter("standardfile.c_sfile_name"));
		params.put("c_releaseunit", request.getParameter("standardfile.c_releaseunit"));
		params.put("c_fw_qcbm", request.getParameter("standardfile.c_fw_qcbm"));
		params.put("c_fw_gkbm", request.getParameter("standardfile.c_fw_gkbm"));
		params.put("c_releasetime", request.getParameter("standardfile.c_releasetime"));
		params.put("c_impletime", request.getParameter("standardfile.c_impletime"));

        Pagination respagination = stdFileService.getStdFileByParams(params, pagination);
        List stdfileList = respagination.getList();
        this.formatDatagirdData(stdfileList, respagination);
        logger.debug("StdFileAction", "getStdFilesPageByParams.Action Exec Ok!");
		return SUCCESS;
	}

    public String getStdFileInfoByID(){
    	HttpServletRequest request = ServletActionContext.getRequest();
        String fileuuid = request.getParameter("sfileid");
        StdFile data = stdFileService.getStdFileById(fileuuid);
        try {
			this.formatData (this.addPrefix (data, "stdfile."));
		} catch (Exception e) {
            request.setAttribute ("throw", e);
            // 如果出错，则输出一个空的JSON对象
            this.setErrorMessage (e);
            logger.error (e.getMessage (), e);
		}// 给对象属性增加统一的前缀名，并格化输出
    	return SUCCESS;
    }
    
	//---------------------------------文件附件相关Action方法
	public String getStdFileAffixByParams() 
	{
		HttpServletRequest request = ServletActionContext.getRequest();
        Map params = new HashMap();
        params.put("c_sfile_id", request.getParameter("sfileid"));
        params.put("c_att_id", request.getParameter("attid"));
        params.put("c_atttype_id", request.getParameter("atttype"));
        params.put("c_contentid", request.getParameter("contentid"));
        Pagination respagination = stdFileService.getStdFileAffixByParams(params, pagination);
        List stdfileList = respagination.getList();
        this.formatDatagirdData(stdfileList, respagination);
        logger.debug("StdFileAction", "getStdFileAffixByParams.Action Exec Ok!");
		return SUCCESS;
	}
	
	//修改标准文件基础信息
	public String UpdateStdFileInfo(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			Session session = SessionFactory.getInstance(request, response);
			Map user = (Map) session.getAttribute("USERSESSION");
			if (user != null) {
				String userName = user.get("name").toString();
				StdFile stdfileinfo = new StdFile();
				stdfileinfo.setC_modifier(userName);
				stdfileinfo.setC_sfile_id(request.getParameter("stdfile.c_sfile_id"));
				stdfileinfo.setC_sfile_name(request.getParameter("stdfile.c_sfile_name"));
				stdfileinfo.setC_sfile_version(request.getParameter("stdfile.c_sfile_version"));
				stdfileinfo.setC_sort_id(request.getParameter("stdfile.c_sort_id"));
				stdfileinfo.setC_releaseunit(request.getParameter("stdfile.c_releaseunit"));
				stdfileinfo.setC_releasetime(request.getParameter("stdfile.c_releasetime"));
				stdfileinfo.setC_impletime(request.getParameter("stdfile.c_impletime"));

				stdfileinfo.setC_fw_zdyj(request.getParameter("stdfile.c_fw_zdyj"));
				stdfileinfo.setC_fw_xdnr1(request.getParameter("stdfile.c_fw_xdnr1"));
				stdfileinfo.setC_fw_tcdw(request.getParameter("stdfile.c_fw_tcdw"));
				stdfileinfo.setC_fw_qcbm(request.getParameter("stdfile.c_fw_qcbm"));
				stdfileinfo.setC_fw_gkbm(request.getParameter("stdfile.c_fw_gkbm"));
				stdfileinfo.setC_fw_pzr(request.getParameter("stdfile.c_fw_pzr"));
				stdfileinfo.setC_fw_qcr(request.getParameter("stdfile.c_fw_qcr"));
				stdfileinfo.setC_fw_fbsj(request.getParameter("stdfile.c_fw_fbsj"));
				stdfileinfo.setC_fw_xdcs(request.getParameter("stdfile.c_fw_xdcs"));
				
				stdfileinfo.setC_foreword1(request.getParameter("stdfile.c_foreword1"));

				stdfileinfo.setC_modifier(userName);
				
				int n = stdFileService.UpdateStdFileInfo(stdfileinfo);
				this.setMessage(n, "StdFileInfo", "UPDATE"); //"DELETE");
			} else {
				this.setErrorMessage("网页已过期，请重新登陆！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.setErrorMessage("修改标准文件信息失败", e);
		}
		return SUCCESS;
	}
	
	/*
	 * 调用文件上传服务接口
	 * filetypes 文件类型(1图片、2音频、3视频、4其它)
	 * filekind 文件业务分类（1.标准;2.过程记录<任务步骤结果及异常>;3...）
	 * 返回---成功保存后的文件信息表中的ID，否则为空字符串
	 * */
	private String SaveFileToServer(File file,String filetypes,String extens, String filekind){
		HttpClient client = new HttpClient( ); 
		//String url = "http://10.75.97.147:8080/CyxwglWebInService/filemanageraction!UpdateFileInfo.action";
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
			ret = obj.getString("fileID");
		}
		catch(Exception e){
			e.printStackTrace();
		}
		method.releaseConnection();
		return ret;
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
 
 	//附件上传完整版
	public String uploadAffixFile() {
		Map pageData = new HashMap();
		String msg = "";
		boolean reb = false; 
		
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");

		Map params = new HashMap();  
		String requestcharset = request.getCharacterEncoding();
		String filename =  request.getParameter("c_filename");
		System.out.print("--------------filename:"+filename);
		try {
			filename = java.net.URLDecoder.decode(request.getParameter("c_filename"),"UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		System.out.print("--------------filename:"+filename);
		
		Session session = SessionFactory.getInstance(request, response);
	    String userid="";
        Map user = (Map) session.getAttribute("USERSESSION");
        if (user != null){
            userid = user.get("id").toString();
        }else{
            pageData.put("msg", "网页已过期，请重新登陆!");
            pageData.put("result",false);
            this.formatData(pageData);
            return SUCCESS;
        }
        
		System.out.print("--------------filename:"+filename);
		params.put("c_sfile_id", request.getParameter("c_sfile_id"));
		params.put("c_filetypes", request.getParameter("c_filetypes"));
		params.put("c_filename", filename); 
		params.put("c_extens", request.getParameter("c_extens")); 
		params.put("c_filekind", request.getParameter("c_filekind"));
		params.put("debug", request.getParameter("debug"));
		params.put("c_Affix_type", request.getParameter("c_Affix_type"));
		params.put("c_chaptercode", request.getParameter("c_chaptercode"));
		System.out.print(params.toString());	

		String filetype = formatFileType(this.upfileAccessoryContentType);
		
		Accessory tmpAccessory = new Accessory();
    	tmpAccessory.setAccessoryFile(this.upfileAccessory);
    	tmpAccessory.setAccessoryFileName(params.get("c_filename").toString()); //this.upfileAccessoryFileName);
    	tmpAccessory.setAttuuid(UUID.randomUUID().toString());
    	tmpAccessory.setCreatetime(new Date());
    	tmpAccessory.setSfileuuid(params.get("c_sfile_id").toString());
    	tmpAccessory.setAtttype(params.get("c_Affix_type").toString());
    	tmpAccessory.setChapterNO(params.get("c_chaptercode").toString());
    	tmpAccessory.setFiletype(filetype); 
    	tmpAccessory.setCreator(userid);
    	
		String c_file_id = this.SaveFileToServer(this.upfileAccessory, "1",filetype, "2");		
		msg = "附件已被保存，ID为"+c_file_id;
		if(c_file_id==""||c_file_id==null) {
			reb = false;
		}
		else{
			reb = true;
			tmpAccessory.setFileid(c_file_id);
		}

    	int n = importFileDBService.addAccessoryFileToDB(tmpAccessory);
    	if(n>0) msg = msg + "\n且附件信息成功添加至数据库."; 

		pageData.put("result", reb);
		pageData.put("msg", msg);
		this.formatData(pageData);
		return SUCCESS;
	}
	
 	//附件上传完整版
	public String uploadAffixFileX() {
		Map pageData = new HashMap();
		String msg = "";
		boolean reb = false; 
		
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");

		Map params = new HashMap();  
		String requestcharset = request.getCharacterEncoding();
		String filename =  request.getParameter("c_filename");
		System.out.print("--------------filename:"+filename);
		try {
			filename = java.net.URLDecoder.decode(request.getParameter("c_filename"),"UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		System.out.print("--------------filename:"+filename);
		
		Session session = SessionFactory.getInstance(request, response);
	    String userid="";
        Map user = (Map) session.getAttribute("USERSESSION");
        if (user != null){
            userid = user.get("id").toString();
        }else{
            pageData.put("msg", "网页已过期，请重新登陆!");
            pageData.put("result",false);
            this.formatData(pageData);
            return SUCCESS;
        }
        
		System.out.print("--------------filename:"+filename);
		params.put("c_sfile_id", request.getParameter("c_sfile_id"));
		params.put("c_Affix_type", request.getParameter("c_Affix_type"));
		params.put("c_contentid", request.getParameter("c_contentid"));
		//params.put("c_filetypes", request.getParameter("c_filetypes"));
		//params.put("c_filename", filename); 
		//params.put("c_extens", request.getParameter("c_extens")); 
		//params.put("c_filekind", request.getParameter("c_filekind"));
		//params.put("debug", request.getParameter("debug"));
		//params.put("c_chaptercode", request.getParameter("c_chaptercode"));
		System.out.print(params.toString());	

		String filetype = formatFileType(this.upfileAccessoryContentType);
		
		StdFileAffixPojo tmpAffix = new StdFileAffixPojo();
		tmpAffix.setC_att_id(UUID.randomUUID().toString());
		tmpAffix.setC_atttype_id(params.get("c_Affix_type").toString());
		tmpAffix.setC_contentid(params.get("c_contentid").toString());
		tmpAffix.setC_sfile_id(params.get("c_sfile_id").toString());
    	
		String c_file_id = this.SaveFileToServer(this.upfileAccessory, "1",filetype, "2");		
		msg = "附件已被保存，ID为"+c_file_id;
		if(c_file_id==""||c_file_id==null) {
			reb = false;
		}
		else{
			reb = true;
			tmpAffix.setC_file_id(c_file_id);
		}

    	//int n = importFileDBService.addAccessoryFileToDB(tmpAccessory);
		int n = stdFileService.InsertStdFileAffixInfo(tmpAffix);
    	if(n>0) msg = msg + "\n且附件信息成功添加至数据库."; 

		pageData.put("result", reb);
		pageData.put("msg", msg);
		this.formatData(pageData);
		return SUCCESS;
	}
	
	//附件提交Action
	public String submitAffixInfo(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();

		Map params = new HashMap();
		params.put("c_sfile_id", request.getParameter("affix.c_sfile_id"));
		params.put("c_file_type", request.getParameter("affix.c_file_type"));
		params.put("c_file_id", request.getParameter("affix.c_file_id")); 
		params.put("c_file_chapter", request.getParameter("affix.c_file_chapter"));
		params.put("c_file_name", request.getParameter("affix.c_file_name"));
		//File file = this.upfileAccessory; // (File) request.getAttribute("upfileAccessory");

    	Accessory tmpAccessory = new Accessory();
    	tmpAccessory.setAccessoryFile(this.upfileAccessory);
    	tmpAccessory.setAccessoryFileName(request.getParameter("affix.c_file_name"));
    	tmpAccessory.setAttuuid(UUID.randomUUID().toString());
    	tmpAccessory.setCreatetime(new Date());
    	tmpAccessory.setSfileuuid(request.getParameter("affix.c_sfile_id"));
    	//tmpAccessory.setAtttype(request.getParameter("affix.c_file_type"));
    	//tmpAccessory.setChapterNO(request.getParameter("affix.c_file_chapter"));
    	tmpAccessory.setFiletype(formatFileType(this.upfileAccessoryContentType)); 
    	
		Map pageData = new HashMap();
 
		if (params.get("c_sfile_id")==null||params.get("c_sfile_id")==""){
			pageData.put("msg", "需要对应的标准文件信息！");
			pageData.put("result", false);
			this.formatData(pageData);
			return SUCCESS;
		}
		
		if (this.upfileAccessory == null){
			pageData.put("msg", "没有需要上传的文件");
			pageData.put("result", false);
			this.formatData(pageData);
			return SUCCESS;
		}
		
		Session session = SessionFactory.getInstance(request, response);
	    String userid="";
        Map user = (Map) session.getAttribute("USERSESSION");
        if (user != null){
            userid = user.get("id").toString();
        }else{
            pageData.put("msg", "网页已过期，请重新登陆!");
            pageData.put("result",false);
            this.formatData(pageData);
            return SUCCESS;
        }
    	tmpAccessory.setCreator(userid);
        
        String tmpmsg = "";
        try{
        	String fileid = this.SaveFileToServer(this.upfileAccessory, "1", this.upfileAccessoryContentType, "2");
        	if (fileid == null || fileid.equals("")){
    			tmpmsg = tmpmsg +"因系统故障导致附件保存失败，请联系系统维护人员！";
    			pageData.put("msg", tmpmsg);
                pageData.put("result",false);
                this.formatData(pageData);
        		return SUCCESS;
    		}
        	importFileDBService.addAccessoryFileToDB(tmpAccessory);
        	tmpmsg = tmpmsg + "已保存成功！";
			pageData.put("msg", tmpmsg);
	        pageData.put("result",true);
        }catch(Exception e){
        	tmpmsg = tmpmsg + "附件保存失败！";
        	//pageData.put("msg", "附件保存失败！");
        	pageData.put("msg", tmpmsg);
            pageData.put("result",false);
        	e.printStackTrace();
        }
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
		}
		return type;
	}

	//文件附件记录删除方法
	public String deleteStdFileAffixById(){
		HttpServletRequest request = ServletActionContext.getRequest();
        String affixid = request.getParameter("c_att_id");
        int n = stdFileService.DeleteStdFileAffixById(affixid);
        logger.debug("StdFileAction", "deleteStdFileAffixById.Action Done! Effect Row count:"+n);
        Map pageData = new HashMap();
        if(n<1){
        	pageData.put("msg", "删除失败！");
			pageData.put("result", false);
			this.formatData(pageData);
        	return ERROR;
        }else{
        	pageData.put("msg", "记录被成功删除！");
			pageData.put("result", true);
			this.formatData(pageData);
			return SUCCESS;
        }
	}
	
	//---------------------------------标准文件内容相关Action方法
	//文件内容记录删除方法
	public String deleteStdFileContentById(){
		HttpServletRequest request = ServletActionContext.getRequest();
        String contentid = request.getParameter("c_contentid");
        int n = stdFileService.DeleteStdFileContentById(contentid);
        logger.debug("StdFileAction", "deleteStdFileContentById.Action Done! Effect Row count:"+n);
        Map pageData = new HashMap();
        if(n<1){
        	pageData.put("msg", "删除失败！");
			pageData.put("result", false);
			this.formatData(pageData);
        	return ERROR;
        }else{
        	pageData.put("msg", "记录被成功删除！");
			pageData.put("result", true);
			this.formatData(pageData);
			return SUCCESS;
        }	
	}
	
	//文件内容获取--根据标准文件ID
	public String getStdFileContentPageByFileId() 
	{
		HttpServletRequest request = ServletActionContext.getRequest();
        String fileuuid = request.getParameter("sfileid");
        Pagination respagination = stdFileService.getStdFileContentByFileid(fileuuid, pagination);
        List stdfileList = respagination.getList();
        this.formatDatagirdData(stdfileList, respagination);
        logger.debug("StdFileAction", "getStdFileContentPageByFileId.Action Exec Ok!");
		return SUCCESS;
	}

	//文件内容获取--根据参数列表
	public String getStdFileContentPageByParams() 
	{
		HttpServletRequest request = ServletActionContext.getRequest();
		Map params = new HashMap();
		params.put("c_sfile_id", request.getParameter("c_sfile_id"));
		params.put("c_contentid", request.getParameter("c_contentid"));
		params.put("c_title", request.getParameter("c_title"));
		params.put("c_sectionid", request.getParameter("c_sectionid"));
		params.put("c_sectionpid", request.getParameter("c_sectionpid"));
        Pagination respagination = stdFileService.getStdFileContentByParams(params, pagination);
        List contentList = respagination.getList();
        this.formatDatagirdData(contentList, respagination);
        logger.debug("StdFileAction", "getStdFileContentPageByParams.Action Exec Ok!");
		return SUCCESS;
	}
	
    public String getStdFileContentInfoByID(){
    	HttpServletRequest request = ServletActionContext.getRequest();
        String contentid = request.getParameter("c_contentid");
        StandardContentPojo data = stdFileService.getStdFileContentInfoById(contentid);
        try {
			this.formatData (this.addPrefix (data, "stdfilecontent."));
		} catch (Exception e) {
            request.setAttribute ("throw", e);
            // 如果出错，则输出一个空的JSON对象
            this.setErrorMessage (e);
            logger.error (e.getMessage (), e);
		}// 给对象属性增加统一的前缀名，并格化输出
    	return SUCCESS;
    }

	//修改标准文件之章节点内容信息
	public String UpdateStdFileContentInfo(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			Session session = SessionFactory.getInstance(request, response);
			Map user = (Map) session.getAttribute("USERSESSION");
			if (user != null) {
				String userName = user.get("name").toString();
				
				StandardContentPojo tmpObj = new StandardContentPojo();
				tmpObj.setC_contentid(request.getParameter("stdfilecontent.c_contentid"));
				String sectionid = request.getParameter("stdfilecontent.c_sectionid");
				tmpObj.setC_sectionid(sectionid);
				int k = sectionid.lastIndexOf("."); k= k<1?0:k;
				String sectionpid = sectionid.substring(0,k);
				if(sectionpid=="") sectionpid = "0";
				tmpObj.setC_sectionpid(sectionpid);
				tmpObj.setC_title(request.getParameter("stdfilecontent.c_title"));
				tmpObj.setC_content(request.getParameter("stdfilecontent.c_content"));
				boolean c_includeatt = request.getParameter("stdfilecontent.c_includeatt").toLowerCase().contains("true");
				tmpObj.setC_includeatt(c_includeatt);
				if(c_includeatt){ tmpObj.setC_includett_name("1"); }else {tmpObj.setC_includett_name("0");}
				tmpObj.setC_sfile_id(request.getParameter("stdfilecontent.c_sfile_id"));
				tmpObj.setC_modifier(userName);
				 
				int n = stdFileService.UpdateStdFileContentInfo(tmpObj);
				this.setMessage(n, "UpdateStdFileContentInfo", "Update");
				this.setOkMessage("数据保存成功！");
			} else {
				this.setErrorMessage("网页已过期，请重新登陆！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.setErrorMessage("修改标准文件章节点信息失败", e);
		}
		return SUCCESS;
	}

	//插入标准文件之章节点内容信息
	public String AddStdFileContentInfo(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String contentid = "";
		int n = 0;
		String msgstr = "";
		try {
			Session session = SessionFactory.getInstance(request, response);
			Map user = (Map) session.getAttribute("USERSESSION");
			if (user != null) {
				String userName = user.get("name").toString();
				
				StandardContentPojo tmpObj = new StandardContentPojo();
				//contentid = request.getParameter("stdfilecontent.c_contentid");
				contentid = UUID.randomUUID().toString(); //生成ID
				tmpObj.setC_contentid(contentid);
				String sectionid = request.getParameter("stdfilecontent.c_sectionid");
				tmpObj.setC_sectionid(sectionid);
				int k = sectionid.lastIndexOf("."); k= k<1?0:k;
				String sectionpid = sectionid.substring(0,k);
				if(sectionpid.length()<1) sectionpid = "0";
				tmpObj.setC_sectionpid(sectionpid);
				tmpObj.setC_title(request.getParameter("stdfilecontent.c_title"));
				tmpObj.setC_content(request.getParameter("stdfilecontent.c_content"));
				tmpObj.setC_sfile_id(request.getParameter("stdfilecontent.c_sfile_id"));
				//tmpObj.setC_includeatt(request.getParameter("stdfilecontent.c_includeatt").toLowerCase().contains("true"));
				boolean c_includeatt = request.getParameter("stdfilecontent.c_includeatt").toLowerCase().contains("true");
				tmpObj.setC_includeatt(c_includeatt);
				if(c_includeatt){ tmpObj.setC_includett_name("1"); }else {tmpObj.setC_includett_name("0");}
				tmpObj.setC_creator(userName);
				
				n = stdFileService.InsertStdFileContentInfo(tmpObj);
				//this.setMessage(n, "InsertStdFileContentInfo", "Update");
			} else {
				//this.setErrorMessage("网页已过期，请重新登陆！");
				msgstr = "网页已过期，请重新登陆！";
			}
		} catch (Exception e) {
			e.printStackTrace();
			//this.setErrorMessage("添加标准文件章节点信息失败", e);
			msgstr = "添加标准文件章节点信息失败.\n"+e;
		}
		//this.setOkMessage("添加标准文件章节点信息成功！");
		 Map pageData = new HashMap();
     	pageData.put("newid", contentid);
	        if(n<1){
	        	pageData.put("msg", msgstr);
				pageData.put("result", false);
				this.formatData(pageData);
	        	return ERROR;
	        }else{
				msgstr = "添加成功! 新的id="+contentid;
	        	pageData.put("msg", msgstr);
				pageData.put("result", true);
				this.formatData(pageData);
				return SUCCESS;
	        }	
		//return SUCCESS;
	}
	
	

}
