package tw.sysbase.servie.pub;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.business.entity.pub.DicView;
import tw.business.service.equ.DicViewService;
import tw.sysbase.dao.GenericDao;
import tw.sysbase.dao.PaginationSupport;
import tw.sysbase.entity.Pagination;
import tw.sysbase.entity.pub.Dic;
import tw.sysbase.entity.pub.ObjBase;
import tw.sysbase.entity.pub.ObjBaseView;
import tw.sysbase.entity.pub.ObjEntityRef;
import tw.sysbase.entity.pub.ObjStructure;
import tw.sysbase.entity.pub.ObjStructureRef;
import tw.sysbase.entity.pub.TreeNode;
import tw.sysbase.entity.pub.TypeRefDic;
import tw.sysbase.pub.Constants;
import tw.sysbase.pub.DateBean;
import tw.sysbase.pub.ResponseBase;
import tw.sysbase.pub.ResultUtil;
import tw.sysbase.pub.StingUtil;
import tw.sysbase.pub.Utility;
import tw.sysbase.servie.sec.SecurityService;
import tw.sysbase.servie.sec.UserService;

@Service
@Transactional
public class ObjStructureService {
	@Resource
	private GenericDao genericDao;
	@Autowired
	private DicService dicService;
	@Autowired
	private UserService userService;
	@Autowired
	private SecurityService securityService;
	@Autowired
	private DicViewService dicViewService;

	/**
	 * 基础模型TREE
	 * 
	 * @param objStructureId
	 * @return
	 */
	public List fetchDicTree(String objStructureId) {
		Map<String, Object> map = new HashMap<String, Object>();
		List list = new ArrayList();
		String queryName = "BD.TREE.FETCHTRD.NODE"; // 根节点
		String queryName1 = "BD.TREE.FETCHTRD.LEAF"; // 子节点
		List listNode = genericDao.getListWithNativeSql(queryName,
				new Object[] { objStructureId });
		List listLeaf = genericDao.getListWithNativeSql(queryName1,
				new Object[] { objStructureId });
		if (listNode.size() > 0) {
			for (int i = 0; i < listNode.size(); i++) {
				Object[] obj = (Object[]) listNode.get(i);
				TreeNode treeNode = new TreeNode();
				treeNode.setId(obj[0].toString());
				treeNode.setPid(obj[1].toString());
				treeNode.setName(obj[2].toString());
				treeNode.setRelationId("4028a35d227d13ff01227d6403fd0002");
				treeNode.setKeyId(obj[4].toString());
				list.add(treeNode);
			}
		}
		if (listLeaf.size() > 0) {
			for (int j = 0; j < listLeaf.size(); j++) {
				Object[] obj = (Object[]) listLeaf.get(j);
				TreeNode treeNode = new TreeNode();
				treeNode.setId(obj[0].toString());
				treeNode.setPid(obj[1].toString());
				treeNode.setName(obj[2].toString());
				treeNode.setRelationId("4028a35d227d13ff01227d6403fd0002");
				treeNode.setKeyId(obj[4].toString());
				list.add(treeNode);
			}
		}
		return list;
	}

	/*
	 * public List fetchDicTreeByCombobox(String structId) { Map<String,Object>
	 * map = new HashMap<String,Object>(); List list = new ArrayList(); String
	 * queryName = "BD.TREE.FETCHTRD.NODE"; List listLeaf =
	 * genericDao.getListWithNativeSql(queryName, new Object[]{structId});
	 * if(listLeaf.size() > 0){ for(int i=0;i<listLeaf.size();i++){ Object []obj
	 * = (Object[]) listLeaf.get(i); TreeNode treeNode = new TreeNode();
	 * treeNode.setId(obj[0].toString()); treeNode.setPid(obj[1].toString());
	 * treeNode.setName(obj[2].toString());
	 * treeNode.setRelationId(obj[4]==null?"":obj[4].toString());
	 * list.add(treeNode); } } return list; }
	 */

