package com.talkweb.xwzcxt.action;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.twdpe.web.session.main.Session;
import com.talkweb.twdpe.web.session.util.SessionFactory;
import com.talkweb.xwzcxt.service.ImportFileDBService;
import com.talkweb.xwzcxt.service.TaskErrTraceService;
import com.talkweb.xwzcxt.vo.TaskErrTraceVo;

public class TaskErrTraceAction extends BusinessAction {

	private static final long serialVersionUID = 34285321431576752L;

	private TaskErrTraceVo taskErrTraceVo=new TaskErrTraceVo();
	@Autowired
	private TaskErrTraceService taskErrTraceService;
	private HttpServletRequest request = ServletActionContext.getRequest();
	private HttpServletResponse response = ServletActionContext.getResponse();
	

	public TaskErrTraceVo getTaskErrTraceVo() {
		
		return taskErrTraceVo;
	}

	public void setTaskErrTraceVo(TaskErrTraceVo taskErrTraceVo) {
		this.taskErrTraceVo = taskErrTraceVo;
	}

	// ---------------------------------文件附件相关Action方法
	// 附件和附录

	private File upfileAccessory;
	private String upfileAccessoryFileName;
	private String upfileAccessoryContentType;

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

	

	@Autowired
	private AppConstants appConstants;

	@Autowired
	private ImportFileDBService importFileDBService;

