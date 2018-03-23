package com.talkweb.xwzcxt.action;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import oracle.sql.DATE;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.common.AppConstants;
import com.talkweb.twdpe.core.util.json.JsonArray;
import com.talkweb.twdpe.core.util.json.JsonObject;
import com.talkweb.twdpe.web.jcontrols.RuleTree;
import com.talkweb.twdpe.web.session.main.Session;
import com.talkweb.twdpe.web.session.util.SessionFactory;
import com.talkweb.xwzcxt.common.Accessory;
import com.talkweb.xwzcxt.common.StandardContent;
import com.talkweb.xwzcxt.pojo.StandardContentPojo;
import com.talkweb.xwzcxt.pojo.StandardLibraryPojo;
import com.talkweb.xwzcxt.service.ExcelReaderService;
import com.talkweb.xwzcxt.service.ImportFileDBService;


public class FileImportExcelAction extends BusinessAction {
	
	private static final long serialVersionUID = 5143175422682523630L;
	
	private StandardLibraryPojo standardLibraryPojo = null;
	private StandardContentPojo standardContentPojo = null;
	
	//标准excel文件
	private static String fileuuid="";
	private File upfileExcel;
	private String upfileExcelFileName;
	private String upfileExcelContentType;
	
	//附件和附录
	private File upfileAccessory;
	private String upfileAccessoryFileName;
	private String upfileAccessoryContentType; 
	
	//原始文件
	private File upfileOriginal;
	private String upfileOriginalFileName;
	private String upfileOriginalContentType; 
	
	//附件和附录的保存结构
	private static List<Accessory> accessoryFiles;
	
	//excel文件读取服务
	@Autowired
	private ExcelReaderService excelReaderService;
	
	//写数据库服务
	@Autowired
	private ImportFileDBService importFileDBService;
	
	@Autowired
    AppConstants appConstants;
	
