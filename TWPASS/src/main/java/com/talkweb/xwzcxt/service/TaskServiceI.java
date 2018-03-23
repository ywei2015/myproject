package com.talkweb.xwzcxt.service;

import java.util.List;
import java.util.Map;

import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.xwzcxt.vo.TTaskVo;

public interface TaskServiceI {
	public TTaskVo getTaskByID(String id);
	public List<TTaskVo> getAllTask();
	public String getPositionNameByUserid(String id);
	public Pagination getTasks(Map params, Pagination page);
	public List<Map> getTasksWithoutPage(Map params);
	public void deleteTaskById(String ids);
	public boolean updatePanelMatterdetail(Map map);
}