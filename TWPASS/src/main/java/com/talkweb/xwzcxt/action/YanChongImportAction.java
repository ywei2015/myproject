package com.talkweb.xwzcxt.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.twdpe.core.log.Logger;
import com.talkweb.twdpe.core.log.LoggerFactory;
import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.twdpe.web.commons.StaticUploadInfo;
import com.talkweb.twdpe.web.session.main.Session;
import com.talkweb.twdpe.web.session.util.SessionFactory;
import com.talkweb.xwzcxt.pojo.YanChongPojo;
import com.talkweb.xwzcxt.service.YanChongService;
import com.talkweb.xwzcxt.service.impl.YanChongServiceImpl;
import com.talkweb.xwzcxt.util.FileOperate;

public class YanChongImportAction extends baseAction {
	@Autowired
	private YanChongService  yanchongservice;
	private OutYcexcel outycexcel=new OutYcexcel();
	private static final Logger logger = LoggerFactory.getLogger(YanChongImportAction.class);
	//烟虫excel数据导入
	public String importYanCNode(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		Map mp = new HashMap();
		Session session = SessionFactory.getInstance(request, response);
		try {
		String filePath=request.getParameter("filePath");
		String path = "";//request.getRealPath("");
		String fileName ="xwzcxt"+ filePath.substring(filePath.lastIndexOf(StaticUploadInfo.upload_system_url) + StaticUploadInfo.upload_system_url.length());
		//String fileName = filePath.substring(filePath.lastIndexOf(StaticUploadInfo.upload_system_url) + StaticUploadInfo.upload_system_url.length());
		path = request.getSession().getServletContext().getRealPath(fileName);
		YanChongPojo yancPojo=new YanChongPojo();
		String userid = "";
		Map user = (Map) session.getAttribute("USERSESSION");
		if (user != null) {
			userid = user.get("id").toString();
			if(userid.equals("1")) 
				userid=""; 
		}
		List<YanChongPojo> YanChongList = null;
		String errorMessage = "<table border=1 style='BORDER-COLLAPSE: collapse' borderColor='#000000' cellPadding=1><tr><td>行</td><td>列</td><td>校验异常信息</td></tr>";
		int error=0 ;//记录失败条数
		int successes = 0;
		int failures = 0;
		if (filePath!= null) {
			try {
				Map map=OutYcexcel.getYcImportDate(path);
				List<YanChongPojo> yanchongList = (List<YanChongPojo>) map.get("yanchongList");
				for (int i = 0; i < yanchongList.size(); i++) {
					YanChongPojo yanchong=yanchongList.get(i);
					if("1".equals(yanchong.getIs_error())){
						error++;
						continue;
						}
					String s = UUID.randomUUID().toString();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd HH:mm:ss");  
		        	long start = System.currentTimeMillis(); 
		        	String nowtime=sdf.format(start);
		        	String nowday=nowtime.substring(nowtime.indexOf("月")+1, nowtime.indexOf(" "));
					String zhounumber=""+(Integer.parseInt(nowday)%7>0?Integer.parseInt(nowday)/7+1:Integer.parseInt(nowday)/7);
					String time="第"+zhounumber+"周";
					yanchong.setC_id(s);
					yanchong.setC_userid(userid);
					yanchong.setC_times(time);
					yanchongservice.insertYcImportDate(yanchong);
					successes++;
				}
			} catch (Exception e) {
				e.printStackTrace();
				delFile(path);
				return SUCCESS;
			}
		}
		String okMessage = "导入完成<br/>成功：" + successes + "条<br/>失败：" + error + "条";
		if(error==0)
			okMessage+="";
		else 
			okMessage += "<br/>注：失败原因——未填入正确数字或编号";
//		if(error!=0)
	    this.setOkMessage(okMessage);
	    delFile(path);
	    mp.put("msg", "1");
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	//烟虫模板导出
	public String outYanCMb(){
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			String area_Id=request.getParameter("cAreaidj");
			StringBuffer sb = new StringBuffer();
			String[] area_Idj=area_Id.split(",");
			for (int i = 0; i < area_Idj.length; i++) {
				String s = area_Idj[i];
				s = "'"+s+"'";
				sb.append(s);
				if(i!=area_Idj.length-1){
				sb.append(",");
				}
			}
			HashMap map = new HashMap();
			map.put("c_area_id", sb.toString());
			List<String> areaIdlist = new ArrayList<String>(Arrays.asList(area_Idj));
			
			HashMap mapj = new HashMap(); 
			mapj.put("c_area_id", areaIdlist);
			//mapj.put("c_area_name",namelist);
			outycexcel.outYCExcel(mapj);
			this.formatData("success");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			this.formatData("error");
		}
		
		return SUCCESS;
	}
  //烟虫记录删除
	public String deleteImportData(){
		try{
			HttpServletRequest request = ServletActionContext.getRequest();
			String ids=request.getParameter("ids");
			String[] s=ids.split(",");
			yanchongservice.deleteImportData(s);
			this.setOkMessage("成功删除");
		}catch (Exception e) {
			e.printStackTrace();
		}	
		
		return SUCCESS;
	}
	
	//烟虫记录查询
 	public String getYImportData(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		Session session = SessionFactory.getInstance(request, response);
		try {
			if(pagination==null){
				pagination=new Pagination(1,15);
			}
			String starttime = request.getParameter("c_start_time")==null?"":request.getParameter("c_start_time");
			String endTime = request.getParameter("c_end_time")==null?"":request.getParameter("c_end_time");
		    String c_userid=request.getParameter("cExecUserid")==null?"":request.getParameter("cExecUserid");
		    String c_areaid=request.getParameter("cAreaId")==null?"":request.getParameter("cAreaId");
			Map user = (Map) session.getAttribute("USERSESSION");
			String userid="";
			/*if (user != null) {
				userid = user.get("id").toString();
			}*/
			Date date=new Date();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
			String c_today=sdf.format(date);
			HashMap map=new HashMap();
			map.put("starttime", starttime);
			map.put("endTime", endTime);
			map.put("userid", c_userid);
			map.put("c_today", c_today);
			map.put("c_areaid", c_areaid);
			List<YanChongPojo> list=yanchongservice.getYImportData(map,pagination);
			this.formatDatagirdData(list, pagination);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		
		return SUCCESS;
	}
	private void delFile(String path) {
		try {
			FileOperate fo = new FileOperate();
			fo.delFile(path);
		} catch (Exception e) {
			
			e.printStackTrace();
			// TODO: handle exception
		}
		
	}

	
}
