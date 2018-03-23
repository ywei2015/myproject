package com.talkweb.xwzcxt.service.impl;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.xwzcxt.dal.AreaInfoDal;
import com.talkweb.xwzcxt.pojo.AreaInfoPojo;


public class AreaInfoImpl {
	
	@Autowired
	private AreaInfoDal areaInfoDal;
	
	public AreaInfoPojo getAreaInfoByID(String id) {
		AreaInfoPojo a=(AreaInfoPojo)areaInfoDal.getAreaInfoByID(id);	    
		return  a;
	}
	
	public List<AreaInfoPojo> getAreaType() {
		List<AreaInfoPojo> a=areaInfoDal.getAreaType();    
		return  a;
	}
	
    public void updateArea(AreaInfoPojo area)
    {
        areaInfoDal.updateArea(area);

    }
    
    public void updateScanCode(AreaInfoPojo area)
    {
    	
        areaInfoDal.updateScanCode(area);

    }
    
    
    public void updateScanDtails(AreaInfoPojo area)
    {
    	
        areaInfoDal.updateScanDtails(area);

    }
    
    public void addArea(AreaInfoPojo area)
    {
    	areaInfoDal.addArea(area);

    }
    
	public int deleteArea(Map map) {
		
		return areaInfoDal.deleteArea(map);
	}
	
	public int printCode(Map param) {
		
		return areaInfoDal.printCode(param);
	}
	
	public String getCurrentPrintCount(String id){
		return areaInfoDal.getCurrentPrintCount(id);
	}
	
	public String getNextAeraId() {
		return areaInfoDal.getNextAeraId();
	}
}
