package tw.sysbase.controller.pub; 

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import tw.business.entity.pub.DicView;
import tw.sysbase.dao.PaginationSupport;
import tw.sysbase.entity.Pagination;
import tw.sysbase.entity.pub.Dic;
import tw.sysbase.entity.pub.ObjEntityRef;
import tw.sysbase.entity.pub.ObjStructure;
import tw.sysbase.entity.pub.TreeNode;
import tw.sysbase.exception.LogException;
import tw.sysbase.pub.Constants;
import tw.sysbase.pub.ResponseBase;
import tw.sysbase.pub.ResultUtil;
import tw.sysbase.servie.pub.DicService;
import tw.sysbase.servie.pub.ObjBasicService;
import tw.sysbase.servie.pub.ObjStructureService;
import tw.sysbase.servie.sec.SecurityService;
import tw.sysbase.servie.sec.UserService;
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
@RequestMapping("/ObjStructure")
public class ObjStructureController {
	 
	@Resource
	private SecurityService securityService; 
	@Resource
	private UserService userService; 
	@Resource
	private ObjStructureService objStructureService;
	@Resource
	private ObjBasicService objBasicService;
	@Autowired
	private DicService dicService;
	
	/**
	 * 基础模型TREE
	 * @param objStructureId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/fetchDicTree")	
	public ResponseBase fetchDicTree(String objStructureId){  
		ResponseBase res = new ResponseBase();
		
		try {
			List list = objStructureService.fetchDicTree(objStructureId);
			res.setDataset(list, "treenode");
		} catch (Exception e) { 
			e.printStackTrace();
			LogException.logEx(e,ObjStructureController.class,"sysbase.fetchDicTree");
		} 
		return res;
	}
	
	/**
	 * 加载子节点作为COMBOBOX
	 * @param structId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/fetchDicTreeByCombobox")	
	public ResponseBase fetchDicTreeByCombobox(String structId){  
		ResponseBase res = new ResponseBase();
		//select * from t_pub_obj_structure where f_pid='4028802934e54c060134e58955c7000d'
		try {
			List list = objStructureService.fetchDicTreeByCombobox(structId);
			res.setDataset(list, "treenode");
		} catch (Exception e) { 
			e.printStackTrace();
			LogException.logEx(e,ObjStructureController.class,"sysbase.fetchDicTreeByParentId");
		} 
		return res;
	}
	/**
	 * 基础模型管理--根据combobox查找TREE
	 * @param structId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/fetchDicTreeByComboboxSub")	
	public ResponseBase fetchDicTreeByComboboxSub(String structId){  
		ResponseBase res = new ResponseBase();
		try {
			List list = objStructureService.fetchDicTreeByComboboxSub(structId);
			res.setDataset(list, "treenode");
		} catch (Exception e) { 
			e.printStackTrace();
			LogException.logEx(e,ObjStructureController.class,"sysbase.fetchDicTreeByComboboxSub");
		} 
		return res;
	}
	
	/*@ResponseBody
	@RequestMapping(value="/fetchDicTreeByComboboxSubById")	
	public ResponseBase fetchDicTreeByComboboxSubById(String id,String structId){ 
		System.out.println("structId="+structId);
		ResponseBase res = new ResponseBase();
		try {
			List list = objStructureService.fetchDicTreeByComboboxSubById(structId,id);
			res.setDataset(list, "treenode");
		} catch (Exception e) { 
			e.printStackTrace();
			LogException.logEx(e,ObjStructureController.class,"sysbase.fetchDicTreeByComboboxSub");
		} 
		return res;
	}*/
	/**
	 * 刷新树节点
	 */
	@ResponseBody
	@RequestMapping(value="/fetchDicTreeByComboboxSubById")	
	public List<TreeNode> fetchDicTreeByComboboxSubById(String structId, String id){
		List<TreeNode> list = null;
		try {
			list = objStructureService.fetchDicTreeByComboboxSubById(structId,id);
		} catch (Exception e) { 
			e.printStackTrace();
			LogException.logEx(e,ObjStructureController.class,"sysbase.fetchDicTreeByComboboxSub");
		}
		
		return list;
	}
	
