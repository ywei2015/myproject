package sail.beans.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sail.beans.dao.GenericDao;
import sail.beans.entity.BatWorkOrder;
import sail.beans.entity.CarCode;

@Service
public class MatBomService {

	@Autowired
	private GenericDao genericDao;  
	
	/**
	 * 验证工单物料是否一致
	 * @param type
	 * @return
	 */
	public List getBomByWorkOrder(BatWorkOrder workOrder,String process,String matCode){
		String cmatcode=workOrder.getMatcode();//工单产出半成品
		if(workOrder.getWorkordertype().equals("ZP12")){
			List <String> matList = genericDao.getListWithNativeSql("MAT.T_MAT_BOM.YSESBCODE", new Object[]{workOrder.getMatcode()});
			if(matList.size()>0)
				cmatcode=matList.get(0);
		}
		List count = genericDao.getListWithNativeSql("MAT.T_MAT_BOM_LIST.CHECK", new Object[]{cmatcode,matCode});
		return count;
	}
	
}
