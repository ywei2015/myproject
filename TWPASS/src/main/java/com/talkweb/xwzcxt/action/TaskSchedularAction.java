package com.talkweb.xwzcxt.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.talkweb.twdpe.core.util.StringUtil;
import com.talkweb.twdpe.core.util.json.JsonObject;
import com.talkweb.twdpe.core.util.ExcelUtil;
import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.twdpe.web.commons.StaticUploadInfo;
import com.talkweb.twdpe.web.session.main.Session;
import com.talkweb.twdpe.web.session.util.SessionFactory;
import com.talkweb.xwzcxt.pojo.MyLogPojo;
import com.talkweb.xwzcxt.pojo.TSchedulingPojo;
import com.talkweb.xwzcxt.service.MyLogService;
import com.talkweb.xwzcxt.service.TaskSchedularService;
import com.talkweb.xwzcxt.util.ExcelReadUtil;
import com.talkweb.xwzcxt.util.FileOperate;
import com.talkweb.xwzcxt.vo.PositionTreeVo;
import com.talkweb.xwzcxt.vo.TaskSchedularVo;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Transactional
public class TaskSchedularAction extends BusinessAction {

	private static final long serialVersionUID = 6005205183746716870L;

	private static final Logger logger = LoggerFactory
			.getLogger(TaskSchedularAction.class);

	@Autowired
	private TaskSchedularService taskSchedularService;

	@Autowired
	private MyLogService logService;
	
	private HttpServletRequest request = ServletActionContext.getRequest();
	private HttpServletResponse response = ServletActionContext.getResponse();

