package com.talkweb.xwzcxt.dal;

import java.util.List;

import com.talkweb.twdpe.dal.batis.spring.SessionDaoSupport;
import com.talkweb.xwzcxt.common.Accessory;
import com.talkweb.xwzcxt.common.StandardContent;
import com.talkweb.xwzcxt.pojo.StandardLibraryPojo;
import com.talkweb.xwzcxt.pojo.TSdActnodeItemPojo;
import com.talkweb.xwzcxt.pojo.TSdActnodePojo;

public class ImportFileDBDal extends SessionDaoSupport {
	public int expendStandardExcelFile(List<StandardContent> data){
		for (int i=0; i<data.size(); i++){
			StandardContent l = (StandardContent) this.getSession().selectOne("stdlibrary.exsistExcelContent", data.get(i));
			if (l == null)
				this.getSession().insert("stdlibrary.expendStandardExcelFile", data.get(i));
		}
		 return 1;
	}

	public List<StandardLibraryPojo> queryAllFileSortTree(){
		return this.getSession().selectList("stdlibrary.queryAllFileSortTree");
	}
	
	public int insertBasicInformationOfFile(StandardLibraryPojo data){
		return this.getSession().insert("stdlibrary.insertBasicInformationOfFile", data);
	}
	
	public int savePrefaceOfFile(StandardLibraryPojo data, float type){
		String sql = "";
		if (type >= 3)//工厂文件
			sql = "stdlibrary.savePrefaceOfFactoryFile";
		else//公司文件
			sql = "stdlibrary.savePrefaceOfCompanyFile";
		return this.getSession().update(sql, data);
	}
	
	public int addAccessoryFileToDB(Accessory data){
		this.getSession().insert("stdlibrary.addAccessoryFileToDB1", data);
		return this.getSession().insert("stdlibrary.addAccessoryFileToDB2", data);
	}
	
	public int saveSDActnode(TSdActnodePojo data){
		return this.getSession().insert("stdlibrary.insertSDActnode", data);
	}
	
	public int saveSDActnodeItem(List<TSdActnodeItemPojo> data){
		for (int i=0; i<data.size(); i++){
			TSdActnodeItemPojo temp = data.get(i);
			this.getSession().insert("stdlibrary.insertSDActnodeItem", temp);
		}
		
		return 1;
	}
	
}
