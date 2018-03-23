package com.talkweb.xwzcxt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.xwzcxt.dal.MediaStdDal;
import com.talkweb.xwzcxt.service.MediaStdService;
import com.talkweb.xwzcxt.vo.MediaStdVo;

public class MediaStdServiceImpl implements MediaStdService{
	
	@Autowired
	private MediaStdDal dal;
	@Override
	public List<MediaStdVo> getStdMediaInfo(MediaStdVo params) {
		return dal.getStdMediaInfo(params);
	}

}
