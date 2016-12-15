package sail.beans.base;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;  

import net.sf.json.JSONException;
import net.sf.json.JSONObject;
 


/**
* <p>类说明：请求参数获取类</p> 
* 
* <p>Copyright: Copyright (c) 2016</p>
*    
* @author xie
* 2016-08-26
*
* @version 1.0 
 * 
 */
public class RequestFetcher {
	private static String getUTF(String str){
		String ret = null; 
		if (str == null)
			return null; 
		try {
			ret = new String(str.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
		}
		return ret;
	}

	public static Object getDataset(HttpServletRequest request, Class beanClass){
		Object obj = null;
		String ds_str = request.getParameter("dataset"); 
		if( ds_str != null ){
			ds_str = ds_str.trim();
			ds_str = RequestFetcher.getUTF(ds_str);
		} 
		System.out.println("dataset json IS "+ ds_str);
    	try {
			JSONObject json = new JSONObject();
			json = JSONObject.fromObject(ds_str); 
			json = json.getJSONObject("dataset"); 
			json = json.getJSONObject(beanClass.getSimpleName().toLowerCase()); 
			obj = JSONObject.toBean(json, beanClass);
		} catch (JSONException e) { 
			e.printStackTrace();
		} 
		return obj;
	}
	
}
