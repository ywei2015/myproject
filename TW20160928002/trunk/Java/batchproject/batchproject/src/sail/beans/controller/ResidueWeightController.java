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
import sail.beans.service.ResidueWeightService;

@Controller
@RequestMapping("/residueweight")
public class ResidueWeightController {
	
	@Resource
	private BatchStorageService batchStorageService; 
	@Resource
	private ResidueWeightService residueWeightService;
	
	@ResponseBody
	@RequestMapping(value="/saveResidueWeight")
	public ResponseBase saveResidueWeight(HttpServletRequest request){
		ResponseBase res=new ResponseBase();
		String batch=request.getParameter("f_batch");
		String weight=request.getParameter("f_weight");
		String userId=request.getParameter("userId");
		BatDepotIoDetail batDepotIoDetail = residueWeightService.saveResidueWeight(batch,weight,userId);
		if(batDepotIoDetail!=null){
			if("1".equals(batDepotIoDetail.getRepeated())){
				res.setResponseData("0", "该批次已经录入!");
			}else{
				res.setResponseData("1", "操作成功!");
				res.setDataset(batDepotIoDetail, "batdepotiodetail");
			}
		}else{
			res.setResponseData("0", "操作失败!");
		}
		return res;
	}
	
	@ResponseBody
	@RequestMapping(value="/getBatDepotIoDetailList")	 
	public ResponseBase getBatDepotIoDetailList(HttpServletRequest request){
		ResponseBase res = new ResponseBase();
		String f_bill_no = request.getParameter("f_bill_no");
		String f_doc_type = request.getParameter("f_doc_type");
		List<BatDepotIoDetail> batdepotiodetaillist=null;
		batdepotiodetaillist = residueWeightService.getBatDepotIoDetailList(f_bill_no);
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
		boolean falg = residueWeightService.deleteBatDepotIoDetail(f_pid,userId);
		if (falg){
			res.setResponseData("1", "操作成功!");
		}else{
			res.setResponseData("0", "牌号信息不匹配!");
		}
		return res;
	}
	
	
}
