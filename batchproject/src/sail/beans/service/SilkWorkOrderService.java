package sail.beans.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sail.beans.dao.GenericDao;

@Service
public class SilkWorkOrderService {

	@Autowired
	private GenericDao genericDao;  
	
	/**
	 * 验证工单和批次是否匹配
	 * @param workorderno
	 * @param matbatch
	 * @return
	 */
	public boolean isExistence(String workorderno,String matbatch){
		boolean falg = false;
		return falg;
	}
	
	 
	
	
}
