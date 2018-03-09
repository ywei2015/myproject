package spc.beans.buffer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spc.beans.entity.spc.TSpcBrand;
import spc.beans.entity.spc.TSpcParameter;
import spc.beans.entity.spc.TSpcProcess;
import spc.beans.entity.spc.TSpcStandard;

/** 
* @ClassName: BaseBuffer 
* @Description: TODO(业务基础信息缓存) 
* @author xieshihe 
* @date 2017年9月5日 下午5:22:14 
*  
*/
public final class BaseBuffer {
		
	private final static Logger logger=LoggerFactory.getLogger(BaseBuffer.class);
	/** 
	* @Fields ProcessMap : TODO(工序Id 及 工序参数) 
	* Key-->工序Tag
	*/ 
	private static Map<String,TSpcProcess> ProcessMap = new ConcurrentHashMap<String, TSpcProcess>();
	public static  Map<String,TSpcProcess> getProcessMap(){ return ProcessMap; }
	/** 
	* @Fields BrandMap : TODO(产品牌号 及 产品参数标准) 
	* Key-->OPC牌号代码
	*/ 
	private static Map<String,TSpcBrand> BrandMap = new ConcurrentHashMap<String, TSpcBrand>();
	public static  Map<String,TSpcBrand> getBrandMap(){ return BrandMap; }
	public static String getBrandID(String opcbrand){
		if(BrandMap.containsKey(opcbrand))
			if(BrandMap.get(opcbrand)!=null) 
				return BrandMap.get(opcbrand).getFPid();
		return null;
	}
	public static TSpcBrand getBrandByID(String pid){
		String opcbrand = getBrandTagById(pid);
		if(BrandMap.containsKey(opcbrand))
			if(BrandMap.get(opcbrand)!=null) 
				return BrandMap.get(opcbrand);
		return null;
	}

	/** 
	* @Fields ParamIDandTagMap : TODO(参数ID与参数Tag对应关系) 
	* Key-->ParameterID; Value-->Tag
	*/ 
	private static Map<String,String> ParamIDandTagMap = new ConcurrentHashMap<String, String>();
	/** 
	* @Fields ParamTagAndTablenameMap : TODO(参数Tag与参数数据TableName对应关系) 
	* Key-->ParameterTag; Value-->TableName
	*/ 
	private static Map<String,String> ParamTagAndTablenameMap = new ConcurrentHashMap<String, String>();
	/**  (参数Tag与参数数据TableName对应关系) Key-->ParameterTag; Value-->TableName */
	public static Map<String,String> getParamTagAndTablenameMap(){
		return ParamTagAndTablenameMap;
	}
	/** 
	* @Fields ParamTableExistMap : TODO(参数数据TableName是否存在对应关系) 
	* Key-->TableName; Value-->IsExistsState(true|false)
	*/ 
	private static Map<String,Boolean> ParamTableExistMap = new ConcurrentHashMap<String, Boolean>();
	/** 
	* @Fields ProcessIDandTagMap : TODO(工序ID与工序Tag对应关系) 
	* Key-->ProcessID; Value-->Tag
	*/ 
	private static Map<String,String> ProcessIDandTagMap = new ConcurrentHashMap<String, String>();
	/** 
	* @Fields BrandIDandTagMap : TODO(牌号ID与牌号Tag对应关系) 
	* Key-->BrandID; Value-->Tag
	*/ 
	private static Map<String,String> BrandIDandTagMap = new ConcurrentHashMap<String, String>();

	/** 
	* @Fields ReadOpcDataSqlMap : TODO(工序Tag与 读了工序上某时间点的SQL模板字符串 对应关系) 
	* Key-->BrandID; Value-->Tag
	*/ 
	private static Map<String,String> ReadOpcDataSqlMap = new ConcurrentHashMap<String, String>(); 
	

