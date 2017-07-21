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
import sail.beans.entity.BatWorkOrder;
import sail.beans.entity.BatWorkOrderInput;
import sail.beans.entity.User;
import sail.beans.entity.vo.BatWorkOrderVo;
import sail.beans.service.LeafSpiceService;
import sail.beans.service.SilkWorkOrderService;

/**
 * 叶片加料
 * **/
@Controller
@RequestMapping("/leafSpice")
public class LeafSpiceController {

	@Resource
	private SilkWorkOrderService silkWorkOrderService; 
	@Resource
	private LeafSpiceService leafSpiceService; 
	
	
	@ResponseBody
	@RequestMapping(value="/saveBatWorkOrderInput")	 
	public ResponseBase saveBatWorkOrderInput(HttpServletRequest request) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		String workOrderCode = request.getParameter("f_workorder_code");
		if(workOrderCode.contains("-")){
			workOrderCode=workOrderCode.substring(workOrderCode.lastIndexOf("-")+1, workOrderCode.length());
		}
		String matBatch = request.getParameter("f_mat_batch");
		String quantity = request.getParameter("f_quantity");
		String location = request.getParameter("location");
		String userId = request.getParameter("userId");
		String tl_type=request.getParameter("f_tl_type");
		ResponseBase res = new ResponseBase();
			BatWorkOrderInput batWorkOrderInput = silkWorkOrderService.saveBatWorkOrderInput(workOrderCode, matBatch, quantity, location,userId,tl_type,"2","JM03");
			if (batWorkOrderInput != null){
				if ("1".equals(batWorkOrderInput.getIsrepair())){
					res.setResponseData("0", "该批次数据已经存在!");
				}else{
					if("e".equalsIgnoreCase(batWorkOrderInput.getRemark5())){
						res.setResponseData("0", "操作失败,该批次处于禁止状态!");
					}else if("2".equalsIgnoreCase(batWorkOrderInput.getRemark5())){
						res.setResponseData("0", "操作失败,该批次处于冻结状态!");
					}else{
						res.setResponseData("1", "操作成功!");
						res.setDataset(batWorkOrderInput, "batworkorderinput");
					}
				}
			}else{
				res.setResponseData("0", "该批次信息有误,请进行核对!");
			}
		return res;
	}
	

}
