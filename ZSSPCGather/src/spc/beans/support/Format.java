package spc.beans.support;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
public class Format {

	/**
	 *
	 * @param strTime
	 * @param type0,kaishi,1jieshu
	 * @return
	 */
	public static String formatStringToTimestamp(String strTime,int type) {
		String retTime = strTime;
		if(type==0&&retTime!=null&&!retTime.equals(""))retTime=retTime+" 00:00:00";
		if(type==1&&retTime!=null&&!retTime.equals(""))retTime=retTime+" 23:59:59";
	    return retTime;
   }
   public static String getTimestamp(String str)
   {
	   String strr="0000-00-00 00:00:00";
	   if(str!=null&&str.length()>=19)
	   {
		   strr=str.substring(0,19);
	   }
	   return strr;
   }
   public static String formatString(String str)
   {
	   String strr=null;
	   if(str!=null)
	   {
		   SimpleDateFormat f=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		   try{
			   strr=f.format(f.parse(str));
		   }catch(Exception e)
		   {
			   strr=str;
//			   System.out.println("格式化数据出错");
		   }
	   }
	   return strr;
   }
   public static double formatNumber(double data,int length)
   {
	   String len="#.#";
	   for(int i=0;i<length-1;i++)len=len+"#";
	   DecimalFormat   df   =   new   DecimalFormat(len);

	   double dd=0;
	   try{
		   String d=df.format(data);
		   dd=Double.parseDouble(d);
	   }catch(Exception e)
	   {
		   dd=0;
	   }
	   return dd;
   }
}
