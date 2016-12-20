package sail.beans.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import sail.beans.base.ResponseBase;
import sail.beans.entity.BatDepotIoDetail;
import sail.beans.service.BatchStorageService;

@Controller
@RequestMapping("/storage")
public class BatchStorageController {
	@Resource
	private BatchStorageService batchStorageService; 
	
	@ResponseBody
	@RequestMapping(value="/saveBatchInStorage")	 
	public ResponseBase saveBatchInStorage(HttpServletRequest request){
		ResponseBase res = new ResponseBase();
		String f_bill_no = request.getParameter("f_bill_no");
		String f_doc_type = request.getParameter("f_doc_type");
		String f_mat_batch = request.getParameter("f_mat_batch");
		BatDepotIoDetail batDepotIoDetail = batchStorageService.saveBatchInStorage(f_bill_no, f_doc_type, f_mat_batch);
		if (batDepotIoDetail != null){
			res.setResponseData("1", "操作成功!");
			res.setDataset(batDepotIoDetail, "batdepotiodetail");
		}else{
			res.setResponseData("0", "牌号信息不匹配!");
		}
		return res;
	}
	
	@ResponseBody
	@RequestMapping(value="/saveBatchOutStorage")	 
	public ResponseBase saveBatchOutStorage(HttpServletRequest request){
		ResponseBase res = new ResponseBase();
		String f_bill_no = request.getParameter("f_bill_no");
		String f_doc_type = request.getParameter("f_doc_type");
		String f_mat_batch = request.getParameter("f_mat_batch");
		BatDepotIoDetail batDepotIoDetail = batchStorageService.saveBatchInStorage(f_bill_no, f_doc_type, f_mat_batch);
		if (batDepotIoDetail != null){
			res.setResponseData("1", "操作成功!");
			res.setDataset(batDepotIoDetail, "batdepotiodetail");
		}else{
			res.setResponseData("0", "牌号信息不匹配!");
		}
		return res;
	}
	
	@ResponseBody
	@RequestMapping(value="/getBatDepotIoDetailList")	 
	public ResponseBase getBatDepotIoDetailList(HttpServletRequest request){
		ResponseBase res = new ResponseBase();
		String f_bill_no = request.getParameter("f_bill_no");
		String f_doc_type = request.getParameter("f_doc_type");
		List<BatDepotIoDetail> batdepotiodetaillist = batchStorageService.getBatDepotIoDetailList(f_bill_no, f_doc_type);
		if (batdepotiodetaillist != null && batdepotiodetaillist.size() > 0){
			res.setResponseData("1", "操作成功!");
			res.setDataset(batdepotiodetaillist, "batdepotiodetail");
		}else{
			res.setResponseData("0", "牌号信息不匹配!");
		}
		return res;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/deleteBatDepotIoDetail")	 
	public ResponseBase deleteBatDepotIoDetail(HttpServletRequest request){
		ResponseBase res = new ResponseBase();
		String f_pid = request.getParameter("f_pid");
		boolean falg = batchStorageService.deleteBatDepotIoDetail(f_pid);
		if (falg){
			res.setResponseData("1", "操作成功!");
		}else{
			res.setResponseData("0", "牌号信息不匹配!");
		}
		return res;
	}
}
