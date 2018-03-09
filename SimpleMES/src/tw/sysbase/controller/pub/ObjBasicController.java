package tw.sysbase.controller.pub; 

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import tw.sysbase.servie.pub.ObjBasicService;
/**
* <p>类说明：Spring MVC Test Controller Class</p> 
* 
* <p>Copyright: Copyright (c) 2016</p>
*    
* @author xie&li
* 2017-08-26
*
* @version 1.0 
 * 
 */
@Controller
@RequestMapping("/ObjBasic")
public class ObjBasicController {
	 
	@Resource
	private ObjBasicService objBasicService;
	
	/**
	 * 异步加载基础模型子节点
	 * @param parentId
	 * @return
	 */
	/*@ResponseBody
	@RequestMapping(value="/fetchObjBaseTree")	
	public ResponseBase fetchObjBaseTree(String superClassPid){  
		ResponseBase res = new ResponseBase();
		
		try {
			List list = objBasicService.fetchObjBaseTree(superClassPid);
			res.setDataset(list, "treenode");
		} catch (Exception e) { 
			e.printStackTrace();
			LogException.logEx(e,ObjBasicController.class,"sysbase.fetchObjBaseList");
		} 
		return res;
	}*/
}
