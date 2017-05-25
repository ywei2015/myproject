package sail.beans.entity;

import java.io.Serializable;
/**
 * 稀释产出小批次
 * */
public class BatSpiceTurn implements Serializable {

	private String pid;
	private String workorder; //稀释工单号
	private String producedate;
	private String matecode;
	private String matename;
	private String masterbatch; //原生产批次
	private String dilutepotcode;//调制罐号
	private String dilutepotname;//调制罐号名称
	private String slave_batch;//运输物料批次
	private Double quantity;  // 数量
	private String unit;
	private String operator;   //打码人ID
	private String operatorname;
	private String operatetime;
	private String iscost;  //是否已消耗
	private String workorderin; //消耗投入的工单
	private String inpotcode;   //投料现场罐号
	private String inpotname;    //投料现场罐名称
	private String operator2;      //扫码人ID
	private String operatorname2;
	private String operatetime2;
	private String remark1;
	private String remark2;
	private String remark3;
	private String remark4;
	private String remark5;
	private String remark;
	private String sysflag;
	private String creator;
	private String createtime;
	private String lastmodifier;
	private String lastmodifiedtime;
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getWorkorder() {
		return workorder;
	}
	public void setWorkorder(String workorder) {
		this.workorder = workorder;
	}
	public String getProducedate() {
		return producedate;
	}
	public void setProducedate(String producedate) {
		this.producedate = producedate;
	}
	public String getMatecode() {
		return matecode;
	}
	public void setMatecode(String matecode) {
		this.matecode = matecode;
	}
	public String getMatename() {
		return matename;
	}
	public void setMatename(String matename) {
		this.matename = matename;
	}
	public String getMasterbatch() {
		return masterbatch;
	}
	public void setMasterbatch(String masterbatch) {
		this.masterbatch = masterbatch;
	}
	public String getDilutepotcode() {
		return dilutepotcode;
	}
	public void setDilutepotcode(String dilutepotcode) {
		this.dilutepotcode = dilutepotcode;
	}
	public String getDilutepotname() {
		return dilutepotname;
	}
	public void setDilutepotname(String dilutepotname) {
		this.dilutepotname = dilutepotname;
	}
	public String getSlave_batch() {
		return slave_batch;
	}
	public void setSlave_batch(String slave_batch) {
		this.slave_batch = slave_batch;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getOperatorname() {
		return operatorname;
	}
	public void setOperatorname(String operatorname) {
		this.operatorname = operatorname;
	}
	public String getOperatetime() {
		return operatetime;
	}
	public void setOperatetime(String operatetime) {
		this.operatetime = operatetime;
	}
	public String getIscost() {
		return iscost;
	}
	public void setIscost(String iscost) {
		this.iscost = iscost;
	}
	public String getWorkorderin() {
		return workorderin;
	}
	public void setWorkorderin(String workorderin) {
		this.workorderin = workorderin;
	}
	public String getInpotcode() {
		return inpotcode;
	}
	public void setInpotcode(String inpotcode) {
		this.inpotcode = inpotcode;
	}
	public String getInpotname() {
		return inpotname;
	}
	public void setInpotname(String inpotname) {
		this.inpotname = inpotname;
	}
	public String getOperator2() {
		return operator2;
	}
	public void setOperator2(String operator2) {
		this.operator2 = operator2;
	}
	public String getOperatorname2() {
		return operatorname2;
	}
	public void setOperatorname2(String operatorname2) {
		this.operatorname2 = operatorname2;
	}
	public String getOperatetime2() {
		return operatetime2;
	}
	public void setOperatetime2(String operatetime2) {
		this.operatetime2 = operatetime2;
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
	public String getRemark3() {
		return remark3;
	}
	public void setRemark3(String remark3) {
		this.remark3 = remark3;
	}
	public String getRemark4() {
		return remark4;
	}
	public void setRemark4(String remark4) {
		this.remark4 = remark4;
	}
	public String getRemark5() {
		return remark5;
	}
	public void setRemark5(String remark5) {
		this.remark5 = remark5;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getSysflag() {
		return sysflag;
	}
	public void setSysflag(String sysflag) {
		this.sysflag = sysflag;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getLastmodifier() {
		return lastmodifier;
	}
	public void setLastmodifier(String lastmodifier) {
		this.lastmodifier = lastmodifier;
	}
	public String getLastmodifiedtime() {
		return lastmodifiedtime;
	}
	public void setLastmodifiedtime(String lastmodifiedtime) {
		this.lastmodifiedtime = lastmodifiedtime;
	}
	public double getQuantity() {
		return quantity;
	}
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
}
