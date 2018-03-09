package spc.beans.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spc.model.CPKEntity;
import com.spc.util.CPKCalculator;

import spc.beans.base.DatetimeEx;
import spc.beans.base.StringEx;
import spc.beans.buffer.AppInfo;
import spc.beans.buffer.BaseBuffer;
import spc.beans.buffer.PubConst;
import spc.beans.entity.spc.BatchRDTState;
import spc.beans.entity.spc.TSpcAbnormal;
import spc.beans.entity.spc.TSpcBrand;
import spc.beans.entity.spc.TSpcParameter;
import spc.beans.entity.spc.TSpcProcess;
import spc.beans.entity.spc.TSpcStandard;
import spc.beans.entity.spc.TSpcStatisticResult;
import spc.beans.service.prealarm.AlarmForecastBase;
import spc.beans.service.prealarm.AlarmWayFactory;
import spc.beans.service.prealarm.TimeValuePair;
import spc.beans.service.rediscache.ParamStandardManagerService;

/** 
* @ClassName: StatisticsService 
* @Description: TODO(统计各参数实时数据表中的数据) 
* @author xieshihe 
* @date 2017年9月8日 下午5:43:35  
*/
@Service
public class StatisticsService {
 
	@Autowired
	TargetDBService targetDBService;
	
	@Autowired
	ParamStandardManagerService paramStandardManager;
	private static boolean isStatisticDoing = false;

	public void doStatistical(){ 
		try
		{
			if(isStatisticDoing) return;
			isStatisticDoing = true;

			AppInfo.setLastStatisticsTimePoint(new Date());//状态更新设置
			 
			List<TSpcStatisticResult> reslist = targetDBService.getProgressResult(); 
			if(reslist==null) return;
			for(int i=0;i<reslist.size();i++){ 
				////异步 统计批次状态 A - StatisticalBatchState
				this.StatisticalBatchState(reslist.get(i)); 
					TSpcStatisticResult resobj = reslist.get(i);
					if(resobj==null) continue;
					//获取数据
					String paramTag = BaseBuffer.getParameterTagById(resobj.getFParamId());
					String tablename = BaseBuffer.getTableNameByParamTag(paramTag);
					TimeValuePair [] pairdata = this.getPairData(tablename, resobj.getFBatch());
					double [] data = this.getData(pairdata); //this.getData(tablename, resobj.getFBatch());
					//获取参数规格标准
					String brandTag=BaseBuffer.getBrandTagById(resobj.getFBrandId());
					Map<String,TSpcStandard> ParamStdMap= paramStandardManager.getLatestParameterStandard(brandTag,resobj.getFBatch());
				    TSpcStandard std=ParamStdMap.get(paramTag);
				    if(std!=null&&data.length>5){
				    	///异步 预报警过虑 B - 
						this.AlarmPredict(reslist.get(i), pairdata, std);
						///异步 统计批次SPC结果 C - 
						this.SPCAnalyz(reslist.get(i), data, std);
				    }
			} 
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			isStatisticDoing = false;
		}
	}
	
	private static boolean isStatisticBatchStateDoing = false; 
	/** 
	* @Title: StatisticalBatchState 
	* @Description: TODO(遍历参数及数据表，进行归集批次与参数信息)  
	* 补全批次SPC结果表中的信息，特别是状态信息   
	* @throws 
	* 2017年9月22日 下午3:48:04 最后修改人 GuveXie 
	*/
	public void StatisticalBatchState(TSpcStatisticResult resobj){
		try{
			if(isStatisticBatchStateDoing) return;
			isStatisticBatchStateDoing = true;
			//do something 
			String paramTag = BaseBuffer.getParameterTagById(resobj.getFParamId());///取参数Tag
			String paramtablename = BaseBuffer.getTableNameByParamTag(paramTag);///取参数存放实时表名 
			BatchRDTState batchrdtstate = targetDBService.getRDTGroupInfo(resobj.getFBatch(), paramtablename); ///参数批次数据表
			////?????接下来将此批次状态存入数据库，如果不是正在生产的批次则将本批次存为已完成的批次,同时完善批次基本信息
			String processid = resobj.getFProcess();
			if(processid==null||processid.equals("")){ 
				TSpcParameter parameter = BaseBuffer.getParameter(resobj.getFParamId()); 
				if(parameter==null) {
					System.out.println("--------"+resobj.getFParamId());
				}else{
					processid =parameter.getFProcessId();
				}
			}
			TSpcProcess process = BaseBuffer.getProcessById(processid);
			if(process!=null){
				if(resobj.getFWorkshop()==null||resobj.getFWorkshop().equals("")) {
					resobj.setFProcess(processid);
					resobj.setFWorksection(process.getFWorkshopId());
					resobj.setFProductLine(process.getFProductlineId()); 
					resobj.setFWorksection(process.getFSectionId()); 
				}
			}else{ 
				System.out.println("--------"+processid+"======");
			}
			TSpcBrand brand = BaseBuffer.getBrandByID(resobj.getFBrandId());
			if(brand!=null) resobj.setFBrandName(brand.getFName());
			TSpcParameter parameter1 = BaseBuffer.getParameter(resobj.getFProcess(), resobj.getFParamId());
			if(parameter1!=null) resobj.setFParamName(parameter1.getFName());  
			
			if(batchrdtstate!=null){
				if(batchrdtstate.getBatch().equals(batchrdtstate.getLastbatch())) 
					resobj.setFState(PubConst.SPCRESULT_STATE_RUN);  
				else 
					resobj.setFState(PubConst.SPCRESULT_STATE_END);  
			}else{
				//无数据打印日志
				System.out.println(String.format("--Batch:%s,tablename:%s is empty data.", resobj.getFBatch(), paramtablename));
			}
			
			resobj.setFLastModifiedTime(DatetimeEx.curDT14());
			targetDBService.SaveObject(resobj);
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			isStatisticBatchStateDoing = false;
		}
	} 