	/*
	 * public List fetchDicTreeByComboboxSub(String pid) { Map<String,Object>
	 * map = new HashMap<String,Object>(); List list = new ArrayList(); String
	 * queryName = "BD.TREE.FETCHTRD.SUBNODE"; List listLeaf =
	 * genericDao.getListWithNativeSql(queryName, new Object[]{pid});
	 * if(listLeaf.size() > 0){ for(int i=0;i<listLeaf.size();i++){ Object []obj
	 * = (Object[]) listLeaf.get(i); TreeNode treeNode = new TreeNode();
	 * treeNode.setId(obj[0].toString()); treeNode.setPid(obj[1].toString());
	 * treeNode.setName(obj[2].toString()); list.add(treeNode); } } return list;
	 * }
	 */
	public List fetchDicTreeByComboboxSub(String structId) {
		Map<String,Object> map = new HashMap<String,Object>();
		List list = new ArrayList();
		if("4028a35d227d13ff01227d6403fd0002".equals(structId)){
			list = this.fetchDicTree(structId);
		}else{
			
			String queryName = "BD.TREE.FETCH.TREE.BY.COMBOBOX"; //父节点
			String queryName1 = "BD.TREE.FETCH.TREE.BY.COMBOBOXSUB";
			Object [] paras = null;
			if(structId.equals(Constants.RES_ACTION_TYPE)){
				paras = new Object[] {structId,Constants.RES_MENU_TYPE};
			}else{
				paras = new Object[] {structId,null};
			}
			List listLeaf = genericDao.getListWithNativeSql(queryName, paras);
			List listLeaf1 = genericDao.getListWithNativeSql(queryName1, new Object[]{structId}); 
			if(listLeaf.size() > 0){
				for(int i=0;i<listLeaf.size();i++){
					Object []obj = (Object[]) listLeaf.get(i);
					TreeNode treeNode = new TreeNode();
					treeNode.setId(obj[0].toString());
					treeNode.setPid(obj[2].toString());
					treeNode.setName(obj[1].toString());
					treeNode.setRelationId(structId);
					treeNode.setKeyId(obj[6].toString());
					treeNode.setObjRefPid(obj[7].toString());
					treeNode.setObjPid(obj[8].toString());
					list.add(treeNode);
				}
			}
			if(listLeaf1.size() > 0){
				for(int j=0;j<listLeaf1.size();j++){
					Object []obj = (Object[]) listLeaf1.get(j);
					TreeNode treeNode = new TreeNode();
					treeNode.setId(obj[0].toString());
					treeNode.setPid(null);
					treeNode.setName(dicService.getDicName(obj[0].toString()));
					treeNode.setRelationId(structId);
					treeNode.setKeyId(obj[0].toString());
					treeNode.setObjRefPid(obj[2].toString());
					list.add(treeNode);
				}
			}
			if("10001".equals(structId)){
				TreeNode treeNode = new TreeNode();
				treeNode.setId(Constants.MENU_ROOT_ID);
				treeNode.setPid(null);
				treeNode.setName(dicService.getDicName(Constants.MENU_ROOT_ID));
				treeNode.setRelationId(structId);
				treeNode.setKeyId(Constants.MENU_ROOT_ID);
				list.add(treeNode);
			}
		}
		return list;
	}
	/**
	 * 获取节点
	 * @param list
	 * @param pid
	 * @return
	 */
	public static List<TreeNode> getChilds(List<TreeNode> list,String pid){  
        List<TreeNode> arr = new ArrayList<TreeNode>();  
        for(TreeNode location : list){  
            if(pid.equals(location.getPid())){  
                arr.addAll(getChilds(list, location.getId()));  
                arr.add(location);  
            }  
        }  
        return arr;  
    }
	/**
	 * 刷新节点
	 */
	public List fetchDicTreeByComboboxSubById(String structId, String id) {
		String queryName = "BD.TREE.FETCHTRD.BYIDANDPARENTIDS"; //父节点
		List<TreeNode> listLeaf = genericDao.getListWithVariableParas(queryName, new Object[]{structId});
		List<TreeNode> listNode = getChilds(listLeaf, id);  
		return listNode;
	}
	
