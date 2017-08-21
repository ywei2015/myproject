package sail.beans.service.retrospect;

import java.util.ArrayList;
import java.util.List;

import org.aspectj.apache.bcel.generic.NEW;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysql.fabric.xmlrpc.base.Array;

import sail.beans.dao.GenericDao;
import sail.beans.dao.iml.Pager;
import sail.beans.entity.BatDepotIoDetail;
import sail.beans.entity.BatWorkOrder;
import sail.beans.entity.BatWorkOrderInput;
import sail.beans.entity.BatWorkOrderOutput;
import sail.beans.entity.BatchMeterialTracking;
import sail.beans.entity.BatchRetrospect;

@Service
public class BatchMaterialTrackingService {
	@Autowired
	private GenericDao genericDao;

	/**
	 * 物料批次追踪
	 * @param pageSize
	 * @param pageNumber
	 * @param orderNum
	 * @return Pager
	 */
	public Pager batchMaterialTr(int pageSize, int pageNumber,String orderNum) {
		Pager page=null;
		try {
			int starnumber=pageSize*(pageNumber-1);
			List<BatchMeterialTracking> batchMaterialTrackingList=new ArrayList();
		    page=genericDao.getListWithNativeSqlFY("BATCH.MATERIAL.TRACKING", new Object[]{orderNum,orderNum},new int[]{starnumber,pageSize});
		    if(page!=null){
				List<Object[]> retrospectList=(List<Object[]>) page.getList();
				if(retrospectList!=null&&retrospectList.size()>0){
					for (int i = 0; i < retrospectList.size(); i++) {
						BatchMeterialTracking batchMeterialTracking=new BatchMeterialTracking();
						Object[] result=retrospectList.get(i);
						String bizType = result[4]==null?"":result[4].toString();
						batchMeterialTracking.setCreateDate(result[0]==null?"":result[0].toString());
						String remark5 = (result[1]==null?"":result[1].toString());
						if("1".equals(remark5)){
							batchMeterialTracking.setReception("是");
						}else if("2".equals(remark5)){
							batchMeterialTracking.setReception("是");
						}else{
							batchMeterialTracking.setReception("否");
						}
						batchMeterialTracking.setMaterialName(result[6]==null?"":result[6].toString());
						batchMeterialTracking.setMaterialCode(result[7]==null?"":result[7].toString());
						if("10".equals(result[5].toString())){
							batchMeterialTracking.setSizeOfA("大件");
						}else if("11".equals(result[5])){
							batchMeterialTracking.setSizeOfA("小件");
						}
						batchMaterialTrackingList.add(batchMeterialTracking);
					}
					page.setList(batchMaterialTrackingList);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  page;
	}
	/**
	 * 出入库流水记录
	 * @param pageSize
	 * @param pageNumber
	 * @param orderNum
	 * @return Pager
	 */
	public Pager batchMaterialInfo(int pageSize, int pageNumber,String orderNum) {
		Pager page=null;
		try {
			int starnumber=pageSize*(pageNumber-1);
			List<BatchMeterialTracking> batchMaterialTrackingList=new ArrayList();
		    page=genericDao.getListWithNativeSqlFY("BATCH.MATERIAL.INOUTSTOCK", new Object[]{orderNum,orderNum},new int[]{starnumber,pageSize});
		    
		    if(page!=null){
				List<Object[]> retrospectList=(List<Object[]>) page.getList();
				if(retrospectList!=null&&retrospectList.size()>0){
					for (int i = 0; i < retrospectList.size(); i++) {
						BatchMeterialTracking batchMeterialTracking=new BatchMeterialTracking();
						Object[] result=retrospectList.get(i);
						batchMeterialTracking.setBatchNum(result[10]==null?"":result[10].toString());
						batchMeterialTracking.setMaterialName(result[6]==null?"":result[6].toString());
						batchMeterialTracking.setMaterialCode(result[7]==null?"":result[7].toString());
						String bizType = result[4]==null?"":result[4].toString();
						if("MM2141".equals(bizType)){
							batchMeterialTracking.setBizType("物资批次采购收货入库");
						}else if("MM2143".equals(bizType)){
							batchMeterialTracking.setBizType("物资批次退回入库");
						}else if("MM2151".equals(bizType)){
							batchMeterialTracking.setBizType("物资批次退货出库");
						}else if("MM2152".equals(bizType)){
							batchMeterialTracking.setBizType("物资批次生产领用出库");
						}else if("MM2153".equals(bizType)){
							batchMeterialTracking.setBizType("物资批次非生产领用出库");
						}
						batchMeterialTracking.setQuantity(result[8]==null?"":result[8].toString());
						batchMeterialTracking.setUnit(result[9]==null?"":result[9].toString());
						batchMeterialTracking.setOperation(result[3]==null?"":result[3].toString());
						batchMeterialTracking.setLastModifyTime(result[2]==null?"":result[2].toString());
						if("10".equals(result[5].toString())){
							batchMeterialTracking.setSizeOfA("大件");
						}else if("11".equals(result[5])){
							batchMeterialTracking.setSizeOfA("小件");
						}
						batchMaterialTrackingList.add(batchMeterialTracking);
					}
					page.setList(batchMaterialTrackingList);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  page;
	}
	/**
	 * 批次号投料记录
	 * @param pageSize
	 * @param pageNumber
	 * @param orderNum
	 * @return Pager
	 */
	public Pager workOrderInfo(int pageSize, int pageNumber,String orderNum) {
		Pager page=null;
		try {
			int starnumber=pageSize*(pageNumber-1);
			List<BatchMeterialTracking> batchMaterialTrackingList=new ArrayList();
		    page=genericDao.getListWithNativeSqlFY("BATCH.MATERIAL.CHARGE", new Object[]{orderNum,orderNum},new int[]{starnumber,pageSize});
		    if(page!=null){
				List<Object[]> retrospectList=(List<Object[]>) page.getList();
				if(retrospectList!=null&&retrospectList.size()>0){
					for (int i = 0; i < retrospectList.size(); i++) {
						BatchMeterialTracking batchMeterialTracking=new BatchMeterialTracking();
						Object[] result=retrospectList.get(i);
						/*
						 * ZP01：卷包生产 ZP02：嘴棒生产 ZP03：烟丝生产 ZP04：
						 * 膨胀烟丝生产 ZP05：梗丝生产 ZP06：香精香料生产 ZP12：叶片生产 ZP13：叶丝生产 ZP15：香精香料稀释 
						 */
						String workType = result[0]==null?"":result[0].toString();
						if("ZP01".equals(workType)){
							batchMeterialTracking.setWorkOrderType("卷包生产");
						}else if("ZP02".equals(workType)){
							batchMeterialTracking.setWorkOrderType("嘴棒生产");
						}else if("ZP03".equals(workType)){
							batchMeterialTracking.setWorkOrderType("烟丝生产");
						}else if("ZP04".equals(workType)){
							batchMeterialTracking.setWorkOrderType("膨胀烟丝生产");
						}else if("ZP06".equals(workType)){
							batchMeterialTracking.setWorkOrderType("梗丝生产");
						}else if("ZP12".equals(workType)){
							batchMeterialTracking.setWorkOrderType("香精香料生产");
						}else if("ZP13".equals(workType)){
							batchMeterialTracking.setWorkOrderType("叶片生产");
						}else if("ZP15".equals(workType)){
							batchMeterialTracking.setWorkOrderType("香精香料稀释");
						}
						batchMeterialTracking.setWorkOrderCode(result[1]==null?"":result[1].toString());
						batchMeterialTracking.setProduceDate(result[2]==null?"":result[2].toString());
						batchMeterialTracking.setWorkTime(result[3]==null?"":result[3].toString());
						batchMeterialTracking.setWorkTeam(result[4]==null?"":result[4].toString());
						batchMeterialTracking.setMatName(result[5]==null?"":result[5].toString());
						batchMeterialTracking.setCreateTime(result[6]==null?"":result[6].toString());
						batchMeterialTracking.setPlanQuantity(result[7]==null?"":result[7].toString());
						batchMeterialTracking.setActualQuantity(result[8]==null?"":result[8].toString());
						batchMeterialTracking.setUnit1(result[9]==null?"":result[9].toString());
						batchMeterialTracking.setLastModifier(result[10]==null?"":result[10].toString());
						batchMaterialTrackingList.add(batchMeterialTracking);
					}
					page.setList(batchMaterialTrackingList);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  page;
	}
}
