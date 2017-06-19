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
	 * 卷包成型投料批次保存
	 * @param workOrderCode
	 * @param machineId
	 * @param matBatch
	 * @param operuser
	 */
	public BatWorkOrderInput saveBatWorkOrderInput(String workOrderCode,String machineId,String matBatch,String operuser){
		List<BatWorkOrder> batWorkList = genericDao.getListWithVariableParas("WORKORDER.T_BAT_WORKORDERLIST.LIST", new Object[]{workOrderCode});
		BatWorkOrderInput batWorkOrderInput = null;
		if (batWorkList != null && batWorkList.size() > 0){
			BatWorkOrder batWorkOrder = batWorkList.get(0);
			List<BatWorkOrderInput> inputList = genericDao.getListWithVariableParas("WORKORDER.T_BAT_WORKORDER_INPUTLIST.LIST", new Object[]{matBatch,workOrderCode,null});
			if (inputList != null && inputList.size() > 0){
				batWorkOrderInput = inputList.get(0);
				batWorkOrderInput.setIsrepair("1");
			}else{
				CarCode carCode = batchStorageService.getResolveValue(matBatch,"JM01");
				if(carCode.getMatcode()==null) {
					return batWorkOrderInput;
				}
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
				batWorkOrderInput.setWorkorderpid(batWorkOrder.getPid());
				batWorkOrderInput.setTltype("0");
				if(batWorkOrder.getWorkordertype().equals("ZP02"))
					batWorkOrderInput.setTltype("2");
				batWorkOrderInput.setMatbatch(matBatch);
				batWorkOrderInput.setMatcode(carCode.getMatcode());
				batWorkOrderInput.setMatname(carCode.getMatname());
				batWorkOrderInput.setUnit(carCode.getUnit());
				batWorkOrderInput.setQuantity(carCode.getAmount());
				batWorkOrderInput.setStarttime(DateBean.getSysdateTime());
				batWorkOrderInput.setEndtime(DateBean.getSysdateTime());
				batWorkOrderInput.setOperatetime(DateBean.getSysdateTime());
				batWorkOrderInput.setOperateuserid(operuser);
				batWorkOrderInput.setSysflag("1");
				batWorkOrderInput.setCreator(operuser);
				batWorkOrderInput.setCreatetime(DateBean.getSysdateTime());
				List matList=matBomService.getBomByWorkOrder(workOrderCode,null,carCode.getMatcode().toString());
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
		List<BatWorkOrderInput> inputList = genericDao.getListWithVariableParas("WORKORDER.T_BAT_WORKORDER_INPUTLIST.LIST", new Object[]{null,workOrderCode,null});
		return inputList;
	}

	/**
	 * 根据工单获取工单状态
	 * @param workOrderCode
	 * @return
	 */
	public String getWorkorderstate(String workOrderCode) {
		List<BatWorkOrder> ruleList=genericDao.getListWithVariableParas("WORKORDER.T_BAT_WORKORDERLIST.LIST", new Object[]{workOrderCode});
		if(ruleList!=null&&ruleList.size()>0){
			BatWorkOrder bill=ruleList.get(0);
			if("10".equals(bill.getWorkorderstate())){
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
		String date=DateBean.getSysdate();
		String date1=DateBean.getBeforDay(DateBean.getSysdate(),1);
		Map<String,List> workOrderMap=new HashMap();
		List<Object[]> workData=null;
		try {
			if(workType.equals("ZP01")){
			     workData=genericDao.getListWithNativeSql("WORKORDER.T_BAT_PROCESSID_LIST",new Object[]{workType});
			}else{
			     workData=genericDao.getListWithNativeSql("WORKORDER.T_BAT_PROCESSID1_LIST",new Object[]{workType});
			}
			if(workData!=null&&workData.size()>0){
				for (int i = 0; i < workData.size(); i++) {
					Object[] rowData=workData.get(i);
					String processId=rowData[0].toString();
					if(processId!=null){
						if(!workOrderMap.containsKey(processId)){
							workOrderMap.put(processId, new ArrayList());
						}
						ArrayList processWorkList=(ArrayList) workOrderMap.get(processId);
						String paihao=rowData[1].toString();
						String tou=paihao.substring(0, paihao.indexOf("-"));
						if("01".equals(tou)) paihao=paihao.replaceFirst(tou, "早班");
						if("02".equals(tou)) paihao=paihao.replaceFirst(tou, "白班");
						if("03".equals(tou)) paihao=paihao.replaceFirst(tou, "中班");
						processWorkList.add(paihao);
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return workOrderMap;
	}
}