	/**
	 * 父节点作为COMBOBOX
	 * 
	 * @param parentId
	 * @return
	 */
	public List fetchDicTreeByCombobox(String structId) {
		Map<String, Object> map = new HashMap<String, Object>();
		List list = new ArrayList();
		String queryName = "BD.TREE.FETCH.TREE.BY.COMBOBOX"; // 父节点
		List listLeaf = genericDao.getListWithNativeSql(queryName,
				new Object[] { structId,null });
		if (listLeaf.size() > 0) {
			for (int i = 0; i < listLeaf.size(); i++) {
				Object[] obj = (Object[]) listLeaf.get(i);
				TreeNode treeNode = new TreeNode();
				treeNode.setId(obj[0].toString());
				treeNode.setPid(obj[2].toString());
				treeNode.setName(obj[1].toString());
				treeNode.setRelationId(obj[5] == null ? "" : obj[5].toString());
				treeNode.setKeyId(obj[6].toString());
				treeNode.setObjRefPid(obj[7].toString());
				treeNode.setObjPid(obj[8].toString());
				list.add(treeNode);
			}
		}
		return list;
	}

	public List<ObjBase> getList(ObjBase objBase) {
		//查询数据
		//Object[] parameter = {Constants.SYS_FLAG_USEING };
	
		String id=null;
		String pid=null;
		if(objBase!=null && objBase.getId()!=null){
			pid= objBase.getId();
		}else{
			id="1000";
		}
		Integer sysFlag=objBase.getSysFlag();
//		Object[] parameter =;
		@SuppressWarnings("unchecked")
		List<ObjBase> objBases = genericDao.getListWithVariableParas("OBJBASE.GETLIST.GET_BYPID", new Object[]{id,pid,sysFlag});
		//格式转换
		
		for(int i=0;i<objBases.size();i++){
			ObjBase base=objBases.get(i);
			List<ObjBase> sonbases = genericDao.getListWithVariableParas("OBJBASE.GETLIST.GET_BYPID",new Object[]{null,base.getPid(),sysFlag} );	
			if(sonbases!=null && !sonbases.isEmpty()){
				base.SetIsParent(true);
			}else{
				/*base.setTreeNode(false);*/
			}
		}
		return objBases;
	}

	/**
	 * 父节点得到子节点列表
	 * 
	 * @param structId
	 * @return
	 */
	public ResponseBase fetchDicListFormaintain(String parentId, String structId) {
		ResponseBase res = new ResponseBase();
		String queryName = "DB.DIC.FETCHDICDETAILFORMAINTAIN"; // 父节点
		List<Dic> dicList = genericDao.getListWithVariableParas(queryName,
				new Object[] { parentId, structId });
		res.setDataset(dicList, "rows");
	/*	res.setTotalRecords(dicList.size());*/
		return res;
	}
	/**
	 * 父节点得到子节点列表
	 * @param structId
	 * @return
	 */
	public ResponseBase fetchDicListFormaintain(Dic dic) {
		String parentId= dic.getParentId();
		String structId=dic.getStructId();
		String code=dic.getCode();
		String name=dic.getName();
		String type = dic.getType();
		code = (StringUtils.isBlank(code) ? code : "%" + code + "%");
		name = (StringUtils.isBlank(name) ? name : "%" + name + "%");
		ResponseBase res = new ResponseBase();
		String queryName = "DB.DIC.FETCHDICDETAILFORMAINTAIN"; //父节点
		List<Dic> dicList = genericDao.getListWithVariableParas(queryName, new Object[]{parentId,structId,type,code,name});
		res.setDataset(dicList, "rows");
	/*	res.setTotalRecords(dicList.size());*/
		return res;
	}
	/**
	 * 获取菜单下的菜单功能项
	 * @param dic
	 * @return
	 */
	public ResponseBase fetchFuncDicListFormaintain(Dic dic) {
		String parentId= dic.getParentId();
		String structId=dic.getStructId();
		String objEntityRefId = dic.getObjEntityRefId();
		String code=dic.getCode();
		String name=dic.getName();
		String type = dic.getType();
		code = (StringUtils.isBlank(code) ? code : "%" + code + "%");
		name = (StringUtils.isBlank(name) ? name : "%" + name + "%");
		String param = null;
		if(objEntityRefId.equals(Constants.RES_ACTION_TYPE)){
			param = Constants.RES_ACTION_TYPE;
		}else if(objEntityRefId.equals(Constants.RES_MENU_TYPE)){
			param = Constants.RES_MENU_TYPE;
		}
		ResponseBase res = new ResponseBase();
		String queryName = "DB.DIC.FETCHDICDETAILFORMAINTAIN3"; //父节点
		List<Dic> dicList = genericDao.getListWithVariableParas(queryName, new Object[]{parentId,param,name,code});
		res.setDataset(dicList, "rows");
		return res;
	}
	/**
	 * 人员管理（功能授权之功能菜单）
	 * 
	 * @return
	 */

