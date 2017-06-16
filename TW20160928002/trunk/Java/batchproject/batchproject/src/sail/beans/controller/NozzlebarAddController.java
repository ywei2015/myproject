package sail.beans.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import sail.beans.base.ResponseBase;
import sail.beans.entity.BatDepotIoDetail;
import sail.beans.entity.BatFiltertipPut;
import sail.beans.entity.BatWorkOrder;
import sail.beans.entity.BatWorkOrderInput;
import sail.beans.entity.User;
import sail.beans.entity.vo.BatWorkOrderVo;
import sail.beans.service.NozzlebarAddService;
import sail.beans.service.SilkReuseService;
import sail.beans.service.SilkWorkOrderService;

/**
 * 嘴棒手工添加
 * */
@Controller
@RequestMapping("/nozzlebaradd")
public class NozzlebarAddController {
	@Resource
	private NozzlebarAddService nozzlebarAddService;
	
	@ResponseBody
	@RequestMapping(value="/saveNozzlebaradd")	 
	public ResponseBase saveNozzlebaradd(HttpServletRequest request) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		String f_jitai_id = request.getParameter("f_jitai_id"); 
		String f_mat_batch = request.getParameter("f_mat_batch");
		String userId=request.getParameter("userId");
		ResponseBase res = new ResponseBase();
		BatFiltertipPut batFiltertipPut = nozzlebarAddService.saveNozzlebaradd(f_jitai_id,f_jitai_id, f_mat_batch,userId);
		if (batFiltertipPut != null){
			if("1".equals(batFiltertipPut.getIsrepair())){
				res.setResponseData("0", "操作失败,该批次已经扫码!");
			}else if("e".equalsIgnoreCase(batFiltertipPut.getRemark5())){
				res.setResponseData("0", "操作失败,该批次处于禁止状态!");
			}else if("2".equalsIgnoreCase(batFiltertipPut.getRemark5())){
				res.setResponseData("0", "操作失败,该批次处于冻结状态!");
			}else{
				res.setResponseData("1", "操作成功!");
				res.setDataset(batFiltertipPut, "batFiltertipPut");
			}
		}else{
			res.setResponseData("0", "该批次信息有误,请进行核对!");
		}
		return res;
	}
	
	/**
	 * 获取发射机组信息
	 * */
	@ResponseBody
	@RequestMapping(value="/getJiTaiMes")
	public ResponseBase getJiTaiMes(HttpServletRequest request){
		ResponseBase res = new ResponseBase();
		//String f_jitai_id = request.getParameter("f_jitai_id");
		List<Map> inputList = nozzlebarAddService.getJiTaiMes();
		if (inputList != null && inputList.size() > 0){
			res.setResponseData("1", "操作成功!");
			res.setDataset(inputList, "map");
		}else{
			res.setResponseData("0", "无可用批次!");
		}
		return res;
	}
	
	@ResponseBody
	@RequestMapping(value="/getBatFiltertipPut")
	public ResponseBase getBatFiltertipPut(HttpServletRequest request){
		ResponseBase res = new ResponseBase();
		String f_jitai_id = request.getParameter("f_jitai_id");
		List<BatFiltertipPut> inputList = nozzlebarAddService.getBatFiltertipPut(f_jitai_id);
		if (inputList != null && inputList.size() > 0){
			res.setResponseData("1", "操作成功!");
			res.setDataset(inputList, "batdepotiodetail");
		}else{
			res.setResponseData("0", "该工单暂无数据!");
		}
		return res;
		
	}
	
	@ResponseBody
	@RequestMapping(value="/deleteBatFiltertipPut")	 
	public ResponseBase deleteIeafInput(HttpServletRequest request){
		String pid = request.getParameter("f_pid");
		String userId = request.getParameter("userId");
		ResponseBase res = new ResponseBase();
		boolean falg = nozzlebarAddService.deleteBatFiltertipPut(pid, userId);
		if (falg){
			res.setResponseData("1", "操作成功!");
		}else{
			res.setResponseData("0", "删除数据失败!");
		}
		return res;
	}
	
}
