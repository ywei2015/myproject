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

/**
 * 片烟剔除
 * **/
@Controller
@RequestMapping("/bladefeeding")
public class BladeFeedingController {

	@Resource
	private SilkWorkOrderService silkWorkOrderService; 
	
	@ResponseBody
	@RequestMapping(value="/saveBatWorkOrderInput")	 
	public ResponseBase saveBatWorkOrderInput(HttpServletRequest request) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		String workOrderCode = request.getParameter("f_workorder_code");
		String matBatch = request.getParameter("f_mat_batch");
		String quantity = request.getParameter("f_quantity");
		String location = request.getParameter("location");
		String userId = request.getParameter("userId");
		String tl_type=request.getParameter("f_tl_type");
		String remark=request.getParameter("remark");
		ResponseBase res = new ResponseBase();
		String state=silkWorkOrderService.getWorkorderstate(workOrderCode);
		if(state.equals("1")){
			BatWorkOrderInput batWorkOrderInput = silkWorkOrderService.saveBatWorkOrderInput(workOrderCode, matBatch, quantity, location,userId,tl_type,remark);
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
		}else if(state.equals("0")){
			res.setResponseData("0", "该工单已过期!");
		}else{
			res.setResponseData("0", "操作失败!");
		}
		return res;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/deleteBatWorkOrderInput")	 
	public ResponseBase deleteBatWorkOrderInput(HttpServletRequest request){
		String pid = request.getParameter("f_pid");
		String userId = request.getParameter("userId");
		ResponseBase res = new ResponseBase();
		boolean falg = silkWorkOrderService.deleteBatWorkOrderInput(pid, userId);
		if (falg){
			res.setResponseData("1", "操作成功!");
		}else{
			res.setResponseData("0", "删除数据失败!");
		}
		return res;
	}
	
	
	
		
	
}
