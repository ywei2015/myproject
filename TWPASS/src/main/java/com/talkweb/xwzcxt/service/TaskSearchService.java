package com.talkweb.xwzcxt.service;

import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;

import com.talkweb.twdpe.base.org.pojo.Org;
import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.xwzcxt.pojo.Message;
import com.talkweb.xwzcxt.pojo.StdFileAffixPojo;
import com.talkweb.xwzcxt.pojo.TaskErrorFeedback;
import com.talkweb.xwzcxt.pojo.TaskMouldPojo;
import com.talkweb.xwzcxt.pojo.TaskStatusPojo;
import com.talkweb.xwzcxt.pojo.TaskStepResultPojo;

public interface TaskSearchService {
	public List<TaskMouldPojo> queryTaskInfoListByCondition(TaskMouldPojo taskMouldPojo,Pagination pagination);
	Document convertOrgList2DomTree(List<Org> orgList);
	public boolean isOrgInDepartmentOrClassGroup(Document orgDocument,String orgId,String departmentId,String classGroupId);
	public List<TaskStepResultPojo> getTaskStepAndResult(String taskId,Pagination pagination);
	public List<Message> getMessageByUserId(String userId);
	public List<Message> getMessageByCondition(Message message);
	public Message getMessageById(String messageId);
	public void updateTaskStepInfo(TaskStepResultPojo pojo);
	public void updateTaskStatusByProc(TaskStatusPojo pojo);
	public String newTask(TaskMouldPojo taskMouldPojo);
	public StdFileAffixPojo queryImgPath(String c_file_id);
    public String errorFeedback(TaskErrorFeedback taskError);
    public void updateMessageState(long c_msg_id);
    public String addMessage(Message message);
    public List queryNewTask(Map map,List list,Pagination pagination);
//    public List getAreaInfo();
	public void saveBz(Map z_bei);
	public String getBz(Map z_bei);
}
