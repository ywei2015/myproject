package sail.beans.service;

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
	public List<Dic> getDicList(String type){
		List<Dic> dicList = genericDao.getListWithVariableParas("DIC.T_PUB_DIC_LIST.LIST", new Object[]{type});
		return dicList;
	}
}
