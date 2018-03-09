package tw.business.controller.equ;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tw.business.service.equ.EquTaskGenerateService;
import tw.sysbase.pub.Constants;
import tw.sysbase.pub.ResultUtil; 

/**
 * 设备维保任务自动生成
 * @author xsh 2018-01-22 
 */
@Controller
@RequestMapping("/equwbtask")
public class GenerateWbTaskController {
	Logger logger = Logger.getLogger(GenerateWbTaskController.class);
	@Resource
	EquTaskGenerateService equTaskGenerateService;

	/**
	 * 获取设备维保任务定义列表数据
	 * @param equWbtaskDefine
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/generatebyid")
	public Object findEquWbtaskDefineList(String taskdefineid){ 
		if(taskdefineid==null||"".equals(taskdefineid)) {
			taskdefineid = "40288c77610c098f01610c0d35380001";
		}
		String id= equTaskGenerateService.generateTaskByDefineId(taskdefineid);
		if(StringUtils.isBlank(id)) {
			return ResultUtil.ErrorResult();
		}
		Map<String, Object> result = ResultUtil.DefaultResult();
		if("1".equals(id)) {
			result.put(Constants.MESSAGE, "已经生成任务，不能重复生成");
		}
		return result;
	}
	
	
}
