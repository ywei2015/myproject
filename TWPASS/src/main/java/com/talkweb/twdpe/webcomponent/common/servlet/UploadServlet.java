package com.talkweb.twdpe.webcomponent.common.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.talkweb.twdpe.core.config.ConfigManager;
import com.talkweb.twdpe.core.util.json.JsonArray;
import com.talkweb.twdpe.core.util.json.JsonObject;
import com.talkweb.twdpe.web.upload.FileUploadService;
import com.talkweb.twdpe.web.upload.impl.File;
import com.talkweb.twdpe.web.upload.impl.FileUploadServiceImpl;

/**
 * 内容摘要:<br>
 * 文件上传组件后台服务.<br>
 * @author chengwei
 * @version 1.3.0
 * @date 2012 11:32:26 AM
 * 
 * @修改历史<br> 修改日期 修改人员 版本 修改内容<br>
 * -----------------------------------------<br>
 * 
 * @版权 版权所有(C)2012<br>
 * @公司 拓维信息系统股份有限公司<br>
 */
public class UploadServlet extends HttpServlet {

	private static final long serialVersionUID = 7618658514199005769L;

	private FileUploadService fileUploadService;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		response.setContentType("text/plain");
		response.setHeader("Content-Type", "text/html;charset=UTF-8");
		response.addHeader("Access-Control-Allow-Origin", "*");
		request.setCharacterEncoding("utf-8");
		String type = request.getParameter("type");
		String path = null;
		if (type != null) {
			path = ConfigManager.getInstance().getAppConfig().get("upload.system").get(type);
		}
		if (path == null) {
			path = ConfigManager.getInstance().getAppConfig().get("upload.system").get("path");
		}
		String maxSize = request.getParameter("maxSize");
		fileUploadService = new FileUploadServiceImpl();
		fileUploadService.setStoragePath(path);
		fileUploadService.setServletContext(getServletContext());
		if (maxSize != null) {
			fileUploadService.setMaxSize(Long.parseLong(maxSize));
		}
		JsonArray array = new JsonArray();
		JsonObject json = null;
		try {
			for (File file : fileUploadService.getFilesInLocal(request)) {
				json = new JsonObject();
				json.put("name", file.getName());
				json.put("url", file.toString());
				json.put("size", file.length());
				json.put("type", file.getContentType());
				json.put("missing", file.isMissing());
				json.put("allowed", file.isAllowed());
				json.put("path", file.getRelativePath());
				array.add(json);
			}
		} catch (Exception e) {
			json = new JsonObject();
			json.put("missing", true);
			array.add(json);
		}
		PrintWriter out = response.getWriter();
		out.println(array.toString());
		out.flush();
		out.close();
	}

}
