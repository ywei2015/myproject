package tw.business.service.equ;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.business.entity.pub.DicView;
import tw.sysbase.dao.GenericDao;

@Service
@Transactional
public class DicViewService {
	
	@Autowired
	private GenericDao genericDao;	
	/**
	 * 更具视图名称查询数据
	 * @param view
	 * @return
	 */
	public List<DicView> listDicByView(String view) {
		String sql ="select t.f_pid,t.f_code,t.f_name,t.f_index from "+ view + "  t";
		List<Object[]> list = genericDao.getListWithNativeSql(sql);
		List<DicView> result =DataToDicView(list);
		return result;
	}
	public List<DicView> DataToDicView(List<Object[]> list) {
		List<DicView>res=new ArrayList<>();
		for(Object[] o:list) {
			DicView dicView=new DicView();
			dicView.setPid((String)o[0]);
			dicView.setCode((String)o[1]);
			dicView.setName((String)o[2]);
			//dicView.setIndex(Int(String)o[3]);
			res.add(dicView);
		}
		return res;
	}
	
	/**
	 * 视图名称和id查询唯一结果集
	 * @return
	 */
	public DicView getById(String view ,String id) {
		
		String sql ="select t.f_pid,t.f_code,t.f_name,t.f_index from "+ view + "  t  where t.f_pid= '"+id +"'" ;
		List<Object[]> list = genericDao.getListWithNativeSql(sql);
		DicView dicView=new DicView();
		if(list!=null && list.size()>0) {
			Object[]o=list.get(0);
			dicView.setPid((String)o[0]);
			dicView.setCode((String)o[1]);
			dicView.setName((String)o[2]);
		}
		return dicView;
	}
	

}
