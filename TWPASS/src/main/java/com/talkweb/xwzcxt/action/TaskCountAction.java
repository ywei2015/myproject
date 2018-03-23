package com.talkweb.xwzcxt.action;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.talkweb.twdpe.web.session.main.Session;
import com.talkweb.twdpe.web.session.util.SessionFactory;
import com.talkweb.twdpe.webcomponent.common.action.BusinessAction;
import com.talkweb.xwzcxt.pojo.Pie;
import com.talkweb.xwzcxt.pojo.TaskCondition;
import com.talkweb.xwzcxt.pojo.TaskCount;
import com.talkweb.xwzcxt.service.CountTaskService;

/**
 * TODO(描述这个类的作用)
 * 
 * @author: 2013-12-30，FLN，（描述修改内容）
 */
public class TaskCountAction extends BusinessAction {
	private static final Logger logger = LoggerFactory
			.getLogger(TaskCountAction.class);

	@Autowired
	private CountTaskService countTaskService;

	private TaskCondition taskcondition;

	private void printJson(Object taskCount) {

		JSONObject obj = JSONObject.fromObject(taskCount);
		{
			try {
				HttpServletResponse response = (HttpServletResponse) ActionContext
						.getContext()
						.get("com.opensymphony.xwork2.dispatcher.HttpServletResponse");
				response.setContentType("text/plain");
				response.setHeader("Content-Type", "text/html;charset=UTF-8");
				PrintWriter out = response.getWriter();
				response.addHeader("Access-Control-Allow-Origin", "*");
				String jsonString = obj.toString();
				String subString = jsonString.substring(jsonString
						.indexOf("\"series\":"));
				String replaceString = subString.replace("\"", "");
				replaceString = replaceString.replace("series", "\"series\"");
				replaceString = replaceString.replace("data", "\"data\"");
				replaceString = replaceString.replace("name", "\"name\"");
				replaceString = replaceString.replace("刚生成", "\"刚生成\"");
				replaceString = replaceString.replace("刚确认", "\"刚确认\"");
				replaceString = replaceString.replace("待执行", "\"待执行\"");
				replaceString = replaceString.replace("已完成", "\"已完成\"");
				jsonString = jsonString.replace(subString, replaceString);
				System.out.println(jsonString);
				out.print(jsonString);
				out.flush();
				out.close();
			} catch (java.io.IOException e) {
				logger.error(e.getMessage(), e);
				e.printStackTrace();
			}

		}

	}

	public String countTask() {
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpServletResponse response = ServletActionContext.getResponse();
			Session session = SessionFactory.getInstance(request, response);
			Map user = (Map) session.getAttribute("USERSESSION");
			if (user != null){
				String userid = user.get("id").toString();
				taskcondition.setUserid(userid);
				if ("1".equals(request.getParameter("type"))) {
	
					TaskCount taskCount = countTaskService.countTask(taskcondition);
					this.printJson(taskCount);
				} else {
					List<Pie> list = countTaskService
							.countPie(taskcondition);
					this.printPieJson(list);
				}
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		return SUCCESS;
	}

	private void printPieJson(List<Pie> list) {
		try {
			HttpServletResponse response = (HttpServletResponse) ActionContext
					.getContext()
					.get("com.opensymphony.xwork2.dispatcher.HttpServletResponse");
			response.setContentType("text/plain");
			response.setHeader("Content-Type", "text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			response.addHeader("Access-Control-Allow-Origin", "*");
			JSONArray jsonArray = JSONArray.fromObject(list);  
			String json = jsonArray.toString();
			System.out.println(json);
			out.print(json);
			out.flush();
			out.close();
		} catch (java.io.IOException e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}

	}

	public CountTaskService getCountTaskService() {
		return countTaskService;
	}

	public void setCountTaskService(CountTaskService countTaskService) {
		this.countTaskService = countTaskService;
	}

	public TaskCondition getTaskcondition() {
		return taskcondition;
	}

	public void setTaskcondition(TaskCondition taskcondition) {
		this.taskcondition = taskcondition;
	}

}
