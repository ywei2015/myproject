package com.talkweb.xwzcxt.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Document;

import com.opensymphony.xwork2.Action;
import com.talkweb.twdpe.base.dict.service.DictionaryService;
import com.talkweb.twdpe.base.org.pojo.Org;
import com.talkweb.twdpe.base.org.pojo.Position;
import com.talkweb.twdpe.base.org.pojo.User;
import com.talkweb.twdpe.base.org.service.OrgService;
import com.talkweb.twdpe.base.org.service.PositionService;
import com.talkweb.twdpe.base.org.service.UserService;
import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.twdpe.web.session.main.Session;
import com.talkweb.twdpe.web.session.util.SessionFactory;
import com.talkweb.twdpe.webcomponent.common.action.BusinessAction;
import com.talkweb.xwzcxt.action.WaithandTaskMngAction.InnerList;
import com.talkweb.xwzcxt.common.XwzcxtCommon.Enum_Xwzcxt;
import com.talkweb.xwzcxt.pojo.Message;
import com.talkweb.xwzcxt.pojo.TaskError;
import com.talkweb.xwzcxt.pojo.TaskErrorFeedback;
import com.talkweb.xwzcxt.pojo.TaskMouldPojo;
import com.talkweb.xwzcxt.service.TaskSearchService;
import com.talkweb.xwzcxt.service.impl.XwzcxtMngImp;
/**
 * 
 * @author YangChen
 * 任务查询模块的action
 * 消息通知模块的action
 * 异常反馈模块的action
 */
public class TaskSearchAction extends WaithandTaskMngAction{
	
	private static final long serialVersionUID = -6081890527802545239L;
	@Autowired
	private DictionaryService dictionaryService;
	@Autowired
    private XwzcxtMngImp xwzcxtMngImp;
	@Autowired
	private OrgService orgService;
	@Autowired
	private UserService userService;
	@Autowired
    private PositionService positionService;
	@Autowired
	private TaskSearchService taskSearchService;
	
	private TaskMouldPojo taskMouldPojo=new TaskMouldPojo();
	
	private TaskErrorFeedback taskErrorFeedback=new TaskErrorFeedback();
    
	
	private Message message=new Message();
	
	private HttpServletResponse response=ServletActionContext.getResponse();
	
	private HttpServletRequest request=ServletActionContext.getRequest();
	
	private PrintWriter out=null;
	
