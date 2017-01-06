package sail.beans.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sail.beans.dao.GenericDao;
import sail.beans.entity.CarCode;

@Service
public class MatBomService {

	@Autowired
	private GenericDao genericDao;  
	
	@Autowired
	BatchStorageService batchStorageService;
	
	/**
	 * 验证工单物料是否一致
	 * @param type
	 * @return
	 */
	public List getBomByWorkOrder(String workOrderCode,String process,String matCode){
		List count = genericDao.getListWithNativeSql("MAT.T_MAT_BOM_LIST.CHECK", new Object[]{workOrderCode,process,matCode});
		return count;
	}
	/**
	 * 验证工单物料是否一致
	 * @param type
	 * @return
	 */
	public boolean getBoomState(String workOrderCode,String match ){
		CarCode carcode=batchStorageService.getResolveValue(match);
		
		
		return true;
		
	}
}
