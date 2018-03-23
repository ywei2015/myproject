package com.talkweb.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * TODO(描述这个类的作用)
 * 
 * @author: 2012-8-30，Administrator，（描述修改内容）
 */
public class Common
{
    private static final Logger logger = LoggerFactory.getLogger(Common.class);

    @Autowired
    AppConstants                appConstants;

    // 行为管理查询对象
    public enum EnumCollection
    {
        TOBACCO_WORKFLOW
    }
    private static byte[] val = { 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,  
        0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,  
        0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,  
        0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,  
        0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x00, 0x01, 0x02,  
        0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x3F, 0x3F, 0x3F, 0x3F,  
        0x3F, 0x3F, 0x3F, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F, 0x3F, 0x3F,  
        0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,  
        0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,  
        0x3F, 0x3F, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F, 0x3F, 0x3F, 0x3F,  
        0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,  
        0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,  
        0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,  
        0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,  
        0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,  
        0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,  
        0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,  
        0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,  
        0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,  
        0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,  
        0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,  
        0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,  
        0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,  
        0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F }; 
    
	public static String unescape(String s) {  
       if (s == null) {  
           return null;  
       }  
       StringBuffer sbuf = new StringBuffer();  
       int i = 0;  
       int len = s.length();  
       while (i < len) {  
           int ch = s.charAt(i);  
           if ('A' <= ch && ch <= 'Z') {  
               sbuf.append((char) ch);  
           } else if ('a' <= ch && ch <= 'z') {  
               sbuf.append((char) ch);  
           } else if ('0' <= ch && ch <= '9') {  
               sbuf.append((char) ch);  
           } else if (ch == '*' || ch == '+' || ch == '-' || ch == '/'  
                   || ch == '_' || ch == '.' || ch == '@') {  
               sbuf.append((char) ch);  
           } else if (ch == '%') {  
               int cint = 0;  
               if ('u' != s.charAt(i + 1)) {  
                   cint = (cint << 4) | val[s.charAt(i + 1)];  
                   cint = (cint << 4) | val[s.charAt(i + 2)];  
                   i += 2;  
               } else {  
                   cint = (cint << 4) | val[s.charAt(i + 2)];  
                   cint = (cint << 4) | val[s.charAt(i + 3)];  
                   cint = (cint << 4) | val[s.charAt(i + 4)];  
                   cint = (cint << 4) | val[s.charAt(i + 5)];  
                   i += 5;  
               }  
               sbuf.append((char) cint);  
           } else {  
               sbuf.append((char) ch);  
           }  
           i++;  
       }  
       return sbuf.toString();  
	} 
    public void aa()
    {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2006);
        cal.set(Calendar.MONTH, 10);
        cal.set(Calendar.DAY_OF_MONTH, 3);
        int weekno = cal.get(Calendar.WEEK_OF_YEAR);
    }
	public String GetNow(){
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(currentTime);
	}
	public String GetNowTime(){
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formatter.format(currentTime);
	}
    public Date strToDate(String strDate)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    // 获取日期对应月份中的第几周
    public String GetDateWeek(String date)
    {
        String strWeek = "";
        Date dtmTargetDate = strToDate(date);
        int dtmDay = dtmTargetDate.getDay();
        int dtmMonth = dtmTargetDate.getMonth();
        int dtmYear = dtmTargetDate.getYear();

        Date dtmStartDate = strToDate(dtmYear + "-" + dtmMonth + "-" + "01");

        Calendar cal = Calendar.getInstance();
        cal.set(dtmYear, dtmMonth, dtmDay);
        int intWeekday = cal.get(Calendar.WEEK_OF_YEAR);

        int intAddon = 8 - intWeekday;

        int intWeek1 = intAddon;
        int intWeek2 = intWeek1 + 7;
        int intWeek3 = intWeek2 + 7;
        int intWeek4 = intWeek3 + 7;
        int intWeek5 = intWeek4 + 7;
        int intWeek6 = intWeek5 + 7;

        if (dtmDay <= intWeek6)
        {
            strWeek = "Week 6";
        }
        if (dtmDay <= intWeek5)
        {
            strWeek = "Week 5";
        }
        if (dtmDay <= intWeek4)
        {
            strWeek = "Week 4";
        }
        if (dtmDay <= intWeek3)
        {
            strWeek = "Week 3";
        }
        if (dtmDay <= intWeek2)
        {
            strWeek = "Week 2";
        }
        if (dtmDay <= intWeek1)
        {
            strWeek = "Week 1";
        }
        return strWeek;
    }    
    /**
     * 设置DOUBLE型的小数点精度 
     * @param a:数字
     * @param b:精度
     * @return
     */
    public Double mathTwoSet(Double a){
        if(StingUtil.isEmpty(a))
            a=0.0;
        BigDecimal ad=new BigDecimal(a);
        ad=ad.setScale(2,BigDecimal.ROUND_HALF_UP);
        return ad.doubleValue();
    }
    public void WriteLog(String log) throws Exception
    {
        File f = new File("d:/a.txt");
        FileInputStream fip = new FileInputStream(f);
        // 构建FileInputStream对象
        InputStreamReader reader = new InputStreamReader(fip, "UTF-8");
        // 构建InputStreamReader对象,编码与写入相同

        StringBuffer sb = new StringBuffer();
        while (reader.ready())
        {
            sb.append((char) reader.read());
            // 转成char加到StringBuffer对象中
        }

        FileOutputStream fop = new FileOutputStream(f);
        // 构建FileOutputStream对象,文件不存在会自动新建
        OutputStreamWriter writer = new OutputStreamWriter(fop, "UTF-8");
        // 构建OutputStreamWriter对象,参数可以指定编码,默认为操作系统默认编码,windows上是gbk
        writer.append(sb.toString() + log);
        // 写入到缓冲区
        writer.append("\r\n");
        // writer.flush();
        // 刷新缓存冲,写入到文件,如果下面已经没有写入的内容了,直接close也会写入
        writer.close();
        // 关闭写入流,同时会把缓冲区内容写入文件,所以上面的注释掉
        fop.close();
        reader.close();
        // 关闭读取流
        fip.close();
        // 关闭输入流,释放系统资源
    }

    // 对象转换为货币形式
    public String FormatDouble(Object dou)
    {
        if (dou.equals(null) || dou.toString().length() <= 3)
        {
            return "0.0";
        }
        double d = Double.parseDouble(dou.toString());
        // 使用本地默认格式输出数
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        // 设置两位小数
        numberFormat.setMaximumFractionDigits(1);
        return numberFormat.format(d);
    }

    // 对象转换为货币形式（四舍五入到万为单位）
    public String FormatRounding(Object dou)
    {
        if (dou.equals(null) || dou.toString().length() <= 3)
        {
            return "0.0";
        }
        double d = Double.parseDouble(dou.toString()) / 10000;
        DecimalFormat decimalFormat = new DecimalFormat("######.0");
        return decimalFormat.format(d);
    }

    // 对象转换为货币形式（四舍五入到万为单位）
    public String FormatRoundingOne(Object dou)
    {
        if (dou.equals(null) || dou.toString().length() <= 3)
        {
            return "0.0";
        }
        double d = Double.parseDouble(dou.toString());
        DecimalFormat decimalFormat = new DecimalFormat("######.0");
        return decimalFormat.format(d);
    }

    private String getIdByName(String sourceFieldValue, List list, String colName, String colId)
    {
        String id = "-1";
        for (int i = 0; i < list.size(); i++)
        {
            if (!((Map) list.get(i)).get(colName).toString().equals(sourceFieldValue))
                continue;
            id = ((Map) list.get(i)).get(colId).toString();
            break;
        }

        return id;
    }

    private boolean isNumber(String str)
    {
        return str.matches("[\\d]+");
    }

    private boolean isCode(String str)
    {
        return str.matches("^[A-Za-z0-9_]+$");
    }

    private boolean isYear(String str)
    {
        Pattern p = Pattern.compile("[0-9]*");
        Matcher m = p.matcher(str);
        if (!m.matches())
            return false;
        return str.length() == 4;
    }

    private boolean checkPhone(String phone)
    {
        return phone
                .matches("((^(13|15|18)[0-9]{9}$)|(^0[1,2]{1} \\d{1}-?\\d{8}$)|(^0[3-9]{1}\\d{2}-?\\d{7,8}$)|(^0[1,2]{1}\\d{1}-?\\d{8}-(\\d{1,4})$)|(^0[3-9]{1}\\d{2}-?\\d{7,8}-(\\d{1,4})$))");
    }

    private boolean isDate(String str)
    {
        String eL = "[0-9]{4}-[0-1]{0,1}[0-9]-[0-9]{1,2}|[0-9]{4}/[0-1]{0,1}[0-9]/[0-9]{1,2}";
        Pattern p = Pattern.compile(eL);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    private boolean isMobile(String str)
    {
        String s = str.toString().trim().substring(0, 3);
        String mobile[] = { "134", "135", "136", "137", "138", "139", "147", "150", "151", "152", "157", "158", "159", "182",
                "183", "187", "188" };
        boolean t = false;
        for (int i = 0; i < mobile.length; i++)
            if (mobile[i].equals(s))
                t = true;

        return t;
    }

    private boolean isEnglish(String str)
    {
        return str.matches("^1\\d{10}");
    }

    private boolean isPhone(String str)
    {
        if (str.endsWith(".0"))
            str = str.substring(0, str.length() - 2);
        return str.matches("^\\d+(-|_)?\\d+$");
    }

}
