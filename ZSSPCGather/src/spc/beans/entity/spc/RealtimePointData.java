package spc.beans.entity.spc;
 
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import spc.beans.base.DatetimeEx;
import spc.beans.buffer.PubConst;
 

/** 
* @ClassName: RealtimePointData 
* @Description: TODO(单点时间某工序上各参数的实时数据实体) 
* @author xieshihe 
* @date 2017年9月6日 下午2:29:28 
*  
*/
public class RealtimePointData {
	private String timepoint; //数据时间点 yyyyMMddHHmiss
	private String processTag; //工序Tag
	private String processId; //工序Id
	private String batch;  //批次号
	private String opcbrand; //牌号
	private String gathertime; //采集时间 yyyyMMddHHmiss 
	
	/** 
	* @Fields paramData : 该工序上的参数数据 
	* Key-->参数Tag， Value-->实际数据
	*/ 
	private Map<String, Double> paramData = new ConcurrentHashMap<String, Double>();
	public boolean isEmptyData(){
		return paramData.isEmpty();
	}
	/** 
	* @Fields paramState : 该工序上的参数数据剔除状态 
	* Key-->参数Tag， Value-->数据剔除状态
	*/ 
	private Map<String, Integer> paramState = new ConcurrentHashMap<String, Integer>();
	public int getState(String paramtag){
		 if( paramState.containsKey(paramtag)){
			 return (paramState.get(paramtag)==null)?0:paramState.get(paramtag).intValue();
		 }
		 return 0;
	}
	public void setState(String paramtag, int stateflag){
		if( paramState.containsKey(paramtag))
			paramState.replace(paramtag, stateflag); 
		else
			paramState.put(paramtag, stateflag);
	}
	public void setBlankState(){ 
		if(paramState!=null){ 
			Set<String> keySet =  paramData.keySet();
			for(String keyname:keySet){
				if(paramState.containsKey(keyname))
					paramState.replace(keyname, PubConst.ELIMINATE_STATE_BLANK);
				else
					paramState.put(keyname, PubConst.ELIMINATE_STATE_BLANK);
			}
		}
	}
	public void setState(int stateflag){ 
		if(paramState!=null){ 
			Set<String> keySet =  paramData.keySet();
			for(String keyname:keySet){
				if(paramState.containsKey(keyname))
					paramState.replace(keyname, stateflag);
				else
					paramState.put(keyname, stateflag); 
			}
		}
	}
	
	public RealtimePointData(Map<String,Object> rowdata){
		if(rowdata!=null){ 
			Set<String> keySet =  rowdata.keySet();
			for(String keyname:keySet){
				try{
					if(keyname.equals(PubConst.DT)) {
						this.timepoint = rowdata.get(PubConst.DT).toString().replace(" ", "").replace(":", "").replace("-", ""); 
						continue;
						}
					if(keyname.equals(PubConst.GX)) {this.processTag = rowdata.get(PubConst.GX)==null?"":rowdata.get(PubConst.GX).toString(); continue;}
					if(keyname.equals(PubConst.BATCH)) {this.batch = rowdata.get(PubConst.BATCH)==null?"":rowdata.get(PubConst.BATCH).toString(); continue;}
					if(keyname.equals(PubConst.PH)) {this.opcbrand = rowdata.get(PubConst.PH)==null?"":rowdata.get(PubConst.PH).toString(); continue;}
					if(keyname.equals(PubConst.GXID)) {this.processId = rowdata.get(PubConst.GXID)==null?"":rowdata.get(PubConst.GXID).toString(); continue;}
					Double value = 0d; // Double.NaN;
					if(rowdata.get(keyname)!=null&&rowdata.get(keyname).toString()!=""&&rowdata.get(keyname).toString()!="-"){
						value = Double.valueOf(rowdata.get(keyname).toString());
					} 
					this.gathertime = DatetimeEx.curDT14();
					this.AddData(keyname, Double.valueOf(value));
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		} 
	}
	
	public void AddData(String key, Double value){
		if(paramData.containsKey(key))
			paramData.replace(key, value);
		else
			paramData.put(key, value);
	}
	
	public String getTimepoint() {
		return timepoint;
	}
	public void setTimepoint(String timepoint) {
		this.timepoint = timepoint;
	}
	public String getProcessTag() {
		return processTag;
	}
	public void setProcessTag(String processTag) {
		this.processTag = processTag;
	}
	public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}
	public String getOpcbrand() {
		if(opcbrand.contains(".")) opcbrand=opcbrand.substring(0, opcbrand.indexOf("."));
		return this.opcbrand;
	}
	public void setOpcbrand(String opcbrand) {
		if(opcbrand.contains(".")) opcbrand=opcbrand.substring(0, opcbrand.indexOf("."));
		this.opcbrand = opcbrand;
	}
	public Map<String, Double> getParamData() {
		return paramData;
	}
	public void setParamData(Map<String, Double> paramData) {
		this.paramData = paramData;
	}
	public String getGathertime() {
		return gathertime;
	}
	public void setGathertime(String gathertime) {
		this.gathertime = gathertime;
	}
	

	public Double getParamData(String tag) {
		if(paramData.containsKey(tag)){
			return paramData.get(tag);
		}
		return null;
	}
	

	  @Override     
	  public String toString(){
		  return String.format("|%s|%s|%s|%s|%s|", timepoint, processTag, batch, opcbrand, gathertime);
	  }
	public int getStateOne(){
		Set<String> keySet =  paramData.keySet();
		for(String keyname:keySet){
			if(paramState.containsKey(keyname))
				return paramState.get(keyname).intValue();
		}
		 return 0;
	}
	public String getProcessId() {
		return processId;
	}
	public void setProcessId(String processId) {
		this.processId = processId;
	}  
		
}
