package sail.beans.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import sail.beans.base.ResponseBase;
import sail.beans.entity.BatDepotIoDetail;
import sail.beans.entity.CarCode;
import sail.beans.entity.User;
import sail.beans.service.BatchStorageService;

@Controller
@RequestMapping("/storage")
public class BatchStorageController {
	@Resource
	private BatchStorageService batchStorageService; 
	
	@ResponseBody
	@RequestMapping(value="/saveBatchStorage")	 
	public ResponseBase saveBatchStorage(HttpServletRequest request){
		ResponseBase res = new ResponseBase();
		String f_bill_no = request.getParameter("f_bill_no");
		String f_doc_type = request.getParameter("f_doc_type");
		String f_bus_type= request.getParameter("f_bus_type");
		String f_mat_batch = request.getParameter("f_mat_batch");
		String userId = request.getParameter("userId");
		//BatDepotIoDetail batDepotIoDetail = batchStorageService.saveBatchInStorage(f_bill_no, f_doc_type, f_mat_batch);
		BatDepotIoDetail batDepotIoDetail =batchStorageService.saveBatchInStorage(f_bill_no,f_doc_type,f_mat_batch,f_bus_type,userId);
		if (batDepotIoDetail != null){
			if("1".equals(batDepotIoDetail.getRepeated())){
				res.setResponseData("0", "改批次已经存在!");
			}else{
				res.setResponseData("1", "操作成功!");
				res.setDataset(batDepotIoDetail, "batdepotiodetail");
			}
		}else{
			res.setResponseData("0", "批次信息不匹配!");
		}
		return res;
	}
	
	@ResponseBody
	@RequestMapping(value="/saveBatchStorageOut")	 
	public ResponseBase saveBatchStorageOut(HttpServletRequest request){
		ResponseBase res = new ResponseBase();
		String f_bill_no = request.getParameter("f_bill_no");
		String f_doc_type = request.getParameter("f_doc_type");
		String f_bus_type= request.getParameter("f_bus_type");
		String f_mat_batch = request.getParameter("f_mat_batch");
		String reason = request.getParameter("reason");
		String userId = request.getParameter("userId");
		BatDepotIoDetail batDepotIoDetail = batchStorageService.saveBatchStorageOut(f_bill_no,f_doc_type,f_bus_type,f_mat_batch,userId);
		if (batDepotIoDetail != null){
			if("1".equals(batDepotIoDetail.getRepeated())){
				res.setResponseData("0", "改批次已经存在!");
			}else{
				res.setResponseData("1", "操作成功!");
				res.setDataset(batDepotIoDetail, "batdepotiodetail");
			}
			
		}else{
			res.setResponseData("0", "批次信息不匹配!");
		}
		return res;
	}
	
	@ResponseBody
	@RequestMapping(value="/getBatDepotIoDetailList")	 
	public ResponseBase getBatDepotIoDetailList(HttpServletRequest request){
		ResponseBase res = new ResponseBase();
		String f_bill_no = request.getParameter("f_bill_no");
		String f_doc_type = request.getParameter("f_doc_type");
		String f_match = request.getParameter("remark");
		List<BatDepotIoDetail> batdepotiodetaillist=null;
		if(f_match!=null){
			 batdepotiodetaillist = batchStorageService.getBatDepotIoDetailListj(f_match);
		}else{
			 batdepotiodetaillist = batchStorageService.getBatDepotIoDetailList(f_bill_no, f_doc_type);
		}
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
		String userId = request.getParameter("userId");
		boolean falg = batchStorageService.deleteBatDepotIoDetail(f_pid,userId);
		if (falg){
			res.setResponseData("1", "操作成功!");
		}else{
			res.setResponseData("0", "牌号信息不匹配!");
		}
		return res;
	}
	
	@ResponseBody
	@RequestMapping(value="/getResolveValue")	 
	public ResponseBase getResolveValue(HttpServletRequest request){
		ResponseBase res = new ResponseBase();
		String match = request.getParameter("match");
		String userId = request.getParameter("userId");
		CarCode carcode = batchStorageService.getResolveValue(match);
		if (carcode.getMatcode()!=null){
			res.setResponseData("1", "操作成功!");
			res.setDataset(carcode, "CarCode");
		}else{
			res.setResponseData("0", "批次信息不正确!");
		}
		return res;
	}
	
	
}
