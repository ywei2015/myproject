package tw.sysbase.pub;

import java.io.Serializable;


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
public class ResponseStatus implements Serializable {
	private String content = "操作成功！";
	private boolean success = true; 
	private int specialflag = 0; //特殊标志，一般默认为0，为其它值时与前台约定特殊意义
	
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
	public int getSpecialflag() {
		return specialflag;
	}
	public void setSpecialflag(int specialflag) {
		this.specialflag = specialflag;
	}
}
