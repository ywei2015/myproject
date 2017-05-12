package sail.beans.service.batinterface;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sail.beans.Constants;
import sail.beans.dao.GenericDao;
import sail.beans.entity.BatHighBayDepotInDetail;
import sail.beans.entity.BatProductMove;
import sail.beans.entity.BatProductMoveDetail;
import sail.beans.entity.UBatTransProductMoveMain;
import sail.beans.entity.UBatTransProductMoveSec;
import sail.beans.entity.UBatTransproductStorageSec;
import sail.beans.support.DateBean;

@Service
public class BatProductMoveService  extends CommonService{
	@Autowired
	private GenericDao genericDao;  
	
	/**
	 * 获取待转储并新增入库后台服务（成品移库（移出/移入）信息）
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class) 
	public void SaveBatProductMove(){
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
					//出库仓库
					batProductMove.setLgortGi(main.getLgortGicode()==null?"":main.getLgortGicode().toString());
					batProductMove.setLgortGr(Constants.DEPOT3);
					batProductMove.setOperator(main.getOperateUsername()==null?"":main.getOperateUsername().toString());
					batProductMove.setTwoStep(main.getTwoStep()==null?"":main.getTwoStep().toString());
					batProductMove.setRemark(main.getRemark()==null?"":main.getRemark().toString());
					batProductMove.setSysFlag(Constants.SYS_FLAG_USEING);
					batProductMove.setCreator(Constants.USERID);
					batProductMove.setCreateTime(DateBean.getSysdateTime());
					/*List<UBatTransproductStorageSec> codeType = genericDao.getListWithVariableParas("SYNCHRO.GETCODETYPE.BY.TRANSFERBILL", new Object[]{main.getTransferBill()});
					if(codeType != null && codeType.size() > 0){ //根据从表判断
						batProductMove.setCodeType(codeType.get(0).getCodeType());
					}*/
					//出库类型的设定待确认（统一默认为移库）1：移库 2：销售出库
					//再核对一号工程的销售出库数据，如果有销售出库数据，就再把移库的数据统一刷成销售出库
					batProductMove.setGiType("1");
					batProductMove.setBizType(Constants.BIZ_TYPE);
					genericDao.save(batProductMove);
					
					//转储完数据后更新主表转储状态
					UBatTransProductMoveMain main1 = (UBatTransProductMoveMain)genericDao.getById(UBatTransProductMoveMain.class,main.getPid());
					main1.setSynchroFlag(Constants.SYN_CHRO_USED);
					main1.setSynchroTime(DateBean.getSysdateTime());
					genericDao.save(main1);
					
					List secList = genericDao.getListWithVariableParas("SYNCHRO.U_BAT_TRANSPRODUCTMOVESEC.BY.INBILLPID", new Object[]{main.getPid()});
					if (secList != null && secList.size() > 0){
						for(int j=0;j<secList.size();j++){
							BatProductMoveDetail batProductMoveDetail = new BatProductMoveDetail();
							Object [] sec = (Object[])secList.get(j);
							batProductMoveDetail.setPid(sec[0].toString());
							batProductMoveDetail.setTransferPid(main.getPid());
							batProductMoveDetail.setBatchNo(sec[2].toString());
							batProductMoveDetail.setCodeType(sec[3].toString());
							batProductMoveDetail.setGiTime(sec[4].toString());
							batProductMoveDetail.setGrTime(sec[4].toString());
							batProductMoveDetail.setTrayNo(sec[5].toString());
							batProductMoveDetail.setRemark(sec[6]==null?"":sec[6].toString());
							batProductMoveDetail.setSysFlag(Constants.SYS_FLAG_USEING);
							batProductMoveDetail.setCreator(Constants.USERID);
							batProductMoveDetail.setCreateTime(DateBean.getSysdateTime());
							genericDao.save(batProductMoveDetail);
							//转储完数据后更新从表转储状态
							UBatTransProductMoveSec sec1 = (UBatTransProductMoveSec)genericDao.getById(UBatTransProductMoveSec.class,sec[0].toString());
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
	
	/**
	 * 定时匹配移库和一号工程销售出库数据
	 * 在转储表中获取8小时内的移库数据，并关联销售出库数据，如果有销售出库数据，去掉销售数据，剩余的插入到移库业务表中
	 */
	/*public void matchProductMoveAndSale(){
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
					//出库仓库
					batProductMove.setLgortGi(main.getLgortGicode()==null?"":main.getLgortGicode().toString());
					batProductMove.setOperator(this.getUserIdByUserCode(main.getOperateUsercode()));
					batProductMove.setTwoStep(Constants.TWO_STEP);
					batProductMove.setRemark(main.getRemark()==null?"":main.getRemark().toString());
					batProductMove.setSysFlag(Constants.SYS_FLAG_USEING);
					batProductMove.setCreator(Constants.USERID);
					batProductMove.setCreateTime(DateBean.getSysdateTime());
					List<UBatTransproductStorageSec> codeType = genericDao.getListWithVariableParas("SYNCHRO.GETCODETYPE.BY.TRANSFERBILL", new Object[]{main.getTransferBill()});
					if(codeType != null && codeType.size() > 0){ //根据从表判断
						batProductMove.setCodeType(codeType.get(0).getCodeType());
					}
					//出库类型的设定待确认（统一默认为移库）1：移库 2：销售出库
					//再核对一号工程的销售出库数据，如果有销售出库数据，就再把移库的数据统一刷成销售出库
					batProductMove.setGiType("1");
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
	}*/
}
