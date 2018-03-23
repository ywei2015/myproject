package com.talkweb.xwzcxt.action;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.common.AppConstants;
import com.talkweb.twdpe.core.log.Logger;
import com.talkweb.twdpe.core.log.LoggerFactory;
import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.twdpe.web.jcontrols.RuleTree;
import com.talkweb.twdpe.webcomponent.common.action.BusinessAction;
import com.talkweb.xwzcxt.pojo.StandardFile;
import com.talkweb.xwzcxt.pojo.StandardLibraryPojo;
import com.talkweb.xwzcxt.service.StandardLibraryService;

/**
 * TODO(标准库-流程节点标准内容管理相关Action方法的实现)
 * 
 * @author: 2014-05-21，FLN，（描述修改内容）
 */
public class StandardLibraryAction extends BusinessAction {
	
	private StandardFile standardfile;

	private static final Logger logger = LoggerFactory
			.getLogger(StandardLibraryAction.class);

	@Autowired
	private StandardLibraryService standardLibraryService;

	@Autowired
	AppConstants appConstants;

	// 管理流程查询
	public String queryManagementProcess() {
		if (standardfile == null)
			standardfile = new StandardFile();
		Pagination result = standardLibraryService.queryManagementProcess(standardfile, pagination);
		List pointList = result.getList();
		this.formatDatagirdData(pointList, result);
		return SUCCESS;
	}

	// 查看文件内容
	public String getStandardcontent() {
		try {
			List<StandardFile> list = standardLibraryService.getStandardcontent(standardfile);
			RuleTree wtree = new RuleTree();
			wtree.setId("c_sectionid");
			wtree.setVal("c_content");
			wtree.setPid("c_sectionpid");
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getC_includeatt().equals("1")) {
					list.get(i).setC_content(
							list.get(i).getC_content() + "  <a href="
									+ appConstants.getIMG_URL()
									+ list.get(i).getC_file_url()
									+ " target=_blank>查看附件</a>");
				}
			}
			List<Map> l = this.initTreeData(list, wtree);
			this.formatData(l);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	// 查看附录内容
	public String getAppendix() {
		try {
			List<StandardFile> list = standardLibraryService.getAppendix(standardfile);
			RuleTree wtree = new RuleTree();
			wtree.setId("c_sectionid");
			wtree.setVal("c_file_name");
			wtree.setPid("c_sectionpid");
			for (int i = 0; i < list.size(); i++) {

				list.get(i).setC_file_name("<a href="
								+ appConstants.getIMG_URL()
								+ list.get(i).getC_file_url()
								+ " target=_blank>"+list.get(i).getC_file_name()+"</a>");
			}
			List<Map> l = this.initTreeData(list, wtree);
			this.formatData(l);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	// 获取流程树
	public String getProcessNodeTree() {
		try {
			List<StandardFile> list = standardLibraryService.getProcessNodeTree();
			RuleTree wtree = new RuleTree();
			wtree.setId("c_action_id");
			wtree.setVal("c_action_sname");
			wtree.setPid("c_action_pid");
			List<Map> l = this.initTreeData(list, wtree);
			this.formatData(l);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	// 删除标准文件信息
	public String delManagementProcess() {
		if (standardfile == null)
			standardfile = new StandardFile();
		HttpServletRequest request = ServletActionContext.getRequest();
		String c_sfile_id = request.getParameter("c_sfile_id");
		standardfile.setC_sfile_id(c_sfile_id);
		standardLibraryService.delManagementProcess(standardfile);
		Map<String, String> pageData = new HashMap<String, String>();
		pageData.put("msg", "成功删除");
		pageData.put("status", "ok");
		this.formatData(pageData);
		return SUCCESS;
	}
	
	//删除标准文件节点
	public String delStandardFileTreeNode(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String sortCode=request.getParameter("ids");
		String[] idsArray=sortCode.split(",");
		List<String> idsList=new ArrayList<String>();
		for(String id: idsArray){
			idsList.add(id);
		}
		standardLibraryService.delStandardFileTreeNode(idsList);
		return SUCCESS;
	}
	
	//新增标准文件节点
	public String addStandardFileTreeNode(){
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		StandardLibraryPojo pojo=new StandardLibraryPojo();
		pojo.setC_sort_pid(request.getParameter("pid"));
		//设置sort_code
		int pid=Integer.parseInt(pojo.getC_sort_pid());
		int sortCode=pid/1000*1000;
		pojo.setC_sort_code(sortCode+"");
		//先得到当前的节点ID
		String currentMaxId=standardLibraryService.getMaxIdOfChildren(pojo);
		String sortId=(Integer.parseInt(currentMaxId)+1)+"";
		pojo.setC_sort_id(sortId);
		pojo.setC_sort_name(request.getParameter("name"));
		standardLibraryService.addStandardFileTreeNode(pojo);
		Map<String, String> pageData = new HashMap<String, String>();
		pageData.put("msg", "成功添加");
		this.formatData(pageData);
		return SUCCESS;
	}
	//修改标准文件节点
	public String updateStandardFileTreeNode(){
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		StandardLibraryPojo pojo=new StandardLibraryPojo();
		pojo.setC_sort_pid(request.getParameter("pid"));
		pojo.setC_sort_id(request.getParameter("id"));
		pojo.setC_sort_name(request.getParameter("name"));
		standardLibraryService.updateStandardFileTreeNode(pojo);
		return SUCCESS;
	}
	public StandardFile getStandardfile() {
		return standardfile;
	}

	public void setStandardfile(StandardFile standardfile) {
		this.standardfile = standardfile;
	}
}
