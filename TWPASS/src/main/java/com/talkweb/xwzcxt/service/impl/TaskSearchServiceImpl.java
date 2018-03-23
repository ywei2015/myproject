package com.talkweb.xwzcxt.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.talkweb.twdpe.base.org.pojo.Org;
import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.xwzcxt.dal.TaskDal;
import com.talkweb.xwzcxt.dal.XwzcxtMngDal;
import com.talkweb.xwzcxt.pojo.Message;
import com.talkweb.xwzcxt.pojo.StdFileAffixPojo;
import com.talkweb.xwzcxt.pojo.TaskErrorFeedback;
import com.talkweb.xwzcxt.pojo.TaskMouldPojo;
import com.talkweb.xwzcxt.pojo.TaskStatusPojo;
import com.talkweb.xwzcxt.pojo.TaskStepResultPojo;
import com.talkweb.xwzcxt.service.TaskSearchService;

public class TaskSearchServiceImpl implements TaskSearchService {

	@Autowired
    public XwzcxtMngDal xwzcxtMngDal;
	@Autowired
	private TaskDal taskDal;
	
	@Override
	public List<TaskMouldPojo> queryTaskInfoListByCondition(
			TaskMouldPojo taskMouldPojo,Pagination pagination) {
		List<TaskMouldPojo> taskList=new ArrayList<TaskMouldPojo>();
		taskList=xwzcxtMngDal.queryTaskInfoListByCondition(taskMouldPojo,pagination);
			for(TaskMouldPojo task:taskList){
				String userid=task.getC_exec_userid();
				if(userid!=null){
					String orginfo[]=getDepartment(userid);
					if(orginfo!=null && orginfo.length>=4){
						task.setOrgdepartname(orginfo[3]);
					}
				}
			}
		
		
		return taskList;
	}
	
	@Override
	public List queryNewTask(Map map,List list,Pagination pagination){
		List<TaskMouldPojo> taskList=new ArrayList<TaskMouldPojo>();
		list=xwzcxtMngDal.queryNewTask(map,list,pagination);
		return list;
	}
	
	
	// 根据用户ID获取部门信息 0.orgid, 1.orgname, 2.orgpath, 3 orgdepartname
	private  String[] getDepartment(String uid){
		Map orglist = taskDal.getDepartmentMapeByUserid(uid);
		String[] strorginfo = new String[4];

		if (orglist != null && orglist.containsKey("PATH")) {
			strorginfo[0] = orglist.get("ORGID").toString();
			strorginfo[1] = orglist.get("ORGNAME").toString();
			String orgpath = orglist.get("PATH").toString();
			strorginfo[2] = orgpath;
			String[] strlist = orgpath.split(";");
			Map dpt = taskDal.getDepartmentNameByID(strlist[1]);
			if (dpt != null && dpt.containsKey("ORGNAME")) {
				strorginfo[3] = dpt.get("ORGNAME").toString();
			}
		}
		return strorginfo;
	}
	

