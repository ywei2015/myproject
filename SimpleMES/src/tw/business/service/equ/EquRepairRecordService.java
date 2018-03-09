/**
 * 
 */
package tw.business.service.equ;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.business.entity.equ.EquInfo;
import tw.business.entity.equ.EquRepairform;
import tw.business.entity.equ.EquWbtask;
import tw.business.entity.equ.EquWbtaskDefine;
import tw.business.entity.pub.DicView;
import tw.sysbase.dao.GenericDao;
import tw.sysbase.dao.PaginationSupport;
import tw.sysbase.entity.Pagination;
import tw.sysbase.entity.pub.Dic;
import tw.sysbase.entity.sec.User;
import tw.sysbase.entity.sec.UserImpl;
import tw.sysbase.pub.Constants;
import tw.sysbase.pub.DateBean;
import tw.sysbase.servie.sec.SecurityService;
import tw.sysbase.servie.sec.UserService;

/**
 * 设备台账
 * @author zy 2018-01-17
 * 
 */
@Service
@Transactional
public class EquRepairRecordService {

	@Resource
	private GenericDao genericDao;
	@Resource
	private SecurityService securityService;
	@Resource
	private UserService userService;

	/**
	 * 获取设备维修申请
	 * 
	 * @param findRepairApplyList
	 * @return
	 */
	public Pagination findRecordList(String equName,String status) {
		//Object[] parameter = {DateBean.convertS2Date(startTime),DateBean.convertS2Date(endTime)};
		equName = (StringUtils.isBlank(equName) ? equName : "%" + equName + "%");
		Object[] parameter = {equName,status};
		EquRepairform equRepairform= new EquRepairform();
		PaginationSupport paginationSupport =genericDao.getPageWithParams(
				"EQU.FETCH.RECORD.LIST", parameter,equRepairform);
		List<EquRepairform> result = new ArrayList<EquRepairform>();
		for (Iterator iterator = paginationSupport.getItems().iterator(); iterator
				.hasNext();) {
			EquRepairform equRepairformList  =	(EquRepairform) iterator.next();
//			if(equRepairformList.getSourceId()!=null) {
//				EquWbtask equWbtaskDefine=(EquWbtask) genericDao.getById(EquWbtask.class, equRepairformList.getSourceId());
//				if(equWbtaskDefine.getWbTasktype()!=null) {
//						Dic repairDic = (Dic)genericDao.getById(Dic.class, equWbtaskDefine.getWbTasktype());
//						equRepairformList.setSourceName(repairDic.getName());
//				}
//			}
			if(!StringUtils.isBlank(equRepairformList.getEquId())) {
				EquInfo equInfo=(EquInfo) genericDao.getById(EquInfo.class, equRepairformList.getEquId());
				equRepairformList.setEquName(equInfo.getEquName());
				equRepairformList.setEquCode(equInfo.getEquCode());
			}
			if(!StringUtils.isBlank(equRepairformList.getRepairType())) {
				Dic repairDic = (Dic)genericDao.getById(Dic.class, equRepairformList.getRepairType());
				equRepairformList.setRepairTypeName(repairDic.getName());
			}
			if(StringUtils.isBlank(equRepairformList.getAbnormalType()) == false) {
				Dic recordDic = (Dic)genericDao.getById(Dic.class, equRepairformList.getAbnormalType());
				equRepairformList.setAbnormalTypeName(recordDic.getName());
			}
//			
			result.add(equRepairformList);
		}
		paginationSupport.setItems(result);
		Pagination pagination = Pagination.init(paginationSupport);
		return pagination;
	}
	
	
	public Pagination findRecordById(String id) {
		Object[] parameter = {id};
		EquRepairform equRepairform= new EquRepairform();
		PaginationSupport paginationSupport =genericDao.getPageWithParams(
				"EQU.FETCH.RECORDBYID.LIST", parameter,equRepairform);
		List<EquRepairform> result = new ArrayList<EquRepairform>();
		for (Iterator iterator = paginationSupport.getItems().iterator(); iterator
				.hasNext();) {
			EquRepairform equRepairformList  =	(EquRepairform) iterator.next();
			if(!StringUtils.isBlank(equRepairformList.getAbnormalType())) {
				Dic recordDic = (Dic)genericDao.getById(Dic.class, equRepairformList.getAbnormalType());
				equRepairformList.setAbnormalTypeName(recordDic.getName());
			}
			result.add(equRepairformList);
		}
		paginationSupport.setItems(result);
		Pagination pagination = Pagination.init(paginationSupport);
		return pagination;
	}
	
