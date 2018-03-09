package tw.sysbase.servie.pub;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.sysbase.dao.GenericDao;
import tw.sysbase.dao.PaginationSupport;
import tw.sysbase.entity.pub.Dic;
import tw.sysbase.entity.pub.ObjBase;
import tw.sysbase.entity.pub.ObjEntityRef;
import tw.sysbase.entity.pub.TypeRefDic;
import tw.sysbase.pub.Constants;
import tw.sysbase.pub.DateBean;
import tw.sysbase.pub.ResponseBase;
import tw.sysbase.pub.ResultUtil;
import tw.sysbase.pub.Utility;
import tw.sysbase.servie.sec.SecurityService;

/**
 * @author mars
 *
 */
@Service
@Transactional
public class DicService{
	@Resource
	private GenericDao genericDao;
	@Autowired  
	private HttpServletRequest request; 
	@Autowired
	private SecurityService securityService;
	/**
	 * 通过F_OBJ_PID查找相关基础数据列表
	 * @param objId
	 * @return
	 */
	public ResponseBase fetchDicList(String objId){
		ResponseBase res = new ResponseBase();
		String queryName = "BD.TREE.FETCHDIC.BY.OBJID";
		List<Dic> listDic = genericDao.getListWithVariableParas(queryName, new Object[]{ objId });
		res.setDataset(listDic,"dic");
		return res;
	}
	
	public String getDicName(String id) {
		Dic dic = (Dic)genericDao.getById(Dic.class, id);
		return dic == null ? null : dic.getName();
	}
	
	public String getBaseDicName(String id) {
		ObjBase base = (ObjBase)genericDao.getById(ObjBase.class, id);
		return base == null ? null : base.getName();
	}
	
	public ObjBase getBase(String id) {
		ObjBase base = (ObjBase)genericDao.getById(ObjBase.class, id);
		return base;
	}

	public PaginationSupport findListDicPagination(Dic dic) {
		String code=dic.getCode();
		String name=dic.getName();
		Integer sysFlag=dic.getSysFlag();
		code = (StringUtils.isBlank(code) ? code : "%" + code + "%");
		name = (StringUtils.isBlank(name) ? name : "%" + name + "%");
		String type = dic.getType();
		Object[] paras ={code,name,type,sysFlag};
		//genericDao.getPageWithVariableParas(queryName, value)
		PaginationSupport paginationSupport = genericDao.getPageWithParams("DIC.FINDLISTPAGINATION.GET_BYPID", paras,dic);
		return paginationSupport;
	}

	public Dic save(Dic dic) {
		if(StringUtils.isBlank(dic.getId())){
			dic.setId(null);
			dic.setCreator(securityService.getOperator());
			dic.setCreateTime(DateBean.getSysdateTime());
		}else{
			dic.setLastModifier(securityService.getOperator());
			dic.setLastModifiedTime(DateBean.getSysdateTime());
		}
		genericDao.save(dic);
		return dic;
	}

	public void delete(Dic dic) {
		genericDao.delete(dic);
		
	}

	public Dic getDic(String id) {
		return (Dic) genericDao.getById(Dic.class, id);
		
	}
	@Transactional
	public void deletes(String ids) {
		if(StringUtils.isNotBlank(ids)){
			String[]arr=ids.split(Constants.SEPARATOR_COMMA);
			for(String id:arr){
				 Dic dic = (Dic) genericDao.getById(Dic.class, id);
				 dic.setSysFlag(0);
				 dic.setSysFlagName(Constants.INVALID);
				 dic.setLastModifier(securityService.getOperator());
				 dic.setLastModifiedTime(DateBean.getSysdateTime());
				 genericDao.save(dic);
			}
		}
	}


