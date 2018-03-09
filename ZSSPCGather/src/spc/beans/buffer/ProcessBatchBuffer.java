package spc.beans.buffer;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import spc.beans.base.DatetimeEx;
import spc.beans.base.PersistenceKit;
import spc.beans.entity.spc.RealtimePointData; 

/** 
* @ClassName: ProcessBatchBuffer 
* @Description: TODO(生产过程批次存放缓存) 
* @author xieshihe 
* @date 2017年9月15日 下午3:46:15 
*  
*/
public final class ProcessBatchBuffer { 
	public static String FLAG_NEW = "NEW"; //新批次
	public static String FLAG_EXISTS = "EXISTS"; //存在批次
	//public static String FLAG_DONE = "批次变更时返回结束的批次"; //批次变更时返回结束的批次
	
	/** 工序与批次(key-工序, value-批次) */
	private static Map<String, String> processBatchMap = new HashMap<String, String>(); 
	 
	/** 
	* @Title: inProcessBatchMap 
	* @Description: TODO(工序当前生产批次管理) 
	* @param processtag 工序
	* @param batch  批次
	* @return String  返回(新批次标识、存在批次标识、变更时返回原批次) 
	* @throws 
	* 2017年9月19日 下午3:50:51 最后修改人 GuveXie 
	*/
	public static String inProcessBatchMap(String processId, String batch){
		if(processBatchMap.containsKey(processId)){
			String existbatch = processBatchMap.get(processId);
			if(existbatch.equals(batch)) return FLAG_EXISTS;
			//setBatchEndState(processId, processBatchMap.get(processId)); //当工序上批次变更 时，旧批次结束
			processBatchMap.replace(processId, batch); //工序批次变更为新批次
			return existbatch;
		}
		processBatchMap.put(processId, batch);
		return FLAG_NEW;
	}
	public static Map<String, String> getProcessBatchMap(){
		return processBatchMap;
	}
/*
 * 
	private List<ProcessBatch> listbatch = new ArrayList<ProcessBatch>(); 
	class ProcessBatch{
		public String ProcessTag;
		public String Batch;
		public String Brand;
		public String FirstDT;
		public String LastDT;
		public boolean isSaved = false;
		 是否过期(5小时为期) 
		public boolean isExpire(){
			Date dt = DatetimeEx.Str2Date14(FirstDT);
			if(dt==null) return true;
			Date now = new Date();
			///超过5小时则认为该批过期
			if(DatetimeEx.BetweenMinutes(now, dt)>(60*5)){
				return true;
			}else{
				return false;
			}
		}
		public String toInsertSql(){
			String sql = null; 
			
			return sql;
		}
	}
	*/
	/*
	public void inBuffer(RealtimePointData data){
		if(data==null) return;
		boolean b = inProcessBatchMap(data.getProcessTag(),data.getBatch());
		if(b){
			ProcessBatch bat = new ProcessBatch();
			bat.ProcessTag = data.getProcessTag();
			bat.Brand = data.getOpcbrand();
			bat.Batch = data.getBatch();
			bat.FirstDT = data.getGathertime();
			listbatch.add(bat);
		}
	}
	
	public void clearExpire(){
		for(int i=0;i<listbatch.size();i++){
			if(listbatch.get(i).isExpire()){
				if(!listbatch.get(i).isSaved) 
					setBatchEndState( listbatch.get(i).ProcessTag,  listbatch.get(i).Batch);
				listbatch.remove(i);
			}
		}
	}
	
	*//** 
	* @Title: UpdateBatchEndState 
	* @Description: TODO(标记批次结束) 
	* @param batch   批次号  
	* 2017年9月18日 上午10:55:23 最后修改人 GuveXie 
	*//*
	public void setBatchEndState(String processtag, String batch){
		PersistenceKit.setValue("EndBatchs", processtag+"|"+batch);
	}
	*//** 
	* @Title: setBatchStartState 
	* @Description: TODO(标记批次开通) 
	* @param batch   批次号  
	* 2017年9月18日 上午10:55:23 最后修改人 GuveXie 
	*//*
	public void setBatchStartState(String processtag, String batch){
		PersistenceKit.setValue("StartBatchs", processtag+"|"+batch);
	}
	*/
}
