package sail.beans.service.batinterface;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sail.beans.Constants;
import sail.beans.dao.GenericDao;
import sail.beans.entity.BatWorkOrder;
import sail.beans.entity.BatWorkOrderInput;
import sail.beans.entity.UBatTransLeafessenceFeeding;
import sail.beans.support.DateBean;

@Service
public class BatTransLeafessenceFeedingService extends CommonService{
	@Autowired
	private GenericDao genericDao;  
	
	/**
	 * 获取待转储并新增生产工单投料后台服务（丝线香精投料）
	 * @return
	 */
	public void saveBatTransLeafessenceFeeding(){
		try{
			List<UBatTransLeafessenceFeeding> mainList = genericDao.getListWithVariableParas("SYNCHRO.U_BAT_TRANSLEAFESSENCEFEEDING.LIST", new Object[]{});
			UBatTransLeafessenceFeeding order = null;
			if (mainList != null && mainList.size() > 0){
				for(int i=0;i<mainList.size();i++){
					String workorderCode = mainList.get(i).getWorkorderCode().toString();
					BatWorkOrder batWorkOrder = this.getWorkorderByCode(workorderCode);
					if(batWorkOrder != null && !"".equals(batWorkOrder)){
						BatWorkOrderInput input = new BatWorkOrderInput();
						order = mainList.get(i);
						input.setWorkorderpid(batWorkOrder.getPid());
						input.setTltype(Constants.TL_TYPE);
						//批次根据罐号(来源位置)找出对应的投入批次号
						//罐号和批次号关系还未建立
						input.setMatbatch(order.getMatBatch()==null?"":order.getMatBatch().toString());
						input.setMatcode(order.getMatCode()==null?"":order.getMatCode().toString());
						input.setMatname(order.getMatCode()==null?"":this.getNameByCode(order.getMatCode()));
						input.setLocation(order.getLocation()==null?"":order.getLocation().toString());
						input.setLocationname(order.getLocationName()==null?"":order.getLocationName().toString());
						input.setQuantity(order.getQuantity()==null?0.0:Double.parseDouble(order.getQuantity().toString()));
						input.setUnit(order.getUnit()==null?"":order.getUnit().toString());
						input.setStarttime(order.getStarttime()==null?"":order.getStarttime().toString());
						input.setEndtime(order.getEndtime()==null?"":order.getEndtime().toString());
						input.setOperateuserid(this.getUserIdByUserCode(order.getOperateUsercode()));
						input.setOperatetime(order.getOperateTime()==null?"":order.getOperateTime().toString());
						input.setMasterslaveflag("0");
						input.setRemark(order.getRemark()==null?"":order.getRemark().toString());
						input.setSysflag(Constants.SYS_FLAG_USEING);
						input.setCreator(Constants.USERID);
						input.setCreatetime(DateBean.getSysdateTime());
						genericDao.save(input);
						//转储完数据后更新转储状态
						UBatTransLeafessenceFeeding main1 = (UBatTransLeafessenceFeeding)genericDao.getById(UBatTransLeafessenceFeeding.class,order.getPid());
						main1.setSynchroFlag(Constants.SYN_CHRO_USED);
						main1.setSynchroTime(DateBean.getSysdateTime());
						genericDao.save(main1);
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
