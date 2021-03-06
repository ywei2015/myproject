package spc.beans.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import spc.beans.base.DatetimeEx;
import spc.beans.buffer.AppInfo;
import spc.beans.buffer.BaseBuffer;
import spc.beans.buffer.ParamBatchBuffer;
import spc.beans.buffer.PointDataQueue;
import spc.beans.buffer.PubConst;
import spc.beans.dao.GenericDao;
import spc.beans.entity.spc.RealtimePointData;
import spc.beans.entity.spc.TRtdLxPy;
import spc.beans.entity.spc.TSpcParameter;
import spc.beans.entity.spc.TSpcStandard;
import spc.beans.entity.spc.TSpcStatisticResult;
import spc.beans.service.rediscache.ParamStandardManagerService;

/** 
* @ClassName: EliminateDataService 
* @Description: TODO(剔除断流数据、剔除不在有效区间数据、并将有数据标记状态存入对应参数实时数据表) 
* @author xieshihe 
* @date 2017年9月8日 下午5:43:35  
*/
@Service
public class EliminateDataService { 
	private static boolean isEliminateRuning = false;	
	@Autowired
	TargetDBService targetDBService;
	@Autowired
	private ParamStandardManagerService paramStandardManager;
	@Autowired
	private GenericDao genericDao; 
	/** 
	* @Fields GxBlank : TODO(工序连续断流次数)
	* Key->processTag, Value->连续断流次数
	*/ 
	private static Map<String, Integer> GxBlank = new ConcurrentHashMap<String, Integer>();
	/** 
	* @Title: getBlankCount 
	* @Description: TODO(  获取工序连续断流次数  ) 
	* @param processtag  工序
	* @return int  返回 工序连续断流次数  
	* 2017年9月11日 上午10:17:16 最后修改人 GuveXie 
	*/
	private static int getBlankCount(String processId){
		 if( GxBlank.containsKey(processId)){
			 return (GxBlank.get(processId)==null)?0:GxBlank.get(processId).intValue();
		 }
		 return 0;
	}	
	/** 
	* @Title: getBlankCount 
	* @Description: TODO( 设置工序连续断流次数  ) 
	* @param processtag  工序 Id
	* @param isblank  是否断流  
	* 2017年9月11日 上午10:17:16 最后修改人 GuveXie 
	*/
	private static void setBlankCount(String processtag, boolean isblank){
		int sum =  0;
		if( GxBlank.containsKey(processtag)){
			sum = getBlankCount(processtag) +1;
			if(!isblank) sum = 0;
			GxBlank.replace(processtag, sum);
			return;
		}
		GxBlank.put(processtag, isblank ? 1 : 0);
	}
	
	 
	/** 
	* @Title: doEliminate 
	* @Description: TODO(实时数据剔除处理)   
	* @return void    返回类型 
	* 2017年9月11日 上午9:07:50 最后修改人 GuveXie 
	*/
	public void doEliminate(){ 
		 try{  
			 //System.out.println( String.format("doEliminate isRuning: %s ", isRuning));
			 //System.out.println( String.format("TargetDBServer Time: %s ", DatetimeEx.toStr19(AppInfo.targetDBTime)));
			 if(isEliminateRuning) return;  
			 isEliminateRuning = true;
			 AppInfo.setLastEliminateTimePoint(new Date());//状态更新设置
			 
			 RealtimePointData pointdata = PointDataQueue.out();
			 if(pointdata==null) return;
			 
			 //判断是否断流状态（稳态生产状态）
			 boolean isBlankFlow = BlankFlowCheck(pointdata); 
			 //获取该工序前面断流次数
			 int blankcount = getBlankCount(pointdata.getProcessId());
			 ///(后续考虑工序参数间生产间隔时间)?????
			 if(isBlankFlow){ 
				 //断流状态---直接剔除
				 pointdata.setBlankState();
			 }else{
				 //非断流状态---独个参数判断有效区间
				 VaildRegionCheck(pointdata);
			 }
			 //处理完断流后记录上一工序是否断流(连续断流次数)
			 setBlankCount(pointdata.getProcessId(), isBlankFlow);
			 
			 //保存各参数到数据库中
			 if(pointdata.getStateOne()!=PubConst.ELIMINATE_STATE_NOSTANDARD){
				 //保存到redis
				 boolean b = SavePointDataForParameterValueToDB(pointdata);
				 if(b){
					 AppInfo.eliminatecount++;
				 }
			 }
			 PointDataQueue.removeFirst(); //成功则删除队列中第一项
			 
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			isEliminateRuning = false;
		}
	} 

