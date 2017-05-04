package sail.beans.service.batinterface;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sail.beans.Constants;
import sail.beans.dao.GenericDao;
import sail.beans.entity.BatPalletForm;
import sail.beans.entity.BatPalletWorkorder;
import sail.beans.entity.UBatTransMaterialWithMain;
import sail.beans.entity.UBatTransMaterialWithSec;
import sail.beans.support.DateBean;
import sail.beans.support.StingUtil;

@Service
public class BatPalletWorkorderService {
	@Autowired
	private GenericDao genericDao;  
	
	/**
	 * 获取待转储并新增后台服务（烟用材料配盘信息）
	 * @return
	 */
	public void SaveBatPalletWorkorder(){
		try{
			List<UBatTransMaterialWithMain> mainList = genericDao.getListWithVariableParas("SYNCHRO.U_BAT_TRANSMATERIALWITHMAIN.LIST", new Object[]{});
			UBatTransMaterialWithMain main = null;
			UBatTransMaterialWithSec sec = null;
			BatPalletWorkorder batPalletWorkorder = null;
			//主表
			if (mainList != null && mainList.size() > 0){
				for(int i=0;i<mainList.size();i++){
					batPalletWorkorder = new BatPalletWorkorder();
					main = mainList.get(i);
					batPalletWorkorder.setPid(main.getPid());
					batPalletWorkorder.setPalletWorkorder(main.getPalletWorkorder()==null?"":main.getPalletWorkorder().toString());
//					batPalletWorkorder.setMesjbWorkorder("mesjb");//此处不确定字段
					batPalletWorkorder.setPalletSid(main.getPalletSid()==null?"":main.getPalletSid().toString());
					batPalletWorkorder.setProduceDate(main.getProduceDate()==null?"":main.getProduceDate().toString());
					batPalletWorkorder.setFactory(Constants.FACTORY);
					
					batPalletWorkorder.setWorkarea("07ef0be1-82f3-40fe-a1eb-e5d7f37fb179");  //固定值卷包车间
					//bat_worktime_zao	1	早	bat_worktime_zong	2	中	bat_worktime_wan	3	晚
					if(!StingUtil.isEmpty(main.getWorktimeCode())){
						if("1".equals(main.getWorktimeCode())){
							batPalletWorkorder.setWorktime("bat_worktime_zao");
						}else if("2".equals(main.getWorktimeCode())){
							batPalletWorkorder.setWorktime("bat_worktime_zong");
						}else if("3".equals(main.getWorktimeCode())){
							batPalletWorkorder.setWorktime("bat_worktime_wan");
						}
					}
					//bat_workteam_jia	1甲  bat_workteam_yi 2乙  bat_workteam_bin	 3丙  bat_workteam_ding	4丁
					if(!StingUtil.isEmpty(main.getWorkteamCode())){
						if("1".equals(main.getWorkteamCode())){
							batPalletWorkorder.setWorkteam("bat_workteam_jia");
						}else if("2".equals(main.getWorkteamCode())){
							batPalletWorkorder.setWorkteam("bat_workteam_yi");
						}else if("3".equals(main.getWorkteamCode())){
							batPalletWorkorder.setWorkteam("bat_workteam_bin");
						}else if("4".equals(main.getWorkteamCode())){
							batPalletWorkorder.setWorkteam("bat_workteam_ding");
						}
					}
					batPalletWorkorder.setWorkorderState(main.getWorkorderState());
					batPalletWorkorder.setRemark(main.getRemark()==null?"":main.getRemark().toString());
					batPalletWorkorder.setSysFlag(Constants.SYS_FLAG_USEING);
					batPalletWorkorder.setCreator(Constants.USERID);
					batPalletWorkorder.setCreateTime(DateBean.getSysdateTime());
					genericDao.evict(batPalletWorkorder);
					genericDao.save(batPalletWorkorder);
					genericDao.flush();
					//转储完数据后更新主表转储状态
					UBatTransMaterialWithMain main1 = (UBatTransMaterialWithMain)genericDao.getById(UBatTransMaterialWithMain.class,main.getPid());
					main1.setSynchroFlag(Constants.SYN_CHRO_USED);
					main1.setSynchroTime(DateBean.getSysdateTime());
					genericDao.save(main1);
					//从表数据
					List<UBatTransMaterialWithSec> secList = genericDao.getListWithVariableParas("SYNCHRO.U_BAT_TRANSMATERIALWITHSEC.LIST", new Object[]{main.getPid()});
					if (secList != null && secList.size() > 0){
						for(int j=0;j<secList.size();j++){
							BatPalletForm batPalletForm = new BatPalletForm();
							sec = secList.get(j);
							batPalletForm.setPid(sec.getPid());
							batPalletForm.setPalletPid(main.getPid());
							batPalletForm.setPalletSid(sec.getPalletSid());
							batPalletForm.setPalletCode(sec.getPalletCode());
							batPalletForm.setMatCode(sec.getMatCode());
							batPalletForm.setMatName(sec.getMatName());
							batPalletForm.setAllocationType(sec.getAllocationType());
							batPalletForm.setMasterBatch(sec.getMasterBatch());
							batPalletForm.setSlaveCount(sec.getSlaveCount());
							batPalletForm.setSlaveBatch(sec.getSlaveBatch());
							batPalletForm.setQuantity(sec.getQuantity()==null?"0":sec.getQuantity());
							batPalletForm.setUnit(sec.getUnit());
							batPalletForm.setLocation(sec.getLocation());
							batPalletForm.setOperateTime(sec.getOperateTime());
							batPalletForm.setOperator(sec.getOperator());
							batPalletForm.setRemark(sec.getRemark());
							batPalletForm.setSysFlag(Constants.SYS_FLAG_USEING);
							batPalletForm.setCreator(Constants.USERID);
							batPalletForm.setCreateTime(DateBean.getSysdateTime());
							genericDao.evict(batPalletForm);
							genericDao.save(batPalletForm);
							genericDao.flush();
							//转储完数据后更新从表转储状态
							UBatTransMaterialWithSec sec1 = (UBatTransMaterialWithSec)genericDao.getById(UBatTransMaterialWithSec.class,sec.getPid());
							sec1.setSynchroFlag(Constants.SYN_CHRO_USED);
							sec1.setSynchroTime(DateBean.getSysdateTime());
							genericDao.save(sec1);
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
