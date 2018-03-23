package com.talkweb.xwzcxt.dal;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.talkweb.twdpe.dal.batis.spring.SessionDaoSupport;
import com.talkweb.xwzcxt.pojo.DpPosition;

public class DpPositionDal extends SessionDaoSupport {

	@SuppressWarnings("unchecked")
	public List<DpPosition> getAllPositon() {
		return (List<DpPosition>) (this.getSession()
				.selectList("dpPosition.getAllPosition"));
	}

	@SuppressWarnings("unchecked")
	public List<DpPosition> getPositionByOrgId(String id) {
		BigDecimal bd = new BigDecimal(id);
		return (List<DpPosition>) (this.getSession().selectList(
				"dpPosition.selectPositionByOrgID", bd));
	}

	@SuppressWarnings("unchecked")
	public List<DpPosition> getPositionByPositionID(String id) {
		BigDecimal bd = new BigDecimal(id);
		return (List<DpPosition>) (this.getSession().selectList(
				"dpPosition.selectPositionByPositionID", bd));
	}

	public DpPosition getPositionByUserId(String id) {
		BigDecimal bd = new BigDecimal(id);
		return (DpPosition) (this.getSession().selectOne(
				"dpPosition.selectPositionByUserID", bd));
	}

	public String getPositionNameByID(String id) {
		BigDecimal bd = new BigDecimal(id);
		return (this.getSession().selectOne("dpPosition.getPositionNameByID",
				bd)).toString();
	}

	public String getUserNameByID(String id) {
		BigDecimal bd = new BigDecimal(id);
		return (this.getSession().selectOne("dpPosition.getUserNameByID",
				bd)).toString();
	}

	@SuppressWarnings("unchecked")
	public Map<String,String> getDepartByPositionID(String id)
	{
		BigDecimal bd = new BigDecimal(id);
		return  ((Map<String,String>)(this.getSession().selectOne("dpPosition.getDepartByPositionID",bd)));
	}
	
	public String getITMinisterOrgId(String orgId){
		return (this.getSession().selectOne("dpPosition.ITMinisterOrgId",
				orgId)).toString();
	}
}
