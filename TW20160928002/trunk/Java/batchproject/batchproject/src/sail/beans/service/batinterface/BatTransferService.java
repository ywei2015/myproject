package sail.beans.service.batinterface;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sail.beans.Constants;
import sail.beans.dao.GenericDao;
import sail.beans.entity.BatTransfer;
import sail.beans.entity.BatTransferDetail;
import sail.beans.entity.UBatTransFinalProductMain;
import sail.beans.entity.UBatTransFinalProductSec;
import sail.beans.support.DateBean;

@Service
public class BatTransferService {
	@Autowired
	private GenericDao genericDao;  
	
	/**
	 * 获取待转储并新增成品调运信息后台服务（成品调运信息）
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class) 
	public void SaveBatTransfer(){
		try{
			List<UBatTransFinalProductMain> mainList = genericDao.getListWithVariableParas("SYNCHRO.U_BAT_TRANSFINALPRODUCTMAIN.LIST", new Object[]{});
			//主表
			if (mainList != null && mainList.size() > 0){
				for(int i=0;i<mainList.size();i++){
					BatTransfer batTransfer = new BatTransfer();
					UBatTransFinalProductMain main = mainList.get(i);
					batTransfer.setTransferBill(main.getTransferBill()==null?"":main.getTransferBill().toString());
					batTransfer.setFactory(Constants.FACTORY);
					batTransfer.setDate(main.getDate().toString().substring(0, 10).replaceAll("-", ""));
					batTransfer.setPlateNumber(main.getPlateNumber()==null?"":main.getPlateNumber());
					batTransfer.setDriver(main.getDriver()==null?"":main.getDriver().toString());
					batTransfer.setBrandCode(null); //此处不做处理，多余字段
					batTransfer.setSendoutDate(main.getSendoutDate()==null?"":main.getSendoutDate().toString());
					batTransfer.setReceivedDate(main.getReceivedDate()==null?"":main.getReceivedDate().toString());
					batTransfer.setSysFlag(Constants.SYS_FLAG_USEING);
					batTransfer.setCreator(Constants.USERID);
					batTransfer.setCreateTime(DateBean.getSysdateTime());
					genericDao.save(batTransfer);
					//转储完数据后更新主表转储状态
					UBatTransFinalProductMain main1 = (UBatTransFinalProductMain)genericDao.getById(UBatTransFinalProductMain.class,mainList.get(i).getPid());
					main1.setSynchroFlag(Constants.SYN_CHRO_USED);
					main1.setSynchroTime(DateBean.getSysdateTime());
					genericDao.save(main1);

					List<UBatTransFinalProductSec> secList = genericDao.getListWithVariableParas("SYNCHRO.U_BAT_TRANSFINALPRODUCTSEC.BY.INBILLPID", new Object[]{mainList.get(i).getPid()});
					if (secList != null && secList.size() > 0){
						for(int j=0;j<secList.size();j++){
							BatTransferDetail batTransferDetail = new BatTransferDetail();
							UBatTransFinalProductSec sec = secList.get(j);
							batTransferDetail.setTransferPid(batTransfer.getPid());
							batTransferDetail.setContractCode(sec.getContractCode());
							batTransferDetail.setBrandCode(sec.getBrandCode());
							batTransferDetail.setQuantity(sec.getQuantity());
							batTransferDetail.setRemark(sec.getRemark());
							batTransferDetail.setSysFlag(Constants.SYS_FLAG_USEING);
							batTransferDetail.setCreator(Constants.USERID);
							batTransferDetail.setCreateTime(DateBean.getSysdateTime());
							genericDao.save(batTransferDetail);
							//转储完数据后更新从表转储状态
							UBatTransFinalProductSec sec1 = (UBatTransFinalProductSec)genericDao.getById(UBatTransFinalProductSec.class,secList.get(j).getPid());
							sec1.setSynchroFlag(Constants.SYN_CHRO_USED);
							sec1.setSynchroTime(DateBean.getSysdateTime());
							genericDao.save(sec1);
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
