package sail.beans.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sail.beans.dao.GenericDao;

@Service
public class MatBomService {

	@Autowired
	private GenericDao genericDao;  
	
	/**
	 * 验证工单物料是否一致
	 * @param type
	 * @return
	 */
	public List getBomByWorkOrder(String workOrderCode,String process,String matCode){
		List count = genericDao.getListWithNativeSql("MAT.T_MAT_BOM_LIST.CHECK", new Object[]{workOrderCode,process,matCode});
		return count;
	}
}
