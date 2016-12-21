package sail.beans.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import sail.beans.base.ResponseBase;
import sail.beans.entity.BatBatAdjustDetail;
import sail.beans.entity.User;
import sail.beans.service.BatchStorageService;

@Controller
@RequestMapping("/sizepieces")
public class SizePiecesAdjustController {

	@Resource
	private BatchStorageService batchStorageService; 
	
	@ResponseBody
	@RequestMapping(value="/saveBatchAdjustment")	 
	public ResponseBase saveBatchAdjustment(HttpServletRequest request){
		ResponseBase res = new ResponseBase();
		String masterbatch = request.getParameter("f_master_batch");
		String slavebatch = request.getParameter("f_slave_batch");
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		BatBatAdjustDetail batBatAdjustDetail = batchStorageService.saveBatchAdjustment(masterbatch, slavebatch, user.getPid());
		if (batBatAdjustDetail != null){
			if ("1".equals(batBatAdjustDetail.getIsrepeat())){
				res.setResponseData("0", "该数据已经存在!");
			}else{
				res.setResponseData("1", "操作成功!");
				res.setDataset(batBatAdjustDetail, "batbatadjustdetail");
			}
			
		}else{
			res.setResponseData("0", "牌号信息不匹配!");
		}
		return res;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/getBatBatAdjustDetail")	 
	public ResponseBase getBatBatAdjustDetail(HttpServletRequest request){
		ResponseBase res = new ResponseBase();
		String masterbatch = request.getParameter("f_master_batch");
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		List<BatBatAdjustDetail> detailList = batchStorageService.getBatBatAdjustDetail(masterbatch);
		if (detailList != null){
			res.setResponseData("1", "操作成功!");
			res.setDataset(detailList, "batbatadjustdetail");
		}else{
			res.setResponseData("0", "牌号信息不匹配!");
		}
		return res;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/deleteBatBatAdjustDetail")	 
	public ResponseBase deleteBatBatAdjustDetail(HttpServletRequest request){
		ResponseBase res = new ResponseBase();
		String detailpid = request.getParameter("f_pid");
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		boolean falg = batchStorageService.deleteBatBatAdjustDetail(detailpid, user.getPid());
		if (falg){
			res.setResponseData("1", "操作成功!");
		}else{
			res.setResponseData("0", "牌号信息不匹配!");
		}
		return res;
	}
}