	/*
	 * 调用文件上传服务接口 filetypes 文件类型(1图片、2音频、3视频、4其它) filekind
	 * 文件业务分类（1.标准;2.过程记录<任务步骤结果及异常>;3...） 返回---成功保存后的文件信息表中的ID，否则为空字符串
	 */
	private String SaveFileToServer(File file, String filetypes, String extens,
			String filekind) {
		HttpClient client = new HttpClient();
		// String url =
		// "http://10.75.97.147:8080/CyxwglWebInService/filemanageraction!UpdateFileInfo.action";
		String url = appConstants.getFILE_URL()
				+ "/filemanageraction!UpdateFileInfo.action";
		PostMethod method = new PostMethod(url);
		Part[] params = new Part[5];
		String ret = "";
		try {
			StringPart stringPart1 = new StringPart("c_filetypes", filetypes); // "1");
			StringPart stringPart2 = new StringPart("c_extens", extens); // ormatFileType(f.getAccessoryType()));
			StringPart stringPart3 = new StringPart("c_filekind", filekind); // "1");

			params[0] = stringPart1;
			params[1] = stringPart2;
			params[2] = stringPart3;

			if (file == null)
				return "";
			// if(file.isFile()&&file.canRead()) return "";
			FilePart filePart = new FilePart("files", file);
			params[3] = filePart;
			// 禁用服务调试模式
			params[4] = new StringPart("debug", "0");
			MultipartRequestEntity post = new MultipartRequestEntity(params,
					method.getParams());
			method.setRequestEntity(post);
			client.executeMethod(method);
			System.out.print("--------------------\n"
					+ method.getResponseBodyAsString());
			JSONObject obj = JSONObject.fromObject(method
					.getResponseBodyAsString());
			ret = obj.getString("fileID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		method.releaseConnection();
		return ret;
	}

	// 附件上传完整版
	public String uploadAffixFile() {
		try {
			Map pageData = new HashMap();
			String msg = "";
			boolean reb = false;

			HttpServletRequest request = ServletActionContext.getRequest();
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=utf-8");

			Map params = new HashMap();
			String requestcharset = request.getCharacterEncoding();
			String filename = request.getParameter("c_filename");
			System.out.print("--------------filename:" + filename);
			try {
				filename = java.net.URLDecoder.decode(
						request.getParameter("c_filename"), "UTF-8");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			System.out.print("--------------filename:" + filename);

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

			System.out.print("--------------filename:" + filename);
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

			String c_file_id = this.SaveFileToServer(this.upfileAccessory,
					params.get("c_filetypes").toString(), filetype,
					params.get("c_filekind").toString());
			msg = "附件已被保存!";
			if (c_file_id == "" || c_file_id == null) {
				reb = false;
			} else {
				reb = true;

			}

			pageData.put("c_file_id", c_file_id);
			pageData.put("msg", msg);
			this.formatData(pageData);
		} catch (Exception e) {
			e.printStackTrace();
			return SUCCESS;
		}
		return SUCCESS;
	}

	private String formatFileType(String contentType) {
		String type = contentType;
		if (contentType != null && !contentType.equals("")) {
			if (contentType.equals("application/msword"))
				type = "doc";
			else if (contentType
					.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document"))
				type = "docx";
			else if (contentType.equals("application/vnd.ms-excel"))
				type = "xls";
			else if (contentType
					.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
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
			else if("image/jpeg".equals(contentType))
				type="jpeg";
			else if("image/gif".equals(contentType))
				type="gif";
		}
		return type;
	}



	private Map getUser(){
		Session session=SessionFactory.getInstance(request, response);
		Map user = (Map) session.getAttribute("USERSESSION");
		return user;
	}
	public String newError() {
		try {
			Map user = getUser();
			if(user==null){
				this.formatData("请先登录!");
				return SUCCESS;
			}
			if(taskErrTraceVo==null){
				this.formatData("没有要提交的数据！");
				return SUCCESS;
			}
			
		   
			taskErrTraceVo.setC_writer((Long)user.get("id"));
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date nowDate=new Date();
			String now=sdf.format(nowDate);
			taskErrTraceVo.setC_write_time(nowDate);
			String c_feedback_lotno=new String(now);
			c_feedback_lotno=c_feedback_lotno.replace("-", "");
			c_feedback_lotno=c_feedback_lotno.replace(":", "");
			c_feedback_lotno=c_feedback_lotno.replace(" ", "");
			taskErrTraceVo.setC_feedback_lotno(c_feedback_lotno);
			String isBySelf=taskErrTraceVo.getC_isbyself();
			String c_cc_userid_list=taskErrTraceVo.getC_cc_userid_list();
			c_cc_userid_list=c_cc_userid_list.replace("3-", "");

			
			taskErrTraceVo.setC_cc_userid_list(c_cc_userid_list);
			if(isBySelf.equals("0")||isBySelf==null || isBySelf.equals("")){
				taskErrTraceVo.setDealc_errdes(null);
				taskErrTraceVo.setDealc_results(null);
				taskErrTraceVo.setDealc_tracefunids(null);
			}else if("1".equals(isBySelf)){
				taskErrTraceVo.setC_chk_status(1);
			}
			taskErrTraceService.newError(taskErrTraceVo);
			WaithandTaskMngAction.setIserrFlag("1");
			
			this.formatData("数据提交成功!");
			
		} catch (Exception e) {
			e.printStackTrace();
			this.formatData("数据提交失败!");
			return SUCCESS;
		}
		return SUCCESS;
	}

	public String getFileInfoById() {
		try {
			String url = appConstants.getIMG_URL();
			String fileid = request.getParameter("c_file_id");
			String[] flist = null;
			List<String> fileList = null;
			List<TaskErrTraceVo> fileInfo=null;
			if (fileid != null) {
				flist = fileid.split(",");
				if (flist != null) {
					fileList = new ArrayList<String>();
					for (String id : flist) {
						fileList.add(id);
					}
					fileInfo=taskErrTraceService.getFileInfoById(fileList);
					for(TaskErrTraceVo vo:fileInfo){
						vo.setC_file_path(url+vo.getC_file_path());
					}
				}
			}
			if (fileInfo != null && fileInfo.size() == 1) {
				this.formatData(fileInfo.get(0));
			} else {
				this.formatData(fileInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return SUCCESS;
		}

		return SUCCESS;
	}
	
	public String getErrorFeedbackList(){
		try{
			Map user=this.getUser();
			if(user==null){
				return SUCCESS;
			}
			Long userid=(Long)user.get("id");
			if(taskErrTraceVo==null){
				taskErrTraceVo=new TaskErrTraceVo();
			}
			taskErrTraceVo.setUserid(userid);
			
			Integer infotype=taskErrTraceVo.getInfotype();
			if(infotype==null){
				taskErrTraceVo.setInfotype(1);
			}
			
/*			if(infotype!=null && infotype.equals("0")){
				taskErrTraceVo.setC_feedback_type(0);
			}else if(infotype!=null && infotype.equals("1")){
				
			}else if(infotype!=null && infotype.equals("2")){
				
			}
			*/
			if(pagination==null){
				pagination=new Pagination(1,20);
			}
			taskErrTraceService.getErrorFeedbackList(taskErrTraceVo, pagination);
			this.formatDatagirdData(pagination.getList(), pagination);
		}catch(Exception e){
			e.printStackTrace();
			this.formatDatagirdData(pagination.getList(), pagination);
			return SUCCESS;
		}
		return SUCCESS;
	}
	
	
	public String receiveConfirmEror(){  //接收确认
		try{
			Map user=this.getUser();
			if(user==null){
				return SUCCESS;
			}
			Long userid=(Long)user.get("id");
			taskErrTraceService.receiveConfirmEror(taskErrTraceVo);
			this.formatData("确认成功！");
		}catch(Exception e){
			e.printStackTrace();
			this.formatData("确认失败！");
			return SUCCESS;
		}
		return SUCCESS;
	}
	
	
	public String continueFeedback(){  //继续反馈
		try{
			Map user=this.getUser();
			if(user==null){
				return SUCCESS;
			}
			Long userid=(Long)user.get("id");
			taskErrTraceVo.setC_feedbacker(userid);
			Date date=new Date();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String c_feedback_lotno = sdf.format(date);
			c_feedback_lotno=c_feedback_lotno.replace("-", "");
			c_feedback_lotno=c_feedback_lotno.replace(":", "");
			c_feedback_lotno=c_feedback_lotno.replace(" ", "");
			taskErrTraceVo.setC_feedback_lotno(c_feedback_lotno);
			
			Date c_suggestend_time=taskErrTraceVo.getC_suggestend_time();
			if(c_suggestend_time==null){
				Calendar cal=Calendar.getInstance();
				cal.setTime(date);
				cal.add(Calendar.HOUR_OF_DAY, 8);
				c_suggestend_time=cal.getTime();
				taskErrTraceVo.setC_suggestend_time(c_suggestend_time);
			}
			
			String c_cc_userid_list=taskErrTraceVo.getC_cc_userid_list();
			if(c_cc_userid_list!=null){
				c_cc_userid_list=c_cc_userid_list.replace("U-", "");
			}
			taskErrTraceVo.setC_cc_userid_list(c_cc_userid_list);
			taskErrTraceService.continueFeedback(taskErrTraceVo);
			this.formatData("数据提交成功!");
		}catch(Exception e){
			e.printStackTrace();
			this.formatData("数据提交失败!");
			return SUCCESS;
		}
		return SUCCESS;
	}

	
	public String finishError(){
		try{
			Map user=this.getUser();
			if(user==null){
				return SUCCESS;
			}
			Long userid=(Long)user.get("id");
			taskErrTraceVo.setC_handle_userid(userid);
			Date date=new Date();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String c_commit_lotno = sdf.format(date);
			c_commit_lotno=c_commit_lotno.replace("-", "");
			c_commit_lotno=c_commit_lotno.replace(":", "");
			c_commit_lotno=c_commit_lotno.replace(" ", "");
			taskErrTraceVo.setC_commit_lotno(c_commit_lotno);
			taskErrTraceService.finishError(taskErrTraceVo);
			this.formatData("数据提交成功！");
		}catch(Exception e){
			e.printStackTrace();
			this.formatData("数据提交失败！");
			return SUCCESS;
		}
		return SUCCESS;
	}
	
	public String getNeedHandleErrInfo(){
		try{
			Map user=this.getUser();
			if(user==null){
				return SUCCESS;
			}
			Long userid=(Long)user.get("id");
			taskErrTraceVo.setUserid(userid);
			if(pagination==null){
				pagination=new Pagination(1,15);
			}
			List list=taskErrTraceService.getNeedHandleErrInfo(taskErrTraceVo,pagination);
			this.formatDatagirdData(list, pagination);
		}catch(Exception e){
			e.printStackTrace();
			this.formatDatagirdData(new ArrayList(), pagination);
			return SUCCESS;
		}
		return SUCCESS;
	}
}