	/** 
	* @Title: setTableIsExist 
	* @Description: TODO(设置Tablename是否存在的状态) 
	* @param tablename 参数数据表名 
	* @param isExists   是否存在  
	* 2017年9月6日 上午10:32:30 最后修改人 GuveXie 
	*/
	public static void setTableIsExist(String tablename, boolean isExists){
		if(ParamTableExistMap .containsKey(tablename))
			ParamTableExistMap.replace(tablename, isExists);
		else 
			ParamTableExistMap.put(tablename, isExists);
	}
	/** 
	* @Title: getTableIsExist 
	* @Description: TODO(根据Tablename获取是否存在) 
	* @param tablename 参数数据表名 
	* @return boolean    返回Tablename是否存在
	* @throws 
	* 2017年9月6日 上午10:32:30 最后修改人 GuveXie 
	*/
	public static boolean getTableIsExist(String tablename){
		if(ParamTableExistMap .containsKey(tablename))
			return ParamTableExistMap.get(tablename);
		return false;
	}
	/** 
	* @Title: getTableNameByParamTag 
	* @Description: TODO(根据参数Tag获取Tablename) 
	* @param paramTag 参数Tag 
	* @return String    返回Tablename
	* @throws 
	* 2017年9月6日 上午10:32:30 最后修改人 GuveXie 
	*/
	public static String getTableNameByParamTag(String paramTag){
		if(ParamTagAndTablenameMap.containsKey(paramTag))
			return ParamTagAndTablenameMap.get(paramTag);
		return null;
	}
	/** 
	* @Title: getProcessTagById 
	* @Description: TODO(根据工序ID获取工序Tag) 
	* @param pid 工序ID  
	* @return String    返回工序Tag 
	* @throws 
	* 2017年9月6日 上午10:32:30 最后修改人 GuveXie 
	*/
	public static String getProcessTagById(String pid){
		if(ProcessIDandTagMap.containsKey(pid)) return ProcessIDandTagMap.get(pid);
		return "";
	}
	/** 
	* @Title: getProcessByTag 
	* @Description: TODO(根据工序Tag获取工序) 
	* @param tag 工序tag  
	* @return TSpcProcess 返回工序
	* @throws 
	* 2017年9月6日 上午10:32:30 最后修改人 GuveXie 
	*/
	public static TSpcProcess getProcessById(String id){
		if(ProcessMap.containsKey(id)) return ProcessMap.get(id);
		return null;
	}
	
	/** 
	* @Title: getParameterTagById 
	* @Description: TODO(根据参数ID获取参数Tag) 
	* @param pid 参数ID  
	* @return String    返回参数Tag 
	* @throws 
	* 2017年9月6日 上午10:32:30 最后修改人 GuveXie 
	*/
	public static String getParameterTagById(String pid){
		if(ParamIDandTagMap.containsKey(pid)) return ParamIDandTagMap.get(pid);
		return "";
	}

	/** 
	* @Title: getParameter 
	* @Description: TODO(根据工序ID和参数ID获取参数) 
	* @param pid 参数ID  
	* @return String    返回参数
	* @throws 
	* 2017年9月6日 上午10:32:30 最后修改人 GuveXie 
	*/
	public static TSpcParameter getParameter(String processid, String paramid){
		TSpcProcess process = getProcessById(processid);
		if(process==null) return null;
		String paramtag = getParameterTagById(paramid);
		if(process.ParameterMap.containsKey(paramtag))
			return process.ParameterMap.get(paramtag);
		return null;
	}

	/** 
	* @Title: getParameter 
	* @Description: TODO(根据参数ID获取参数) 
	* @param pid 参数ID  
	* @return String    返回参数 
	* @throws 
	* 2017年9月6日 上午10:32:30 最后修改人 GuveXie 
	*/
	public static TSpcParameter getParameter(String paramid){  
		String paramtag = getParameterTagById(paramid); 
		Set<String> KeySet =  ProcessMap.keySet();  
		for(String key:KeySet){ 
			if(ProcessMap.get(key).ParameterMap.containsKey(paramtag))
				return ProcessMap.get(key).ParameterMap.get(paramtag); 
		}
		return null;
	}

