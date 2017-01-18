package sail.beans.service.batinterface;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sail.beans.Constants;
import sail.beans.dao.GenericDao;
import sail.beans.entity.BatHighBayDepotIn;
import sail.beans.entity.BatHighBayDepotInDetail;
import sail.beans.entity.UBatTransproductStorageMain;
import sail.beans.entity.UBatTransproductStorageSec;
import sail.beans.support.DateBean;

@Service
public class BatHighBayDepotInService {
	@Autowired
	private GenericDao genericDao;  
	
	/**
	 * 获取待转储并新增入库后台服务（成品（成品/在制品）入库信息）
	 * @param billNo
	 * @param docType
	 * @param matBatch
	 * @return
	 */
	public void saveBatHighBayDepotIn(){
		try{
			List<UBatTransproductStorageMain> mainList = genericDao.getListWithVariableParas("SYNCHRO.U_BAT_TRANSPRODUCTSTORAGEMAIN.LIST", new Object[]{});
//			List<UBatTransproductStorageSec> secList = genericDao.getListWithVariableParas("SYNCHRO.U_BAT_TRANSPRODUCTSTORAGESEC.LIST", new Object[]{});
			UBatTransproductStorageMain main = null;
			//主表
			if (mainList != null && mainList.size() > 0){
				for(int i=0;i<mainList.size();i++){
					BatHighBayDepotIn batHighBayDepotIn = new BatHighBayDepotIn();
					main = mainList.get(0);
					batHighBayDepotIn.setPid(main.getPid());
					batHighBayDepotIn.setEntrydepotBill(main.getTransferBill()==null?"":main.getTransferBill().toString());
					batHighBayDepotIn.setDate(main.getDate()==null?"":main.getDate().toString());
					batHighBayDepotIn.setFactory(Constants.FACTORY);
					batHighBayDepotIn.setDepot(main.getLgortGicode()==null?"":main.getLgortGicode().toString());
					batHighBayDepotIn.setOperateUserid(main.getOperateUsername()==null?"":main.getOperateUsername().toString());
					batHighBayDepotIn.setOperateTime(DateBean.getSysdateTime());
					batHighBayDepotIn.setRemark(main.getRemark()==null?"":main.getRemark().toString());
					batHighBayDepotIn.setSysFlag(Constants.SYS_FLAG_USEING);
					batHighBayDepotIn.setCreator(Constants.USERID);
					batHighBayDepotIn.setCreateTime(DateBean.getSysdateTime());
					List<UBatTransproductStorageSec> codeType = genericDao.getListWithVariableParas("SYNCHRO.GETCODETYPE.BY.TRANSFERBILL", new Object[]{main.getTransferBill()});
					if(codeType != null && codeType.size() > 0){ //根据从表判断
						batHighBayDepotIn.setCodeType(codeType.get(0).getCodeType());
					}
					genericDao.save(batHighBayDepotIn);
					//转储完数据后更新主表转储状态
					UBatTransproductStorageMain main1 = (UBatTransproductStorageMain)genericDao.getById(UBatTransproductStorageMain.class,main.getPid());
					main1.setSynchroFlag(Constants.SYN_CHRO_USED);
					genericDao.save(main1);
					Set<UBatTransproductStorageSec> secList = main.getSecs();
					Iterator iterator = secList.iterator();

					while(iterator.hasNext()){
						BatHighBayDepotInDetail batHighBayDepotInDetail = new BatHighBayDepotInDetail();
						UBatTransproductStorageSec sec = (UBatTransproductStorageSec) iterator.next();
						batHighBayDepotInDetail.setPid(sec.getPid());
						batHighBayDepotInDetail.setBill(batHighBayDepotIn);
						batHighBayDepotInDetail.setBoxCode(sec.getBoxCode());
						batHighBayDepotInDetail.setBatch(sec.getBatch());
						batHighBayDepotInDetail.setLot(sec.getLot());
						batHighBayDepotInDetail.setInspectNo(sec.getInspectNo());
						batHighBayDepotInDetail.setBrandCode(sec.getBrandCode());
						batHighBayDepotInDetail.setLocationCode(sec.getLocationCode());
						batHighBayDepotInDetail.setLocationName(sec.getLocationName());
						batHighBayDepotInDetail.setTray(sec.getTray());
						batHighBayDepotInDetail.setReceiver(Constants.USERID);
						batHighBayDepotInDetail.setReceiverTime(sec.getReceiveTime());
						batHighBayDepotInDetail.setRemark(sec.getRemark());
						batHighBayDepotInDetail.setSysFlag(Constants.SYS_FLAG_USEING);
						batHighBayDepotInDetail.setCreator(Constants.USERID);
						batHighBayDepotInDetail.setCreateTime(DateBean.getSysdateTime());
						genericDao.save(batHighBayDepotInDetail);
						//转储完数据后更新从表转储状态
						UBatTransproductStorageSec sec1 = (UBatTransproductStorageSec)genericDao.getById(UBatTransproductStorageSec.class,sec.getPid());
						sec1.setSynchroFlag(Constants.SYN_CHRO_USED);
						genericDao.save(sec1);
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
