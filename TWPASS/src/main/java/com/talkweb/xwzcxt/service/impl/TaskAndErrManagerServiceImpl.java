package com.talkweb.xwzcxt.service.impl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.xwzcxt.dal.TaskAndErrManagerDal;
import com.talkweb.xwzcxt.pojo.MyLogPojo;
import com.talkweb.xwzcxt.pojo.TTask;
import com.talkweb.xwzcxt.service.MyLogService;
import com.talkweb.xwzcxt.service.TaskAndErrManagerService;
import com.talkweb.xwzcxt.vo.TaskAndErrManagerVo;


public class TaskAndErrManagerServiceImpl implements TaskAndErrManagerService{

	@Autowired
	private TaskAndErrManagerDal dal;
	@Autowired
	private MyLogService logService;
	@Override
	public List getUnexecuteableTask(TaskAndErrManagerVo params,Pagination pagination) {
		return dal.getUnexecuteableTask(params,pagination);
	}

	public int deleteTask(Map map, HttpServletRequest request, HttpServletResponse response){
		Map ret = dal.deleteTask(map);
		int tag = (Integer)ret.get("tag");
		if(tag != 0){
			List<TTask> oldValue = (List<TTask>)ret.get("oldValue");
			String oldValue_datafield = (String)ret.get("oldValue_datafield");
			logService.addLogInfo(request, response, "1", "过程行为支持系统", "", "任务管理", "任务管理维护", MyLogPojo.del, "任务管理维护-删除", "成功", "1", oldValue_datafield, oldValue.toString(), "");
		}
		return tag;
	}
}
