package sail.beans.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import sail.beans.base.ResponseBase;
import sail.beans.entity.BatWorkOrderInput;
import sail.beans.entity.CarCode;
import sail.beans.service.SilkReuseService;
@Controller
@RequestMapping("/silkreuse")
public class SilkReuseController {
	
	@Resource
	private SilkReuseService silkReuseService;
	
	@ResponseBody
	@RequestMapping(value="/saveSilkReuseInput")	 
	public ResponseBase saveSilkReuseInput(HttpServletRequest request){
		String workOrderCode = request.getParameter("f_workorder_code");
		String matBatch = request.getParameter("f_mat_batch");
		String quantity = request.getParameter("f_quantity");
		String location = request.getParameter("location");
		String userId = request.getParameter("userId");
		String remark = request.getParameter("remark");
		ResponseBase res = new ResponseBase();
		boolean enough=silkReuseService.isEnough(matBatch,quantity,"cansi");
		if(enough){
			BatWorkOrderInput batWorkOrderInput = silkReuseService.saveSilkReuseInput(workOrderCode, matBatch, quantity, remark,location,userId);
			if (batWorkOrderInput != null){
				if("e".equalsIgnoreCase(batWorkOrderInput.getRemark5())){
					res.setResponseData("0", "操作失败,该批次处于禁止状态!");
				}else if("2".equalsIgnoreCase(batWorkOrderInput.getRemark5())){
					res.setResponseData("0", "操作失败,该批次处于冻结状态!");
				}else{
					res.setResponseData("1", "操作成功!");
					res.setDataset(batWorkOrderInput, "batworkorderinput");
				}
			}else{
				res.setResponseData("0", "该批次信息有误,请进行核对!");
			}
		}else{
			res.setResponseData("0", "批次物料数量不足!");
		}
		return res;
		
}
	@ResponseBody
	@RequestMapping(value="/getSilkReuseBatch")	
	public ResponseBase getSilkReuseBatch(HttpServletRequest request){
		ResponseBase res = new ResponseBase();
		List<String> inputList = silkReuseService.getSilkReuseBatch("cansi",null);
		if (inputList != null && inputList.size() > 0){
			res.setResponseData("1", "操作成功!");
			res.setDataset(inputList, "workOrderAndProcessList");
		}else{
			res.setResponseData("0", "无可用批次!");
		}
		return res;
	}
	
	@ResponseBody
	@RequestMapping(value="/getSilkReuseValue")	
	public ResponseBase getSilkReuseValue(HttpServletRequest request){
		String matBatch = request.getParameter("f_mat_batch");
		ResponseBase res = new ResponseBase();
		CarCode carcode=silkReuseService.getSilkReuseValue(matBatch);
		if (carcode.getMatcode()!=null){
			res.setResponseData("1", "操作成功!");
			res.setDataset(carcode, "CarCode");
		}else{
			res.setResponseData("0", "批次信息不正确!");
		}
		return res;
	}
}
