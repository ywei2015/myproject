package tw.sysbase.controller.pub;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import tw.sysbase.dao.PaginationSupport;
import tw.sysbase.entity.Pagination;
import tw.sysbase.entity.pub.ObjBase;
import tw.sysbase.entity.pub.ObjEntityRef;
import tw.sysbase.pub.Constants;
import tw.sysbase.pub.ResultUtil;
import tw.sysbase.servie.pub.ObjEntityRefService;

@Controller
@RequestMapping("ObjEntityRef")
public class ObjEntityRefController {
	@Autowired
	private ObjEntityRefService objEntityRefService;
	
	@RequestMapping("findListPagination")
	@ResponseBody
	public Pagination findListPagination(ObjEntityRef entityRef){
		PaginationSupport paginationSupport=objEntityRefService.findConditionWithPage(entityRef);
		return Pagination.init(paginationSupport);
	
	}
	
	@RequestMapping("deletes")
	@ResponseBody
	public Map<String,Object>deletes(String ids){
		objEntityRefService.deletes(ids);
		return ResultUtil.DefaultResult();
	}
	
	@RequestMapping("save")
	@ResponseBody
	public  Map<String,Object> save(ObjEntityRef ref){
		objEntityRefService.save(ref);
		return ResultUtil.DefaultResult();
	}
	
	@RequestMapping("get")
	@ResponseBody
	public  ObjEntityRef get(ObjEntityRef ref) {
		ObjEntityRef objEntityRef = objEntityRefService.get(ref);
		return objEntityRef;
	}
	/**
	 * 校验，判断是否存在相同的
	 */
	@RequestMapping("validField")
	@ResponseBody
	public Object validField(ObjEntityRef oer) {
		Map<String, Object> result = ResultUtil.DefaultResult();
		if(StringUtils.isNotBlank(oer.getName())) {
			result.put(Constants.MESSAGE, "该名称已存在");
		}else if(StringUtils.isNotBlank(oer.getCode())) {
			result.put(Constants.MESSAGE, "该code已经存在");
		}
		Boolean flag=objEntityRefService.isExist(oer);
		result.put("valid", flag);
		return result;
	}
	/**
	 * 校验用户名和code ，是否存在相同
	 * @param ob
	 * @return
	 */
	@RequestMapping("validCodeAndName")
	@ResponseBody
	public Object validCodeAndName(ObjEntityRef oer) {
		Map<String, Object> result = objEntityRefService.validCodeAndName(oer);
		return result;
	}
	
}
