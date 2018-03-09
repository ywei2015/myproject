package spc.beans.service.prealarm.way;

import java.util.ArrayList;
import java.util.List;

import spc.beans.buffer.PubConst;
import spc.beans.entity.spc.TSpcAbnormal;
import spc.beans.entity.spc.TSpcStandard;
import spc.beans.service.prealarm.AlarmForecastBase;
import spc.beans.service.prealarm.TimeValuePair; 

public class S6OverLimit extends AlarmForecastBase { 
	private final static String ruleid="S6OverLimit";
	private final static String rulename="连续6点超标";
	private final static String abnormaltype = PubConst.ABNORMAL_TYPE_PREALARM;
	public S6OverLimit(){ }

	@Override
	public List<TSpcAbnormal> forecast(TimeValuePair[] data, TSpcStandard std) {
		List<TSpcAbnormal> list = new ArrayList<TSpcAbnormal>();
		int M = 0; //消息数
		int SCount = 0; //单环计数
		String STime = ""; ////单环计时间
		boolean flag = false; ///单环是否在状态内
		//String i
		for(int i=0;i<data.length;i++){
			//判断是否超差
			if(isOver(data[i].value,std)){
				flag = true;//超差标记
				SCount++;
				if(SCount==1) STime = data[i].timetag; 
			}else{ 
				flag = false;  //不超差标记
				if(SCount>=6&&!flag){ //大于6点而且由异常变为正常
					/* 测试代码（抓取数据记录末端点）
					 * if(std.getFKBrandid().equals("ID1128")&&std.getFKParameterid().equals("SSHCGX_HZ_CKWD"))
						System.out.println("ID1128--SSHCGX_HZ_CKWD_"+data[i].value);*/
					//达到6点则生成报警消息
					TSpcAbnormal tmpobj = new TSpcAbnormal();
					tmpobj.setFPid( String.format("%s%04d", ruleid, M+1) );
					tmpobj.setFRuleId(ruleid);
					tmpobj.setFRuleName(rulename);
					tmpobj.setFAbnormalType(abnormaltype); 
					tmpobj.setFOccurStime(STime);
					tmpobj.setFOccurEtime(data[i].timetag); 
					list.add(tmpobj); 
					M++;
				} 
				SCount = 0; STime = ""; 
			}

			if( (SCount>=6) && ((i+1)==data.length) ){ ////大于6点而且到尾部
				//达到6点则生成报警消息
				TSpcAbnormal tmpobj = new TSpcAbnormal();
				tmpobj.setFPid( String.format("%s%04d", ruleid, M+1) );
				tmpobj.setFRuleId(ruleid);
				tmpobj.setFRuleName(rulename);
				tmpobj.setFAbnormalType(abnormaltype); 
				tmpobj.setFOccurStime(STime);
				tmpobj.setFOccurEtime(data[i].timetag); 
				tmpobj.setFRemark1("tail");
				list.add(tmpobj);
				M++;
				SCount = 0; STime = "";
			}
		}
		return list; 
	}
	
	//判断是否合格
	private boolean isOver(double val, TSpcStandard std){
		boolean b = false;
		if(std.getFUsl()!=null){
			if(val>std.getFUsl()) b=true;
			if(std.getFIncludeUSL().equals("0")&&val==std.getFUsl()) b=true;
		} 
		if(std.getFLsl()!=null){
			if(val<std.getFLsl()) b=true;
			if(std.getFIncludeLSL().equals("0")&&val==std.getFLsl()) b=true;
		} 
		return b;
	}
 
	@Override
	public void setWayParams(String[] args) {
		if(args==null) return;
		if(args.length>0){
			//若有参数则设置
		} 
	}
 
}
