package com.talkweb.xwzcxt.dal;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.talkweb.twdpe.dal.batis.spring.SessionDaoSupport;
import com.talkweb.xwzcxt.pojo.TSdTempletScancodePojo;

public class TSdTempletScancodeDal extends SessionDaoSupport {
	public List<TSdTempletScancodePojo> getScancodeByTempletId(Map params) {
		return this.getSession().selectList("TSdTempletScancode.getScancodeByTempletId", params);
	}

	public Map addcJobObjects(String cIsscan, String id, int type, String cCreator, String cid) {
		Map params = new HashMap();
		Map ret = new HashMap();
		List<String> cidList = new ArrayList<String>();
		cidList.add(cid);
		params.put("type", type);
		params.put("cid", cidList);
		ret = this.deleteScancodesById(params);
		List<TSdTempletScancodePojo> r = new ArrayList<TSdTempletScancodePojo>();
		if (cIsscan.equals("1") && !id.equals("")) {
			String ids[] = id.split(",");
			for (int i = 0; i < ids.length; i++) {
				TSdTempletScancodePojo t = new TSdTempletScancodePojo();
				t.setcId(UUID.randomUUID().toString());
				t.setcBasecodeId(ids[i]);
				t.setcCreator(cCreator);
				t.setcCreatetime(new Date());
				t.setcType(Integer.toString(type));
				t.setcIsdelete("0");
				switch (type) {
					case 1:
						t.setcTempletId(cid);
						break;
					case 2:
						t.setcTaskId(new BigDecimal(cid));
						break;
					default:
						break;
				}
				this.getSession().insert("TSdTempletScancode.addScancode", t);
				r.add(t);
			}
			ret.put("r", r);
			ret.put("r_datafield", "TWXWZC.T_SD_TEMPLET_SCANCODE");
		}
		return ret;
	}

	public int updateScancodeById(TSdTempletScancodePojo t) {
		return this.getSession().update("TSdTempletScancode.updateScancodeById", t);
	}

	public Map deleteScancodesById(Map params) {
		Map ret = new HashMap();
		List<TSdTempletScancodePojo> oldValue = this.searchDeleteScancodesById(params);
		int type = this.getSession().delete("TSdTempletScancode.deleteScancodesById", params);
		ret.put("deleteScancodesByIdoldValueType", type);
		if(type != 0){
			ret.put("deleteScancodesByIdoldValue", oldValue);
			ret.put("deleteScancodesByIdoldValue_datafield", "TWXWZC.T_SD_TEMPLET_SCANCODE");
		}
		return ret;
	}
	/**
	 * @author ZhangZhiheng
	 * @param params 与deleteScancodesById相同
	 * @return 返回将要删除的数据(oldValue)
	 */
	public List<TSdTempletScancodePojo> searchDeleteScancodesById(Map params){
		return this.getSession().selectList("TSdTempletScancode.searchDeleteScancodesById", params);
	}
}