	/** 
	* @Title: getParameterIdByTag 
	* @Description: TODO(根据参数Tag获取参数ID) 
	* @param tag 参数tag  
	* @return String 返回参数ID
	* @throws 
	* 2017年9月6日 上午10:32:30 最后修改人 GuveXie 
	*/
	public static String getParameterIdByTag(String tag){ 
		Set<String> KeySet =  ParamIDandTagMap.keySet();  
		for(String key:KeySet){ 
			if(ParamIDandTagMap.get(key).equals(tag)) return key;
		}
		return null;
	}
	
	/** 
	* @Title: InitParamIDandTagMap 
	* @Description: TODO(工序参数) 
	* @param list  工序参数列表
	* @return boolean  是否成功 
	* @throws 
	* 2017年9月6日 上午10:18:19 最后修改人 GuveXie 
	*/
	public static boolean InitParamIDandTagMap(List<TSpcParameter>  list){
		if(list==null) return false;  
		for(int i=0;i<list.size();i++){ 
			if(ParamIDandTagMap.containsKey(list.get(i).getFPid())){
				ParamIDandTagMap.replace(list.get(i).getFPid(), list.get(i).getFTag());
			}else{ 
				ParamIDandTagMap.put(list.get(i).getFPid(), list.get(i).getFTag());
			}
		}
		return true;
	}
	/** 
	* @Title: getBrandTagById 
	* @Description: TODO(根据牌号ID获取牌号Tag) 
	* @param pid 牌号ID  
	* @return String    返回牌号Tag 
	* @throws 
	* 2017年9月6日 上午10:32:30 最后修改人 GuveXie 
	*/
	public static String getBrandTagById(String pid){
		if(BrandIDandTagMap.containsKey(pid)) return BrandIDandTagMap.get(pid);
		return "";
	}
	

	/** 
	* @Title: AddProcessList 
	* @Description: TODO(添加生产工序) 
	* @param processlist    生产工序列表 
	* @return boolean   成功与失败 
	* @throws 
	* 2017年9月5日 下午5:21:39 最后修改人 GuveXie 
	*/
	public static boolean AddProcessList(List<TSpcProcess> processlist){
		if(processlist==null) return false;
		for(int i=0;i<processlist.size();i++){
			AddProcess(processlist.get(i));
		}
		return true; 
	}
	
	/** 
	* @Title: AddProcess 
	* @Description: TODO(添加生产工序) 
	* @param process    生产工序 
	* @return boolean   成功与失败 
	* @throws 
	* 2017年9月5日 下午5:21:39 最后修改人 GuveXie 
	*/
	public static boolean AddProcess(TSpcProcess process){
		if(process==null) return false;
		//初始化工序缓存
		if(process.getFPid()==null) {
			logger.error("{}Id为空",process);
			System.out.println("process.Id为null");
		}
		if(ProcessMap.containsKey(process.getFPid()))
		{
			if(ProcessMap.get(process.getFPid()).ParameterMap.isEmpty())
				ProcessMap.replace(process.getFPid(), process);
		}
		else
			ProcessMap.put(process.getFPid(), process);
		//初始化工序的ID与Tag对应关系缓存
		if(ProcessIDandTagMap.containsKey(process.getFPid()))
		{ 
				ProcessIDandTagMap.replace(process.getFPid(), process.getFTag());
		}
		else
			ProcessIDandTagMap.put(process.getFPid(), process.getFTag());
		
		return true;
	}

