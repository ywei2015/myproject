package sail.beans.service.batinterface;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sail.beans.Constants;
import sail.beans.dao.GenericDao;
import sail.beans.entity.BatProductMove;
import sail.beans.entity.BatProductMoveDetail;
import sail.beans.entity.UBatTransProductMoveMain;
import sail.beans.entity.UBatTransProductMoveSec;
import sail.beans.support.DateBean;

@Service
public class BatProductMoveService {
	@Autowired
	private GenericDao genericDao;  
	
	/**
	 * 获取待转储并新增入库后台服务（成品移库（移出/移入）信息）
	 * @return
	 */
	public void saveBatProductMove(){
		try{
			List<UBatTransProductMoveMain> mainList = genericDao.getListWithVariableParas("SYNCHRO.U_BAT_TRANSPRODUCTMOVEMAIN.LIST", new Object[]{});
			UBatTransProductMoveMain main = null;
			//主表
			if (mainList != null && mainList.size() > 0){
				for(int i=0;i<mainList.size();i++){
					BatProductMove batProductMove = new BatProductMove();
					main = mainList.get(i);
					batProductMove.setPid(main.getPid());
					batProductMove.setTransferBill(main.getTransferBill()==null?"":main.getTransferBill().toString());
					batProductMove.setFactory(Constants.FACTORY);
					batProductMove.setDate(main.getDate()==null?"":main.getDate().toString());
					batProductMove.setLgortGi(main.getLgortGicode()==null?"":main.getLgortGicode().toString());
					batProductMove.setLgortGr(main.getLgortGiname()==null?"":main.getLgortGiname().toString());
					batProductMove.setOperator(main.getOperateUsername()==null?"":main.getOperateUsername().toString());
					batProductMove.setTwoStep(Constants.TWO_STEP);
					batProductMove.setRemark(main.getRemark()==null?"":main.getRemark().toString());
					batProductMove.setSysFlag(Constants.SYS_FLAG_USEING);
					batProductMove.setCreator(Constants.USERID);
					batProductMove.setCreateTime(DateBean.getSysdateTime());
					/*List<UBatTransproductStorageSec> codeType = genericDao.getListWithVariableParas("SYNCHRO.GETCODETYPE.BY.TRANSFERBILL", new Object[]{main.getTransferBill()});
					if(codeType != null && codeType.size() > 0){ //根据从表判断
						batProductMove.setCodeType(codeType.get(0).getCodeType());
					}*/
					//出库类型的设定待确认
					batProductMove.setGiType(null);
					batProductMove.setBizType(Constants.BIZ_TYPE);
					genericDao.save(batProductMove);
					
					//转储完数据后更新主表转储状态
					UBatTransProductMoveMain main1 = (UBatTransProductMoveMain)genericDao.getById(UBatTransProductMoveMain.class,main.getPid());
					main1.setSynchroFlag(Constants.SYN_CHRO_USED);
					main1.setSynchroTime(DateBean.getSysdateTime());
					genericDao.save(main1);
					Set<UBatTransProductMoveSec> secList = main.getSecs();
					Iterator iterator = secList.iterator();

					while(iterator.hasNext()){
						BatProductMoveDetail batProductMoveDetail = new BatProductMoveDetail();
						UBatTransProductMoveSec sec = (UBatTransProductMoveSec) iterator.next();
						batProductMoveDetail.setPid(sec.getPid());
						batProductMoveDetail.setBill(batProductMove);
						batProductMoveDetail.setBatchNo(sec.getBatchNo());
						batProductMoveDetail.setCodeType(sec.getCodeType());
						batProductMoveDetail.setGiTime(sec.getGiTime());
						batProductMoveDetail.setGrTime(sec.getGiTime());
						batProductMoveDetail.setTrayNo(sec.getTrayNo());
						batProductMoveDetail.setRemark(sec.getRemark());
						batProductMoveDetail.setSysFlag(Constants.SYS_FLAG_USEING);
						batProductMoveDetail.setCreator(Constants.USERID);
						batProductMoveDetail.setCreateTime(DateBean.getSysdateTime());
						genericDao.save(batProductMoveDetail);
						//转储完数据后更新从表转储状态
						UBatTransProductMoveSec sec1 = (UBatTransProductMoveSec)genericDao.getById(UBatTransProductMoveSec.class,sec.getPid());
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
