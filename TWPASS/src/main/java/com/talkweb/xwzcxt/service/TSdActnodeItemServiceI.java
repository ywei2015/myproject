package com.talkweb.xwzcxt.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.talkweb.xwzcxt.pojo.TSdActnodeItemPojo;

public interface TSdActnodeItemServiceI {
	public List<TSdActnodeItemPojo> getActnodeItemsByActnodeID(String aid);
	public void addActnodeItem(TSdActnodeItemPojo actnodeItem, HttpServletRequest request, HttpServletResponse response);
}