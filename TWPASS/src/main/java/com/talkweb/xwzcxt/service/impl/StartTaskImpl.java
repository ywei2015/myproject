package com.talkweb.xwzcxt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.xwzcxt.dal.StartTaskDal;
import com.talkweb.xwzcxt.pojo.StartTaskPojo;
import com.talkweb.xwzcxt.service.StartTaskService;

public class StartTaskImpl implements StartTaskService{


	@Autowired
    public StartTaskDal startTaskDal;
	
	@Override
	public String startTask(StartTaskPojo startTaskPojo) {
		   // TODO Auto-generated method stub
        return startTaskDal.startTask(startTaskPojo);
	}
	

}
