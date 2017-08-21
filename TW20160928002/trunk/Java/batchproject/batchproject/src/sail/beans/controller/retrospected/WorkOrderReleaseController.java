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
import sail.beans.service.retrospect.WorkOrderReleaseService;
import sail.beans.service.transfromdata.TransfromdataService;

/*
 * 批次追溯服务类
 */
@Controller
@RequestMapping("/workOrderRelease")
public class WorkOrderReleaseController {
	
	@Resource
	private WorkOrderReleaseService workOrderReleaseService;
	 
	/**
	 * 工单投料统计查询
	 * @param request
	 * @return Map<String,Object>
	 */
	@ResponseBody
	@RequestMapping(value="/workorderRe")
	 public Map<String,Object> workorderRe(HttpServletRequest request){
		Map map=new HashMap();
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));
		int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));  
        String orderNum = request.getParameter("orderNum") ;
        String[] date = orderNum.split(" - ");
        String startDate = date[0].replace("-", "");
        String endDate = date[1].replace("-", "");
        String worktime = request.getParameter("worktime");
        String workorderType = request.getParameter("workorderType");
    	Pager page=workOrderReleaseService.workorderRel(pageSize, pageNumber, startDate,endDate,worktime,workorderType);
		if(page!=null){
			List<BatchMeterialTracking> batchRetrospectList=(List<BatchMeterialTracking>) page.getList();
			map.put("rows", batchRetrospectList);
			map.put("total", page.getRowsTotal());
		}
		return map;
	}
	
}