	@Override
	public Document convertOrgList2DomTree(List<Org> orgList) {
		//元素栈
		List<Element> elementStack=new ArrayList<Element>();
		//组织机构栈
		List<Org> stack=new ArrayList<Org>();
		//冗余List
		List<Org> orgToDelete=new ArrayList<Org>();
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
		DocumentBuilder builder=null;
		Document document=null;
		String rootId="1";
		String rootName="长沙卷烟厂";
		try {
			builder=factory.newDocumentBuilder();
			document=builder.newDocument();
			//初始化顶级元素
			Element topElement=document.createElement("org");
			topElement.setAttribute("id", rootId);
			topElement.setIdAttribute("id", true);
			topElement.setAttribute("name", rootName);
			document.appendChild(topElement);
			for(Org org:orgList){
				String orgPid=org.getParentOrgId()+"";
				if(orgPid.equals(topElement.getAttribute("id"))){
					stack.add(org);
					orgToDelete.add(org);
					Element node=document.createElement("org");
					node.setAttribute("id", org.getOrgId()+"");
					node.setIdAttribute("id", true);
					node.setAttribute("pid", orgPid);
					node.setAttribute("name", org.getOrgName());
					topElement.appendChild(node);
					elementStack.add(node);
				}
			}
			orgList.removeAll(orgToDelete);
			orgToDelete.clear();
			while(stack.size()>0){
				Org currentOrg=stack.remove(stack.size()-1);
				Element parentNode=elementStack.remove(elementStack.size()-1);
				for(Org org:orgList){
					String orgPid=org.getParentOrgId()+"";
					if(orgPid.equals(currentOrg.getOrgId()+"")){
						stack.add(org);
						orgToDelete.add(org);
						Element node=document.createElement("org");
						node.setAttribute("id", org.getOrgId()+"");
						node.setIdAttribute("id", true);
						node.setAttribute("pid", orgPid);
						node.setAttribute("name", org.getOrgName());
						parentNode.appendChild(node);
						elementStack.add(node);
					}
				}
				orgList.removeAll(orgToDelete);
				orgToDelete.clear();
			}
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return document;
	}

	public boolean isOrgInDepartmentOrClassGroup(Document orgDocument,String orgId,String departmentId,String classGroupId){
		Element topElement=null;
		NodeList list=null;
		if(classGroupId!=null&&!classGroupId.equals("")){
			topElement=orgDocument.getElementById(classGroupId);
		}
		else{
			topElement=orgDocument.getElementById(departmentId);
		}
		//若当前岗位为顶级，则无需再查
		if(topElement.getAttribute("id").equals(orgId)){
			return true;
		}
		list=topElement.getElementsByTagName("org");
		//若该树无子节点，则无需再查
		if(list==null||list.getLength()<1){
			return false;
		}
		for(int i=0;i<list.getLength();i++){
			Node node=list.item(i);
			Element element=(Element)node;
			if(element.getAttribute("id").equals(orgId)){
				return true;
			}
		}
		return false;
	}

	@Override
	public List<TaskStepResultPojo> getTaskStepAndResult(String taskId,Pagination pagination) {
		return xwzcxtMngDal.getTaskStepAndResult(taskId,pagination);
	}

	@Override
	public List<Message> getMessageByUserId(String userId) {
		// TODO Auto-generated method stub
		return xwzcxtMngDal.getMessageByUserId(userId);
	}

	@Override
	public List<Message> getMessageByCondition(Message message) {
		// TODO Auto-generated method stub
		return xwzcxtMngDal.getMessageByCondition(message);
	}

	@Override
	public Message getMessageById(String messageId) {
		// TODO Auto-generated method stub
		return xwzcxtMngDal.getMessageById(messageId);
	}

	@Override
	public void updateTaskStepInfo(TaskStepResultPojo pojo) {
		// TODO Auto-generated method stub
		xwzcxtMngDal.updateTaskStepInfo(pojo);
	}

	@Override
	public void updateTaskStatusByProc(TaskStatusPojo pojo) {
		// TODO Auto-generated method stub
	  xwzcxtMngDal.updateTaskStatusByProc(pojo);
	}
	
	@Override
    public String newTask(TaskMouldPojo taskMouldPojo) {
        // TODO Auto-generated method stub
        return xwzcxtMngDal.newTask(taskMouldPojo);
    }
	
    @Override
    public StdFileAffixPojo queryImgPath(String c_file_id)
    {
        return xwzcxtMngDal.queryImgPath(c_file_id);
    }
    
    @Override
    public String errorFeedback(TaskErrorFeedback taskError) {
        
        return xwzcxtMngDal.errorFeedback(taskError);
    }
    
    @Override
    public void updateMessageState(long c_msg_id) {
        // TODO Auto-generated method stub
        xwzcxtMngDal.updateMessageState(c_msg_id);
    }
    
    @Override
    public String addMessage(Message message) {
        // TODO Auto-generated method stub
        return xwzcxtMngDal.addMessage(message);
    }

	@Override
	public void saveBz(Map z_bei) {
		// TODO Auto-generated method stub
		 xwzcxtMngDal.saveBz(z_bei);
	}

	@Override
	public String getBz(Map z_bei) {
		// TODO Auto-generated method stub
		 
		return xwzcxtMngDal.getBz(z_bei);
	}

//	@Override
//	public List getAreaInfo() {
//		return xwzcxtMngDal.getAreaInfo();
//	}
	
}
