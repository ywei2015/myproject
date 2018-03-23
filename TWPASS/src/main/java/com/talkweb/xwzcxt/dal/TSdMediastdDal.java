package com.talkweb.xwzcxt.dal;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.talkweb.twdpe.dal.batis.spring.SessionDaoSupport;
import com.talkweb.xwzcxt.pojo.TSdMediastdPojo;

public class TSdMediastdDal extends SessionDaoSupport {
	public List getAllMediaStdById(Map params) {
		return this.getSession().selectList("TSdMediastd.getAllMediaStdById", params);
	}

	public Map addMediaFile(String id, int type, String cCreator, String cid) {
		Map params = new HashMap();
		Map ret = new HashMap();
		List<String> cidList = new ArrayList<String>();
		cidList.add(cid);
		params.put("type", type);
		params.put("cid", cidList);
		ret = this.deleteMediaStdById(params);
		
		List<TSdMediastdPojo> r = new ArrayList<TSdMediastdPojo>();
		if (!id.equals("")) {
			String ids[] = id.split(",");
			
			for (int i = 0; i < ids.length; i++) {
				TSdMediastdPojo t = new TSdMediastdPojo();
				t.setcId(UUID.randomUUID().toString());
				t.setcSdfileId(ids[i]);
				t.setcMediastdType(new BigDecimal(type));
				t.setcCreator(cCreator);
				t.setcCreatetime(new Date());
				switch (type) {
					case 1:
						t.setcActnodeId(cid);
						break;
					case 2:
						t.setcTempletId(cid);
						break;
					case 3:
						t.setcStepId(cid);
						break;
					default:
						break;
				}
				this.getSession().insert("TSdMediastd.addMediaStd", t);
				r.add(t);
			}
			ret.put("r", r);
			ret.put("r_datafield", "TWXWZC.T_SD_MEDIASTD");
		}
		return ret;
	}

	public int updateMediaStdById(TSdMediastdPojo t) {
		return this.getSession().update("TSdMediastd.updateMediaStdById", t);
	}

	public Map deleteMediaStdById(Map params) {
		Map ret = new HashMap();
		List<TSdMediastdPojo> searchDeleteMediaStdByIdResultMap = this.searchDeleteMediaStdById(params);
		int deleteMediaStdByIdRet = this.getSession().delete("TSdMediastd.deleteMediaStdById", params);
		ret.put("deleteMediaStdByIdRet", deleteMediaStdByIdRet);
		if(deleteMediaStdByIdRet != 0){
			ret.put("searchDeleteMediaStdByIdResultMap", searchDeleteMediaStdByIdResultMap);
			ret.put("searchDeleteMediaStdByIdResultMap_datafield", "TWXWZC.T_SD_MEDIASTD");
		}
		return ret;
	}

	public Map getFileInfoById(String fid) {
		return (Map) this.getSession().selectOne("TSdMediastd.getFileInfoById", fid);
	}
	/**
	 * @author ZhangZhiheng
	 * @param params 与deleteMediaStdById参数相同
	 * @return 返回将要删除的数据（oldValue）
	 */
	public List<TSdMediastdPojo> searchDeleteMediaStdById(Map params){
		return  this.getSession().selectList("TSdMediastd.searchDeleteMediaStdById", params);
	}
	
	
}