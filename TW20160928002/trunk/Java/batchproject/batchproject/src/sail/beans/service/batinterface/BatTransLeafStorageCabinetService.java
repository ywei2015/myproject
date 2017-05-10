package sail.beans.service.batinterface;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sail.beans.Constants;
import sail.beans.dao.GenericDao;
import sail.beans.entity.BatWorkOrder;
import sail.beans.entity.BatWorkOrderOutput;
import sail.beans.entity.UBatTransLeafStorageCabinet;
import sail.beans.entity.UBatTransPermixTankOrder;
import sail.beans.support.DateBean;
import sail.beans.support.StingUtil;

@Service
public class BatTransLeafStorageCabinetService extends CommonService{
	@Autowired
	private GenericDao genericDao;  
	
	/**
	 * 获取待转储并新增生产工单投料后台服务（叶片入配叶柜）
	 * @return
	 */
	public void SaveBatTransLeafStorageCabinet(){
		try{
			List<UBatTransLeafStorageCabinet> mainList = genericDao.getListWithVariableParas("SYNCHRO.U_BAT_TRANSLEAFSTORAGECABINET.LIST", new Object[]{});
			UBatTransLeafStorageCabinet order = null;
			BatWorkOrderOutput output = null;
			if (mainList != null && mainList.size() > 0){
				for(int i=0;i<mainList.size();i++){
					String matBatch = mainList.get(i).getMatBatch().toString()+Constants.ZP12;
					//根据批次号从叶片入预配柜查找存放位置和存放时间
					List<UBatTransPermixTankOrder> tankList = genericDao.getListWithVariableParas("GET.U_BAT_TRANSPREMIXTANKORDER.BY.BATCH"
							, new Object[]{mainList.get(i).getMatBatch().toString()});
					BatWorkOrder batWorkOrder = this.getWorkorderByBatch(matBatch);
					if(!StingUtil.isEmpty(batWorkOrder) && tankList.size() > 0){
						output = new BatWorkOrderOutput();
						order = mainList.get(i);
						output.setWorkorderpid(batWorkOrder.getPid());
						output.setMatbatch(order.getMatBatch()==null?"":order.getMatBatch().toString());
						output.setWater(null);
						output.setLocation(tankList.get(0).getLocation()==null?"":tankList.get(0).getLocation().toString());
						output.setLocationname(tankList.get(0).getLocationName()==null?"":tankList.get(0).getLocationName().toString());
						output.setStime2(tankList.get(0).getActualStarttime()==null?"":tankList.get(0).getActualStarttime().toString());
						output.setEtime2(tankList.get(0).getActualEndtime()==null?"":tankList.get(0).getActualEndtime().toString());
						output.setLocation3(order.getLocation()==null?"":order.getLocation().toString());
						output.setLocationname3(order.getLocationName()==null?"":order.getLocationName().toString());
						output.setStime3(order.getActualStarttime()==null?"":order.getActualStarttime().toString());
						output.setEtime3(order.getActualEndtime()==null?"":order.getActualEndtime().toString());
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
						UBatTransLeafStorageCabinet main1 = (UBatTransLeafStorageCabinet)genericDao.getById(UBatTransLeafStorageCabinet.class,mainList.get(i).getPid());
						main1.setSynchroFlag(Constants.SYN_CHRO_USED);
						main1.setSynchroTime(DateBean.getSysdateTime());
						genericDao.save(main1);
						//将工单表中该批次类型为ZP12的工单状态置为20已执行、工单完成时间为入柜完成时间、实际产量为入柜数量
						batWorkOrder.setWorkorderstate("20");
						batWorkOrder.setActualendtime(order.getActualEndtime());
						batWorkOrder.setActualquantity(order.getQuantity());
						genericDao.save(batWorkOrder);
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
}
