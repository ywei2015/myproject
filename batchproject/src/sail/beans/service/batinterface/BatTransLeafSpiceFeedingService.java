package sail.beans.service.batinterface;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sail.beans.Constants;
import sail.beans.dao.GenericDao;
import sail.beans.entity.BatWorkOrder;
import sail.beans.entity.BatWorkOrderInput;
import sail.beans.entity.UBatTransLeafSpiceFeeding;
import sail.beans.support.DateBean;
import sail.beans.support.StingUtil;

@Service
public class BatTransLeafSpiceFeedingService extends CommonService{
	@Autowired
	private GenericDao genericDao;  
	
	/**
	 * 获取待转储并新增生产工单投料后台服务（叶线香料稀释液投料）
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class) 
	public void SaveBatTransLeafSpiceFeeding(){
		try{
			List<UBatTransLeafSpiceFeeding> mainList = genericDao.getListWithVariableParas("SYNCHRO.U_BAT_TRANSLEAFSPICEFEEDING.LIST", new Object[]{});
			if (mainList != null && mainList.size() > 0){
				for(int i=0;i<mainList.size();i++){
					String matBatch = mainList.get(i).getMatBatch().toString()+Constants.ZP12;
					BatWorkOrder batWorkOrder = this.getWorkorderByBatch(matBatch);
					if(!StingUtil.isEmpty(batWorkOrder)){
						BatWorkOrderInput input = new BatWorkOrderInput();
						UBatTransLeafSpiceFeeding order = mainList.get(i);
						input.setWorkorderpid(batWorkOrder.getPid());
						input.setTltype(Constants.TL_TYPE);
						//批次根据罐号(来源位置)找出对应的投入批次号
						//罐号和批次号关系还未建立
						input.setMatbatch(order.getMatBatch()==null?"":order.getMatBatch().toString());
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
						UBatTransLeafSpiceFeeding main1 = (UBatTransLeafSpiceFeeding)genericDao.getById(UBatTransLeafSpiceFeeding.class,mainList.get(i).getPid());
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
