package sail.beans.service.batinterface;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sail.beans.Constants;
import sail.beans.dao.GenericDao;
import sail.beans.entity.BatWorkOrder;
import sail.beans.entity.BatWorkOrderOutput;
import sail.beans.entity.UBatTransBlendingOrder;
import sail.beans.entity.UBatTransToBaccoOutCabinet;
import sail.beans.entity.UBatTransYesiIntoCabinet;
import sail.beans.support.DateBean;
import sail.beans.support.StingUtil;

@Service
public class BatTransYesiIntoCabinetService extends CommonService{
	@Autowired
	private GenericDao genericDao;  
	
	/**
	 * 获取待转储并新增生产工单投料后台服务（叶丝入柜）
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class) 
	public void SaveBatTransYesiIntoCabinet(){
		try{
			List<UBatTransYesiIntoCabinet> mainList = genericDao.getListWithVariableParas("SYNCHRO.U_BAT_TRANSYESIINTOCABINET.LIST", new Object[]{});
			if (mainList != null && mainList.size() > 0){
				for(int i=0;i<mainList.size();i++){
					String matBatch = mainList.get(i).getMatBatch().toString()+Constants.ZP13;
					BatWorkOrder batWorkOrder = this.getWorkorderByBatch(matBatch);
					if(batWorkOrder != null && !"".equals(batWorkOrder)){
						UBatTransBlendingOrder blend = this.getQuantityByBatch(mainList.get(i).getMatBatch());
						if(!StingUtil.isEmpty(blend)){
							BatWorkOrderOutput output = new BatWorkOrderOutput();
							UBatTransYesiIntoCabinet order = mainList.get(i);
							output.setWorkorderpid(batWorkOrder.getPid());
							output.setMatbatch(order.getMatBatch()==null?"":order.getMatBatch().toString()+Constants.ZP13);
							output.setWater(order.getWaterContect()==null?"":order.getWaterContect().toString());
							output.setStime2(order.getActualStarttime()==null?"":order.getActualStarttime().toString());
							output.setEtime2(order.getActualEndtime()==null?"":order.getActualEndtime().toString());
							output.setQuantity(blend.getQuantity());
							output.setUnit(order.getUnit()==null?"":order.getUnit().toString());
							output.setDepot(Constants.DEPOT);
							output.setOperateuserid(this.getUserIdByUserCode(order.getOperateUsercode()));
							output.setRemark(order.getRemark()==null?"":order.getRemark().toString());
							output.setSysflag(Constants.SYS_FLAG_USEING);
							output.setCreator(Constants.USERID);
							output.setCreatetime(DateBean.getSysdateTime());
							genericDao.save(output);
							//转储完数据后更新转储状态
							UBatTransToBaccoOutCabinet main1 = (UBatTransToBaccoOutCabinet)genericDao.getById(UBatTransToBaccoOutCabinet.class,mainList.get(i).getPid());
							main1.setSynchroFlag(Constants.SYN_CHRO_USED);
							main1.setSynchroTime(DateBean.getSysdateTime());
							genericDao.save(main1);
							//将工单表中该批次类型为ZP13的工单状态置为20已执行、工单完成时间为入柜完成时间、实际产量为入柜数量
							batWorkOrder.setWorkorderstate("20");
							batWorkOrder.setActualstarttime(order.getActualStarttime());
							batWorkOrder.setActualendtime(order.getActualEndtime());
							batWorkOrder.setActualquantity(blend.getQuantity());
							batWorkOrder.setUnit(order.getUnit());
							batWorkOrder.setLastmodifier(this.getUserIdByUserCode(order.getOperateUsercode()));
							batWorkOrder.setLastmodifiedtime(DateBean.getSysdateTime());
							genericDao.save(batWorkOrder);
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
}
