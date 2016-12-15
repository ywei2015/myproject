package sail.beans.support;

/**
 * 		
 *    
 * <p>Copyright: Copyright (c) 2008</p>
 *    
 * @author mark
 * 2007-12-12
 *
 * @version 1.0
 */


public class GeneralTools {
	public static final String filterUrl_1="http://mes.furongwang.com";
	public static final String filterUrl_2="http://10.75.140.145";
	public static String getDate(){
		return "";
	}
	public static boolean isFilterUrl(String retUrl){
		if((filterUrl_1.equals(retUrl)||(filterUrl_1+"/").equals(retUrl)||(filterUrl_1+"/index.jsp").equals(retUrl))){
			return true;
		}else if((filterUrl_2.equals(retUrl)||(filterUrl_2+"/").equals(retUrl)||(filterUrl_2+"/index.jsp").equals(retUrl))){
			return true;
		}else{
			return false;
		}
	}
}
