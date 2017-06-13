package sail.beans.service.batinterface;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sail.beans.Constants;
import sail.beans.dao.GenericDao;
import sail.beans.entity.BatWorkOrder;
import sail.beans.entity.BatWorkOrderInput;
import sail.beans.entity.BatWorkOrderOutput;
import sail.beans.entity.UBatTransBlendingOrder;
import sail.beans.service.TransfromWorkCodeDataService;
import sail.beans.support.DateBean;
import sail.beans.support.StingUtil;

@Service
public class BatTransBlendingOrderService extends CommonService{
	@Autowired
	private GenericDao genericDao;  
	@Autowired
	private TransfromWorkCodeDataService service;
	
	/**
	 * 获取待转储并新增生产工单投料后台服务（五丝掺配）
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class) 
	public void SaveBatTransBlendingOrder(){
		try{
			List<UBatTransBlendingOrder> mainList = genericDao.getListWithVariableParas("SYNCHRO.U_BAT_TRANSBLENDINGORDER.LIST", new Object[]{});
			if (mainList != null && mainList.size() > 0){
				for(int i=0;i<mainList.size();i++){
					String matBatchZP03 = mainList.get(i).getMatBatch().toString()+Constants.ZP03;
					String matBatchZP13 = mainList.get(i).getMatBatch().toString()+Constants.ZP13;
					BatWorkOrder batWorkOrderZP03 = this.getWorkorderByBatch(matBatchZP03);
					BatWorkOrder batWorkOrderZP13 = this.getWorkorderByBatch(matBatchZP13);
					BatWorkOrderInput input = new BatWorkOrderInput();
					BatWorkOrderOutput output = new BatWorkOrderOutput();
					UBatTransBlendingOrder order = mainList.get(i);
					//叶丝转成ZP13叶丝工单的产出，同时转成掺配工单ZP03的投料，1：叶丝  2：梗丝
					if(!StingUtil.isEmpty(matBatchZP03) && !StingUtil.isEmpty(matBatchZP13) && "1".equals(order.getBlendingType())){
						output.setWorkorderpid(batWorkOrderZP13.getPid());
						output.setMatbatch(order.getMatBatch()==null?"":order.getMatBatch().toString()+Constants.ZP13);
						output.setStime2(order.getStarttime()==null?"":order.getStarttime().toString());
						output.setEtime2(order.getEndtime()==null?"":order.getEndtime().toString());
						output.setQuantity(order.getQuantity());
						output.setUnit(order.getUnit()==null?"":order.getUnit().toString());
						output.setDepot(Constants.DEPOT);
						output.setOperateuserid(this.getUserIdByUserCode(order.getOperateUsercode()));
						output.setRemark(order.getRemark()==null?"":order.getRemark().toString());
						output.setSysflag(Constants.SYS_FLAG_USEING);
						output.setCreator(Constants.USERID);
						output.setCreatetime(DateBean.getSysdateTime());
						genericDao.save(output);
						
						//将工单表中该批次类型为ZP13的工单状态置为20已执行、工单完成时间为入柜完成时间、实际产量为入柜数量
						service.TransfromWorkOrder(batWorkOrderZP13);
						batWorkOrderZP13.setWorkorderstate("20");
						batWorkOrderZP13.setActualquantity(order.getQuantity());
						batWorkOrderZP13.setUnit(order.getUnit());
						batWorkOrderZP13.setLastmodifier(this.getUserIdByUserCode(order.getOperateUsercode()));
						batWorkOrderZP13.setLastmodifiedtime(DateBean.getSysdateTime());
						genericDao.save(batWorkOrderZP13);
						
						input.setWorkorderpid(batWorkOrderZP03.getPid());
						input.setTltype(Constants.TL_TYPE);
						input.setMatbatch(order.getMatBatch()==null?"":order.getMatBatch().toString()+Constants.ZP03);
						input.setMatcode(order.getMatCode()==null?"":order.getMatCode().toString());
						input.setMatname(this.getBatYsName(order.getMatCode().toString()));
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
						UBatTransBlendingOrder main1 = (UBatTransBlendingOrder)genericDao.getById(UBatTransBlendingOrder.class,mainList.get(i).getPid());
						main1.setSynchroFlag(Constants.SYN_CHRO_USED);
						main1.setSynchroTime(DateBean.getSysdateTime());
						genericDao.save(main1);
					}else if(!StingUtil.isEmpty(matBatchZP03) && "2".equals(order.getBlendingType())){  //梗丝
						input.setWorkorderpid(batWorkOrderZP03.getPid());
						input.setTltype(Constants.TL_TYPE);
						input.setMatbatch(order.getMatBatch()==null?"":order.getMatBatch().toString()+Constants.ZP03);
						input.setMatcode(order.getMatCode()==null?"":order.getMatCode().toString());
						input.setMatname(this.getBatYsName(order.getMatCode().toString()));
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
						UBatTransBlendingOrder main1 = (UBatTransBlendingOrder)genericDao.getById(UBatTransBlendingOrder.class,mainList.get(i).getPid());
						main1.setSynchroFlag(Constants.SYN_CHRO_USED);
						main1.setSynchroTime(DateBean.getSysdateTime());
						genericDao.save(main1);
					}else{
						UBatTransBlendingOrder main1 = (UBatTransBlendingOrder)genericDao.getById(UBatTransBlendingOrder.class,mainList.get(i).getPid());
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
