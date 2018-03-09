package spc.beans.controller; 

import java.util.Map;
 






import javax.servlet.http.HttpServletRequest;  

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
 






import spc.beans.base.ResponseBase;
import spc.beans.buffer.AppInfo;
import spc.beans.buffer.ProcessBatchBuffer; 
import spc.beans.entity.spc.TSpcStandard;
import spc.beans.service.rediscache.ParamStandardManagerService;
 
/** 
* @ClassName: TestController 
* @Description: TODO(Spring MVC Test Controller Class) 
* @author xieshihe 
* @date 2017年9月15日 上午8:35:50 
*  
*/
@Controller
@RequestMapping("/test")
public class TestController{
	
	@Autowired
	private ParamStandardManagerService paramStandardManagerService;
//	@Resource
//	private TestService testService;  

	/** 获取应用程序运行状态信息 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getAppinfo")	
	public ResponseBase getAppinfo(){  
		ResponseBase res = new ResponseBase(); 
		Map runInfo = AppInfo.toMap();
		res.setDataset(runInfo, "AppInfo"); 
		return res;
	} 

	/** 获取工序批次信息 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getProcessBatch")	
	public ResponseBase getProcessBatch(){  
		ResponseBase res = new ResponseBase(); 
		Map processbatchInfo = ProcessBatchBuffer.getProcessBatchMap();
		res.setDataset(processbatchInfo, "processbatchInfo"); 
		return res;
	} 

	/** 单实体对象示例
	 * @param pid
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getEntity")	
	public ResponseBase getEntity(String id){  
		ResponseBase res = new ResponseBase();
		/*try { 
			res = testService.getEntityByID2(id);
		} catch (Exception e) { 
			e.printStackTrace();
			LogException.logEx(e,TestController.class,"test.getEntity");
		} */
		return res;
	} 
	/**分页示例
	 * @param typeid 类型ID
	 * @param start 第几页(前台固定不可修改)
	 * @param limit 每页大小(前台固定不可修改)
	 * @return 
	 */
	@ResponseBody
	@RequestMapping(value="/getEntityPage")	 
	public ResponseBase getEntityListByPage(String typeid, int start, int limit){  
		ResponseBase res = new ResponseBase(); 
		/*QueryCorrelation qryRelation = new QueryCorrelation();
		qryRelation.setLimit(limit);
		qryRelation.setStart(start);
		try {
			res = testService.getEntityListByID(typeid, qryRelation);
		} catch (Exception e) { 
			e.printStackTrace();
			LogException.logEx(e, TestController.class, "test.getEntityPage");
		} */
		return res;
	}

	
	/**保存 
	 * @param request 请求对象(其中包含dataset.jsonobj) 
	 * @return 
	 */
	@ResponseBody
	@RequestMapping(value="/SaveEntity")	 
	public ResponseBase saveEntity(HttpServletRequest request){  
		ResponseBase res = new ResponseBase();
		/*TestEntity obj = (TestEntity) RequestFetcher.getDataset(request, TestEntity.class);
		try {
			res = testService.saveEntity(obj);
		} catch (Exception e) { 
			e.printStackTrace();
			LogException.logEx(e, TestController.class, "test.SaveEntity");
		} */
		return res;
	}
	
}