	/** 
	* @Title: AddProcessParameter 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param processTag 工序
	* @param parameter  参数
	* @return boolean    成功与失败 
	* @throws 
	* 2017年9月5日 下午5:31:40 最后修改人 GuveXie 
	*/
	public static boolean AddProcessParameter(String processTag, TSpcParameter parameter){
		if(ProcessMap.isEmpty()) return false;
		if(parameter==null) return false;
		//初始化参数与参数Tablename对应关系
		if(ParamTagAndTablenameMap.containsKey(parameter.getFTag())){
			ParamTagAndTablenameMap.replace(parameter.getFTag(), parameter.getFDataTable());
		}else{ 
			ParamTagAndTablenameMap.put(parameter.getFTag(), parameter.getFDataTable());
		} 
		//初始化工序参数的ID与Tag对应关系缓存
		if(ParamIDandTagMap.containsKey(parameter.getFPid())){
			ParamIDandTagMap.replace(parameter.getFPid(), parameter.getFTag());
		}else{ 
			ParamIDandTagMap.put(parameter.getFPid(), parameter.getFTag());
		}
		//初始化工序的参数缓存 
		if(ProcessMap.containsKey(processTag))
		{
			if(ProcessMap.get(processTag).ParameterMap.containsKey(parameter.getFTag())){
				ProcessMap.get(processTag).ParameterMap.replace(parameter.getFTag(), parameter);
			}else{
				ProcessMap.get(processTag).ParameterMap.put(parameter.getFTag(), parameter); 
			} 
		}
		else
			return false;
		return true;
	}  

	/** 
	* @Title: AddBrandList 
	* @Description: TODO(添加生产牌号) 
	* @param brandlist    生产产品牌号列表 
	* @return boolean   成功与失败 
	* @throws 
	* 2017年9月5日 下午5:21:39 最后修改人 GuveXie 
	*/
	public static boolean AddBrandList(List<TSpcBrand> brandlist){
		if(brandlist==null) return false;
		for(int i=0;i<brandlist.size();i++){
			AddBrand(brandlist.get(i));
		}
		return true;
	}
	/** 
	* @Title: AddBrand 
	* @Description: TODO(添加生产牌号) 
	* @param brand    生产产品牌号 
	* @return boolean   成功与失败 
	* @throws 
	* 2017年9月5日 下午5:21:39 最后修改人 GuveXie 
	*/
	public static boolean AddBrand(TSpcBrand brand){
		if(brand==null) return false;
		//初始化牌号缓存
		if(BrandMap.containsKey(brand.getFOpcCode()))
		{
			if(BrandMap.get(brand.getFOpcCode()).ParamStdMap.isEmpty())
				BrandMap.replace(brand.getFOpcCode(), brand);
		}
		else
			BrandMap.put(brand.getFOpcCode(), brand);
		//初始化牌号ID与Tag关系缓存 
		if(BrandIDandTagMap.containsKey(brand.getFPid())){
			BrandIDandTagMap.replace(brand.getFPid(), brand.getFOpcCode());
		}else{ 
			BrandIDandTagMap.put(brand.getFPid(), brand.getFOpcCode());
		} 
		return true;
	}

	/** 
	* @Title: AddBrandParameterStandard 
	* @Description: TODO(增加牌号参数标准) 
	* @param opcbrand opc产品牌号代码
	* @param paramstd 参数
	* @return boolean 成功与失败 
	* @throws 
	* 2017年9月5日 下午5:31:40 最后修改人 GuveXie 
	*/
	public static boolean AddBrandParameterStandard(String opcbrand, TSpcStandard paramstd){
		if(BrandMap.isEmpty()) return false;
		if(paramstd==null) return false;
		if(BrandMap.containsKey(opcbrand))
		{ 
			paramstd.setParamTag(BaseBuffer.getParameterTagById(paramstd.getFKParameterid()));
			if(BrandMap.get(opcbrand).ParamStdMap.containsKey(paramstd.getParamTag())){
				//如果有新标准则用新标准替换旧标准
				/*TSpcStandard paramstd_old=BrandMap.get(opcbrand).ParamStdMap.get(paramstd.getParamTag());
				if(Double.parseDouble(paramstd_old.getFCreateTime())<Double.parseDouble(paramstd.getFCreateTime())){
					BrandMap.get(opcbrand).ParamStdMap.replace(paramstd.getParamTag(), paramstd);
				}*/
			}else{
				BrandMap.get(opcbrand).ParamStdMap.put(paramstd.getParamTag(), paramstd); 
			}
		}
		else
			return false;
		return true;
	} 

