package sail.beans.service.batinterface;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sail.beans.Constants;
import sail.beans.dao.GenericDao;
import sail.beans.entity.BatWorkOrderInput;
import sail.beans.entity.UBatTransToBaccoOutCabinet;
import sail.beans.support.DateBean;

@Service
public class BatTransToBaccoOutCabinetService extends CommonService{
	@Autowired
	private GenericDao genericDao;  
	
	/**
	 * 获取待转储并新增生产工单投料后台服务（成品烟丝出柜）
	 * 根据喂丝机号、从喂丝机与卷接机对应关系表(T_BAT_EQU_LINK)中找出对应的卷包机组，再根据卷包机组找出对应的工单号
	 * 投料开始结束时间必须在工单实际开始结束时间内
	 * #A喂丝机ESB编码(1000005045)
	 * @return
	 */
	public void SaveBatTransToBaccoOutCabinet(){
		try{
			List<UBatTransToBaccoOutCabinet> mainList = genericDao.getListWithVariableParas("SYNCHRO.U_BAT_TRANSTOBACCOOUTCABINET.LIST", new Object[]{});
			UBatTransToBaccoOutCabinet order = null;
			BatWorkOrderInput input = null;
			if (mainList != null && mainList.size() > 0){
				for(int i=0;i<mainList.size();i++){
					String wirefeedingCode = mainList.get(i).getWirefeedingCode().toString();
					//根据喂丝机ESB编码和投料开始结束时间查找工单号得到主键T_BAT_WORKORDER主键ID
					List workorderCodeList = genericDao.getListWithNativeSql("SYNCHRO.GET.WORKORDER.CODE", 
							new Object[]{wirefeedingCode,mainList.get(i).getStarttime(),mainList.get(i).getEndtime()});
					if(workorderCodeList != null && workorderCodeList.size() > 0){
						for(int j=0;j<workorderCodeList.size();j++){
							Object [] obj = (Object[])workorderCodeList.get(j);
							input = new BatWorkOrderInput();
							order = mainList.get(i);
							input.setWorkorderpid(obj[0].toString());
							input.setTltype(Constants.TL_TYPE);
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
							input.setOperatetime(order.getOperateTime()==null?"":order.getOperateTime().toString());
							input.setMasterslaveflag("0");
							input.setRemark(order.getRemark()==null?"":order.getRemark().toString());
							input.setSysflag(Constants.SYS_FLAG_USEING);
							input.setCreator(Constants.USERID);
							input.setCreatetime(DateBean.getSysdateTime());
							genericDao.save(input);
							
							//转储完数据后更新转储状态
							UBatTransToBaccoOutCabinet main1 = (UBatTransToBaccoOutCabinet)genericDao.getById(UBatTransToBaccoOutCabinet.class,mainList.get(i).getPid());
							main1.setSynchroFlag(Constants.SYN_CHRO_USED);
							main1.setSynchroTime(DateBean.getSysdateTime());
							genericDao.save(main1);
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