	public Dic dic_ref_detail(String id,String structId) {
		Dic d =getDic(id);
		if(d != null){
			d = d.clone();
			@SuppressWarnings("unchecked")
			//id作为父节点
			List<TypeRefDic> list = genericDao.getListWithVariableParas("BD.DIC.FETCHDICDETAILFORMAINTAIN2", new Object[]{id,structId});
			if(list.size() > 0){
				TypeRefDic t = list.get(0);
				ObjEntityRef ref = (ObjEntityRef) genericDao.getById(ObjEntityRef.class, t.getObjEntityRefId());
				d.setTrdOrder(t.getOrder());
				d.setParentId(t.getParentId());
				d.setObjEntityRefId(t.getObjEntityRefId());
				d.setTypeRefDicId(t.getId());
				d.setTypeChild(ref.getChildId());  //子节点类型
			}
			//id作为子节点
			else {
				list = genericDao.getListWithVariableParas("BD.DIC.FETCHDICDETAILFORMAINTAIN", new Object[]{id});
				if(list.size() > 0){
					TypeRefDic t = list.get(0);
					d.setTrdOrder(t.getOrder());
					d.setParentId(t.getParentId());
					d.setObjEntityRefId(t.getObjEntityRefId());
					d.setTypeRefDicId(t.getId());
					d.setTypeChild(t.getObjEntityRefId());
				}
			}
		}
		return d;
	}

