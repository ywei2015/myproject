package spc.beans.buffer;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

import spc.beans.base.DatetimeEx;
import spc.beans.entity.spc.TSpcStatisticResult; 

public class ParamBatchBuffer {
	private static Map<String, TSpcStatisticResult> parambatchMap = new HashMap<String, TSpcStatisticResult>();
	private static ReentrantLock locker=new ReentrantLock(); 
	public static String SAVE_FLAG_INIT = "0";
	public static String SAVE_FLAG_CREATED = "1";
	public static String SAVE_FLAG_DONE = "2";
	public static int Size() {
		return parambatchMap.size();
	}
	
	public static String combinekey(String paramid, String batch){
		return String.format("%s#%s", paramid, batch);
	}

	public static void updateParamBatchMap(TSpcStatisticResult sobj){ 
		try
		{
			locker.lock(); 
			
			String key = combinekey(sobj.getFParamId(), sobj.getFBatch());
			if(parambatchMap.containsKey(key)){
				TSpcStatisticResult sobj1 = parambatchMap.get(key); 
				int i = Integer.valueOf(sobj1.getFRemark5()); 
				sobj.setFRemark5(String.valueOf(++i));
				parambatchMap.replace(key, sobj);
			}else{
				sobj.setFRemark4(SAVE_FLAG_INIT); //SAVE_FLAG_INIT
				sobj.setFRemark5("1"); 
				parambatchMap.put(key, sobj);
			}
			
		}finally{
			locker.unlock();
		}
	}
	

	private static boolean isExpire(String str14, int minutes){ 
		Date dt = DatetimeEx.Str2Date14(str14);
		if(dt==null) return true;
		Date now = new Date(); 
		if(DatetimeEx.BetweenMinutes(now, dt)>minutes){
			return true;
		}else{
			return false;
		}
	}
	public static void ClearExpireParamBatchMap(){
		try
		{
			locker.lock();  
			Set<String> KeySet =  parambatchMap.keySet();  
			for(String key:KeySet){ 
				if(parambatchMap.get(key)==null) continue;
				String dt14 = parambatchMap.get(key).getFStartTime();
				boolean isexpired = isExpire(dt14, 60*5);
				if(isexpired){
					parambatchMap.remove(key);
				}
			} 
		}finally{
			locker.unlock();
		}
	}

	public static List<TSpcStatisticResult> getFirstCreateList(){
		List<TSpcStatisticResult> list = new ArrayList<TSpcStatisticResult>();
		try
		{
			locker.lock();  
			Set<String> KeySet =  parambatchMap.keySet();  
			for(String key:KeySet){ 
				if(parambatchMap.get(key)==null) continue;
				String isSaved = parambatchMap.get(key).getFRemark4();
				if(isSaved==null||isSaved.equals("")){
					list.add(parambatchMap.get(key));
				}
			} 
		}finally{
			locker.unlock();
		}
		return list;
	}
	
	public static void setSaved(String key, String flag){ 
		try
		{
			locker.lock();  
			if(parambatchMap.containsKey(key)){
				parambatchMap.get(key).setFRemark4(flag);
			}
		}finally{
			locker.unlock();
		}
	}
	
}
