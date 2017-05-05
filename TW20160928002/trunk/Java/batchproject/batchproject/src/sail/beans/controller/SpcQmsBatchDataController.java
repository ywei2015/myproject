package sail.beans.controller; 
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import sail.beans.base.ResponseBase;
import sail.beans.service.SpcQmsBatchDataService;

/**
 * SPC制丝工艺质量参数转储
 * 2017-05-05
 */

@Controller
@RequestMapping("/spcQms")
public class SpcQmsBatchDataController{
	 
	@Resource
	private SpcQmsBatchDataService spcQmsBatchDataService; 

	/**
	 * 通过批次号查找
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getSpcQmsDataByBatch")	 
	public ResponseBase getBomByWorkOrder(HttpServletRequest request){  
		ResponseBase res = new ResponseBase();
		String batch = request.getParameter("batch");
		boolean flag = spcQmsBatchDataService.SaveSpcQmsBatchData(batch);
		if (flag){
			res.setResponseData("1", "操作成功!");
		}else{
			res.setResponseData("0", "操作失败!");
		}
		return res;
	}
}
