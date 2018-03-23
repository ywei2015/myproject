package com.talkweb.xwzcxt.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.xwzcxt.dal.TaskAndErrManagerDal;
import com.talkweb.xwzcxt.vo.TaskAndErrManagerVo;

public class Test {

     public static void main(String[] args) {
	   
    	List<String> list=new ArrayList<String>(); 
    	//list.add("classpath:config/app.bean.xml");
    	//list.add("classpath:config/cache.bean.xml");
    	list.add("classpath:config/database/datasource.bean.xml");
    	//list.add("classpath:config/sqlmap/task.sql.xml");
    	//list.add("classpath:config/service/taskVerifyAndComment.service.xml");
    	list.add("classpath:config/service/taskAndErrManager.service.xml");
    	
    	//list.add("");
    	String [] files=list.toArray(new String[0]);
    	
    	ClassPathXmlApplicationContext ctx =new ClassPathXmlApplicationContext();
    	ctx.setConfigLocations(files);
    	System.out.println(files);
    	ctx.refresh();
    	
    	//TaskVerifyAndCommentDal dal=(TaskVerifyAndCommentDal) ctx.getBean("taskVerifyDal");
    	TaskAndErrManagerDal dal=(TaskAndErrManagerDal) ctx.getBean("taskAndErrManagerDal");
    	
    	
    	TaskAndErrManagerVo vo=new TaskAndErrManagerVo();
    	vo.setUserid(2000412L);
    	List list2=dal.getUnexecuteableTask(vo, new Pagination(1,15));
    	System.out.println(list2);
		//List list2= dal.getSelfData(exevo);
    	//System.out.println(list2);
     }
	
	
}
