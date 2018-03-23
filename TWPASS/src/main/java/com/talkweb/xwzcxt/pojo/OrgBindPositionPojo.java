package com.talkweb.xwzcxt.pojo;

import com.talkweb.twdpe.base.org.pojo.Org;
import com.talkweb.twdpe.core.data.BasePOJO;

public class OrgBindPositionPojo extends Org {

	/**
	 * 岗位绑定组织目录树实体
	 */
	private static final long serialVersionUID = -2148181095196119489L;
	{
		Org org=new Org();
//		System.out.println(org instanceof BasePOJO);
	}

	private String cid;		//外键_主键ID
	private String dtype;		//是岗位：2 组织：1
	private String dataid;		//ID
	private String datacode;	//编号
	private String dataname;	//名称
	private String pid;			//父ID
	
	public OrgBindPositionPojo(){}
	
	public String getDtype() {
		return dtype;
	}


	public void setDtype(String dtype) {
		this.dtype = dtype;
	}

	public String getCid() {
		return cid;
	}


	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getDataid() {
		return dataid;
	}


	public void setDataid(String dataid) {
		this.dataid = dataid;
	}


	public String getDatacode() {
		return datacode;
	}


	public void setDatacode(String datacode) {
		this.datacode = datacode;
	}


	public String getDataname() {
		return dataname;
	}


	public void setDataname(String dataname) {
		this.dataname = dataname;
	}


	public String getPid() {
		return pid;
	}


	public void setPid(String pid) {
		this.pid = pid;
	}


	public static void main(String[] args){
		new OrgBindPositionPojo();
	}
}
