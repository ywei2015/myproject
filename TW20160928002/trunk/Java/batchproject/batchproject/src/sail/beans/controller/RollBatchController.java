package sail.beans.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import sail.beans.base.ResponseBase;
import sail.beans.entity.BatWorkOrderInput;
import sail.beans.entity.User;
import sail.beans.service.RollBatchService;

@Controller
@RequestMapping("/rollbatch")
public class RollBatchController {
	@Resource
	private RollBatchService rollBatchService; 
	
	
	@ResponseBody
	@RequestMapping(value="/saveBatWorkOrderInput")	 
	public ResponseBase saveBatWorkOrderInput(HttpServletRequest request) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		String workOrderCode = request.getParameter("f_workorder_code");
		String matBatch = request.getParameter("f_mat_batch");
		String machine = request.getParameter("f_machine");
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		ResponseBase res = new ResponseBase();
		BatWorkOrderInput batWorkOrderInput = rollBatchService.saveBatWorkOrderInput(workOrderCode,machine, matBatch,user.getPid());
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
		return res;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/deleteBatWorkOrderInput")	 
	public ResponseBase deleteBatWorkOrderInput(HttpServletRequest request){
		String pid = request.getParameter("f_pid");
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		ResponseBase res = new ResponseBase();
		boolean falg = rollBatchService.deleteBatWorkOrderInput(pid, user.getPid());
		if (falg){
			res.setResponseData("1", "操作成功!");
		}else{
			res.setResponseData("0", "删除数据失败!");
		}
		return res;
	}
}
