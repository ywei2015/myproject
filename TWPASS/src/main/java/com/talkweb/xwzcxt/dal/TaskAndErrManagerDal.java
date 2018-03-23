package com.talkweb.xwzcxt.dal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.talkweb.twdpe.dal.batis.spring.SessionDaoSupport;
import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.xwzcxt.pojo.TTask;
import com.talkweb.xwzcxt.vo.TaskAndErrManagerVo;

public class TaskAndErrManagerDal extends SessionDaoSupport{

	public List getUnexecuteableTask(TaskAndErrManagerVo params,Pagination pagination){
		return this.getSession().selectList("taskAndErrManager.getUnexecuteableTask",params,pagination);
	}
	
	public Map deleteTask(Map map){
		Map ret = new HashMap();
		List<TTask> oldValue = this.searchDeleteTask(map);
		int tag = this.getSession().update("taskAndErrManager.deleteTask",map);
		ret.put("tag", tag);
		if(tag != 0){
			ret.put("oldValue", oldValue);
			ret.put("oldValue_datafield", "t_task");
		}
		return ret;
	}
	
	public List<TTask> searchDeleteTask(Map map){
		return this.getSession().selectList("taskAndErrManager.searchDeleteTask", map);
	}
}

