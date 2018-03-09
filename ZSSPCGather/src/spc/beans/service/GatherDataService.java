package spc.beans.service;
   
import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate; 
import org.springframework.stereotype.Service;
 
import spc.beans.base.DatetimeEx;
import spc.beans.base.PersistenceKit;
import spc.beans.buffer.AppInfo;
import spc.beans.buffer.BaseBuffer;
import spc.beans.buffer.PointDataQueue; 
import spc.beans.buffer.PubConst;
import spc.beans.entity.spc.RealtimePointData; 

/** 
* @ClassName: GatherDataService 
* @Description: TODO(OPC数据采集服务) 
* @author xieshihe 
* @date 2017年9月6日 下午4:21:29  
*/
@Service
public class GatherDataService {  
	@Autowired
	private JdbcTemplate jdbcTemplate;  
	
	//private static Date LastGatherTimePoint = null;
	private static boolean isReading = false;
	 
	/** 
	* @Title: ReadOpcList 
	* @Description: TODO(根据OPC的点，从实时库中读取OPC点的数据) 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws 
	* 2017年9月6日 下午2:37:20 最后修改人 GuveXie 
	*/
	public void ReadOpcList(){
		 try{  
			 if(isReading) return;
			 isReading = true;
			 if(AppInfo.getLastGatherTimePoint()==null) return;
			 if(AppInfo.getLastGatherTimePoint().before(AppInfo.sourceDBTime)){
				 //System.out.println("-------Gather--Go----------"); 
				 if(ReadDataByTimePoint()){
					 AppInfo.setLastGatherTimePoint( DatetimeEx.AddSecond(AppInfo.getLastGatherTimePoint(),6) ); 
					 PersistenceKit.setValue("LastGatherTimePoint", DatetimeEx.toStr14(AppInfo.getLastGatherTimePoint()));
					 /*long a = PointDataQueue.getCount();
					 System.out.println( String.format("LastGatherTimePoint: %s | SourceDBTime: %s | -----PointDataQueue Count: %s ----", 
							 DatetimeEx.toStr14(LastGatherTimePoint), DatetimeEx.toStr14(AppInfo.sourceDBTime)
							 , a));*/  
				 } 
			 }
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			isReading = false;
		}
	}
	
	/** 
	* @Title: ReadDataByTimePoint 
	* @Description: TODO(采集数据-将工序数据加入数据点列队以待处理)   
	* @return boolean    是否采集成功 
	* @throws 
	* 2017年9月8日 上午11:01:44 最后修改人 GuveXie 
	*/
	private boolean ReadDataByTimePoint(){ 
		String sql =  "";
		String timepoint = DatetimeEx.toStr19(AppInfo.getLastGatherTimePoint());
		Set<String> processKeySet =  BaseBuffer.getProcessMap().keySet();  
		for(String pkeyname:processKeySet){
			try{
				sql = BaseBuffer.getSql(pkeyname, timepoint);
				if (sql != null && !"".equals(sql)){
					Map<String, Object> rowdata = null;
					try{
						InitJDBCConnect();
						rowdata = jdbcTemplate.queryForMap(sql);   
					}catch(Exception ex){
						return false; //如果数据库连接问题，则下一次采集时间点不累加
					}
					if(rowdata!=null){ 
						//批次号或者牌号为空则取下一个工序
						if(rowdata.get(PubConst.GX)==null||"".equals(rowdata.get(PubConst.GX).toString())
								||"0".equals(rowdata.get(PubConst.GX).toString())) continue;
						if(rowdata.get(PubConst.BATCH)==null||"".equals(rowdata.get(PubConst.BATCH).toString())
								||"0".equals(rowdata.get(PubConst.BATCH).toString())) continue; 
						if(rowdata.get(PubConst.PH)==null||"".equals(rowdata.get(PubConst.PH).toString())
								||"0".equals(rowdata.get(PubConst.PH).toString())) continue;
							//将工序数据加入数据点列队以待处理
						RealtimePointData pointdata = new RealtimePointData(rowdata);
						if(!pointdata.isEmptyData()){ //如果没有参数数据则放弃入队列
							PointDataQueue.in(pointdata); 
							UpdateProcessBatchInfo(pointdata);
						}
					}
				}   
			}catch(Exception ex){
				ex.printStackTrace();
			} 
		}
		return true;
	}	
	 
 	public Date getDBServerCurTime(){
		String sql = "select convert(nvarchar, getdate(), 120) as curtime"; 
		Date dt = null;
		try{
			InitJDBCConnect();
			dt = jdbcTemplate.queryForObject(sql, Date.class);   
		}catch(Exception ex){
			ex.printStackTrace();
		}
	    //SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    //String dateStr = formatter.format(dt);  
		//System.out.println( String.format("%s   | getDBServerCurTime: %s ", formatter.format(new Date()) , dateStr));
		return dt;
	}
 	private void InitJDBCConnect(){ 
		if(jdbcTemplate==null){
			jdbcTemplate = new JdbcTemplate();
			jdbcTemplate.setQueryTimeout(3);
		}
 	}
 	
 	/** 
 	* @Title: UpdateProcessBatchInfo 
 	* @Description: TODO(异步更新工序当前生产批次) 
 	* @param pdata    工序时间点数据  
 	* 2017年9月19日 下午4:09:56 最后修改人 GuveXie 
 	*/
 	private void UpdateProcessBatchInfo(RealtimePointData pdata){ 
 		try
 		{
 			SaveProcessBatchThread thread = new SaveProcessBatchThread(pdata);
 			thread.handle();
 		}catch(Exception e){
 			e.printStackTrace();
 		}
 	}
 	
}
