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
import sail.beans.entity.BatWorkOrderInput;
import sail.beans.entity.User;
import sail.beans.entity.vo.BatWorkOrderVo;
import sail.beans.service.SilkWorkOrderService;


@Controller
@RequestMapping("/silkorder")
public class SilkWorkOrderController {

	@Resource
	private SilkWorkOrderService silkWorkOrderService; 
	
	
	@ResponseBody
	@RequestMapping(value="/getWorkOrderList")	 
	public ResponseBase getWorkOrderList(HttpServletRequest request){
		String type = request.getParameter("f_type");
		ResponseBase res = new ResponseBase();
		List<BatWorkOrderVo> orderList = silkWorkOrderService.getWorkOrderList(type);
		if (orderList != null && orderList.size() > 0){
			res.setResponseData("1", "操作成功!");
			res.setDataset(orderList, "batworkordervo");
		}else{
			res.setResponseData("0", "该类型不存在工单!");
		}
		return res;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/saveBatWorkOrderInput")	 
	public ResponseBase saveBatWorkOrderInput(HttpServletRequest request) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		String workOrderCode = request.getParameter("f_workorder_code");
		String matBatch = request.getParameter("f_mat_batch");
		String quantity = request.getParameter("f_quantity");
		String location = request.getParameter("f_location");
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		ResponseBase res = new ResponseBase();
		boolean state=silkWorkOrderService.getWorkorderstate(workOrderCode);
		if(state){
			BatWorkOrderInput batWorkOrderInput = silkWorkOrderService.saveBatWorkOrderInput(workOrderCode, matBatch, quantity, location, user.getPid());
			if (batWorkOrderInput != null){
				if ("1".equals(batWorkOrderInput.getIsrepair())){
					res.setResponseData("0", "该批次数据已经存在!");
				}else{
					res.setResponseData("1", "操作成功!");
					res.setDataset(batWorkOrderInput, "batworkorderinput");
				}
			}else{
				res.setResponseData("0", "该批次数据有问题，请进行核对!");
			}
			}else{
				res.setResponseData("0", "该工单已过期!");
		}
		return res;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/deleteBatWorkOrderInput")	 
	public ResponseBase deleteBatWorkOrderInput(HttpServletRequest request){
		String pid = request.getParameter("f_pid");
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		ResponseBase res = new ResponseBase();
		boolean falg = silkWorkOrderService.deleteBatWorkOrderInput(pid, user.getPid());
		if (falg){
			res.setResponseData("1", "操作成功!");
		}else{
			res.setResponseData("0", "删除数据失败!");
		}
		return res;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/getBatWorkOrderInput")	 
	public ResponseBase getBatWorkOrderInput(HttpServletRequest request){
		String workOrderCode = request.getParameter("f_workorder_code");
		ResponseBase res = new ResponseBase();
		List<BatWorkOrderInput> inputList = silkWorkOrderService.getBatWorkOrderInput(workOrderCode);
		if (inputList != null && inputList.size() > 0){
			res.setResponseData("1", "操作成功!");
			res.setDataset(inputList, "batdepotiodetail");
		}else{
			res.setResponseData("0", "该工单数据有问题，请进行核对!");
		}
		return res;
	}
	
	@ResponseBody
	@RequestMapping(value="/saveBatchStorageOut")	 
	public ResponseBase saveBatchStorageOut(HttpServletRequest request){
		ResponseBase res = new ResponseBase();
		String f_mat_batch = request.getParameter("f_mat_batch");
		String reason = request.getParameter("f_reason");
		String userId = request.getParameter("userId");
		BatDepotIoDetail batDepotIoDetail = silkWorkOrderService.saveBatchStorageOut(reason,f_mat_batch,userId);
		if (batDepotIoDetail != null){
			res.setResponseData("1", "操作成功!");
			res.setDataset(batDepotIoDetail, "batdepotiodetail");
		}else{
			res.setResponseData("0", "牌号信息不匹配!");
		}
		return res;
	}
		
	
}
