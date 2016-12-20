package sail.beans.base;

import java.io.Serializable;


/**
* <p>类说明：Spring MVC Response Status Base Class</p> 
* 
* <p>Copyright: Copyright (c) 2016</p>
*    
* @author xie&li
* 2016-08-26
*
* @version 1.0 
 * 
 */
public class ResponseStatus implements Serializable {
	private String message = "成功！";
	private String code = "0";
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	
	
	
}
