package com.talkweb.twdpe.webcomponent.component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.talkweb.twdpe.base.common.exception.ExistChildrenException;
import com.talkweb.twdpe.base.org.pojo.Area;
import com.talkweb.twdpe.base.org.service.AreaService;
import com.talkweb.twdpe.core.log.Logger;
import com.talkweb.twdpe.core.log.LoggerFactory;
import com.talkweb.twdpe.core.util.StringUtil;
import com.talkweb.twdpe.dal.exception.DalException;
import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.twdpe.web.jcontrols.RuleTree;
import com.talkweb.twdpe.webcomponent.common.action.BusinessAction;

/**
 * 文件名称: AreaMngAction.java 内容摘要:
 * 
 * @author: 袁刚
 * @version: 1.0
 * @Date: 2011-4-13 下午02:51:02
 * 
 * 修改历史: 修改日期 修改人员 版本 修改内容 ---------------------------------------------- 2011-4-13 袁刚 1.0 1.0 XXXX
 * 
 * 版权: 版权所有(C)2011 公司: 拓维信息系统股份有限公司
 */
public class AreaMngAction extends BusinessAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2061872160007336103L;

	private static final Logger logger = LoggerFactory.getLogger(AreaMngAction.class);

	/**
	 * 通过注入方式初始化的地区管理类,用于地区管理的增，删，改，查的数据库操作类
	 * */
	@Autowired
	private AreaService areaService;

	/**
	 * 地区类型对象，用于添加和修改地区类型时封装的对象
	 * */
	private Area area = null;

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	/**
	 * 初始化表单控件默认值
	 * 
	 * @Title: initFromControl
	 * @Description: 初始化表单控件默认值
	 * @throws IOException
	 * @throws DalException
	 */
	public String initFromControl() {
		HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(
				ServletActionContext.HTTP_REQUEST);
		String id = request.getParameter("id");
		area = areaService.getArea(id);
		try {
			if (area != null) {
				this.formatData(this.addPrefix(area, "area."));
			}
		} catch (Exception e) {
			ServletActionContext.getRequest().setAttribute("throw", e);
			logger.error(e.getMessage(), e);
		}
		area = null;
		return SUCCESS;
	}

	/**
	 * 根据地区id获取地区信息
	 * 
	 * @Title: getAreaById
	 * @Description: 初始化表单控件默认值
	 * @throws IOException
	 * @throws DalException
	 */
	public String getAreaById() {
		HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(
				ServletActionContext.HTTP_REQUEST);
		String id = request.getParameter("id");
		area = areaService.getArea(id);
		try {
			if (area == null) {
				return null;
			}
			this.formatData(area);
		} catch (Exception e) {
			ServletActionContext.getRequest().setAttribute("throw", e);
			logger.error(e.getMessage(), e);
		}
		area = null;
		return SUCCESS;
	}

	/**
	 * 异步数据源对应的获取已选地区数据值
	 * @return
	 */
	private List<Area> initSelectedSubAreaTree(String currentAreaId) {
		List<Area> areaTree = new ArrayList<Area>();
		Area area;
		if (!StringUtil.isBlank(currentAreaId)) {
			while ((area = areaService.getArea(currentAreaId)) != null) {
				areaTree.add(area);
				currentAreaId = area.getUpArea();
			}
		}
		return areaTree;
	}

	/**
	 * 异步构建地区树数据 （异步数据源） <br>
	 * 传递请求参数ID，则查找指定节点下的数据（非初始加载，对应界面上点击+展开节点的情况） <br>
	 * 未传递请求参数ID的同时传递了请求参数value，则会查对应已选取节点的子地区树（初始化加载时的情况）
	 */
	public String initSubAreaTree() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String parentAreaId = request.getParameter("id");
		String selectedAreaId = request.getParameter("value");
		List<Area> areas = new ArrayList<Area>();
		Area area = new Area();
		pagination = new Pagination(true, 99999999);
		if (StringUtil.isBlank(parentAreaId)) {
			List<Area> roots = areaService.getRootAreas();
			List<Area> provinces = new ArrayList<Area>();
			areas.addAll(roots);
			// 查询至省级地区
			for (Area a : roots) {
				area.setUpArea(a.getArea());
				provinces.addAll(areaService.queryAreas(area, pagination).getList());
			}
			areas.addAll(provinces);
			// 查询已选地区的子树
			if (!StringUtil.isBlank(selectedAreaId)) {
				// 已选子地区路径信息
				List<Area> subAreaTree = initSelectedSubAreaTree(selectedAreaId);
				boolean end = false;
				do {
					boolean next = false;
					for (Area province : provinces) {
						for (Area child : subAreaTree) {
							if (province.getArea().equals(child.getArea())) {
								// 借用upAreaName字段标记异步数据源
								province.setUpAreaName("false");
								// 展开该省会下地区节点
								area.setUpArea(province.getArea());
								provinces = areaService.queryAreas(area, pagination).getList();
								areas.addAll(provinces);
								end = selectedAreaId.equals(child.getArea()) || provinces.size() == 0;
								next = true;
								break;
							}
						}
						if (next) {
							break;
						}
					}
				} while (!end);
			}
		} else {
			// 查询指定地区的子级城市
			area.setUpArea(parentAreaId);
			List<Area> children = areaService.queryAreas(area, pagination).getList();
			areas.addAll(children);
		}
		// 判断是否仍含子级地区
		for (Area a : areas) {
			area.setUpArea(a.getArea());
			// 如果地区级别为小于或等于1（省级），则默认为有子级地区
			if ("false".equals(a.getUpAreaName())) {
				continue;
			}
			if (a.getNodeLvl() <= 1 || areaService.queryAreas(area, pagination).getList().size() > 0) {
				// 注意！因为树的异步数据源必须有个标识值，此处借用upAreaName字段来保存
				a.setUpAreaName("true");
			} else {
				a.setUpAreaName("false");
			}
		}
		try {
			// 设置树控件数据映射关系
			RuleTree wtree = new RuleTree();
			wtree.setId("area");
			wtree.setVal("areaName");
			wtree.setPid("upArea");
			wtree.setAsyn("upAreaName");
			// 格式化树控件数据,按树控件的ID，VAL，PID的顺序
			this.formatData(this.initTreeData(areas, wtree));
		} catch (Exception e) {
			ServletActionContext.getRequest().setAttribute("throw", e);
			logger.error(e.getMessage(), e);
		}
		return SUCCESS;
	}

	/**
	 * 初始化树控件值
	 * 
	 * @Title: initTree
	 * @Description: 初始化树控件值
	 * @throws IOException
	 * @throws DalException
	 */
	@SuppressWarnings("unchecked")
	public String initTree() {
		List<Area> list = areaService.getAreas();
		try {
			// 设置树控件数据映射关系
			RuleTree wtree = new RuleTree();
			wtree.setId("area");
			wtree.setVal("areaName");
			wtree.setPid("upArea");

			// 格式化树控件数据,按树控件的ID，VAL，PID的顺序
			List<Map> l = this.initTreeData(list, wtree);
			this.formatData(l);
		} catch (Exception e) {
			ServletActionContext.getRequest().setAttribute("throw", e);
			// e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		return SUCCESS;
	}

	/**
	 * 初始化表单上级地区树控件值
	 * 
	 * @Title: initTreeInForm
	 * @Description: 初始化表单上级地区树控件值
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String initTreeInForm() {
		List<Area> list = areaService.getAreas();
		try {
			// 设置树控件数据映射关系
			RuleTree wtree = new RuleTree();
			wtree.setId("area");
			wtree.setVal("areaName");
			wtree.setPid("upArea");

			// 格式化树控件数据,按树控件的ID，VAL，PID的顺序
			List<Map> l = this.initTreeData(list, wtree);
			this.formatData(l);
		} catch (Exception e) {
			ServletActionContext.getRequest().setAttribute("throw", e);
			// e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		return SUCCESS;
	}

	/**
	 * 增加地区信息
	 * 
	 * @Title: addArea
	 * @Description: 增加地区信息
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public String addArea() {
	    area.setArea(System.nanoTime()+"");
	    area.setNodeLvl(3);
		String id = area.getArea();
		Area areainfo = areaService.getArea(id);
		if (area.getUpArea() == null || "".equals(area.getUpArea())) {
			area.setUpArea("-1");
			// Map map = new HashMap();
			// map.put("status", "bad");
			// map.put("msg", "上级地区不能为空");
			// pageData = map;
		}
		if (areainfo != null && areainfo.getArea() != null && !"".equals(areainfo.getArea())) {
			this.setMessage(2, "AREA", "ADD");
		} else {
			try {
				areaService.addArea(area);
				this.setMessage(1, "AREA", "ADD");
			} catch (Exception e) {
				ServletActionContext.getRequest().setAttribute("throw", e);
				this.setMessage(0, "AREA", "ADD");
				logger.error(e.getMessage(), e);
			}
		}
		// addSystemLog(LogConfigParam.MODULE_AREA_ID,LogConfigParam.MODULE_AREA_NAME,LogConfigParam.OPERATOR_TYPE_ADD,
		// jsonToString(JsonObject.toString(area)),
		// this.pageData.get("msg").toString());
		area = null;
		return SUCCESS;
	}

	/**
	 * 修改指定地区信息
	 * 
	 * @Title: updateArea
	 * @Description: 修改指定地区信息
	 */
	@SuppressWarnings("unchecked")
	public String updateArea() {
		if (area.getUpArea() == null || "".equals(area.getUpArea())) {
			area.setUpArea("-1");
		}
		try {
			areaService.modifyArea(area);
			this.setMessage(1, "AREA", "MODIFY");
		} catch (Exception e) {
			ServletActionContext.getRequest().setAttribute("throw", e);
			this.setMessage(0, "AREA", "MODIFY");
			logger.error(e.getMessage(), e);
		}
		// addSystemLog(LogConfigParam.MODULE_AREA_ID,LogConfigParam.MODULE_AREA_NAME,LogConfigParam.OPERATOR_TYPE_MODIFY,
		// jsonToString(JsonObject.toString(area)),
		// this.pageData.get("msg").toString());
		area = null;
		return SUCCESS;
	}

	/**
	 * 删除地区信息
	 * 
	 * @param id 需要删除的地区id
	 * @throws IOException
	 * @Title: delArea
	 * @Description: 删除地区信息
	 */
	public String delArea() {
		HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(
				ServletActionContext.HTTP_REQUEST);
		String id = request.getParameter("id");
		try {
			areaService.removeArea(id);
			this.setMessage(1, "AREA", "DELETE");
		} catch (ExistChildrenException e) {
			ServletActionContext.getRequest().setAttribute("throw", e);
			this.setMessage(2, "AREA", "DELETE");
			logger.debug(e.getMessage(), e);
		} catch (Exception e) {
			ServletActionContext.getRequest().setAttribute("throw", e);
			this.setMessage(0, "AREA", "DELETE");
			logger.debug(e.getMessage(), e);
		}
		// addSystemLog(LogConfigParam.MODULE_AREA_ID,LogConfigParam.MODULE_AREA_NAME,LogConfigParam.OPERATOR_TYPE_DELETE,
		// jsonToString(JsonObject.toString(id)),
		// this.pageData.get("msg").toString());
		return SUCCESS;
	}

	/**
	 * 查询所有地区信息
	 * 
	 * @Title: searchAreaToForm
	 * @Description: 查询所有地区信息
	 * @throws IOException
	 */
	public String searchAreaToForm() {
		int rows = 10000;
		int page = 1;
		Pagination pagination = new Pagination(page, rows);
		List<Area> list = areaService.getAreas();
		this.formatDatagirdData(list, pagination);
		return SUCCESS;
	}
}
