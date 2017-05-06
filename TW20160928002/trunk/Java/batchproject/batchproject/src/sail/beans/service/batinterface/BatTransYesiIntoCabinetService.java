package sail.beans.service.batinterface;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sail.beans.Constants;
import sail.beans.dao.GenericDao;
import sail.beans.entity.BatWorkOrder;
import sail.beans.entity.BatWorkOrderOutput;
import sail.beans.entity.UBatTransToBaccoOutCabinet;
import sail.beans.entity.UBatTransYesiIntoCabinet;
import sail.beans.support.DateBean;

@Service
public class BatTransYesiIntoCabinetService extends CommonService{
	@Autowired
	private GenericDao genericDao;  
	
	/**
	 * 获取待转储并新增生产工单投料后台服务（叶丝入柜）
	 * @return
	 */
	public void SaveBatTransYesiIntoCabinet(){
		try{
			List<UBatTransYesiIntoCabinet> mainList = genericDao.getListWithVariableParas("SYNCHRO.U_BAT_TRANSYESIINTOCABINET.LIST", new Object[]{});
			UBatTransYesiIntoCabinet order = null;
			if (mainList != null && mainList.size() > 0){
				for(int i=0;i<mainList.size();i++){
					String matBatch = mainList.get(i).getMatBatch().toString();
					BatWorkOrder batWorkOrder = this.getWorkorderByBatch(matBatch);
					if(batWorkOrder != null && !"".equals(batWorkOrder)){
						BatWorkOrderOutput output = new BatWorkOrderOutput();
						order = mainList.get(i);
						output.setWorkorderpid(batWorkOrder.getPid());
						output.setMatbatch(order.getMatBatch()==null?"":order.getMatBatch().toString());
						output.setWater(order.getWaterContect()==null?"":order.getWaterContect().toString());
//						output.setLocation(order.getLocation()==null?"":order.getLocation().toString());
//						output.setLocationname(order.getLocationName()==null?"":order.getLocationName().toString());
						output.setStime2(order.getStarttime()==null?"":order.getStarttime().toString());
						output.setEtime2(order.getEndtime()==null?"":order.getEndtime().toString());
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
						UBatTransToBaccoOutCabinet main1 = (UBatTransToBaccoOutCabinet)genericDao.getById(UBatTransToBaccoOutCabinet.class,mainList.get(i).getPid());
						main1.setSynchroFlag(Constants.SYN_CHRO_USED);
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