	@ResponseBody
	@RequestMapping(value="/fetchDicListFormaintain")	
	public Pagination fetchDicListFormaintain(Dic dic){  
		ResponseBase res = new ResponseBase();
		Pagination pagination=new Pagination();
			res = objStructureService.fetchDicListFormaintain(dic);
			List list = (List) res.getDataset().get("rows");
			pagination.setRows(list);
			if(list!=null){
				pagination.setTotal(list.size());
			}
			
		return pagination;
	}
	/**
	 * 获取菜单下的菜单功能项
	 * @param dic
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/fetchFuncDicListFormaintain")	
	public Pagination fetchFuncDicListFormaintain(Dic dic){  
		ResponseBase res = new ResponseBase();
		Pagination pagination=new Pagination();
			res = objStructureService.fetchFuncDicListFormaintain(dic);
			List list = (List) res.getDataset().get("rows");
			pagination.setRows(list);
			if(list!=null){
				pagination.setTotal(list.size());
			}
		return pagination;
	}
	/**
	 * 人员管理（功能授权之功能菜单）
	 * @param parentId
	 * @param structId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/fetchAccessControlOSList")	
	public ResponseBase fetchAccessControlOSList(){  
		ResponseBase res = new ResponseBase();
		try {
			List list = objStructureService.fetchAccessControlOSList();
			res.setDataset(list, "treenode");
		} catch (Exception e) { 
			e.printStackTrace();
			LogException.logEx(e,ObjStructureController.class,"sysbase.fetchAccessControlOSList");
		} 
		return res;
	}
	
	/**
	 * 角色管理（功能授权之功能菜单树）
	 * @param parentId
	 * @param structId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/funTreeDataById")	
	public ResponseBase funTreeDataById(String id){  
		ResponseBase res = new ResponseBase();
		try {
			List list = objStructureService.funTreeDataById(id);
			res.setDataset(list, "treenode");
		} catch (Exception e) { 
			e.printStackTrace();
			LogException.logEx(e,ObjStructureController.class,"sysbase.fetchAccessControlOSList");
		} 
		return res;
	}
	
	/**
	 * 用户管理（部门岗位联动）
	 * @param parentId
	 * @param structId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/funTreeDataByIdAndParentId")	
	public ResponseBase funTreeDataByIdAndParentId(String id,String parentId){  
		ResponseBase res = new ResponseBase();
		try {
			List list = objStructureService.funTreeDataByIdAndParentId(id,parentId);
			res.setDataset(list, "treenode");
		} catch (Exception e) { 
			e.printStackTrace();
			LogException.logEx(e,ObjStructureController.class,"sysbase.fetchAccessControlOSList");
		} 
		return res;
	}
	
	/**
	 * 功能菜单下的具体菜单授权
	 * @param userId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/funTreeSelectedDataForUser")	
	public ResponseBase funTreeSelectedDataForUser(String userId){  
		ResponseBase res = new ResponseBase();
		try {
			res = objStructureService.funTreeSelectedDataForUser(userId);
		} catch (Exception e) { 
			e.printStackTrace();
			LogException.logEx(e,ObjStructureController.class,"sysbase.funTreeSelectedDataForUser");
		} 
		return res;
	}
	
	/**
	 * 功能菜单下的具体菜单授权
	 * @param userId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/funTreeSelectedData")	
	public ResponseBase funTreeSelectedData(String roleId){  
		ResponseBase res = new ResponseBase();
		try {
			res = objStructureService.funTreeSelectedData(roleId);
		} catch (Exception e) { 
			e.printStackTrace();
			LogException.logEx(e,ObjStructureController.class,"sysbase.funTreeSelectedData");
		} 
		return res;
	}
	@RequestMapping("fetchObjStructureList")
	@ResponseBody
	public Pagination fetchObjStructureList(ObjStructure obj){
		
		PaginationSupport paginationSupport=objStructureService.fetchObjStructureList(obj);
		Pagination pagination = Pagination.init(paginationSupport);
		return pagination;
	}
	@RequestMapping("fetchObjStructureSub")
	@ResponseBody
	public ResponseBase fetchObjStructureSub(String id){
		ResponseBase res = new ResponseBase();
		try {
			res = objStructureService.fetchObjStructureSub(id);
		} catch (Exception e) { 
			e.printStackTrace();
			LogException.logEx(e,ObjStructureController.class,"sysbase.fetchObjStructureSub");
		} 
		return res;
	}
	@RequestMapping("fetchObjStructureRelation")
	@ResponseBody
	public ResponseBase fetchObjStructureRelation(String id){
		ResponseBase res = new ResponseBase();
		try {
			res = objStructureService.fetchRelationTree(id);
		} catch (Exception e) { 
			e.printStackTrace();
			LogException.logEx(e,ObjStructureController.class,"sysbase.fetchObjStructureRelation");
		} 
		return res;
	}
	@RequestMapping("deleteObjStructure")
	@ResponseBody
	public Map<String,Object> deleteObjStructure(String ids){
		try {
			objStructureService.deleteObjStructure(ids);
		} catch (Exception e) {
			e.printStackTrace();
			LogException.logEx(e,ObjStructureController.class,"sysbase.deleteObjStructure");
		}
		return ResultUtil.DefaultResult();
	}
	@RequestMapping("saveObjStructure")
	@ResponseBody
	public  Map<String,Object> saveObjStructure(ObjStructure obj,String ids){
		objStructureService.saveObjStructure(obj,ids);
		return ResultUtil.DefaultResult();
	}
	
	@RequestMapping("getObjStructure")
	@ResponseBody
	public ResponseBase getObjStructure(String id){
		ResponseBase res = new ResponseBase();
		try {
			res = objStructureService.getObjStructure(id);
		} catch (Exception e) {
			e.printStackTrace();
			LogException.logEx(e,ObjStructureController.class,"sysbase.getObjStructure");
		}
		return res;
	}
	
	@RequestMapping("fetchRelationTree")
	@ResponseBody
	public ResponseBase fetchRelationTree(String id){
		ResponseBase res = new ResponseBase();
		try {
			res = objStructureService.fetchRelationTree(id);
		} catch (Exception e) {
			e.printStackTrace();
			LogException.logEx(e,ObjStructureController.class,"sysbase.fetchRelationTree");
		}
		return res;
	}
	/**
	 * @Description:获取dic基本数据信息和关联obj_entity_ref表的信息，根据struct_id，和dic_id查询 
	 * @author: zw
	 */
	@RequestMapping("dic_ref_detail")
	@ResponseBody
	public Dic dic_ref_detail(String id,String structId){
	    Dic dic=dicService.dic_ref_detail(id,structId);
		return dic;
	}
	
