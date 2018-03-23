package com.talkweb.xwzcxt.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.common.Common;
import com.talkweb.xwzcxt.common.XwzcxtCommon;
import com.talkweb.xwzcxt.common.XwzcxtCommon.Enum_Xwzcxt;
import com.talkweb.xwzcxt.dal.XwzcxtMngDal;
import com.talkweb.xwzcxt.pojo.BasicMouldPojo;
import com.talkweb.xwzcxt.pojo.InitAutoPojo;
import com.talkweb.xwzcxt.pojo.TaskMouldPojo;
import com.talkweb.twdpe.base.org.pojo.User;
import com.talkweb.twdpe.core.data.DataMap;
import com.talkweb.twdpe.core.data.IData;
import com.talkweb.twdpe.dal.jdbc.info.Pagination;

@SuppressWarnings({ "unused", "rawtypes", "unchecked" })
public class XwzcxtBasicImp {

    // 查询DAL类
    @Autowired
    XwzcxtMngDal xwzcxtMngDal;
   


    // / <summary>
	public List<BasicMouldPojo> GetCurrentRecordByPage(BasicMouldPojo basicMouldPojo, Pagination pagination,Enum_Xwzcxt enumX)
    {
        List<BasicMouldPojo> list_UsrStaffreg = new ArrayList();
        List<?> dt = xwzcxtMngDal.GetCurrentRecordByPage(pagination, basicMouldPojo,enumX);
        String status = null;
        int rowCount = dt.size();
        if (rowCount > 0)
        {
            try
            {
            	BasicMouldPojo model;
                for (int n = 0; n < rowCount; n++)
                {
                    model = new BasicMouldPojo();
                    model = (BasicMouldPojo) dt.get(n);
                    switch(enumX){
                        case TASK_BASIC_JOB:
                            switch(Integer.parseInt(model.getC_state())){
                                case 0:
                                    model.setC_theory_isworkday_name("失效");
                                    break;
                                case 1:
                                    model.setC_theory_isworkday_name("有效");
                                    break;                                    
                            }   
                            break;
                        //日历
                        case TASK_BASIC_CAL:
                            switch(Integer.parseInt(model.getC_theory_isworkday())){
                                case 0:
                                    model.setC_theory_isworkday_name("非工作日");
                                    break;
                                case 1:
                                    model.setC_theory_isworkday_name("工作日");
                                    break;                                    
                            }                          
                            switch(Integer.parseInt(model.getC_fact_isworkday())){
                                case 0:
                                    model.setC_fact_isworkday_name("非工作日");
                                    break;
                                case 1:
                                    model.setC_fact_isworkday_name("工作日");
                                    break;                                
                            }
                            break;
                        case TASK_BASIC_TYPE:
                            break;
                        case TASK_BASIC_SCHEDULE:
                            break;
                        case TASK_STANDARD_POINT:                            
                            switch(Integer.parseInt(model.getC_iskeyctrl())){
                                case 0:
                                    model.setC_iskeyctrl_name("非关键管控");
                                    break;
                                case 1:
                                    model.setC_iskeyctrl_name("关键管控");
                                    break;
                            }
                            switch(Integer.parseInt(model.getC_issequence())){
                                case 0:
                                    model.setC_issequence_name("可不按顺序执行");
                                    break;
                                case 1:
                                    model.setC_issequence_name("需要按顺序执行");
                                    break;
                            }
                            break;
                        case TASK_STANDARD_TYPE:
                            break;
                        case TASK_STANDARD_STANDARD:
                            switch(Integer.parseInt(model.getC_std_flag())){
                                case 0:
                                    model.setC_std_flag_name("手动");
                                    break;
                                case 1:
                                    model.setC_std_flag_name("自动");
                                    break;
                            }                            
                            switch(Integer.parseInt(model.getC_check_flag())){
                            case 0:
                                model.setC_check_flag_name("手动");
                                break;
                            case 1:
                                model.setC_check_flag_name("自动");
                                break;
                                
                            }                            
                            switch(Integer.parseInt(model.getC_errstd_flag())){
                            case 0:
                                model.setC_errstd_flag_name("手动");
                                break;
                            case 1:
                                model.setC_errstd_flag_name("自动");
                                break;
                                
                            }
                            break;                        
                        case TASK_RULE_TIME:
                        	if (model.getC_ctrl_type() != null){
	                            switch(Integer.parseInt(model.getC_ctrl_type())){
	                                case 1:
	                                    model.setC_ctrl_type_name("日管控");
	                                    break;
	                                case 2:
	                                    model.setC_ctrl_type_name("周管控");
	                                    break;
	                                case 3:
	                                    model.setC_ctrl_type_name("月管控");
	                                    break;
	                                case 4:
	                                    model.setC_ctrl_type_name("其他");
	                                    break;
	                            }
                        	}
                            if(model.getC_date_relate()!=null){
                                switch(Integer.parseInt(model.getC_date_relate())){
                                    case 0:
                                        model.setC_date_relate_name("当日");
                                        break;
                                    case 1:
                                        model.setC_date_relate_name("当底最后一个工作日");
                                        break;
                                    case 2:
                                        model.setC_date_relate_name("当周最后一个工作日");
                                        break;
    
                                }
                            }
                            if(model.getC_time_relate()!=null){
                                switch(Integer.parseInt(model.getC_time_relate())){
                                    case 0:
                                        model.setC_time_relate_name("绝对时间");
                                        break;
                                    case 1:
                                        model.setC_time_relate_name("间隔时长");
                                        break;
                                }
                            }
                            break;
                        case TASK_TOTAL_PERSON:
                            break;
                        case TASK_TOTAL_STATU:
                            break;
                    }
                    
                    //Add By Rita.Zhou for pageMoudle
                    /*
                    
                   
                   
                    
                    switch(Integer.parseInt(model.getC_iskeyctrl())){
	                    case 0:
	                    	model.setC_iskeyctrl_name("非关键管控");
	                    	break;
	                    case 1:
	                    	model.setC_iskeyctrl_name("关键管控");
	                    	break;
	                }
	                
	                switch(Integer.parseInt(model.getC_issequence())){
	                    case 0:
	                    	model.setC_issequence_name("可不按顺序执行");
	                    	break;
	                    case 1:
	                    	model.setC_issequence_name("需要按顺序执行");
	                    	break;
	                }
	                
	                switch(Integer.parseInt(model.getC_isgetnotify())){
	                    case 0:
	                    	model.setC_isgetnotify_name("不接收");
	                    	break;
	                    case 1:
	                    	model.setC_isgetnotify_name("接收");
	                    	break;
	                }
	                
	                switch(Integer.parseInt(model.getC_isrelay())){
	                    case 0:
	                    	model.setC_isrelay_name("不转发");
	                    	break;
	                    case 1:
	                    	model.setC_isrelay_name("转发");
	                    	break;
	                }*/
	                
                    //c_fact_isworkday
                    
                    list_UsrStaffreg.add(model);
                }
            } catch (Exception ex)
            {
                // log.Error("/r/n初始化实体对象信息失败:GetBoxingProductRecordByPage" +
                // ex.Message);
            	ex.printStackTrace();
            }
        }
        return list_UsrStaffreg;
    }
    // / <summary>
    // / 考勤基础信息分页查询
    // / </summary>
    // / <param name="pageIndex"></param>
    // / <param name="pageSize"></param>
    // / <param name="sort"></param>
    // / <returns></returns>
    public List<InitAutoPojo> GetInitBasicItem(String param,int action)
    {
        List<InitAutoPojo> list_UsrStaffreg = new ArrayList();
        List<?> dt = xwzcxtMngDal.GetCurrentTreeList(param,action);
        String id="",val="";
        int rowCount = dt.size();
        if (rowCount > 0)
        {
            try
            {
                BasicMouldPojo model;InitAutoPojo pojo;
                for (int n = 0; n < rowCount; n++)
                {
                    model = new BasicMouldPojo();
                    model = (BasicMouldPojo) dt.get(n);
                    pojo = new InitAutoPojo();
                    switch(action){
                        //岗位末节点
                        case 1:
                            id = model.getC_actnode_id();
                            val = model.getC_actnode_name();
                            break;
                        //岗位
                        case 2:
                            id = model.getPositionid();
                            val= model.getPosiname();
                            break;
                        //区域
                        case 3:
                            id = model.getArea();
                            val= model.getAreaname();
                            break;
                        //时间规则
                        case 4:
                            id = model.getC_timerule_id()+"";
                            val= model.getC_timerule_name();
                            break;
                        //任务列表
                        case 5:
                            id = String.valueOf(model.getC_tasktemplet_id());
                            val= model.getC_tasktemplet_name();
                            break;
                        //标准数据项列表
                        case 6:
                        case 7:
                            id = model.getC_actitem_id()+"";
                            val= model.getC_actitem_name();
                            break;
                    }
                    pojo.setId(id);
                    pojo.setValue(id);
                    pojo.setText(val);
                    pojo.setVal(val);
                    pojo.setPid("0");
                    list_UsrStaffreg.add(pojo);
                }
            } catch (Exception ex)
            {
                // log.Error("/r/n初始化实体对象信息失败:GetBoxingProductRecordByPage" +
                // ex.Message);
            }
        }
        return list_UsrStaffreg;
    }

