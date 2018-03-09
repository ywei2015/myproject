package spc.beans.base;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import javax.servlet.ServletContext;


 

public final class PersistenceKit {
	
	private static ServletContext servletContext = null;   //ServletContext 
	public static ServletContext getContext() {
		return servletContext;
	}  
	public static void setContext(ServletContext context) {
		PersistenceKit.servletContext = context;
	}

	
	/** 
	* @Title: getValue 
	* @Description: TODO(获取Key的Value) 
	* @param key 
	* @return String  Value  
	* 2017年9月15日 下午2:09:25 最后修改人 GuveXie 
	*/
	public static String getValue(String key){
		if(servletContext==null) return null;
        Properties properties = new Properties();  
        String value = "";
        try{ 
        	InputStream fis= PersistenceKit.class.getResourceAsStream("/runevent.properties");
        	properties.load(fis); 
	        if(properties.containsKey(key)) value = properties.getProperty(key); 
	        fis.close();
	        return value;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        } 
	}
	
	/** 
	* @Title: setValue 
	* @Description: TODO(更新Key的Value) 
	* @param @param key
	* @param @param value  
	* @return boolean  是否成功 
	* 2017年9月15日 下午2:09:25 最后修改人 GuveXie 
	*/
	public static boolean setValue(String key, String value){
		if(servletContext==null) return false;
        Properties properties = new Properties();
        try {
        	String path = PersistenceKit.class.getResource("/runevent.properties").getPath();
        	OutputStream outputStream=new FileOutputStream(path);
        	properties.setProperty(key, value); 
        	properties.store(outputStream, "modify");
        	outputStream.close(); 
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
	} 
	
}
