package spc.beans.service.rediscache;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.aspectj.weaver.Iterators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spc.beans.buffer.BaseBuffer;
import spc.beans.buffer.ServiceBuffer;
import spc.beans.dao.GenericDao;
import spc.beans.entity.spc.TSpcStandard;

/**
 * @description 标准缓存方法
 * @author YWW
 * @date 2017-11-1
 * */
@Service
public  class ParamStandardManagerService {
	@Autowired
	private  GenericDao genericDao; 
	/**
	 * @description:根据牌号flag返回最新的牌号参数标准
	 * @param brandflag
	 * */
	public  Map<String,TSpcStandard> getLatestParameterStandard(String brandId,String patch){
		Map<String,TSpcStandard> ParamStdMap =new HashMap<String, TSpcStandard>();
		try {
			String opcbrandId = BaseBuffer.getBrandID(brandId);
			if(opcbrandId!=null){
				List<TSpcStandard> stdlist = findLatestStandardList(opcbrandId);
				if(stdlist!=null)
				{ 
					for(int i=0;i<stdlist.size();i++){
						if(stdlist.get(i)==null) continue;
						TSpcStandard paramstd=stdlist.get(i);
						paramstd.setParamTag(BaseBuffer.getParameterTagById(paramstd.getFKParameterid()));
						if(ParamStdMap.containsKey(paramstd.getParamTag())){
							//如果有新标准则用新标准替换旧标准
							/*TSpcStandard paramstd_old=ParamStdMap.get(paramstd.getParamTag());
							if(Double.parseDouble(paramstd_old.getFCreateTime())<Double.parseDouble(paramstd.getFCreateTime())){
								ParamStdMap.replace(paramstd.getParamTag(), paramstd);
							}*/
						}else{
							ParamStdMap.put(paramstd.getParamTag(), paramstd); 
					}
					}
				} 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ParamStdMap;
	}
	
	private  List<TSpcStandard> findLatestStandardList(String brandId) {
		GenericDao genericDao1=genericDao;
		List<TSpcStandard> list = genericDao.getListTest("SPC.T_SPC_STANDARD.LISTBYQRY",new Object[]{brandId});  
		return list;
	}
}
