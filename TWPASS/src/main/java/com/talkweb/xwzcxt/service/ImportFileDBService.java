package com.talkweb.xwzcxt.service;

import java.util.List;

import com.talkweb.xwzcxt.common.Accessory;
import com.talkweb.xwzcxt.common.StandardContent;
import com.talkweb.xwzcxt.pojo.StandardLibraryPojo;
import com.talkweb.xwzcxt.pojo.TSdActnodeItemPojo;
import com.talkweb.xwzcxt.pojo.TSdActnodePojo;

public interface ImportFileDBService {
	
	public int expendStandardExcelFile(List<StandardContent> data);
	public List<StandardLibraryPojo> queryAllFileSortTree();
	public int insertBasicInformationOfFile(StandardLibraryPojo data);
	public int savePrefaceOfFile(StandardLibraryPojo data,float type);
	public int addAccessoryFileToDB(Accessory data);
	public int saveSDActnode(TSdActnodePojo data);
	public int saveSDActnodeItem(List<TSdActnodeItemPojo> data);
}
