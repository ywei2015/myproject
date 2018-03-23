package com.talkweb.xwzcxt.dal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.talkweb.twdpe.dal.batis.spring.SessionDaoSupport;
import com.talkweb.xwzcxt.pojo.TSdPublicPojo;

public class TSdPublicDal extends SessionDaoSupport {
	public List getPublicNodesTree() {
		return this.getSession().selectList("TSdPublic.getPublicNodesTree");
	}

	public TSdPublicPojo getPublicNodeById(String id) {
		return (TSdPublicPojo) this.getSession().selectOne("TSdPublic.getPublicNodeById", id);
	}

	public int addPublicNode(TSdPublicPojo t) {
		return this.getSession().insert("TSdPublic.addPublicNode", t);
	}

	public Map updatePublicNodeById(TSdPublicPojo t) {
		Map ret = new HashMap();
		TSdPublicPojo oldValue = this.getPublicNodeById(t.getcPublicId());
		if(oldValue != null){
			ret.put("oldValue", oldValue);
			ret.put("oldValue_datafield", "TWXWZC.T_SD_PUBLIC");
		}
		int type = this.getSession().update("TSdPublic.updatePublicNodeById", t);
		ret.put("type", type);
		return ret;
	}

	public Map deletePublicNodesById(TSdPublicPojo t) {
		Map ret = new HashMap();
		TSdPublicPojo oldValue = this.getPublicNodeById(t.getcPublicId());
		if(oldValue != null){
			ret.put("oldValue", oldValue);
			ret.put("oldValue_datafield", "TWXWZC.T_SD_PUBLIC");
		}
		int type = this.getSession().update("TSdPublic.deletePublicNodesById", t);
		ret.put("type", type);
		return ret;
	}
}