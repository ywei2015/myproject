package tw.business.service.produce;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.business.entity.mat.Matdic;
import tw.business.entity.produce.ProduceIn;
import tw.business.entity.produce.ProduceOut;
import tw.business.entity.produce.ProduceWo;
import tw.business.entity.pub.DicView;
import tw.business.service.equ.DicViewService;
import tw.sysbase.dao.GenericDao;
import tw.sysbase.dao.PaginationSupport;
import tw.sysbase.pub.Constants;
import tw.sysbase.pub.DateBean;
import tw.sysbase.pub.StingUtil;
import tw.sysbase.servie.sec.SecurityService;
import tw.sysbase.servie.sec.UserService;

/**
 * 生产工单
 * @author cyj 2018-01-23
 * 
 */
@Service
@Transactional
public class ProduceService {

	@Resource
	private GenericDao genericDao;
	@Resource
	private SecurityService securityService;
	@Resource
	private UserService userService;
	@Autowired
	private DicViewService dicViewService;
	
	/**
	 * 获取生产工单列表数据
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public PaginationSupport findProduceWoList(String startTime,String endTime,String pwoType) {
//		taskName = (StringUtils.isBlank(taskName) ? taskName : "%" +taskName + "%");
		Object[] parameter = {DateBean.convertS2Date(startTime),DateBean.convertS2Date(endTime),pwoType};
		ProduceWo produceWo= new ProduceWo();
		PaginationSupport ps=null;
        try {
            ps=genericDao.getPageWithParams(
     				"EQU.FETCH.PRODUCEWO.LIST", parameter, produceWo);
            @SuppressWarnings("unchecked")
            List<ProduceWo> prolist = ps.getItems();
            for(ProduceWo pro : prolist) {
    			//设置工单类型名称
    			DicView dicView = dicViewService.getById("v_produce_wotype",pro.getPwoType());
    			pro.setPwoTypeName(dicView.getName());
    			//单位名称
    			DicView dicView1 = dicViewService.getById("v_mat_units",pro.getUnit());
    			pro.setUnitName(dicView1.getName());
    			
    			/*String productName = getMatDicByCode(pro.getProductCode());
    			pro.setProductName(productName);*/
    		}
            ps.setItems(prolist);
        } catch (Exception e) {
            e.printStackTrace();
        }
		return ps;
	}

	/**
	 * 获取生产工单详情
	 * @param id
	 * @return
	 */
	public ProduceWo getProduceWoById(String id) {
		ProduceWo pro = null;
        try {
            pro = (ProduceWo) genericDao.getById(ProduceWo.class, id);
            if(!StingUtil.isEmpty(pro)){
    			pro.setProductName(getMatDicByCode(pro.getProductCode()));
    		}
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pro;
	}

	/**
	 * 添加/修改生产工单
	 * @param produceWo
	 * @return
	 */
	public ProduceWo saveProduceWo(ProduceWo produceWo) {
		Timestamp time = new Timestamp(System.currentTimeMillis()); 
		try {
			if(!StringUtils.isBlank(produceWo.getPid()) == false){
				produceWo.setPid(null);
				//设置工单类型名称
    			DicView dicView = dicViewService.getById("v_produce_wotype",produceWo.getPwoType());
    			DicView dicView1 = dicViewService.getById("v_workshop",produceWo.getWorkshopId());
//    			String productName = getMatDicByCode(produceWo.getProductCode());
    			String matName = getMatDicByCode(produceWo.getMatCode());
				//工单类型编码+物料+日期
    			String newIndex = getNewPwoSn(produceWo.getPwoType(),DateBean.dateStrToTime1(produceWo.getPlanStVo()));
				produceWo.setPwoSn(dicView.getCode()+produceWo.getMatCode()+DateBean.dateStrToTime1(produceWo.getPlanStVo())+newIndex);
//				produceWo.setProductName(productName);
				produceWo.setWorkshopName(dicView1.getName());
				produceWo.setMatName(matName);
				produceWo.setPlanSt(Timestamp.valueOf(produceWo.getPlanStVo()));
				produceWo.setPlanEt(Timestamp.valueOf(produceWo.getPlanEtVo()));
				produceWo.setStatus(ProduceConstant.PRODUCE_STATUS_INIT);
				produceWo.setSysFlag(Constants.SYS_FLAG_USEING);
				produceWo.setCreator(securityService.getOperator());
				produceWo.setCreateTime(time);
				genericDao.save(produceWo);
			}else{
				ProduceWo proWo = (ProduceWo) this.genericDao.getById(ProduceWo.class, produceWo.getPid());
				DicView dicView = dicViewService.getById("v_workshop",produceWo.getWorkshopId());
				//设置工单类型名称
    			String matName = getMatDicByCode(produceWo.getMatCode());
    			proWo.setPwoType(produceWo.getPwoType());
    			proWo.setWorkshopId(produceWo.getWorkshopId());
    			proWo.setWorkshopName(dicView.getName());
    			proWo.setMatCode(produceWo.getMatCode());
    			proWo.setMatName(matName);
//    			proWo.setProductCode(produceWo.getProductCode());
    			proWo.setUnit(produceWo.getUnit());
    			proWo.setPlanQuantity(produceWo.getPlanQuantity());
    			proWo.setPlanSt(Timestamp.valueOf(produceWo.getPlanStVo()));
    			proWo.setPlanEt(Timestamp.valueOf(produceWo.getPlanEtVo()));
    			proWo.setLastModifier(securityService.getOperator());
    			proWo.setLastModifiedTime(time);
    			genericDao.save(proWo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 工单下达
	 * @param ids
	 */
	public void saveIssueProduceWo(String ids) {
		try {
			Timestamp time = new Timestamp(System.currentTimeMillis()); 
			if (StringUtils.isBlank(ids)) {
				throw new RuntimeException("ids: IDS传入的参数为空!");
			}
			String[] idArray = ids.split(Constants.SEPARATOR_COLON);
			for (int i = 0; i < idArray.length; i++) {
				String id = idArray[i];
				ProduceWo produceWo = (ProduceWo) genericDao.getById(ProduceWo.class, id);
				produceWo.setIssueUserid(securityService.getOperator());
				produceWo.setIssueUsername(userService.findNameById(securityService.getOperator()));
				produceWo.setIssueTime(time);
				produceWo.setStatus(ProduceConstant.PRODUCE_STATUS_ISSUED);
				genericDao.save(produceWo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getMatDicByCode(String matCode)
	{
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("FROM tw.business.entity.mat.Matdic T ");
		strBuffer.append("Where T.matCode = '"); 
		strBuffer.append(matCode);
		strBuffer.append("'");
		strBuffer.append(" AND T.sysFlag = '1'");
		
		String hql = strBuffer.toString();
		List<Matdic> conList = this.genericDao.getListByHql(hql);
		if (conList == null || conList.size() == 0)
		{
			return null;
		}
		return conList.get(0).getMatName();
	}
	
	
	/**
	 * 删除生产工单记录
	 * @param ids
	 */
	public void deleteProduceWo(String ids) {
		Timestamp time = new Timestamp(System.currentTimeMillis()); 
		if (StringUtils.isBlank(ids)) {
			throw new RuntimeException("ids: IDS传入的参数为空!");
		}
		String[] idArray = ids.split(Constants.SEPARATOR_COLON);
		for (int i = 0; i < idArray.length; i++) {
			String id = idArray[i];
			ProduceWo produceWo = (ProduceWo) genericDao.getById(ProduceWo.class, id);
			produceWo.setSysFlag(Constants.SYS_FLAG_DELETED);
			produceWo.setLastModifier(securityService.getOperator());
			produceWo.setLastModifiedTime(time);
			genericDao.save(produceWo);
		}
	}
	
	/**
	 * 获取生产工单执行列表数据
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public PaginationSupport findProducePerformList(String startTime,String endTime) {
		Object[] parameter = {DateBean.convertS2Date(startTime),DateBean.convertS2Date(endTime)};
		ProduceWo produceWo= new ProduceWo();
		PaginationSupport ps=null;
        try {
            ps=genericDao.getPageWithParams(
     				"EQU.FETCH.PRODUCE.PERFORM.LIST", parameter, produceWo);
            @SuppressWarnings("unchecked")
            List<ProduceWo> prolist = ps.getItems();
            for(ProduceWo pro : prolist) {
    			//设置工单类型名称
    			DicView dicView = dicViewService.getById("v_produce_wotype",pro.getPwoType());
    			pro.setPwoTypeName(dicView.getName());
    			//单位名称
    			DicView dicView1 = dicViewService.getById("v_mat_units",pro.getUnit());
    			pro.setUnitName(dicView1.getName());
    			
    			/*String productName = getMatDicByCode(pro.getProductCode());
    			pro.setProductName(productName);*/
    		}
            ps.setItems(prolist);
        } catch (Exception e) {
            e.printStackTrace();
        }
		return ps;
	}
	
	/**
	 * 开始工单
	 * @param ids
	 */
	public void saveBeginProduceWo(String ids) {
		try {
			Timestamp time = new Timestamp(System.currentTimeMillis()); 
			if (StringUtils.isBlank(ids)) {
				throw new RuntimeException("ids: IDS传入的参数为空!");
			}
			String[] idArray = ids.split(Constants.SEPARATOR_COLON);
			for (int i = 0; i < idArray.length; i++) {
				String id = idArray[i];
				ProduceWo produceWo = (ProduceWo) genericDao.getById(ProduceWo.class, id);
				produceWo.setFactSt(time);
				produceWo.setLastModifier(securityService.getOperator());
				produceWo.setLastModifiedTime(time);
				produceWo.setStatus(ProduceConstant.PRODUCE_STATUS_EXECUTING);
				genericDao.save(produceWo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 结束工单
	 * @param ids
	 */
	public void saveEndProduceWo(String ids) {
		try {
			Timestamp time = new Timestamp(System.currentTimeMillis()); 
			if (StringUtils.isBlank(ids)) {
				throw new RuntimeException("ids: IDS传入的参数为空!");
			}
			String[] idArray = ids.split(Constants.SEPARATOR_COLON);
			for (int i = 0; i < idArray.length; i++) {
				String id = idArray[i];
				ProduceWo produceWo = (ProduceWo) genericDao.getById(ProduceWo.class, id);
				produceWo.setFactEt(time);
				produceWo.setLastModifier(securityService.getOperator());
				produceWo.setLastModifiedTime(time);
				produceWo.setStatus(ProduceConstant.PRODUCE_STATUS_DONE);
				genericDao.save(produceWo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取生产工单投入
	 * @param produceIn
	 * @return
	 */
	public PaginationSupport findProduceInList(ProduceIn produceIn) {
		String pwoId = produceIn.getPwoId();
		Object[] parameter = { pwoId };
		PaginationSupport ps = null;
        try {
            ps=genericDao.getPageWithParams(
     				"EQU.FETCH.PRODUCEIN.LIST", parameter, produceIn);
            @SuppressWarnings("unchecked")
            List<ProduceIn> prolist = ps.getItems();
            for(ProduceIn pro : prolist) {
    			//单位名称
    			DicView dicView = dicViewService.getById("v_mat_units",pro.getUnit());
    			pro.setUnitName(dicView.getName());
    			DicView dicView1 = dicViewService.getById("v_produceline",pro.getProduceLine());
    			pro.setProduceLineName(dicView1.getName());
    		}
            ps.setItems(prolist);
        } catch (Exception e) {
            e.printStackTrace();
        }
		return ps;
	}
	
	/**
	 * 获取生产工单产出
	 * @param produceOut
	 * @return
	 */
	public PaginationSupport findProduceOutList(ProduceOut produceOut) {
		String pwoId = produceOut.getPwoId();
		Object[] parameter = { pwoId };
		PaginationSupport ps = null;
        try {
            ps=genericDao.getPageWithParams(
     				"EQU.FETCH.PRODUCEOUT.LIST", parameter, produceOut);
            @SuppressWarnings("unchecked")
            List<ProduceOut> prolist = ps.getItems();
            for(ProduceOut pro : prolist) {
    			//单位名称
    			DicView dicView1 = dicViewService.getById("v_mat_units",pro.getUnit());
    			pro.setUnitName(dicView1.getName());
    		}
            ps.setItems(prolist);
        } catch (Exception e) {
            e.printStackTrace();
        }
		return ps;
	}
	
	/**
	 * 获取生产工单投入详情
	 * @param id
	 * @return
	 */
	public ProduceIn getProduceInById(String id) {
		ProduceIn pro = null;
        try {
            pro = (ProduceIn) genericDao.getById(ProduceIn.class, id);
            if(!StingUtil.isEmpty(pro)){
    			DicView dicView1 = dicViewService.getById("v_mat_units",pro.getUnit());
    			pro.setUnitName(dicView1.getName());
    		}
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pro;
	}
	
	/**
	 * 添加/修改生产工单投入
	 * @param produceIn
	 * @return
	 */
	public ProduceIn saveProduceIn(ProduceIn produceIn) {
		Timestamp time = new Timestamp(System.currentTimeMillis()); 
		try {
			if(!StringUtils.isBlank(produceIn.getPid()) == false){
				produceIn.setPid(null);
    			DicView dicView1 = dicViewService.getById("v_equ_info",produceIn.getEquCode());
    			String matName = getMatDicByCode(produceIn.getMatCode());
    			produceIn.setEquName(dicView1.getName());
    			produceIn.setMatName(matName);
    			produceIn.setInputSt(Timestamp.valueOf(produceIn.getInputStVo()));
    			produceIn.setInputEt(Timestamp.valueOf(produceIn.getInputEtVo()));
    			produceIn.setSysFlag(Constants.SYS_FLAG_USEING);
    			produceIn.setCreator(securityService.getOperator());
    			produceIn.setCreateTime(time);
				genericDao.save(produceIn);
			}else{
				ProduceIn proIn = (ProduceIn) this.genericDao.getById(ProduceIn.class, produceIn.getPid());
    			DicView dicView1 = dicViewService.getById("v_equ_info",produceIn.getEquCode());
    			String matName = getMatDicByCode(produceIn.getMatCode());
    			proIn.setProduceLine(produceIn.getProduceLine());
    			proIn.setMatCode(produceIn.getMatCode());
    			proIn.setMatName(matName);
    			proIn.setEquCode(produceIn.getEquCode());
    			proIn.setEquName(dicView1.getName());
    			proIn.setUnit(produceIn.getUnit());
    			proIn.setQuantity(produceIn.getQuantity());
    			proIn.setInputSt(Timestamp.valueOf(produceIn.getInputStVo()));
    			proIn.setInputEt(Timestamp.valueOf(produceIn.getInputEtVo()));
    			proIn.setMatLotno(produceIn.getMatLotno());
    			proIn.setLastModifier(securityService.getOperator());
    			proIn.setLastModifiedTime(time);
    			genericDao.save(proIn);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 删除生产工单投入记录
	 * @param ids
	 */
	public void deleteProduceIn(String ids) {
		Timestamp time = new Timestamp(System.currentTimeMillis()); 
		if (StringUtils.isBlank(ids)) {
			throw new RuntimeException("ids: IDS传入的参数为空!");
		}
		String[] idArray = ids.split(Constants.SEPARATOR_COLON);
		for (int i = 0; i < idArray.length; i++) {
			String id = idArray[i];
			ProduceIn produceIn = (ProduceIn) genericDao.getById(ProduceIn.class, id);
			produceIn.setSysFlag(Constants.SYS_FLAG_DELETED);
			produceIn.setLastModifier(securityService.getOperator());
			produceIn.setLastModifiedTime(time);
			genericDao.save(produceIn);
		}
	}
	
	/**
	 * 添加/修改生产工单产出
	 * @param produceOut
	 * @return
	 */
	public ProduceOut saveProduceOut(ProduceOut produceOut) {
		Timestamp time = new Timestamp(System.currentTimeMillis()); 
		try {
			if(!StringUtils.isBlank(produceOut.getPid()) == false){
				produceOut.setPid(null);
    			DicView dicView = dicViewService.getById("v_equ_info",produceOut.getObjectId());
    			String matName = getMatDicByCode(produceOut.getMatCode());
    			produceOut.setObjectCode(dicView.getCode());
    			produceOut.setObjectName(dicView.getName());
    			produceOut.setMatName(matName);
    			produceOut.setOutSt(Timestamp.valueOf(produceOut.getOutStVo()));
    			produceOut.setOutEt(Timestamp.valueOf(produceOut.getOutEtVo()));
    			produceOut.setSysFlag(Constants.SYS_FLAG_USEING);
    			produceOut.setCreator(securityService.getOperator());
    			produceOut.setCreateTime(time);
				genericDao.save(produceOut);
			}else{
				ProduceOut proOut = (ProduceOut) this.genericDao.getById(ProduceOut.class, produceOut.getPid());
    			DicView dicView = dicViewService.getById("v_equ_info",produceOut.getObjectId());
    			String matName = getMatDicByCode(produceOut.getMatCode());
//    			proOut.setCollectType(produceOut.getCollectType());
    			proOut.setObjectId(produceOut.getObjectId());
    			proOut.setObjectCode(dicView.getCode());
    			proOut.setObjectName(dicView.getName());
    			proOut.setMatCode(produceOut.getMatCode());
    			proOut.setMatName(matName);
    			proOut.setUnit(produceOut.getUnit());
    			proOut.setQuantity(produceOut.getQuantity());
    			proOut.setNgQuantity(produceOut.getNgQuantity());
    			proOut.setLacation(produceOut.getLacation());
    			proOut.setOutSt(Timestamp.valueOf(produceOut.getOutStVo()));
    			proOut.setOutEt(Timestamp.valueOf(produceOut.getOutEtVo()));
    			proOut.setWarehouse(produceOut.getWarehouse());
    			proOut.setLastModifier(securityService.getOperator());
    			proOut.setLastModifiedTime(time);
    			genericDao.save(proOut);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 删除生产工单产出记录
	 * @param ids
	 */
	public void deleteProduceOut(String ids) {
		Timestamp time = new Timestamp(System.currentTimeMillis()); 
		if (StringUtils.isBlank(ids)) {
			throw new RuntimeException("ids: IDS传入的参数为空!");
		}
		String[] idArray = ids.split(Constants.SEPARATOR_COLON);
		for (int i = 0; i < idArray.length; i++) {
			String id = idArray[i];
			ProduceOut produceOut = (ProduceOut) genericDao.getById(ProduceOut.class, id);
			produceOut.setSysFlag(Constants.SYS_FLAG_DELETED);
			produceOut.setLastModifier(securityService.getOperator());
			produceOut.setLastModifiedTime(time);
			genericDao.save(produceOut);
		}
	}
	
	/**
	 * 获取生产工单产出详情
	 * @param id
	 * @return
	 */
	public ProduceOut getProduceOutById(String id) {
		ProduceOut pro = null;
        try {
            pro = (ProduceOut) genericDao.getById(ProduceOut.class, id);
            if(!StingUtil.isEmpty(pro)){
    			DicView dicView1 = dicViewService.getById("v_mat_units",pro.getUnit());
    			pro.setUnitName(dicView1.getName());
    		}
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pro;
	}
	
	public String getNewPwoSn(String pwoType, String pwoDate)
	{
		Integer pwoSnCount = getProduceCount(pwoType, pwoDate);
		pwoSnCount++;
		String newIndex = pwoSnCount.toString();
		String pattern = "0000";
		newIndex =  pattern.substring(newIndex.length()) + newIndex;		
		return newIndex;
	}
	
	/**
	 * 获取指定工单类型的指定日期当天的单据数量
	 * @param pwoType 工单类型
	 * @param pwoDate 工单日期
	 * @return
	 */
	public Integer getProduceCount(String pwoType, String pwoDate)
	{
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("SELECT max(pro.pwoSn) ");
		strBuffer.append("FROM tw.business.entity.produce.ProduceWo pro ");
		strBuffer.append("Where pro.sysFlag = '1' ");
		strBuffer.append("AND pro.pwoType = '");
		strBuffer.append(pwoType);
		strBuffer.append("' ");
		strBuffer.append("AND Date(pro.planSt) = '");
		strBuffer.append(pwoDate);
		strBuffer.append("' ");
		String hql = strBuffer.toString();
		List resultList = genericDao.getListByHql(hql);
		if (resultList == null || resultList.size() == 0)
		{
			return 0;
		}
		
		String maxSn = (String)resultList.get(0);
		if (StringUtils.isBlank(maxSn))
		{
			return 0;
		}
		
		Integer billCount = objToInteger(maxSn.substring(maxSn.length()-4));
		return billCount;		
	}
	
	/**
	 * 把一个对象转换成Integer型数据
	 * 当obj为null时，返回null
	 * @param obj
	 * @return
	 */
	public final static Integer objToInteger(Object obj)
	{
		if (obj == null)
		{
			return null;
		}
		
		return Integer.valueOf(obj.toString());
	}
}
