package com.talkweb.xwzcxt.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.xwzcxt.vo.TaskAndErrManagerVo;

public interface TaskAndErrManagerService {
  public List getUnexecuteableTask(TaskAndErrManagerVo params,Pagination pagination);
  public int deleteTask(Map map, HttpServletRequest request, HttpServletResponse response);
}