    public  List<BasicMouldPojo> GetCurrentRecordByPage(BasicMouldPojo basicMouldPojo,Enum_Xwzcxt enumX)
    {
        List<BasicMouldPojo> list_UsrStaffreg = new ArrayList();
        List<?>  dt = xwzcxtMngDal.GetCurrentRecordList(basicMouldPojo,enumX);
        String status = null;
        int rowCount = dt.size();
        if (rowCount > 0)
        {
            try
            {
                BasicMouldPojo model;
                for (int n = 0; n < rowCount; n++)
                {
                    model = new BasicMouldPojo();
                    model = (BasicMouldPojo) dt.get(n);
                    
                    
                    
                    list_UsrStaffreg.add(model);
                }
            } catch (Exception ex)
            {
                // log.Error("/r/n初始化实体对象信息失败:GetBoxingProductRecordByPage" +
                // ex.Message);
            }
        }
        return list_UsrStaffreg;
    }
    
    
    public BasicMouldPojo queryTobaccoEntry (Enum_Xwzcxt enumX, BasicMouldPojo basicMouldPojo) {
        BasicMouldPojo resultPojo= (BasicMouldPojo)xwzcxtMngDal.GetCurrentRecord(basicMouldPojo,enumX);
        return resultPojo;
	}

    public boolean Delete (IData param, Enum_Xwzcxt curPro) {
    	IData param1 = new DataMap();
		String action = "";
    	return xwzcxtMngDal.DeleteData (action , param);
    }

