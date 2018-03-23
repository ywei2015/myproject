package com.talkweb.xwzcxt.dal;

import java.util.List;

import com.talkweb.twdpe.dal.batis.spring.SessionDaoSupport;
import com.talkweb.xwzcxt.vo.MediaStdVo;

public class MediaStdDal extends SessionDaoSupport{
	
	public List<MediaStdVo> getStdMediaInfo(MediaStdVo params){
		return this.getSession().selectList("midiaStandardFileInfo.getStdMediaInfo",params);
	}
}
