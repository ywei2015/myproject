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
	private String content = "操作成功！";
	private boolean success = true;
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	} 
	public void setStatus(boolean success) {
		this.success = success;
		if(success){
			this.content = "操作成功！"; 
		}else{
			content = "操作失败！";
		}
	} 
}
