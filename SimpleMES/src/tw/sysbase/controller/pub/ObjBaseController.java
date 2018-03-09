package tw.sysbase.controller.pub;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import tw.business.entity.pub.DicView;
import tw.sysbase.entity.Pagination;
import tw.sysbase.entity.pub.ObjBase;
import tw.sysbase.exception.LogException;
import tw.sysbase.pub.Constants;
import tw.sysbase.pub.ResponseBase;
import tw.sysbase.pub.ResultUtil;
import tw.sysbase.servie.pub.ObjBaseService;

@Controller
@RequestMapping("objBase")
public class ObjBaseController {
	@Autowired
	private ObjBaseService objBaseService;
	@RequestMapping("toIndex")
	public String toIndex(){
		return "platform/business/dic/basic_data";
	}
	/**
	 * @Description: 获得tree数据集合
	 * @author: zw
	 */
	@RequestMapping("getList")
	@ResponseBody
	public List<ObjBase> getList(ObjBase ob){
		List<ObjBase> list = objBaseService.getList(ob);
		return list;
	}
	@RequestMapping("isExistChild")
	@ResponseBody
	public Object isExistChild(ObjBase ob){
		List<ObjBase> list = objBaseService.getList(ob);
		if(list!=null &&list.size()>=1) {
			Map<String, Object> result = ResultUtil.ErrorResult();
			result.put(Constants.MESSAGE, "存在子节点不能删除,请先删除子节点！");
			return result;
		}
		return ResultUtil.DefaultResult();
	}
	/**
	 * @Description:右侧表单查询基础数据，根据父id查询
	 * @author: zw
	 */
	@RequestMapping("findListPagination")
	@ResponseBody
	public Pagination findListPagination(ObjBase ob){
		
		Pagination pagination =objBaseService.getListByCondition(ob);
	
		return pagination;
	}

	/**
	 * @Description: 标记删除
	 * @author: zw
	 */
	@RequestMapping("deletes")
	@ResponseBody
	public Map<String,Object> deletes(String ids){
		objBaseService.deletes(ids);
		return ResultUtil.DefaultResult();
	}
	/**
	 * @Description: 根据id查询
	 * @author: zw
	 */
	@RequestMapping("get")
	@ResponseBody
	public ObjBase get(ObjBase ob){
		ObjBase objbase = objBaseService.get(ob);
		return objbase;
	}
	/**
	 * @Description: 保存一个dic实体
	 * @author: zw
	 * @throws Exception 
	 */
	@RequestMapping("save")
	@ResponseBody
	public Object save(ObjBase objbase) throws Exception{
		Object o= objBaseService.save(objbase);
		return ResultUtil.DefaultResult();
	}
	/**
	 * 校验，判断是否存在相同的  无用
	 */
	@RequestMapping("validField")
	@ResponseBody
	public Object validField(ObjBase ob) {
		Map<String, Object> result = ResultUtil.DefaultResult();
		if(StringUtils.isNotBlank(ob.getName())) {
			result.put(Constants.MESSAGE, "该名称已存在");
		}else if(StringUtils.isNotBlank(ob.getCode())) {
			result.put(Constants.MESSAGE, "该code已经存在");
		}
		Boolean flag=objBaseService.isExist( ob);
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
	public Object validCodeAndName(ObjBase ob) {
		Map<String, Object> result = objBaseService.validCodeAndName(ob);
		return result;
	}
	
	@RequestMapping("fetchOBTypeVoDetail")
	@ResponseBody
	public ResponseBase fetchOBTypeVoDetail(String id, String flag){
		ResponseBase res = new ResponseBase();
		try {
			res = objBaseService.fetchOBTypeVoDetail(id, flag);
		} catch (Exception e) { 
			e.printStackTrace();
			LogException.logEx(e,ObjStructureController.class,"sysbase.fetchOBTypeVoDetail");
		} 
		return res;
	}
	
	@RequestMapping("fetchObjTypeCombox")
	@ResponseBody
	public  Map<String, Object> fetchObjTypeCombox(){
		List<DicView>list = objBaseService.fetchObjTypeCombox();
		Map<String, Object> result = ResultUtil.initResult();
		Map<String, Object> defResult = ResultUtil.DefResult(result, "data", list);
		
		return defResult ;
	}
	
	@RequestMapping("fetchObjattributeCombox")
	@ResponseBody
	public  Map<String, Object> fetchObjattributeCombox(){
		List<DicView>list = objBaseService.fetchObjattributeCombox();
		Map<String, Object> result = ResultUtil.initResult();
		Map<String, Object> defResult = ResultUtil.DefResult(result, "data", list);
		
		return defResult ;
	}
	
	@RequestMapping("saveOBTypeVo")
	@ResponseBody
	public Object saveOBTypeVo(String listData,String formData) throws Exception{
		objBaseService.saveOBTypeVo(listData,formData);
		return ResultUtil.DefaultResult();
	}
	
	@RequestMapping("updateOBTypeVo")
	@ResponseBody
	public Object updateOBTypeVo(String listData,String formData) throws Exception{
		objBaseService.updateOBTypeVo(listData,formData);
		return ResultUtil.DefaultResult();
	}
	
	/**
	 * 获取关联动态字段
	 */
	@RequestMapping("fetchObjAttributeList")
	@ResponseBody
	public ResponseBase fetchObjAttributeList(String objId) throws Exception{
		ResponseBase res = new ResponseBase();
		try {
			res = objBaseService.fetchObjAttributeList(objId);
		} catch (Exception e) { 
			e.printStackTrace();
			LogException.logEx(e,ObjStructureController.class,"sysbase.fetchOBTypeVoDetail");
		} 
		return res;
	}
	
	/**
	 * 基础数据模型维护子节点radio
	 * @param structId
	 * @param keyId
	 * @return
	 */
	@RequestMapping("fetchObjBaseList")
	@ResponseBody
	public ResponseBase fetchObjBaseList(String structId,String keyId){
		ResponseBase res = new ResponseBase();
		try {
			res = objBaseService.fetchObjBaseList(structId, keyId);
		} catch (Exception e) { 
			e.printStackTrace();
			LogException.logEx(e,ObjStructureController.class,"sysbase.fetchOBTypeVoDetail");
		} 
		return res;
	}
}
