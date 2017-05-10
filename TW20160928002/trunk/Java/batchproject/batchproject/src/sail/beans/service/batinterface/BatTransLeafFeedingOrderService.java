package sail.beans.service.batinterface;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sail.beans.Constants;
import sail.beans.dao.GenericDao;
import sail.beans.entity.BatWorkOrder;
import sail.beans.entity.BatWorkOrderInput;
import sail.beans.entity.UBatTransLeafFeedingOrder;
import sail.beans.support.DateBean;
import sail.beans.support.StingUtil;

@Service
public class BatTransLeafFeedingOrderService extends CommonService{
	@Autowired
	private GenericDao genericDao;  
	
	/**
	 * 获取待转储并新增生产工单投料后台服务（叶片投料）
	 * @return
	 */
	public void SaveBatTransLeafFeedingOrder(){
		try{
			List<UBatTransLeafFeedingOrder> mainList = genericDao.getListWithVariableParas("SYNCHRO.U_BAT_TRANSLEAFFEEDINGORDER.LIST", new Object[]{});
			UBatTransLeafFeedingOrder order = null;
			BatWorkOrderInput input = null;
			if (mainList != null && mainList.size() > 0){
				for(int i=0;i<mainList.size();i++){
					String matBatch = mainList.get(i).getMatBatch().toString()+Constants.ZP12;
					BatWorkOrder batWorkOrder = this.getWorkorderByBatch(matBatch);
					if(!StingUtil.isEmpty(batWorkOrder)){
						input = new BatWorkOrderInput();
						order = mainList.get(i);
						input.setWorkorderpid(batWorkOrder.getPid());
						input.setTltype(Constants.TL_TYPE);
						input.setMatbatch(order.getMatBatch()==null?"":order.getMatBatch().toString()+Constants.ZP12);
						input.setMatcode(order.getMatCode()==null?"":order.getMatCode().toString());
						input.setMatname(order.getMatName()==null?"":order.getMatName().toString());
						input.setLocation(order.getLocation()==null?"":order.getLocation().toString());
						input.setLocationname(order.getLocationName()==null?"":order.getLocationName().toString());
						input.setQuantity(order.getQuantity()==null?0.0:Double.parseDouble(order.getQuantity().toString()));
						input.setUnit(order.getUnit()==null?"":order.getUnit().toString());
						input.setStarttime(order.getStarttime()==null?"":order.getStarttime().toString());
						input.setEndtime(order.getEndtime()==null?"":order.getEndtime().toString());
						input.setOperateuserid(this.getUserIdByUserCode(order.getOperateUsercode()));
						input.setOperateusername(order.getOperateUsername()==null?"":order.getOperateUsername().toString());
						input.setOperatetime(order.getOperateTime()==null?"":order.getOperateTime().toString());
						input.setMasterslaveflag("0");
						input.setRemark(order.getRemark()==null?"":order.getRemark().toString());
						input.setSysflag(Constants.SYS_FLAG_USEING);
						input.setCreator(Constants.USERID);
						input.setCreatetime(DateBean.getSysdateTime());
						genericDao.save(input);
						//转储完数据后更新转储状态
						UBatTransLeafFeedingOrder main1 = (UBatTransLeafFeedingOrder)genericDao.getById(UBatTransLeafFeedingOrder.class,mainList.get(i).getPid());
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
