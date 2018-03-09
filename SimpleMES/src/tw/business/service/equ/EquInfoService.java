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
import tw.sysbase.dao.GenericDao;
import tw.sysbase.dao.PaginationSupport;
import tw.sysbase.entity.Pagination;
import tw.sysbase.entity.pub.Dic;
import tw.sysbase.pub.Constants;
import tw.sysbase.servie.sec.SecurityService;

/**
 * 设备台账
 * @author zy 2018-01-17
 * 
 */
@Service
@Transactional
public class EquInfoService {

	@Resource
	private GenericDao genericDao;
	@Resource
	private SecurityService securityService;

	/**
	 * 获取设备台账列表数据
	 * 
	 * @param equWbtaskDefine
	 * @return
	 */
	public Pagination findInfoList(EquInfo equInfo) {
		String equName = equInfo.getEquName();
		equName = (StringUtils.isBlank(equName) ? equName : "%" + equName + "%");
		Object[] parameter = { equName,equInfo.getEquKind(),equInfo.getStatus() };

		PaginationSupport paginationSupport = genericDao.getPageWithParams(
				"EQU.FETCH.TZINFO.LIST", parameter,equInfo);
		List<EquInfo> result = new ArrayList<EquInfo>();
		for (Iterator iterator = paginationSupport.getItems().iterator(); iterator
				.hasNext();) {
			EquInfo equInfoList  =	(EquInfo) iterator.next();
			if(!StringUtils.isBlank(equInfoList.getOrgId())) {
				Dic orgDic=(Dic) genericDao.getById(Dic.class, equInfoList.getOrgId());
				equInfoList.setOrgName(orgDic.getName());
			}
			if(!StringUtils.isBlank(equInfoList.getEquKind())) {
				Dic kindDic=(Dic) genericDao.getById(Dic.class, equInfoList.getEquKind());
				equInfoList.setKindName(kindDic.getName());
			}
			
			result.add(equInfoList);
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
	public EquInfo findEquInfo(String id) {
		EquInfo equInfo = (EquInfo) genericDao.getById(EquInfo.class, id);
		if(!StringUtils.isBlank(equInfo.getOrgId())) {
			Dic orgDic=(Dic) genericDao.getById(Dic.class, equInfo.getOrgId());
			equInfo.setOrgName(orgDic.getName());
		}
		if(!StringUtils.isBlank(equInfo.getEquKind())) {
			Dic kindDic=(Dic) genericDao.getById(Dic.class, equInfo.getEquKind());
			equInfo.setKindName(kindDic.getName());
		}
		return equInfo;
	}
	
	
	/**
	 * 删除
	 * @param ids
	 */
	public void deletesInfo(String ids) {
		Timestamp time = new Timestamp(System.currentTimeMillis()); 
		if (StringUtils.isBlank(ids)) {
			throw new RuntimeException("ids: IDS传入的参数为空!");
		}
		String[] idArray = ids.split(Constants.SEPARATOR_COLON);
		for (int i = 0; i < idArray.length; i++) {
			String id = idArray[i];
			EquInfo equInfo = (EquInfo) genericDao.getById(EquInfo.class, id);
			equInfo.setSysFlag(Constants.SYS_FLAG_DELETED);
			equInfo.setLastModifier(securityService.getOperator());
			equInfo.setLastModifiedTime(time);
			genericDao.save(equInfo);
		}
	}
	
	/**
	 * 保存台账
	 */
	public void saveInfo(EquInfo equInfo) {
		Timestamp time = new Timestamp(System.currentTimeMillis());
		if(!StringUtils.isBlank(equInfo.getPid()) == false){
			equInfo.setPid(null);
			equInfo.setSysFlag("1");
			equInfo.setCreator(securityService.getOperator());
			equInfo.setCreateTime(time);
			genericDao.save(equInfo);
		}else {
			EquInfo oldEquInfo=(EquInfo) genericDao.getById(EquInfo.class, equInfo.getPid());
			oldEquInfo.setEquName(equInfo.getEquName());
			oldEquInfo.setEquCode(equInfo.getEquCode());
			oldEquInfo.setEquKind(equInfo.getEquKind());
			oldEquInfo.setOrgId(equInfo.getOrgId());
			oldEquInfo.setFixassetCode(equInfo.getFixassetCode());
			oldEquInfo.setManufacturer(equInfo.getManufacturer());
			oldEquInfo.setEquModel(equInfo.getEquModel());
			oldEquInfo.setFactoryNum(equInfo.getFactoryNum());
			oldEquInfo.setPurchaseDate(equInfo.getPurchaseDate());
			oldEquInfo.setStatus(equInfo.getStatus());
			oldEquInfo.setLocation(equInfo.getLocation());
			oldEquInfo.setPurpose(equInfo.getPurpose());
			oldEquInfo.setRemark(equInfo.getRemark());
			oldEquInfo.setLastModifier(securityService.getOperator());
			oldEquInfo.setLastModifiedTime(time);
			genericDao.save(oldEquInfo);
		}
	}
}
