package sail.beans.controller.retrospected;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import sail.beans.dao.iml.Pager;
import sail.beans.entity.FeedingStatistic;
import sail.beans.entity.PersonalFeedingStatistic;
import sail.beans.entity.ProductionWorkorder;
import sail.beans.entity.TopDressFlavor;
import sail.beans.entity.UnacceptMaterial;
import sail.beans.entity.UnacceptMaterialDetail;
import sail.beans.service.retrospect.MaterialUnacceptService;

/*
 * 未确认物资
 */
@Controller
@RequestMapping("/MaterialUnaccept")
public class MaterialUnacceptController {
	@Resource
	private MaterialUnacceptService materialUnacceptService;
	/**
	 * 获取未确认物资 
	 */
	@ResponseBody
	@RequestMapping(value="/materialUnacceptBase")
	public Map<String,Object> materialUnacceptBase(HttpServletRequest request){
		Map map=new HashMap();
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));       //pageSize
        int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));   //pageNumber
        String f_mat_code = request.getParameter("f_mat_code");					 //物料编码
        String startDate = request.getParameter("startDate"); 					 //开始时间
        String endDate = request.getParameter("endDate");						 //结束时间
        Pager page=materialUnacceptService.materialUnacceptForBase(pageSize,pageNumber,f_mat_code,startDate,endDate);
        
		if(page!=null){
			List<UnacceptMaterial> unacceptMaterialList=(List<UnacceptMaterial>) page.getList();
			map.put("rows", unacceptMaterialList);
			map.put("total", page.getRowsTotal());
		}
		return map;
	}
	/**
	 * 查看未确认物资大件明细
	 */
	@ResponseBody
	@RequestMapping(value="/materialUnacceptDetail")
	public Map<String,Object> materialUnacceptDetail(HttpServletRequest request){
		Map map=new HashMap();
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));       //pageSize
        int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));   //pageNumber
        String f_mat_code = request.getParameter("code");					 //物料编码
        String date = request.getParameter("date"); 					 //开始时间
        Pager page=materialUnacceptService.materialUnacceptForDetail(pageSize,pageNumber,f_mat_code,date);
        
		if(page!=null){
			List<UnacceptMaterialDetail> unacceptMaterialDetailList=(List<UnacceptMaterialDetail>) page.getList();
			map.put("rows", unacceptMaterialDetailList);
			map.put("total", page.getRowsTotal());
		}
		
		return map;
	}

	/**
	 * 表香余重
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/topDressFlavorRemainWeight")
	public Map<String,Object> topDressFlavorRemainWeight(HttpServletRequest request){
		Map map=new HashMap();
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));       //pageSize
        int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));   //pageNumber
        
        String matBatch = request.getParameter("matBatch");					 //物料编码
        String startDate = request.getParameter("startDate"); 					 //开始时间
        String endDate = request.getParameter("endDate");						 //结束时间
        
        Pager page=materialUnacceptService.topDressFlavor(pageSize, pageNumber, matBatch, startDate, endDate);
        
		if(page!=null){
			List<TopDressFlavor> topDressFlavorlList=(List<TopDressFlavor>) page.getList();
			map.put("rows", topDressFlavorlList);
			map.put("total", page.getRowsTotal());
		}
		
		return map;
	}
	
	/**
	 * 生产工单
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/productionWorkOrder")
	public Map<String,Object> productionWorkOrder(HttpServletRequest request){
		Map map=new HashMap();
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));       //pageSize
        int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));   //pageNumber
        
        String workorderCode = request.getParameter("workorderCode");					 //物料编码
        String startDate = request.getParameter("startDate"); 					 //开始时间
        String endDate = request.getParameter("endDate");						 //结束时间
        System.out.println(workorderCode+"//"+startDate+"//"+endDate);
        
        Pager page=materialUnacceptService.productWorkOrder(pageSize, pageNumber, workorderCode, startDate, endDate);
        
		if(page!=null){
			List<ProductionWorkorder> productionWorkorderList=(List<ProductionWorkorder>) page.getList();
			map.put("rows", productionWorkorderList);
			map.put("total", page.getRowsTotal());
		}
		
		return map;
	}
	
	/**
	 * 投料统计
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/feedingStatistic")
	public Map<String,Object> feedingStatistic(HttpServletRequest request){
		Map map=new HashMap();
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));       //pageSize
        int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));   //pageNumber
        
        String code = request.getParameter("code");					 //物料编码
        String workorderType = request.getParameter("workorderType");
        String startDate = request.getParameter("startDate"); 					 //开始时间
        String endDate = request.getParameter("endDate");						 //结束时间
        System.out.println(code+"//"+workorderType+"//"+startDate+"//"+endDate);
        
        Pager page=materialUnacceptService.feedstatistic(pageSize,pageNumber,code,workorderType,startDate,endDate);
        
		if(page!=null){
			List<FeedingStatistic> feedingStatisticList=(List<FeedingStatistic>) page.getList();
			map.put("rows", feedingStatisticList);
			map.put("total", page.getRowsTotal());
		}
		
		return map;
	}
	
	/**
	 * 人员投料统计
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/personalFeedingStatistic")
	public Map<String,Object> personalFeedingStatistic(HttpServletRequest request){
		Map map=new HashMap();
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));       //pageSize
        int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));   //pageNumber
        
        String code = request.getParameter("code");					 //物料编码
        String startDate = request.getParameter("startDate"); 					 //开始时间
        String endDate = request.getParameter("endDate");						 //结束时间
        System.out.println(code+" "+startDate+endDate);
        
        Pager page=materialUnacceptService.personFeedStatistic(pageSize,pageNumber,code,startDate,endDate);
        
		if(page!=null){
			List<PersonalFeedingStatistic> personalFeedingStatisticList=(List<PersonalFeedingStatistic>) page.getList();
			map.put("rows", personalFeedingStatisticList);
			map.put("total", page.getRowsTotal());
		}
		
		return map;
	}

/**
 * 下拉框
 * 查找工单类型
 * @param request
 * @return
 */
	@ResponseBody
	@RequestMapping(value="/feedWorkorderType")
	public List<String> feedWorkorderType(HttpServletRequest request){
		List<String> workorderTypeList = new ArrayList<>();
		
		List<String> list = materialUnacceptService.feedWorkorderType();
		if(list != null){
			workorderTypeList.addAll(list);
		}
		
		return workorderTypeList;
	}

}












