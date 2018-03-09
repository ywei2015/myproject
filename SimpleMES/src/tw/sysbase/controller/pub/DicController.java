package tw.sysbase.controller.pub; 

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import tw.sysbase.dao.PaginationSupport;
import tw.sysbase.entity.Pagination;
import tw.sysbase.entity.pub.Dic;
import tw.sysbase.entity.pub.ObjBase;
import tw.sysbase.exception.LogException;
import tw.sysbase.pub.Constants;
import tw.sysbase.pub.ResponseBase;
import tw.sysbase.pub.ResultUtil;
import tw.sysbase.servie.pub.DicService;
/**
* <p>类说明：Spring MVC Test Controller Class</p> 
* 
* <p>Copyright: Copyright (c) 2016</p>
*    
* @author xie&li
* 2017-08-26
*
* @version 1.0 
 * 
 */
@Controller
@RequestMapping("/Dic")
public class DicController {
	 
	@Resource
	private DicService dicService;
	
	/**
	 * 异步加载基础模型子节点
	 * @param parentId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/fetchDicList")	
	public ResponseBase fetchDicList(String objId){  
		ResponseBase res = new ResponseBase();
		
		try {
			res = dicService.fetchDicList(objId);
		} catch (Exception e) { 
			e.printStackTrace();
			LogException.logEx(e,DicController.class,"sysbase.fetchDicList");
		} 
		return res;
	}
	
	/**
	 * @Description: 查询dic分页查询
	 * @author: zw
	 */
	@RequestMapping("findListDicPagination")
	@ResponseBody
	public Pagination findListDicPagination(Dic dic){
		
		PaginationSupport paginationSupport=dicService.findListDicPagination(dic);
		Pagination pagination = Pagination.init(paginationSupport);
		return pagination;
	}
	/**
	 * @Description: 保存一个dic实体
	 * @author: zw
	 */
	@RequestMapping("save")
	@ResponseBody
	public Map<String,Object> save(Dic dic){	
		dic=dicService.save(dic);
		return ResultUtil.DefaultResult();
	}
	@RequestMapping("delete")
	@ResponseBody
	public Map<String,Object> delete(Dic dic){
		dicService.delete(dic);
		return ResultUtil.DefaultResult();
	}
	/**
	 * @Description: 标记删除
	 * @author: zw
	 */
	@RequestMapping("deletes")
	@ResponseBody
	public Map<String,Object> deletes(String ids){
		dicService.deletes(ids);
		return ResultUtil.DefaultResult();
	}
	
	@RequestMapping("get")
	@ResponseBody
	public Dic get(String id){
		Dic dic=dicService.getDic(id);
		return dic;
	}
	/**
	 * 校验，判断是否存在相同的
	 */
	@RequestMapping("validField")
	@ResponseBody
	public Object validField(Dic dic) {
		Map<String, Object> result = ResultUtil.DefaultResult();
		if(StringUtils.isNotBlank(dic.getName())) {
			result.put(Constants.MESSAGE, "该名称已存在");
		}else if(StringUtils.isNotBlank(dic.getCode())) {
			result.put(Constants.MESSAGE, "该code已经存在");
		}
		Boolean flag=dicService.isExist( dic);
		result.put("valid", flag);
		return result;
	}
	@RequestMapping("validCodeAndName")
	@ResponseBody
	public Object validCodeAndName(Dic dic) {
		Map<String, Object> result = dicService.validCodeAndName(dic);
		return result;
	}
}
