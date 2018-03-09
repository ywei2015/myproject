package spc.beans.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Transactional;

import spc.beans.base.DatetimeEx;
import spc.beans.buffer.BaseBuffer;
import spc.beans.dao.GenericDao;
import spc.beans.entity.spc.BatchRDTState;
import spc.beans.entity.spc.TRtdLxPy;
import spc.beans.entity.spc.TSpcAbnormal;
import spc.beans.entity.spc.TSpcBrand;
import spc.beans.entity.spc.TSpcParameter;
import spc.beans.entity.spc.TSpcProcess;
import spc.beans.entity.spc.TSpcStatisticResult;
import spc.beans.service.prealarm.TimeValuePair; 

@Service
//@Transactional
@SuppressWarnings("unchecked")
public class TargetDBService { 

	@Autowired
	private GenericDao genericDao;   

	private final static String SQL_GetDBServerTime="select to_char(sysdate,'yyyy-MM-dd hh24:mi:ss') from dual";
 
	@Transactional
	public Date getDBServerTime(){ 
		Date dt = null;   
		//dt = (Date) genericDao.getObjectBySql("DBSERVER.CURDATETIME", null); 
		Object obj = genericDao.getObjectByNativeSql(SQL_GetDBServerTime);  
		if(obj!=null) dt = DatetimeEx.Str2Date(obj.toString());  
		return dt;
	}
	
	/** 
	* @Title: ExecuteSql 
	* @Description: TODO(执行本地SQL，异常由调用处理) 
	* @param insertSql  
	* @return int    返回类型 
	* @throws 
	* 2017年9月11日 下午5:47:35 最后修改人 GuveXie 
	*/
	@Transactional
	public int ExecuteSql(String insertSql){   
		return genericDao.executeNativeSql(insertSql); 
	} 

	/** 
	* @Title: CreateDataTableSql 
	* @Description: TODO(创建实时数据存放表) 
	* @param @param tablename  
	* @return boolean    返回类型 
	* 2017年9月11日 下午5:48:13 最后修改人 GuveXie 
	*/ 
	@Transactional
	public boolean CreateDataTableSql(String tablename){ 
		try
		{
			String sql = TRtdLxPy.getCreateTableSql(tablename);
			genericDao.executeNativeSql(sql);
			return true;
		}catch(Exception e){ 
			e.printStackTrace();
			return false;
		}
	}  

	private static String ExistTableSqlFormart = "select count(1) from all_tables where Upper(TABLE_NAME) = UPPER('%s') ";
	/** 
	* @Title: getExistsTableSql 
	* @Description: TODO(实时数据存放表是否存在) 
	* @param @param tablename  
	* @return int  返回类型>0则存在 
	* 2017年9月11日 下午5:48:13 最后修改人 GuveXie 
	*/
	@Transactional
	public int getTableIsExists(String tablename){
		if(BaseBuffer.getTableIsExist(tablename)) return 1;
		int n = 0;
		String sql = String.format(ExistTableSqlFormart, tablename);
		Object obj = genericDao.getObjectByNativeSql(sql);
		if(obj!=null) n = Integer.parseInt(obj.toString());
		if(n>0) {
			BaseBuffer.setTableIsExist(tablename, true);
		}else{
			if(CreateDataTableSql(tablename)){
				n=1;
				BaseBuffer.setTableIsExist(tablename, true);
			}
			else{
				n=0;
			}
		}
		return n;
	} 
	 