	@RequestMapping("saveDicRef")
	@ResponseBody
	public Map<String,Object> saveDicRef(Dic dic){
		dicService.saveDicRef(dic);
		return ResultUtil.DefaultResult();
	}
	@RequestMapping("saveDic")
	@ResponseBody
	public Map<String,Object> saveDic(Dic dic){
		dicService.saveDic(dic);
		return ResultUtil.DefaultResult();
	}
	@RequestMapping("deletesDicRef")
	@ResponseBody
	public Map<String ,Object> deletesDicRef(String ids){
		dicService.deletesDicRef(ids);
		
		return ResultUtil.DefaultResult();
		
	}
	@RequestMapping("fetchDicForAddMaintain")
	@ResponseBody
	public Pagination fetchDicForAddMaintain(String struId, String typeId,String parentId, String name){
		ResponseBase res = new ResponseBase();
		Pagination pagination=new Pagination();
			res = objStructureService.fetchDicForAddMaintain(struId, typeId, parentId, name);
			List list = (List) res.getDataset().get("rows");
			pagination.setRows(list);
			if(list!=null){
				pagination.setTotal(list.size());
			}
			
		return pagination;
		/*ResponseBase res = new ResponseBase();
		try {
			res = objStructureService.fetchDicForAddMaintain(struId, typeId, name);
		} catch (Exception e) { 
			e.printStackTrace();
			LogException.logEx(e,ObjStructureController.class,"sysbase.fetchDicForAddMaintain");
		} 
		return res;*/
	}
	/**
	 * 添加已有项
	 * @param fullJson
	 * @return
	 */
	@RequestMapping("saveExistTypeRefDic")
	@ResponseBody
	public Map<String,Object> saveExistTypeRefDic(String childId,String typeId,String objEntityRefId,String parentId){
		try {
			objStructureService.saveExistTypeRefDic(childId, typeId, objEntityRefId, parentId);
		} catch (Exception e) {
			e.printStackTrace();
			LogException.logEx(e,ObjStructureController.class,"sysbase.saveExistTypeRefDic");
		}
		return ResultUtil.DefaultResult();
	}
	
	@RequestMapping("fetchObjEntityRefList")
	@ResponseBody
	public  Map<String, Object> fetchObjEntityRefList(String structId,String parentId){
		List<DicView>list = objStructureService.fetchObjEntityRefList(structId,parentId);
		Map<String, Object> result = ResultUtil.initResult();
		Map<String, Object> defResult = ResultUtil.DefResult(result, "data", list);
		
		return defResult ;
	}
	
	/**
	 * 校验，判断是否存在相同的
	 */
	@RequestMapping("validField")
	@ResponseBody
	public Object validField(ObjStructure os) {
		Map<String, Object> result = ResultUtil.DefaultResult();
		if(StringUtils.isNotBlank(os.getName())) {
			result.put(Constants.MESSAGE, "该名称已存在");
		}else if(StringUtils.isNotBlank(os.getCode())) {
			result.put(Constants.MESSAGE, "该code已经存在");
		}
		Boolean flag=objStructureService.isExist(os);
		result.put("valid", flag);
		return result;
	}
	@RequestMapping("validCodeAndName")
	@ResponseBody
	public Object validCodeAndName(ObjStructure obj) {
		Map<String, Object> result = objStructureService.validCodeAndName(obj);
		return result;
	}
	
}
