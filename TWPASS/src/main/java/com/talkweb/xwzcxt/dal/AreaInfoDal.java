package com.talkweb.xwzcxt.dal;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.talkweb.twdpe.dal.batis.spring.SessionDaoSupport;
import com.talkweb.xwzcxt.pojo.AreaInfoPojo;



public class AreaInfoDal extends SessionDaoSupport{
	

		public AreaInfoPojo getAreaInfoByID(String bd) {
			BigDecimal id = new BigDecimal(bd);
			return (AreaInfoPojo) (this.getSession().selectOne(
					"AreaInfo.getAreaInfoById", id));
		}
		
		public List<AreaInfoPojo> getAreaType() {
			return  (List<AreaInfoPojo>)this.getSession().selectList(
					"AreaInfo.getAreaType");
		}
        
	    public void updateArea(AreaInfoPojo area)
	    {
	    	
	        this.getSession().update("AreaInfo.updateArea", area);

	    }
	    
	    public void updateScanCode(AreaInfoPojo area)
	    {
	    	
	        this.getSession().update("AreaInfo.updateScanCode",area);

	    }
	    
	    public void updateScanDtails(AreaInfoPojo area)
	    {
	    	
	        this.getSession().update("AreaInfo.updateScanDtails",area);

	    }
	    
	    public void addArea(AreaInfoPojo area)
	    {
	        this.getSession().insert("AreaInfo.insertArea", area);

	    }
	    
		public int deleteArea(Map map){
			
			return getSession().update("AreaInfo.deleteArea", map);
		}
	    
		
		public String getNextAeraId() {
			return (this.getSession().selectOne("AreaInfo.getNextAeraId")).toString();
		}
		
		public int printCode(Map param) {
			return getSession().update("AreaInfo.printCode",param);
		}
		
		public String getCurrentPrintCount(String id){
			return (String)getSession().selectOne("AreaInfo.getCurrentPrintCount",id);
		}
}
