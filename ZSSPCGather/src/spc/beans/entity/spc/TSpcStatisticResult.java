package spc.beans.entity.spc;

public class TSpcStatisticResult {
	private String FPid;
	private String FBatch;
	private String FWorkshop;
	private String FProductLine;
	private String FWorksection;
	private String FProcess;
	private String FParamId;
	private String FParamName;
	private String FBrandId;
	private String FBrandName;
	private String FWorktime;
	private String FWorkteam;
	private String FStatisticType;
	private String FResultType;
	private String FStartTime;
	private String FEndTime;
	private Double FUpline;
	private Double FLowline;
	private Double FTarget;
	private Double FModulus;
	private Integer FSampleCount;
	private Integer FValidSamplecount;
	private Integer FUnquanlified;
	private Double FSamplePassrate;
	private Double FSpcPassrate;
	private Double FCa;
	private Double FCp;
	private Double FPp;
	private Double FCpk;
	private Double FPpk;
	private Double FAverage;
	private Double FMax;
	private Double FMin;
	private Double FSigmaShort;
	private Double FSigmaLong;
	private Double FRAverage;
	private Double FSAverage;
	private Integer FGroupsize;
	private Integer FGroupcount;
	private Double FCv;
	private Double FSkewness;
	private Double FKurtosis;
	private String FState;
	private String FRemark1;
	private String FRemark2;
	private String FRemark3;
	private String FRemark4;
	private String FRemark5;
	private String FSysFlag;
	private String FCreator;
	private String FCreateTime;
	private String FLastModifier;
	private String FLastModifiedTime;
	
	
	public TSpcStatisticResult(){}
	
	public TSpcStatisticResult(String batch, String process, String brand, String parameter, String stime){
		this.FPid = java.util.UUID.randomUUID().toString();
		this.FBatch = batch;
		this.FProcess = process;
		this.FBrandId = brand;
		this.FParamId = parameter;
		this.FStatisticType = "1"; //1-统计
		this.FStartTime = stime;
		this.FResultType = "0"; //0-自动采集
		this.FState = "0"; //0-创建
		this.FSysFlag = "1"; //1-有效
	}
	
	public String toFirstCreateSql(){
		String sql = null;
		String fields = "F_PID,F_BATCH,F_PARAM_ID,F_BRAND_ID,F_STATISTIC_TYPE,F_RESULT_TYPE,F_START_TIME,F_STATE,F_SYS_FLAG";
		String values = String.format("'%s', '%s', '%s', '%s', %s, '%s', '%s', %s, %s", 
				this.FPid,
				this.FBatch,
				this.FParamId,
				this.FBrandId,
				this.FStatisticType,  
				this.FResultType,
				this.FStartTime,
				//(this.remark1==null)?"null": "'"+this.remark1+"'", 
				this.FState,
				this.FSysFlag
				);
		//INSERT INTO table_name (列1, 列2,...) VALUES (值1, 值2,....)
		sql = String.format("INSERT INTO %s(%s) VALUES (%s) ", "T_SPC_STATISTIC_RESULT", fields, values); 
		return sql;
	}


