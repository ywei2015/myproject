package com.talkweb.xwzcxt.dal;

import java.util.List;
import java.util.Map;

import com.talkweb.twdpe.dal.batis.spring.SessionDaoSupport;
import com.talkweb.twdpe.base.org.pojo.Org;

public class PositionBindOrgDal extends SessionDaoSupport{
	@SuppressWarnings("unchecked")
	public List<Org> unionPositionAndOrg(){
		return this.getSession().selectList("positionBindOrg.unionPositionAndOrg");
	}

	@SuppressWarnings("unchecked")
	public List<Org> unionPositionAndOrg(Map params){
		return this.getSession().selectList("positionBindOrg.unionPositionAndOrg", params);
	}

	public String getOrgtypeByDataId(String dataid) {
		return (this.getSession().selectOne("positionBindOrg.getOrgtypeByDataId", dataid)).toString();
	}
}