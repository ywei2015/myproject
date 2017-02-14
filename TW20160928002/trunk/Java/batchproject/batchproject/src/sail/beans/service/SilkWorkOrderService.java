package sail.beans.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sail.beans.dao.GenericDao;
import sail.beans.entity.BatDepotIoBill;
import sail.beans.entity.BatDepotIoDetail;
import sail.beans.entity.BatDepotIoDetailList;
import sail.beans.entity.BatWorkOrder;
import sail.beans.entity.BatWorkOrderInput;
import sail.beans.entity.BatWorkOrderOutput;
import sail.beans.entity.CarCode;
import sail.beans.entity.vo.BatWorkOrderVo;
import sail.beans.support.DateBean;

@Service
public class SilkWorkOrderService {

	@Autowired
	private GenericDao genericDao;  
	
	@Autowired
	BatchStorageService batchStorageService;
	
	@Autowired
	private MatBomService matBomService; 
	
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
	 * @param tl_type 
	 * @return
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public BatWorkOrderInput saveBatWorkOrderInput(String workOrderCode,String matBatch,String quantity,String location,String operuser, String tl_type){
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
				if(carCode==null) {
					return batWorkOrderInput;
				}
				if("w".equalsIgnoreCase(carCode.getState())){
					batWorkOrderInput.setRemark5("w");
				}else if("2".equalsIgnoreCase(carCode.getState())){
					batWorkOrderInput.setRemark5("2");
					return batWorkOrderInput;
				}else if("e".equalsIgnoreCase(carCode.getState())){
					batWorkOrderInput.setRemark5("e");
					return batWorkOrderInput;
				}
				batWorkOrderInput.setWorkorderpid(BatWorkOrder.getPid());
				if("1".equals(tl_type))
					batWorkOrderInput.setTltype("1");
				else{
					batWorkOrderInput.setTltype("2");//投料类型待确认
				}
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
				List matList=matBomService.getBomByWorkOrder(workOrderCode,null,matBatch);
				if(matList.size()==0)
					batWorkOrderInput.setRemark4("0");
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
	 * @param tl_type 
	 * @return
	 */
	public List<BatWorkOrderInput> getBatWorkOrderInput(String workOrderCode, String tl_type){
		List<BatWorkOrderInput> inputList=null;
		if("1".equals(tl_type)){
			 inputList = genericDao.getListWithVariableParas("WORKORDER.T_BAT_WORKORDER_INPUTLIST.LIST", new Object[]{workOrderCode});
		}else{
			 inputList = genericDao.getListWithVariableParas("WORKORDER.T_BAT_WORKORDER_INPUTLIST2.LIST", new Object[]{workOrderCode});
		}
		
		return inputList;
	}
	/**
	 * 获取工单状态
	 * @param workOrderCode
	 * @return
	 */
	public String getWorkorderstate(String billno){
		List<BatWorkOrder> ruleList=genericDao.getListWithVariableParas("GET_WORKSTATE_BYBILLNO", new Object[]{billno});
		if(ruleList!=null&&ruleList.size()>0){
			BatWorkOrder bill=ruleList.get(0);
			if("1".equals(bill.getWorkorderstate())){
				return "1";  //未过期
			}else
				return "0";  //过期
		}
		return "2";   //工单不存在
	}

	/**
	 * 香料其它消耗业务特殊出库保存
	 * @param workOrderCode
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class) 
	public BatDepotIoDetail saveBatchStorageOut(String reason, String f_mat_batch, String userId) {
		BatDepotIoBill batDepotIoBill = null;
		BatDepotIoDetail batDepotIoDetail=null;
		BatDepotIoDetailList batDepotIoDetailList=null;
		try{
			List<BatDepotIoDetail> detailList = genericDao.getListWithVariableParas("BATCHDATA_BAT_DEPOT_IODETAIL", new Object[]{f_mat_batch});
			CarCode carcode=batchStorageService.getResolveValue(f_mat_batch);
			String f_bill_no="SLXH"+DateBean.getSysdateTime();
			if (carcode.getMatcode()!=null){
				if (detailList != null && detailList.size() > 0){
					batDepotIoDetail = detailList.get(0);
					String p_id1=batDepotIoDetail.getPid();
					if("2".equals(batDepotIoDetail.getRemark5())){
						batDepotIoDetail.setRepeated("1");
						return batDepotIoDetail;
					}
				}
				batDepotIoBill=new BatDepotIoBill();
				batDepotIoBill.setDepot(carcode.getDepot());
				batDepotIoBill.setFactory("2200");
				batDepotIoBill.setBillno(f_bill_no);
				batDepotIoBill.setSysflag("1");
				batDepotIoBill.setBiztype("MM2153");
				batDepotIoBill.setBilltype("22");
				batDepotIoBill.setDoctype("ZO40");
				batDepotIoBill.setDepot("HZ10 ");
				batDepotIoBill.setIsEnter("1");
				batDepotIoBill.setCreatetime(DateBean.getSysdateTime());
				batDepotIoBill.setCreator(userId);
				batDepotIoBill.setOperateuserid(userId);
				batDepotIoBill.setOperatetime(DateBean.getSysdateTime());
				this.genericDao.save(batDepotIoBill);
				List<BatDepotIoBill> billList = genericDao.getListWithVariableParas("STORAGE.T_BAT_DEPOT_IOBILLLIST.LIST", new Object[]{f_bill_no,null});
				String p_id=billList.get(0).getPid();
				
				BatDepotIoDetail batDepotIoDetail1=new BatDepotIoDetail();
				batDepotIoDetail1.setMatcode(carcode.getMatcode());
				batDepotIoDetail1.setMatname(carcode.getMatname());
				batDepotIoDetail1.setMatcode(carcode.getMatcode());
				batDepotIoDetail1.setQuantity(Double.parseDouble(carcode.getAmount()));
				batDepotIoDetail1.setStatus(carcode.getState());
				batDepotIoDetail1.setUnit(carcode.getUnit());
				batDepotIoDetail1.setIsEnter("1");
				batDepotIoDetail1.setRemark5("2");
				batDepotIoDetail1.setBillpid(p_id);
				batDepotIoDetail1.setSuppliersortcode("01");//产出表找不到，暂为01
				batDepotIoDetail1.setMatbatch(f_mat_batch);
				batDepotIoDetail1.setCreatetime(DateBean.getSysdateTime());
				batDepotIoDetail1.setCreator(userId);
				batDepotIoDetail1.setReason(reason);
				batDepotIoDetail1.setSysflag("1");
				this.genericDao.save(batDepotIoDetail1);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return batDepotIoDetail;

	}
}
	
