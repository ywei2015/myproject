package sail.beans.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sail.beans.dao.GenericDao;
import sail.beans.entity.BatDepotIoBill;
import sail.beans.entity.BatDepotIoDetail;
import sail.beans.entity.BatWorkOrder;
import sail.beans.entity.BatWorkOrderInput;
import sail.beans.entity.CarCode;
import sail.beans.entity.vo.BatWorkOrderVo;
import sail.beans.support.DateBean;

@Service
public class SilkWorkOrderService {

	@Autowired
	private GenericDao genericDao;  
	
	@Autowired
	BatchStorageService batchStorageService;
	
	/**
	 * 验证工单和批次是否匹配
	 * @param workorderno
	 * @param matbatch
	 * @return
	 */
	public boolean isExistence(String workorderno,String matbatch){
		boolean falg = false;
		return falg;
	}
	
	/**
	 * 根据工单类型获取对应的工单
	 * @param type
	 * @return
	 */
	public List<BatWorkOrderVo> getWorkOrderList(String type){
		List<BatWorkOrder> workorderList = genericDao.getListWithVariableParas("WORKORDER.T_BAT_WORKORDER.LIST", new Object[]{type});
		List<BatWorkOrderVo> voList = new ArrayList<BatWorkOrderVo>();
		if (workorderList != null && workorderList.size() > 0){
			for (int i = 0; i < workorderList.size() ; i ++){
				BatWorkOrder batWorkOrder = workorderList.get(i);
				BatWorkOrderVo batWorkOrderVo = new BatWorkOrderVo();
				batWorkOrderVo.setPid(batWorkOrder.getPid());
				batWorkOrderVo.setWorkordercode(batWorkOrder.getWorkordercode());
				batWorkOrderVo.setWorkordertype(batWorkOrder.getWorkordertype());
				voList.add(batWorkOrderVo);
			}
		}
		return voList;
	}
	
