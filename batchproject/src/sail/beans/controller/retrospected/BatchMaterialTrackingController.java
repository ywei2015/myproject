package sail.beans.controller.retrospected;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import sail.beans.dao.iml.Pager;
import sail.beans.entity.BatDepotIoDetail;
import sail.beans.entity.BatWorkOrder;
import sail.beans.entity.BatWorkOrderInput;
import sail.beans.entity.BatchMeterialTracking;
import sail.beans.entity.BatchRetrospect;
import sail.beans.service.retrospect.BatchMaterialTrackingService;
import sail.beans.service.retrospect.BatchRetrospectService;
import sail.beans.service.transfromdata.TransfromdataService;

/*
 * 批次追溯服务类
 */
@Controller
@RequestMapping("/batchMaterialTracking")
public class BatchMaterialTrackingController {
	
	@Resource
	private BatchMaterialTrackingService batchMaterialTrackingService;
	 
	/**
	 * 物料批次追踪
	 * @param request
	 * @return Map<String,Object>
	 */
	@ResponseBody
	@RequestMapping(value="/batchMaterialTr")
	 public Map<String,Object> batchMaterialTr(HttpServletRequest request){
		Map map=new HashMap();
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));  
		int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));  
        String batch = request.getParameter("orderNum") ;
    	Pager page=batchMaterialTrackingService.batchMaterialTr(pageSize, pageNumber, batch);
		if(page!=null){
			List<BatchMeterialTracking> batchRetrospectList=(List<BatchMeterialTracking>) page.getList();
			map.put("rows", batchRetrospectList);
			map.put("total", page.getRowsTotal());
		}
		return map;
	}
	/**
	 * 批次投料查询
	 * @param request
	 * @return Map<String,Object>
	 */
	@ResponseBody
	@RequestMapping(value="/batchMaterialInfo")
	 public Map<String,Object> batchMaterialInfo(HttpServletRequest request){
		Map map=new HashMap();
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));  
		int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));  
        String batch = request.getParameter("orderNum") ;
    	Pager page=batchMaterialTrackingService.batchMaterialInfo(pageSize, pageNumber, batch);
		if(page!=null){
			List<BatchMeterialTracking> batchRetrospectList=(List<BatchMeterialTracking>) page.getList();
			map.put("rows", batchRetrospectList);
			map.put("total", page.getRowsTotal());
		}
		return map;
	}
	
	/**
	 * 批次号投料记录
	 * @param request
	 * @return Map<String,Object>
	 */
	
	@ResponseBody
	@RequestMapping(value="/workOrderInfo")
	 public Map<String,Object> workOrderInfo(HttpServletRequest request){
		Map map=new HashMap();
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));  
		int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));  
        String batch = request.getParameter("orderNum") ;
    	Pager page=batchMaterialTrackingService.workOrderInfo(pageSize, pageNumber, batch);
		if(page!=null){
			List<BatchMeterialTracking> batchRetrospectList=(List<BatchMeterialTracking>) page.getList();
			map.put("rows", batchRetrospectList);
			map.put("total", page.getRowsTotal());
		}
		return map;
	}
}
