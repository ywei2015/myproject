package tw.sysbase.servie.pub;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.business.service.pub.BasicDataService;
import tw.sysbase.dao.GenericDao;
import tw.sysbase.dao.PaginationSupport;
import tw.sysbase.entity.pub.Dic;
import tw.sysbase.entity.pub.ObjBase;
import tw.sysbase.entity.pub.ObjEntityRef;
import tw.sysbase.pub.Constants;
import tw.sysbase.pub.ResultUtil;
import tw.sysbase.servie.sec.SecurityService;

@Service
@Transactional
public class ObjEntityRefService implements  BasicDataService<ObjEntityRef> {
	@Resource
	private GenericDao genericDao;
	@Autowired
	private SecurityService securityService;

	@Override
	public List<ObjEntityRef> getList(ObjEntityRef t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ObjEntityRef get(ObjEntityRef t) {
		ObjEntityRef ref = (ObjEntityRef) genericDao.getById(ObjEntityRef.class, t.getId());
		ref.setChildName(getData(ref.getChildId()));
		ref.setParentName(getData(ref.getParentId()));
		return ref;
	}

	@Override
	public void update(ObjEntityRef t) {
		genericDao.save(t);
	}

	@Override
	public void delete(ObjEntityRef t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ObjEntityRef save(ObjEntityRef t) {
		if(StringUtils.isBlank(t.getId())){
			t.setId(null);
		}
		genericDao.save(t);
		return t;
	}

	public PaginationSupport findConditionWithPage(ObjEntityRef objEntityRef) {
		String code=objEntityRef.getCode();
		String name=objEntityRef.getName();
		Integer sysFlag=objEntityRef.getSysFlag();
		String parentId = objEntityRef.getParentId();
		String childId = objEntityRef.getChildId();
		code = (code == null ? code : "%" + code + "%");
		name = (name == null ? name : "%" + name + "%");
		Object[] paras = new Object[] {code, name, parentId, childId,sysFlag};
		PaginationSupport ps = genericDao.getPageWithParams("BD.OBJENTITYREF.LIST", paras, objEntityRef);
		if(ps.getItems() != null){
			List<ObjEntityRef> oerList = (List<ObjEntityRef>)ps.getItems();
			for (ObjEntityRef oer : oerList) {
					oer.setChildName(getData(oer.getChildId()));
					oer.setParentName(getData(oer.getParentId()));
			}
		}
		return ps;
	}

	private String getData(String id) {
		ObjBase objBase=	(ObjBase) genericDao.getById(ObjBase.class, id);
		return objBase.getName();
	}

	public void deletes(String ids) {
		if(StringUtils.isNotBlank(ids)){
			String[]arr=ids.split(Constants.SEPARATOR_COMMA);
			for(String id:arr){
				ObjEntityRef ObjEntityRef = (ObjEntityRef) genericDao.getById(ObjEntityRef.class, id);
				ObjEntityRef.setSysFlag(0);
				 genericDao.save(ObjEntityRef);
			}
		}
		
	}
	/*
	 *判断数据库是否存在该code or name 
	 */
	public Boolean isExist(ObjEntityRef oer) {
		String sql="select count(*) from t_pub_obj_entity_ref t where t.f_code = ? and t.f_name = ?";
		Object [] params= {oer.getCode(),oer.getName()};
		BigInteger o =(BigInteger) genericDao.getUniqueResult(sql, params);
		if(o==null || o.intValue()<1) {
			return true;
		}
		//在这里借用id这个字段,因为在更新时，可能这个字段以存在
		String id=oer.getId();
		if(StringUtils.isNotBlank(id) &&o.intValue()==1) {
			ObjEntityRef d=(ObjEntityRef) genericDao.getById(ObjEntityRef.class, id);
			if(d!=null && StringUtils.isNotBlank(d.getName()) && d.getName().equals(oer.getName())) {
				return true;
			}
			if(d!=null && StringUtils.isNotBlank(d.getCode()) && d.getCode().equals(oer.getCode())) {
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
	public Map<String,Object> validCodeAndName(ObjEntityRef oer) {
		ObjEntityRef ob1=new ObjEntityRef();
		ob1.setId(oer.getId());
		ob1.setCode(oer.getCode());
		Map<String, Object> result = ResultUtil.ErrorResult();
		Boolean flag=true;
		if(!this.isExist(ob1)) {
			result.put(Constants.MESSAGE, "该code已存在");
			flag=false;
			
		}
		ob1.setCode(null);
		ob1.setName(oer.getName());
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

}
