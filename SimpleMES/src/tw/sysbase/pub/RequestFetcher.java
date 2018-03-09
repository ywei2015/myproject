package tw.sysbase.pub;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;  

import net.sf.json.JSONException;
import net.sf.json.JSONObject; 
import tw.sysbase.exception.LogException;
 


/**
* <p>类说明：Spring MVC Response Base Class</p> 
* 
* <p>Copyright: Copyright (c) 2017</p>
*    
* @author GuveXie
* 2017-08-24
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
			LogException.logEx(ex);
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
			LogException.logEx(e, beanClass,"RequestFetcher.getDataset"); 
		} 
		return obj;
	}
	
}
