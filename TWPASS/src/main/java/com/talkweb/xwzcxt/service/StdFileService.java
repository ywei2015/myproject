package com.talkweb.xwzcxt.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.common.AppConstants;
import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.xwzcxt.dal.StdFileDal;
import com.talkweb.xwzcxt.pojo.StandardContentPojo;
import com.talkweb.xwzcxt.pojo.StdFile;
import com.talkweb.xwzcxt.pojo.StdFileAffixPojo;

/**
 * TODO(描述这个类的作用)
 * 
 * @author: 2014-5-27，GuveXie，（描述修改内容）
 */
public class StdFileService {

	@Autowired
    private StdFileDal stdFileDal;
	
	@Autowired
	AppConstants appConstants;
	
	public Pagination getStdFileBySortId(String c_sort_id,Pagination page)
	{
		return stdFileDal.SelectStdFileBySortId(c_sort_id, page);
	}

	public List<StdFile> getStdFileBySortId(String c_sort_id)
	{
		return (List<StdFile>) stdFileDal.SelectStdFileBySortId(c_sort_id);
	}
	
	public Pagination getStdFileByParams(Map params,Pagination page)
	{
		return stdFileDal.SelectStdFileByParams(params,page);
	}
	
	public StdFile getStdFileById(String c_sfile_id){
		return stdFileDal.SelectStdFileBySFileID(c_sfile_id);
	}
	
	public StandardContentPojo getStdFileContentInfoById(String contentid){
		return stdFileDal.SelectStdFileContentInfoById(contentid);
	}
	
	public Pagination getStdFileContentByFileid(String c_sfile_id,Pagination page)
	{
		return stdFileDal.SelectStdFileContentByFileid(c_sfile_id, page);
	}
	
	public Pagination getStdFileContentByParams(Map params,Pagination page)
	{
		return stdFileDal.SelectStdFileContentByParams(params, page);
	}

	public Pagination getStdFileAffixByParams(Map params,Pagination page)
	{
		return stdFileDal.SelectStdFileAffixByParams(params, page);
	}
	
	public int UpdateStdFileInfo(StdFile stdfileinfo){
		return stdFileDal.UpdateStdFileInfo(stdfileinfo);
	}
	
	public int DeleteStdFileContentById(String contentid){
		return stdFileDal.DeleteStdFileContentById(contentid);
	}
	
	public int DeleteStdFileAffixById(String affixid){
		return stdFileDal.DeleteStdFileAffixById(affixid);
	}
	
	public int UpdateStdFileContentInfo(StandardContentPojo contentobj){
		return stdFileDal.UpdateStdFileContentInfo(contentobj);
	}
	public int InsertStdFileContentInfo(StandardContentPojo contentobj){
		return stdFileDal.InsertStdFileContentInfo(contentobj);
	}
	public int InsertStdFileAffixInfo(StdFileAffixPojo affixobj){
		return stdFileDal.InsertStdFileAffixInfo(affixobj);
	}
}
