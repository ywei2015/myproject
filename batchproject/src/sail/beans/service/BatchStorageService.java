package sail.beans.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sail.beans.dao.GenericDao;
import sail.beans.entity.BatBatAdjustDetail;
import sail.beans.entity.BatDepotIoBill;
import sail.beans.entity.BatDepotIoDetail;
import sail.beans.entity.CarCode;
import sail.beans.entity.CarCodeRule;

@Service
public class BatchStorageService {
	@Autowired
	private GenericDao genericDao;  
	
	private static HashMap<String,List<CarCodeRule>> ruleHashMap = new <String,List<CarCodeRule>>HashMap();
	
	/**
	 * 出入库后台服务
	 * @param billNo
	 * @param docType
	 * @param matBatch
	 * @return
	 */
	public BatDepotIoDetail saveBatchInStorage(String billNo,String docType,String matBatch){
		List<BatDepotIoBill> billList = genericDao.getListWithVariableParas("STORAGE.T_BAT_DEPOT_IOBILLLIST.LIST", new Object[]{billNo,docType});
		BatDepotIoBill batDepotIoBill = null;
		if (billList != null && billList.size() > 0){
			batDepotIoBill = billList.get(0);
		}
//		CarCode carCode = new CarCode();
//		this.getResolveValue(matBatch, carCode);
		String matcode = "0006";
//		String matcode = matBatch.substring(1, 9);
		BatDepotIoDetail batDepotIoDetail = null;
		List<BatDepotIoDetail> detailList = genericDao.getListWithVariableParas("STORAGE.T_BAT_DEPOT_IOBILLDETAILLIST.LIST", new Object[]{batDepotIoBill.getPid(),matcode});
		if(detailList != null && detailList.size() > 0){
			batDepotIoDetail = detailList.get(0);
			genericDao.save(batDepotIoDetail);
		}
		
		return batDepotIoDetail;
		
	}
	
	/**
	 * 根据单号和类型查询出明细数据
	 * @param billNo
	 * @param docType
	 * @return
	 */
	public List<BatDepotIoDetail> getBatDepotIoDetailList(String billNo,String docType){
		List<BatDepotIoDetail> detailList = genericDao.getListWithVariableParas("STORAGE.T_BAT_DEPOT_IOBILLDETAIL.LIST", new Object[]{billNo,docType});
		return detailList;
	}
	
	/**
	 * 删除对应明细数据
	 * @param detailpid
	 */
	public boolean deleteBatDepotIoDetail(String detailpid){
		boolean falg = false;
		BatDepotIoDetail batDepotIoDetail = (BatDepotIoDetail)genericDao.getById(BatDepotIoDetail.class,detailpid);
		batDepotIoDetail.setSysflag("0");
		genericDao.save(genericDao);
		falg = true;
		return falg;
		
	}
	
	
	
	/**
	 * 获取条形码规则
	 */
	public void getCarCodeRule(){
		List<CarCodeRule> carCodeList = genericDao.getListWithVariableParas("", new Object[]{});
		if (carCodeList != null && carCodeList.size() > 0){
			for(int i = 0; i < carCodeList.size(); i++){
				CarCodeRule carCode = carCodeList.get(i);
				if (ruleHashMap.get(carCode.getType()) != null){
					List<CarCodeRule> ruleList = ruleHashMap.get(carCode.getType());
					ruleList.add(carCode);
					ruleHashMap.put(carCode.getType(), ruleList);
				}else{
					List<CarCodeRule> ruleList = new ArrayList<CarCodeRule>();
					ruleHashMap.put(carCode.getType(), ruleList);
				}
			}
		}
	}
	
	/**
	 * 条形码解析
	 * @param type
	 * @param matCode
	 * @param carCode
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static void getResolveValue(String matCode,CarCode carCode) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		String type = matCode.substring(0, 1);
		List<CarCodeRule> ruleList = ruleHashMap.get(type);
		int startIndex = 0;
		if (ruleList != null && ruleList.size() > 0){
			for (int i = 0 ; i < ruleList.size() ; i ++){
				CarCodeRule carCodeRule = ruleList.get(i);
				String reulst = matCode.substring(startIndex,startIndex+Integer.parseInt(carCodeRule.getValue()));
				Field idF = carCode.getClass().getDeclaredField(carCodeRule.getField());  
			    idF.setAccessible(true); 
			    idF.set(carCode, reulst);
			    startIndex= startIndex+Integer.parseInt(carCodeRule.getValue());
			}
		}
	}
	
	
	
	public List<BatBatAdjustDetail> saveBatchAdjustment(String newmatbactch){
		
		return null;
	}

}
