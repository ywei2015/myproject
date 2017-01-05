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
		/*List<BatDepotIoDetail> ruleList=genericDao.getListWithVariableParas("BATCHDATA_BAT_DEPOT_IODETAIL", new Object[]{matCode});
		if(ruleList!=null){
			BatDepotIoDetail batdepot=ruleList.get(0);
			carCode.setAmount(batdepot.getQuantity()+"");
			carCode.setMatcode(batdepot.getMatcode());
			carCode.setMatname(batdepot.getMatname());
			carCode.setUnit(batdepot.getUnit());
			carCode.setStroecode(batdepot.getSuppliersortcode());
			carCode.setValue2("1");
		
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
			carCode.setValue2("2");
			carCode.setOldbatch(batdepot1.getMatbatch());
			carCode.setStroecode(batdepot1.getSuppliersortcode());
			
			//List<BatDepotIoBill> ruleList2=(List<BatDepotIoBill>) genericDao.getById(BatDepotIoBill.class,batdepot.getBillpid());
			//BatDepotIoBill batDepotIoBill=ruleList2.get(0);
			//carCode.setFactory(batDepotIoBill.getFactory()==null?"":batDepotIoBill.getFactory());
			//carCode.setStroecode(batDepotIoBill.getDepot()==null?"":batDepotIoBill.getDepot());
		}
		carCode.setFactory("2200");
		carCode.setDepot("HZ10");*/
	
		carCode.setAmount("120");
		carCode.setMatcode("001");
		carCode.setMatname("物料1");
		carCode.setUnit("KG");
		carCode.setOldbatch("0002");
		carCode.setStroecode("01");
		carCode.setFactory("111");
		carCode.setStroecode("110");
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
		CarCode carcode=this.getResolveValue(slavebatch);
		batBatAdjustDetail.setMatcode(carcode.getMatcode());
		batBatAdjustDetail.setMatname(carcode.getMatname());
		batBatAdjustDetail.setOldmasterbatch(carcode.getOldbatch());
		batBatAdjustDetail.setSuppliersortcode(carcode.getStroecode());
		batBatAdjustDetail.setSlavebatch(slavebatch);
		batBatAdjustDetail.setNewmasterbatch(masterbatch);
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
	/**
	 * 出库保存接口
	 * @param 
	 * @param 
	 * @return
	 */
	public BatDepotIoDetail saveBatchStorageOut(String f_bill_no,String f_doc_type,String f_mat_batch,String user) {
		List<BatDepotIoBill> billList = genericDao.getListWithVariableParas("STORAGE.T_BAT_DEPOT_IOBILLLIST.LIST", new Object[]{f_bill_no,f_doc_type});
		BatDepotIoBill batDepotIoBill = null;
		CarCode carcode=new CarCode();
		carcode=this.getResolveValue(f_mat_batch);
		if (billList != null && billList.size() > 0){
			batDepotIoBill = billList.get(0);
		}
		if(batDepotIoBill==null){
			batDepotIoBill.setBillno(f_bill_no);
			batDepotIoBill.setDoctype(f_doc_type);
			batDepotIoBill.setBilltype("12");//需要调整
			batDepotIoBill.setBiztype("MM2152");//需要确定
			batDepotIoBill.setFactory(carcode.getFactory());
			batDepotIoBill.setDepot(carcode.getDepot());
			batDepotIoBill.setCreatetime(DateBean.getSysdateTime());
			batDepotIoBill.setCreator(user);
			batDepotIoBill.setOperateuserid(user);
			batDepotIoBill.setOperatetime(DateBean.getSysdateTime());
			batDepotIoBill.setSysflag("1");
			genericDao.save(batDepotIoBill);
			billList= genericDao.getListWithVariableParas("STORAGE.T_BAT_DEPOT_IOBILLLIST.LIST", new Object[]{f_bill_no,f_doc_type});
		    batDepotIoBill = billList.get(0);	
		}
		BatDepotIoDetail depotIoDetail=new BatDepotIoDetail();
		depotIoDetail.setBillpid(batDepotIoBill.getPid());
		depotIoDetail.setMatbatch(f_mat_batch);
		depotIoDetail.setMatcode(carcode.getMatcode());
		depotIoDetail.setMatname(carcode.getMatname());
		depotIoDetail.setQuantity(Double.parseDouble(carcode.getAmount()));
		depotIoDetail.setUnit(carcode.getUnit());
		depotIoDetail.setSysflag("1");
		depotIoDetail.setCreatetime(DateBean.getSysdateTime());
		depotIoDetail.setCreator(user);
	    depotIoDetail.setSuppliersortcode(carcode.getStroecode());
		genericDao.save(depotIoDetail);
		return depotIoDetail;
	}
	
	/**
	 *香料其它消耗业务解码
	 * @param batch
	 * @return
	 */
   public CarCode getResolveValue2(String batch){
	   CarCode carCode=new CarCode();
	 /*  carCode=batchStorageService.getResolveValue(batch);
	   if(carCode.getMatcode()!=null)
		   return carCode;
	   else{
		   List<BatWorkOrderInput> ruleList=genericDao.getListWithVariableParas("WORKORDER.T_BAT_WORKORDER_INPUT.LIST", new Object[]{batch});
		   BatWorkOrderInput workInput=ruleList.get(0);
		   carCode.setAmount(workInput.getQuantity()+"");
		   carCode.setMatcode(workInput.getMatcode());
		   carCode.setMatname(workInput.getMatname());
		   carCode.setUnit(workInput.getUnit());
		   carCode.setFactory("2200");
		   carCode.setStroecode("111");
		   carCode.setDepot("HZ10");
		   return carCode;
	   }*/
	    carCode.setAmount("120");
		carCode.setMatcode("001");
		carCode.setMatname("物料1");
		carCode.setUnit("KG");
		carCode.setOldbatch("0002");
		carCode.setFactory("2200");
		carCode.setStroecode("111");
		carCode.setDepot("HZ10");
		return carCode;
   }

}
