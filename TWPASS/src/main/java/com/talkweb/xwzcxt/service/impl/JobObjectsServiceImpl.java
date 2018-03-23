package com.talkweb.xwzcxt.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.xwzcxt.dal.JobObjectsDal;
import com.talkweb.xwzcxt.log.Log;
import com.talkweb.xwzcxt.pojo.TTaskPointcheckPojo;
import com.talkweb.xwzcxt.pojo.TbIntegrateInfo;
import com.talkweb.xwzcxt.service.JobObjectsService;
import com.talkweb.xwzcxt.util.DataTypeParseUtil;

public class JobObjectsServiceImpl implements JobObjectsService {
    @Autowired
	private JobObjectsDal jobObjectsDal;
    
	@Override
	public List getTableTypeTree() {
      return jobObjectsDal.getTableTypeTree();
	}

	@Override
	public void getObjectInfo(Pagination page, Map tb) {
		jobObjectsDal.getObjectInfo(page, tb);
	}

	@Override
	public List getColumnsConfig(Map tb) {
		return jobObjectsDal.getColumnsConfig(tb);
	}

	@Override
	public int deleteObjects(Map cBasedataId) {
		
		return jobObjectsDal.deleteObjects(cBasedataId);
	}

	@Override
//	@Log(funcGroupId="JC_1",actionName="jObjects_updateObject.action",type="modify")
	public int updateObject(TbIntegrateInfo tb) {
		
		return jobObjectsDal.updateObject(tb);
	}

	@Override
	public int addObject(TbIntegrateInfo tb) {
		return jobObjectsDal.addObject(tb);
	}

	public String getMaxBasedataId(TbIntegrateInfo tb) {
		return jobObjectsDal.getMaxBasedataId(tb);
	}
	
	@Override
	public int printCode(Map param) {
		
		return jobObjectsDal.printCode(param);
	}

	@Override
	public String getOrgname(Map cOrgid){
		return jobObjectsDal.getOrgname(cOrgid);
	}
	
	@Override
	public TbIntegrateInfo getCurrentPrintCount(Map cBasedataId){
		return jobObjectsDal.getCurrentPrintCount(cBasedataId);
	}

	@Override
	public TbIntegrateInfo getTabletypeById(Map cTabletypeId) {
		return jobObjectsDal.getTabletypeById(cTabletypeId);
	}

	@Override
	public List getDynamicsByType(Map cTabletypeId) {
		return jobObjectsDal.getDynamicsByType(cTabletypeId);
	}

	@Override
	public int updateTabletype(TbIntegrateInfo tbIntegrateInfo) {
		return jobObjectsDal.updateTabletype(tbIntegrateInfo);
	}

	@Override
	public int addtTabletype(TbIntegrateInfo tbIntegrateInfo) {
		return jobObjectsDal.addtTabletype(tbIntegrateInfo);
	}

	@Override
	public int addDynamicCol(TbIntegrateInfo tbIntegrateInfo) {
		return jobObjectsDal.addDynamicCol(tbIntegrateInfo);
	}

	@Override
	public int deleteTabletype(Map cTabletypeId) {
		return jobObjectsDal.deleteTabletype(cTabletypeId);
	}

	@Override
	public int deleteTabletypeCol(Map cTabletypeId) {
		return jobObjectsDal.deleteTabletypeCol(cTabletypeId);
	}
	@Override
	public int getCountColum(Map cTabletypeId) {
		
		return jobObjectsDal.getCountColum(cTabletypeId);
	}

	@Override
	public int updateDynamicCol(Map map) {
		return jobObjectsDal.updateDynamicCol(map);
	}

	@Override
	public int getDynamicCount(Map cTabletypeId) {
		return jobObjectsDal.getDynamicCount(cTabletypeId);
	}

	@Override
	public int updateColCount(Map map) {
		return jobObjectsDal.updateColCount(map);
	}

	@Override
	public int deleteDynamicById(Map cDycolId) {
		return jobObjectsDal.deleteDynamicById(cDycolId);
	}

	@Override
	public int checkHasIntegrateData(Map cTabletypeId) {
		return jobObjectsDal.checkHasIntegrateData(cTabletypeId);
	}

