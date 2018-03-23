package com.talkweb.xwzcxt.service;

import java.util.List;
import java.util.Map;

import com.talkweb.twdpe.web.jcontrols.RuleTree;
import com.talkweb.xwzcxt.common.StandardContent;
import com.talkweb.xwzcxt.pojo.Pie;
import com.talkweb.xwzcxt.pojo.PreviewPojo;
import com.talkweb.xwzcxt.pojo.TaskCondition;
import com.talkweb.xwzcxt.pojo.TaskCount;

/**
 * TODO(描述这个类的作用) 
 * @author: 
 * 2013-12-30，FLN，（描述修改内容）
 */
public interface PreviewService
{

	String setPreview(List<StandardContent> standardContent);


	List<PreviewPojo> getPreview(String key);

    

}
