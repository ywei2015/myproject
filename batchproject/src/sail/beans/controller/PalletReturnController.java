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
import sail.beans.entity.BatPalletReturn;
import sail.beans.entity.BatPalletReturnDetails;
import sail.beans.entity.BatWorkOrder;
import sail.beans.entity.BatWorkOrderInput;
import sail.beans.entity.User;
import sail.beans.entity.vo.BatWorkOrderVo;
import sail.beans.service.NozzlebarAddService;
import sail.beans.service.PalletReturnService;
import sail.beans.service.SilkReuseService;
import sail.beans.service.SilkWorkOrderService;

/**
 * 托盘退料
 * */
@Controller
@RequestMapping("/palletreturn")
public class PalletReturnController {

	@Resource
	private PalletReturnService palletReturnService; 
	
	
	@ResponseBody
	@RequestMapping(value="/savePalletreturn")	 
	public ResponseBase savePalletReturn(HttpServletRequest request) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		String f_date = request.getParameter("f_date").replace("-", ""); 
		String f_pallet_no = request.getParameter("f_pallet_no");
		String f_mat_batch = request.getParameter("f_mat_batch");
		String userId=request.getParameter("userId");
		ResponseBase res = new ResponseBase();
		BatPalletReturnDetails batPalletReturnDetails = palletReturnService.savePalletReturn(f_date,f_pallet_no, f_mat_batch,userId);
		if (batPalletReturnDetails != null){
			if("1".equals(batPalletReturnDetails.getIsrepeat())){
				res.setResponseData("0", "操作失败,该批次已经扫码!");
			}else if("e".equalsIgnoreCase(batPalletReturnDetails.getRemark5())){
				res.setResponseData("0", "操作失败,该批次处于禁止状态!");
			}else if("2".equalsIgnoreCase(batPalletReturnDetails.getRemark5())){
				res.setResponseData("0", "操作失败,该批次处于冻结状态!");
			}else{
				res.setResponseData("1", "操作成功!");
				res.setDataset(batPalletReturnDetails, "batPalletReturnDetails");
			}
		}else{
			res.setResponseData("0", "该批次信息有误,请进行核对!");
		}
		return res;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/getPalletReturn")
	public ResponseBase getPalletReturn(HttpServletRequest request){
		ResponseBase res = new ResponseBase();
		String f_date = request.getParameter("f_date").replace("-", ""); 
		String f_pallet_no = request.getParameter("f_pallet_no");
		String billno =f_date+f_pallet_no;
		List<BatPalletReturnDetails> inputList = palletReturnService.getPalletReturn(billno);
		if (inputList != null && inputList.size() > 0){
			res.setResponseData("1", "操作成功!");
			res.setDataset(inputList, "batdepotiodetail");
		}else{
			res.setResponseData("0", "该工单暂无数据!");
		}
		return res;
		
	}
	
	@ResponseBody
	@RequestMapping(value="/deletePalletReturn")	 
	public ResponseBase deletePalletReturn(HttpServletRequest request){
		String pid = request.getParameter("f_pid");
		String userId = request.getParameter("userId");
		ResponseBase res = new ResponseBase();
		boolean falg = palletReturnService.deletePalletReturn(pid, userId);
		if (falg){
			res.setResponseData("1", "操作成功!");
		}else{
			res.setResponseData("0", "删除数据失败!");
		}
		return res;
	}
		
	
}
