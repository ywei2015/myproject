package spc.beans.service.prealarm;

import java.util.List;

import spc.beans.entity.spc.TSpcAbnormal;
import spc.beans.entity.spc.TSpcStandard;

public abstract class AlarmForecastBase { 
	public String ruleid = "";
	public String rulename = "";  
	public String abnormaltype = "";  
	
	public abstract void setWayParams(String [] args);
	public abstract List<TSpcAbnormal> forecast(TimeValuePair [] data, TSpcStandard std); 
}
