package sail.beans.entity;

import java.io.Serializable;

public class Dic implements Serializable{
	private String pid;
	private String diccode;
	private String dicname;
	private String objpid;
	private char sysflag;
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getDiccode() {
		return diccode;
	}
	public void setDiccode(String diccode) {
		this.diccode = diccode;
	}
	public String getDicname() {
		return dicname;
	}
	public void setDicname(String dicname) {
		this.dicname = dicname;
	}
	public String getObjpid() {
		return objpid;
	}
	public void setObjpid(String objpid) {
		this.objpid = objpid;
	}
	public char getSysflag() {
		return sysflag;
	}
	public void setSysflag(char sysflag) {
		this.sysflag = sysflag;
	}
	

	

	
}
