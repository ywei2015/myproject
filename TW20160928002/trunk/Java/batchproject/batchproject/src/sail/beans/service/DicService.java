package sail.beans.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sail.beans.dao.GenericDao;
import sail.beans.entity.Dic;

@Service
public class DicService {

	@Autowired
	private GenericDao genericDao;  
	
	/**
	 * 根据类型获取基础数据
	 * @param type
	 * @return
	 */
	public List getDicList(String type){
		List dicList =null;
		String sql = null;
		if("instorage".equals(type)){
			sql="SELECT * from v_bat_instorage_doctype";
			dicList = genericDao.getListWithNativeSql(sql);
		}else if("outstorage".equals(type)){
			sql="SELECT * from v_bat_outstorage_doctype";
			dicList = genericDao.getListWithNativeSql(sql);
		}
		return dicList;
	}
}