	@Override
	public int verifyRepeatObject(TbIntegrateInfo tbIntegrateInfo) {
		
		return jobObjectsDal.verifyRepeatObject(tbIntegrateInfo);
	}

	@Override
	public List selectJobObjectTree(Map cBasedataUserCode) {
		return jobObjectsDal.selectJobObjectTree(cBasedataUserCode);
	}

	@Override
	public List getTableTypeInfo(Map cTabletypeId) {
		return jobObjectsDal.getTableTypeInfo(cTabletypeId);
	}

	@Override
	public int addTaskPointCheck(TTaskPointcheckPojo ttaskPointCheck) {
		return jobObjectsDal.addTaskPointCheck(ttaskPointCheck);
	}
  
	@Override
	public void queryPtObject(Pagination page, Map tb) {
		jobObjectsDal.queryPtObject(page, tb);
	}

	@Override
	public int updatePointCheckByCBasedataId(TTaskPointcheckPojo ttaskPointCheck) {
		return jobObjectsDal.updatePointCheckByCBasedataId(ttaskPointCheck);
	}

	@Override
	public List getObjectsToExl(Map param) {
		return jobObjectsDal.getObjectToExl(param);
	}

	@Override
	public int deletePointCheckByCBaseDataId(Map cBasedataId) {
		return jobObjectsDal.deletePointCheckByCBaseDataId(cBasedataId);
	}
	
	@Override
	public Pagination getAllTaskPointcheckInfo(Map params, Pagination page) {
		return ChangeModelListPage(jobObjectsDal.getAllTaskPointcheckInfo(params, page));
	}
	
	@Override
	public List<TTaskPointcheckPojo> getAllTaskPointcheckInfoWithoutPage(Map params) {
		return ChangeModelList(jobObjectsDal.getAllTaskPointcheckInfoWithoutPage(params));
	}
	
	private Pagination ChangeModelListPage(Pagination page) {
		ChangeModelList(page.getList());
		return page;
	}
	
	private List<TTaskPointcheckPojo> ChangeModelList(List<TTaskPointcheckPojo> l) {
		if (l != null && l.size() > 0) {
			for (TTaskPointcheckPojo t : l) {
				Boolean isCheck = false;
				Date checkTime = null, planchecktime = null;
				if (t.getcCheckstatus() != null) {
					t.setcCheckstatusName(t.getcCheckstatus().intValue() == 0 ? "未检查" : "已检查");
				}
				if (t.getcIserror() != null) {
					t.setcIserrorName((t.getcIserror().equals("0") && t.getcChecktime()!=null )? "正常" : "异常");
				}
				if (t.getcAreaFullname() != null) {
					String areaFullname = t.getcAreaFullname();
					t.setcAreaFullname(areaFullname.substring(areaFullname.indexOf("长沙卷烟厂,") + 6));
				}
				if (t.getcResettime() != null) {
					t.setcResettimeString(DataTypeParseUtil.getDateString(t.getcResettime()));
				}
				if (t.getcChecktime() != null) {
					isCheck = true;
					checkTime = t.getcChecktime();
					t.setcChecktimeString(DataTypeParseUtil.getDateString(checkTime));
				} else {
					checkTime = new Date();
				}
				if (t.getcPlanchecktime() != null) {
					planchecktime = t.getcPlanchecktime();
					t.setcPlanchecktimeString(DataTypeParseUtil.getDateString(planchecktime));
					long result = checkTime.getTime() - planchecktime.getTime();
					if (result > 0) {
						t.setcIslate("逾期");
					} else {
						if (isCheck) {
							t.setcIslate("及时");
						} else {
							t.setcIslate("—");
						}
					}
				} else {
					t.setcIslate("—");
				}
			}
		}
		return l;
	}

	@Override
	public List getAllOrgIds(Map orgId) {
		return jobObjectsDal.getOrgAllIds(orgId);
	}

	@Override
	public List getIntegrateById(List<String> ids) {
		return jobObjectsDal.getIntegrateById(ids);
	}
	
	@Override
	public List getTaskPointcheckInfoById(List<String> ids) {
		return jobObjectsDal.getTaskPointcheckInfoById(ids);
	}

	@Override
	public List getOrgList() {
		return jobObjectsDal.getOrgList();
	}
}
