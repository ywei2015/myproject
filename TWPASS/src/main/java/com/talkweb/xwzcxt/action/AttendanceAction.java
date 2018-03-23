package com.talkweb.xwzcxt.action;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.NetworkInterface;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.twdpe.web.session.main.Session;
import com.talkweb.twdpe.web.session.util.SessionFactory;
import com.talkweb.xwzcxt.pojo.AttendancePojo;
import com.talkweb.xwzcxt.service.AttendanceService;
import com.talkweb.xwzcxt.vo.AttendanceVo;


public class AttendanceAction extends baseAction {
	@Autowired
	private AttendanceService attendanceservice;
 
	HttpServletRequest request = (HttpServletRequest) ActionContext
            .getContext().get(ServletActionContext.HTTP_REQUEST);
	
	HttpServletResponse response = (HttpServletResponse) ActionContext
			.getContext().get(ServletActionContext.HTTP_RESPONSE);
	
	public String getAttendanceData(){
		try{
			if(pagination==null){
				pagination=new Pagination(1,15);
			}
			Session session = SessionFactory.getInstance(request, response);
		    Map   user = (Map) session.getAttribute("USERSESSION");
			String d_userid="";
			if(user!=null) {
				 d_userid=user.get("id").toString();
				 if(d_userid.equals("1"))
					 d_userid="";
			 }
			List<AttendancePojo> list=new ArrayList<AttendancePojo>();
			HttpServletRequest request = ServletActionContext.getRequest();
			String map_ycj = request.getParameter("map")==null?"undefined":request.getParameter("map").toString();
			String map_yc = URLDecoder.decode(map_ycj,"UTF-8");
			String cExecUserid = request.getParameter("cExecUserid");
			if (cExecUserid != null && !cExecUserid.isEmpty()) {
				cExecUserid = cExecUserid.replaceAll("U-", "");
			} else {
				cExecUserid = null;
			}
			Pagination paginationj = new Pagination();
			if(!map_yc.equals("undefined")){
				 list=getAttendanceByArea(map_yc);//获得异常地图list
				 paginationj=new Pagination(1,20);
			}
			else{
			Map map = new HashMap();
			String time = request.getParameter("c_start_time")==null?"":request.getParameter("c_start_time");
			String endTime = request.getParameter("c_end_time")==null?"":request.getParameter("c_end_time");
			String orgid = request.getParameter("orgid")==null?"":request.getParameter("orgid");
			String positionId = request.getParameter("positionid")==null?"":request.getParameter("positionid");
			//String c_userid = request.getParameter("c_userid")==null?"":request.getParameter("c_userid");
			map.put("orgid", orgid);
			map.put("positionId", positionId);
			map.put("c_userid", d_userid);
			map.put("c_end_time", endTime);
			map.put("c_start_time", time);
			map.put("cExecUserid", cExecUserid);
		    list=attendanceservice.getAttendanceData(map, pagination);
		   paginationj=pagination;
			}
	for (int i = 0; i < list.size(); i++) {
		AttendancePojo  atp = list.get(i);
		Long userid= atp.getC_userid();
		String c_tag_ip=atp.getC_tag_ip()==null?"":atp.getC_tag_ip();
		String c_attend_date=atp.getC_attend_date()==null?"":atp.getC_attend_date();
		Map mapOne = new HashMap();
		mapOne.put("userid", userid);
		mapOne.put("c_tag_ip", c_tag_ip);
		mapOne.put("c_attend_date", c_attend_date);
		atp.setC_attend_good(getKaoqinYc(mapOne));
		String dp_name=attendanceservice.getdepName(mapOne);
		int  attend_times = attendanceservice.getAttendanceTimes(mapOne);
		atp.setC_attend_times(attend_times);
		atp.setC_dep_name(dp_name);
		
	}
		
		this.formatDatagirdData(list, paginationj);
		}catch(Exception e){
			e.printStackTrace();
			}
		return SUCCESS ;
	}
	
	
	public String  getAttendanceDetails(){
		try{
			if(pagination==null){
				pagination=new Pagination(1,15);
			}
			HttpServletRequest request = ServletActionContext.getRequest();
			Map map = new HashMap();
			String c_kqyc="";
			String c_userid = request.getParameter("c_userid")==null?"":request.getParameter("c_userid");
			String c_tag_ip = request.getParameter("c_tag_ip")==null?"":request.getParameter("c_tag_ip");
			String c_attend_date = request.getParameter("c_attend_date")==null?"":request.getParameter("c_attend_date");
			String key = URLDecoder.decode(URLDecoder.decode(c_tag_ip,"UTF-8"),"utf-8"); 
			map.put("userid", c_userid);
			map.put("c_tag_ip", key);
			map.put("c_attend_date", c_attend_date);
			//Pagination pagination=new Pagination(1,15);
			List<AttendancePojo> list=attendanceservice.getAttendanceDetails(map, pagination);
			for (int i = 0; i < list.size(); i++) {
				AttendancePojo  atp = list.get(i);
				Long userid= atp.getC_userid();
				Map mapOne = new HashMap();
				mapOne.put("userid", userid);
				mapOne.put("c_attend_date", atp.getC_attend_date());
				mapOne.put("c_tag_ip",atp.getC_tag_ip());
				String dp_name=attendanceservice.getdepName(mapOne);
				atp.setC_dep_name(dp_name);
				if("1".equals(atp.getC_attend_type()))
					c_kqyc=kqTime_isyc(atp)+isZcip(atp.getC_tag_ip());
				else if("2".equals(atp.getC_attend_type()))
				    c_kqyc=kqTime_isyc(atp)+mac_Isyc(atp);
				atp.setC_attend_good(c_kqyc);
			}
			this.formatDatagirdData(list, pagination);
		}catch(Exception e){
			e.printStackTrace();
			}
		return SUCCESS ;
	}
	