	//获取所有文件类别，以树型结构返回
	public String getAllFileSort(){
		try
        {
			List<StandardLibraryPojo> list = importFileDBService.queryAllFileSortTree();
	        RuleTree wtree = new RuleTree();
	        wtree.setId("c_sort_id");
	        wtree.setVal("c_sort_name");
	        wtree.setPid("c_sort_pid");
            List<Map> l = this.initTreeData(list, wtree);
            this.formatData(l);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
		return SUCCESS;
	}
	
	public String uploadStandardExcelFile(){
		HttpServletRequest request = ServletActionContext.getRequest();
	    HttpServletResponse response = ServletActionContext.getResponse();
	    Session session = SessionFactory.getInstance(request, response);
	    Map pageData = new HashMap();
	    String userid="";
        Map user = (Map) session.getAttribute("USERSESSION");
        if (user != null){
            userid = user.get("id").toString();
        }else{
            pageData.put("alert", "网页已过期，请重新登陆!");
            pageData.put("result",false);
            this.formatData(pageData);
            return SUCCESS;
        }
		
		
		boolean isQulify = true;
		String msg = "";
		String alert = "";
		try{
			if (fileuuid != null && !fileuuid.equals("")){
				if (upfileExcel != null){
					//清空附件附录列表
					if (accessoryFiles != null)
						accessoryFiles.clear();
					
					//解析内容并做格式校验
					FileInputStream fileis = new FileInputStream(upfileExcel);
					Map<Integer,List<String>> content = new HashMap<Integer,List<String>>();
					content = excelReaderService.readExcelContentEx(fileis);
					//校验格式是否合法
					List<StandardContent> standardContent = new ArrayList<StandardContent>();
					for (int i=0; i<content.size(); i++){
						List<String> tempcontent = content.get(i);
						if (tempcontent.size() >= 4){
							StandardContent standtemp = new StandardContent();
							//处理章节号为.0的状态
							String ttt = tempcontent.get(0).trim();
							ttt = ttt.replaceAll("^[　*| *]*","").replaceAll("[　*| *]*$","");
							if (ttt.charAt(ttt.length()-1) =='0'
								&& ttt.charAt(ttt.length()-2) =='.'){
								ttt = ((int)Float.parseFloat(ttt))+"";
								//tempcontent.set(0, ((int)Float.parseFloat(tempcontent.get(0)))+"");
							}
							//章节编号不为空，且内容为数字和.
							if (ttt.matches("^([0-9])+(.([0-9])+)*$") == false){
								isQulify = false;
								msg += "第"+(i+2)+"行章节号不符合规范"+'\n';
							}
							//标题和内容不能同时为空
							if (tempcontent.get(1).isEmpty() && tempcontent.get(2).isEmpty()){
								isQulify = false;
								msg += "第"+(i+2)+"行标题和内容同时为空"+'\n';
							}
							//解析是否有附件
							if (!tempcontent.get(3).isEmpty() && isQulify == true){
								if (tempcontent.get(3).equals("是")){
									msg += ttt+",";
								}
							}
								
							if (isQulify == true){
								standtemp.setC_contentid(UUID.randomUUID().toString());
								standtemp.setC_sectionid(ttt);
									
								String pid="0";
								if (ttt.contains("."))
									pid = ttt.substring(0,ttt.lastIndexOf('.'));
								
								standtemp.setC_sectionpid(pid);
								standtemp.setC_title(tempcontent.get(1));
								standtemp.setC_content(tempcontent.get(2));
								standtemp.setC_includeatt(tempcontent.get(3).equals("是")?"1":"0");
								standtemp.setC_sfile_id(fileuuid);
								standtemp.setC_creator(userid);
								standtemp.setC_createtime(new Date());
								standardContent.add(standtemp);
							}
						}else{
							msg +="文件格式不符合要求,";
							isQulify = false;
						}
					}
					
					if (isQulify == true){//文件合法，将内容存入数据库
						importFileDBService.expendStandardExcelFile(standardContent);
					}
					
					pageData.put("msg", msg);
					pageData.put("result", isQulify);
				}
			}else{
				pageData.put("msg", "请先保存文件基本信息！");
				pageData.put("result", false);
			}
			pageData.put("alert", "");
		} catch(Exception e) {
			pageData.put("alert", "保存文件失败！" + e.toString()+e.getStackTrace().toString());
			pageData.put("result", false);
			e.printStackTrace();
		}
		this.formatData(pageData);
		return SUCCESS;
	}
	
	public String uploadAccessoryFile(){
		//HttpServletRequest request = ServletActionContext.getRequest();
		if (accessoryFiles == null)
			accessoryFiles = new ArrayList<Accessory>();
		
		Map pageData = new HashMap();
		boolean result = true;
		try{
			if (fileuuid != "" && upfileAccessory != null){
				for (int i=0; i<accessoryFiles.size(); i++){
					if (upfileAccessoryFileName.equals(accessoryFiles.get(i).getAccessoryFileName())){
						pageData.put("msg", "该文件已存在！");
						result = false;
					}
				}
				if (result == true){
					Accessory temp = new Accessory();
					temp.setAccessoryFileName(upfileAccessoryFileName);
					temp.setAccessoryFile(upfileAccessory);
					temp.setAccessoryType(upfileAccessoryContentType);
					accessoryFiles.add(temp);
				}
				pageData.put("result", result);
			}else{
				pageData.put("result", false);
				pageData.put("msg","系统异常!");
			}
		} catch(Exception e) {
			pageData.put("result", false);
			pageData.put("msg","系统异常!");
			e.printStackTrace();
		}
		this.formatData(pageData);
		return SUCCESS;
	}
	
	public String submitAccessoryInformation(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String datas = request.getParameter("data");
		
		Map pageData = new HashMap();
		if (fileuuid == null || fileuuid.equals("")){
			pageData.put("msg", "请先保存文件的基本信息！");
			pageData.put("result", false);
			this.formatData(pageData);
			return SUCCESS;
		}
		
		if (accessoryFiles == null || accessoryFiles.size()<=0){
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
		
        try{
			if (datas!=null && !datas.equals("")){
				JSONArray jsons = JSONArray.fromObject(datas);
				for (int i=0; i<jsons.size(); i++){
					JSONObject obj = JSONObject.fromObject(jsons.get(i));
					Map<String, String> result = new HashMap<String, String>(); 
			        Iterator<String> iterator = obj.keys(); 
			        String key = null; 
			        String value = null; 
			        while (iterator.hasNext()) 
			        { 
			            key = iterator.next(); 
			            value = obj.getString(key); 
			            result.put(key, value); 
			        }
			        
			        for (int j=0; j<accessoryFiles.size(); j++){
			        	Accessory temp = accessoryFiles.get(j);
			        	if (result.get("filenamegrid").equals(temp.getAccessoryFileName())){
			        		//调用谢辉的保存接口返回的id值
			        		temp.setAttuuid(UUID.randomUUID().toString());
			        		temp.setCreator(userid);
			        		temp.setCreatetime(new Date());
			        		temp.setSfileuuid(fileuuid);
			        		temp.setAtttype(result.get("filetype"));
			        		temp.setChapterNO(result.get("filechapter"));
			        		temp.setFiletype(formatFileType(temp.getAccessoryType()));
			        		//保存信息到T_SD_FILE和T_SD_STANDARDATT
			        		String fileid=saveFileToTSDFile(temp);
			        		Thread.sleep(1);
			        		if (fileid == null || fileid.equals("")){
			        			pageData.put("msg", "因系统故障导致附件保存失败，请联系系统维护人员！");
			                    pageData.put("result",false);
			                    this.formatData(pageData);
			            		return SUCCESS;
			        		}
			        		temp.setFileid(fileid);
			        		importFileDBService.addAccessoryFileToDB(temp);
			        		accessoryFiles.remove(j);
			        		break;
			        	}
			        }
				}
				pageData.put("msg", "附件保存成功！");
	            pageData.put("result",true);
			}
        }catch(Exception e){
        	pageData.put("msg", "附件保存失败！");
            pageData.put("result",false);
        	e.printStackTrace();
        }
		this.formatData(pageData);
		return SUCCESS;
	}
	
	public String deleteAccessory(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String name = request.getParameter("filename");
		if (!name.equals("") && accessoryFiles != null && accessoryFiles.size()>0){
			for (int i=0; i<accessoryFiles.size(); i++){
				if (name.equals(accessoryFiles.get(i).getAccessoryFileName()))
					accessoryFiles.remove(i);
			}
		}
		return SUCCESS;
	}
	
	public String uploadOriginalFile(){
		//HttpServletRequest request = ServletActionContext.getRequest();
		JsonArray array = new JsonArray();
		JsonObject json = null;
		try{
			// upfileOriginal
			String fileName = upfileOriginalFileName;
			/*
			 * String sufix = StringUtils.substringAfterLast(fileName, ".");// 获取后缀名 String radomname = UniqueIdUtils.getUID(); String path = realpath + File.separator + radomname + "." + sufix; File destFile = new File(path); FileUtils.copyFile(upfile.get(i), destFile);
			 * 
			 * /*String imgpath = thuPath + File.separator + radomname + "thu." +sufix; ImageUtil.compress(path, imgpath, 200, 200);
			 */

			json = new JsonObject();
			json.put("name", fileName);
			// json.put("fileid", fileid);
			// json.put("path", "/" + image+"/" + radomname + "." + sufix);
			json.put("allowed", true);
			json.put("missing", false);
			array.add(json);
			
			this.setOkMessage("图片上传成功");
		} catch(Exception e) {
			json = new JsonObject();
			json.put("missing", true);
			array.add(json);
			//显示图片上传失败
			this.setErrorMessage("图片上传失败");
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	//保存文件基础信息
	public String saveBasicInformationOfFile(){
		if (standardLibraryPojo == null)
			standardLibraryPojo = new StandardLibraryPojo();
		this.excludeNullProperties(standardLibraryPojo);
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		fileuuid = request.getParameter("uuid");
		
		Map pagedata = new HashMap();
		Session session = SessionFactory.getInstance(request, response);
	    String userid="";
        Map user = (Map) session.getAttribute("USERSESSION");
        if (user != null){
            userid = user.get("id").toString();
        }else{
        	pagedata.put("msg", "网页已过期，请重新登陆!");
        	pagedata.put("result",false);
            this.formatData(pagedata);
            return SUCCESS;
        }
		
        int result = -1;
		String msg = "";
		String data = "";
		try{
			if (fileuuid == null || fileuuid.equals("")){//新增
				if (accessoryFiles != null && accessoryFiles.size()>0)
					accessoryFiles.clear();
				
				fileuuid = UUID.randomUUID().toString();
				standardLibraryPojo.setC_sfile_id(fileuuid);
				standardLibraryPojo.setC_creator(userid);
				standardLibraryPojo.setC_createtime(new Date());
				importFileDBService.insertBasicInformationOfFile(standardLibraryPojo);
				result = 0;
				data = fileuuid;
				msg = "写入文件信息成功！";
			}else{//编辑
				
			}
		}catch(Exception e){
			msg = "写入标准文件信息失败，请检查填写内容！";
			e.printStackTrace();
		}
		pagedata.put("result", result);
		pagedata.put("msg", msg);
		pagedata.put("data", data);
		this.formatData(pagedata);
		return SUCCESS;
	}
	
	//保存前言
	public String savePrefaceOfFile(){
		if (standardLibraryPojo == null)
			standardLibraryPojo = new StandardLibraryPojo();
		this.excludeNullProperties(standardLibraryPojo);
		HttpServletRequest request = ServletActionContext.getRequest();
		String uuid = request.getParameter("uuid");
		String type = request.getParameter("type");
		Map pagedata = new HashMap();
		int result = -1;
		String msg = "";
		
		try{
			standardLibraryPojo.setC_sfile_id(uuid);
			standardLibraryPojo.setC_sort_id(type);
			importFileDBService.savePrefaceOfFile(standardLibraryPojo,Float.parseFloat(type));
			result = 0;
			msg = "写入标准文件信息成功！";
		}catch(Exception e){
			result = -1;
			msg = "写入标准文件信息失败，请检查填写内容！";
			e.printStackTrace();
		}
		
		pagedata.put("result", result);
		pagedata.put("msg", msg);
		this.formatData(pagedata);
		return SUCCESS;
	}
	
	private String saveFileToTSDFile(Accessory f){
		HttpClient client = new HttpClient( ); 
		//String url = "http://10.75.97.147:8080/CyxwglWebInService/filemanageraction!UpdateFileInfo.action";
		String url = appConstants.getFILE_URL()+"/filemanageraction!UpdateFileInfo.action";
		PostMethod method = new PostMethod(url);
		Part[] params = new Part[4]; 
		String ret = "";
		try{
			StringPart stringPart1 = new StringPart("c_filetypes","1");  
			StringPart stringPart2 = new StringPart("c_extens",formatFileType(f.getAccessoryType()));  
			StringPart stringPart3 = new StringPart("c_filekind","2");  
			
			params[0]=stringPart1;
			params[1]=stringPart2;
			params[2]=stringPart3;
			
			FilePart filePart = new FilePart("files", f.getAccessoryFile());  
			params[3]=filePart;
			//禁用服务调试模式
			params[4]=new StringPart("debug","0");
			MultipartRequestEntity post =  new MultipartRequestEntity(params, method.getParams());  

			method.setRequestEntity(post);  
			client.executeMethod(method); 
			JSONObject obj = JSONObject.fromObject(method.getResponseBodyAsString());
			ret = obj.getString("fileID");
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return ret;
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

	public File getUpfileAccessory() {
		return upfileAccessory;
	}

	public void setUpfileAccessory(File upfileAccessory) {
		this.upfileAccessory = upfileAccessory;
	}

	public String getUpfileAccessoryFileName() {
		return upfileAccessoryFileName;
	}

	public void setUpfileAccessoryFileName(String upfileAccessoryFileName) {
		this.upfileAccessoryFileName = upfileAccessoryFileName;
	}

	public String getUpfileAccessoryContentType() {
		return upfileAccessoryContentType;
	}

	public void setUpfileAccessoryContentType(String upfileAccessoryContentType) {
		this.upfileAccessoryContentType = upfileAccessoryContentType;
	}

	public File getUpfileOriginal() {
		return upfileOriginal;
	}

	public void setUpfileOriginal(File upfileOriginal) {
		this.upfileOriginal = upfileOriginal;
	}

	public String getUpfileOriginalFileName() {
		return upfileOriginalFileName;
	}

	public void setUpfileOriginalFileName(String upfileOriginalFileName) {
		this.upfileOriginalFileName = upfileOriginalFileName;
	}

	public String getUpfileOriginalContentType() {
		return upfileOriginalContentType;
	}

	public void setUpfileOriginalContentType(String upfileOriginalContentType) {
		this.upfileOriginalContentType = upfileOriginalContentType;
	}

	public ExcelReaderService getExcelReaderService() {
		return excelReaderService;
	}

	public void setExcelReaderService(ExcelReaderService excelReaderService) {
		this.excelReaderService = excelReaderService;
	}

	public ImportFileDBService getImportFileDBService() {
		return importFileDBService;
	}

	public void setImportFileDBService(ImportFileDBService importFileDBService) {
		this.importFileDBService = importFileDBService;
	}

	public StandardLibraryPojo getStandardLibraryPojo() {
		return standardLibraryPojo;
	}

	public void setStandardLibraryPojo(StandardLibraryPojo standardLibraryPojo) {
		this.standardLibraryPojo = standardLibraryPojo;
	}

	public StandardContentPojo getStandardContentPojo() {
		return standardContentPojo;
	}

	public void setStandardContentPojo(StandardContentPojo standardContentPojo) {
		this.standardContentPojo = standardContentPojo;
	}

}
