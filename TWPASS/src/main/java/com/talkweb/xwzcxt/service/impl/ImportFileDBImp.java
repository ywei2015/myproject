package com.talkweb.xwzcxt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.xwzcxt.common.Accessory;
import com.talkweb.xwzcxt.common.StandardContent;
import com.talkweb.xwzcxt.dal.ImportFileDBDal;
import com.talkweb.xwzcxt.pojo.StandardLibraryPojo;
import com.talkweb.xwzcxt.pojo.TSdActnodeItemPojo;
import com.talkweb.xwzcxt.pojo.TSdActnodePojo;
import com.talkweb.xwzcxt.service.ImportFileDBService;

public class ImportFileDBImp implements ImportFileDBService {

	@Autowired
    private ImportFileDBDal importFileDBDal;

	@Override
	//将标准excel文件内容存入数据库
	public int expendStandardExcelFile(List<StandardContent> data) {
		return importFileDBDal.expendStandardExcelFile(data);
	}

	@Override
	public List<StandardLibraryPojo> queryAllFileSortTree() {
		return importFileDBDal.queryAllFileSortTree();
	}

	@Override
	public int insertBasicInformationOfFile(StandardLibraryPojo data) {
		return importFileDBDal.insertBasicInformationOfFile(data);
	}

	@Override
	public int savePrefaceOfFile(StandardLibraryPojo data, float type) {
		return importFileDBDal.savePrefaceOfFile(data, type);
	}

	@Override
	public int addAccessoryFileToDB(Accessory data) {
		return importFileDBDal.addAccessoryFileToDB(data);
	}

	@Override
	public int saveSDActnode(TSdActnodePojo data) {
		return importFileDBDal.saveSDActnode(data);
	}

	@Override
	public int saveSDActnodeItem(List<TSdActnodeItemPojo> data) {
		return importFileDBDal.saveSDActnodeItem(data);
	}

	
	

}
