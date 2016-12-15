package sail.beans.dao.iml.cacheQueryCount;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Collections;
import java.util.Properties;
import java.util.Set;

//import com.sun.net.ssl.internal.ssl.Debug;
import sail.beans.support.PropertyBean;

public class CountCache {
	private static CountCache instance = null;
	private Map<SqlParams, Long> dataCache = Collections
			.synchronizedMap(new HashMap<SqlParams, Long>());
	private Map<SqlParams, Long> timeCache = Collections
			.synchronizedMap(new HashMap<SqlParams, Long>());
	private Set<String> sql = Collections
			.synchronizedSet(new HashSet<String>());

	private String type;
	private String exectime;
	int execMillis = 0;

	private long lastClearTime;
	// 5分钟过期
	private long EXPIRETIME = 3600 * 1000;
	// 15分钟整体清理
	private long CLEARTIME = 7200 * 1000;

	private CountCache() {
		lastClearTime = System.currentTimeMillis();
		Properties ps = PropertyBean
				.getProperties("/config/countcache.properties");
		type = PropertyBean.getProperty(ps, "type");
		exectime = PropertyBean.getProperty(ps, "exectime");
		execMillis = Integer.parseInt(exectime.trim()) * 1000;

		String count = PropertyBean.getProperty(ps, "count");
		int c = Integer.parseInt(count.trim());
		for (int i = 1; i <= c; i++) {
			String s = PropertyBean.getProperty(ps, "sql" + i);
			sql.add(s);
		}

		count = PropertyBean.getProperty(ps, "expiretime");
		EXPIRETIME = Long.parseLong(count.trim()) * 1000;
		count = PropertyBean.getProperty(ps, "cleartime");
		CLEARTIME = Long.parseLong(count.trim()) * 1000;
	}

	public static synchronized CountCache getInstance() {
		if (instance == null) {
			instance = new CountCache();
		}
		return instance;
	}

	public Long get(SqlParams key) {
		long now = System.currentTimeMillis();
		if (isExpired(key)) {
			// 清除可能的过期缓存
			clear(key);
		}
		if (now - lastClearTime > CLEARTIME) {
			// 整体清理过期缓存
			ArrayList<SqlParams> expireIds = new ArrayList<SqlParams>();
			Iterator<SqlParams> keys = dataCache.keySet().iterator();
			while (keys.hasNext()) {
				SqlParams k = keys.next();
				if (isExpired(k)) {
					expireIds.add(k);
				}
			}
			for (SqlParams id : expireIds) {
				dataCache.remove(id);
				timeCache.remove(id);
			}
		}
		return dataCache.get(key);
	}

	private boolean isExpired(SqlParams key) {
		if (!timeCache.containsKey(key))
			return false;
		long checkTime = timeCache.get(key);
		if (checkTime == Long.MAX_VALUE)
			return false;
		if (System.currentTimeMillis() - checkTime > EXPIRETIME)
			return true;
		else
			return false;
	}

	public void set(SqlParams key, Long data, long beginExecTime) {
		boolean bl = false;
		// 加入cache策约处理
		if (type.equals("1")) {
			// 执行时间
			long now = System.currentTimeMillis();
			if (now - beginExecTime > execMillis) {
				bl = true;
			} else {
				bl = false;
			}
		} else {
			// 定制ＳＱＬ   
			for(String strSQL: sql){
				if(key.getSql().indexOf(strSQL) > -1){ 
					bl = true;
					break;
				}
			}
		}

		if (bl) {
			//加入cache
			if (data != null) {
				dataCache.put(key, data);
				timeCache.put(key, System.currentTimeMillis());
			}
		}
	}

	public void setNoExpired(SqlParams key, Long data) {
		if (data != null) {
			dataCache.put(key, data);
			timeCache.put(key, Long.MAX_VALUE);
		}
	}

	public void clear(SqlParams key) {
		dataCache.remove(key);
		timeCache.remove(key);
	}
}
