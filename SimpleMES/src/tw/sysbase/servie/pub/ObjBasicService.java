package tw.sysbase.servie.pub;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.sysbase.dao.GenericDao;
import tw.sysbase.entity.pub.ObjAttribute;
import tw.sysbase.entity.pub.ObjBase;
import tw.sysbase.entity.pub.TreeNode;

/**
 * @author mars
 *
 */
@Service
@Transactional
public class ObjBasicService {
	@Resource
	private GenericDao genericDao;
	@Autowired
	private DicService dicService;
	
	private static final char PREFIX = '{';    //前缀
	private static final char SUFFIX = '}';    //后缀
	private static final char DELIMITER = ':'; //分割
	//
	private static final String BASIC = "basic"; //基础类型
	private static final String SET = "set"; //set集合
	private static final String LIST = "list"; //list集合
	private static final String CUSTOM = "custom"; //自定义对象
	
	private StringBuilder sb;
	private int maxLevel;
	
	/**
	 * @param id
	 * @param maxLevel
	 * @return
	 */
	public String buildJsonObjView(String id, int maxLevel){
		//取得对象结构
		ObjBase obj = (ObjBase)genericDao.getById(ObjBase.class, id);
		if(obj == null) return "";
		//最大对象结构深度
		if(maxLevel < 1) maxLevel = 1;
		this.maxLevel = maxLevel;
		//构建JSON
		this.sb = new StringBuilder();
		sb.append(PREFIX);
		addComment(obj.getName());
		sb.append(obj.getClassName());
		sb.append(DELIMITER);
		buildJsonObj(obj,1);
		sb.append(SUFFIX);
		return this.sb.toString();
	}
	
	/**
	 * @param obj
	 * @param level
	 */
	private void buildJsonObj(ObjBase obj, int level){
		sb.append(PREFIX);
		boolean isFirst = true;
		while(obj != null) {
			for (ObjAttribute property : obj.getProperties()) {
				if(!isFirst) sb.append(',');
				if(isFirst) isFirst = false;
				//
				String typeCate = property.getObjType().getTypeCate();
				if(typeCate.equals(BASIC)){
					addComment(property.getName());
					sb.append(property.getCode());
					sb.append(DELIMITER);
					sb.append("\"");
					sb.append(property.getObjType().getDefaultValue());
					sb.append("\"");
				}
				else if((typeCate.equals(SET) || typeCate.equals(LIST))
						&& level <= maxLevel) {
					addComment(property.getName());
					sb.append(property.getCode());
					sb.append(DELIMITER);
					sb.append(PREFIX);
					sb.append(property.getObj().getClassName());
					sb.append(DELIMITER);
					sb.append("[");
					int length = property.getObjType().getDefaultLength();
					for(int i = 0 ; i < length; i++){
						if(i > 0) sb.append(",");
						buildJsonObj(property.getObj(), ++level);
					}
					sb.append("]");
					sb.append(SUFFIX);
				}
				else if(typeCate.equals(CUSTOM) && level <= maxLevel) {
					addComment(property.getName());
					sb.append(property.getCode());
					sb.append(DELIMITER);
					buildJsonObj(property.getObj(), ++level);
				}
				else{}
			}
			if(obj.getSuperClass() != null) {
				obj = obj.getSuperClass();
			}
			else {
				obj = null;
			}
		}
		sb.append(SUFFIX);
	}
	
	/**
	 * 添加注释块
	 * @param comment注释内容
	 */
	private void addComment(String comment){
		sb.append("/*");
		sb.append(comment);
		sb.append("*/");
	}
	
