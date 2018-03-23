package com.talkweb.xwzcxt.action;

import java.io.File;
import java.io.PrintWriter;
import java.io.Serializable;
import java.text.ParseException;
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

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.common.AppConstants;
import com.talkweb.twdpe.core.io.IOException;
import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.twdpe.web.session.main.Session;
import com.talkweb.twdpe.web.session.util.SessionFactory;
import com.talkweb.xwzcxt.pojo.MyLogPojo;
import com.talkweb.xwzcxt.pojo.NewsPojo;
import com.talkweb.xwzcxt.service.MyLogService;
import com.talkweb.xwzcxt.service.NewsService;


public class NewsAction extends baseAction { 
	
	private static final long serialVersionUID = 8058213201981056018L;
	private static final Logger logger = LoggerFactory.getLogger(JobObjectsAction.class);
	
	@Autowired
	AppConstants appConstants;
	
	@Autowired
	private NewsService newsService;
	
	@Autowired
	private MyLogService logService;
	
	private NewsPojo newsPojo = new NewsPojo();
	
	private HttpServletRequest request = ServletActionContext.getRequest();
	private HttpServletResponse response = ServletActionContext.getResponse();	
 
 
	private boolean isLogin() {
		Session session = SessionFactory.getInstance(request, response);
		Map user = (Map) session.getAttribute("USERSESSION");
		if (user != null) {
			return true;
		}
		return false;
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getNewsInfo() {
		try {
			String newsTitle = request.getParameter("newsTitle");
			String newsAuthor = request.getParameter("newsAuthor");
			String newsId = request.getParameter("newsId");
			
			String manageSection = request.getParameter("manageSection");
			String orgId = request.getParameter("orgId");
			
			Map map = new HashMap();
			
			if(newsTitle != null && !"".equals(newsTitle)){
				map.put("newsTitle", "%" + newsTitle + "%");
			}
			if(newsAuthor !=null && !"".equals(newsAuthor)){
				map.put("newsAuthor", "%" + newsAuthor + "%");
			}
			if(newsId !=null && !"".equals(newsId)){
				map.put("newsId", newsId);
			}
			if(manageSection != null && !"".equals(manageSection)){
				map.put("manageSection", Long.parseLong(manageSection));
			}
			if(orgId != null && !"".equals(orgId)){
				map.put("orgId", orgId);
			}
			
			if (pagination == null) {
				pagination = new Pagination(1, 20);
			}
			
			newsService.getNewsInfo(pagination, map);
 
			this.formatDatagirdData(pagination.getList(), pagination);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		return SUCCESS;
	}	
 
	
	@SuppressWarnings("unused")
	public String newsAdd() {
		String userName="";
		String msg="";
		boolean result=false;
		Map pageData = new HashMap();
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String newsTitle=request.getParameter("newsTitle");
		String newsXhEditor=request.getParameter("editorVal");
		String newsAuthor=request.getParameter("newsAuthor");
		String newsSummary=request.getParameter("summary");
		String cNewsFileId=request.getParameter("cNewsFileId");
		
		String manageSection=request.getParameter("manageSection");
		String manageSectionName=request.getParameter("manageSectionName");
		String orgId=request.getParameter("orgId");
		String orgName=request.getParameter("orgName");
		
		Session session = SessionFactory.getInstance(request, response);
		Map user = (Map) session.getAttribute("USERSESSION");
		if (user != null) {
			userName = user.get("name").toString();
		}

		NewsPojo newsInfo=new NewsPojo();
		newsInfo.setNewsId(UUID.randomUUID().toString());
		newsInfo.setNewsTitle(newsTitle);
		newsInfo.setNewsAuthor(newsAuthor);
		newsInfo.setBody(newsXhEditor);
		newsInfo.setSummary(newsSummary);
		newsInfo.setCreator(userName);
		newsInfo.setCover(cNewsFileId);
		newsInfo.setcNewsFileId(cNewsFileId);
		
		if(manageSection!=null&&!"".equals(manageSection)){
			newsInfo.setManageSection(Long.parseLong(manageSection));
		}
		if(orgId!=null&&!"".equals(orgId)){
			newsInfo.setOrgId(Long.parseLong(orgId));
		}
		
		newsInfo.setManageSectionName(manageSectionName);
		newsInfo.setOrgName(orgName);
 
		try {
			String createDate=df.format(new Date());
			newsInfo.setCreateTime(df.parse(createDate));
			newsService.addNewsInfo(newsInfo);
			
			//记录数据日志
			NewsPojo addnewsinfo = this.getNewsById(newsInfo.getNewsId());
			JSONObject jsonNews = JSONObject.fromObject(addnewsinfo);
			logService.addLogInfo(request, response, "1", "过程行为支持系统", "2", "基础管理", "新闻发布管理", MyLogPojo.add, "新闻发布管理-新增", "成功", "1", "T_NEWS", "", jsonNews.toString());
			
			//调用新闻发布接口
			this.publishTheNews(createDate,"1");
			this.formatData("新增新闻成功！");
			
		} catch (Exception e) {
			this.formatData("新增新闻失败！");
			e.printStackTrace();
		}
 
		return SUCCESS;
	}
	
	//新闻发布
	private void publishTheNews(String createTime, String type) {
		HttpClient client = new HttpClient();
		String url = appConstants.getFILE_URL() + "/chatCircleAction!getNewByTime.action";
		PostMethod method = new PostMethod(url);
		Part[] params = new Part[2];
		String ret = "";
		try {
			StringPart stringPart1 = new StringPart("createTime",createTime);
			StringPart stringPart2 = new StringPart("type", type);

			params[0] = stringPart1;
			params[1] = stringPart2;
			MultipartRequestEntity post = new MultipartRequestEntity(params, method.getParams());
			method.setRequestEntity(post);
			client.executeMethod(method);
			
			System.out.print("--------------------\n" + method.getResponseBodyAsString());
			JSONObject obj = JSONObject.fromObject(method.getResponseBodyAsString());
			ret = obj.getString("result");
			JSONObject obj2 = JSONObject.fromObject(ret);
			String code = obj2.getString("code");
			if(code.equals("1")){
				System.out.print("--------------------\n新闻发布成功！");
				method.releaseConnection();
				//return true;
			}else{
				System.out.print("--------------------\n新闻发布失败！");
				method.releaseConnection();
				//return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			method.releaseConnection();
		}
	}


	public String newsUpdate(){
		if (!isLogin()) {
			this.formatData("请先登录!");
			return SUCCESS;
		}
		
		String userName="";
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		Session session = SessionFactory.getInstance(request, response);
		Map user = (Map) session.getAttribute("USERSESSION");
		if (user != null) {
			userName = user.get("name").toString();
		}
		
		String newsId=request.getParameter("newsId");
		String newsTitle=request.getParameter("newsTitle");
		String newsXhEditor=request.getParameter("editorVal");
		String newsAuthor=request.getParameter("newsAuthor");
		String newsSummary=request.getParameter("summary");
		String cNewsFileId=request.getParameter("cNewsFileId");
		
		String manageSection=request.getParameter("manageSection");
		String manageSectionName=request.getParameter("manageSectionName");
		String orgId=request.getParameter("orgId");
		String orgName=request.getParameter("orgName");
		
		if(newsId!=null){
			newsPojo.setNewsId(newsId);
		}
		if(newsTitle!=null){
			newsPojo.setNewsTitle(newsTitle);
		}
		if(newsXhEditor!=null){
			newsPojo.setBody(newsXhEditor);
		}
		if(newsAuthor!=null){
			newsPojo.setNewsAuthor(newsAuthor);
		}
		if(newsSummary!=null){
			newsPojo.setSummary(newsSummary);
		}
		if(cNewsFileId!=null){
			newsPojo.setCover(cNewsFileId);
			newsPojo.setcNewsFileId(cNewsFileId);
		}
		if(manageSection!=null&&!"".equals(manageSection)){
			newsPojo.setManageSection(Long.parseLong(manageSection));
		}
		if(manageSectionName!=null){
			newsPojo.setManageSectionName(manageSectionName);
		}
		if(orgId!=null&&!"".equals(orgId)){
			newsPojo.setOrgId(Long.parseLong(orgId));
		}
		if(orgName!=null){
			newsPojo.setOrgName(orgName);
		}
		newsPojo.setModifier(userName);
		
		try {
			newsPojo.setModifyTime(df.parse(df.format(new Date())));
			NewsPojo oldnewsinfo = this.getNewsById(newsPojo.getNewsId());
			int count=newsService.updateNews(newsPojo);
			
			//记录数据日志
			NewsPojo newnewsinfo = this.getNewsById(newsPojo.getNewsId());
			JSONObject oldjsonNews = JSONObject.fromObject(oldnewsinfo);
			JSONObject newjsonNews = JSONObject.fromObject(newnewsinfo);
			logService.addLogInfo(request, response, "1", "过程行为支持系统", "2", "基础管理", "新闻发布管理", MyLogPojo.change, "新闻发布管理-修改", "成功", "1", "T_NEWS", oldjsonNews.toString(), newjsonNews.toString());
			
			if(count<1){
				this.formatData("修改新闻失败！");
			}else{
				this.formatData("成功修改了" + count + "条数据!");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			this.formatData("修改新闻失败！");
		}
		
		return SUCCESS;
	}
	
	
	public String deleteNews(){
		try {
			if (!isLogin()) {
				this.formatData("请先登录!");
				return SUCCESS;
			}
			String ids[] = request.getParameter("newsId").split(",");
			if (ids == null) {
				this.formatData("请选择数据!");
				return SUCCESS;
			}
			Map map = new HashMap();
			List l = new ArrayList();
			for (String id : ids) {
				l.add(id);
			}
			map.put("newsId", l);
			List<NewsPojo> delList = newsService.getNesInfoIds(l);
			int count = newsService.deleteNews(map);
			
			//记录数据日志
			JSONArray oldjsonNews = JSONArray.fromObject(delList);
			logService.addLogInfo(request, response, "1", "过程行为支持系统", "2", "基础管理", "新闻发布管理", MyLogPojo.change, "新闻发布管理-删除", "成功", "1", "T_NEWS", oldjsonNews.toString(), "");
			
			if(count<1){
				this.formatData("删除新闻记录失败！");
			}else{
				this.formatData("成功删除了"+count+"条数据！");
			}
 
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		return SUCCESS;
	
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	private NewsPojo getNewsById(String newsId){

		Map map = new HashMap();
		
		map.put("newsId", newsId);
		
		if (pagination == null) {
			pagination = new Pagination(1, 20);
		}
		
		newsService.getNewsInfo(pagination, map);

		List<NewsPojo> newslist = pagination.getList();
		if(newslist.size()>0){
			return newslist.get(0);
		}else{
			return null;
		}
	}
	
}