	public boolean ExecuteInsert (Enum_Xwzcxt curPro, BasicMouldPojo param) {
		String action = "";
		switch (curPro)
		{
			 
				
		}		
		return xwzcxtMngDal.ExecuteInsert (action, param);
	}
	public boolean ExecuteUpdate (Enum_Xwzcxt curPro, BasicMouldPojo param) {
		String action = "";
		switch (curPro)
		{
			 
				
		}
		return xwzcxtMngDal.ExecuteUpdate (action, param);
	}
	public boolean ExecuteUpdate (Enum_Xwzcxt curPro, IData param) {
		String action = "";
		switch (curPro)
		{
			 
		}
		return xwzcxtMngDal.ExecuteUpdate (action, param);
	}
	
	//Add By Rita.Zhou for add functions
	public int addWorkObject(BasicMouldPojo param){
		return xwzcxtMngDal.insertWorkObject(param);
	}
	
	public int addActiveStandard(BasicMouldPojo param){
		//将前台获取到的nodeName转换成nodeCode再插入
		return xwzcxtMngDal.insertActnodeItem(param);
	}
	
	public int updateActiveStandard(BasicMouldPojo param){
		return xwzcxtMngDal.updateActiveStandard(param);
	}
	
	public BasicMouldPojo getStandardById(String param){
		return (BasicMouldPojo) xwzcxtMngDal.getStandardById(param);
	}
	
	public int deleteStandardStepById(String param){
		return xwzcxtMngDal.deleteStandardStepById(param);
	}
	
	//批量删除标准项
	public int batchDeleteStdStepById(String[] ids){
		if (xwzcxtMngDal.batchDeleteStdStepById(ids) >= 0)
			return xwzcxtMngDal.batchDeleteStdStepActitemById(ids);
		else
			return -1;
	}
	
	public int deleteStepDataById(String param){
		return xwzcxtMngDal.deleteStepDataById(param);
	}
	
	public List<BasicMouldPojo> getAllActNode(){
		return (List<BasicMouldPojo>) xwzcxtMngDal.selectAllActNode();
	}
	
	
	public Object getCalDataBySeq(String seq){
		return xwzcxtMngDal.getCalDataBySeq(seq);
	}
	
	public int modifyCalData(BasicMouldPojo param){
		return xwzcxtMngDal.modifyCalData(param);
	}
	
	public List<BasicMouldPojo> getStandardStepById(String itemName){
		return (List<BasicMouldPojo>) xwzcxtMngDal.getStandardStepById(itemName);
	}
	
	public int deleteObjectById(String param){
		return xwzcxtMngDal.deleteObjectById(param);
	}
	
	public int modifyBasicData(BasicMouldPojo param){
		return xwzcxtMngDal.modifyBasicData(param);
	}
	
	public BasicMouldPojo getBasicDataByObjId(String param){
		return (BasicMouldPojo) xwzcxtMngDal.getBasicDataByObjId(param);
	}
	
	public List<BasicMouldPojo> getAllTraceFunName(){
		return (List<BasicMouldPojo>) xwzcxtMngDal.getAllTraceFunName();
	}
	
	public int addStepData(Object param){
		return xwzcxtMngDal.addStepData(param);
	}
	
	public BasicMouldPojo getStepDataByCid(String param){
		return (BasicMouldPojo) xwzcxtMngDal.getStepDataByCid(param);
	}
	
	public int modifyStepDataByCid(Object param){
		return xwzcxtMngDal.modifyStepDataByCid(param);
	}
	
	public int addTimeRule(Object param){
		return xwzcxtMngDal.addTimeRule(param);
	}
	
	public BasicMouldPojo getTimeRuleById(String param){
		return (BasicMouldPojo) xwzcxtMngDal.getTimeRuleById(param);
	}
	
	public int modifyTimeRuleById(Object param){
		return xwzcxtMngDal.modifyTimeRuleById(param);
	}
	
	public int deleteTimeruleById(String param){
		return xwzcxtMngDal.deleteTimeruleById(param);
	}
	public int submitStepData(Object param){
		return xwzcxtMngDal.submitStepData(param);
	}
	public List<BasicMouldPojo> getAllUsers(){
		return (List<BasicMouldPojo>) xwzcxtMngDal.getAllUsers();
	}
	
	//获取登录用户及其下属用户信息  GuveXie 20140814
	public List<User> getUsersByUpUserID(Map params){
		return (List<User>) xwzcxtMngDal.getUsersByUpUserID(params);
	}
}
