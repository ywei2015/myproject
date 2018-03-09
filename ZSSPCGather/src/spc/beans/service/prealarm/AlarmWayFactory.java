package spc.beans.service.prealarm;

import java.util.ArrayList;
import java.util.List;

import spc.beans.service.prealarm.way.S6OverLimit;
import spc.beans.service.prealarm.way.S9UnilateralOver;

/** 
* @ClassName: AlarmWayFactory 
* @Description: TODO(报警规则方法管理类) 
* @author GuveXie 
* @date 2017年10月10日 下午2:34:32  
*/
public class AlarmWayFactory {
	private static List<AlarmForecastBase> FullWays = new ArrayList<AlarmForecastBase>();
	
	public static void init(){
		if(FullWays.size()>0) return; //已经初始化则不再初始化
		S6OverLimit s6overlimit = new S6OverLimit();
		FullWays.add(s6overlimit);
		S9UnilateralOver s9unilateralover = new S9UnilateralOver();
		FullWays.add(s9unilateralover); 
	}

	/** 
	* @Title: getForecastWays 
	* @Description: TODO(获取预警规则实现方法) 
	* @param paramtag  参数Tag 
	* @return List<AlarmForecastBase>    返回类型 
	* 2017年10月10日 下午2:35:11 最后修改人  GuveXie 
	*/
	public static List<AlarmForecastBase> getForecastWays(String paramtag){
		//若用户要将规则绑定具体参数时则考虑通过参数paramtag进行过虑 (暂未实现)
		return FullWays;
	}
	
}