	/** 
	* @Title: BlankFlowCheck 
	* @Description: TODO(判断是否断流状态（稳态生产状态）) 
	* @param pdata  工序时间点的数据集
	* @return boolean    返回是否断流状态 
	* 2017年9月11日 上午10:58:12 最后修改人 GuveXie 
	*/
	private boolean BlankFlowCheck(RealtimePointData pdata){
		String processId = pdata.getProcessId(); 
		String primaryTag = BaseBuffer.getProcessMap().get(processId).getMasterParamTag();
		if(primaryTag==null||primaryTag.equals("")) return false; 
		Double value = pdata.getParamData(primaryTag);
		if(value==null||value==Double.NaN) return false; 
		TSpcParameter masterParam = null;
		if(BaseBuffer.getProcessMap().containsKey(processId))
		{
			if(BaseBuffer.getProcessMap().get(processId).ParameterMap.containsKey(primaryTag)){
				masterParam = BaseBuffer.getProcessMap().get(processId).ParameterMap.get(primaryTag);
			}
		}
		if(masterParam==null) return false;
		if(masterParam.getFSteadystateSetval()==null||masterParam.getFSteadystateSetval().equals("")) return false;
		if(value<masterParam.getFSteadystateSetval()) return true; 
		return false;
	}
	/** 
	* @Title: VaildRegionCheck 
	* @Description: TODO(独个参数判断有效区间) 
	* @param pdata  工序时间点的数据集
	* @return boolean    返回处理过程是否有异常 
	* 2017年9月11日 上午10:58:12 最后修改人 GuveXie 
	*/
	private boolean VaildRegionCheck(RealtimePointData pdata){
		try{
			String processtId = pdata.getProcessId();
			String opcbrand = pdata.getOpcbrand(); 
			String primaryTag = BaseBuffer.getProcessMap().get(processtId).getMasterParamTag();
		    List<String> paramtags = BaseBuffer.getProcessMap().get(processtId).getParamTagList(); 
		    if(paramtags==null||paramtags.isEmpty()){ 
		    	pdata.setState(PubConst.ELIMINATE_STATE_PRGERROR);
		    	return false;
		    }
		    //从缓存数据库获取最新的参数标准
		    Map<String,TSpcStandard> ParamStdMap= paramStandardManager.getLatestParameterStandard(opcbrand, pdata.getBatch());
		    if(ParamStdMap.isEmpty()){
		    	pdata.setState(PubConst.ELIMINATE_STATE_NOSTANDARD);
		    	return false;
		    }
		    ///判断各参数是否有效
		    for(int i=0;i<paramtags.size();i++)
		    {
		    	//取得参数实际值
		    	Double val = pdata.getParamData(paramtags.get(i));
		    	if(val==null){ 
			    	pdata.setState(paramtags.get(i), PubConst.ELIMINATE_STATE_INVALIDREGION);
			    	continue;
		    	}
		    	//取得参数规格标准
				TSpcStandard paramStd = ParamStdMap.get(paramtags.get(i));
				/*if(BaseBuffer.getBrandMap().containsKey(opcbrand))
				{
					if(BaseBuffer.getBrandMap().get(opcbrand).ParamStdMap.containsKey(primaryTag)){
						paramStd = BaseBuffer.getBrandMap().get(opcbrand).ParamStdMap.get(primaryTag);
					}
				}*/
				if(paramStd==null){ 
			    	pdata.setState(paramtags.get(i), PubConst.ELIMINATE_STATE_PRGERROR);
			    	continue;
			    }
				///根据规格标准判断是否有效
				boolean isvalid = IsValidLimitCheck(paramStd, val);
				if(!isvalid){
					pdata.setState(paramtags.get(i), PubConst.ELIMINATE_STATE_INVALIDREGION);
				}
				//continue for next paramtags.getIndex(i);
		    } 
		}catch(Exception ex){
			ex.printStackTrace();
			return false;
		} 
		return true;
	}
	

	/** 
	* @Title: IsValidCheck 
	* @Description: TODO( 根据标准判断是否在有效范围内 ) 
	* @param std  参数标准
	* @param value  需要判断的值 
	* @return boolean 返回是否在有效区间 
	* 2017年9月11日 下午5:16:08 最后修改人 GuveXie 
	*/
	private boolean IsValidLimitCheck(TSpcStandard std, Double value){
		try{
			if(value==null||value.isNaN()) return false;
			Double gul =std.getFGatherUplimit();
			Double gll =std.getFGatherLowlimit();
			if(value>=gll&&value<=gul) return true;
			return false;
			/*Double usl =std.getFUsl();
			Double lsl =std.getFLsl();
			if(std.getFControlModel().equals(PubConst.SPEC_CONTROL_BILATERAL)){
				if(value==usl&&PubConst.toBool((std.getFIncludeUSL()))) return true;
				if(value==lsl&&PubConst.toBool((std.getFIncludeLSL()))) return true;
				if(value>lsl&&value<usl) return true;
				return false;
			}
			else if(std.getFControlModel().equals(PubConst.SPEC_CONTROL_UNILATERAL_UPPER)){ 
				if(value==usl&&PubConst.toBool((std.getFIncludeUSL()))) return true;
				if(value<usl) return true;
				return false;
			}
			else if(std.getFControlModel().equals(PubConst.SPEC_CONTROL_UNILATERAL_LOWER)){ 
				if(value==lsl&&PubConst.toBool((std.getFIncludeLSL()))) return true;
				if(value>lsl) return true;
				return false;
			}
			return false; */
		}catch(Exception ex){
			ex.printStackTrace();
			return false;
		} 
	}
	
