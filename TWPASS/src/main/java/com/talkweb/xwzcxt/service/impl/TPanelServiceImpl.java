package com.talkweb.xwzcxt.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.xwzcxt.dal.TPanelDal;
import com.talkweb.xwzcxt.pojo.TPanelPojo;
import com.talkweb.xwzcxt.service.TPanelServiceI;
import com.talkweb.xwzcxt.util.DataTypeParseUtil;

public class TPanelServiceImpl implements TPanelServiceI {
	@Autowired
	private TPanelDal tPanelDal;

	private void ChangeModelList(List<TPanelPojo> l) {
		if (l != null && l.size() > 0) {
			for (TPanelPojo t : l) {
				ChangeModel(t);
			}
		}
	}

	private void ChangeModel(TPanelPojo p) {
		if (null != p.getcUsertype()) {
			switch (p.getcUsertype().intValue()) {
				case 1:
					p.setcUsertypeName("执行人");
					break;
				case 2:
					p.setcUsertypeName("验证人");
					break;
				case 3:
					p.setcUsertypeName("评价人");
					break;
				case 4:
					p.setcUsertypeName("反馈人");
					break;
				case 5:
					p.setcUsertypeName("抄送人");
					break;
				default:
					p.setcUsertypeName("");
					break;
			}
		}
		if (null != p.getcIsread()) {
			p.setcIsreadName(p.getcIsread().equals("0") ? "未查阅" : "已查阅");
		}
		if (null != p.getcReadtime()) {
			p.setcReadtime(DataTypeParseUtil.DateConvert(p.getcReadtime()));
			p.setcReadtimeString(DataTypeParseUtil.getDateString(p.getcReadtime()));
		}
	}

	@Override
	public Map getPanelInfoCount(Map params) {
		Map panelInfo = new HashMap();
		int type[] = {1, 2, 11, 12};
		params.put("cIsread", "0");
		for (int i = 0; i < type.length; i++) {
			params.put("cPanelType", type[i]);
			panelInfo.put("information" + type[i], tPanelDal.getPanelInfoCount(params));
		}
		return panelInfo;
	}

	@Override
	public Pagination getPanelInfoList(Map params, Pagination page) {
		Pagination pagination = tPanelDal.getPanelInfoList(params, page);
		List<TPanelPojo> l = pagination.getList();
		ChangeModelList(l);
		return pagination;
	}

	@Override
	public int readInformation(Map params) {
		return tPanelDal.readInformation(params);
	}
}