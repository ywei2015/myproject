package sail.beans.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sail.beans.dao.GenericDao;

@Service
public class BatchStorageService {
	@Autowired
	private GenericDao genericDao;  
	
	public void saveBatchInStorage(String billNo,String docType,String matBatch){
		
	}
}
