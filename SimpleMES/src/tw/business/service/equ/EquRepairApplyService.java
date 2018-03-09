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
import tw.utils.DatetimeEx;

/**
 * 设备台账
 * @author zy 2018-01-17
 * 
 */
@Service
@Transactional
public class EquRepairApplyService {

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
	public Pagination findRepairApplyList(String startTime,String endTime,String equName,String status) {
		//String equName = dateSearch.getName();
		//SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		startTime=startTime+" 00:00:00";
//		endTime=endTime+" 23:59:59";
		 //String startTime1=df.format(Date.valueOf(startTime));
		// String endTime1=df.format(Date.valueOf(endTime));
		equName = (StringUtils.isBlank(equName) ? equName : "%" + equName + "%");
		Object[] parameter = {equName,status,DateBean.convertS2Date(startTime),DateBean.convertS2Date(endTime)};
		EquRepairform equRepairform= new EquRepairform();
		PaginationSupport paginationSupport =genericDao.getPageWithParams(
				"EQU.FETCH.REPAIR.LIST", parameter,equRepairform);
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
			
//			
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
	public void deletes(String ids) {
		Timestamp time = new Timestamp(System.currentTimeMillis()); 
		if (StringUtils.isBlank(ids)) {
			throw new RuntimeException("ids: IDS传入的参数为空!");
		}
		String[] idArray = ids.split(Constants.SEPARATOR_COLON);
		for (int i = 0; i < idArray.length; i++) {
			String id = idArray[i];
			EquRepairform equRepairform = (EquRepairform) genericDao.getById(EquRepairform.class, id);
			equRepairform.setSysFlag(Constants.SYS_FLAG_DELETED);
			equRepairform.setLastModifier(securityService.getOperator());
			equRepairform.setLastModifiedTime(time);
			genericDao.save(equRepairform);
		}
	}
	
	/**
	 * 保存
	 */
	public void save(EquRepairform equRepairform) {
		Timestamp time = new Timestamp(System.currentTimeMillis());
		if(!StringUtils.isBlank(equRepairform.getPid()) == false){
			equRepairform.setPid(null);
			EquInfo equInfo =(EquInfo)genericDao.getById(EquInfo.class, equRepairform.getEquId());
			equRepairform.setApplyUserid(securityService.getOperator());
			User user = (User) genericDao.getById(UserImpl.class, equRepairform.getApplyUserid());
			equRepairform.setApplyUsername(user.getName());
			User user1 = (User) genericDao.getById(UserImpl.class, equRepairform.getRepairUserid());
			equRepairform.setRepairUsername(user1.getName());
			//equRepairform.setEquId(equRepairform.getEquId()); //设备Id
			equRepairform.setEquCode(equInfo.getEquCode());
			equRepairform.setEquName(equInfo.getEquName());
			equRepairform.setSysFlag("1");
			Dic repairCode=(Dic)genericDao.getById(Dic.class,equRepairform.getRepairType());
			String num=String.format("R%02d%s%s", Integer.parseInt(repairCode.getCode()), equRepairform.getEquCode(), DatetimeEx.toStr(new java.util.Date(), "yyMMddHHmm"));
			equRepairform.setRepairSn(num);
			//equRepairform.setReceiveTime(time);//申请报修时间
			equRepairform.setStatus("0"); //将维修单状态改为草稿
			equRepairform.setCreator(securityService.getOperator());
			equRepairform.setCreateTime(time);
			genericDao.save(equRepairform);
		}else {
			EquRepairform erf = (EquRepairform) genericDao.getById(EquRepairform.class, equRepairform.getPid());
			if(!StringUtils.isBlank(equRepairform.getEquId())) {
				EquInfo equInfo =(EquInfo)genericDao.getById(EquInfo.class, equRepairform.getEquId());
				erf.setEquId(equRepairform.getEquId()); //设备Id
				erf.setEquCode(equInfo.getEquCode());
				erf.setEquName(equInfo.getEquName());
			}
			if(!StringUtils.isBlank(equRepairform.getRepairUserid())) {
				User user = (User) genericDao.getById(UserImpl.class, equRepairform.getRepairUserid());
				erf.setRepairUserid(equRepairform.getRepairUserid());  //指定维修人Id
				erf.setRepairUsername(user.getName());
			}
			erf.setRepairType(equRepairform.getRepairType()); //报修类型
			erf.setRemark(equRepairform.getRemark());
			erf.setOccurTime(equRepairform.getOccurTime()); //发生时间
			erf.setAbnormalDes(equRepairform.getAbnormalDes());   //异常描述
			erf.setLastModifier(securityService.getOperator());
			erf.setLastModifiedTime(time);
			genericDao.save(erf);
		}
	}
	
	/**
	 * 保存维修记录
	 * @return
	 */
	public void saveRecord(EquRepairform equRepairform) {
		if(!StringUtils.isBlank(equRepairform.getPid()) == false){
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
		}
	}
	
	
	
	/**
	 * 是否提交草稿
	 * @return
	 */
	public void isCheckUpdate(String id) {
		if(StringUtils.isBlank(id) == false){
			EquRepairform erf = (EquRepairform) genericDao.getById(EquRepairform.class, id);
			erf.setStatus("10"); //提交草稿
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