	public List<TreeNode> funTreeDataById( String id){
		List list=new ArrayList();
		List ret=genericDao.getListWithNativeSql("BD.TREE.FETCHTRD.BYID", new Object[] { id });
		if(ret.size() > 0){
			for(int i=0;i<ret.size();i++){
				Object []obj = (Object[]) ret.get(i);
				TreeNode treeNode = new TreeNode();
				treeNode.setId(obj[0].toString());
				treeNode.setPid(obj[2].toString());
				treeNode.setName(obj[1].toString());
				list.add(treeNode);
			}
		}
		//List ret=genericDao.getListWithVariableParasByHQL("BD.TREE.FETCHTRD.BYID",new Object[] { id });
		return list;
}
	
	/**
	 * 用户管理（部门岗位联动）
	 * 
	 * @return
	 */

	public List<TreeNode> funTreeDataByIdAndParentId( String id,String parentId){
		List list=new ArrayList();
		List ret=genericDao.getListWithNativeSql("BD.TREE.FETCHTRD.BYIDANDPARENTID", new Object[] { id,parentId });
		if(ret.size() > 0){
			for(int i=0;i<ret.size();i++){
				Object []obj = (Object[]) ret.get(i);
				TreeNode treeNode = new TreeNode();
				treeNode.setId(obj[0].toString());
				treeNode.setPid(obj[2].toString());
				treeNode.setName(obj[1].toString());
				list.add(treeNode);
			}
		}
		//List ret=genericDao.getListWithVariableParasByHQL("BD.TREE.FETCHTRD.BYID",new Object[] { id });
		return list;
}
	
	/**
	 * 人员管理（功能授权之功能菜单）
	 * 
	 * @return
	 */

	public List<ObjStructure> fetchAccessControlOSList(){
		List<ObjStructure> ret = genericDao.getListDefault(
				"BD.OBJSTRUCTURE.ACCESSCONTRL.LIST");
		return ret;
}

	/**
	 * 功能菜单下的具体菜单授权
	 * 
	 * @param userId
	 * @return
	 */
	public ResponseBase funTreeSelectedDataForUser(String userId) {
		ResponseBase res = new ResponseBase();
		StringBuffer json = new StringBuffer();
		// json.append("{dataset:{response:{content:'操作成功。',success:'true'},selectedNodes:'");
		json.append(securityService.getResIdStringByUserIdAndType(userId));
		// json.append("'}}");
		res.setDataset(json, "tree");
		return res;
	}

	/**
	 * 功能菜单下的具体菜单授权
	 * 
	 * @param roleId
	 * @return
	 */
	public ResponseBase funTreeSelectedData(String roleId) {
		 //roleId="40288c715e9e6263015e9e657bd40001";
		ResponseBase res = new ResponseBase();
		StringBuffer json = new StringBuffer();
		// json.append("{dataset:{response:{content:'操作成功。',success:'true'},selectedNodes:'");
		json.append(securityService.getResIdStringByRoleIdAndType(roleId));
		// json.append("'}}");
		res.setDataset(json, "tree");
		return res;
	}