	/**
	 * 保存投料明细数据
	 * @param workOrderCode
	 * @param matBatch
	 * @param quantity
	 * @param operuser
	 * @return
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public BatWorkOrderInput saveBatWorkOrderInput(String workOrderCode,String matBatch,String quantity,String location,String operuser) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		BatWorkOrderInput batWorkOrderInput = null;
		List<BatWorkOrder> batWorkList = genericDao.getListWithVariableParas("WORKORDER.T_BAT_WORKORDERLIST.LIST", new Object[]{workOrderCode});
		if (batWorkList != null && batWorkList.size() > 0){
			BatWorkOrder BatWorkOrder = batWorkList.get(0);
			List<BatWorkOrderInput> inputList = genericDao.getListWithVariableParas("WORKORDER.T_BAT_WORKORDER_INPUT.LIST", new Object[]{matBatch});
			if (inputList != null && inputList.size() > 0){
				batWorkOrderInput = inputList.get(0);
				batWorkOrderInput.setIsrepair("1");
			}else{
				batWorkOrderInput = new BatWorkOrderInput();
				CarCode carCode = batchStorageService.getResolveValue(matBatch);
				batWorkOrderInput.setWorkorderpid(BatWorkOrder.getPid());
				batWorkOrderInput.setTltype("2");//
				batWorkOrderInput.setMatbatch(matBatch);
				batWorkOrderInput.setMatcode(carCode.getMatcode());
				batWorkOrderInput.setMatname(carCode.getMatname());
				if (location != null && !"".equals(location)){
					batWorkOrderInput.setLocation(location);
				}
				batWorkOrderInput.setMatname(carCode.getMatname());
				if(quantity==null){
					batWorkOrderInput.setQuantity(Double.parseDouble(carCode.getAmount()));
				}else{
					batWorkOrderInput.setQuantity(Double.parseDouble(quantity));
				}
				batWorkOrderInput.setUnit(carCode.getUnit());
				batWorkOrderInput.setStarttime(DateBean.getSysdateTime());
				batWorkOrderInput.setEndtime(DateBean.getSysdateTime());
				batWorkOrderInput.setOperatetime(DateBean.getSysdateTime());
				batWorkOrderInput.setOperateuserid(operuser);
				batWorkOrderInput.setSysflag("1");
				batWorkOrderInput.setCreator(operuser);
				batWorkOrderInput.setCreatetime(DateBean.getSysdateTime());
				genericDao.save(batWorkOrderInput);
			}
		}
		
		return batWorkOrderInput;
	}
	
	/**
	 * 根据ID删除对应的数据
	 * @param pid
	 * @param operuser
	 * @return
	 */
	public boolean deleteBatWorkOrderInput(String pid,String operuser){
		boolean falg = false;
		BatWorkOrderInput BatWorkOrderInput = (BatWorkOrderInput)genericDao.getById(BatWorkOrderInput.class,pid);
		if (BatWorkOrderInput !=null){
			BatWorkOrderInput.setSysflag("0");
			BatWorkOrderInput.setLastmodifier(operuser);
			BatWorkOrderInput.setLastmodifiedtime(DateBean.getSysdateTime());
			genericDao.save(BatWorkOrderInput);
			falg = true;
		}
		return falg;
	}
	
	
	/**
	 * 根据工单获取明细数据
	 * @param workOrderCode
	 * @return
	 */
	public List<BatWorkOrderInput> getBatWorkOrderInput(String workOrderCode){
		List<BatWorkOrderInput> inputList = genericDao.getListWithVariableParas("WORKORDER.T_BAT_WORKORDER_INPUTLIST.LIST", new Object[]{workOrderCode});
		return inputList;
	}
	/**
	 * 获取工单状态
	 * @param workOrderCode
	 * @return
	 */
	public boolean getWorkorderstate(String billno){
		List<BatWorkOrder> ruleList=genericDao.getListWithVariableParas("GET_WORKSTATE_BYBILLNO", new Object[]{billno});
		BatWorkOrder bill=ruleList.get(0);
		if("1".equals(bill.getWorkorderstate())){
			return true;
		}
		return false;
	}

	public BatDepotIoDetail saveBatchStorageOut(String reason, String f_mat_batch, String user) {
		BatDepotIoBill batDepotIoBill=new BatDepotIoBill();
		CarCode carcode=new CarCode();
		carcode=batchStorageService.getResolveValue2(f_mat_batch);
		String billno="SLXH"+DateBean.getSysdateTime();
		batDepotIoBill.setBillno(billno);
		batDepotIoBill.setDoctype("ZO40");
		batDepotIoBill.setBiztype("MM2153");
		batDepotIoBill.setBilltype("12");
		batDepotIoBill.setFactory(carcode.getFactory());
		batDepotIoBill.setDepot(carcode.getDepot());
		batDepotIoBill.setCreatetime(DateBean.getSysdateTime());
		batDepotIoBill.setCreator(user);
		batDepotIoBill.setOperateuserid(user);
		batDepotIoBill.setOperatetime(DateBean.getSysdateTime());
		batDepotIoBill.setSysflag("1");
		genericDao.save(batDepotIoBill);
		List<BatDepotIoBill>billList= genericDao.getListWithVariableParas("STORAGE.T_BAT_DEPOT_IOBILLLIST.LIST", new Object[]{billno,null});
	    batDepotIoBill = billList.get(0);	
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
	    depotIoDetail.setReason(reason);
	    depotIoDetail.setSuppliersortcode(carcode.getStroecode());
	    genericDao.save(depotIoDetail);
	    depotIoDetail.setRemark(billno);
	    return depotIoDetail;
		}

	public List<BatDepotIoDetail> getBatDepotIoDetailList(String billNo) {
		List<BatDepotIoDetail> detailList = genericDao.getListWithVariableParas("STORAGE.T_BAT_DEPOT_IOBILLDETAIL.LIST", new Object[]{billNo,null});
		return detailList;
	}
	
	
}
