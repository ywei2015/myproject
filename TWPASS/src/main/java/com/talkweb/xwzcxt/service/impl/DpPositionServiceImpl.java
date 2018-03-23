package com.talkweb.xwzcxt.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.xwzcxt.dal.DpPositionDal;
import com.talkweb.xwzcxt.dal.TaskDal;
import com.talkweb.xwzcxt.pojo.DpPosition;
import com.talkweb.xwzcxt.service.DpPositionServiceI;
import com.talkweb.xwzcxt.vo.DpPositionVo;

public class DpPositionServiceImpl implements DpPositionServiceI {

	@Autowired
	private DpPositionDal dpPositionDal;
	@Autowired
	private TaskDal taskDal;

	@Override
	public List<DpPositionVo> getDpPositionByOrgId(String id) {
		return ChangeModelList(dpPositionDal.getPositionByOrgId(id));
	}

	private DpPositionVo ChangeModel(DpPosition dp) {
		DpPositionVo dpv = new DpPositionVo();
		if (dp != null) {
			BeanUtils.copyProperties(dp, dpv);
			dpv.setPositionChilds(getPositionByPositionID(dpv.getPositionid().toString()));
		}
		
		return dpv;
	}

	private List<DpPositionVo> ChangeModelList(List<DpPosition> dplist) {
		List<DpPositionVo> dpvlist = new ArrayList<DpPositionVo>();
		for (DpPosition l : dplist) {
			dpvlist.add(ChangeModel(l));
		}
		return dpvlist;
	}

	@Override
	public List<DpPositionVo> getPositionByPositionID(String positionid) {
		List<DpPositionVo> plist = ChangeModelList(dpPositionDal.getPositionByPositionID(positionid));
		if(plist!=null && plist.size() >0)
		{
			for (DpPositionVo l : plist) {
				List<DpPositionVo> pchildlist = ChangeModelList(dpPositionDal.getPositionByPositionID(l.getPositionid().toString()));
				l.setPositionChilds(pchildlist);
			}
		}
		return plist;
	}

	@Override
	public DpPositionVo getPositionByUserID(String uid) {
		return ChangeModel(dpPositionDal.getPositionByUserId(uid));
	}

	@Override
	public String getPositionNameByID(String pid) {
		return dpPositionDal.getPositionNameByID(pid);
	}

	@Override
	public String getUserNameByID(String pid) {
		return dpPositionDal.getUserNameByID(pid);
	}

	@Override
	public List<DpPositionVo> getAllPositon() {
		return ChangeModelList(dpPositionDal.getAllPositon());
	}
		
	
	// 根据用户ID获取部门信息 0.orgid, 1.orgname, 2.orgpath, 3 orgdepartname
	public String[] getDepartByPositionID(String pid) {
		Map orglist = dpPositionDal.getDepartByPositionID(pid);
		String[] strorginfo = new String[4];

		if (orglist != null && orglist.containsKey("PATH")) {
			strorginfo[0] = orglist.get("ORGID").toString();
			strorginfo[1] = orglist.get("ORGNAME").toString();
			String orgpath = orglist.get("PATH").toString();
			strorginfo[2] = orgpath;
			String[] strlist = orgpath.split(";");
			Map dpt = taskDal.getDepartmentNameByID(strlist[1]);
			if (dpt != null && dpt.containsKey("ORGNAME")) {
				strorginfo[3] = dpt.get("ORGNAME").toString();
			}
		}
		return strorginfo;
	}
	
	public String getITMinisterOrgId(String orgId){
		String itMinisterOrgId = dpPositionDal.getITMinisterOrgId(orgId);
		return itMinisterOrgId;
	}

}