	/** 
	* @Title: setParamsInfo 
	* @Description: TODO(参数信息写入) 
	* @param @param tablename  
	* @return int  返回类型>0则表示成功 
	* 2017年9月11日 下午5:48:13 最后修改人 GuveXie 
	*/  
	@Transactional
	public int setParamsInfo(TSpcStatisticResult spcobj){
		try{
			String sql = spcobj.getIsExistSQL();
			Object obj = genericDao.getObjectByNativeSql(sql);
			int n = 0, k=0;
			if(obj!=null) n = Integer.parseInt(obj.toString());
			if(n>0) return 100;
			k = ExecuteSql(spcobj.toFirstCreateSql());
			return k;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}
	
	/** 
	* @Title: getProgressResult 
	* @Description: TODO(获取小未完成的统计批次结果)   
	* @return List<TSpcStatisticResult>    返回类型 
	* @throws 
	* 2017年9月22日 下午4:06:16 最后修改人 GuveXie 
	*/
	@Transactional
	public List<TSpcStatisticResult> getProgressResult(){
		List<TSpcStatisticResult> list = genericDao.getList("SPC.T_SPC_STATISTIC_RESULT.PROGRESS.LISTBYQRY",null);  
		return list;
	}

	/** 
	* @Title: getRDTGroupInfo 
	* @Description: TODO(获取实时数据表中某批次数据量结果)   
	* @throws 
	* 2017年9月22日 下午4:06:16 最后修改人 GuveXie 
	*/
	@Transactional
	public BatchRDTState getRDTGroupInfo(String batch, String tablename){ 
		BatchRDTState obj = null;
		String nativesql = BatchRDTState.getGroupBySql(batch, tablename); ///参数批次数据表
		Object dbres = this.genericDao.getObjectByNativeSql(nativesql);
		if(dbres==null) return obj; 
		obj = new BatchRDTState(dbres); 
		Object bobj = this.genericDao.getObjectByNativeSql(BatchRDTState.getLastBatchByRDTtable(tablename));
		if(bobj!=null)  obj.setLastbatch(bobj.toString());  
		return obj;
	}

	@Transactional
	public void SaveObject(Object obj){
		this.genericDao.save(obj);
	}
	
	public TSpcProcess getProcessById(String pid) { 
		return (TSpcProcess) this.genericDao.getById(TSpcProcess.class, pid);
	}
	public TSpcBrand  getBrandById(String pid) { 
		return (TSpcBrand) this.genericDao.getById(TSpcBrand.class, pid);
	}
	public TSpcParameter  getParameterById(String pid) { 
		return (TSpcParameter) this.genericDao.getById(TSpcParameter.class, pid);
	}
	 
	/** 
	* @Title: getRDTDataList 
	* @Description: TODO(获取实时数据)   
	* @return double [] 返回类型 
	* @throws 
	* 2017年9月22日 下午4:06:16 最后修改人 GuveXie 
	*/
	@SuppressWarnings("null")
	@Transactional
	public double [] getRDTDataList(String tablename, String batch){
		double [] data = null; 
		String nativeSql = String.format("select f_data from %s where f_batch='%s' and f_state='0' order by f_gather_time ", tablename, batch);
		List<Object> list = genericDao.getListWithNativeSql(nativeSql);      
		if(list==null&&list.isEmpty()) return null;
		data = new double[list.size()];
		for(int i=0;i<list.size();i++){
			/*if(list.get(i)!=null){ 
				if(list.get(i)==null) continue;
				data[i] = Double.valueOf(list.get(i).toString()); //list.get(i).doubleValue();
			} */
			if(list.get(i)==null) continue;
			if(list.get(i).toString().equals("")) continue; 
			data[i] = Double.valueOf(list.get(i).toString()); //list.get(i).value;
		}
		return data;
	}

	/** 
	* @Title: getRDTDataPairList 
	* @Description: TODO(获取实时数据)   
	* @return TimeValuePair [] 返回类型 
	* @throws 
	* 2017年10月11日 下午4:06:16 最后修改人 GuveXie 
	*/
	@SuppressWarnings("null")
	@Transactional
	public TimeValuePair [] getRDTDataPairList(String tablename, String batch){
		TimeValuePair [] data = null; 
		String nativeSql = String.format("select f_gather_time,f_data from %s where f_batch='%s' and f_state='0' order by f_gather_time ", tablename, batch);
		List<Object> list = genericDao.getListWithNativeSql(nativeSql);      
		if(list==null&&list.isEmpty()) return null;
		data = new TimeValuePair[list.size()];
		for(int i=0;i<list.size();i++){ 
			if(list.get(i)==null) continue;
			if(list.get(i).getClass().isArray()){
				TimeValuePair tmp = new TimeValuePair();
				tmp.timetag = ((Object[])list.get(i))[0].toString();
				tmp.value = Double.valueOf(((Object[])list.get(i))[1].toString()); 
				data[i] = tmp;
			}
		}
		return data;
	}


	/** 
	* @Title: SaveAbnormalInfo 
	* @Description: TODO(保存预报警信息) 
	* @param obj  TSpcAbnormal
	* @return int 返回执行是否成功 
	* 2017年10月10日 下午2:17:44 最后修改人  GuveXie 
	*/
	@Transactional
	public int SaveAbnormalInfo(TSpcAbnormal obj){
		try{
			genericDao.save(obj);  
			return 1;
		}catch(Exception e){
			e.printStackTrace();
		}
		return 0;
	}

	/** 
	* @Title: SaveAbnormalInfo 
	* @Description: TODO(保存预报警信息) 
	* @param obj  TSpcAbnormal
	* @return int 返回执行是否成功 
	* 2017年10月10日 下午2:17:44 最后修改人  GuveXie 
	*/
	@Transactional
	public int SaveAbnormalList(List<TSpcAbnormal> list){
		try{ 
			for(TSpcAbnormal obj:list){
				genericDao.save(obj);  
			}
			return 1;
		}catch(Exception e){
			e.printStackTrace();
		}
		return 0;
	}

	public Object getById(Class class1, String fPid) {
		Object obj=null;
		try {
			 obj=genericDao.getByIdWithSession(class1, fPid);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return obj;
	}
}
