package tw.sysbase.pub;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Collections;

import tw.sysbase.entity.pub.Dic;

public class PubCache {
	private static PubCache instance=null;
	private Map<String, Map<String, Object>> dataCache = Collections.synchronizedMap(new HashMap<String, Map<String, Object>>());
	private Map<String, String[]> dicCache = Collections.synchronizedMap(new HashMap<String, String[]>());
	private Map<String, Long> timeCache = Collections.synchronizedMap(new HashMap<String, Long>());
	private long lastClearTime;
	//业务数据暂时取消缓存
	private final long EXPIRETIME = 0;
	//15分钟整体清理
	private final long CLEARTIME = 900000;
	//实例锁,主要在多Map操作时同步
	private byte[] lock = new byte[0];
	
	private PubCache() {
		lastClearTime = System.currentTimeMillis();
	}
	public static synchronized PubCache getInstance(){ 
		if(instance==null){ 
			instance=new PubCache(); 
		} 
		return instance; 
	}

	public Map<String, Object> get(String key){
		long now = System.currentTimeMillis();
		if(isExpired(key)){
			//清除可能的过期缓存
			clear(key);
		}
		if(now -lastClearTime > CLEARTIME)
		{
			//整体清理过期缓存
			ArrayList<String> expireIds = new ArrayList<String>();
			Iterator<String> keys = dataCache.keySet().iterator();
			while (keys.hasNext()) {
				String k = keys.next();
				if(isExpired(k)){
					expireIds.add(k);
				}
			}
			for (String id : expireIds) {
				dataCache.remove(id);
				timeCache.remove(id);
			}
		}
		return dataCache.get(key);
	}
	
	private boolean isExpired(String key){
		if(!timeCache.containsKey(key)) return false;
		long checkTime = timeCache.get(key);
		if(checkTime == Long.MAX_VALUE) return false;
		if(System.currentTimeMillis() - checkTime > EXPIRETIME)
			return true;
		else
			return false;
	}
	
	public void set(String key, Map<String, Object> data){
		if(data != null) {
			dataCache.put(key, data);
			timeCache.put(key, System.currentTimeMillis());
		}
	}
	
	public void setNoExpired(String key, Map<String, Object> data){
		if(data != null) {
			dataCache.put(key, data);
			timeCache.put(key, Long.MAX_VALUE);
		}
	}
	
	public Dic getDic(String id){
		if(dicCache.containsKey(id)){
			String[] data = dicCache.get(id);
			return getDic(data[0], data[1]);
		}
		return null;
	}
	
	public Dic getDic(String type, String code){
		Map<String, Object> ht = get(type);
		if(ht == null) return null;
		return (Dic)ht.get(code);
	}
	
	public void setDic(String id, String[] data){
		if(data != null) {
			dicCache.put(id, data);
		}
	}
	
	public void deleteDics(String[] ids){
		for (String id : ids) {
			if(dicCache.containsKey(id)){
				String[] data = dicCache.get(id);
				Map<String, Object> ht = get(data[0]);
				if(ht != null) //ht.remove(data[1]);
				{
					((Dic)ht.get(data[1])).setSysFlag(0);
				}
				else{
					dicCache.remove(id);
				}
			}
		}
	}
	
	public void updateDic(Dic dic){
		if(dic != null && dicCache.containsKey(dic.getId())){
			String[] data = dicCache.get(dic.getId());
			Map<String, Object> ht = get(data[0]);
			if(dic.getCode().equals(data[1])){
				ht.put(data[1], dic);
			}
			else{
				synchronized(lock) {
					ht.remove(data[1]);
					data[1] = dic.getCode();
					dicCache.put(dic.getId(), data);
					ht.put(dic.getCode(), dic);
				}
			}
		}
	}
	
	public void addDic(Dic dic, String typeCode){
		if(dic != null){
			synchronized(lock) {
				setDic(dic.getId(), new String[]{typeCode, dic.getCode()});
				get(typeCode).put(dic.getCode(), dic);
			}
		}
	}
	
	public void clear(String key){
		dataCache.remove(key);
		timeCache.remove(key);
	}
}
