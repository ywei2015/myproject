package com.talkweb.xwzcxt.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.common.AppConstants;
import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.xwzcxt.dal.NewsDal;
import com.talkweb.xwzcxt.dal.TSdMediastdDal;
import com.talkweb.xwzcxt.pojo.NewsPojo;
import com.talkweb.xwzcxt.pojo.TSdMediastdPojo;
import com.talkweb.xwzcxt.service.NewsService;

public class NewsServiceImpl implements NewsService {
	@Autowired
	private NewsDal newsDal;
	
	@Autowired
	private TSdMediastdDal tSdMediastdDal;

	@Override
	public void getNewsInfo(Pagination page, Map tb) {
		newsDal.getNewsInfo(page, tb);
		setFileInfos(page);
	}
	
	private void setFileInfos(Pagination page) {
		List<NewsPojo> list=page.getList();
		for (NewsPojo v : list) {
			Map params=new HashMap();
			// 多媒体
			params.put("type", 1);
			params.put("cid", v.getNewsId());
			List<TSdMediastdPojo> lsd = tSdMediastdDal.getAllMediaStdById(params);
			String cTempletMediaFileId = "", cTempletMediaFileType = "", cTempletMediaFilePath = "";
			/*for (TSdMediastdPojo tsd : lsd) {
				cTempletMediaFileId += (tsd.getcSdfileId() + ",");
				cTempletMediaFileType += (tsd.getcSdfileType() + ",");
				cTempletMediaFilePath += (AppConstants.getFilePathPrefix() + tsd.getcSdfilePath() + ",");
			}*/
			
			if(lsd!=null&&lsd.size()>0){
				TSdMediastdPojo tsd=lsd.get(lsd.size()-1);
				cTempletMediaFileId += (tsd.getcSdfileId() + ",");
				cTempletMediaFileType += (tsd.getcSdfileType() + ",");
				cTempletMediaFilePath += (AppConstants.getFilePathPrefix() + tsd.getcSdfilePath() + ",");				
			}
			
			if (!cTempletMediaFileId.equals("") || !cTempletMediaFileType.equals("") || !cTempletMediaFilePath.equals("")) {
				cTempletMediaFileId = cTempletMediaFileId.substring(0, cTempletMediaFileId.length() - 1);
				cTempletMediaFileType = cTempletMediaFileType.substring(0, cTempletMediaFileType.length() - 1);
				cTempletMediaFilePath = cTempletMediaFilePath.substring(0, cTempletMediaFilePath.length() - 1);
			}
			v.setcNewsFileId(cTempletMediaFileId);
			v.setcNewsFileType(cTempletMediaFileType);
			v.setcNewsFilePath(cTempletMediaFilePath);
		}
	}

	@Override
	public int addNewsInfo(NewsPojo newsInfo){
		tSdMediastdDal.addMediaFile(newsInfo.getcNewsFileId(), 1, newsInfo.getCreator(), newsInfo.getNewsId());
		return newsDal.addNewsInfo(newsInfo);
	}

	@Override
	public int deleteNews(Map newsId) {
		return newsDal.deleteNews(newsId);
	}

	@Override
	public int updateNews(NewsPojo news) {
		tSdMediastdDal.addMediaFile(news.getcNewsFileId(), 1, news.getCreator(), news.getNewsId());
		return newsDal.updateNews(news);
	}
 
	@Override
	public List<NewsPojo> getNesInfoIds(List<String> ids) {
		List<NewsPojo> list = (List<NewsPojo>) newsDal.getNesInfoIds(ids);
		for (NewsPojo v : list) {
			Map params=new HashMap();
			// 多媒体
			params.put("type", 1);
			params.put("cid", v.getNewsId());
			List<TSdMediastdPojo> lsd = tSdMediastdDal.getAllMediaStdById(params);
			String cTempletMediaFileId = "", cTempletMediaFileType = "", cTempletMediaFilePath = "";

			
			if(lsd!=null&&lsd.size()>0){
				TSdMediastdPojo tsd=lsd.get(lsd.size()-1);
				cTempletMediaFileId += (tsd.getcSdfileId() + ",");
				cTempletMediaFileType += (tsd.getcSdfileType() + ",");
				cTempletMediaFilePath += (AppConstants.getFilePathPrefix() + tsd.getcSdfilePath() + ",");				
			}
			
			if (!cTempletMediaFileId.equals("") || !cTempletMediaFileType.equals("") || !cTempletMediaFilePath.equals("")) {
				cTempletMediaFileId = cTempletMediaFileId.substring(0, cTempletMediaFileId.length() - 1);
				cTempletMediaFileType = cTempletMediaFileType.substring(0, cTempletMediaFileType.length() - 1);
				cTempletMediaFilePath = cTempletMediaFilePath.substring(0, cTempletMediaFilePath.length() - 1);
			}
			v.setcNewsFileId(cTempletMediaFileId);
			v.setcNewsFileType(cTempletMediaFileType);
			v.setcNewsFilePath(cTempletMediaFilePath);
		}
		
		return list;
	}
}
