package com.talkweb.xwzcxt.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.xwzcxt.dal.DpAreaDal;
import com.talkweb.xwzcxt.dal.TaskDal;
import com.talkweb.xwzcxt.dal.XwzcxtMngDal;
import com.talkweb.xwzcxt.pojo.TSdActnodeItemPojo;
import com.talkweb.xwzcxt.pojo.TTaskPojo;
import com.talkweb.xwzcxt.service.DpPositionServiceI;
import com.talkweb.xwzcxt.service.KaoqinServiceI;
import com.talkweb.xwzcxt.service.TSdActnodeItemServiceI;
import com.talkweb.xwzcxt.service.TaskServiceI;
import com.talkweb.xwzcxt.util.DataTypeParseUtil;
import com.talkweb.xwzcxt.vo.DpPositionVo;
import com.talkweb.xwzcxt.vo.TTaskVo;

public class KaoqinServiceImpl implements KaoqinServiceI {
	@Autowired
	private TaskDal taskDal;

	@Override
	public List getKaoqinData(Map params, Pagination page) {
	
		return null;
	}

}