	public String exportAttendance() {

		try{
			HttpServletRequest request = ServletActionContext.getRequest();
			Map map = new HashMap();
			String time = request.getParameter("c_start_time")==null?"":request.getParameter("c_start_time");
			
			String endTime = request.getParameter("c_end_time")==null?"":request.getParameter("c_start_time");
			String orgid = request.getParameter("orgid")==null?"":request.getParameter("orgid");
			String positionId = request.getParameter("positionid")==null?"":request.getParameter("positionid");
			String c_userid = request.getParameter("c_userid")==null?"":request.getParameter("c_userid");
			map.put("orgid", orgid);
			map.put("positionId", positionId);
			map.put("c_userid", c_userid);
			map.put("c_end_time", endTime);
			map.put("c_start_time", time);
		
		List<AttendancePojo> list=attendanceservice.exportAttendance(map);
		List<AttendanceVo> listOne=new ArrayList<AttendanceVo> ();
		for (int i = 0; i < list.size(); i++) {
			AttendancePojo  atp = list.get(i);
			AttendanceVo  attendancevo=new AttendanceVo();
			Long userid= atp.getC_userid();
			String user_name=atp.getC_username();
			String att_date=atp.getC_attend_date();
			String att_ip=atp.getC_tag_ip();
			attendancevo.setC_tag_ip(att_ip);
			attendancevo.setC_username(user_name);
			attendancevo.setC_attend_date(att_date);
			Map mapOne = new HashMap();
			Date att_time=atp.getC_attend_time();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			String str=sdf.format(att_time);
			attendancevo.setC_attend_time(str);
			mapOne.put("userid", userid);
			String dp_name=attendanceservice.getdepName(mapOne);
			attendancevo.setC_dep_name(dp_name);
			listOne.add(attendancevo);
			
		}
			String[] titles = {"部门","姓名", "考勤日期", "IP地址","考勤时间"};
			String[] fields = {"c_dep_name","c_username","c_attend_date","c_tag_ip","c_attend_time"};
			String url = this.exportExcelData(titles, fields, "KaoQin",  listOne, "考勤导出");
			Map mapT = new HashMap();
			mapT.put("url", url);
			this.formatData(mapT);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	public String getAttenForMobile() {
		HttpServletRequest request = ServletActionContext.getRequest();
		Map params = new HashMap();
		try{
		params.put("userId", request.getParameter("userId"));
		String jdate=request.getParameter("c_date");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  //24小时制
		Date nowDate=new Date();
		String now=sdf.format(nowDate);
		if(jdate==null||jdate==""){
			jdate=now;
		}
		params.put("c_date", request.getParameter("c_date"));
		Pagination pagination = new Pagination(Integer.parseInt(request.getParameter("index")), Integer.parseInt(request.getParameter("page")));
		Pagination page = attendanceservice.getAttenForMobile(params, pagination);
		ArrayList<AttendancePojo> list=(ArrayList<AttendancePojo>) page.getList();
		
		for (int i = 0; i < list.size(); i++) {
			AttendancePojo  atp = list.get(i);
			String ip=atp.getC_tag_ip();
			String pb_yc=kqTime_isyc(atp);
			String ip_yc=isZcip(ip);
			String _yc=pb_yc+" "+ip_yc;
			atp.setC_attend_good(_yc);
		}
		this.formatDatagirdData(list, page);
	   }catch (Exception e) {
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	public String doKaoqin(){
		try{
		Map user = new HashMap();
		Map userj = new HashMap();
		Session session = SessionFactory.getInstance(request, response);
        userj = (Map) session.getAttribute("USERSESSION");
        Date date=new Date();
        DateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        String s = UUID.randomUUID().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datet=sdf.format(date);
        Date kao_time=sdf.parse(datet);
        String kaoq_date=format.format(date);
        String mac_ip=request.getParameter("mac_addr");
        String mymac_Ip=mac_ip.replace(":", "-");
        user.put("c_id", s);
        user.put("kao_date", kaoq_date);
        user.put("kao_time", datet);
        user.put("c_ip",mymac_Ip);
        user.put("id", userj.get("id"));
        user.put("name", userj.get("name"));
        user.put("code", userj.get("code"));
        attendanceservice.doKaoqin(user);
		}catch(Exception e){
			e.printStackTrace();
			}
		return SUCCESS;
	}
	 /**
	 * @param request
	 * @return
	 */
	public String getKaoqinYc(Map map){
		String mac_yc="";  //打卡地址是否异常
		String ip_yc="";   //打卡时间是否异常
	    Pagination pagination2 = new Pagination(1,999999999);
		try{
			List<AttendancePojo> list=attendanceservice.getAttendanceDetails(map, pagination2);
			String my_IP="";
			for (int i = 0; i < list.size(); i++) {
				AttendancePojo  atp = list.get(i);
				String time_yc=kqTime_isyc(atp);
				if("1".equals(atp.getC_attend_type())){
					String yd_Ip=atp.getC_tag_ip();
					ArrayList<String>ip_list=attendanceservice.getAddr(); 
				    my_IP=isZcip(yd_Ip);   //移动端打卡地址是否异常
				}else if("2".equals(atp.getC_attend_type())){
					 my_IP=mac_Isyc(atp);  //web打卡地址是否异常
				}
				
				if(my_IP!="")
					mac_yc=my_IP;
				
				if(time_yc!="")
					ip_yc=time_yc;
				}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return ip_yc+mac_yc;
	}
	//mac地址是否异常
	private String mac_Isyc(AttendancePojo atp) {
		// TODO Auto-generated method stub
		String mac=atp.getC_tag_ip();
		List <String>list=attendanceservice.getKaoMac();
		for (String kq_mac : list) {
			 if(mac.equals(kq_mac))
				 return "";
		}
		return " 打卡地址异常";
	}
//考勤时间异常
	public String kqTime_isyc(AttendancePojo atp){
		Map map = new HashMap();
		Long c_userid=atp.getC_userid();
		String c_attend_date=atp.getC_attend_date();
		map.put("c_userid", c_userid);
		map.put("c_attend_date", c_attend_date);
		HashMap mapj=attendanceservice.getKaoPB(map);
		Date c_attend_time=atp.getC_attend_time();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(c_attend_time);
		int hours=calendar.get(Calendar.HOUR_OF_DAY);
		//System.out.println(hours);
		if(mapj!=null){
			Date starttime=(Date)mapj.get("C_START_TIME");
			Date endtime=(Date)mapj.get("C_END_TIME");
		  if(c_attend_time.getTime()>=starttime.getTime()&c_attend_time.getTime()<=endtime.getTime())
			 return "";
		  else if (hours>=23){
				String c_attend_datej=atp.getC_attend_date();
				SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
				try {
					Date birthday = sdf.parse(c_attend_datej);
					Date c_attend_timej= new Date(birthday.getTime() +1 * 24 * 60 * 60 * 1000);
					String c_attend_timejj=sdf.format(c_attend_timej);
					map.put("c_attend_date", c_attend_timejj);
					HashMap mapjj=attendanceservice.getKaoPB(map);
					if(mapjj==null) 
						return "";
					else{
						String paib_ytpe=(String)mapjj.get("C_SHIFT_NAME");
						String pb=paib_ytpe.substring(paib_ytpe.length()-2, paib_ytpe.length());
						if(pb.equals("早班")){
						return "";
					  }else return "打卡时间异常";
					}
					
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}	
		  else return "打卡时间异常";
		}
		
		return "";
	}
	
	//移动端考勤ip是否正常
	public String isZcip(String ip){
		ArrayList<String>list=attendanceservice.getAddr();  //移动端匹配iP地址
		for (String list1 : list) {
			String ip1=list1.substring(0,list1.lastIndexOf("."));
			String ip_c=ip.substring(0,ip.lastIndexOf("."));
			if(ip1.equals(ip_c)){
				return "";
			}else continue;
		}
		return "打卡地址异常";
	}
	public boolean mzkaoQinYc(AttendancePojo atp){
		String c_kqyc="";
		if("1".equals(atp.getC_attend_type()))
			c_kqyc=kqTime_isyc(atp)+isZcip(atp.getC_tag_ip());
		else if("2".equals(atp.getC_attend_type()))
		    c_kqyc=kqTime_isyc(atp)+mac_Isyc(atp);
		if(!c_kqyc.isEmpty()) 
			return false;
		return true;
	}
	
	public List<AttendancePojo> getAttendanceByArea(String area){
		 String date=new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	     Map map=new HashMap<String, String>();
	     map.put("date", date);
	     List<AttendancePojo> list=attendanceservice.getAttendanceForMapGroup(map);
		 List<AttendancePojo> listData=new ArrayList<AttendancePojo>();
		 for(int i=0;i<list.size();i++){
			 if(area.trim().equals(list.get(i).getCol7().trim())){
				 listData.add(list.get(i));
			 }
		 }
		 //如果mac地址跟考勤日期都为null,则赋值为""
		 for(int i=0;i<listData.size();i++){
			 AttendancePojo attendance=listData.get(i);
			 String macIp=attendance.getC_tag_ip();
			 attendance.setC_tag_ip(macIp==null?"":macIp);
			 String a=attendance.getC_attend_date();
			 attendance.setC_attend_date(a==null?"":a);
			 //暂时定为不显示未打卡的异常
			 if(macIp==null||a==null){
				 listData.remove(i);
				 i--;
			 }
		 }
		 return  listData;
	}
	

	//为考勤地图得到考勤数据
	  public String getAttendanceForMap(){
		  try{
		  String date=new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	      Map map=new HashMap<String, String>();
	      map.put("date", date);
	      List<Map> list=attendanceservice.getAttendanceForMap(map);
	      List<Map> json=new ArrayList<Map>();
	    
	      for(int i=0;i<list.size();i++){
	        Map jsonMap=new HashMap();
	        String listAreaName=(String)list.get(i).get("COL7");
	        jsonMap.put("areaName",listAreaName);
	        AttendancePojo attendance;
	        try{
	         attendance=(AttendancePojo)list.get(i);
	        }catch(Exception e){
	        	//异常说明没打卡
	        	 jsonMap.put("error", 1);
		         jsonMap.put("normal", 0);
		         dealWithMap(json,jsonMap);
		         continue;
	        }
	        
	        //1.打卡时间异常 2. mac地址异常 移动端ip异常
	        boolean flag=mzkaoQinYc(attendance);
	        if(!flag){
	          //异常false
	          jsonMap.put("error", 1);
	          jsonMap.put("normal", 0);
	        }else{
	          //正常true
	          jsonMap.put("error", 0);
	          jsonMap.put("normal", 1);
	        }
	        dealWithMap(json,jsonMap);
	      }
	      this.formatData(json);
	    }catch(Exception e){
	      e.printStackTrace();
	    }
	    return SUCCESS;
	  }
	  
	  private void dealWithMap(List<Map> json,Map jsonMap){
		  if(json.size()==0){
	          json.add(jsonMap);
	        }else{
		        boolean areaNameFlag=false;
		        for(int j=0;j<json.size();j++){
		          Map a=json.get(j);
		          String jsonMapName=(String)jsonMap.get("areaName");
		          if(a.get("areaName").equals(jsonMapName)){
		            int error=(Integer)a.get("error");
		            int normal=(Integer)a.get("normal");
		            a.put("error",error+(Integer)jsonMap.get("error"));
		            a.put("normal",normal+(Integer)jsonMap.get("normal"));
		            areaNameFlag=true;
		            break;
		          }
		        }
		        if(!areaNameFlag){
		          json.add(jsonMap);
		        }
	        }
	  }
}