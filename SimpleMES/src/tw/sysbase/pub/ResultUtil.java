package tw.sysbase.pub;

import java.util.HashMap;
import java.util.Map;

public class ResultUtil {
	/**
	 * @Description: 默认成功操作
	 * @author: zw
	 */
	public static Map<String,Object> DefaultResult(){
		 Map<String,Object> map=new HashMap<>();
		 map.put(Constants.STATUS	, Constants.SUCCESS);
		 map.put(Constants.MESSAGE, Constants.SUCCESS_MSG);
		 return map;
	}
	public static Map<String,Object>ErrorResult(){
		 Map<String,Object> map=new HashMap<>();
		 map.put(Constants.STATUS	, Constants.ERROR);
		 map.put(Constants.MESSAGE, Constants.FAILURE_MSG);
		 return map;
	}
	
	public  static Map<String,Object> initResult(){
		 Map<String,Object> map=new HashMap<>();
		 Map<String,Object> map1=new HashMap<>();
		 Map<String,Map<String,Object>> map2=new HashMap<>();
		 map1.put("code", "0");
		 map1.put(Constants.MESSAGE, Constants.SUCCESS_MSG);
		 map.put("result", map1);
		 map.put("dataset", map2);
		return map;
	}
	
	public static Map<String,Object> DefResult( Map<String,Object> result,String key,Object value){
		
		Map<String,Object> map = (Map<String, Object>) result.get("dataset");
		map.put(key, value);
		return result;
	}
	
}