	// *****************************结构维护***********************************
	/**
	 * 查询指定条件的结构列表
	 * 
	 * @param code
	 *            : 编码
	 * @param name
	 *            : 名称
	 * @param sysFlag
	 *            : 系统标示符
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public PaginationSupport fetchObjStructureList(ObjStructure obj) {
		String code = obj.getCode();
		String name = obj.getName();
		code = (StringUtils.isBlank(code) ? code : "%" + code + "%");
		name = (StringUtils.isBlank(name) ? name : "%" + name + "%");
		Object[] paras = new Object[] { code, name };
		PaginationSupport ps = genericDao.getPageWithParams(
				"BD.OBJSTRUCTURE.LIST", paras, obj);
		if (ps.getItems() != null) {
			List<ObjStructure> list = (List<ObjStructure>) ps.getItems();
			for (ObjStructure os : list) {
				if (!StringUtils.isBlank(os.getAccessControlId()))
					os.setAccessControlName(dicService.getDicName(os
							.getAccessControlId()));
			}
		}
		return ps;
	}

	/**
	 * 返回此结构所有的对象关系类型
	 * 
	 * @param id
	 *            : 结构主键
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ResponseBase fetchObjStructureSub(String id) {
		ResponseBase res = new ResponseBase();
		// 校验参数
		Utility.raiseIfWrong(id, "id不能为空");

		List<ObjEntityRef> oerList = genericDao.getListWithVariableParas(
				"BD.OBJSTRUCTURE.FETCHOBJSTRUCTURESUB", new Object[] { id });
		for (ObjEntityRef oer : oerList) {
			oer.setChildName(dicService.getBaseDicName(oer.getChildId()));
			oer.setParentName(dicService.getBaseDicName(oer.getParentId()));
		}
		res.setDataset(oerList, "rows");
		return res;
	}
	
	@SuppressWarnings("unchecked")
	public ResponseBase fetchObjStructureRelation(String id) {
		ResponseBase res = new ResponseBase();
		// 校验参数
		Utility.raiseIfWrong(id, "id不能为空");
		List list = new ArrayList();

		List<ObjEntityRef> oerList = genericDao.getListWithVariableParas(
				"BD.OBJSTRUCTURE.FETCHOBJSTRUCTURESUB", new Object[] { id });
		for (ObjEntityRef oer : oerList) {
			TreeNode node = new TreeNode();
			node.setId(oer.getChildId());
			node.setName(dicService.getBaseDicName(oer.getParentId()));
			node.setPid(oer.getParentId());
			oer.setChildName(dicService.getBaseDicName(oer.getChildId()));
			oer.setParentName(dicService.getBaseDicName(oer.getParentId()));
			list.add(node);
		}
		res.setDataset(list, "rows");
		return res;
	}
	
	@SuppressWarnings("unchecked")
	public ResponseBase fetchRelationTree(String id) {
		ResponseBase res = new ResponseBase();
		List ret = new ArrayList();
		List<ObjEntityRef> oers = fetchObjStructure(id);
		HashSet<String> pIds = new HashSet<String>();
		HashSet<String> cIds = new HashSet<String>();
		for (ObjEntityRef oer : oers) {
			pIds.add(oer.getParentId());
			if(!oer.getParentId().equals(oer.getChildId()))
				cIds.add(oer.getChildId());
		}
		pIds.removeAll(cIds);
		
		for (Iterator<String> iterator = pIds.iterator(); iterator.hasNext();) {
			String pId = iterator.next();
			ret.add(createObjBaseView(pId, oers, null, null));
		}
		res.setDataset(ret, "rows");
		return res;
	}
	
	public List<ObjEntityRef> fetchObjStructure(String id) {
		// 校验参数
		Utility.raiseIfWrong(id, "id不能为空");
		
		List<ObjEntityRef> oerList = genericDao.getListWithVariableParas("BD.OBJSTRUCTURE.FETCHOBJSTRUCTURESUB", new Object[]{id});
		for (ObjEntityRef oer : oerList) {
			oer.setChildName(dicService.getBaseDicName(oer.getChildId()));
			oer.setParentName(dicService.getBaseDicName(oer.getParentId()));
		}
		return oerList;
	}
	
	private ObjBaseView createObjBaseView(String id, List<ObjEntityRef> oers,
			String grandFather, String objEntityRefId) {
		ObjBaseView obv = null;
		ObjBase data = (ObjBase) genericDao.getById(ObjBase.class, id);
		if (data != null) {
			obv = new ObjBaseView();
			obv.setId(data.getId());
			obv.setCode(data.getCode());
			obv.setName(data.getName());
			if(objEntityRefId != null) obv.setObjEntityRefId(objEntityRefId);
			for (Iterator<ObjEntityRef> iterator = oers.iterator(); iterator
					.hasNext();) {
				ObjEntityRef oer = iterator.next();
				if (oer.getParentId().equals(id) && 
					!oer.getChildId().equals(grandFather)) {
					obv.getChildren().add(
							createObjBaseView(oer.getChildId(), oers, id, oer.getId()));
				}
			}
		}
		return obv;
	}
	
	/**
	 * 删除选定的结构数据
	 * 
	 * @param ids
	 */
	@Transactional
	public void deleteObjStructure(String ids) {
		if (StringUtils.isNotBlank(ids)) {
			String[] arr = ids.split(Constants.SEPARATOR_COMMA);
			for (String id : arr) {
				ObjStructure os = (ObjStructure) genericDao.getById(ObjStructure.class, id);
				os.setSysFlag(0);
				genericDao.save(os);
			}
		}
	}
	/**
	 * 保存结构数据
	 * @param obj
	 * @param ids
	 */
	@Transactional
	public void saveObjStructure(ObjStructure obj,String ids) {
		if(StringUtils.isBlank(obj.getId())){
			obj.setId(null);
		}
		obj.setSysFlag(1);
		genericDao.save(obj);
		if(StringUtils.isNotBlank(obj.getId())){
			List<ObjStructureRef> list = genericDao.getListWithVariableParas("BD.OBJSTRUCTURE.FETCHOBJSTRUCTURELIST", new Object[] { obj.getId() });
			if(list.size() > 0){
				String sql = "DELETE FROM t_pub_obj_structure_ref WHERE F_STRUCTURE_PID = '"+obj.getId()+"'";
				genericDao.executeNativeSql(sql);
			}
		}
		if (StringUtils.isNotBlank(ids)) {
			String[] arr = ids.split(Constants.SEPARATOR_COMMA);
			for (String id : arr) {
				ObjStructureRef os = new ObjStructureRef();
				os.setStructureId(obj.getId());
				os.setObjRefId(id);
				genericDao.save(os);
			}
		}
	}
	
