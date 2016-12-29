package sail.beans.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sail.beans.dao.GenericDao;
import sail.beans.entity.BatBatAdjust;
import sail.beans.entity.BatBatAdjustDetail;
import sail.beans.entity.BatDepotIoBill;
import sail.beans.entity.BatDepotIoDetail;
import sail.beans.entity.BatDepotIoDetailList;
import sail.beans.entity.BatWorkOrder;
import sail.beans.entity.CarCode;
import sail.beans.entity.CarCodeRule;
import sail.beans.support.DateBean;

@Service
public class BatchStorageService {
	@Autowired
	private GenericDao genericDao;  
	
	/**
	 * 出入库后台服务
	 * @param billNo
	 * @param docType
	 * @param matBatch
	 * @return
	 */
	public BatDepotIoDetail saveBatchInStorage(String billNo,String docType,String matBatch){
		List<BatDepotIoBill> billList = genericDao.getListWithVariableParas("STORAGE.T_BAT_DEPOT_IOBILLLIST.LIST", new Object[]{billNo,docType});
		BatDepotIoBill batDepotIoBill = null;
		if (billList != null && billList.size() > 0){
			batDepotIoBill = billList.get(0);
		}
//		CarCode carCode = this.getResolveValue(matBatch);
//		String matcode = "0006";
//		String matcode = matBatch.substring(1, 9);
		BatDepotIoDetail batDepotIoDetail = null;
		List<BatDepotIoDetail> detailList = genericDao.getListWithVariableParas("STORAGE.T_BAT_DEPOT_IOBILLDETAILLIST.LIST", new Object[]{batDepotIoBill.getPid(),matBatch});
		if(detailList != null && detailList.size() > 0){
			batDepotIoDetail = detailList.get(0);
			genericDao.save(batDepotIoDetail);
		}
		
		return batDepotIoDetail;
		
	}
	
	/**
	 * 根据单号和类型查询出明细数据
	 * @param billNo
	 * @param docType
	 * @return
	 */
	public List<BatDepotIoDetail> getBatDepotIoDetailList(String billNo,String docType){
		List<BatDepotIoDetail> detailList = genericDao.getListWithVariableParas("STORAGE.T_BAT_DEPOT_IOBILLDETAIL.LIST", new Object[]{billNo,docType});
		return detailList;
	}
	
	/**
	 * 删除对应明细数据
	 * @param detailpid
	 */
	@SuppressWarnings("finally")
	public boolean deleteBatDepotIoDetail(String detailpid,String operuser){
		boolean falg = false;
		try{
		BatDepotIoDetail batDepotIoDetail = (BatDepotIoDetail)genericDao.getById(BatDepotIoDetail.class,detailpid);
		if (batDepotIoDetail == null){
			return falg;
		}
		batDepotIoDetail.setSysflag("0");
		batDepotIoDetail.setLastmodifiedtime(DateBean.getSysdateTime());
		batDepotIoDetail.setLastmodifier(operuser);
		genericDao.save(batDepotIoDetail);
		falg = true;
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			return falg;
		}
		
	}
	
