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
		String workOrderCode1 = request.getParameter("f_workorder_code");
		String workOrderCode=null;
		if(workOrderCode1!=null&&workOrderCode1.length()>0)
			 workOrderCode=workOrderCode1.substring(workOrderCode1.lastIndexOf("-")+1,workOrderCode1.length());
		String matBatch = request.getParameter("f_mat_batch");
		String machine = request.getParameter("f_machine");
		String userId = request.getParameter("userId");
		ResponseBase res = new ResponseBase();
		BatWorkOrderInput batWorkOrderInput = rollBatchService.saveBatWorkOrderInput(workOrderCode,machine, matBatch,userId);
		if (batWorkOrderInput != null){
			if ("1".equals(batWorkOrderInput.getIsrepair())){
				res.setResponseData("0", "该批次数据已经存在!");
			}else{
				if("e".equalsIgnoreCase(batWorkOrderInput.getRemark5())){
					res.setResponseData("0", "操作失败,该批次处于禁止状态!");
				}else if("2".equalsIgnoreCase(batWorkOrderInput.getRemark5())){
					res.setResponseData("0", "操作失败,该批次处于冻结状态!");
				}else if("w".equalsIgnoreCase(batWorkOrderInput.getRemark5())){
					res.setResponseData("1", "该批次需要关注!");
				}else{
					res.setResponseData("1", "操作成功!");
					res.setDataset(batWorkOrderInput, "batworkorderinput");
				}
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
		String userId = request.getParameter("userId");
		ResponseBase res = new ResponseBase();
		boolean falg = rollBatchService.deleteBatWorkOrderInput(pid, userId);
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
		String workOrderCode1 = request.getParameter("f_workorder_code");
		String workOrderCode=null;
		if(workOrderCode1!=null&&workOrderCode1.length()>0)
			 workOrderCode=workOrderCode1.substring(workOrderCode1.lastIndexOf("-")+1,workOrderCode1.length());
		ResponseBase res = new ResponseBase();
		List<BatWorkOrderInput> inputList = rollBatchService.getBatWorkOrderInput(workOrderCode);
		if (inputList != null && inputList.size() > 0){
			res.setResponseData("1", "操作成功!");
			res.setDataset(inputList, "batworkorderinput");
		}else{
			res.setResponseData("0", "该工单数据有问题，请进行核对!");
		}
		return res;
	}
	
	@ResponseBody
	@RequestMapping(value="/getWorkOrderAndProcess")	
	public ResponseBase getWorkOrderAndProcess(HttpServletRequest request){
		String worktype = request.getParameter("f_workorder_type");
		ResponseBase res = new ResponseBase();
		Map<String,List> inputList = rollBatchService.getWorkOrderAndProcess(worktype);
		if (inputList != null && inputList.size() > 0){
			res.setResponseData("1", "操作成功!");
			res.setDataset(inputList, "workOrderAndProcessList");
		}else{
			res.setResponseData("0", "该工单数据暂无，请进行核对!");
		}
		return res;
	}
}