	public ResponseBase getObjStructure(String id) {
		ResponseBase res = new ResponseBase();
		ObjStructure ref = (ObjStructure) genericDao.getById(ObjStructure.class, id);
		List<ObjEntityRef> oerList = genericDao.getListWithVariableParas("BD.OBJSTRUCTURE.FETCHOBJSTRUCTURESUB", new Object[] { id });
		for (ObjEntityRef oer : oerList) {
			oer.setChildName(dicService.getBaseDicName(oer.getChildId()));
			oer.setParentName(dicService.getBaseDicName(oer.getParentId())); 
		}
		ref.setChildren(oerList);
		res.setDataset(ref, "objstructure");
		return res;
	}
	
	/**
	 * @param struId
	 * @param typeId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ResponseBase fetchDicForAddMaintain(String struId, String typeId, String parentId, String name){
		ResponseBase res = new ResponseBase();
		Pagination ps = null;
		
		name = StringUtils.isBlank(name) ? "%%" : "%" + name + "%";
		List list = genericDao.getListWithNativeSql("BD.DIC.FETCHDICFORADDMAINTAIN", 
				new Object[]{typeId, struId, typeId, struId, typeId, name});
		if(list.size() > 0){
			List<Dic> ret = new ArrayList<Dic>();
			for (Object temp : list) {
				Object[] values = (Object[])temp;
				Dic d = new Dic();
				d.setId(values[0].toString());
				d.setCode(values[1].toString());
				d.setName(values[2].toString());
				d.setFullName((String)values[3]);
				d.setObjEntityRefId(struId); //t_pub_typerefdic F_OBJ_REF_PID
				d.setParentId(parentId);
				ret.add(d);
			}
			res.setDataset(ret, "rows");
		}else{
			List list1 = genericDao.getListWithNativeSql("BD.DIC.FETCHDICFORADDMAINTAIN", 
					new Object[]{parentId, struId, parentId, struId, parentId, name});
			List<Dic> ret = new ArrayList<Dic>();
			for (Object temp : list1) {
				Object[] values = (Object[])temp;
				Dic d = new Dic();
				d.setId(values[0].toString());
				d.setCode(values[1].toString());
				d.setName(values[2].toString());
				d.setFullName((String)values[3]);
				d.setObjEntityRefId(struId); //t_pub_typerefdic F_OBJ_REF_PID
				d.setParentId(parentId);
				ret.add(d);
			}
			res.setDataset(ret, "rows");
		}
		//res.setDataset(ret, "rows");
		return res;
	}
	
	/**
	 * 获取已配置按钮
	 * @param structId
	 * @param funId
	 * @return
	 */
	public List<Dic> fetchFuncDicList(String structId,String funId) {
		String queryName = "DB.DIC.FETCHDICDETAILFORMAINTAIN3"; //父节点
		List<Dic> dicList = genericDao.getListWithVariableParas(queryName, new Object[]{funId,structId,null,null});
		return dicList;
	}
	
