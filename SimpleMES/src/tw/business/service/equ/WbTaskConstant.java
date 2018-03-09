package tw.business.service.equ;

/**
 * 设备管理相关常量
 * @author XSH
 *
 */
public class WbTaskConstant {
	public final static String DefaultSysFlag = "1"; //0-无效，1-有效
	public final static String CREATOR_USERID = "1001"; //admin管理员ID
	public final static String CALENDAR_TYPE_NORMAL = "0"; //通用基础日历
	
	//执行频率(1-班,10-天,20-周,30-月)
	public final static String FREQUENCY_BY_SHIFT = "1"; //每班
	public final static String FREQUENCY_BY_DAY = "10"; //每日
	public final static String FREQUENCY_BY_WEEK = "20"; //每周
	public final static String FREQUENCY_BY_MONTH = "30"; //每月
	
	//任务状态(0-草稿,5-下达,10-执行中,20-正常完成,44-任务取消) 
	public final static String WBTASK_STATUS_INIT = "0"; //草稿
	public final static String WBTASK_STATUS_ISSUED = "5"; //下达
	public final static String WBTASK_STATUS_EXECUTING = "10"; //执行中
	public final static String WBTASK_STATUS_DONE = "20"; //正常完成
	public final static String WBTASK_STATUS_CANCEL = "44"; //任务取消
	
	//作业对象类型(1-机台,2-机台类型)
	public final static String WBTASK_WORKOBJTYPE_EQUID = "1"; //机台
	public final static String WBTASK_WORKOBJTYPE_EQUTYPE = "2"; //机台类型
	
	
}
