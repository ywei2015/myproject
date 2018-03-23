package com.talkweb.xwzcxt.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DateUtils {
	public static int DAY=1,WEEK=2,MONTH=3;
	public static List<String> getDays(final int type){
		final  List <String>  list=new ArrayList<String>();
		final Calendar cal=Calendar.getInstance();
		//cal.set(Calendar.MONTH, 11);
		//cal.set(Calendar.DAY_OF_MONTH,28);
		int year=cal.get(Calendar.YEAR);
		int month=cal.get(Calendar.MONTH);
		int day=cal.get(Calendar.DAY_OF_MONTH);
		
		switch(type){
		case 1:
			list.add(year+"-"+(month+1)+"-"+day);
			break;
		case 2:
			int weekday=cal.get(Calendar.DAY_OF_WEEK);
			if(weekday==0){
				weekday=7;
			}
			 
			cal.add(Calendar.DAY_OF_MONTH, -(weekday-1) );
			day=cal.get(Calendar.DAY_OF_MONTH); 
			int m=month+1;
			month=cal.get(Calendar.MONTH)+1;  
			if(month>m){
				year--;
			}
			
			final int maxDay= cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			for(int i=0;i<7;i++){
				day++;

				if(day>maxDay){
					day=day-maxDay;
					month=month%12+1;
					year++;
				}
				
				list.add(year+"-"+month+"-"+day);
			}
			break;
		case 3:
			final int maxDay2= cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			for(int i=1;i<=maxDay2;i++){
				list.add(year+"-"+(month+1)+"-"+i);
			}
			break;
		}
		
		return list;
	}

	
	public static void main(String[] args) {
		System.out.println(getDays(DateUtils.DAY));
		System.out.println("########################");
		System.out.println(getDays(DateUtils.WEEK));
		System.out.println("########################");
		System.out.println(getDays(DateUtils.MONTH));
		System.out.println("########################");
	}
}
