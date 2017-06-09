package sail.beans.service.batinterface;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sail.beans.Constants;
import sail.beans.dao.GenericDao;
import sail.beans.entity.BatWorkOrder;
import sail.beans.entity.BatWorkOrderOutput;
import sail.beans.entity.UBatTransPermixTankOrder;
import sail.beans.service.TransfromWorkCodeDataService;
import sail.beans.support.DateBean;
import sail.beans.support.StingUtil;

@Service
public class BatTransPermixTankOrderService extends CommonService{
	@Autowired
	private GenericDao genericDao;  
	@Autowired
	private TransfromWorkCodeDataService service;
	
	/**
	 * 获取待转储并新增生产工单投料后台服务（叶片入预混柜）
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class) 
	public void SaveBatTransPermixTankOrder(){
		try{
			List<UBatTransPermixTankOrder> mainList = genericDao.getListWithVariableParas("SYNCHRO.U_BAT_TRANSPREMIXTANKORDER.LIST", new Object[]{});
			if (mainList != null && mainList.size() > 0){
				for(int i=0;i<mainList.size();i++){
					String matBatch = mainList.get(i).getMatBatch().toString()+Constants.ZP12;
					BatWorkOrder batWorkOrder = this.getWorkorderByBatch(matBatch);
					if(!StingUtil.isEmpty(batWorkOrder)){
						BatWorkOrderOutput output = new BatWorkOrderOutput();
						UBatTransPermixTankOrder order = mainList.get(i);
						output.setWorkorderpid(batWorkOrder.getPid());
						output.setMatbatch(order.getMatBatch()==null?"":order.getMatBatch().toString()+Constants.ZP12);
						output.setWater(null);
						output.setLocation(order.getLocation()==null?"":order.getLocation().toString());
						output.setLocationname(order.getLocationName()==null?"":order.getLocationName().toString());
						output.setStime2(order.getActualStarttime()==null?"":order.getActualStarttime().toString());
						output.setEtime2(order.getActualEndtime()==null?"":order.getActualEndtime().toString());
						output.setQuantity(order.getQuantity()==null?0.0:Double.parseDouble(order.getQuantity().toString()));
						output.setUnit(order.getUnit()==null?"":order.getUnit().toString());
						output.setDepot(Constants.DEPOT);
						output.setOperateuserid(this.getUserIdByUserCode(order.getOperateUsercode()));
						output.setRemark(order.getRemark()==null?"":order.getRemark().toString());
						output.setSysflag(Constants.SYS_FLAG_USEING);
						output.setCreator(Constants.USERID);
						output.setCreatetime(DateBean.getSysdateTime());
						genericDao.save(output);
						//转储完数据后更新转储状态
						UBatTransPermixTankOrder main1 = (UBatTransPermixTankOrder)genericDao.getById(UBatTransPermixTankOrder.class,mainList.get(i).getPid());
						main1.setSynchroFlag(Constants.SYN_CHRO_USED);
						main1.setSynchroTime(DateBean.getSysdateTime());
						genericDao.save(main1);
						//将工单表中该批次类型为ZP13的工单状态置为20已执行、工单完成时间为入柜完成时间、实际产量为入柜数量
						service.TransfromWorkOrder(batWorkOrder);
						batWorkOrder.setWorkorderstate("20");
						batWorkOrder.setActualquantity(order.getQuantity());
						batWorkOrder.setUnit(order.getUnit());
						batWorkOrder.setLastmodifier(this.getUserIdByUserCode(order.getOperateUsercode()));
						genericDao.save(batWorkOrder);
					}else{
						UBatTransPermixTankOrder main1 = (UBatTransPermixTankOrder)genericDao.getById(UBatTransPermixTankOrder.class,mainList.get(i).getPid());
						main1.setSynchroFlag(Constants.SYN_CHRO_UNFIND);
						main1.setSynchroTime(DateBean.getSysdateTime());
						genericDao.save(main1);
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
}