	/******************************************************************************/
	/**
	 * @param id
	 * @return
	 */
	/*public TypeVo fetchObjBaseViewDetail(String id){
		TypeVo ret = null;
		ObjBase ob = (ObjBase) genericDao.getById(ObjBase.class, id);
		if(ob != null){
			ret = new TypeVo();
			ret.setId(id);
			ret.setCode(ob.getCode());
			ret.setName(ob.getName());
			
			while(ob != null){
				Set<ObjAttribute> pros = ob.getProperties();
				for (Iterator<ObjAttribute> iterator = pros.iterator(); iterator.hasNext();) {
					ObjAttribute attr = iterator.next();
					ret.getProperties().add(0, buildPropertyVo(attr));
					
				}
				ob = ob.getSuperClass();
			}
		}
		return ret;
	}
	
	private PropertyVo buildPropertyVo(ObjAttribute attr){
		PropertyVo pv = new PropertyVo();
		pv.setId(attr.getId());
		pv.setName(attr.getName());
		pv.setColumn(attr.getColumn());
		pv.setPkFlag(attr.getPkFlag());
		pv.setPkFlagName(dicManager
				.getDataName("IsPrimerKey", attr.getPkFlag().toString()));
		pv.setOrder(attr.getOrder());
		pv.setCateId(attr.getObjType().getId());
		pv.setCateCode(attr.getObjType().getCode());
		pv.setCateName(attr.getObjType().getName());
		pv.setAllowBlank(attr.getAllowBlank());
		pv.setAllowBlankName(dicManager
				.getDataName("AllowBlank", (String)attr.getAllowBlank().toString()));
		pv.setVerifyRule(attr.getVerifyRule());		
		pv.setSysFlag(attr.getSysFlag());
		return pv;
	}
	
	*//**
	 * @param id    ObjBase主键
	 * @param flag  0表示查询, 其它表示编辑状态
	 * @return
	 *//*
	public OBTypeVo fetchOBTypeVoDetail(String id, String flag){
		OBTypeVo ret = null;
		ObjBase ob = (ObjBase) genericDao.getById(ObjBase.class, id);
		if(ob != null){
			ret = new OBTypeVo();
			if(!flag.equals("0")){
				//主表信息
				ret.setId(id);
				ret.setCode(ob.getCode());
				ret.setName(ob.getName());
				
				//非继承属性
				Set<ObjAttribute> pros = ob.getProperties();
				for (Iterator<ObjAttribute> iterator = pros.iterator(); iterator.hasNext();) {
					ObjAttribute attr = iterator.next();
					ret.getPros().add(0, buildPropertyVo(attr));
				}
				//继承属性
				ob = ob.getSuperClass();
				if(ob != null) ret.setSuperClassId(ob.getId());
				while(ob != null){
					Set<ObjAttribute> inherPros = ob.getProperties();
					for (Iterator<ObjAttribute> iterator = inherPros.iterator(); iterator.hasNext();) {
						ObjAttribute attr = iterator.next();
						ret.getInherPros().add(0, buildPropertyVo(attr));
					}
					ob = ob.getSuperClass();
				}
			}
			else {
				ret.setSuperClassId(id);
				//继承属性
				while(ob != null){
					Set<ObjAttribute> inherPros = ob.getProperties();
					for (Iterator<ObjAttribute> iterator = inherPros.iterator(); iterator.hasNext();) {
						ObjAttribute attr = iterator.next();
						ret.getInherPros().add(0, buildPropertyVo(attr));
					}
					ob = ob.getSuperClass();
				}
			}
		}
		return ret;
	}
	
	public OBTypeVo saveOBTypeVo(OBTypeVo obv){
		Utility.raiseIfWrong(obv, "obv不能为空");
		ObjBase ob = new ObjBase();
		ob.setId(obv.getId());
		ob.setCode(obv.getCode());
		ob.setName(obv.getName());
		ob.setSysFlag(1);
		if(!StringUtils.isBlank(obv.getSuperClassId())){
			ob.setSuperClass((ObjBase)genericDao.getById(
					ObjBase.class, obv.getSuperClassId()));
		}
		//
		if(obv.getPros() != null){
			Set<ObjAttribute> pros = new HashSet<ObjAttribute>();
			List<PropertyVo> vpros = obv.getPros();
			for (PropertyVo vp : vpros) {
				ObjAttribute oa = new ObjAttribute();
				oa.setId(vp.getId());
				oa.setName(vp.getName());
				oa.setColumn(vp.getColumn());
				oa.setOrder(vp.getOrder());
				oa.setPkFlag(vp.getPkFlag());
				oa.setAllowBlank(vp.getAllowBlank());
				oa.setVerifyRule(vp.getVerifyRule());
				oa.setObj(ob);
				oa.setObjType((ObjType)genericDao.getById(
						ObjType.class, vp.getCateId()));
				oa.setSysFlag(1);
				pros.add(oa);
			}
			ob.setProperties(pros);
		}
		//
		genericDao.save(ob);
		//
		HqlFetcher fetcher = new HqlFetcher(this);
		fetcher.setFindCacheFirst(false);
		fetcher.setKey(PubConstants.OBJBASETYPECODE);
		fetcher.getData();
		//
		obv.setId(ob.getId());
		return obv;
	}
	
	public void deleteObjBase (String ids){
		//校验参数
		Utility.raiseIfWrong(ids, "ids不能为空");
		
		String[] pids = ids.split(PubConstants.SEPARATOR);
		for (String id : pids) {
			ObjBase ob = (ObjBase) genericDao.getById(ObjBase.class, id);
			ob.setSysFlag(0);
			genericDao.save(ob);
		}
	}*/
	
	/**
	 * 基础类型关系维护TREE
	 * @param superClassPid
	 * @return
	 */
	/*public List fetchObjBaseTree(String superClassPid){
		String queryName = "BD.TREE.FETCHTRD.BASE";
		List list = new ArrayList();
		List listNode = genericDao.getListWithNativeSql(queryName, new Object[]{superClassPid});
		if(listNode.size() > 0){
			for(int i=0;i<listNode.size();i++){
				Object []obj = (Object[]) listNode.get(i);
				TreeNode treeNode = new TreeNode();
				treeNode.setId(obj[0].toString());
				treeNode.setName(obj[1].toString());
				treeNode.setParentId(obj[2].toString());
				treeNode.setParentName(obj[3].toString());
				list.add(treeNode);
			}
		}
		return list;
	}*/
}
