package sail.beans.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;

import sail.beans.service.BatchStorageService;

@Controller
public class BatchStorageController {
	@Resource
	private BatchStorageService batchStorageService; 
	
	public void saveBatchInStorage(HttpServletRequest request){
		String f_bill_no = request.getParameter("f_bill_no");
		String f_doc_type = request.getParameter("f_doc_type");
		String f_mat_batch = request.getParameter("f_mat_batch");
		batchStorageService.saveBatchInStorage(f_bill_no, f_doc_type, f_mat_batch);
	}
}
