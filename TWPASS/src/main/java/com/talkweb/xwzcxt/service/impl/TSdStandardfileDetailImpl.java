package com.talkweb.xwzcxt.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.xwzcxt.dal.TSdStandardfileDetailDal;
import com.talkweb.xwzcxt.pojo.MyLogPojo;
import com.talkweb.xwzcxt.pojo.TSdStandardfileDetailPojo;
import com.talkweb.xwzcxt.service.MyLogService;
import com.talkweb.xwzcxt.service.TSdStandardfileDetailI;
import com.talkweb.xwzcxt.util.DataTypeParseUtil;

public class TSdStandardfileDetailImpl implements TSdStandardfileDetailI {
	@Autowired
	private TSdStandardfileDetailDal tSdStandardfileDetailDal;

	@Autowired
	private MyLogService logService;
	
	private void ChangeModelList(List<TSdStandardfileDetailPojo> plist) {
		if (plist != null && plist.size() > 0) {
			for (TSdStandardfileDetailPojo t : plist) {
				switch (t.getcStatus().intValue()) {
					case 1:
						t.setcStatusName("待制订");
						break;
					case 2:
						t.setcStatusName("制订中");
						break;
					case 3:
						t.setcStatusName("修订中");
						break;
					case 4:
						t.setcStatusName("已发布");
						break;
					default:
						break;
				}
				switch (t.getcSystag().intValue()) {
					case 1:
						t.setcSystagName("质量管理体系用");
						break;
					case 2:
						t.setcSystagName("职业健康安全管理体系用");
						break;
					case 3:
						t.setcSystagName("两体系共用");
						break;
					case 4:
						t.setcSystagName("其他");
						break;
					default:
						break;
				}
				if (t.getcStatusTime() != null) {
					String cStatusTime = DataTypeParseUtil.getDateString(t.getcStatusTime());
					t.setcStatusTimeString(cStatusTime.substring(0, cStatusTime.indexOf(" ")));
				}
				if (t.getcCreatetime() != null) {
					t.setcCreatetimeString(DataTypeParseUtil.getDateString(t.getcCreatetime()));
				}
				if (t.getcModifytime() != null) {
					t.setcModifytimeString(DataTypeParseUtil.getDateString(t.getcModifytime()));
				}
			}
		}
	}

	@Override
	public Pagination getAllTSdStandardfileByConditions(Map params, Pagination page) {
		Pagination pagination = tSdStandardfileDetailDal.getAllTSdStandardfileByConditions(params, page);
		ChangeModelList(pagination.getList());
		return pagination;
	}

	@Override
	public TSdStandardfileDetailPojo getTSdStandardfileDetailById(String id) {
		return tSdStandardfileDetailDal.getTSdStandardfileDetailById(id);
	}

	@Override
	public int addTSdStandardfile(TSdStandardfileDetailPojo t, HttpServletRequest request, HttpServletResponse response) {
		int type = tSdStandardfileDetailDal.addTSdStandardfile(t);
		if(type != 0){
			logService.addLogInfo(request, response, "1", "过程行为支持系统", "", "标准管理", "标准文件明细表", MyLogPojo.add, "标准文件明细表-新增", "成功", "1", "TWXWZC.T_SD_STANDARDFILE_DETAIL", "", t.toString());
		}
		return type;
		
	}

	@Override
	public int updateTSdStandardfileById(TSdStandardfileDetailPojo t, HttpServletRequest request, HttpServletResponse response) {
		TSdStandardfileDetailPojo oldValue = tSdStandardfileDetailDal.getTSdStandardfileDetailById(t.getcFiledetailId());
		int type = tSdStandardfileDetailDal.updateTSdStandardfileById(t);
		if(type != 0){
			logService.addLogInfo(request, response, "1", "过程行为支持系统", "", "标准管理", "标准文件明细表", MyLogPojo.change, "标准文件明细表-修改", "成功", "1", "TWXWZC.T_SD_STANDARDFILE_DETAIL", oldValue.toString(), t.toString());
		}
		return type;
	}

	@Override
	public void deleteTSdStandardfileById(String ids, HttpServletRequest request, HttpServletResponse response) {
		String idsArray[] = ids.split(",");
		List<String> idsList = new ArrayList<String>();
		for (String id: idsArray) {
			idsList.add(id);
		}
		Map deleteTSdStandardfileByIdRet = tSdStandardfileDetailDal.deleteTSdStandardfileById(idsList);
		int type = (Integer)deleteTSdStandardfileByIdRet.get("type");
		if(type != 0){
			List<TSdStandardfileDetailPojo> searchDeleteTSdStandardfileByIdRet = (List<TSdStandardfileDetailPojo>)deleteTSdStandardfileByIdRet.get("searchDeleteTSdStandardfileByIdRet");
			String searchDeleteTSdStandardfileByIdRet_datafield = (String)deleteTSdStandardfileByIdRet.get("searchDeleteTSdStandardfileByIdRet_datafield");
			logService.addLogInfo(request, response, "1", "过程行为支持系统", "", "标准管理", "标准文件明细表", MyLogPojo.del, "标准文件明细表-删除", "成功", "1", searchDeleteTSdStandardfileByIdRet_datafield,searchDeleteTSdStandardfileByIdRet.toString(), "");
		}
	}
}