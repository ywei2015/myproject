package spc.beans.controller;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import spc.beans.buffer.BaseBuffer;
import spc.beans.entity.spc.TSpcStandard;
import spc.beans.service.rediscache.ParamStandardManagerService;
@Controller
@RequestMapping("/param")
public class ParamBrandMessController {
	
	@Autowired
	private ParamStandardManagerService paramStandardManagerService;
	
	@ResponseBody
	@RequestMapping(value="/brandStandard", produces="text/html;charset=UTF-8")	
	public ResponseEntity<TSpcStandard> testbrandStandard(HttpServletRequest request){ 
		String batch=request.getParameter("batch");
		String brand=request.getParameter("brand");
		String paramId=request.getParameter("param");
		Map<String,TSpcStandard> map =paramStandardManagerService.getLatestParameterStandard(brand, batch);
		String paraflag=BaseBuffer.getParameterTagById(paramId);
		TSpcStandard tsd=map.get(paraflag);
		ResponseEntity<TSpcStandard> entity=new ResponseEntity <TSpcStandard>(tsd,HttpStatus.OK);
		return entity;
	}
}
