package com.talkweb.xwzcxt.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.twdpe.base.org.pojo.Org;
import com.talkweb.xwzcxt.dal.PositionBindOrgDal;
import com.talkweb.xwzcxt.service.PositionBindOrgService;

public class PositionBindOrgServiceImpl implements PositionBindOrgService {
	@Autowired
	public PositionBindOrgDal positionBindOrgDal;

	public PositionBindOrgDal getPositionBindOrgDal() {
		return positionBindOrgDal;
	}

	public void setPositionBindOrgDal(PositionBindOrgDal positionBindOrgDal) {
		this.positionBindOrgDal = positionBindOrgDal;
	}

	@Override
	public List<Org> unionPositionAndOrg() {
		return positionBindOrgDal.unionPositionAndOrg(null);
	}

	public List<Org> unionPositionAndOrg(Map params) {
		return positionBindOrgDal.unionPositionAndOrg(params);
	}
}