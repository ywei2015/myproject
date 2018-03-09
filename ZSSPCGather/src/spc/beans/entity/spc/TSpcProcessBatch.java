package spc.beans.entity.spc;

public class TSpcProcessBatch {
	private String FPid;
	private String FBatch;
	private String FProcess;
	private String FBrand;
	private String FStartTime;
	private String FEndTime;
	private String FState; //处理状态(0-初始,9-结束)
	private String FRemark1;
	private String FRemark2;
	private String FRemark3;
	private String FRemark4;
	private String FRemark5;
	private String FSysFlag;
	private String FCreateTime;
	
	public TSpcProcessBatch(String batch, String process, String brand, String startDT){
		this.FPid = String.format("%s#%s", batch, process);
		this.FBatch = batch;
		this.FProcess = process;
		this.FBrand = brand; 
		this.FStartTime = startDT;
		this.FState = "0";
		this.FSysFlag = "1";
	}
	
	public TSpcProcessBatch(){
		
	}
	

	public String toFirstCreateSql(){ 
		String sql = null;
		String fields = "F_PID, F_BATCH, F_PROCESS, F_BRAND, F_START_TIME,	F_STATE, F_SYS_FLAG";
		String values = String.format("'%s', '%s', '%s', '%s', %s, '%s', '%s'", 
				this.FPid,
				this.FBatch,
				this.FProcess,
				this.FBrand,
				this.FStartTime,  
				this.FState, 
				this.FSysFlag
				);
		//INSERT INTO table_name (列1, 列2,...) VALUES (值1, 值2,....)
		sql = String.format("INSERT INTO %s(%s) VALUES (%s) ", "T_SPC_PROCESSBATCH", fields, values); 
		return sql;
	}

	public static String toProcessBatchEndSql(String batch, String process, String endDT){ 
		String sql = null;
		String FPid = String.format("%s#%s", batch, process); 
		//INSERT INTO table_name (列1, 列2,...) VALUES (值1, 值2,....)
		sql = String.format("UPDATE %s SET F_STATE='9', F_END_TIME='%s' WHERE F_PID='%s'", "T_SPC_PROCESSBATCH", endDT, FPid); 
		return sql;
	}

	
	public String getFPid() {
		return this.FPid;
	}

	public void setFPid(String FPid) {
		this.FPid = FPid;
	}

	public String getFBatch() {
		return this.FBatch;
	}

	public void setFBatch(String FBatch) {
		this.FBatch = FBatch;
	}

	public String getFBrand() {
		return this.FBrand;
	}

	public void setFBrand(String FBrand) {
		this.FBrand = FBrand;
	}

	public String getFStartTime() {
		return this.FStartTime;
	}

	public void setFStartTime(String FStartTime) {
		this.FStartTime = FStartTime;
	}

	public String getFEndTime() {
		return this.FEndTime;
	}

	public void setFEndTime(String FEndTime) {
		this.FEndTime = FEndTime;
	}

	public String getFRemark1() {
		return this.FRemark1;
	}

	public void setFRemark1(String FRemark1) {
		this.FRemark1 = FRemark1;
	}

	public String getFRemark2() {
		return this.FRemark2;
	}

	public void setFRemark2(String FRemark2) {
		this.FRemark2 = FRemark2;
	}

	public String getFRemark3() {
		return this.FRemark3;
	}

	public void setFRemark3(String FRemark3) {
		this.FRemark3 = FRemark3;
	}

	public String getFRemark4() {
		return this.FRemark4;
	}

	public void setFRemark4(String FRemark4) {
		this.FRemark4 = FRemark4;
	}

	public String getFRemark5() {
		return this.FRemark5;
	}

	public void setFRemark5(String FRemark5) {
		this.FRemark5 = FRemark5;
	}

	public String getFSysFlag() {
		return this.FSysFlag;
	}

	public void setFSysFlag(String FSysFlag) {
		this.FSysFlag = FSysFlag;
	}

	public String getFCreateTime() {
		return this.FCreateTime;
	}

	public void setFCreateTime(String FCreateTime) {
		this.FCreateTime = FCreateTime;
	}

	public String getFState() {
		return FState;
	}

	public void setFState(String fState) {
		FState = fState;
	}

	@Override
	public String toString() {
		return "FPid = " + this.FPid + "/r/n" + "FBatch = " + this.FBatch + "/r/n" + "FBrand = " + this.FBrand + "/r/n"
				+ "FStartTime = " + this.FStartTime + "/r/n" + "FEndTime = " + this.FEndTime + "/r/n" + "FRemark1 = "
				+ this.FRemark1 + "/r/n" + "FRemark2 = " + this.FRemark2 + "/r/n" + "FRemark3 = " + this.FRemark3
				+ "/r/n" + "FRemark4 = " + this.FRemark4 + "/r/n" + "FRemark5 = " + this.FRemark5 + "/r/n"
				+ "FSysFlag = " + this.FSysFlag + "/r/n" + "FCreateTime = " + this.FCreateTime + "/r/n";
	}

	public String getFProcess() {
		return FProcess;
	}

	public void setFProcess(String fProcess) {
		FProcess = fProcess;
	}

}