	public String getIsExistSQL(){ 
		String sql = String.format("Select count(1) from %s where F_BATCH='%s' and F_PARAM_ID='%s'",
				"T_SPC_STATISTIC_RESULT", this.getFBatch(), this.getFParamId());
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

	public String getFWorkshop() {
		return this.FWorkshop;
	}

	public void setFWorkshop(String FWorkshop) {
		this.FWorkshop = FWorkshop;
	}

	public String getFProductLine() {
		return this.FProductLine;
	}

	public void setFProductLine(String FProductLine) {
		this.FProductLine = FProductLine;
	}

	public String getFWorksection() {
		return this.FWorksection;
	}

	public void setFWorksection(String FWorksection) {
		this.FWorksection = FWorksection;
	}

	public String getFProcess() {
		return this.FProcess;
	}

	public void setFProcess(String FProcess) {
		this.FProcess = FProcess;
	}

	public String getFParamId() {
		return this.FParamId;
	}

	public void setFParamId(String FParamId) {
		this.FParamId = FParamId;
	}

	public String getFParamName() {
		return this.FParamName;
	}

	public void setFParamName(String FParamName) {
		this.FParamName = FParamName;
	}

	public String getFBrandId() {
		return this.FBrandId;
	}

	public void setFBrandId(String FBrandId) {
		this.FBrandId = FBrandId;
	}

	public String getFBrandName() {
		return this.FBrandName;
	}

	public void setFBrandName(String FBrandName) {
		this.FBrandName = FBrandName;
	}

	public String getFWorktime() {
		return this.FWorktime;
	}

	public void setFWorktime(String FWorktime) {
		this.FWorktime = FWorktime;
	}

	public String getFWorkteam() {
		return this.FWorkteam;
	}

	public void setFWorkteam(String FWorkteam) {
		this.FWorkteam = FWorkteam;
	}

	public String getFResultType() {
		return this.FResultType;
	}

	public void setFResultType(String FResultType) {
		this.FResultType = FResultType;
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

	public Double getFUpline() {
		return this.FUpline;
	}

	public void setFUpline(Double FUpline) {
		this.FUpline = FUpline;
	}

	public Double getFLowline() {
		return this.FLowline;
	}

	public void setFLowline(Double FLowline) {
		this.FLowline = FLowline;
	}

	public Double getFTarget() {
		return this.FTarget;
	}

	public void setFTarget(Double FTarget) {
		this.FTarget = FTarget;
	}

	public Double getFModulus() {
		return this.FModulus;
	}

	public void setFModulus(Double FModulus) {
		this.FModulus = FModulus;
	}

	public Integer getFSampleCount() {
		return this.FSampleCount;
	}

	public void setFSampleCount(Integer FSampleCount) {
		this.FSampleCount = FSampleCount;
	}

	public Integer getFValidSamplecount() {
		return this.FValidSamplecount;
	}

	public void setFValidSamplecount(Integer FValidSamplecount) {
		this.FValidSamplecount = FValidSamplecount;
	}

	public Integer getFUnquanlified() {
		return this.FUnquanlified;
	}

	public void setFUnquanlified(Integer FUnquanlified) {
		this.FUnquanlified = FUnquanlified;
	}

	public Double getFSamplePassrate() {
		return this.FSamplePassrate;
	}

	public void setFSamplePassrate(Double FSamplePassrate) {
		this.FSamplePassrate = FSamplePassrate;
	}

	public Double getFSpcPassrate() {
		return this.FSpcPassrate;
	}

	public void setFSpcPassrate(Double FSpcPassrate) {
		this.FSpcPassrate = FSpcPassrate;
	}

	public Double getFCa() {
		return this.FCa;
	}

	public void setFCa(Double FCa) {
		this.FCa = FCa;
	}

	public Double getFCp() {
		return this.FCp;
	}

	public void setFCp(Double FCp) {
		this.FCp = FCp;
	}

	public Double getFPp() {
		return this.FPp;
	}

	public void setFPp(Double FPp) {
		this.FPp = FPp;
	}

	public Double getFCpk() {
		return this.FCpk;
	}

	public void setFCpk(Double FCpk) {
		this.FCpk = FCpk;
	}

	public Double getFPpk() {
		return this.FPpk;
	}

	public void setFPpk(Double FPpk) {
		this.FPpk = FPpk;
	}

	public Double getFAverage() {
		return this.FAverage;
	}

	public void setFAverage(Double FAverage) {
		this.FAverage = FAverage;
	}

	public Double getFMax() {
		return this.FMax;
	}

	public void setFMax(Double FMax) {
		this.FMax = FMax;
	}

	public Double getFMin() {
		return this.FMin;
	}

	public void setFMin(Double FMin) {
		this.FMin = FMin;
	}

	public Double getFSigmaShort() {
		return this.FSigmaShort;
	}

	public void setFSigmaShort(Double FSigmaShort) {
		this.FSigmaShort = FSigmaShort;
	}

	public Double getFSigmaLong() {
		return this.FSigmaLong;
	}

	public void setFSigmaLong(Double FSigmaLong) {
		this.FSigmaLong = FSigmaLong;
	}

	public Double getFRAverage() {
		return this.FRAverage;
	}

	public void setFRAverage(Double FRAverage) {
		this.FRAverage = FRAverage;
	}

	public Double getFSAverage() {
		return this.FSAverage;
	}

	public void setFSAverage(Double FSAverage) {
		this.FSAverage = FSAverage;
	}

	public Integer getFGroupsize() {
		return this.FGroupsize;
	}

	public void setFGroupsize(Integer FGroupsize) {
		this.FGroupsize = FGroupsize;
	}

	public Integer getFGroupcount() {
		return this.FGroupcount;
	}

	public void setFGroupcount(Integer FGroupcount) {
		this.FGroupcount = FGroupcount;
	}

	public Double getFCv() {
		return this.FCv;
	}

	public void setFCv(Double FCv) {
		this.FCv = FCv;
	}

	public Double getFSkewness() {
		return this.FSkewness;
	}

	public void setFSkewness(Double FSkewness) {
		this.FSkewness = FSkewness;
	}

	public Double getFKurtosis() {
		return this.FKurtosis;
	}

	public void setFKurtosis(Double FKurtosis) {
		this.FKurtosis = FKurtosis;
	}

	public String getFState() {
		return this.FState;
	}

	public void setFState(String FState) {
		this.FState = FState;
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

	public String getFCreator() {
		return this.FCreator;
	}

	public void setFCreator(String FCreator) {
		this.FCreator = FCreator;
	}

	public String getFCreateTime() {
		return this.FCreateTime;
	}

	public void setFCreateTime(String FCreateTime) {
		this.FCreateTime = FCreateTime;
	}

	public String getFLastModifier() {
		return this.FLastModifier;
	}

	public void setFLastModifier(String FLastModifier) {
		this.FLastModifier = FLastModifier;
	}

	public String getFLastModifiedTime() {
		return this.FLastModifiedTime;
	}

	public void setFLastModifiedTime(String FLastModifiedTime) {
		this.FLastModifiedTime = FLastModifiedTime;
	}

	@Override
	public String toString() {
		return "FPid = " + this.FPid + "/r/n" + "FBatch = " + this.FBatch + "/r/n" + "FWorkshop = " + this.FWorkshop
				+ "/r/n" + "FProductLine = " + this.FProductLine + "/r/n" + "FWorksection = " + this.FWorksection
				+ "/r/n" + "FProcess = " + this.FProcess + "/r/n" + "FParamId = " + this.FParamId + "/r/n"
				+ "FParamName = " + this.FParamName + "/r/n" + "FBrandId = " + this.FBrandId + "/r/n" + "FBrandName = "
				+ this.FBrandName + "/r/n" + "FWorktime = " + this.FWorktime + "/r/n" + "FWorkteam = " + this.FWorkteam
				+ "/r/n" + "FResultType = " + this.FResultType + "/r/n" + "FStartTime = " + this.FStartTime + "/r/n"
				+ "FEndTime = " + this.FEndTime + "/r/n" + "FUpline = " + this.FUpline + "/r/n" + "FLowline = "
				+ this.FLowline + "/r/n" + "FTarget = " + this.FTarget + "/r/n" + "FModulus = " + this.FModulus + "/r/n"
				+ "FSampleCount = " + this.FSampleCount + "/r/n" + "FValidSamplecount = " + this.FValidSamplecount
				+ "/r/n" + "FUnquanlified = " + this.FUnquanlified + "/r/n" + "FSamplePassrate = "
				+ this.FSamplePassrate + "/r/n" + "FSpcPassrate = " + this.FSpcPassrate + "/r/n" + "FCa = " + this.FCa
				+ "/r/n" + "FCp = " + this.FCp + "/r/n" + "FPp = " + this.FPp + "/r/n" + "FCpk = " + this.FCpk + "/r/n"
				+ "FPpk = " + this.FPpk + "/r/n" + "FAverage = " + this.FAverage + "/r/n" + "FMax = " + this.FMax
				+ "/r/n" + "FMin = " + this.FMin + "/r/n" + "FSigmaShort = " + this.FSigmaShort + "/r/n"
				+ "FSigmaLong = " + this.FSigmaLong + "/r/n" + "FRAverage = " + this.FRAverage + "/r/n" + "FSAverage = "
				+ this.FSAverage + "/r/n" + "FGroupsize = " + this.FGroupsize + "/r/n" + "FGroupcount = "
				+ this.FGroupcount + "/r/n" + "FCv = " + this.FCv + "/r/n" + "FSkewness = " + this.FSkewness + "/r/n"
				+ "FKurtosis = " + this.FKurtosis + "/r/n" + "FState = " + this.FState + "/r/n" + "FRemark1 = "
				+ this.FRemark1 + "/r/n" + "FRemark2 = " + this.FRemark2 + "/r/n" + "FRemark3 = " + this.FRemark3
				+ "/r/n" + "FRemark4 = " + this.FRemark4 + "/r/n" + "FRemark5 = " + this.FRemark5 + "/r/n"
				+ "FSysFlag = " + this.FSysFlag + "/r/n" + "FCreator = " + this.FCreator + "/r/n" + "FCreateTime = "
				+ this.FCreateTime + "/r/n" + "FLastModifier = " + this.FLastModifier + "/r/n" + "FLastModifiedTime = "
				+ this.FLastModifiedTime + "/r/n";
	}

	public String getFStatisticType() {
		return FStatisticType;
	}

	public void setFStatisticType(String fStatisticType) {
		FStatisticType = fStatisticType;
	}
}