	private static boolean isAlarmPredicting = false; 
	/** 
	* @Title: AlarmPredict 
	* @Description: TODO(遍历参数及数据表，分析预测生成预警与报警信息) 
	* @param resobj 统计实体
	* @param data 参数数据集
	* @param std  工艺标准 
	* @return void    返回类型 
	* 2017年10月10日 上午10:16:34 最后修改人  GuveXie 
	*/
	public void AlarmPredict(TSpcStatisticResult resobj, TimeValuePair [] data, TSpcStandard std){
		try{
			if(isAlarmPredicting) return;
			isAlarmPredicting = true;
			AlarmWayFactory.init(); //初始化预报敬
			//获取预报警分析器
			List<AlarmForecastBase> list = AlarmWayFactory.getForecastWays(resobj.getFParamId());
			for(int i=0;i<list.size();i++){ 
				List<TSpcAbnormal> abnormallist = list.get(i).forecast(data, std);
				SaveAbnomalList(resobj, abnormallist);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			isAlarmPredicting = false;
		}
	} 
	private void SaveAbnomalList(TSpcStatisticResult resobj,List<TSpcAbnormal> abnormallist){ 
		if(abnormallist==null) return;
		String processname = BaseBuffer.getProcessById(resobj.getFProcess()).getFName();
		for(int i=0;i<abnormallist.size();i++){ 
			String FPid = resobj.getFBatch() + abnormallist.get(i).getFPid();
			abnormallist.get(i).setFPid(FPid);    
			abnormallist.get(i).setFBatch(resobj.getFBatch());
			abnormallist.get(i).setFBrandId(resobj.getFBrandId());
			abnormallist.get(i).setFBrandName(resobj.getFBrandName());
			abnormallist.get(i).setFParamId(resobj.getFParamId());
			abnormallist.get(i).setFParamName(resobj.getFParamName());
			abnormallist.get(i).setFProcess(resobj.getFProcess());
			abnormallist.get(i).setFProcessName(processname);
			abnormallist.get(i).setFWorksection(resobj.getFWorksection());
			abnormallist.get(i).setFProductLine(resobj.getFProductLine());
			//abnormallist.get(i).setFProductLinename(resobj.getFProductLine());
			abnormallist.get(i).setFWorksection(resobj.getFWorksection()); 
			abnormallist.get(i).setFWorkteam(resobj.getFWorkteam());
			abnormallist.get(i).setFWorktime(resobj.getFWorktime());
			//targetDBService.SaveAbnormalInfo(abnormallist.get(i));
		}
		targetDBService.SaveAbnormalList(abnormallist);
	}

	private static boolean isSPCAnalyzing = false; 
	/** 
	* @Title: SPCAnalyz 
	* @Description: TODO(遍历参数及数据表，进行批次数据的SPC统计分析生成结果信息) 
	* @param resobj 统计实体
	* @param data 参数数据集
	* @param std  工艺标准 
	* @return void    返回类型 
	* 2017年10月10日 上午10:18:02 最后修改人  GuveXie 
	*/
	public void SPCAnalyz(TSpcStatisticResult resobj, double [] data, TSpcStandard std){
		try{
			if(isSPCAnalyzing) return;
			isSPCAnalyzing = true; 
			//////放宽系数处理
			double modulus = std.getFModulus().doubleValue();
			if(modulus!=1.0d&&std.getFTarget()!=null){
				if(std.getFUsl()!=null) std.setFUsl(std.getFTarget()+(std.getFUsl()-std.getFTarget())*modulus);  
				if(std.getFLsl()!=null) std.setFLsl(std.getFTarget()-(std.getFTarget()-std.getFLsl())*modulus);  
			}
			//用工具统计SPC结果
			CPKEntity cpkmode = CPKCalculator.calculate(data, std.getFUsl(), std.getFLsl(), std.getFTarget());
			//赋给resobj并保存到数据库中
			resobj.setFUpline(std.getFUsl());
			resobj.setFLowline(std.getFLsl());
			resobj.setFTarget(std.getFTarget());
			resobj.setFModulus(std.getFModulus());
			resobj.setFAverage(cpkmode.getAverage());
			resobj.setFMax(cpkmode.getMax());
			resobj.setFMin(cpkmode.getMin());
			resobj.setFSigmaShort(cpkmode.getShorttermSigma());
			resobj.setFSigmaLong(cpkmode.getLongtermSigma());
			resobj.setFSampleCount(data.length); //总样本数
			resobj.setFValidSamplecount(cpkmode.getSamplesize()); //有效样本数
			resobj.setFUnquanlified(cpkmode.getOvernum());  //不合格数 
			
			//分组时有以下参数
			resobj.setFRAverage(cpkmode.getRAverage()); //RAverage
			resobj.setFSAverage(cpkmode.getSAverage()); //SAverage
			resobj.setFGroupcount(cpkmode.getGroupcount()); //Groupcount
			resobj.setFGroupsize(cpkmode.getGroupsize()); //Groupsize  
			
			resobj.setFSamplePassrate(cpkmode.getQualityrate().isNaN()?0:cpkmode.getQualityrate());  //不合格数
			resobj.setFSpcPassrate(cpkmode.getProcessfailurerate());
			resobj.setFCv(cpkmode.getCv());
			//resobj.setFCv(cpkmode.getCv().isNaN()?0:cpkmode.getCv());
			resobj.setFCa(cpkmode.getCa()); 
			resobj.setFCp(cpkmode.getCp()); 
			resobj.setFCpk(cpkmode.getCpk()); 
			resobj.setFPp(cpkmode.getPp()); 
			resobj.setFPpk(cpkmode.getPpk()); 
			resobj.setFKurtosis(cpkmode.getKurtosis().isNaN()?0:cpkmode.getKurtosis());
			resobj.setFSkewness(cpkmode.getSkewness().isNaN()?0:cpkmode.getSkewness());  
			resobj.setFLastModifiedTime(DatetimeEx.curDT14());
			resobj.setFRemark1(String.format("%.4f", cpkmode.getP()));
			resobj.setFRemark2(String.format("%s", cpkmode.getIsNormal()?"1":"0"));
			targetDBService.SaveObject(resobj);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			isSPCAnalyzing = false;
		}
	}
	
	/** 
	* @Title: getData 
	* @Description: TODO(根据表名和批次取具体数据) 
	* @param @param tablename
	* @param @param batch 
	* @return Double[] 返回类型 
	* @throws 
	* 2017年9月25日 上午11:09:13 最后修改人 GuveXie 
	*/
	private double[] getData(String tablename, String batch){
		double [] data = null;
		data = targetDBService.getRDTDataList(tablename, batch);
		return data;
	}
	/** 
	* @Title: getData 
	* @Description: TODO(抽取数据) 
	* @param pairdata  
	* @return double[]    返回类型 
	* 2017年10月11日 下午5:35:23 最后修改人  GuveXie 
	*/
	private double [] getData(TimeValuePair[] pairdata){
		if(pairdata==null) return null;
		double [] data = new double[pairdata.length]; 
		for(int i=0;i<pairdata.length;i++){
			data[i] = pairdata[i].value;
		}
		return data;
	}

	/** 
	* @Title: getPairData 
	* @Description: TODO(根据表名和批次取具体数据) 
	* @param @param tablename
	* @param @param batch 
	* @return Double[] 返回类型 
	* @throws 
	* 2017年9月25日 上午11:09:13 最后修改人 GuveXie 
	*/
	private TimeValuePair[] getPairData(String tablename, String batch){
		TimeValuePair [] data = null;
		data = targetDBService.getRDTDataPairList(tablename, batch);
		return data;
	}
	 
}
