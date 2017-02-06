package sail.beans.service.batinterface;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sail.beans.Constants;
import sail.beans.dao.GenericDao;
import sail.beans.entity.BatWorkOrder;
import sail.beans.entity.BatWorkOrderInput;
import sail.beans.entity.UBatTransLeafStorageCabinet;
import sail.beans.support.DateBean;

@Service
public class BatTransLeafStorageCabinetService extends CommonService{
	@Autowired
	private GenericDao genericDao;  
	
	/**
	 * 获取待转储并新增生产工单投料后台服务（叶片入配叶柜）
	 * @return
	 */
	public void saveBatTransLeafStorageCabinet(){
		try{
			List<UBatTransLeafStorageCabinet> mainList = genericDao.getListWithVariableParas("SYNCHRO.U_BAT_TRANSLEAFSTORAGECABINET.LIST", new Object[]{});
			UBatTransLeafStorageCabinet order = null;
			if (mainList != null && mainList.size() > 0){
				for(int i=0;i<mainList.size();i++){
					String workorderCode = mainList.get(i).getWorkorderCode().toString();
					BatWorkOrder batWorkOrder = this.getWorkorderByCode(workorderCode);
					if(batWorkOrder != null && !"".equals(batWorkOrder)){
						BatWorkOrderInput input = new BatWorkOrderInput();
						order = mainList.get(0);
						input.setMain(batWorkOrder);
						input.setTltype("1");
						input.setMatbatch(order.getMatBatch()==null?"":order.getMatBatch().toString());
						input.setMatcode(order.getMatCode()==null?"":order.getMatCode().toString());
						input.setMatname(order.getMatCode()==null?"":this.getNameByCode(order.getMatCode()));
						input.setStarttime(order.getActualStarttime()==null?"":order.getActualStarttime().toString());
						input.setEndtime(order.getActualEndtime()==null?"":order.getActualEndtime().toString());
						input.setQuantity(order.getQuantity()==null?0.0:Double.parseDouble(order.getQuantity().toString()));
						input.setUnit(order.getUnit()==null?"":order.getUnit().toString());
						input.setLocation(order.getLocation()==null?"":order.getLocation().toString());
						input.setLocationname(order.getLocationName()==null?"":order.getLocationName().toString());
						input.setOperateuserid(order.getOperateUsercode()==null?"":order.getOperateUsercode().toString());
						input.setOperatetime(order.getOperateTime()==null?"":order.getOperateTime().toString());
						input.setMasterslaveflag("0");
						input.setRemark(order.getRemark()==null?"":order.getRemark().toString());
						input.setSysflag(Constants.SYS_FLAG_USEING);
						input.setCreator(Constants.USERID);
						input.setCreatetime(DateBean.getSysdateTime());
						genericDao.save(input);
						//转储完数据后更新转储状态
						UBatTransLeafStorageCabinet main1 = (UBatTransLeafStorageCabinet)genericDao.getById(UBatTransLeafStorageCabinet.class,order.getPid());
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