	public Pagination findVerificat(String equName,String status) {
		equName = (StringUtils.isBlank(equName) ? equName : "%" + equName + "%");
		Object[] parameter = {equName,status};
		EquRepairform equRepairform= new EquRepairform();
		PaginationSupport paginationSupport =genericDao.getPageWithParams(
				"EQU.FETCH.VERIFICAT.LIST", parameter,equRepairform);
		List<EquRepairform> result = new ArrayList<EquRepairform>();
		for (Iterator iterator = paginationSupport.getItems().iterator(); iterator
				.hasNext();) {
			EquRepairform equRepairformList  =	(EquRepairform) iterator.next();
//			if(equRepairformList.getSourceId()!=null) {
//				EquWbtask equWbtaskDefine=(EquWbtask) genericDao.getById(EquWbtask.class, equRepairformList.getSourceId());
//				if(equWbtaskDefine.getWbTasktype()!=null) {
//						Dic repairDic = (Dic)genericDao.getById(Dic.class, equWbtaskDefine.getWbTasktype());
//						equRepairformList.setSourceName(repairDic.getName());
//				}
//			}
			if(!StringUtils.isBlank(equRepairformList.getEquId())) {
				EquInfo equInfo=(EquInfo) genericDao.getById(EquInfo.class, equRepairformList.getEquId());
				equRepairformList.setEquName(equInfo.getEquName());
				equRepairformList.setEquCode(equInfo.getEquCode());
			}
			if(!StringUtils.isBlank(equRepairformList.getRepairType())) {
				Dic repairDic = (Dic)genericDao.getById(Dic.class, equRepairformList.getRepairType());
				equRepairformList.setRepairTypeName(repairDic.getName());
			}
			if(StringUtils.isBlank(equRepairformList.getAbnormalType()) == false) {
				Dic recordDic = (Dic)genericDao.getById(Dic.class, equRepairformList.getAbnormalType());
				equRepairformList.setAbnormalTypeName(recordDic.getName());
			}
			result.add(equRepairformList);
		}
		paginationSupport.setItems(result);
		Pagination pagination = Pagination.init(paginationSupport);
		return pagination;
	}
	
	/**
	 * 通过id获取对象
	 * @param id
	 * @return
	 */
	public EquRepairform findRepairById(String id) {
		EquRepairform equRepairform = (EquRepairform) genericDao.getById(EquRepairform.class, id);
		
		equRepairform.setApplyUserid(securityService.getOperator());
		equRepairform.setApplyUsername(userService.findNameById(equRepairform.getApplyUserid()));
//		if(equInfo.getOrgId()!=null) {
//			Dic orgDic=(Dic) genericDao.getById(Dic.class, equInfo.getOrgId());
//			equInfo.setOrgName(orgDic.getName());
//		}
//		if(equInfo.getEquKind()!=null) {
//			Dic kindDic=(Dic) genericDao.getById(Dic.class, equInfo.getEquKind());
//			equInfo.setKindName(kindDic.getName());
//		}
		return equRepairform;
	}
	
	
	/**
	 * 删除
	 * @param ids
	 */
	public void deletes(String id) {
		Timestamp time = new Timestamp(System.currentTimeMillis()); 
		if (StringUtils.isBlank(id)) {
			throw new RuntimeException("ids: IDS传入的参数为空!");
		}
			EquRepairform equRepairform = (EquRepairform) genericDao.getById(EquRepairform.class, id);
			
			equRepairform.setLastModifier(securityService.getOperator());
			equRepairform.setLastModifiedTime(time);
			genericDao.save(equRepairform);
	}
	
