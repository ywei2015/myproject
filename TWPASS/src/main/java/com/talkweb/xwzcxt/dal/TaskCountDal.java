package com.talkweb.xwzcxt.dal;

import java.util.List;

import com.talkweb.twdpe.dal.batis.spring.SessionDaoSupport;
import com.talkweb.xwzcxt.pojo.TTask;
import com.talkweb.xwzcxt.pojo.TaskCondition;

/**
 * TODO(描述这个类的作用)
 * 
 * @author: 2013-12-31，FLN，（描述修改内容）
 */
public class TaskCountDal extends SessionDaoSupport
{
    public List<TTask> queryTaskInfo(TaskCondition taskCondition)
    {

        return (List<TTask>) this.getSession().selectList(
                "taskCount.queryTaskInfoByStatus", taskCondition);

    }
}
