package sail.beans.controller;

import java.util.List;
import java.util.Map;

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
				res.setResponseData("0", "该批次已经存在!");
			}else{
				res.setResponseData("1", "保存成功!");
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
				res.setResponseData("0", "失败!该批次已经存在!");
			}else if("2".equals(batDepotIoDetail.getRepeated())){
				res.setResponseData("0", "失败!该批次没有入库!");
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
		if(f_doc_type.equals("ZI101"))
			f_doc_type="ZI20";
		String batch = request.getParameter("f_mat_batch");
		String remark=request.getParameter("remark5");
		List<BatDepotIoDetail> batdepotiodetaillist=null;
		batdepotiodetaillist = batchStorageService.getBatDepotIoDetailList(f_bill_no, f_doc_type,batch,remark);
		if (batdepotiodetaillist != null && batdepotiodetaillist.size() > 0){
			res.setResponseData("1", "操作成功!");
			res.setDataset(batdepotiodetaillist, "batdepotiodetail");
		}else{
			res.setResponseData("0", "牌号信息不匹配!");
		}
		return res;
	}
	
	/**
	 * 通过人员,日期，出入库类型获取详细列表
	 * */
	@ResponseBody
	@RequestMapping(value="/getBatDepotIoDetailByDate")	 
	public ResponseBase getBatDepotIoDetailListByDate(HttpServletRequest request){
		ResponseBase res = new ResponseBase();
		String f_bill_no = request.getParameter("f_bill_no");
		String matcode = request.getParameter("matcode");
		String f_doc_type = request.getParameter("f_doc_type");
		if(f_doc_type.equals("ZI101"))
			f_doc_type="ZI20";
		String batch = request.getParameter("f_mat_batch");
		String remark=request.getParameter("remark5");
		String userId = request.getParameter("userId");
		List<BatDepotIoDetail> batdepotiodetaillist=null;
		batdepotiodetaillist = batchStorageService.getBatDepotIoDetailListByDate(remark,userId,f_bill_no,matcode);
		if (batdepotiodetaillist != null && batdepotiodetaillist.size() > 0){
			res.setResponseData("1", "操作成功!");
			res.setDataset(batdepotiodetaillist, "batdepotiodetail");
		}else{
			res.setResponseData("0", "牌号信息不匹配!");
		}
		return res;
	}
	
	/**
	 * 通过人员,日期，出入库类型获取详细列表
	 * */
	@ResponseBody
	@RequestMapping(value="/getBatDepotIoDetailByFZ")	 
	public ResponseBase getBatDepotIoDetailByFZ(HttpServletRequest request){
		ResponseBase res = new ResponseBase();
		String f_bill_no = request.getParameter("f_bill_no");
		String matcode = request.getParameter("matcode");
		String f_doc_type = request.getParameter("f_doc_type");
		String batch = request.getParameter("f_mat_batch");
		String remark=request.getParameter("remark5");
		String userId = request.getParameter("userId");
		List<BatDepotIoDetail> batdepotiodetaillist=null;
		batdepotiodetaillist = batchStorageService.getBatDepotIoDetailListByFZ(f_bill_no,userId);
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
		String type = request.getParameter("type");
		if(type!=null)
			type=type.trim();
		String code = request.getParameter("code");
		String userId = request.getParameter("userId");
		CarCode carcode = batchStorageService.getResolveValue(match,type);
		if (carcode.getMatcode()!=null){
			res.setResponseData("1", "操作成功!");
			res.setDataset(carcode, "CarCode");
		}else{
			res.setResponseData("0", "批次信息不正确!");
		}
		return res;
	}
	
	@ResponseBody
	@RequestMapping(value="/getBatDepotValidate")
	public	ResponseBase getBatDepotValidate(HttpServletRequest request){
		ResponseBase res = new ResponseBase();
		String f_bill_no = request.getParameter("f_bill_no");
		String f_doc_type = request.getParameter("f_doc_type");
		List<BatDepotIoDetail> batdepotiodetaillist=null;
	    batdepotiodetaillist = batchStorageService.getBatDepotValidate(f_bill_no,f_doc_type);
		if (batdepotiodetaillist != null && batdepotiodetaillist.size() > 0){
			res.setResponseData("1", "操作成功!");
			res.setDataset(batdepotiodetaillist, "batdepotiodetail");
		}else{
			res.setResponseData("0", "牌号信息不匹配!");
		}
		return res;
	}
	
	@ResponseBody
	@RequestMapping(value="/getBatDepotStorageTotal")
	public	Map getBatDepotStorageTotal(HttpServletRequest request){
		ResponseBase res = new ResponseBase();
		String f_bill_no = request.getParameter("f_bill_no");
		String f_doc_type = request.getParameter("f_doc_type");
		List<BatDepotIoDetail> batdepotiodetaillist=null;
	    Map map = batchStorageService.getBatDepotStorageTotal(f_bill_no,f_doc_type);
		if (map != null){
			res.setResponseData("1", "操作成功!");
			res.setDataset(map, "batdepotiodetail");
		}else{
			res.setResponseData("0", "牌号信息不匹配!");
		}
		return map;
	}
	
	/**
	 * 出库工单自动生成
	 * */
	@ResponseBody
	@RequestMapping(value="/getBillNoList")
	public ResponseBase getBillNoList(HttpServletRequest request){
		ResponseBase res = new ResponseBase();
		String userId = request.getParameter("userId");
		String userCode = request.getParameter("userCode");
		List<String> inputList = batchStorageService.getBillNoList(userId,userCode);
		res.setDataset(inputList, "billList");
		res.setResponseData("1", "操作成功!");
		return res;
	}
	
}
