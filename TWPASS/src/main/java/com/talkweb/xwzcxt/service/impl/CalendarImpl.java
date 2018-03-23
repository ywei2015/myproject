package com.talkweb.xwzcxt.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.xwzcxt.dal.CalendarDal;
import com.talkweb.xwzcxt.pojo.CalendarPojo;


public class CalendarImpl {
      
	@Autowired
	private  CalendarDal calendarDal;
	
	public List<CalendarPojo> getCurrentRecordByPage(Object param, Pagination pagination){
		    List<CalendarPojo> list_cal = new ArrayList<CalendarPojo>();
	        List<?> dt = calendarDal.getCurrentRecordByPage(param, pagination);
	        int rowCount = dt.size();
	        if (rowCount > 0)
	        {
	            try
	            {
	            	CalendarPojo model;
	                for (int n = 0; n < rowCount; n++)
	                {
	                    model = new CalendarPojo();
	                    model = (CalendarPojo) dt.get(n);
		
		                switch(Integer.parseInt(model.getC_theory_isworkday())){
			                      case 0:
			                           model.setC_theory_isworkday("非工作日");
			                           break;
			                      case 1:
			                           model.setC_theory_isworkday("工作日");
			                           break;                                    
                                  }                          
                       switch(Integer.parseInt(model.getC_fact_isworkday())){
			                        case 0:
			                              model.setC_fact_isworkday("非工作日");
			                              break;
			                        case 1:
			                              model.setC_fact_isworkday("工作日");
			                              break;                                
                                  }
			
		
                          list_cal.add(model);
	                }
	            } catch (Exception ex){
	                // log.Error("/r/n初始化实体对象信息失败:GetBoxingProductRecordByPage" +
	                // ex.Message);
	            	ex.printStackTrace();
	            }
	        }
	     return list_cal;
	}
	
	public Object getCalDataBySeq(String seq){
		return calendarDal.getCalDataBySeq(seq);
	}
	
	public int modifyCalData(CalendarPojo param){
		return calendarDal.modifyCalData(param);
	}
}
