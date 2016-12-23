package sail.beans.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sail.beans.dao.GenericDao;
import sail.beans.entity.BatWorkOrder;
import sail.beans.entity.BatWorkOrderInput;
import sail.beans.entity.CarCode;
import sail.beans.support.DateBean;
@Service
public class RollBatchService {

	@Autowired
	private GenericDao genericDao;  
	
	@Autowired
	BatchStorageService batchStorageService;
	
	/**
	 * 卷包批次保存
	 * @param workOrderCode
	 * @param machineId
	 * @param matBatch
	 * @param operuser
	 * @return
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public BatWorkOrderInput saveBatWorkOrderInput(String workOrderCode,String machineId,String matBatch,String operuser) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		List<BatWorkOrder> batWorkList = genericDao.getListWithVariableParas("WORKORDER.T_BAT_WORKORDERLIST.LIST", new Object[]{workOrderCode});
		BatWorkOrderInput batWorkOrderInput = null;
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
				batWorkOrderInput.setMatname(carCode.getMatname());
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
}
