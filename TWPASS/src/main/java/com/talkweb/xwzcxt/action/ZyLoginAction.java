package com.talkweb.xwzcxt.action;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.twdpe.core.log.Logger;
import com.talkweb.twdpe.core.log.LoggerFactory;
import com.talkweb.xwzcxt.service.ZyLoginService;

public class ZyLoginAction extends BusinessAction implements ServletRequestAware{
	private static final Logger logger = LoggerFactory.getLogger(ZyLoginAction.class);

	private static final long serialVersionUID = 4230618879695103387L;

	@Autowired
    private ZyLoginService zyLoginService;
	private String userId;
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	private HttpServletRequest request;
	
	public String zyUserLogin(){
		BufferedWriter fw = null;
		try{
			Map param =new HashMap();
			String zyid=null;
			zyid=(String)request.getSession().getAttribute("zyid");
			File file = new File("D://text.txt");
			fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), "UTF-8"));
			fw.append("中烟系统传过来的id="+zyid);
			fw.newLine();
			fw.append("如果等于后面没有跟值就是参数没有传,或者没调 到我们这边的default.jsp页面");
			fw.flush(); 
			param.put("zyid", zyid);
			param=zyLoginService.zyUserLogin(param);
			String userid=param.get("USERID").toString();
			Long id=null;
			if(userid!=null){
				id=Long.parseLong(userid);
			}
			String code=(String) param.get("USERCODE");
			
			String pos=param.get("POSITIONID").toString();
			Long  positionid=null;
			if(pos!=null){
				positionid= Long.parseLong(pos);
			}
			
			String org=param.get("ORGID").toString();
			Long  orgid=Long.parseLong(org);
			String name=(String)param.get("DISPLAYNAME");
			
			/*Long id=2000412L;
			String code="1745";
			Long  positionid= 1002814L;
			String name= "邓伶娜";*/
			
			
			param.clear();
			param.put("id", id);
			param.put("name", name);
			param.put("positionid", positionid);
			param.put("code", code);
			param.put("orgid", orgid);
			
			request.getSession().setAttribute("USERSESSION", param);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			e.printStackTrace();
			return SUCCESS;
		}finally{
			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return SUCCESS;
	}
	
	public String ssoLogin(){
		String userId = request.getParameter("userId");
		Map param =new HashMap();
		param.put("userId", userId);
		param=zyLoginService.zyUserLogin(param);
		String userid=param.get("USERID").toString();
		Long id=null;
		if(userid!=null){
			id=Long.parseLong(userid);
		}
		String code=(String) param.get("USERCODE");
		
		String pos=param.get("POSITIONID").toString();
		Long  positionid=null;
		if(pos!=null){
			positionid= Long.parseLong(pos);
		}
		
		String org=param.get("ORGID").toString();
		Long  orgid=Long.parseLong(org);
		String name=(String)param.get("DISPLAYNAME");
		
		/*Long id=2000412L;
		String code="1745";
		Long  positionid= 1002814L;
		String name= "邓伶娜";*/
		
		
		param.clear();
		param.put("id", id);
		param.put("name", name);
		param.put("positionid", positionid);
		param.put("code", code);
		param.put("orgid", orgid);
		
		request.getSession().setAttribute("USERSESSION", param);
		return "task";
		
	}

	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		this.request=arg0;
		
	}


	
}
