package sail.beans.support;

/**
 * <p>类说明：</p>
 * 		
 *    
 * <p>Copyright: Copyright (c) 2008</p>
 *    
 * @author liuwei
 * 2007-12-12
 *
 * @version 1.0
 */

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class StingUtil {

	public static boolean isEmpty(Object value) {
		if (value != null && !"".equals(value)) {
			return false;
		}
		return true;
	}

	/**
	 * 功能说明： 将字符串转换成为：UTF-8格式。
	 * 
	 * @param vs_Str
	 * @return
	 * 
	 *         Added by mark 2007年12月6日
	 */
	public static String getUTF(String str) {
		String ret = null;

		if (str == null)
			return null;

		try {
			ret = new String(str.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ret;

	}

	public static String getGBK(String str) {
		String ret = null;

		if (str == null)
			return null;

		try {

			ret = new String(str.getBytes("ISO-8859-1"), "GBK");
			// ret = new String(str.getBytes("UTF-8"), "GBK");
		} catch (UnsupportedEncodingException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ret;

	}

	public static String decode(String content, String enc) {
		try {
			return URLDecoder.decode(content, enc);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return content;
	}

	/**
	 * 
	 * 方法说明： 将两个字符串数据合并。并返回合并后的字符串数组。
	 * 
	 * @param str1
	 * @param str2
	 * @return String[]
	 */
	final public static String[] strAdd(String[] str1, String[] str2) {
		if (str1 == null)
			return str2;
		if (str2 == null)
			return str1;

		String[] ret = new String[str1.length + str2.length];

		int count = 0;

		for (count = 0; count < str1.length; count++)
			ret[count] = str1[count];

		for (int i = 0; i < str2.length; i++)
			ret[count++] = str2[i];

		return ret;
	}

	/**
	 * 返回格式化的序列号
	 * 
	 * @param serialNum
	 *            数值
	 * @param digit
	 *            长度
	 * @return
	 */
	public static String genFormatSerialNnumber(int serialNum, int digit) {
		String ret = "";
		if (String.valueOf(serialNum).length() > digit) {
			System.out.println("参数设置有问题:数值的长度超过了设置的位数");
		}
		StringBuffer sbf = new StringBuffer(digit + 1);
		sbf.append("1");
		for (int i = 0; i < digit; i++) {
			sbf.append("0");
		}
		int num = Integer.valueOf(sbf.toString());
		// 不足4位的前面补0。
		num = num + serialNum;
		String noS = String.valueOf(num);
		ret = noS.substring(1);
		return ret;
	}

	/**
	 * 对MAP对象进行排序
	 * 
	 * @param map
	 * @return
	 */
	public static List sortMap(Map map) {
		List newMap = new ArrayList();
		Object[] key = map.keySet().toArray();
		for (int k = 0; k < key.length; k++) {
			for (int j = k + 1; j < key.length; j++) {
				if (key[k].toString().compareToIgnoreCase(key[j].toString()) >= 0) {
					String centerValue = key[k].toString();
					key[k] = key[j];
					key[j] = centerValue;
				}
			}
		}
		for (int i = key.length - 1; i >= 0; i--) {
			newMap.add(map.get(key[i]));
		}
		return newMap;
	}

	/**
	 * 将字符串 t_String 中的 value1 替换为 value2
	 * 
	 * @param t_String
	 * @param value1
	 *            原字符
	 * @param value2
	 *            新字符
	 * @return
	 */
	public static String replaceString(String t_String, String value1,
			String value2) {
		while (t_String.indexOf(value1) != -1) {
			t_String = t_String.substring(0, t_String.indexOf(value1))
					+ value2
					+ t_String.substring(
							t_String.indexOf(value1) + value1.length(),
							t_String.length());
		}
		return t_String;
	}

	public static void main(String args[]) {
		System.out.println(StingUtil.genFormatSerialNnumber(600000, 5));
	}

	public static String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}
	
	public static String StringChangeToUrl(String url){
		/*  url=url.replace("#", "%23");
		  url=url.replace(" ", "%20");
		  url=url.replace(";", "%3B");
		  url=url.replace("/", "%2F");
		  url=url.replace("?", "%3F");
		  url=url.replace(":", "%3A");
		  url=url.replace("@", "%4O");
		  url=url.replace("=", "%3D");
		  url=url.replace("&", "%26");
		  url=url.replace("<", "%3C");
		  url=url.replace(">", "%3E");
		  url=url.replace("|", "%7C");
		  url=url.replace("^", "%5E");
		  url=url.replace("~", "%7E");
		  url=url.replace("[", "%5B");
		  url=url.replace("]", "%5D");*/
		  try {
			url= URLEncoder.encode(url,"utf-8");
		} catch (UnsupportedEncodingException e) {
			System.out.println(url+"转码异常");
			e.printStackTrace();
			
		}
		  return url;
	}
}
