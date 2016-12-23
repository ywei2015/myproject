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
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		BatSpiceRemain batSpiceRemain = spiceRemainService.saveBatSpiceRemain(matbatch, location, quantity, user.getPid());
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
}
