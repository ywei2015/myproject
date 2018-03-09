package tw.sysbase.servie.pub;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.business.entity.pub.DicView;
import tw.business.service.equ.DicViewService;
import tw.sysbase.dao.GenericDao;
import tw.sysbase.dao.PaginationSupport;
import tw.sysbase.entity.Pagination;
import tw.sysbase.entity.pub.OBTypeVo;
import tw.sysbase.entity.pub.ObjAttribute;
import tw.sysbase.entity.pub.ObjAttributeVo;
import tw.sysbase.entity.pub.ObjBase;
import tw.sysbase.entity.pub.ObjEntityRef;
import tw.sysbase.entity.pub.ObjType;
import tw.sysbase.entity.pub.PropertyVo;
import tw.sysbase.pub.Constants;
import tw.sysbase.pub.DateBean;
import tw.sysbase.pub.ResponseBase;
import tw.sysbase.pub.ResultUtil;
import tw.sysbase.pub.StingUtil;
import tw.sysbase.servie.sec.SecurityService;

@Service
@Transactional(readOnly=false)
public class ObjBaseService  {
	@Resource
	private GenericDao genericDao;
	@Autowired
	private SecurityService securityService;
	@Autowired
	private DicService dicService;
	@Autowired
	private DicViewService dicViewService;
	
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
		Object[] parameter ={id,pid,1};
		@SuppressWarnings("unchecked")
		List<ObjBase> objBases = genericDao.getListWithVariableParas("OBJBASE.GETLIST.GET_BYPID", parameter);
		//格式转换
		
