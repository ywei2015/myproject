package tw.business.entity.msg;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import tw.business.entity.pub.BasicData;

@Entity(name="t_message")
public class Message extends BasicData {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name = "F_PID")
	private String pid;
	@Column(name="F_MSG_TITLE")
	private String title;
	@Column(name="F_MSG_CONTENT")
	private String content;
	@Column(name="F_MSG_TYPE")
	private String msgType;//消息类别
	@Column(name="F_MSG_LEVEL")
	private String level;//消息级别
	@Column(name="F_NOTIFY_TYPE")
	private Integer  notifyType;//通知类型
	@Column(name="F_FROM")
	private String  from;//来源
	@Column(name="F_PLAN_TIME")
	private String planTime;//计划时间
	@Column(name="F_EXPIRY_TIME")
	private String expiryTime;//预期时间
	@Column(name="F_SENDER")
	private String sender;//发送者
	@Column(name="F_RECEIVER")
	private String receiver;//接受者
	@Column(name="F_FACT_SENDTIME")
	private String factSendTime;
	@Column(name="F_FACT_GETTIME")
	private String factGetTime;
	@Column(name="F_DEVICE_SN")
	private String deviceSN;//接收设备号
	@Column(name="F_SEND_COUNT")
	private Double sendCount;//发送次数
	@Column(name="F_BUSINESS_PID")
	private  String businessPid;//关联业务id
	@Column(name="F_STATUS")
	private Integer status;//是否已读
	@Column(name="F_REMARK")
	private String remark;//备注
	@Column(name="F_SYS_FLAG")
	private Integer sysFlag;//是否有效
	@Column(name="F_CREATOR")
	private String creator;
	@Column(name="F_CREATE_TIME")
	private String createTime;
	@Column(name="F_LAST_MODIFIER")
	private String lastModifer;
	@Column(name="F_LAST_MODIFIED_TIME")
	private String lastModifedTime;
	
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public Integer getNotifyType() {
		return notifyType;
	}
	public void setNotifyType(Integer notifyType) {
		this.notifyType = notifyType;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getLastModifer() {
		return lastModifer;
	}
	public void setLastModifer(String lastModifer) {
		this.lastModifer = lastModifer;
	}
	public String getLastModifedTime() {
		return lastModifedTime;
	}
	public void setLastModifedTime(String lastModifedTime) {
		this.lastModifedTime = lastModifedTime;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getPlanTime() {
		return planTime;
	}
	public void setPlanTime(String planTime) {
		this.planTime = planTime;
	}
	public String getExpiryTime() {
		return expiryTime;
	}
	public void setExpiryTime(String expiryTime) {
		this.expiryTime = expiryTime;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getFactSendTime() {
		return factSendTime;
	}
	public void setFactSendTime(String factSendTime) {
		this.factSendTime = factSendTime;
	}
	public String getFactGetTime() {
		return factGetTime;
	}
	public void setFactGetTime(String factGetTime) {
		this.factGetTime = factGetTime;
	}
	public String getDeviceSN() {
		return deviceSN;
	}
	public void setDeviceSN(String deviceSN) {
		this.deviceSN = deviceSN;
	}
	public Double getSendCount() {
		return sendCount;
	}
	public void setSendCount(Double sendCount) {
		this.sendCount = sendCount;
	}
	public String getBusinessPid() {
		return businessPid;
	}
	public void setBusinessPid(String businessPid) {
		this.businessPid = businessPid;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getSysFlag() {
		return sysFlag;
	}
	public void setSysFlag(Integer sysFlag) {
		this.sysFlag = sysFlag;
	}
	
}
