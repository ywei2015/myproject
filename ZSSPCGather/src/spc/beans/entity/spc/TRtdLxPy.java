package spc.beans.entity.spc;

/** 
* @ClassName: TRtdLxPy 
* @Description: TODO(批次参数某时间点数据实体) 
* @author xieshihe 
* @date 2017年9月11日 下午4:24:23 
*  
*/
public class TRtdLxPy {
	private String pid; // F_PID	主键
	private String batch; // F_BATCH	生产批次
	private String brand; // F_BRAND	产品牌号ID
	private String gathertime; // F_GATHER_TIME	采样时间点
	private Double data; // F_DATA	数据值
	private String state = "0"; // F_STATE	剔除状态
	private String isManualWeed = "0"; // F_ISMANUAL_WEED	是否人工剔除
	private String remark1; // F_REMARK1	预留1
	private String remark2; // F_REMARK2	预留2
	private String sysflag = "1"; // F_SYS_FLAG	记录有效标志
	private String createtime; // F_CREATE_TIME	创建时间
	private String lastmodifytime; //F_LAST_MODIFIED_TIME is 修改时间
	
	public TRtdLxPy(){}
	public TRtdLxPy(String _batch, String _brand, String _gathertime, Double _data, int _state){
		this.batch = _batch;
		this.brand = _brand;
		this.gathertime = _gathertime;
		this.data = _data;
		this.state = String.valueOf(_state);
	}

	public String toInsertSql(String tablename){
		String sql = null;
		String fields = "";
		String values = "";
		if(this.pid==null) this.pid= java.util.UUID.randomUUID().toString();
		fields = "F_PID, F_BATCH, F_BRAND, F_GATHER_TIME, F_DATA, F_STATE, F_ISMANUAL_WEED, F_REMARK1, F_REMARK2, F_SYS_FLAG";
		values = String.format("'%s', '%s', '%s', '%s', %s, '%s', '%s', %s, %s, '%s'", 
				this.pid,
				this.batch,
				this.brand,
				this.gathertime,
				(this.data==null||this.data==Double.NaN)?"0": String.format("%.5f", this.data.doubleValue()), //this.data,
				this.state,
				this.isManualWeed,
				(this.remark1==null)?"null": "'"+this.remark1+"'",
				(this.remark2==null)?"null": "'"+this.remark2+"'",
				this.sysflag);
		//INSERT INTO table_name (列1, 列2,...) VALUES (值1, 值2,....)
		sql = String.format("INSERT INTO %s(%s) VALUES (%s) ", tablename, fields, values); 
		return sql;
	}
	
	public static String getCreateTableSql(String tablename){
		String sql = "";
		sql = String.format("create table %s "
				+ "(F_PID                VARCHAR2(50)         not null,"
				+ "F_BATCH              VARCHAR2(50)         not null,"
				+ "F_BRAND              VARCHAR2(50)         not null,"
				+ "F_GATHER_TIME        VARCHAR2(20)         not null,"
				+ "F_DATA               VARCHAR2(100)        not null,"
				+ "F_STATE              CHAR(1)              not null,"
				+ "F_ISMANUAL_WEED      CHAR(1)              default '0' not null,"
				+ "F_REMARK1            VARCHAR2(50),"
				+ "F_REMARK2            VARCHAR2(50),"
				+ "F_SYS_FLAG           CHAR(1)              default '1' not null,"
				+ "F_CREATE_TIME        VARCHAR2(14)         default to_char(sysdate,'yyyyMMddhh24miss') not null,"
				+ "F_LAST_MODIFIED_TIME VARCHAR2(14),"
				+ "constraint PK_%s primary key (F_PID) ) ", tablename, tablename);
		return sql;
	}
	
	
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getGathertime() {
		return gathertime;
	}
	public void setGathertime(String gathertime) {
		this.gathertime = gathertime;
	}
	public Double getData() {
		return data;
	}
	public void setData(Double data) {
		this.data = data;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getIsManualWeed() {
		return isManualWeed;
	}
	public void setIsManualWeed(String isManualWeed) {
		this.isManualWeed = isManualWeed;
	}
	public String getRemark1() {
		return remark1;
	}
	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}
	public String getRemark2() {
		return remark2;
	}
	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}
	public String getSysflag() {
		return sysflag;
	}
	public void setSysflag(String sysflag) {
		this.sysflag = sysflag;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getLastmodifytime() {
		return lastmodifytime;
	} 

	public void setLastmodifytime(String lastmodifytime) {
		this.lastmodifytime = lastmodifytime;
	}



	
}