	/** 
	* @Title: SavePointDataForParameterValueToDB 
	* @Description: TODO( 存储已经剔除处理好的数据---某工序上某时间点的数据实体 ) 
	* @param pdata  某工序上某时间点的数据实体
	* @return boolean    返回成功与否 
	* 2017年9月11日 下午5:16:08 最后修改人 GuveXie 
	*/
	private boolean SavePointDataForParameterValueToDB(RealtimePointData pdata){ 
		String batch = pdata.getBatch();
		String brandid = BaseBuffer.getBrandID(pdata.getOpcbrand()); 
		if(brandid==null||"".equals(brandid)) {
	    	System.out.println( String.format("-----Save RTD Failure --Not Find BrandID | %s ----" , pdata.toString()));
			return false;
		}
		String processId = pdata.getProcessId();
		int sumrows = 0;
		//String primaryTag = BaseBuffer.getProcessMap().get(processtag).getMasterParamTag();
	    List<String> paramtags = BaseBuffer.getProcessMap().get(processId).getParamTagList(); 
	    if(paramtags==null||paramtags.isEmpty()) return false;  
	    for(int i=0;i<paramtags.size();i++){
	    	Double value = pdata.getParamData(paramtags.get(i));
	    	int valState = pdata.getState(paramtags.get(i));
	    	String tablename = BaseBuffer.getTableNameByParamTag(paramtags.get(i));
	    	if(tablename!=null||!"".equals(tablename)){  
		    	TRtdLxPy rtdLxPy = new TRtdLxPy(batch, brandid, pdata.getTimepoint(), value, valState);
		    	if(targetDBService.getTableIsExists(tablename)==0){ 
			    	System.out.println( String.format("-----Create T_RTD_LxPy Table Failure | %s ----" , tablename));
		    		continue;
		    	}
		    	if(pointDataIsExit(rtdLxPy,tablename)) continue;
		    	try{ ///存储批次参数信息，以备进程按此进行参数的过程能力分析，预警分析等
		    		String processid = pdata.getProcessId();
		    		String parameterid = BaseBuffer.getParameterIdByTag(paramtags.get(i));
		    		if(processid!=null&&parameterid!=null){
		    			TSpcStatisticResult srobj = new TSpcStatisticResult(batch, processid,brandid,parameterid,pdata.getTimepoint());
		    			ParamBatchBuffer.updateParamBatchMap(srobj);
		    		}
		    	}catch(Exception e){ e.printStackTrace(); }
		    	///将数据插入数据库表
		    	String insertDataSql = rtdLxPy.toInsertSql(tablename);
		    	try{
			    	int rows = targetDBService.ExecuteSql(insertDataSql);
			    	sumrows += rows; 
		    	}catch(Exception e){
		    		e.printStackTrace(); 
			    	System.out.println( String.format("-----Save RTD Failure | %s ----" , insertDataSql));
		    	}
	    	} 
	    }
	    if(sumrows<1){ 
	    	System.out.println( String.format("-----Save RTD Failure by Zero | %s ----" , pdata.toString()));
	    	return false; 
	    }
		return true;
	}
	
	/**
	 * @param tablename 
	 * @Description: 检测参数点是否已经存在
	 * @param pdata  工序时间点的数据
	 * */
	public boolean pointDataIsExit(TRtdLxPy rtdLxPy, String tablename){
		String sql=getIsExitSql(rtdLxPy,tablename);
		try {
			List<Object> countList=this.genericDao.getNativeListWithOpenSession(sql);
			int count=Integer.parseInt(countList.get(0).toString());
			if(count>0) return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public String getIsExitSql(TRtdLxPy rtdLxPy, String tablename){
		String sql="select count(1) from %s where F_BATCH = '%s' and F_BRAND = '%s' and F_GATHER_TIME = '%s'";
		String count_sql=String.format(sql, tablename,rtdLxPy.getBatch(),rtdLxPy.getBrand(),rtdLxPy.getGathertime());
		return count_sql;
	}
	
	
}
