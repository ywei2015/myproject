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
import sail.beans.service.SilkReuseService;
import sail.beans.service.SilkWorkOrderService;

/**
 * 余梗投料
 * **/
@Controller
@RequestMapping("/scrapFeed")
public class ScrapFeedController {

	@Resource
	private SilkReuseService silkReuseService;
	
	
	@ResponseBody
	@RequestMapping(value="/saveScrapFeedInput")	 
	public ResponseBase saveScrapFeedInput(HttpServletRequest request) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		String workOrderCode = request.getParameter("f_workorder_code");
		String matBatch = request.getParameter("f_mat_batch");
		if(matBatch.contains("-")){
			matBatch=matBatch.substring(0, matBatch.indexOf("-"));
		}
		String quantity = request.getParameter("f_quantity");
		String location = request.getParameter("location");
		String userId = request.getParameter("userId");
		String remark=request.getParameter("remark");
		ResponseBase res = new ResponseBase();
		boolean enough=silkReuseService.isEnough(matBatch,quantity,"gengsi");
		if(enough){
			BatWorkOrderInput batWorkOrderInput = silkReuseService.saveSilkReuseInput(workOrderCode, matBatch, quantity,remark,location,userId);
			if (batWorkOrderInput != null){
				if("e".equalsIgnoreCase(batWorkOrderInput.getRemark5())){
					res.setResponseData("0", "操作失败,该批次处于禁止状态!");
				}else if("2".equalsIgnoreCase(batWorkOrderInput.getRemark5())){
					res.setResponseData("0", "操作失败,该批次处于冻结状态!");
				}else{
					res.setResponseData("1", "操作成功!");
					res.setDataset(batWorkOrderInput, "batworkorderinput");
				}
			}
			else{
				res.setResponseData("0", "该批次信息有误,请进行核对!");
			}
		}else{
			res.setResponseData("0", "批次物料数量不足!");
		}
		return res;
	}
	
	@ResponseBody
	@RequestMapping(value="/getScrapFeedBatch")
	public ResponseBase getScrapFeedBatch(HttpServletRequest request){
		ResponseBase res = new ResponseBase();
		String workOrderCode = request.getParameter("f_workorder_code");
		List<String> inputList = silkReuseService.getSilkReuseBatch("gengsi",workOrderCode);
		if (inputList != null && inputList.size() > 0){
			res.setResponseData("1", "操作成功!");
			res.setDataset(inputList, "workOrderAndProcessList");
		}else{
			res.setResponseData("0", "无可用批次!");
		}
		return res;
	}
	
	
	
		
	
}
