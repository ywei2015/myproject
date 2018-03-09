package spc.beans.buffer;
 
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
 
import spc.beans.base.DatetimeEx;
import spc.beans.base.PersistenceKit;

public final class AppInfo {
	
	//private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static int disConnectDalaySeconds = 10; //超过10秒没有更新数据库服务器的时间认为连接断开
	public static Date appStartTime = new Date(); //应用程序启动时间 
	public static Date sourceDBRequestTime = new Date(); //源数据库请求时间
	public static Date sourceDBTime = new Date(); //源数据库时间
	public static Date targetDBRequestTime = new Date(); //目标数据库请求时间
	public static Date targetDBTime = new Date(); //目标数据库时间
	private static Date LastGatherTimePoint = null; //最后的采集时间
	private static Date LastEliminateTimePoint = null; //最后的剔除时间
	private static Date LastStatisticsTimePoint = null; //最后的分析时间
	public static int eliminatecount = 0; //完成剔除分析次数

	/** 
	* @Title: getAppDuration 
	* @Description: TODO(获取应用程序运行时长)   
	* @return String   应用程序驻留时长 %s天,%s时%s分%s秒 
	* @throws 
	* 2017年9月7日 上午10:35:25 最后修改人 GuveXie 
	*/
	public static String getAppDuration(){  
	    String dateStr = DatetimeEx.SubtractDT(new Date(), appStartTime);  
	    return dateStr;
	}
	
	/** 
	* @Title: isSourceDBConnected 
	* @Description: TODO(获取是否与源数据库是否连接成功)   
	* @return boolean   连接是否成功 
	* @throws 
	* 2017年9月7日 上午10:35:25 最后修改人 GuveXie 
	*/
	public static boolean isSourceDBConnected(){ 
		int timelength = DatetimeEx.BetweenSeconds(new Date(), sourceDBRequestTime);
		if(timelength>disConnectDalaySeconds){
			return false;
		}
		return true;
	}

	/** 
	* @Title: isTargetDBConnected 
	* @Description: TODO(获取是否与目标数据库是否连接成功)   
	* @return boolean   连接是否成功 
	* @throws 
	* 2017年9月7日 上午10:35:25 最后修改人 GuveXie 
	*/
	public static boolean isTargetDBConnected(){ 
		int timelength = DatetimeEx.BetweenSeconds(new Date(), targetDBRequestTime);
		if(timelength>disConnectDalaySeconds){
			return false;
		}
		return true;
	}

	public static Date getLastGatherTimePoint() {
		if(LastGatherTimePoint==null){ 
			String str = PersistenceKit.getValue("LastGatherTimePoint");
			if(str==null||str.equals("")) str =DatetimeEx.curDT14();   //"20170610055320";
			LastGatherTimePoint  = DatetimeEx.Str2Date14(str);
		} 
		return LastGatherTimePoint;
	}

	public static void setLastGatherTimePoint(Date lastGatherTimePoint) {
		LastGatherTimePoint = lastGatherTimePoint;
	} 
 
	public static void setLastEliminateTimePoint(Date _lastEliminateTimePoint) {
		LastEliminateTimePoint = _lastEliminateTimePoint;
	}  
	public static void setLastStatisticsTimePoint(Date _lastStatisticsTimePoint) {
		LastStatisticsTimePoint = _lastStatisticsTimePoint;
	}   

	public static String getDurationTime(){
		return DatetimeEx.SubtractDT(new Date(), appStartTime);
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String, String> toMap(){
		@SuppressWarnings("rawtypes")
		Map map = new HashMap(); 
		map.put("disConnectDalaySeconds", String.valueOf(disConnectDalaySeconds));
		map.put("appStartTime", DatetimeEx.toStr19(appStartTime));  
		map.put("sourceDBConnected", isSourceDBConnected() );  
		map.put("sourceDBRequestTime", DatetimeEx.toStr19(sourceDBRequestTime));
		map.put("sourceDBTime", DatetimeEx.toStr19(sourceDBTime)); 
		map.put("targetDBConnected", isTargetDBConnected() );  
		map.put("targetDBRequestTime", DatetimeEx.toStr19(targetDBRequestTime));
		map.put("targetDBTime", DatetimeEx.toStr19(targetDBTime));
		map.put("LastGatherTimePoint", DatetimeEx.toStr19(getLastGatherTimePoint()));
		map.put("LastEliminateTimePoint", DatetimeEx.toStr19(LastEliminateTimePoint));
		map.put("LastStatisticsTimePoint", DatetimeEx.toStr19(LastStatisticsTimePoint));
		map.put("totalegathercount", PointDataQueue.getGathertotalcount());
		map.put("waiteliminatecount", PointDataQueue.getCount());
		map.put("totaleliminatecount", eliminatecount);
		map.put("DurationTime", getDurationTime());
		return map;
	}
}
