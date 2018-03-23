package com.talkweb.xwzcxt.dal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.talkweb.twdpe.dal.batis.spring.SessionDaoSupport;
import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.xwzcxt.pojo.TSdStandardfileDetailPojo;

public class TSdStandardfileDetailDal extends SessionDaoSupport {
	public Pagination getAllTSdStandardfileByConditions(Map params, Pagination page) {
		List li = this.getSession().selectList("TSdStandardfileDetail.getAllTSdStandardfileByConditions", params, page);
		if (li != null)
			page.setList(li);
		return page;
	}

	public TSdStandardfileDetailPojo getTSdStandardfileDetailById(String id) {
		return (TSdStandardfileDetailPojo) (this.getSession().selectOne("TSdStandardfileDetail.getTSdStandardfileDetailById", id));
	}

	public int addTSdStandardfile(TSdStandardfileDetailPojo t) {
		return this.getSession().insert("TSdStandardfileDetail.addTSdStandardfile", t);
	}

	public int updateTSdStandardfileById(TSdStandardfileDetailPojo t) {
		return this.getSession().update("TSdStandardfileDetail.updateTSdStandardfileById", t);
	}

	public Map deleteTSdStandardfileById(List<String> ids) {
		Map ret = new HashMap();
		List<TSdStandardfileDetailPojo> searchDeleteTSdStandardfileByIdRet = this.searchDeleteTSdStandardfileById(ids);
		int type = this.getSession().delete("TSdStandardfileDetail.deleteTSdStandardfileById", ids);
		ret.put("type", type);
		if(type != 0){
			ret.put("searchDeleteTSdStandardfileByIdRet", searchDeleteTSdStandardfileByIdRet);
			ret.put("searchDeleteTSdStandardfileByIdRet_datafield", "TWXWZC.T_SD_STANDARDFILE_DETAIL");
		}
		return ret;
	}
	
	public List<TSdStandardfileDetailPojo> searchDeleteTSdStandardfileById(List<String> ids){
		return this.getSession().selectList("TSdStandardfileDetail.searchDeleteTSdStandardfileById", ids);
	}
}