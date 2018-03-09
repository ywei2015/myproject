package spc.beans.entity.spc;

import java.util.List;

public class BatchRDTState {

	//private String paramid;
	private String batch;
	private String brandid;
	private String totalSample; 
	private String minDT;
	private String maxDT;
	private String lastbatch;
	
	public BatchRDTState(){}

	public BatchRDTState(Object resobj){
		Object [] objs= (Object[]) resobj;
		if(objs==null) return;
		this.batch = objs[0].toString();
		this.brandid =  objs[1].toString();
		this.totalSample =  objs[2].toString();
		this.minDT =  objs[3].toString();
		this.maxDT =  objs[4].toString();
	}
	/*
	public BatchRDTState(List list){
		if(list==null) return;
		this.batch = list.get(0).toString();
		this.brandid =  list.get(1).toString();
		this.totalSample =  list.get(2).toString();
		this.minDT =  list.get(3).toString();
		this.maxDT =  list.get(4).toString();
	}*/

	public static String getGroupBySql(String batch, String tablename){
		String sql = "select t.f_batch, t.f_brand, count(1) N, min(t.f_gather_time) MinDT, max(t.f_gather_time) MaxDT "
				+ "from "+tablename+" t WHERE t.f_batch='"+batch+"'"
				+ "group by t.f_batch, t.f_brand";
		return sql;
	}
	
	public static String getLastBatchByRDTtable(String tablename){
		return "select f_batch from (select f_batch, f.F_CREATE_TIME from "+tablename+" f  order by f.f_create_time desc) s where rownum=1";
	}
	 

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public String getBrandid() {
		return brandid;
	}

	public void setBrandid(String brandid) {
		this.brandid = brandid;
	}

	public String getTotalSample() {
		return totalSample;
	}

	public void setTotalSample(String totalSample) {
		this.totalSample = totalSample;
	}
 
	public String getMinDT() {
		return minDT;
	}

	public void setMinDT(String minDT) {
		this.minDT = minDT;
	}

	public String getMaxDT() {
		return maxDT;
	}

	public void setMaxDT(String maxDT) {
		this.maxDT = maxDT;
	}

	public String getLastbatch() {
		return lastbatch;
	}

	public void setLastbatch(String lastbatch) {
		this.lastbatch = lastbatch;
	}

}
