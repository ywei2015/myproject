package sail.beans.service.batinterface;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sail.beans.Constants;
import sail.beans.dao.GenericDao;
import sail.beans.entity.BatWorkOrder;
import sail.beans.entity.BatWorkOrderInput;
import sail.beans.entity.BatWorkOrderOutput;
import sail.beans.entity.UBatTransToBaccoOutCabinet;
import sail.beans.support.DateBean;
import sail.beans.support.StingUtil;

@Service
public class BatTransToBaccoOutCabinetService extends CommonService{
	@Autowired
	private GenericDao genericDao;  
	
	/**
	 * 获取待转储并新增生产工单投料后台服务（成品烟丝出柜）
	 * @return
	 */
	public void saveBatTransToBaccoOutCabinet(){
		try{
			List<UBatTransToBaccoOutCabinet> mainList = genericDao.getListWithVariableParas("SYNCHRO.U_BAT_TRANSTOBACCOOUTCABINET.LIST", new Object[]{});
			UBatTransToBaccoOutCabinet order = null;
			if (mainList != null && mainList.size() > 0){
				for(int i=0;i<mainList.size();i++){
					String workorderCode = mainList.get(i).getWorkorderCode().toString();
					BatWorkOrder batWorkOrder = this.getWorkorderByCode(workorderCode);
					if(batWorkOrder != null && !"".equals(batWorkOrder)){
						BatWorkOrderInput input = new BatWorkOrderInput();
						BatWorkOrderOutput output = new BatWorkOrderOutput();
						order = mainList.get(0);
						//判断喂丝机信息不为空时，信息保存到卷包投料表中T_BAT_WORKORDER_INPUT
						if(!StingUtil.isEmpty(order.getWirefeedingCode())){
							input.setWorkorderpid(batWorkOrder.getPid());
							input.setTltype("1");
							input.setMatbatch(order.getMatBatch()==null?"":order.getMatBatch().toString());
							input.setMatcode(order.getMatCode()==null?"":order.getMatCode().toString());
							input.setMatname(order.getMatCode()==null?"":this.getNameByCode(order.getMatCode()));
							input.setLocation(order.getWirefeedingCode()==null?"":order.getWirefeedingCode().toString());
							input.setLocationname(order.getWirefeedingName()==null?"":order.getWirefeedingName().toString());
							input.setQuantity(order.getQuantity()==null?0.0:Double.parseDouble(order.getQuantity().toString()));
							input.setUnit(order.getUnit()==null?"":order.getUnit().toString());
							input.setStarttime(order.getStarttime()==null?"":order.getStarttime().toString());
							input.setEndtime(order.getEndtime()==null?"":order.getEndtime().toString());
							input.setOperateuserid(order.getOperateUsercode()==null?"":order.getOperateUsercode().toString());
							input.setOperatetime(order.getOperateTime()==null?"":order.getOperateTime().toString());
							input.setMasterslaveflag("0");
							input.setRemark(order.getRemark()==null?"":order.getRemark().toString());
							input.setSysflag(Constants.SYS_FLAG_USEING);
							input.setCreator(Constants.USERID);
							input.setCreatetime(DateBean.getSysdateTime());
							genericDao.save(input);
						}else{
							//出柜数据掺配T_BAT_WORKORDER_OUTPUT
							output.setWorkorderpid(batWorkOrder.getPid());
							output.setMatbatch(order.getMatBatch()==null?"":order.getMatBatch().toString());
							output.setLocation(order.getLocation()==null?"":order.getLocation().toString());
							output.setLocationname(order.getLocationName()==null?"":order.getLocationName().toString());
							output.setStime2(order.getStarttime()==null?"":order.getStarttime().toString());
							output.setEtime2(order.getEndtime()==null?"":order.getEndtime().toString());
							output.setQuantity(order.getQuantity()==null?0.0:Double.parseDouble(order.getQuantity().toString()));
							output.setUnit(order.getUnit()==null?"":order.getUnit().toString());
							output.setDepot(Constants.DEPOT);
							output.setOperateuserid(order.getOperateUsercode()==null?"":order.getOperateUsercode().toString());
							output.setRemark(order.getRemark()==null?"":order.getRemark().toString());
							output.setSysflag(Constants.SYS_FLAG_USEING);
							output.setCreator(Constants.USERID);
							output.setCreatetime(DateBean.getSysdateTime());
							genericDao.save(output);
						}
						//转储完数据后更新转储状态
						UBatTransToBaccoOutCabinet main1 = (UBatTransToBaccoOutCabinet)genericDao.getById(UBatTransToBaccoOutCabinet.class,order.getPid());
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
