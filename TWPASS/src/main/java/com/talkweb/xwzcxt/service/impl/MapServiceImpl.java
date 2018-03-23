package com.talkweb.xwzcxt.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.xwzcxt.dal.MapDal;
import com.talkweb.xwzcxt.service.MapServiceI;
import com.talkweb.xwzcxt.util.DataTypeParseUtil;

public class MapServiceImpl implements MapServiceI {
	@Autowired
	private MapDal mapDal;

	@Override
	public List<Map> getAreaInfo(Map params) {
		String cObjectTypeid = params.get("cObjectTypeid").toString();
		List<Map> areaInfo = new ArrayList<Map>();

		List<Map> index = mapDal.getAreaIndex(params);

		for (int i = 0; i < index.size(); i++) {
			String indexs[] = index.get(i).get("C_MAP_POINT").toString().split(",");
			if (indexs[0].equals("0") && indexs[1].equals("0") && indexs[2].equals("0") && indexs[3].equals("0")) {
				// do nothing
			} else {
				Map temp = new HashMap();
				temp.put("x1", indexs[0]);
				temp.put("y1", indexs[1]);
				temp.put("x2", indexs[2]);
				temp.put("y2", indexs[3]);

				String cAreaId = index.get(i).get("C_BASEDATA_ID").toString();
				temp.put("cAreaId", cAreaId);
				temp.put("cAreaFullname", index.get(i).get("C_AREA_FULLNAME").toString().replace("长沙卷烟厂,", ""));

				Map sumMap = new HashMap();
				sumMap.put("cAreaId", cAreaId);
				sumMap.put("cObjectTypeid", cObjectTypeid);
				temp.put("sum", mapDal.getAreaStatus(sumMap));

				Map checkMap = new HashMap();
				checkMap.put("cAreaId", cAreaId);
				checkMap.put("cCheckstatus", "1");
				checkMap.put("cIserror", "0");
				checkMap.put("cObjectTypeid", cObjectTypeid);
				temp.put("check", mapDal.getAreaStatus(checkMap));

				Map uncheckMap = new HashMap();
				uncheckMap.put("cAreaId", cAreaId);
				uncheckMap.put("cCheckstatus", "0");
				uncheckMap.put("cIserror", "0");
				uncheckMap.put("cObjectTypeid", cObjectTypeid);
				temp.put("uncheck", mapDal.getAreaStatus(uncheckMap));

				Map errorMap = new HashMap();
				errorMap.put("cAreaId", cAreaId);
				errorMap.put("cIserror", "1");
				errorMap.put("cObjectTypeid", cObjectTypeid);
				temp.put("error", mapDal.getAreaStatus(errorMap));

				areaInfo.add(temp);
			}
		}

		return areaInfo;
	}

	@Override
	public List<Map> getAreaIcon(Map params) {
		return mapDal.getAreaIcon(params);
	}

	@Override
	public Map getAreaIconDetailById(Map params) {
		Map m = mapDal.getAreaIconDetailById(params);
		m.put("C_AREA_FULLNAME", m.get("C_AREA_FULLNAME").toString().replace("长沙卷烟厂,", ""));
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			if (m.get("C_PLANCHECKTIME") != null) {
				m.put("C_PLANCHECKTIME", DataTypeParseUtil.getDateString(sdf.parse(m.get("C_PLANCHECKTIME").toString())));
			}
			if (m.get("C_CHECKTIME") != null) {
				m.put("C_CHECKTIME", DataTypeParseUtil.getDateString(sdf.parse(m.get("C_CHECKTIME").toString())));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return m;
	}
}