	public List<Dic> fetchFuncDicListByUser(String structId,String funId) {
		String queryName = "DB.DIC.FETCHDICDETAILFORMAINTAIN3"; //父节点
		List<Dic> dicList = genericDao.getListWithVariableParas(queryName, new Object[]{funId,structId,null,null});
		return dicList;
	}
	/**
	 * 保存已有项
	 * @param fullJson
	 */
	@Transactional
	public void saveExistTypeRefDic(String childId,String typeId,String objEntityRefId,String parentId) {
//		JSONArray jsonArray = JSONArray.fromObject(fullJson);
//		for (int i = 0; i < jsonArray.size(); i++) {
//			JSONObject jsonObject = jsonArray.getJSONObject(i);
			TypeRefDic trd = new TypeRefDic();
//			String objRefPid = jsonObject.get("objEntityRefId").toString();
//			String parentId = jsonObject.get("parentId").toString();
//			String childId = jsonObject.get("childId").toString();
			trd.setObjEntityRefId(objEntityRefId);
			trd.setParentId(parentId);
			trd.setChildId(childId);
			trd.setSysFlag(1);
			trd.setCreateTime(DateBean.getSysdateTime());
			genericDao.save(trd);
//		}
	}
	/**
	 * 添加已有项类型选择combox
	 */
	public List<DicView> fetchObjEntityRefList(String structId,String parentId) {
		Object []obj = { structId,parentId };
		if(!StingUtil.isEmpty(structId) && !StingUtil.isEmpty(parentId)){
			List<Object[]> list = genericDao.getListWithNativeSql("BD.DIC.FETCHOBJENTITYREFLIST",obj);
			List<DicView> result =dicViewService.DataToDicView(list);
			return result;
		}
		return null;
	}
	/*
	 *判断数据库是否存在该code or name 
	 */
	public Boolean isExist(ObjStructure os) {
		String sql="select count(*) from t_pub_obj_structure t where t.f_struct_code = ? and t.f_struct_name = ?";
		Object [] params= {os.getCode(),os.getName()};
		BigInteger o =(BigInteger) genericDao.getUniqueResult(sql, params);
		if(o==null || o.intValue()<1) {
			return true;
		}
		//在这里借用id这个字段,因为在更新时，可能这个字段以存在
		String id=os.getId();
		if(StringUtils.isNotBlank(id) &&o.intValue()==1) {
			ObjStructure d=(ObjStructure) genericDao.getById(ObjStructure.class, id);
			if(d!=null && StringUtils.isNotBlank(d.getName()) && d.getName().equals(os.getName())) {
				return true;
			}
			if(d!=null && StringUtils.isNotBlank(d.getCode()) && d.getCode().equals(os.getCode())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 校验字段，然后返回值result；
	 * @param ob
	 * @return
	 */
	public Map<String,Object> validCodeAndName(ObjStructure obj) {
		ObjStructure ob1=new ObjStructure();
		ob1.setId(obj.getId());
		ob1.setCode(obj.getCode());
		Map<String, Object> result = ResultUtil.ErrorResult();
		Boolean flag=true;
		if(!this.isExist(ob1)) {
			result.put(Constants.MESSAGE, "该code已存在");
			flag=false;
			
		}
		ob1.setCode(null);
		ob1.setName(obj.getName());
		if(!this.isExist(ob1)) {
			if(flag) {
				flag=false;
				result.put(Constants.MESSAGE, "该名称已存在");
			}
			else {
				result.put(Constants.MESSAGE, "该编号和名称已存在");
			}
		}
		if(flag) {
			return ResultUtil.DefaultResult();
		}else {
			return result;
		}
	}
	

	/*
	 * public List<ObjEntityRef> fetchObjStructureSub(String id) {
	 * Utility.raiseIfWrong(id, "id不能为空");
	 * 
	 * List<ObjEntityRef> oerList =
	 * genericDao.getListWithVariableParas("BD.OBJSTRUCTURE.FETCHOBJSTRUCTURESUB"
	 * , new Object[]{id}); for (ObjEntityRef oer : oerList) {
	 * oer.setChildName(getDataName(PubConstants.OBJBASETYPECODE,
	 * oer.getChildId()));
	 * oer.setParentName(getDataName(PubConstants.OBJBASETYPECODE,
	 * oer.getParentId())); } return oerList; }
	 */
}
