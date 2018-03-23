package com.talkweb.xwzcxt.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.xwzcxt.dal.TaskCountDal;
import com.talkweb.xwzcxt.pojo.Pie;
import com.talkweb.xwzcxt.pojo.Series;
import com.talkweb.xwzcxt.pojo.TTask;
import com.talkweb.xwzcxt.pojo.TaskCondition;
import com.talkweb.xwzcxt.pojo.TaskCount;
import com.talkweb.xwzcxt.service.CountTaskService;

/**
 * TODO(描述这个类的作用)
 * 
 * @author: 2013-12-30，FLN，（描述修改内容）
 */
public class CountTaskServiceImpl implements CountTaskService {
	private static final Logger logger = LoggerFactory
			.getLogger(CountTaskServiceImpl.class);

	@Autowired
	private TaskCountDal taskCountDal;

	/**
	 * 柱状图
	 */
	@Override
	public TaskCount countTask(TaskCondition taskcondition) {
		TaskCount taskCount = new TaskCount();
		List<TTask> list = taskCountDal.queryTaskInfo(taskcondition);
		if ("1".equals(taskcondition.getGroup())) {

			Series Serie0 = new Series("刚生成");
			Series Serie11 = new Series("刚确认");
			Series Serie22 = new Series("待执行");
			Series Serie33 = new Series("已完成");
			Map<String, Integer> map0 = new TreeMap<String, Integer>();
			Map<String, Integer> map11 = new TreeMap<String, Integer>();
			Map<String, Integer> map22 = new TreeMap<String, Integer>();
			Map<String, Integer> map33 = new TreeMap<String, Integer>();
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				TTask tTask = (TTask) iterator.next();
				if (!taskCount.getIds().contains(tTask.getC_EXEC_USERID())) {
					taskCount.addCategories(tTask.getUserName());
					taskCount.addIds(tTask.getC_EXEC_USERID() + "");
				}
				switch (tTask.getC_STATUS().intValue()) {
				case 0:
					map0.put(tTask.getC_EXEC_USERID() + "",
							map0.get(tTask.getC_EXEC_USERID()) == null ? 1
									: map0.get(tTask.getC_EXEC_USERID()) + 1);
					break;
				case 11:
					map11.put(tTask.getC_EXEC_USERID() + "",
							map11.get(tTask.getC_EXEC_USERID()) == null ? 1
									: map11.get(tTask.getC_EXEC_USERID()) + 1);
					break;
				case 22:
					map22.put(tTask.getC_EXEC_USERID() + "",
							map22.get(tTask.getC_EXEC_USERID()) == null ? 1
									: map22.get(tTask.getC_EXEC_USERID()) + 1);
					break;
				case 33:
					map33.put(tTask.getC_EXEC_USERID() + "",
							map33.get(tTask.getC_EXEC_USERID()) == null ? 1
									: map33.get(tTask.getC_EXEC_USERID()) + 1);
					break;

				}

			}

			for (int i = 0; i < taskCount.getIds().size(); i++) {
				String id = taskCount.getIds().get(i);
				Serie0.addData(map0.get(id) == null ? "0" : map0.get(id)
						.toString());
				Serie11.addData(map11.get(id) == null ? "0" : map11.get(id)
						.toString());
				Serie22.addData(map22.get(id) == null ? "0" : map22.get(id)
						.toString());
				Serie33.addData(map33.get(id) == null ? "0" : map33.get(id)
						.toString());

			}
			taskCount.addSeries(Serie0);
			taskCount.addSeries(Serie11);
			taskCount.addSeries(Serie22);
			taskCount.addSeries(Serie33);

		} else {

		}

		return taskCount;
	}

	@Override
	public List<Pie> countPie(TaskCondition taskcondition) {
		TaskCount taskCount = new TaskCount();
		Map<String, Integer> map = new HashMap<String, Integer>();
		Pie p1 = new Pie("刚生成",1);
		Pie p2 = new Pie("刚确认",1);
		Pie p3 = new Pie("待执行",1);
		Pie p4 = new Pie("已完成",1);
	 
		List<TTask> list = taskCountDal.queryTaskInfo(taskcondition);
		List<Pie> lst = new ArrayList<Pie>();
		if ("1".equals(taskcondition.getGroup())) {
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				TTask tTask = (TTask) iterator.next();
				switch (tTask.getC_STATUS().intValue()) {
				case 0:
					p1.setY(p1.getY()+1);
					break;
				case 11:
					p2.setY(p2.getY()+1);
					break;
				case 22:
					p3.setY(p3.getY()+1);
					break;
				case 33:
					p4.setY(p4.getY()+1);
					break;

				}

			}
			lst.add(p1);
			lst.add(p2);
			lst.add(p3);
			lst.add(p4);
		} else {

		}
		return lst;
	}

	public TaskCountDal getTaskCountDal() {
		return taskCountDal;
	}

	public void setTaskCountDal(TaskCountDal taskCountDal) {
		this.taskCountDal = taskCountDal;
	}

}
