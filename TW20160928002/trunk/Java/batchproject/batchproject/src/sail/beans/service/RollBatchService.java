package sail.beans.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	@Autowired
	private MatBomService matBomService; 
	
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
	public BatWorkOrderInput saveBatWorkOrderInput(String workOrderCode,String machineId,String matBatch,String operuser){
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
				batWorkOrderInput.setMain(BatWorkOrder);
				batWorkOrderInput.setTltype("2");
				batWorkOrderInput.setMatbatch(matBatch);
				batWorkOrderInput.setMatcode(carCode.getMatcode());
				batWorkOrderInput.setMatname(carCode.getMatname());
				batWorkOrderInput.setUnit(carCode.getUnit());
				batWorkOrderInput.setQuantity(Double.parseDouble(carCode.getAmount()));
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
	 * @return
	 */
	public List<BatWorkOrderInput> getBatWorkOrderInput(String workOrderCode){
		List<BatWorkOrderInput> inputList = genericDao.getListWithVariableParas("WORKORDER.T_BAT_WORKORDER_INPUTLIST2.LIST", new Object[]{workOrderCode});
		return inputList;
	}

	/**
	 * 根据工单获取工单状态
	 * @param workOrderCode
	 * @return
	 */
	public String getWorkorderstate(String workOrderCode) {
		List<BatWorkOrder> ruleList=genericDao.getListWithVariableParas("GET_WORKSTATE_BYBILLNO", new Object[]{workOrderCode});
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
	 * 根据生产工单类型获取机台与工单的对应关系
	 * @param workOrderCode
	 * @return
	 */
	public Map<String,List> getWorkOrderAndProcess(String workType){
		List<Object[]> workData=genericDao.getListWithNativeSql("WORKORDER.T_BAT_PROCESSID_LIST",new Object[]{workType});
		Map<String,List> workOrderMap=new HashMap();
		if(workData!=null&&workData.size()>0){
			for (int i = 0; i < workData.size(); i++) {
				Object[] rowData=workData.get(i);
				String processId=rowData[0].toString();
				if(processId!=null){
					if(!workOrderMap.containsKey(processId)){
						workOrderMap.put(processId, new ArrayList());
					}
					ArrayList processWorkList=(ArrayList) workOrderMap.get(processId);
					processWorkList.add(rowData[1]);
				}
			}
		}
		
		return workOrderMap;
	}
}
