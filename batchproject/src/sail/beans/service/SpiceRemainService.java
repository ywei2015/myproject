package sail.beans.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sail.beans.dao.GenericDao;
import sail.beans.entity.BatDepotIoDetail;
import sail.beans.entity.BatSpiceRemain;
import sail.beans.entity.BatWorkOrderInput;
import sail.beans.entity.CarCode;
import sail.beans.support.DateBean;

@Service
public class SpiceRemainService {

	@Autowired
	private GenericDao genericDao;  
	
	@Autowired
	BatchStorageService batchStorageService;
	
	/**
	 * 保存余料退回数据
	 * @param matbatch
	 * @param location
	 * @param quantity
	 * @param operuser
	 * @return
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public BatSpiceRemain saveBatSpiceRemain(String matbatch,String location,String quantity,String operuser) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		BatSpiceRemain batSpiceRemain = null;
		List<BatSpiceRemain> remainList = genericDao.getListWithVariableParas("SPICEREMAIN.T_BAT_SPICE_REMAIN.LIST", new Object[]{matbatch});
		if (remainList != null && remainList.size() > 0){
			batSpiceRemain = remainList.get(0);
			batSpiceRemain.setIsrepair("1");
			return batSpiceRemain;
		}else{
			batSpiceRemain = new BatSpiceRemain();
			CarCode carCode = batchStorageService.getResolveValue(matbatch);
			batSpiceRemain.setWorkarea("HZ10");
			batSpiceRemain.setDepot("2230");
			batSpiceRemain.setMatcode(carCode.getMatcode());
			batSpiceRemain.setOldbatch(matbatch);
			batSpiceRemain.setNewbatch(matbatch);
			batSpiceRemain.setLocation(location);
			batSpiceRemain.setQuantity(Double.parseDouble(quantity));
			batSpiceRemain.setUnit(carCode.getUnit());
			batSpiceRemain.setOperatetime(DateBean.getSysdateTime());
			batSpiceRemain.setOperator(operuser);
			batSpiceRemain.setSysflag("1");
			batSpiceRemain.setCreator(operuser);
			batSpiceRemain.setCreatetime(DateBean.getSysdateTime());
			genericDao.save(batSpiceRemain);
			return batSpiceRemain;
		}
		
	}
	
	/**
	 * 根据ID删除对应的数据
	 * @param pid
	 * @param operuser
	 * @return
	 */
	public boolean deleteBatSpiceRemain(String pid,String operuser){
		boolean falg = false;
		BatSpiceRemain batSpiceRemain = (BatSpiceRemain)genericDao.getById(BatSpiceRemain.class,pid);
		if (batSpiceRemain !=null){
			batSpiceRemain.setSysflag("0");
			batSpiceRemain.setLastmodifier(operuser);
			batSpiceRemain.setLastmodifiedtime(DateBean.getSysdateTime());
			genericDao.save(batSpiceRemain);
			falg = true;
		}
		return falg;
	}
	
	public List<BatSpiceRemain> getBatSpiceRemainList(){
		List<BatSpiceRemain> detailList = genericDao.getListWithVariableParas("STORAGE.T_BAT_SPICEREMAIN.LIST", new Object[]{});
		return detailList;
	}
}
