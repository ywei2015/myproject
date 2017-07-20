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
import sail.beans.entity.BatWorkOrder;
import sail.beans.entity.BatWorkOrderInput;
import sail.beans.entity.BatchRetrospect;
import sail.beans.service.retrospect.BatchRetrospectService;
import sail.beans.service.transfromdata.TransfromdataService;

/*
 * 批次追溯服务类
 */
@Controller
@RequestMapping("/batchRetrospect")
public class BatchRetrospectController {
	
	@Resource
	private BatchRetrospectService batchRetrospectService;
	 
	@ResponseBody
	@RequestMapping(value="/batchRetrospectForJianYan")
	 public Map<String,Object> batchRetrospectForJianYan(HttpServletRequest request){
		Map map=new HashMap();
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));  
        int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));  
        String batch = request.getParameter("orderNum") ; 
		Pager page=batchRetrospectService.batchRetrospectForJianYan(pageSize,pageNumber,batch);
		if(page!=null){
			List<BatchRetrospect> batchRetrospectList=(List<BatchRetrospect>) page.getList();
			map.put("rows", batchRetrospectList);
			map.put("total", page.getRowsTotal());
		}
		return map;
	}
	
	@ResponseBody
	@RequestMapping(value="/batchRetrospectForJBao")
	 public Map<String,Object> batchRetrospectForJBao(HttpServletRequest request){
		Map map=new HashMap();
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));  
        int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));  
        String code = request.getParameter("lcode") ; 
		Pager page=batchRetrospectService.batchRetrospectForJBao(pageSize,pageNumber,code);
		if(page!=null){
			List<BatWorkOrder> batchRetrospectList=(List<BatWorkOrder>) page.getList();
			map.put("rows", batchRetrospectList);
			map.put("total", page.getRowsTotal());
		}
		return map;
	}
	
	@ResponseBody
	@RequestMapping(value="/batchRetrospectForWorkOder")
	 public BatWorkOrder batchRetrospectForWorkOder(HttpServletRequest request){
        String code = request.getParameter("workcode") ; 
        String batch = request.getParameter("batch") ;
        String workcode=batchRetrospectService.getWorkcodeBypara(code,batch);
        BatWorkOrder batWorkOrder=batchRetrospectService.batchRetrospectForWorkOder(workcode);
		return batWorkOrder;
	}
	

	@ResponseBody
	@RequestMapping(value="/retrospectInputByGroup")
	 public Map retrospectInputByGroup(HttpServletRequest request){
		Map map=new HashMap();
		List batlist=new ArrayList();
        String code = request.getParameter("workcode"); 
        String batch = request.getParameter("batch") ;
        String workcode=batchRetrospectService.getWorkcodeBypara(code,batch);
        BatWorkOrder batWorkOrder=batchRetrospectService.retrospectInputByGroup(workcode);
        batlist.add(batWorkOrder);
        map.put("rows", batlist);
		return map;
	}
	
	@ResponseBody
	@RequestMapping(value="/retrospectInput")
	 public Map retrospectInput(HttpServletRequest request){
		Map map=new HashMap();
        String code = request.getParameter("workcode") ; 
        String batch = request.getParameter("batch") ;
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));  
        int pageNumber = Integer.parseInt(request.getParameter("pageNumber")); 
        String workcode=batchRetrospectService.getWorkcodeBypara(code,batch);
        Pager page=batchRetrospectService.retrospectInput(workcode,pageSize,pageNumber);
        map.put("rows", page.getList());
        map.put("total", page.getPageTotal());
		return map;
	}
	 
}