	/**
	 * 保存维修记录
	 * @return
	 */
	public void saveRecord(EquRepairform equRepairform) {
		if(StringUtils.isBlank(equRepairform.getPid()) == false){
			EquRepairform erf = (EquRepairform) genericDao.getById(EquRepairform.class, equRepairform.getPid());
			if(!StringUtils.isBlank(equRepairform.getEquId())) {
				EquInfo equInfo =(EquInfo)genericDao.getById(EquInfo.class, equRepairform.getEquId());
				erf.setEquId(equRepairform.getEquId());//设备id
				erf.setEquCode(equInfo.getEquCode());
				erf.setEquName(equInfo.getEquName());
			}
			if(!StringUtils.isBlank(equRepairform.getRepairUserid())) {
				User user = (User) genericDao.getById(UserImpl.class, equRepairform.getRepairUserid());
				erf.setRepairUserid(equRepairform.getRepairUserid());  //指定维修人Id
				erf.setRepairUsername(user.getName());
			}
			erf.setAbnormalType(equRepairform.getAbnormalType());//故障类型
			erf.setRepairSt(equRepairform.getRepairSt());//维修开始时间
			erf.setRepairEt(equRepairform.getRepairEt());//维修结束时间
			erf.setRepairProdes(equRepairform.getRepairProdes()); //维修过程描述
			erf.setIssubmitCheck("0"); //未提交
			erf.setStatus("29"); //维修状态
			genericDao.save(erf);
		}
	}
	/**
	 * 保存验证saveVerificat
	 * @return
	 */
	public void saveVerificat(EquRepairform equRepairform) {
		if(StringUtils.isBlank(equRepairform.getPid()) == false){
			if("1".equals(equRepairform.getChekcResult())) {  //验证通过
				EquRepairform erf = (EquRepairform) genericDao.getById(EquRepairform.class, equRepairform.getPid());
				if(!StringUtils.isBlank(equRepairform.getCheckUserid())) {
					User user = (User) genericDao.getById(UserImpl.class, equRepairform.getCheckUserid());
					erf.setCheckUserid(equRepairform.getCheckUserid());  //指定维修人Id
					erf.setCheckUsername(user.getName());
				}
				erf.setStatus("40");
				erf.setChekcResult(equRepairform.getChekcResult()); //验证结果
				erf.setInvalidDes(equRepairform.getInvalidDes());  //验证描述
				erf.setCheckEt(equRepairform.getCheckEt());   //验证时间
				genericDao.save(erf);
			}else {
				EquRepairform erf = (EquRepairform) genericDao.getById(EquRepairform.class, equRepairform.getPid());
				if(!StringUtils.isBlank(equRepairform.getCheckUserid())) {
					User user = (User) genericDao.getById(UserImpl.class, equRepairform.getCheckUserid());
					erf.setCheckUserid(equRepairform.getCheckUserid());  //指定维修人Id
					erf.setCheckUsername(user.getName());
				}
				erf.setStatus(equRepairform.getStatus());
				erf.setChekcResult(equRepairform.getChekcResult()); //验证结果
				erf.setInvalidDes(equRepairform.getInvalidDes());   
				genericDao.save(erf);
			}
		}
	}
	
	/**
	 * 是否提交验证
	 * @return
	 */
	public void isCheck(String id) {
		if(StringUtils.isBlank(id) == false){
			EquRepairform erf = (EquRepairform) genericDao.getById(EquRepairform.class, id);
			erf.setIssubmitCheck("1"); //提交验证
		}
	}
	
	public List<DicView> findEqu() {
		String sql ="select t.f_pid,t.f_equ_code,t.f_equ_name from  t_equ_info  t";
		List<Object[]> list = genericDao.getListWithNativeSql(sql);
		List<DicView>res=new ArrayList<>();
		for(Object[] o:list) {
			DicView dicView=new DicView();
			dicView.setPid((String)o[0]);
			dicView.setCode((String)o[1]);
			dicView.setName((String)o[2]);
			//dicView.setIndex(Int(String)o[3]);
			res.add(dicView);
		}
		return res;
	}
}
