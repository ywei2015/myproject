package spc.beans.job;

import org.springframework.beans.factory.annotation.Autowired;

import spc.beans.service.EliminateDataService;
import spc.beans.service.SaveParamBatchThread;

public class EliminateJob {

	@Autowired
	EliminateDataService eliminateDataService;

	public void doEliminateJob() throws InterruptedException{  
		eliminateDataService.doEliminate();
		SaveParamBatchThread.getInstance().handle(); //存储批次号参数到参数结果表，以备分析
	}
	
}