		for(int i=0;i<objBases.size();i++){
			ObjBase base=objBases.get(i);
			pid=base.getId();
			List<ObjBase> sonbases = genericDao.getListWithVariableParas("OBJBASE.GETLIST.GET_BYPID",new Object[]{null,pid,null} );	
			if(sonbases!=null && !sonbases.isEmpty()){
				base.SetIsParent(true);
			}else{
				/*base.setTreeNode(false);*/
			}
		}
		return objBases;
	}
	
	public Pagination getListByCondition(ObjBase objBase) {
		String code=objBase.getCode();
		String name=objBase.getName();
		code = (StringUtils.isBlank(code) ? code : "%" + code + "%");
		name = (StringUtils.isBlank(name) ? name : "%" + name + "%");
		String pid= objBase.getId();
		Object[] paras ={code,name,pid};
		@SuppressWarnings("unchecked")
		PaginationSupport paginationSupport = genericDao.getPageWithParams(
				"OBJBASE.GETLIST.GET_BYPCONDITION", paras,objBase);
		//格式转换"OBJBASE.GETLIST.GET_BYPCONDITION", paras
		/*List<ObjBase>objBases =genericDao.getPageWithList("select  new tw.sysbase.entity.pub.ObjBase(id, code ,name,sysFlag,remark) from tw.sysbase.entity.pub.ObjBase t"
		+" where t.sysFlag = '1' and  t.code like ?    and t.name like ?   and t.superClass.id=?", paras,objBase);*/
		/*Long total = (Long) genericDao.getUniqueResult("select count(t.id) from tw.sysbase.entity.pub.ObjBase t"
		+" where t.sysFlag = '1'	  and  t.code like ?   and t.name like ?  "
		+ " and t.superClass.id=?", paras);*/
		Pagination pagination= Pagination.init(paginationSupport);
		//pagination.setRows(objBases);
		//pagination.setTotal(total.intValue());
		return pagination;
	}

	/**
	 * 如果是更新，先查询，后插入
	 * @param t
	 * @return
	 * @throws Exception 
	 */
	public Object save(ObjBase t) throws Exception {
	
		if(StringUtils.isNotBlank(t.getId())){
			genericDao.save(t);
		}else {
			t.setId(null);
			genericDao.save(t);
		}
		
		return t;
	}
	/**
	 * @Description:分页获取数据
	 * @author: zw
	 */
	public PaginationSupport findListPagination(ObjBase ob) {
		String code=ob.getCode();
		String name=ob.getName();
		code = (StringUtils.isBlank(code) ? code : "%" + code + "%");
		name = (StringUtils.isBlank(name) ? name : "%" + name + "%");
		String pid= ob.getId();
		Object[] paras ={code,name,pid};
		//genericDao.getPageWithVariableParas(queryName, value)
		PaginationSupport paginationSupport = genericDao.getPageWithParams("OBJBASE.FINDLISTPAGINATION", paras, ob);
		return paginationSupport;
	}
	/**
	 * @Description: 根据id删除采用逗号分隔
	 * @author: zw
	 */

	public void deletes(String ids) {
		if(StringUtils.isNotBlank(ids)){
			String[]arr=ids.split(Constants.SEPARATOR_COMMA);
			for(String id:arr){
				ObjBase ObjBase = (ObjBase) genericDao.getById(ObjBase.class, id);
				ObjBase.setSysFlag(0);
				 genericDao.save(ObjBase);
			}
		}
		
	}

	public ObjBase get(ObjBase ob) {
		/*Object [] objs = { ob.getId() };
		List<ObjBase> oba = genericDao.getListWithVariableParas("OBJ.OBJBASE.BYID", objs);
		return oba.get(0);*/
		return (ObjBase) genericDao.getById(ObjBase.class, ob.getId());
	}
	/**
	 * 校验字段，然后返回值result；
	 * @param ob
	 * @return
	 */
	public Map<String,Object> validCodeAndName(ObjBase ob) {
		ObjBase ob1=new ObjBase();
		ob1.setId(ob.getId());
		ob1.setCode(ob.getCode());
		Map<String, Object> result = ResultUtil.ErrorResult();
		Boolean flag=true;
		if(!this.isExist(ob1)) {
			result.put(Constants.MESSAGE, "该code已存在");
			flag=false;
			
		}
		ob1.setCode(null);
		ob1.setName(ob.getName());
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
	 *判断数据库是否存在该code or name 
	 */
	public Boolean isExist(ObjBase ob) {
		String sql="select count(*) from t_pub_obj_base t where t.f_obj_code = ? and t.f_obj_name = ?";
		Object [] params= {ob.getCode(),ob.getName()};
		BigInteger o =(BigInteger) genericDao.getUniqueResult(sql, params);
		if(o==null || o.intValue()<1) {
			return true;
		}
		//在这里借用id这个字段,因为在更新时，可能这个字段以存在
		String id=ob.getId();
		if(StringUtils.isNotBlank(id) &&o.intValue()==1) {
			ObjBase objBase=(ObjBase) genericDao.getById(ObjBase.class, id);
			if(objBase!=null && StringUtils.isNotBlank(objBase.getName()) && objBase.getName().equals(ob.getName())) {
				return true;
			}
			if(objBase!=null && StringUtils.isNotBlank(objBase.getCode()) && objBase.getCode().equals(ob.getCode())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param id    ObjBase主键
	 * @param flag  0表示查询, 其它表示编辑状态
	 * @return
	 */
	public ResponseBase fetchOBTypeVoDetail(String id, String flag){
		OBTypeVo ret = null;
		ResponseBase res = new ResponseBase();
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
					if(attr.getSysFlag() == 1){
						ret.getPros().add(0, buildPropertyVo(attr));
					}
				}
				//继承属性
				ob = ob.getSuperClass();
				if(ob != null) ret.setSuperClassId(ob.getId());
				while(ob != null){
					Set<ObjAttribute> inherPros = ob.getProperties();
					for (Iterator<ObjAttribute> iterator = inherPros.iterator(); iterator.hasNext();) {
						ObjAttribute attr = iterator.next();
						if(attr.getSysFlag() == 1){
							ret.getInherPros().add(0, buildPropertyVo(attr));
						}
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
						if(attr.getSysFlag() == 1){
							ret.getInherPros().add(0, buildPropertyVo(attr));
						}
					}
					ob = ob.getSuperClass();
				}
			}
		}
		res.setDataset(ret, "rows");
		return res;
	}
	private PropertyVo buildPropertyVo(ObjAttribute attr){
		PropertyVo pv = new PropertyVo();
		pv.setId(attr.getId());
		pv.setName(attr.getName());
		pv.setColumn(attr.getColumn());
		pv.setPkFlag(attr.getPkFlag());
		/*pv.setPkFlagName(dicManager
				.getDataName("IsPrimerKey", attr.getPkFlag().toString()));*/
		pv.setOrder(attr.getOrder());
		pv.setCateId(attr.getObjType().getId());
		pv.setCateCode(attr.getObjType().getCode());
		pv.setCateName(attr.getObjType().getName());
		pv.setAllowBlank(attr.getAllowBlank());
		pv.setAllowBlankName(dicService.getDicName(attr.getAllowBlank().toString()));
		pv.setVerifyRule(attr.getVerifyRule());		
		pv.setSysFlag(attr.getSysFlag());
		return pv;
	}
	
	/**
	 * 字段类型选择combox
	 */
	public List<DicView> fetchObjTypeCombox() {
		List<Object[]> list = genericDao.getListWithNativeSql("BD.OBJTYPE.SELECT",new Object[]{});
		List<DicView> result =dicViewService.DataToDicView(list);
		return result;
	}
	
	/**
	 * 扩展字段选择combox
	 */
	public List<DicView> fetchObjattributeCombox() {
		List<Object[]> list = genericDao.getListWithNativeSql("BD.OBJATTRIBUTE.SELECT",new Object[]{});
		List<DicView> result =dicViewService.DataToDicView(list);
		return result;
	}
	
	/**
	 * 保存派生类型
	 * @param listData
	 * @param formData
	 */
	@Transactional
	public void saveOBTypeVo(String listData,String formData){
		JSONObject object = JSONObject.fromObject(formData);
		ObjBase ob = new ObjBase();
		ob.setId(null);
		ob.setCode(object.getString("code"));
		ob.setName(object.getString("name"));
		ob.setSysFlag(1);
		ob.setCreateTime(DateBean.getSysdateTime());
		if(!StringUtils.isBlank(object.getString("id"))){
			ob.setSuperClass((ObjBase)genericDao.getById(
					ObjBase.class, object.getString("id")));
		}
		JSONArray jsonArray = JSONArray.fromObject(listData);
		if(jsonArray.size() > 0){
			for(int i = 0; i < jsonArray.size(); i++){
				Object obj = jsonArray.get(i);
				JSONObject jsonObject = JSONObject.fromObject(obj);
				ObjAttribute oa = new ObjAttribute();
				oa.setId(null);
				oa.setName(jsonObject.getString("name"));
				oa.setColumn(jsonObject.getString("column"));
				oa.setOrder(Integer.parseInt(jsonObject.getString("order")));
				oa.setPkFlag(Integer.parseInt(jsonObject.getString("pkFlag")));
				oa.setAllowBlank(Integer.parseInt(jsonObject.getString("allowBlank")));
				oa.setVerifyRule(jsonObject.getString("verifyRule")==null?"":jsonObject.getString("verifyRule"));
				oa.setObj(ob);
				oa.setObjType((ObjType)genericDao.getById(
						ObjType.class, jsonObject.getString("cateId")));
				oa.setSysFlag(1);
				oa.setCreateTime(DateBean.getSysdateTime());
				genericDao.save(oa);
			}
		}
		genericDao.save(ob);
	}
	
	/**
	 * 编辑类型
	 * @param listData
	 * @param formData
	 */
	@Transactional
	public void updateOBTypeVo(String listData,String formData){
		JSONObject object = JSONObject.fromObject(formData);
		ObjBase ob = (ObjBase) genericDao.getById(ObjBase.class, object.getString("id"));
		ob.setCode(object.getString("code"));
		ob.setName(object.getString("name"));
		if(!StringUtils.isBlank(object.getString("id"))){
			ob.setSuperClass((ObjBase)genericDao.getById(
					ObjBase.class, object.getString("id")));
		}
		List list = new ArrayList();
		Set<ObjAttribute> pros = ob.getProperties();
		JSONArray jsonArray = JSONArray.fromObject(listData);
		if(jsonArray.size() > 0){
			for(int i = 0; i < jsonArray.size(); i++){
				Object obj = jsonArray.get(i);
				JSONObject jsonObject = JSONObject.fromObject(obj);
				list.add(jsonObject.getString("id"));
				ObjAttribute attr = (ObjAttribute) genericDao.getById(ObjAttribute.class, jsonObject.getString("id"));
				if(!StingUtil.isEmpty(attr)){
					attr.setName(jsonObject.getString("name"));
					attr.setColumn(jsonObject.getString("column"));
					attr.setOrder(Integer.parseInt(jsonObject.getString("order")));
					attr.setPkFlag(Integer.parseInt(jsonObject.getString("pkFlag")));
					attr.setAllowBlank(Integer.parseInt(jsonObject.getString("allowBlank")));
					attr.setVerifyRule(jsonObject.getString("verifyRule")==null?"":jsonObject.getString("verifyRule"));
					attr.setObj(ob);
					attr.setObjType((ObjType)genericDao.getById(
							ObjType.class, jsonObject.getString("cateId")));
					genericDao.save(attr);
				}else{
					ObjAttribute oa = new ObjAttribute();
					oa.setId(null);
					oa.setName(jsonObject.getString("name"));
					oa.setSysFlag(1);
					oa.setColumn(jsonObject.getString("column"));
					oa.setOrder(Integer.parseInt(jsonObject.getString("order")));
					oa.setPkFlag(Integer.parseInt(jsonObject.getString("pkFlag")));
					oa.setAllowBlank(Integer.parseInt(jsonObject.getString("allowBlank")));
					oa.setVerifyRule(jsonObject.getString("verifyRule")==null?"":jsonObject.getString("verifyRule"));
					oa.setObj(ob);
					oa.setObjType((ObjType)genericDao.getById(
							ObjType.class, jsonObject.getString("cateId")));
					oa.setCreateTime(DateBean.getSysdateTime());
					genericDao.save(oa);
				}
			}
			for (Iterator<ObjAttribute> iterator = pros.iterator(); iterator.hasNext();) {
				ObjAttribute attr = iterator.next();
				if(!list.contains(attr.getId())){
					attr.setSysFlag(0);
					//ObjAttribute oae = genericDao.getById(ObjAttribute.class, attr.getId());
					genericDao.save(attr);
				}
			}
		}
		genericDao.save(ob);
	}
	
	/**
	 * 获取关联动态字段
	 */
	public ResponseBase fetchObjAttributeList(String objId){
		ResponseBase res = new ResponseBase();
		List list = genericDao.getListWithNativeSql("OBJ.OBJATTRIBUTE.LIST", new Object[]{ objId });
		List<ObjAttributeVo> oalist = new ArrayList<ObjAttributeVo>();
		for(int i = 0; i<list.size();i++){
			ObjAttributeVo oa = new ObjAttributeVo();
			Object [] obj = (Object[]) list.get(i);
			oa.setId(obj[0]==null?"":obj[0].toString());
			oa.setObjId(obj[1]==null?"":obj[1].toString());
			oa.setCode(obj[2]==null?"":obj[2].toString());
			oa.setName(obj[3]==null?"":obj[3].toString());
			oa.setSysFlag(Integer.parseInt(obj[4].toString()));
			oa.setColumn(obj[5]==null?"":obj[5].toString());
			oa.setPkFlag(Integer.parseInt(obj[6].toString()));
			oa.setClassType(obj[7]==null?"":obj[7].toString());
			oa.setOrder(Integer.parseInt(obj[8].toString()));
			oa.setAllowBlank(Integer.parseInt(obj[9].toString()));
			oa.setVerifyRule(obj[10]==null?"":obj[10].toString());
			oa.setCreateTime(obj[11]==null?"":obj[11].toString());
			oalist.add(oa);
		}
		res.setDataset(oalist, "objAttribute");
		return res;
	}
	
	/**
	 * 树子节点类型
	 */
	public ResponseBase fetchObjBaseList(String structId,String keyId){
		ResponseBase res = new ResponseBase();
		Object [] objs = {structId,keyId};
		List<ObjBase> list = genericDao.getList("BD.OBJBASERADIO.LIST", objs);
		res.setDataset(list, "objBase");
		return res;
	}

}
