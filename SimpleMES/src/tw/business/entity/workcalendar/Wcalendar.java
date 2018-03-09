package tw.business.entity.workcalendar;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
 
/**
 * 工作日历
 * @author XSH
 *
 */
@Entity
@Table(name = "t_calendar")
public class Wcalendar {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name = "F_PID")
	private String pid; 

	/**
	 * 日历类型
	 */
	@Column(name = "F_CALENDAR_TYPE")
	private String calendartype;

	/**
	 * 日期
	 */
	@Column(name = "F_DATE")
	private Date workdate;

	/**
	 * 周几
	 */
	@Column(name = "F_DAY_OF_WEEK")
	private int dayofweek;
	
	/**
	 * 原本是否工作日
	 */
	@Column(name = "F_THEORY_ISWORKDAY")
	private String isworkdaytheory;
	
	/**
	 * 实际是否工作日
	 */
	@Column(name = "F_FACT_ISWORKDAY")
	private String isworkdayfact; 
 

	@Column(name = "F_REMARK")
	private String remark;
	
	@Column(name = "F_SYS_FLAG")
	private String sysFlag;
	
	@Column(name = "F_CREATOR")
	private String creator;
	
	@Transient
	private String creatorName;
	
	@Column(name = "F_CREATE_TIME")
	private Timestamp createTime;
	
	@Column(name = "F_LAST_MODIFIER")
	private String lastModifier;
	
	@Transient
	private String lastModifierName;
	
	@Column(name = "F_LAST_MODIFIED_TIME")
	private Timestamp lastModifiedTime;

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getCalendartype() {
		return calendartype;
	}

	public void setCalendartype(String calendartype) {
		this.calendartype = calendartype;
	}

	public Date getWorkdate() {
		return workdate;
	}

	public void setWorkdate(Date workdate) {
		this.workdate = workdate;
	}

	public int getDayofweek() {
		return dayofweek;
	}

	public void setDayofweek(int dayofweek) {
		this.dayofweek = dayofweek;
	}

	public String getIsworkdaytheory() {
		return isworkdaytheory;
	}

	public void setIsworkdaytheory(String isworkdaytheory) {
		this.isworkdaytheory = isworkdaytheory;
	}

	public String getIsworkdayfact() {
		return isworkdayfact;
	}

	public void setIsworkdayfact(String isworkdayfact) {
		this.isworkdayfact = isworkdayfact;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSysFlag() {
		return sysFlag;
	}

	public void setSysFlag(String sysFlag) {
		this.sysFlag = sysFlag;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getLastModifier() {
		return lastModifier;
	}

	public void setLastModifier(String lastModifier) {
		this.lastModifier = lastModifier;
	}

	public String getLastModifierName() {
		return lastModifierName;
	}

	public void setLastModifierName(String lastModifierName) {
		this.lastModifierName = lastModifierName;
	}

	public Timestamp getLastModifiedTime() {
		return lastModifiedTime;
	}

	public void setLastModifiedTime(Timestamp lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}

	
}
