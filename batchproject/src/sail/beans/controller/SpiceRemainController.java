package sail.beans.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import sail.beans.base.ResponseBase;
import sail.beans.entity.BatSpiceRemain;
import sail.beans.entity.BatWorkOrderInput;
import sail.beans.entity.Dic;
import sail.beans.entity.User;
import sail.beans.service.SpiceRemainService;

@Controller
@RequestMapping("/spiceremain")
public class SpiceRemainController {
	@Resource
	private SpiceRemainService spiceRemainService; 
	
	
	@ResponseBody
	@RequestMapping(value="/saveBatSpiceRemain")	 
	public ResponseBase saveBatSpiceRemain(HttpServletRequest request) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{  
		ResponseBase res = new ResponseBase();
		String matbatch = request.getParameter("f_mat_batch");
		String location = request.getParameter("f_location");
		String quantity = request.getParameter("f_quantity");
		String userId = request.getParameter("userId");
		BatSpiceRemain batSpiceRemain = spiceRemainService.saveBatSpiceRemain(matbatch, location, quantity, userId);
		if (batSpiceRemain != null){
			if ("1".equals(batSpiceRemain.getIsrepair())){
				res.setResponseData("0", "该批次数据已经存在!");
			}else{
				res.setResponseData("1", "操作成功!");
				res.setDataset(batSpiceRemain, "batspiceremain");
			}
		}else{
			res.setResponseData("0", "操作失败!");
		}
		
		return res;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/deleteBatSpiceRemain")	 
	public ResponseBase deleteBatSpiceRemain(HttpServletRequest request){  
		ResponseBase res = new ResponseBase();
		String pid = request.getParameter("f_pid");
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		boolean falg = spiceRemainService.deleteBatSpiceRemain(pid,user.getPid());
		if (falg){
			res.setResponseData("1", "操作成功!");
		}else{
			res.setResponseData("0", "操作失败!");
		}
		
		return res;
	}
	@ResponseBody
	@RequestMapping(value="/getBatSpiceRemain")	
	public ResponseBase getBatSpiceRemainList(HttpServletRequest request){
		String workOrderCode = request.getParameter("f_workorder_code");
		ResponseBase res = new ResponseBase();
		List<BatSpiceRemain> inputList = spiceRemainService.getBatSpiceRemainList();
		if (inputList != null && inputList.size() > 0){
			res.setResponseData("1", "操作成功!");
			res.setDataset(inputList, "batspiceremain");
		}else{
			res.setResponseData("0", "该工单数据有问题，请进行核对!");
		}
		return res;
	}
}
