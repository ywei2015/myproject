package com.talkweb.xwzcxt.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;
import com.talkweb.common.AppConstants;
import com.talkweb.xwzcxt.service.MediaStdService;
import com.talkweb.xwzcxt.vo.MediaStdVo;

public class MediaStdAction extends BusinessAction implements ModelDriven<MediaStdVo>{

	private static final long serialVersionUID = -7185304759919931846L;

	private MediaStdVo params=new MediaStdVo();

	public MediaStdVo getParams() {
		return params;
	}

	public void setParams(MediaStdVo params) {
		this.params = params;
	}

	@Autowired
	private MediaStdService service;
	@Autowired
	private AppConstants appConstants;
	
	@Override
	public MediaStdVo getModel() {
		return params!=null?params:new MediaStdVo();
	}
	
	
	@SuppressWarnings("unchecked")
	public String getStdMediaInfo(){
		@SuppressWarnings("rawtypes")
		Map map=new HashMap();
		try{
			String head=appConstants.getIMG_URL();
			List<MediaStdVo> list=service.getStdMediaInfo(params);
			map.put("head", head);
			map.put("fileList", list);
			this.formatData(map);
		}catch(Exception e){
			e.printStackTrace();
			this.formatData(null);
		}
		return SUCCESS;
	}
}
