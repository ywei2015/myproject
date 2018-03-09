package spc.beans.service;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysql.fabric.xmlrpc.base.Data;

import spc.beans.dao.GenericDao;
import spc.beans.base.DatetimeEx;
import spc.beans.buffer.BaseBuffer;
import spc.beans.buffer.PubConst;
import spc.beans.buffer.RegatherDataQueue;
import spc.beans.entity.spc.RealtimePointData;
import spc.beans.entity.spc.TSpcProcessBatch;

/**
 * @description 补采
 * @author YWW
 * @date 2017-11-1
 * */
@Service
public class RegatherDataService {
	
	@Autowired
	private JdbcTemplate jdbcTemplate; 
	
	@Autowired
	private GenericDao genericDao; 
	
	@Autowired
	private EliminateRegatherDataService eliminateRegatherDataService;
	
	@Transactional
	public String regatherByProcessBatch(String batch, String process, String startTime, String endTime){
		String brand="";
		String sql =  "";
		StringBuffer mess=new StringBuffer();
		String starttimepoint = DatetimeEx.toStr19(DatetimeEx.Str2Date14(startTime));
		String endtimepoint = DatetimeEx.toStr19(DatetimeEx.Str2Date14(endTime));
		String[] timeparam=new String[]{starttimepoint,endtimepoint};
		try{
			sql = BaseBuffer.getRegatherSql(process, timeparam, batch);
			if (sql != null && !"".equals(sql)){
				List<Map<String, Object>> rowdataList = null;
				try{
					InitJDBCConnect();
					rowdataList=jdbcTemplate.queryForList(sql);
				}catch(Exception ex){
					return mess.append("采集失败").toString(); //如果数据库连接问题，则下一次采集时间点不累加
				}
				if(rowdataList!=null&&rowdataList.size()>0){
					for (int i = 0; i < rowdataList.size(); i++) {
						Map<String, Object> rowdata=rowdataList.get(i);
						//批次号或者牌号为空则取下一个工序
						if(rowdata.get(PubConst.GX)==null||"".equals(rowdata.get(PubConst.GX).toString())) continue;
						if(rowdata.get(PubConst.BATCH)==null||"".equals(rowdata.get(PubConst.BATCH).toString())) 
							continue; 
						if(rowdata.get(PubConst.PH)==null||"".equals(rowdata.get(PubConst.PH).toString())) continue;
						//将工序数据加入数据点列队以待处理
						RealtimePointData pointdata = new RealtimePointData(rowdata);
						if(!pointdata.isEmptyData()){ //如果没有参数数据则放弃入队列
							RegatherDataQueue.in(pointdata); 
							brand=pointdata.getOpcbrand();
						}
					}
					if (RegatherDataQueue.getCount()>0) {
						mess.append("采集成功").toString();
						UpdateProcessBatchInfo(process,batch,brand);
						eliminateRegatherDataService.doEliminate();
					}else
						mess.append("暂无有效补采数据").toString();
				}else
					mess.append("暂无补采数据").toString();
				
			}   
		}catch(Exception ex){
			System.out.println("2222222");
			RegatherDataQueue.clearAll();
			ex.printStackTrace();
		} 
		return mess.toString();
	}
	
	@Transactional
	private void UpdateProcessBatchInfo(String process, String batch, String brand) {
		// TODO Auto-generated method stub
		try {
			TSpcProcessBatch tSpcProcessBatch=null;
			String FPid = String.format("%s#%s", batch, process);
		    tSpcProcessBatch=(TSpcProcessBatch) this.genericDao.getById(TSpcProcessBatch.class, FPid);
			if(tSpcProcessBatch!=null){
				if(!"0".equals(tSpcProcessBatch.getFState())){
					tSpcProcessBatch.setFState("0");
					this.genericDao.save(tSpcProcessBatch);
				}
			}else{
				//String batch, String process, String brand, String startDT
				tSpcProcessBatch=new TSpcProcessBatch(batch, process, brand,DatetimeEx.toStr14(new Date()) );
				tSpcProcessBatch.setFCreateTime(DatetimeEx.curDT14());
				this.genericDao.save(tSpcProcessBatch);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
			
		}
		
	}

	private void InitJDBCConnect(){ 
		if(jdbcTemplate==null){
			jdbcTemplate = new JdbcTemplate();
			jdbcTemplate.setQueryTimeout(3);
		}
 	}
	
}
