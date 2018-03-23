package com.talkweb.xwzcxt.dal;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.common.AppConstants;
import com.talkweb.twdpe.dal.batis.spring.SessionDaoSupport;
import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.xwzcxt.pojo.StandardContentPojo;
import com.talkweb.xwzcxt.pojo.StdFile;
import com.talkweb.xwzcxt.pojo.StdFileAffixPojo;
import com.talkweb.xwzcxt.pojo.TaskMouldPojo;


/**
 * TODO(描述这个类的作用)
 * 
 * @author: 2014-5-27，GuveXie，（描述修改内容）
 */

public class StdFileDal extends SessionDaoSupport {
	@Autowired
	AppConstants appConstants;

	public Pagination SelectStdFileBySortId(String c_sort_id, Pagination page) {
		// TODO Auto-generated method stub
		List li = this.getSession().selectList("stdfile.selectstdfilesbysortid",c_sort_id,page);
		if (li != null)
			page.setList(li);
		return page;
	}
	
	public List<StdFile> SelectStdFileBySortId(String c_sort_id) {
		// TODO Auto-generated method stub
		return this.getSession().selectList("stdfile.selectstdfilesbysortid",c_sort_id);
	}
	

	public Pagination SelectStdFileByParams(Map params, Pagination page) {
		List li =  this.getSession().selectList("stdfile.selectstdfilesbyparams",params,page);
		if (li != null)
			page.setList(li);
		return page;
	}

    public StdFile SelectStdFileBySFileID(String c_sfile_id)
    {
    	return (StdFile)this.getSession().selectOne("stdfile.queryStdfileByID",c_sfile_id);
    }
    
	public StandardContentPojo SelectStdFileContentInfoById(String c_contentid){
		return (StandardContentPojo) this.getSession().selectOne("stdfile.queryStdfileContentByContentId",c_contentid);
	}
    
	public Pagination SelectStdFileContentByFileid(String c_sfile_id, Pagination page) {
		List li =  this.getSession().selectList("stdfile.queryStdfileContentByfileid",c_sfile_id,page);
		if (li != null)
			page.setList(li);
		return page;
	}
	
	public Pagination SelectStdFileContentByParams(Map params, Pagination page) {
		List li =  this.getSession().selectList("stdfile.queryStdfileContentByParams",params,page);
		if (li != null)
			page.setList(li);
		return page;
	}
	
	public Pagination SelectStdFileAffixByParams(Map params, Pagination page) {
		List li =  this.getSession().selectList("stdfile.queryStdfileAffixByParams",params,page);
		String preurl = appConstants.getIMG_URL();
		for(int i=0;i<li.size();i++){
            StdFileAffixPojo stdfileaffix = new StdFileAffixPojo();
            stdfileaffix = (StdFileAffixPojo) li.get(i);
            if(stdfileaffix.getC_file_url()!= null){
            	stdfileaffix.setC_file_url(preurl+stdfileaffix.getC_file_url());
            }
		}
		if (li != null)
			page.setList(li);
		return page;
	}
	
	public int UpdateStdFileInfo(StdFile stdfileinfo){
		return this.getSession().update("stdfile.updateStdfileInfo", stdfileinfo);
	}

	public int DeleteStdFileContentById(String contentid){
		return this.getSession().delete("stdfile.delStdFileContentbyId", contentid);
	}

	public int DeleteStdFileAffixById(String affixid){
		return this.getSession().delete("stdfile.delStdFileAffixById", affixid);
	}
	
	public int UpdateStdFileContentInfo(StandardContentPojo contentobj){
		return this.getSession().update("stdfile.updateStdfileContentInfo", contentobj);
	}
	public int InsertStdFileContentInfo(StandardContentPojo contentobj){
		return this.getSession().insert("stdfile.insertStdfileContentInfo", contentobj);
	}
	
	public int InsertStdFileAffixInfo(StdFileAffixPojo affixobj){
		int n =  this.getSession().insert("stdfile.updatesdfiletable", affixobj);
		return n+this.getSession().insert("stdfile.insertStdfileAffixInfo", affixobj);
	}
}