	public void saveDicRef(Dic dic) {
		boolean isExsit = false, isRoot = false;
		//dic被保存时，会重新给予新值，导致部分赋值消失
		Dic d=dic.clone();
		if(dic!=null && StringUtils.isBlank(dic.getId())){
			dic.setId(null);
			dic.setCreator(securityService.getOperator());
			dic.setCreateTime(DateBean.getSysdateTime());
		}else {
			dic.setLastModifier(securityService.getOperator());
			dic.setLastModifiedTime(DateBean.getSysdateTime());
		}
		if(dic != null && dic.getId() != null) isExsit = true;
//		dic.setType(dic.getTypeChild());
		genericDao.save(dic);
		
		//这里判断是否是跟节点，这里暂无此需求，添加的都是子节点，后期考虑
		if(d != null &&StringUtils.isBlank(d.getParentId())) isRoot = true;
		
		/*if(isExsit && !isRoot){
			//跟新关系
			TypeRefDic trd = (TypeRefDic)genericDao
				.getById(TypeRefDic.class, dic.getTypeRefDicId());
			if(trd!=null){
				trd.setOrder(dic.getTrdOrder());
				genericDao.save(trd);
			}
			
		}*/
		else if(!isExsit && isRoot){
			//添加根关系
			TypeRefDic trd = new TypeRefDic();
			trd.setObjEntityRefId(d.getObjEntityRefId());
			trd.setParentId(d.getParentId());
			trd.setSysFlag(1);
			genericDao.save(trd);
			dic.setTypeRefDicId(trd.getId());
		}
		if(!isExsit&&!isRoot){
			//添加非根关系
			TypeRefDic trd = new TypeRefDic();
			trd.setObjEntityRefId(d.getObjEntityRefId());
			//树根据parentid显示,那么parentid就是dic的父id
			trd.setParentId(d.getParentId());
			trd.setChildId(dic.getId());
			trd.setOrder(d.getTrdOrder());
			trd.setSysFlag(1);
			genericDao.save(trd);
			dic.setTypeRefDicId(d.getId());
		}
		
	}
	public void saveDic(Dic dic) {
		Dic d = null;
		if(StringUtils.isBlank(dic.getId())){
			dic.setId(null);
			dic.setCreator(securityService.getOperator());
			dic.setCreateTime(DateBean.getSysdateTime());
		}else {
			d = (Dic) genericDao.getById(Dic.class, dic.getId());
			d.setCode(dic.getCode());
			d.setName(dic.getName());
			d.setFullName(dic.getFullName());
			d.setSysFlag(dic.getSysFlag());
			d.setNum1(dic.getNum1());
			d.setValue2(dic.getValue2());
			d.setValue3(dic.getValue3());
			d.setValue4(dic.getValue4());
			d.setValue5(dic.getValue5());
			d.setValue6(dic.getValue6());
			d.setValue7(dic.getValue7());
			d.setValue8(dic.getValue8());
			d.setValue9(dic.getValue9());
			d.setValue10(dic.getValue10());
			d.setOtherId1(dic.getOtherId1());
			d.setOtherId2(dic.getOtherId2());
			d.setOtherId3(dic.getOtherId3());
			d.setOtherId4(dic.getOtherId4());
			d.setOtherId5(dic.getOtherId5());
			d.setFlag1(dic.getFlag1());
			d.setFlag2(dic.getFlag2());
			d.setFlag3(dic.getFlag3());
			d.setFlag4(dic.getFlag4());
			d.setFlag5(dic.getFlag5());
			d.setNum2(dic.getNum2());
			d.setNum3(dic.getNum3());
			d.setNum4(dic.getNum4());
			d.setNum5(dic.getNum5());
			d.setDouble1(dic.getDouble1());
			d.setDouble2(dic.getDouble2());
			d.setDouble3(dic.getDouble3());
			d.setDouble4(dic.getDouble4());
			d.setDouble5(dic.getDouble5());
			d.setRemark(dic.getRemark());
			dic.setLastModifiedTime(DateBean.getSysdateTime());
			dic.setLastModifier(securityService.getOperator());
		}
		genericDao.save(d);
	}
	/**
	 * @Description: 删除dic本表和TypeRefDic这张表，TypeRefDic这种表的child。id是dic的id
	 * @author: zw
	 */
	public void deletesDicRef(String ids) {
		if(StringUtils.isNotBlank(ids)){
			String[]arr=ids.split(Constants.SEPARATOR_COMMA);
			for(String id:arr){
				Dic dic=(Dic) genericDao.getById(Dic.class, id);
				dic.setLastModifier(securityService.getOperator());
				dic.setLastModifiedTime(DateBean.getSysdateTime());
				dic.setSysFlag(Integer.parseInt(Constants.SYS_FLAG_DELETED));
				genericDao.save(dic);
				List<TypeRefDic> list=genericDao.getList("TypeRefDic_FIND_BYCHILDID", id);
				if(list!=null && list.size()>0){
					TypeRefDic trd = list.get(0);
					trd.setSysFlag(Integer.parseInt(Constants.SYS_FLAG_DELETED));
					genericDao.save(trd);
				}
			}
		}
	}
	@SuppressWarnings("unchecked")
	public List<ObjEntityRef> fetchObjStructureSub(String id) {
		// 校验参数
		Utility.raiseIfWrong(id, "id不能为空");
		
		List<ObjEntityRef> oerList = genericDao.getListWithVariableParas("BD.OBJSTRUCTURE.FETCHOBJSTRUCTURESUB", new Object[]{id});
		for (ObjEntityRef oer : oerList) {
			oer.setChildName(getBaseDicName(oer.getChildId()));
			oer.setParentName(getBaseDicName(oer.getParentId()));
		}
		return oerList;
	}
	/**
	 * 校验字段，然后返回值result；
	 * @param ob
	 * @return
	 */
	public Map<String,Object> validCodeAndName(Dic dic) {
		Dic ob1=new Dic();
		ob1.setId(dic.getId());
		ob1.setCode(dic.getCode());
		ob1.setType(dic.getType());
		Map<String, Object> result = ResultUtil.ErrorResult();
		Boolean flag=true;
		if(!this.isExist(ob1)) {
			result.put(Constants.MESSAGE, "该code已存在");
			flag=false;
			
		}
		ob1.setCode(null);
		ob1.setName(dic.getName());
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
	public Boolean isExist(Dic dic) {
		String sql="select count(*) from t_pub_dic t where t.f_dic_code = ? and t.f_dic_name = ? and t.f_obj_pid = ? ";
		Object [] params= {dic.getCode(),dic.getName(),dic.getType()};
		BigInteger o =(BigInteger) genericDao.getUniqueResult(sql, params);
		if(o==null || o.intValue()<1) {
			return true;
		}
		//在这里借用id这个字段,因为在更新时，可能这个字段以存在
		String id=dic.getId();
		if(StringUtils.isNotBlank(id) &&o.intValue()==1) {
			Dic d=(Dic) genericDao.getById(Dic.class, id);
			if(d!=null && StringUtils.isNotBlank(d.getName()) && d.getName().equals(dic.getName())) {
				return true;
			}
			if(d!=null && StringUtils.isNotBlank(d.getCode()) && d.getCode().equals(dic.getCode())) {
				return true;
			}
		}
		return false;
	}



}
