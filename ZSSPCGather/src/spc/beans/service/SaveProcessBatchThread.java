package spc.beans.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spc.beans.buffer.BaseBuffer; 
import spc.beans.buffer.ProcessBatchBuffer;
import spc.beans.buffer.ServiceBuffer;
import spc.beans.dao.GenericDao;
import spc.beans.entity.spc.RealtimePointData;
import spc.beans.entity.spc.TSpcProcessBatch; 

@Service
public final class SaveProcessBatchThread {   
	private static long  newCount = 0;  
	private static long  doneCount = 0;
	private RealtimePointData pdata = null;
	
	/*private static SaveProcessBatchThread instance = null;
	public static SaveProcessBatchThread getInstance(){
		if(instance==null)
			instance = new SaveProcessBatchThread();
		return instance;
	}*/
	@Autowired
	private GenericDao genericDao;
	
	public SaveProcessBatchThread(RealtimePointData ppdata){
		this.pdata = ppdata;
	}
	
	public void handle() { //AsyncHandler  
        new Thread() {
            public void run() {
            	try{  
        			///工序当前生产批次记录
        			String pbs = ProcessBatchBuffer.inProcessBatchMap(pdata.getProcessId(), pdata.getBatch());
        			if(pbs.equals(ProcessBatchBuffer.FLAG_NEW)){
        				//创建新的工序批次信息 
        				String processid = pdata.getProcessId();
        				String brandid = BaseBuffer.getBrandID(pdata.getOpcbrand());
        				if(processid==null||brandid==null) return;
        				//如果中途服务挂掉，防止启动时报错
        				String FPid = String.format("%s#%s", pdata.getBatch(), pdata.getProcessTag());
        				TSpcProcessBatch tSpcProcessBatch= (TSpcProcessBatch)ServiceBuffer.getTargetService().getById(TSpcProcessBatch.class, FPid);
        				if(tSpcProcessBatch==null){
        					TSpcProcessBatch tpb = new TSpcProcessBatch(pdata.getBatch(), pdata.getProcessTag(), brandid,pdata.getGathertime());
            				String sql = tpb.toFirstCreateSql();
            				int n = ServiceBuffer.getTargetService().ExecuteSql(sql);
            				if(n>0) newCount ++;
        				}
        			}else if(pbs.equals(ProcessBatchBuffer.FLAG_EXISTS)){
        				//正在生产的批次没变化
        			}else{
        				//生产批次变化，创建工序的新批次，并将原生产批次标为结束 
        				String processid = pdata.getProcessId();
        				String sql = TSpcProcessBatch.toProcessBatchEndSql(pdata.getBatch(), processid, pdata.getGathertime()); 
        				int n = ServiceBuffer.getTargetService().ExecuteSql(sql);
        				if(n>0){ newCount ++; doneCount ++; }   				
        			} 
                }catch(Exception e){
                	System.out.println("--SaveProcessBatchThread-Err:----------------");
                	e.printStackTrace();
                }finally{ }
            } 
        }.start(); 
    }

	public static long getNewCount() {
		return newCount;
	}

	public static void setNewCount(long newCount) {
		SaveProcessBatchThread.newCount = newCount;
	}

	public static long getDoneCount() {
		return doneCount;
	}

	public static void setDoneCount(long doneCount) {
		SaveProcessBatchThread.doneCount = doneCount;
	}
 
 
}
