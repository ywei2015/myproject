package spc.beans.service.prealarm.way;

import java.util.List;

import spc.beans.buffer.PubConst;
import spc.beans.entity.spc.TSpcAbnormal;
import spc.beans.entity.spc.TSpcStandard;
import spc.beans.service.prealarm.AlarmForecastBase;
import spc.beans.service.prealarm.TimeValuePair;

public class S9UnilateralOver extends AlarmForecastBase {
	private final static String ruleid="S9UnilateralOver";
	private final static String rulename="单边连续9点超标";
	private final static String abnormaltype = PubConst.ABNORMAL_TYPE_PREALARM;
	public S9UnilateralOver(){  
	}

	@Override
	public List<TSpcAbnormal> forecast(TimeValuePair[] data, TSpcStandard std) { 
		return null;
	}

	@Override
	public void setWayParams(String[] args) {
		if(args==null) return;
		if(args.length>0){
			//若有参数则设置
		} 
	}
}