	public static Map<String,String> TASK_TYPE_MAP=new HashMap<String,String>();
	//初始化out对象
	{
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			out=response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//初始化任务类型集合
	static{
		TASK_TYPE_MAP.put("1", "日管控");
		TASK_TYPE_MAP.put("2", "周管控");
		TASK_TYPE_MAP.put("3", "月管控");
		TASK_TYPE_MAP.put("10", "异常处理");
		TASK_TYPE_MAP.put("20", "临时任务");
	}
	
	public TaskSearchAction(){}
	
	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public DictionaryService getDictionaryService() {
		return dictionaryService;
	}

	public void setDictionaryService(DictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
	}
	
	public static Map<String, String> getTASK_TYPE_MAP() {
		return TASK_TYPE_MAP;
	}

	public static void setTASK_TYPE_MAP(Map<String, String> tASK_TYPE_MAP) {
		TASK_TYPE_MAP = tASK_TYPE_MAP;
	}
	
	public XwzcxtMngImp getXwzcxtMngImp() {
		return xwzcxtMngImp;
	}

	public void setXwzcxtMngImp(XwzcxtMngImp xwzcxtMngImp) {
		this.xwzcxtMngImp = xwzcxtMngImp;
	}
	
	public TaskMouldPojo getTaskMouldPojo() {
		return taskMouldPojo;
	}

	public void setTaskMouldPojo(TaskMouldPojo taskMouldPojo) {
		this.taskMouldPojo = taskMouldPojo;
	}
	
	public TaskErrorFeedback getTaskErrorFeedback()
    {
        return taskErrorFeedback;
    }

    public void setTaskErrorFeedback(TaskErrorFeedback taskErrorFeedback)
    {
        this.taskErrorFeedback = taskErrorFeedback;
    }

    //获取所有的任务类别
	public void getAllTaskType(){
		String str="";
		str += "[";
		//迭代Map，同时拼接JSON
		@SuppressWarnings("static-access")
		Iterator iterator=this.getTASK_TYPE_MAP().entrySet().iterator();
		while(iterator.hasNext()){
			Entry entry=(Entry) iterator.next();
			str += "{";
				str += "\"text\":" + "\"" + entry.getValue() + "\",";
				str += "\"value\":" + "\"" + entry.getKey() + "\"";
			str += "}";
			str += ",";
		}
		//干掉最后的逗号
		str = str.substring(0, str.length()-1);
		str += "]";
		out.print(str);
		out.close();
	}
	
	public String queryTaskByPageAndCondition(){
		List<TaskMouldPojo> finalTaskList=new ArrayList<TaskMouldPojo>();
		String departmentId=request.getParameter("departmentId");
		String classGroupId=request.getParameter("classGroupInput");
		pagination=new Pagination();
		//如果是第一次加载
		if(departmentId==null||"".equals(departmentId)){
			pagination.setAllPage(1);
			pagination.setCount(0);
			pagination.setCurrPage(1);
			pagination.setList(finalTaskList);
			pagination.setSize(20);
			this.formatDatagirdData(finalTaskList, pagination);
			return SUCCESS;
		}
		taskMouldPojo.setC_department_id(departmentId);
		taskMouldPojo.setC_classgroup_id(classGroupId);
		List<TaskMouldPojo> taskList=taskSearchService.queryTaskInfoListByCondition(taskMouldPojo,null);
		//初始化Org树
		List<Org> orgList=orgService.queryOrgs(new Org());
		Document document=taskSearchService.convertOrgList2DomTree(orgList);
		//过滤掉非该部门或班组的任务
		for(TaskMouldPojo pojo:taskList){
			if(taskSearchService.isOrgInDepartmentOrClassGroup(document, pojo.getC_org_id(), taskMouldPojo.getC_department_id(), taskMouldPojo.getC_classgroup_id())){
				finalTaskList.add(pojo);
			}
		}
		//List<TaskMouldPojo> taskList=xwzcxtMngImp.GetCurrentRecordByPage(taskMouldPojo,pagination, Enum_Xwzcxt.TASK_INFO_LIST);
		//this.formatDatagirdData(taskList, pagination);//格式化DataGrid控件数据
		return null;
	}
	//按索引从数组中删除元素
	private Object[] deleteElementFromArray(Object[] arr,int index){
		if(index<0||index>arr.length-1){
			try {
				throw new Exception("IndexOutOfBoundException:"+index);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		for(int i=index;i<arr.length-1;i++){
			arr[i]=arr[i+1];
		}
		Object[] newArray=new Object[arr.length-1];
		System.arraycopy(arr, 0, newArray, 0, arr.length-1);
		return newArray;
	}
	
	public void getAllDepartment(){
		String str="";
		Long rootNodeForOrg=1L;
		Org org=new Org();
		org.setParentOrgId(rootNodeForOrg);
		List<Org> departmentsList=orgService.queryOrgs(org);
		str += "[";
		for(int i=0;i<departmentsList.size();i++){
			Org department=departmentsList.get(i);
			str += "{";
				str += "\"text\":" + "\"" + department.getOrgName() + "\"" + ",";
				str += "\"value\":" + "\"" + department.getOrgId() + "\"";
			str += "}";
			if(i<departmentsList.size()-1){
				str += ",";
			}
		}
		str += "]";
		out.print(str);
		out.close();
	}
	
	public void getCurrentUserDepartment(){
		String str="";
		long loginUserId=this.getLongUserID();
		User currentUser=userService.getUser(loginUserId);
		Long loginUserOrgId=currentUser.getOrgId();
		Org org=new Org();
		org=orgService.getOrg(loginUserOrgId);
		while(org.getParentOrgId()!=1){
			Long parentOrgId=org.getParentOrgId();
			org=orgService.getOrg(parentOrgId);
		}
		str += "{";
		str += "\"text\":" + "\"" + org.getOrgName() + "\"" + ",";
		str += "\"value\":" + "\"" + org.getOrgId() + "\"";
		str += "}";
		out.print(str);
		out.close();
	}
	
	public void getClassGroupTreeByDepartmentId(){
		String str="";
		String departmentId=request.getParameter("departmentId");
		List<Org> classGroups=orgService.getChildOrgs(Long.parseLong(departmentId));
		str += "[";
		for(Org org:classGroups){
			str += "{";
				str += "\"id\":" + "\"" + org.getOrgId() + "\"" + ",";
				if(departmentId.equals(org.getParentOrgId()+"")){
					str += "\"pid\":" + "\"" + 0 + "\"" + ",";
				}
				else{
					str += "\"pid\":" + "\"" + org.getParentOrgId() + "\"" + ",";
				}
				str += "\"val\":" + "\"" + org.getOrgName() + "\"";
			str += "}";
			str += ",";
		}
		str = str.substring(0,str.length()-1);
		str += "]";
		out.print(str);
		out.close();
	}
	
	public void getPositionByClassGroup(){
		String str="";
		String classGroupId=request.getParameter("classGroupId");
		List<Position> positionsList=positionService.getPositions();
		List<Position> positionsInClassGroup=new ArrayList<Position>();
		//过滤出该班组下的position
		for(Position pos:positionsList){
			String currentIteratorPosition=pos.getOrgId()+"";
			if(classGroupId.equals(currentIteratorPosition)){
				positionsInClassGroup.add(pos);
			}
		}
		str += "[";
		for(Position pos:positionsInClassGroup){
			str += "{";
			str += "\"text\":" + "\"" + pos.getPosiName() + "\",";
			str += "\"value\":" + "\"" + pos.getPositionId() + "\"";
			str += "}";
			str += ",";
		}
		if(positionsInClassGroup.size()>0){
			str = str.substring(0, str.length()-1);
		}
		str += "]";
		out.print(str);
		out.close();
	}
	
	public void getUserNameByPositionId(){
		String str="";
		String positionId=request.getParameter("positionId");
		User user=new User();
		user.setPositionId(Long.parseLong(positionId));
		List<User> userList=userService.queryUsers(user);
		str += "[";
		for(int i=0;i<userList.size();i++){
			User u=userList.get(i);
			str += "{";
			str += "\"text\":" + "\"" + u.getDisplayName() + "\",";
			str += "\"value\":" + "\"" + u.getUserId() + "\"";
			str += "}";
			if(i<userList.size()-1){
				str += ",";
			}
		}
		str += "]";
		out.print(str);
		out.close();
	}
	
	public void test(){
		Org org=new Org();
		org.setParentOrgId((long) 1000441);
		System.out.println(orgService.queryOrgs(org).size());
		//System.out.println(taskSearchService.isOrgInDepartmentOrClassGroup(document, "1000132", "1000028", ""));
	}
	
	public String convertNum2MessageLevel(int number){
		String messageLevel="";
		if(number==1){
			messageLevel="普通";
		}
		else if(number==2){
			messageLevel="重要";
		}
		else if(number==3){
			messageLevel="紧急";
		}
		else if(number==4){
			messageLevel="非常紧急";
		}
		return messageLevel;
	}
	public String getMessages(){
		List<Message> messageList=new ArrayList<Message>();
		//如果是按条件查询
//		if(null!=this.getMessage().getC_remark()&&!"".equals(this.getMessage().getC_remark())){
			message.setC_receiver(this.getLongUserID());
			messageList=taskSearchService.getMessageByCondition(message);
//		}
//		else{
//			messageList=taskSearchService.getMessageByUserId(this.getLongUserID()+"");
//		}
		for(Message message:messageList){
		    if(Integer.parseInt(message.getC_status())<22){
		        message.setC_status_string("未读");
		    }else{
		        message.setC_status_string("已读");
		    }
			//message.setC_task_status_string(new WaithandTaskMngAction().convertStatusCode2String(message.getC_task_status()));
			//message.setWatchMessageDetail("<a name=messageLink onclick=submitHiddenForm('"+message.getC_msg_id()+"') style=text-decoration:none;display:block; title=点击查看详情>"+message.getWatchMessageDetail()+"</a>");
		}
		WaithandTaskMngAction waithand=new WaithandTaskMngAction();
		//调用父类的内部类，对任务列表List进行泛型规定
        InnerList<Message> innerList=waithand.new InnerList<Message>();
        innerList.setList(messageList);
        //调用继承父类的初始化分页方法
        List<TaskMouldPojo> currentList=(List<TaskMouldPojo>)(this.initPagination(innerList, 20,request));
		
		this.formatDatagirdData(currentList, pagination);
		return SUCCESS;
	}
	
	public String getMessageDetail(){
		List<Message> messageList=new ArrayList<Message>();
		Message message=taskSearchService.getMessageById(this.getMessage().getC_msg_id()+"");
		if(message==null){
			message=new Message();
		}
		else{
			message.setC_msg_level_string(this.convertNum2MessageLevel(message.getC_msg_level()));
			message.setC_sender_name(userService.getUser(message.getC_sender()).getDisplayName());
		}
		messageList.add(message);
		WaithandTaskMngAction waithand=new WaithandTaskMngAction();
		InnerList<Message> list=waithand.new InnerList<Message>();
		list.setList(messageList);
		this.initPagination(list, 1, request);
		this.formatDatagirdData(messageList, pagination);
		return SUCCESS;
	}
	
	//更新消息通知状态
    public String updateMessageState(){

        taskSearchService.updateMessageState(message.getC_msg_id());
      
        Map pageData = new HashMap();
        pageData.put("result", true);
      
        this.formatData(pageData);
        return SUCCESS;
        
    }
    //发起消息通知
    public String addMessage(){
        Session session = SessionFactory.getInstance(request, response);
        Map user = new HashMap();
        String loginUserID ="";
        if (user != null){
            user = (Map) session.getAttribute("USERSESSION");
            loginUserID = user.get("id").toString();
        }
        message.setC_sender(Long.valueOf(loginUserID));
        Map pageData = new HashMap();
        String msg_id =taskSearchService.addMessage(message);
        
        if(msg_id!=null){
            
            pageData.put("result", true);
            pageData.put("msg", "发起消息成功");
            
        }else{
            pageData.put("result", false);
            pageData.put("msg", "发起消息失败");
        }
        
      
        this.formatData(pageData);
        return SUCCESS;
        
    }
	
	//获取上级领导
	public void getCurrentUserLeader(){
		response.setCharacterEncoding("UTF-8");
		PrintWriter out=null;
		String jsonString="";
		User currentUser=userService.getUser(this.getLongUserID());
		long leaderId=currentUser.getUpUserId();
		User leader=userService.getUser(leaderId);
		jsonString += "{";
			if(leader!=null){
				jsonString += "\"userName\":" + "\"" + leader.getDisplayName() + "\",";
				jsonString += "\"userId\":" + "\"" + leader.getUserId() + "\"";
			}
		jsonString += "}";
		try {
			out=response.getWriter();
			out.print(jsonString);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			if(out!=null){
				out.close();
			}
		}
	}
	
	//根据组织机构ID获取人员
	public void getPersonListByOrgId(){
		String jsonString="";
		PrintWriter out=null;
		List<User> userList=new ArrayList<User>();
		String orgId=request.getParameter("orgId");
		Org orgChildTreeRoot=orgService.getOrg(Long.parseLong(orgId));
		Org tempOrg=new Org();
		tempOrg.setParentOrgId(Long.parseLong(orgId));
		//获取子树
		List<Org> orgChildTree=orgService.queryOrgs(tempOrg);
		//加上根节点
		orgChildTree.add(orgChildTreeRoot);
		for(Org org:orgChildTree){
			User tempUser=new User();
			tempUser.setOrgId(org.getOrgId());
			List<User> tempUserList=userService.queryUsers(tempUser);
			userList.addAll(tempUserList);
		}
		//System.out.println(userList.size());
		//开始对json进行拼接
		jsonString += "[";
		for(User user:userList){
			jsonString += "{";
				jsonString += "\"id\":" + "\"" + user.getUserId() + "\",";
				jsonString += "\"pid\":" + "\"" + "-1" + "\",";
				jsonString += "\"val\":" + "\"" + user.getDisplayName() + "\"";
			jsonString += "}";
			jsonString += ",";
		}
		if(userList.size()>0){
			jsonString=jsonString.substring(0, jsonString.length()-1);
		}
		jsonString += "]";
		try {
			out=response.getWriter();
			out.print(jsonString);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			if(out!=null){
				out.close();
			}
		}
	}
	
	//异常反馈
	public String errorFeedback(){
	    Session session = SessionFactory.getInstance(request, response);
        Map user = new HashMap();
        String loginUserID ="";
        if (user != null){
            user = (Map) session.getAttribute("USERSESSION");
            loginUserID = user.get("id").toString();
        }
        taskErrorFeedback.setC_writer(loginUserID);
        String err_id = taskSearchService.errorFeedback(taskErrorFeedback);
        
        Map pageData = new HashMap();
        if(err_id!=null){
            
            pageData.put("result", true);
            pageData.put("msg", "异常反馈提交成功");
            
        }else{
            pageData.put("result", false);
            pageData.put("msg", "异常反馈提交失败");
        }
        this.formatData(pageData);
        return SUCCESS;
	    
    }
}
