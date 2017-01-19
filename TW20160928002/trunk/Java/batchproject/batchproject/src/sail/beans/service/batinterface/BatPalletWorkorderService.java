package sail.beans.service.batinterface;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sail.beans.Constants;
import sail.beans.dao.GenericDao;
import sail.beans.entity.BatPalletForm;
import sail.beans.entity.BatPalletWorkorder;
import sail.beans.entity.UBatTransMaterialWithMain;
import sail.beans.entity.UBatTransMaterialWithSec;
import sail.beans.support.DateBean;

@Service
public class BatPalletWorkorderService {
	@Autowired
	private GenericDao genericDao;  
	
	/**
	 * 获取待转储并新增后台服务（烟用材料配盘信息）
	 * @return
	 */
	public void saveBatPalletWorkorder(){
		try{
			List<UBatTransMaterialWithMain> mainList = genericDao.getListWithVariableParas("SYNCHRO.U_BAT_TRANSMATERIALWITHMAIN.LIST", new Object[]{});
			UBatTransMaterialWithMain main = null;
			//主表
			if (mainList != null && mainList.size() > 0){
				for(int i=0;i<mainList.size();i++){
					BatPalletWorkorder batPalletWorkorder = new BatPalletWorkorder();
					main = mainList.get(i);
					batPalletWorkorder.setPid(main.getPid());
					batPalletWorkorder.setPalletWorkorder(main.getPalletWorkorder()==null?"":main.getPalletWorkorder().toString());
					batPalletWorkorder.setMesjbWorkorder("mesjb");//此处不确定字段
					batPalletWorkorder.setPalletSid(main.getPalletSid()==null?"":main.getPalletSid().toString());
					batPalletWorkorder.setProduceDate(main.getProduceDate()==null?"":main.getProduceDate().toString());
					batPalletWorkorder.setFactory(Constants.FACTORY);
					batPalletWorkorder.setWorkarea("07ef0be1-82f3-40fe-a1eb-e5d7f37fb179");  //固定值卷包车间
					batPalletWorkorder.setWorktime("d1586f93-bb7a-42b0-bdde-826233a57f5e");  //班次名称  固定值:配盘班  不确定
					batPalletWorkorder.setWorkteam("68e55e08-f15f-45ae-9a98-f7ca6a74f514");  //班组名称  固定值:配盘班  不确定
					batPalletWorkorder.setWorkorderState(main.getWorkorderState());
					batPalletWorkorder.setRemark(main.getRemark()==null?"":main.getRemark().toString());
					batPalletWorkorder.setSysFlag(Constants.SYS_FLAG_USEING);
					batPalletWorkorder.setCreator(Constants.USERID);
					batPalletWorkorder.setCreateTime(DateBean.getSysdateTime());
					genericDao.save(batPalletWorkorder);
					//转储完数据后更新主表转储状态
					UBatTransMaterialWithMain main1 = (UBatTransMaterialWithMain)genericDao.getById(UBatTransMaterialWithMain.class,main.getPid());
					main1.setSynchroFlag(Constants.SYN_CHRO_USED);
					main1.setSynchroTime(DateBean.getSysdateTime());
					genericDao.save(main1);
					Set<UBatTransMaterialWithSec> secList = main.getSecs();
					Iterator iterator = secList.iterator();

					while(iterator.hasNext()){
						BatPalletForm batPalletForm = new BatPalletForm();
						UBatTransMaterialWithSec sec = (UBatTransMaterialWithSec) iterator.next();
						batPalletForm.setPid(sec.getPid());
						batPalletForm.setMain(batPalletWorkorder);
						batPalletForm.setPalletSid(sec.getPalletSid());
						batPalletForm.setPalletCode(sec.getPalletCode());
						batPalletForm.setMatCode(sec.getMatCode());
						batPalletForm.setMatName(sec.getMatName());
						batPalletForm.setAllocationType(sec.getAllocationType());
						batPalletForm.setMasterBatch(sec.getMasterBatch());
						batPalletForm.setSlaveCount(sec.getSlaveCount());
						batPalletForm.setSlaveBatch(sec.getSlaveBatch());
						batPalletForm.setQuantity(sec.getQuantity());
						batPalletForm.setUnit(sec.getUnit());
						batPalletForm.setLocation(sec.getLocation());
						batPalletForm.setOperateTime(sec.getOperateTime());
						batPalletForm.setOperator(sec.getOperator());
						batPalletForm.setRemark(sec.getRemark());
						batPalletForm.setSysFlag(Constants.SYS_FLAG_USEING);
						batPalletForm.setCreator(Constants.USERID);
						batPalletForm.setCreateTime(DateBean.getSysdateTime());
						genericDao.save(batPalletForm);
						//转储完数据后更新从表转储状态
						UBatTransMaterialWithSec sec1 = (UBatTransMaterialWithSec)genericDao.getById(UBatTransMaterialWithSec.class,sec.getPid());
						sec1.setSynchroFlag(Constants.SYN_CHRO_USED);
						sec1.setSynchroTime(DateBean.getSysdateTime());
						genericDao.save(sec1);
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