	/** 
	* @Title: getStandard 
	* @Description: TODO(获取牌号参数规格标准) 
	* @param brandid 产品牌号Id
	* @param paramId 参数Id
	* @return TSpcStandard 规格标准 
	* @throws 
	* 2017年9月25日 下午5:31:40 最后修改人 GuveXie 
	*/
	public static TSpcStandard getStandard(String brandid, String paramid){
		if(BrandMap.isEmpty()) return null;
		if(brandid==null||brandid.equals("")||paramid==null||paramid.equals("")) return null;
		TSpcBrand brand = getBrandByID(brandid);
		if(brand!=null)
		{ 
			String paramtag = getParameterTagById(paramid);
			if(brand.ParamStdMap.containsKey(paramtag)){
				return brand.ParamStdMap.get(paramtag);
			}
		}
		else
			return null;
		return null;
	}  
	public static String getSqlByProcessTag(String processTag){
		if(ReadOpcDataSqlMap.containsKey(processTag))
			return ReadOpcDataSqlMap.get(processTag);
		else 
			return null;
	}
	
	public static void ReloadSqlMap(){ 
		Set<String> processMapKeySet =  ProcessMap.keySet(); 
		for(String processkeyname:processMapKeySet){
			TSpcProcess process =  ProcessMap.get(processkeyname);
			if(process==null) continue;
			String sql_key = process.getFTag();
			String sql_value = "";
			sql_value += String.format("%s=[%s],", PubConst.BATCH, process.getFBatchTag());
			sql_value += String.format("%s=[%s],", PubConst.PH, process.getFPhTag());
			if(process.ParameterMap!=null){
				if(!process.ParameterMap.isEmpty()){
					Set<String> paramMapKeySet =  process.ParameterMap.keySet(); 
					for(String paramkeyname: paramMapKeySet){
						TSpcParameter param = process.ParameterMap.get(paramkeyname); 
						if(param!=null){ 
							//sql_value += String.format("%s=[%s],", param.getFTag(), param.getFTag()); 
							sql_value += String.format("[%s],", param.getFTag(), param.getFTag()); 
						}
					}
				}
			}
			ReadOpcDataSqlMap.put(sql_key, sql_value);
		}
	}
	
	private static String getDataSqlByPointTime1 = "SET QUOTED_IDENTIFIER OFF "
						+"SELECT * "
						+"FROM OPENQUERY(INSQL, "
						+"\"SELECT "
						+"        " + PubConst.DT + " = convert(nvarchar, DateTime, 20), "
						//+"        [SSHCGX_HZ.BATCH],"
						//+"        [SSHCGX_HZ.GDH],"
						//+"        [SSHCGX_HZ.PH],"
						//+"        [DZC119_HZ.FLOW],"
						//+"        [SSHCJ121_HZ.CKSF],"
						//+"        [SSHCJ121_HZ.CKWD]"
						+"@PARAMETERLIST "
						+"@PROCESSTAG " //此行解决末尾,的问题
						//+" DateTime = convert(nvarchar, DateTime, 120)"
						+" FROM WideHistory"
						+" WHERE wwRetrievalMode = 'Cyclic'"
						+" AND wwResolution = 1000"
						+" AND wwVersion = 'Latest' "
						+" AND DateTime <= '@TIMEPOINT'"
						+" AND DateTime >= '@TIMEPOINT' "
						+" \""
						+" )  "
			; 

