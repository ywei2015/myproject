package spc.beans.initial;

import java.util.Date;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
 
import org.springframework.web.context.support.WebApplicationContextUtils;

import spc.beans.base.DatetimeEx;
import spc.beans.base.PersistenceKit;
import spc.beans.buffer.AppInfo;
import spc.beans.buffer.BaseBuffer;
import spc.beans.buffer.ServiceBuffer;
import spc.beans.entity.spc.TSpcBrand;
import spc.beans.entity.spc.TSpcParameter;
import spc.beans.entity.spc.TSpcProcess;
import spc.beans.entity.spc.TSpcStandard;

public class BaseInfoInitListener implements ServletContextListener { 
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {  
		PersistenceKit.setContext(arg0.getServletContext());
		ServiceBuffer.appContext = WebApplicationContextUtils.getWebApplicationContext(arg0.getServletContext());
		AppInfo.appStartTime = new Date();
		PersistenceKit.setValue("appStartTime", DatetimeEx.toStr14(AppInfo.appStartTime));
		//GatherDataService gatherService = WebApplicationContextUtils.getWebApplicationContext(arg0.getServletContext()).getBean(GatherDataService.class);
		//ServiceBuffer.gatherService=gatherService;
		//System.out.println( String.format("getDBServerCurTime: %s " , gatherService.getDBServerCurTime()));
		//BaseInfoService baseinfoService = WebApplicationContextUtils.getWebApplicationContext(arg0.getServletContext()).getBean(BaseInfoService.class);
		//ServiceBuffer.baseinfoService=baseinfoService;
		//TargetDBService targetDbService = WebApplicationContextUtils.getWebApplicationContext(arg0.getServletContext()).getBean(TargetDBService.class);
		//ServiceBuffer.targetDbService=targetDbService;
		InitBaseBuffer(); //初始化基础数据缓存
	}

	/** 
	* @Title: InitBaseBuffer 
	* @Description: TODO(初始化基础数据缓存) 
	* @param     NULL 
	* @return void    
	* @throws 
	* 2017年9月7日 上午8:36:04 最后修改人 GuveXie 
	*/
	private void InitBaseBuffer(){ 
		try {
			//初始化工序缓存
			List<TSpcProcess> processList = ServiceBuffer.getBaseinfoService().getProcessList();
			if(processList!=null)
			{ 
				BaseBuffer.AddProcessList(processList);
			}
			//初始化工序参数缓存
			List<TSpcParameter> paramList = ServiceBuffer.getBaseinfoService().getParamList();
			if(processList!=null)
			{
				for(int i=0;i<paramList.size();i++){
					if(paramList.get(i)==null) continue;
					String processId = paramList.get(i).getFProcessId();
					BaseBuffer.AddProcessParameter(processId, paramList.get(i));
				}
			} 
			//初始化牌号缓存
			List<TSpcBrand> brandList = ServiceBuffer.getBaseinfoService().getBrandList();
			if(brandList!=null)
			{ 
				BaseBuffer.AddBrandList(brandList);
			} 
			//初始化牌号参数标准缓存
			List<TSpcStandard> stdlist = ServiceBuffer.getBaseinfoService().getStandardList();
			if(stdlist!=null)
			{ 
				for(int i=0;i<stdlist.size();i++){
					if(stdlist.get(i)==null) continue;
					String opcbrand = BaseBuffer.getBrandTagById(stdlist.get(i).getFKBrandid());
					BaseBuffer.AddBrandParameterStandard(opcbrand, stdlist.get(i));
				}
			}   
			/*System.out.println( String.format("ProcessMap: %s  |  BrandMap:%s  |  ProcessMap(0):%s" ,
					BaseBuffer.getProcessMap().size(),
					BaseBuffer.getBrandMap().size(),
					BaseBuffer.getProcessMap().get("JXGX_HZ")==null?"NULL":BaseBuffer.getProcessMap().get("JXGX_HZ").ParameterMap.size() 
					)); */
		} catch (Exception e) { 
			e.printStackTrace();
		} 
		
		try{
			BaseBuffer.ReloadSqlMap();//重新装载SQL字段组装
		} catch (Exception e) { 
			e.printStackTrace();
		} 
		
	}
	
	
	/** 
	* @Title: StartGatherThread 
	* @Description: TODO(ddd) 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws 
	* 2017年9月7日 上午8:46:02 最后修改人 GuveXie 
	*/
	private void StartGatherThread(){
		
	}
}
