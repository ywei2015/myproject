package com.talkweb.xwzcxt.common;

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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.common.StingUtil;

/**
 * TODO(描述这个类的作用)
 * 
 * @author: 2012-8-30，Administrator，（描述修改内容）
 */
public class XwzcxtCommon
{

    public static final String MODULE_P_ID = "M71";
    public static final String MODULE_B_ID = "M63";
    
    private static final Logger logger = LoggerFactory.getLogger(XwzcxtCommon.class);
    /** 
    * 实现不重复的时间 
    */
    public static long getCurrentTime() {  
    	AtomicLong lastTime = new AtomicLong(System.currentTimeMillis());
        return lastTime.incrementAndGet();  
    }
    /*//获取系统当前时间
    public String getNowTime(){
        Date now = new Date();
        return DateFormat.getDateTimeInstance().format(now);
    }*/
    // 行为管理查询对象
    public enum Enum_Xwzcxt
    {
    	TASK_MOULD,
        TASK_MOULD_DETAILS,
        TASK_TEMPLET,
        TASK_TEMPLET_DETAILS,
        TASK_TEMPLET_CHILD,
        TASK_INFO_LIST,
        TASK_INFO_DETAIL,
        TASK_STEP_LIST,
        TASK_STEP_DETAIL,
        
        TASK_BASIC_JOB,
        TASK_BASIC_CAL,
        TASK_BASIC_TYPE,
        TASK_BASIC_SCHEDULE,
        
        
        TASK_STANDARD_POINT,
        TASK_STANDARD_TYPE,
        TASK_STANDARD_STANDARD,
        
        TASK_RULE_MOULD,
        TASK_RULE_MOULD_STEP,
        TASK_RULE_MOULD_ITEM,
        TASK_RULE_TIME,
        
        TASK_TOTAL_PERSON,
        TASK_TOTAL_STATU,
        TASK_TOTAL_TASK,
        
        //Add By Rita.Zhou for My Task
        TASK_READY_TASK,
        TASK_DONE_TASK,
        TASK_ERROR_TRACE
        
    }
    public static void WriteLog(String log) throws Exception{
        File f = new File("d:/a.txt");
        FileInputStream fip = new FileInputStream(f);
        // 构建FileInputStream对象
        InputStreamReader reader = new InputStreamReader(fip, "UTF-8");
        // 构建InputStreamReader对象,编码与写入相同
 
        StringBuffer sb = new StringBuffer();
        while (reader.ready()) {
            sb.append((char) reader.read());
            // 转成char加到StringBuffer对象中
        }
        
        FileOutputStream fop = new FileOutputStream(f);
        // 构建FileOutputStream对象,文件不存在会自动新建
        OutputStreamWriter writer = new OutputStreamWriter(fop, "UTF-8");
        // 构建OutputStreamWriter对象,参数可以指定编码,默认为操作系统默认编码,windows上是gbk
        writer.append(sb.toString()+log);
        // 写入到缓冲区
        writer.append("\r\n");
//      writer.flush();
        // 刷新缓存冲,写入到文件,如果下面已经没有写入的内容了,直接close也会写入
        writer.close();
        //关闭写入流,同时会把缓冲区内容写入文件,所以上面的注释掉
        fop.close();
        reader.close();
        // 关闭读取流
        fip.close();
        // 关闭输入流,释放系统资源
    }
    
    /**
   	 * 设置DOUBLE型的小数点精度 
   	 * @param a:数字
   	 * @param b:精度
   	 * @return
   	 */
   	public static Double mathSet(Double a,int b){
   		if(StingUtil.isEmpty(a))
   			a=0.0;
   		BigDecimal ad=new BigDecimal(a);
   		ad=ad.setScale(b,BigDecimal.ROUND_HALF_UP);
   		return ad.doubleValue();
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
        for(int i = 0; i < list.size(); i++)
        {
            if(!((Map)list.get(i)).get(colName).toString().equals(sourceFieldValue))
                continue;
            id = ((Map)list.get(i)).get(colId).toString();
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
        if(!m.matches())
            return false;
        return str.length() == 4;
    }
    private boolean checkPhone(String phone)
    {
        return phone.matches("((^(13|15|18)[0-9]{9}$)|(^0[1,2]{1} \\d{1}-?\\d{8}$)|(^0[3-9]{1}\\d{2}-?\\d{7,8}$)|(^0[1,2]{1}\\d{1}-?\\d{8}-(\\d{1,4})$)|(^0[3-9]{1}\\d{2}-?\\d{7,8}-(\\d{1,4})$))");
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
        String mobile[] = {
            "134", "135", "136", "137", "138", "139", "147", "150", "151", "152", 
            "157", "158", "159", "182", "183", "187", "188"
        };
        boolean t = false;
        for(int i = 0; i < mobile.length; i++)
            if(mobile[i].equals(s))
                t = true;

        return t;
    }

    private boolean isEnglish(String str)
    {
        return str.matches("^1\\d{10}");
    }

    private boolean isPhone(String str)
    {
        if(str.endsWith(".0"))
            str = str.substring(0, str.length() - 2);
        return str.matches("^\\d+(-|_)?\\d+$");
    }
    
    
}
