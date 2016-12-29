package sail.beans.controller; 
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import sail.beans.base.ResponseBase;
import sail.beans.entity.Dic;
import sail.beans.service.DicService;


/**
* <p>类说明：Spring MVC Test Controller Class</p> 
* 
* <p>Copyright: Copyright (c) 2016</p>
*    
* @author xie&li
* 2016-08-26
*
* @version 1.0 
 * 
 */
@Controller
@RequestMapping("/dic")
public class DicController{
	 
	@Resource
	private DicService dicService; 

	/**
	 * 根据类型获取入库类型
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getDicList")	 
	public ResponseBase getDicList(HttpServletRequest request){  
		ResponseBase res = new ResponseBase();
		String type = request.getParameter("f_type");
		List<Dic> dicList = dicService.getDicList(type);
		res.setResponseData("1", "操作成功!");
		res.setDataset(dicList, "dic");
		return res;
	}
}
