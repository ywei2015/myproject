package tw.business.entity.pub;

public class ExcelData {
	private String caihongtu;//彩虹图
	//private String 
	private  String zhifangtu;//直方图
	
	private String extreme1;//单值移动
	private String extreme2; //单值移动极差值
	
	private String average1;//分组平均
	private String average2;//分组极差值
	
	private String deviation1;//分组标准偏差
	private String deviation2;
	
	private String tableName; //数据来源表明
	private String pids;//剔除数据id 采取，分割
	private String batch;//数据来源批次
	
	private String pid;//统计结果表id
	private Integer groupSize;//分组大小
	
	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public Integer getGroupSize() {
		return groupSize;
	}

	public void setGroupSize(Integer groupSize) {
		this.groupSize = groupSize;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getPids() {
		return pids;
	}

	public void setPids(String pids) {
		this.pids = pids;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public String getZhifangtu() {
		return zhifangtu;
	}

	public void setZhifangtu(String zhifangtu) {
		this.zhifangtu = zhifangtu;
	}

	public String getExtreme1() {
		return extreme1;
	}

	public void setExtreme1(String extreme1) {
		this.extreme1 = extreme1;
	}

	public String getExtreme2() {
		return extreme2;
	}

	public void setExtreme2(String extreme2) {
		this.extreme2 = extreme2;
	}

	public String getAverage1() {
		return average1;
	}

	public void setAverage1(String average1) {
		this.average1 = average1;
	}

	public String getAverage2() {
		return average2;
	}

	public void setAverage2(String average2) {
		this.average2 = average2;
	}

	public String getDeviation1() {
		return deviation1;
	}

	public void setDeviation1(String deviation1) {
		this.deviation1 = deviation1;
	}

	public String getDeviation2() {
		return deviation2;
	}

	public void setDeviation2(String deviation2) {
		this.deviation2 = deviation2;
	}

	public String getCaihongtu() {
		return caihongtu;
	}

	public void setCaihongtu(String caihongtu) {
		this.caihongtu = caihongtu;
	}
	
}
