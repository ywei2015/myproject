package sail.beans.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sail.beans.dao.GenericDao;
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
				batWorkOrderInput.setTltype("2");
				batWorkOrderInput.setMatbatch(matBatch);
				batWorkOrderInput.setMatcode(carCode.getMatcode());
				if (location != null && !"".equals(location)){
					batWorkOrderInput.setLocation(location);
				}
				batWorkOrderInput.setMatname(carCode.getMatname());
				batWorkOrderInput.setQuantity(Double.parseDouble(quantity));
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
}
