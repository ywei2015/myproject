package spc.beans.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import spc.beans.buffer.BaseBuffer;
import spc.beans.entity.spc.TSpcProcess;
import spc.beans.service.RegatherDataService;
/**
 * @Description 补采集程序控制
 * @author yww
 * @date 2017-10-16 
 * */
@Controller
@RequestMapping("/regather")
public class RegatherController {
	
	@Autowired
	private RegatherDataService regatherService;
	/**
	 * 获取所有工序参数基础信息
	 * */
	@ResponseBody
	@RequestMapping(value="/getProcessMap")
	public Map<String,TSpcProcess>  getProcessMap(){
		return BaseBuffer.getProcessMap();
	}
	
	/**
	 * 补采
	 * */
	@ResponseBody
	@RequestMapping(value="/regatherByProcessBatch", produces="text/html;charset=UTF-8")
	public String  regatherByProcessBatch(HttpServletRequest request){
		String batch=request.getParameter("batch");
		String process=request.getParameter("process");
		String startTime=request.getParameter("startTime");
		String endTime=request.getParameter("endDate");
		String mess= regatherService.regatherByProcessBatch(batch,process,startTime,endTime);
		return mess;
	}
	
}