	private boolean isLogin(){
		Session session=SessionFactory.getInstance(request, response);
		Map user =(Map)session.getAttribute("USERSESSION");
		if(user==null){
			return false;
		}
		return true;
	}
	public String getPersonSchedular() {
		try {
		   if(!isLogin()){
			   this.formatData("请先登录！");
			   return SUCCESS;
		   }
           String year=request.getParameter("year");
           String month=request.getParameter("month");
           String userid=request.getParameter("member");
           if(userid==null||userid.equals("")){
        	   List list=new ArrayList();
        	   getDepartmentSchedular();    //获得部门所有成员的排班情况
       	       return SUCCESS;
           }
           Map map=getPersonSchedular(year,month,userid);
           
           List l = new ArrayList();
           if(map!=null&&map.size()>0){
        	   l.add(map);
        	   if(pagination==null){
        		   pagination=new Pagination(1,20);
        	   }
        	   this.formatDatagirdData(l, pagination);
           }else{
        	   this.formatData(l);
           }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	
	private Map getPersonSchedular(String year,String month,String userid){
	    List listSchedular=null;
	    Map map=null;
		try{
		Map params  =new HashMap();
        params.put("year", year);
        params.put("month", month);
        params.put("cUserId", userid);
        listSchedular=taskSchedularService.getPersonSchedular(params);
        map =new HashMap();
        if(listSchedular.size()>0){
        	map.put("c_userid", userid);
        	if(month.trim().length()<2){
        		map.put("year_month", year+"-0"+month);
        	}else{        	
        		map.put("year_month", year+"-"+month);
        	}
        	TaskSchedularVo vo=(TaskSchedularVo)listSchedular.get(0);
           
     	   map.put("name",((TaskSchedularVo)vo).getcUserName());
     	   map.put("c_org_id",vo.getC_org_id());
     	   for(Object ob:listSchedular){
     		   int day=((TaskSchedularVo)ob).getcDate();
     		   map.put("day"+day, ((TaskSchedularVo)ob).getcShiftName());
     	   }
        }
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return map;
	}
       
	private String getDepartmentSchedular(){
		try{
			 if(!isLogin()){
				   this.formatData("请先登录！");
				   return SUCCESS;
			 }
			 String year=request.getParameter("year");
	         String month=request.getParameter("month");
			 String orgid=request.getParameter("department");
			 HashMap params =new HashMap();
	         params.put("orgid", orgid);
	         if(pagination==null){
	      		   pagination=new Pagination(1,20);
	      	   }
	         params.put("pagination", pagination);
	         List list=taskSchedularService.getUserList(params);
	         List departCalendar=new ArrayList();
	         if(list!=null){
	        	 for(Object ob:list){
	        		 String userid=((TaskSchedularVo)ob).getUserid();
	        		 Map map=getPersonSchedular(year,month,userid);
	        		 if(map!=null &&map.size()>0){
	        			 departCalendar.add(map);
	        		 }
	        	 }
	         }
	         if(departCalendar.size()==0){
	        	 pagination.setCount(0);
	        	 pagination.setAllPage(1);
	         }
      	   this.formatDatagirdData(departCalendar, pagination);
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	public String getUserList() {
		try {
		  if(!isLogin()){
				   this.formatData("请先登录！");
				   return SUCCESS;
		  }
          String orgid=request.getParameter("department");
          HashMap params =new HashMap();
          params.put("orgid", orgid);
          List list=taskSchedularService.getUserList(params);
          if(list!=null){
        	  List users=new LinkedList();
        	  for(Object ob:list){
        		  //System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
        		  Map map=new HashMap();
        		  TaskSchedularVo ts=(TaskSchedularVo)ob;
        		  map.put("value",ts.getUserid());
        		  map.put("text", ts.getcUserName());
        		  users.add(map);
        		  //System.out.println(map+"^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
        	  }
        	 // System.out.println(a.toString()+"****************************");        	 
        	this.formatData(users);
          }else{
        	  this.formatData("");
          }
          
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String getOrganization() {
		try {
			if(!isLogin()){
				   this.formatData("请先登录！");
				   return SUCCESS;
			 }
          List list= taskSchedularService.getOrganization( );
          this.formatData(list);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String getAllShitTypes(){
		try{
			List list=taskSchedularService.getShiftName();
			List shiftList=new ArrayList();
			if(list!=null){
				for(Object ob:list){
					TSchedulingPojo pojo=(TSchedulingPojo)ob;
					HashMap map=new HashMap();
					map.put("text", pojo.getC_shift_name());
					map.put("value", pojo.getC_shift_id());
					shiftList.add(map);
				}
			}
			this.formatData(shiftList);
			}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			return SUCCESS;
		}
		return SUCCESS;
	}
	
	
	public String getOneDayShift(){
		try{
			HashMap params=new HashMap();
			final String c_date=request.getParameter("c_date");
			final String c_userid=request.getParameter("c_userid");
			params.put("c_date", c_date);
			params.put("c_userid", c_userid);
			Map map=taskSchedularService.getOneDayShift(params);
			
			if(map!=null){
            String  c_remark=(map.get("C_REMARK")==null?"":map.get("C_REMARK").toString());
            String  c_shift_id=map.get("C_SHIFT_ID").toString();
			map.clear();
			map.put("c_shift_id", c_shift_id);
			map.put("c_remark", c_remark);
			}else{
				map.put("c_shift_id", "");
				map.put("c_remark", "");
			}
			
			this.formatData(map);
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage(),e);
		}
		return SUCCESS;
	}
	
	
	public String fileImport(){
		String path = "";
		int count=0,k=0;
		try{
			 Session session = SessionFactory.getInstance(request, response);
			 Map user =(Map)session.getAttribute("USERSESSION");
			 if(user==null){
				 this.formatData("请先登录!");
				 return SUCCESS;
			 }
			 
		     Map map =new HashMap();
		     String filePath = request.getParameter("filePath");
		     if(filePath==null||filePath.trim().equals("")){
		    	 this.setErrorMessage("没有找到文件");
		    	 return SUCCESS;
		     }
		   //  map.put("filePath", filePath);
		     
		     String fileName = filePath.substring(filePath.lastIndexOf(StaticUploadInfo.upload_system_url)+StaticUploadInfo.upload_system_url.length());
		     path = request.getSession().getServletContext().getRealPath(fileName);
		     
		     String userid = "";
		     userid = user.get("id").toString();
		     
		     List<TSchedulingPojo> schedularList = null;
		        String errMsgHead = "<table border=1   style='BORDER-COLLAPSE: collapse' borderColor='#000000' cellPadding=1><tr><td>行</td><td>列</td><td>校验异常信息</td></tr>";
		 	   
		        List<String[]> list = null;
		        if(filePath != null) {
		            try {
		            	list=ExcelReadUtil.readExcel(path);
		            } catch(ArrayIndexOutOfBoundsException e) {
		            	e.printStackTrace();
		            	delFile(path);
		            	this.setOkMessage("模板格式有误，请下载系统提供的模版！");
			            return SUCCESS;
		            } catch (Exception e) {
		            	e.printStackTrace();
		                ServletActionContext.getRequest().setAttribute("throw", e);
		                this.setOkMessage("模板格式有误，请下载系统提供的模版！");
		                //删除上传的文件
		                delFile(path);
		                return SUCCESS;
		            }
		        }
		        
		        if(list!=null&&!isStandardFile(list)){
		                this.setOkMessage("模板格式有误，请下载系统提供的模版！");
		                //删除上传的文件
		                delFile(path);
		                return SUCCESS;
		        }
		        
		        if(list!=null){
		        	map=getSchedularFromFile(list);
		        }
		     
		        List<TSchedulingPojo>Schedularlist=(List<TSchedulingPojo>)map.get("schedularList");
		        //errNum=(Integer)map.get("errNum");
		       String errMsg=(String)map.get("errMsg");
		        if(errMsg==null||!"".equals(errMsg)){
		        	this.setOkMessage(errMsgHead+errMsg+"</table><br/>");
		        	delFile(path);
		        	return SUCCESS;
		        }
		        
		        //errMsg="<table>";
		        List<TSchedulingPojo> addObjList = new ArrayList<TSchedulingPojo>();
		        HashMap params=new HashMap();
		        errMsgHead = "<table border=1   style='BORDER-COLLAPSE: collapse' borderColor='#000000' cellPadding=1><tr><td>序号</td><td>校验异常信息</td></tr>";
		        for(TSchedulingPojo pojo:Schedularlist){
		        	  params.put("c_userid", pojo.getC_userid());
		        	  params.put("c_date", pojo.getC_date());
		        	  Map resultMap= taskSchedularService.getOneDayShift(params);
		        	  if(resultMap!=null){
		        		  k++;
		        		  errMsg+= "<tr><td>"+k+"</td><td>第"+(count+2)+"行数据导入失败，数据库里面已有当天的排班数据！</td></tr>";
		        	  }
		        	  else{
		        		  if(taskSchedularService.addShift(pojo)<1){
			        		 k++;
			        		 errMsg+= "<tr>"+k+"<td></td><td>第"+count+"行数据导入失败，请仔细检查格式是否正确</td></tr>";
		        		  }else{
		        			  TSchedulingPojo addObj = taskSchedularService.getShiftByUserDate(params);
		        			  addObjList.add(addObj);
		        		  }
		        	  }
		        	  count++;
		        }
		        errMsg=errMsgHead+errMsg+"</table><br/>";
		        
		        if(k>0){
		        	this.setOkMessage("本次成功导入了"+(count-k)+"条数据!<br/>共出现了"+k+"条错误数据<br/>"+errMsg);
		        }else{
		        	this.setOkMessage("成功导入了"+count+"条数据!");
		        }
		        
		        //添加数据日志
		        if(addObjList.size()>0){
		        	JSONArray newLogJson = JSONArray.fromObject(addObjList);
			        String newlogString = newLogJson.toString();
			        logService.addLogInfo(request, response, "1", "过程行为支持系统", "2", "基础管理", "排班管理", MyLogPojo.add, "排班管理-导入", "成功", "1", "T_SCHEDULING", "", newlogString);
		        }
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			this.setOkMessage("在导入第"+(count+2)+"条排班数据的时候失败!");
			delFile(path);
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
		
		
   private void delFile(String path){
		    FileOperate fo = new FileOperate();
	        fo.delFile(path);
   }
   
   private boolean isStandardFile(List<String[]> list){

       String[] attr = list.get(0);
       final  String [] title={"排班日期","执行人岗位牌号","执行人姓名","班次","排班人岗位牌号","排班人姓名","备注"};
       for(int i=0;i<attr.length;i++){
    	 boolean isSame=attr[i].replace("\n", "").trim().equals(title[i]);
    	 if(!isSame){
    		 return false;
    	 }
       }
	   return true;
   }

   
   private Map getSchedularFromFile(List<String[]> list){
	   List<TSchedulingPojo> SchedularList=new ArrayList<TSchedulingPojo>();
	   Map map = new HashMap();
       String errMsg = "";
       //int errNum = 0;
     
       //errMsg = "<table border=1 style='BORDER-COLLAPSE: collapse' borderColor='#000000' cellPadding=1><tr><td>行</td><td>列</td><td>校验异常信息</td></tr>";
	   final String dateformat="(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})"+
                "/((([13578]|1[02])/([1-9]|[12][0-9]|3[01]))|(([469]|11)"+
                "/([1-9]|[12][0-9]|30))|(2/([1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))/2/29)";
	   final String dateformat2="(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})"+
               "-((([13578]|1[02])-([1-9]|[12][0-9]|3[01]))|(([469]|11)"+
               "-([1-9]|[12][0-9]|30))|(2-([1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-2-29)";
	   
	   final String dateformat3="(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})"+
               "/(((0[13578]|1[02])/(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)"+
               "/(0[1-9]|[12][0-9]|30))|(02/(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))/02/29)";
	   
	   final String dateformat4="(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})"+
               "-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)"+
               "-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)";
	   
       Pattern pattern=Pattern.compile(dateformat);
       Pattern pattern2=Pattern.compile(dateformat2);
       Pattern pattern3=Pattern.compile(dateformat3);
       Pattern pattern4=Pattern.compile(dateformat4);
       HashMap param=new HashMap();
       HashMap<String,TSchedulingPojo> userMap=new HashMap<String,TSchedulingPojo>();    //存储执行人、排班人信息，可以不必每次都从数据库中查询，存储数据量超过50就清空
       HashMap<Long,String> shiftMap=new HashMap<Long,String>();   //存储所有排班种类信息
       List shiftList=taskSchedularService.getShiftName();
       for(int i=0;shiftList!=null&&i<shiftList.size();i++){
    	   TSchedulingPojo pojo=(TSchedulingPojo)(shiftList.get(i));
    	   shiftMap.put(pojo.getC_shift_id(),pojo.getC_shift_fullname());
       }
       ArrayList<TSchedulingPojo> Schedularlist=new ArrayList();
       for(int i=1;i<list.size();i++){   //从第二行开始解析
           boolean isRight=true;
           String[] s = list.get(i);
           if(s!=null && s.length==7)
           {
        	   TSchedulingPojo currentSchedular=new TSchedulingPojo();
        	   if(s[0]==null){
        		   isRight=false;
        		  // errNum++;
        		   errMsg+="<tr><td>"+(i+1)+"</td><td>1</td><td>日期不能为空(正确格式示例：2014/1/1 、 2014/11/11)</td></tr>";
        	   }
        	   else if( !pattern3.matcher(s[0].trim()).matches()&&!pattern4.matcher(s[0].trim()).matches()&&!pattern.matcher(s[0].trim()).matches()&&!pattern2.matcher(s[0].trim()).matches()){
        		   errMsg+="<tr><td>"+(i+1)+"</td><td>1</td><td>日期格式错误(正确格式示例：2014/1/1 、 2014/11/11)</td></tr>";
        		   isRight=false;
        		   //errNum++;
        	   }else{
        		   s[0]=s[0].replace("-", "/");
        		   currentSchedular.setC_date(s[0].trim());
        		   String c_ym_id;
        		   String [] ym=s[0].split("/");
        		   if(ym[1].length()==1){
        			   ym[1]="0"+ym[1];
        		   }
        		   c_ym_id=ym[0]+ym[1];
        		   currentSchedular.setC_ym_id(Integer.parseInt(c_ym_id));
        	   }

        	   //用户编码验证和根据用户编码查找用户信息
        	   int index=-1;
        	   if(s[1]!=null){        		   
        		   index=s[1].lastIndexOf(".");
        	   }
        	   if(index!=-1){
        		   s[1]=s[1].substring(0,index);
        	   }
        	   if(s[1]==null){
        		   isRight=false;
        		   //errNum++;
        		   errMsg+="<tr><td>"+(i+1)+"</td><td>2</td><td>执行人岗位牌号不能为空</td></tr>";
        	   }
        	   else if(!StringUtil.isNumber(s[1].trim())){  
        		   isRight=false;
        		   //errNum++;
            	   errMsg+="<tr><td>"+(i+1)+"</td><td>2</td><td>执行人岗位牌号是数字</td></tr>";
               }else{
            	   
            	   TSchedulingPojo  pojo=userMap.get(s[1].trim());
            	   if(pojo==null){
            		   param.put("usercode", s[1].trim());
            		   pojo=taskSchedularService.getShiftUserInfo(param); 
            		   if(pojo!=null){
            			   if(userMap.size()>50){
            				   userMap.clear();
            			   }
            			   userMap.put(pojo.getC_usercode(), pojo);
            		   }
            	   }
            	   if(pojo==null){
            		   isRight=false;
            		  // errNum++;
            		   errMsg+="<tr><td>"+(i+1)+"</td><td>2</td><td>该执行人的岗位牌号不存在</td></tr>";
            	   }else{
            		   currentSchedular.setC_userid(pojo.getC_userid());
            		   currentSchedular.setC_username(pojo.getC_username());
            		   currentSchedular.setC_org_id(pojo.getC_org_id());
            		   
            	   }
               }
               
               //比较执行人姓名和执行人岗位牌号是否相匹配
               if(s[2]!=null&&!s[2].trim().equals(currentSchedular.getC_username())){
            	   isRight=false;
        		  // errNum++;
            	   errMsg+="<tr><td>"+(i+1)+"</td><td>3</td><td>执行人岗位牌号和执行人姓名不匹配</td></tr>";
               }
               
               //班次信息验证
               if(s[3]==null){
            	   isRight=false;
        		   //errNum++;
            	   errMsg+="<tr><td>"+(i+1)+"</td><td>4</td><td>班次不能为空</td></tr>";
               }else{
            	   String[] shiftInfo=new String[2];
            	   s[3]=s[3].trim();
            	   int split=s[3].indexOf("-");
            	   shiftInfo[0]=s[3].substring(0,split);
            	   shiftInfo[1]=s[3].substring(split+1);
            	   if(!StringUtil.isNumber(shiftInfo[0])){
            		   isRight=false;
            		   //errNum++;
            		   errMsg+="<tr><td>"+(i+1)+"</td><td>4</td><td>班次格式错误,请使用标准模板自带下拉框编辑</td></tr>";
            	   }else{
            		   String c_shift_fullname=shiftMap.get(Long.parseLong(shiftInfo[0]));
            		   if(c_shift_fullname.equals(shiftInfo[1])){
            			   currentSchedular.setC_shift_name(c_shift_fullname.substring(0,c_shift_fullname.indexOf("(")));
            			   currentSchedular.setC_shift_id(Integer.parseInt(shiftInfo[0]));
            		   }else{
            			   isRight=false;
                		   //errNum++;
            			   errMsg+="<tr><td>"+(i+1)+"</td><td>4</td><td>班次格式错误,请使用标准模板自带下拉框编辑</td></tr>";
            		   }
            	   }
               }
               
               
               index=-1;
               if(s[4]!=null){
            	   index=s[4].lastIndexOf(".");
            	   s[4]=s[4].trim();
               }
               if(index!=-1){
            	   s[4]=s[4].substring(0,index);
               }
               if(s[4]==null||"".equals(s[4])){
//            	   isRight=false;
//        		  // errNum++;
//            	   errMsg+="<tr><td>"+(i+1)+"</td><td>5</td><td>排班人岗位排号不能为空</td></tr>";
               }else{
            	   if(!StringUtil.isNumber(s[4])){  
            		   isRight=false;
            		   //errNum++;
                	   errMsg+="<tr><td>"+(i+1)+"</td><td>5</td><td>排班人岗位牌号是数字</td></tr>";
                   }else{
                	   TSchedulingPojo  pojo=userMap.get(s[4]);
                	   if(pojo==null){
                		   param.put("usercode", s[4]);
                		   pojo=taskSchedularService.getShiftUserInfo(param); 
                		   if(pojo!=null){
                			   if(userMap.size()>50){
                				   userMap.clear();
                			   }
                			   userMap.put(pojo.getC_usercode(), pojo);
                		   }
                	   }

                	   if(pojo==null){
                		   isRight=false;
                		  // errNum++;
                		   errMsg+="<tr><td>"+(i+1)+"</td><td>5</td><td>该执行人的岗位牌号不存在</td></tr>";
                	   }else{
                		   currentSchedular.setC_creator(pojo.getC_username());
                		   currentSchedular.setC_last_modifier(pojo.getC_username());
                	   }
                   }
               }
               
               //对比数据库中的创建人姓名和Excel表格里面的是否一致
               if("".equals(s[4])||s[4]==null){
            	   
               }else if(s[5]!=null&&!s[5].trim().equals(currentSchedular.getC_creator())){
            	   isRight=false;
        		   //errNum++;
            	   errMsg+="<tr><td>"+(i+1)+"</td><td>6</td><td>排班人岗位牌号和排班人姓名不匹配</td></tr>";
              }
//               else if(s[5]!=null&&s[4]==null){
//            	   isRight=false;
//              	   errMsg+="<tr><td>"+(i+1)+"</td><td>5</td><td>排班人岗位排号不能为空,但是排班人姓名不为空</td></tr>";
//               
//               }
               
               currentSchedular.setC_remark(s[6]);
               if(isRight){
            	   Schedularlist.add(currentSchedular);
               }else{
            	  break;
               }

           }           
       }
       map.put("schedularList", Schedularlist);           
       //errMsg+="</table><br/>";
       map.put("errMsg", errMsg);
       //map.put("errNum", errNum);
       return map;
   }
   
  public String updateUserShift(){
	   try{
		  Session session=SessionFactory.getInstance(request, response);
		  Map userInfo =(Map)session.getAttribute("USERSESSION");
          String lastModifier=userInfo.get("name").toString();
		  String c_date= request.getParameter("c_date");
		  String c_userid=request.getParameter("c_userid");
		  String c_shift_id=request.getParameter("c_shift_id");
		  String c_remark=request.getParameter("c_remark");
		  
		  HashMap params=new HashMap();
		  params.put("c_remark", c_remark);
		  params.put("c_date", c_date);
		  params.put("c_userid", c_userid);
		  params.put("c_last_modifier", lastModifier);
		  if(c_shift_id==null||c_shift_id.trim().equals("")){
			  TSchedulingPojo delObjList = taskSchedularService.getShiftByUserDate(params);
			  taskSchedularService.deleteOneDayShift(params);
			  JSONObject delLogJson = JSONObject.fromObject(delObjList);
			  String dellogString = delLogJson.toString();
		      logService.addLogInfo(request, response, "1", "过程行为支持系统", "2", "基础管理", "排班管理", MyLogPojo.change, "排班管理-修改", "成功", "1", "T_SCHEDULING", dellogString, "修改为休息");
			  this.formatData("排班修改成功！");
			  return SUCCESS;
		  }
		  params.put("c_shift_id", c_shift_id);		  

		  HashMap params2=new HashMap();
    	  params2.put("c_userid", c_userid);
    	  params2.put("c_date", c_date);
		  TSchedulingPojo oldObjInfo = taskSchedularService.getShiftByUserDate(params2);
		  int count= taskSchedularService.updateUserShift(params);
		  
		  if(count<1){
			  TSchedulingPojo  param=new TSchedulingPojo();
			  String c_username=request.getParameter("c_username");
			  String c_org_id=request.getParameter("c_org_id");
			  String c_shift_name=request.getParameter("c_shift_name");
			  param.setC_userid(Long.parseLong(c_userid));
			  param.setC_shift_id(Long.parseLong(c_shift_id));
			  param.setC_date(c_date.replace("-", "/"));
			  param.setC_last_modifier(lastModifier);
			  param.setC_username(c_username);
			  param.setC_org_id(Long.parseLong(c_org_id));
			  param.setC_remark(c_remark);
			  c_date=c_date.substring(0,c_date.lastIndexOf('-'));
			  param.setC_ym_id(Integer.parseInt(c_date.replace("-", "")));
			  param.setC_shift_name(c_shift_name);
			  param.setC_creator(lastModifier);
			  
			  taskSchedularService.addShift(param);
			  this.formatData("排班修改成功！");

		  }else{
			  this.formatData("排班修改成功！");
		  }
		  
		  TSchedulingPojo newObjInfo = taskSchedularService.getShiftByUserDate(params2);
		  //添加数据日志
		  JSONObject oldLogJson = JSONObject.fromObject(oldObjInfo);
		  JSONObject newLogJson = JSONObject.fromObject(newObjInfo);
		  String oldlogString = oldObjInfo==null?"之前未排班":oldLogJson.toString();
	      String newlogString = newLogJson.toString();
	      logService.addLogInfo(request, response, "1", "过程行为支持系统", "2", "基础管理", "排班管理", MyLogPojo.change, "排班管理-修改", "成功", "1", "T_SCHEDULING", oldlogString, newlogString);  
	   }catch(Exception e){
		   e.printStackTrace();
		   logger.error(e.getMessage(),e);
		   return SUCCESS;
	   }
	   return SUCCESS;
   }
  
  

  public String getAllPostionTreeNodes(){
	  try{
		  List list=taskSchedularService.getAllPositionTreeNodes();
		  this.formatData(list);
	  }catch(Exception e){
		  e.printStackTrace();
		  this.formatData("[]");
		  return SUCCESS;
	  }
	  return SUCCESS;
  }
  
  public  String getDymanicPositionTreeNodes(){
	  try{
		  String  nodeId=request.getParameter("id");
			 
		  PositionTreeVo  node=null;
		  if(nodeId!=null&&!nodeId.equals("")){
			  node=new PositionTreeVo();
			  node.setId(nodeId);
			  List list=taskSchedularService.getDymanicPositionTreeNodes(node);
			  this.formatData(list);
		  }else{			  
			  this.formatData("[]");
		  }
	  }catch(Exception e){
		  e.printStackTrace();
		  this.formatData("[]");
	  }
	  return  SUCCESS;
  }
  
  /**
 * 方法用途：班组树部门树
 * 参数：
 * 返回类型：String
 * 编写时间：2015年7月21日下午2:36:06
 * 编写人：caoyong
 */
public String getDepartmentTree(){
	  String type = request.getParameter("type");
	  this.formatData(taskSchedularService.getDepartmentTree(type));
		return SUCCESS;
  }
}
