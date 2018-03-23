package com.talkweb.xwzcxt.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.talkweb.xwzcxt.common.StandardContent;
import com.talkweb.xwzcxt.pojo.PreviewPojo;
import com.talkweb.xwzcxt.service.PreviewService;

public class PreviewServiceImp implements PreviewService {

	static Map<String,List<PreviewPojo>> previewTreeMap;
	
	@Override
	public String setPreview(List<StandardContent> standardContents) {
		// TODO Auto-generated method stub
		
		 String key = "";
		 List<PreviewPojo> list = new ArrayList<PreviewPojo>();
		 StandardContent standtemp = new StandardContent();
		 for(int i=0;i<standardContents.size();i++){
			 standtemp = standardContents.get(i);
			 PreviewPojo pepojo = new PreviewPojo();
			 key = standtemp.getC_sfile_id();
			 pepojo.setId(standtemp.getC_sectionid());
			 pepojo.setPid(standtemp.getC_sectionpid());
			 pepojo.setVal(standtemp.getC_title()+standtemp.getC_content());
			 list.add(pepojo);
		 }
		 
		 previewTreeMap.put(key, list);

         return key;
	}
	
	
	@Override
	public List<PreviewPojo> getPreview(String key) {
		// TODO Auto-generated method stub
		List<PreviewPojo> list = previewTreeMap.get(key);
		return list;
	}


}
