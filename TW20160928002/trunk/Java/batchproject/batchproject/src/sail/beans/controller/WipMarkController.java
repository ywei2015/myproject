package sail.beans.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import sail.beans.base.ResponseBase;
import sail.beans.entity.BatWipMarkDetail;
import sail.beans.service.WipMarkService;

/*
 * 在制品贴码
 * */
@Controller
@RequestMapping(value="/wipmark")
public class WipMarkController {
	@Resource
	private WipMarkService wipMarkService;
	
	@ResponseBody
	@RequestMapping(value="/saveWipMark")
	public ResponseBase saveWipMark(HttpServletRequest request){
		String billno=request.getParameter("billno");
		String batchno=request.getParameter("batchno");
		String refbatchno=request.getParameter("refbatchno");
		String userId=request.getParameter("userId");
		ResponseBase res=new ResponseBase();
		BatWipMarkDetail batWipMarkDetail=wipMarkService.saveWipMark(billno,batchno,refbatchno,userId);
		if (batWipMarkDetail != null){
			if("1".equals(batWipMarkDetail.getIsrepeat())){
				res.setResponseData("0", "失败!改批次已经存在!");
			}else{
				res.setResponseData("1", "操作成功!");
				res.setDataset(batWipMarkDetail, "batdepotiodetail");
			}
			
		}else{
			res.setResponseData("0", "批次信息不匹配!");
		}
		return res;
	}
	
	@ResponseBody
	@RequestMapping(value="/getWipMark")
	public ResponseBase getWipMark(HttpServletRequest request){
		String billno=request.getParameter("f_workorder_code");
		ResponseBase res=new ResponseBase();
		List<BatWipMarkDetail> batWipMarkDetails=wipMarkService.getWipMark(billno);
		if(batWipMarkDetails!=null&&batWipMarkDetails.size()>0){
			res.setResponseData("1", "操作成功!");
			res.setDataset(batWipMarkDetails, "batdepotiodetail");
		}else{
			res.setResponseData("0", "暂无数据!");
		}
		return res;
	}
	
	@ResponseBody
	@RequestMapping(value="/deleteWipMark")
	public ResponseBase deleteWipMark(HttpServletRequest request){
		String pid=request.getParameter("f_pid");
		String userId=request.getParameter("userId");
		ResponseBase res=new ResponseBase();
		/*Map map=new HashMap<>();
		map.put("1","aa");*/
		boolean falg=wipMarkService.deleteWipMark(pid,userId);
		if (falg){
			res.setResponseData("1", "操作成功!");
		}else{
			res.setResponseData("0", "信息不匹配!");
		}
		return res;
	}
	
	@ResponseBody
	@RequestMapping(value="/getWipMarkBillNo")
	public ResponseBase getWipMarkBillNo(){
		ResponseBase res=new ResponseBase();
		List<String> workList=wipMarkService.getWipMarkBillNo();
		if(workList!=null&&workList.size()>0){
			res.setResponseData("1", "成功!");
			res.setDataset(workList, "worklist");
		}else{
			res.setResponseData("0", "工单数据暂无存在!");
		}
		return res;
	}
	
}
