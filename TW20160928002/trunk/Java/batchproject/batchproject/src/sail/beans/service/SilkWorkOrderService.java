package sail.beans.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import sail.beans.dao.GenericDao;
import sail.beans.entity.BatDepotIoBill;
import sail.beans.entity.BatDepotIoDetail;
import sail.beans.entity.BatDepotIoDetailList;
import sail.beans.entity.BatSpiceTurn;
import sail.beans.entity.BatWorkOrder;
import sail.beans.entity.BatWorkOrderInput;
import sail.beans.entity.BatWorkOrderOutput;
import sail.beans.entity.CarCode;
import sail.beans.entity.vo.BatWorkOrderVo;
import sail.beans.support.DateBean;

/**
 * 投料记录服务类
 * */
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
		String date=DateBean.getBeforDay(DateBean.getSysdate(), 1);
		List<BatWorkOrder> workorderList = genericDao.getListWithVariableParas("WORKORDER.T_BAT_WORKORDER.LIST", new Object[]{type});
		List<BatWorkOrderVo> voList = new ArrayList<BatWorkOrderVo>();
		String workodercode=null;
		if (workorderList != null && workorderList.size() > 0){
			for (int i = 0; i < workorderList.size() ; i ++){
				BatWorkOrder batWorkOrder = workorderList.get(i);
				BatWorkOrderVo batWorkOrderVo = new BatWorkOrderVo();
				batWorkOrderVo.setPid(batWorkOrder.getPid());
				batWorkOrderVo.setWorkordercode(batWorkOrder.getWorkordercode());
				if(type.equals("ZP15")){
					workodercode=batWorkOrder.getMatname()+"-"+batWorkOrder.getWorkordercode();
					batWorkOrderVo.setWorkordercode(workodercode);
				}
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
	 */
	public BatWorkOrderInput saveBatWorkOrderInput(String workOrderCode,String matBatch,String quantity,String location,String operuser, String tl_type,String remark,String type){
		BatWorkOrderInput batWorkOrderInput = null;
		try {
			CarCode carCode = batchStorageService.getResolveValue(matBatch,type);
			if(carCode.getMatcode()!=null) {
				if("4".equals(carCode.getValue2())){
					batWorkOrderInput=this.saveBatWorkOrderInput2(workOrderCode,matBatch,quantity,location,operuser,remark,carCode);
					return batWorkOrderInput;
				}			
				List<BatWorkOrder> batWorkList = genericDao.getListWithVariableParas("WORKORDER.T_BAT_WORKORDERLIST.LIST", new Object[]{workOrderCode});
				if (batWorkList != null && batWorkList.size() > 0){
					BatWorkOrder BatWorkOrder = batWorkList.get(0);
					List<BatWorkOrderInput> inputList = genericDao.getListWithVariableParas("WORKORDER.T_BAT_WORKORDER_INPUTLIST.LIST", new Object[]{matBatch,workOrderCode,null});
					if (inputList != null && inputList.size() > 0){
						batWorkOrderInput = inputList.get(0);
						batWorkOrderInput.setIsrepair("1");
					}else{
						batWorkOrderInput = new BatWorkOrderInput();
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
						batWorkOrderInput.setTltype("0");
						batWorkOrderInput.setMatbatch(matBatch);
						batWorkOrderInput.setMatcode(carCode.getMatcode());
						batWorkOrderInput.setMatname(carCode.getMatname());
						if (location != null && !"".equals(location)){
							batWorkOrderInput.setLocation(location);
						}
						batWorkOrderInput.setMatname(carCode.getMatname());
						if(quantity==null){
							batWorkOrderInput.setQuantity(carCode.getAmount());
						}else{
							batWorkOrderInput.setQuantity(Double.parseDouble(quantity));
						}
						if(remark!=null&&!remark.equals("")){
							batWorkOrderInput.setRemark3(remark);
						}
						batWorkOrderInput.setUnit(carCode.getUnit());
						batWorkOrderInput.setStarttime(DateBean.getSysdateTime());
						batWorkOrderInput.setEndtime(DateBean.getSysdateTime());
						batWorkOrderInput.setOperatetime(DateBean.getSysdateTime());
						batWorkOrderInput.setOperateuserid(operuser);
						batWorkOrderInput.setSysflag("1");
						batWorkOrderInput.setCreator(operuser);
						batWorkOrderInput.setCreatetime(DateBean.getSysdateTime());
						batWorkOrderInput.setLastmodifier(operuser);
						batWorkOrderInput.setLastmodifiedtime(DateBean.getSysdateTime());
						List matList=matBomService.getBomByWorkOrder(BatWorkOrder,null,carCode.getMatcode().toString());
						if(matList.size()==0)
							batWorkOrderInput.setRemark4("0");
						genericDao.save(batWorkOrderInput);
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException();
		}
		return batWorkOrderInput;
	}
	
	/**
	 * 保存稀释液小批次投料明细数据
	 * @param workOrderCode
	 * @param matBatch
	 * @param quantity
	 * @param operuser
	 * @param tl_type 
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class) 
	private BatWorkOrderInput saveBatWorkOrderInput2(String workOrderCode, String matBatch,String quantity, String location, String operuser,
			String remark,CarCode carCode) {
		// TODO Auto-generated method stub
		BatWorkOrderInput batWorkOrderInput = null;
		try {
			List<BatSpiceTurn> batSpiceTurnList = genericDao.getListWithVariableParas("WORKORDER.T_BAT_SPICETURN.LIST", new Object[]{matBatch,null});
			if(batSpiceTurnList!=null&&batSpiceTurnList.size()>0){
				BatSpiceTurn batSpiceTurn=batSpiceTurnList.get(0);
				if("1".equals(batSpiceTurn.getIscost())){
					batWorkOrderInput=new BatWorkOrderInput();
					batWorkOrderInput.setIsrepair("1");
				}else{
					batSpiceTurn.setSlave_batch(matBatch);
					if(quantity==null){
						batSpiceTurn.setQuantity(carCode.getAmount());
					}else{
						batSpiceTurn.setQuantity(Double.parseDouble(quantity));
					}
					batSpiceTurn.setIscost("1");
					batSpiceTurn.setWorkorderin(workOrderCode);
					if(location!=null)
						batSpiceTurn.setInpotcode(location);
					batSpiceTurn.setOperator2(operuser);
					batSpiceTurn.setOperatetime2(DateBean.getSysdateTime());
					batSpiceTurn.setLastmodifier(operuser);
					batSpiceTurn.setLastmodifiedtime(DateBean.getSysdateTime());
					this.genericDao.save(batSpiceTurn);
					//把小件扫码归集到对应大件投料表中
					List<BatWorkOrder> batWorkList = genericDao.getListWithVariableParas("WORKORDER.T_BAT_WORKORDERLIST.LIST", new Object[]{workOrderCode});
					if (batWorkList != null && batWorkList.size() > 0){
						BatWorkOrder BatWorkOrder = batWorkList.get(0);
						List<BatWorkOrderInput> inputList = genericDao.getListWithVariableParas("WORKORDER.T_BAT_WORKORDER_INPUTLIST.LIST", new Object[]{batSpiceTurn.getMasterbatch(),workOrderCode,null});
						if (inputList != null && inputList.size() > 0){
							batWorkOrderInput = inputList.get(0);
							double quality=batSpiceTurn.getQuantity()+batWorkOrderInput.getQuantity();
							batWorkOrderInput.setQuantity(quality);
							batWorkOrderInput.setLastmodifier(operuser);
							batWorkOrderInput.setLastmodifiedtime(DateBean.getSysdateTime());
						}else{
							batWorkOrderInput = new BatWorkOrderInput();
							batWorkOrderInput.setWorkorderpid(BatWorkOrder.getPid());
							batWorkOrderInput.setTltype("0");
							batWorkOrderInput.setMatbatch(batSpiceTurn.getMasterbatch());
							batWorkOrderInput.setMatcode(batSpiceTurn.getMatecode());
							batWorkOrderInput.setMatname(batSpiceTurn.getMatename());
							if (location != null && !"".equals(location)){
								batWorkOrderInput.setLocation(location);
							}
							batWorkOrderInput.setQuantity(batSpiceTurn.getQuantity());
							if(remark!=null&&!remark.equals("")){
								batWorkOrderInput.setRemark3(remark);
								if(remark.equals("3")){
									batWorkOrderInput.setTltype("1");
								}
							}
							batWorkOrderInput.setUnit("KG");
							batWorkOrderInput.setStarttime(DateBean.getSysdateTime());
							batWorkOrderInput.setEndtime(DateBean.getSysdateTime());
							batWorkOrderInput.setOperatetime(DateBean.getSysdateTime());
							batWorkOrderInput.setOperateuserid(operuser);
							batWorkOrderInput.setSysflag("1");
							batWorkOrderInput.setCreator(operuser);
							batWorkOrderInput.setCreatetime(DateBean.getSysdateTime());
							batWorkOrderInput.setLastmodifier(operuser);
							batWorkOrderInput.setLastmodifiedtime(DateBean.getSysdateTime());
							List matList=matBomService.getBomByWorkOrder(BatWorkOrder,null,carCode.getMatcode().toString());
							if(matList.size()==0)
								batWorkOrderInput.setRemark4("0");
							genericDao.save(batWorkOrderInput);
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException();
		}
		
		return batWorkOrderInput;

	}

	/**
	 * 根据ID删除对应的数据
	 * @param pid
	 * @param operuser
	 * @return boolean
	 */
	public boolean deleteBatWorkOrderInput(String pid, String userId) {
		boolean falg = false;
		BatWorkOrderInput BatWorkOrderInput = (BatWorkOrderInput)genericDao.getById(BatWorkOrderInput.class,pid);
		try{
			if (BatWorkOrderInput !=null){
				BatWorkOrderInput.setSysflag("0");
				BatWorkOrderInput.setLastmodifier(userId);
				BatWorkOrderInput.setLastmodifiedtime(DateBean.getSysdateTime());
				genericDao.save(BatWorkOrderInput);
				List<BatSpiceTurn> batSpiceTurnList = genericDao.getListWithVariableParas("WORKORDER.T_BAT_SPICETURN.LIST", new Object[]{null,BatWorkOrderInput.getMatbatch()});
				if(batSpiceTurnList!=null&&batSpiceTurnList.size()>0){
					for (int i = 0; i < batSpiceTurnList.size(); i++) {
						BatSpiceTurn batSpiceTurn=batSpiceTurnList.get(i);
						batSpiceTurn.setIscost("0");
						batSpiceTurn.setLastmodifier(userId);
						batSpiceTurn.setLastmodifiedtime(DateBean.getSysdateTime());
						genericDao.save(batSpiceTurn);
					}
				}
				falg = true;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return falg;
	}
	
	/**
	 * 根据工单获取明细数据
	 * @param workOrderCode
	 * @param tl_type 
	 * @param remark 
	 * @return
	 */
	public List<BatWorkOrderInput> getBatWorkOrderInput(String workOrderCode, String tl_type,String workOrderCodeType, String remark){
		List<BatWorkOrderInput> inputList=null;
		if(remark!=null&&!remark.equals("")){
			 inputList = genericDao.getListWithVariableParas("WORKORDER.T_BAT_WORKORDER_INPUTLIST.LIST", new Object[]{null,workOrderCode,remark});
		}else if("1".equals(tl_type)){
			 inputList = genericDao.getListWithVariableParas("WORKORDER.T_BAT_WORKORDER_INPUTLIST.LIST", new Object[]{null,workOrderCode,"3"});
		}
		else{
			 inputList = genericDao.getListWithVariableParas("WORKORDER.T_BAT_WORKORDER_INPUTLIST.LIST", new Object[]{null,workOrderCode,null});
		}
		return inputList;
	}
	/**
	 * 获取工单状态
	 * @param workOrderCode
	 * @return
	 */
	public BatWorkOrder getWorkorderstate(String billno){
		BatWorkOrder batWorkOrder=null;
		List<BatWorkOrder> ruleList=genericDao.getListWithVariableParas("WORKORDER.T_BAT_WORKORDERLIST.LIST", new Object[]{billno});
		if(ruleList!=null&&ruleList.size()>0){
		    batWorkOrder=ruleList.get(0);
			if("10".equals(batWorkOrder.getWorkorderstate())){
				batWorkOrder.setRemark("1"); //未过期
			}else
				batWorkOrder.setRemark("0");  //过期
		}
		return batWorkOrder;   //工单不存在
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
		String f_bill_no="SLXH"+DateBean.getSysdateTime();
		try{
			List<BatDepotIoDetail> detailList = genericDao.getListWithVariableParas("STORAGE.T_BAT_DEPOT_IOBILLDETAIL.LIST", new Object[]{null,"ZO40",null,f_mat_batch});
			CarCode carcode=batchStorageService.getResolveValue(f_mat_batch,"JM02");
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
				batDepotIoBill.setBilltype("12");
				batDepotIoBill.setDoctype("ZO40");
				batDepotIoBill.setDepot("HZ10 ");
				batDepotIoBill.setIsEnter("1");
				batDepotIoBill.setCreatetime(DateBean.getSysdateTime());
				batDepotIoBill.setCreator(userId);
				batDepotIoBill.setOperateuserid(userId);
				batDepotIoBill.setOperatetime(DateBean.getSysdateTime());
				this.genericDao.save(batDepotIoBill);
				//List<BatDepotIoBill> billList = genericDao.getListWithVariableParas("STORAGE.T_BAT_DEPOT_IOBILLLIST.LIST", new Object[]{f_bill_no,null});
				String p_id=batDepotIoBill.getPid();
				
				BatDepotIoDetail batDepotIoDetail1=new BatDepotIoDetail();
				batDepotIoDetail1.setMatcode(carcode.getMatcode());
				batDepotIoDetail1.setMatname(carcode.getMatname());
				batDepotIoDetail1.setMatcode(carcode.getMatcode());
				batDepotIoDetail1.setQuantity(carcode.getAmount());
				batDepotIoDetail1.setStatus(carcode.getState());
				batDepotIoDetail1.setUnit(carcode.getUnit());
				batDepotIoDetail1.setIsEnter("1");
				batDepotIoDetail1.setRemark5("2");
				batDepotIoDetail1.setBillpid(p_id);
				batDepotIoDetail1.setShkzg("H");
				batDepotIoDetail1.setStatus("A");
				batDepotIoDetail1.setInventorytype("0");
				batDepotIoDetail1.setSuppliersortcode("01");//产出表找不到，暂为01
				batDepotIoDetail1.setMatbatch(f_mat_batch);
				batDepotIoDetail1.setCreatetime(DateBean.getSysdateTime());
				batDepotIoDetail1.setCreator(userId);
				batDepotIoDetail1.setReason(reason);
				batDepotIoDetail1.setSysflag("1");
				this.genericDao.save(batDepotIoDetail1);
				batDepotIoDetail=batDepotIoDetail1;
			}else{
				return batDepotIoDetail;
			}
			
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
		
		return batDepotIoDetail;

	}
/**
 * 片烟投料剔除
 * */
	public boolean deleteIeafInput(String batch, String userId) {
		boolean falg = false;
		List<BatWorkOrderInput> BatWorkOrderInputList = genericDao.getListWithVariableParas("ieafInput.getbatworkinput.bybatch", new Object[]{batch});
		if (BatWorkOrderInputList !=null){
			BatWorkOrderInput batWorkOrderInput=BatWorkOrderInputList.get(0);
			batWorkOrderInput.setLastmodifier(userId);
			batWorkOrderInput.setTltype("1");
			batWorkOrderInput.setLastmodifiedtime(DateBean.getSysdateTime());
			genericDao.save(batWorkOrderInput);
			falg = true;
		}
		return falg;
	}
}
	
