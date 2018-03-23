package com.talkweb.xwzcxt.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.talkweb.xwzcxt.pojo.TSdPublicPojo;

public interface TSdPublicServiceI {
	public List getPublicNodesTree();
	public TSdPublicPojo getPublicNodeById(String cPublicId);
	public int addPublicNode(TSdPublicPojo t, HttpServletRequest request, HttpServletResponse response);
	public int updatePublicNodeById(TSdPublicPojo t, HttpServletRequest request, HttpServletResponse response);
	public int deletePublicNodesById(TSdPublicPojo t, HttpServletRequest request, HttpServletResponse response);
}