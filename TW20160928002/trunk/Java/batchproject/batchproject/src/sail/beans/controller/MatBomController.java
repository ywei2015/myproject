package sail.beans.controller; 
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import sail.beans.base.ResponseBase;
import sail.beans.service.MatBomService;


/**
* <p>类说明：Spring MVC Test Controller Class</p> 
* 
* <p>Copyright: Copyright (c) 2016</p>
*    
* @author xie&li
* 2016-12-28
*
* @version 1.0 
 * 
 */
@Controller
@RequestMapping("/matbom")
public class MatBomController{
	 
	@Resource
	private MatBomService matBomService; 

	/**
	 * 验证工单物料是否一致
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getBomByWorkOrder")	 
	public ResponseBase getBomByWorkOrder(HttpServletRequest request){  
		ResponseBase res = new ResponseBase();
		String workorderCode = request.getParameter("f_workorder_code");
		String process = request.getParameter("f_process");
		String matCode = request.getParameter("f_mat_code");
		List list = matBomService.getBomByWorkOrder(workorderCode, process, matCode);
		if (list.size() > 0){
			res.setResponseData("1", "操作成功!");
		}else{
			res.setResponseData("0", "材料与工单不符!");
		}
		return res;
	}
}
