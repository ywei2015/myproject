package tw.business.controller.equ;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tw.business.entity.pub.DicView;
import tw.business.service.equ.DicViewService;
import tw.sysbase.pub.ResultUtil;

/**
 * 视图查询
 *
 */
@RestController
@RequestMapping("dicView")
public class DicViewController {
	@Autowired
	private DicViewService dicViewService;
	/**
	 * 根据视图名称，查询数据集合
	 * @param view
	 * @return
	 */
	@RequestMapping("listDic")
	public  Map<String, Object> listDic(String view){
		List<DicView>list=dicViewService.listDicByView(view);
		Map<String, Object> result = ResultUtil.initResult();
		Map<String, Object> defResult = ResultUtil.DefResult(result, "data", list);
		
		return defResult ;
	}
	
}
