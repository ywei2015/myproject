package tw.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DatetimeEx {

	private static String formatStr19 = "yyyy-MM-dd HH:mm:ss";
	private static String formatStr10 = "yyyy-MM-dd";
	private static String formatStr14 = "yyyyMMddHHmmss";
	//private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static String DAYS = "days";
	private static String HOURS = "hours";
	private static String MINUTES = "minutes";
	private static String SECONDES = "seconds";
	private static int days;
	private static int hours;
	private static int minutes;
	private static int seconds;
	
	public static String SubtractDT(Date maxdt, Date mindt){
		String str = "";  
		long sub =  maxdt.getTime() - mindt.getTime();  
		days = (int) (sub/(1000 * 60 * 60 * 24));  
		hours = (int) (sub/(1000 * 60 * 60));  
		minutes = (int) (sub/(1000 * 60));  
		seconds = (int) (sub/(1000)); 
		str = String.format("%s天,%s时%s分%s秒", days, hours%24, minutes%60, seconds%60);
		return str;
	} 
 
	public static java.sql.Date sqlDate(){
		Date dt = new Date();
		return new java.sql.Date(dt.getTime());
	}
	public static java.sql.Timestamp sqlTimestamp(){
		Date dt = new Date();
		return new java.sql.Timestamp(dt.getTime());
	}
	public static java.sql.Timestamp sqlTimestamp(Date dt){
		if(dt==null) return null;
		return new java.sql.Timestamp(dt.getTime());
	}
	public static java.sql.Timestamp sqlTimestampST(java.util.Date dt){ 
		//return new java.sql.Timestamp(dt.getTime());
		return new java.sql.Timestamp(dt.getYear(),dt.getMonth(),dt.getDay(),0,0,0,0);
	}
	public static java.sql.Timestamp sqlTimestampST(java.sql.Date dt){ 
		//return new java.sql.Timestamp(dt.getTime());
		return new java.sql.Timestamp(dt.getYear(),dt.getMonth(),dt.getDay(),0,0,0,0);
	}
	public static java.sql.Timestamp sqlTimestampET(java.util.Date dt){ 
		return new java.sql.Timestamp(dt.getYear(),dt.getMonth(),dt.getDay(),23,59,59,999);
	}
	public static java.sql.Timestamp sqlTimestampET(java.sql.Date dt){ 
		return new java.sql.Timestamp(dt.getYear(),dt.getMonth(),dt.getDay(),23,59,59,999);
	}
	
	public static String toStr(Date dt, String formatstr){ 
		SimpleDateFormat sdf = new SimpleDateFormat(formatstr);
		return sdf.format(dt);
	}
	
	public static String curDT10(){
		return toStr10(new Date());
	}
	public static String toStr10(Date dt){ 
		SimpleDateFormat sdf = new SimpleDateFormat(formatStr10);
		return sdf.format(dt);
	}
	public static Date Str2Date10(String str10){
		Date dt = null;
		try
		{
			SimpleDateFormat sdf = new SimpleDateFormat(formatStr10);
			dt = sdf.parse(str10);
		}
		catch (ParseException e)
		{
			System.out.println(e.getMessage());
		}
		return dt;
	}
	
	public static String curDT14(){
		return toStr14(new Date());
	}
	public static String toStr14(Date dt){ 
		SimpleDateFormat sdf = new SimpleDateFormat(formatStr14);
		return sdf.format(dt);
	}
	public static String toStr19(Date dt){ 
		SimpleDateFormat sdf = new SimpleDateFormat(formatStr19);
		return sdf.format(dt);
	} 
	public static Date Str2Date(String str19){
		Date dt = null;
		try
		{
			SimpleDateFormat sdf = new SimpleDateFormat(formatStr19);
			dt = sdf.parse(str19);
		}
		catch (ParseException e)
		{
			System.out.println(e.getMessage());
		}
		return dt;
	}
	public static Date Str2Date14(String str14){
		Date dt = null;
		try
		{
			SimpleDateFormat sdf = new SimpleDateFormat(formatStr14);
			dt = sdf.parse(str14);
		}
		catch (ParseException e)
		{
			System.out.println(e.getMessage());
		}
		return dt;
	}
	
	public static Calendar toCalendar(Date dt){
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		return cal;
		//cal.add(Calendar.DAY_OF_MONTH, -1);//取当前日期的前一天.   
	}

	public static Date AddSecond(Date dt, int sencondcount){
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt); 
		cal.add(Calendar.SECOND, 6);//+6s
		return cal.getTime();
	}
	
	public static int BetweenDays(Date maxdt, Date mindt){
		long sub =  maxdt.getTime() - mindt.getTime();  
		days = (int) (sub/(1000 * 60 * 60 * 24));  
		return days;
	}
	public static int BetweenHours(Date maxdt, Date mindt){
		long sub =  maxdt.getTime() - mindt.getTime();  
		hours = (int) (sub/(1000 * 60 * 60));  
		return hours;
	}
	public static int BetweenMinutes(Date maxdt, Date mindt){
		long sub =  maxdt.getTime() - mindt.getTime();  
		minutes = (int) (sub/(1000 * 60));  
		return minutes;
	}
	public static int BetweenSeconds(Date maxdt, Date mindt){
		long sub =  maxdt.getTime() - mindt.getTime();  
		seconds = (int) (sub/(1000)); 
		return seconds;
	}
	public static Map<String, Integer> Subtract(Date maxdt, Date mindt){
		Map<String, Integer> map = new HashMap<String, Integer>();  
		long sub =  maxdt.getTime() - mindt.getTime();  
		days = (int) (sub/(1000 * 60 * 60 * 24));  
		hours = (int) (sub/(1000 * 60 * 60));  
		minutes = (int) (sub/(1000 * 60));  
		seconds = (int) (sub/(1000)); 
		map.put(DAYS, days);
		map.put(HOURS, hours);
		map.put(MINUTES, minutes);
		map.put(SECONDES, seconds);
		return map;
	}

	//获取当前日期为所在年份的第几周
	public static int getWeekofYear(java.sql.Date dt) {
		if (dt == null) {
			return 0;
		}
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(Calendar.SUNDAY);  
		cal.setTime(dt);
		return cal.get(Calendar.WEEK_OF_YEAR);
	}
	//获取本周的开始时间
	public static java.sql.Timestamp getBeginDayOfWeek(java.sql.Date dt) { 
		if (dt == null) {
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(Calendar.SUNDAY); 
		cal.setTime(dt);
		int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
		if (dayofweek == 1) {
			dayofweek += 7;
		}
		cal.add(Calendar.DATE, 2 - dayofweek);  
		return sqlTimestampST(cal.getTime());
	}
	// 获取本周的结束时间
	public static java.sql.Timestamp getEndDayOfWeek(java.sql.Date dt) {
		if (dt == null) {
			return null;
		} 
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(Calendar.SUNDAY); 
		cal.setTime(getBeginDayOfWeek(dt));
		cal.add(Calendar.DAY_OF_WEEK, 6);
		Date weekEndSta = cal.getTime();
		return sqlTimestampET(weekEndSta);
	} 
	// 获取本月的开始时间
	public static java.sql.Timestamp getBeginDayOfMonth(java.sql.Date dt) {  
		if (dt == null) {
			return null; 
		}
		java.sql.Timestamp ts = new java.sql.Timestamp(dt.getYear(),dt.getMonth(),1,0,0,0,0);
		return ts;
	}
	//获取本月的结束时间
	public static java.sql.Timestamp getEndDayOfMonth(java.sql.Date dt) {
		if (dt == null) {
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		int MaxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		java.sql.Timestamp ts = new java.sql.Timestamp(dt.getYear(),dt.getMonth(),MaxDay,23,59,59,999);
		return ts; 
	}
	
	
}