	/**
	 * 条形码解析
	 * @param matCode
	 */
	public CarCode getResolveValue(String matCode){
		CarCode carCode = new CarCode();
		List<BatDepotIoDetail> ruleList=genericDao.getListWithVariableParas("BATCHDATA_BAT_DEPOT_IODETAIL", new Object[]{matCode});
		if(ruleList!=null){
			BatDepotIoDetail batdepot=ruleList.get(0);
			carCode.setAmount(batdepot.getQuantity()+"");
			carCode.setMatcode(batdepot.getMatcode());
			carCode.setMatname(batdepot.getMatname());
			carCode.setUnit(batdepot.getUnit());
		}else{
			List<BatDepotIoDetailList> ruleListx=genericDao.getListWithVariableParas("BATCHDATA_BAT_DEPOT_IODETAILIST", new Object[]{matCode});
			BatDepotIoDetailList batdepot=ruleListx.get(0);
			String code=batdepot.getBillpid();
			List<BatDepotIoDetail> ruleList1=genericDao.getListWithVariableParas("BATCHDATA_DEPOT_IODETAIL_BYPID", new Object[]{code});
			BatDepotIoDetail batdepot1=ruleList1.get(0);
			carCode.setAmount(batdepot.getQuantity()+"");
			carCode.setMatcode(batdepot1.getMatcode());
			carCode.setMatname(batdepot1.getMatname());
			carCode.setUnit(batdepot.getUnit());
		}
		return carCode;
		}
		
	
	/**
	 * 物资批次大小件关系调整
	 * @param masterbatch
	 * @param slavebatch
	 * @param operuser
	 * @return
	 */
	public BatBatAdjustDetail saveBatchAdjustment(String masterbatch,String slavebatch,String operuser){
		BatBatAdjustDetail batBatAdjustDetail = null;
		List<BatBatAdjustDetail> masterList = genericDao.getListWithVariableParas("STORAGE.T_BAT_BATADJUST_DETAILSLAVE.LIST", new Object[]{null,masterbatch});
		//判断是否已经存在改批次的数据
		if (masterList != null && masterList.size() > 0){
			List<BatBatAdjustDetail> BatBatAdjustDetailList = genericDao.getListWithVariableParas("STORAGE.T_BAT_BATADJUST_DETAILSLAVE.LIST", new Object[]{slavebatch,null});
			//判断该小件批次的数据是否已经扫描过
			if (BatBatAdjustDetailList != null && BatBatAdjustDetailList.size() > 0){
				batBatAdjustDetail = BatBatAdjustDetailList.get(0);
				batBatAdjustDetail.setIsrepeat("1");
				return batBatAdjustDetail;
			}else{
				batBatAdjustDetail = new BatBatAdjustDetail();
				batBatAdjustDetail = this.saveDetail(masterList.get(0).getAdjustpid(), operuser, slavebatch, masterbatch);
				return batBatAdjustDetail;
			}
		}else{
			BatBatAdjust batBatAdjust = new BatBatAdjust();
			batBatAdjust.setAdjustno(DateBean.getSysdate()+""+masterbatch);
			batBatAdjust.setActflag("1");
			batBatAdjust.setFactory("2200 ");
			batBatAdjust.setDepot("HZ10");
			batBatAdjust.setOperatetime(DateBean.getSysdateTime());
			batBatAdjust.setOperateuserid(operuser);
			batBatAdjust.setSysflag("1");
			batBatAdjust.setCreator(operuser);
			batBatAdjust.setCreatetime(DateBean.getSysdateTime());
			genericDao.save(batBatAdjust);
			batBatAdjustDetail = this.saveDetail(batBatAdjust.getPid(), operuser, slavebatch, masterbatch);
			return batBatAdjustDetail;
		}
	}
	
	/**
	 * 组装明细数据
	 * @param adjustpid
	 * @param operuser
	 * @param slavebatch
	 * @param masterbatch
	 * @return
	 */
	public BatBatAdjustDetail saveDetail(String adjustpid,String operuser,String slavebatch,String masterbatch){
		BatBatAdjustDetail batBatAdjustDetail = new BatBatAdjustDetail();
		batBatAdjustDetail.setAdjustpid(adjustpid);
//		CarCode carCode = new CarCode();
//		this.getResolveValue(matBatch, carCode);
//		private String matcode;
//		private String matname;
		batBatAdjustDetail.setSlavebatch(slavebatch);
//		private String oldmasterbatch;
		batBatAdjustDetail.setNewmasterbatch(masterbatch);
//		private String suppliersortcode;
		batBatAdjustDetail.setSysflag("1");
		batBatAdjustDetail.setCreator(operuser);
		batBatAdjustDetail.setCreatetime(DateBean.getSysdateTime());
		genericDao.save(batBatAdjustDetail);
		return batBatAdjustDetail;
	}
	
	/**
	 * 根据大件批次获取小件批次的数据
	 * @param masterbatch
	 * @return
	 */
	public List<BatBatAdjustDetail> getBatBatAdjustDetail(String masterbatch){
		List<BatBatAdjustDetail> BatBatAdjustDetailList = genericDao.getListWithVariableParas("STORAGE.T_BAT_BATADJUST_DETAILSLAVE.LIST", new Object[]{null,masterbatch});
		return BatBatAdjustDetailList;
	}
	
	/**
	 * 删除对应的小批次数据
	 * @param pid
	 * @param operuser
	 * @return
	 */
	public boolean deleteBatBatAdjustDetail(String pid,String operuser){
		boolean falg = false;
		BatBatAdjustDetail batBatAdjustDetail = (BatBatAdjustDetail)genericDao.getById(BatBatAdjustDetail.class, pid);
		batBatAdjustDetail.setSysflag("0");
		batBatAdjustDetail.setLastmodifier(operuser);
		batBatAdjustDetail.setLastmodifiedtime(DateBean.getSysdateTime());
		genericDao.save(batBatAdjustDetail);
		falg = true;
		return falg;
	}
	

}
