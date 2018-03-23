package com.talkweb.xwzcxt.dal;

import java.util.List;

import com.talkweb.twdpe.dal.batis.spring.SessionDaoSupport;
import com.talkweb.xwzcxt.pojo.TSdActnodeItemPojo;

public class TSdActnodeItemDal extends SessionDaoSupport {
	public List<TSdActnodeItemPojo> getActnodeItemsByActnodeID(String id) {
		return this.getSession().selectList("TSdActnodeItem.getActnodeItemByActnodeID", id);
	}

	public void addActnodeItem(TSdActnodeItemPojo actnodeItem) {
		this.getSession().insert("TSdActnodeItem.addActnodeItem", actnodeItem);
	}
}