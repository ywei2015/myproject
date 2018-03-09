package spc.beans.base;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DatetimeEx {

	private static String formatStr19 = "yyyy-MM-dd HH:mm:ss";
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
}
