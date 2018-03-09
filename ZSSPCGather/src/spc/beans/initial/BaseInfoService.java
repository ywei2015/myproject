package spc.beans.initial;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import spc.beans.base.DatetimeEx;
import spc.beans.dao.GenericDao;
import spc.beans.entity.spc.TSpcBrand;
import spc.beans.entity.spc.TSpcParameter;
import spc.beans.entity.spc.TSpcProcess;
import spc.beans.entity.spc.TSpcStandard;

@Service
@SuppressWarnings("unchecked")
public class BaseInfoService {

	@Autowired
	private GenericDao genericDao;   
	private final static String SQL_GetDBServerTime="select to_char(sysdate,'yyyy-MM-dd hh24:mi:ss') from dual";
 
	public List<TSpcProcess> getProcessList(){ 
		List<TSpcProcess> list = genericDao.getList("SPC.T_SPC_PROCESS.LISTBYQRY",null);  
		return list;
	}
	public List<TSpcParameter> getParamList(){ 
		List<TSpcParameter> list = genericDao.getList("SPC.T_SPC_PARAMETER.LISTBYQRY",null);  
		return list;
	}
	public List<TSpcBrand> getBrandList(){ 
		List<TSpcBrand> list = genericDao.getList("SPC.T_SPC_BRAND.LISTBYQRY",null);  
		return list;
	}
	public List<TSpcStandard> getStandardList(){ 
		List<TSpcStandard> list = genericDao.getList("SPC.T_SPC_STANDARD.LISTBYQRY",new Object[]{null});  
		return list;
	} 
	@Transactional
	public Date getDBServerTime(){ 
		Date dt = null;   
		//dt = (Date) genericDao.getObjectBySql("DBSERVER.CURDATETIME", null); 
		Object obj = genericDao.getObjectByNativeSql(SQL_GetDBServerTime);  
		if(obj!=null) dt = DatetimeEx.Str2Date(obj.toString());  
		return dt;
	}
	public List<TSpcStandard> getLatestStandardList(String brandflag) {
		List<TSpcStandard> list = genericDao.getList("SPC.T_SPC_STANDARD.LISTBYQRY",new Object[]{brandflag});  
		return list;
	}
	 
}