	private static String getDataSqlByPointTime = "SET QUOTED_IDENTIFIER OFF "
						+"SELECT * "
						+"FROM OPENQUERY(INSQL, "
						+"\"SELECT "
						+"        " + PubConst.DT + " = convert(nvarchar, DateTime, 20), "
						//+"        [SSHCGX_HZ.BATCH],"
						//+"        [SSHCGX_HZ.GDH],"
						//+"        [SSHCGX_HZ.PH],"
						//+"        [DZC119_HZ.FLOW],"
						//+"        [SSHCJ121_HZ.CKSF],"
						//+"        [SSHCJ121_HZ.CKWD]"
						+"@PARAMETERLIST "
						+"@PROCESSTAG " //此行解决末尾,的问题
						+" FROM WideHistory"
						+" WHERE DateTime = '@TIMEPOINT'" 
						+" \""
						+" )  "
			; 
	/** 
	* @Title: getSql 
	* @Description: TODO(根据工序Tag和采集时间点获取要采集该工序参数数据的SQL) 
	* @param processTag 工序Tag
	* @param timepoint  时间点 yyyy-MM-dd hh:mm:ss
	* @return String 返回SQL 
	* @throws 
	* 2017年9月7日 下午5:51:34 最后修改人 GuveXie 
	*/
	public static String getSql(String processTag, String timepoint){
		String processtagstr = String.format(" %s='%s' ", PubConst.GX , processTag);
		processtagstr+=String.format(",%s = '%s'",PubConst.GXID,processTag);
		String params = ReadOpcDataSqlMap.containsKey(processTag)?ReadOpcDataSqlMap.get(processTag):"";
		String str = getDataSqlByPointTime.replace("@PROCESSTAG", processtagstr)
				.replace("@PARAMETERLIST", params)
				.replace("@TIMEPOINT", timepoint); 
		return str;
	}
	
	/** 
	* @Description: TODO(根据工序Tag和采集时间点获取要采集该工序参数数据的SQL) 
	* @param processTag 工序Tag
	* @param timepoint  时间点 yyyy-MM-dd hh:mm:ss
	* @return String 返回SQL 
	* @throws 
	*/
	public static String getRegatherSql(String processId, String[] timepoint,String batch){
		String processtagstr = String.format(" %s='%s' ", PubConst.GX , processId);
		//String processstr = String.format(" %s='%s' ", PubConst.GX , processTag);
		String params = ReadOpcDataSqlMap.containsKey(processId)?ReadOpcDataSqlMap.get(processId):"";
		String str = getDataSqlByProcessBatch.replace("@PROCESSTAG", processtagstr)
				.replace("@PARAMETERLIST", params)
				.replace("@STARTTIME", timepoint[0])
				.replace("@ENDTIME", timepoint[1]); 
		String batchsql="["+getProcessById(processId).getFBatchTag()+"] = '"+batch+"'";
		str=str.replace("@batch", batchsql);
		return str;
	}
	
	private static String getDataSqlByProcessBatch = "SET QUOTED_IDENTIFIER OFF "
			+"SELECT * "
			+"FROM OPENQUERY(INSQL, "
			+"\"SELECT "
			+"        " + PubConst.DT + " = convert(nvarchar, DateTime, 20), "
			//+"        [SSHCGX_HZ.BATCH],"
			//+"        [SSHCGX_HZ.GDH],"
			//+"        [SSHCGX_HZ.PH],"
			//+"        [DZC119_HZ.FLOW],"
			//+"        [SSHCJ121_HZ.CKSF],"
			//+"        [SSHCJ121_HZ.CKWD]"
			+"@PARAMETERLIST "
			+"@PROCESSTAG " //此行解决末尾,的问题
			+" FROM WideHistory"
			+" WHERE wwRetrievalMode = 'Cyclic'"
			+" AND wwResolution = 10000"
			+" AND wwVersion = 'Latest' "
			+"and DateTime >= '@STARTTIME'" 
			+"and DateTime <= '@ENDTIME'"
			+"and @batch"
			+" \""
			+" )  "
; 
	
}
