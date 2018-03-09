package spc.beans.service;

import java.util.List;

import spc.beans.buffer.ParamBatchBuffer;
import spc.beans.buffer.ServiceBuffer;
import spc.beans.entity.spc.TSpcStatisticResult;

public final class SaveParamBatchThread {  
	private static boolean  isDoing = false;
	private static long  resCount = 0;
	
	private static SaveParamBatchThread instance = null;
	public static SaveParamBatchThread getInstance(){
		if(instance==null)
			instance = new SaveParamBatchThread();
		return instance;
	}
	
	public void handle() { //AsyncHandler 
        new Thread() {
            public void run() {
            	try{
            		if(isDoing) return;
            		isDoing = true;
                	UpdateParamBathchDataToDB();
                }catch(Exception e){
                	System.out.println("--SaveParamBatchThread-Err:----------------");
                	e.printStackTrace();
                }finally{
                	isDoing = false;
                }
            } 
        }.start(); 
    }

	private void UpdateParamBathchDataToDB(){
		if(ServiceBuffer.getTargetService()==null) return;
		List<TSpcStatisticResult> list = ParamBatchBuffer.getFirstCreateList();
		if(list==null||list.isEmpty()) return;
		for(int i=0;i<list.size();i++){
			try{
				if(list.get(i)==null) continue; 
				int n = ServiceBuffer.getTargetService().setParamsInfo(list.get(i)); 
				if(n>0){
					ParamBatchBuffer.setSaved(
							ParamBatchBuffer.combinekey(list.get(i).getFParamId(), list.get(i).getFBatch()), 
							ParamBatchBuffer.SAVE_FLAG_CREATED);
					setResCount(getResCount() + 1);
				}
			}catch(Exception e){
            	System.out.println("--SaveParamBatchThread-Err1:----------------");
				e.printStackTrace();
			}
		}
	}

	public static long getResCount() {
		return resCount;
	}

	public static void setResCount(long resCount) {
		SaveParamBatchThread.resCount = resCount;
	}
}
